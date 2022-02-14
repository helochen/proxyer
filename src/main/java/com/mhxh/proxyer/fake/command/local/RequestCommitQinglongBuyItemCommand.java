package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RequestCommitQinglongBuyItemCommand extends LocalBaseCommand {

    public RequestCommitQinglongBuyItemCommand() {
        super(LocalSendCommandRuleConstants.COMMIT_BUY_ITEM_BOX);
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify);
    }
}
