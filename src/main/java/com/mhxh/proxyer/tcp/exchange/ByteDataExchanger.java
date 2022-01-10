package com.mhxh.proxyer.tcp.exchange;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ByteDataExchanger {


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
