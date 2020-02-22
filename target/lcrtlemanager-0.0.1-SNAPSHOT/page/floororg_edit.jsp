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
<title>添加/修改楼层信息</title>
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
		.w_div1
		{
			float: left;
			background:#3c8dbc;
			border:1px solid #aaa;
			margin-left:2px;
			padding:1px 10px;
			border-radius:4px;
		}
		.w_div1 span
		{
			cursor:pointer;
			font-weight:bold;
			margin-right:5px;
			color: rgba(255, 255, 255, 0.7);
		}
		
	</style>
</head>
<body style="overflow-x: hidden; overflow-y: hidden;">
	<form action="saveFloorInfo.do?flag=${pd.flag}" name="userForm" id="userForm" method="post">
		<input type="hidden" id="floorId" name="floorId" value="${pd.floorInfo.id}">
		<div id="zhongxin">
			<table border="1" id="table_report" class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 130px; text-align: right;">楼层名称:</td>
					<td><input type="text" class="form-control input-sm" name="floorname" id="floorname" value="${pd.floorInfo.name}" placeholder="这里输入楼层名称" title="楼层名称"></td>
				</tr>
				<tr>
					<td style="width: 130px; text-align: right;">包含的平面图:</td>
					<td >
						<div style="width:100%">
							<div style="float: left;width:573px;height:120px;border:1px solid #ccc">
								<input type="hidden" id="palnmapids" name="palnmapids">
								<div id="addpalnmap" style="width:100%;color:white;padding:5px 3px">
									<c:forEach items="${pd.baseList}" var="bas" varStatus="vss">
										<div class="w_div1" id="baseid${vss.index}" value="${bas.id}"><span onclick="delebase(this)">×</span>${bas.name}</div>
									</c:forEach>
								</div>
								<%-- <textarea id="palnmapnames"  rows="5" name="palnmapnames"  class="form-control input-sm" disabled="disabled">${pd.planMapNameStr}</textarea> --%>
							</div>
							<div style="float:right;width:50px;margin-right:4px;">
								<input type="button" onclick="selectBaseMap()" value="选择..." />
							</div>
						</div>
					</td>
					
				</tr>
				<tr>
					<td style="width: 130px; text-align: right;">包含的区域列表:</td>
					<td >
						<div style="width:100%">
							<div style="float: left;width:573px;height:150px;border:1px solid #ccc">
								<input type="hidden" id="areaids" name="areaids">
								<div id="addAreaTest" style="width:100%;color:white;padding:5px 3px">
									<c:forEach items="${pd.areaList}" var="var" varStatus="vs">
										<div class="w_div1" id="divid${vs.index}" value="${var.id}"><span onclick="dele(this)">×</span>${var.name}</div>
									</c:forEach>
								</div>
							</div>
							<div style="float:right;width:50px;margin-right:4px;">
								<input type="button" onclick="selectArea()" value="选择..." />
							</div>
						</div>
					</td>
					
				</tr>
				<tr>
					<td style="width: 130px; text-align: right;">备注:</td>
					<td>
					<textarea id="comments" rows="4" name="comments" value="${pd.floorInfo.comments}" class="form-control input-sm"></textarea>
					
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
		$(function(){
			$(".w_div1").each(function(i){
				  $(this).first().on(
					function ()
					{
						console.info("你点击了我");
					}
				  ) 
			 });
				})
		//验证数据项
        function validate(){
			var floorname = $("#floorname").val();
			if(floorname=="") return "楼层名称不能为空";
			
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
        
    	//移除一个区域
        function dele(divid){
			$(divid).parent().remove();
	    }
    	
    	//移除一个平面图
		function delebase(divid){
			$(divid).parent().remove();
	    }
		 
	  //打开选择平面图的文件
	  function selectBaseMap(){
		 var basedivid = "";
			$("#addpalnmap div").each(function(){
					var idd = $(this).attr("id");
					var value = $('#'+idd).attr("value");
					basedivid = basedivid+value+",";
				});
			if (basedivid.substr(0,1)==','){
				basedivid=basedivid.substr(1);
			}
		 var baseid=basedivid.substring(0, basedivid.lastIndexOf(','));
		 var diag = new Dialog();
		 diag.Width = 500;
		 diag.Height = 280;
		 diag.Title ="选择文件";
		 diag.URL = "${basePath}/floorPlan/selectPlanList.do?baseid="+baseid+"";
		 diag.OKEvent = function(){
			 		var receivestr=diag.innerFrame.contentWindow.getMapIDs();
			 		var showName=receivestr.split('@');
			 		console.info("t:"+showName);
			 		if(showName[0]=='')
			 			{
							Dialog.alert("提示：你没有选择任何内容,若想取消选择，请点击【取消】按钮");
			 			}else
			 				{
			 				var strs= new Array(); //定义一数组
			 				strs=showName[0].split(',');
			 				strsname=showName[1].split(',');
			 				for (var i = 0; i < strs.length; i++) {
				 				var id = strs[i];
				 				var suiji = Math.random().toString(36).substr(2);
				 				$("#addpalnmap").append('<div class="w_div1" id="palnmapids'+suiji+'" value="'+id+'"><span onclick="dele(this)">×</span>'+strsname[i]+'</div>');
				 			}
			 				diag.close();
			 			}
				};
		 diag.CancelEvent = function(){ //关闭事件
					diag.close();
				 };
		 diag.show();  
	  }
	  //打开选择区域的窗口
	  function selectArea(){
		  var ids = "";
			$("#addAreaTest div").each(function(){
					var idd = $(this).attr("id");
					var value = $('#'+idd).attr("value");
					ids = ids+value+",";
				});
			if (ids.substr(0,1)==','){
				ids=ids.substr(1);
			}
		  var areid=ids.substring(0, ids.lastIndexOf(','));
		  var diag = new Dialog();
		  diag.Width = 500;
		  diag.Height = 280;
		  diag.Title ="选择文件";
		  diag.URL = "${basePath}/area/selectArea.do?areaid="+areid+"";
		  diag.OKEvent = function(){
				  	var receivestr=diag.innerFrame.contentWindow.getAreaIDs();
			 		var showName=receivestr.split('@');
			 		console.info("t:"+showName);
			 		if(showName[0]=='')
			 			{
							Dialog.alert("提示：你没有选择任何内容,若想取消选择，请点击【取消】按钮");
			 			}else{
			 				var strs= new Array(); //定义一数组
			 				strs=showName[0].split(',');
			 				strsname=showName[1].split(',');
			 				for (var i = 0; i < strs.length; i++) {
				 				var id = strs[i];
				 				var suiji = Math.random().toString(36).substr(2);
				 				$("#addAreaTest").append('<div class="w_div1" id="areaids'+suiji+'" value="'+id+'"><span onclick="dele(this)">×</span>'+strsname[i]+'</div>');
				 			}
			 				diag.close();
			 			}
					};
			 diag.CancelEvent = function(){ //关闭事件
						diag.close();
					 };
			 diag.show(); 
	   }
	   //每添加一个区域，其实是添加一个元素到显示区域中
	  function addAreaToContainer()
	   {
		   //
		  var htmltest='<span class="select2 select2-container select2-container--default select2-container--focus" dir="ltr" style="width: 270px;">'
					   +'<span class="selection">'
					   +'<span class="select2-selection select2-selection--multiple" role="combobox" aria-haspopup="true" aria-expanded="false" tabindex="-1">'
					   +'<ul class="select2-selection__rendered">'
					   +'<li class="select2-selection__choice" title="基站1">'
					   +'<span class="select2-selection__choice__remove" role="presentation">×</span>基站1'
					   +'</li></ul></span></span></span>';
			$("#addAreaTest").html(htmltest);
		   
	   }

		//保存
	  function save(){
			var flag = validate();
			var basedivid = "";
			$("#addpalnmap div").each(function(){
					var idd = $(this).attr("id");
					var value = $('#'+idd).attr("value");
					basedivid = basedivid+value+",";
				});
			if (basedivid.substr(0,1)==','){
				basedivid=basedivid.substr(1);
			}
			var baseid=basedivid.substring(0, basedivid.lastIndexOf(','));
			var baseids=$("#palnmapids").val(baseid);
			var ids = "";
			$("#addAreaTest div").each(function(){
					var idd = $(this).attr("id");
					var value = $('#'+idd).attr("value");
					ids = ids+value+",";
				});
			if (ids.substr(0,1)==','){
				ids=ids.substr(1);
			}
			var areid=ids.substring(0, ids.lastIndexOf(','));
			var idss=$("#areaids").val(areid);
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