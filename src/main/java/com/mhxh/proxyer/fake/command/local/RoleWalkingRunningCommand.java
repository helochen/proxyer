package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RoleWalkingRunningCommand extends LocalBaseCommand {

    private int x;
    private int y;


    public RoleWalkingRunningCommand(int x, int y) {
        super(LocalSendCommandRuleConstants.ROLE_WALKING_RUNNING_GBK);
        this.x = 20 * x;
        this.y = 20 * y;
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), x, y, time, verify);
    }
}
