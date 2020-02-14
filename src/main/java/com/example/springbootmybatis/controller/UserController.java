/*
 *用户：少爷辉
 *邮箱：www.403367632@qq.com
 *时间：2019/10/13 18:14
 *作者：葛进进
 */
package com.example.springbootmybatis.controller;/*
 *用户：少爷辉
 *邮箱：www.403367632@qq.com
 *时间：2019/10/13 18:14
 *作者：葛进进
 */

import com.example.springbootmybatis.entity.User;
import com.example.springbootmybatis.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public List<User> hello(){
        List<User> hello = null;
        try {
            hello = userService.hello();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(hello);
        return hello;
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(){
        return "这是添加用户！！";
    }
    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(){
        return "这是删除用户！！";
    }
    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(){
        return "这是更新用户！！";
    }
    @RequestMapping("/findAllUsers")
    @ResponseBody
    public String findAllUsers(){
        return "这是搜索所有用户！！";
    }
}
