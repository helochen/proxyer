package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class UserMultiFlagFlyToDestinationSayYesCommand extends LocalBaseCommand {

    public UserMultiFlagFlyToDestinationSayYesCommand() {
        super(LocalSendCommandRuleConstants.MULTI_GUOJING_FLAG_FLY_TO_CONTENT_GBK);
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify);
    }
}
