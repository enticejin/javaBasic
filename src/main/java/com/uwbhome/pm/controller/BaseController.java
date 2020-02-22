package com.uwbhome.pm.controller;

import java.io.File;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.uwbhome.pm.utils.Logger;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.pm.utils.PropertyUtils;
import com.uwbhome.pm.utils.Tools;
import com.uwbhome.pm.utils.UuidUtil;

/**
 * 基类控制器
 * @author xu.yuanli
 *
 */
public class BaseController  {
	protected Logger logger = Logger.getLogger(this.getClass());
	@SuppressWarnings("unused")
	private  String basePath; 
	protected static int  pageSize=12;
	protected static String sysname="";
	protected static String sysInitPwd="";
	static
	{
		String tmpPath=Thread.currentThread().getContextClassLoader().getResource("/").getPath()+"sysinfo.properties";
		Properties p=PropertyUtils.getProperties(new File(tmpPath));
		pageSize=Integer.parseInt(p.getProperty("sys.pageSize"));
		sysname=p.getProperty("sys.sysname");
		sysInitPwd=p.getProperty("sys.userInitPWD");
	}
	
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	public static void logBefore(Logger logger, String interfaceName){
		
		//logger.info("start");
		logger.info(interfaceName);
		
		
	}
	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	public static void logAfter(Logger logger){
		logger.info("end");
	}
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	public ModelAndView getModelAndView(){
		
		//设置websocket地址
		String remoteUrl=getRequest().getRequestURL().toString();
		String serverPort=getRequest().getServerPort()+"";
		int pos1=remoteUrl.indexOf("http://");
		int pos2=remoteUrl.indexOf(serverPort);
		remoteUrl=remoteUrl.substring(pos1+7, pos2-1);
		String webSocketUrl=remoteUrl+":"+getRequest().getServerPort()+getRequest().getContextPath();
		ModelAndView mv=new ModelAndView();
		mv.addObject("websocketUrl", webSocketUrl);
		mv.addObject("basePath",getBasePath());
		//System.out.println("websocketUrl:->"+webSocketUrl);
		return mv;
	}
	public  String getBasePath() {
		
		HttpServletRequest request=getRequest();
		String tempPath=request.getContextPath();
		
		return tempPath;
	}
	
}
