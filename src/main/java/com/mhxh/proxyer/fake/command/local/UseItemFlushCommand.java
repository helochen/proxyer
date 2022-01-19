package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class UseItemFlushCommand extends LocalBaseCommand {

    public UseItemFlushCommand() {
        super(LocalSendCommandRuleConstants.USE_ITEM_FORMAT_GBK_APPEND);
    }

    @Override
    public String format() {
        return String.format(super.getPattern(), super.getTime(), super.getVerify());
    }

    @Override
    public String format(String time, String verify) {
        return String.format(super.getPattern(), time, verify);
    }
}
