package com.mhxh.proxyer.tcp.game.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskConstants {

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
            , "森罗殿传送阴曹地府" // 领取抓鬼任务 13
            , "潮音洞传送普陀山" // 普陀山抓鬼 14
            , "大唐境外传送五庄观" // 五庄观抓鬼 15
            , "五庄观传送大唐境外" // 去境外抓鬼 16
            , "长寿神庙传送长寿村" // 长寿村 17
            , "傲来国客栈传送傲来国" // 傲来国 18
            , "女儿村村长家传送女儿村" // 女儿村 19
            , "长安传送江南野外" // 江南野外 20
            , "建邺杂货铺出建邺城" // 建业城 21
            , "潮音洞传送普陀山" // 普陀山 22
            , "阴曹地府传送大唐国境" // 国境 23
            , "大唐境外传送朱紫国" // 朱紫国 24
            //
            ,"轮回司传送阴曹地府" // 抓鬼25
            ,"地狱迷宫一层传送阴曹地府" // 抓鬼26
            //
            ,"长安传送金銮殿" // 27
            ,"留香阁传送长安" // 28
            ,"长安传送留香阁" // 29
            ,"朱紫国传送麒麟山"//30
            ,"地狱迷宫二层传送地狱迷宫三层2"//31
            ,"地狱迷宫三层传送地狱迷宫四层"//32
            ,"地狱迷宫四层传送无名鬼城"//33
            ,"长安传送冯记铁铺"//34

    };

    public static final Map<Integer, Integer[]> NPC_TALK_MAP = new ConcurrentHashMap<>();
    public static final Map<Integer, Integer[]> MOVE_MAP_POS = new ConcurrentHashMap<>();

    public static final Map<String, Integer> FLY_TO_MAP = new ConcurrentHashMap<>();


    static {
        NPC_TALK_MAP.put(1, new Integer[]{1054, 1, 1});
        NPC_TALK_MAP.put(2, new Integer[]{1043, 1, 1});
        NPC_TALK_MAP.put(3, new Integer[]{1124, 1, 1});
        NPC_TALK_MAP.put(4, new Integer[]{1147, 1, 1});
        NPC_TALK_MAP.put(5, new Integer[]{1143, 1, 1});
        NPC_TALK_MAP.put(6, new Integer[]{1145, 1, 1});
        NPC_TALK_MAP.put(7, new Integer[]{1117, 1, 1});
        NPC_TALK_MAP.put(8, new Integer[]{1134, 1, 1});
        NPC_TALK_MAP.put(9, new Integer[]{1112, 2, 2});
        NPC_TALK_MAP.put(10, new Integer[]{1144, 2, 2});
        NPC_TALK_MAP.put(11, new Integer[]{1141, 1, 1});
        NPC_TALK_MAP.put(12, new Integer[]{1137, 1, 1});

        MOVE_MAP_POS.put(2, new Integer[]{18, 16});
        MOVE_MAP_POS.put(9, new Integer[]{30, 43});
        MOVE_MAP_POS.put(10, new Integer[]{31, 17});
        MOVE_MAP_POS.put(11, new Integer[]{15, 14});
        MOVE_MAP_POS.put(12, new Integer[]{40, 31});

        FLY_TO_MAP.put("普陀山", 14);
        FLY_TO_MAP.put("五庄观", 15);
        FLY_TO_MAP.put("大唐境外", 16);
        FLY_TO_MAP.put("长寿村", 17);
        FLY_TO_MAP.put("傲来国", 18);
        FLY_TO_MAP.put("女儿村", 19);
        FLY_TO_MAP.put("江南野外", 20);
        FLY_TO_MAP.put("建邺城", 21);
        FLY_TO_MAP.put("大唐国境", 23);
        FLY_TO_MAP.put("朱紫国", 24);
    }

    public static final String TASK_CATCH_GHOST = "捉鬼任务";


    public static final String TASK_JIANG_HU = "初出江湖";

    public static final String TASK_QING_LONG = "青龙堂主管正在四处搜寻";

    public static final String NPC_MONSTER_PANGNI = "天庭叛";

    public static final String NPC_MONSTER_NIANSHOU_ = "年兽";

    public static final String NPC_MASTER_FIRE = "烈火";
}
