package com.mhxh.proxyer.tcp.game.constants;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MapConstants {


    /**
     * 中文名字转向地图ID
     */
    public final Map<String, String> MAP_CN_TO_CODE = new ConcurrentHashMap<>();

    /**
     * 地图ID转向序号
     */
    public final Map<String, String> CODE_TO_SERIAL_NO = new ConcurrentHashMap<>();


    {
        MAP_CN_TO_CODE.put("建邺城", MAP_ID_JY);
        MAP_CN_TO_CODE.put("江南野外", MAP_ID_JNYW);
        MAP_CN_TO_CODE.put("傲来国", MAP_ID_AL);
        MAP_CN_TO_CODE.put("朱紫国", MAP_ID_ZZG);
        MAP_CN_TO_CODE.put("宝象国", MAP_ID_BX);


        CODE_TO_SERIAL_NO.put(MAP_ID_ZZG, "8");
        CODE_TO_SERIAL_NO.put(MAP_ID_AL, "4");
        CODE_TO_SERIAL_NO.put(MAP_ID_BX, "7");
        CODE_TO_SERIAL_NO.put(MAP_ID_CHANG_AN, "2");


    }

    /**
     * 长安城,坐标与像素放大倍率是20
     */
    private static final String MAP_ID_CHANG_AN = "1001";
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
    private static final String MAP_ID_BX = "1226";
    /**
     * 1208 朱紫国
     */
    private static final String MAP_ID_ZZG = "1208";







}
