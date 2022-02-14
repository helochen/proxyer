package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

public class RefuseQingLongTaskPopupCommand extends AbstractRefuseCommand {

    private static IRefuseFilter instance;

    private RefuseQingLongTaskPopupCommand(ByteDataExchanger exchanger) {
        super.exchanger = exchanger;
        this.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_QINGLONG_TASK_CONTENT_POPUP_CONTENT_GBKBYTES.length);
        this.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_QINGLONG_TASK_CONTENT_POPUP_CONTENT_GBKBYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseCatchGhostInfoPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseQingLongTaskPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }
}
