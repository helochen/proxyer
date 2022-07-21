package com.mhxh.proxyer.fake.command.v1.local;

import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RequestWorkHardForWeaponCommand extends LocalBaseCommand {


    public RequestWorkHardForWeaponCommand() {
        super(LocalSendCommandRuleConstants.WORK_HARD_FOR_WEAPON_ONE_TIME);
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify);
    }
}
