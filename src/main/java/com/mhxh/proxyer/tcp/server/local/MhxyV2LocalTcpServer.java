package com.mhxh.proxyer.tcp.server.local;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.netty.AbstractLocalTcpProxyServer;
import com.mhxh.proxyer.tcp.server.handler.ChannelRegisterIdentifySimpleHandler;
import com.mhxh.proxyer.tcp.server.handler.MessageLengthFromatHandler;
import com.mhxh.proxyer.tcp.server.handler.MyDataEncryptLoggerSimpleHandler;
import com.mhxh.proxyer.tcp.server.remote.MhxyV2GameRemoteServerProxyClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

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
                pipeline.addLast(new MessageLengthFromatHandler());
                if (getPort() == 8084) {
                    pipeline.addLast(new ChannelRegisterIdentifySimpleHandler(exchanger));
                }
                pipeline.addLast(new MyDataEncryptLoggerSimpleHandler(exchanger
                        , ByteDataExchanger.SERVER_OF_LOCAL
                        , MhxyV2LocalTcpServer.super.getPort()))
                        // 处理数据发送逻辑
                        .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                // 构建链接游戏服务器的链接对象
                                logger.info("端口：{}服务器信息注册.{}", getPort(), ctx.channel().id());
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
                                    if (getPort() == 8084) {
                                        // 请求好友信息，代理命令
                                        if (byteBuf.toString(Charset.forName("GBK")).contains("VP,wd,*-*mP,m9, P8,m9,wu,Lq,P8, fN,ET,nB,={}")) {
                                            logger.info("好友命令请求：-------------------------");
                                            if (!exchanger.requestFakeCommand(remoteChannel)) {
                                                remoteChannel.writeAndFlush(byteBuf.retain());
                                            }
                                        }
                                    } else {
                                        remoteChannel.writeAndFlush(byteBuf.retain());
                                    }

                                }
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                logger.info("关闭本地服务器的链接->{}:{}", getIp(), getPort());
                                Channel remote = exchanger.clearByLocal(ctx.channel());
                                if (remote != null) {
                                    remote.close();
                                }
                                super.channelInactive(ctx);
                            }
                        });
            }
        };
    }
}
