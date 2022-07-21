package com.mhxh.proxyer.web.service.impl;

import com.mhxh.proxyer.fake.command.v2.local.LocalChangeMapV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendWalkingPixelV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendWalkingV2Command;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;
import com.mhxh.proxyer.web.service.IRegisterDirectCommandService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/20
 * @project proxyer
 **/
@Service
public class RegisterDirectCommandServiceImpl implements IRegisterDirectCommandService {

    private static final Logger logger = LoggerFactory.getLogger(IRegisterDirectCommandService.class);

    @Autowired
    private ByteDataExchanger byteDataExchanger;


    @Override
    public void gotoXY(int x, int y, String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            long time = System.currentTimeMillis() / 1000;

            final LocalSendWalkingV2Command localSendWalkingV2Command = new LocalSendWalkingV2Command(x, y);

            String hexRun = localSendWalkingV2Command.format(String.valueOf(time), null);
            final byte[] bytesRun = ByteBufUtil.decodeHexDump(hexRun);
            final LocalSendWalkingPixelV2Command localSendWalkingPixelV2Command = new LocalSendWalkingPixelV2Command(x, y);

            String hexRunPixel = localSendWalkingPixelV2Command.format(String.valueOf(time), null);

            final byte[] bytesRunPixel = ByteBufUtil.decodeHexDump(hexRunPixel);
            logger.info("虚拟命令：{},{}", hexRun, hexRunPixel);
            final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(bytesRun.length + bytesRunPixel.length);
            try {
                buffer.writeBytes(bytesRun);
                buffer.writeBytes(bytesRunPixel);
                channel.writeAndFlush(buffer.retain());
            } finally {
                ReferenceCountUtil.release(buffer);
            }
        }
    }

    @Override
    public void changeMap(int dst, String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            long time = System.currentTimeMillis() / 1000;
            LocalChangeMapV2Command command = new
                    LocalChangeMapV2Command(LocalSendV2CommandRuleConstants.ROLE_CHANGE_MAP_DIRECT[dst]);
            String hexRun = command.format(String.valueOf(time), null);
            logger.info("虚拟命令：{}", hexRun);
            final byte[] bytesRunPixel = ByteBufUtil.decodeHexDump(hexRun);
            final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(bytesRunPixel.length);
            try {
                buffer.writeBytes(bytesRunPixel);
                channel.writeAndFlush(buffer.retain());
            } finally {
                ReferenceCountUtil.release(buffer);
            }
        }
    }
}
