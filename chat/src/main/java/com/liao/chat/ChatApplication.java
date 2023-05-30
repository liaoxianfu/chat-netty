package com.liao.chat;

import com.liao.chat.im.server.NettyChatServer;

public class ChatApplication {
    public static void main(String[] args) {
        NettyChatServer chatServer = new NettyChatServer();
        try {
            chatServer.start();
        } catch (Exception e) {
            try {
                chatServer.stop();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
