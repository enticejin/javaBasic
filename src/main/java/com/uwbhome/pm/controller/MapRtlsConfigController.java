package com.uwbhome.pm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.Anchor;
import com.uwbhome.rtle.api.Anchors;
import com.uwbhome.rtle.api.Area;
import com.uwbhome.rtle.api.Areas;
import com.uwbhome.rtle.api.Basemap;
import com.uwbhome.rtle.api.Basemaps;
import com.uwbhome.rtle.api.ClockSource;
import com.uwbhome.rtle.api.ClockSources;
import com.uwbhome.rtle.api.Floor;
import com.uwbhome.rtle.api.Floors;
import com.uwbhome.rtle.api.GuardArea;
import com.uwbhome.rtle.api.GuardAreas;
import com.uwbhome.rtle.api.RTLEAPI;
import com.uwbhome.rtle.api.UserFile;
import com.uwbhome.rtle.api.UserFiles;
import com.uwbhome.rtle.comm.LonLatPoint;
import com.uwbhome.rtle.comm.Point2;
import com.uwbhome.rtle.comm.Polygon;
import com.uwbhome.rtle.utils.Misc;

@Controller
@RequestMapping("/map")
public class MapRtlsConfigController extends BaseController {
	
	/**
	 * 获取当前定位区域中的区域数据
	 */
	@RequestMapping(value="/getMapDataForArea.do")
	@ResponseBody
	public Object getMapDataForArea()
	{
		RTLEAPI rtleapi=RTLEAPI.getInstance(); 
		LonLatPoint origin = new LonLatPoint(rtleapi.getOrigin().getLongitude(), rtleapi.getOrigin().getLatitude(), 0);
		Map<String, Object> map = new HashMap<>();
		map.put("type", "FeatureCollection");
		Map<String, Object> crsMap = new HashMap<>();// crs
		crsMap.put("type", "name");
		Map<String, Object> propertieMap = new HashMap<>();// properties
		propertieMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		crsMap.put("properties", propertieMap);
		map.put("crs", crsMap);
		List<Map<String, Object>> featureList = new ArrayList<>();
		List<Area> areaList=new ArrayList<Area>();
		areaList.addAll(Areas.getInstance().getList().values());
		for (Area area : areaList) {
			if(!area.isEnable())
			{
				//如果该区域没有激活，就不用加载到地图上
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
				LonLatPoint lonLatPoint = Misc.meter2LonLat(origin, area.getClockSource().getX(),area.getClockSource().getY());
				coordinateList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLongitude(), 8)));
				coordinateList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLatitude(), 8)));
				geometryMap.put("coordinates", coordinateList);
				featureMap.put("geometry", geometryMap);
				featureList.add(featureMap);
			}
			// 区域
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
				LonLatPoint lonLatPoint = Misc.meter2LonLat(origin, point.getX(), point.getY());// 将x y坐标转换为经纬度
				List<Double> strList = new ArrayList<>();// 区域图表每个点的坐标
				strList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLongitude(), 8)));
				strList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLatitude(), 8)));
				pList.add(strList);
			}
			coordinateList.add(pList);
			geometryMap.put("coordinates", coordinateList);
			featureMap.put("geometry", geometryMap);
			featureList.add(featureMap);
		}
		map.put("features", featureList);
		return map;
	}
	/**
	 * 获取区域中的基站
	 */
	@RequestMapping(value="/getMapDataForAnchor.do")
	@ResponseBody
	public Object getMapDataForAnchor()
	{
		RTLEAPI rtleapi=RTLEAPI.getInstance();
		LonLatPoint origin = new LonLatPoint(rtleapi.getOrigin().getLongitude(), rtleapi.getOrigin().getLatitude(), 0);
		Map<String, Object> map = new HashMap<>();
		map.put("type", "FeatureCollection");
		Map<String, Object> crsMap = new HashMap<>();// crs
		crsMap.put("type", "name");
		Map<String, Object> propertyMap = new HashMap<>();
		propertyMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		crsMap.put("properties", propertyMap);
		map.put("crs", crsMap);
		List<Anchor> anchorList=new ArrayList<Anchor>();
		anchorList.addAll(Anchors.getInstance().getList().values());
		List<Object> featureList = new ArrayList<>();
		for (Anchor anchor : anchorList) {
			//此处应该将不是属于任何区域的基站排除在外
			if(!isbelongToArea(anchor))
			continue;
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
	   * 基站是否属于某个区域
	 * @param anchor
	 * @return
	 */
	public boolean isbelongToArea(Anchor anchor)
	{
		boolean ret=false;
		if(anchor==null)
		return ret;
		String targetAnchorid=anchor.getId();
		List<Area> areaList=new ArrayList<Area>();
		areaList.addAll(Areas.getInstance().getList().values());
		for (Area area : areaList) {
			List<String> anchorIds=area.getSpecifyAnchorsId();
			for (String aid : anchorIds) {
				if(targetAnchorid.equals(aid))
				{
					ret=true;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 保存区域的地图数据
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/saveAreaMapData.do")
	@ResponseBody
	public Object saveAreaMapData() throws JsonParseException, JsonMappingException, IOException
	{
		PageData pdret=new PageData();
		RTLEAPI rtleApi=RTLEAPI.getInstance();
		LonLatPoint origin = new LonLatPoint(rtleApi.getOrigin().getLongitude(), rtleApi.getOrigin().getLatitude(),0);
		logBefore(logger, "保存在地图上配置的区域、基站数据");
		PageData pd = this.getPageData();
		String[] jsons = pd.getString("json").split("\\|");
		String areaClockJson = jsons[0];// 区域时钟的数据
		String anchorJson = jsons[1];// 基站的数据
		ObjectMapper mapper = new ObjectMapper();
		// 处理区域
		@SuppressWarnings("unchecked")
		Map<String, Object> mapArea = mapper.readValue(areaClockJson, Map.class);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> featureListArea = (List<Map<String, Object>>) mapArea.get("features");
		for (Map<String, Object> featureMap : featureListArea) {
			String id = featureMap.get("id").toString();
			
			@SuppressWarnings("unchecked")
			Map<String, Object> geometryMap = (Map<String, Object>) featureMap.get("geometry");
			
			@SuppressWarnings("unchecked")
			List<Double> coordinateList = (List<Double>) geometryMap.get("coordinates");
			// 判断是时钟还是区域
			if (id != null && id.indexOf("CS_") != -1) {
				List<ClockSource> clockSourceList=new ArrayList<ClockSource>();
				clockSourceList.addAll(ClockSources.getInstance().getList().values());
				for (ClockSource clockSource : clockSourceList) {
					String clockSourceId = clockSource.getId();
					if (id.indexOf(clockSourceId) != -1) {// 找到时钟
						// 取到经纬度
						LonLatPoint lonLatPoint = new LonLatPoint(coordinateList.get(0), coordinateList.get(1), 0);
						// 转换为米
						Point2 point2 = Misc.lonLat2Meter(origin, lonLatPoint);
						clockSource.setX(point2.getX());
						clockSource.setY(point2.getY());
						clockSource.save();
					}
				}
			} else {// 区域
				List<Point2> listPoint2 = new ArrayList<>();
				List<Area> areaList=new ArrayList<Area>();
				areaList.addAll(Areas.getInstance().getList().values());
				for (Area area : areaList) {
					String areaId = area.getId();
					if (id != null && id.equals(areaId)) {// 找到区域
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
						area.setPolygon(new Polygon(point2));

					}
					area.save();
				}
			}
		}
		// 处理基站
		@SuppressWarnings("unchecked")
		Map<String, Object> mapAnchor = mapper.readValue(anchorJson, Map.class);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> featureListAnchor = (List<Map<String, Object>>) mapAnchor.get("features");
		for (Map<String, Object> featureMap : featureListAnchor) {
			String id = featureMap.get("id").toString();
			@SuppressWarnings("unchecked")
			Map<String, Object> geometryMap = (Map<String, Object>) featureMap.get("geometry");
			@SuppressWarnings("unchecked")
			List<Double> coordinateList = (List<Double>) geometryMap.get("coordinates");
			List<Anchor> anchorList=new ArrayList<Anchor>();
			anchorList.addAll(Anchors.getInstance().getList().values());
			for (Anchor anchor : anchorList) {
				// 查找基站
				if (id != null && id.equals(anchor.getId())) {
					// 取到经纬度
					LonLatPoint lonLatPoint = new LonLatPoint(coordinateList.get(0), coordinateList.get(1), 0);
					// 转换为米
					Point2 point2 = Misc.lonLat2Meter(origin, lonLatPoint);
					anchor.setX(point2.getX());
					anchor.setY(point2.getY());
					anchor.save();
				}
			}
		}
		pdret.put("status", "ok");
		pdret.put("msg", "操作成功");
		return pdret;
	}
	//根据区域获取平面图
	//获取楼层平面图
	@RequestMapping(value="/getPlanMapJson.do")
	@ResponseBody
	public Object getPlanMapData(HttpServletRequest request)
	{
			PageData pd=this.getPageData();
			String areaId=pd.getString("areaId");
			Collection<Floor>  floorList=Floors.getInstance().getList().values();
			//查出该区域属于哪一个楼层
			Floor selectFloor=null;
			for (Floor floor : floorList) {
				if(floor.getAreaIds().contains(areaId)) 
				{
					selectFloor=floor;
					break;
				}
			}
			ArrayList<String> baseMapIdList=null;
			if(selectFloor!=null)
			{
				baseMapIdList=selectFloor.getBasemapIds();
			}else
			{
				//该区域未划分楼层，请先划分楼层
				
			}
			//地图加载平面图时候，就按设置平面图基本信息时的坐标放置，如果有2个平面图，那么就会在地图上添加2个层，一个平面图是一个层
			List<PageData> retbp=new ArrayList<PageData>();
			if(null!=baseMapIdList&&baseMapIdList.size()!=0)
			{
				for (String bid : baseMapIdList) {
					PageData bptemp=new PageData();
					String palnMapId=bid;
					Basemap bp=Basemaps.getInstance().get(palnMapId);
					String basemapFile=bp.getBasemapBitmapFilename();
					UserFile uf=UserFiles.getInstance().get(basemapFile);
					String ufstring=getPicData(uf);
					bptemp.put("bp", bp);
					bptemp.put("ufstring", ufstring);
					retbp.add(bptemp);
				}
			}
			return retbp;
	}
	//根据区域获取平面图
	//获取楼层平面图
	@RequestMapping(value="/getTestPlanMapJson.do")
	@ResponseBody
	public Object getTestPlanMapData(HttpServletRequest request)
		{
			List<PageData> retbp=new ArrayList<PageData>();				
			PageData bptemp=new PageData();
			LonLatPoint lonlatPoint=RTLEAPI.getInstance().getOrigin();
			Point2 p2=	GuardAreas.getInstance().get("222").getPolygon().getVertexes()[0];
			
			Basemap bp=Basemaps.getInstance().get("0008D30000000006");
			String basemapFile=bp.getBasemapBitmapFilename();
			UserFile uf=UserFiles.getInstance().get(basemapFile);
			String ufstring=getPicData(uf);
			bptemp.put("bp", bp);
			bptemp.put("ufstring", ufstring);
			retbp.add(bptemp);
			return retbp;
		}
	
	//根据电子围栏id查询该楼层里所有的平面图
	@RequestMapping(value="/getGuardPlanMapData.do")
	@ResponseBody
	public Object getGuardPlanMapData(HttpServletRequest request,String guardId){
			List<PageData> retbp=new ArrayList<PageData>();		
			LonLatPoint origin=RTLEAPI.getInstance().getOrigin();		
			GuardArea guardArea = GuardAreas.getInstance().get(guardId);
			Point2[] vertexes = guardArea.getPolygon().getVertexes();
			Point2 point2 = vertexes[0];		
			Floor floor = Floors.getInstance().get(guardArea.getFloorId());		
			ArrayList<String> basemapIds = floor.getBasemapIds();	
			List<Basemap> baselist = new ArrayList<Basemap>();
			for (String baseid : basemapIds) {
				Basemap basemap = Basemaps.getInstance().get(baseid);
				baselist.add(basemap);
			}
			for (Basemap bmap : baselist) {
				PageData bptemp=new PageData();
				double centerX = bmap.getCenterX();
				double centerY = bmap.getCenterY();	
				LonLatPoint lonat = new LonLatPoint(centerX, centerY);
				Point2 lonLat2Meter = Misc.lonLat2Meter(origin, lonat);
				double pow = Math.pow(point2.getX()-lonLat2Meter.getX(), 2);
				double pow2 = Math.pow(point2.getY()-lonLat2Meter.getY(), 2);
				double xy = Math.sqrt(pow+pow2);			
				if (xy<=100) {
					Basemap bp=Basemaps.getInstance().get(bmap.getId());
					String basemapFile=bp.getBasemapBitmapFilename();
					UserFile uf=UserFiles.getInstance().get(basemapFile);
					String ufstring=getPicData(uf);
					bptemp.put("bp", bp);
					bptemp.put("ufstring", ufstring);
					retbp.add(bptemp);
				}
				System.out.println(xy);
			}
			
			return retbp;
		}
	
	/**
	 * 坐标轴的数据
	 * @return
	 */
	@RequestMapping(value = "/json_coordinate_axis.do")
	@ResponseBody
	public Object json_coordinate_axis() {
		Map<String, Object> map = new HashMap<>();
		map.put("type", "FeatureCollection");
		Map<String, Object> crsMap = new HashMap<>();
		crsMap.put("type", "name");
		Map<String, Object> propertyMap = new HashMap<>();
		propertyMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		crsMap.put("properties", propertyMap);
		map.put("crs", crsMap);
		List<Object> featureList = new ArrayList<>();
		// X 坐标轴
		Map<String, Object> featureXMap = new HashMap<>();
		featureXMap.put("type", "Feature");
		featureXMap.put("id", "x_axis");
		Map<String, Object> propertyXMap = new HashMap<>();
		propertyXMap.put("id", "x_axis");
		propertyXMap.put("name", "X 坐标轴");
		featureXMap.put("properties", propertyXMap);
		Map<String, Object> geometryXMap = new HashMap<>();
		geometryXMap.put("type", "LineString");
		List<Object> coordinateXList = new ArrayList<Object>();
		List<Double> xxList = new ArrayList<>();
		RTLEAPI api=RTLEAPI.getInstance();
		xxList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLongitude() - 0.01, 8)));
		xxList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLatitude(), 8)));
		coordinateXList.add(xxList);
		List<Double> xyList = new ArrayList<>();
		xyList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLongitude() + 0.01, 8)));
		xyList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLatitude(), 8)));
		coordinateXList.add(xyList);
		geometryXMap.put("coordinates", coordinateXList);
		featureXMap.put("geometry", geometryXMap);
		featureList.add(featureXMap);

		// Y 坐标轴
		Map<String, Object> featureYMap = new HashMap<>();
		featureYMap.put("type", "Feature");
		featureYMap.put("id", "y_axis");
		Map<String, Object> propertyYMap = new HashMap<>();
		propertyYMap.put("id", "y_axis");
		propertyYMap.put("name", "Y 坐标轴");
		featureYMap.put("properties", propertyYMap);
		Map<String, Object> geometryYMap = new HashMap<>();
		geometryYMap.put("type", "LineString");
		List<Object> coordinateYList = new ArrayList<Object>();
		List<Double> yxList = new ArrayList<>();
		yxList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLongitude(), 8)));
		yxList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLatitude() - 0.01, 8)));
		coordinateYList.add(yxList);
		List<Double> yyList = new ArrayList<>();
		yyList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLongitude(), 8)));
		yyList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLatitude() + 0.01, 8)));
		coordinateYList.add(yyList);
		geometryYMap.put("coordinates", coordinateYList);
		featureYMap.put("geometry", geometryYMap);
		featureList.add(featureYMap);

		// 原点
		Map<String, Object> featureOMap = new HashMap<>();
		featureOMap.put("type", "Feature");
		featureOMap.put("id", "coordinate_origin");
		Map<String, Object> propertyOMap = new HashMap<>();
		propertyOMap.put("id", "coordinate_origin");
		propertyOMap.put("name", "原点");
		featureOMap.put("properties", propertyOMap);
		Map<String, Object> geometryOMap = new HashMap<>();
		geometryOMap.put("type", "Point");
		List<Double> coordinateOList = new ArrayList<>();
		coordinateOList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLongitude(), 8)));
		coordinateOList.add(Double.valueOf(Misc.fixedWidthDoubletoString(api.getOrigin().getLatitude(), 8)));
		geometryOMap.put("coordinates", coordinateOList);
		featureOMap.put("geometry", geometryOMap);
		featureList.add(featureOMap);
		map.put("features", featureList);
		return map;
	}
	
	//返回图片的数据
	public String getPicData(UserFile uf)
	{
			String retufstring="";
			String ufstring=Base64.encodeBase64String(uf.getFileContent());
			String fname=uf.getFileName();
			int pos1=fname.indexOf(".")+1;
			String fileSuffix=fname.substring(pos1);//文件名后缀
			switch (fileSuffix) {
			case "png":
				retufstring="data:image/png;base64,"+ufstring;
				break;
			case "jpg":
				retufstring="data:image/jpg;base64,"+ufstring;
				break;
			default:
				retufstring="-1";
				break;
			}
			return retufstring;
	}
	
	//获取中心点查相近平面图
	@RequestMapping(value="/getRtleapi.do")
	@ResponseBody
	public Object getRtleapi(HttpServletRequest request){
		List<PageData> retbp=new ArrayList<PageData>();	
		LonLatPoint origin=RTLEAPI.getInstance().getOrigin();
		LonLatPoint lonat = new LonLatPoint(origin.getLongitude(), origin.getLatitude());
		Point2 lonLat2Meter = Misc.lonLat2Meter(origin, lonat);
		List<Floor> floorlist = new ArrayList<Floor>();
		floorlist.addAll(Floors.getInstance().getList().values());
		for (Floor floor : floorlist) {
			Floor floor2 = Floors.getInstance().get(floor.getId());
			ArrayList<String> basemapIds = floor2.getBasemapIds();
			for (String basemap : basemapIds) {
				Basemap bmap = Basemaps.getInstance().get(basemap);			
				PageData bptemp=new PageData();
				if(bmap == null) {
					break;
				}else {
					double centerX = bmap.getCenterX();
					double centerY = bmap.getCenterY();
					LonLatPoint lonat1 = new LonLatPoint(centerX, centerY);
					Point2 lonLat2Meter1 = Misc.lonLat2Meter(origin, lonat1);
					double pow = Math.pow(lonLat2Meter.getX()-lonLat2Meter1.getX(), 2);
					double pow2 = Math.pow(lonLat2Meter.getY()-lonLat2Meter1.getY(), 2);
					double xy = Math.sqrt(pow+pow2);
					if (xy<=10) {
						Basemap bp=Basemaps.getInstance().get(bmap.getId());
						String basemapFile=bp.getBasemapBitmapFilename();
						UserFile uf=UserFiles.getInstance().get(basemapFile);
						String ufstring=getPicData(uf);
						bptemp.put("bp", bp);
						bptemp.put("ufstring", ufstring);
						bptemp.put("floor", floor2);
						retbp.add(bptemp);
					}
					System.out.println(xy);
				}
			}
		}
			
			return retbp;
		}
	
	/**
	 * 获取电子围栏
	 */
	@RequestMapping(value="/getMapGuard.do")
	@ResponseBody
	public Object getMapGuard(String floorIds)
	{	
		RTLEAPI rtleapi=RTLEAPI.getInstance(); 
		LonLatPoint origin = new LonLatPoint(rtleapi.getOrigin().getLongitude(), rtleapi.getOrigin().getLatitude(), 0);
		Map<String, Object> map = new HashMap<>();
		map.put("type", "FeatureCollection");
		Map<String, Object> crsMap = new HashMap<>();
		crsMap.put("type", "name");
		Map<String, Object> propertieMap = new HashMap<>();
		propertieMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		crsMap.put("properties", propertieMap);
		map.put("crs", crsMap);
		List<Map<String, Object>> featureList = new ArrayList<>();	
		String ids = "";
		ids = floorIds.substring(1);
		ids = ids.substring(0, ids.length() - 1);
		ids = ids.replaceAll(" ", "");  
		String[] floorId = null;  
		floorId = ids.split(","); 
		List<GuardArea> guardlist = new ArrayList<GuardArea>();
		guardlist.addAll(GuardAreas.getInstance().getList().values());
		for (String id : floorId) {
			for (GuardArea guardArea : guardlist) {
				Map<String, Object> featureMap = new HashMap<>();
				featureMap.put("type", "Feature");
				featureMap.put("id", guardArea.getId());
				Map<String, Object> propertyMap = new HashMap<>();
				propertyMap.put("type", "area");
				propertyMap.put("name", guardArea.getName());
				featureMap.put("properties", propertyMap);
				Map<String, Object> geometryMap = new HashMap<>();
				geometryMap.put("type", "Polygon");
				List<Object> coordinateList = new ArrayList<>();
				List<Object> pList = new ArrayList<>();
				if (guardArea.getFloorId().equals(id)) {					
					for (Point2 point : guardArea.getPolygon().getVertexes()) {
						LonLatPoint lonLatPoint = Misc.meter2LonLat(origin, point.getX(), point.getY());// 将x y坐标转换为经纬度
						List<Double> strList = new ArrayList<>();// 区域图表每个点的坐标
						strList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLongitude(), 8)));
						strList.add(Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLatitude(), 8)));
						pList.add(strList);
					}
					coordinateList.add(pList);
					geometryMap.put("coordinates", coordinateList);
					featureMap.put("geometry", geometryMap);
					featureList.add(featureMap);
				}
			}
		}
		map.put("features", featureList);
		return map;
	}
	/**
	 * 设置中心经纬度
	 */
	@RequestMapping(value = "/mapLngLat")
	public Object mapLngLat() {
		//获取页面
		PageData pageData = this.getPageData();
		//获取RTLE配置界面的经纬度
		String longitude = pageData.getString("longitude");
		String latitude = pageData.getString("latitude");
		ModelAndView mv = this.getModelAndView();
		//将RTLE获取的经纬度传到中心纬度设置界面
		mv.addObject("longitude",longitude);
		mv.addObject("latitude",latitude);
		mv.setViewName("mapLngLat");
		return mv;
	}
}
