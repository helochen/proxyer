package com.mhxh.proxyer.fake.command.v1.local;

import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

public class UseNpcFlyToJiangNanMapCommand extends LocalBaseCommand {

    public UseNpcFlyToJiangNanMapCommand() {
        super(LocalSendCommandRuleConstants.NPC_SEND_TO_JIANGNAN_MAP_CONTENT_GBK);
    }


    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify);
    }
}
