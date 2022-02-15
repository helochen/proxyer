package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RequestQinglongTaskInfoCommand extends LocalBaseCommand {


    public RequestQinglongTaskInfoCommand() {
        super(LocalSendCommandRuleConstants.GET_QINGLONG_TASK_FOR_SURE);
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify);
    }
}
