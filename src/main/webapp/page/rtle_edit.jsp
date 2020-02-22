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
<!-- bootstrap-addtabs -->
<link rel="stylesheet" href="${basePath}/staticres/plugins/bootstrap-addtabs/css/bootstrap-addtabs.css">
<!-- 加载遮罩层插件 -->
<link rel="stylesheet" href="${basePath}/staticres/plugins/showLoading/css/showLoading.css" />  
</head>
<body style="text-align: center">
	<form action="saveRtls.do" name="Form" id="Form" method="post">
	<input id="useMeterValue" name="useMeterValue"  type="hidden">
	<input id="showRangeMessageInfoValue" name="showRangeMessageInfoValue"  type="hidden">
	<input id="showClockSourceMessageInfoValue" name="showClockSourceMessageInfoValue"  type="hidden">
		<div style="width: 840px;">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 80px; text-align: right; padding-top: 13px;">经度:</td>
					<td><input class="form-control input-sm" type="text" name="longitude" id="longitude" value="${pd.longitude}" maxlength="32" /></td>
					<td style="width: 80px; text-align: right; padding-top: 13px;">纬度:</td>
					<td><input class="form-control input-sm" type="text" name="latitude" id="latitude" value="${pd.latitude}" maxlength="32" /></td>
				</tr>
				<tr style="">
					<td style="width: 80px; text-align: right; padding-top: 13px;"></td>
					<td><div class="checkbox icheck" style="text-align:left;"><label><input id="showRangeMessageInfo" name="showRangeMessageInfo" 
					<c:if test="${pd.showRangeMessageInfo}">
					 	checked="checked"
					 </c:if>
					type="checkbox"> 显示测距消息</label></div></td>
					<td style="width: 80px; text-align: right; padding-top: 13px;"></td>
					<td><div class="checkbox icheck" style="text-align:left;"><label><input id="showClockSourceMessageInfo" name="showClockSourceMessageInfo" 
					<c:if test="${pd.showClockSourceMessageInfo}">
					 	checked="checked"
					 </c:if>
					type="checkbox"> 显示时钟消息</label></div></td>
				</tr>
				
			
			</table>
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<tr>
					<td colspan="2" align="right" style="height:40px;"><a class="btn btn-small btn-success btn-sm btn-flat" onclick="setLonLat();">设置中心经纬度</a></td>
					<td colspan="2" align="left" style="height:40px;"><a class="btn btn-small btn-danger btn-sm btn-flat" onclick="save();">保存</a></td>
				</tr>
			</table>
		</div>
	</form>	
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
	$(function() {
		hideLoading();//隐藏遮罩层
		 $('input').iCheck({
		      checkboxClass: 'icheckbox_square-blue',
		      radioClass: 'iradio_square-blue',
		      increaseArea: '20%' // optional
		 }); 
	});

	//保存
	function save() {
		if ($("#longitude").val() == "") {
			$("#longitude").tips({
				side : 3,
				msg : '请输入经度',
				bg : '#AE81FF',
				time : 2
			});
			$("#longitude").focus();
			return false;
		} else if(!checkFloat($("#longitude").val())){
			$("#longitude").tips({
				side : 3,
				msg : '请输入正确的经度',
				bg : '#AE81FF',
				time : 2
			});
			$("#longitude").focus();
		}
		if ($("#latitude").val() == "") {
			$("#latitude").tips({
				side : 3,
				msg : '请输入纬度',
				bg : '#AE81FF',
				time : 2
			});
			$("#latitude").focus();
			return false;
		}
		showLoading();//显示遮罩层
		var selState = $("#useMeter").is(':checked');
		var rangeState = $("#showRangeMessageInfo").is(':checked');
		$("#showRangeMessageInfoValue").val(rangeState);
		var clockSourceMessageState = $("#showClockSourceMessageInfo").is(':checked');
		$("#showClockSourceMessageInfoValue").val(clockSourceMessageState);
		$("#useMeterValue").val(selState);
		$("#Form").submit();
	}
	//设置经纬度
	function setLonLat(){
		var diag = new Dialog();
		if ($("#longitude").val() == "") {
			alert('经度不能为空')
			$("#longitude").focus();
			return false;
		} else if(!checkFloat($("#longitude").val())){
			$("#longitude").tips({
				side : 3,
				msg : '请输入正确的经度',
				bg : '#AE81FF',
				time : 2
			});
			$("#longitude").focus();
		}
		if ($("#latitude").val() == "") {
			alert('纬度不能为空')
			$("#latitude").focus();
			return false;
		}
		diag.Width = '70%';
		diag.Height = '78%';
		diag.Title ="地图窗口";
		diag.URL = '${basePath}/map/mapLngLat?longitude='+$("#longitude").val()+'&latitude='+$("#latitude").val();
 		diag.OKEvent = function(){
			var lonlat = diag.innerFrame.contentWindow.document.getElementById('textValue').innerHTML;
			var strs= new Array(2); //定义一数组 
			strs=lonlat.split(","); //字符分割 
			$("#longitude").val(strs[0].substring(0,11));
			$("#latitude").val(strs[1].substring(0,10));
			diag.close();
		};
		diag.CancelEvent = function(){ //关闭事件
			diag.close();
		 }; 
		 diag.show();
		 
	}
	</script>
</body>
</html>

