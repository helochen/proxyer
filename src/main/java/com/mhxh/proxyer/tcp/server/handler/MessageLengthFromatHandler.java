package com.mhxh.proxyer.tcp.server.handler;

import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
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


        String hexGBK = ByteBufUtil.hexDump(in);
        logger.info("hexGBK->{}" , hexGBK);
        if (hexGBK.contains(LocalSendV2CommandRuleConstants.COMMEND_PROTO_GET_HEADER)) {
            int length = hexGBK.indexOf("2a56502c");
            if (length >=0 && in.readableBytes() >= length / 2 + 31) {
                out.add(in.readRetainedSlice(length / 2 + 31));
            }
        } else if (hexGBK.contains(LocalSendV2CommandRuleConstants.COMMEND_PROTO_POST_HEADER)) {
            int length = hexGBK.indexOf("72657475726e2072657420656e64");
            if (length >=0 && in.readableBytes() >= length / 2 + 14) {
                out.add(in.readRetainedSlice(length / 2 + 14));
            }
        } else {
            if (in.readableBytes() > PROTO_HEAD_LENGTH) {
                in.markReaderIndex();
                int length = in.readByte() & 0xff;
                in.resetReaderIndex();
                if (in.readableBytes() >= length + 4) {
                    out.add(in.readRetainedSlice(length + 4));
                }
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
