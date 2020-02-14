package com.example.springbootmybatis.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//注解开启 Spring Security 安全认证与授权
@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    //用户认证

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //用户认证
        auth.inMemoryAuthentication().passwordEncoder(new MyPassWordEncoder())
        //添加用户，密码，角色
        .withUser("zs").password("123456").roles("A")
                //链式编程
                .and()
                .withUser("ls").password("123456").roles("B")
                .and()
                .withUser("ww").password("123456").roles("C","primary")
                .and()
                .withUser("zl").password("123456").roles("primary");
    }

    //用户授权

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * permitAll()：允许一切用户访问
         * hasRole()：url请求允许访问的角色
         * hasAnyRole() : url请求允许访问的多个角色
         * access()：允许访问的角色，permitAll、hasRole、hasAnyRole 底层都是调用 access 方法
         * access("permitAll") 等价于 permitAll()
         */
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("user/addUser").hasRole("A")
                .antMatchers("user/deleteUser/**").hasAnyRole("A","B")
                .antMatchers("user/updateUser").hasAnyRole("A","B","C")
                .antMatchers("user/findAllUsers").access("permitAll");

        http.authorizeRequests().anyRequest().authenticated();
        /**
         * formLogin：指定支持基于表单的身份验证
         * 当用户没有登录、没有权限时就会自动跳转到登录页面(默认 /login)
         * 当登录失败时，默认跳转到 /error
         * 登录成功时会放行
         */
        http.formLogin();
    }
}
