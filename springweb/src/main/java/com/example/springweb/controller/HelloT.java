package com.example.springweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname HelloT
 * @Description TODO
 * @Date 2020/3/12 10:45
 * @Created by Administrator
 */
@Controller
public class HelloT {

    @RequestMapping("/ht")
    public String ht(Model model){
        model.addAttribute("title","hello Thymeleaf").addAttribute("info","this is first thymeleaf test");
        return "t1";
    }
}
