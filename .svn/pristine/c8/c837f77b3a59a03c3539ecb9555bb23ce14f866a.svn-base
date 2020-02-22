<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>设置中心经纬度</title>
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
<link rel="stylesheet" href="${basePath}/staticres/ol3source/ol.css">


    <style>
        .map {
            width: 100%;
            height: 575px;
        }
    </style>
</head>
<body>
<div id="map" class="map"></div>
<div style="background-color: #999;"><span>鼠标点击所在位置坐标：</span><span name="textValue" id='textValue'>${longitude},${latitude}</span></div>
<input type="hidden" id="latitude"  value="${latitude}"> 
<input type="hidden" id="longitude"  value="${longitude}">
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
	<script src="${basePath}/staticres/ol6source/ol.js"></script>
<script type="text/javascript">
	$(function() {
		hideLoading();//隐藏遮罩层
		 $('input').iCheck({
		      checkboxClass: 'icheckbox_square-blue',
		      radioClass: 'iradio_square-blue',
		      increaseArea: '20%' // optional
		 });
	});
	//获取经纬度的值
	var longitude = $('#longitude').val();
	var latitude = $('#latitude').val();

	//加载地图
    var map = new ol.Map({
        controls: ol.control.defaults().extend([
            new ol.control.FullScreen(),
            new ol.control.OverviewMap(),
            new ol.control.ScaleLine(),
            new ol.control.ZoomSlider(),
            new ol.control.ZoomToExtent()
        ]),
        //加载层级
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            })
        ],
        target: 'map',
        //加载视图
        view: new ol.View({
        	//设置中心坐标位置为贵阳
            center: ol.proj.transform(
                [longitude,latitude], 'EPSG:4326', 'EPSG:3857'),
                //缩放比例
            zoom: 10
        })
    });
    $("#map").click(function (e) {
        // alert('X ; '+ e.clientX  + 'Y: '+e.clientY);
        //获取经纬度
        var t = ol.proj.transform(map.getEventCoordinate(e), 'EPSG:3857', 'EPSG:4326');
        //将经纬度显示到页面
        document.getElementById('textValue').innerHTML = t;
    })
    //添加移动鼠标显示地图经纬度功能
    map.addControl(new ol.control.MousePosition({coordinateFormat: ol.coordinate.createStringXY(8),projection: 'EPSG:4326'}));
</script>
</body>
</html>