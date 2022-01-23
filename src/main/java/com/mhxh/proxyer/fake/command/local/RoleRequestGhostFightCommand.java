package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class RoleRequestGhostFightCommand extends LocalBaseCommand {

    private String mapId;

    private String serialNo;

    private String id;

    public RoleRequestGhostFightCommand(String mapId, String serialNo , String id) {
        super(LocalSendCommandRuleConstants.CATCH_GHOST_NPC_REQUSET_BEGIN_SEND_COMMAND_CONTENT_GBK);
        this.mapId = mapId;
        this.serialNo = serialNo;
        this.id = id;
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify, mapId, serialNo, serialNo, id);
    }
}
