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
public class LocalSendWalkingV2Command extends LocalBaseV2Command {

    private final int x;

    private final int y;


    public LocalSendWalkingV2Command(int x, int y) {
        super(LocalSendV2CommandRuleConstants.ROLE_WALK_TO_DESTINATION_V2_HEX_HEAD, LocalSendV2CommandRuleConstants.ROLE_WALK_TO_DESTINATION_GBK_V2);
        this.x = x;
        this.y = y;
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(this.getPattern(), y, x, time);
    }
}
