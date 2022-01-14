package com.mhxh.proxyer.tcp.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class MyDelimiterBasedFrameDecoder extends DelimiterBasedFrameDecoder {


    private static final ByteBuf delimiter = Unpooled.copiedBuffer("return ret end".getBytes());

    public MyDelimiterBasedFrameDecoder() {
        super(8192 * 8, false, delimiter);
    }
}
