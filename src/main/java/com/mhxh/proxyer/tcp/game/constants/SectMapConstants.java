package com.mhxh.proxyer.tcp.game.constants;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 门派的对象
 */
public class SectMapConstants {


    public static final Set<String> SECT_NAMES = new ConcurrentSkipListSet<>();

    static {
        SECT_NAMES.add("女儿村");
        SECT_NAMES.add("普陀山");
        SECT_NAMES.add("阴曹地府");
        SECT_NAMES.add("五庄观");
    }

}
