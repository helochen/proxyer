package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.exchange.EventTypeEnum;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class RefuseContinueCatchGhostPopupCommand extends AbstractRefuseCommand {


    private static IRefuseFilter instance;

    private RefuseContinueCatchGhostPopupCommand(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;
        super.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_CONTINUE_CATCH_GHOST_POPUP_CONTENT_BYTES.length);
        super.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_CONTINUE_CATCH_GHOST_POPUP_CONTENT_BYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseContinueCatchGhostPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseContinueCatchGhostPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized boolean refuse(ByteBuf srcCmd) {
        boolean refuse = super.refuse(srcCmd);
        if (refuse) {
            exchanger.publishGhostEvent(EventTypeEnum.EVENT_CATCH_GHOST);
        }
        return refuse;
    }
}
