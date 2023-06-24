package com.liao.chat.admin;

import com.liao.chat.admin.entity.SysMenu;
import com.liao.chat.admin.mapper.SysMenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ChatTest {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Test
    void test01() {
        SysMenu oneByIdSysMenu = sysMenuMapper.findOneByIdSysMenu(1);
        System.out.println(oneByIdSysMenu);
    }
}
