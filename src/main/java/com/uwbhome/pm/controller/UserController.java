package com.uwbhome.pm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.pm.utils.Tools;
import com.uwbhome.rtle.api.ClockSource;
import com.uwbhome.rtle.api.ClockSources;
import com.uwbhome.rtle.api.User;
import com.uwbhome.rtle.api.Users;

import io.netty.handler.codec.http.HttpRequest;

/**
 * 用户管理控制器
 * @author xu.yuanli
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	//用户列表
	@RequestMapping(value="/userList.do")
	public ModelAndView userList(Page page, HttpServletRequest request)
	{
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		//获取所有数据
		Collection<User> users = Users.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取前台传过来的NAME
		String NAME = pd.getString("NAME");
		if (null != NAME && !"".equals(NAME)) {
			NAME = NAME.trim();
		} else {
			NAME = "";
		}
		List<User> userList = new ArrayList<User>();
		//遍历并判断是否存在NAME
		for (User user : users) {
			if (null != user.getName() && user.getName().contains(NAME)) {
				userList.add(user);
			}
		}
		// 排序
		Collections.sort(userList, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<User> userListPage = new ArrayList<User>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < userList.size() - currIdx; i++) {
			User a = userList.get(currIdx + i);
			userListPage.add(a);
		}

		page.setTotalResult(userList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("user_list");
		mv.addObject("varList", userListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	//添加用户
	@RequestMapping(value = "/add.do")
	public ModelAndView addUser() {
		ModelAndView mv=this.getModelAndView();
		PageData pd = new PageData();
		pd.put("flag", "add");
		mv.addObject("pd", pd);
		mv.setViewName("user_edit");
		return mv;
	}
	//编辑用户
	@RequestMapping(value = "/edit.do")
	public ModelAndView editUser(String userName) {
		ModelAndView mv=this.getModelAndView();
		User u=Users.getInstance().get(userName);
		PageData pd = new PageData();
		if(u!=null)
		{
			pd.put("user",u);
		}else
		{
			pd.put("user", "-1");
		}
		pd.put("flag", "update");
		mv.addObject("pd", pd);
		mv.setViewName("user_edit");
		return mv;
	}
	//保存用户数据
	@RequestMapping(value = "/saveUser.do")
	@ResponseBody
	public Object saveUser(Page page, HttpServletRequest request) throws Exception {
		PageData pd = this.getPageData();
		String _userName=pd.getString("name");
		String _fullName=pd.getString("fullName");
		String _enableValue=pd.getString("enable");
		boolean _enable=false;
		PageData pd1=new PageData();
		if(_enableValue.equals("0"))
		{
			_enable=false;
		}else if(_enableValue.equals("1"))
		{
			_enable=true;
		}
		String _comments=pd.getString("comments");
		String flag=pd.getString("flag");
		String result="";
		//判断是更新还是新保存
		if(flag!="")
		{
			if(flag.equals("add"))
			{
				//保存新数据
				System.out.println("新增用户");
				User u=new User(_userName);
				u.setFullName(_fullName);
				u.setEnable(_enable);
				u.setComments(_comments);
				byte b=0;
				u.setRole(b);//设置为默认的
				
				u.setPassword(this.sysInitPwd);//新增用户时设置密码为初始密码，初始密码在配置文件中
				u.save();
				result="新增用户成功!";
				
			}else if(flag.equals("update"))
			{
				//更新数据
				User u=Users.getInstance().get(_userName);
				u.setFullName(_fullName);
				u.setEnable(_enable);
				u.setComments(_comments);
				u.save();	
				result="更新用户成功!";
			}
			pd1.put("status", "ok");
			pd1.put("msg", result);
		}else
		{
			throw new Exception("参数不正确，flag为空");
		}
		return pd1;
	}
	//删除用户
	@RequestMapping("/delUser.do")
	@ResponseBody
	public Object delUser()
	{
		PageData pd=this.getPageData();
		String userName=pd.getString("userName");
		Users.getInstance().remove(userName);
		pd.put("status", "ok");
		pd.put("msg", "删除用户成功");
		return pd;
		
	}
	/**
	 * 返回分页数据
	 * @param usfilesList
	 * @param currentPage
	 * @param showCount
	 * @return
	 */
	public List<User> getCurrentPageData(List<User> userList,int currentPage,int showCount)
	{
		// 排序
		Collections.sort(userList, new Comparator<User>() {
							@Override
							public int compare(User u1, User u2) {
								return u1.getName().compareTo(u2.getName());
							}
						});
		//分页
		List<User> usfilesListPage = new ArrayList<User>();
				int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
				for (int i = 0; i < showCount && i < userList.size() - currIdx; i++) {
					User u = userList.get(currIdx + i);
					usfilesListPage.add(u);
				}
		return usfilesListPage;
				
	}
	//检查用户名是否存在
	@RequestMapping(value = "/existByName")
	@ResponseBody
	public Object existByName() throws Exception{
				PageData pd = new PageData();
				pd = this.getPageData();
				String userName=pd.getString("userName");
				User u=	Users.getInstance().get(userName);
				if(u!=null)
				{
					//用户名已存在
					return 1;
				}else
				{
					//用户名不存在
					return 0;
				}
	    }
	
	//测试
	@RequestMapping(value = "/uploadimg.do")
	@ResponseBody
	public void testUploadImg(HttpServletRequest request ) throws IOException
	{
		System.out.println("收到上传文件的请求 ");
		ServletInputStream ism=request.getInputStream();
		
		
		
		
		
		
	}
}
