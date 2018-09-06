package com.gita.backend.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.gita.backend.api.user.UserService;
import com.gita.backend.dao.UserMapper;
import com.gita.backend.dto.common.Response;
import com.gita.backend.dto.user.UserEnter;
import com.gita.backend.exceptions.BusinessException;
import com.gita.backend.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

@Service(registry = "dubbo-provider")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public Response login(UserEnter enter) {
        int userCount = userMapper.selectUserByName(enter);
        if(userCount == 0){
            throw new BusinessException("001","用户不存在");
        }
        UserModel user = userMapper.selectUser(enter);
        if(null == user){
            throw new BusinessException("002","密码错误");
        }
        return Response.ok();
    }

    @Override
    public void logout() {
        System.out.println("logout ...");
    }
}
