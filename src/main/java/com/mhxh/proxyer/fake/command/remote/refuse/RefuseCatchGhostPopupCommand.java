package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

public class RefuseCatchGhostPopupCommand extends AbstractRefuseCommand {

    private static IRefuseFilter instance;

    private RefuseCatchGhostPopupCommand(ByteDataExchanger exchanger) {
        super.exchanger = exchanger;
        super.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_CMD_CATCH_GHOST_POPUP_CONTENT_BYTES.length);
        super.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_CMD_CATCH_GHOST_POPUP_CONTENT_BYTES);
    }

    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseCatchGhostPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseCatchGhostPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }
}
