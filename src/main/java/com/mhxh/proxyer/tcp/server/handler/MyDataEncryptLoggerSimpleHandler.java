package com.mhxh.proxyer.tcp.server.handler;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskRejectedException;

public class MyDataEncryptLoggerSimpleHandler extends SimpleChannelInboundHandler<ByteBuf> {


    private static final Logger logger = LoggerFactory.getLogger(MyDataEncryptLoggerSimpleHandler.class);

    private final ByteDataExchanger exchanger;
    private final int type;
    private final int port;

    public MyDataEncryptLoggerSimpleHandler(ByteDataExchanger exchanger, int type, int port) {
        super();
        this.exchanger = exchanger;
        this.type = type;
        this.port = port;

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf buf) throws Exception {
        ByteBuf recordBuf = buf.retainedSlice();
        try {
            exchanger.getTaskExecutor().execute(() -> {
                exchanger.getDumpDataService().outputEncryptHexStrAndFormatStr(recordBuf, type, port);
            });
        } catch (TaskRejectedException e) {
            logger.error("出现了奇怪的异常！！！！{}", e.getMessage());
        }
        channelHandlerContext.fireChannelRead(buf.retain());
    }
}
