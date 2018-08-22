package com.gita.backend.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.gita.backend.configuration.dbProperties.XqtravelRWDbProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private XqtravelRWDbProperties xqtravelRWDbProperties;

    @Primary
    @Bean(name = "xqtravelRWDataSource")
    public DataSource xqtravelRWDataSourceDruid() {
        log.info("-------------------- xqtravelRWDataSource init ---------------------");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(xqtravelRWDbProperties.getDriverClassName());
        dataSource.setUrl(xqtravelRWDbProperties.getUrl());
        dataSource.setUsername(xqtravelRWDbProperties.getUsername());
        dataSource.setPassword(xqtravelRWDbProperties.getPassword());
        dataSource.setInitialSize(xqtravelRWDbProperties.getInitialSize());
        dataSource.setMaxActive(xqtravelRWDbProperties.getMaxActive());
        dataSource.setMinIdle(xqtravelRWDbProperties.getMinIdle());
        dataSource.setMaxWait(xqtravelRWDbProperties.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(xqtravelRWDbProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setValidationQuery(xqtravelRWDbProperties.getValidationQuery());
        dataSource.setTestWhileIdle(xqtravelRWDbProperties.isTestWhileIdle());
        return dataSource;
    }

    @Bean(name = "xqdbTransactionManager")
    @Primary
    public PlatformTransactionManager xqdbTransactionManager(@Qualifier("xqtravelRWDataSource")DataSource xqtravelRWDataSource){
        return new DataSourceTransactionManager(xqtravelRWDataSource);
    }
}
