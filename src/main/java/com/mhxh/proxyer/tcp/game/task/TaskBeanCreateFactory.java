package com.mhxh.proxyer.tcp.game.task;

import com.mhxh.proxyer.tcp.game.constants.TaskConstants;
import com.mhxh.proxyer.tcp.game.task.bean.DestroyTempNpcTaskBean;
import com.mhxh.proxyer.tcp.game.task.bean.QinglongTaskBean;

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
            case TaskConstants.TASK_JIANG_HU:
                bean = new DestroyTempNpcTaskBean();
                break;
            case TaskConstants.TASK_QING_LONG:
                bean = new QinglongTaskBean(content);
                break;
                default:
        }
        return bean;
    }
}
