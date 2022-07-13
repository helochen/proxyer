package com.mhxh.proxyer.tcp.server.handler;

import com.mhxh.proxyer.decode.EncryptDictionary;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/13
 * @project proxyer
 **/
public class ChannelRegisterIdentifySimpleHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final String ID_HEAD = "[\"数字hY,mP,\"]=";

    private final ByteDataExchanger exchanger;

    public ChannelRegisterIdentifySimpleHandler(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;

    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        final ByteBuf retain = byteBuf.retain();
        try {
            final String gbk = retain.toString(Charset.forName("GBK"));
            final int i = gbk.indexOf(ID_HEAD);
            channelHandlerContext.fireChannelRead(byteBuf.retain());
            if (i >= 0) {
                final String subGbk = gbk.substring(i + ID_HEAD.length());
                final int codeEnd = subGbk.indexOf("}");
                if (codeEnd > 0) {
                    String code = subGbk.substring(0, codeEnd - 1);
                    code = EncryptDictionary.decodeEncryptFromOriginalString(code);
                    // 注册上去，移除拦截器
                    exchanger.registerCode(code, channelHandlerContext.channel());
                    channelHandlerContext.pipeline().remove(this);
                }
            }
        }finally {
            ReferenceCountUtil.release(retain);
        }
    }
}
