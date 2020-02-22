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
<title>电子围栏区域新增/修改</title>
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
	<form action="saveGuardarea.do?flag=${pd.flag}" name="userForm"
		id="userForm" method="post">
		<input type="hidden" id="guardId" name="guardId" value="${pd.guardInfo.id}">
		<div id="zhongxin">
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 150px; text-align: right;"><font color="red">*
					</font>电子围栏名称:</td>
					<td><input type="text" class="form-control input-sm" id="name"
						name="name" value="${pd.guardInfo.name}" placeholder="这里输入电子围栏名称"
						title="电子围栏名称"></td>
				</tr>
				
				<tr>
					<td style="width: 100px; text-align: right;">是否启用:</td>
					<td><select id="enable" name="enable"
						class="form-control select2">
							<option value=1 <c:if test="${pd.guardInfo.enable == true}">selected</c:if>>是</option>
							<option value=0 <c:if test="${pd.guardInfo.enable == false}">selected</c:if>>否</option>
					</select></td>
				</tr>
				
				<tr>
					<td style="width: 100px; text-align: right;">触发方式:</td>
					<td colspan="3"><select style="width: 300px;" id="locationTrigger"
						name="locationTrigger" class="form-control select2">
							<c:forEach items="${pdListLQ}" var="var" varStatus="vs">
								<option value="${var.id}"
									<c:if test="${pd.guardInfo.triggerMode == var.id}">selected</c:if>>${var.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					
					<td style="width: 100px; text-align: right;"><font color="red">
							* </font>选择楼层</td>
					<td><select style="width: 300px;" id="specifyFloorsId"
						name="specifyFloorsId" class="form-control select2">
							<option value="">无</option>
							<c:forEach items="${pdListFloors}" var="var" varStatus="vs">
								<option value="${var.id}"
									<c:if test="${pd.floor == var.id}">selected</c:if>>${var.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<td style="width: 105px; text-align: right;">备注信息:</td>
					<td><input type="text" class="form-control input-sm"
						id="comments" name="comments" value="${pd.guardInfo.comments}"
						placeholder="这里输入备注信息" title="备注信息"></td>
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
			var result = "OK";
            var name = $("#name").val();//名称
            if(name == "") return "电子围栏名称不能为空!";
            var specifyFloorsId = $("#specifyFloorsId").val();
            if(specifyFloorsId == "") return "请选择楼层";
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