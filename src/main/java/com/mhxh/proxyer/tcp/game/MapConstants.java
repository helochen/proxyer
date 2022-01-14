package com.mhxh.proxyer.tcp.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapConstants {


    public static final Map<String, String> MAP_CN_TO_CODE = new ConcurrentHashMap<>();

    /*
     * 建业
     * */
    private static final String MAP_ID_JY = "1501";

    /**
     * 长寿
     */
    private static final String MAP_ID_CS = "1070";

    /**
     * 傲来
     */
    private static final String MAP_ID_AL = "1092";


    /**
     * 江南野外
     */
    private static final String MAP_ID_JNYW = "1193";

    /**
     * 宝象
     */

    static {
        MAP_CN_TO_CODE.put("建邺城", MAP_ID_JY);
        MAP_CN_TO_CODE.put("江南野外", MAP_ID_JNYW);
        MAP_CN_TO_CODE.put("傲来国", MAP_ID_AL);
        // MAP_CN_TO_CODE.put("长寿", MAP_ID_JY);
    }

}
