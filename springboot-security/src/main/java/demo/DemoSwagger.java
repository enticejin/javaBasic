package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author gejin
 * @createTime 2020/2/9-23:38
 * @description SpringBoot学习（三）—— springboot快速整合swagger文档  访问地址http://localhost:8080/swagger-ui.html
 */

//通过 @Configuration 注解，让 Spring 来加载该类配置。
//再通过 @EnableSwagger2 注解来启用 Swagger2
@Configuration
@EnableSwagger2
public class DemoSwagger {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //指定扫描的包的路径
                .apis(RequestHandlerSelectors.basePackage("demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("项目api文档")
                .description("swagger接入教程")
                .version("1.0")
                .build();
    }
}
