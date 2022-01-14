package com.mhxh.proxyer.tcp.service.impl;


import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.game.DataSplitConstant;
import com.mhxh.proxyer.tcp.game.MapConstants;
import com.mhxh.proxyer.tcp.game.TaskConstants;
import com.mhxh.proxyer.tcp.service.IDumpDataService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.regex.Matcher;

@Service
public class DumpDataServiceImpl implements IDumpDataService {


    private static final Logger logger = LoggerFactory.getLogger(IDumpDataService.class);


    @Override
    public void outputHexStrAndFormatStr(ByteBuf buf, int source) {
        try {
            ByteBuf receive = buf.retainedSlice();
            // 复制
            String gbk = receive.toString(Charset.forName("GBK"));
            String from = source == ByteDataExchanger.SERVER_OF_LOCAL ?
                    "本地数据" : "服务器数据";

            if (!gbk.contains("欢迎") && !gbk.contains("击败") && !gbk.contains("奖励") && !gbk.contains("活命")
                    && !gbk.contains("频道")) {
                String hexDump = ByteBufUtil.hexDump(receive);
                this.findTaskByReturnData(hexDump, gbk);
                logger.info("\n{}->\t发送16进制数据=>{}," +
                                "\n{}->\t发送GBK解析数据=> {}", from,
                        hexDump, from, gbk);
            }
            receive.release();

        } catch (Exception e) {
            logger.info("数据转换异常：{}", e.getMessage());
        } finally {
            ReferenceCountUtil.refCnt(buf);
        }

    }

    private void findTaskByReturnData(String hexDump, String gbk) {

        if (gbk.contains(TaskConstants.TASK_JIAN_HU)) {
            Matcher tasksFind = DataSplitConstant.DATA_PATTERN.matcher(gbk);
            while (tasksFind.find()) {
                String taskContent = tasksFind.group();

                if (taskContent.contains(TaskConstants.TASK_CATCH_GHOST)) {
                    // TODO 地图位置，地图坐标

                } else if (taskContent.contains(TaskConstants.TASK_JIAN_HU)) {
                    Matcher p = DataSplitConstant.TASK_DECOMPOSITION_POSTION.matcher(taskContent);

                    if (p.find()) {
                        String position = p.group();
                        int i = position.indexOf("(");
                        if (i >= 0) {
                            String city = position.substring(0, i);
                            String x_y = position.replace(city, "").replace("(", "").replace(")", "");

                            try {
                                String[] xy = x_y.split(",");
                                Integer.parseInt(xy[0]);
                                Integer.parseInt(xy[1]);
                                if (MapConstants.MAP_CN_TO_CODE.containsKey(city)) {
                                    ByteDataExchanger.NextPosition.put(MapConstants.MAP_CN_TO_CODE.get(city), xy);
                                }
                            } catch (Exception e) {
                                logger.error("异常：数据转换失败：{}", e.getMessage());
                            }
                        }
                    }

                } else {
                    logger.info("未知的任务：{}", taskContent);
                }

            }
        }
    }
}
