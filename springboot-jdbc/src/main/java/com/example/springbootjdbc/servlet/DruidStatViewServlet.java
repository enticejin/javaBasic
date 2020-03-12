package com.example.springbootjdbc.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @Classname DruidStatViewServlet
 * @Description TODO
 * @Date 2020/3/12 15:07
 * @Created by Administrator
 */

@WebServlet(urlPatterns = { "/druid/*" }, initParams =
        { @WebInitParam(name = "loginUsername", value = "gejin"),
                @WebInitParam(name = "loginPassword", value = "123456") })
public class DruidStatViewServlet extends StatViewServlet {
    private static final long serialVersionUID = 1L;
}
