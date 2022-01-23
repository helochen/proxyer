package com.mhxh.proxyer.tcp.game.constants;

import com.google.common.base.Splitter;
import io.netty.buffer.ByteBufUtil;

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

    public static void main(String[] args) {
        String content = "{x=31,名称=\"申时三刻抠掏鬼\",y=22,事件=\"单位\",显示饰品=false,变异=false,id=\"4000562_8_1642838721_744_39756873\",编号=1000,模型=\"骷髅怪\",地图=1193}";

        Map<String, String> holder = Splitter.on(",").trimResults().withKeyValueSeparator("=").split(content.replaceAll("\\{", "").replaceAll("}", ""));

        String m = "3a3700000000500000000000" +
                "646f206c6f63616c207265743d7bd0f2bac53d313030352cc4dac8dd3d7b5b315d3d313139332c5b325d3d32302c5b335d3d377d7d2072657475726e2072657420656e64";

        String m2 = "3a3700000000660000000000" +
                "31302a2d2a646f206c6f63616c207265743d7b5b22cab1bce4225d3d313634323831393537352c5b22cafdbeddd1e9d6a4225d3d226332663638643538376361396131306335666163303936616563623565303061227d2072657475726e2072657420656e64";

        String x = "3a3700000000660000000000";
        String y = "31302a2d2a646f206c6f63616c207265743d7b5b22cab1bce4225d3d313634323831393537352c5b22cafdbeddd1e9d6a4225d3d226332663638643538376361396131306335666163303936616563623565303061227d2072657475726e2072657420656e64";
        byte[] bytes = ByteBufUtil.decodeHexDump(x);
        System.out.println(((byte) bytes.length & 0xff));
        byte[] bytes1 = ByteBufUtil.decodeHexDump(y);
        System.out.println(((byte) bytes1.length) & 0xff);

        byte[] bytes2 = ByteBufUtil.decodeHexDump(x + y);
        System.out.println(((byte) bytes2.length) & 0xff);


    }
}
