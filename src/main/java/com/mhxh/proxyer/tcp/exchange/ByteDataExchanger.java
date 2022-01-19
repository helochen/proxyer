package com.mhxh.proxyer.tcp.exchange;

import com.mhxh.proxyer.fake.command.base.BaseCommand;
import com.mhxh.proxyer.fake.command.base.IFormatCommand;
import com.mhxh.proxyer.fake.command.remote.refuse.IRefuseFilter;
import com.mhxh.proxyer.tcp.service.IDumpDataService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executor;

@Component
public class ByteDataExchanger {

    /**
     * 增加一个命令列表，每一个命令都是由一系列命令构成
     */
    private final Queue<Queue<BaseCommand>> tasks = new ConcurrentLinkedDeque<>();

    /**
     * 过滤部分服务器数据列表
     */
    private final Set<IRefuseFilter> filters = new ConcurrentSkipListSet<>();


    @Autowired
    @Qualifier("taskExecutor")
    @Getter
    private Executor taskExecutor;


    @Autowired
    @Getter
    private IDumpDataService dumpDataService;

    public static final int SERVER_OF_REMOTE = 1;
    public static final int SERVER_OF_LOCAL = 2;

    /**
     * 管理TCP链接的功能
     */
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

    //TODO 如何让获取命令进入等待时机

    /***
     * 拒绝操作
     */
    public boolean filterServerCommand(ByteBuf byteBuf) {
        Iterator<IRefuseFilter> iterator = filters.iterator();
        while (iterator.hasNext()) {
            boolean refuse = iterator.next().refuse(byteBuf);
            if (refuse) {
                return true;
            }
        }
        return false;
    }
}
