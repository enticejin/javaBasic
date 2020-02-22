package com.uwbhome.pm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.Area;
import com.uwbhome.rtle.api.Areas;
import com.uwbhome.rtle.api.GuardAreas;
import com.uwbhome.rtle.api.Tag;
import com.uwbhome.rtle.api.TagType;
import com.uwbhome.rtle.api.TagTypes;
import com.uwbhome.rtle.api.Tags;

/**
 * 标签的控制器
 * @author xu.yuanli
 *
 */
@Controller
@RequestMapping("/tag")
public class TagInfoController extends BaseController {
	
	//打开标签列表
	@RequestMapping(value="/Taglist.do")
	public ModelAndView list(Page page, HttpServletRequest request){
		
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		
		Collection<Tag> tags = Tags.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		String tagname = pd.getString("tagname");
		if (null != tagname && !"".equals(tagname)) {
			tagname = tagname.trim();
		} else {
			tagname = "";
		}
		List<Tag> tagList = new ArrayList<Tag>();
		List<String> timeStr = new ArrayList<String>();
		//获取最后接收消息的时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		for (Tag tag : tags) {
			if (null != tag.getName() && tag.getName().contains(tagname)) {
				
				date = tag.getLastMessageTime();
				tagList.add(tag);
			}
		}
		timeStr.add(sdf.format(date));
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
		
		page.setTotalResult(tagList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("taginfo_list");
		mv.addObject("varList", tagListPage);
		pd.put("timeStr", timeStr);
		mv.addObject("pd", pd);
		return mv;
	}
	
		//打开新增标签页面
		@RequestMapping(value="/tagAdd.do")
		public ModelAndView tagAdd(){	
			ModelAndView mv=getModelAndView();
			PageData pd=new PageData();
			List<PageData> tagtypes = TagTypeList();
			pd.put("flag", "add");
			mv.setViewName("taginfo_edit");
			mv.addObject("tagtypes", tagtypes);
			mv.addObject("pd", pd);
			return mv;
		}
	
		//打开修改标签页面
		@RequestMapping(value="/tagEdit.do")
		public ModelAndView tagEdit(){
			ModelAndView mv=this.getModelAndView();
			PageData pd1=this.getPageData();
			String tagId=pd1.get("tagId").toString();
			Tag tag=Tags.getInstance().get(tagId);
			List<PageData> tagtypes = TagTypeList();
			List<PageData> areas = AreaList();
			PageData pd=new PageData();
			pd.put("flag","update");
			pd.put("tagInfo",tag);
			mv.addObject("pd", pd);
			mv.setViewName("taginfo_edit");
			mv.addObject("tagtypes", tagtypes);
			mv.addObject("areas", areas);
			mv.addObject("pd", pd);
			return mv;
		}
		
		//保存标签功能处理
		@RequestMapping(value="/saveTag.do")
		@ResponseBody
		public Object saveTag() 
		{
			PageData pd=getPageData();
			String name=pd.getString("name");//名称
			String tagtypeId=pd.getString("tagtypeId");//标签类型
			String comments=pd.getString("comments");//备注
			String icon=pd.getString("icon");//备注
			int averageFilterSampleTimeLength=Integer.parseInt(pd.getString("averageFilterSampleTimeLength"));//平均值滤波长度
			int kalmanFilterLevel=Integer.parseInt(pd.getString("kalmanFilterLevel"));//卡尔曼	
			boolean useAreaFilter=Boolean.parseBoolean(pd.getString("useAreaFilterValue"));//是否使用区域过滤器
			boolean useAverageFilter=Boolean.parseBoolean(pd.getString("useAverageFilterValue"));//是否使用平均值滤波
			boolean useKalmanFilter=Boolean.parseBoolean(pd.getString("useKalmanFilterValue"));//是否使用卡尔曼滤波
			//String areaId=pd.getString("areaId");//楼层列表	
			String newGuarID=GuardAreas.getInstance().getNewId();
			PageData pd1=new PageData();
			String flag=pd.getString("flag");//获取是更新还是新增的标志
			String enableValue=pd.getString("enable");//是否启用
			boolean enable=false;
			if(enableValue.equals("0")){
				enable=false;
			}else if(enableValue.equals("1")){
				enable=true;
			}
			if(flag!=""){
			Tag tagBean=null;
			if(flag.equals("update")){
				//保存的逻辑
				String tagId=pd.getString("tagId");//id
				tagBean=Tags.getInstance().get(tagId);
			}
				tagBean.setName(name);
				tagBean.setEnable(enable);
				tagBean.setTagTypeId(tagtypeId);
				//tagBean.setAreaId(areaId);
				tagBean.setComments(comments);	
				tagBean.setIcon(icon);
				tagBean.setAverageFilterSampleTimeLength(averageFilterSampleTimeLength);
				tagBean.setKalmanFilterLevel(kalmanFilterLevel);
				tagBean.setUseAreaFilter(useAreaFilter);
				tagBean.setUseAverageFilter(useAverageFilter);
				tagBean.setUseKalmanFilter(useKalmanFilter);
				tagBean.save();
				pd1.put("status", "ok");
				pd1.put("msg", "操作成功");
			}else{
				//参数错误
				pd1.put("status", "err");
				pd1.put("msg", "参数错误");
			}
			return  pd1;
		}
		
