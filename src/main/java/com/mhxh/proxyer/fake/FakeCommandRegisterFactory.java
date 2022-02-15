package com.mhxh.proxyer.fake;

import com.mhxh.proxyer.fake.command.base.IFormatCommand;
import com.mhxh.proxyer.fake.command.local.*;
import com.mhxh.proxyer.fake.command.remote.refuse.*;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;
import com.mhxh.proxyer.tcp.game.constants.MapConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class FakeCommandRegisterFactory {

    @Autowired
    private ByteDataExchanger exchanger;


    public void registerFlyTicketItemFlyToMap(String serialNo) {

        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, serialNo));
        taskQueue.offer(new UseItemFlushCommand());


        exchanger.addFakeCommand(taskQueue);
    }

    public void registerFlyToSectByName(String serialNo, String sect) {
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        // 先飞长安城
        taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, serialNo));
        taskQueue.offer(new ChangAnNpcFlyToSectCommand(sect));

        exchanger.addFakeCommand(taskQueue);
    }

    public void registerFlyToJiangNanMap() {
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        // 先飞长安城
        taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, MapConstants.NAME_TO_SERIAL_NO.get("1001")));
        taskQueue.offer(new UseMapTransferToJiangnanyewaiMapCommand());

        exchanger.addFakeCommand(taskQueue);
    }

    public void registerFlyFlagToMap(String flagIdx, String mapId) {
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        UseBoxItemCommand useBoxItemCommand = new UseBoxItemCommand(flagIdx);
        useBoxItemCommand.addRefuseFilter(RefuseFlagPopupCommand.createInstance(exchanger));
        taskQueue.offer(useBoxItemCommand);
        taskQueue.offer(new UseFlyFlagToMapDestinationSayYesCommand(mapId));
        exchanger.addFakeCommand(taskQueue);
    }

    public void registerCatchGhost() {
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();

        // 补充飞行符
        FastUseSkillToXianlingDianpuCommand buyFlyTicketCommand = new FastUseSkillToXianlingDianpuCommand();
        buyFlyTicketCommand.addRefuseFilter(RefuseSkillXianLingDianpuPoppupCommand.createInstance(exchanger));
        taskQueue.offer(buyFlyTicketCommand);
        taskQueue.offer(new BuyFlyTicketItemCommand(1));

        // 先飞长安城
        UseBoxItemCommand useBoxItemCommand = new UseBoxItemCommand("1");
        useBoxItemCommand.addRefuseFilter(RefuseFlyTicketPopupCommand.createInstance(exchanger));
        taskQueue.offer(useBoxItemCommand);
        taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, "2"));
        taskQueue.offer(new UseItemFlushCommand());
        taskQueue.offer(new ChangAnNpcFlyToSectCommand("阴曹地府"));
        // 领任务
        RequestCatchGhostCommand requestCatchGhostCommand = new RequestCatchGhostCommand();
        requestCatchGhostCommand.addRefuseFilter(RefuseCatchGhostPopupCommand.createInstance(exchanger));
        taskQueue.offer(requestCatchGhostCommand);
        // 确定任务
        RequestCatchGhostForSureCommand requestCatchGhostForSureCommand = new RequestCatchGhostForSureCommand();
        requestCatchGhostForSureCommand.addRefuseFilter(RefuseCatchGhostInfoPopupCommand.createInstance(exchanger));
        taskQueue.offer(requestCatchGhostForSureCommand);
        // 查询任务
        QueryTaskListCommand queryTaskListCommand = new QueryTaskListCommand();
        queryTaskListCommand.addRefuseFilter(RefuseTaskListPopupCommand.createInstance(exchanger));
        taskQueue.offer(queryTaskListCommand);

        exchanger.addFakeCommand(taskQueue);
    }

    public void registerMoveTo(int x, int y) {
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();

        // 移动到目的地
        taskQueue.offer(new RoleMoveToTargetCommand(x, y));
        taskQueue.offer(new RoleWalkingRunningCommand(x, y));
        taskQueue.offer(new RoleWalkingStopCommand(x, y));
        exchanger.addFakeCommand(taskQueue);
    }

    public void registerBuyFlyTicket(int num) {
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        taskQueue.offer(new BuyFlyTicketItemCommand(num));
        exchanger.addFakeCommand(taskQueue);
    }

    public void registerFinishQinglongTask() {
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        taskQueue.offer(new RequestCommitQinglongBuyItemCommand());
        taskQueue.offer(new ResponseQingLongTaskItemCommand(1));
        exchanger.addFakeCommand(taskQueue);
    }


    public void registerGetQinglongTask() {
        Queue<IFormatCommand> taskQueue = new ConcurrentLinkedDeque<>();
        taskQueue.offer(new RequestQinglongTaskInfoCommand());
        exchanger.addFakeCommand(taskQueue);
    }
}
