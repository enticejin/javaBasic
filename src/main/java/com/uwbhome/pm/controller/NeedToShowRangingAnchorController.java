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

import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.Anchor;
import com.uwbhome.rtle.api.Anchors;
import com.uwbhome.rtle.api.Area;
import com.uwbhome.rtle.api.Areas;
import com.uwbhome.rtle.api.ClockSource;

@Controller
@RequestMapping("/need_show_anchor_range")
public class NeedToShowRangingAnchorController extends BaseController {
	//展示需要展示测距消息的基站列表
	@RequestMapping("/needToShowAnchorList.do")
	@ResponseBody
	public Object needToShowAnchorList(Page page, HttpServletRequest request,String idsList) {
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		
		List<Anchor> anchorList = new ArrayList<Anchor>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//基站ID
			String anchorIds = pd.getString("idsList");

			if(null != anchorIds && !"".equals(anchorIds)) {
				anchorIds = anchorIds.trim();
				if(anchorIds.contains(",")) {
					String[] anchorIdss = anchorIds.split(",");
					for(String id : anchorIdss) {
						anchorList.add(Anchors.getInstance().get(id));
					}
					//遍历基站列表
					for (Anchor anchor : anchorList) {
						if (null != anchor.getId() && anchor.getId().contains(anchorIds)) {
							anchorList.add(anchor);
						}
					}
				}else {
					anchorList.add(Anchors.getInstance().get(anchorIds));
				}
				
			}else {
				anchorIds = "";
			}
			
		
		//时钟源名称
		String clockSourceName = pd.getString("NAME");
		if (null != clockSourceName && !"".equals(clockSourceName)) {
			clockSourceName = clockSourceName.trim();
		} else {
			clockSourceName = "";
		}
		List<Anchor> anchorList1 = new ArrayList<Anchor>();
		for (Anchor anchor : anchorList) {
			if (null != anchor.getName() && anchor.getName().contains(clockSourceName)) {
				anchorList1.add(anchor);
			}
		}
		// 排序
		Collections.sort(anchorList, new Comparator<Anchor>() {
			@Override
			public int compare(Anchor o1, Anchor o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<Anchor> clockListPage = new ArrayList<Anchor>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < anchorList.size() - currIdx; i++) {
			Anchor a = anchorList.get(currIdx + i);
			clockListPage.add(a);
		}
		
		page.setTotalResult(anchorList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		pd.put("anchorIds", anchorIds);
		mv.setViewName("need_show_anchor_list");
		mv.addObject("varList", clockListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	//基站关联的区域
	@RequestMapping("/related.do")
	@ResponseBody
	public Object related(String anchorId) {
		Collection<Area> areaColl = Areas.getInstance().getList().values();
		List<Area> areaList = new ArrayList<Area>();
		List<String> areaIdListByAnchor = new ArrayList<String>();
		for(Area area : areaColl) {
			if(area.getSpecifyAnchorsId().contains(anchorId)) {
				areaList.add(area);
				areaIdListByAnchor.add(area.getId());
			}
		}
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		pd.put("anchorId", anchorId);
		pd.put("areaIdListByAnchor", areaIdListByAnchor);
		mv.addObject("varList", areaList);
		mv.addObject("pd",pd);
		mv.setViewName("need_show_anchor_area_related");
		return mv;
	}
	//从测试列表中移除基站
		@RequestMapping("/remove.do")
		@ResponseBody
		public Object remove(String anchorId,String anchorIds) {
			List<String> anchorIdListBySelected = new ArrayList<String>();
			if(anchorIds.contains(",")) {
				if(anchorIds != null && anchorIds.trim().length() > 0) {
					anchorIds = anchorIds.trim();
					String[] anchorIdss = anchorIds.split(",");
					for(String id : anchorIdss) {
						anchorIdListBySelected.add(id);
					}
				}
				
				if(anchorIdListBySelected.contains(anchorId)) {
					anchorIdListBySelected.remove(anchorId);
				}
			}
			PageData pd=new PageData();
			pd.put("status", "ok");
			pd.put("msg", "移除成功");
			pd.put("idsList", anchorIdListBySelected);
			pd.put("anchorId", anchorId);
			return pd;
			
		}
		
	//保存剩余的基站id
		@RequestMapping(value = "/save.do")
		@ResponseBody
		public Object saveAnchor() {
			PageData pd1 = this.getPageData();
			
			if(pd1.get("flag") != null && pd1.get("flag").toString().equals("area")) {
				//areaIdListByAnchor
				String areaIdListByAnchor = pd1.getString("areaIdListByAnchor").toString();
				areaIdListByAnchor = areaIdListByAnchor.substring(1, areaIdListByAnchor.length()-1);
				//获取基站信息
				List<Area> areaList = new ArrayList<Area>();
				if(areaIdListByAnchor.contains(",")) {
					areaIdListByAnchor = areaIdListByAnchor.trim();
					String[] ids = areaIdListByAnchor.split(",");
					for(String id : ids) {
						areaList.add(Areas.getInstance().get(id));
					}
				}else {
					areaList.add(Areas.getInstance().get(areaIdListByAnchor));
				}
				System.out.println("保存剩下的基站与区域关联的区域id有：" + areaIdListByAnchor);
				
				pd1.put("idsList", areaIdListByAnchor);
				pd1.put("debugAreaList", areaList);
				return pd1;	
			}else {
				String anchorIdListByEnd = pd1.getString("anchorIdListByEnd").toString();
				//获取基站信息
				List<Anchor> debugAnchorList = new ArrayList<Anchor>();
				List<String> anchorIdList = new ArrayList<String>();
				if(anchorIdListByEnd.contains(",")) {
					anchorIdListByEnd = anchorIdListByEnd.trim();
					String[] ids = anchorIdListByEnd.split(",");
					for(String id : ids) {
						debugAnchorList.add(Anchors.getInstance().get(id));
						anchorIdList.add(id);
					}
				}else {
					debugAnchorList.add(Anchors.getInstance().get(anchorIdListByEnd));
					anchorIdList.add(anchorIdListByEnd);
				}
				System.out.println("保存剩下的基站id有：" + anchorIdListByEnd);
				PageData pd  = new PageData();
				pd.put("idList", anchorIdList);
				pd.put("debugTagList", debugAnchorList);
				return pd;	
			}
		}
		//设置基站坐标
		@RequestMapping(value = "/setXYZ.do")
		@ResponseBody
		public Object setAnchorXYZ(String id) {
			PageData pd = new PageData();
			PageData retpd = new PageData();
			pd = this.getPageData();
			String anchorWebId = pd.getString("id");
			String anchorIds = pd.getString("anchorIds");
			if(anchorWebId==null)
			{
				return "";
			}
			double x = Double.parseDouble(pd.getString("x"));
			double y = Double.parseDouble(pd.getString("y"));
			double z = Double.parseDouble(pd.getString("z"));
			
			Anchor anchor=Anchors.getInstance().get(anchorWebId);
			if(anchor!=null)
			{
				anchor.setX(x);
				anchor.setY(y);
				anchor.setZ(z);
				anchor.save();
				retpd.put("status", "ok");
				retpd.put("anchorIds", anchorIds);
				retpd.put("idsList", id);
				retpd.put("msg", "操作成功");
				
			}else
			{
				retpd.put("status", "err");
				retpd.put("msg", "操作失败");
			}
			return retpd;
		}
		
}
