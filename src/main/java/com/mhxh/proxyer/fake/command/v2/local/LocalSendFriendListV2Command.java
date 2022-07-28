package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/27
 * @project proxyer
 **/
public class LocalSendFriendListV2Command extends LocalBaseV2Command {

    public LocalSendFriendListV2Command() {
        super(LocalSendV2CommandRuleConstants.ROLE_QUERY_FRIEND_LIST);
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), time);
    }
}
