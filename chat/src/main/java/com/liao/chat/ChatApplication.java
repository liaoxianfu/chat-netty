package com.liao.chat;

import com.liao.chat.im.server.NettyChatServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication {
    public static void main(String[] args) throws Exception {
        var context = SpringChatApplication.run(ChatApplication.class, NettyChatServer.class, args);
    }
}
