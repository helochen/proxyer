package com.mhxh.proxyer.fake;

import com.mhxh.proxyer.tcp.exchange.TaskDataManager;
import com.mhxh.proxyer.web.service.IRegisterDirectCommandService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/27
 * @project proxyer
 **/
@Component
public class FakeCommandV2RegisterManager {

    private static final Logger logger = LoggerFactory.getLogger(FakeCommandV2RegisterManager.class);

    @Autowired
    private TaskDataManager taskDataManager;

    @Autowired
    private IRegisterDirectCommandService directCommandService;

    private volatile String leaderId;

    private volatile boolean tryAgain = false;

    public synchronized void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
        tryAgain = false;
    }

    public synchronized String getLeaderId() {
        return leaderId;
    }

    public void sendDirectCommand(String id, Channel remoteChannel) {

        if (!tryAgain && taskDataManager.getRoleTasks().isEmpty()) {
            // 去地府
            directCommandService.takeMoney(26, id);
            // 领任务
            directCommandService.catchGhost(id);
        } else {
            String result = directCommandService.fightGhost(id);
            if (!"success".equals(result)) {
                tryAgain = true;
                logger.info("抓鬼任务：{}，重试", result);
            }
        }
    }
}
