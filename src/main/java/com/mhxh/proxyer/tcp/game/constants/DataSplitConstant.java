package com.mhxh.proxyer.tcp.game.constants;

import com.mhxh.proxyer.decode.EncryptDictionary;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendWalkingPixelV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendWalkingV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;
import io.netty.buffer.ByteBufUtil;
import lombok.val;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Pattern;

public class DataSplitConstant {

    public static final Pattern DATA_PATTERN = Pattern.compile("\\{.*?}");

    public static final Pattern MAP_NPC_PATTERN = Pattern.compile("\\{x.*?地图.*?}");

    // 去除领取人
    public static final Pattern MAP_NPC_TARGET_ROLE_PATTERN = Pattern.compile("领取人id=\\{.*?},");


    public static final Pattern TASK_DECOMPOSITION_POSTION = Pattern.compile("(?<=#Y/).*?(?=#W)");

    public static final Pattern TASK_DECOMPOSITION_NAME = Pattern.compile("(?<=#G/).*?(?=#R)");

    public static final Pattern TASK_DECOMPOSITION_X_Y = Pattern.compile("(.*?)");

    // 青龙堂任务
    public static final Pattern TASK_QING_LONG_NEED_ITEM = Pattern.compile("(?<=#Y).*?(?=#W)");

    public static void main(String[] args) {


        String[] tmp = new String[]{
                  "31362a2d2a646f206c6f63616c207265743d7b7d2072657475726e2072657420656e642a2d2a31363538333836333732"
                , "a70080cb91da00a356502c71362c71362c5a752c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d49762c38762c79502c71362c2c5b2253392c225d3d77642c425a2c49762c38762c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c38762c71362c35522c79502c71362c"
                , "a70080cb91da00a056502c71362c71362c5a752c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d49762c38762c79502c71362c2c5b2253392c225d3d77642c425a2c49762c38762c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c38762c71362c35522c79502c71362c"
                , "9e0080cb91da009a56502c71362c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d5a752c77642c71362c2c5b2253392c225d3d49762c71362c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c38762c71362c79502c38762c71362c"
                , "9e0080cb91da009a56502c71362c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d5a752c425a2c57612c2c5b2253392c225d3d49762c56502c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c38762c71362c425a2c49762c79502c"
                , "810080cb91da007d57612c57612c57612c57612c77642c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c38762c71362c425a2c49762c79502c"
                , "a70080cb91da00a356502c71362c71362c5a752c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d56502c5a752c71362c5a752c2c5b2253392c225d3d56502c79502c38762c49762c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c35522c57612c35522c79502c57612c"
                , "a10080cb91da009d56502c71362c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d56502c57612c49762c2c5b2253392c225d3d49762c5a752c35522c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c38762c71362c56502c71362c38762c"
                , "a70080cb91da00a356502c71362c71362c5a752c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d49762c38762c49762c57612c2c5b2253392c225d3d77642c425a2c57612c57612c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c38762c71362c56502c71362c38762c"
                , "9e0080cb91da009a56502c71362c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d5a752c35522c79502c2c5b2253392c225d3d56502c49762c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c38762c71362c56502c35522c38762c"
        };
        Map<String, String> decodeMap = EncryptDictionary.encodeMap;

        for (String t : tmp) {
            System.out.println("===================================");
            //System.out.println(new String(ByteBufUtil.decodeHexDump(t), Charset.forName("GBK")));
            for (String key : decodeMap.keySet()) {
                t = t.replaceAll(key, decodeMap.get(key));
            }
            byte[] bytes6 = ByteBufUtil.decodeHexDump(t);
            String result6 = new String(bytes6, Charset.forName("GBK"));
            System.out.println(result6);

        }
        System.out.println("+++++++++++++++++++++++++++++++++");

    }
}
