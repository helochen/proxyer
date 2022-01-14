package com.mhxh.proxyer.tcp.game;

import io.netty.buffer.ByteBufUtil;

import java.util.regex.Pattern;

public class DataSplitConstant {

    public static final Pattern DATA_PATTERN = Pattern.compile("\\{.*?}");


    public static final Pattern TASK_DECOMPOSITION_POSTION = Pattern.compile("(?<=#Y/).*?(?=#W)");

    public static final Pattern TASK_DECOMPOSITION_NAME = Pattern.compile("(?<=#G/).*?(?=#R)");

    public static final Pattern TASK_DECOMPOSITION_X_Y = Pattern.compile("(.*?)");

    public static void main(String[] args) {
        String m ="3a3700000000500000000000" +
                "646f206c6f63616c207265743d7bd0f2bac53d313030352cc4dac8dd3d7b5b315d3d313139332c5b325d3d32302c5b335d3d377d7d2072657475726e2072657420656e64";

        String m2 = "3a3700000000460000000000" +
                "646f206c6f63616c207265743d7bd0f2bac53d313030352cc4dac8dd3d7b5b315d3d313139332c5b325d3d3134392c5b335d3d36357d7d2072657475726e2072657420656e64";
                String x = "3a3700000000460000000000";
        String y ="646f206c6f63616c207265743d7bd0f2bac53d313030352cc4dac8dd3d7b5b315d3d313139332c5b325d3d3134392c5b335d3d36357d7d2072657475726e2072657420656e64";
        byte[] bytes = ByteBufUtil.decodeHexDump(x);
        System.out.println((byte)bytes.length);
        byte[] bytes1 = ByteBufUtil.decodeHexDump(y);
        System.out.println((byte)bytes1.length);

        byte[] bytes2 = ByteBufUtil.decodeHexDump(x+y);
        System.out.println((byte)bytes2.length);

    }
}
