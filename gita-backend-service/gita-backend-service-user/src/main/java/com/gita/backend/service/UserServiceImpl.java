package com.gita.backend.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.gita.backend.api.user.UserService;
import com.gita.backend.dao.UserMapper;
import com.gita.backend.dto.user.UserEnter;
import com.gita.backend.exceptions.BusinessException;
import com.gita.backend.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

@Service(registry = "dubbo-provider")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public void login(UserEnter enter) {
        System.out.println("login ...");
//        UserModel user = userMapper.selectUser(enter);
//        System.out.println(user);
        throw new BusinessException("1","自定义异常");
    }

    @Override
    public void logout() {
        System.out.println("logout ...");
    }
}
