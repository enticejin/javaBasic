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
<title>设置区域及基站信息</title>
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
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/control/layerswitchercontrol.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/control/togglecontrol.js"></script>
	
	<!-- 公共js文件 --> 
	<script src="${basePath}/staticres/js/commjs.js"></script>

	<!--引入弹窗组件start-->
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDialog.js"></script>
	
	<script type="text/javascript">		
	
		hideLoading();//隐藏遮罩层
		var targetAreaId = "";
		var showAnchorCoordinate = false;//是否显示基站的坐标
		var rtleOrigin = null;  
		var wgs84Sphere = new ol.Sphere(6378137);
	    // 图标的URL
	    var clockSourceIconURL = "../staticres/dist/img/clocksource48px.png";
	    var anchorIconURL = "../staticres/dist/img/anchor48px.png";
	    var tagIconURL = "../staticres/dist/img/tag48px.png";
	    
	    //== 自定义 logo, 显示在图层属性中
	    var logoElement = document.createElement('a');
	    logoElement.href = 'http://localhost:8888/rtls/oltest/examples';
	    logoElement.target = '_blank';
	    var logoImage = document.createElement('img');
	    logoImage.src = '../staticres/dist/img/logo.png';
	    logoElement.appendChild(logoImage);    
	    //Areas --区域
	    var getAreaText = function(feature, resolution) {
	    	if (feature.get('type') != 'cs') {
	        	var text = feature.get('name');
	        	return text;
	    	} 
	    	else
	    		return '';
	      };
	    var createAreasTextStyle = function(feature, resolution) {
	      var align = "center";
	      var baseline = "middle";
	      var size = "14px";
	      var offsetX = 0;
	      var offsetY = 0;
	      var weight = "bold";		// normal
	      var rotation = 0;
	      var font = weight + ' ' + size + ' ' + 'Arial';
	      var fillColor = 'rgba(0, 0, 255, 0.5)';	//"#A0A0FF";
	      var outlineColor = "#FFFFFF";
	      var outlineWidth = 6;

	      return new ol.style.Text({
	        textAlign: align,
	        textBaseline: baseline,
	        font: font,
	        text: getAreaText(feature, resolution),
	        fill: new ol.style.Fill({color: fillColor}),
	        stroke: new ol.style.Stroke({color: outlineColor, width: outlineWidth}),
	        offsetX: offsetX,
	        offsetY: offsetY,
	        rotation: rotation
	      });
	    };

		function areasStyleFunction(feature, resolution) {
			return new ol.style.Style({
				image: new ol.style.Icon({
	          				src: clockSourceIconURL,
	          				scale: map.getView().getZoom() / 22
	        			}),
	        	stroke: new ol.style.Stroke({
	                		color: 'blue',
	                		width: 3
	              		}),
	    		fill: new ol.style.Fill({
	                		color: 'rgba(0, 0, 255, 0.1)'
						}),      	
				text: createAreasTextStyle(feature, resolution)
	    	});
		}
		var vectorAreas = new ol.layer.Vector({
			title: "区域层",    		
	    	source: new ol.source.Vector({
	    			url: '${basePath}/area/json_area_byId.do?areaId=${areaId}',
	      			format: new ol.format.GeoJSON()
	    		}),
	    	style: areasStyleFunction
		});
		
		//一维
		var vectorAreasOne = new ol.layer.Vector(
				{
					title: "区域层",    		
			    	source: new ol.source.Vector({
			    			url: '../rtlsConfig/json_area_byId?areaId=${areaId}',
			      			format: new ol.format.GeoJSON()
			    		}),
			    	style: areasStyleFunction
				}
		);
		// Anchors
		var getAnchorText = function(feature, resolution) {
			var text = feature.get('name');//+' - '+feature.get('sn');
			if ((showAnchorCoordinate) && (vectorAxis != null)) {
				var originFeature = vectorAxis.getSource().getFeatureById('coordinate_origin');
				if (originFeature != null) {
					var sourceProj = map.getView().getProjection();
					var c1 = ol.proj.transform(originFeature.getGeometry().getCoordinates(), sourceProj, 'EPSG:4326');
					var c2 = ol.proj.transform(feature.getGeometry().getCoordinates(), sourceProj, 'EPSG:4326');
					var c2x = [c2[0], c1[1]];
					var c2y = [c1[0], c2[1]];
					var x = wgs84Sphere.haversineDistance(c1, c2x);
					var y = wgs84Sphere.haversineDistance(c1, c2y);
					if (c1[0]>c2[0]) {x=-x;}
					if (c1[1]>c2[1]) {y=-y;}
					var z = feature.get('z');
					text = text +'\r\n('+x.toFixed(4)+','+y.toFixed(4)+','+z.toFixed(4)+')';
				}
			}
			return text;
		};   
	    var createAnchorTextStyle = function(feature, resolution) {
	        var align = "center";
	        var baseline = "middle";
	        var size = "14px";
	        var offsetX = 0;
	        var offsetY = 0;
	        var weight = "normal";		// normal
	        var rotation = 0;
	        var font = weight + ' ' + size + ' ' + 'Arial';
	        var fillColor = 'rgba(0, 127, 0, 0.5)';	//"#0000FF";
	        var outlineColor = "#FFFFFF";
	        var outlineWidth = 6;

	        return new ol.style.Text({
	          textAlign: align,
	          textBaseline: baseline,
	          font: font,
	          text: getAnchorText(feature, resolution),
	          fill: new ol.style.Fill({color: fillColor}),
	          stroke: new ol.style.Stroke({color: outlineColor, width: outlineWidth}),
	          offsetX: offsetX,
	          offsetY: offsetY,
	          rotation: rotation
	        });
	      };

	    function anchorStyleFunction(feature, resolution) {
	      return new ol.style.Style({
			image: new ol.style.Icon({
	            src: anchorIconURL,
	            scale: map.getView().getZoom() / 20
	          }),
	        text: createAnchorTextStyle(feature, resolution)  
	      });
	    }

	    var vectorAnchors = new ol.layer.Vector({
					title: "基站层",    		
					source: new ol.source.Vector({
							url:'${basePath}/area/json_anchors_byAreaId.do?areaId=${areaId}',
							
	        				format: new ol.format.GeoJSON()
	      				}),
	      			style: anchorStyleFunction
	    });
	    	// 坐标轴
	      var getAxisText = function(feature, resolution) {
	          var text = feature.get('name');
	          return text;
	        };
	        
	      var createAxisTextStyle = function(feature, resolution) {
	        var align = "center";
	        var baseline = "middle";
	        var size = "14px";
	        var offsetX = 0;
	        var offsetY = 50;
	        var weight = "bold";		// normal
	        var rotation = 0;
	        var font = weight + ' ' + size + ' ' + 'Arial';
	        var fillColor = "#FF0000";
	        var outlineColor = "#FFFFFF";
	        var outlineWidth = 6;

	        return new ol.style.Text({
	          textAlign: align,
	          textBaseline: baseline,
	          font: font,
	          text: getAxisText(feature, resolution),
	          fill: new ol.style.Fill({color: fillColor}),
	          stroke: new ol.style.Stroke({color: outlineColor, width: outlineWidth}),
	          offsetX: offsetX,
	          offsetY: offsetY,
	          rotation: rotation
	        });
	      };
	      
	    function axisStyleFunction(feature, resolution) {
	      return [new ol.style.Style({
	        stroke: new ol.style.Stroke({
	          color: 'green',
	          width: 4
	        })
	      })];
	    }
	    
		//坐标轴的数据
	    var vectorAxis = new ol.layer.Vector({
				title: "坐标轴",  
				source: new ol.source.Vector({
					   url:'${basePath}/map/json_coordinate_axis.do',
	        			format: new ol.format.GeoJSON()
	      			}),
	      		style: axisStyleFunction
	    	});

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

	    
	 	// Tags track
	    var tagTrackStyle = new ol.style.Style({
	        image: new ol.style.Circle({
	        radius: 5,
	        snapToPixel: false,
	        fill: new ol.style.Fill({color: 'yellow'}),
	        stroke: new ol.style.Stroke({color: 'red', width: 1})
	      }),
	      stroke: new ol.style.Stroke({
	          color: 'green',
	          width: 2
	        })
	    });
	    
	    // 创建 interaction 对象 select 和 modify
	    // ===== 区域选择和修改器 =====
	    // 选中的区域的 Style
		var getSelectedAreaText = function(feature, resolution) {
			var text = feature.get('name');
			var originFeature = vectorAxis.getSource().getFeatureById('coordinate_origin');
			if (originFeature != null && feature.get('type')=='cs') {
				var sourceProj = map.getView().getProjection();
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
			return text;
		};
	    
		var createSelectedClockSourceTextStyle = function(feature, resolution) {
			var align = "center";
			var baseline = "middle";
			var size = "14px";
			var offsetX = 0;
			var offsetY = 0;
			var weight = "bold";		// normal
			var rotation = 0;
			var font = weight + ' ' + size + ' ' + 'Arial';
			var fillColor = 'rgba(255, 0, 0, 0.5)';	//"#A0A0FF";
			var outlineColor = "#FFFFFF";
			var outlineWidth = 6;

			return new ol.style.Text({
	    		textAlign: align,
	    		textBaseline: baseline,
	    		font: font,
	    		text: getSelectedAreaText(feature, resolution),
	    		fill: new ol.style.Fill({color: fillColor}),
	    		stroke: new ol.style.Stroke({color: outlineColor, width: outlineWidth}),
	    		offsetX: offsetX,
	    		offsetY: offsetY,
	    		rotation: rotation
	    	});
		};

		function selectedAreaStyleFunction(feature, resolution) {
	    	return new ol.style.Style({
				image: new ol.style.Icon({
	          		src: clockSourceIconURL,
	          		scale: map.getView().getZoom() / 22
	        		}),
	        	stroke: new ol.style.Stroke({
	                	color: 'red',
	                	width: 2
	              		}),
	    		fill: new ol.style.Fill({
	                	color: 'rgba(255, 0, 0, 0.1)'
	              		}),      	
	      		text: createSelectedClockSourceTextStyle(feature, resolution)
	    	});
		}
	    
		//== 区域选择器
	    var selectArea = new ol.interaction.Select({
				style: selectedAreaStyleFunction,
	    		filter: function(feature, layer){
	            	return layer === vectorAreas;
	        	}
	    	});
	    selectArea.setActive(false);							// select 平时不激活
	    var selectedArea = selectArea.getFeatures();				// 这是选中的 features
	    var modifyArea = new ol.interaction.Modify({
	      features: selectedArea
	    });
	    modifyArea.setActive(false);							// modify 平时不激活
	    modifyArea.on('modifyend', function(e) {
	    	var features = selectArea.getFeatures();
	    	var feature = features.getArray()[0];
	    	var areaName = feature.getProperties().name;
	    	var geometry = feature.getGeometry();
	    	var coordinates = geometry.getCoordinates()[0];
	    	var str='';
	    	for (var i=0; i<coordinates.length; i++) {
	    		str = str + 'dot'+i+': ('+coordinates[i][0]+','+coordinates[i][1]+') ';
	    	}
	      });

	    // =========== Anchor 选择和修改器 ============
		var createSelectedAnchorTextStyle = function(feature, resolution) {
	        var align = "center";
	        var baseline = "middle";
	        var size = "14px";
	        var offsetX = 0;
	        var offsetY = 0;
	        var weight = "bold";		// normal
	        var rotation = 0;
	        var font = weight + ' ' + size + ' ' + 'Arial';
	        var fillColor = 'rgba(255, 0, 5, 1)';	//"#0000FF";
	        var outlineColor = "#FFFFFF";
	        var outlineWidth = 6;

	        return new ol.style.Text({
	          textAlign: align,
	          textBaseline: baseline,
	          font: font,
	          text: getAnchorText(feature, resolution),
	          fill: new ol.style.Fill({color: fillColor}),
	          stroke: new ol.style.Stroke({color: outlineColor, width: outlineWidth}),
	          offsetX: offsetX,
	          offsetY: offsetY,
	          rotation: rotation
			});
		};

	    function selectedAnchorStyleFunction(feature, resolution) {
	      return new ol.style.Style({
			image: new ol.style.Icon({
	            src: anchorIconURL,
	            scale: map.getView().getZoom() / 20
	          }),
	        text: createSelectedAnchorTextStyle(feature, resolution)  
	      });
	    }
	    	
	    var selectAnchor = new ol.interaction.Select({
	    		style: selectedAnchorStyleFunction,
	    		filter: function(feature, layer){
	            	return layer === vectorAnchors;
	        	}
	    	});
	    selectAnchor.setActive(false);							// select 平时不激活
	    var selectedAnchor = selectAnchor.getFeatures();				// 这是选中的 features
	    var modifyAnchor = new ol.interaction.Modify({
	      features: selectedAnchor
	    });
	    modifyAnchor.setActive(false);							// modify 平时不激活
	    modifyAnchor.on('modifyend', function(e) {
	    	var features = selectAnchor.getFeatures();
	    	var feature = features.getArray()[0];
	    	var areaName = feature.getProperties().name;
	    	var geometry = feature.getGeometry();
	    	var coordinates = geometry.getCoordinates()[0];
	    	var str='';
	    	for (var i=0; i<coordinates.length; i++) {
	    		str = str + 'dot'+i+': ('+coordinates[i][0]+','+coordinates[i][1]+') ';
	    	}
	      });
	    
	    //定义视图
	 	var view = new ol.View({
	          center: ol.proj.fromLonLat([106.64598602, 26.64227590]),
	          zoom: 22.8
	        });
	    var map = new ol.Map({
	    	interactions: ol.interaction.defaults().extend([selectArea, modifyArea, selectAnchor, modifyAnchor]),	// 使用 select 和 modify
	        controls: ol.control.defaults().extend([]),
	        target: 'map',
	        layers: [
	          new ol.layer.Tile({
					title: "OpenStreetMap 基础地图",
					baseLayer: true,
					source: new ol.source.OSM(),
					visible: true
	          })],
	        view: view,
	        logo: logoElement        
	      });
		var vectorFloor;
		//区域属于楼层，楼层具有楼层平面图
		function loadPlanMap(map)
		{
			$.ajax({
				type: "get",
				/* url: '${basePath}/map/getPlanMapJson.do?areaId=${pd.areaId}', */
				url: '${basePath}/map/getTestPlanMapJson.do?areaId=${pd.areaId}',		
				dataType:'json',
				async:false,
				success: function(data){
					if(data.length==0)
					{
						Dialog.alert("该区域并未设置所属的楼层，无法加载平面图");
						return;
					}
					for(var i=0;i<data.length;i++)
					{
						if(data[i].bp.centerX && data[i].bp.centerY ){
							view.setCenter(ol.proj.fromLonLat([data[i].bp.centerX,data[i].bp.centerY]));
						}
						
						var planMapImg=new Image();
						planMapImg.src=data[i].ufstring;
						var palnMapLayer=new ol.layer.Image({
							name : "楼层平面图",
							title : "楼层平面图",
							opacity : 1,
							source : new ol.source.GeoImage({
								image :planMapImg,
								imageCenter : ol.proj
										.fromLonLat([
											data[i].bp.centerX,
											data[i].bp.centerY]),
								imageScale : [
									data[i].bp.scaleX,
									data[i].bp.scaleY ],
								imageRotate : data[i].bp.rotate
										* Math.PI / 180,
								projection : 'EPSG：4326',
								attributions : [ new ol.Attribution(
										{
											html : "<a href='http://www.jlrtls.com/'>&copy; 旌霖科技</a>"
										}) ]
							})
						})	
						map.addLayer(palnMapLayer);//添加到图层中
					}
				 }
			});
		}

		
	    //== 加控件 ==
		map.addControl(new ol.control.CanvasScaleLine());		// CanvasScaleLine control
		map.addControl(new ol.control.ZoomSlider({  }));		// ZoomSlider
		map.addControl(new ol.control.Rotate({autoHide:true}));	// Rotate
		map.addControl(new ol.control.MousePosition({coordinateFormat: ol.coordinate.createStringXY(8),projection: 'EPSG:4326'}));
		loadPlanMap(map);
		//== 加层 ==
		map.addLayer(vectorAxis);		// 坐标轴	
		map.addLayer(vectorAreas);		// Areas Layer
		map.addLayer(vectorAnchors);	// Anchors Layer 
		
		
		
	    //=======================
	    // 层切换按钮
	    //=======================
		// Add a layer switcher outside the map
		var switcher = new ol.control.LayerSwitcher({target:$(".layerSwitcher").get(0), 
				show_progress:false,
				extent: false,
				trash: false
			});
		
		map.addControl(switcher);
	  	//== 按钮 ======
		// Main control bar, 全部的按钮都在 mainbar 上面
		var mainbar = new ol.control.Bar();
		mainbar.setPosition("top");	// "top", "top-left", "left", "bottom-left", "bottom", "bottom-right", "right", "top-right"
		map.addControl(mainbar);

		// Edit control bar, 编辑相关的按钮分成一类放在 editbar 中, editbar 放在 mainbar 中
		// editbar 中的按钮同一时刻只能激活一个
		var editbar = new ol.control.Bar({	toggleOne: true,	// one control active at the same time
					group:false			// group controls together
				});
		mainbar.addControl(editbar);

		// editbar 加一个编辑区域的按钮
		var btnAreaEdit = new ol.control.Toggle({	
					/* html: '<i class="fa fa-map-o" ></i>', */
					html: '编辑',
					title: '编辑区域',
					id: '100px',
					onToggle: function(e) {
			        	selectArea.getFeatures().clear();
			        	selectAnchor.getFeatures().clear();
			        	selectAnchor.setActive(! e);
				        modifyAnchor.setActive(! e);
				        selectArea.setActive(e);
				        modifyArea.setActive(e);
				        showAnchorCoordinate = false;
				        vectorAnchors.changed();
				        /* try {
				        	window.parent.showPolyon("the info from map, e="+e);
				        }
				        catch(ee) {
				        	alert("call ShowPolyon() ERR: "+ee);
				        } */
					}
				});
		
		if('${type}' == "area"){
			editbar.addControl(btnAreaEdit);
		}
		// editbar 加一个编辑 Anchor 的按钮
		var btnAnchorEdit = new ol.control.Toggle({	
			/* html: '<i class="fa fa-wifi" ></i>', */
			html: '编辑',
					title: '编辑基站',
					onToggle: function(e) 
					{
			        	selectArea.getFeatures().clear();
			        	selectAnchor.getFeatures().clear();
				        selectArea.setActive(! e);
				        modifyArea.setActive(! e);
				        selectAnchor.setActive(e);
				        modifyAnchor.setActive(e);
				        showAnchorCoordinate = e;
				        vectorAnchors.changed();
					}
				});
		if('${type}' == "anchor"){
			editbar.addControl(btnAnchorEdit);
		}
			// Add a simple push button to save features
			var save = new ol.control.Button({
				/* html : '<i class="fa fa-save"></i>', */
				html : '保存',
				title : "保存",
				handleClick : function(e) {
					var json = '';
					/*if (selectArea.getActive()) {
						json = new ol.format.GeoJSON().writeFeatures(vectorAreas.getSource().getFeatures(), {featureProjection : 'EPSG:3857',dataProjection : 'EPSG:4326'});
						//saveGeoJson(json);
					} else if (selectAnchor.getActive()) {
						json = new ol.format.GeoJSON().writeFeatures(vectorAnchors.getSource().getFeatures(), {featureProjection : 'EPSG:3857',dataProjection : 'EPSG:4326'});
						//saveGeoJson(json);
					}
					info(json);*/
					json = new ol.format.GeoJSON().writeFeatures(vectorAreas.getSource().getFeatures(), {featureProjection : 'EPSG:3857',dataProjection : 'EPSG:4326'});
					json += "|" + new ol.format.GeoJSON().writeFeatures(vectorAnchors.getSource().getFeatures(), {featureProjection : 'EPSG:3857',dataProjection : 'EPSG:4326'});
					console.log("json:"+json);
					saveGeoJson(json)
				}
		});
		mainbar.addControl(save);
			
		// 加一个刷新 按钮
		var btnReloadAnchors = new ol.control.Button(
					{	/* html: '<i class="fa fa-refresh"></i>', */
						
						html: '刷新',
						title: "刷新",
						handleClick: function(e)
						{
						  	  //刷新基站
							  var now = Date.now();
							  var source = vectorAnchors.getSource();
							  var format = new ol.format.GeoJSON();
							  var url = source.getUrl();	//+'?t=' + now;
							  var loader = ol.featureloader.xhr(url, format);
							  source.clear();
							  loader.call(source, [], 1, 'EPSG:3857');
							
							  
							  
							  //刷新区域
							  var sourceArea = vectorAreas.getSource();
							  var formatArea = new ol.format.GeoJSON();
							  var urlArea = sourceArea.getUrl();	//+'?t=' + now;
							  var loaderArea = ol.featureloader.xhr(urlArea, formatArea);
							  sourceArea.clear();
							  loaderArea.call(sourceArea, [], 1, 'EPSG:3857');
						}
					});
			mainbar.addControl(btnReloadAnchors);
			
		// 保存区域设置
		function saveGeoJson(json) {
			showLoading();
			$.ajax({
				type: "post",
				url: '${basePath}/map/saveAreaMapData.do',
		    	data: {json:json},
				dataType:'json',
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

		function getQueryString(name) { 
	        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	        var r = window.location.search.substr(1).match(reg); 
	        if (r != null) return unescape(r[2]); 
	        return null; 
		}

		vectorAreas.getSource().on('addfeature', function (vectorEvent) {
			//console.log("on addfeature: " + vectorEvent.feature.getId());
			if (targetAreaId === vectorEvent.feature.getId()) {
				console.log("**** Found areaId ****");	
			    selectArea.setActive(true);							// select 平时不激活
			    selectedArea.push(vectorEvent.feature);
			}
		});
		vectorAreas.on('change:source', function (objEvent) {
			//console.log("on change:source");
		});
		

		vectorAreas.getSource().getFeatures().forEach(function(features,index,arr) {
			//console.log(index + " features..");
		});
		//console.log("features len="+vectorAreas.getSource().getFeatures().length);


	$("button[title='编辑区域']").css('width','47px');
	$("button[title='编辑基站']").css('width','47px');
	$("button[title='保存']").css('width','47px');
	$("button[title='刷新']").css('width','47px');
	
	var MeasureTool = new ol.control.MeasureTool({
        sphereradius : 6378137,//sphereradius
      });
    map.addControl(MeasureTool);
	</script>
</body>
</html>