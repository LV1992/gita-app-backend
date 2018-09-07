package com.gita.backend.configuartion.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**redis 配置
 * @author yihang.lv 2018/9/7、17:09
 */
@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    /**
     * 代表连接地址
     */
    private String redisAddress;
    private Integer redisMaxTotal;
    private Integer redisMaxIdle;
    private Integer redisMinIdle;
    private Integer redisTimeout;
    private Integer redisMaxAttempts;
}
