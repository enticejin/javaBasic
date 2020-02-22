<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="com.uwbhome.rtle.api.Tag"%>
<%@page import="com.uwbhome.rtle.api.Tags"%>
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
		<form action="Taglist.do" method="post" name="Form" id="Form">
			<div class="row table-query">
				<table>
					<tr>
						<td><input class="form-control input-sm"  id="nav-search-input" type="text" name="tagname" value="${pd.name }" placeholder="标签名称" /></td>
						<td><a class="btn btn-block btn-default btn-sm btn-flat" onclick="query();" title="检索"><i class="fa fa-search" style="color: #3C8DBC;"></i></a></td>
					</tr>
				</table>
			</div>
			<div class="row" >
				<table id="table_report" class="table table-striped table-condensed table-bordered table-hover" style="margin:5px 0px 0px 2px;">
					<thead>
						<tr>
							<th><input type="checkbox" id="zcheckbox"/></th>
							<th>id</th>
							<th>名称</th>
							<th>备注</th>
							<th>标签禁用状态</th>
							<th>最后接收消息时间</th>
							<th style="text-align: center;width: 240px;">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty varList}">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td><input type='checkbox' name='answer' id="tagId" value="${var.id}" /></td>
											<td>${var.id}</td>
											<td>${var.name}</td>
											<td>${var.comments}</td>
											<td>
												<c:if test="${var.enable == true}">
   													<p>是<p>
												</c:if>
												<c:if test="${var.enable == false}">
   													<p>否<p>
												</c:if>
											</td>
											<td>
											<fmt:formatDate value="${var.getLastMessageTime()}" pattern="yyyy-MM-dd HH:mm:ss" />
											</td>  
									<td style="width: 180px;text-align: center;">

										<a title="修改"  class="btn btn-social-icon  btn-primary btn-xs btn-flat" onclick="edit('${var.id}');"><i class="fa fa-edit"></i></a>
										
										<a class="btn btn-social-icon btn-danger btn-xs btn-flat" title="删除" onclick="del('${var.id}');"><i class="fa fa-trash"></i></a>
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
						<a class="btn btn-small btn-success btn-sm btn-flat" name="needToShowTagList" onclick="needToShowTagList();">添加至需要显示测距消息的标签列表</a>
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
		//修改区域配置
		var editDiag;
		function edit(id){
			editDiag = new Dialog();
			editDiag.Width = 800;
			editDiag.Height = 400;
			editDiag.Title = "编辑";
			editDiag.URL = '${basePath}/tag/tagEdit.do?tagId='+id;
			editDiag.OKEvent = function(){
				editDiag.innerFrame.contentWindow.save();
			};
			editDiag.CancelEvent = function(){//关闭事件
				editDiag.close();
			}
			editDiag.show(); 
		}

		//删除
		function del(Id){
			Dialog.confirm("确定要删除吗?", function(){
				showLoading();//显示遮罩层
				$.ajax({
					type: "POST",
					url: "${basePath}/tag/delete.do?tagId="+Id+"&tm="+new Date().getTime(),
					dataType:'json',
					cache: false,
					success: function(data){
						hideLoading(); 
						if(data.status=="ok")
							{
								query();
							}else
								{
									Dialog.alert(data);	
								}	
					},
				     error : function(XMLHttpRequest, textStatus, errorThrown) {
				    	 hideLoading();
				    	 Dialog.alert(getError());
				     }
				});
			});
		}
		//前往展示需要展示测距消息的标签页面
		var showDialog
		function needToShowTagList(){
			var idsList = [];
			  $('input:checkbox').each(function(){
		            if($(this).is(':checked')==true){
		                idsList.push($(this).val());
		            }
		        })
		        if(idsList.length == 0){
		        		showLoading();//显示遮罩层
						$.ajax({
							type: "POST",
							url: "${basePath}/need_show_rang/tips.do?idsList="+idsList,
							dataType:'json',
							cache: false,
							success: function(data){
								debugger;
								hideLoading(); 
								if(data.status=="ok")
									{
										Dialog.alert(data.msg);	
									}
							},
						     error : function(XMLHttpRequest, textStatus, errorThrown) {
						    	 hideLoading();
						    	 Dialog.alert(getError());
						     }
						});
		        	
		        	}
		        else{
		        	showDialog = new Dialog();
					showDialog.Width = 800;
					showDialog.Height = 500;
					showDialog.Title = "需要显示测距消息的标签列表";
					showDialog.URL = '${basePath}/need_show_rang/needToShowTagList.do?idsList='+idsList;
					showDialog.OKEvent = function(){
						showDialog.innerFrame.contentWindow.save();
					};
					showDialog.CancelEvent = function(){//关闭事件
						showDialog.close();
					}
					showDialog.show();
		        } 
		}
		//检索
		function query(){
			showLoading();//显示遮罩层
			$("#Form").submit();
		}
		</script>
	</body>
</html>

