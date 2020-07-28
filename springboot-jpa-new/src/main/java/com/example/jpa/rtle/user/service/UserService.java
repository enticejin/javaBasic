package com.example.jpa.rtle.user.service;

import com.example.jpa.rtle.user.model.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


public interface UserService {
    List<User> findAll();
    void delete(int id);
    User getOne(int id);
    void save(User user);

  /*
     * 分页
     * @param User
     * @param page
     * @param limit
     * @return
     */
    public List<User> list(User User, int page, int limit);
    /**
     * 获取总数
     * @param User
     * @return
     */
    public Long getCount(User User);
    /**
     * 按名称查询区域
     * @param UserName
     * @return
     */
    public List<User> getUserByName(String UserName);

}
