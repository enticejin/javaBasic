package com.example.jpa.rtle.user.service.impl;

import com.example.jpa.rtle.user.dao.UserDao;
import com.example.jpa.rtle.user.model.User;
import com.example.jpa.rtle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void delete(int id) {
        userDao.deleteById(id);
    }

    @Override
    public User getOne(int id) {
        return userDao.getOne(id);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }


    @Override
    public List<User> list(User User, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1,pageSize, Sort.Direction.DESC,"id");
        Page<User> pageUserFile = userDao.findAll(new Specification<User>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                javax.persistence.criteria.Predicate predicate=cb.conjunction();
                predicate.getExpressions().add(cb.equal(root.get("flag"), 0));
                return predicate;
            }
        }, pageable);
        return pageUserFile.getContent();
    }

    @Override
    public Long getCount(User user) {
        return userDao.count(new Specification<User>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                javax.persistence.criteria.Predicate predicate = cb.conjunction();
                predicate.getExpressions().add(cb.equal(root.get("flag"), 0));
                return predicate;
            }
        });
    }

    @Override
    public List<User> getUserByName(String userName) {
        return userDao.findByNameIsLike(userName);
    }
}
