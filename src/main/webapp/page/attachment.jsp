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

<!-- 文件上传组件 -->
<link rel="stylesheet" href="${basePath}/staticres/plugins/bootstrap-fileinput/css/fileinput.min.css" >
<link rel="stylesheet" href="${basePath}/staticres/plugins/bootstrap-fileinput/themes/explorer/theme.css" > 
	<style type="text/css">
		.table-query td{
			padding:2px;
		} 
	</style>
</head>
<body style="text-align: center; overflow-x: hidden; overflow-y: none; padding: 20px;">
	<input id="input-ke-2" name="file" type="file" multiple class="file-loading">
	<input id="attachmentIds" type="hidden">
	
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
	<!-- 文件上传组件 -->
	<script type="text/javascript" src="${basePath}/staticres/plugins/bootstrap-fileinput/js/fileinput.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/bootstrap-fileinput/themes/explorer/theme.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/bootstrap-fileinput/js/locales/zh.js"></script>
	
	<script type="text/javascript">
	    var allowedFileExtensions = ${pd.allowedFileExtensions};//上传附件类型
		var maxFileCount = ${pd.maxFileCount};//最多上传多少个附件;
		var theme = ${pd.theme};//上传界面主题
		$(function () {
			$("#input-ke-2").fileinput({
				language: "zh",	//设置语言
				theme: theme,//主题样式  explorer fa gly
			    uploadUrl: "fileUploadBatch.do",//上传地址
			    maxFileCount: maxFileCount,//表示允许同时上传的最大文件数
			    //allowedFileExtensions : ['jpg', 'png','gif'],
			    allowedFileExtensions:allowedFileExtensions,//限制上传附件类型
			    overwriteInitial: false,//不覆盖已存在的附件
			    previewFileIcon: '<i class="fa fa-file"></i>',
			    initialPreviewAsData: true, // defaults markup  
			    uploadExtraData: {
			        img_key: "1000",
			        img_keywords: "happy, nature",
			    },
			    preferIconicPreview: true, // this will force thumbnails to display icons for following file extensions
			    previewFileExtSettings: { // configure the logic for determining icon file extensions
			        'doc': function(ext) {
			            return ext.match(/(doc|docx)$/i);
			        },
			        'xls': function(ext) {
			            return ext.match(/(xls|xlsx)$/i);
			        },
			        'ppt': function(ext) {
			            return ext.match(/(ppt|pptx)$/i);
			        },
			        'zip': function(ext) {
			            return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
			        },
			        'htm': function(ext) {
			            return ext.match(/(htm|html)$/i);
			        },
			        'txt': function(ext) {
			            return ext.match(/(txt|ini|csv|java|php|js|css)$/i);
			        },
			        'mov': function(ext) {
			            return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
			        },
			        'mp3': function(ext) {
			            return ext.match(/(mp3|wav)$/i);
			        },
			    }
			}).on("fileuploaded", function (event, data, previewId, index) {//上传成功
		        var id = data.response.attachmentId;
			    var ids = $("#attachmentIds").val();
			    if(ids == "" || ids== null){
			    	ids=ids+id;
			    }else{
			    	ids=ids+","+id;
			    }
		        $("#attachmentIds").val(ids);
		         //Dialog.alert("提示：上传成功!"); 
		        //Dialog.confirm("提示：上传成功!");
		       
		        //parent.upLoadSuccess();
		    });
		});
			
	</script>	
	
</body>
</html>

