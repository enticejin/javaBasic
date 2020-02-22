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
<title>添加用户</title>
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
	<form action="saveUser.do?flag=${pd.flag}" name="userForm" id="userForm" method="post">
		<div id="zhongxin">
			<table border="1" id="table_report" class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 100px; text-align: right;">用户名:</td>
					<td><input type="text" class="form-control input-sm" name="name" id="name" value="${pd.user.name}" placeholder="这里输入用户名" title="用户名"></td>
					<%-- <td style="width: 100px; text-align: right;">姓名:</td>
					<td><input type="text" class="form-control input-sm"
						name="nickname" id="nickname" value="${pd.nickname}"
						placeholder="这里输入姓名" title="姓名">
					</td> --%>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;">姓名:</td>
					<td><input type="text" class="form-control input-sm"
						name="fullName" id="fullName" value="${pd.user.fullName}"
						placeholder="这里输入姓名" title="姓名">
					</td>
				</tr>
				<%-- <tr>
					<td style="width: 100px; text-align: right;">性别:</td>
					<td style="text-align: left;"><select id="gender" name="gender" class="form-control select2">
							<option value=女 <c:if test="${pd.gender == '女'}">selected</c:if>>女</option>
							<option value=男 <c:if test="${pd.gender == '男'}">selected</c:if>>男</option>
					</select></td>
					<td style="width: 100px; text-align: right;">年龄:</td>
					<td><input type="text" class="form-control input-sm"
						name="age" id="age" value="${pd.age}" placeholder="这里输入年龄"
						title="年龄"></td>
				</tr> --%>
				<%-- <tr>
					<td style="width: 100px; text-align: right;">手机号码:</td>
					<td><input type="text" class="form-control input-sm"
						name="phone" id="phone" value="${pd.phone}" placeholder="这里输入手机号码"
						title="手机号码"></td>
					<td style="width: 100px; text-align: right;">邮箱:</td>
					<td><input type="text" class="form-control input-sm"
						name="email" id="email" value="${pd.email}" placeholder="这里输入邮箱"
						title="邮箱"></td>
				</tr> --%>
				<tr>
					<td style="width: 100px; text-align: right;">是否启用:</td>
					<td><select id="enable" name="enable"
						class="form-control select2">
							<option value=1 <c:if test="${pd.user.enable == true}">selected</c:if>>是</option>
							<option value=0 <c:if test="${pd.user.enable == false}">selected</c:if>>否</option>
					</select></td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;">备注:</td>
					<td><input type="text" multiple="multiple" class="form-control input-sm"
						name="comments" id="comments" value="${pd.user.comments}"
						placeholder="这里输入备注信息" title="备注">
					</td>
				</tr>
				<%-- <tr>
					<td style="width: 100px; text-align: right;">头象:</td>
					<td colspan="3">
						 <div style="width: 100%;height: 200px;border: 1px solid #D2D6DE;background: #FFFFFF">
						 	<c:if test="${not empty attachment}">
								<img id="imgId"  style="width: 200px;height: 200px;" src="${attachment.attachment_url}${attachment.attachment_name}">
							</c:if>
							<c:if test="${empty attachment}">
								<img id="imgId"  style="width: 200px;height: 200px;">
							</c:if>
						 </div>
						 <div style="margin-top: 10px;text-align: left;">
						 	<a class="btn btn-small btn-success btn-sm btn-flat" onclick="uploadWin();">上传</a>
							<a class="btn btn-small btn-danger btn-sm btn-flat" onclick="clearPicture();">清除</a>
						 </div>
					</td>
				</tr> --%>
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
			
		 //验证
        function validate(){
           
			var name = $("#name").val();
            var fullname = $("#fullName").val();
            var comments = $("#comments").val();
            //var enable=$("enable").val();
            if(name == "") return "用户名不能为空!";
            if(fullname == "") return "姓名不能为空!";
            //if(comments == null || role == "") return "用户角色不能为空!";
            return "OK";
        }
		
		//保存
		function save(){
			var flag = validate();
			debugger;
			var userName=$("#user.name").val();
			if(flag == "OK"){
				//提交表单	
				$.ajax({//查询用户名是否存在
						type: "POST",
						url: '${basePath}/user/existByName?userName='+$("#name").val()+'&tm='+new Date().getTime(),
						dataType:'text',
						cache: false,
						success: function(data){
							if(data != 0 && "${pd.flag}"!= "update"){
								Dialog.alert("用户名已经存在，不能重复!!!");
							}else{
								//$("#userForm").submit();
								submitUserForm();
							}
						},error : function(XMLHttpRequest, textStatus, errorThrown) {
					    	 Dialog.alert(getError());
					     }
					});
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
		//显示图片
		function showPicture(){
			var ids = $("#attachmentId").val();
			$.ajax({
				type:"POST",
				url:"${basePath}/attachment/list?DATA_IDS="+ids,
				dataType:"json",
				cache:"false",
				success:function(data){
					$.each(data,function(index,value){
					     $("#imgId").attr('src',value.attachment_url+value.attachment_name); 
					});
				},error:function(XMLHttpRequest, textStatus, errorThrown){
					Dialog.alert(getError());
				}
			});
		}
		
		//打开上传窗口
		//allowedFileExtensions:附件类型
		//maxFileCount:限制上传附件数量
		//theme:界面主题
		function uploadWin(){
			var diag = new Dialog();
			diag.Width = 770;
			diag.Height = 580;
			diag.Title = "文件上传";
			diag.URL = "${basePath}/attachment/goAttachment?allowedFileExtensions=['BMP', 'JPG','JPEG','PNG','GIF']&maxFileCount=1&theme='fa'";
			diag.CancelEvent = function(){ //关闭事件
				//如果有上传附件，ids返回上传成功后的附件ID，以逗号分隔。
				var ids = diag.innerFrame.contentWindow.document.getElementById('attachmentIds').value;
				$("#attachmentId").val(ids);
				diag.close();
				showPicture();//显示图片
			}
			diag.show(); 
		}
		
		//清除头象图片
		function clearPicture(){
			$("#attachmentId").val("");
			$("#imgId").attr('src',""); 
		}
	</script>
</body>
</html>