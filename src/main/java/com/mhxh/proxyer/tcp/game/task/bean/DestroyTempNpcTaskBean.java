package com.mhxh.proxyer.tcp.game.task.bean;


import com.mhxh.proxyer.tcp.game.task.ITaskBean;


/**
 * 抓鬼或者江湖任务应该都是这个任务对象
 */
public class DestroyTempNpcTaskBean extends AbstractTaskBean implements ITaskBean {

    public DestroyTempNpcTaskBean() {
        super();
        this.taskType = 2;
    }

    public DestroyTempNpcTaskBean(int type) {
        super();
        this.taskType = type;
    }
}
