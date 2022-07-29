package com.mhxh.proxyer.tcp.game.constants;

import com.mhxh.proxyer.decode.EncryptDictionary;
import com.mhxh.proxyer.fake.command.v2.local.LocalCatchGhostV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalKillGhostTaskV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalRequestCatchGhostV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendFriendListV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;
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

        String[] tmp = new String[]{
                "9b0080cb91da009756502c71362c71362c49762c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22cbb5c3f7225d3d22b3a4cad9c9f1c3edb4abcbcdb3a4cad9b4e5227d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c57612c71362c35522c38762c79502c35522c57612c"
                ,"9d0080cb91da009956502c71362c71362c49762c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22cbb5c3f7225d3d22b0c1c0b4b9fabfcdd5bbb4abcbcdb0c1c0b4b9fa227d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c57612c71362c35522c38762c79502c38762c56502c"
                ,"9d0080cb91da009956502c71362c71362c49762c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b22cbb5c3f7225d3d22b0c1c0b4b9fabfcdd5bbb4abcbcdb0c1c0b4b9fa227d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c57612c71362c35522c38762c425a2c5a752c77642c"
                , "820080cb91da007e56502c2a2d2a56502c57612c5a752c79502c56502c57612c71362c425a2c402b404c712c6a4f2c6d502c61572c4a412c55632c61622c59782c50382c402b404c712c6a4f2c6d502c61572c4a412c55632c61622c59782c50382c402b402a2d2a56502c77642c425a2c38762c57612c35522c49762c38762c71362c79502c"
                ,"820080cb91da007e56502c2a2d2a56502c57612c5a752c79502c56502c57612c71362c425a2c402b404c712c6a4f2c6d502c61572c4a412c55632c61622c59782c50382c402b404c712c6a4f2c6d502c61572c4a412c55632c61622c59782c50382c402b402a2d2a56502c77642c425a2c38762c57612c35522c79502c425a2c57612c425a2c"
                ,"820080cb91da007e312a2d2a3139323431393035402b406173646667686a6b6c402b406173646667686a6b6c402b402a2d2a31363538393731363534"
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

        LocalSendFriendListV2Command command = new LocalSendFriendListV2Command();
        final String format = command.format();
        System.out.println(new String(ByteBufUtil.decodeHexDump(format), Charset.forName("GBK")));

    }
}
