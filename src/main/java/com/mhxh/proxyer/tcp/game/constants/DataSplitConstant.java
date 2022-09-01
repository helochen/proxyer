package com.mhxh.proxyer.tcp.game.constants;

import com.mhxh.proxyer.decode.EncryptDictionary;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendFriendListV2Command;
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
        //3a0080cb91da0036
        System.out.println("\"_112_1661782554_907_6137\"".length());
        System.out.println("\"_112_1661782554_897_162\"".length());
        System.out.println("\"_112_1662005055_9_277\"".length());

        String[] tmp = new String[]{
                 "020180cb91da00fe56502c425a2c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22b5d8cdbc225d3d56502c56502c57612c49762c2c5b22b1e0bac5225d3d56502c71362c56502c425a2c2c5b22b1eacab6225d3d225f56502c56502c5a752c5f56502c77642c77642c5a752c71362c71362c425a2c71362c425a2c425a2c5f57612c5f5a752c35522c35522c222c5b22d0f2c1d0225d3d56502c71362c56502c425a2c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c5a752c71362c56502c56502c77642c56502c49762c"
                ,"a50080cb91da00a156502c71362c71362c49762c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22cbb5c3f7225d3d22b5d8d3fcc3d4b9acc8fdb2e3b4abcbcdb5d8d3fcc3d4b9accbc4b2e3227d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c56502c57612c57612c49762c35522c71362c71362c"
                ,"9b0080cb91da009756502c71362c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d49762c5a752c2c5b2253392c225d3d38762c49762c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c56502c57612c57612c49762c35522c79502c77642c"
                ,"b30080cb91da00af56502c425a2c71362c5a752c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b56502c5d3d22c4c7becdcad4cad4222c5b5a752c5d3d56502c56502c57612c49762c2c5b49762c5d3d22c2d6bbd82dc8cbbdd9227d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c56502c38762c5a752c56502c5a752c56502c57612c"
                ,"b30080cb91da00af56502c425a2c71362c5a752c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b56502c5d3d22c4c7becdcad4cad4222c5b5a752c5d3d56502c56502c57612c49762c2c5b49762c5d3d22c2d6bbd82dc8cbbdd9227d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c56502c38762c5a752c56502c49762c71362c38762c"
                ,"080180cb91da010456502c425a2c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22b5d8cdbc225d3d56502c56502c57612c49762c2c5b22b1e0bac5225d3d56502c71362c56502c77642c2c5b22b1eacab6225d3d225f56502c56502c5a752c5f56502c77642c77642c56502c35522c38762c5a752c425a2c425a2c79502c5f38762c57612c35522c5f56502c77642c5a752c222c5b22d0f2c1d0225d3d56502c71362c56502c77642c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c77642c56502c38762c49762c71362c49762c71362c79502c"
         };

        Map<String, String> decodeMap = EncryptDictionary.encodeMap;

        for (String t : tmp) {
            System.out.println("===================================");
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
