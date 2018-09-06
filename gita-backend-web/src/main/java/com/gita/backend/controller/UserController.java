package com.gita.backend.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gita.backend.api.user.UserService;
import com.gita.backend.context.SessionContext;
import com.gita.backend.dto.common.Response;
import com.gita.backend.dto.entity.LoginSession;
import com.gita.backend.dto.user.UserEnter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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


    /**
     * app二维码登录地址，这里为了测试才传{user},实际项目中user是通过其他方式传值
     *
     * @param sessionKey
     * @return
     */
    @ResponseBody
    @GetMapping("qrUrl/{sessionKey}")
    public Map<String, Object> setUser(@PathVariable String sessionKey) {
        LoginSession session = SessionContext.getSession();
        if (session.getSessionKey().equals(sessionKey)) {

            // 赋值登录用户
            LoginSession loginSession = session;

            // 唤醒登录等待线程
            loginSession.latch.countDown();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("sessionKey", sessionKey);
        return result;
    }

    /**
     * 等待二维码扫码结果的长连接
     *
     * @param sessionKey
     * @return
     */
    @GetMapping("login/getResponse/{sessionKey}")
    @ResponseBody
    public Response getResponse(@PathVariable String sessionKey) {
        Response result = Response.ok();
        Map<String,Object> r = new HashMap<>();
        LoginSession session = SessionContext.getSession();
        r.put("session",session);
        try {
            LoginSession loginSession = null;
            //没有登录
            if (!session.getSessionKey().equals(sessionKey)) {
                loginSession = new LoginSession();
                SessionContext.setSession(loginSession);
            } else {
                loginSession = session;
            }
            // 第一次判断
            // 判断是否登录,如果已登录则写入session
            if (session.getSessionKey() != null) {
                loginSession = new LoginSession();
                SessionContext.setSession(loginSession);
                return result;
            }

            if (loginSession.latch == null) {
                //设置信号量为1，当时使用latch..countDown(); 时，信号量变为0，线程唤醒
                loginSession.latch = new CountDownLatch(1);
            }
            try {
                // 线程等待
                loginSession.latch.await(5, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } finally {
            // 移除登录请求
            if (session.getSessionKey().equals(sessionKey))
                SessionContext.clear();
        }
    }


}
