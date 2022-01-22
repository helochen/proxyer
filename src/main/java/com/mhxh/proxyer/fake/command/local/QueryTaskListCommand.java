package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class QueryTaskListCommand extends LocalBaseCommand {

    public QueryTaskListCommand() {
        super(LocalSendCommandRuleConstants.ROLE_REQUEST_TASK_LIST);
    }

    @Override
    public String format() {
        return this.format(getTime(), getVerify());
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify);
    }
}
