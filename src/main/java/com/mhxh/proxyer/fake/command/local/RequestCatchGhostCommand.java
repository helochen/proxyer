package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RequestCatchGhostCommand extends LocalBaseCommand {


    public RequestCatchGhostCommand() {
        super(LocalSendCommandRuleConstants.GET_CATCH_GHOST_COMMAND_CONTENT_GBK);
    }

    @Override
    public String format() {
        return this.format(getTime(), getVerify());
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), verify, time);
    }
}
