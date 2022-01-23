package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RoleFightWithGhostForSureCommand extends LocalBaseCommand {

    private String mapId;

    private String ghostName;

    public RoleFightWithGhostForSureCommand(String mapId, String ghostName) {
        super(LocalSendCommandRuleConstants.CATCH_GHOST_NPC_REQUEST_FIGHT_COMMAND_CONTENT_GBK);
        this.mapId = mapId;
        this.ghostName = ghostName;
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), mapId, ghostName, time, verify);
    }
}
