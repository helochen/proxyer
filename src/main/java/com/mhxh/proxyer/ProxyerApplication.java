package com.mhxh.proxyer;

import com.mhxh.proxyer.tcp.server.local.MhxyLocalTcpProxyServer;
import com.mhxh.proxyer.tcp.server.multi.MultiServerCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProxyerApplication {

    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(ProxyerApplication.class, args);

        MultiServerCreator serverCreator = context.getBean(MultiServerCreator.class);

        // 初始化
        serverCreator.runMultiServer();
    }

}
