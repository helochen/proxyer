package com.mhxh.proxyer.fake.command.v1.local;

import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RequestCatchGhostForSureCommand extends LocalBaseCommand {

    public RequestCatchGhostForSureCommand() {
        super(LocalSendCommandRuleConstants.CATCH_GHOST_TASK_FOR_SURE_CONTENT_GBK);
    }

    @Override
    public String format() {
        return this.format(this.getTime(), this.getVerify());
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify);
    }
}
