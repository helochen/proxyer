package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class BuyFlyTicketItemCommand extends LocalBaseCommand {

    private int count;

    public BuyFlyTicketItemCommand(int count) {
        super(LocalSendCommandRuleConstants.BUY_FLY_TICKET_ITEM_CONTENT_GBK);
        this.count = count;
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), count, verify, time);
    }
}
