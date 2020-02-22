package com.uwbhome.pm.controller.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uwbhome.rtle.api.Anchor;
import com.uwbhome.rtle.api.Anchors;
import com.uwbhome.pm.controller.BaseController;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.Area;
import com.uwbhome.rtle.api.Areas;
import com.uwbhome.rtle.api.Basemap;
import com.uwbhome.rtle.api.Basemaps;
import com.uwbhome.rtle.api.ClockSource;
import com.uwbhome.rtle.api.Floor;
import com.uwbhome.rtle.api.Floors;
import com.uwbhome.rtle.api.RTLEAPI;
import com.uwbhome.rtle.api.UserFile;
import com.uwbhome.rtle.api.UserFiles;
import com.uwbhome.rtle.comm.LonLatPoint;
import com.uwbhome.rtle.comm.Point2;
import com.uwbhome.rtle.utils.Misc;

/** 
* @version 创建时间：2020年1月16日 下午2:07:13
* 响应实时定位显示楼层
*/
@Controller
@RequestMapping(value = "/rtlsConfig")
public class RtlsConfigController extends BaseController {
	/**
	 * 根据楼层id获取区域列表
	 * @param floorId
	 * @return
	 */
	@RequestMapping("/json_areas")
	@ResponseBody
	public Object json_areas(String floorId) {
		LonLatPoint origin = new LonLatPoint(RTLEAPI.getInstance().getOrigin().getLongitude(),
				RTLEAPI.getInstance().getOrigin().getLatitude(), 0);
		Map<String, Object> map = new HashMap<>();
		map.put("type", "FeatureCollection");
		Map<String, Object> crsMap = new HashMap<>();// crs
		crsMap.put("type", "name");
		Map<String, Object> propertieMap = new HashMap<>();// properties
		propertieMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		crsMap.put("properties", propertieMap);
		map.put("crs", crsMap);
		
		List<Map<String, Object>> featureList = new ArrayList<>();
		Floor floor = Floors.getInstance().get(floorId);
		ArrayList<String> areaIdList = new ArrayList<String>();
		areaIdList.addAll(floor.getAreaIds());
		List<Area> areaList = new ArrayList<Area>();
		if(areaIdList.size() > 0 ) {
			for(String id : areaIdList) {
				Area area = Areas.getInstance().get(id);
				if(area != null && !area.isEnable()) {
					continue;
				}
				areaList.add(area);
			}
		}
		List<ClockSource> clockSouceList = new ArrayList<ClockSource>();
		for(Area area : areaList) {
			if(area != null) {
				clockSouceList.add(area.getClockSource());
				for(ClockSource clockSource : clockSouceList) {
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
			
		}
		
		map.put("features", featureList);
		return map;
		
	}
	
	// 获取中心点查相近平面图
	@RequestMapping(value = "/getRtleapi.do")
	@ResponseBody
	public Object getRtleapi(HttpServletRequest request, String floorId) {
		Floor floor2 = Floors.getInstance().get(floorId);
		ArrayList<String> basemapIds = floor2.getBasemapIds();
		List<PageData> retbp = new ArrayList<PageData>();
		LonLatPoint origin = RTLEAPI.getInstance().getOrigin();
		LonLatPoint lonat = new LonLatPoint(origin.getLongitude(), origin.getLatitude());
		Point2 lonLat2Meter = Misc.lonLat2Meter(origin, lonat);
		List<Floor> floorlist = new ArrayList<Floor>();
		floorlist.addAll(Floors.getInstance().getList().values());
		for (String basemap : basemapIds) {
			Basemap bmap = Basemaps.getInstance().get(basemap);
			PageData bptemp = new PageData();
			if (bmap == null) {
				break;
			} else {
				double centerX = bmap.getCenterX();
				double centerY = bmap.getCenterY();
				LonLatPoint lonat1 = new LonLatPoint(centerX, centerY);
				Point2 lonLat2Meter1 = Misc.lonLat2Meter(origin, lonat1);
				double pow = Math.pow(lonLat2Meter.getX() - lonLat2Meter1.getX(), 2);
				double pow2 = Math.pow(lonLat2Meter.getY() - lonLat2Meter1.getY(), 2);
				double xy = Math.sqrt(pow + pow2);
				if (xy <= 10) {
					Basemap bp = Basemaps.getInstance().get(bmap.getId());
					String basemapFile = bp.getBasemapBitmapFilename();
					UserFile uf = UserFiles.getInstance().get(basemapFile);
					String ufstring = getPicData(uf);
					bptemp.put("bp", bp);
					bptemp.put("ufstring", ufstring);
					bptemp.put("floor", floor2);
					retbp.add(bptemp);
				}
			}
		}

		return retbp;
	}
	/**
	 * 获取JSON数据_根据楼层ID查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/json_anchors_byFloorId")
	@ResponseBody
	public Object json_anchors_byFloorId(String floorId) throws Exception {
		Floor floor = Floors.getInstance().get(floorId);
		ArrayList<String> areaIdList = new ArrayList<String>();
		areaIdList = floor.getAreaIds();
		List<Area> areaList = new ArrayList<Area>();
		List<String> anchorIdList = new ArrayList<String>();
		List<Anchor> anchorList = new ArrayList<Anchor>();
		Hashtable<String, Anchor> okHt=new Hashtable<>();
		if(areaIdList.size() > 0) {
			for(String id : areaIdList) {
				areaList.add(Areas.getInstance().get(id));
			}
			for(Area area : areaList) {
				if(area != null && area.isEnable()) {
					anchorIdList.addAll(area.getSpecifyAnchorsId());
				}
			}
		}
		for(String anchorId : anchorIdList) {
			Anchor anchor = Anchors.getInstance().get(anchorId);
			if(anchor != null) {
				anchorList.add(anchor);
			}
				//continue;
			okHt.put(anchorId, anchor);//用hashtable，防止重复，避免基站共享的情况
		}
		
		LonLatPoint origin = new LonLatPoint(RTLEAPI.getInstance().getOrigin().getLongitude(), RTLEAPI.getInstance().getOrigin().getLatitude(),0);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", "FeatureCollection");
		Map<String, Object> crsMap = new HashMap<>();// crs
		crsMap.put("type", "name");
		Map<String, Object> propertyMap = new HashMap<>();
		propertyMap.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
		crsMap.put("properties", propertyMap);
		map.put("crs", crsMap);
		List<Object> featureList = new ArrayList<>();
		for(Anchor anchor : anchorList) {
			String aid = anchor.getId();
			if(!okHt.containsKey(aid)) {
				System.out.println("这个基站不在该楼层，所以去掉");
				continue;//如果不在表中的基站都不添加
			}
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
		System.out.println("有"+featureList.size()+"个基站是ok的");
		map.put("features", featureList);
		return map;
	}
	/**
	 * 获取JSON数据_根据楼层ID查询楼层平面图
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getJsonByFloorId.do")
	@ResponseBody
	public Object getJsonByFloorId(String floorId) throws Exception{
		List<PageData> retbp=new ArrayList<PageData>();	
		LonLatPoint origin=RTLEAPI.getInstance().getOrigin();
		LonLatPoint lonat = new LonLatPoint(origin.getLongitude(), origin.getLatitude());
		Point2 lonLat2Meter = Misc.lonLat2Meter(origin, lonat);
		ArrayList<String> basemapIds = new ArrayList<String>();
		if(floorId != null) {
			Floor floor = Floors.getInstance().get(floorId);
			if(floor.getBasemapIds() != null) {
				basemapIds.addAll(floor.getBasemapIds());
				if(basemapIds.size() > 0) {
					for(String id : basemapIds) {
						Basemap bmap = Basemaps.getInstance().get(id);
						PageData bptemp  = new PageData();
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
								bptemp.put("floor", floor);
								retbp.add(bptemp);
						}
						}
					}
				}
			}
		}
			return retbp;
	}
	//获取所有楼层名称
	@RequestMapping("getFloorName.do")
	@ResponseBody
	public Object getFloorName() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> floorAllNameList = new ArrayList<String>();
		Collection<Floor> floorColl = Floors.getInstance().getList().values();
		for(Floor floor : floorColl) {
			floorAllNameList.add(floor.getName());
		}
		if(floorAllNameList.size() == 0) {
			map.put("msg", "楼层未添加平面图");
		}
		map.put("floorNames", floorAllNameList);
		return map;
	}
	// 返回图片的数据
		public String getPicData(UserFile uf) {
			String retufstring = "";
			String ufstring = Base64.encodeBase64String(uf.getFileContent());
			String fname = uf.getFileName();
			int pos1 = fname.indexOf(".") + 1;
			String fileSuffix = fname.substring(pos1);// 文件名后缀
			switch (fileSuffix) {
			case "png":
				retufstring = "data:image/png;base64," + ufstring;
				break;
			case "jpg":
				retufstring = "data:image/jpg;base64," + ufstring;
				break;
			default:
				retufstring = "-1";
				break;
			}
			return retufstring;
		}
}
