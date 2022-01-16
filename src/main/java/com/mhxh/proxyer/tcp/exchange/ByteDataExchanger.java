package com.mhxh.proxyer.tcp.exchange;

import com.mhxh.proxyer.fake.command.BaseCommand;
import com.mhxh.proxyer.fake.command.IFormatCommand;
import com.mhxh.proxyer.tcp.service.IDumpDataService;
import io.netty.channel.Channel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;

@Component
public class ByteDataExchanger {

    /**
     * 增加一个命令列表，每一个命令都是由一系列命令构成
     */
    private final Queue<Queue<BaseCommand>> tasks = new ConcurrentLinkedDeque<>();

    @Autowired
    @Qualifier("taskExecutor")
    @Getter
    private Executor taskExecutor;


    @Autowired
    @Getter
    private IDumpDataService dumpDataService;

    public static final int SERVER_OF_REMOTE = 1;
    public static final int SERVER_OF_LOCAL = 2;

    private final Map<Channel, Channel> localFastQuery = new ConcurrentHashMap<>();
    private final Map<Channel, Channel> remoteFastQuery = new ConcurrentHashMap<>();


    public void register(Channel local, Channel remote) {
        localFastQuery.put(local, remote);
        remoteFastQuery.put(remote, local);
    }

    public Channel getLocalByRemote(Channel remote) {
        return remoteFastQuery.get(remote);
    }

    public Channel getRemoteByLocal(Channel local) {
        return localFastQuery.get(local);
    }

    public Channel clearByLocal(Channel local) {
        Channel remote = localFastQuery.get(local);
        if (remote != null) {
            remoteFastQuery.remove(remote);
        }
        return remote;
    }


    /***
     * 增加一个命令链子
     * @param taskQueue
     */
    public void addFakeCommand(Queue<BaseCommand> taskQueue) {
        tasks.offer(taskQueue);
        synchronized (this) {
            if (CollectionUtils.isEmpty(currentCommandQueue)) {
                currentCommandQueue = tasks.poll();
            }
        }
    }

    private Queue<BaseCommand> currentCommandQueue;

    /**
     * 获取当前需要转换的命令
     *
     * @return
     */
    public synchronized IFormatCommand getOneCommand() {
        if (!ObjectUtils.isEmpty(currentCommandQueue)) {
            BaseCommand current = currentCommandQueue.poll();
            if (CollectionUtils.isEmpty(currentCommandQueue)) {
                currentCommandQueue = tasks.poll();
            }
            return current;
        }
        return null;
    }
}
