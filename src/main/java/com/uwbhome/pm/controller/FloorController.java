package com.uwbhome.pm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.uwbhome.rtle.api.*;
import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;

/**
 * 楼层信息的处理
 * @author xu.yuanli
 *
 */
@Controller
@RequestMapping("/floor")
public class FloorController extends BaseController {
	
	@RequestMapping(value="/floorList.do")
	public ModelAndView floorList(Page page,HttpServletRequest request)
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
		Collection<Floor> floors = Floors.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取前台穿过来的NAME
		String NAME = pd.getString("ORG_NAME");
		if (null != NAME && !"".equals(NAME)) {
			NAME = NAME.trim();
		} else {
			NAME = "";
		}
		List<Floor> floorList = new ArrayList<Floor>();
		//遍历并判断是否存在NAME
		for (Floor floor : floors) {
			if (null != floor.getName() && floor.getName().contains(NAME)) {
				floorList.add(floor);
			}
		}
		// 排序
		Collections.sort(floorList, new Comparator<Floor>() {
			@Override
			public int compare(Floor o1, Floor o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<Floor> floorListPage = new ArrayList<Floor>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < floorList.size() - currIdx; i++) {
			Floor a = floorList.get(currIdx + i);
			floorListPage.add(a);
		}

		page.setTotalResult(floorList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("floororg_list");
		mv.addObject("varList", floorListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	
	//新增页面
	@RequestMapping(value="/addFloor.do")
	public ModelAndView addFloor()
	{
		ModelAndView mv=getModelAndView();
		PageData pd=new PageData();
		pd.put("flag", "add");
		mv.setViewName("floororg_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	//编辑页面
	@RequestMapping(value="/editFloor.do")
	public ModelAndView editFloor()
	{
		ModelAndView mv=getModelAndView();
		PageData pd1=getPageData();
 		PageData pd=new PageData();
		String floorId=pd1.get("floorId").toString();
		Floor floorInfo=Floors.getInstance().get(floorId);
		ArrayList<String> areaIds=floorInfo.getAreaIds();
		List<Area> areaList=new ArrayList<Area>();
		if (!areaIds.get(0).equals("0000000000000000")) {
			for (String areaId : areaIds) {
				Area area = Areas.getInstance().get(areaId);
				areaList.add(area);
			}
		}
		ArrayList<String> baseMapIds=floorInfo.getBasemapIds();
		List<Basemap> baseList = new ArrayList<Basemap>();
		if (!baseMapIds.get(0).equals("0000000000000000")) {
			for (String baseid : baseMapIds) {
				Basemap base = Basemaps.getInstance().get(baseid);
				baseList.add(base);
			}
		}
		String areaIdStr=getSplitIDStr(areaIds);
		String planMapIdStr=getSplitIDStr(baseMapIds);
		String areaNameStr=getSplitAreaNameStr(areaIds);
		String planMapNameStr=getSplitPlanMapNameStr(baseMapIds);
		pd.put("areaIdStr", areaIdStr);
		pd.put("planMapIdStr", planMapIdStr);
		pd.put("areaNameStr", areaNameStr);
		pd.put("planMapNameStr", planMapNameStr);
		pd.put("floorInfo", floorInfo);
		pd.put("flag", "update");
		pd.put("areaList", areaList);
		pd.put("baseList", baseList);
		mv.setViewName("floororg_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	//保存更新楼层信息
	@RequestMapping(value="/saveFloorInfo.do")
	@ResponseBody
	public Object saveFloorInfo()
	{
		PageData pd=getPageData();
		PageData pd1=new PageData();
		String flag=pd.getString("flag");//获取是更新还是新增的标志
		String newFloorID=Floors.getInstance().getNewId();
		String floorName=pd.getString("floorname");
		String baseMapIds=pd.getString("palnmapids");
		String areaIds=pd.getString("areaids");
		String comments=pd.getString("comments");
		ArrayList<String> areaIdList=new ArrayList<String>();
		ArrayList<String> planMapIdList=new ArrayList<String>();
		if(areaIds!="")
		{
			String[] areastrs=areaIds.split(",");
			for (String sid : areastrs) {
				areaIdList.add(sid);
			}
		}
		if(baseMapIds!="")
		{
			String[] planMapstrs=baseMapIds.split(",");
			for (String mapid : planMapstrs) {
				planMapIdList.add(mapid);
			}
		}
		if(flag!="")
		{
			Floor floor=null;
			if(flag.equals("add"))
			{
				floor=new Floor(newFloorID, floorName,false, comments);
				
			}else if(flag.equals("update"))
			{
				String floorId=pd.getString("floorId");//楼层id
				floor=Floors.getInstance().get(floorId);
			}
			floor.setName(floorName);
			floor.setAreaIds(areaIdList);
			floor.setBasemapIds(planMapIdList);
			floor.setComments(comments);
			floor.save();
			pd1.put("status", "ok");
			pd1.put("msg", "操作成功");
		}else
		{
			//参数错误
			pd1.put("status", "err");
			pd1.put("msg", "参数错误");
		}
		return  pd1;
	}
	//删除功能
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public Object deletePlanMap(String floorId)
	{
		logBefore(logger, "删除楼层");
		//新建页面
		PageData pagedata = new PageData();
		//判断获取楼层ID是否为空
		if(null == floorId) {
			return "";
		}
		try {
			//获取当前页面
			pagedata = this.getPageData();
			//执行删除操作
			Floors.getInstance().remove(floorId);
			//向前台返回数据
			return "ok";
		} catch (Exception e) {
			logger.error(e.toString(), e);
			return "no Success";
		}
	}
	/**
	 * 添加判断楼层名称是否存在
	 * @param floorName
	 * @return
	 */
	@RequestMapping(value = "/existByName")
	@ResponseBody
	public Object existByName(String floorName) {
		//获取所有楼层数据
		Collection<Floor> floors = Floors.getInstance().getList().values();
		for(Floor floor : floors) {
			//根据接收的楼层名判断是否存在
			if(floor.getName().equals(floorName)) {
				//楼层名称已经存在
				return 1;
			}else {
				//楼层名称不存在
				return 0;
			}
		}
		return floors;
	}
	//将list分解成含逗号的字符串
	public String getSplitIDStr(ArrayList<String> strList)
	{
		String retstr="";
		for (String str : strList) {
			retstr+=str+",";
		}
		return retstr;
	}
	public String getSplitAreaNameStr(ArrayList<String> strList)
	{
		String retstr="";
		for (String str : strList) {
			Area ar=Areas.getInstance().get(str);
			if(ar!=null)
			{
				retstr+=Areas.getInstance().get(str).getName()+",";
			}
		}
		return retstr;
	}
	public String getSplitPlanMapNameStr(ArrayList<String> strList)
	{
		String retstr="";
		for (String str : strList) {
			Basemap bp=Basemaps.getInstance().get(str);
			if(bp!=null)
			{
				retstr+=Basemaps.getInstance().get(str).getName();
			}
			
		}
		return retstr;
	}
	
}
