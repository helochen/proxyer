package com.mhxh.proxyer.tcp.game.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapConstants {

    /**
     * 长安城,坐标与像素放大倍率是20
     */
    private static final String MAP_ID_CHANG_AN = "1001";
    /**
     * 建业
     */
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

    /**
     * 大唐境外 1173
     */

    private static final String MAP_ID_DTJW = "1173";

    /**
     * 西凉女国
     */
    private static final String MAP_ID_XLNG = "1040";


    /**
     * 地图中文名字转向地图ID
     */
    public static final Map<String, String> MAP_CN_TO_CODE = new ConcurrentHashMap<>();

    /**
     * 地图中文名称转向序号
     */
    public static final Map<String, String> NAME_TO_SERIAL_NO = new ConcurrentHashMap<>();


    static  {
        MAP_CN_TO_CODE.put("建邺城", MAP_ID_JY);
        NAME_TO_SERIAL_NO.put("建邺城", "3");

        MAP_CN_TO_CODE.put("傲来国", MAP_ID_AL);
        NAME_TO_SERIAL_NO.put("傲来国", "4");

        MAP_CN_TO_CODE.put("长安城", MAP_ID_CHANG_AN);
        NAME_TO_SERIAL_NO.put("长安城", "2");

        MAP_CN_TO_CODE.put("朱紫国", MAP_ID_ZZG);
        NAME_TO_SERIAL_NO.put("朱紫国", "8");

        MAP_CN_TO_CODE.put("宝象国", MAP_ID_BX);
        NAME_TO_SERIAL_NO.put("宝象国", "7");

        MAP_CN_TO_CODE.put("西梁女国", MAP_ID_XLNG);
        NAME_TO_SERIAL_NO.put("西梁女国", "6");

        MAP_CN_TO_CODE.put("长寿村", MAP_ID_CS);
        NAME_TO_SERIAL_NO.put("长寿村", "5");


        //MAP_CN_TO_CODE.put("江南野外", MAP_ID_JNYW);
        //MAP_CN_TO_CODE.put("大唐境外", MAP_ID_DTJW);

    }


}
