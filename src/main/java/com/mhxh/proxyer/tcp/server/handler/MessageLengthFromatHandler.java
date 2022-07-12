package com.mhxh.proxyer.tcp.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class:  MessageLengthFromatHandler
 * <p>
 * Author: Chen
 * Date:   2019/6/3 14:11
 */

public class MessageLengthFromatHandler extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(MessageLengthFromatHandler.class);

    /**
     * 包体最大长度
     */
    private static final int MAX_LENGTH = 65535;

    /**
     * 命令长度整形int size = 4
     */
    private static final int PROTO_HEAD_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() > PROTO_HEAD_LENGTH) {
            in.markReaderIndex();
            int length = in.readByte() & 0xff;

            if (in.readableBytes() >= length) {
                in.resetReaderIndex();
                out.add(in.readRetainedSlice(length + 4));
            } else {
                in.resetReaderIndex();
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.error("catch exception:{}", evt.toString());
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public boolean isSharable() {
        return false;
    }

}
