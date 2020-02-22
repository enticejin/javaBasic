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
import com.uwbhome.rtle.api.*;

@Controller
@RequestMapping("/anchor")
public class AnchorController extends BaseController {
	
	@RequestMapping(value="/add.do")
	public void add()
	{
		System.out.println("添加基站");
	}
	/**
	 * 基站列表
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list.do")
	public ModelAndView list(Page page, HttpServletRequest request)
	{
		
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		
		Collection<Anchor> anchors = Anchors.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		String NAME = pd.getString("NAME");
		if (null != NAME && !"".equals(NAME)) {
			NAME = NAME.trim();
		} else {
			NAME = "";
		}
		List<Anchor> anchorList = new ArrayList<Anchor>();
		for (Anchor anchor : anchors) {
			if (null != anchor.getName() && anchor.getName().contains(NAME)) {
				anchorList.add(anchor);
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
		List<Anchor> anchorListPage = new ArrayList<Anchor>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < anchorList.size() - currIdx; i++) {
			Anchor a = anchorList.get(currIdx + i);
			anchorListPage.add(a);
		}

		page.setTotalResult(anchorList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("anchor_list");
		mv.addObject("varList", anchorListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	//设置基站的坐标
	@RequestMapping(value = "/setXYZ.do")
	@ResponseBody
	public Object setAnchorXYZ() {
		PageData pd = new PageData();
		PageData retpd = new PageData();
		pd = this.getPageData();
		String anchorWebId = pd.getString("id");
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
			retpd.put("msg", "操作成功");
			
		}else
		{
			retpd.put("status", "err");
			retpd.put("msg", "操作失败");
		}
		return retpd;
	} 
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public Object deleteAnchor(String anchorId) {
		logBefore(logger, "删除基站");
		PageData pd = new PageData();
		if (null == anchorId) {
			return "";
		}
		try {
			pd = this.getPageData();
			Anchors.getInstance().remove(anchorId);
			return "ok";
		} catch (Exception e) {
			logger.error(e.toString(), e);
			return "no Success";
		}

	}
	@RequestMapping(value = "/deleteAll.do")
	@ResponseBody
	public Object deleteManyAnchor(String DATA_IDS) {
		logBefore(logger, "删除多个基站");
		PageData pd = new PageData();
		if (null == DATA_IDS||"".equals(DATA_IDS)) {
			pd.put("status", "err");
			pd.put("msg", "没有选择任何要删除的基站");
			
		}else
		{
			String[] anchorIDs=DATA_IDS.split(",");
			for (String aid : anchorIDs) {
				Anchors.getInstance().remove(aid);
			}
			pd.put("status", "ok");
			pd.put("msg", "操作成功");
		}
		return pd;
	}

}
