package com.mhxh.proxyer;

import com.google.common.base.Splitter;
import com.mhxh.proxyer.decode.EncryptDictionary;
import com.mhxh.proxyer.tcp.game.constants.DataSplitConstant;
import com.mhxh.proxyer.tcp.game.constants.TaskConstants;
import com.mhxh.proxyer.tcp.game.task.ITaskBean;
import com.mhxh.proxyer.tcp.game.task.TaskBeanCreateFactory;
import io.netty.buffer.ByteBufUtil;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/22
 * @project proxyer
 **/
public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        //String format = "1502*-*do local ret={[1]=\"回你的地狱去\",[2]=1142,[3]=\"辰时五刻诌鬼\"} return ret end*-*1658537137";
        String format = "1502*-*do local ret={[1]=\"回你的地狱去\",[2]=1142,[3]=\"辰时五刻诌鬼\"} return ret end*-*1658537137";
        String result = EncryptDictionary.encodeEncryptString(format);
        final byte[] gbkBytes = result.getBytes(Charset.forName("GBK"));
        final String gbk = ByteBufUtil.hexDump(gbkBytes);
        String x = Integer.toHexString(gbkBytes.length + 4) + "0080cb91da00" + Integer.toHexString(gbkBytes.length) + gbk;
        System.out.println(gbkBytes.length);
        System.out.println(x);
    }


    public static void main(String[] args) {
        String gbk = "�\u0002�藨�\u0002筪o local ret={序号=40,内容={[1]={[1]=\"罗羹效果\",[2]=96172,[3]=\"介绍\",[4]=0},[2]={[1]=\"大战心魔[1]\",[2]=\"#W/你来到#G/大唐境外(234,57）看到#R/ht/天兵飞剑#W正对卷帘大将施以飞剑穿胸之刑。不忍卷帘大将受苦，你出手阻止了天兵飞剑。#R(需战斗)\",[3]=\"完成后可获得奖励\"},[3]={[1]=\"捉鬼任务\",[2]=\"前往#Y/江南野外(68,48)#W/处降服#G/酉时四刻冒失鬼#R/(当前第5次)\",[3]=\"可获得经验、银子，第10次任务可几率获得物品\"},[4]={[1]=\"摄妖香\",[2]=\"#W你的摄妖香效果还可持续#R/20#W/分钟。\",[3]=\"在低于自身等级+10的场景中不会触发暗雷战斗。\",[4]=20}}} return ret end";
        ITaskBean taskBean = null;
        if (gbk.contains(TaskConstants.TASK_JIANG_HU) || gbk.contains(TaskConstants.TASK_CATCH_GHOST)) {
            Matcher tasksFind = DataSplitConstant.DATA_PATTERN.matcher(gbk);
            while (tasksFind.find()) {
                String taskContent = tasksFind.group();
                if (taskContent.contains(TaskConstants.TASK_CATCH_GHOST) || taskContent.contains(TaskConstants.TASK_JIANG_HU)) {
                    taskBean = TaskBeanCreateFactory.createTaskBean(TaskConstants.TASK_CATCH_GHOST, taskContent);
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

                            }
                            taskBean.initNpcTargetMapName(city);
                        }
                    }
                    Matcher taskNpcNameMatcher = DataSplitConstant.TASK_DECOMPOSITION_NAME.matcher(taskContent);
                    if (taskNpcNameMatcher.find()) {
                        String npcName = taskNpcNameMatcher.group();
                        taskBean.initNpcName(npcName);


                    }
                } else {

                }
            }
        } else {
            // 这个是去地图查找所有的NPC地址信息
            Matcher detailNpc = DataSplitConstant.MAP_NPC_TARGET_NPC_PATTERN.matcher(gbk);
            while (detailNpc.find()) {
                String npcDetail = detailNpc.group();
                System.out.println("地图查找所有的NPC地址信息：{}"+ gbk+ npcDetail);

            }
        }
    }
}
