package com.mhxh.proxyer.web.service.impl;

import com.mhxh.proxyer.fake.command.v2.local.LocalAgreeCatchGhostV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalAgreeMoneyTaskV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalChangeMapV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalKillGhostTaskV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalRequestCatchGhostV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendWalkingPixelV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendWalkingV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalTalkToNpcV2Command;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.exchange.TaskDataManager;
import com.mhxh.proxyer.tcp.game.constants.TaskConstants;
import com.mhxh.proxyer.tcp.game.task.ITaskBean;
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
import org.springframework.util.StringUtils;

import java.util.Queue;

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

    @Autowired
    private TaskDataManager taskDataManager;

    @Override
    public void gotoXY(int x, int y, String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {

            final LocalSendWalkingV2Command localSendWalkingV2Command = new LocalSendWalkingV2Command(x, y);

            String hexRun = localSendWalkingV2Command.format();
            final byte[] bytesRun = ByteBufUtil.decodeHexDump(hexRun);
            final LocalSendWalkingPixelV2Command localSendWalkingPixelV2Command = new LocalSendWalkingPixelV2Command(x, y);

            String hexRunPixel = localSendWalkingPixelV2Command.format();

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
    public void takeMoney(int dst, String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            long time = System.currentTimeMillis() / 1000;
            LocalChangeMapV2Command command = new
                    LocalChangeMapV2Command(TaskConstants.ROLE_CHANGE_MAP_DIRECT[dst]);

            String hexRun = command.format(String.valueOf(time), null);
            logger.info("虚拟命令：{}", hexRun);
            final byte[] changeMapBytes = ByteBufUtil.decodeHexDump(hexRun);

            byte[] getTask = null;
            if (dst == 0) {
                LocalAgreeMoneyTaskV2Command money = new LocalAgreeMoneyTaskV2Command();
                String hexTask = money.format(String.valueOf(time), null);
                getTask = ByteBufUtil.decodeHexDump(hexTask);

            }

            final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(changeMapBytes.length +
                    (getTask == null ? 0 : getTask.length));
            try {
                buffer.writeBytes(changeMapBytes);
                if (getTask != null) {
                    buffer.writeBytes(getTask);
                }
                channel.writeAndFlush(buffer.retain());
            } finally {
                ReferenceCountUtil.release(buffer);
            }
            if (dst != 0) {
                try {
                    Thread.sleep(300);
                    // 移动
                    if (dst == 2 || dst == 9 || dst == 10 || dst == 11 || dst == 12) {
                        if (TaskConstants.MOVE_MAP_POS.containsKey(dst)) {
                            Integer[] pos = TaskConstants.MOVE_MAP_POS.get(dst);
                            this.gotoXY(pos[0], pos[1], id);
                        }
                    }
                    // 与 NPC聊天
                    if (TaskConstants.NPC_TALK_MAP.containsKey(dst)) {
                        Integer[] info = TaskConstants.NPC_TALK_MAP.get(dst);
                        if (info.length >= 3) {
                            Thread.sleep(300);
                            this.talkToNpc(info[0], info[1], info[2], id);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void catchGhost(String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            long time = System.currentTimeMillis() / 1000;
            LocalAgreeCatchGhostV2Command agree = new LocalAgreeCatchGhostV2Command();
            String hexAgree = agree.format(String.valueOf(time), null);
            final byte[] bytesAgree = ByteBufUtil.decodeHexDump(hexAgree);
            final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(bytesAgree.length);
            try {
                buffer.writeBytes(bytesAgree);
                channel.writeAndFlush(buffer.retain());
            } finally {
                ReferenceCountUtil.release(buffer);
            }
        }
    }

    @Override
    public String fightGhost(String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            try {
                final Queue<ITaskBean> roleTasks = taskDataManager.getRoleTasks();
                final ITaskBean poll = roleTasks.poll();
                if (StringUtils.hasText(poll.getId())) {
                    // 直接发送干鬼的消息
                    this.gotoXY(poll.getX(), poll.getY(), id);
                    Thread.sleep(200);
                    LocalRequestCatchGhostV2Command request = new
                            LocalRequestCatchGhostV2Command(poll.getMapId(), poll.getSerialNo(),
                            poll.getId(), poll.getSerialNo());
                    final String requsetStr = request.format();
                    byte[] getTaskRequest = ByteBufUtil.decodeHexDump(requsetStr);
                    final ByteBuf bufferRequest = ByteBufAllocator.DEFAULT.buffer(getTaskRequest.length);
                    try {
                        bufferRequest.writeBytes(getTaskRequest);
                        channel.writeAndFlush(bufferRequest.retain());
                    } finally {
                        ReferenceCountUtil.release(bufferRequest);
                    }
                    logger.info("虚拟命令：{}", requsetStr);
                    Thread.sleep(200);

                    LocalKillGhostTaskV2Command kill = new LocalKillGhostTaskV2Command(poll.getMapId(), poll.getNpcName());
                    final String format = kill.format();
                    byte[] getTask = ByteBufUtil.decodeHexDump(format);
                    final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(getTask.length);
                    try {
                        buffer.writeBytes(getTask);
                        channel.writeAndFlush(buffer.retain());
                    } finally {
                        ReferenceCountUtil.release(buffer);
                    }
                    logger.info("虚拟命令：{}", format);

                    return "success";
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return "fail";
    }

    private void talkToNpc(int mapId, int no, int idx, String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            LocalTalkToNpcV2Command talk = new LocalTalkToNpcV2Command(mapId, no, idx);
            String hexTask = talk.format();
            byte[] getTask = ByteBufUtil.decodeHexDump(hexTask);
            final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(getTask.length);
            try {
                buffer.writeBytes(getTask);
                channel.writeAndFlush(buffer.retain());
            } finally {
                ReferenceCountUtil.release(buffer);
            }
        }
    }
}
