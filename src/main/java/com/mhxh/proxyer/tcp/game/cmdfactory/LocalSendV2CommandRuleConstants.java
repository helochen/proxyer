package com.mhxh.proxyer.tcp.game.cmdfactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/20
 * @project proxyer
 **/
public class LocalSendV2CommandRuleConstants {

    // 返回数据头
    public static final String COMMEND_PROTO_GET_HEADER = "0180cb91da01";
    // 请求数据头信息
    public static final String COMMAND_PROTO_HEADER = "0080cb91da00";
    // 地图行走的请求
    public static final String ROLE_MAP_WALKING_RUNNING_V2_HEX_HEAD = "0080cb91da00";

    public static final String ROLE_MAP_WALKING_RUNNING_GBK_V2 = "1002*-*do local ret={[\"y\"]=%s,[\"x\"]=%s} return ret end*-*%s";

    // 行走，移动请求数据
    public static final String ROLE_WALK_TO_DESTINATION_V2_HEX_HEAD = "0080cb91da00";

    public static final String ROLE_WALK_TO_DESTINATION_GBK_V2 = "1001*-*do local ret={[\"y\"]=%s,[\"x\"]=%s} return ret end*-*%s";

    // 切换地图
    public static final String ROLE_CHANGE_MAP_GBK_V2_HEX_HEAD = "0080cb91da00";




    public static final String ROLE_CHANGE_MAP_GBK_V2 = "1003*-*do local ret={[\"说明\"]=\"%s\"} return ret end*-*%s";


    // 查询好友列表
    public static final String ROLE_QUERY_FRIEND_LIST = "16*-*do local ret={} return ret end*-*%s";

    // 领取抓鬼任务
    public static final String ROLE_GET_CATCH_GHOST_GBK_V2 = "1501*-*do local ret={[\"地图\"]=1122,[\"编号\"]=1,[\"序列\"]=1} return ret end*-*%s";

    // 答应抓鬼请求
    public static final String ROLE_AGREE_CATCH_GHOST_GBK_V2 = "1502*-*do local ret={[1]=\"好的 我帮你\",[2]=1122,[3]=\"钟馗\"} return ret end*-*%s";


    // 自动领取跑镖任务
    public static final String ROLE_GET_MONEY_TASK_GBK_V2 = "1502*-*do local ret={[1]=\"二级镖银（50级能运）\",[2]=1024,[3]=\"郑镖头\"} return ret end*-*%s";

    // 查询任务信息
    public static final String ROLE_QUERY_TASK_GBK_V2 = "10*-*do local ret={} return ret end*-*%s";

    // 与NPC对话
    public static final String ROLE_TALK_TO_NPC_GBK_V2 = "1501*-*do local ret={[\"地图\"]=%s,[\"编号\"]=%s,[\"序列\"]=%s} return ret end*-*%s";


    // 请求抓鬼任务
    public static final String ROLE_REQUEST_CATCH_GHOST_GBK_V2 = "1501*-*do local ret={[\"地图\"]=%s,[\"编号\"]=%s,[\"标识\"]=%s,[\"序列\"]=%s} return ret end*-*%s";

    // 杀鬼
    public static final String ROLE_FIGHT_WITH_GHOST_GBK_V2 = "1502*-*do local ret={[1]=\"回你的地狱去\",[2]=%s,[3]=\"%s\"} return ret end*-*%s";
}
