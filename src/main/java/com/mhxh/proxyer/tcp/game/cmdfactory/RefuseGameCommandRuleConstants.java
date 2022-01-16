package com.mhxh.proxyer.tcp.game.cmdfactory;

import io.netty.buffer.ByteBufUtil;

public class RefuseGameCommandRuleConstants {


    /**
     * 单旗子的飞行返回弹窗命令内容，用于拦截
     */
    private static final String REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_GBK = "请送我过去";
    public static final byte[] REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_BYTES = ByteBufUtil.decodeHexDump(REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_GBK);
}
