package com.mhxh.proxyer.tcp.game;

public interface ProxyCommandConstant {


    String BUY_ITEM_SECOND_PAGE_ITEM_TEST_FORMAT_STR = "do local ret={[\"时间\"]=%s,[\"数据验证\"]=\"%s\",[\"类型\"]=\"购买\",[\"数据\"]={[\"选中\"]=5,[\"类型\"]=\"物品店\",[\"页数\"]=1}} return ret end";


    String FLY_MAP_1_TYPE_DIRECT = "do local ret={序号=1005,内容={[1]=%s,[2]=%s,[3]=%s}} return ret end";
}
