package com.atguigu.springboot.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author worker
 * @createTime 03 21:24
 * @description
 */
//实现WebMvcConfigurer接口来扩展springmvc的功能
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送/atguigu请求，来到success
        registry.addViewController("/atguigu").setViewName("success");
    }
}
