package com.mhxh.proxyer.fake;

import com.mhxh.proxyer.fake.command.BaseCommand;
import com.mhxh.proxyer.fake.command.local.UseFlyItemFlayToMapCommand;
import com.mhxh.proxyer.fake.command.local.UseItemFlushCommand;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendCommandRuleConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class FakeCommandRegisterFactory {

    @Autowired
    private ByteDataExchanger exchanger;


    public void registerFlyTicketItemFlyToMap(String serialNo) {

        Queue<BaseCommand> taskQueue = new ConcurrentLinkedDeque<>();
        taskQueue.offer(new UseFlyItemFlayToMapCommand(LocalSendCommandRuleConstants.USE_ITEM_FLY_TO_MAP_GKB, serialNo));
        taskQueue.offer(new UseItemFlushCommand());


        exchanger.addFakeCommand(taskQueue);
    }
}
