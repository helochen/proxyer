package com.mhxh.proxyer.tcp.game.cmdfactory;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/20
 * @project proxyer
 **/
public class LocalSendV2CommandRuleConstants {

    // 数据头信息
    public static final String COMMAND_PROTO_HEADER = "0080cb91da00";
    // 地图行走的请求
    public static final String ROLE_MAP_WALKING_RUNNING_V2_HEX_HEAD = "0080cb91da00";

    public static final String ROLE_MAP_WALKING_RUNNING_GBK_V2 = "1002*-*do local ret={[\"y\"]=%s,[\"x\"]=%s} return ret end*-*%s";

    // 行走，移动请求数据
    public static final String ROLE_WALK_TO_DESTINATION_V2_HEX_HEAD = "0080cb91da00";

    public static final String ROLE_WALK_TO_DESTINATION_GBK_V2 = "1001*-*do local ret={[\"y\"]=%s,[\"x\"]=%s} return ret end*-*%s";

    // 切换地图
    public static final String ROLE_CHANGE_MAP_GBK_V2_HEX_HEAD = "0080cb91da00";

    public static final String[] ROLE_CHANGE_MAP_DIRECT = new String[]{
            "长安传送长风镖局",
            /*跑镖*/
            "大唐官府传送程咬金府", // 大唐 1
            "化生寺传送藏经阁"  // 化生 2
            , "森罗殿传送地藏王府" // 地府 3
            , "五庄观传送乾坤殿" // 五庄观  4
            , "女儿村传送女儿村村长家" // 女儿村村长家5
            , "魔王寨传送魔王居" //  魔王寨6
            , "龙宫进水晶宫" // 龙宫水晶宫7
            , "狮驼岭传送狮王洞" // 狮驼岭大大王8
            , "天宫传送凌霄宝殿" // 天宫9
            , "盘丝岭传送盘丝洞" // 盘丝洞10
            , "普陀山传送潮音洞" // 普陀山11
            , "方寸山传送灵台宫" // 方寸山 12
            /*抓鬼的位置*/
            , "潮音洞传送普陀山" // 普陀山抓鬼
            , "大唐境外传送五庄观" // 五庄观抓鬼
            , "五庄观传送大唐境外" // 去境外抓鬼


    };

    public static final String ROLE_CHANGE_MAP_GBK_V2 = "1003*-*do local ret={[\"说明\"]=\"%s\"} return ret end*-*%s";


    // 查询好友列表
    public static final String ROLE_QUERY_FRIEND_LIST = "16*-*do local ret={} return ret end*-*%s";
}
