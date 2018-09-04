package com.gita.backend.configuration.dbProperties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据源配置信息
 *
 * @author xuziwen
 * @date 2017-11-29 16:00
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "druid.datasource")
public class CRMDbProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int initialSize;
    private int maxActive;
    private int minIdle;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private String validationQuery;
    private boolean testWhileIdle;

}
