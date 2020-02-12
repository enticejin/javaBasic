/*
 *用户：少爷辉
 *邮箱：www.403367632@qq.com
 *时间：2019/10/13 18:10
 *作者：葛进进
 */
package com.example.springbootmybatis.mapper;
/*
 *用户：少爷辉
 *邮箱：www.403367632@qq.com
 *时间：2019/10/13 18:10
 *作者：葛进进
 */

import com.example.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface UserMapper  {

    List<User> hello()throws DataAccessException;

}
