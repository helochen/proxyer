package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class ChangAnNpcFlyToSectCommand extends LocalBaseCommand {

    private String sectName;

    /**
     * 门派名称
     *
     * @param sectName
     */
    public ChangAnNpcFlyToSectCommand(String sectName) {
        super(LocalSendCommandRuleConstants.USE_CHANG_AN_NPC_FLY_TO_SECT_GBK);
        this.sectName = sectName;
    }

    @Override
    public String format() {
        return String.format(super.getPattern(), sectName, super.getTime(), super.getVerify());
    }

    @Override
    public String format(String time, String verify) {
        return String.format(super.getPattern(), sectName, time, verify);
    }
}
