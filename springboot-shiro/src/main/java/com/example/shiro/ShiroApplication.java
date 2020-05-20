package com.example.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShiroApplication {
    /*
    http://127.0.0.1:8080/login?userName=zhangsan&password=123456
    http://127.0.0.1:8080/index
     */
    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class, args);
    }

}
