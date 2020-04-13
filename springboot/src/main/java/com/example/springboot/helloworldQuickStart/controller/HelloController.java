package com.example.springboot.helloworldQuickStart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname HelloController
 * @Description TODO
 * @Date 2020/3/12 9:16
 * @Created by Administrator
 */
//RestController 是ResponseBody和Controller的缩写
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello world!!!";
    }

}
