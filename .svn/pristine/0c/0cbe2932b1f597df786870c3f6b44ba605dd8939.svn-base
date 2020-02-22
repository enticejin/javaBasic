<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签新增</title>
<title>${sysname}</title>
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet"
	href="${basePath}/staticres/bootstrap/css/bootstrap.css">
<!-- 自定义的样式 -->
<link rel="stylesheet" href="${basePath}/staticres/css/myindex.css">
<!-- Select2 -->
<link rel="stylesheet"
	href="${basePath}/staticres/plugins/select2/select2.min.css">
<link rel="stylesheet"
	href="${basePath}/staticres/dist/css/AdminLTE.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="${basePath}/staticres/dist/css/font-awesome-4.7.0/css/font-awesome.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="${basePath}/staticres/dist/css/ionicons-master/css/ionicons.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet"
	href="${basePath}/staticres/dist/css/skins/_all-skins.css">


<link rel="stylesheet"
	href="${basePath}/staticres/plugins/iCheck/all.css">
<!-- iCheck -->
<link rel="stylesheet"
	href="${basePath}/staticres/plugins/iCheck/flat/blue.css">
<!-- bootstrap-addtabs -->
<link rel="stylesheet"
	href="${basePath}/staticres/plugins/bootstrap-addtabs/css/bootstrap-addtabs.css">
<!-- 加载遮罩层插件 -->
<link rel="stylesheet"
	href="${basePath}/staticres/plugins/showLoading/css/showLoading.css" />
<style type="text/css">
.table-query td {
	padding: 2px;
}
</style>
</head>
<body style="overflow-x: hidden; overflow-y: hidden;">
	<form action="saveTag.do?flag=${pd.flag}" name="userForm"
		id="userForm" method="post">
		<input type="hidden" id="areaId" name="areaId" value="${pd.areaInfo.id}">
		<input type="hidden" id="activeValue" name="activeValue" value="${pd.areaInfo.enable}">
		<input type="hidden" id="useAverageFilterValue" name="useAverageFilterValue" value="${pd.areaInfo.useAverageFilter}">
		<input type="hidden" id="useKalmanFilterValue" name="useKalmanFilterValue" value="${pd.areaInfo.useKalmanFilter}">
		<%-- <input type="hidden" id="useSpecifyAnchorsOnlyValue" name="useSpecifyAnchorsOnlyValue" value="${pd.areaInfo.useSpecifyAnchorsOnly}"> --%>
		<input type="hidden" id="discardPointsOfOutOfAreaValue" name="discardPointsOfOutOfAreaValue" value="${pd.areaInfo.discardPointsOfOutOfArea}">
		<input type="hidden" id="specifyAnchorsIdlist" name="specifyAnchorsIdlist">
		<div id="zhongxin">
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 110px; text-align: right;"><font color="red">
					</font>标签类型ID:</td>
					<td><input type="text" class="form-control input-sm" id="tagId"
						name="tagId" value="${pd.newTagTypeId}" readonly="readonly"
						title="标签ID">
						<td style="width: 105px; text-align: right;">标签类型名称：:</td>
					<td><input type="text" class="form-control input-sm"
						id="tagName" name="tagName" value="${pd.tagTypeInfo.name}"
						placeholder="这里输入标签名称 "></td>
				</tr>
				<tr>
					<td style="width: 105px; text-align: right;">标签类型备注：</td>
					<td><input type="text" class="form-control input-sm"
						id="comments" name="comments" value="${pd.tagTypeInfo.comments}"
						placeholder="这里输入标签备注信息" title="标签备注"></td>
						<td style="width: 105px; text-align: right;">缺省图标：</td>
					<td><input type="text" class="form-control input-sm"
						id="icon" name="defaultIcon" value="${pd.defaultIcon}" readonly="readonly"
						placeholder="这里输入缺省图标信息" title="缺省图标"></td>
				</tr>
			</table>		
		</div>
		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <br /> <img
				src="${basePath}/staticres/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>
	</form>
	<!-- jQuery 2.2.3 -->
	<script src="${basePath}/staticres/js/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${basePath}/staticres/bootstrap/js/bootstrap.js"></script>
	<!-- bootstrap-addtabs -->
	<script
		src="${basePath}/staticres/plugins/bootstrap-addtabs/js/bootstrap-addtabs.js"></script>
	<script
		src="${basePath}/staticres/plugins/showLoading/js/jquery.showLoading.js"></script>
	<!-- Select2 -->
	<script src="${basePath}/staticres/plugins/select2/select2.full.min.js"></script>
	<script src="${basePath}/staticres/dist/js/demo.js"></script>
	<!-- 公共js文件 -->
	<script src="${basePath}/staticres/js/commjs.js"></script>
	<!-- iCheck 1.0.1 -->
	<script src="${basePath}/staticres/plugins/iCheck/icheck.min.js"></script>
	<!--引入弹窗组件start-->
	<script type="text/javascript"
		src="${basePath}/staticres/plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript"
		src="${basePath}/staticres/plugins/attention/zDialog/zDialog.js"></script>

	<script type="text/javascript">	
		$(function () {
			$(".select2").select2();
			$('input').iCheck({
			      checkboxClass: 'icheckbox_square-blue',
			      radioClass: 'iradio_square-blue',
			      increaseArea: '20%' // optional
			 });
		 });
	
		 //验证
        function validate(){
			 debugger;
			var result = "OK";
			var tagName = $("#tagName").val();//标签类型名称
			var comments = $("#comments").val();//标签类型备注
			
			
			if(comments == '') return '标签类型备注不能为空';
			if(tagName == '') return '标签类型名称不能为空';
            return result;
        }
		
		//保存
		function save(){
			var flag = validate();
			if(flag == "OK"){
				submitUserForm();
			}else{
				Dialog.alert(flag);
			}
		}
	   //异步提交表单
		function submitUserForm()
		{
			var targetUrl = $("#userForm").attr("action");    
			var mydata = $("#userForm").serialize();  
			$.ajax({
				type:'POST',  
			    url:targetUrl, 
			    cache: false,
			    data:mydata,  //重点必须为一个变量如：data
			    dataType:'json', 
			    success:function(data){      
			    	console.info(data.msg);
			    	parent.query();
			    },
			    error:function(){ 
			    	Dialog.alert("请求失败")
			    	
			    }
			   })
		}
	</script>
</body>
</html>