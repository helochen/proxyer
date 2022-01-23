package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

public class RefuseFightWithGhostPopupCommand extends AbstractRefuseCommand {
    private static IRefuseFilter instance;

    private RefuseFightWithGhostPopupCommand(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;
        super.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_TALK_WITH_GHOST_POPUP_CONTENT_BYTES.length);
        super.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_TALK_WITH_GHOST_POPUP_CONTENT_BYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseFightWithGhostPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseFightWithGhostPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }
}
