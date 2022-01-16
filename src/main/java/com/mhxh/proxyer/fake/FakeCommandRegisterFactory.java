package com.mhxh.proxyer.fake;

import com.mhxh.proxyer.fake.command.BaseCommand;
import com.mhxh.proxyer.fake.command.local.*;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;
import com.mhxh.proxyer.tcp.game.constants.MapConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class FakeCommandRegisterFactory {

    @Autowired
    private ByteDataExchanger exchanger;

    @Resource
    private MapConstants mapConstants;

    public void registerFlyTicketItemFlyToMap(String serialNo) {

        Queue<BaseCommand> taskQueue = new ConcurrentLinkedDeque<>();
        taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, serialNo));
        taskQueue.offer(new UseItemFlushCommand());


        exchanger.addFakeCommand(taskQueue);
    }

    public void registerFlyToSectByName(String serialNo, String sect) {
        Queue<BaseCommand> taskQueue = new ConcurrentLinkedDeque<>();
        // 先飞长安城
        taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, serialNo));
        taskQueue.offer(new ChangAnNpcFlyToSectCommand(sect));

        exchanger.addFakeCommand(taskQueue);
    }

    public void registerFlyToJiangNanMap() {
        Queue<BaseCommand> taskQueue = new ConcurrentLinkedDeque<>();
        // 先飞长安城
        taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, mapConstants.CODE_TO_SERIAL_NO.get("1001")));
        taskQueue.offer(new UseMapTransferToJiangnanyewaiMapCommand());

        exchanger.addFakeCommand(taskQueue);
    }

    public void registerFlyFlagToMap(String flagIdx, String mapId) {
        Queue<BaseCommand> taskQueue = new ConcurrentLinkedDeque<>();
        taskQueue.offer(new UseBoxItemCommand(flagIdx));
        taskQueue.offer(new UseFlyFlagToMapDestinationSayYesCommand(mapId));
        exchanger.addFakeCommand(taskQueue);
    }
}
