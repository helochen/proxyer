package com.mhxh.proxyer.fake.command.v1.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/2/17
 * @project proxyer
 **/
public class RefuseCatchGhostAgainPopupCommand extends  AbstractRefuseCommand{

    private static IRefuseFilter instance;

    private RefuseCatchGhostAgainPopupCommand(ByteDataExchanger exchanger) {
        super.exchanger = exchanger;
        this.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_CATCH_GHOST_GET_TASK_AGAIN_POPUP_CONTENT_GBK_BYTES.length);
        this.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_CATCH_GHOST_GET_TASK_AGAIN_POPUP_CONTENT_GBK_BYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseCatchGhostAgainPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseCatchGhostAgainPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }
}
