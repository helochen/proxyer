package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/22
 * @project proxyer
 **/
public class LocalKillGhostTaskV2Command extends LocalBaseV2Command {

    private final String id;
    private final String name;

    public LocalKillGhostTaskV2Command(String id, String name) {
        super(LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_GBK_V2);
        this.id = id;
        this.name = name;
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), id, name, time);
    }
}
