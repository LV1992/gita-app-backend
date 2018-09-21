package com.gita.backend.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gita.backend.api.user.UserService;
import com.gita.backend.configuartion.Receiver;
import com.gita.backend.context.SessionContext;
import com.gita.backend.dto.common.Response;
import com.gita.backend.dto.entity.LoginSession;
import com.gita.backend.dto.user.UserEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * ${yihang.lv}、2018/6/28、15:21
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Reference(registry = "dubbo-consumer")
    private UserService userService;

    @Autowired
    private Receiver receiver;

    @Autowired
    private Jedis jedis;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response logoin(UserEnter enter){return userService.login(enter);}

    @RequestMapping(value = "logout/{sessionKey}",method = RequestMethod.POST)
    public void logout(@PathVariable String sessionKey){userService.logout(sessionKey);}


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
        String userSessionKey = session.getSessionKey();
        String value = jedis.get(userSessionKey);
        if (value != null) {
            // 保存认证信息
            jedis.setex(userSessionKey, 1 ,session.getMobile());

            // 发布登录广播消息，使用Receiver receiveLogin 监听
            jedis.publish(Receiver.TOPIC_NAME, sessionKey);
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
    public Callable<Map<String, Object>> getResponse(@PathVariable String sessionKey) {
        // 非阻塞
        Callable<Map<String, Object>> callable = () -> {

            Map<String, Object> result = new HashMap<>();
            result.put("loginId", sessionKey);

            try {
                String user = jedis.get( sessionKey);
                // 长时间不扫码，二维码失效。需重新获二维码
                if (user == null) {
                    result.put("success", false);
                    result.put("stats", "refresh");
                    return result;
                }

                // 已登录
                if (!user.equals(sessionKey)) {
                    // 登录成,认证信息写入session
//                    session.setAttribute(WebSecurityConfig.SESSION_KEY, user);
                    result.put("success", true);
                    result.put("stats", "ok");
                    return result;
                }

                // 等待二维码被扫
                try {
                    // 线程等待30秒
                    receiver.getLoginLatch(sessionKey).await(30, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                result.put("success", false);
                result.put("stats", "waiting");
                return result;

            } finally {
                // 移除登录请求
                receiver.removeLoginLatch(sessionKey);
            }
        };

        return callable;
    }


}
