package com.mhxh.proxyer.tcp.service.impl;


import com.mhxh.proxyer.decode.EncryptDictionary;
import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.exchange.TaskDataManager;
import com.mhxh.proxyer.tcp.game.constants.DataSplitConstant;
import com.mhxh.proxyer.tcp.game.constants.TaskConstants;
import com.mhxh.proxyer.tcp.game.task.ITaskBean;
import com.mhxh.proxyer.tcp.game.task.TaskBeanCreateFactory;
import com.mhxh.proxyer.tcp.service.IDumpDataService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;

@Service
public class DumpDataServiceImpl implements IDumpDataService {

    @Autowired
    private TaskDataManager taskDataManager;


    private static final Logger logger = LoggerFactory.getLogger(IDumpDataService.class);


    @Override
    public void outputHexStrAndFormatStr(ByteBuf buf, int source, int port) {
        try {
            // 复制
            String gbk = buf.toString(Charset.forName("GBK"));
            String from = source == ByteDataExchanger.SERVER_OF_LOCAL ?
                    "本地数据" : "服务器数据";

            if (!gbk.contains("欢迎") && !gbk.contains("击败") && !gbk.contains("活命")
                    && !gbk.contains("频道")) {
                String hexDump = ByteBufUtil.hexDump(buf);
                this.findTaskByReturnData(gbk);
                logger.info("\n{}:{}->\t发送16进制数据=>{}," +
                                "\n{}:{}->\t发送GBK解析数据=> {}", from, port,
                        hexDump, from, port, gbk);
            } else {
                logger.info("全局排除信息内容：{}", gbk);
            }
        } catch (Exception e) {
            logger.info("数据转换异常：{}", e.getMessage());
        } finally {
            ReferenceCountUtil.release(buf);
        }
    }

    @Override
    public void outputEncryptHexStrAndFormatStr(ByteBuf buf, int source, int port) throws Exception {
        try {
            String hexDump = ByteBufUtil.hexDump(buf);

            String from = source == ByteDataExchanger.SERVER_OF_LOCAL ?
                    "本地数据" : "服务器数据";
            hexDump = EncryptDictionary.DecodeEncryptString(hexDump);
            byte[] bytes = ByteBufUtil.decodeHexDump(hexDump);
            String gbkHex = new String(bytes, Charset.forName("GBK"));

            logger.info("\n{}:{}->\t发送16进制数据=>{},\n{}:{}->\t发送GBK解析数据=> {}",
                    from, port, hexDump, from, port, gbkHex);

        } catch (Exception e) {
            logger.info("数据转换异常：源头：{} ,{}", source, e.getMessage());
        } finally {
            ReferenceCountUtil.release(buf);
        }
    }


    private void findTaskByReturnData(String gbk) {

        /**
         * 看看是不是任务信息
         */
        if (gbk.contains(TaskConstants.TASK_JIANG_HU) || gbk.contains(TaskConstants.TASK_CATCH_GHOST)) {
            Matcher tasksFind = DataSplitConstant.DATA_PATTERN.matcher(gbk);
            while (tasksFind.find()) {
                String taskContent = tasksFind.group();
                if (taskContent.contains(TaskConstants.TASK_CATCH_GHOST) || taskContent.contains(TaskConstants.TASK_JIANG_HU)) {
                    ITaskBean taskBean = TaskBeanCreateFactory.createTaskBean(TaskConstants.TASK_CATCH_GHOST, taskContent);
                    // 地图位置，地图坐标
                    Matcher p = DataSplitConstant.TASK_DECOMPOSITION_POSTION.matcher(taskContent);
                    if (p.find()) {
                        String position = p.group();
                        int i = position.indexOf("(");
                        if (i >= 0) {
                            String city = position.substring(0, i);
                            String x_y = position.replace(city, "").replace("(", "").replace(")", "");

                            try {
                                String[] xy = x_y.split(",");
                                int x = Integer.parseInt(xy[0]);
                                int y = Integer.parseInt(xy[1]);
                                taskBean.initNpcXY(x, y);
                            } catch (Exception e) {
                                logger.error("异常：数据转换失败：{}", e.getMessage());
                            }
                            taskBean.initNpcTargetMapName(city);
                        }
                    }
                    Matcher taskNpcNameMatcher = DataSplitConstant.TASK_DECOMPOSITION_NAME.matcher(taskContent);
                    if (taskNpcNameMatcher.find()) {
                        String npcName = taskNpcNameMatcher.group();
                        taskBean.initNpcName(npcName);

                        logger.info("任务注册：{}->{}", taskBean.getMapName(), npcName);
                    }
                    taskDataManager.registerTaskBean(taskBean);

                } else {
                    logger.info("未知的任务：{}", taskContent);
                }
            }
        } else if (gbk.contains(TaskConstants.TASK_QING_LONG)) {
            Matcher tasksFind = DataSplitConstant.TASK_QING_LONG_NEED_ITEM.matcher(gbk);
            while (tasksFind.find()) {
                ITaskBean taskBean = TaskBeanCreateFactory.createTaskBean(TaskConstants.TASK_QING_LONG, tasksFind.group());
                taskDataManager.registerTaskBean(taskBean);
            }
        } else {
            // 这个是去地图查找所有的NPC地址信息
            Matcher detailNpc = DataSplitConstant.MAP_NPC_PATTERN.matcher(gbk);
            while (detailNpc.find()) {
                String npcDetail = detailNpc.group();
                if (taskDataManager.complementTask(npcDetail)) {
                    // 补充完成一个任务就可以了
                    logger.info("补充任务注册信息：{}", npcDetail);
                    break;
                } else {
                    logger.info("地图怪物信息：{}", npcDetail);
                    taskDataManager.registerNpcPosition(npcDetail);
                }
            }
        }
    }
}
