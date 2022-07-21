package com.mhxh.proxyer.fake.command.v1.local;

import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class ResponseQingLongTaskItemCommand extends LocalBaseCommand {
    private int idx;

    public ResponseQingLongTaskItemCommand(int index) {
        super(LocalSendCommandRuleConstants.FOR_SURE_COMMIT_ITEM_INFO);
        this.idx = index;
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify, idx);
    }
}
