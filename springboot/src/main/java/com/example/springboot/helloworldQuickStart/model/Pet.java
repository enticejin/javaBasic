package com.example.springboot.helloworldQuickStart.model;

/**
 * @Classname Pet
 * @Description TODO
 * @Date 2020/3/12 9:25
 * @Created by Administrator
 */
public class Pet {
    private Integer age;
    private String name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
