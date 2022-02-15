package com.mhxh.proxyer.tcp.game.task.bean;

import com.mhxh.proxyer.fake.command.constants.SytemItemConstant;

public class QinglongTaskBean extends AbstractTaskBean {

    public QinglongTaskBean(String taskItemName) {
        initNpcName(taskItemName);
        // 找到物品坐标
        Integer[] integers = SytemItemConstant.itemMap.get(taskItemName);
        this.initNpcXY(integers[0], integers[1]);
        this.taskType = 1;
    }

}
