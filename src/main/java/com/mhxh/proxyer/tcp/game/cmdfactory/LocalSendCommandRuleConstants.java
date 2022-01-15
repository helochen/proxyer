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
     */
    public static final String USE_ITEM_FORMAT_GBK_APPEND = "3699*-*do local ret={[\"时间\"]=%s,[\"数据验证\"]=\"%s\"} return ret end";


}
