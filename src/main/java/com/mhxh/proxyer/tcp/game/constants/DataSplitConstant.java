package com.mhxh.proxyer.tcp.game.constants;

import com.mhxh.proxyer.decode.EncryptDictionary;
import io.netty.buffer.ByteBufUtil;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Pattern;

public class DataSplitConstant {

    public static final Pattern DATA_PATTERN = Pattern.compile("\\{.*?}");

    public static final Pattern MAP_NPC_PATTERN = Pattern.compile("\\{x.*?地图.*?}");

    // 去除领取人
    public static final Pattern MAP_NPC_TARGET_ROLE_PATTERN = Pattern.compile("领取人id=\\{.*?},");

    // 触发NPC信息
    public static final Pattern MAP_NPC_TARGET_NPC_PATTERN = Pattern.compile("内容=\\{.*?}} return");


    public static final Pattern TASK_DECOMPOSITION_POSTION = Pattern.compile("(?<=#Y/).*?(?=#W)");

    public static final Pattern TASK_DECOMPOSITION_NAME = Pattern.compile("(?<=#G/).*?(?=#R)");

    public static final Pattern TASK_DECOMPOSITION_X_Y = Pattern.compile("(.*?)");

    // 青龙堂任务
    public static final Pattern TASK_QING_LONG_NEED_ITEM = Pattern.compile("(?<=#Y).*?(?=#W)");

    public static void main(String[] args) {
        byte[] bytes1 = "return ret end".getBytes();
        String hexDump = ByteBufUtil.hexDump(bytes1);
        System.out.println(new String(bytes1, Charset.forName("GBK")));
        System.out.println(hexDump);

        //3a0080cb91da0036
        System.out.println("\"_112_1661782554_907_6137\"".length());
        System.out.println("\"_112_1661782554_897_162\"".length());
        System.out.println("\"_112_1662005055_9_277\"".length());

        String[] tmp = new String[]{
                "bf0180cb91da01bb353530322a2d2a646f206c6f63616c207265743d7b5b315d3d7b5b22b8bdbcd3225d3d342c5b22c4bfb1ea225d3d312c5b22c0e0d0cd225d3d22b9a5bbf7222c5b22d5bdb6b7b4fabac5225d3d223431325f33303730395f313636323033383835345f353331222c5b22b5d0ced2225d3d302c5b22b2cecafd225d3d22227d2c5b325d3d7b5b22b8bdbcd3225d3d342c5b22c4bfb1ea225d3d312c5b22c0e0d0cd225d3d22b7a8caf5222c5b22d5bdb6b7b4fabac5225d3d223431325f33303730395f313636323033383835345f353331222c5b22b5d0ced2225d3d302c5b22b2cecafd225d3d22cca9c9bdd1b9b6a5227d7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a" +
                        "56502c77642c77642c5a752c71362c49762c38762c38762c77642c5a752c",
                "cf0080cb91da00cb425a2c425a2c71362c49762c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22d5bdb6b7b4fabac5225d3d2235522c5a752c38762c5f49762c71362c35522c71362c57612c5f56502c77642c77642c5a752c71362c79502c71362c5a752c71362c35522c5f38762c77642c425a2c227d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c5a752c71362c79502c71362c425a2c79502c77642c"
                ,"280280cb91da0224646f206c6f63616c207265743d7bd0f2bac53d353530342cc4dac8dd3d7b5b315d3d7bc1f7b3cc3d312cb7b5bbd83d747275652cb9a5bbf7b7bd3d332cc9cbbaa63d313337352cb0a4b4f2b7bd3d7b5b315d3d7bccd8d0a73d7b5b315d3d22b1bbbbf7d6d0227d2cc9cbbaa6c0e0d0cd3d312ccbc0cdf63d312cc9cbbaa63d313337352cb6afd7f73d22b0a4b4f2222cb0a4b4f2b7bd3d317d7d7d2c5b325d3d7bc1f7b3cc3d3230352cb0a4b4f2b7bd3d7b5b315d3d7bc0e0d0cd3d332cc9cbbaa63d313733332cb0a4b4f2b7bd3d322ccedec9cbbaa63d66616c73652cb3c9b9a63d747275652cccd8d0a73d7b5b315d3d22cca9c9bdd1b9b6a5222c5b325d3d22b7a8b1a9227d2ccbc0cdf63d312cc8a1cffbd7b4ccac3d7b7d2cb6e3b1dcb3c9b9a63d66616c73657d7d2ccce1cabe3d7bd4cad0ed3d747275652cc3fbb3c63d2278787878cab9d3c3c1cbcca9c9bdd1b9b6a5222cc0e0d0cd3d22b7a8caf5227d2cb9a5bbf7b7bd3d347d7d7d2072657475726e2072657420656e64"
        };


        Map<String, String> decodeMap = EncryptDictionary.encodeMap;

        for (String t : tmp) {
            System.out.println("===================================");
            byte[] bytes = ByteBufUtil.decodeHexDump(t);

            System.out.println(bytes.length);
            int length = t.indexOf("2a56502c");

            System.out.println(length / 2 + 30);
            System.out.println(bytes[length / 2]);
            System.out.println(bytes[length / 2 + 30]);

            byte[] dst = new byte[length / 2 + 31];

            System.arraycopy(bytes, 0, dst, 0, length / 2 + 31);
            String a = new String(dst, Charset.forName("GBK"));
            System.out.println(a);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println(new String(ByteBufUtil.decodeHexDump(t), Charset.forName("GBK")));
            for (String key : decodeMap.keySet()) {
                t = t.replaceAll(key, decodeMap.get(key));
            }
            byte[] bytes6 = ByteBufUtil.decodeHexDump(t);
            System.out.println(bytes6.length);
            String result6 = new String(bytes6, Charset.forName("GBK"));
            System.out.println(result6);

        }
        System.out.println("+++++++++++++++++++++++++++++++++");

    }
}
