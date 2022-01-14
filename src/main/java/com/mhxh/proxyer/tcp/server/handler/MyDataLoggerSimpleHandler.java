package com.mhxh.proxyer.tcp.server.handler;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class MyDataLoggerSimpleHandler extends SimpleChannelInboundHandler<ByteBuf> {


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
        exchanger.getTaskExecutor().execute(() -> exchanger.getDumpDataService().outputHexStrAndFormatStr(recordBuf, type));
        channelHandlerContext.fireChannelRead(buf.retain());
    }
}
