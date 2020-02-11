package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 注：如果没有分拆项目成微服务架构，或分布式架构，application主类中的@SpringBootApplication自动会扫描本包中的@Controller，@Service，@Resource等，
 * 是不需要浪费另一行@ComponentScan注解，配置路径的
 */
@SpringBootApplication
public class SpringbootSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityApplication.class, args);
	}

}
