package com.gita.backend.configuration.properties.dbProperties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: yihang.lv
 * @Date: 2018-07-26 16:00
 * @Description: 读写库
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "xqdb.datasource")
public class XqtravelRWDbProperties {

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
