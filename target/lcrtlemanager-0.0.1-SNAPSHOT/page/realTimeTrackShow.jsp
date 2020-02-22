<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="cn">
<head>
  <meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>实时定位演示</title>
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="${basePath}/staticres/bootstrap/css/bootstrap.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="${basePath}/staticres/dist/css/AdminLTE.min.css">
	<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
	<link rel="stylesheet" href="${basePath}/staticres/dist/css/skins/_all-skins.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="${basePath}/staticres/dist/css/font-awesome-4.7.0/css/font-awesome.css">
	<!-- Ionicons -->
	<link rel="stylesheet" href="${basePath}/staticres/dist/css/ionicons-master/css/ionicons.css">
    <link rel="stylesheet" href="${basePath}/staticres/dist/css/adminmap.css" type="text/css">
    <link rel="stylesheet" href="${basePath}/staticres/ol3source/ol.css" type="text/css">
	<link rel="stylesheet" href="${basePath}/staticres/plugins/ol-ext/control/controlbar.css" type="text/css" />
	<link rel="stylesheet" href="${basePath}/staticres/plugins/ol-ext/control/layerswitchercontrol.css" />
	<!-- Bootstrap Color Picker -->
  	<%-- <link rel="stylesheet" href="<%=path%>/admin/plugins/colorpicker/bootstrap-colorpicker.min.css"> --%>
	<link rel="stylesheet" href="${basePath}/staticres/plugins/ol3-measuretool-master/measuretool.css" />
	
<style>
.box-body {
	padding: 5px;
}
 .ol-rotate{
    position:absolute; 
    height:30px; 
    top:40px; 
    z-index:999;
  }
