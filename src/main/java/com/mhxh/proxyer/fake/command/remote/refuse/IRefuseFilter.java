package com.mhxh.proxyer.fake.command.remote.refuse;

import io.netty.buffer.ByteBuf;

public interface IRefuseFilter {

    /**
     *  拒绝消息
     */
    Boolean refuse(ByteBuf srcCmd);

    /**
     * 增加一次
     */
    int addOneTime();
}
