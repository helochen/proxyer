package com.mhxh.proxyer.fake.command.remote;

import com.mhxh.proxyer.fake.command.base.IType;
import com.mhxh.proxyer.fake.command.base.RemoteBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.FromGameServerCommandRuleConstants;


public class OpenFlagPostionCommand extends RemoteBaseCommand implements IType {


    public OpenFlagPostionCommand() {
        super(FromGameServerCommandRuleConstants.FLY_FLAG_ITEM_USE_RETURN_EFFECT);
    }

    @Override
    public String format() {
        return getPattern();
    }

    @Override
    public String format(String time, String verify) {
        return getPattern();
    }
}
