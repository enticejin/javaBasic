package com.uwbhome.pm.controller;
/**
 * RTLE的控制器
 * @author xu.yuanli
 *
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.RTLEAPI;
import com.uwbhome.rtle.comm.LonLatPoint;
/**
 * rtle 设置
 * @author xu.yuanli
 *
 */
@Controller
@RequestMapping("/rtle")
public class RtleController extends BaseController {
	
	
	//打开rtle主界面
	@RequestMapping("/rtle_editIndex.do")
	public ModelAndView editRtle()
	{
		logBefore(logger, "信息RTLE");

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		RTLEAPI rtleapi=RTLEAPI.getInstance();
		System.out.println("rtleapi.getOrigin().getLongitude():"+rtleapi.getOrigin().getLongitude());
		pd.put("longitude", rtleapi.getOrigin().getLongitude());
		pd.put("latitude", rtleapi.getOrigin().getLatitude());
		pd.put("useMeter", rtleapi.isUseMeter());
		
		pd.put("showRangeMessageInfo", rtleapi.isShowRangeMessageDebugInfo());
		pd.put("showClockSourceMessageInfo",rtleapi.isShowCSSyncMessageDebugInfo());
		
		mv.setViewName("rtle_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 保存RTLS配置
	 */
	@RequestMapping(value = "/saveRtls.do")
	public ModelAndView save() throws Exception {
		logBefore(logger, "修改rtle配置");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Double longitude = Double.valueOf(pd.get("longitude").toString());
		Double latitude = Double.valueOf((pd.get("latitude").toString()));
		LonLatPoint newOr=new LonLatPoint(longitude,latitude);
		// 修改RTLE配置
		RTLEAPI rtleapi=RTLEAPI.getInstance();
		rtleapi.setOrigin(newOr);
		rtleapi.setUseMeter(Boolean.valueOf(pd.getString("useMeterValue")));
		rtleapi.setShowRangeMessageDebugInfo(Boolean.valueOf(pd.getString("showRangeMessageInfoValue")));
		rtleapi.setShowCSSyncMessageDebugInfo(Boolean.valueOf(pd.getString("showClockSourceMessageInfoValue")));
		
		/* System.out.println("页面提交的数据："+longitude+","+latitude); */
		//保存到引擎中去
		rtleapi.save();
		//构造页面需要的数据 
		pd = new PageData();
		pd.put("longitude", longitude);
		pd.put("latitude", latitude);
		pd.put("useMeter", rtleapi.isUseMeter());
		pd.put("showRangeMessageInfo", rtleapi.isShowRangeMessageDebugInfo());
		pd.put("showClockSourceMessageInfo", rtleapi.isShowCSSyncMessageDebugInfo());
	
		mv.setViewName("rtle_edit");
		mv.addObject("pd", pd);
		return mv;
	}

}
