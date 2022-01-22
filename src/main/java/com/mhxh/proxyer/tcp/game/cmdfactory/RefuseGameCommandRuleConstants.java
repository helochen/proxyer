package com.mhxh.proxyer.tcp.game.cmdfactory;

import com.mhxh.proxyer.tcp.game.constants.TaskConstants;

import java.nio.charset.Charset;

public class RefuseGameCommandRuleConstants {


    /**
     * 单旗子的飞行返回弹窗命令内容，用于拦截
     */
    private static final String REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_GBK = "请送我过去";
    public static final byte[] REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_BYTES = REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 如果有抓鬼任务抓鬼弹窗拦截
     */

    private static final String REFUSE_CMD_CATCH_GHOST_TASK_LIST_CONTENT_GBK = TaskConstants.TASK_CATCH_GHOST;
    public static final byte[] REFUSE_CMD_CATCH_GHOST_TASK_LIST_CONTENT_BYTES = REFUSE_CMD_CATCH_GHOST_TASK_LIST_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 如果有杜少海任务弹窗拦截
     */
    private static final String REFUSE_CMD_JIANGHU_TASK_CONTENT_GBK = TaskConstants.TASK_JIAN_HU;
    public static final byte[]  REFUSE_CMD_JIANGHU_TASK_CONTENT_BYTES = REFUSE_CMD_JIANGHU_TASK_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 拒绝任务抓鬼的领取扽弹窗
     */
    private static final String REFUSE_CMD_CATCH_GHOST_POPUP_CONTENT_GBK = "现在做鬼的也不安分";
    public static final byte[] REFUSE_CMD_CATCH_GHOST_POPUP_CONTENT_BYTES = REFUSE_CMD_CATCH_GHOST_POPUP_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 拒绝抓鬼的提示信息弹窗
     */
    private static final String REFUSE_CMD_CATCH_GHOST_INFORMATION_CONTENT_GBK = "请立即前去降服";
    public static final byte[] REFUSE_CMD_CATCH_GHOST_INFORMATION_CONTENT_BYTES = REFUSE_CMD_CATCH_GHOST_INFORMATION_CONTENT_GBK.getBytes(Charset.forName("GBK"));
}
