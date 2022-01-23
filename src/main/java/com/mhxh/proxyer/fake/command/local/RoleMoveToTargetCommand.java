package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RoleMoveToTargetCommand extends LocalBaseCommand {

    private int x;
    private int y;

    public RoleMoveToTargetCommand(int x, int y) {
        super(LocalSendCommandRuleConstants.ROLE_WALK_TO_DESTINATION_GBK);
        this.x = x;
        this.y = y;
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), y, x, time, verify);
    }
}
