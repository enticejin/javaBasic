package com.example.springweb.viewersolver;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * @Classname MyViewResolver
 * @Description TODO
 * @Date 2020/3/12 10:27
 * @Created by Administrator
 */
public class MyViewResolver  implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return null;
    }
}
