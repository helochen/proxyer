package com.mhxh.proxyer.fake.command.v1.local;

import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

/**
 * 使用背包里的物品
 */
public class UseBoxItemCommand extends LocalBaseCommand {

    /**
     * 道具的顺序，从1开始
     */
    private String itemIdx;

    public UseBoxItemCommand(String itemIdx) {
        super(LocalSendCommandRuleConstants.USE_ITEM_FORMAT_GKB);
        this.itemIdx = itemIdx;
    }

    @Override
    public String format() {
        return String.format(getPattern(), getTime(), getVerify(), itemIdx);
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify, itemIdx);
    }
}
