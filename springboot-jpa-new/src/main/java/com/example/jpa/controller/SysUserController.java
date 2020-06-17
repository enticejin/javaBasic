package com.example.jpa.controller;

import com.example.jpa.model.SysUser;
import com.example.jpa.service.SysUserService;
import com.example.jpa.util.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping(value = "/save")
    public Object save(@RequestBody SysUser user){
        sysUserService.save(user);
        return  1;
    }
    @PostMapping(value = "/delete")
    public Object delete(@RequestBody SysUser user){
        sysUserService.delete(user);
        return 1;
    }
    @GetMapping(value = "/findAll")
    public Object findAll(){
        return sysUserService.findAll();
    }
    @PostMapping(value = "/findPage")
    public Object findPage(@RequestBody PageQuery pageQuery){
        return sysUserService.findPage(pageQuery);
    }
}
