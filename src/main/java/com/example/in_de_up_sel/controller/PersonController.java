package com.example.in_de_up_sel.controller;

import com.example.in_de_up_sel.model.Person;
import com.example.in_de_up_sel.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    // 设置访问路由值为路径
    @RequestMapping("/")
    public ModelAndView index(){
        // 顾名思义 实体和数据 同时返回页面模板和数据
        ModelAndView mav = new ModelAndView("index");
        List<Person> list = personService.getAll();
        mav.addObject("list",list);
        return mav;
    }
    // 设置访问路由值为路径
    @RequestMapping("/copy")
    public ModelAndView copy(int id){
        // 顾名思义 实体和数据 同时返回页面模板和数据
        int count = 0;
        ModelAndView mav = new ModelAndView("copy");
        Person personByID = personService.getPersonByID(id);
        String copyAllName = personByID.getName();
        //获取所有的名字
        List<String> namesList = nameList();
        //截取需要复制的名字
        String name = subStringName(copyAllName);

        //遍历所有的名字
        for(String name1 : namesList){
            name1 = subStringName(name1);
            if (name1.equals(name)){
                count++;
            }
        }
        if(judgeName(copyAllName)){
            count++;
        }
        name = name + "副本" + count ;
        personByID.setName(name);
        mav.addObject("person",personByID);
        return mav;
    }
    //保存
    @RequestMapping("/save")
    public void save(String name, String email,HttpServletResponse response) throws IOException {
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        personService.newp(person);
        response.sendRedirect("/");
    }
    //删除
    @RequestMapping("/deleteTxt")
    public void deleteTxt(int id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        personService.delete(id);
        response.sendRedirect("/");
    }
    //修改
    @RequestMapping("/updateTxt")
    public ModelAndView updateTxt(int id){
        Person personByID = personService.getPersonByID(id);
        ModelAndView mav = new ModelAndView("update");
        mav.addObject("person",personByID);
        return mav;
    }

    //新增
    @RequestMapping("/addTxt")
    public ModelAndView addTxt(){
        Person personByID = new Person();
        ModelAndView mav = new ModelAndView("add");
        mav.addObject("person",personByID);
        return mav;
    }

    /**
     * 获取所有的名字
     * @return
     */
    public List<String> nameList(){
        List<String> namesList = new ArrayList<String>();
        List<Person> all = personService.getAll();
        //添加所有的名字
        for (Person person : all){
            namesList.add(person.getName());
        }
        return namesList;
    }

    /**
     * 截取需要复制的名字
     */
    public String subStringName(String name){
        if(name.contains("副本")){
            String[] copyNames = name.split("副本");
            name = copyNames[0];
        }
        return name;
    }

    /**
     * 判断输入的名字是否重名
     */
    public boolean judgeName(String name){
        //获取所有人员
        List<Person> allPerson = personService.getAll();
        List<String> personNames = new ArrayList<>();
        //将所有人员的属性添加进List
        for (Person person : allPerson){
            personNames.add(person.getName());
        }
        //判断人员列表中是否已经包含名字
        if(personNames.contains(name)){
            //true表示包含
            return true;
        }else{
            return false;
        }
    }
}
