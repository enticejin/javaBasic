package com.example.springbootjdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @Classname DruidConfiguration
 * @Description TODO
 * @Date 2020/3/12 14:57
 * @Created by Administrator
 */
@Configuration
public class DruidConfiguration {
    @ConditionalOnClass(DruidDataSource.class)
    @ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource",
                            matchIfMissing = true)
    static class Druid extends DruidConfiguration{
        @Bean
        @ConfigurationProperties("spring.datasource.druid")
        public DruidDataSource dataSource(DataSourceProperties properties){
            DruidDataSource druidDataSource = properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
            DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(properties.determineUrl());

            String validationQuery = databaseDriver.getValidationQuery();
            if(!StringUtils.isEmpty(validationQuery)){
                druidDataSource.setValidationQuery(validationQuery);
            }
            return druidDataSource;
        }
    }
}
