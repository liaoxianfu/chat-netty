package com.liao.chat;

import com.liao.chat.im.server.ChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringChatApplication extends SpringApplication {

    private final static Logger log = LoggerFactory.getLogger(SpringChatApplication.class);

    public static ConfigurableApplicationContext run(Class<?> primarySource, Class<? extends ChatServer> serverClass, String... args)
            throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(primarySource, args);
        ChatServer server = applicationContext.getBean(serverClass);
        try {
            log.info("正在启动");
            server.start();
        } catch (Exception e) {
            log.error("启动异常，异常信息为{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
        return applicationContext;
    }
}
