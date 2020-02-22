package com.uwbhome.pm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uwbhome.pm.security.Authentication;
import com.uwbhome.pm.security.AuthenticationInterface;
import com.uwbhome.pm.utils.RtleRequestUtils;

/*登录控制器*/
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	/**
	 * 打开登录页面
	 * @return
	 */
	@RequestMapping(value = "/login.do")
	public ModelAndView toLoginPage(){
		logBefore(logger, "访问登录页");
		ModelAndView mav =this.getModelAndView();
		mav.setViewName("login");
		return mav;
	}
	/**
     * 退出系统方法
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView toLogout(String way,HttpServletRequest request,
			HttpServletResponse response) {
        ModelAndView mav = this.getModelAndView();
        HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
        //返回登录页面
        mav.setViewName("login");
        return mav;
    }
	//处理登录
    @RequestMapping("/dealLogin")
    @ResponseBody
    public Object dealLogin(String userName,String passWord,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String, Object> hm = new HashMap<>();
    	Authentication auth=authMng.login(userName, passWord, RtleRequestUtils.getIpAddr(request), request, response, session);
    	if(auth.getId()!=null) {
    		//跳转到登录成功的页面
    		hm.put("msg", "success");
    	}else
    	{
    		//如果登录不成功,则返回错误信息到登录页面
    		hm.put("msg", "fail");
    	}
    	return hm;
    }
    @Autowired
	private AuthenticationInterface authMng;
	@Autowired
	private com.uwbhome.pm.security.SessionProvider session;
}
