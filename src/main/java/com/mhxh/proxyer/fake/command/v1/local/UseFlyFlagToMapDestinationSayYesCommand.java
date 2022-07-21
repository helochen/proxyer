package com.mhxh.proxyer.fake.command.v1.local;

import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;


public class UseFlyFlagToMapDestinationSayYesCommand extends LocalBaseCommand {

    private String mapId;

    public UseFlyFlagToMapDestinationSayYesCommand(String mapId) {
        super(LocalSendCommandRuleConstants.USE_SIGLE_FLAG_TO_MAP_POSITION_GBK);
        this.mapId = mapId;
    }

    @Override
    public String format() {
        return String.format(getPattern(), mapId, getTime(), getVerify());
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), mapId, time, verify);
    }
}
