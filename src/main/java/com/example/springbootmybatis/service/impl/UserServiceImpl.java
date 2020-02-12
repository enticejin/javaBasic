/*
 *用户：少爷辉
 *邮箱：www.403367632@qq.com
 *时间：2019/10/13 18:08
 *作者：葛进进
 */
package com.example.springbootmybatis.service.impl;/*
 *用户：少爷辉
 *邮箱：www.403367632@qq.com
 *时间：2019/10/13 18:08
 *作者：葛进进
 */

import com.example.springbootmybatis.mapper.UserMapper;
import com.example.springbootmybatis.entity.User;
import com.example.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> hello() throws Exception {
        return userMapper.hello();
    }
}
