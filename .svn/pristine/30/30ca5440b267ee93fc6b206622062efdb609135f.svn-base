<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>系统登录</title>
<!-- 自定义主题样式加载 -->
<link rel="stylesheet" href="../staticres/css/loginindex.css">
</head>
<body class="hold-transition login-page jl_loginindexbg">
	<div class="jl_loginindexbgimg">
		<div class="jl_login-box">
			<div class="jl_loginmain">
				<div style="background:;width:621px;height:45px;margin-top: 254px;margin-left:33px;float:left">
					<form  method="post" id="loginForm">
						<!-- 用户名 -->
						<div class="jl_userNameInput">
							<input id="loginUserName" name="userName" type="text" value="" placeholder="用户名" />
						</div>
						<!-- 密码 -->
						<div class="jl_userPwdInput">
							<input id="loginPassword" name="passWord" type="password" value="" placeholder="密码" class="jl_pwdinput"  />
						</div>
						<!-- 验证码 显示-->
						<div class="jl_verificationCodeshow">
							
						</div>
						<!-- 验证码 输入-->
						<div class="jl_verificationCode">
							<input id="loginValidCode" name="validCode" type="text" placeholder="验证码" class="jl_verificationCodeinput" style="line-height:23px"  />
						</div>
						<!-- 登录按钮 -->
						<div class="jl_btnloginContener" onclick="login()">
							<!-- <input id="loginValidCode" name="validCode" type="button" /> -->
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- jQuery 2.2.3 -->
	<script src="../staticres/js/jquery-2.2.3.min.js"></script>
	<script>
	$(function () {
		//获取项目名称
		$.ajax({
		    url:'<%=path%>/login/systemName',
		    type:'post',    
		    cache:false,
		    dataType:'text',
		    success:function(data) {
		    	document.title = data;
		     }
		});  
		/**如果是login.jsp 在顶层框架打开**/
		var strUrl=window.location.href;
		var arrUrl=strUrl.split("/");
		var strPage=arrUrl[arrUrl.length-1];
		if(strPage.indexOf("login.jsp;JSESSIONID")>=0){
			window.top.location.href="<%=path%>/admin/page/general/login.jsp";
		} 
		/**End**/
			
		$("body").keydown(function() {
            if (event.keyCode == "13") {//keyCode=13是回车键
            	login();
            }
        });
	});
	//更换验证码，此处要加时间戳，避免IE无法加载
    function changeCode() {
        $('#codeImg').attr("src", "<%=path%>/login/createImage?" + new Date().getTime());
    }
	
	//登录 异步提交
	function login(){
		//$("#loginForm").submit();
		var valiFlag = validate();
		if(valiFlag == "OK"){
			$.ajax({
				type: "get",
				url: '<%=path%>/login/dealLogin.do',
		    	data: $("#loginForm").serialize(),
				dataType:'json',
				success: function(data){
					var msg = data.msg;
					//返回信息处理，OK表示验证成功!
					if("success" == msg){
						//登陆成功跳转到首页
						window.location = '<%=path%>/index.do';
					}else{
						bootbox.alert(msg);
					}
				}
			});
		}else{
			bootbox.alert(valiFlag);
		}
	}
	
	//验证登录信息
	function validate(){
		var loginName = $("#loginUserName").val();
		var loginPassword = $("#loginPassword").val();
		var loginValidCode = $("#loginValidCode").val();
		if (loginName== null || loginName== undefined || loginName== '')
			{
				$("#loginUserName").focus();	
				return "用户名不能为空！";
			}
		if (loginPassword== null || loginPassword== undefined || loginPassword== '')
			{
				$("#loginPassword").focus();	
				return "密码不能为空！";
			}
		//if (loginValidCode== null || loginValidCode== undefined || loginValidCode== '') return "验证码不能为空！";
		return "OK";
	}
</script>
</body>
</html>