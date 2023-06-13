package com.liao.chat.session;

import com.liao.chat.eneity.ChatPlatform;
import com.liao.chat.eneity.ChatUser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 实现思路
 * 用户在不同的平台可以登录，但是同一平台不能多设备登录
 */


@Slf4j
public class LocalChatSession implements ChatSession {

    private final ConcurrentHashMap<String, Channel> sessionMap = new ConcurrentHashMap<>();

    private LocalChatSession() {
    }

    private static LocalChatSession localChatSession;

    public static LocalChatSession INSTANCE() {
        if (localChatSession == null) {
            localChatSession = new LocalChatSession();
        }
        return localChatSession;
    }

    @Override
    public void addSession(ChatUser user, Channel channel) {
        String userID = user.getUserID();
        String platform = user.getPlatform().name();
        String key = userID + "-" + platform;
        log.debug("加入的用户信息{}", key);
        if (sessionMap.containsKey(key)) {
            // 踢下线
            try {
                offlineOldSession(key);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // 加入新的session
        sessionMap.put(key, channel);
    }

    // 同平台相同用户登录，踢出原来的平台登录状态
    public void offlineOldSession(String key) throws InterruptedException {
        sessionMap.get(key).closeFuture().sync()
                .addListener((ChannelFutureListener) f -> {
                    if (f.isSuccess()) {
                        log.info("用户{} 同平台 被下线", key);
                    } else {
                        log.error("用户{} 同平台 下线失败", key);
                    }
                });
    }

    @Override
    public List<Channel> getSession(ChatUser user) {
        if (user.getUserID() == null) {
            log.error("userID is null");
            return null;
        }
        ArrayList<Channel> channels = new ArrayList<>();
        ChatPlatform.getPlatformList().forEach(p -> {
            String key = user.getUserID() + "-" + p;
            if (sessionMap.containsKey(key)) {
                channels.add(sessionMap.get(key));
            }
        });
        return channels;
    }
}
