package com.mhxh.proxyer.tcp.server.local;

import com.mhxh.proxyer.fake.command.IFormatCommand;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;
import com.mhxh.proxyer.tcp.game.constants.GameCommandConstant;
import com.mhxh.proxyer.tcp.netty.AbstractLocalTcpProxyServer;
import com.mhxh.proxyer.tcp.server.handler.MyDataLoggerSimpleHandler;
import com.mhxh.proxyer.tcp.server.handler.MyDelimiterBasedFrameDecoder;
import com.mhxh.proxyer.tcp.server.remote.MhxyGameServerProxyClient;
import com.mhxh.proxyer.tcp.util.MyBytesUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MhxyLocalTcpProxyServer extends AbstractLocalTcpProxyServer {


    @Autowired
    private ByteDataExchanger exchanger;

    private static ByteBuf CMD_TIME_HEX_BYTEBUF = null;
    private static ByteBuf CMD_VERIFY_HEX_BYTEBUF = null;


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

        CMD_TIME_HEX_BYTEBUF = ByteBufAllocator.DEFAULT.directBuffer(GameCommandConstant.CMD_TIME_HEX_BYTES.length);
        CMD_TIME_HEX_BYTEBUF.writeBytes(GameCommandConstant.CMD_TIME_HEX_BYTES);

        CMD_VERIFY_HEX_BYTEBUF = ByteBufAllocator.DEFAULT.directBuffer(GameCommandConstant.CMD_VERIFY_BYTES.length);
        CMD_VERIFY_HEX_BYTEBUF.writeBytes(GameCommandConstant.CMD_VERIFY_BYTES);


    }

    @Override
    protected ChannelHandler channelHandlers() {

        return new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel channel) {
                ChannelPipeline pipeline = channel.pipeline();

                pipeline.addLast(new MyDataLoggerSimpleHandler(exchanger,
                        ByteDataExchanger.SERVER_OF_LOCAL));

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


                        Channel remoteChannel = exchanger.getRemoteByLocal(channelHandlerContext.channel());
                        if (remoteChannel == null) {
                            logger.info("没找到映射");
                        } else {
                            ByteBuf readBuf = byteBuf.retainedSlice();
                            boolean isProxy = false;
                            if (MyBytesUtil.startWithBytes(readBuf, GameCommandConstant.CMD_OPEN_FRIEND_LIST_BYTES)) {
                                //  解析验证参数
                                int timeIdx = ByteBufUtil.indexOf(CMD_TIME_HEX_BYTEBUF, readBuf);
                                int verifyIdx = ByteBufUtil.indexOf(CMD_VERIFY_HEX_BYTEBUF, readBuf);

                                if (timeIdx >= 0 && verifyIdx >= 0) {
                                    byte[] timeBytes = new byte[GameCommandConstant.CMD_CONTENT_TIME_CONTENT_LENGTH];
                                    readBuf.getBytes(timeIdx + GameCommandConstant.CMD_CONTENT_TIME_SKIP_LENGTH, timeBytes);

                                    byte[] verifyBytes = new byte[GameCommandConstant.CMD_CONTENT_VERIFY_CONTENT_LENGTH];
                                    readBuf.getBytes(verifyIdx + GameCommandConstant.CMD_CONTENT_VERIFY_SKIP_LENGTH, verifyBytes);
                                    String timeGBK = new String(timeBytes, Charset.forName("GBK"));
                                    String verifyGBK = new String(verifyBytes, Charset.forName("GBK"));
                                    IFormatCommand oneCommand = exchanger.getOneCommand();
                                    if (!ObjectUtils.isEmpty(oneCommand)) {
                                        String fakeCmdContent = oneCommand.format(timeGBK, verifyGBK);
                                        logger.info("发送伪装命令：{}", fakeCmdContent);
                                        byte[] fakeCmdContentBytes = fakeCmdContent.getBytes(Charset.forName("GBK"));

                                        ByteBuf proxyCmd = ByteBufAllocator.DEFAULT.directBuffer(LocalSendCommandRuleConstants.COMMAND_STANDARD_LENGTH + fakeCmdContentBytes.length);
                                        try {
                                            isProxy = true;
                                            proxyCmd.writeBytes(LocalSendCommandRuleConstants.COMMAND_STANDARD_HEAD_BYTES)
                                                    .writeByte(fakeCmdContentBytes.length)
                                                    .writeBytes(LocalSendCommandRuleConstants.COMMAND_STANDARD_HEAD_APPEND_BYTES)
                                                    .writeBytes(fakeCmdContentBytes);
                                            remoteChannel.writeAndFlush(proxyCmd.retain());
                                        } finally {
                                            ReferenceCountUtil.release(proxyCmd);
                                        }
                                    }
                                }

                            }
                            if (!isProxy) {
                                remoteChannel.writeAndFlush(byteBuf.retain());
                            }

                            if (count.decrementAndGet() == 0) {
                                channel.pipeline().addFirst(new MyDelimiterBasedFrameDecoder());
                            }
                            readBuf.release();
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
