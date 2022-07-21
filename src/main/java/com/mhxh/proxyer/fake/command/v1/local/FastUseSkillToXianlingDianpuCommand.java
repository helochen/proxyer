package com.mhxh.proxyer.fake.command.v1.local;

import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class FastUseSkillToXianlingDianpuCommand extends LocalBaseCommand {


    public FastUseSkillToXianlingDianpuCommand() {
        super(LocalSendCommandRuleConstants.FAST_USE_SKILL_TO_XIANLING_DIANPU_CONTENT_GBK);
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern() , time ,verify);
    }
}
