package com.example.springlearn.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

@Controller
public class TestController {

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "index";
    }
}
