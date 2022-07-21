package com.mhxh.proxyer.fake.command.v1.remote.refuse;

import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractRefuseCommand implements IRefuseFilter, Comparable {


    protected ByteDataExchanger exchanger;

    ByteBuf byteBuf;

    AtomicInteger count = new AtomicInteger(0);

    @Override
    synchronized public boolean refuse(ByteBuf srcCmd) {
        boolean isFilter = count.get() > 0 && srcCmd != null && ByteBufUtil.indexOf(byteBuf, srcCmd) >= 0;
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

    @Override
    public int compareTo(Object o) {
        return this == o ? 0 : -1;
    }

    @Override
    public void reset() {
        count.set(0);
    }
}
