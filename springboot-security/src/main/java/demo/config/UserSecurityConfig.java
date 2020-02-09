package demo.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author gejin
 * @createTime 2020/2/9-22:49
 * @description 用户的安全验证配置
 */
//注解开启 Spring Security 安全认证与授权
@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    //用户认证
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //内存里面放着
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
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
        http.authorizeRequests().antMatchers("/").permitAll();// "/"：应用首页所有用户都可以访问
        http.authorizeRequests()
                .antMatchers("user/addUser").hasRole("A") // 首斜杠"/"表示应用上下文,/user/addUser 请求允许 A 角色访问
                .antMatchers("user/deleteUser/**").hasAnyRole("A","B") //"/user/deleteUser/**"允许 "AAA", "BBB" 角色访问，/**匹配任意
                .antMatchers("user/updateUser").hasAnyRole("A","B")//除了这种链式编程，也可以分开写
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
