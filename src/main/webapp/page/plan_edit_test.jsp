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
<title>设置平面图比列</title>
<title>	${sysname}</title>
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="${basePath}/staticres/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${basePath}/staticres/ol3source/ol.css">
	<link rel="stylesheet" href="${basePath}/staticres/plugins/ol3-measuretool-master/measuretool.css" /> 
	<link rel="stylesheet" href="${basePath}/staticres/plugins/ol-ext/controlbar.css" />  
	<!-- 加载遮罩层插件 -->
	<link rel="stylesheet" href="${basePath}/staticres/plugins/showLoading/css/showLoading.css" />  
	<style type="text/css">
	.map {
		  width: 100%;
		  height:200px;;
		  /* position:absolute; */
		  left:0px;
		}
	</style>
</head>
<body style="overflow-x: hidden; overflow-y: hidden;">
	<div id="map" class="map"></div>
	<div style="width:200px;height:200px;background: red"><img id="test" height="100" width="100" src="" />
		<input type="button" value="测试" onclick="loadPlanMapTest()">
	</div>
	
	<!-- jQuery 2.2.3 -->
	<script src="${basePath}/staticres/js/jquery-2.2.3.min.js"></script>
	<script src="${basePath}/staticres/ol3source/ol.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol3-measuretool-master/measuretool.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/controlbar.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/style/settextpathstyle.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/ol3source/layer/geoimagesource.js"></script>
	<!-- 公共js文件 --> 
	<script src="${basePath}/staticres/js/commjs.js"></script>

	<!--引入弹窗组件start-->
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDialog.js"></script>
	
	<script type="text/javascript">		
		//hideLoading();//隐藏遮罩层
		//定义一个视图
		var view = new ol.View({
	          center: ol.proj.fromLonLat([106.64598602, 26.64227590]),
	          zoom: 22.8
	        });
		//定义地图变量
		var map = new ol.Map({
					interactions: ol.interaction.defaults().extend([]),
			        controls: ol.control.defaults().extend([]),
			        target: 'map',
			        layers: [
			          new ol.layer.Tile({
							title: "基础地图",
							baseLayer: true,
							source: new ol.source.OSM(),
							visible: true
			          })],
			        view: view
		      });
		map.addControl(new ol.control.MousePosition({coordinateFormat: ol.coordinate.createStringXY(8),projection: 'EPSG:4326'}));
	    //map.addControl(new ol.control.CanvasScaleLine());		// CanvasScaleLine control
	    //定义一个坐标轴
	    var vectorAxis = new ol.layer.Vector({
				title: "坐标轴",  
				source: new ol.source.Vector({
	        			url: '${basePath}/map/json_coordinate_axis.do',
	        			format: new ol.format.GeoJSON()
	      			}),
	      		style: axisStyleFunction
	    	});
	    //坐标轴的样式
	    vectorAxis.setTextPathStyle(function (f)
	    		{	return [ new ol.style.Style(
	    			{	text: new ol.style.TextPath(
	    				{	text: f.get("name"),
	    					font: "bold 15px Arial",
	    					fill: new ol.style.Fill ({ color:"#FF0000" }),
	    					stroke: new ol.style.Stroke({ color:"#FFFFFF", width:6 }),
	    					textBaseline: "middle",
	    					textAlign: "center",
	    					rotateWithView: false,
	    					textOverflow: "visible",
	    					minWidth: 2
	    				})
	    			})]
	    		}, 
	    		3);
	    function axisStyleFunction(feature, resolution) {
			return [ new ol.style.Style({
				stroke : new ol.style.Stroke({
					color : 'green',
					width : 4
				})
			}) ];
		}
	    //加载自定义按钮
	    function loadCustomButton()
	    {
	    	var mainbar = new ol.control.Bar();
	    	mainbar.setPosition("top");
	    	map.addControl(mainbar);
	    	var editbar = new ol.control.Bar({
				toggleOne : true, // one control active at the same time
				group : false
			});
	    	mainbar.addControl(editbar);
	    	//保存按钮
			var save = new ol.control.Button({
				//html : '<i class="fa fa-save"></i>',
				html : '保存',
				title : "保存",
				handleClick : function(e) {
					saveGeoJson()
				}
			});
			mainbar.addControl(save);
			$("button[title='保存']").css('width', '47px');
			var btnModifyLayoutPoints = new ol.control.Button({
				html : '修改大小和位置',
				title : "修改大小和位置",
				handleClick : function(e) {
					modifyLayoutPointsSizeClick();
				}
			});
			editbar.addControl(btnModifyLayoutPoints);
			$("button[title='修改大小和位置']").css('width', '135px');
			
			var btnModifyLayoutPointsRotation = new ol.control.Button({
				html : '旋转',
				title : "旋转布局图",
				handleClick : function(e) {
					modifyLayoutPointsRotationClick();
				}
			});
			editbar.addControl(btnModifyLayoutPointsRotation);
			$("button[title='旋转布局图']").css('width', '40px');
			
			var btnModifyLayoutPointsRotation = new ol.control.Button({
				html : '取消',
				title : "取消修改",
				handleClick : function(e) {
					reload();//重新加载楼层图
				}
			});
			editbar.addControl(btnModifyLayoutPointsRotation);
			$("button[title='取消修改']").css('width', '40px');	
	    }
	    //加载平面图
	    var geoplanimg;
	    function loadPlanMap()
	    {
	    	$.ajax({
				type: "post",
				url: '${basePath}/floorPlan/getPlanMapJson.do?planMapId=${pd.planMapId}',
				dataType:'json',
				async:false,
				success: function(data){
					debugger;
					//先设置地图中心与平面图的一致
					if(data.bp.centerX && data.bp.centerY ){
						view.setCenter(ol.proj.fromLonLat([data.bp.centerX,data.bp.centerY]));
					}
					var myimage=new Image();
					myimage.src="data:image/png;base64,"+data.userfile;
					geoimg = new ol.layer.Image(
									{
										name : "楼层平面图",
										title : "楼层平面图",
										opacity : 1,
										source : new ol.source.GeoImage(
												{
													image :myimage,
													imageCenter : ol.proj
															.fromLonLat([
																	data.bp.centerX,
																	data.bp.centerY]),
													imageScale : [
															data.bp.scaleX,
															data.bp.scaleY ],
													imageRotate : data.bp.rotate
															* Math.PI / 180,
													projection : 'EPSG：4326',
													attributions : [ new ol.Attribution(
															{
																html : "<a href='http://www.jlrtls.com/'>&copy; 旌霖科技</a>"
															}) ]
												})
									});
							//map.addLayer(geoimg);
						}
				});
	    	}
	    function loadPlanMapTest()
	    {
	    	$.ajax({
				type: "post",
				url: '${basePath}/floorPlan/getPlanMapJson.do?planMapId=${pd.planMapId}',
				dataType:'json',
				async:false,
				success: function(data){
					debugger;
					//$("#test").src=data.userfile;
					$("#test").attr("src","data:image/png;base64,"+data.userfile);
					console.info("src属性："+$("#test").attr("src"));
				}
	    	});
	    }
	    //loadPlanMapTest();
	    //loadPlanMap();
	</script>
</body>
</html>