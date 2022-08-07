package com.mhxh.proxyer.tcp.exchange;

import com.google.common.base.Splitter;
import com.mhxh.proxyer.tcp.game.constants.DataSplitConstant;
import com.mhxh.proxyer.tcp.game.constants.TaskConstants;
import com.mhxh.proxyer.tcp.game.task.ITaskBean;
import com.mhxh.proxyer.web.patch.CatchGhostTaskPatch;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Matcher;

@Component
public class TaskDataManager {

    private static final Logger logger = LoggerFactory.getLogger(TaskDataManager.class);

    @Autowired
    private ByteDataExchanger exchanger;

    /**
     * 当前任务（抓鬼、初出江湖等）的对象管理器
     */
    @Getter
    private final Queue<ITaskBean> roleTasks = new ConcurrentLinkedDeque<>();

    /**
     * 注册抓鬼任务到队列中
     */
    public void registerTaskBeanV2(ITaskBean taskBean) {
        if ("null".equals(taskBean.getNpcName())
                || taskBean.getNpcName() == null) {
            logger.info("任务注册：移除奇怪的信息注册{}", taskBean.getNpcName());
            return;
        }
        Iterator<ITaskBean> iterator = roleTasks.iterator();
        synchronized (this) {
            while (iterator.hasNext()) {
                ITaskBean next = iterator.next();
                if (next.isFinish()) {
                    roleTasks.remove(next);
                    logger.info("任务注册：任务完成{}", taskBean.getNpcName());
                } else {
                    if (next.equals(taskBean)) {
                        logger.info("任务注册：重复得抓鬼任务{}", taskBean.getNpcName());
                        return;
                    }
                }
            }
        }

        // 产生任务对象
        switch (taskBean.getTaskType()) {
            case 2:
                roleTasks.offer(taskBean);
                if (exchanger.registerFlyDirectMapV2(taskBean.getMapName())) {
                    logger.info("抓鬼DEBUG信息：新增抓鬼任务,队伍中包含数量{}:{}->{}", roleTasks.size(), taskBean.getMapName(), taskBean.getNpcName());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 注入手头任务信息
     *
     * @param taskBean
     */
    public void registerTaskBean(ITaskBean taskBean) {
        // 将现在手头的任务进行注册
        Iterator<ITaskBean> iterator = roleTasks.iterator();

        synchronized (TaskDataManager.class) {
            while (iterator.hasNext()) {
                ITaskBean next = iterator.next();
                if (next.isFinish()) {
                    logger.info("抓鬼DEBUG信息：移除抓鬼任务,队伍中包含数量{}:{}->{}", roleTasks.size(), next.getNpcName(), next.getMapName());
                    iterator.remove();
                }
                if (next.equals(taskBean)) {
                    logger.info("任务注册：重复得抓鬼任务{}", taskBean.getNpcName());
                    if (taskBean.getTaskType() == 2) {
                        if (CatchGhostTaskPatch.getInstance().isBlock()) {
                            CatchGhostTaskPatch.getInstance().reset();
                            exchanger.registerChangeMapFakeCommand("长安城");
                            exchanger.registerChangeMapFakeCommand(next.getMapName());
                        }
                    }
                    return;
                }
            }

        }
        // 产生任务对象
        switch (taskBean.getTaskType()) {
            case 1:
                exchanger.registerFinishQinglongTaskItem(taskBean);
                break;
            case 2:
                CatchGhostTaskPatch.getInstance().reset();
                roleTasks.offer(taskBean);
                logger.info("抓鬼DEBUG信息：新增抓鬼任务,队伍中包含数量{}:{}->{}", roleTasks.size(), taskBean.getMapName(), taskBean.getNpcName());
                exchanger.registerChangeMapFakeCommand(taskBean.getMapName());
                break;
            default:
                break;
        }
    }

    /**
     * 任务信息填充V2
     */
    public boolean complementTaskV2(String npcDetail) {
        if (npcDetail.contains("事件=\"单位\"")) {
            Iterator<ITaskBean> iterator = roleTasks.iterator();
            while (iterator.hasNext()) {
                ITaskBean next = iterator.next();
                if (StringUtils.hasText(next.getNpcName()) && npcDetail.contains(next.getNpcName()) && !next.isFinish()) {
                    synchronized (TaskDataManager.class) {
                        npcDetail = npcDetail.substring(3, npcDetail.length() - 8);
                        try {
                            Map<String, String> holder = Splitter.on(",").trimResults().withKeyValueSeparator("=").split(npcDetail.replaceAll("\\{", "").replaceAll("}", ""));
                            String mapId = holder.getOrDefault("地图", "");
                            String id = holder.getOrDefault("id", "");
                            String serialNo = holder.getOrDefault("编号", "");
                            int x = Integer.parseInt(holder.getOrDefault("x", "-1"));
                            int y = Integer.parseInt(holder.getOrDefault("y", "-1"));
                            String no = holder.getOrDefault("序号", "");
                            //String name = holder.getOrDefault("名称", "");

                            next.initNpcXY(x, y).setSerialNo(serialNo)
                                    .setId(id).setMapId(mapId).setNo(no);
                            logger.info("补充信息：{}", next);
                            if (StringUtils.hasText(id) && StringUtils.hasText(mapId) && x >= 0 && y >= 0 && StringUtils.hasText(serialNo)) {
                                exchanger.registerFightWithNpcCommandV2(next);
                            }
                            return true;
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 把任务信息进行填充
     *
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
                    int x = Integer.parseInt(holder.getOrDefault("x", "10"));
                    int y = Integer.parseInt(holder.getOrDefault("y", "10"));

                    next.initNpcXY(x, y).setSerialNo(serialNo)
                            .setId(id).setMapId(mapId);

                    exchanger.registerFightWithNpcCommand(next);
                    synchronized (TaskDataManager.class) {
                        next.finish();
                    }
                    return true;
                } catch (Exception ex) {
                    logger.error("{}, {}", npcDetail, ex.getMessage());
                }
            }
        }
        return false;
    }

    public void registerNpcPosition(String npcDetail) {
        if (npcDetail.contains(TaskConstants.NPC_MONSTER_NIANSHOU_)
                || npcDetail.contains(TaskConstants.NPC_MASTER_FIRE)
                || npcDetail.contains(TaskConstants.NPC_MONSTER_PANGNI)) {
            logger.info("NPC位置查询：{}", npcDetail);
        }
    }
}
