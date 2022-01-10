package com.mhxh.proxyer.tcp.server.remote;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.netty.AbstractLinkGameServerClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import java.nio.charset.Charset;


public class MhxyGameServerProxyClient extends AbstractLinkGameServerClient {


    private final ByteDataExchanger exchanger;

    private MhxyGameServerProxyClient(String ip, int port, int core, ByteDataExchanger exchanger) {
        super(ip, port, core);
        this.exchanger = exchanger;
    }

    @Override
    protected ChannelHandler channelHandler() {


        return new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();

                ByteBuf delimiter = Unpooled.copiedBuffer("return ret end".getBytes());

                pipeline.addLast(new DelimiterBasedFrameDecoder(8192, false, delimiter));

                pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        ByteBuf receive = byteBuf.retainedSlice();
                        logger.info("{},数据长度：{}服务返回数据：{}", channelHandlerContext.channel().id(),receive.readableBytes() ,receive.toString(Charset.forName("GBK")));
                        receive.release();

                        Channel local = exchanger.getLocalByRemote(channelHandlerContext.channel());
                        if (null != local) {
                            local.writeAndFlush(byteBuf.retain());
                        }
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
