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
import com.uwbhome.rtle.api.TagType;
import com.uwbhome.rtle.api.TagTypes;

@Controller
@RequestMapping("/tagType")
public class TagTypeController extends BaseController {
	// 打开标签类型管理列表
	@RequestMapping(value = "/tagTypeList.do")
	public ModelAndView tagTypeList(Page page, HttpServletRequest request) {

		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		//获取所有数据
		Collection<TagType> tagTypes = TagTypes.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取前台传过来的NAME
		String NAME = pd.getString("tagName");
		if (null != NAME && !"".equals(NAME)) {
			NAME = NAME.trim();
		} else {
			NAME = "";
		}
		List<TagType> tagTypeList = new ArrayList<TagType>();
		//遍历并判断是否存在NAME
		for (TagType tagType : tagTypes) {
			if (null != tagType.getName() && tagType.getName().contains(NAME)) {
				tagTypeList.add(tagType);
			}
		}
		// 排序
		Collections.sort(tagTypeList, new Comparator<TagType>() {
			@Override
			public int compare(TagType o1, TagType o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<TagType> tagTypeListPage = new ArrayList<TagType>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < tagTypeList.size() - currIdx; i++) {
			TagType a = tagTypeList.get(currIdx + i);
			tagTypeListPage.add(a);
		}

		page.setTotalResult(tagTypeList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("tag_list");
		mv.addObject("varList", tagTypeListPage);
		mv.addObject("pd", pd);
		return mv;
	}

	// 打开新增标签信息页面
	@RequestMapping("/tagAdd.do")
	public ModelAndView tagAdd(String defaultIcon,HttpServletRequest request) {
		logBefore(logger, "添加标签TagInfo");
		String palnmapfilename = request.getParameter("palnmapfilename");
		if(palnmapfilename == null || "".equals(palnmapfilename)) {
			palnmapfilename = defaultIcon;
		}else {
			defaultIcon = palnmapfilename;
		}
		PageData pd = this.getPageData();
		ModelAndView mv=this.getModelAndView();
		String id = TagTypes.getInstance().getNewTagTypeId();
		pd.put("id", id);
		pd.put("defaultIcon", palnmapfilename);
		pd.put("flag", "add");
		mv.addObject("pd", pd);
		mv.setViewName("tag_edit");
		return mv;
	}

	// 打开修改标签信息页面
	@RequestMapping("/tagEdit.do")
	public ModelAndView tagEdit(String tagId,HttpServletRequest request) {
		logBefore(logger, "修改TagInfo");
		ModelAndView mv = this.getModelAndView();
		TagType tageType = TagTypes.getInstance().get(tagId);
		PageData pageData = new PageData();
		String palnmapfilename = request.getParameter("palnmapfilename");
		String defaultIcon = TagTypes.getInstance().get(tagId).getDefaultIcon();
		if(tageType != null) {
			pageData.put("tagInfo", tageType);
		}else {
			pageData.put("tagInfo", "-1");
		}
		if(palnmapfilename == null || "".equals(palnmapfilename)) {
			palnmapfilename = defaultIcon;
		}else {
			defaultIcon = palnmapfilename;
		}
		
		pageData.put("id", tagId);
		pageData.put("defaultIcon", defaultIcon);
		pageData.put("flag", "update");
		mv.addObject("pd", pageData);
		mv.setViewName("tag_edit");
		return mv;
	}

	// 保存标签编辑信息
	@RequestMapping(value = "saveTag.do")
	@ResponseBody
	public Object saveTag() {
		PageData pd = this.getPageData();
		String tagId = pd.getString("tagId").toString();// 标签类型ID
		String tagName = pd.getString("tagName").toString();// 标签类型名称
		String comments = pd.getString("comments").toString();// 备注消息
		String palnmapfilename = pd.getString("palnmapfilename").toString();// 图标
		String defaultIcon = pd.getString("defaultIcon").toString();// 图标
		if(palnmapfilename == null || "".equals(palnmapfilename)) {
			palnmapfilename = defaultIcon;
		}else {
			defaultIcon = palnmapfilename;
		}
		PageData pd1 = new PageData();
		String flag = pd.getString("flag").toString();// 获取是更新还是新增的标志
		if (flag != "") {
			if (flag.equals("update")) {
				// 保存的逻辑
				TagType saveBean = TagTypes.getInstance().get(tagId);
				saveBean.setName(tagName);
				saveBean.setComments(comments);
				saveBean.setDefaultIcon(defaultIcon);
				saveBean.save();
				pd1.put("status", "ok");
				pd1.put("msg", "操作成功");
			}
			else if (flag.equals("add")) {
				// 保存的逻辑
				TagType saveBeanAdd = new TagType(tagId,tagName,comments,defaultIcon);
				saveBeanAdd.save();
				pd1.put("status", "ok");
				pd1.put("msg", "操作成功");
			} else {
				// 参数错误
				pd1.put("status", "err");
				pd1.put("msg", "参数错误");
			}
		}
		return pd;
	}
	// 删除标签类型
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public Object deleteTag() {
		PageData pd = getPageData();
		String tagId = pd.get("tagId").toString();
		TagTypes.getInstance().remove(tagId);
		PageData pd1 = new PageData();
		pd1.put("status", "ok");
		pd1.put("msg", "删除成功");
		return pd1;
	}
	//检查标签类型是否存在
	@RequestMapping(value = "/existByName")
		@ResponseBody
		public Object existByName() throws Exception{
					PageData pd = this.getPageData();
					//获取添加页面的标签名称
					String tageTypeName=pd.getString("tagName").toString();
					//获取已经保存的所有标签
					Collection<TagType> tagTypes =  TagTypes.getInstance().getList().values();
					//遍历判断是否存在同名标签类型
					for(TagType t : tagTypes) {
						System.out.println(t.getName());
						if(t.getName().toString().equals(tageTypeName)) {
							//标签类型名已存在
							return 1;
						}
						else {
							//标签类型名不存在
							return 0;
						}
					}
					return tagTypes;
		    }
	public List<TagType> getCurrentPageData(List<TagType> tagTypeList, int currentPage, int showCount) {
		// 排序
		Collections.sort(tagTypeList, new Comparator<TagType>() {
			@Override
			public int compare(TagType t1, TagType t2) {
				return t1.getName().compareTo(t2.getName());
			}
		});
		// 分页
		List<TagType> tagTypeListPage = new ArrayList<TagType>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < tagTypeList.size() - currIdx; i++) {
			TagType tag = tagTypeList.get(currIdx + i);
			tagTypeListPage.add(tag);
		}
		return tagTypeListPage;

	}

}
