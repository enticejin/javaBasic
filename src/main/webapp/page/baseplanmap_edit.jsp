<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加平面图</title>
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
	<style type="text/css">
		.table-query td{
			padding:2px;
		} 
	</style>
</head>
<body style="overflow-x: hidden; overflow-y: hidden;">
	<form action="savePlanMap.do?flag=${pd.flag}" name="userForm" id="userForm" method="post">
		<input type="hidden" id="basemapId" name="basemapId" value="${pd.bsasemap.id}">
		<div id="zhongxin">
			<table border="1" id="table_report" class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 100px; text-align: right;">平面图名称:</td>
					<td><input type="text" class="form-control input-sm" name="planmapname" id="planmapname" value="${pd.bsasemap.name}" placeholder="这里输入平面图的名称" title="平面图名称"></td>
					<td style="width: 100px; text-align: right;">平面图类型:</td>
					<td><select id="pictype" name="pictype" class="form-control select2" style="height:30px;padding: 3px 12px;">
							<option value=0 <c:if test="${pd.bsasemap.basemapType == '0'}">selected</c:if>>位图</option>
							<option value=1 <c:if test="${pd.bsasemap.basemapType == '1'}">selected</c:if>>矢量图</option>
					</select>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;">透明度:</td>
					<td><input type="text" class="form-control input-sm" name="opacity" id="opacity" value="${pd.bsasemap.opacity}" placeholder="这里设置透明度" title=""></td>
					<td style="width: 100px; text-align: right;">旋转角度:</td>
					<td><input type="text" class="form-control input-sm"
						name="rotate" id="rotate" value="${pd.bsasemap.rotate}"
						placeholder="设置旋转角度" title="">
					</td>
				</tr>
				
				<tr>
					<td style="width: 100px; text-align: right;">中心经度:</td>
					<td><input type="text" class="form-control input-sm" name="centerlon" id="centerlon" value="${pd.lon}" placeholder="这里设置经度" title=""></td>
					<td style="width: 100px; text-align: right;">中心维度:</td>
					<td><input type="text" class="form-control input-sm"
						name="centerlat" id="centerlat" value="${pd.lat}"
						placeholder="这里设置纬度" title="">
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;">X缩放:</td>
					<td><input type="text" class="form-control input-sm" name="scalex" id="scalex" value="${pd.bsasemap.scaleX}" placeholder="X的缩放值" title=""></td>
					<td style="width: 100px; text-align: right;">Y缩放:</td>
					<td><input type="text" class="form-control input-sm"
						name="scaley" id="scaley" value="${pd.bsasemap.scaleY}"
						placeholder="Y的缩放值" title="">
					</td>
				</tr>
				
				<tr>
					<td style="width: 100px; text-align: right;">平面图文件:</td>
					<td colspan="3">
						<div style="width:100%">
							<div style="float: left;width: 447px">
								<input type="text" class="form-control input-sm" name="palnmapfilename" id="palnmapfilename" value="${pd.bsasemap.basemapBitmapFilename}" placeholder="这里选择平面图对应的文件" title="">
							</div>
							<div style="float:right;width:50px;margin-right:50px;">
								<input type="button" onclick="selectFile()" value="点击选择文件" />
							</div>
						</div>
					</td>
					
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;">备注:</td>
					<td colspan="3"><textarea  class="form-control input-sm"
						name="beizhu" id="beizhu" rows="3"
						placeholder="这里输入备注" title="备注">${pd.bsasemap.comments}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id="zhongxin2" class="center" style="display: none">
			<br />
			<br />
			<br />
			<br />
			<br />
			<img src="${basePath}/staticres/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
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
		 //验证数据项
        function validate(){
			var planmapname = $("#planmapname").val();//平面图名称
			if(planmapname=="") return "平面图名称不能为空";
			var opacity=$("#opacity").val();   //透明度
			if(isNumber(opacity)) return "透明度必须是数字"  //
			var rotate=$("#rotate").val();   //旋转角度
			if(isNumber(rotate)) return "旋转角度必须是数字"  //
			var centerlon=$("#centerlon").val();//经度
			if(isNumber(centerlon)) return "经度必须是数字"  //
			var centerlat=$("#centerlat").val();//经度
			if(isNumber(centerlat)) return "纬度必须是数字"  //
			var scalex=$("#scalex").val();   //缩放x的比例
			if(isNumber(scalex)) return "x缩放比例必须是数字"  //
			var scaley=$("#scaley").val();   //缩放y的比例
			if(isNumber(scaley)) return "y缩放比例必须是数字"  //
			var palnmapfilename=$("#palnmapfilename").val();//底图的文件名
			if(planmapname=="") return "底图文件不能为空";
			var beizhu=$("#beizhu").val();  //备注
            return "OK";
        }
		 //判断是否是数字
        function isNumber(val){
            var regPos = /^\d+(\.\d+)?$/; //非负浮点数
            var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
            if(regPos.test(val) && regNeg.test(val)){
                return true;
            }else{
                return false;
            }
        }
	  //打开选择平面图的文件
	  function selectFile()
	  {
		 var diag = new Dialog();
		 diag.Width = 500;
		 diag.Height = 280;
		 diag.Title ="选择文件";
		 diag.URL = "${basePath}/userFile/selectFile_List.do";
		 diag.OKEvent = function(){
					//diag.innerFrame.contentWindow.save();
			 		var fileName=diag.innerFrame.contentWindow.getSelectFileName();
			 		console.info("t:"+fileName);
			 		if(fileName=='')
			 			{
							Dialog.alert("提示：你没有选择任何内容,若想取消选择，请点击【取消】按钮");
			 			}else
			 				{
			 				$("#palnmapfilename").val(fileName);
			 				diag.close();
			 			}
				};
		 diag.CancelEvent = function(){ //关闭事件
					diag.close();
				 };
		 diag.show();  
	  }
		
		//保存
		function save(){
			var flag = validate();
			if(flag == "OK"){
				//提交表单	
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
	</script>
</body>
</html>