package com.uwbhome.pm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.uwbhome.pm.observer.LocationObserver;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.RTLEAPI;

@Controller
public class IndexController extends BaseController {

	/**
	 * 打开首页
	 */
	@RequestMapping(value="/index.do")
	public ModelAndView indexMain()
	{
		logBefore(logger, "访问首页1");
		RTLEAPI.getInstance().getRTLSEventMonitor().addObserver(new LocationObserver());
		System.out.println("添加观察者：LocationObserver");
		PageData pd=new PageData();
		//装入登录的用户信息
		
		pd.put("attachment_url", "staticres/images/adminphoto.jpg");
		pd.put("nickname", "admin");
		pd.put("name", "admin");
		
		ModelAndView mv=getModelAndView();
		mv.addObject("sysname", "RTLEweb管理端");
		mv.addObject("current_login_user", pd);
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping(value="/homepage.do")
	public ModelAndView homePageIndex()
	{
		ModelAndView mv=getModelAndView();
		mv.setViewName("homepage");
		System.out.println("打开首页显示的内容");
		return mv;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void index(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws IOException {
		//到这里并不会触发拦截器
		logBefore(logger, "访问首页");
		
	}


}
