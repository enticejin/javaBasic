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
<title>LC-RTLE-MANAGER</title>
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="../staticres/css/bootstrap.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="../staticres/css/font-awesome-4.7.0/css/font-awesome.css">
<!-- Ionicons -->
<link rel="stylesheet" href="../staticres/css/ionicons-master/css/ionicons.css">
<!-- Theme style -->
<link rel="stylesheet" href="../staticres/css/AdminLTE.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="../staticres/css/skins/_all-skins.css">
<!-- iCheck -->
<link rel="stylesheet" href="../admin/plugins/iCheck/flat/blue.css">
<!-- Morris chart -->
<link rel="stylesheet" href="../admin/plugins/morris/morris.css">
<!-- jvectormap -->
<link rel="stylesheet"
	href="../admin/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<!-- Date Picker -->
<link rel="stylesheet" href="../admin/plugins/datepicker/datepicker3.css">
<!-- Daterange picker -->
<link rel="stylesheet" href="../admin/plugins/daterangepicker/daterangepicker.css">
<!-- bootstrap wysihtml5 - text editor -->
<link rel="stylesheet"
	href="../admin/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.css">
<!-- bootstrap-addtabs -->
<link rel="stylesheet" href="../admin/plugins/bootstrap-addtabs/css/bootstrap-addtabs.css">
<!-- 加载遮罩层插件 -->
<link rel="stylesheet" href="../admin/plugins/showLoading/css/showLoading.css" /> 

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.js"></script>
  <![endif]-->
