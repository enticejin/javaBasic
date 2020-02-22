<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${basePath}/staticres/bootstrap/css/bootstrap.css">
<!-- 自定义的样式 -->
<link rel="stylesheet" href="${basePath}/staticres/css/myindex.css">
<link rel="stylesheet" href="${basePath}/staticres/dist/css/AdminLTE.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="${basePath}/staticres/dist/css/font-awesome-4.7.0/css/font-awesome.css">
<!-- Ionicons -->
<link rel="stylesheet" href="${basePath}/staticres/dist/css/ionicons-master/css/ionicons.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="${basePath}/staticres/dist/css/skins/_all-skins.css">
<!-- bootstrap-addtabs -->
<link rel="stylesheet" href="${basePath}/staticres/plugins/bootstrap-addtabs/css/bootstrap-addtabs.css">
<!-- 加载遮罩层插件 -->
<link rel="stylesheet" href="${basePath}/staticres/plugins/showLoading/css/showLoading.css" />  
</head>
<body style="background-color: #FFFFFF ;padding:20px;" scroll="no">
	<!-- 常用按钮 -->
	<div class="row">
		<div class="box box-primary">
            <div class="box-body">         	
            	<a class="btn btn-app" onclick="addTab('fa-bars-bts-config','fa fa-wifi','基站配置','anchor/list.do')"><i class="fa fa-wifi"></i>基站配置</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-clock-config','fa fa-clock-o','时钟源配置','clocksource/cslist.do');"><i class="fa fa-clock-o"></i>时钟源配置</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-area-config','fa fa-object-ungroup','区域配置','area/arealist.do');"><i class="fa fa-object-ungroup"></i> 区域配置</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-tag-config','fa fa-send-o','标签配置','tag/Taglist.do');"><i class="fa fa-send-o"></i>标签配置</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-floor-config','fa fa-send-o','楼层管理','floor/floorList.do');"><i class="fa fa-binoculars"></i>楼层管理</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-planmap-config','fa fa-send-o','平面图管理','floorPlan/floorPlanList.do');"><i class="fa fa-dashcube"></i>平面图管理</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-ufile-config','fa fa-send-o','用户文件管理','userFile/userFileList.do');"><i class="fa fa-cc-discover"></i>用户文件管理</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-user-config','fa fa-send-o','用户管理','user/userList.do');"><i class="fa fa-twitch"></i>用户管理</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-rtle-config','fa fa-send-o','RTLE配置','rtle/rtle_editIndex.do');"><i class="fa fa-soccer-ball-o"></i>RTLE配置</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-guardArea','fa fa-send-o','电子围栏管理','guardArea/guardArealist.do');"><i class="fa fa-share-alt"></i>电子围栏管理</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-tagType','fa fa-send-o','标签类型','tagType/tagTypeList.do');"><i class="fa fa-share-alt"></i>标签类型管理</a>
            	<a class="btn btn-app" onclick="addTab('fa-bars-realTimeshow','fa fa-send-o','定位演示','coordinate/showRealTimeTrack.do');"><i class="fa fa-share-alt"></i>定位演示</a>
            </div>
		</div>
	</div>
	<!-- 架构图 -->
	<div class="row">
		<div class="box box-success">
			<!-- <div class="box-header">
				<h1 class="box-title">定位单元架构图</h1>
			</div> -->
			<div class="box-body" style="text-align: center;">
				<img alt="" src="${basePath}/staticres/images/homePagePic1.png">
			</div>
		</div>
	</div>
	
	<!-- jQuery 2.2.3 -->
	<script src="${basePath}/staticres/js/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${basePath}/staticres/bootstrap/js/bootstrap.js"></script>
	<!-- bootstrap-addtabs -->
	<script src="${basePath}/staticres/plugins/bootstrap-addtabs/js/bootstrap-addtabs.js"></script>
	<script src="${basePath}/staticres/plugins/showLoading/js/jquery.showLoading.js"></script>
	<!-- 公共js文件 -->
	<script src="${basePath}/staticres/js/commjs.js"></script>
	<script type="text/javascript">
		$(function () {
			hideLoading();//隐藏遮罩层
		});
		
		//在Tab栏上打开
		function addTab(id,ico,title,url){
			console.info(id+";"+ico+";"+title+";"+url);
			window.parent.addTab(id,ico,title,url);
		}
	</script>
</body>
</html>