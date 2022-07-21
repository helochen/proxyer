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
public class LocalSendWalkingPixelV2Command extends LocalBaseV2Command {

    private final int x;
    private final int y;

    public LocalSendWalkingPixelV2Command(int x, int y) {
        super(LocalSendV2CommandRuleConstants.ROLE_MAP_WALKING_RUNNING_V2_HEX_HEAD, LocalSendV2CommandRuleConstants.ROLE_MAP_WALKING_RUNNING_GBK_V2);
        this.x = x * 20;
        this.y = y * 20;
    }


    @Override
    protected String innerFormat(String time) {
        return String.format(super.getPattern(), y, x, time);
    }
}
