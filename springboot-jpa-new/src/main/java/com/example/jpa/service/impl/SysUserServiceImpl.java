package com.example.jpa.service.impl;

import com.example.jpa.dao.SysUserDao;
import com.example.jpa.model.SysUser;
import com.example.jpa.service.SysUserService;
import com.example.jpa.util.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Override
    public void save(SysUser user) {
        sysUserDao.save(user);
    }

    @Override
    public void delete(SysUser user) {
        sysUserDao.delete(user);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserDao.findAll();
    }

    @Override
    public Object findPage(PageQuery pageQuery) {
        return sysUserDao.findAll(PageRequest.of(pageQuery.getPage(),pageQuery.getSize()));
    }
}
