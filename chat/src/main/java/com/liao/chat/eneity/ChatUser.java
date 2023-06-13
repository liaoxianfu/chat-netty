package com.liao.chat.eneity;

import lombok.Data;

@Data

// 平台
public class ChatUser {
    // 用户的唯一标识符
    private String userID;

    private String userName;

    // 头像
    private String avatar;

    private ChatPlatform platform;

}
