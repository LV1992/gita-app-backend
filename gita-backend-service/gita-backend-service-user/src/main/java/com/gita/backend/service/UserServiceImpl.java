package com.gita.backend.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.gita.backend.api.user.UserService;
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService{
    @Override
    public void logout() {

    }
}
