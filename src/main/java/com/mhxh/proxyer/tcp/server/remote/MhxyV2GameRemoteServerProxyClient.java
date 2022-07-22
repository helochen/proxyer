package com.mhxh.proxyer.tcp.server.remote;


import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.netty.AbstractLinkGameServerClient;
import com.mhxh.proxyer.tcp.server.handler.MyDataEncryptLoggerSimpleHandler;
import com.mhxh.proxyer.tcp.server.handler.MyEncryptDelimiterBasedFrameDecorder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

// 代理远程服务器
public class MhxyV2GameRemoteServerProxyClient extends AbstractLinkGameServerClient {


    private final ByteDataExchanger exchanger;

    public MhxyV2GameRemoteServerProxyClient(String ip, int port, int core, ByteDataExchanger exchanger) {
        super(ip, port, core);
        this.exchanger = exchanger;
    }

    @Override
    protected ChannelHandler channelHandler() {
        return new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();

                pipeline.addLast(new MyEncryptDelimiterBasedFrameDecorder())
                        .addLast(new MyDataEncryptLoggerSimpleHandler(exchanger
                                , ByteDataExchanger.SERVER_OF_REMOTE
                                , MhxyV2GameRemoteServerProxyClient.super.getPort()))
                        .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
                                if (getPort() == 8084) {
                                    if (exchanger.filterServerCommand(byteBuf)) {
                                        return;
                                    }
                                }
                                Channel local = exchanger.getLocalByRemote(ctx.channel());
                                if (null != local) {
                                    local.writeAndFlush(byteBuf.retain());
                                }
                            }
                        })
                ;
            }
        };
    }

    public static Channel createInstance(String ip, int port, int core, ByteDataExchanger exchanger) {

        MhxyV2GameRemoteServerProxyClient client = new MhxyV2GameRemoteServerProxyClient(ip, port, core, exchanger);

        // 初始化
        try {
            client.startAsync();
            client.awaitRunning();
            client.getChannel().closeFuture().addListener(ChannelFutureListener.CLOSE)
                    .addListener((ChannelFutureListener) channelFuture -> {
                        if (client.isRunning()) {
                            client.stopAsync();
                            client.awaitTerminated();
                            logger.error("Channel使得代理服务器关闭...{}:{}", ip, port);
                        }
                    });
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (client.isRunning()) {
                    client.stopAsync();
                    client.awaitTerminated();
                }
            }));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return client.getChannel();
    }
}
