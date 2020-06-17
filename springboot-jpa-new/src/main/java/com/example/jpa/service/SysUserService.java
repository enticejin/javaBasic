package com.example.jpa.service;

import com.example.jpa.model.SysUser;
import com.example.jpa.util.PageQuery;

import java.util.List;

public interface SysUserService {
    //保存
    public void save(SysUser user);
    //删除
    public void delete(SysUser user);
    //查询所有
    public List<SysUser> findAll();
    //分页查询
    public Object findPage(PageQuery pageQuery);
}
