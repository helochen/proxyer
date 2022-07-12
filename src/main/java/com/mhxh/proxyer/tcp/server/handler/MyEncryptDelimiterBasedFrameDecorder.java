package com.mhxh.proxyer.tcp.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class MyEncryptDelimiterBasedFrameDecorder extends DelimiterBasedFrameDecoder {


    private static final ByteBuf delimiter = Unpooled.copiedBuffer("fN,ET,nB,Aa,fN,Hu, fN,ET,nB, ET,Hu,mP,".getBytes());

    public MyEncryptDelimiterBasedFrameDecorder() {
        super(8192 * 8, false, delimiter);
    }
}
