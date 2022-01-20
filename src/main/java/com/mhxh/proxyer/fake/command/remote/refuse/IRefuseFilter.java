package com.mhxh.proxyer.fake.command.remote.refuse;

import com.mhxh.proxyer.fake.command.base.IType;
import io.netty.buffer.ByteBuf;

public interface IRefuseFilter extends IType {

    /**
     *  拒绝消息
     */
    Boolean refuse(ByteBuf srcCmd);

    /**
     * 增加一次
     */
    int addOneTime();
}
