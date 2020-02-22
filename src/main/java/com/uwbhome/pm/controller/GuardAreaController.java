package com.uwbhome.pm.controller;

import java.io.IOException;
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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.Area;
import com.uwbhome.rtle.api.Areas;
import com.uwbhome.rtle.api.Floor;
import com.uwbhome.rtle.api.Floors;
import com.uwbhome.rtle.api.GuardArea;
import com.uwbhome.rtle.api.GuardAreas;
import com.uwbhome.rtle.api.RTLEAPI;
import com.uwbhome.rtle.comm.LonLatPoint;
import com.uwbhome.rtle.comm.Point2;
import com.uwbhome.rtle.comm.Polygon;
import com.uwbhome.rtle.utils.Misc;

/**
   *  电子围栏控制器
 * @author xu.yuanli
 *
 */
@Controller
@RequestMapping("/guardArea")
public class GuardAreaController extends BaseController {
	
	//打开电子围栏列表
	@RequestMapping(value="/guardArealist.do")
	public ModelAndView list(Page page, HttpServletRequest request){
		
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		
		Collection<GuardArea> guards = GuardAreas.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		String guardname = pd.getString("guardname");
		if (null != guardname && !"".equals(guardname)) {
			guardname = guardname.trim();
		} else {
			guardname = "";
		}
		List<GuardArea> guardList = new ArrayList<GuardArea>();
		for (GuardArea guard : guards) {
			if (null != guard.getName() && guard.getName().contains(guardname)) {
				guardList.add(guard);
			}
		}
		// 排序
		Collections.sort(guardList, new Comparator<GuardArea>() {
			@Override
			public int compare(GuardArea o1, GuardArea o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<GuardArea> guarareaListPage = new ArrayList<GuardArea>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < guardList.size() - currIdx; i++) {
			GuardArea a = guardList.get(currIdx + i);
			guarareaListPage.add(a);
		}
		
		page.setTotalResult(guardList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("guardarea_list");
		mv.addObject("varList", guarareaListPage);
		mv.addObject("pd", pd);
		return mv;
	}
		
		//打开新增电子围栏页面
		@RequestMapping(value="/guardareaAdd.do")
		public ModelAndView guardareaAdd(){	
			ModelAndView mv=getModelAndView();
			List<PageData> pdListLQ=locationTriggerData();		
			List<PageData> pdListFloors = FloorList();
			PageData pd=new PageData();
			pd.put("flag", "add");
			mv.addObject("pdListLQ", pdListLQ);// 定位触发方式
			mv.setViewName("guardarea_edit");
			mv.addObject("pd", pd);
			mv.addObject("pdListFloors", pdListFloors);
			return mv;
		}
		
		//打开修改电子围栏页面
		@RequestMapping(value="/GuarareaEdit.do")
		public ModelAndView GuarareaEdit(){
			ModelAndView mv=this.getModelAndView();
			PageData pd1=this.getPageData();
			String guardId=pd1.get("guardId").toString();//获取需要编辑 的ID
			GuardArea guard=GuardAreas.getInstance().get(guardId);//获取需要编辑的area对象
			//定位触发方式
			List<PageData> pdListLQ=locationTriggerData();
			List<PageData> pdListFloors = FloorList();		
			String floor=guard.getFloorId();
			PageData pd=new PageData();
			pd.put("flag","update");
			pd.put("guardInfo",guard);
			pd.put("floor",floor);
			mv.addObject("pd", pd);
			mv.addObject("pdListLQ", pdListLQ);// 定位触发方式
			mv.addObject("pdListFloors", pdListFloors);
			mv.setViewName("guardarea_edit");
			return mv;
		}
		
		//保存电子围栏功能处理
		@RequestMapping(value="/saveGuardarea.do")
		@ResponseBody
		public Object saveGuardarea() 
		{
			PageData pd=getPageData();
			String name=pd.getString("name");//名称
			int triggerMode=Integer.parseInt(pd.getString("locationTrigger"));//触发定位的条件
			String comments=pd.getString("comments");//备注
			String newGuarID=GuardAreas.getInstance().getNewId();
			PageData pd1=new PageData();
			String flag=pd.getString("flag");//获取是更新还是新增的标志	
			String specifyFloorId=pd.getString("specifyFloorsId");//楼层id
			String enableValue=pd.getString("enable");//是否启用
			boolean enable=false;
			if(enableValue.equals("0")){
				enable=false;
			}else if(enableValue.equals("1")){
				enable=true;
			}		
			if(flag!=""){
			GuardArea guaedBean=null;
			
			if(flag.equals("add")){
				List<GuardArea> listAll = new ArrayList<GuardArea>();
				listAll.addAll(GuardAreas.getInstance().getList().values());
				for (int i = 0; i < listAll.size(); i++) {
					if (name.equals(listAll.get(i).getName())) {
						pd1.put("status", "errname");
						pd1.put("msg", "该名称已存在");
						return  pd1;
					}
				}

				Point2[] point = new Point2[4];
				point[0] = new Point2(0, 0);
				point[1] = new Point2(0, 1);
				point[2] = new Point2(1, 1);
				point[3] = new Point2(1, 0);
				Polygon polygon = new Polygon(point);
				guaedBean=new GuardArea(enable, newGuarID, name, specifyFloorId, comments, triggerMode, polygon);
				guaedBean.save();
			}else if(flag.equals("update")){
				//保存的逻辑
				String guardId=pd.getString("guardId");//id
				List<GuardArea> listAll = new ArrayList<GuardArea>();
				listAll.addAll(GuardAreas.getInstance().getList().values());
				for (int i = 0; i < listAll.size(); i++) {
					if (name.equals(listAll.get(i).getName())) {
						if (!guardId.equals(listAll.get(i).getId())) {
							pd1.put("status", "errname");
							pd1.put("msg", "该名称已存在");
							return  pd1;
						}
					}
				}
				
				guaedBean=GuardAreas.getInstance().get(guardId);
				guaedBean.setId(guardId);
				guaedBean.setEnable(enable);
				guaedBean.setName(name);
				guaedBean.setTriggerMode(triggerMode);
				guaedBean.setFloorId(specifyFloorId);
				guaedBean.setComments(comments);
				guaedBean.setPolygon(guaedBean.getPolygon());
				
				guaedBean.save();
			}
				pd1.put("status", "ok");
				pd1.put("msg", "操作成功");
			}else{
				//参数错误
				pd1.put("status", "err");
				pd1.put("msg", "参数错误");
			}
			return  pd1;
		}
		
		//楼层下拉列表
		public List<PageData> FloorList()
		{
			List<PageData> pdFloor = new ArrayList<>();
			List<Floor> floorList=new ArrayList<Floor>();
			floorList.addAll(Floors.getInstance().getList().values());
			for (Floor floor : floorList) {
				PageData tempPd = new PageData();
				tempPd.put("id",floor.getId());
				tempPd.put("name",floor.getName());
				pdFloor.add(tempPd);
			}
			return  pdFloor;
		}
		
		//在地图上编辑电子围栏
		@RequestMapping("/editGuardOnMap.do")
		public ModelAndView gotoGuardMap(){
			PageData pd=this.getPageData();
			ModelAndView mv=this.getModelAndView();
			String guardId=pd.getString("guardId");
			mv.addObject("guardId",guardId);
			mv.setViewName("guard_map_edit_index");
			return  mv;
			
		}
		
		//获取区域的json数据
		@RequestMapping(value = "/json_guard_byId.do")
		@ResponseBody
		public Object json_guard_byId(String guardId) {
			RTLEAPI rtapi=RTLEAPI.getInstance();
			LonLatPoint origin = new LonLatPoint(rtapi.getOrigin().getLongitude(), rtapi.getOrigin().getLatitude(), 0);
			GuardArea guard = GuardAreas.getInstance().get(guardId);
			
			if (guard.getPolygon().getVertexes().length==0) {
				Point2[] point = new Point2[4];
				point[0] = new Point2(0, 0);
				point[1] = new Point2(0, 1);
				point[2] = new Point2(1, 1);
				point[3] = new Point2(1, 0);
				Polygon polygon = new Polygon(point);
				guard.setPolygon(polygon);
				guard.save();
			}
			
			GuardArea guardArea = GuardAreas.getInstance().get(guardId);
			List<Object> coordinateList = new ArrayList<>();
			List<Object> pList = new ArrayList<>();
			for (Point2 point : guardArea.getPolygon().getVertexes()) {
				LonLatPoint lonLatPoint = Misc.meter2LonLat(origin, point.getX(), point.getY());// 将x
				List<Double> strList = new ArrayList<>();// 区域图表每个点的坐标
				strList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLongitude(), 8)));
				strList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLatitude(), 8)));
				pList.add(strList);
			}
			coordinateList.add(pList);
			
			Map<String, Object> map = new HashMap<>();
			map.put("type", "FeatureCollection");
			Map<String, Object> crsMap = new HashMap<>();// crs
			crsMap.put("type", "name");
			Map<String, Object> propertieMap = new HashMap<>();// properties
			propertieMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
			crsMap.put("properties", propertieMap);
			map.put("crs", crsMap);

			List<Map<String, Object>> featureList = new ArrayList<>();
			
				// 区域的数据
				Map<String, Object> featureMap = new HashMap<>();
				featureMap.put("type", "Feature");
				featureMap.put("id", "001");
				Map<String, Object> propertyMap = new HashMap<>();// properties
				propertyMap.put("type", "area");
				propertyMap.put("name", guardArea.getName());
				featureMap.put("properties", propertyMap);
				Map<String, Object> geometryMap = new HashMap<>(); // geometry
				geometryMap.put("type", "Polygon");
				geometryMap.put("coordinates", coordinateList);
				featureMap.put("geometry", geometryMap);
				featureList.add(featureMap);
	
			map.put("features", featureList);
			return map;
		}
		
		/**
		 * 保存区域的地图数据
		 * @return
		 * @throws JsonParseException
		 * @throws JsonMappingException
		 * @throws IOException
		 */
		@RequestMapping(value="/saveGuardMapData.do")
		@ResponseBody
		public Object saveGuardMapData(String guardId) throws JsonParseException, JsonMappingException, IOException
		{
			GuardArea guardArea = GuardAreas.getInstance().get(guardId);
			PageData pdret=new PageData();
			RTLEAPI rtleApi=RTLEAPI.getInstance();
			LonLatPoint origin = new LonLatPoint(rtleApi.getOrigin().getLongitude(), rtleApi.getOrigin().getLatitude(),0);
			logBefore(logger, "保存在地图上配置的区域数据");
			PageData pd = this.getPageData();
			String[] jsons = pd.getString("json").split("\\|");
			String areaClockJson = jsons[0];// 区域的数据
			ObjectMapper mapper = new ObjectMapper();
			// 处理区域
			@SuppressWarnings("unchecked")
			Map<String, Object> mapArea = mapper.readValue(areaClockJson, Map.class);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> featureListArea = (List<Map<String, Object>>) mapArea.get("features");
			for (Map<String, Object> featureMap : featureListArea) {
				@SuppressWarnings("unchecked")
				Map<String, Object> geometryMap = (Map<String, Object>) featureMap.get("geometry");
					//区域
					List<Point2> listPoint2 = new ArrayList<>();
					List<Area> areaList=new ArrayList<Area>();
					areaList.addAll(Areas.getInstance().getList().values());
							@SuppressWarnings("unchecked")
							List<List<List<Double>>> coordinateListDouble = (List<List<List<Double>>>) geometryMap.get("coordinates");
							List<List<Double>> listDouble = coordinateListDouble.get(0);
							for (List<Double> tempListDouble : listDouble) {
								// 取到经纬度
								LonLatPoint lonLatPoint = new LonLatPoint(tempListDouble.get(0), tempListDouble.get(1),0);
								// 转换为米
								Point2 point2 = Misc.lonLat2Meter(origin, lonLatPoint);
								listPoint2.add(point2);
							}
							// 将ArrayList转换成Point2数组
							Point2[] point2 = new Point2[listPoint2.size()];
							for (int i = 0; i < listPoint2.size(); i++) {
								point2[i] = listPoint2.get(i);
							}
							guardArea.setPolygon(new Polygon(point2));

						guardArea.save();
			
			}
			pdret.put("status", "ok");
			pdret.put("msg", "操作成功");
			return pdret;
		}
		
		//删除电子围栏功能处理
		@RequestMapping(value="/delete.do")
		@ResponseBody
		public Object deleteArea(){
			PageData pd=getPageData();
			String guardId=pd.get("guardId").toString();
			GuardAreas.getInstance().remove(guardId);
			PageData pd1=new PageData();
			pd1.put("status", "ok");
			pd1.put("msg", "删除成功");
			return pd1;
		}
		
		//构造触发定位的选择列表
		public List<PageData> locationTriggerData()
		{
			List<PageData> pdListLQ=new ArrayList<PageData>();
			PageData pdLTQuery1 = new PageData();
			PageData pdLTQuery2 = new PageData();
			PageData pdLTQuery3 = new PageData();
			pdLTQuery1.put("id","1");
			pdLTQuery1.put("name","in");
			pdLTQuery2.put("id","2");
			pdLTQuery2.put("name","out");
			pdLTQuery3.put("id","3");
			pdLTQuery3.put("name","in_out");
			pdListLQ.add(pdLTQuery1);
			pdListLQ.add(pdLTQuery2);
			pdListLQ.add(pdLTQuery3);
			return pdListLQ;
		}

}
