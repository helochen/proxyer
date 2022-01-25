package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBufAllocator;

public class RefuseSkillXianLingDianpuPoppupCommand extends AbstractRefuseCommand {

    private static IRefuseFilter instance;

    private RefuseSkillXianLingDianpuPoppupCommand(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;
        super.byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_XIANLING_DIANPU_POPUP_CONTENT_BYTES.length);
        super.byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_XIANLING_DIANPU_POPUP_CONTENT_BYTES);
    }


    public static IRefuseFilter createInstance(ByteDataExchanger exchanger) {
        if (instance == null) {
            synchronized (RefuseSkillXianLingDianpuPoppupCommand.class) {
                if (instance == null) {
                    instance = new RefuseSkillXianLingDianpuPoppupCommand(exchanger);
                    exchanger.addRefuseCommand(instance);
                }
            }
        }
        return instance;
    }
}
