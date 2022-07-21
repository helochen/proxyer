package com.mhxh.proxyer.tcp.game.cmdfactory;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/20
 * @project proxyer
 **/
public class LocalSendV2CommandRuleConstants {

    // 地图行走的请求
    public static final String ROLE_MAP_WALKING_RUNNING_V2_HEX_HEAD = "0080cb91da00a0";

    public static final String ROLE_MAP_WALKING_RUNNING_GBK_V2 = "1002*-*do local ret={[\"y\"]=%s,[\"x\"]=%s} return ret end*-*%s";

    // 行走，移动请求数据
    public static final String ROLE_WALK_TO_DESTINATION_V2_HEX_HEAD = "0080cb91da009d";

    public static final String ROLE_WALK_TO_DESTINATION_GBK_V2 = "1001*-*do local ret={[\"y\"]=%s,[\"x\"]=%s} return ret end*-*%s";

    // 切换地图
    public static final String ROLE_CHANGE_MAP_GBK_V2_HEX_HEAD = "0080cb91da0095";

    public static final String ROLE_CHANGE_MAP_GBK_V2 = "1003*-*do local ret={[\"说明\"]=\"方寸山传送灵台宫\"} return ret end*-*%s";
}
