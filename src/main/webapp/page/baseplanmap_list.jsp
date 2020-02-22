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
	<title>平面图管理</title>
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
		<form action="floorPlanList.do" method="post" name="Form" id="Form">
			<div class="row table-query">
				<table>
					<tr>
						<td><input class="form-control input-sm"  id="nav-search-input" type="text" name="NAME" value="${pd.NAME}" placeholder="平面图名称" /></td>
						<td><a class="btn btn-block btn-default btn-sm btn-flat" onclick="query();" title="检索"><i class="fa fa-search" style="color: #3C8DBC;"></i></a></td>
					</tr>
				</table>
			</div>
			<div class="row" >
				<table id="table_report" class="table table-striped table-condensed table-bordered table-hover" style="margin:5px 0px 0px 2px;">
					<thead>
						<tr>
							<th style="width: 20px;"><input type="checkbox" id="zcheckbox" /></th>
							<th style="width: 50px;">序号</th>
							<th style="width: 128px;">平面图ID</th>
							<th >平面图名称</th>
							<th style="width: 100px;">底图类型</th>
							<th style="width: 200px;">底图名称</th>
							<th style="width: 200px;">中心坐标</th>
							<th style="width: 200px;">备注</th>
							<th style="text-align: center;width:200px;">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty varList}">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td><input type='checkbox' name='ids' value="${var.id}" /></td>
									<td>${vs.index+1}</td>
											<td>${var.id}</td>
											<td>${var.name}</td>
											<td><c:if test="${var.basemapType==0}">位图</c:if><c:if test="${var.basemapType ==1}">矢量图</c:if></td>
											<td>${var.basemapBitmapFilename}</td>
											<td>(${var.centerX},${var.centerY})</td>
											<td>${var.comments}</td>
									<td style="width: 100px;text-align: center;">
										
										<button type="button" class="btn bg-purple btn-xs btn-flat" onclick="gotomap('${var.id }');">平面图配置</button>
										<a class="btn btn-social-icon  btn-primary btn-xs btn-flat"  title="编辑" onclick="edit('${var.id}');"><i class="fa fa-edit"></i></a>
										<a class="btn btn-social-icon btn-danger btn-xs btn-flat" title="删除" onclick="delPlanMap('${var.id}');"><i class="fa fa-trash"></i></a>
									</td>
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
			
		<div class="row page-header position-relative" style="margin-left: -12px;margin-right:-12px;">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<a class="btn btn-small btn-success btn-sm btn-flat" onclick="add();">新增</a>
					<!-- <a class="btn btn-small btn-danger btn-sm btn-flat"" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='fa fa-trash'></i></a> -->
				</td>
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
		
		
		//检索
		function query(){
			showLoading();//显示遮罩层
			$("#Form").submit();
		}
		
		//新增
		function add(){
			 var diag = new Dialog();
			 diag.Width = 700;
			 diag.Height = 400;
			 diag.Title = "新增";
			 diag.URL = '${basePath}/floorPlan/add.do';
			 diag.OKEvent = function(){
				diag.innerFrame.contentWindow.save();
			 };
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//修改窗口
		function edit(Id){
			var diag = new Dialog();
			diag.Width = 700;
			diag.Height = 400;
			diag.Title ="编辑";
			diag.URL = "${basePath}/floorPlan/edit.do?planMapId="+Id;
			diag.OKEvent = function(){
				diag.innerFrame.contentWindow.save();
			};
			diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
			 
		}
		
		//删除一个平面图
		function delPlanMap(Id){
			Dialog.confirm("确定要删除吗?", function(){
				showLoading();//显示遮罩层
				$.ajax({
					type: "POST",
					url: "${basePath}/floorPlan/delete.do?id="+Id+"&tm="+new Date().getTime(),//删除操作地址
					dataType:'json',
					cache: false,
					success: function(data){
						debugger;
						hideLoading(); 
						if(data.status=="ok")
							{
								Dialog.alert(data.msg);		
								query();
							}else
								{
									console.log(data.name);
									Dialog.alert("该平面图已被楼层   "+data.name+" 占用，请解除该平面图后删除！");	
								}	
					},
				     error : function(XMLHttpRequest, textStatus, errorThrown) {
				    	 hideLoading();
				    	 Dialog.alert(getError());
				     }
				});
			});
			
		}
		
		//跳转到在地图上设置平面图的比列
		function gotomap(Id){
			window.parent.addTab("fa-bars-plance-pic-map","fa fa-file-picture-o","平面图配置-地图","${basePath}/floorPlan/planMapIndex.do?planMapId="+Id);
		}
			
		</script>
	</body>
</html>

