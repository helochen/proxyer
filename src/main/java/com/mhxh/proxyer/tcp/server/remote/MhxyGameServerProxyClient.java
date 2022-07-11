package com.mhxh.proxyer.tcp.server.remote;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.constants.GameCommandConstant;
import com.mhxh.proxyer.tcp.netty.AbstractLinkGameServerClient;
import com.mhxh.proxyer.tcp.server.handler.MyDataLoggerSimpleHandler;
import com.mhxh.proxyer.tcp.server.handler.MyDelimiterBasedFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;


public class MhxyGameServerProxyClient extends AbstractLinkGameServerClient {

    private ByteBuf MAP_HOOK_HEADER = null;

    private final ByteDataExchanger exchanger;

    private MhxyGameServerProxyClient(String ip, int port, int core, ByteDataExchanger exchanger) {
        super(ip, port, core);
        this.exchanger = exchanger;

        MAP_HOOK_HEADER = ByteBufAllocator.DEFAULT.directBuffer(GameCommandConstant.CMD_MAP_DIRECT_FLY_1_BYTES.length);
        MAP_HOOK_HEADER.writeBytes(GameCommandConstant.CMD_MAP_DIRECT_FLY_1_BYTES);
    }

    @Override
    protected ChannelHandler channelHandler() {


        return new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel channel) {
                ChannelPipeline pipeline = channel.pipeline();


                pipeline.addLast(new MyDelimiterBasedFrameDecoder());

                pipeline.addLast(new MyDataLoggerSimpleHandler(exchanger, ByteDataExchanger.SERVER_OF_REMOTE));

                pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        if (!exchanger.filterServerCommand(byteBuf)) {
                            Channel local = exchanger.getLocalByRemote(channelHandlerContext.channel());
                            if (null != local) {
                                local.writeAndFlush(byteBuf.retain());
                            }
                        }
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

                    }
                });
            }
        };


    }

    public static Channel createInstance(String ip, int port, int core, ByteDataExchanger exchanger) {

        MhxyGameServerProxyClient client = new MhxyGameServerProxyClient(ip, port, core, exchanger);

        // 初始化
        try {
            client.startAsync();
            client.awaitRunning();
            client.getChannel().closeFuture().addListener(ChannelFutureListener.CLOSE)
                    .addListener((ChannelFutureListener) channelFuture -> {
                        if (!client.isRunning()) {
                            client.stopAsync();
                            logger.error("代理服务器关闭...");
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
