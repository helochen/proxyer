package com.mhxh.proxyer.fake.command.v1.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

public class RefuseCatchGhostInfoPopupCommand extends AbstractRefuseCommand {


    private static IRefuseFilter instance;

    private RefuseCatchGhostInfoPopupCommand(ByteDataExchanger exchanger) {
        super.exchanger = exchanger;
        this.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_CMD_CATCH_GHOST_INFORMATION_CONTENT_BYTES.length);
        this.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_CMD_CATCH_GHOST_INFORMATION_CONTENT_BYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseCatchGhostInfoPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseCatchGhostInfoPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }
}
