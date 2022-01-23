package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

public class RefuseCmdJingwaiFlagPopupCommand extends AbstractRefuseCommand {


    private static IRefuseFilter instance;

    private RefuseCmdJingwaiFlagPopupCommand(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;
        super.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_CMD_JINGWAI_FLAG_POPUP_CONTENT_BYTES.length);
        super.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_CMD_JINGWAI_FLAG_POPUP_CONTENT_BYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseCmdJingwaiFlagPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseCmdJingwaiFlagPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }
}
