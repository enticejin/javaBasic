package com.example.springbootmybatis.controller.hello;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String hello(HttpServletRequest request, @RequestParam(value = "name",defaultValue = "springboot") String name){
        request.setAttribute("name",name);
        return "hello";
    }
}
