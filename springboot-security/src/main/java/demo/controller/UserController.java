package demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

//@Api：用在类上，说明该类的作用。
//@ApiOperation：说明该方法的作用。
@Controller
@RequestMapping("/user")
@Api(value = "用户模块说明")
public class UserController {

    @RequestMapping("/addUser")
    @ResponseBody
    @ApiOperation(value = "添加用户",notes = "放一些信息，供测试判断")
    public String addUser(){
        return "这是进行用户的添加";
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    @ApiOperation(value = "删除用户",notes = "放一些信息，供测试判断")
    public String deleteUser(){
        return "这是进行用户的删除操作";
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    @ApiOperation(value = "更改用户操作",notes = "放一些信息，供测试判断")
    public String updateUser(){
        return "这是进行用户的修改操作";
    }

    @RequestMapping("/findAllUsers")
    @ResponseBody
    @ApiOperation(value = "查询所有用户",notes = "放一些信息，供测试判断")
    public String findAllUsers(){
        return "这是进行查询所有的用户";
    }
}
