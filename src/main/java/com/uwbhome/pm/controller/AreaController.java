package com.uwbhome.pm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.*;
import com.uwbhome.rtle.comm.LonLatPoint;
import com.uwbhome.rtle.comm.Point2;
import com.uwbhome.rtle.comm.Polygon;
import com.uwbhome.rtle.utils.Misc;

/**
   *  区域控制器 
 * @author xu.yuanli
 *
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {
	//打开区域列表
	@RequestMapping(value="/arealist.do")
	public ModelAndView list(Page page)
	{
		int currentPage = 0;
		PageData pd = this.getPageData();
		String areaName=pd.getString("areaname");
		List<Area> areaList=new ArrayList<Area>();
		//从api获取文件数据
		if(areaName!=null&&areaName.trim()!="")
		{
			List<Area> areaListAll=new ArrayList<Area>();
			areaListAll.addAll(Areas.getInstance().getList().values());
		}else
		{
			areaList.addAll(Areas.getInstance().getList().values());
		}
		//获取分页数据
		List<Area> areaListPage=getCurrentPageData(areaList,currentPage,this.pageSize);
		
		page.setTotalResult(areaListPage.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(this.pageSize);
		page.setCurrentPage(page.getCurrentPage());
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("area_list");
		
		mv.addObject("varList", areaListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	//打开新增区域页面
	@RequestMapping(value="/areaAdd.do")
	public ModelAndView areaAdd()
	{
		ModelAndView mv=this.getModelAndView();
		
		//构造时钟的下拉列表
		List<PageData> pdClockSource = clockSurceList();
		//该区域的定位基站
		List<PageData> pdListAnchors = getAnchorList(null);
		//构造定位的维度对象
		List<PageData> pdListLD=locationModeData();
		//定位触发方式
		List<PageData> pdListLQ=locationTriggerData();	

		PageData pd=new PageData();
		pd.put("flag", "add");
		pd.put("areaInfo",new Area());
		mv.addObject("pd", pd);
		mv.addObject("pdClockSource", pdClockSource);// 时钟源
		mv.addObject("pdListLD", pdListLD);// 定位维度
		mv.addObject("pdListLQ", pdListLQ);// 定位触发方式
		mv.addObject("pdListAnchors", pdListAnchors);// 获取所有基站数据，如果是区域关联的state = 1;
		mv.setViewName("area_edit");
		return mv;
	}

		//复制区域页面
		@RequestMapping(value="/areaCopy.do")
		public ModelAndView areaCopy()
		{
			ModelAndView mv=this.getModelAndView();
			PageData pd1=this.getPageData();
			String areaId=pd1.get("areaId").toString();//获取需要复制 的ID
			Area area=Areas.getInstance().get(areaId);//获取需要复制的area对象
			
			String name = area.getName();	

			String str2 = name.substring(name.length()-2, name.length()) ;
			if (str2.equals("副本")) {
				List<Area> list1 = new ArrayList<Area>();
				list1.addAll(Areas.getInstance().getList().values());
				String name3 = name.substring(0, name.indexOf("副本"));
				int sum = 0;//假设第一个元素是最大值
				for (int i = 0; i < list1.size(); i++) {
					String name2 = list1.get(i).getName();
					
					String name1 = name2.substring(0, name2.indexOf("副本"));
					String newName = name2.substring(name1.length()+2, name2.length());
					if (!newName.equals("")&&name1.equals(name3)&&(!newName.equals("副本"))) {
						
						int max = Integer.parseInt(newName);
						 if (sum < max){//数组中的元素跟sum比较，比sum大就把它赋值给sum作为新的比较值
				                sum = max;
				          }
					}
					
				}
				int b=sum+1;
				name=name+b;
				
			}else {
				List<Area> list1 = new ArrayList<Area>();
				list1.addAll(Areas.getInstance().getList().values());
				String name3 = name.substring(0, name.indexOf("副本"));
				int sum = 0;//假设第一个元素是最大值
				String qian = "";
				for (int i = 0; i < list1.size(); i++) {
					String name2 = list1.get(i).getName();
					
					String name1 = name2.substring(0, name2.indexOf("副本"));
					String newName = name2.substring(name1.length()+2, name2.length());
					if (!newName.equals("")&&name1.equals(name3)&&(!newName.equals("副本"))) {
						int max = Integer.parseInt(newName);
						 if (sum < max){//数组中的元素跟sum比较，比sum大就把它赋值给sum作为新的比较值
				                sum = max;
				                qian = name1;
				          }
					}
					
				}
				int b=sum+1;
				name=qian+"副本"+b;
			}
			
			//构造时钟的下拉列表
			List<PageData> pdClockSource = clockSurceList();
			//该区域的定位基站
			List<PageData> pdListAnchors = getAnchorList(area);
			//构造定位的维度对象
			List<PageData> pdListLD=locationModeData();
			//定位触发方式
			List<PageData> pdListLQ=locationTriggerData();	
			PageData pd=new PageData();
			pd.put("flag","copy");
			pd.put("areaInfo",area);
			ClockSource cls=area.getClockSource(); 
			String clockSourceId="";
			if(cls!=null)
			{
				clockSourceId=area.getClockSource().getId();
			}
			pd.put("clockSourceId",clockSourceId);
			mv.addObject("pd", pd);
			mv.addObject("pdClockSource", pdClockSource);// 时钟源
			mv.addObject("pdListLD", pdListLD);// 定位维度
			mv.addObject("pdListLQ", pdListLQ);// 定位触发方式
			mv.addObject("pdListAnchors", pdListAnchors);// 获取所有基站数据，如果是区域关联的state = 1;
			mv.addObject("name", name);// 获取所有基站数据，如果是区域关联的state = 1;
			mv.setViewName("area_copy");
			return mv;
		}

	//打开修改区域页面
	@RequestMapping(value="/areaEdit.do")
	public ModelAndView areaEdit()
	{
		ModelAndView mv=this.getModelAndView();
		PageData pd1=this.getPageData();
		String areaId=pd1.get("areaId").toString();//获取需要编辑 的ID
		Area area=Areas.getInstance().get(areaId);//获取需要编辑的area对象
		String areaName = null;
		if(area.getName().endsWith("副本")) {
			areaName = area.getName().substring(0,area.getName().indexOf("副本"));
		}else {
			areaName = area.getName();
		}
		//构造时钟的下拉列表
		List<PageData> pdClockSource = clockSurceList();
		//该区域的定位基站
		List<PageData> pdListAnchors = getAnchorList(area);
		//构造定位的维度对象
		List<PageData> pdListLD=locationModeData();
		//定位触发方式
		List<PageData> pdListLQ=locationTriggerData();	
		PageData pd=new PageData();
		pd.put("flag","update");
		pd.put("areaInfo",area);
		ClockSource cls=area.getClockSource();
		String clockSourceId="";
		if(cls!=null)
		{
			clockSourceId=area.getClockSource().getId();
		}
		pd.put("clockSourceId",clockSourceId);
		mv.addObject("areaName", areaName);
		mv.addObject("pd", pd);
		mv.addObject("pdClockSource", pdClockSource);// 时钟源
		mv.addObject("pdListLD", pdListLD);// 定位维度
		mv.addObject("pdListLQ", pdListLQ);// 定位触发方式
		mv.addObject("pdListAnchors", pdListAnchors);// 获取所有基站数据，如果是区域关联的state = 1;
		mv.setViewName("area_edit");
		return mv;
	}
	
	//删除区域功能处理
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public Object deleteArea()
	{
		PageData pd=getPageData();
		String areaID=pd.get("areaId").toString();
		Areas.getInstance().remove(areaID);
		PageData pd1=new PageData();
		pd1.put("status", "ok");
		pd1.put("msg", "删除成功");
		return pd1;
	}
	//保存区域功能处理
	@RequestMapping(value="/saveArea.do")
	@ResponseBody
	public Object saveArea() 
	{
		PageData pd=getPageData();
		String areaName=pd.getString("name");//区域名称
		String comments=pd.getString("comments");//区域备注
		boolean enable=Boolean.parseBoolean(pd.getString("activeValue"));//区域是否激活
		
		String clockSourceId=pd.getString("clockSourceId");//区域时钟ID
		ClockSource currentCs=ClockSources.getInstance().get(clockSourceId); 
		int locationDimensionality=Integer.parseInt(pd.getString("locationDimensionality"));//区域定位维度 1-一维，2-二维，3-三维
		double defaultZ=Double.parseDouble(pd.getString("defaultZ_Value"));//区域缺省Z坐标
		//int needAnchorNum=Integer.parseInt(pd.getString("needAnchorNum")) ;//区域定位的基站数量
		int locationTrigger=Integer.parseInt(pd.getString("locationTrigger"));//触发定位的条件
		boolean useAverageFilter=Boolean.parseBoolean(pd.getString("useAverageFilterValue")) ;//是否使用均值滤波
		boolean useKalmanFilter=Boolean.parseBoolean(pd.getString("useKalmanFilterValue"));//是否使用卡尔曼滤波
		int averageFilterSampleTimeLength=Integer.parseInt(pd.getString("averageFilterSampleTimeLength"));//均值滤波的时间，单位毫秒
		int kalmanFilterLevel=Integer.parseInt(pd.getString("kalmanFilterLevel"));//卡尔曼滤波的样本数
		String specifyAnchorsId=pd.getString("specifyAnchorsIdlist");//区域的定位基站列表
		
		ArrayList<String> specifyAnchorsIdList=getAnchorIdList(specifyAnchorsId);
		boolean discardPointsOfOutOfArea=Boolean.parseBoolean(pd.getString("discardPointsOfOutOfArea"));//是否丢弃区域外的点
		//int signalCheckFlag=Integer.parseInt(pd.getString("signalCheckFlag"));//采取何种信号参数  1-为接收信号强度，2-为第一路径信号强度
		//double signalErrorConstant=Double.parseDouble(pd.getString("signalErrorConstant"));//信号误差参数signalErrorConstant
		//double coordinateValidTime=Double.parseDouble(pd.getString("coordinateValidTime"));//坐标的有效时间，单位为毫秒
		
		Areas areas=Areas.getInstance();
		PageData pd1=new PageData();
		String flag=pd.getString("flag");//获取是更新还是新增的标志
		if(flag!="")
		{
			Area saveBean=null;
			if(flag.equals("add") || flag.equals("copy"))
			{
				//新增的逻辑
				saveBean=new Area();
				Point2[] point = new Point2[4];
				point[0] = new Point2(0, 0);
				point[1] = new Point2(0, 1);
				point[2] = new Point2(1, 1);
				point[3] = new Point2(1, 0);
				Polygon aPolygon = new Polygon(point);
				saveBean.setPolygon(aPolygon);
				saveBean.setId(areas.getNewAreaId());
				
			}else if(flag.equals("update"))
			{
				//保存的逻辑
				String areaid=pd.getString("areaId");
				saveBean=areas.get(areaid);
			}
			
			if (flag.equals("copy")) {
				saveBean.setName(areaName);
			}else if(flag.equals("add")){
				saveBean.setName(areaName+"副本");
			}else {
				//判断接收的区域名称是否包含“副本”字符
				if(areaName.endsWith("副本")) {
					saveBean.setName(areaName);
				}else
					saveBean.setName(areaName+"副本");
			}
			saveBean.setComments(comments);
			saveBean.setClockSource(currentCs);

			saveBean.setDefaultZ(defaultZ);
			saveBean.setDiscardPointsOfOutOfArea(discardPointsOfOutOfArea);
			saveBean.setEnable(enable);
			saveBean.setKalmanFilterLevel(kalmanFilterLevel);
			saveBean.setAverageFilterSampleTimeLength(averageFilterSampleTimeLength);
			saveBean.setLocationDimensionality(locationDimensionality);//定位模式
			saveBean.setLocationTrigger(locationTrigger);//设置触发方式
			//saveBean.setNeedAnchorNum(needAnchorNum);//设置需要的基站数量
			saveBean.setSpecifyAnchorsId(specifyAnchorsIdList);
			saveBean.setUseAverageFilter(useAverageFilter);//是否使用均值滤波
			saveBean.setUseKalmanFilter(useKalmanFilter);//是否使用卡尔曼滤波
			//saveBean.setUseSpecifyAnchorsOnly(true);//这个值客户端不再指定，永远都是true
			saveBean.save();
			pd1.put("status", "ok");
			pd1.put("msg", "操作成功");
		}else
		{
			//参数错误
			pd1.put("status", "err");
			pd1.put("msg", "参数错误");
		}	
		return  pd;
	}
	
	//给其他窗口调用选择区域的窗口
	@RequestMapping(value="/selectArea.do")
	public  ModelAndView selectPlanMapList(String areaid,Page page, HttpServletRequest request)
	{
		//todo
		int currentPage = 0;
		List<Area> areaList=new ArrayList<Area>();
		//从api获取文件数据
		areaList.addAll(Areas.getInstance().getList().values());
		//获取分页数据
		List<Area> areaListPage=getCurrentPageData(areaList,currentPage,this.pageSize);

		page.setTotalResult(areaList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(5);
		page.setCurrentPage(page.getCurrentPage());
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("select_area_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("varList", areaListPage);
		mv.addObject("areaid", areaid);
		mv.addObject("pd", pd);
		return mv;
	}
	//根据基站ID字符串返回
	public ArrayList<String> getAnchorIdList(String aids)
	{
		ArrayList<String> retList=new ArrayList<String>();
		if(aids!="")
		{
			 String[] anchorids=aids.split(",");
			 for (String id : anchorids) {
				 retList.add(id);
			}
		}else
		{
			//参数为空
			System.out.println("基站ID字符参数为空!!");
		}
		return retList;
	}
	
	//在地图上编辑区域和基站位置
	@RequestMapping("/editAreaOnMap.do")
	public ModelAndView gotoAreaMap()
	{
		String type = "area";
		PageData pd=this.getPageData();
		String areaId=pd.getString("areaId");
		String anchorId=pd.getString("anchorId");
		if(null != anchorId && !"".equals(anchorId)) {
			type = "anchor";
			//说明传入的是基站ID，也就是用户点击了基站列表的修改坐标的按钮的触发方式
			//此时我们需要求出该基站属于哪个区域，因为存在基站共享的问题，所以我们只要找到一个就行、
			List<Area>  areas=new ArrayList<Area>();
			areas.addAll(Areas.getInstance().getList().values());
			for (Area _area : areas) {
				List<String> anchorIdList = _area.getSpecifyAnchorsId();
				for (String  aid : anchorIdList) {
					if(null!=aid && aid.equals(anchorId)){
						areaId=_area.getId();
						break;
					}
				}
			}
			
		}
		ModelAndView mv=this.getModelAndView();
		mv.addObject("areaId",areaId);
		mv.addObject("type", type);
		mv.setViewName("area_map_edit_index");
		return  mv;
		
	}
	
	//构造定位模式下拉列表      
	public List<PageData> locationModeData()
	{
		List<PageData> pdListLD=new ArrayList<PageData>();
		PageData pdLDQuery1 = new PageData();
		PageData pdLDQuery2 = new PageData();
		PageData pdLDQuery3 = new PageData();
		pdLDQuery1.put("id", "1");
		pdLDQuery1.put("name", "一维");
		pdLDQuery2.put("id", "2");
		pdLDQuery2.put("name", "二维");
		pdLDQuery3.put("id", "3");
		pdLDQuery3.put("name", "三维");
		pdListLD.add(pdLDQuery1);
		pdListLD.add(pdLDQuery2);
		pdListLD.add(pdLDQuery3);
		return pdListLD;	
	}
	//构造触发定位的选择列表
	public List<PageData> locationTriggerData()
	{
		List<PageData> pdListLQ=new ArrayList<PageData>();
		PageData pdLTQuery1 = new PageData();
		PageData pdLTQuery2 = new PageData();
		pdLTQuery1.put("id","1");
		pdLTQuery1.put("name","消息数量达到最小要求, 触发计算");
		pdLTQuery2.put("id","2");
		pdLTQuery2.put("name","下一条消息到达, 触发计算(如果消息数量不够,不触发)");
		pdListLQ.add(pdLTQuery1);
		pdListLQ.add(pdLTQuery2);
		return pdListLQ;
	}
	//基站的下拉列表
	public List<PageData> getAnchorList(Area area)
	{
		List<PageData> pdListAnchors = new ArrayList<PageData>();
		List<Anchor> anchorList=new ArrayList<Anchor>();
		anchorList.addAll(Anchors.getInstance().getList().values());
		// 排序
		Collections.sort(anchorList, new Comparator<Anchor>() {
									@Override
									public int compare(Anchor a1, Anchor a2) {
										return a1.getName().compareTo(a2.getName());
									}
								});
		if(area!=null)
		{
			List<String> anchorIds=area.getSpecifyAnchorsId();
			for (Anchor an : anchorList) {	
				Integer state = 0;
				PageData pdTemp = new PageData();
				pdTemp.put("id", an.getId());
				pdTemp.put("name", an.getName());
				for (String anchorId : anchorIds) {
					if (an.getId().equals(anchorId)) {
						state = 1;
						break;
					}
				}
				pdTemp.put("state", state);
				pdListAnchors.add(pdTemp);
			}
			
			
		}else
		{
			for (Anchor an : anchorList) {	
				PageData pdTemp = new PageData();
				pdTemp.put("id", an.getId());
				pdTemp.put("name", an.getName());
				pdTemp.put("state", 0);
				pdListAnchors.add(pdTemp);
				
			}
		}
		return pdListAnchors;	
	}
	
	//构造时钟下拉列表
	public List<PageData> clockSurceList()
	{
		List<PageData> pdClockSource = new ArrayList<>();
		List<ClockSource> clockList=new ArrayList<ClockSource>();
		clockList.addAll(ClockSources.getInstance().getList().values());
		for (ClockSource clockSource : clockList) {
			PageData tempPd = new PageData();
			tempPd.put("id",clockSource.getId());
			tempPd.put("name",clockSource.getName());
			pdClockSource.add(tempPd);
		}
		return  pdClockSource;
	}
	//获取区域的json数据
	@RequestMapping(value = "/json_area_byId.do")
	@ResponseBody
	public Object json_area_byId(String areaId) {
		RTLEAPI rtapi=RTLEAPI.getInstance();
		LonLatPoint origin = new LonLatPoint(rtapi.getOrigin().getLongitude(), rtapi.getOrigin().getLatitude(), 0);
		Map<String, Object> map = new HashMap<>();
		map.put("type", "FeatureCollection");
		Map<String, Object> crsMap = new HashMap<>();// crs
		crsMap.put("type", "name");
		Map<String, Object> propertieMap = new HashMap<>();// properties
		propertieMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		crsMap.put("properties", propertieMap);
		map.put("crs", crsMap);

		List<Map<String, Object>> featureList = new ArrayList<>();
		Collection<Area> areas = Areas.getInstance().getList().values();
		for (Area area : areas) {
			if (null == areaId || !areaId.equals(area.getId())) {
				continue;
			}
			ClockSource clockSource = area.getClockSource();// 区域关联的时钟
			// 时钟
			if (clockSource != null) {
				Map<String, Object> featureMap = new HashMap<>();
				featureMap.put("type", "Feature");
				featureMap.put("id", "CS_" + clockSource.getId());
				Map<String, Object> propertyMap = new HashMap<>();// properties
				propertyMap.put("type", "cs");
				propertyMap.put("name", clockSource.getName());
				featureMap.put("properties", propertyMap);
				Map<String, Object> geometryMap = new HashMap<>();// geometry
				geometryMap.put("type", "Point");
				List<Double> coordinateList = new ArrayList<>();// coordinates
				LonLatPoint lonLatPoint =	Misc.meter2LonLat(origin, area.getClockSource().getX(),area.getClockSource().getY());
				coordinateList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLongitude(), 8)));
				coordinateList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLatitude(), 8)));
				geometryMap.put("coordinates", coordinateList);
				featureMap.put("geometry", geometryMap);
				featureList.add(featureMap);
			}
			// 区域的数据
			Map<String, Object> featureMap = new HashMap<>();
			featureMap.put("type", "Feature");
			featureMap.put("id", area.getId());
			Map<String, Object> propertyMap = new HashMap<>();// properties
			propertyMap.put("type", "area");
			propertyMap.put("name", area.getName());
			featureMap.put("properties", propertyMap);
			Map<String, Object> geometryMap = new HashMap<>(); // geometry
			geometryMap.put("type", "Polygon");
			List<Object> coordinateList = new ArrayList<>();// coordinates
			List<Object> pList = new ArrayList<>();
			for (Point2 point : area.getPolygon().getVertexes()) {
				LonLatPoint lonLatPoint = Misc.meter2LonLat(origin, point.getX(), point.getY());// 将x
				List<Double> strList = new ArrayList<>();// 区域图表每个点的坐标
				strList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLongitude(), 8)));
				strList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLatitude(), 8)));
				pList.add(strList);
			}
			coordinateList.add(pList);
			geometryMap.put("coordinates", coordinateList);
			featureMap.put("geometry", geometryMap);
			featureList.add(featureMap);
			break;
		}
		map.put("features", featureList);
		return map;
	}
	//获取区域的定位基站
	@RequestMapping("/json_anchors_byAreaId.do")
	@ResponseBody
	public Object json_anchors_byAreaId(String areaId) {
		RTLEAPI rtapi=RTLEAPI.getInstance();
		LonLatPoint origin = new LonLatPoint(rtapi.getOrigin().getLongitude(), rtapi.getOrigin().getLatitude(), 0);
		Map<String, Object> map = new HashMap<>();
		map.put("type", "FeatureCollection");
		Map<String, Object> crsMap = new HashMap<>();// crs
		crsMap.put("type", "name");
		Map<String, Object> propertyMap = new HashMap<>();
		propertyMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		crsMap.put("properties", propertyMap);
		map.put("crs", crsMap);
		Area area = Areas.getInstance().get(areaId);// 获取区域
		List<String> specifyAnchorsIdList = new ArrayList<String>();// 区域绑定的基站id
		if (null != area && null != area.getSpecifyAnchorsId()) {
			specifyAnchorsIdList = area.getSpecifyAnchorsId();
		}
		// Collection<Anchor> anchors = Anchors.getAnchorList().values();
		List<Anchor> anchors = new ArrayList<>();
		for (String anchorId : specifyAnchorsIdList) {
			Anchor anchor = Anchors.getInstance().get(anchorId);
			if (null != anchor) {
				anchors.add(anchor);
			}
		}
		List<Object> featureList = new ArrayList<>();
		for (Anchor anchor : anchors) {
			Map<String, Object> featureMap = new HashMap<>();
			featureMap.put("type", "Feature");
			featureMap.put("id", anchor.getId());
			Map<String, Object> propertyFeatureMap = new HashMap<>();// properties
			propertyFeatureMap.put("type", "anchor");
			propertyFeatureMap.put("no", anchor.getId());
			propertyFeatureMap.put("name", anchor.getName());
			propertyFeatureMap.put("sn", anchor.getSerialNo());
			propertyFeatureMap.put("x", Double.valueOf(Misc.fixedWidthDoubletoString(anchor.getX(), 4)));
			propertyFeatureMap.put("y", Double.valueOf(Misc.fixedWidthDoubletoString(anchor.getY(), 4)));
			propertyFeatureMap.put("z", Double.valueOf(Misc.fixedWidthDoubletoString(anchor.getZ(), 4)));
			featureMap.put("properties", propertyFeatureMap);
			Map<String, Object> geometryMap = new HashMap<>();
			geometryMap.put("type", "Point");
			List<Double> coordinateList = new ArrayList<Double>();
			LonLatPoint lonLatPoint = Misc.meter2LonLat(origin, anchor.getX(), anchor.getY());
			coordinateList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLongitude(), 8)));
			coordinateList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLatitude(), 8)));
			geometryMap.put("coordinates", coordinateList);
			featureMap.put("geometry", geometryMap);
			featureList.add(featureMap);
		}
		map.put("features", featureList);
		return map;
	}
	/**
	 * 返回分页数据
	 * @param usfilesList
	 * @param currentPage
	 * @param showCount
	 * @return
	 */
	public List<Area> getCurrentPageData(List<Area> areaList,int currentPage,int showCount)
	{
		// 排序
		Collections.sort(areaList, new Comparator<Area>() {
							@Override
							public int compare(Area a1, Area a2) {
								return a1.getName().compareTo(a2.getName());
							}
						});
		//分页
		List<Area> areaListPage = new ArrayList<Area>();
				int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
				for (int i = 0; i < showCount && i < areaList.size() - currIdx; i++) {
					Area area = areaList.get(currIdx + i);
					areaListPage.add(area);
				}
		return areaListPage;
				
	}
}
