package com.uwbhome.pm.controller.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

@Controller
@RequestMapping("/coordinate")
public class TestLCRealITmeTrackController extends BaseController {
	/**
	 * 将坐标显示在demo页面
	 * 
	 * @author xu.yuanli
	 *
	 */
	// 打开坐标演示页面
	@RequestMapping("/showRealTimeTrack.do")
	public ModelAndView showRealTimeTrack() {
		ModelAndView mv = this.getModelAndView();
		List<PageData> retbp = new ArrayList<PageData>();
		// 通过api获取经纬度
		LonLatPoint origin = RTLEAPI.getInstance().getOrigin();
		LonLatPoint lonat = new LonLatPoint(origin.getLongitude(), origin.getLatitude());
		// 将经纬度加入数组
		Point2 lonLat2Meter = Misc.lonLat2Meter(origin, lonat);
		// 获取所有楼层信息
		List<Floor> floorlist = new ArrayList<Floor>();
		floorlist.addAll(Floors.getInstance().getList().values());
		Set<Object> floorIds = new HashSet<>();
		for (Floor floor : floorlist) {
			// 通过楼层信息加载平面图
			Floor floor2 = Floors.getInstance().get(floor.getId());
			ArrayList<String> basemapIds = floor2.getBasemapIds();
			// 遍历平面图ID
			for (String basemap : basemapIds) {
				Basemap bmap = Basemaps.getInstance().get(basemap);
				if (bmap == null) {
					break;
				} else {
					PageData bptemp = new PageData();
					// 获取X，Y坐标
					double centerX = bmap.getCenterX();
					double centerY = bmap.getCenterY();
					LonLatPoint lonat1 = new LonLatPoint(centerX, centerY);
					Point2 lonLat2Meter1 = Misc.lonLat2Meter(origin, lonat1);
					double pow = Math.pow(lonLat2Meter.getX() - lonLat2Meter1.getX(), 2);
					double pow2 = Math.pow(lonLat2Meter.getY() - lonLat2Meter1.getY(), 2);
					// 计算距离
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
						floorIds.add(floor2.getId());
					}
				}
			}
		}
		//先获取一个楼层
		if(floorlist.get(0) != null) {
			mv.addObject("floor", floorlist.get(0));
		}
		//所有楼层
		mv.addObject("allFloorList", floorlist);
		mv.addObject("retbp", retbp);
		mv.addObject("floorIds", floorIds);
		mv.setViewName("test");
		return mv;
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