</style>
</head>
  <body style="overflow: hidden;background:#ECF0F5;">
  <input type="hidden" name="FLOORORG_ID" id="FLOORORG_ID" value="${floor.FLOORORG_ID}"/>
  <div style="padding:10px;">
      <div class="row" >
	        <!-- 左边地图 -->
	        <div class="col-md-12">
	        	<div class="box box-primary" >
		            <div class="box-header with-border" >
			              <div id="tagTypeColorList" class="pull-right box-tools">
				              	
			              </div>
		              	   <i class="fa fa-map-marker"></i> 实时定位演示
		              </div>
		            <div class="box-body" >
		              	<div id="mapDiv"   style="border:1px solid #E4E5E6;float:left;width:100%;height:100%;position: absolute;">
		              		<div id="map" style="width:100%;"></div>
		              	</div>
		            </div>
		          </div>
	        </div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="${basePath}/staticres/js/jquery-2.2.3.min.js"></script>
	<script src="${basePath}/staticres/ol3source/ol.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${basePath}/staticres/bootstrap/js/bootstrap.min.js"></script>
	<!-- controls -->
	<script type="text/javascript" src="${basePath}/staticres/ol3source/layer/geoimagesource.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol3-measuretool-master/measuretool.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/control/controlbar.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/style/settextpathstyle.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/control/canvasscalelinecontrol.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/control/buttoncontrol.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/utils/cspline.js"></script>
	
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/attention/zDialog/zDialog.js"></script>
	<!-- 公共js文件 --> 
	<script src="${basePath}/staticres/js/commjs.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/featureanimation/featureanimation.js"></script>
	<script type="text/javascript" src="${basePath}/staticres/plugins/ol-ext/featureanimation/zoomanimation.js"></script>
	<script type="text/javascript">
	$(function() {
		hideLoading();//隐藏遮罩层
		setMapHeight();
		 //color picker with addon
	    //$(".my-colorpicker2").colorpicker();
		//改变窗口大小事件
		$(window).resize(function() {
			setMapHeight();
		});
	});
	//清除历史下线的点
	function clearHistory()
	{
		
		
	}
	//设置Map、Tag地图高度
	function setMapHeight() {
		var bodyHeight = $(this).height();
		$("#mapDiv").height(bodyHeight - 60);
		$("#map").css("height","100%");
		$("#rightDiv").height(bodyHeight - 60);
	}

	//颜色
	var styleColors = {
			/* 'tag' : new ol.style.Fill({
				color : 'red'
			}) */
			'tag' :  'red'
	};
	
	 // 图标的URL
    var clockSourceIconURL = "../staticres/dist/img/clocksource48px.png";
    var anchorIconURL = "../staticres/dist/img/anchor48px.png";
    var tagIconURL = "../staticres/dist/img/tag48px.png";
    var myAreaID="0008D10000000002";//暂时先指定一个区域ID
	//将基站也加载出来
	var vectorAreas = new ol.layer.Vector({
				title: "区域层",    		
		    	source: new ol.source.Vector({
		    			url: '${basePath}/map/getMapDataForArea.do',
		      			format: new ol.format.GeoJSON()
		    		}),
		    	style: areasStyleFunction
			});
	//
	var getAreaText = function(feature, resolution) {
		    	if (feature.get('type') != 'cs') {
		        	var text = feature.get('name');
		        	return text;
		    	}
		    	else
		    		return '';
		      };
	function areasStyleFunction(feature, resolution) {
				return new ol.style.Style({
					//image: new ol.style.Icon({
		          	//			src: clockSourceIconURL,
		          	//			scale: map.getView().getZoom() / 22
		        	//		}),
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
	    
	    //在地图上显示出基站
	    var vectorAnchors = new ol.layer.Vector({
			title: "基站层",    		
			source: new ol.source.Vector({
					url:'${basePath}/map/getMapDataForAnchor.do',
    				format: new ol.format.GeoJSON()
  				}),
  			style: anchorStyleFunction
		});
	    function anchorStyleFunction(feature, resolution) {
		      return new ol.style.Style({
				image: new ol.style.Icon({
		            src: anchorIconURL,
		            scale: map.getView().getZoom() / 20
		          }),
		        text: createAnchorTextStyle(feature, resolution)  
		      });
		    }
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
	   // Anchors
	   var getAnchorText = function(feature, resolution) {
				var text = feature.get('name');//+' - '+feature.get('sn');
				/* if ((showAnchorCoordinate) && (vectorAxis != null)) {
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
				} */
				return text;
			};
	//初始化样式styleColors
	function iniStyleColors(){
		var typeDivs = $('div[id^="type"]')
		if(!typeDivs){
			return false;
		}
		for(var i=0; i<typeDivs.length; i++){
			var divId = typeDivs.eq(i).attr('id');
			var id = divId.replace("type_","");
			var colorVal = $.cookie("color_"+id)
			if(colorVal){
				$("#type_"+id).css("background",colorVal);
				$("#color_"+id).val(colorVal);
				/* styleColors[id] = new ol.style.Fill({
							color : colorVal
						}); */
				styleColors[id] = colorVal;
			}
		}
		
	}
	
	
	//设置标签颜色
	function setTagColor(cId){
		var colorVal = $("#color_"+cId).val();
		$("#type_"+cId).css("background",colorVal);
		//将颜色保存到cookie中
		$.cookie("color_"+cId, colorVal, { expires: 365 });
		/* styleColors[cId] = new ol.style.Fill({
									color : colorVal
								}); */
		styleColors[cId] = colorVal;
	}
	
	//得到标签样式
	function getStyle(feature){
		if(!styleColors[feature.get('type')]){
			return new ol.style.Style({
			    image: new ol.style.Circle({
					radius : 5,
					snapToPixel : false,
					/* fill :  styleColors['tag']*/
					fill : new ol.style.Fill({
						color : styleColors['tag']
					}) 
			    }),
			    text: new ol.style.Text({
			        textAlign: "center",
			        textBaseline: "middle",
			        font: "14px",
			        text: feature.get('name'),
			        /* fill: styleColors['tag'], */
			        fill : new ol.style.Fill({
						color : styleColors['tag']
					}) ,
			        offsetX: 0,
			        offsetY: -20,
			        rotation: 0
			      })
			}) 
		} else {
			return new ol.style.Style({
			    image: new ol.style.Circle({
					radius : 5,
					snapToPixel : false,
					/* fill :styleColors[feature.get('type')] */
			    	fill : new ol.style.Fill({
						color : styleColors[feature.get('type')]
					}) 
			    }),
			    text: new ol.style.Text({
			        textAlign: "center",
			        textBaseline: "middle",
			        font: "14px",
			        text: feature.get('name'),
			       /*  fill: styleColors[feature.get('type')], */
			       	fill : new ol.style.Fill({
						color : styleColors[feature.get('type')]
					}),
			        offsetX: 0,
			        offsetY: -20,
			        rotation: 0
			      })
			}) 
		} 
	}
	
	//获取轨迹颜色
	function getTrackColor(feature){
		var type = feature.get('type');
		var color = styleColors[type];
		if(color){
			return color;
		} else {
			return styleColors['tag'];
		}
	}
	//标签层
	var vectorTags = new ol.layer.Vector({
		title : "标签层",
		source : new ol.source.Vector({
			<%-- url : '<%=basePath%>realTimeTrack/tageJson', --%>
			format : new ol.format.GeoJSON()
		}),
		style: function(feature) {
			return getStyle(feature);
	    }
	});
	
	//轨迹层
	var featureTracks = new ol.Collection();
    var vectorTrack = new ol.layer.Vector(
			{	name: 'Route',
				source: new ol.source.Vector({ features: featureTracks }),
				style: function(f) 
				{	
					/* var opt = 
					{	tension: 1, 
						pointsPerSeg: 100,
						normalize: false
					};
					var csp = f.getGeometry().cspline(opt);
					return [ new ol.style.Style(
					{	stroke: new ol.style.Stroke({ color:getTrackColor(f), width:2 }),
						geometry: csp
					})
					] */
					return [
						new ol.style.Style({
							stroke: new ol.style.Stroke({ color: '#ffcc33',width: 2 }),
						}),
						new ol.style.Style({
							image: new ol.style.RegularShape({ radius: 4, points:4, fill: new ol.style.Fill({ color: '#f00' }) }),
							geometry: new ol.geom.MultiPoint([f.getGeometry().getFirstCoordinate(),f.getGeometry().getLastCoordinate()])
						})
					]
				}
			});
    
	//加载电子围栏
    function loadFencenets(map)
	{
    	var vectorFencenets = new ol.layer.Vector({
    		title : "电子围栏",
    		source : new ol.source.Vector({
    			url : '../fencenet/fencenet_json_by_floorId?floorId='+$("#FLOORORG_ID").val(),
    			format : new ol.format.GeoJSON()
    		}),
    		style : FencenetsStyleFunction
    	});
    	map.addLayer(vectorFencenets);
	}
	function FencenetsStyleFunction(feature, resolution) {
		return new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : 'blue',
				width : 1
			}),
			fill : new ol.style.Fill({
				color : 'rgba(0, 0, 255, 0.1)'
			}),
			text: createFencenetsTextStyle(feature, resolution)
		});
	}
	var createFencenetsTextStyle = function(feature, resolution) {
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
	        text: getFencenetText(feature, resolution),
	        fill: new ol.style.Fill({color: fillColor}),
	        stroke: new ol.style.Stroke({color: outlineColor, width: outlineWidth}),
	        offsetX: offsetX,
	        offsetY: offsetY,
	        rotation: rotation
	      });
	    };
	//Fencenet
	var getFencenetText = function(feature, resolution) {
		if (feature.get('type') != 'cs') {
			var text = feature.get('name');
			return text;
		} else
			return '';
	};
	
	
	var view = new ol.View({
		center : ol.proj.fromLonLat([ 106.65261487, 36.64227590 ]),
		zoom : 22.8
	});
	//平面图
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

	
	//地图
	var map = new ol.Map({
		controls : ol.control.defaults().extend([]),
		target : 'map',
		layers : [ new ol.layer.Tile({
			title : "OpenStreetMap 基础地图",
			baseLayer : true,
			source : new ol.source.OSM(),
			visible : true
		}) ],
		view : view
	});

	//== 加控件 ==
	map.addControl(new ol.control.ScaleLine());// CanvasScaleLine control
	map.addControl(new ol.control.ZoomSlider({}));// ZoomSlider
	map.addControl(new ol.control.Rotate({autoHide : true})); // Rotate
	map.addControl(new ol.control.MousePosition({coordinateFormat : ol.coordinate.createStringXY(8),projection : 'EPSG:4326'}));
	map.addControl(new ol.control.FullScreen({
		tipLabel : "全屏"
	}));
	map.addControl(new ol.control.Rotate({
    	autoHide: true
    }));
	loadPlanMap(map);
	//loadFencenets(map);
	map.addLayer(vectorTrack);
	map.addLayer(vectorTags);
	map.addLayer(vectorAreas);
	map.addLayer(vectorAnchors);
	
	var MeasureTool = new ol.control.MeasureTool({
	        sphereradius : 6378137,//sphereradius
	      });
	    map.addControl(MeasureTool);
	
	var tags;
	//加载标签分类
	function loadTagTypes() {
		$.ajax({
			url : '../realTimeTrack/tagTypeJson',
			type : 'POST',
			data : {},
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var zNode = { id: data[i].BUSDICT_ID, pId:0, name:data[i].BUSDICT_TYPE_NAME, open:true, checked:true};
					zNodes.push(zNode);
				}
				loadTags();
			},
			error : function() {
				Dialog.alert("未能加载到标签分类");
			}
		});
	}

	//加载标签
	function loadTags() {
		$.ajax({
			url : '../realTimeTrack/tagList',
			type : 'POST',
			data : {},
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var zNode = { id: data[i].TAG_ID, pId:data[i].TAG_TYPE_ID, name:data[i].TAG_NAME, open:true, checked:true};
					zNodes.push(zNode);
				}
				createTree();
				$("#init").bind("change", createTree);
				$("#last").bind("change", createTree);
				//定时器_刷新位置信息
				//var intValue = setInterval("loadTagByIds()",100);
				loadTagByIds();
				showPowerAndSign();//显示电量
			},
			error : function() {
				Dialog.alert("未能加载到标签");
			}
		});
	}
	
	//加载选中的标签
	function loadTagByIds() {
		var zTree = $.fn.zTree.getZTreeObj("treeTag");
		var checks = zTree.getCheckedNodes(true);
		var ids = '';
		for(var i = 0; i < checks.length; i++){
			if(i != checks.length-1){
				ids += checks[i].id + ",";
			}else {
				ids += checks[i].id;
			}
		}
		//获取定位数据
		/* $.ajax({
			url : '../realTimeTrack/loadTagByIdsAndFloorId',
			type : 'POST',
			data : {ids:ids,floorId:$("#FLOORORG_ID").val(),pointNum:$("#pointNum").val()},
			success : function(data) {
				tags = data;
				showTag();
			}
		});  */
		$.ajax({
			url : '../realTimeTrack/updateTagParm',
			type : 'POST',
			data : {ids:ids,floorId:$("#FLOORORG_ID").val(),pointNum:$("#pointNum").val()},
			success : function(data) {
				//关闭websocket
				disconnect();
				//连接websocket
				connect();
			}
		});
	}
	
	var myStyles = {
			  'route': new ol.style.Style({
			    stroke: new ol.style.Stroke({
			      width: 6, color: [237, 212, 0, 0.8]
			    })
			  }),
			  'icon': new ol.style.Style({
			    image: new ol.style.Icon({
			      anchor: [0.5, 1],
			      src: 'data/icon.png'
			    })
			  }),
			  'geoMarker': new ol.style.Style({
			    image: new ol.style.Circle({
			      radius: 7,
			      snapToPixel: false,
			      fill: new ol.style.Fill({color: 'black'}),
			      stroke: new ol.style.Stroke({
			        color: 'white', width: 2
			      })
			    })
			  }),
			   'geoMarker1': new ol.style.Style({
			    image: new ol.style.Circle({
			      radius: 7,
			      snapToPixel: false,
			      fill: new ol.style.Fill({color: 'green'}),
			      stroke: new ol.style.Stroke({
			        color: 'white', width: 2
			      })
			    })
			  })
			};
	
	
	function drawLine(currTag)
	 {
		//轨迹
		var routeCoords = currTag.coords;
		if(routeCoords)
			{
				var lstring = [];
				
				for (var j = 0; j < routeCoords.length; j++) {
				if(isNaN(routeCoords[j][0]) || isNaN(routeCoords[j][1])){
							continue;
					}
						if(j == routeCoords.length-1){
							lstring.push(ol.proj.fromLonLat(routeCoords[j]));
							break;
						}
						var x0 = routeCoords[j][0];
						var y0 = routeCoords[j][1];
						var x1 = routeCoords[j+1][0];
						var y1 = routeCoords[j+1][1];
						if(x0==x1 && y0==y1){
							continue;
						}
						lstring.push(ol.proj.fromLonLat(routeCoords[j]));
					}
					
					//画线
					var layerline = new ol.Feature({
		           	type: currTag.TAG_TYPE_ID,
		           	name: currTag.TAG_NAME,
		             geometry: new ol.geom.LineString(lstring)
		         });
					return layerline;
			}else
				{
					return null;
				}
			
	 }
	//定义图层数组
	var trackLayer=[];
	var mapLayer=[];
	function showTag(){
		//debugger;
		//console.info(tags[0].TAG_NAME+"---------"+tags[0].X+","+tags[0].Y+","+tags[0].Z);
		var tagid=tags[0].TAG_ID;
		var isExist=0;
		var myVector =null;
		for(var i=0;i<mapLayer.length;i++)
			{
				if(mapLayer[i].getProperties().title==tagid)
					{
						isExist=1
						myVector=mapLayer[i];
						break;
					}
			}
		if(isExist==1)
			{
				myVector.getSource().clear();
				var myTagMarker = new ol.Feature({
		          	type: tags[0].TAG_TYPE_ID,
		          	name: tags[0].TAG_NAME,
		            geometry: new ol.geom.Point(ol.proj.fromLonLat([tags[0].lon,tags[0].lat]))
		        });
				myTagMarker.setId(tags[0].TAG_ID);
				myVector.getSource().addFeature(myTagMarker);
				
				//再画出他的轨迹
				var mycurrTrackLayer=null;
				if(tags[0].coords)
					{
						for(var mm=0;mm<trackLayer.length;mm++)
							{
								if(trackLayer[i].getProperties().name==tags[0].TAG_ID)
									{
										mycurrTrackLayer=trackLayer[i];
										break;
									}
								
							}
						//mycurrTrackLayer.getSource().clear();
						//mycurrTrackLayer.getSource().addFeature(drawLine(tags[0]));
					}
			}else
				{
					myVector = new ol.layer.Vector({
						title : tags[0].TAG_ID,
						seq64:tags[0].TAG_SEQ64,
						source : new ol.source.Vector({
							format : new ol.format.GeoJSON()
						}),
						style: function(feature) {
							return getStyle(feature);
					    }
					});
					
					var myTagMarker = new ol.Feature({
			          	type: tags[0].TAG_TYPE_ID,
			          	name: tags[0].TAG_NAME,
			            geometry: new ol.geom.Point(ol.proj.fromLonLat([tags[0].lon,tags[0].lat]))
			        });
					myTagMarker.setId(tags[0].TAG_ID);
					myVector.getSource().addFeature(myTagMarker);
					mapLayer.push(myVector);
					//再画出他的轨迹
					if(tags[0].coords)
						{
							var f_Tracks = new ol.Collection();
							var vTagTrack = new ol.layer.Vector(
								{	name: tags[0].TAG_ID,
									source: new ol.source.Vector({ features: f_Tracks }),
									style: function(f) 
									{	
										var opt = 
										{	tension: 1, 
											pointsPerSeg: 100,
											normalize: false
										};
										var csp = f.getGeometry().cspline(opt);
										return [ new ol.style.Style(
										{	stroke: new ol.style.Stroke({ color:getTrackColor(f), width:2 }),
											geometry: csp
										})
										]
									}
								});
							//vTagTrack.getSource().addFeature(drawLine(tags[0]));
							//trackLayer.push(vTagTrack);
							//map.addLayer(vTagTrack);
						}
					map.addLayer(myVector);
				}
		//判断是否按下标签求救按钮
		var switchStatus = tags[0].switchStatus;
		//console.info("状态："+switchStatus);
		if(switchStatus){
			pulse([tags[0].lon, tags[0].lat],20,8,50,"red");
		}
	}
	// Pulse at lonlat
	function pulse(lonlat,radius,points,duration,color){	
		pulseFeature (ol.proj.transform(lonlat, 'EPSG:4326', map.getView().getProjection()),radius,points,duration,color);
	}
	function pulseFeature(coord,radius,points,duration,color){	
  		var f = new ol.Feature (new ol.geom.Point(coord));
		f.setStyle (new ol.style.Style(
					{	image: new ol.style.Circle (
						{	radius: radius, 
							points: points,
							stroke: new ol.style.Stroke ({ color: color, width:3 })
						})
					}));
		map.animateFeature (f, new ol.featureAnimation.Zoom(
			{	fade: ol.easing.easeOut, 
				duration:duration, 
				easing: ol.easing.easeOut
			}));
	}
	//显示标签
	/* function showTag(){
		//
		vectorTags.getSource().clear();
		vectorTrack.getSource().clear();
		for(var i=0; i<tags.length; i++){
			//debugger;
			//console.log("坐标:"+tags[i].lon+","+tags[i].lat);
			//点
			if(tags[i].lon != null && tags[i].lon !=""){
				var tagMarker = new ol.Feature({
	              	type: tags[i].TAG_TYPE_ID,
	              	name: tags[i].TAG_NAME,
	                geometry: new ol.geom.Point(ol.proj.fromLonLat([tags[i].lon,tags[i].lat]))
	            });
				tagMarker.setId(tags[i].TAG_ID);
				vectorTags.getSource().addFeature(tagMarker);
			}
			
			//轨迹
			var routeCoords = tags[i].coords;
			var lstring = [];
			for (var j = 0; i < routeCoords.length; j++) {
				if(isNaN(routeCoords[j][0]) || isNaN(routeCoords[j][1])){
					continue;
				}
				if(j == routeCoords.length-1){
					lstring.push(ol.proj.fromLonLat(routeCoords[j]));
					break;
				}
				var x0 = routeCoords[j][0];
				var y0 = routeCoords[j][1];
				var x1 = routeCoords[j+1][0];
				var y1 = routeCoords[j+1][1];
				if(x0==x1 && y0==y1){
					continue;
				}
				lstring.push(ol.proj.fromLonLat(routeCoords[j]));
			}
			
			//画线
			var f = new ol.Feature({
              	type: tags[i].TAG_TYPE_ID,
              	name: tags[i].TAG_NAME,
                geometry: new ol.geom.LineString(lstring)
            });
			vectorTrack.getSource().addFeature(f);
		}
	} */
	
	//-----------------------zTree Start---------------------------------
	//配置信息
	var setting = {
			view: {
				selectedMulti: false,
				showIcon: function(treeId, treeNode){
					return false;
				}
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: onCheck
			}
		};

		//zTree 节点
		var zNodes =[
			/* { id:1, pId:0, name:"随意勾选 1", open:true, checked:true},
			{ id:11, pId:1, name:"随意勾选 1-1", checked:true}, */
		];

		var clearFlag = false;
		//选中/取消选中,触发的事件
		function onCheck(e, treeId, treeNode) {
			
			//如果标签被取消选中的状态，则应该删除该标签图层
			if(!treeNode.checked)
				{
					//
				   for(var i=0;i<mapLayer.length;i++)
						{
							
					   if(mapLayer[i].getProperties().title==treeNode.id)
						   {
						   	mapLayer[i].getSource().clear();	
						   }
					   
						}
				}
			loadTagByIds();//重新加载标签
			if (clearFlag) {
				clearCheckedOldNodes();
			}
			showPowerAndSign();
		}
		//清除选择的状态
		function clearCheckedOldNodes() {
			var zTree = $.fn.zTree.getZTreeObj("treeTag"),
			nodes = zTree.getChangeCheckedNodes();
			for (var i=0, l=nodes.length; i<l; i++) {
				nodes[i].checkedOld = nodes[i].checked;
			}
		}
		
		function createTree() {
			$.fn.zTree.init($("#treeTag"), setting, zNodes);
			clearFlag = $("#last").attr("checked");
		}
		//-----------------------zTree End---------------------------------
		
		//隐藏或显示右边标签列表
		function showOrHiddenRightDiv(){
			var display = $("#rightDiv").css("display");
			if(display=="none"){
				$("#rightDiv").css("display","");
				$("#mapDiv").css("width","80%");
			}else{
				$("#rightDiv").css("display","none");
				$("#mapDiv").css("width","100%");
			}
		}
		
		//显示电量和信号
		function showPowerAndSign(){
			var zTree = $.fn.zTree.getZTreeObj("treeTag");
			
			$.ajax({
				url : '../realTimeTrack/tagList',
				type : 'POST',
				data : {},
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						var id = data[i].TAG_ID;
						var zn = zTree.getNodeByParam("id", id);
						if(!zn){
							continue;
						}
						var tId = zn.tId;
						if(!tId){
							continue;
						}
						var node = $("#" + tId + "_a");
						if(!$("#power_" + id).length>0){
							node.before(' <i id="power_'+ id +'" class="fa fa-battery-1"></i>');
						}else{
							$("#power_" + id).attr("class","fa fa-battery-1");
						}
						/* if(!$("#sign_" + id).length>0){
							node.before(' <i id="sign_'+ id +'" class="fa fa-feed"></i>');
						}else{
							$("#sign_" + id).attr("class","fa fa-battery-1");
						} */
					}
				},
				error : function() {
					//Dialog.alert("未能加载到标签");
				}
			});	
		}
		
		//旋转角度
		var rotateVal = 0;
		var rotateInterval;
		//旋转
		function rotateView(){
			window.clearInterval(rotateInterval);
			if(rotateVal == 0){
				rotateVal = view.getRotation();
			}
			rotateVal = rotateVal + 45;
			rotateInterval = setInterval("rotateAnimationView()",1);
		}
		
		function rotateAnimationView(){
 			var hudu = (Math.PI/180)*rotateVal
 			view.rotate(view.getRotation() + Math.PI/360);
			if(view.getRotation() >= hudu){
				window.clearInterval(rotateInterval);
			} 
		}
		
		$(".ol-rotate").click(function(){
				rotateVal = 0;
			});
		
		$(document).ready(function() {
			iniStyleColors();
			//loadTagTypes();
			connect();
		});
		
		
		
		//重新加载页面
		function reload(){
			var floorId = $("#FLOORORG_ID").val();
			//debugger;
			window.parent.addTab("fa-bars-real-time-track-2d","fa fa-eye","实时轨迹显示(2D)","/realTimeTrack/show?floorId="+floorId);
		}
		
		//定时器_刷新标签电量和信号
		//var PowerAndSignIntValue = setInterval("showPowerAndSign()",10000);
		
		//连接websocket
		var ws = null;
		function connect() {
			debugger;
			ws = new WebSocket('ws://${websocketUrl}/webSocketServer');
			ws.onopen = function () {
                //setConnected(true);
                //log('Info: connection opened.');
                console.log("connection opened");
            };
            
            ws.onmessage = function (event) {
            	tags = eval(event.data);
            	showTag();
            };
            
            ws.onclose = function (event) {
                //setConnected(false);
                //log('Info: connection closed.');
                //log(event);
                console.log("connection closed");
            };
		}
		
		/*关闭websocket*/
		function disconnect() {
            if (ws != null) {
                ws.close();
                ws = null;
            }
            //setConnected(false);
        }
	</script>
  </body>
</html>
