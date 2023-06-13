package com.liao.chat.session;


import com.liao.chat.eneity.ChatUser;
import io.netty.channel.Channel;

import java.util.List;

/**
 * 用户聊天会话的接口
 */
public interface ChatSession {
    void addSession(ChatUser user, Channel channel);
    List<Channel> getSession(ChatUser user);
}
