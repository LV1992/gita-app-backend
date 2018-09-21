package com.gita.backend.configuartion;

import com.gita.backend.configuartion.properties.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.Jedis;

/**
 * @Auther: yihang.lv
 * @Date: 2018/9/26 14:26
 * @Description: redis 配置
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisConfig;

    @Bean
    public Jedis initJedis() {
        String host = redisConfig.getHost();
        int port = redisConfig.getPort();
        Jedis jedis = new Jedis(host,port);
        return jedis;
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 订阅登录消息
        container.addMessageListener(listenerAdapter, new PatternTopic(Receiver.TOPIC_NAME));
        return container;
    }

    /**
     * 监听Receiver 调用 receive方法
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver) {
        // 方法名
        String methodName = "receive";
        return new MessageListenerAdapter(receiver, methodName);
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

}
