package com.mhxh.proxyer.tcp.server.local;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.netty.AbstractLocalTcpProxyServer;
import com.mhxh.proxyer.tcp.server.handler.MessageLengthFromatHandler;
import com.mhxh.proxyer.tcp.server.handler.MyDataEncryptLoggerSimpleHandler;
import com.mhxh.proxyer.tcp.server.remote.MhxyV2GameRemoteServerProxyClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

public class MhxyV2LocalTcpServer extends AbstractLocalTcpProxyServer {

    private final ByteDataExchanger exchanger;

    public MhxyV2LocalTcpServer(String ip, int listener, int core, ByteDataExchanger exchanger) {
        super(ip, listener, core);
        this.exchanger = exchanger;
    }

    @Override
    protected ChannelHandler channelHandlers() {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                // 数据长度截取
                pipeline.addLast(new MessageLengthFromatHandler())
                        .addLast(new MyDataEncryptLoggerSimpleHandler(exchanger, ByteDataExchanger.SERVER_OF_LOCAL))
                        // 处理数据发送逻辑
                        .addLast(new SimpleChannelInboundHandler<ByteBuf>() {

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                // 构建链接游戏服务器的链接对象
                                logger.info("服务器信息注册.{}", ctx.channel().id());
                                Channel clientToRemoteGameServer = MhxyV2GameRemoteServerProxyClient.createInstance(
                                        MhxyV2LocalTcpServer.super.getIp()
                                        , MhxyV2LocalTcpServer.super.getPort(), MhxyV2LocalTcpServer.super.getCore(), exchanger);
                                exchanger.register(ctx.channel(), clientToRemoteGameServer);
                                super.channelActive(ctx);
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                Channel localChannel = channelHandlerContext.channel();

                                Channel remoteChannel = exchanger.getRemoteByLocal(localChannel);
                                if (null != remoteChannel) {
                                    remoteChannel.writeAndFlush(byteBuf.retain());
                                }
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                logger.info("关闭服务器{}", ctx.channel().id());
                                Channel remote = exchanger.clearByLocal(ctx.channel());
                                if (remote != null) {
                                    remote.close();
                                }
                                super.channelInactive(ctx);
                            }
                        })
                ;
            }
        };
    }
}
