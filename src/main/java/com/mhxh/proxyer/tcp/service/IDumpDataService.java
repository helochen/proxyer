package com.mhxh.proxyer.tcp.service;

import io.netty.buffer.ByteBuf;

public interface IDumpDataService extends IBaseService {


    void outputHexStrAndFormatStr(ByteBuf buf, int src);

    void outputEncryptHexStrAndFormatStr(ByteBuf recordBuf, int type) throws Exception;
}
