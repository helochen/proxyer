package com.mhxh.proxyer.tcp.netty;

import com.google.common.util.concurrent.AbstractIdleService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Class:  AbstractLocalTcpProxyServer
 * <p>
 * Author: Chen
 * Date:   2019/5/22 14:12
 */

public abstract class AbstractLocalTcpProxyServer extends AbstractIdleService {

    public static final Logger logger = LoggerFactory.getLogger(AbstractLocalTcpProxyServer.class);

    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workGroup;

    private final SocketAddress socketAddress;
    @Getter
    private final int core;

    public AbstractLocalTcpProxyServer(final String ip, final int listener, final int core) {
        logger.info("AbstractLocalTcpProxyServer listen port:{}", listener);
        this.core = core;
        serverBootstrap = new ServerBootstrap();

        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup(core);

        socketAddress = new InetSocketAddress(ip, listener);
    }

    protected abstract ChannelHandler channelHandlers();

    @Override
    protected void shutDown() throws Exception {
        stop();
    }

    @Override
    protected void startUp() throws InterruptedException {
        serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(channelHandlers()).bind(socketAddress).sync().channel().closeFuture().addListener(ChannelFutureListener.CLOSE)
        .addListener((ChannelFutureListener) channelFuture -> logger.error("游戏客户端TCP链接断开..."));

    }

    private void stop() throws InterruptedException {
        logger.info("shut down tcp server..............start");
        workGroup.shutdownGracefully();
        workGroup.terminationFuture().sync();
        bossGroup.shutdownGracefully();
        bossGroup.terminationFuture().sync();
        logger.info("shut down tcp server..............end");
    }
}
