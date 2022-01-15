package com.mhxh.proxyer.tcp.server.remote;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.constants.GameCommandConstant;
import com.mhxh.proxyer.tcp.game.constants.ProxyCommandConstant;
import com.mhxh.proxyer.tcp.netty.AbstractLinkGameServerClient;
import com.mhxh.proxyer.tcp.server.handler.MyDataLoggerSimpleHandler;
import com.mhxh.proxyer.tcp.server.handler.MyDelimiterBasedFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.*;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;


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
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();


                pipeline.addLast(new MyDelimiterBasedFrameDecoder());

                pipeline.addLast(new MyDataLoggerSimpleHandler(exchanger, ByteDataExchanger.SERVER_OF_REMOTE));

                pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {


                        Channel local = exchanger.getLocalByRemote(channelHandlerContext.channel());
                        if (null != local) {
                            ByteBuf hookBuf = byteBuf.retainedSlice();
                            boolean skipCmd = false;
                            if (ByteDataExchanger.NextPosition.size() > 0 && ByteBufUtil.indexOf(MAP_HOOK_HEADER, hookBuf) >= 0) {
                                for (String key : ByteDataExchanger.NextPosition.keySet()) {
                                    String[] remove = ByteDataExchanger.NextPosition.remove(key);

                                    if (!ObjectUtils.isEmpty(remove)) {
                                        String cmdContent = String.format(ProxyCommandConstant.FLY_MAP_1_TYPE_DIRECT, key, remove[0], remove[1]);

                                        byte[] contentBytes = cmdContent.getBytes(Charset.forName("GBK"));

                                        ByteBuf newCmd = ByteBufAllocator.DEFAULT.directBuffer(GameCommandConstant.CMD_MAP_DIRECT_FLY_1_BYTES.length + contentBytes.length);
                                        newCmd.writeBytes(ByteBufUtil.decodeHexDump(GameCommandConstant.CMD_MAP_DIRECT_FLY_1_ONE_PART))
                                                .writeByte(contentBytes.length)
                                                .writeBytes(ByteBufUtil.decodeHexDump(GameCommandConstant.CMD_MAP_DIRECT_FLY_1_TWO_PART))
                                                .writeBytes(contentBytes);
                                        skipCmd = true;
                                        logger.info("接受伪装命令：{}=={}", ByteBufUtil.hexDump(newCmd), newCmd.toString(Charset.forName("GBK")));
                                        local.writeAndFlush(newCmd.retain());
                                        newCmd.release();
                                    }
                                }
                            }
                            if (!skipCmd) {
                                local.writeAndFlush(byteBuf.retain());
                            } else {
                                logger.info("跳过服务器数据：{}", byteBuf.toString(Charset.forName("GBK")));
                            }

                            hookBuf.release();
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
