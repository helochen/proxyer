package com.mhxh.proxyer;

import com.mhxh.proxyer.tcp.server.local.MhxyLocalTcpProxyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProxyerApplication {

    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(ProxyerApplication.class, args);

        MhxyLocalTcpProxyServer server = context.getBean(MhxyLocalTcpProxyServer.class);

        // 初始化
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
            ex.printStackTrace();
        }
    }

}
