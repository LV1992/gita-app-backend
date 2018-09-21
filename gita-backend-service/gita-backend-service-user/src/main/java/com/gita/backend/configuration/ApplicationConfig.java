package com.gita.backend.configuration;

import com.gita.backend.configuration.properties.RedisProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**redis 配置
 * @author yihang.lv 2018/9/19、15:38
 */
@Data
@Configuration
public class ApplicationConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public Jedis initJedis(){
        Jedis jedis = new Jedis(redisProperties.getHost(),
                redisProperties.getPort());
        return jedis;
    }

    @Bean
    public MyEndpointConfigure getConfig(){
        return new MyEndpointConfigure();
    }
}
