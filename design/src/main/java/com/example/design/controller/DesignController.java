package com.example.design.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class DesignController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String index(){
        return "index";
    }
}
