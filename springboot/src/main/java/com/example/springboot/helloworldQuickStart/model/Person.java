package com.example.springboot.helloworldQuickStart.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname Person
 * @Description TODO
 * @Date 2020/3/12 9:26
 * @Created by Administrator
 * * 将配置文件中配置的每一个属性的值，映射到这个组件中
 *  * <p>
 *  * ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 *  * prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *  * 只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；
 */
@Component
@ConfigurationProperties("person")
public class Person {
    private List<Object> lists;
    private Map<String, Object> maps;
    private Integer age;
    private String lastName;
    private Character gender;
    private boolean boss;
    private Date birth;
    private Pet pet;

    public List<Object> getLists() {
        return lists;
    }

    public void setLists(List<Object> lists) {
        this.lists = lists;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Person{" +
                "lists=" + lists +
                ", maps=" + maps +
                ", age=" + age +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", boss=" + boss +
                ", birth=" + birth +
                ", pet=" + pet +
                '}';
    }
}
