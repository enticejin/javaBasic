package com.learn.atguigu.config;

import com.learn.atguigu.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author worker
 * @createTime 01 14:46
 * @description
 */
@Configuration
public class MyAppConfig {
    //将返回值添加到容器中
    @Bean
    public HelloService helloService(){
        System.out.println("@Bean给容器中添加组件了...");
        return new HelloService();
    }
}
