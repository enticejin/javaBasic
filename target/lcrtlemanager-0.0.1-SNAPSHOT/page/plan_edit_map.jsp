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
	<link rel="stylesheet" href="${basePath}/staticres/plugins/ol-ext/control/controlbar.css" />  
	<link rel="stylesheet" href="${basePath}/staticres/plugins/ol-ext/control/layerswitchercontrol.css" />
	<!-- 加载遮罩层插件 -->
	<link rel="stylesheet" href="${basePath}/staticres/plugins/showLoading/css/showLoading.css" />  
	<style type="text/css">
	.map {
		  width: 100%;
		  height:100%;
		  position:absolute;
		  left:0px;
		}
	</style>
</head>
<body style="overflow-x: hidden; overflow-y: hidden;">
	<div id="map" class="map"></div>
	<!-- jQuery 2.2.3 -->
	<script src="${basePath}/staticres/js/jquery-2.2.3.min.js"></script>
	<script src="${basePath}/staticres/ol3source/ol.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/ol3source/layer/geoimagesource.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol3-measuretool-master/measuretool.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/control/controlbar.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/style/settextpathstyle.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/control/canvasscalelinecontrol.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/control/buttoncontrol.js"></script>
	<!-- 公共js文件 --> 
	<script src="${basePath}/staticres/js/commjs.js"></script>

	<!--引入弹窗组件start-->
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDialog.js"></script>
	
	<script type="text/javascript">		
		hideLoading();//隐藏遮罩层
		var wgs84Sphere = new ol.Sphere(6378137);
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
	    map.addControl(new ol.control.CanvasScaleLine());		// CanvasScaleLine control
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
			mainbar.addControl(save);//添加保存按钮
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
					//先设置地图中心与平面图的一致
					if(data.bp.centerX && data.bp.centerY ){
						view.setCenter(ol.proj.fromLonLat([data.bp.centerX,data.bp.centerY]));
					}
					var myimage=new Image();
					myimage.src=data.userfile;
					geoplanimg = new ol.layer.Image(
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
						}
				});
	    	}
	   
	    var modifySize=true;
		// 修改大小
	    function modifyLayoutPointsSizeClick() {
	    	modifySize=true;	// 处于修改大小模式
	    	getCurrentLayer();
	    	createImageRefPointsFeatures();
	    	f_modifyLayoutPoints();
	    }
		
		
	    //== 获取坐标原点
		var coordinateOrigin = null;
		var currentLayer = null;
		var currentLayerOnChangeListener = null;
	 	// 找到选中的层作为当前层 currentLayer
		function getCurrentLayer() {
			if (currentLayerOnChangeListener != null) {
				ol.Observable.unByKey(currentLayerOnChangeListener);
			}
			currentLayer = null;
			currentLayer = geoplanimg;	// 找到选中的层了
			currentLayerOnChangeListener = currentLayer.getSource().on('change', function(event) {
			 	createImageRefPointsFeatures(event);
			});
			return currentLayer;
		}
	 	
		var sourceLayoutPoints = new ol.source.Vector();
		// 根据图片, 生成 sourceLayoutPoints 的 Features
		function createImageRefPointsFeatures(event) {
			if (currentLayer == null)
				return;
			sourceLayoutPoints.clear();
			var sourceCurrentLayer = currentLayer.getSource();
			var image = sourceCurrentLayer.getImage();
			var center = sourceCurrentLayer.getCenter();
			
			var feature_center = new ol.Feature({
	        		geometry: new ol.geom.Point(center),
	        		id: "image_CENTER",
					name: "图像中心点"
			});
			sourceLayoutPoints.addFeature(feature_center);	// 中心点

			var pixelRatio = 1;
			var resolution = view.getResolution();
			var scale = sourceCurrentLayer.getScale();
			var imageSize = sourceCurrentLayer.imageSize;
			var w = imageSize[0]*scale[0] /* /resolution *pixelRatio*/;
			var h = imageSize[1]*scale[1] /* /resolution *pixelRatio*/;
			var r = - sourceCurrentLayer.getRotation();		// 这是顺时针旋转, 我们改为逆时针旋转, 好计算

			var sinr = Math.sin(r);
			var cosr = Math.cos(r);
			sourceLayoutPoints.addFeature(new ol.Feature({id: "image_LU", name: "图像左上", geometry: new ol.geom.Point([ (-w/2.0)*cosr- (+h/2.0)*sinr+center[0], (-w/2.0)*sinr+(+h/2.0)*cosr+center[1] ])/*, coordinate:[ (-w/2.0)*cosr-(+h/2.0)*sinr+center[0], (-w/2.0)*sinr+(+h/2.0)*cosr+center[1] ]*/}));	// 左上
			sourceLayoutPoints.addFeature(new ol.Feature({id: "image_RU", name: "图像右上", geometry: new ol.geom.Point([ (+w/2.0)*cosr- (+h/2.0)*sinr+center[0], (+w/2.0)*sinr+(+h/2.0)*cosr+center[1] ])/*, coordinate:[ (+w/2.0)*cosr-(+h/2.0)*sinr+center[0], (+w/2.0)*sinr+(+h/2.0)*cosr+center[1] ]*/}));	// 右上
			sourceLayoutPoints.addFeature(new ol.Feature({id: "image_LD", name: "图像左下", geometry: new ol.geom.Point([ (-w/2.0)*cosr- (-h/2.0)*sinr+center[0], (-w/2.0)*sinr+(-h/2.0)*cosr+center[1] ])/*, coordinate:[ (-w/2.0)*cosr-(-h/2.0)*sinr+center[0], (-w/2.0)*sinr+(-h/2.0)*cosr+center[1] ]*/}));	// 左下
			sourceLayoutPoints.addFeature(new ol.Feature({id: "image_RD", name: "图像右下", geometry: new ol.geom.Point([ (+w/2.0)*cosr- (-h/2.0)*sinr+center[0], (+w/2.0)*sinr+(-h/2.0)*cosr+center[1] ])/*, coordinate:[ (+w/2.0)*cosr-(-h/2.0)*sinr+center[0], (+w/2.0)*sinr+(-h/2.0)*cosr+center[1] ]*/}));	// 右下
			sourceLayoutPoints.addFeature(new ol.Feature({id: "image_UP", name: "图像上",  geometry: new ol.geom.Point([                -(+h/2.0)*sinr+center[0],             +(+h/2.0)*cosr+center[1] ])/*, coordinate:[              -(+h/2.0)*sinr+center[0],              +(+h/2.0)*cosr+center[1] ]*/}));	// 上
			sourceLayoutPoints.addFeature(new ol.Feature({id: "image_DN", name: "图像下",  geometry: new ol.geom.Point([                -(-h/2.0)*sinr+center[0],             +(-h/2.0)*cosr+center[1] ])/*, coordinate:[              -(-h/2.0)*sinr+center[0],              +(-h/2.0)*cosr+center[1] ]*/}));	// 下
			sourceLayoutPoints.addFeature(new ol.Feature({id: "image_LT", name: "图像左",  geometry: new ol.geom.Point([ (-w/2.0)*cosr               +center[0], (-w/2.0)*sinr             +center[1] ])/*, coordinate:[ (-w/2.0)*cosr              +center[0], (-w/2.0)*sinr              +center[1] ]*/}));	// 左
			sourceLayoutPoints.addFeature(new ol.Feature({id: "image_RT", name: "图像右",  geometry: new ol.geom.Point([ (+w/2.0)*cosr               +center[0], (+w/2.0)*sinr             +center[1] ])/*, coordinate:[ (+w/2.0)*cosr              +center[0], (+w/2.0)*sinr              +center[1] ]*/}));	// 右

			var features = sourceLayoutPoints.getFeatures();
	    	for (var i=0; i<features.length; i++) {
	    		features[i].on('change', LayoutPointChanged);
	    		features[i].set('coordinate', features[i].getGeometry().getCoordinates());
	    	}
	    	
		}
		
		function LayoutPointChanged(event) {
			if (currentLayer == null)
				return;
			var sourceCurrentLayer = currentLayer.getSource();
			if (event.target.get("id") == "image_CENTER") {	// 移动的是中间点，要移动图像位置
				sourceCurrentLayer.setCenter(event.target.getGeometry().getCoordinates());
			}
			else {
				var scale = sourceCurrentLayer.getScale();
				var imageSize = sourceCurrentLayer.imageSize;
				var w = imageSize[0]*scale[0];		// 缩放后的宽
				var h = imageSize[1]*scale[1];
				var px = event.target.getGeometry().getCoordinates()[0];
				var py = event.target.getGeometry().getCoordinates()[1];
				var oldpx = event.target.get("coordinate")[0];
				var oldpy = event.target.get("coordinate")[1];
				var cx = sourceCurrentLayer.getCenter()[0];
				var cy = sourceCurrentLayer.getCenter()[1];

				var r = sourceCurrentLayer.getRotation();		// 图像已经顺时针旋转某个角度, 我们逆时针旋转选中点
				var sinr = Math.sin(r);
				var cosr = Math.cos(r);
				var dx = px-cx;
				var dy = py-cy;
				var olddx = oldpx-cx;
				var olddy = oldpy-cy;
				
				if (modifySize) {
					//== 修改大小模式
					px = (dx)*cosr - (dy)*sinr + cx;
					py = (dx)*sinr + (dy)*cosr + cy;
				
					if ((event.target.get("id") == "image_LU") || 
							(event.target.get("id") == "image_RU") ||
							(event.target.get("id") == "image_LD") ||
							(event.target.get("id") == "image_RD") ) {
								
							w = Math.abs(px-cx)*2;
							h = Math.abs(py-cy)*2;
						}
						else if ((event.target.get("id") == "image_LT") || (event.target.get("id") == "image_RT")) {
							w = Math.abs(px-cx)*2;
						} 
						else if ((event.target.get("id") == "image_UP") || (event.target.get("id") == "image_DN")) {
							h = Math.abs(py-cy)*2;
						} 
						scale[0] = w / imageSize[0];
						scale[1] = h / imageSize[1];
						sourceCurrentLayer.setScale(scale);
				}
				else {
					//== 修改旋转模式
					rr = Math.atan2(dy, dx);
					old_rr = Math.atan2(olddy, olddx);
					r = sourceCurrentLayer.get("old_angle");
					if (isNaN(r)) {
						r = 0;
					}
					sourceCurrentLayer.setRotation(-rr + old_rr + r);
				}
			}
			createImageRefPointsFeatures();

	    }
		
		function layoutPointStyleFunction(feature, resolution) {
			return new ol.style.Style({
							image: new ol.style.RegularShape({
									fill: new ol.style.Fill({color: 'blue'}),
									stroke: new ol.style.Stroke({color: 'blue', width: 3}),
									points: 4,
									radius: 20,
									radius2: 0,
									angle: 0
								}),						
							text: createLayoutPointTextStyle(feature, resolution)  
	      });
	    }
		
		var vectorLayoutPointsLayer = new ol.layer.Vector({
			title: "",    		
	    	source: sourceLayoutPoints,
	    	style: layoutPointStyleFunction
		});
		
		// 定义坐标的选择器和修改器
	    var selectLayoutPoints = new ol.interaction.Select({
				style: selectLayoutPointsStyleFunction,
	    		filter: function(feature, layer){
	    			return (layer === vectorLayoutPointsLayer);
	        	}
	    	});
	    selectLayoutPoints.setActive(false);
	    
	    var selectedLayoutPoints = selectLayoutPoints.getFeatures();		// 这是选中的 features
	    var modifyLayoutPoints = new ol.interaction.Modify({
			style: modifyLayoutPointsStyleFunction,
			features: selectedLayoutPoints
	    });
	    modifyLayoutPoints.setActive(false);							// modify 平时不激活

	    modifyLayoutPoints.on('modifystart', function(e) {
			if (currentLayer == null)
				return;
	    	currentLayer.getSource().set("old_angle", currentLayer.getSource().getRotation());
	    });
	    
	    modifyLayoutPoints.on('modifyend', function(e) {
	    	e.features.clear();
		});
		
		function restoreInteractionDefault() {
	        selectLayoutPoints.setActive(false);
	        modifyLayoutPoints.setActive(false);
	        map.removeInteraction(selectLayoutPoints);
	        map.removeInteraction(modifyLayoutPoints);
	    }
	 	
		// 设置为修改坐标模式, 在函数中调用
	    function f_modifyLayoutPoints() {
	    	restoreInteractionDefault();
	        map.addInteraction(selectLayoutPoints);
	        map.addInteraction(modifyLayoutPoints);
	        selectLayoutPoints.setActive(true);
	        modifyLayoutPoints.setActive(true);
		}
		
	    function selectLayoutPointsStyleFunction(feature, resolution) {
			return new ol.style.Style({
							image: new ol.style.RegularShape({
									fill: new ol.style.Fill({color: 'red'}),
									stroke: new ol.style.Stroke({color: 'red', width: 3}),
									points: 4,
									radius: 20,
									radius2: 0,
									angle: 0
								}),						
							text: createLayoutPointTextStyle(feature, resolution)  
	      });
	    }

		function modifyLayoutPointsStyleFunction(feature, resolution) {
			return new ol.style.Style({
							image: new ol.style.RegularShape({
									fill: new ol.style.Fill({color: 'green'}),
									stroke: new ol.style.Stroke({color: 'green', width: 3}),
									points: 4,
									radius: 20,
									radius2: 0,
									angle: 0
								}),						
							text: createLayoutPointTextStyle(feature, resolution)  
	      });
	    }
		
		var createLayoutPointTextStyle = function(feature, resolution) {
	        var align = "center";
	        var baseline = "middle";
	        var size = "14px";
	        var offsetX = -40;
	        var offsetY = 20;
	        var weight = "bold";		// normal
	        var rotation = 0;
	        var font = weight + ' ' + size + ' ' + 'Arial';
	        var fillColor = 'rgba(0, 0, 255, 0.5)';	//"#0000FF";
	        var outlineColor = "#FFFFFF";
	        var outlineWidth = 6;

	        return new ol.style.Text({
	          textAlign: align,
	          textBaseline: baseline,
	          font: font,
	          text: getLayoutPointText(feature, resolution),
	          fill: new ol.style.Fill({color: fillColor}),
	          stroke: new ol.style.Stroke({color: outlineColor, width: outlineWidth}),
	          offsetX: offsetX,
	          offsetY: offsetY,
	          rotation: rotation
	        });
	      };
	      
	   // 平面图矢量点的 Style
	  	var getLayoutPointText = function(feature, resolution) {
	  		var text = feature.get('name');
	  		if (typeof(text) == "undefined")
	  			text = "";
	  		if (/*(showAnchorCoordinate) &&*/ (vectorAxis != null)) {
	  			var originFeature = vectorAxis.getSource().getFeatureById('coordinate_origin');
	  			if (originFeature != null) {
	  				var originCoordinate = originFeature.getGeometry().getCoordinates();
	  				var sourceProj = view.getProjection();
	  				var c1 = ol.proj.transform(originFeature.getGeometry().getCoordinates(), sourceProj, 'EPSG:4326');
	  				var c2 = ol.proj.transform(feature.getGeometry().getCoordinates(), sourceProj, 'EPSG:4326');
	  				var c2x = [c2[0], c1[1]];
	  				var c2y = [c1[0], c2[1]];
	  				var x = wgs84Sphere.haversineDistance(c1, c2x);
	  				var y = wgs84Sphere.haversineDistance(c1, c2y);
	  				if (c1[0]>c2[0]) {x=-x;}
	  				if (c1[1]>c2[1]) {y=-y;}
	  				text = text +'\r\n('+x.toFixed(4)+','+y.toFixed(4)+')';
	  			}
	  		}

	  		return text;
	  	};
	  	
	  	
	 // 旋转选中的底图
	    function modifyLayoutPointsRotationClick() {
	    	modifySize=false;	// 处于修改旋转模式
	    	getCurrentLayer();
	    	createImageRefPointsFeatures();
	    	f_modifyLayoutPoints();
	    }
	 
	   //重新加载楼层图
	   function reload(){
		   window.parent.addTab("fa-bars-plance-pic-map","fa fa-file-picture-o","平面图配置-地图","${basePath}/floorPlan/planMapIndex.do?planMapId=${pd.planMapId}");
		   
	   }
	   // 保存区域设置
	   function saveGeoJson() {
			showLoading();
			var center = ol.proj.transform(geoplanimg.getSource().getCenter(), view.getProjection(), 'EPSG:4326');
			var json = {"opacity":geoplanimg.getOpacity(),'center':[center[0],center[1]],'rotate':(geoplanimg.getSource().getRotation() / (Math.PI/180)),'scale':[geoplanimg.getSource().getScale()[0],geoplanimg.getSource().getScale()[1]]};
			$.ajax({
				type: "post",
				url: '${basePath}/floorPlan/save_plance_pic_by_json.do',
		    	data: {json:JSON.stringify(json),planMapId:'${pd.planMapId}'},
				dataType:'text',
				success: function(data){
					hideLoading();
					//返回信息处理，OK表示验证成功!
					if("OK" == data){
						
					}
				},error : function(XMLHttpRequest, textStatus, errorThrown) {
				    	 hideLoading();
				    	 Dialog.alert(getError());
				}
			});
		}
	   loadPlanMap();
	   loadCustomButton();
	  //添加图层
		if(geoplanimg){
			map.addLayer(geoplanimg);
		}
		map.addLayer(vectorAxis);
		map.addLayer(vectorLayoutPointsLayer);
		
		
		var MeasureTool = new ol.control.MeasureTool({
	        sphereradius : 6378137,//sphereradius
	      });
	     map.addControl(MeasureTool);
	</script>
</body>
</html>