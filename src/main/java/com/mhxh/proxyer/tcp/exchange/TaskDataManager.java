package com.mhxh.proxyer.tcp.exchange;

import com.google.common.base.Splitter;
import com.mhxh.proxyer.tcp.game.constants.DataSplitConstant;
import com.mhxh.proxyer.tcp.game.task.ITaskBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Matcher;

@Component
public class TaskDataManager {

    private static final Logger logger = LoggerFactory.getLogger(TaskDataManager.class);

    /**
     * 当前任务（抓鬼、初出江湖等）的对象管理器
     */
    private final Queue<ITaskBean> roleTasks = new ConcurrentLinkedDeque<>();



    /**
     * 注入手头任务信息
     *
     * @param taskBean
     */
    public void registerTaskBean(ITaskBean taskBean) {
        // 将现在手头的任务进行注册
        Iterator<ITaskBean> iterator = roleTasks.iterator();
        while (iterator.hasNext()) {
            ITaskBean next = iterator.next();
            if (next.equals(taskBean)) {
                return;
            }
        }
        roleTasks.offer(taskBean);
    }

    /**
     * 把任务信息进行填充
     * @param npcDetail
     * @return
     */
    public boolean complementTask(String npcDetail) {
        Iterator<ITaskBean> iterator = roleTasks.iterator();
        while (iterator.hasNext()) {
            ITaskBean next = iterator.next();
            if (npcDetail.contains(next.getNpcName())) {
                try {
                    Matcher m = DataSplitConstant.MAP_NPC_TARGET_ROLE_PATTERN.matcher(npcDetail);
                    while (m.find()) {
                        npcDetail = npcDetail.replace(m.group(), "");
                    }
                    Map<String, String> holder = Splitter.on(",").trimResults().withKeyValueSeparator("=").split(npcDetail.replaceAll("\\{", "").replaceAll("}", ""));
                    String mapId = holder.getOrDefault("地图", "");
                    String id = holder.getOrDefault("id", "");
                    String serialNo = holder.getOrDefault("编号", "");
                    next.setSerialNo(serialNo)
                            .setId(id).setMapId(mapId);
                    return true;
                } catch (Exception ex) {
                    logger.error("{}, {}", npcDetail, ex.getMessage());
                }
            }
        }
        return false;
    }
}