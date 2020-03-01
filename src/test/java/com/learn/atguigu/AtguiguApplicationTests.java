package com.learn.atguigu;

import com.learn.atguigu.dao.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;


@SpringBootTest
public class AtguiguApplicationTests {
    @Autowired
    private Person person;

    @Autowired
    ApplicationContext ioc;

    @Test
    //@ImportResource(locations = {"classpath:beans.xml"})加载到配置类
    /**
     * Spingboot推荐添加方式
     * 先写一个配置类
     */
    public void testHelloService(){
        boolean b = ioc.containsBean("helloService");
        System.out.println(b);
    }
    @Test
    //可以使用@PropertySource(value = "classpath:person.properties")将配置文件加载到实体类上
    void contextLoads() {
        System.out.println(person);
    }

}
