package com.mhxh.proxyer.fake.command.v2.shit;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/8/30
 * @project proxyer
 **/
public class LocalShitLoginOtherRoleV2Command extends LocalBaseV2Command {

    private final String id;

    public LocalShitLoginOtherRoleV2Command(String id) {
        super(LocalSendV2CommandRuleConstants.LOGIN_OTHER_ROLE_GBK_V2);
        this.id = id;
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), id, time);
    }
}
