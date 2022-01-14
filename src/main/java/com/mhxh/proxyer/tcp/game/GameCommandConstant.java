package com.mhxh.proxyer.tcp.game;

import io.netty.buffer.ByteBufUtil;

public interface GameCommandConstant {

    /**
     * 打开箱子的头命令
     */
    String CMD_OPEN_BOX_HEADER_HEX_STR = "3a3700000000680000000000333639392a2d2a";


    byte[] CMD_OPEN_BOX_HEADER_BYTES = ByteBufUtil.decodeHexDump(CMD_OPEN_BOX_HEADER_HEX_STR);

    /**
     * 打开好友列表的命令，没用的用于生成验证码
     */
    String CMD_OPEN_FRIEND_LIST_HEX_STR = "3a370000000066000000000031362a2d2a";

    byte[] CMD_OPEN_FRIEND_LIST_BYTES = ByteBufUtil.decodeHexDump(CMD_OPEN_FRIEND_LIST_HEX_STR);

    /**=================================购买物品的直接命令=====================================================**/
    /**
     * 第二页的购买
     */
    String CMD_BUY_SYSTEM_ITEM_SECOND_PAGE_HEX_STR = "3a3700000000a9000000000038302a2d2a";

    byte[] CMD_BUY_SYSTEM_ITEM_SECOND_PAGE_BYTES = ByteBufUtil.decodeHexDump(CMD_BUY_SYSTEM_ITEM_SECOND_PAGE_HEX_STR);
    /**
     * CMD的内容跳跃4个字节
     */

    /**=================================时间戳=====================================================**/
    /**
     * 时间的字节
     */
    String CMD_TIME_HEX_STR = "cab1bce4";

    byte[] CMD_TIME_HEX_BYTES = ByteBufUtil.decodeHexDump(CMD_TIME_HEX_STR);


    /**
     * CMD的内容跳跃4个字节
     */
    int CMD_CONTENT_TIME_SKIP_LENGTH = CMD_TIME_HEX_BYTES.length + 3;

    /**
     * 取值长度
     */
    int CMD_CONTENT_TIME_CONTENT_LENGTH = 10;


    /**=================================数据验证=====================================================**/
    /**
     * 数据验证的字节
     */
    String CAD_VERIFY_HEX_SIR = "cafdbeddd1e9d6a4";

    byte[] CMD_VERIFY_BYTES = ByteBufUtil.decodeHexDump(CAD_VERIFY_HEX_SIR);

    int CMD_CONTENT_VERIFY_SKIP_LENGTH = CMD_VERIFY_BYTES.length + 4;
    /**
     * 取值长度
     */
    int CMD_CONTENT_VERIFY_CONTENT_LENGTH = 32;


    /**
     * =================================查询任务列表 alt+Q =====================================================
     **/
    String CMD_QUERY_TASK_CALLBACK_HEAD_STR = "3a3700000000320100000000";

    byte[] CMD_QUERY_TASK_CALLBACK_HEAD_BYTES = ByteBufUtil.decodeHexDump(CMD_QUERY_TASK_CALLBACK_HEAD_STR);
    /**
     * =================================系统推送地图物品、怪物的信息 =====================================================
     **/
    String CMD_MAP_ITEM_INFORMATION_PUSH_HEAD_STR = "3a3700000000380200000000";

    byte[] CMD_MAP_ITEM_INFORMATION_PUSH_HEAD_BYTES = ByteBufUtil.decodeHexDump(CMD_MAP_ITEM_INFORMATION_PUSH_HEAD_STR);

    /**
     * ================================地图直接飞 ====================================================================
     */
    String CMD_MAP_DIRECT_FLY_1 = "3a3700000000460000000000";
    String CMD_MAP_DIRECT_FLY_1_ONE_PART = "3a3700000000";
    String CMD_MAP_DIRECT_FLY_1_TWO_PART = "0000000000";
    byte[] CMD_MAP_DIRECT_FLY_1_BYTES = ByteBufUtil.decodeHexDump(CMD_MAP_DIRECT_FLY_1);

    String CMD_MAP_DIRECT_FLY_2 = "3a37000000002f0000000000";

    String CMD_MAP_DIRECT_FLY_3 = "3a37000000002f0000000000";


}
