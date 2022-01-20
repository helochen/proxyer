package com.mhxh.proxyer.tcp.game.cmdfactory;

import java.nio.charset.Charset;

public class RefuseGameCommandRuleConstants {


    /**
     * 单旗子的飞行返回弹窗命令内容，用于拦截
     */
    private static final String REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_GBK = "请送我过去";
    public static final byte[] REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_BYTES = REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_GBK.getBytes(Charset.forName("GBK"));
}