</head>
<!-- id=activity_pane作为加载遮罩层名称 -->
<body id="activity_pane" class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<header class="main-header">
			<!-- Logo -->
			<a href="javascript:void(0);" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"><b>R</b>TLS</span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><b>旌霖科技</b>[RTLS]</span>
			</a>
			<!-- Header Navbar: style can be found in header.less -->
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button" > <span class="sr-only">Toggle navigation</span></a>
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
									<p><small>Email：${current_login_user.email}</small></p>
									<p><small>电话：${current_login_user.phone}</small></p>
								</li>
								<!-- Menu Body -->
								<!-- <li class="user-body">
									<div class="row">
										<div class="col-xs-4 text-center">
											<a href="#">Followers</a>
										</div>
										<div class="col-xs-4 text-center">
											<a href="#">Sales</a>
										</div>
										<div class="col-xs-4 text-center">
											<a href="#">Friends</a>
										</div>
									</div> /.row
								</li> -->
								<!-- Menu Footer-->
								<li class="user-footer">
									<div class="pull-left">
										<a href="javascript:void(0);"class="btn btn-default btn-flat" onclick='modifyPwd();'>密码修改</a>
									</div>
									<div class="pull-right">
										<a href="logout.do" class="btn btn-default btn-flat">注销</a>
									</div>
								</li>
							</ul></li>
						<!-- Control Sidebar Toggle Button -->
						<li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li>
					</ul>
				</div>
			</nav>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<div class="user-panel">
					<div class="pull-left image">
						<c:if test="${not empty current_login_user.attachment_url}">
							<img id="left_photo" src="${current_login_user.attachment_url}" class="img-circle"  alt="User Image"  style="width:45px;height: 45px;">
						</c:if>
						<c:if test="${empty current_login_user.attachment_url}">
							<img id="left_photo" src="admin/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
						</c:if>
					</div>
					<div class="pull-left info">
							<p>${current_login_user.name}</p>
							<a href="javascrip:void(0);"><i class="fa fa-circle text-success"></i>${current_login_user.nickname}</a>
					</div>
				</div>
				<header class="topbar admin-header">
					
				</header>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!--Main content-->
			<section class="content" style="padding: 3px 3px 0px 0px;">
				<div class="main">
					<div id="tabs">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs" role="tablist">
							<!-- <li role="presentation" class="active" style="padding-left: 3px;">
							<a href="#home" aria-controls="home" role="tab" data-toggle="tab"><i class="glyphicon glyphicon-floppy-disk"></i>é¦é¡µ</a></li> -->
						</ul>
						<!-- Tab panes -->
						<div class="tab-content"  style="margin-bottom: -5px;">
							<!-- <div role="tabpanel" class="tab-pane active" id="home">
				                
				            </div> -->
						</div>
					</div>
				</div>
			</section>
			<!-- right col -->
		</div>
		<!-- /.row (main row) -->
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->
 	<footer class="main-footer">
		<div class="pull-right hidden-xs">
			<b>Version</b> 2.3.6
		</div>
		<strong>Copyright &copy; 2017-2018 旌霖科技.
		</strong> 版权所有.
	</footer>

	<!-- Control Sidebar -->
	<aside class="control-sidebar control-sidebar-dark">
		<!-- Create the tabs -->
		<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
			<li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
		</ul>
		<!-- Tab panes -->
		<div class="tab-content">
			<!-- Home tab content -->
			<div class="tab-pane" id="control-sidebar-home-tab">
				<h3 class="control-sidebar-heading">开发工具</h3>
				<ul class="control-sidebar-menu">
					<li><a href="javascript:void(0)" onclick="productCode();"> <i class="menu-icon fa fa-file-code-o bg-green"></i>
							<div class="menu-info">
								<h4 class="control-sidebar-subheading">代码生成器</h4>
								<p>根据输入的参数自动生成(增、删、改、查、导出Excel)所需的代码，包括(sql、jsp文件、controller、service、mapper)。</p>
							</div>
					</a></li>
				</ul>
			</div>
		</div>
	</aside>
	<!-- /.control-sidebar -->
	<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
	<div class="control-sidebar-bg"></div>
	<!-- ./wrapper -->

	<!-- jQuery 2.2.3 -->
	<script src="../staticres/js/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="../staticres/js/bootstrap.js"></script>
	<!-- Sparkline -->
	<script src="../admin/plugins/sparkline/jquery.sparkline.js"></script>
	<!-- jvectormap -->
	<script src="../admin/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="../admin/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	<!-- jQuery Knob Chart -->
	<script src="../admin/plugins/knob/jquery.knob.js"></script>
	<!-- daterangepicker -->
	<script src="../admin/plugins/daterangepicker/moment.js"></script>
	<script src="../admin/plugins/daterangepicker/daterangepicker.js"></script>
	<!-- datepicker -->
	<script src="../admin/plugins/datepicker/bootstrap-datepicker.js"></script>
	<!-- Bootstrap WYSIHTML5 -->
	<script
		src="../admin/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.js"></script>
	<!-- Slimscroll -->
	<script src="../admin/plugins/slimScroll/jquery.slimscroll.js"></script>
	<!-- FastClick -->
	<script src="../admin/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="../admin/dist/js/app.js"></script>
	<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
	<%-- <script src="<%=path%>/admin/dist/js/pages/dashboard.js"></script> --%>
	<!-- bootstrap-addtabs -->
	<script src="../admin/plugins/bootstrap-addtabs/js/bootstrap-addtabs.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="../admin/dist/js/demo.js"></script>
	<script type="text/javascript" src="../admin/plugins/showLoading/js/jquery.showLoading.js"></script>
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="../admin/plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="../admin/plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript">
		$(function () {
			//加载首页
		  Addtabs.add({
		        id: 'fa-home',
		        title: '<i class="fa fa-home"></i>首页',
		        url: '<%=path%>/admin/page/general/homepage.jsp',
		        isfirst:true,
		        iframeHeight:$(document).height() - 151 //固定TAB中IFRAME高度,根据需要自己修改
		    }); 
		});
		
		//在Tab栏上打开
		function addTab(id,ico,title,url){
			Addtabs.add({
		        id: id,
		        title: '<i class="'+ico+'"></i>'+title,
		        url: '<%=path%>'+url,
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
		
		//关闭窗口事件
		/* window.onunload = function(){
            ajaxLogout();
        } */
       /*  function window.onUnload() { 
        	ajaxLogout();
        } */
		
		//利用AJAX来解决用户不注销关闭浏览器的问题
	    function ajaxLogout(){
	        var logoutURL = "<%=path%>/login/ajaxLogout"; //用于注销用户的url
	        var userAgent = navigator.userAgent.toLowerCase();
	        if(userAgent.indexOf("msie")>-1) { //IE
	            $.ajax({ url: logoutURL, crossDomain: true, async: false, dataType: "jsonp" });
	        }else { //FireFox Chrome
	            $.ajax({ url: logoutURL, async: false });
	        }
	    }
		
		//代码生成器
		function productCode(){
			var diag = new Dialog();
			diag.Width = 800;
			diag.Height = 400;
			diag.Title = "代码生成器";
			diag.URL = "<%=path%>/createCode/goProductCode";
			diag.CancelEvent = function(){ //关闭事件
				diag.close();
			}
			diag.show(); 
		}
		
		//修改密码
		function modifyPwd(){
			var diag = new Dialog();
			diag.Width = 400;
			diag.Height = 180;
			diag.Title = "修改密码";
			diag.URL = "#";
			diag.OKEvent = function(){
				var sourcePwd = diag.innerFrame.contentWindow.document.getElementById("sourcePwd").value;
				var newPwd= diag.innerFrame.contentWindow.document.getElementById("newPwd").value;
				var newVerifyPwd = diag.innerFrame.contentWindow.document.getElementById("newVerifyPwd").value;
				if(sourcePwd == ""){
					Dialog.alert("请输入原密码，修改密码需要进行原密码验证！");
				}else if(newPwd != newVerifyPwd){
					Dialog.alert("重复密码与新密码不匹配！");
				}else{
					$.ajax({
	                    type: 'post',
	                    url: '<%=path%>/user/editPassword',
	                    data: {
	                        sourcePwd: sourcePwd,
	                        newPwd: newPwd
	                    },success: function (data) {
	                        if (data == "OK") {
	                        	Dialog.alert("密码修改成功！");
	                            diag.close();
	                        }else{
	                        	Dialog.alert('原密码不正确，请重新输入！');	
	                        }
	                    }
	                });
				}
			};
			diag.show();
			var strHtml = '<html>';
			strHtml+='<link rel="stylesheet" href="<%=path%>/admin/bootstrap/css/bootstrap.css"><link rel="stylesheet" href="<%=path%>/admin/dist/css/AdminLTE.css">';
			strHtml+='<body style="background:#FFFFFF;padding:10px;overflow-y: hidden">';
			strHtml+='<table border="1" id="table_report" class="table table-striped table-bordered table-hover">';
			strHtml+='<tr>';
			strHtml+='<td style="width: 100px; text-align: right;">原始密码:</td>';
			strHtml+='<td><input id="sourcePwd" name="sourcePwd" class="form-control input-sm"  type="password" ></td>';
			strHtml+='</tr>';
			strHtml+='<tr>';
			strHtml+='<td style="width: 100px; text-align: right;">新密码:</td>';
			strHtml+='<td><input id="newPwd" name="newPwd" class="form-control input-sm"  type="password" ></td></td>';
			strHtml+='</tr>';
			strHtml+='<tr>';
			strHtml+='<td style="width: 100px; text-align: right;">确认密码:</td>';
			strHtml+='<td><input id="newVerifyPwd" name="newVerifyPwd" class="form-control input-sm"  type="password"></td></td>';
			strHtml+='</tr>';
			strHtml+='</table>';
			strHtml+='</body>';
			strHtml+='</html>';
			var doc=diag.innerFrame.contentWindow.document;
			doc.open();
			doc.write(strHtml) ;
			doc.close();
		}
	</script>
</body>
</html>
