package com.liao.chat.im.server;


/**
 * 聊天服务的接口
 */
public interface ChatServer {

    /**
     * 开启聊天服务
     * @throws Exception 抛出异常
     */
    void start() throws Exception;


    /**
     * 关闭服务
     * @throws Exception 抛出异常
     */
    void stop() throws  Exception;
}
