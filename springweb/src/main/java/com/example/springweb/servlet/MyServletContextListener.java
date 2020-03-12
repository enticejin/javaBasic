package com.example.springweb.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Classname MyServletContextListener
 * @Description TODO
 * @Date 2020/3/12 10:17
 * @Created by Administrator
 */
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("web 容器启动........");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("web 容器销毁........");
    }
}
