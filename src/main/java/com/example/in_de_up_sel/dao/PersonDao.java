package com.example.in_de_up_sel.dao;

import com.example.in_de_up_sel.model.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonDao {
    /*
   查所有
   return List<Person>
    */
    List<Person> getAll();

    /*
    根据ID查询
    {id} 要查询人员的 id
     */
    Person getPersonByID(int id);

    /*
    删除
    {id} 要删除人员的 id
     */
    void delete(int id);

    /*
    更新
    {p} 要更新的Person实例
     */
    void update(Person p);

    /*
    增加
    {p} 要新增的Person实例
     */
    void newp(Person p);
}
