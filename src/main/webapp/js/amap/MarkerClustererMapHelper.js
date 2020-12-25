/**
 * 
 * 描述：MapHelper地图加载插件，调用方式：var mapObj = $.Map.Init();
 */
(function ($, window, document) {
    $.extend({
        Map: {
            /* 
				用途：初始化地图 
				输入：id图层Id  
				返回：mapObj实例化后地图对象 
			*/
            Init: function () {
                //初始化地图
                var mapObj = new AMap.Map("map", {
//                    layers: [
//						new AMap.TileLayer({ tileUrl: "http://mt{1,2,3,0}.google.cn/vt/lyrs=m@142&hl=zh-CN&gl=cn&x=[x]&y=[y]&z=[z]&s=Galil" })
//                    ],
                	resizeEnable: true,
                	 center:[105,34],
                });
                $(".amap-logo,.amap-copyright").hide();
                return mapObj;
            },
            /* 
				用途：设置地图显示坐标集合 
				输入：map实例化后地图对象  coordinates坐标集合[{ id: 测站编码, name: 测站名称, addr: 测站地址, code: 测站类型, lat: 纬度, lng: 经度, value: 采集值, unit: 单位, time: 采集时间, video: 摄像头类型（0表示无1表示普通摄像头2表示DVR）, status: 当前状态 }]
				返回：无 
			*/
            SetMarker: function (map, coordinates) {
                
                var cluster, markers= [];
                var points = [];
                
                if(coordinates != undefined){
                	for (var i = 0; i < coordinates.length; i++) {
                		if(coordinates[i].longitude != undefined && coordinates[i].latitude != undefined){
                			var netState = 0;
                			if(coordinates[i].alarmState>0 && coordinates[i].netState==1) {
                				netState = 2;
                			} else {
                				netState = coordinates[i].netState;
                			}
//                			var water = coordinates[i].water;
//                			var waterhistory = coordinates[i].waterhistory;
//                			if(deviceState==1){
//                				if(water!=null && waterhistory!=null){
//                					if(water>waterhistory){
//                						deviceState = 11;
//                					}else if(water<waterhistory){
//                						deviceState = 13;
//                					}else{
//                						deviceState = 12;
//                					}
//                				}
//                			}
//                			var marker = new AMap.Marker({
//                				//自定义图标
//                				icon: new AMap.Icon({
//                					//图标大小
//                					size: new AMap.Size(30, 38),
//                					//图标地址
//                					image: "content/skin/css/images/map/map-DEV-" + netState + ".png"
//                				}),
////                				icon: coordinates[i].state==1?"http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png":"http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png",
////                				icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
////                				extData:coordinates[i].stcd,
////                				icon: coordinates[i].dsfl==1?"content/skin/css/images/map_icon.png":"content/skin/css/images/map_icon.png",
//                				//在地图上添加点
//                			 	position: new AMap.LngLat(coordinates[i].longitude, coordinates[i].latitude),
//                			 	//地图上右侧设备名称
////                			 	label:{
////                			 		content:coordinates[i].deviceName,
////                			 	    offset: new AMap.Pixel(30, 0)
////                			 	}
//                			});
                			points.push([coordinates[i].longitude, coordinates[i].latitude])
            				markers.push(new AMap.Marker({
            					icon: new AMap.Icon({
            						//图标大小
            						size: new AMap.Size(30, 38),
            						//图标地址
//            						image: "css/images/map/map-DEV-" + netState + ".png"
            						image: "css/images/map/enterprise.png"
            					}),
            					position: new AMap.LngLat(coordinates[i].longitude, coordinates[i].latitude),
            					offset: new AMap.Pixel(-15, -15)
            				}))
//                			marker.setMap(map);
                			//fn._click(map, marker, coordinates[i]);
//                			$.Map.MapPointClick(map, marker, coordinates[i]);
            				$.Map.MapPointClick(map, markers[i], coordinates[i]);
                		}
                	}
                	cluster = new AMap.MarkerClusterer(map, markers, {
        				gridSize: 80
        			});
                	
                	//把所有点连成一个隐形的多边形，然后获取多边形图层，才能达到地图自适应的情况(适用于点聚合，正常的标记物直接用map.setFitView()做地图自适应就好了)
                	var polygon = new AMap.Polygon({
            			path : points,  //以5个点的坐标创建一个隐藏的多边形
            			map:map,
            			strokeOpacity:0,//透明
            			fillOpacity:0,//透明
            			bubble:true//事件穿透到地图
            		});
            		var overlaysList = map.getAllOverlays('polygon');//获取多边形图层
            		map.setFitView(overlaysList);

//                	map.setFitView();//地图自适应
//                	if(map.getZoom()>9) map.setZoom(11);	
                }

            },
            
            MapPointClick: function (map, marker, coordinates) {
                //监听鼠标点击事件
                AMap.event.addListener(marker, 'click', function () {  
                	var params ={
                		  enterpriseName: coordinates.enterpriseName
                		}
                	//用于实时刷新当前所点击信息框的信息
                	$("#markerEnterpriseName").val(coordinates.enterpriseName)
                	$.post("environmentalQuality/pollute/mapRealData.do", params, function(data) {
                		
                		var deviceRtdList = JSON.parse(data)
                		console.log(deviceRtdList)
                		console.log(deviceRtdList[0].enterpriseName)
                		var info = $("<div><div>");
                        info.attr("class", "mapInfo");
                        var formatedTime = "";
                        if (coordinates.collectTime != null) {
                        	formatedTime = getMyDate(coordinates.collectTime)
                        }
                        var infoHtml = "<div class=\"map-title map-skin\">";
                        infoHtml += "<h1>详细信息</h1>";
                        infoHtml += "<a class=\"map-close\"></a>";
                        infoHtml += "</div>";
                        infoHtml += "<div class=\"map-content\">";
                        infoHtml += "<div class=\"content\" style=\"width:504px\">";
                        infoHtml += "<div class=\"content_title1\" style=\"width:120px\">企业名称：</div>";
                        infoHtml += "<div class=\"content_info1 ellipsis\">"+coordinates.enterpriseName+"</div>";
                        infoHtml += "<div class=\"content_title1\" style=\"width:120px\">行业类别：</div>";
                        infoHtml += "<div class=\"content_info1 ellipsis\">"+coordinates.industryCategory+"</div>";
                        infoHtml += "<div class=\"content_title1\" style=\"width:120px\">关注程度：</div>";
                        infoHtml += "<div class=\"content_info1 ellipsis\">"+coordinates.concernDegree+"</div>";
                        infoHtml += "<div class=\"content_title1\" style=\"width:120px\">控制级别：</div>";
                        infoHtml += "<div class=\"content_info1 ellipsis\">"+coordinates.controlLevel+"</div>";
                        infoHtml += "<div class=\"content_title1\" style=\"width:120px\">所在流域：</div>";
                        infoHtml += "<div class=\"content_info1 ellipsis\">"+coordinates.basinName+"</div>";
                        infoHtml += "<div class=\"content_title1\" style=\"width:120px\">排水类型：</div>";
                        infoHtml += "<div class=\"content_info1 ellipsis\">"+coordinates.drainageType+"</div>";
//                        infoHtml += "<div class=\"content_title1\" style=\"width:120px\">采集时间：</div>";
//                        infoHtml += "<div class=\"content_info1 ellipsis\" id='collectTime'>"+(coordinates.collectTime==null?'无数据':''+formatedTime+'')+"</div>";
//                        infoHtml += "<div class=\"content_title1\" style=\"width:120px\">网络状态：</div>";
//                        if(coordinates.netState==0){
//                              infoHtml += "<div class=\"content_info1\"><img src='content/skin/css/images/icon/icon-exclamation.png'></img>离线</div>";
//                         }else{
//                         infoHtml += "<div class=\"content_info1\"><img src='content/skin/css/images/icon/icon-accept.png'></img>在线</div>";
//                         }           
//                         infoHtml += "<div class=\"content_title1\" style=\"width:120px\">所属企业：</div>";
//                         infoHtml += "<div class=\"content_info1 ellipsis\" title='"+coordinates.enterpriseName+"'>"+coordinates.enterpriseName+"</div>";
//                         infoHtml += "<div class=\"content_title1\" style=\"width:120px\">设备状态：</div>";
//                         if(coordinates.factorFlag=="N"){
//                              infoHtml += "<div class=\"content_info1\"><img src='content/skin/css/images/icon/icon-accept.png'></img>正常</div>";
//                         }else if(coordinates.factorFlag=="T"){
//                              infoHtml += "<div class=\"content_info1\"><img src='content/skin/css/images/icon/icon-exclamation.png'></img>数值超过测量上限</div>";
//                         }else if(coordinates.factorFlag=="B"){
//                              infoHtml += "<div class=\"content_info1\"><img src='content/skin/css/images/icon/icon-exclamation.png'></img>通讯异常</div>";
//                         }else{
//                              infoHtml += "<div class=\"content_info1\"><img src='content/skin/css/images/icon/icon-accept.png'></img>正常</div>";
//                         } 
//                         if(deviceRtdList!=null){
//                             for(var i=0;i<deviceRtdList.length;i++){
//                                 infoHtml += "<div class=\"content_title1\" style=\"width:120px\">"+deviceRtdList[i].factorName+"：</div>";
//                                 if(deviceRtdList[i].factorName=='风向'){
//                                	    if(deviceRtdList[i].factorValue==0){
//                                            infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>北风</div>";
//                                       }else if(deviceRtdList[i].factorValue==1){
//                                            infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>东北风</div>";
//                                       }else if(deviceRtdList[i].factorValue==2){
//                                            infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>东风</div>";
//                                       }else if(deviceRtdList[i].factorValue==3){
//                                            infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>东南风</div>";
//                                       }else if(deviceRtdList[i].factorValue==4){
//                                            infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>南风</div>";
//                                       }else if(deviceRtdList[i].factorValue==5){
//                                            infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>西南风</div>";
//                                       }else if(deviceRtdList[i].factorValue==6){
//                                            infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>西风</div>";
//                                       }else if(deviceRtdList[i].factorValue==7){
//                                            infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>西北风</div>";
//                                       }else{
//                                    	   infoHtml += "<div class=\"content_info1\">"+deviceRtdList[i].factorValue+"</div>";
//                                       } 
//                                 }else{
//                                	 infoHtml += "<div class=\"content_info1\" id='"+coordinates.deviceCode+"_"+i+"'>"+(deviceRtdList[i].factorValue==null?'--':''+deviceRtdList[i].factorValue.toFixed(2)+'')+" "+deviceRtdList[i].factorUnit+"</div>";
//                                 }
//                               } 
//                         }                                                           
//                                                           
//                       infoHtml += "<div class=\"content_title1\" style=\"width:120px\">安装前现场照片：</div>";
//                       infoHtml += "<div class=\"content_info1\"><a class='ml20' style='color:#4ba2be;cursor: pointer;' onclick='ShowPhotoInfo(\""+coordinates.deviceCode+"\",\"before\")'>预览</a></div>";
//                       infoHtml += "<div class=\"content_title1\" style=\"width:120px\">安装后现场照片：</div>";
//                       infoHtml += "<div class=\"content_info1\"><a class='ml20' style='color:#4ba2be;cursor: pointer;' onclick='ShowPhotoInfo(\""+coordinates.deviceCode+"\",\"after\")'>预览</a></div>";
                       infoHtml += "</div>";
                       infoHtml += "</div>";
                       infoHtml += "<div class=\"map-bottom\"></div>";
                       info.html(infoHtml);
                       //绑定地图关闭事件
                       info.find("a.map-close").click(function () {
                             map.clearInfoWindow();
                       });
                                    
                       var infoWindow = new AMap.InfoWindow({
    /*                                    content: info[0],
                                        size:new AMap.Size(489, 0),   
                                        autoMove: true, //超出屏幕自动平移
                                        offset: new AMap.Pixel(18, -20),
                                        isCustom: false,  //使用自定义窗体
    */                                    
                           isCustom: true,  //使用自定义窗体
                           content: info[0],
                           offset: new AMap.Pixel(40, -10)
                       });
                      infoWindow.open(map, marker.getPosition());
        			});
                   
                    
                    
                });
            },
            
            /* 
				用途：设置地图类型切换 
				输入：map地图对象 layers地图类型 
				返回：无 
			*/
            SetLayers: function (map, layers) {
                if (layers == "satellite") {
                    map.setDefaultLayer(new AMap.TileLayer.Satellite({ zIndex: 6 }), new AMap.TileLayer.RoadNet({ zIndex: 11 }));
                } else {
                    map.setDefaultLayer(new AMap.TileLayer({ tileUrl: "http://mt{1,2,3,0}.google.cn/vt/lyrs=m@142&hl=zh-CN&gl=cn&x=[x]&y=[y]&z=[z]&s=Galil" }));
                }
            },
        }
    });
})(jQuery);


