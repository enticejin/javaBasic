package com.example.springall.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller //这是一个控制器
@RequestMapping(path = "/demo") //在程序启动后的一个URL
public class MainController {

    @Autowired //获取UserRepository的bean
    //spring自动生成，我们可以使用它操作数据
    private UserRepository userRepository;

    @PostMapping("/add") //只会响应POST请求
    public @ResponseBody String addUser(@RequestParam String name,
                                        @RequestParam String email){
        //@ResponseBody 表示返回一个String而不是一个视图名
        //@RequestParam 表示获取GET或者POST请求的参数
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            userRepository.save(user);
            return "saved";
    }

    @GetMapping(path = "/all")//处理get请求
    public @ResponseBody Iterable<User> getAllUser(){
        //这将返回一个json或者xml类型的数据
        return userRepository.findAll();
    }
}
