package com.mhxh.proxyer.tcp.server.handler;

import com.mhxh.proxyer.decode.EncryptDictionary;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
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

    private static final String ID_HEAD = "0080cb91da003679502c";

    private final ByteDataExchanger exchanger;

    public ChannelRegisterIdentifySimpleHandler(ByteDataExchanger exchanger) {
        this.exchanger = exchanger;

    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        final ByteBuf retain = byteBuf.retain();
        try {
            final String hexDump = ByteBufUtil.hexDump(retain);
            final int i = hexDump.indexOf(ID_HEAD);
            channelHandlerContext.fireChannelRead(byteBuf.retain());
            if (i >= 0) {
                final String subGbk = hexDump.substring(i + ID_HEAD.length() + 6);
                final int codeEnd = subGbk.indexOf("2a2d2a");
                if (codeEnd > 0) {
                    String code = subGbk.substring(0, codeEnd);
                    code = EncryptDictionary.decodeEncryptFromOriginalString(new String(ByteBufUtil.decodeHexDump(code), Charset.forName("GBK")));
                    // 注册上去，移除拦截器
                    exchanger.registerCode(code, channelHandlerContext.channel());
                    channelHandlerContext.pipeline().remove(this);
                }
            }
        } finally {
            ReferenceCountUtil.release(retain);
        }
    }
}
