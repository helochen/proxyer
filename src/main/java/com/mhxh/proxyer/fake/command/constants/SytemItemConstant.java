package com.mhxh.proxyer.fake.command.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SytemItemConstant {


    public static final Map<String, Integer[]> itemMap = new ConcurrentHashMap<>();


    static {
        itemMap.put("臭豆腐", new Integer[]{0,5});
        itemMap.put("烤鸭", new Integer[]{0,6});
        itemMap.put("烤肉", new Integer[]{0,7});
        itemMap.put("佛跳墙", new Integer[]{0,8});
        itemMap.put("翡翠豆腐", new Integer[]{0,9});
        itemMap.put("桂花丸", new Integer[]{0,10});
        itemMap.put("豆斋果", new Integer[]{0,11});
        itemMap.put("长寿面", new Integer[]{0,12});
        itemMap.put("珍露酒", new Integer[]{0,13});
        itemMap.put("女儿红", new Integer[]{0,14});
        itemMap.put("梅花酒", new Integer[]{0,15});
        itemMap.put("蛇胆酒", new Integer[]{0,16});
        itemMap.put("虎骨酒", new Integer[]{0,17});
        itemMap.put("百味酒", new Integer[]{0,18});
        itemMap.put("醉生梦死", new Integer[]{0,19});


        itemMap.put("天不老", new Integer[]{1,2});
        itemMap.put("紫石英", new Integer[]{1,3});
        itemMap.put("鹿茸", new Integer[]{1,4});
        itemMap.put("血色茶花", new Integer[]{1,5});
        itemMap.put("六道轮回", new Integer[]{1,6});
        itemMap.put("熊胆", new Integer[]{1,7});
        itemMap.put("凤凰尾", new Integer[]{1,8});
        itemMap.put("硫磺草", new Integer[]{1,9});
        itemMap.put("龙之心屑", new Integer[]{1,10});
        itemMap.put("火凤之睛", new Integer[]{1,11});
        itemMap.put("丁香水", new Integer[]{1,12});
        itemMap.put("月星子", new Integer[]{1,13});


        itemMap.put("餐风饮露", new Integer[]{2,5});
        itemMap.put("白露为霜", new Integer[]{2,6});
        itemMap.put("地狱灵芝", new Integer[]{2,7});
        itemMap.put("天龙水", new Integer[]{2,8});
        itemMap.put("孔雀红", new Integer[]{2,9});
        itemMap.put("麝香", new Integer[]{2,10});
        itemMap.put("血珊瑚", new Integer[]{2,11});
        itemMap.put("仙狐涎", new Integer[]{2,12});


    }

}