		//删除标签功能处理
		@RequestMapping(value="/delete.do")
		@ResponseBody
		public Object deleteTag(){
			PageData pd=getPageData();
			String tagId=pd.get("tagId").toString();
			Tags.getInstance().remove(tagId);
			PageData pd1=new PageData();
			pd1.put("status", "ok");
			pd1.put("msg", "删除成功");
			return pd1;
		}
		
		//标签类型下拉列表
		public List<PageData> TagTypeList()
		{
			List<PageData> pdFloor = new ArrayList<>();
			List<TagType> floorList=new ArrayList<TagType>();
			floorList.addAll(TagTypes.getInstance().getList().values());
			for (TagType floor : floorList) {
				PageData tempPd = new PageData();
				tempPd.put("id",floor.getId());
				tempPd.put("name",floor.getName());
				pdFloor.add(tempPd);
			}
			return  pdFloor;
		}	
		
		//区域名称下拉列表
		public List<PageData> AreaList()
		{
			List<PageData> pdFloor = new ArrayList<>();
			List<Area> floorList=new ArrayList<Area>();
			floorList.addAll(Areas.getInstance().getList().values());
			for (Area floor : floorList) {
				PageData tempPd = new PageData();
				tempPd.put("id",floor.getId());
				tempPd.put("name",floor.getName());
				pdFloor.add(tempPd);
			}
			return  pdFloor;
		}	

		//展示需要测距的标签列表
		@RequestMapping(value="/needToShowTagList.do")
		@ResponseBody
		public Object needToShowTagList(String idsList){
			ModelAndView mv = this.getModelAndView();
			//去除包含的空格
			idsList = idsList.trim();
			//截取标签的id列表
			String[] ids = idsList.split(",");
			ArrayList<String> debugTagList = new ArrayList<String>();
			List<Tag> tagList = new ArrayList<Tag>();
			for(String id : ids) {
				tagList.add(Tags.getInstance().get(id));
				debugTagList.add(id);
			}
				PageData pd = new PageData();
				pd.put("idsList", idsList);
				pd.put("varList", tagList);
				mv.addObject("pd", pd);
				mv.addObject("varList", tagList);
				mv.setViewName("need_show_tag_list");
			
			return mv;
		}
		
		//展示需要测距的标签列表
				@RequestMapping(value="/Taglist_show.do")
				@ResponseBody
				public Object Taglist_show(){
					PageData pd = this.getPageData();
					String idsList = pd.getString("idsList");
					if(idsList.trim().length() == 0) {
						
					}
					idsList = idsList.trim().substring(0, idsList.length()-1);
					String[] ids = null;
					List<Tag> tagList = new ArrayList<Tag>();
					if(idsList.contains(",")) {
						 ids = idsList.split(",");
						 for(String id : ids) {
							 tagList.add(Tags.getInstance().get(id));
						 }
					}else
						tagList.add(Tags.getInstance().get(idsList));
					ModelAndView mv = this.getModelAndView();
					PageData pd1 = new PageData();
					pd1.put("varList", tagList);
					mv.addObject("pd", pd1);
					mv.setViewName("need_show_tag_list");
					return pd;
				}
		
		//移除列表中的元素
		@RequestMapping(value="/remove.do")
		@ResponseBody
		public Object remove(String tagId,String idsList){
			ModelAndView mv = this.getModelAndView();
			//去除包含的空格
			idsList = idsList.trim();
			tagId =  tagId.trim();
			//截取标签的id列表
			idsList = idsList.substring(3, idsList.length() - 1);
			String[] ids = idsList.split(",");
			ArrayList<String> debugTagList = new ArrayList<String>();
			List<Tag> tagList = new ArrayList<Tag>();
			for(String id : ids) {
				if(!id.equals(tagId)) {
					tagList.add(Tags.getInstance().get(id));
					debugTagList.add(id);
				}
			}
				PageData pd = new PageData();
				pd.put("idsList", idsList);
				pd.put("varList", tagList);
				mv.addObject("pd", pd);
				mv.addObject("varList", tagList);
				PageData pd1 = this.getPageData();
				pd1.put("status", "ok");
				pd1.put("msg", "移除成功");
				return pd1;
		}
	/**
	 * 返回分页数据
	 * @param usfilesList
	 * @param currentPage
	 * @param showCount
	 * @return
	 */
	public List<Tag> getCurrentPageData(List<Tag> guarareaList,int currentPage,int showCount)
	{
		// 排序
		Collections.sort(guarareaList, new Comparator<Tag>() {
							@Override
							public int compare(Tag a1, Tag a2) {
								return a1.getName().compareTo(a2.getName());
							}
						});
		//分页
		List<Tag> guarareaListPage = new ArrayList<Tag>();
				int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
				for (int i = 0; i < showCount && i < guarareaList.size() - currIdx; i++) {
					Tag guararea = guarareaList.get(currIdx + i);
					guarareaListPage.add(guararea);
				}
		return guarareaListPage;
				
	}
}
