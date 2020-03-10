package com.example.in_de_up_sel.service;

import com.example.in_de_up_sel.dao.PersonDao;
import com.example.in_de_up_sel.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;
    /*
        Service层介于controller和dao之间作为服务层进行一些逻辑处理，
        这里逻辑太简单所以知识单纯调用dao所以不做注释
     */
    public List<Person> getAll(){
        return personDao.getAll();
    }

    public Person getPersonByID(int id){
        return personDao.getPersonByID(id);
    }

    public void  delete(int id){
        personDao.delete(id);
    }

    public void update(Person p){
        personDao.update(p);
    }

    public void newp(Person p){
        personDao.newp(p);
    }
}
