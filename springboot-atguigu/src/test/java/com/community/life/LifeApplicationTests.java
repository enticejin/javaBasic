package com.community.life;


import com.community.life.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * spingboot单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LifeApplicationTests {

    @Autowired
    ApplicationContext ioc;
    @Test
    public void testHelloService(){
       boolean b = ioc.containsBean("helloService");
        System.out.println(b);
    }

    @Autowired
    Person person;
    @Test
   public void contextLoads() {
        System.out.println(person);
    }

}
