package com.example.springweb;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Classname ServletInitializer
 * @Description TODO
 * @Date 2020/3/12 10:34
 * @Created by Administrator
 */
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringwebApplication.class);
    }
}
