package com.example.springweb.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MyExceptionHandler
 * @Description TODO
 * @Date 2020/3/12 9:51
 * @Created by Administrator
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e, HttpServletRequest request){
        Map<String, Object> map = new HashMap(2);
        map.put("name", "hello");
        map.put("password", "123456");

        //设置状态码
        request.setAttribute("javax.servlet.error.status_code", 500);

        //把数据放在request域中
        request.setAttribute("ext", map);
        return "forward:/error";
    }
}
