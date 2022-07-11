package com.mhxh.proxyer.tcp.server.multi;


import com.mhxh.proxyer.tcp.exchange.ByteDataExchanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Component
public class MultiServerCreator {



    @Autowired
    private ByteDataExchanger exchanger;

    @Value("${game.server.ip}")
    private String ip;

    @Value("#{'${game.server.multi.ports}'.split(',')}")
    private List<Integer> ports;


    public void runMultiServer() {
        for (Integer port : ports) {

        }
    }

}
