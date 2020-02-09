package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gejin
 * @createTime 2020/2/9-22:22
 * @description 验证security
 */
@RestController
public class TestSecurityController {
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String say(){
        return "index";
    }

}
