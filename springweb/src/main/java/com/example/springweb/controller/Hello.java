package com.example.springweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname Hello
 * @Description TODO
 * @Date 2020/3/12 10:42
 * @Created by Administrator
 */
@RestController
public class Hello {

    @RequestMapping("/hello")
    public String hello(String str){
        if("hi".equals(str)){
            int i = 10 / 0;
        }
        return "hello,world";
    }
}
