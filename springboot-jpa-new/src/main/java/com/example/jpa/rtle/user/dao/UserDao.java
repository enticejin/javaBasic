package com.example.jpa.rtle.user.dao;

import com.example.jpa.rtle.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer>, Serializable {

    Page<User> findAll(Specification<User> flag, Pageable pageable) ;

    List<User> findByNameIsLike(String userName);

    Long count(Specification<User> specification);
}
