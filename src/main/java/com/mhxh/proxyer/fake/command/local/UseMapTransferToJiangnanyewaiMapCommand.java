package com.mhxh.proxyer.fake.command.local;

import com.mhxh.proxyer.fake.command.base.LocalBaseCommand;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;

/**
 * 传送到江南野外的地图功能
 */
public class UseMapTransferToJiangnanyewaiMapCommand extends LocalBaseCommand {


    public UseMapTransferToJiangnanyewaiMapCommand() {
        super(LocalSendCommandRuleConstants.USE_TRANSFER_TO_JIANG_NAN_YEWAI_MSG_GBK);
    }

    @Override
    public String format() {
        return String.format(getPattern(), getTime(), getVerify());
    }

    @Override
    public String format(String time, String verify) {
        return String.format(getPattern(), time, verify);
    }
}
