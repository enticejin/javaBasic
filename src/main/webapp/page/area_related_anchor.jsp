<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

<link rel="stylesheet" href="${basePath}/staticres/plugins/iCheck/all.css">
<!-- iCheck -->
<link rel="stylesheet" href="${basePath}/staticres/plugins/iCheck/flat/blue.css">

<%-- 
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
	<style type="text/css">
		.table-query td{
			padding:2px;
		} 
	</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="container-fluid" style="padding:10px 23px 10px 23px;">
		<!-- 检索  -->
		<form action="list.do" method="post" name="Form" id="Form">
			<div class="row table-query">
				<table>
					<tr>
						<td><input class="form-control input-sm"  id="nav-search-input" type="text" name="NAME" value="${pd.NAME }" placeholder="基站名称" /></td>
						<td><a class="btn btn-block btn-default btn-sm btn-flat" onclick="query();" title="检索"><i class="fa fa-search" style="color: #3C8DBC;"></i></a></td>
					</tr>
				</table>
			</div>
			<div class="row" >
				<table id="table_report" class="table table-striped table-condensed table-bordered table-hover" style="margin:5px 0px 0px 2px;">
					<thead>
						<tr>
							<th><input type="checkbox" id="zcheckbox" /></th>
							<th>基站编号</th>
							<th>基站名称</th>
							<th>序列号</th>
							<th>X坐标</th>
							<th>Y坐标</th>
							<th>Z坐标</th>
							<td>状态</td>
							
							
							
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty varList}">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td><input type='checkbox' name='ids' value="${var.id}" /></td>
											<td>${var.id}</td>
											<td>${var.name}</td>
											<td>${var.serialNo}</td>
											<td id="x_${var.id }">${var.x}</td>
											<td id="y_${var.id }">${var.y}</td>
											<td id="z_${var.id }">${var.z}</td>
											<td>
												<c:if test ="${var.online}">在线</c:if>
												<c:if test ="${!var.online}">离线</c:if>
											</td>
											
											
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="100" style="text-align: center;" >没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			<div class="row page-header position-relative" style="margin-left: -12px;margin-right:-12px;">
			<table style="width:100%;">
				<tr>
					<td style="vertical-align:top;"><div style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
				</tr>
			</table>
			</div>
		</form>
	</div>
		
		
		
	<!-- jQuery 2.2.3 -->
	<script src="${basePath}/staticres/js/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${basePath}/staticres/bootstrap/js/bootstrap.js"></script>
	<!-- bootstrap-addtabs -->
	<script src="${basePath}/staticres/plugins/bootstrap-addtabs/js/bootstrap-addtabs.js"></script>
	<script src="${basePath}/staticres/plugins/showLoading/js/jquery.showLoading.js"></script>
	<script src="${basePath}/staticres/dist/js/demo.js"></script>
	<!-- 公共js文件 -->
	<script src="${basePath}/staticres/js/commjs.js"></script>
	<!-- iCheck 1.0.1 -->
	<script src="${basePath}/staticres/plugins/iCheck/icheck.min.js"></script>
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDialog.js"></script>
	
	<script type="text/javascript">
		$(function () {
			hideLoading();//隐藏遮罩层
			$('input').iCheck({
			      checkboxClass: 'icheckbox_minimal-blue',
			      radioClass: 'iradio_minimal-blue'
			 });
			
			//复选框选中事件
			$('table th input:checkbox').on('ifChecked' , function(){
				$(this).closest('table').find('tr > td:first-child input:checkbox').each(function(){
					$(this).iCheck('check');
				});
			});
			
			//复选框取消选中事件
			$('table th input:checkbox').on('ifUnchecked' , function(){
				$(this).closest('table').find('tr > td:first-child input:checkbox').each(function(){
					$(this).iCheck('uncheck');
				});
			});		
		});
		//保存
		function save(){
			submitAnchorForm();
		}
		//异步传输数据
		function submitAnchorForm(){
			var areaId ="${pd.areaId}";
			var anchorIdListByArea ="${pd.anchorIdListByArea}";
			var mydata = $("#Form").serialize();  
			$.ajax({
				type:'POST',  
			    url:'${basePath}/need_show_area_range/save.do?areaId='+areaId+'&flag=anchor'+'&anchorIdListByArea='+anchorIdListByArea, 
			    cache: false,
			    data:mydata,  //重点必须为一个变量如：data
			    dataType:'json', 
			    success:function(data){
			    	if (data.status=="errname") {
			    		Dialog.alert(data.msg);
			    		parent.window.showDialog.close();
					}else{
						parent.query();
					}
			    	
			    },
			    error:function(){ 
			    	Dialog.alert("请求失败")
			    	
			    }
			   })
		}
		//检索
		function query(){
			showLoading();//显示遮罩层
			$("#Form").submit();
		}
		</script>
	</body>
</html>

