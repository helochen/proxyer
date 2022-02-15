package com.mhxh.proxyer.tcp.netty;

import com.google.common.util.concurrent.AbstractIdleService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class:  AbstractLinkGameServerClient
 * <p>
 * Author: Chen
 * Date:   2019/6/12 15:56
 */

public abstract class AbstractLinkGameServerClient extends AbstractIdleService {

    public static final Logger logger = LoggerFactory.getLogger(AbstractLinkGameServerClient.class);

    @Getter
    private Channel channel;

    private final Bootstrap clientBootstrap;
    private final EventLoopGroup loopGroup;

    private final SocketAddress socketAddress;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    public AbstractLinkGameServerClient(final String ip, final int port, final int core) {
        clientBootstrap = new Bootstrap();
        loopGroup = new NioEventLoopGroup(core);
        socketAddress = new InetSocketAddress(ip, port);
    }

    protected abstract ChannelHandler channelHandler();

    @Override
    protected void startUp() throws Exception {
        run(false);
    }

    @Override
    protected void shutDown() throws Exception {
        loopGroup.shutdownGracefully();
        loopGroup.terminationFuture();
    }

    public void run(boolean retry) throws InterruptedException {
        if (!retry) {
            logger.info("listener init.....");
            clientBootstrap.group(loopGroup).channel(NioSocketChannel.class)
                    .handler(channelHandler());
        } else {
            logger.warn("retry......");
        }
        channel = clientBootstrap.connect(socketAddress).addListener(future -> {
            if (!future.isSuccess()) {
                logger.warn("connect fail will retry again....");
                scheduledThreadPoolExecutor.schedule(() -> {
                    try {
                        logger.info("begin to retry...............");
                        run(true);
                    } catch (Exception e) {
                        logger.error("retry error!{}", e.getMessage());
                    }
                }, 5L, TimeUnit.SECONDS);
            }
        }).sync().channel();
        channel.closeFuture().addListener(ChannelFutureListener.CLOSE).addListener((ChannelFutureListener) channelFuture -> logger.error("远程游戏服务器TCP链接被关闭....."));
        logger.info("connect to 远程游戏服务器成功......");
    }
}
