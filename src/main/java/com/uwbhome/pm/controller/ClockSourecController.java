package com.uwbhome.pm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.*;

/**
   *  时钟源
 * @author xu.yuanli
 *
 */
@Controller
@RequestMapping("/clocksource")
public class ClockSourecController extends BaseController{
	 //时钟源列表
	@RequestMapping(value="cslist.do")
	public ModelAndView list(Page page, HttpServletRequest request)
	{	
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		//获取时钟源所有数据
		Collection<ClockSource> clockSources = ClockSources.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取前台穿过来的NAME
		String NAME = pd.getString("NAME");
		if (null != NAME && !"".equals(NAME)) {
			NAME = NAME.trim();
		} else {
			NAME = "";
		}
		List<ClockSource> clockSourceList = new ArrayList<ClockSource>();
		//遍历并判断是否存在NAME
		for (ClockSource clock : clockSources) {
			if (null != clock.getName() && clock.getName().contains(NAME)) {
				clockSourceList.add(clock);
			}
		}
		// 排序
		Collections.sort(clockSourceList, new Comparator<ClockSource>() {
			@Override
			public int compare(ClockSource o1, ClockSource o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<ClockSource> ClockSourceListPage = new ArrayList<ClockSource>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < clockSourceList.size() - currIdx; i++) {
			ClockSource a = clockSourceList.get(currIdx + i);
			ClockSourceListPage.add(a);
		}

		page.setTotalResult(clockSourceList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("clocksource_list");
		mv.addObject("varList", ClockSourceListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	//删除时钟
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public Object deleteArea(){
		PageData pd=getPageData();
		String clockSourceId=pd.get("clockSourceId").toString();	 
		List<Area> areaListAll=new ArrayList<Area>();
		areaListAll.addAll(Areas.getInstance().getList().values());
		String zhong = "";
		for (int i = 0; i < areaListAll.size(); i++) {
			String shizhong = areaListAll.get(i).getClockSource().getId();
			if (clockSourceId.equals(shizhong)) {
				zhong += ','+areaListAll.get(i).getName();
			}
		}
		PageData pd1=new PageData();
		if (zhong=="") {
			ClockSources.getInstance().remove(clockSourceId);
			pd1.put("status", "ok");
			pd1.put("msg", "删除成功");
		}else {
			pd1.put("zhong", zhong.substring(1));
		}
		
		return pd1;
	}
	//修改时钟坐标
	@RequestMapping(value = "/setXYZ.do")
	@ResponseBody
	public Object setAnchorXYZ() {
		PageData pd = new PageData();
		PageData retpd = new PageData();
		pd = this.getPageData();
		String csWebId = pd.getString("id");
		if(csWebId==null)
		{
			return "";
		}
		double x = Double.parseDouble(pd.getString("x"));
		double y = Double.parseDouble(pd.getString("y"));
		double z = Double.parseDouble(pd.getString("z"));
		
		ClockSource cs=ClockSources.getInstance().get(csWebId);
		if(cs!=null)
		{
			cs.setX(x);
			cs.setY(y);
			cs.setZ(z);
			cs.save();
			retpd.put("status", "ok");
			retpd.put("msg", "操作成功");
			
		}else
		{
			retpd.put("status", "err");
			retpd.put("msg", "操作失败");
		}
		return retpd;
	} 
	/**
	 * 返回分页数据
	 * @param usfilesList
	 * @param currentPage
	 * @param showCount
	 * @return
	 */
	public List<ClockSource> getCurrentPageData(List<ClockSource> clockList,int currentPage,int showCount)
	{
		// 排序
		Collections.sort(clockList, new Comparator<ClockSource>() {
							@Override
							public int compare(ClockSource c1, ClockSource c2) {
								return c1.getName().compareTo(c2.getName());
							}
						});
		//分页
		List<ClockSource> csListPage = new ArrayList<ClockSource>();
				int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
				for (int i = 0; i < showCount && i < clockList.size() - currIdx; i++) {
					ClockSource bmap = clockList.get(currIdx + i);
					csListPage.add(bmap);
				}
		return csListPage;
				
	}
}
