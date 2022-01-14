package com.mhxh.proxyer.tcp.exchange;

import com.mhxh.proxyer.tcp.service.IDumpDataService;
import io.netty.channel.Channel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@Component
public class ByteDataExchanger {


    public static final Map<String, String[]> NextPosition = new ConcurrentHashMap<>();

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

}
