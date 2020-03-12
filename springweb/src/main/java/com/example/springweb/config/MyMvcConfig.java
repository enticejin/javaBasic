package com.example.springweb.config;

import com.example.springweb.intercepter.LoginHandlerInterceptor;
import com.example.springweb.servlet.MyFilter;
import com.example.springweb.servlet.MyServlet;
import com.example.springweb.servlet.MyServletContextListener;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MyMvcConfig
 * @Description TODO
 * @Date 2020/3/12 10:00
 * @Created by Administrator
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");
    }
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    class MyErrorAttributes extends DefaultErrorAttributes{
        @Override
        public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
            //调用父类的方法获取数据
            Map<String, Object> map = new HashMap<>(super.getErrorAttributes(webRequest, includeStackTrace));

            //从request域中获取自定义的数据
            Map<String, Object> ext = (Map<String, Object>) webRequest.getAttribute("ext", RequestAttributes.SCOPE_REQUEST);

            map.putAll(ext);
            return map;
        }
    }


    private static final String[] excludePaths = {"/", "/index", "/index.html", "/user/login", "/asserts/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加不拦截的路径，SpringBoot已经做好了静态资源映射，所以我们不用管
        registry.addInterceptor(new LoginHandlerInterceptor())
                .excludePathPatterns(excludePaths);
    }

    @Bean
    public  WebServerFactoryCustomizer webServerFactoryCustomizer(){
        return (WebServerFactoryCustomizer<ConfigurableWebServerFactory>) factory -> factory.setPort(8081);
    }

    @Bean
    public ServletRegistrationBean myServlet(){
        ServletRegistrationBean register = new ServletRegistrationBean(new MyServlet(), "/myServlet");
        register.setLoadOnStartup(1);
        return register;
    }
    @Bean
    public ServletListenerRegistrationBean myServletContextListener(){
        return new ServletListenerRegistrationBean(new MyServletContextListener());
    }


    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean register = new FilterRegistrationBean(new MyFilter());
        register.setUrlPatterns(Arrays.asList("/myServlet","/"));
        return register;
    }

}
