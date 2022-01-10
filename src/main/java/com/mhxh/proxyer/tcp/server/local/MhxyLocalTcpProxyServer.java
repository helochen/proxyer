package com.mhxh.proxyer.tcp.server.local;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.netty.AbstractLocalTcpProxyServer;
import com.mhxh.proxyer.tcp.server.remote.MhxyGameServerProxyClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class MhxyLocalTcpProxyServer extends AbstractLocalTcpProxyServer {


    @Autowired
    private ByteDataExchanger exchanger;

    @Value("${game.server.ip}")
    private String gameIp;

    @Value("${game.server.port}")
    private int gamePort;

    private int core;

    public MhxyLocalTcpProxyServer(@Value("${local.server.ip}") String ip
            , @Value("${local.server.port}") int listener
            , @Value("${server.core}") int core) {
        super(ip, listener, core);
        this.core = core;
    }

    @Override
    protected ChannelHandler channelHandlers() {
        return new SimpleChannelInboundHandler<ByteBuf>() {

            @Override
            public boolean isSharable() {
                return true;
            }

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                // 构建链接游戏服务器的链接对象
                logger.info("服务器信息注册.{}", ctx.channel().id());
                Channel clientToRemoteGameServer = MhxyGameServerProxyClient.createInstance(gameIp, gamePort, core, exchanger);
                exchanger.register(ctx.channel(), clientToRemoteGameServer);
                super.channelActive(ctx);
            }

            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                ByteBuf readBuf = byteBuf.retainedSlice();
                // 复制
                logger.info("{}发送数据:{}", channelHandlerContext.channel().id(), readBuf.toString(Charset.forName("GBK")));

                byteBuf.release();
                Channel remoteChannel = exchanger.getRemoteByLocal(channelHandlerContext.channel());
                if (remoteChannel != null) {
                    remoteChannel.writeAndFlush(byteBuf.retain());
                } else {
                    logger.info("没找到映射");
                }
            }

            @Override
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                logger.info("关闭服务器{}" , ctx.channel().id());
                Channel remote = exchanger.clearByLocal(ctx.channel());
                if (remote != null) {
                    remote.close();
                }
                super.channelInactive(ctx);
            }
        };
    }
}
