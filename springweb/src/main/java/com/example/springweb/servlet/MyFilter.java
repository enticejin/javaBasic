package com.example.springweb.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

/**
 * @Classname MyFilter
 * @Description TODO
 * @Date 2020/3/12 10:15
 * @Created by Administrator
 */
public class MyFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.getWriter().write("Intercept Request!!");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("过滤器初始化");
    }
}
