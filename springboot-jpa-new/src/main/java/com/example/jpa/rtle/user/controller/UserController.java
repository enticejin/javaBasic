package com.example.jpa.rtle.user.controller;

import com.example.jpa.rtle.user.model.User;
import com.example.jpa.rtle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/userList")
    public String userList(Model model){
        List<User> userList = userService.findAll();
        for(User user: userList){
            System.out.println("userName = "+user.getName());
        }
        model.addAttribute("userList", userService.findAll());
        return  "user/user_list";
    }
}
