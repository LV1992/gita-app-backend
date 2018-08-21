package com.gita.backend.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author yihang.lv 2018/8/21、11:12
 */
@Slf4j
@Configuration
public class DataSourceConfig {
    @Bean(name = "xqdbDataSource")
    @Primary
    @ConfigurationProperties(prefix = "xqdb-datasource")
    public DataSource xqdbDataSource(){
        log.info("xqdb datasource init...");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "xqdbTransactionManager")
    @Primary
    public PlatformTransactionManager xqdbTransactionManager(@Qualifier("xqdbDataSource")DataSource xqdbDataSource){
        return new DataSourceTransactionManager(xqdbDataSource);
    }
}
