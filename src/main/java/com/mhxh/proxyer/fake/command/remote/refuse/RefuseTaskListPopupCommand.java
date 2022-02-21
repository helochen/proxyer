package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

public class RefuseTaskListPopupCommand extends AbstractRefuseCommand implements IRefuseFilter {


    private RefuseTaskListPopupCommand(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;
        super.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_CMD_CATCH_GHOST_TASK_LIST_CONTENT_BYTES.length);
        super.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_CMD_CATCH_GHOST_TASK_LIST_CONTENT_BYTES);
    }

    private static IRefuseFilter instance;


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseTaskListPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseTaskListPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }


}
