/*
 * 这是管理界面用的地图, 带有一配配置功能，例如调整基站位置等等 
 * 
 * 在 html 页面中已经加载了 ol3.js
*/
	var targetAreaId = "";
	var showAnchorCoordinate = false;
	var rtleOrigin = null;  
	var wgs84Sphere = new ol.Sphere(6378137);
    // 图标的URL
    var clockSourceIconURL = "../admin/dist/img/clocksource48px.png";
    var anchorIconURL = "../admin/dist/img/anchor48px.png";
    var tagIconURL = "../admin/dist/img/tag48px.png";
    
    //== 自定义 logo, 显示在图层属性中
    var logoElement = document.createElement('a');
    logoElement.href = 'http://localhost:8888/rtls/oltest/examples';
    logoElement.target = '_blank';
    var logoImage = document.createElement('img');
    logoImage.src = '../admin/dist/img/logo.png';
    logoElement.appendChild(logoImage);    
    

    //Areas
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
                		width: 1
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
    			url: '../rtlsConfig/json_areas',
      			format: new ol.format.GeoJSON()
    		}),
    	style: areasStyleFunction
	});

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
						url:'../rtlsConfig/json_anchors',
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
          color: 'red',
          width: 4
        })
      })];
    }
    

    var vectorAxis = new ol.layer.Vector({
			title: "坐标轴",  
			source: new ol.source.Vector({
				    //url:'../admin/page/location/json_coordinate_axis.jsp',
				   url:'../rtlsConfig/json_coordinate_axis',
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
        view: new ol.View({
          center: ol.proj.fromLonLat([106.64598602, 26.64227590]),
          zoom: 22.8
        }),
        logo: logoElement        
      });

    //== 加控件 ==
	map.addControl(new ol.control.CanvasScaleLine());		// CanvasScaleLine control
	map.addControl(new ol.control.ZoomSlider({  }));		// ZoomSlider
	map.addControl(new ol.control.Rotate({autoHide:true}));	// Rotate
	map.addControl(new ol.control.MousePosition({coordinateFormat: ol.coordinate.createStringXY(8),projection: 'EPSG:4326'}));
    //== 加层 ==
	map.addLayer(vectorAxis);		// 坐标轴	
	map.addLayer(vectorAreas);		// Areas Layer
	map.addLayer(vectorAnchors);	// Anchors Layer 
	
	//=======================
    // 加建筑平面图层
    //=======================
	var geoimg27;
	$.ajax({
		type: "get",
		url: '../plancePic/getJson',
		dataType:'json',
		async:false,
		success: function(data){
			if(data.url != null){//如果已上传平面图
				geoimg27 = new ol.layer.Image({	
					name: "27楼平面图",
					title: "27楼平面图",
					opacity: data.pic_opacity,
					source: new ol.source.GeoImage({	url: '../'+data.url,
						imageCenter: ol.proj.fromLonLat([data.pic_center_longitude, data.pic_center_latitude]),
						imageScale: [data.pic_scale_x, data.pic_scale_y],
						imageRotate: data.pic_rotate*Math.PI/180,
						projection: 'EPSG：4326',
						attributions: [ new ol.Attribution({html:"<a href='http://www.jlrtls.com/'>&copy; 旌霖科技</a>"}) ]
					})
			    });
				map.addLayer(geoimg27);
			}
		}
	});
	
	
    //=======================
    // 层切换
    //=======================
	// Add a layer switcher outside the map
	var switcher = new ol.control.LayerSwitcher({	target:$(".layerSwitcher").get(0), 
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

	// editbar 加一个编辑平面图的按钮
	var btnIchnographyEdit = new ol.control.Button({	
				html: '<i class="fa fa-picture-o" ></i>',
				title: '调整平面图',
				handleClick: function(e) {
					//查询是否有平面图
					$.ajax({
						type: "get",
						url: '../plancePic/getJson',
						dataType:'json',
						async:false,
						success: function(data){
							if(data.url != null){//如果已上传平面图
								var diag = new Dialog();
								diag.Width = 500;
								diag.Height = 200;
								diag.Title = "平面图调整";
								diag.URL = "../plancePic/showPlanceWin";
								diag.OKEvent = function(){
									 var pic_opacity = Number(diag.innerFrame.contentWindow.document.getElementById('pic_opacity').value);
									 var pic_rotate = Number(diag.innerFrame.contentWindow.document.getElementById('pic_rotate').value);
									 var pic_center_longitude = Number(diag.innerFrame.contentWindow.document.getElementById('pic_center_longitude').value);
									 var pic_center_latitude = Number(diag.innerFrame.contentWindow.document.getElementById('pic_center_latitude').value);
									 var pic_scale_x = Number(diag.innerFrame.contentWindow.document.getElementById('pic_scale_x').value);
									 var pic_scale_y = Number(diag.innerFrame.contentWindow.document.getElementById('pic_scale_y').value);
									 geoimg27.setOpacity(pic_opacity);
									 geoimg27.getSource().setCenter(ol.proj.fromLonLat([pic_center_longitude,pic_center_latitude]));
									 geoimg27.getSource().setRotation(pic_rotate*Math.PI/180);
									 geoimg27.getSource().setScale([pic_scale_x,pic_scale_y]);
									 diag.innerFrame.contentWindow.save();//执行保存
								};
								diag.CancelEvent = function(){ //关闭事件
									diag.close();
								}
								diag.show(); 
							}else{
								Dialog.alert("请先上传平面图!");
							}
						}
					});
				}
			});
	editbar.addControl(btnIchnographyEdit);
	
	
	
	// editbar 加一个编辑区域的按钮
	var btnAreaEdit = new ol.control.Toggle({	
				html: '<i class="fa fa-map-o" ></i>',
				title: '编辑区域',
				onToggle: function(e) {
		        	selectArea.getFeatures().clear();
		        	selectAnchor.getFeatures().clear();
		        	selectAnchor.setActive(! e);
			        modifyAnchor.setActive(! e);
			        selectArea.setActive(e);
			        modifyArea.setActive(e);
			        showAnchorCoordinate = false;
			        vectorAnchors.changed();
			        try {
			        	window.parent.showPolyon("the info from map, e="+e);
			        }
			        catch(ee) {
			        	alert("call ShowPolyon() ERR: "+ee);
			        }
				}
			});
	editbar.addControl(btnAreaEdit);
	
	// editbar 加一个编辑 Anchor 的按钮
	var btnAnchorEdit = new ol.control.Toggle({	
		html: '<i class="fa fa-wifi" ></i>',
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
	editbar.addControl(btnAnchorEdit);

		// Add a simple push button to save features
		var save = new ol.control.Button({
			html : '<i class="fa fa-save"></i>',
			title : "Save",
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
				saveGeoJson(json)
			}
	});
	mainbar.addControl(save);
		
		// 加一个重载 Anchors 层按钮
		var btnReloadAnchors = new ol.control.Button(
				{	html: '<i class="fa fa-refresh"></i>',
					title: "重新加载基站数据",
					handleClick: function(e)
					{
						  var now = Date.now();
						  var source = vectorAnchors.getSource();
						  var format = new ol.format.GeoJSON();
						  var url = source.getUrl();	//+'?t=' + now;
						  var loader = ol.featureloader.xhr(url, format);

						  source.clear();
						  loader.call(source, [], 1, 'EPSG:3857');						
					}
				});
		mainbar.addControl(btnReloadAnchors);
		
	// 保存区域设置
	function saveGeoJson(json) {
		/*$.ajax({
            type: "POST",	//提交数据的类型 POST GET
            url: "save_geojson.jsp",	//提交的网址
			data: { areas: jsonArea },	// {Name:"sanmao",Password:"sanmaoword"},		//提交的数据
			datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".//返回数据的格式 
			//beforeSend: function() {$("#msg").html("logining");},			//在请求之前调用的函数
			success: function(data) {$("#msg").html(decodeURI(data));},		//成功返回之后调用的函数             
			complete: function(XMLHttpRequest, textStatus) {		//调用执行后调用的函数
					alert(XMLHttpRequest.responseText);
					alert(textStatus);
					//HideLoading();
				},
			error: function() {									//调用出错执行的函数
					//请求出错处理
				}         
		});*/
		showLoading();
		$.ajax({
			type: "post",
			url: '../rtlsConfig/saveMapRtlsData',
	    	data: {json:json},
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

	function getQueryString(name) { 
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
        var r = window.location.search.substr(1).match(reg); 
        if (r != null) return unescape(r[2]); 
        return null; 
	}

	vectorAreas.getSource().on('addfeature', function (vectorEvent) {
		console.log("on addfeature: " + vectorEvent.feature.getId());
		if (targetAreaId === vectorEvent.feature.getId()) {
			console.log("**** Found areaId ****");	
		    selectArea.setActive(true);							// select 平时不激活
		    selectedArea.push(vectorEvent.feature);
		}
	});
	vectorAreas.on('change:source', function (objEvent) {
		console.log("on change:source");
	});
	

	vectorAreas.getSource().getFeatures().forEach(function(features,index,arr) {
		console.log(index + " features..");
	});
	console.log("features len="+vectorAreas.getSource().getFeatures().length);

