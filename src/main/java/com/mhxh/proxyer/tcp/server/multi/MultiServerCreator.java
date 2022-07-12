package com.mhxh.proxyer.tcp.server.multi;


import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import com.mhxh.proxyer.tcp.server.local.MhxyV2LocalTcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Component
public class MultiServerCreator {

    private static final Logger logger = LoggerFactory.getLogger(MultiServerCreator.class);

    @Autowired
    private ByteDataExchanger exchanger;

    @Value("${game.server.ip}")
    private String ip;

    @Value("#{'${game.server.multi.ports}'.split(',')}")
    private List<Integer> ports;

    @Value("${server.core}")
    private int core;

    public void runMultiServer() {
        for (Integer port : ports) {
            MhxyV2LocalTcpServer server = new MhxyV2LocalTcpServer(ip, port, core, exchanger);
            try {
                server.startAsync();
                server.awaitRunning();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (server.isRunning()) {
                        server.stopAsync();
                        server.awaitTerminated();
                    }
                }));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
    }

}
