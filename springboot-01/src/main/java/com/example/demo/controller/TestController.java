package com.example.demo.controller;


import com.example.demo.model.Girl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author gejin
 * @createTime 2020/2/9-21:19
 * @description 测试springboot
 */
@RestController
public class TestController {
    //使用${}来获取配置文件的值
    @Value("${size}")
    private String size;
    @Value("${age}")
    private int age;
    @Value("${content}")
    private String content;
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String say(){
        return "配置文件中的size是： "+size+",配置文件中的age是： "+age+", 配置文件中的content的值是： "+content;
    }

    @Autowired
    private Girl girl;
    @RequestMapping(value = "/girl",method = RequestMethod.GET)
    public String girl(){
        return new JSONObject(girl).toString();
    }
}
