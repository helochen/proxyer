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
                 "a10080cb91da009d56502c71362c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d5a752c71362c5a752c2c5b2253392c225d3d49762c5a752c79502c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c35522c425a2c56502c56502c79502c"
                ,"a10080cb91da009d56502c71362c71362c56502c2a2d2a6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7b5b2278692c225d3d5a752c71362c5a752c2c5b2253392c225d3d49762c5a752c79502c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c2a2d2a56502c77642c425a2c38762c49762c35522c425a2c56502c56502c79502c"
                ,"130180cb91da010f6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7bc4dac8dd3d7bc6b5b5c03d2253392c6e422c222cc4dac8dd3d222339572c28c8fdbde7d0fcc9cdc1ee2923675a2c2fc8cbd7dfb2e8c1b92351692cb0cfc9bdc9e6cbaed6d5d3dab3c9b9a6c7dcc4c3c1cb23675a2ccda8bca9b7b82351692ca3acd2f2b4cbbbf1b5c3c1cbccfacedecbabc6e4bdb1c0f8b5c42356652c2f49762c71362ccfc9d3f12351692c2338762c5a752c227d2cd0f2bac53d57612c57612c2ccab1bce43d56502c77642c425a2c38762c49762c35522c425a2c56502c56502c77642c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c"
                , "0f0180cb91da010b6d502c6d392c2050382c6d392c77752c4c712c50382c20664e2c45542c6e422c3d7bc4dac8dd3d7bc6b5b5c03d2253392c6e422c222cc4dac8dd3d222339572c28b5d8c9b7d0c72923675a2c2fd7aabdc7d3f6b5bdc7bd2351692cbeadb9fdd2bbb7acbca4c1d2b5c4d5bdb6b7a3acd7eed6d5d5bdcaa4c1cb23675a2cb5d8c9b7d0c72351692ca3acd2f2b4cbbbf1b5c3c1cbc6e4bdb1c0f8b5c42356652c2fb6a8bbead6e92351692c2335522c77642c227d2cd0f2bac53d57612c57612c2ccab1bce43d56502c77642c425a2c38762c49762c35522c425a2c56502c49762c56502c7d20664e2c45542c6e422c41612c664e2c48752c20664e2c45542c6e422c2045542c48752c6d502c"
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
        // 生成字节代码测试
        final LocalSendWalkingV2Command localSendWalkingV2Command = new LocalSendWalkingV2Command(324, 202);
        String result = localSendWalkingV2Command.format("1658375114", null);
        System.out.println(result);


        for (int i = 0; i < result.length(); i++) {
            char c = result.charAt(i);
            char c2 = tmp[1].charAt(i);
            if (c != c2) {
                System.out.println(i);
                System.out.println(c + "    " + c2);
            }
        }

        System.out.println(tmp[0].equals(result));



    }
}
