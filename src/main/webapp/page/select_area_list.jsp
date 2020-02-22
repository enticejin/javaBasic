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
	<title>选择平面图</title>
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
	<style type="text/css">
		.table-query td{
			padding:2px;
		} 
		
	</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="container-fluid" style="padding:10px 23px 10px 23px;">
		<!-- 检索  -->
		<form action="selectPlanList.do" method="post" name="Form" id="Form">
			<input type="hidden" id="basemapids" name="basemapids" />
			<div class="row table-query">
				<table>
					<tr>
						<td><input class="form-control input-sm"  id="nav-search-input" type="text" name="query_str" value="${pd.query_str}" placeholder="平面图名称" /></td>
						<td><a class="btn btn-block btn-default btn-sm btn-flat" onclick="query();" title="检索"><i class="fa fa-search" style="color: #3C8DBC;"></i></a></td>
					</tr>
				</table>
			</div>
			<div class="row" >
				<table id="table_report" class="table table-striped table-condensed table-bordered table-hover" style="margin:5px 0px 0px 2px;">
					<thead>
						<tr>
							<th style="width: 20px;"></th>
							<th style="width: 50px;">序号</th>
							<th >区域名称</th>
							<th style="width: 200px;">备注</th>
							
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty varList}">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td><input type='checkbox' id=test${vs.index+1} name='ids' value="${var.id}"/></td>
									<td>${vs.index+1}</td>
									<td>${var.name} <input type="hidden"  name="planMapNames" value="${var.name}" /></td>
									<td>${var.comments}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="100" style="text-align: center;"  >没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			
		<div class="row page-header position-relative" style="margin-left: -12px;margin-right:-12px;background:">
			<div style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr1}</div>
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
	
	
		//获取选择了的平面图的IDs
		function  getAreaIDs(){
			var areaids = "${areaid}";
			var strs= new Array(); //定义一数组
			strs=areaids.split(',');
			var str = '';
			var planMapName='';
			var strid = '';
			var planMapNameid='';
			var idname = '';
			var idnamees = '';
			var hename = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
				  if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') {
				  		str += document.getElementsByName('ids')[i].value;
				  		planMapName+=document.getElementsByName('planMapNames')[i].value;
				  		strid = document.getElementsByName('ids')[i].value;
				  		planMapNameid = document.getElementsByName('planMapNames')[i].value;
						idnamees+=strid+":"+planMapNameid;
				  	}
				  	else {
				  		str += ',' + document.getElementsByName('ids')[i].value;
				  		planMapName+=','+document.getElementsByName('planMapNames')[i].value;
				  		strid = document.getElementsByName('ids')[i].value;
				  		planMapNameid = document.getElementsByName('planMapNames')[i].value;
						idname+=strid+":"+planMapNameid+',';
				  	}		
				  }
			 }
			 if (str!='') {
				 hename=idnamees+","+idname;
				 var name="";
				 hename = hename.substring(0, hename.lastIndexOf(','));
				 if(hename.indexOf(",")!=-1){
					 var idnames= new Array();
					 idnames = hename.split(',');
				        for (var i = 0; i < strs.length; i++) {   
								for (var k = 0; k < idnames.length; k++) {
									var strr= new Array(); //定义一数组
									strr=idnames[k].split(':');
									if (strs[i]==strr[0]) {
										name+=','+strr[1];
									}
								}						 	
						}
				        if (name!='') {
				        	Dialog.alert(name.substr(1)+"---已添加");
							return;
						}	
				       
				 }else{  
				        for (var i = 0; i < strs.length; i++) {
								if (str==strs[i]) {
									name+=','+planMapName;
								}
						}
				        if (name!='') {
							Dialog.alert(name.substr(1)+"---已添加"); 
							return;
						}
				 }  
		
			 }
			 if(str==''){
				Dialog.alert("提示：你没有选择任何内容!"); 
				return;
			 }
			 return str+"@"+planMapName;
		}
		//检索
		function query(){
			showLoading();//显示遮罩层
			$("#Form").submit();
		}	
		</script>
	</body>
</html>

