package com.gita.backend.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gita.backend.api.user.UserService;
import com.gita.backend.dto.common.Response;
import com.gita.backend.dto.user.UserEnter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${yihang.lv}、2018/6/28、15:21
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Reference(registry = "dubbo-consumer")
    private UserService userService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response logoin(UserEnter enter){return userService.login(enter);}

    @RequestMapping("logout")
    public void logout(){userService.logout();}


}
