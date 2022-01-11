package com.mhxh.proxyer.tcp.server.local;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.netty.AbstractLocalTcpProxyServer;
import com.mhxh.proxyer.tcp.server.handler.MyDelimiterBasedFrameDecoder;
import com.mhxh.proxyer.tcp.server.remote.MhxyGameServerProxyClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

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

        return new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();

                pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {

                    private AtomicInteger count = new AtomicInteger(2);

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
                        readBuf.markReaderIndex();

                        String hexDump = ByteBufUtil.hexDump(readBuf);
                        int d1a1d6d0 = hexDump.indexOf("d1a1d6d0"); // 移动到数据块
                        byte[] number = d1a1d6d0 >= 0 ? ByteBufUtil.getBytes(readBuf, d1a1d6d0 / 2 + 7, 1) : null; // 数据位置
                        int d2b3cafd = hexDump.indexOf("d2b3cafd"); // 移动到数据块
                        byte[] page = d2b3cafd >= 0 ? ByteBufUtil.getBytes(readBuf, d2b3cafd / 2 + 7, 1) : null; // 数据位置

                        if (d2b3cafd >= 0 && d1a1d6d0 >= 0) {
                            logger.info("强制修改为买茶花");
                            if (readBuf.getByte(d1a1d6d0 / 2 + 8) != (byte) ',') {
                                ByteBufUtil.setShortBE(readBuf, d1a1d6d0 / 2 + 7, (byte) 53);
                            } else {
                                readBuf.setByte(d1a1d6d0 / 2 + 7 , (byte)53);
                            }
                            readBuf.setByte(d2b3cafd / 2 + 7 , (byte)49);
                        }

                        readBuf.resetReaderIndex();
                        logger.info("{},数据长度：{},\t\n发送16进制数据{}," +
                                        "\t\n发送数量：{}" +
                                        "\t\n发送页数：{}" +
                                        "\t\n发送数据: {}", channelHandlerContext.channel().id(), readBuf.readableBytes(), hexDump
                                , number == null ? "无数据" : Byte.toUnsignedInt(number[0])
                                , page == null ? "无数据" : Byte.toUnsignedInt(page[0])
                                , readBuf.toString(Charset.forName("GBK")));
                        byteBuf.release();

                        Channel remoteChannel = exchanger.getRemoteByLocal(channelHandlerContext.channel());
                        if (remoteChannel != null) {
                            remoteChannel.writeAndFlush(byteBuf.retain());
                        } else {
                            logger.info("没找到映射");
                        }
                        if (count.decrementAndGet() == 0) {
                            channel.pipeline().addFirst(new MyDelimiterBasedFrameDecoder());
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
                });
            }
        };
    }
}
