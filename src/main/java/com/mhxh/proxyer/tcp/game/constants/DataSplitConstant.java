package com.mhxh.proxyer.tcp.game.constants;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.regex.Pattern;

public class DataSplitConstant {

    public static final Pattern DATA_PATTERN = Pattern.compile("\\{.*?}");


    public static final Pattern TASK_DECOMPOSITION_POSTION = Pattern.compile("(?<=#Y/).*?(?=#W)");

    public static final Pattern TASK_DECOMPOSITION_NAME = Pattern.compile("(?<=#G/).*?(?=#R)");

    public static final Pattern TASK_DECOMPOSITION_X_Y = Pattern.compile("(.*?)");

    public static void main(String[] args) {
        String m ="3a3700000000500000000000" +
                "646f206c6f63616c207265743d7bd0f2bac53d313030352cc4dac8dd3d7b5b315d3d313139332c5b325d3d32302c5b335d3d377d7d2072657475726e2072657420656e64";

        String m2 = "646f206c6f63616c207265743d7bd0f2bac53d313530312cc4dac8dd3d7bd1a1cfee3d7b5b315d3d22c7ebcbcdced2b9fdc8a5222c5b325d3d22ced2d4d9cfebcfeb227d2cb6d4bbb03d22c7ebc8b7c8cfcac7b7f1d2aab4abcbcdd6c123472fb3a4b0b2b3c7283437332c3234392923572fb4a6227d7d2072657475726e2072657420656e64";

        String x  = "3a3700000000860000000000";
        String y ="646f206c6f63616c207265743d7bd0f2bac53d313530312cc4dac8dd3d7bd1a1cfee3d7b5b315d3d22c7ebcbcdced2b9fdc8a5222c5b325d3d22ced2d4d9cfebcfeb227d2cb6d4bbb03d22c7ebc8b7c8cfcac7b7f1d2aab4abcbcdd6c123472fb3a4b0b2b3c7283437332c3234392923572fb4a6227d7d2072657475726e2072657420656e64";
        byte[] bytes = ByteBufUtil.decodeHexDump(x);
        System.out.println(((byte)bytes.length & 0xff));
        byte[] bytes1 = ByteBufUtil.decodeHexDump(y);
        System.out.println(((byte)bytes1.length )& 0xff);

        byte[] bytes2 = ByteBufUtil.decodeHexDump(x+y);
        System.out.println(((byte)bytes2.length )& 0xff);


        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.directBuffer(153);

        byteBuf.writeBytes(ByteBufUtil.decodeHexDump("3a3700000000"))
                .writeByte(bytes1.length)
                .writeBytes(ByteBufUtil.decodeHexDump("0000000000"))
                .writeBytes(ByteBufUtil.decodeHexDump(y));

        System.out.println(ByteBufUtil.hexDump(byteBuf));


        ReferenceCountUtil.release(byteBuf);


    }
}
