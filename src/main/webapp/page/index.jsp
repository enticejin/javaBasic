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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>	${sysname}</title>
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

<%-- <!-- iCheck -->
<link rel="stylesheet" href="${basePath}/admin/plugins/iCheck/flat/blue.css">
<!-- Morris chart -->
<link rel="stylesheet" href="${basePath}/admin/plugins/morris/morris.css">
<!-- jvectormap -->
<link rel="stylesheet"
	href="${basePath}/admin/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<!-- Date Picker -->
<link rel="stylesheet" href="${basePath}/admin/plugins/datepicker/datepicker3.css">
<!-- Daterange picker -->
<link rel="stylesheet" href="${basePath}/admin/plugins/daterangepicker/daterangepicker.css">
<!-- bootstrap wysihtml5 - text editor -->
<link rel="stylesheet"
	href="${basePath}/admin/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.css">
	--%>
<!-- bootstrap-addtabs -->
<link rel="stylesheet" href="${basePath}/staticres/plugins/bootstrap-addtabs/css/bootstrap-addtabs.css">
<!-- 加载遮罩层插件 -->
<link rel="stylesheet" href="${basePath}/staticres/plugins/showLoading/css/showLoading.css" />  
</head>
<!-- id=activity_pane作为加载遮罩层名称 -->
<body id="activity_pane" class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<header class="main-header">
				<!-- Logo -->
				<a href="javascript:void(0);" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
					<span class="logo-mini"><b>R</b>TLS</span> <!-- logo for regular state and mobile devices -->
					<span class="logo-lg"><b>联创科技</b>[RTLS]</span>
				</a>
				<!-- Header Navbar: style can be found in header.less -->
				<nav class="navbar navbar-static-top">
					<!-- Sidebar toggle button-->
					
					<div class="navbar-custom-menu">
						<ul class="nav navbar-nav">
							
							<li class="dropdown user user-menu">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
									<c:if test="${not empty current_login_user.attachment_url}">
										<img src="${current_login_user.attachment_url}" class="user-image" alt="User Image">
									</c:if>
									<c:if test="${empty current_login_user.attachment_url}">
										<img src="admin/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
									</c:if>
									<span class="hidden-xs">${current_login_user.nickname}</span>
								</a>
								<ul class="dropdown-menu">
									<!-- User image -->
									<li class="user-header">
									<c:if test="${not empty current_login_user.attachment_url}">
										<img src="${current_login_user.attachment_url}" class="img-circle" alt="User Image">
									</c:if>
									<c:if test="${empty current_login_user.attachment_url}">
										<img src="admin/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
									</c:if>
										<p><small>Email：gzasor@foxmail.com</small></p>
										<p><small>电话：0851-87990016</small></p>
									</li>
									
									<li class="user-footer">
										<div class="pull-left">
											<a href="javascript:void(0);"class="btn btn-default btn-flat" onclick='modifyPwd();'>密码修改</a>
										</div>
										<div class="pull-right">
											<a href="login/logout" class="btn btn-default btn-flat">注销</a>
										</div>
									</li>
								</ul>
							</li>
							<!-- Control Sidebar Toggle Button -->
							<li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li>
						</ul>
					</div>
				</nav>
		</header>
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
			<!--Main content-->
			<section class="content" style="padding: 3px 3px 0px 0px;">
				<div class="main">
					<div id="tabs">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs" role="tablist">
							
						</ul>
						<!-- Tab panes -->
						<div class="tab-content"  style="margin-bottom: -5px;">
							
						</div>
					</div>
				</div>
			</section>
			<!-- right col -->
		</div>

	<!-- /.content-wrapper -->
		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 2.3.6
			</div>
			<strong>Copyright &copy; 2017-2018 LC-TECHNOLOGY.
			</strong> 版权所有.
		</footer>
	</div>
	<!-- jQuery 2.2.3 -->
	<script src="${basePath}/staticres/js/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${basePath}/staticres/bootstrap/js/bootstrap.js"></script>
	<!-- bootstrap-addtabs -->
	<script src="${basePath}/staticres/plugins/bootstrap-addtabs/js/bootstrap-addtabs.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/showLoading/js/jquery.showLoading.js"></script>
	<script src="${basePath}/staticres/dist/js/demo.js"></script>
	<script type="text/javascript">
			$(function () {
				//加载首页
				Addtabs.add({
			        id: 'fa-home',
			        title: '<i class="fa fa-home"></i>首页',
			        url: '${basePath}/homepage.do',
			        isfirst:true,
			        iframeHeight:$(document).height() - 151 //固定TAB中IFRAME高度,根据需要自己修改
			    }); 
			});
			
			//在Tab栏上打开
			function addTab(id,ico,title,url){
				Addtabs.add({
			        id: id,
			        title: '<i class="'+ico+'"></i>'+title,
			        url: '${bashPath}'+url,
			        isfirst:false,
			        close:true,
			        iframeHeight:$(document).height() - 151 //固定TAB中IFRAME高度,根据需要自己修改
			    });
			}
			
			//显示遮罩加载层
			function showLoading (){
				$("#activity_pane").showLoading();
			}
			
			//隐藏遮罩加载层
			function hideLoading(){
				$('#activity_pane').hideLoading();
			}
	  </script>
</body>
</html>