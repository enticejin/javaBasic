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
<title>区域复制</title>
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
	<form action="saveArea.do?flag=${pd.flag}" name="userForm"
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
					<td style="width: 110px; text-align: right;"><font color="red">*
					</font>区域名称:</td>
					<td><input type="text" class="form-control input-sm" id="name"
						name="name" value="${pd.areaInfo.name}副本" placeholder="这里输入区域名称"
						title="区域名称"></td>
					<td style="width: 105px; text-align: right;">备注信息:</td>
					<td><input type="text" class="form-control input-sm"
						id="comments" name="comments" value="${pd.areaInfo.comments}"
						placeholder="这里输入备注信息" title="备注信息"></td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;"><font color="red">
							* </font>定位维度:</td>
					<td><select style="width: 270px;" id="locationDimensionality"
						name="locationDimensionality" class="form-control select2">
							<c:if test="${pd.flag == 'add'}">
								<c:forEach items="${pdListLD}" var="var" varStatus="vs">
									<option value="${var.id}"
										<c:if test="${pd.areaInfo.locationDimensionality == var.id}">selected</c:if>>${var.name}</option>
								</c:forEach>
							</c:if>
							<c:if test="${pd.flag == 'copy'}">
								<c:forEach items="${pdListLD}" var="var" varStatus="vs">
									<option value="${var.id}"
										<c:if test="${pd.areaInfo.locationDimensionality == var.id}">selected</c:if>>${var.name}</option>
								</c:forEach>
							</c:if>
					</select></td>

					<td style="width: 100px; text-align: right;"><font color="red">
							* </font>时钟源</td>
					<td><select style="width: 270px;" id="clockSourceId"
						name="clockSourceId" class="form-control select2">
							<option value="">无</option>
							<c:forEach items="${pdClockSource}" var="var" varStatus="vs">
								<option value="${var.id}"
									<c:if test="${pd.clockSourceId == var.id}">selected</c:if>>${var.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>

					<td style="width: 100px; text-align: right;"><font color="red">
							* </font>触发方式:</td>
					<td colspan="3"><select style="width: 270px;" id="locationTrigger"
						name="locationTrigger" class="form-control select2">
							<c:forEach items="${pdListLQ}" var="var" varStatus="vs">
								<option value="${var.id}"
									<c:if test="${pd.locationTrigger == var.id}">selected</c:if>>${var.name}</option>
							</c:forEach>
					</select></td>
					
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;"><font color="red">
							* </font>Z坐标缺省高度:</td>
					<td><input type="text" class="form-control input-sm"
						id="defaultZ_Value" name="defaultZ_Value"
						value="${pd.areaInfo.defaultZ}" placeholder="这里输入Z坐标缺省值"
						title="Z坐标缺省值"></td>
					<td style="width: 100px; text-align: right;"></td>
					<td style="text-align: left;">
						<div class="checkbox icheck">
							<label><input id="useAverageFilter"
								name="useAverageFilter" type="checkbox"> 使用平均值滤波器</label>
						</div>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;"><font color="red">
							* </font>平均滤波时长(毫秒):</td>
					<td><input type="text" class="form-control input-sm"
						id="averageFilterSampleTimeLength"
						name="averageFilterSampleTimeLength"
						value="${pd.areaInfo.averageFilterSampleTimeLength}"
						placeholder="输入平均值滤波器时长单位是毫秒" title="输入平均值滤波器时长单位是毫秒"></td>
					<td style="width: 100px; text-align: right;"></td>
					<td style="text-align: left;">
						<div class="checkbox icheck">

							<label><input id="discardPointsOfOutOfArea"
								name="discardPointsOfOutOfArea" type="checkbox">
								是否启用边界限制</label>
						</div>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;"><font color="red">
							* </font>卡尔曼:</td>
					<td><input type="text" class="form-control input-sm"
						id=kalmanFilterLevel name="kalmanFilterLevel"
						value="${pd.areaInfo.kalmanFilterLevel}" placeholder="这里输入卡尔曼滤波器"
						title="卡尔曼滤波器"></td>
					<td style="width: 100px; text-align: right;"></td>
					<td style="text-align: left;">
						<div class="checkbox icheck">
							<label><input id="useKalmanFilter" name="useKalmanFilter"
								type="checkbox"> 使用卡尔曼滤波器</label>
						</div>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;"><font color="red">
							* </font>定位基站:</td>
					<td style="text-align: left;"><select style="width: 270px;"
						id="specifyAnchorsId" multiple="multiple" name="specifyAnchorsId"
						class="form-control select2">
							<c:forEach items="${pdListAnchors}" var="var" varStatus="vs">
								<option value="${var.id}"
									<c:if test="${var.state == 1}">selected</c:if>>${var.name}</option>
							</c:forEach>
					</select></td>
					<td style="width: 100px; text-align: right;"></td>
					<td style="text-align: left;">
						<div class="checkbox icheck">
							<label><input id="active" name="active" type="checkbox"> 是否激活(激活才会定位)</label>
						</div>
					</td>
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
			if("${pd.flag}"=="copy")
			{
				initCheckbox();
			}
			//处理是否激活
			$('#active').on('ifChecked', function(event){ 
				$("#activeValue").val(true);
			}); 
			$('#active').on('ifUnchecked', function(event){
				$("#activeValue").val(false);
			}); 
			//使用平均值滤波器
			$('#useAverageFilter').on('ifChecked', function(event){ 
				$("#useAverageFilterValue").val(true);
			}); 
			$('#useAverageFilter').on('ifUnchecked', function(event){
				$("#useAverageFilterValue").val(false);
			}); 
			//使用卡尔曼滤波器
			$('#useKalmanFilter').on('ifChecked', function(event){ 
				$("#useKalmanFilterValue").val(true);
			}); 
			$('#useKalmanFilter').on('ifUnchecked', function(event){
				$("#useKalmanFilterValue").val(false);
			}); 
			
			//是否启动边界限制
			$('#discardPointsOfOutOfArea').on('ifChecked', function(event){ 
				$("#discardPointsOfOutOfAreaValue").val(true);
			}); 
			$('#discardPointsOfOutOfArea').on('ifUnchecked', function(event){
				$("#discardPointsOfOutOfAreaValue").val(false);
			}); 
		 });
	
		 //验证
        function validate(){
			 debugger;
			var result = "OK";
            var name = $("#name").val();//区域名称
          	var needAnchorNum = $("#needAnchorNum").val();//最小基站数
            var averageFilterSampleNum = $("#averageFilterSampleTimeLength").val();//使用平均值滤波器
            var kalmanFilterLevel = $("#kalmanFilterLevel").val();
            var specifyAnchorsId = $("#specifyAnchorsId").val();
            $("#specifyAnchorsIdlist").val(specifyAnchorsId);
            
            var clockSourceId = $("#clockSourceId").val();
            var areaId = $("#id").val();
            if(name == "") return "区域名称不能为空!";
            //if(needAnchorNum == "") return "最小基站数不能为空!";
          	//if(!checkNumber(needAnchorNum)) return "最小基站数只能为数值!";
			if(needAnchorNum == 0) return "最小基站数必须大于0!";
			//if(isNaN($("#signalErrorConstant").val())) return "信号强度误差参数只能为数值!";
			//if(isNaN($("#coordinateValidTime").val())) return "坐标失效时间只能为数值!";
            if(averageFilterSampleNum == null || averageFilterSampleNum == "") return "平均值滤波器样本数不能为空!";
            if(!checkNumber(averageFilterSampleNum)) return "平均值滤波器样本数只能为数值型!"
            if(kalmanFilterLevel == null || kalmanFilterLevel == "") return "卡尔曼滤波器";
            if(!checkNumber(kalmanFilterLevel)) return "卡尔曼滤波器只能为数值型!";

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
			    	console.info(data.msg)
			    	parent.query();
			    },
			    error:function(){ 
			    	Dialog.alert("请求失败")
			    	
			    }
			   })
		}
      //初始化checkbox
	  function initCheckbox(){
			//判断是否激活
			if(${pd.areaInfo.enable} == true){
				$('#active').iCheck('check');
			}
				
			//使用平均值滤波器
			if(${pd.areaInfo.useAverageFilter} == true){
				$("#useAverageFilter").iCheck('check');
			}
				
			//使用卡尔曼滤波器
			if(${pd.areaInfo.useKalmanFilter} == true){
				$("#useKalmanFilter").iCheck('check');
			}
			
			
			//是否启用边界限制
			if(${pd.areaInfo.discardPointsOfOutOfArea} == true){
				$("#discardPointsOfOutOfArea").iCheck('check');
			}
	   }
		
	</script>
</body>
</html>