package com.uwbhome.pm.controller;

import java.util.ArrayList;
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

import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.Anchor;
import com.uwbhome.rtle.api.Anchors;
import com.uwbhome.rtle.api.Area;
import com.uwbhome.rtle.api.Areas;
import com.uwbhome.rtle.api.ClockSource;

/**
 * @version 创建时间：2020年2月18日 下午2:48:30 类说明 :展示测试区域列表的控制器
 */
@Controller
@RequestMapping("/need_show_area_range")
public class NeedToShowRangingAreaController extends BaseController {

	@RequestMapping("/needToShowAreaList.do")
	@ResponseBody
	public Object showAreaList(Page page, HttpServletRequest request, String idsList) {
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();

		List<Area> areaList = new ArrayList<Area>();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 区域ID
		String areaIds = pd.getString("idsList");

		if (null != areaIds && !"".equals(areaIds)) {
			areaIds = areaIds.trim();
			if (areaIds.contains(",")) {
				String[] areaIdss = areaIds.split(",");
				for (String id : areaIdss) {
					areaList.add(Areas.getInstance().get(id));
				}
				// 遍历基站列表
				for (Area area : areaList) {
					if (null != area.getId() && area.getId().contains(areaIds)) {
						areaList.add(area);
					}
				}
			} else {
				areaList.add(Areas.getInstance().get(areaIds));
			}

		} else {
			areaIds = "";
		}

		// 区域名称
		String areaName = pd.getString("NAME");
		if (null != areaName && !"".equals(areaName)) {
			areaName = areaName.trim();
		} else {
			areaName = "";
		}
		List<Area> areaList1 = new ArrayList<Area>();
		for (Area area : areaList) {
			if (null != area.getName() && area.getName().contains(areaName)) {
				areaList1.add(area);
			}
		}
		// 排序
		Collections.sort(areaList, new Comparator<Area>() {
			@Override
			public int compare(Area o1, Area o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<Area> areaListPage = new ArrayList<Area>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < areaList.size() - currIdx; i++) {
			Area a = areaList.get(currIdx + i);
			areaListPage.add(a);
		}

		page.setTotalResult(areaList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());

		pd.put("areaIds", areaIds);
		mv.setViewName("need_show_area_list");
		mv.addObject("varList", areaListPage);
		mv.addObject("pd", pd);
		return mv;
	}

	// 从列表中移除标签
	@RequestMapping("/remove.do")
	@ResponseBody
	public Object remove(String areaId, String areaIds) {
		List<String> idsList = new ArrayList<String>();
		if (areaIds.contains(",")) {
			if (areaIds != null && areaIds.trim().length() > 0) {
				areaIds = areaIds.trim();
				String[] id = areaIds.split(",");
				for (String id1 : id) {
					idsList.add(id1);
				}
			}
		}

		if (idsList.contains(areaId)) {
			idsList.remove(areaId);
		}

		PageData pd = new PageData();
		pd.put("status", "ok");
		pd.put("msg", "移除成功");
		pd.put("idsList", idsList);
		pd.put("areaId", areaId);
		return pd;
	}

	// 保存区域功能处理
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Object saveArea(String flag) {
		PageData pd = this.getPageData();
		PageData pd1 = new PageData();
		String idsList = pd.getString("idsList");
		if(pd.get("flag") != null && pd.get("flag").toString().equals("anchor")) {
			String areaId = pd.get("areaId").toString();
			String anchorIdListByArea = pd.get("anchorIdListByArea").toString();
			//清除id两边的中括号
			System.out.println("区域id:" + areaId + "对应的基站id有："+anchorIdListByArea);
			pd1.put("status", "errname");
			pd1.put("areaId", areaId);
			pd1.put("anchorIdListByArea", anchorIdListByArea);
			
			return pd1;
		}else if(pd.get("flag") != null && pd.get("flag").toString().equals("clock")){
			String areaId = pd.get("areaId").toString();
			String clockIdByArea = pd.get("clockIdByArea").toString();
			//清除id两边的中括号
			System.out.println("区域id:" + areaId + "对应的时钟源id有："+clockIdByArea);
			pd1.put("status", "errname");
			pd1.put("areaId", areaId);
			pd1.put("clockIdByArea", clockIdByArea);
			
			return pd1;
		} 
		else {
			// 获取标签信息
			List<Area> debugAreaList = new ArrayList<Area>();
			List<String> areaidList = new ArrayList<String>();
			if (idsList.contains(",")) {
				idsList = idsList.trim();
				String[] ids = idsList.split(",");
				for (String id : ids) {
					debugAreaList.add(Areas.getInstance().get(id));
					areaidList.add(id);
				}
			} else {
				debugAreaList.add(Areas.getInstance().get(idsList));
				areaidList.add(idsList);
			}
			pd.put("idList", idsList);
			pd.put("debugAreaList", debugAreaList);
			System.out.println("debugAreaIdList = " + idsList);
			System.out.println("debugAreaList = " + debugAreaList);

			return pd;
		}
	}
	
	//与区域关联的基站
	@RequestMapping("/relatedAnhor.do")
	@ResponseBody
	public Object related(String areaId,String flag) {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		ArrayList<String> anchorIdListByArea = new ArrayList<String>();
		if(flag != null && !"".equals(flag)) {
			if(flag.equals("anchor")) {
				Area area = Areas.getInstance().get(areaId);
				anchorIdListByArea.addAll(area.getSpecifyAnchorsId());
				
				Map<String, List<Object>> mapList = new HashMap<String, List<Object>>();
				List<Object> objList = new ArrayList<Object>();
				List<Anchor> anchorList = new ArrayList<Anchor>();
				for(String id : anchorIdListByArea) {
					objList.add(Anchors.getInstance().get(id));
					mapList.put(id, objList);
					Anchor anchor = Anchors.getInstance().get(id);
					anchorList.add(anchor);
				}
				pd.put("areaId",areaId);
				pd.put("anchorIdListByArea", anchorIdListByArea);
				mv.addObject("varList", anchorList);
				mv.addObject("pd", pd);
				mv.setViewName("area_related_anchor");
				
			}else if(flag.equals("clock")) {
					
					Area area = Areas.getInstance().get(areaId);
					String clockIdByArea = area.getClockSource().getId();
					List<ClockSource> clocSourceList = new ArrayList<ClockSource>();
					ClockSource clockSource = area.getClockSource();
					clocSourceList.add(clockSource);
					pd.put("areaId",areaId);
					pd.put("clockIdByArea", clockIdByArea);
					mv.addObject("varList", clocSourceList);
					mv.addObject("pd", pd);
					mv.setViewName("area_related_clock");
			}
			
		}
		return mv;
	}
}
