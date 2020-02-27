package com.community.life.config;

import com.community.life.service.HelloService.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration表明当前类是配置类，代替spring中的配置文件
 * 在配置文件中<bean></bean>标签添加组件
 */
@Configuration
public class MyAppConfig {
    //将方法的返回值添加到容器中，容器中这个组件默认的id就是方法名
    @Bean
    public HelloService helloService(){
        System.out.println("配置类@Bean给容器添加组件了...");
        return new HelloService();
    }
}
