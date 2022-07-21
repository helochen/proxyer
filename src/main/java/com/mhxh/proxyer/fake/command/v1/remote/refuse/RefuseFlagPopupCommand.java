package com.mhxh.proxyer.fake.command.v1.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

/***
 * 飞行器弹窗
 */
public class RefuseFlagPopupCommand extends AbstractRefuseCommand implements IRefuseFilter {


    private static IRefuseFilter instance;

    private RefuseFlagPopupCommand(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;
        super.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_BYTES.length);
        super.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_BYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseFlagPopupCommand.class) {
                if (instance == null) {
                    instance = new RefuseFlagPopupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }


}
