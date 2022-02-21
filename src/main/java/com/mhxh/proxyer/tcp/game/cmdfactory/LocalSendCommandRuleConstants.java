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

    /**
     * 拉去店铺技能列表
     */
    public static final String FAST_USE_SKILL_TO_XIANLING_DIANPU_CONTENT_GBK = "13*-*do local ret={[\"时间\"]=%s,[\"序列\"]=2,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 购买飞行棋
     */
    public static final String BUY_FLY_TICKET_ITEM_CONTENT_GBK = "1503*-*do local ret={[\"数量\"]=\"%s\",[\"数据验证\"]=\"%s\",[\"时间\"]=%s,[\"商品\"]=\"飞行符*750\",[\"类型\"]=\"银子\"} return ret end";


    /**
     * 发送请求青龙堂任务信息
     */
    public static final String GET_QINGLONG_TASK_INFO = "1501*-*do local ret={[\"数据验证\"]=\"%s\",[\"地图\"]=1865,[\"编号\"]=1,[\"时间\"]=%s,[\"序列\"]=1} return ret end";

    /**
     * 获得青龙堂任务
     */
    public static final String GET_QINGLONG_TASK_FOR_SURE = "1502*-*do local ret={[1]=\"领取青龙堂任务\",[2]=1865,[3]=\"青龙堂主管\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 购买物品
     */
    public static final String BUY_SYSTEM_QINGLONG_TASK_ITEM = "80*-*do local ret={[\"时间\"]=%s,[\"数据验证\"]=\"%s\",[\"类型\"]=\"购买\",[\"数据\"]={[\"选中\"]=%s,[\"类型\"]=\"物品店\",[\"页数\"]=%s}} return ret end";

    /**
     * 上交任务
     */
    public static final String COMMIT_BUY_ITEM_BOX = "1502*-*do local ret={[1]=\"上交物品\",[2]=1865,[3]=\"青龙堂主管\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";

    /**
     * 确认上交物品,格子
     */
    public static final String FOR_SURE_COMMIT_ITEM_INFO = "3715*-*do local ret={[\"数量\"]={[1]=\"1\"},[\"类型\"]=\"道具\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\",[\"格子\"]={[1]=%s},[\"银子\"]=\"0\"} return ret end";

    /**
     * 打造对话,打工一次
     */
    public static final String WORK_HARD_FOR_WEAPON_ONE_TIME = "1502*-*do local ret={[1]=\"打工一次\",[2]=1025,[3]=\"冯铁匠\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";
    public static final String WORK_HARD_FOR_CLOTH_ONE_TIME = "1502*-*do local ret={[1]=\"打工一次\",[2]=1022,[3]=\"张裁缝\",[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";
}
