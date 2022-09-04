package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

public class LocalGetSkillFromBookV2Command extends LocalBaseV2Command {

    private int x;

    private int y;

    public LocalGetSkillFromBookV2Command(int x, int y) {
        super(LocalSendV2CommandRuleConstants.ROLE_GET_BOOK_SKILL_GBK_V2);
        this.x = x;
        this.y = y;
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), x, y, time);
    }
}
