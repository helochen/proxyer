package com.mhxh.proxyer.fake.command.v1.remote.refuse;

import com.mhxh.proxyer.fake.command.v1.base.IType;
import io.netty.buffer.ByteBuf;

public interface IRefuseFilter extends IType {

    /**
     *  拒绝消息
     */
    boolean refuse(ByteBuf srcCmd);

    /**
     * 增加一次
     */
    int addOneTime();

    /**
     * 重置数量
     */
    void reset();
}
