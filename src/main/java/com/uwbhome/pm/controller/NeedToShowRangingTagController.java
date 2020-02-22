package com.uwbhome.pm.controller;

import java.util.ArrayList;
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
import com.uwbhome.rtle.api.Areas;
import com.uwbhome.rtle.api.Tag;
import com.uwbhome.rtle.api.Tags;

@Controller
@RequestMapping("/need_show_rang")
public class NeedToShowRangingTagController extends BaseController {
	//需要显示测距消息的标签列表
	@RequestMapping("/needToShowTagList.do")
	@ResponseBody
	public Object needToShowTagList(HttpServletRequest request,Page page,String idsList){
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		
		List<Tag> tagList = new ArrayList<Tag>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//标签ID
		String tagIds = pd.getString("idsList");
		if(null != tagIds && !"".equals(tagIds)) {
			tagIds = tagIds.trim();
			if(tagIds.contains(",")) {
				String[] idss = tagIds.split(",");
				for(String id : idss) {
					tagList.add(Tags.getInstance().get(id));
				}
				//遍历标签列表
				for (Tag tag : tagList) {
					if (null != tag.getId() && tag.getId().contains(tagIds)) {
						tagList.add(tag);
					}
				}
			}else {
				tagList.add(Tags.getInstance().get(tagIds));
			}
			
		}else {
			tagIds = "";
		}
		
		//标签名称
		String tagname = pd.getString("tagname");
		//去除空格
		if (null != tagname && !"".equals(tagname)) {
			tagname = tagname.trim();
		} else {
			tagname = "";
		}
		List<Tag> tagList1 = new ArrayList<Tag>();
		for (Tag tag : tagList) {
			if (null != tag.getName() && tag.getName().equals(tagname)) {
				tagList1.add(tag);
			}
		}
		// 排序
		Collections.sort(tagList, new Comparator<Tag>() {
			@Override
			public int compare(Tag o1, Tag o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<Tag> tagListPage = new ArrayList<Tag>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < tagList.size() - currIdx; i++) {
			Tag a = tagList.get(currIdx + i);
			tagListPage.add(a);
		}
		pd.put("tagIdList", Tags.getInstance().getIds());
		pd.put("ids", tagIds);
		page.setTotalResult(tagList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		if(tagList1.size() > 0) {
			tagListPage = tagList1;
		}
		mv.setViewName("need_show_tag_list");
		mv.addObject("varList", tagListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	
	//从列表中移除标签
		@RequestMapping("/remove.do")
		@ResponseBody
		public Object remove(String tagId,String ids) {
			List<String> idsList = new ArrayList<String>();
			if(ids.contains(",")) {
				if(ids != null && ids.trim().length() > 0) {
					ids=ids.trim();
					String[] id = ids.split(",");
					for(String id1 : id) {
						idsList.add(id1);
					}
				}
			}
			
			if(idsList.contains(tagId)) {
				idsList.remove(tagId);
			}
			
			PageData pd=new PageData();
			pd.put("status", "ok");
			pd.put("msg", "移除成功");
			pd.put("idsList", idsList);
			pd.put("tagId", tagId);
			return pd;
		}
	
	//保存标签功能处理
	@RequestMapping(value="/save.do")
	@ResponseBody
	public Object saveTag(String idsList) 
	{
		//获取标签信息
		List<Tag> debugTagList = new ArrayList<Tag>();
		List<String> idList = new ArrayList<String>();
		if(idsList.contains(",")) {
			idsList = idsList.trim();
			String[] ids = idsList.split(",");
			for(String id : ids) {
				debugTagList.add(Tags.getInstance().get(id));
				idList.add(id);
			}
		}else {
			debugTagList.add(Tags.getInstance().get(idsList));
			idList.add(idsList);
		}
		PageData pd  = new PageData();
		pd.put("idList", idList);
		pd.put("debugTagList", debugTagList);
		System.out.println("debugTagIdList = " + idList);
		System.out.println("debugTagList = " + debugTagList);
		
		return pd;			
	}
		
	//提示消息
	@RequestMapping("/tips.do")
	@ResponseBody
	public Object tips(String idsList) {
		PageData pd=new PageData();
		pd.put("msg", "请先选择需要添加的元素");
		pd.put("status", "ok");
		return pd;
	}
	
}
