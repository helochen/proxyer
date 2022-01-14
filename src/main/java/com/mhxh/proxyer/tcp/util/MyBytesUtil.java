package com.mhxh.proxyer.tcp.util;

import io.netty.buffer.ByteBuf;

public class MyBytesUtil {


    public static Boolean startWithBytes(ByteBuf buf, byte[] target) {
        if (buf.readableBytes() >= target.length) {
            for (int i = 0; i < target.length; i++) {
                if (buf.getByte(i) != target[i])
                    return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
