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
    private static final String REFUSE_CMD_JIANGHU_TASK_CONTENT_GBK = TaskConstants.TASK_JIANG_HU;
    public static final byte[] REFUSE_CMD_JIANGHU_TASK_CONTENT_BYTES = REFUSE_CMD_JIANGHU_TASK_CONTENT_GBK.getBytes(Charset.forName("GBK"));

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

    /**
     * 拒绝境外旗子弹窗的功能
     */
    private static final String REFUSE_CMD_JINGWAI_FLAG_POPUP_CONTENT_GBK = "序号=3529,内容={地图=1173";
    public static final byte[] REFUSE_CMD_JINGWAI_FLAG_POPUP_CONTENT_BYTES = REFUSE_CMD_JINGWAI_FLAG_POPUP_CONTENT_GBK.getBytes(Charset.forName("GBK"));


    /**
     * 拒绝与鬼聊天的弹窗功能
     */
    private static final String REFUSE_TALK_WITH_GHOST_POPUP_CONTENT_GBK = "悄悄地告诉你，其实我是从地狱里偷跑出来的";
    public static final byte[] REFUSE_TALK_WITH_GHOST_POPUP_CONTENT_BYTES = REFUSE_TALK_WITH_GHOST_POPUP_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 拒绝飞行符的弹窗
     */
    private static final String REFUSE_FLY_CONTENT_POPUP_CONTENT_GBK = "{序号=13,内容=\"1\"}";
    public static final byte[] REFUSE_FLY_CONTENT_POPUP_CONTENT_BYTES = REFUSE_FLY_CONTENT_POPUP_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 继续抓鬼的避免弹窗功能
     */
    private static final String REFUSE_CONTINUE_CATCH_GHOST_POPUP_CONTENT_GBK = "少侠干得不错哟，如果你愿意继续协助我捉拿这些小鬼";
    public static final byte[] REFUSE_CONTINUE_CATCH_GHOST_POPUP_CONTENT_BYTES = REFUSE_CONTINUE_CATCH_GHOST_POPUP_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 拒绝技能弹窗
     */
    private static final String REFUSE_XIANLING_DIANPU_POPUP_CONTENT_GBK = "名称=\"仙灵店铺\"";
    public static final byte[] REFUSE_XIANLING_DIANPU_POPUP_CONTENT_BYTES = REFUSE_XIANLING_DIANPU_POPUP_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 拒绝青龙堂弹窗
     */
    private static final String REFUSE_QINGLONG_TASK_CONTENT_POPUP_CONTENT_GBK = "听闻帮派青龙堂的青龙堂主管正在四处搜寻";
    public static final byte[] REFUSE_QINGLONG_TASK_CONTENT_POPUP_CONTENT_GBK_BYTES = REFUSE_QINGLONG_TASK_CONTENT_POPUP_CONTENT_GBK.getBytes(Charset.forName("GBK"));

    /**
     * 已经有人领取抓鬼任务弹窗
     */
    private static final String REFUSE_CATCH_GHOST_GET_TASK_AGAIN_POPUP_CONTENT_GBK = "队伍中已有人领取过此任务";
    public static final byte[] REFUSE_CATCH_GHOST_GET_TASK_AGAIN_POPUP_CONTENT_GBK_BYTES = REFUSE_CATCH_GHOST_GET_TASK_AGAIN_POPUP_CONTENT_GBK.getBytes(Charset.forName("GBK"));
}
