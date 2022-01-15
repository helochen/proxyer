package com.mhxh.proxyer.tcp.executor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class MyThreadPoolExecutorConfiguration {



    @Value("${bd.thread.pool.core:4}")
    private int core;

    @Value("${bd.thread.pool.maxQueue:1500}")
    private int maxQueue;

    @Value("${bd.thread.pool.pool:50}")
    private int pool;


    @Bean("taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置线程池参数
        taskExecutor.setQueueCapacity(maxQueue);
        taskExecutor.setCorePoolSize(core);
        taskExecutor.setMaxPoolSize(pool);
        taskExecutor.setKeepAliveSeconds(10);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        taskExecutor.initialize();

        return taskExecutor;
    }
}
