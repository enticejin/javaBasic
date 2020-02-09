package com.example.demo.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author gejin
 * @createTime 2020/2/9-21:53
 * @description 配置文件的girl
 */
@Component
//引入配置文件，前缀为gill的属性
@ConfigurationProperties(prefix = "girl")
public class Girl {
    private String cupSize;
    private int age;
    private String sex;
    private String height;
    private String weight;

    public String getCupSize() {
        return cupSize;
    }

    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "cupSize='" + cupSize + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
