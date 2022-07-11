package com.mhxh.proxyer.tcp.server.local;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.netty.AbstractLocalTcpProxyServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class MhxyV2LocalTcpServer extends AbstractLocalTcpProxyServer {

    private ByteDataExchanger exchanger;

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
                // TODO 数据长度截取


            }
        };
    }
}
