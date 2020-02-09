package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gejin
 * @createTime 2020/2/9-22:42
 * @description 用户验证
 */

/**
 *
 *
 * 以用户名 zs 登录（其角色权限为AAA），可以进入系统，浏览器输入地址可以访问， localhost:8080，localhost:8080/user/addUser，localhost:8080/user/deleteUser，localhost:8080/user/updateUser，localhost:8080/user/findAllUsers
 *
 * 以用户名 ls 登录（其角色权限为BBB），可以进入系统，浏览器输入地址可以访问， localhost:8080，localhost:8080/user/deleteUser，localhost:8080/user/updateUser，localhost:8080/user/findAllUsers
 *
 * 以用户名 ww 登录（其角色权限为CCC），可以进入系统，浏览器输入地址可以访问， localhost:8080，localhost:8080/user/deleteUser，localhost:8080/user/updateUser，localhost:8080/user/findAllUsers
 *
 * 以用户名 zl 登录（其角色权限为CCC），可以进入系统，浏览器输入地址可以访问， localhost:8080，localhost:8080/user/updateUser，localhost:8080/user/findAllUsers
 *
 * 以用户名 admin 登录，不可以进入系统，因为系统中还没有该用户。
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(){
        return "这是进行用户的添加";
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(){
        return "这是进行用户的删除操作";
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(){
        return "这是进行用户的修改操作";
    }

    @RequestMapping("/findAllUsers")
    @ResponseBody
    public String findAllUsers(){
        return "这是进行查询所有的用户";
    }
}
