package com.gita.backend.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**使用redisCluster 需要配置redis.config 文件 的cluster-enabled yes
 * @author yihang.lv 2018/9/19、16:14
 */
@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private String host;
    private int port;
}
