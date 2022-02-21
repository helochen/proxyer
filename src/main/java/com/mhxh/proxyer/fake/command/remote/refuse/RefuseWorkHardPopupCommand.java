package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

public class RefuseWorkHardPopupCommand extends  AbstractRefuseCommand {

    private static IRefuseFilter instance;

    private RefuseWorkHardPopupCommand(ByteDataExchanger exchanger) {
        super.exchanger = exchanger;
        this.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_WORK_HARD_POPUP_CONTENT_GBK_BYTES.length);
        this.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_WORK_HARD_POPUP_CONTENT_GBK_BYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseWorkHardPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseWorkHardPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }
}
