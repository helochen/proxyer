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
                "425a2c71362c56502c5a752c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22d0f2c1d056502c225d3d5a752c2c5b22d0f2c1d0225d3d57612c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c5a752c56502c5a752c425a2c77642c38762c71362c"
                ,"9a0080cb91da0096425a2c71362c56502c71362c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22d0f2c1d056502c225d3d5a752c2c5b22d0f2c1d0225d3d35522c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c5a752c56502c5a752c77642c57612c71362c49762c"
                ,"9d0080cb91da0099425a2c71362c56502c5a752c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22d0f2c1d056502c225d3d5a752c2c5b22d0f2c1d0225d3d56502c56502c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c5a752c56502c5a752c77642c57612c57612c35522c"
                ,"9a0080cb91da0096425a2c71362c71362c38762c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22d0f2c1d056502c225d3d5a752c2c5b22d0f2c1d0225d3d35522c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c5a752c56502c5a752c35522c5a752c5a752c71362c"
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
