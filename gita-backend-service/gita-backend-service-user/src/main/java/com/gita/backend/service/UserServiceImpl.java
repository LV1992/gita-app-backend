package com.gita.backend.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.gita.backend.api.user.UserService;
@Service(registry = "${dubbo.registry.id}")
public class UserServiceImpl implements UserService{
    @Override
    public void login() {
        System.out.println("login ...");
    }

    @Override
    public void logout() {
        System.out.println("logout ...");
    }
}
