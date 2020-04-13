package com.example.springboot.helloworldQuickStart.config;

import com.example.springboot.helloworldQuickStart.model.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname BeanConfiguration
 * @Description TODO
 * @Date 2020/3/12 9:24
 * @Created by Administrator
 */
//Configuration：指明当前类是一个配置类；就是来替代之前的Spring配置文件
@Configuration
public class BeanConfiguration {

    /**
     *相当于在配置文件中用<bean><bean/>标签添加组件
     */
    @Bean
    public Pet dudu(){
        Pet pet = new Pet();
        pet.setAge(3);
        pet.setName("嘟嘟");
        return pet;
    }
}
