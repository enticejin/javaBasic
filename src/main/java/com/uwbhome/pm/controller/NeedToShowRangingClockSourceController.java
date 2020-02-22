package com.uwbhome.pm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.Anchor;
import com.uwbhome.rtle.api.Anchors;
import com.uwbhome.rtle.api.Area;
import com.uwbhome.rtle.api.Areas;
import com.uwbhome.rtle.api.ClockSource;
import com.uwbhome.rtle.api.ClockSources;

@Controller
@RequestMapping("/need_show_clock_rang")
public class NeedToShowRangingClockSourceController extends BaseController {
		//需要显示测距消息的时钟列表
		@RequestMapping("/needToShowClockList.do")
		@ResponseBody
		public Object needToShowClockList(HttpServletRequest request,Page page,String idsList){
			int showCount = this.pageSize;
			int currentPage = 0;
			try {
				showCount = Integer.parseInt(request.getParameter("page.showCount"));
				currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
			} catch (Exception e) {
			}
			ModelAndView mv = this.getModelAndView();
			
			List<ClockSource> clockList = new ArrayList<ClockSource>();
			PageData pd = new PageData();
			pd = this.getPageData();
			//时钟源ID
			String clockIds = pd.getString("idsList");
			if(null != clockIds && !"".equals(clockIds)) {
				clockIds = clockIds.trim();
				if(clockIds.contains(",")) {
					String[] clockIdss = clockIds.split(",");
					for(String id : clockIdss) {
						clockList.add(ClockSources.getInstance().get(id));
					}
					//遍历时钟列表
					for (ClockSource clockSource : clockList) {
						if (null != clockSource.getId() && clockSource.getId().contains(clockIds)) {
							clockList.add(clockSource);
						}
					}
				}else {
					clockList.add(ClockSources.getInstance().get(clockIds));
				}
				
			}else {
				clockIds = "";
			}
			//时钟源名称
			String clockSourceName = pd.getString("NAME");
			if (null != clockSourceName && !"".equals(clockSourceName)) {
				clockSourceName = clockSourceName.trim();
			} else {
				clockSourceName = "";
			}
			List<ClockSource> clockList1 = new ArrayList<ClockSource>();
			for (ClockSource clockSource : clockList) {
				if (null != clockSource.getName() && clockSource.getName().contains(clockSourceName)) {
					clockList1.add(clockSource);
				}
			}
			// 排序
			Collections.sort(clockList, new Comparator<ClockSource>() {
				@Override
				public int compare(ClockSource o1, ClockSource o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});

			// 分页
			List<ClockSource> clockListPage = new ArrayList<ClockSource>();
			int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
			for (int i = 0; i < showCount && i < clockList.size() - currIdx; i++) {
				ClockSource a = clockList.get(currIdx + i);
				clockListPage.add(a);
			}
			pd.put("ids", clockIds);
			pd.put("flag","clock");
			page.setTotalResult(clockList.size());
			page.setTotalPage(page.getTotalPage());
			page.setCurrentPage(currentPage);
			page.setShowCount(showCount);
			page.setCurrentPage(page.getCurrentPage());
			
			mv.setViewName("need_show_clock_list");
			mv.addObject("varList", clockListPage);
			mv.addObject("pd", pd);
			return mv;
		}

		//从列表中移除时钟
			@RequestMapping("/remove.do")
			@ResponseBody
			public Object remove(String clockId,String ids) {
					List<String> clockIdsList = new ArrayList<String>();
					if(ids.contains(",")) {
						if(ids != null && ids.trim().length() > 0) {
							ids=ids.trim();
							String[] id = ids.split(",");
							for(String id1 : id) {
								clockIdsList.add(id1);
							}
						}
					}
					
					if(clockIdsList.contains(clockId)) {
						clockIdsList.remove(clockId);
					}
					
					PageData pd=new PageData();
					pd.put("status", "ok");
					pd.put("msg", "移除成功");
					pd.put("idsList", clockIdsList);
					pd.put("clockSourceId", clockId);
					return pd;
				}
			
			//修改时钟坐标
			@RequestMapping(value = "/setXYZ.do")
			@ResponseBody
			public Object setAnchorXYZ(String id) {
				PageData pd = new PageData();
				PageData retpd = new PageData();
				pd = this.getPageData();
				String csWebId = pd.getString("id");
				String clockSouceIds = pd.getString("clockSouceIds");
				
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
					retpd.put("clockSouceIds", clockSouceIds);
					retpd.put("msg", "操作成功");
					
				}else
				{
					retpd.put("status", "err");
					retpd.put("msg", "操作失败");
				}
				return retpd;
			}
			//与时钟关联的区域
			@RequestMapping("/related.do")
			@ResponseBody
			public Object related(String clockId,String flag) {
				PageData pd = this.getPageData();
				String areaIds = (String) pd.get("areaIdList");
					if(areaIds == null) {
						clockId = clockId.trim();
						//获取所有区域
						Collection<Area> areaColl = Areas.getInstance().getList().values();
						List<Area> areaList =new ArrayList<Area>();
						//遍历区域查询时钟
						for(Area area : areaColl) {
							 if(area.getClockSource() == null ){
								 	pd.put("status", "ok");
									pd.put("msg", "该时钟没有关联区域");
									return pd;
								}
							 else if(area.getClockSource().getId() != null && area.getClockSource().getId().equals(clockId)) {
								areaList.add(area);
							}
						}
						List<String> areaIdList = new ArrayList<String>();
						for(Area area : areaList) {
							areaIdList.add(area.getId());
						}
						pd.put("clockId",clockId);
						pd.put("areaIdList",areaIdList.toString());
						pd.put("varList", areaList);
						ModelAndView mv = this.getModelAndView();
						mv.addObject("pd", pd);
						mv.addObject("varList", areaList);
						mv.setViewName("need_show_related_area");
						return mv;
					}else {
						List<Area> areaList = new ArrayList<Area>();
						areaIds = areaIds.trim();
						if(areaIds.contains(",")) {
							String[] ids = areaIds.split(",");
							for(String id : ids) {
								areaList.add(Areas.getInstance().get(id));
							}
						}else {
							areaList.add(Areas.getInstance().get(areaIds));
						}
						pd.put("clockId",clockId);
						pd.put("varList", areaList);
						ModelAndView mv = this.getModelAndView();
						mv.addObject("pd", pd);
						mv.addObject("varList", areaList);
						mv.setViewName("need_show_related_area");
						return mv;
						
					}
			}
			//取消时钟与区域关联
			@RequestMapping("/cancelRelated.do")
			@ResponseBody
			public Object cancelRelated(String areaId) {
				PageData pd = this.getPageData();
				//根据区域id获取区域信息
				Area area = Areas.getInstance().get(areaId);
				String clockIdByArea = area.getClockSource().getId();
				ClockSource clockSource = ClockSources.getInstance().get(clockIdByArea);
				List<Area> areaList = new ArrayList<Area>();
				Collection<Area> areaColl = Areas.getInstance().getList().values();
				for(Area area1 : areaColl) {
					if(area1.getClockSource().equals(clockSource) && area1.getId().equals(areaId)) {
						areaList.remove(area1);
						break;
					}else {
						areaList.add(area1);
					}
				}
				String clockSourceId = new String();
				List<String> areaIdList = new ArrayList<String>();
				for(Area area2 : areaList) {
					clockSourceId = area2.getClockSource().getId();
					areaIdList.add(area2.getId());
				}
				if(area.getClockSource().getId().trim().length() > 0 || area.getClockSource().getId() != null) {
					pd.put("areaIdList", areaIdList);
					pd.put("clockSourceId", clockSourceId);
					pd.put("msg", "取消关联成功");
					pd.put("status", "ok");
				}else {
					pd.put("msg", "取消关联失败");
				}
				return pd;
			}
		//保存时钟关联的区域
		@RequestMapping("/save.do")
		@ResponseBody
		public Object save() {
			PageData pd = this.getPageData();
			PageData pd1 = new PageData();
			String clockSorceIds = pd.getString("idsList");
			//保存时钟关联区域列表的逻辑
			if(pd.get("flag") != null && pd.get("flag").toString().equals("area")) {
				String areaIds = pd.get("areaIdList").toString();
				//清除id两边的中括号
				areaIds = areaIds.substring(1,areaIds.length() - 1);
				List<String> areaIdListByClock = new ArrayList<String>();
				List<Area> areaList = new ArrayList<Area>();
				if(areaIds.indexOf(",") != -1) {
					String[] areaIdss = areaIds.split(",");
					for(String id : areaIdss) {
						areaIdListByClock.add(id);
						areaList.add(Areas.getInstance().get(id));
					}
				}else {
					areaIdListByClock.add(areaIds);
					areaList.add(Areas.getInstance().get(areaIds.trim().toString()));
				}
				if(areaIdListByClock.size() == 0) {
					pd1.put("status", "errname");
					pd1.put("msg", "该时钟没有绑定任何区域，请前往区域配置页面");
				}else {
					pd1.put("status", "errname");
					pd1.put("msg", "保存成功");
				}
				System.out.println("与时钟源关联的区域id = " + areaIdListByClock);
				System.out.println("与时钟源关联的区域对象有 " + areaList);
				pd1.put("areaListByClock", areaList);
				pd1.put("areaIdListByClock", areaIdListByClock);
				return pd1;
			}else {
				Collection<Area> areaColl = Areas.getInstance().getList().values();
				//与时钟关联的区域id列表
				ArrayList<String> areaIdListByClock = new ArrayList<>();
				//与时钟关联的区域列表
				List<Area> areaListByClock = new ArrayList<Area>();
				//当一个时钟源id传过来的时候
				if(clockSorceIds.contains(",")) {
					for(Area area : areaColl) {
						if(area.getClockSource().getId().equals(clockSorceIds)) {
							areaIdListByClock.add(area.getId());
						}
					}
					if(areaIdListByClock.size() == 0) {
						pd1.put("status", "errname");
						pd1.put("msg", "该时钟没有绑定任何区域，请前往区域配置页面");
					}else {
						if(areaIdListByClock.size() > 1) {
							for(String areaId : areaIdListByClock) {
								areaListByClock.add(Areas.getInstance().get(areaId));
							}
						}else {
							areaListByClock.add(Areas.getInstance().get(areaIdListByClock.get(0)));
						}
					}
				}else {//当多个时钟源id传过来的时候
					String[] clockIdsBySplit = clockSorceIds.split(",");
					for(String id : clockIdsBySplit) {
						for(Area area : areaColl) {
							if(area.getClockSource().getId().equals(id)) {
								areaIdListByClock.add(area.getId());
							}
						}
					}
					if(areaIdListByClock.size() == 0) {
						pd1.put("status", "errname");
						pd1.put("msg", "该时钟没有绑定任何区域，请前往区域配置页面");
					}else {
						if(areaIdListByClock.size() > 1) {
							for(String areaId : areaIdListByClock) {
								areaListByClock.add(Areas.getInstance().get(areaId));
							}
						}else {
							areaListByClock.add(Areas.getInstance().get(areaIdListByClock.get(0)));
						}
					}
				}
				
				
				if(areaListByClock.size() == 0) {
					pd1.put("status", "errname");
					pd1.put("msg", "该时钟没有绑定任何区域，请前往区域配置页面");
				}else {
					pd1.put("status", "errname");
					pd1.put("msg", "保存成功");
				}
				System.out.println("与时钟源关联的区域id = " + areaIdListByClock);
				System.out.println("与时钟源关联的区域对象有 " + areaListByClock);
				pd1.put("areaListByClock", areaListByClock);
				pd1.put("areaIdListByClock", areaIdListByClock);
				return pd1;
			}
		}
		
}
