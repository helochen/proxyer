package com.mhxh.proxyer.tcp.exchange;

import com.mhxh.proxyer.fake.FakeCommandRegisterFactory;
import com.mhxh.proxyer.fake.FakeCommandV2RegisterManager;
import com.mhxh.proxyer.fake.command.v1.base.IFormatCommand;
import com.mhxh.proxyer.fake.command.v1.local.*;
import com.mhxh.proxyer.fake.command.v1.remote.refuse.*;
import com.mhxh.proxyer.fake.command.v2.local.*;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;
import com.mhxh.proxyer.tcp.game.constants.MapConstants;
import com.mhxh.proxyer.tcp.game.constants.SectMapConstants;
import com.mhxh.proxyer.tcp.game.constants.TaskConstants;
import com.mhxh.proxyer.tcp.game.task.ITaskBean;
import com.mhxh.proxyer.tcp.service.IDumpDataService;
import com.mhxh.proxyer.web.patch.CatchGhostTaskPatch;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ByteDataExchanger {
    private static final Logger logger = LoggerFactory.getLogger(ByteDataExchanger.class);
    /**
     * 增加一个命令列表，每一个命令都是由一系列命令构成
     */
    private final Deque<Queue<IFormatCommand>> tasks = new ConcurrentLinkedDeque<>();

    /**
     * 过滤部分服务器数据列表
     */
    private final Set<IRefuseFilter> filters = new ConcurrentSkipListSet<>();


    @Autowired
    @Qualifier("taskExecutor")
    @Getter
    private Executor taskExecutor;

    @Getter
    @Autowired
    private FakeCommandRegisterFactory factory;

    @Autowired
    @Getter
    private IDumpDataService dumpDataService;

    @Autowired
    private FakeCommandV2RegisterManager fakeCommandV2RegisterManager;

    /**
     * 模拟命令功能相关参数
     */
    private Queue<IFormatCommand> currentCommandQueue;
    private boolean waiting = false;

    public static final int SERVER_OF_REMOTE = 1;
    public static final int SERVER_OF_LOCAL = 2;

    /**
     * 管理TCP链接的功能
     */
    private final Map<Channel, Channel> localFastQuery = new ConcurrentHashMap<>();
    private final Map<Channel, Channel> remoteFastQuery = new ConcurrentHashMap<>();
    /**
     * 用于直接给远程服务器发送信息
     */
    private final Map<String, Channel> pickChannel = new ConcurrentHashMap<>();
    private final Map<Channel, String> repickCodeChannel = new ConcurrentHashMap<>();


    public void register(Channel local, Channel remote) {
        if (!ObjectUtils.isEmpty(local) && !ObjectUtils.isEmpty(remote)) {
            localFastQuery.put(local, remote);
            remoteFastQuery.put(remote, local);
        }
    }

    public Channel getLocalByRemote(Channel remote) {
        return remoteFastQuery.get(remote);
    }

    public Channel getRemoteByLocal(Channel local) {
        return localFastQuery.get(local);
    }

    public Channel clearByLocal(Channel local) {
        Channel remote = localFastQuery.get(local);
        if (remote != null) {
            remoteFastQuery.remove(remote);
            if (repickCodeChannel.containsKey(remote)) {
                final String code = repickCodeChannel.remove(remote);
                pickChannel.remove(code);
            }
        }
        return remote;
    }


    /***
     * 增加一个命令链子
     * @param taskQueue
     */
    public void addFakeCommand(Queue<IFormatCommand> taskQueue) {
        if (!CollectionUtils.isEmpty(taskQueue)) {
            tasks.offer(taskQueue);
        }
        synchronized (this) {
            if (CollectionUtils.isEmpty(currentCommandQueue)) {
                currentCommandQueue = tasks.poll();
            }
        }
    }

    public synchronized Queue<IFormatCommand> getGroupCommandV2() {
        return tasks.poll();
    }

    /**
     * 获取当前需要转换的命令
     *
     * @return
     */
    public synchronized IFormatCommand getOneCommand() {
        if (!ObjectUtils.isEmpty(currentCommandQueue) && !waiting) {
            IFormatCommand current = currentCommandQueue.poll();
            if (CollectionUtils.isEmpty(currentCommandQueue)) {
                currentCommandQueue = tasks.poll();
            }
            if (!ObjectUtils.isEmpty(current)) {
                current.beforeCommandAddFilters();
            }
            return current;
        }
        return null;
    }

    synchronized public boolean resetWaiting(boolean waiting) {
        return this.waiting = waiting;
    }

    /***
     * 拒绝操作
     */
    public boolean filterServerCommand(ByteBuf byteBuf) {
        Iterator<IRefuseFilter> iterator = filters.iterator();
        while (iterator.hasNext()) {
            boolean refuse = iterator.next().refuse(byteBuf);
            if (refuse) {
                return true;
            }
        }
        return false;
    }


    /**
     * 添加拒绝命令
     *
     * @param instance
     */
    public void addRefuseCommand(IRefuseFilter instance) {
        filters.add(instance);
    }


    /**
     * 这个可以将地图转换为飞行命令
     *
     * @param mapName
     */
    public void registerChangeMapFakeCommand(String mapName) {

        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        if (MapConstants.MAP_CN_TO_CODE.containsKey(mapName)) {
            String serialNo = MapConstants.NAME_TO_SERIAL_NO.get(mapName);
            if (StringUtils.hasText(serialNo)) {
                this.buyFlyTicketFunction(1, taskQueue);
                UseBoxItemCommand useBoxItemCommand = new UseBoxItemCommand("1");
                useBoxItemCommand.addRefuseFilter(RefuseFlyTicketPopupCommand.createInstance(this));
                taskQueue.offer(useBoxItemCommand);
                taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, serialNo));
                taskQueue.offer(new UseItemFlushCommand());
            } else {
                logger.error("找到地图，但是没用结果：{}", mapName);
            }
        } else if (SectMapConstants.SECT_NAMES.contains(mapName)) {
            this.buyFlyTicketFunction(1, taskQueue);

            UseBoxItemCommand useBoxItemCommand = new UseBoxItemCommand("1");
            useBoxItemCommand.addRefuseFilter(RefuseFlyTicketPopupCommand.createInstance(this));
            taskQueue.offer(useBoxItemCommand);
            taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, "2"));
            taskQueue.offer(new UseItemFlushCommand());
            taskQueue.offer(new ChangAnNpcFlyToSectCommand(mapName));

        } else if ("江南野外".equals(mapName)) {
            this.buyFlyTicketFunction(1, taskQueue);

            UseBoxItemCommand useBoxItemCommand = new UseBoxItemCommand("1");
            useBoxItemCommand.addRefuseFilter(RefuseFlyTicketPopupCommand.createInstance(this));
            taskQueue.offer(useBoxItemCommand);
            taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, "3"));
            taskQueue.offer(new UseItemFlushCommand());
            taskQueue.offer(new UseNpcFlyToJiangNanMapCommand());

        } else if ("大唐境外".equals(mapName)) {
            UseBoxItemCommand useBoxItemCommand = new UseBoxItemCommand("16");
            useBoxItemCommand.addRefuseFilter(RefuseCmdJingwaiFlagPopupCommand.createInstance(this));
            taskQueue.offer(useBoxItemCommand);
            taskQueue.offer(new UseItemFlushCommand());
            taskQueue.offer(new UserMultiFlagFlyToDestinationSayYesCommand());
        } else {
            logger.error("未处理的地图位置:{}", mapName);
        }

        if (!CollectionUtils.isEmpty(taskQueue)) {
            this.addFakeCommand(taskQueue);
        }
    }

    private void buyFlyTicketFunction(int num, Queue<IFormatCommand> taskQueue) {
        if (num > 0) {
            FastUseSkillToXianlingDianpuCommand buyFlyTicketCommand = new FastUseSkillToXianlingDianpuCommand();
            buyFlyTicketCommand.addRefuseFilter(RefuseSkillXianLingDianpuPoppupCommand.createInstance(this));
            taskQueue.offer(buyFlyTicketCommand);
            taskQueue.offer(new BuyFlyTicketItemCommand(num));
        }

    }

    /**
     * 与抓鬼的怪兽NPC战斗
     *
     * @param next
     */
    public void registerFightWithNpcCommand(ITaskBean next) {
        String mapId = next.getMapId();
        String serialNo = next.getSerialNo();
        String id = next.getId();
        String name = next.getNpcName();

        if (StringUtils.hasText(mapId) && StringUtils.hasText(serialNo) && StringUtils.hasText(id)
                && StringUtils.hasText(name)) {

            Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();

            // 移动到目的地
            taskQueue.offer(new RoleMoveToTargetCommand(next.getX(), next.getY()));
            taskQueue.offer(new RoleWalkingRunningCommand(next.getX(), next.getY()));
            taskQueue.offer(new RoleWalkingStopCommand(next.getX(), next.getY()));

            // 直接先买个FF
            this.buyFlyTicketFunction(1, taskQueue);

            // 与鬼战斗，以及进制弹窗功能
            RoleRequestGhostFightCommand ghostFightCommand = new RoleRequestGhostFightCommand(mapId, serialNo, id);
            ghostFightCommand.addRefuseFilter(RefuseFightWithGhostPopupCommand.createInstance(this));
            ghostFightCommand.addRefuseFilter(RefuseContinueCatchGhostPopupCommand.createInstance(this));
            taskQueue.offer(ghostFightCommand);
            taskQueue.offer(new RoleFightWithGhostForSureCommand(mapId, name));

            this.addFakeCommand(taskQueue);
        }

    }

    private final AtomicInteger count = new AtomicInteger(1);

    /**
     * 发送事件对象
     *
     * @param eventCatchGhost
     */
    public void publishGhostEvent(EventTypeEnum eventCatchGhost) {
        switch (eventCatchGhost) {
            case EVENT_CATCH_GHOST:
                //eventPublisher.publishEvent(new CatchGhostEvent(this));
                int val = count.incrementAndGet();
                logger.info("任务注册：请求抓鬼任务,第{}次", val);
                factory.registerCatchGhost(0);
                break;
            default:
                break;
        }
    }

    public void registerFinishQinglongTaskItem(ITaskBean taskBean) {

        // 购买物品
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        taskQueue.offer(new BuySystemItemCommand(taskBean.getX(), taskBean.getY()));

        // 请求提交物品
        taskQueue.offer(new RequestCommitQinglongBuyItemCommand());
        // 提交物品
        taskQueue.offer(new ResponseQingLongTaskItemCommand(1));

        // 继续领取任务
        RequestQinglongTaskInfoCommand requestQinglongTaskInfoCommand = new RequestQinglongTaskInfoCommand();
        requestQinglongTaskInfoCommand.addRefuseFilter(RefuseQingLongTaskPopupCommand.createInstance(this));
        taskQueue.offer(requestQinglongTaskInfoCommand);

        this.addFakeCommand(taskQueue);
    }

    /**
     * 重启任务，避免有时候抓鬼被卡住
     */
    synchronized public void tryRegisterRestartTask() {
        if (CatchGhostTaskPatch.getInstance().timeout()) {
            logger.info("抓鬼补充任务触发");
            if (CatchGhostTaskPatch.getInstance().jumpToAddGhostTask()) {
                logger.info("抓鬼DEBUG信息：补充一次直接抓鬼请求");
                factory.registerCatchGhost(1);
            } else {
                // 补充任务
                logger.info("抓鬼DEBUG信息：补充一次抓鬼任务列表请求");
                Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
                QueryTaskListCommand queryTaskListCommand = new QueryTaskListCommand();
                queryTaskListCommand.addRefuseFilter(RefuseTaskListPopupCommand.createInstance(this));
                taskQueue.offer(queryTaskListCommand);

                this.addFakeCommand(taskQueue);
            }

        }

    }

    public void clearAllCommand() {
        tasks.clear();
        for (IRefuseFilter filter : filters) {
            filter.reset();
        }
    }

    public void registerCode(String code, Channel channel) {
        final Channel remoteByLocal = getRemoteByLocal(channel);
        if (null != remoteByLocal) {
            pickChannel.put(code, remoteByLocal);
            repickCodeChannel.put(remoteByLocal, code);
            logger.info("注册角色ID:{}与远程链接{}->{}", code, channel.id(), remoteByLocal.id());
        }
    }

    public Channel queryChannelById(String id) {
        return pickChannel.get(id);
    }


    /**
     * 发送飞行地图功能
     *
     * @param mapName
     */
    public boolean registerFlyDirectMapV2(String mapName) {
        if (StringUtils.hasText(fakeCommandV2RegisterManager.getLeaderId())
                && TaskConstants.FLY_TO_MAP.containsKey(mapName)) {
            Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
            taskQueue.offer(new
                    LocalChangeMapV2Command(TaskConstants.ROLE_CHANGE_MAP_DIRECT[
                    TaskConstants.FLY_TO_MAP.get(mapName)]));
            logger.info("V2抓鬼消息注册：飞行地图任务：{}", mapName);
            tasks.addLast(taskQueue);
            return true;
        }
        return false;
    }

    /**
     * 战斗
     *
     * @param next
     */
    public void registerFightWithNpcCommandV2(ITaskBean next) {
        if (StringUtils.hasText(fakeCommandV2RegisterManager.getLeaderId())) {
            Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
            taskQueue.offer(new LocalSendWalkingV2Command(next.getX(), next.getY()));
            taskQueue.offer(new LocalSendWalkingPixelV2Command(next.getX(), next.getY()));
            logger.info("V2抓鬼消息注册：移动命令：x:{},y:{}", next.getX(), next.getY());
            tasks.addLast(taskQueue);


            LocalRequestCatchGhostV2Command request = null;
            if (next.getId().length() == 28) {
                request = new
                        LocalRequestCatchGhostV2Command(next.getMapId(), next.getSerialNo(),
                        next.getId(), next.getSerialNo(),
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[1][0],
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[1][1]);
            } else if (next.getId().length() == 27) {
                request = new
                        LocalRequestCatchGhostV2Command(next.getMapId(), next.getSerialNo(),
                        next.getId(), next.getSerialNo(),
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[0][0],
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[0][1]);
            } else if (next.getId().length() == 26) {
                request = new
                        LocalRequestCatchGhostV2Command(next.getMapId(), next.getSerialNo(),
                        next.getId(), next.getSerialNo(),
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[2][0],
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[2][1]);
            } else if (next.getId().length() == 25) {
                request = new
                        LocalRequestCatchGhostV2Command(next.getMapId(), next.getSerialNo(),
                        next.getId(), next.getSerialNo(),
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[3][0],
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[3][1]);
            } else if (next.getId().length() == 24) {
                request = new
                LocalRequestCatchGhostV2Command(next.getMapId(), next.getSerialNo(),
                        next.getId(), next.getSerialNo(),
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[4][0],
                        LocalSendV2CommandRuleConstants.ROLE_FIGHT_WITH_GHOST_STRANGE_HEADER[4][1]);
            } else {
                logger.info("V2抓鬼消息注册：抓鬼数据异常：{},{}", next.getNpcName(), next.getMapName());
            }
            if (request != null) {

                Queue<IFormatCommand> fightQueue = new ConcurrentLinkedDeque<>();
                fightQueue.offer(request);
                fightQueue.offer(new LocalKillGhostTaskV2Command(next.getMapId(), next.getNpcName()));
                logger.info("V2抓鬼消息注册：杀鬼命令：{},{}", next.getNpcName(), next.getMapName());
                next.setId(null).setMapId(null).setSerialNo(null).initNpcTargetMapName(null);
                next.finish();
                tasks.addLast(fightQueue);
                // 领抓鬼任务
                this.registerCatchGhost();
            }
        }
    }


    public void registerCatchGhost() {
        Queue<IFormatCommand> getTaskQueue = new ConcurrentLinkedDeque<>();
        int dst = System.currentTimeMillis() % 4 == 1 ? 26 : 25;
        getTaskQueue.offer(new
                LocalChangeMapV2Command(TaskConstants.ROLE_CHANGE_MAP_DIRECT[dst]));
        getTaskQueue.offer(new LocalAgreeCatchGhostV2Command());
        getTaskQueue.offer(new LocalQueryTaskV2Commend());
        logger.info("V2抓鬼消息注册：强制重新领取任务：{},{}", TaskConstants.ROLE_CHANGE_MAP_DIRECT[dst], dst);
        tasks.addLast(getTaskQueue);
    }

    public boolean hasCommand() {
        return !tasks.isEmpty() && !CollectionUtils.isEmpty(currentCommandQueue);
    }

    public void directOfferTaskGroup(Queue<IFormatCommand> taskQueue) {
        tasks.addLast(taskQueue);
    }

    public void registerCancelGhostTask() {
        if (StringUtils.hasText(fakeCommandV2RegisterManager.getLeaderId())){
            Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
            taskQueue.offer(new LocalCancelGhostV2Command());
            logger.info("V2抓鬼消息注册：取消任务");
            tasks.addLast(taskQueue);
        }
    }
}
