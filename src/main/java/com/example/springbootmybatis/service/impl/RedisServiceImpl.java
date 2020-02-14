package com.example.springbootmybatis.service.impl;

import com.example.springbootmybatis.service.RedisService;
import com.example.springbootmybatis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private RedisUtil redisUtil;
    @Override
    public void redisString() {
        //添加值
        redisUtil.stringSet("123456","这是一个String类型");
        //取值
        Object value = redisUtil.StringGet("123456");
        System.out.println(value);
    }

    @Override
    public void redisHash() {
        //添加值
        redisUtil.hashSet("BBB","test-01","原hash值1");
        redisUtil.hashSet("BBB","test-02","新hash值1");
        redisUtil.hashSet("BBB","test-01","原hash值2");
        redisUtil.hashSet("BBB","test-02","新hash值2");
        //取值
        Object value1 = redisUtil.hashGet("BBB","test-01");
        Object value2 = redisUtil.hashGet("BBB","test-02");
        System.out.println(value1);
        System.out.println(value2);
    }

    @Override
    public void redisSet() {
        //添加值
        redisUtil.setSet("CCC","这是SET集合的第一个");
        redisUtil.setSet("CCC","这是SET集合的第二个");
        redisUtil.setSet("CCC","这是SET集合的第三个");
        //取值
        Set value = redisUtil.setGet("CCC");
        System.out.println(value);
    }

    @Override
    public void redisList() {
        //添加值
        redisUtil.listSet("DDD", "这是一组List集合的第一个");
        redisUtil.listSet("DDD", "这是一组List集合的第二个");
        redisUtil.listSet("DDD", "这是一组List集合的第三个");
        //取值
        List list = redisUtil.listGet("DDD", 0 , -1);
        System.out.println(list);
    }

    @Override
    public void redisSortedSort() {
        //有序的set，故而省略
    }
}
