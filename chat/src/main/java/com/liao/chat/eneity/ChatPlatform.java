package com.liao.chat.eneity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public enum ChatPlatform {
    PC, Phone, PAD;

    public static List<String> getPlatformList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(ChatPlatform.PC.name());
        list.add(ChatPlatform.PAD.name());
        list.add(ChatPlatform.Phone.name());
        return list;
    }
}
