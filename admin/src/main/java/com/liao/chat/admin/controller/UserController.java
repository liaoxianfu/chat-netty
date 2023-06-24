package com.liao.chat.admin.controller;


import com.liao.chat.admin.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    @GetMapping("/user")

    public List<UserVO> getUser() {
        log.debug("进入请求");
        UserVO demo1 = new UserVO("demo1", 11, "aaa.png");
        UserVO demo2 = new UserVO("demo2", 12, "bbb.png");
        UserVO demo3 = new UserVO("demo3", 13, "ccc.png");
        UserVO demo4 = new UserVO("demo4", 14, "ddd.png");
        List<UserVO> list = Arrays.asList(demo1, demo2, demo3, demo4);
        return list;
    }
}
