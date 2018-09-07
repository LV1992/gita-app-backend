package com.gita.backend.configuartion;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**存储登录状态和接收广播的类：
 * @author yihang.lv 2018/9/7、16:11
 */
public class Receiver {
    public static final String TOPIC_NAME = "login";
    @Autowired
    JedisCluster jedisCluster;
    /**
     * 存储登录状态
     */
    private Map<String, CountDownLatch> loginMap = new ConcurrentHashMap<>();

    /**
     * 接收登录广播
     *
     * @param loginId
     */
    public void receiveLogin(String loginId) {

        if (loginMap.containsKey(loginId)) {
            CountDownLatch latch = loginMap.get(loginId);
            if (latch != null) {
                // 唤醒登录等待线程
                latch.countDown();
            }
        }
    }

    public void receive(String loginId){
        jedisCluster.subscribe(new JedisPubSub(){
            @Override
            public void onMessage(String channel, String message) {
                if (loginMap.containsKey(message)) {
                    CountDownLatch latch = loginMap.get(message);
                    if (latch != null) {
                        // 唤醒登录等待线程
                        latch.countDown();
                    }
                }
            }
        },new String[]{TOPIC_NAME});
    }

    public CountDownLatch getLoginLatch(String loginId) {
        CountDownLatch latch = null;
        if (!loginMap.containsKey(loginId)) {
            latch = new CountDownLatch(1);
            loginMap.put(loginId, latch);
        } else
            latch = loginMap.get(loginId);

        return latch;
    }

    public void removeLoginLatch(String loginId) {
        if (loginMap.containsKey(loginId)) {
            loginMap.remove(loginId);
        }
    }
}
