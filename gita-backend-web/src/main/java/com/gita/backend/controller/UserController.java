package com.gita.backend.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gita.backend.api.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${yihang.lv}、2018/6/28、15:21
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Reference(registry = "dubbo-consumer")
    private UserService userService;

    @RequestMapping("logout")
    public void logout(){userService.logout();}


}
