package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/21
 * @project proxyer
 **/
public class LocalChangeMapV2Command extends LocalBaseV2Command {

    private final String destination;

    public LocalChangeMapV2Command(String destination) {
        super(LocalSendV2CommandRuleConstants.ROLE_CHANGE_MAP_GBK_V2_HEX_HEAD, LocalSendV2CommandRuleConstants.ROLE_CHANGE_MAP_GBK_V2);
        this.destination = destination;
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), destination, time);
    }
}
