package com.mhxh.proxyer.tcp.game.task;

import com.mhxh.proxyer.tcp.game.constants.TaskConstants;
import com.mhxh.proxyer.tcp.game.task.bean.DestroyTempNpcTaskBean;

public class TaskBeanCreateFactory {


    /**
     * 创建一个任务BEAN
     * @param type
     * @param content
     * @return
     */
    public static ITaskBean createTaskBean(String type, String content) {
        ITaskBean bean = null;
        switch (type) {
            case TaskConstants.TASK_CATCH_GHOST:
            case TaskConstants.TASK_JIAN_HU:
                bean = new DestroyTempNpcTaskBean();
                default:
        }
        return bean;
    }
}
