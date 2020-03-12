package com.example.springweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2020/3/12 10:28
 * @Created by Administrator
 */
@Controller
public class UserController {
    @PostMapping("/user/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){

        if(!StringUtils.isEmpty(username) && password.equals("123456")){
            //登录成功，把用户信息放在session中，防止表单重复提交，重定向到后台页面
            session.setAttribute("loginUser",username);
            return "redirect:/main.html";
        }
        //登录失败返回登录页面
        model.addAttribute("msg","用户名或密码错误");
        return "login";
    }

    @GetMapping("/login")
    public String loginIn(){
        return "login";
    }
}
