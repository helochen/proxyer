package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class BuySystemItemCommand extends LocalBaseCommand {

    private int x;

    private int y;

    public BuySystemItemCommand(Integer x, Integer y) {
        super(LocalSendCommandRuleConstants.BUY_SYSTEM_QINGLONG_TASK_ITEM);
        this.x = x;
        this.y = y;
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify, y, x);
    }
}
