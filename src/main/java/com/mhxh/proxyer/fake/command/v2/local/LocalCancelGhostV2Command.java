package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/8/24
 * @project proxyer
 **/
public class LocalCancelGhostV2Command extends LocalBaseV2Command {
    public LocalCancelGhostV2Command() {
        super(LocalSendV2CommandRuleConstants.ROLE_CANCEL_CATCH_GHOST_GBK_V2);
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), time);
    }
}
