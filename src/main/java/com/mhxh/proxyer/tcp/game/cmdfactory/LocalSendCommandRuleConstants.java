package com.mhxh.proxyer.tcp.game.cmdfactory;

import io.netty.buffer.ByteBufUtil;

public class LocalSendCommandRuleConstants {


    /**
     * 通用12字节长度的命令头,
     * 分割为　前缀，　＋　　长度　＋　后缀
     * hex:3a3700000000 + 68 +0000000000
     */
    public static final String COMMAND_STANDARD_HEX_HEAD = "3a3700000000";
    public static final String COMMAND_STANDARD_HEX_HEAD_APPEND = "0000000000";

    public static final byte[] COMMAND_STANDARD_HEAD_BYTES = ByteBufUtil.decodeHexDump(COMMAND_STANDARD_HEX_HEAD);
    public static final byte[] COMMAND_STANDARD_HEAD_APPEND_BYTES = ByteBufUtil.decodeHexDump(COMMAND_STANDARD_HEX_HEAD_APPEND);

    public static final int COMMAND_STANDARD_LENGTH = 12;

    /**
     * 使用道具
     */
    public static final String USE_ITEM_FORMAT_GKB = "3705*-*do local ret={[\"时间\"]=%s,[\"类型\"]=\"道具\",[\"数据验证\"]=\"%s\",[\"编号\"]=%s,[\"窗口\"]=\"主人公\",[\"序列\"]=0} return ret end";

    /**
     * 使用飞行道具,序号代表地图的位置信息
     */
    public static final String USE_ITEM_FLY_TO_MAP_GKB = "3706*-*do local ret={[\"时间\"]=%s,[\"序列\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 使用传送人飞行,长安全门派传送人的传送命令1001 代表长安的地图需要，12NPC顺序？？序列不知道是啥
     */
    public static final String USE_NPC_FLY_TO_ALL_SECT_GBK = "1501*-*do local ret={[\"数据验证\"]=\"%s\",[\"地图\"]=1001,[\"编号\"]=12,[\"时间\"]=%s,[\"序列\"]=12} return ret end";

    /**
     * 长安NPC直接飞各个门派的命令
     */
    public static final String USE_CHANG_AN_NPC_FLY_TO_SECT_GBK = "1502*-*do local ret={[1]=\"%s\",[2]=1001,[3]=\"门派传送人\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";


    /**
     * 使用旗子飞行时候的请求单旗子
     */
    public static final String USE_SIGLE_FLAG_TO_MAP_POSITION_GBK = "1502*-*do local ret={[1]=\"请送我过去\",[2]=%s,[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";


    /**
     * 使用道具后会有个无名的操作,应该是通知服务器刷新之类的，毕竟数据发生变动
     * <p>
     * 2022-01-16 这个是打开背包的协议
     */
    public static final String USE_ITEM_FORMAT_GBK_APPEND = "3699*-*do local ret={[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";


    /**
     * 飞往江南野外的功能，会提示距离过远
     */
    public static final String USE_TRANSFER_TO_JIANG_NAN_YEWAI_MSG_GBK = "1003*-*do local ret={[\"说明\"]=\"长安传送江南野外\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\",[\"id\"]=1} return ret end";


    /**
     * 行走地图,目的地
     */
    public static final String ROLE_WALK_TO_DESTINATION_GBK = "1001*-*do local ret={[\"y\"]=%s,[\"x\"]=%s,[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";


    /**
     * 行走过程
     */
    public static final String ROLE_WALKING_RUNNING_GBK = "1002*-*do local ret={[\"x\"]=%s,[\"方向\"]=1,[\"类型\"]=\"行走\",[\"y\"]=%s,[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    public static final String ROLE_WALKING_STOPPING_GBK = "1002*-*do local ret={[\"x\"]=%s,[\"方向\"]=1,[\"类型\"]=\"停止\",[\"y\"]=%s,[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 请求任务
     */
    public static final String ROLE_REQUEST_TASK_LIST = "10*-*do local ret={[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 点击鬼的请求任务
     */
    public static final String CATCH_GHOST_NPC_REQUSET_BEGIN_SEND_COMMAND_CONTENT_GBK = "1501*-*do local ret={[\"时间\"]=%s,[\"数据验证\"]=\"%s\",[\"地图\"]=%s,[\"编号\"]=%s,[\"序列\"]=%s,[\"标识\"]=%s} return ret end";


    /**
     * 开干鬼的命令
     */
    public static final String CATCH_GHOST_NPC_REQUEST_FIGHT_COMMAND_CONTENT_GBK = "1502*-*do local ret={[1]=\"回你的地狱去\",[2]=%s,[3]=\"%s\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 进入战斗后有个请求,未知
     */
    public static final String CATCH_GHOST_AFTER_FIGHT_UNKNOWN_COMMEND_CONTENT_GBK = "5501*-*do local ret={[\"时间\"]=%s,[\"数据验证\"]=\"%s\"}";

    /**
     * 送回地府
     */
    public static final String GO_BACK_TO_UNDER_GROUND_CONTENT_GBK = "1502*-*do local ret={[1]=\"请送我回阴曹地府\",[2]=%s,[3]=\"钟馗\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";


    /**
     * 请求抓鬼命令
     */
    public static final String GET_CATCH_GHOST_COMMAND_CONTENT_GBK = "1501*-*do local ret={[\"数据验证\"]=\"%s\",[\"地图\"]=1122,[\"编号\"]=1,[\"时间\"]=%s,[\"序列\"]=1} return ret end";

    /**
     * 确认任务
     */
    public static final String CATCH_GHOST_TASK_FOR_SURE_CONTENT_GBK = "1502*-*do local ret={[1]=\"好的 我帮你\",[2]=1122,[3]=\"钟馗\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 飞往江南野外
     */
    public static final String NPC_SEND_TO_JIANGNAN_MAP_CONTENT_GBK = "1502*-*do local ret={[1]=\"传送江南野外\",[2]=1501,[3]=\"建邺守卫\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 飞往大唐国境的地图旗子的请求数据
     */
    public static final String MULTI_GUOJING_FLAG_FLY_TO_CONTENT_GBK = "3737*-*do local ret={[\"时间\"]=%s,[\"序列\"]=3,[\"数据验证\"]=\"%s\"} return ret end";
}
