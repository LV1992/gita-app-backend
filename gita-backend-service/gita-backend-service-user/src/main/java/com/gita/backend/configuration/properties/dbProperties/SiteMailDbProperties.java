package com.gita.backend.configuration.properties.dbProperties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据源配置信息
 *
 * @author yihang.lv
 * @date 2018-07-26 16:00
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "sitemail.datasource")
public class SiteMailDbProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

}
