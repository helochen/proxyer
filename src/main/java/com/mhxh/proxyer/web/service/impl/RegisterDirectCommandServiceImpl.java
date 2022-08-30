package com.mhxh.proxyer.web.service.impl;

import com.mhxh.proxyer.fake.FakeCommandV2RegisterManager;
import com.mhxh.proxyer.fake.command.v1.base.IFormatCommand;
import com.mhxh.proxyer.fake.command.v2.local.LocalAgreeCatchGhostV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalAgreeMoneyTaskV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalChangeMapV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalKillGhostTaskV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalQueryTaskV2Commend;
import com.mhxh.proxyer.fake.command.v2.local.LocalRequestCatchGhostV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendWalkingPixelV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalSendWalkingV2Command;
import com.mhxh.proxyer.fake.command.v2.local.LocalTalkToNpcV2Command;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.exchange.TaskDataManager;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;
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
import java.util.concurrent.ConcurrentLinkedDeque;

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

    @Autowired
    private FakeCommandV2RegisterManager registerManager;

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
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.queryTaskList(id);
        }
    }

    @Override
    public String fightGhost(String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            try {
                final Queue<ITaskBean> roleTasks = taskDataManager.getRoleTasks();
                final ITaskBean poll = roleTasks.poll();
                if (poll != null && StringUtils.hasText(poll.getId()) && !poll.isFinish()) {
                    // 直接发送干鬼的消息
                    this.gotoXY(poll.getX(), poll.getY(), id);
                    Thread.sleep(200);
                    LocalRequestCatchGhostV2Command request;
                    logger.info("请求抓鬼数据：{},{},{},{}", poll.getNpcName(), poll.getMapId(), poll.getId(), poll.getId().length());
                    if (poll.getId().length() == 28) {
                        request = new
                                LocalRequestCatchGhostV2Command(poll.getMapId(), poll.getSerialNo(),
                                poll.getId(), poll.getSerialNo(),
                                LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[1][0],
                                LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[1][1]);
                    } else if (poll.getId().length() == 27) {
                        request = new
                                LocalRequestCatchGhostV2Command(poll.getMapId(), poll.getSerialNo(),
                                poll.getId(), poll.getSerialNo(),
                                LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[0][0],
                                LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[0][1]);
                    } else if (poll.getId().length() == 26) {
                        request = new
                                LocalRequestCatchGhostV2Command(poll.getMapId(), poll.getSerialNo(),
                                poll.getId(), poll.getSerialNo(),
                                LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[2][0],
                                LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[2][1]);
                    } else if (poll.getId().length() == 25) {
                        request = new
                                LocalRequestCatchGhostV2Command(poll.getMapId(), poll.getSerialNo(),
                                poll.getId(), poll.getSerialNo(),
                                LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[3][0],
                                LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[3][1]);
                    } else {
                        logger.info("抓鬼数据异常：{}", poll.getNpcName());
                        return "error name";
                    }


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
                } else {
                    // 移除空抓鬼任务
                    assert poll != null;
                    logger.info("抓鬼任务失败：{},{},{}", poll.getNpcName(), poll.getMapName(), poll.getSerialNo());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return "fail";
    }

    @Override
    public String autoGhost(String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            registerManager.setLeaderId(id);
            if (!byteDataExchanger.hasCommand()) {
                LocalChangeMapV2Command command = new
                        LocalChangeMapV2Command(TaskConstants.ROLE_CHANGE_MAP_DIRECT[26]);
                Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
                taskQueue.offer(command);

                LocalAgreeCatchGhostV2Command agree = new LocalAgreeCatchGhostV2Command();
                taskQueue.offer(agree);

                byteDataExchanger.directOfferTaskGroup(taskQueue);
            }
            return id;
        } else {
            registerManager.setLeaderId(null);
        }
        return null;
    }

    @Override
    public String autoLunhui(String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (channel != null) {
            registerManager.setLeaderId(id);
            if (!byteDataExchanger.hasCommand()) {
                LocalChangeMapV2Command command = new
                        LocalChangeMapV2Command(TaskConstants.ROLE_CHANGE_MAP_DIRECT[20]);
                Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
                taskQueue.offer(command);

                byteDataExchanger.directOfferTaskGroup(taskQueue);
            }
            return id;
        } else {
            registerManager.setLeaderId(null);
        }
        return null;
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

    private void queryTaskList(String id) {
        final Channel channel = byteDataExchanger.queryChannelById(id);
        if (null != channel) {
            LocalQueryTaskV2Commend queryTaskV2Commend = new LocalQueryTaskV2Commend();
            String hexTask = queryTaskV2Commend.format();
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
