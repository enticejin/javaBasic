package com.community.life;

import com.community.life.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @SpringBootApplication标注主程序类，说明是一个springboot应用
 */
/*

// spingboot单元测试

@RunWith(SpringRunner.class)
@SpringBootTest
public class LifeApplicationTests {

    @Autowired
    Person person;
    @Test
    public void contextLoads() {
        System.out.println(person);
    }

}
 */
//导入Spring的配置文件，让其生效
//@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class LifeApplication {

    public static void main(String[] args) {
        //主程序的运行
        SpringApplication.run(LifeApplication.class, args);
    }

}
