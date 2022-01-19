package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.game.cmdfactory.RefuseGameCommandRuleConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

import java.util.concurrent.atomic.AtomicInteger;

/***
 * 飞行器弹窗
 */
public class RefuseFlagPopupCommand implements IRefuseFilter {

    private static final ByteBuf byteBuf;

    static {
        byteBuf = ByteBufAllocator.DEFAULT.directBuffer(RefuseGameCommandRuleConstants.REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_BYTES.length);
        byteBuf.writeBytes(RefuseGameCommandRuleConstants.REFUSE_CMD_CLIENT_OPEN_FLAG_CONTENT_BYTES);
    }

    private AtomicInteger count = new AtomicInteger(0);

    private RefuseFlagPopupCommand() {

    }

    private static final IRefuseFilter instance = new RefuseFlagPopupCommand();

    public static IRefuseFilter getInstance() {
        return instance;
    }

    @Override
    public Boolean refuse(ByteBuf srcCmd) {
        Boolean isFilter = count.get() > 0 && srcCmd != null && ByteBufUtil.indexOf(byteBuf, srcCmd) >= 0;
        if (isFilter) {
            count.decrementAndGet();
        }
        return isFilter;
    }

    @Override
    public int addOneTime() {
        return count.incrementAndGet();
    }
}
