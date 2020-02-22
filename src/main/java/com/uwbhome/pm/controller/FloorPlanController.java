package com.uwbhome.pm.controller;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.rtle.api.*;
import com.uwbhome.rtle.comm.LonLatPoint;
import com.uwbhome.rtle.utils.Misc;

/**
 * 楼层平面图控制器
 * @author xu.yuanli
 *
 */
@Controller
@RequestMapping("/floorPlan")
public class FloorPlanController  extends BaseController {
	
	//新增平面图
	@RequestMapping(value="/add.do")
	public ModelAndView floorPlanAdd(Page page, HttpServletRequest request)
	{
		ModelAndView mv=getModelAndView();
		PageData pd=this.getPageData();
		//经纬度需要转换一下
		RTLEAPI rtleapi=RTLEAPI.getInstance();
		pd.put("lon", rtleapi.getOrigin().getLongitude());
		pd.put("lat",  rtleapi.getOrigin().getLatitude());
		pd.put("flag", "add");
		mv.setViewName("baseplanmap_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	//编辑平面图
	@RequestMapping(value="/edit.do")
	public ModelAndView floorPlanEdit(Page page, HttpServletRequest request)
	{
		ModelAndView mv=getModelAndView();
		PageData pd=this.getPageData();
		String palnId=pd.get("planMapId").toString();
		Basemap bsasemap=Basemaps.getInstance().get(palnId);
		pd.put("bsasemap", bsasemap);
		//经纬度需要转换一下
		//RTLEAPI rtleapi=RTLEAPI.getInstance();
		
		//LonLatPoint lonlat=	Misc.meter2LonLat(rtleapi.getOrigin(), bsasemap.getCenterX(), bsasemap.getCenterY());
		pd.put("lon", bsasemap.getCenterX());
		pd.put("lat", bsasemap.getCenterY());
		pd.put("flag", "update");
		mv.setViewName("baseplanmap_edit");
		mv.addObject("pd", pd);
		return mv;
		
	}
	//保存平面图
	@RequestMapping(value="/savePlanMap.do")
	@ResponseBody
	public Object savePlanMap()
	{
		PageData pd=getPageData();
		PageData pd1=new PageData();
		String flag=pd.getString("flag");//获取是更新还是新增的标志
		String basemapId=pd.getString("basemapId");//获取basemap的ID
		String newID=Basemaps.getInstance().getNewId();
		String basemapName=pd.getString("planmapname");//平面图名称
		String comments=pd.getString("beizhu");//备注
		String basemapFileName=pd.getString("palnmapfilename");//底图文件名
		int basemapType=Integer.parseInt(pd.getString("pictype"));
		double centerX=Double.parseDouble(pd.getString("centerlon"));//中心经度
		double centerY=Double.parseDouble(pd.getString("centerlat"));//中心维度
		
		double scalex=Double.parseDouble(pd.getString("scalex"));//x缩放
		double scaley=Double.parseDouble(pd.getString("scaley"));//y缩放
		
		double rotate=Double.parseDouble(pd.getString("rotate"));//旋转角度
		double opacity=Double.parseDouble(pd.getString("opacity"));//透明度
		
		if(flag!="")
		{
			if(flag.equals("add"))
			{
				//新增的保存
				Basemap bp=new Basemap(newID,basemapName, comments,basemapType, centerX, centerY);
				bp.setBasemapBitmapFilename(basemapFileName);
				bp.setScaleX(scalex);
				bp.setScaleY(scaley);
				bp.setOpacity(opacity);
				bp.setRotate(rotate);
				pd1.put("status", "ok");
				pd1.put("msg", "新增保存成功");
				Basemaps.getInstance().add(bp); 
				bp.save();
			}else if(flag.equals("update"))
			{
				//更新的保存
				Basemap bp=Basemaps.getInstance().get(basemapId);
				bp.setBasemapBitmapFilename(basemapFileName);
				bp.setBasemapType(basemapType);
				bp.setCenterX(centerX);
				bp.setCenterY(centerY);
				bp.setComments(comments);
				bp.setName(basemapName);
				bp.setOpacity(opacity);
				bp.setRotate(rotate);
				bp.setScaleX(scalex);
				bp.setScaleY(scaley);
				pd1.put("status", "ok");
				pd1.put("msg", "数据更新保存成功");
				bp.save();
			}
		}else
		{
			//参数错误
			pd1.put("status", "err");
			pd1.put("msg", "参数错误");
		}
		return pd1;
	}
	
	
	//打开楼层平面图列表
	@RequestMapping(value="/floorPlanList.do")
	public ModelAndView floorPlanList(Page page, HttpServletRequest request)
	{
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		//获取所有数据
		Collection<Basemap> basemaps = Basemaps.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取前台传过来的NAME
		String NAME = pd.getString("NAME");
		if (null != NAME && !"".equals(NAME)) {
			NAME = NAME.trim();
		} else {
			NAME = "";
		}
		List<Basemap> baseMapList = new ArrayList<Basemap>();
		//遍历并判断是否存在NAME
		for (Basemap baseMap : basemaps) {
			if (null != baseMap.getName() && baseMap.getName().contains(NAME)) {
				baseMapList.add(baseMap);
			}
		}
		// 排序
		Collections.sort(baseMapList, new Comparator<Basemap>() {
			@Override
			public int compare(Basemap o1, Basemap o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// 分页
		List<Basemap> baseMapListPage = new ArrayList<Basemap>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < baseMapList.size() - currIdx; i++) {
			Basemap a = baseMapList.get(currIdx + i);
			baseMapListPage.add(a);
		}

		page.setTotalResult(baseMapList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("baseplanmap_list");
		mv.addObject("varList", baseMapListPage);
		mv.addObject("pd", pd);
		return mv;
		
	}
	//删除
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public Object deletePlanMap(String id)
	{
		logBefore(logger, "删除平面图");
		if(null == id) {
    		return "";
    	}
		//获取所有楼层
		List<Floor> floorListAll=new ArrayList<Floor>();
		floorListAll.addAll(Floors.getInstance().getList().values());
		String name = "";
		for (int i = 0; i < floorListAll.size(); i++) {
			ArrayList<String> floors = floorListAll.get(i).getBasemapIds();
			for(String str : floors) {
				if (id.equals(str)) {
					name += ','+floorListAll.get(i).getName();
				}
			}
		}
		PageData pd1=new PageData();
		if (name=="") {
			ClockSources.getInstance().remove(id);
			pd1.put("status", "ok");
			pd1.put("msg", "删除成功");
		}else {
			pd1.put("name", name.substring(1));
		}
		
		return pd1;
	}
	//打开地图，并在地图设置 平面图的比列
	@RequestMapping(value="/planMapIndex.do")
	public ModelAndView editPlanOnMap(String planMapId)
	{
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd.put("planMapId", planMapId);
		mv.addObject("pd", pd);
		mv.setViewName("plan_edit_map");
		return mv;
	}
	@RequestMapping(value="/planMapIndextest.do")
	public ModelAndView editPlanOnMapTest(String planMapId)
	{
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd.put("planMapId", planMapId);
		mv.addObject("pd", pd);
		mv.setViewName("plan_edit_test");
		return mv;
	}
	//获取楼层平面图
	@RequestMapping(value="/getPlanMapJson.do")
	@ResponseBody
	public Object getPlanMapData(HttpServletRequest request)
	{
		PageData pd=this.getPageData();
		String palnMapId=pd.get("planMapId").toString();
		Basemap bp=Basemaps.getInstance().get(palnMapId);
		PageData pd1=new PageData();
		String basemapFile=bp.getBasemapBitmapFilename();
		UserFile uf=UserFiles.getInstance().get(basemapFile);
		String ufstring=getPicData(uf);
		
		pd1.put("userfile", ufstring);
		pd1.put("bp", bp);
		
		return pd1;
	}
	//返回图片的数据
	public String getPicData(UserFile uf)
	{
		String retufstring="";
		String ufstring=Base64.encodeBase64String(uf.getFileContent());
		String fname=uf.getFileName();
		int pos1=fname.indexOf(".")+1;
		String fileSuffix=fname.substring(pos1);//文件名后缀
		switch (fileSuffix) {
		case "png":
			retufstring="data:image/png;base64,"+ufstring;
			break;
		case "jpg":
			retufstring="data:image/jpg;base64,"+ufstring;
			break;
		default:
			retufstring="-1";
			break;
		}
		return retufstring;
	}
	
	/**
	 * 返回分页数据
	 * @param usfilesList
	 * @param currentPage
	 * @param showCount
	 * @return
	 */
	public List<Basemap> getCurrentPageData(List<Basemap> baseMapList,int currentPage,int showCount)
	{
		// 排序
		Collections.sort(baseMapList, new Comparator<Basemap>() {
							@Override
							public int compare(Basemap u1, Basemap u2) {
								return u1.getName().compareTo(u2.getName());
							}
						});
		//分页
		List<Basemap> usfilesListPage = new ArrayList<Basemap>();
				int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
				for (int i = 0; i < showCount && i < baseMapList.size() - currIdx; i++) {
					Basemap bmap = baseMapList.get(currIdx + i);
					usfilesListPage.add(bmap);
				}
		return usfilesListPage;
				
	}
	@RequestMapping(value = "/save_plance_pic_by_json.do")
	@ResponseBody
	public String  savePlancePicByJson(String planMapId,HttpServletRequest request) throws Exception {
		String strdata = request.getParameter("json");
		Basemap bpobject=Basemaps.getInstance().get(planMapId);
		//将数据解析出来
		ObjectMapper mapper=new ObjectMapper();
		JsonNode root = mapper.readTree(strdata);
		double opacity=Double.parseDouble(root.get("opacity").toString());//透明度
		double rotate=Double.parseDouble(root.get("rotate").toString());//旋转角度
		
		JsonNode root1=mapper.readTree(root.get("center").toString());
		double centerX=Double.parseDouble(root1.get(0).toString());
		double centerY=Double.parseDouble(root1.get(1).toString());
		bpobject.setCenter(centerX, centerY);
		
		JsonNode root2=mapper.readTree(root.get("scale").toString());
		double scaleX=Double.parseDouble(root2.get(0).toString());
		double scaleY=Double.parseDouble(root2.get(1).toString());
		bpobject.setScaleX(scaleX);
		bpobject.setScaleY(scaleY);
		
		bpobject.setOpacity(opacity);
		bpobject.setRotate(rotate);

//		System.out.println("id="+bpobject.getId()+" name="+bpobject.getName()+" type="+bpobject.getBasemapType()+" dituFileName="+bpobject.getBasemapBitmapFilename()
//		+" center=("+bpobject.getCenterX()+","+bpobject.getCenterY()+")"+" opacity="+bpobject.getOpacity()+" rotate="+bpobject.getRotate()
//		+" scaleX="+bpobject.getScaleX()+" scaleY="+bpobject.getScaleY());
		bpobject.save();
		return "ok";
	}
	//提供给其他页面选择平面图页面
	@RequestMapping(value="/selectPlanList.do")
	public  ModelAndView selectPlanMapList(String baseid,Page page, HttpServletRequest request)
	{
		//todo
		int currentPage = 0;
		List<Basemap> baseMapList=new ArrayList<Basemap>();
		//从api获取文件数据
		baseMapList.addAll(Basemaps.getInstance().getList().values());
		//获取分页数据
		List<Basemap> basemapListPage=getCurrentPageData(baseMapList,currentPage,this.pageSize);
		
		page.setTotalResult(baseMapList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(5);
		page.setCurrentPage(page.getCurrentPage());
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("selectplanmap_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("varList", basemapListPage);
		mv.addObject("baseid", baseid);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	    * 用二进制写出图片,保存到文件去
     * @param imgs
     * @param path
     */
    public static void writeByteimg(byte []imgs,String path) {
        DataOutputStream outimg=null;
        try {
            outimg=new DataOutputStream(new FileOutputStream(path));
            outimg.write(imgs);
            outimg.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outimg.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
}
