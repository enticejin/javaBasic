package com.example.springbootmybatis.controller;

import com.example.springbootmybatis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redis")
public class ReidsController {
    @Autowired
    private RedisService redisService;

    @RequestMapping("/redisString")
    public void redisString(){
        this.redisService.redisString();
    }

    @RequestMapping("/redisHash")
    public void redisHash(){
        this.redisService.redisHash();
    }

    @RequestMapping("/redisList")
    public void redisList(){
        this.redisService.redisList();
    }

    @RequestMapping("/redisSet")
    public void redisSet(){
        this.redisService.redisSet();
    }

    @RequestMapping("/redisSortedSort")
    public void redisSortedSort(){
        //有序的set，故而省略
    }
}
