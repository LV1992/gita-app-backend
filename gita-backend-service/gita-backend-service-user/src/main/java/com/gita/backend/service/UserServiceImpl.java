package com.gita.backend.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.gita.backend.api.user.UserService;
import com.gita.backend.context.SessionContext;
import com.gita.backend.dao.UserMapper;
import com.gita.backend.dto.common.Response;
import com.gita.backend.dto.entity.LoginSession;
import com.gita.backend.dto.user.UserEnter;
import com.gita.backend.exceptions.BusinessException;
import com.gita.backend.model.UserModel;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@Service(registry = "dubbo-provider")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Jedis jedis;

    private static final String LOGIN_SESSION_KEY = "LOGIN_SESSION_KEY:";
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
        //存用户session
        LoginSession session = new LoginSession();
        session.setId(user.getId());
        String mobile = user.getMobile();
        if(!Strings.isNullOrEmpty(mobile)){
            mobile = mobile.substring(0,3) + "*****" + mobile.substring(mobile.length() - 3 , mobile.length());
        }
        session.setMobile(mobile);
        String sessionKey = "PC:" + user.getId() + UUID.randomUUID();
        session.setSessionKey(sessionKey);
        SessionContext.setSession(session);
        //绑定sessionKey和userId
        jedis.set(sessionKey,""+user.getId());
        //缓存（更新）userId和sessionKey
        jedis.set(LOGIN_SESSION_KEY+user.getId(),sessionKey);
        return new Response(session);
    }

    @Override
    public void logout(String sessionKey) {
        //清空所有session
        SessionContext.clear();
        String userId = jedis.get(sessionKey);
        if(!Strings.isNullOrEmpty(userId)){
            //删除缓存userId和sessionKey
            jedis.del(LOGIN_SESSION_KEY+userId);
            //删除sessionKey
            jedis.del(sessionKey);
        }
    }
}
