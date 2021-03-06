package com.mhxh.proxyer.tcp.server.handler;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskRejectedException;


public class MyDataLoggerSimpleHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger logger = LoggerFactory.getLogger(MyDataLoggerSimpleHandler.class);

    private ByteDataExchanger exchanger;
    private int type;

    public MyDataLoggerSimpleHandler(ByteDataExchanger exchanger, int type) {
        super();
        this.exchanger = exchanger;
        this.type = type;

    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf buf) {
        ByteBuf recordBuf = buf.retainedSlice();
        try {
            exchanger.getTaskExecutor().execute(() -> exchanger.getDumpDataService().outputHexStrAndFormatStr(recordBuf, type));
        } catch (TaskRejectedException e) {
            logger.error("出现了奇怪的异常！！！！{}", e.getMessage());
            ReferenceCountUtil.release(recordBuf);
        }
        channelHandlerContext.fireChannelRead(buf.retain());
    }
}
