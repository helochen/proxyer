package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
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

    private ByteDataExchanger exchanger;

    private AtomicInteger count = new AtomicInteger(0);

    private static IRefuseFilter instance;

    private RefuseFlagPopupCommand(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;
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


    @Override
    synchronized public Boolean refuse(ByteBuf srcCmd) {
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


    @Override
    public int type() {
        return ByteDataExchanger.SERVER_OF_REMOTE;
    }
}
