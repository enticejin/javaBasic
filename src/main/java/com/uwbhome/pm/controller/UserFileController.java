package com.uwbhome.pm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.uwbhome.pm.security.Authentication;
import com.uwbhome.pm.utils.DateUtil;
import com.uwbhome.pm.utils.FileUpload;
import com.uwbhome.pm.utils.Page;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.pm.utils.PathUtil;
import com.uwbhome.pm.utils.RtleRequestUtils;
import com.uwbhome.rtle.api.*;

@Controller
@RequestMapping("/userFile")
public class UserFileController extends BaseController {

	@RequestMapping(value="/userFileList.do")
	public ModelAndView userFileList(Page page, HttpServletRequest request)
	{
//		int currentPage = 0;
//		
//		List<UserFile> usfilesList=new ArrayList<UserFile>();
//		//从api获取文件数据
//		usfilesList.addAll(UserFiles.getInstance().getList().values());
//		
//		//获取分页数据
//		List<UserFile> usfilesListPage=getCurrentPageData(usfilesList,currentPage,this.pageSize);
//		
//		page.setTotalResult(usfilesList.size());
//		page.setTotalPage(page.getTotalPage());
//		page.setCurrentPage(currentPage);
//		page.setShowCount(this.pageSize);
//		page.setCurrentPage(page.getCurrentPage());
//		ModelAndView mv = this.getModelAndView();
//		mv.setViewName("userfile_list");
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		mv.addObject("varList", usfilesListPage);
//		mv.addObject("pd", pd);
//		return mv;
		
		int showCount = this.pageSize;
		int currentPage = 0;
		try {
			showCount = Integer.parseInt(request.getParameter("page.showCount"));
			currentPage = Integer.parseInt(request.getParameter("page.currentPage"));
		} catch (Exception e) {
		}
		ModelAndView mv = this.getModelAndView();
		//获取所有数据
		Collection<UserFile> userFiles = UserFiles.getInstance().getList().values();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取前台穿过来的NAME
		String NAME = pd.getString("fileName");
		if (null != NAME && !"".equals(NAME)) {
			NAME = NAME.trim();
		} else {
			NAME = "";
		}
		List<UserFile> userFileList = new ArrayList<UserFile>();
		//遍历并判断是否存在NAME
		for (UserFile userFile : userFiles) {
			if (null != userFile.getFileName() && userFile.getFileName().contains(NAME)) {
				userFileList.add(userFile);
			}
		}
		// 排序
		Collections.sort(userFileList, new Comparator<UserFile>() {
			@Override
			public int compare(UserFile o1, UserFile o2) {
				return o1.getFileName().compareTo(o2.getFileName());
			}
		});

		// 分页
		List<UserFile> userFileListPage = new ArrayList<UserFile>();
		int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
		for (int i = 0; i < showCount && i < userFileList.size() - currIdx; i++) {
			UserFile a = userFileList.get(currIdx + i);
			userFileListPage.add(a);
		}

		page.setTotalResult(userFileList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		page.setCurrentPage(page.getCurrentPage());
		
		mv.setViewName("userfile_list");
		mv.addObject("varList", userFileListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	//提供一个给其他弹出窗口调用选择文件的界面
	@RequestMapping(value="/selectFile_List.do")
	public ModelAndView selectFile(Page page, HttpServletRequest request)
	{
		int currentPage = 0;
		
		List<UserFile> usfilesList=new ArrayList<UserFile>();
		//从api获取文件数据
		usfilesList.addAll(UserFiles.getInstance().getList().values());
		
		//获取分页数据
		List<UserFile> usfilesListPage=getCurrentPageData(usfilesList,currentPage,this.pageSize);
		
		page.setTotalResult(usfilesList.size());
		page.setTotalPage(page.getTotalPage());
		page.setCurrentPage(currentPage);
		page.setShowCount(this.pageSize);
		page.setCurrentPage(page.getCurrentPage());
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("selectfile_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("varList", usfilesListPage);
		mv.addObject("pd", pd);
		return mv;
	}
	//打开上传文件页面
	@RequestMapping(value="/uploadpage.do")
	public ModelAndView gotoUploadPage()
	{
		logBefore(logger, "打开上传文件页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("attachment");
		mv.addObject("pd",pd);
		return mv;
	}
	// 处理上传的图片数据
	@RequestMapping(value="/fileUploadBatch.do")
	@ResponseBody
	public Object uploadImage(@RequestParam(required=false) MultipartFile file) throws Exception
	{
		logBefore(logger, "处理上传的文件");
		PageData pd = new PageData();
		String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String  ffile = DateUtil.getDays(), fileName = "";
		String filePath;
		if(file.getContentType().indexOf("image") != -1){//图片
			filePath = PathUtil.getClasspath() + "uploadFiles/uploadImgs/" + ffile;//文件上传路径
			pd.put("attachment_type", "image");
			pd.put("attachment_url", "uploadFiles/uploadImgs/"+ffile+"/"+fileName);//url
			
			//fileName = FileUpload.fileUp(file, filePath, this.get32UUID());//上传到本地服务器，
			//上传到定位引擎
			System.out.println("开始上传到引擎");
			UserFile ufile=new UserFile(file.getOriginalFilename());
			ufile.setFileContent(file.getBytes());
			ufile.save();
			System.out.println("上传到引擎完毕");
			//if(file.getContentType().indexOf("image") != -1){
				//加水印
				//Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);
			//	}
			pd.put("attachment_name", fileName);//文件名
			pd.put("attachment_suffix", extName);
			pd.put("attachment_local_path", filePath);
			pd.put("attachment_id", "");
			
			Integer attachmentId = 0;//Integer.valueOf(pd.getString("attachment_id").toString());
			pd.put("result", "ok");
			pd.put("attachmentId", attachmentId);
		}else{
			//不是图片就不处理了
			
		}
		return pd;
	}
	//删除图片
    @RequestMapping(value="/delPicture.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object delPicture(String fileName) {
    	logBefore(logger, "删除文件");
    	//判断获取的文件名是否为空
    	if(null == fileName) {
    		return "";
    	}
    	try {
			//执行删除操作
    		List<Basemap> baseMpas=new ArrayList<Basemap>();
    		baseMpas.addAll(Basemaps.getInstance().getList().values());
    		boolean isQuote=false;
    		for (Basemap basemap : baseMpas) {
				if(basemap.getBasemapBitmapFilename().equals(fileName))
				{
					//说明该文件被引用了，不能删除
					isQuote=true;
					break;
				}
			}
    		if(!isQuote)
    		{
    			UserFiles.getInstance().remove(fileName);
    			return "ok";
    		}else
    		{
    			return "该文件已被引用，无法删除";
    		}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			return "no Success";
		}
    }
	
	/**
	 * 返回分页数据
	 * @param usfilesList
	 * @param currentPage
	 * @param showCount
	 * @return
	 */
	public List<UserFile> getCurrentPageData(List<UserFile> usfilesList,int currentPage,int showCount)
	{
		// 排序
		Collections.sort(usfilesList, new Comparator<UserFile>() {
							@Override
							public int compare(UserFile o1, UserFile o2) {
								return o1.getFileName().compareTo(o2.getFileName());
							}
						});
		//分页
		List<UserFile> usfilesListPage = new ArrayList<UserFile>();
				int currIdx = (currentPage > 1 ? (currentPage - 1) * showCount : 0);
				for (int i = 0; i < showCount && i < usfilesList.size() - currIdx; i++) {
					UserFile a = usfilesList.get(currIdx + i);
					usfilesListPage.add(a);
				}
		return usfilesListPage;
				
	}
	
	
}
