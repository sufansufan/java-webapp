/**
 * 
 * 描述：MapHelper地图加载插件，调用方式：var mapObj = $.Map.Init();
 */
(function ($, window, document) {

    var cluster, markers = [];
    $.extend({
        Map: {
            /* 
				用途：初始化地图 
				输入：id图层Id  
				返回：mapObj实例化后地图对象 
			*/
            Init: function () {
                //初始化地图
                var mapObj = new AMap.Map("enterpiserMap", {
//                    layers: [
//						new AMap.TileLayer({ tileUrl: "http://mt{1,2,3,0}.google.cn/vt/lyrs=m@142&hl=zh-CN&gl=cn&x=[x]&y=[y]&z=[z]&s=Galil" })
//                    ],
//                	 layers: [new AMap.TileLayer.Satellite()],
                	resizeEnable: true,
                	zoom:13,
                	center:[121.452045,31.189423],
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
            	if(markers.length>0){
            	cluster.removeMarkers(markers);
            	}
                var points = [];
                cluster, markers = [];
                if(coordinates != undefined){
                	for (var i = 0; i < coordinates.length; i++) {
                		if(coordinates[i].longitude != undefined && coordinates[i].latitude != undefined){
                			
                			var imageUrl = "css/images/map/green.png";
                			if(coordinates[i].alarmState>0 && coordinates[i].netState==1) {
                				imageUrl = "css/images/map/red.png";
                			} else if(coordinates[i].alarmState==0 && coordinates[i].netState==1){
                				imageUrl = "css/images/map/green.png";
                			}else if(coordinates[i].netState==0){
                				imageUrl = "css/images/map/orange.png";
                			}
                			
                			var marker = new AMap.Marker({
                				//自定义图标
                				icon: new AMap.Icon({
                					//图标大小
                					size: new AMap.Size(38, 38),
                					//图标地址
                					image: imageUrl
                				}),
            					position: new AMap.LngLat(coordinates[i].longitude, coordinates[i].latitude),
            					offset: new AMap.Pixel(-15, -15)
            				})

                			 map.add(marker);
//                			marker.setMap(map);
                			$.Map.MapPointClick(map, marker, coordinates[i]);
                			
                				
                		}
                	}

                    map.setFitView();//地图自适应
                }
              	
            },
            
            MapPointClick: function (map, marker, coordinates) {
                //监听鼠标点击事件
                AMap.event.addListener(marker, 'click', function () {  
                	var params ={
                		  enterpriseName: coordinates.enterpriseName,
                		  enterpriseId:coordinates.id
                		}
                	//用于实时刷新当前所点击信息框的信息
                	$("#markerEnterpriseName").val(coordinates.enterpriseName)
                	$.post("remoteMonitor/condensingDeviceMonitor/mapCondensingDeviceList.do", params, function(data) {
                		var condensingDeviceList = JSON.parse(JSON.parse(data))
//                        var formatedTime = "";
//                        if (coordinates.collectTime != null) {
//                      	  formatedTime = getMyDate(coordinates.collectTime)
//                        }
                		var info = $("<div><div>");
//                		var info = $(".mapInfo");
                	    info.attr("class", "mapInfo");
                	    var infoHtml = "<div class=\"map-title-lg map-skin\" style='width: 620px;'>";
                	    infoHtml += "<h1>"+coordinates.enterpriseName+"</h1>";
                	    infoHtml += "<a class=\"map-close\"></a>";
                	    infoHtml += "<h1 class='toEnterperiseMainPanel' style='float:right;margin-right:20px;cursor:pointer'><span>进入主看板</span><img src='../../css/images/enter.png'></h1>";
                	   
                	    infoHtml += "</div>";
                	    infoHtml += "<div class=\"map-content-lg layui-form\" style='width: 620px;max-height:400px;overflow: auto;'>";
                	    
                	    for(i=0;i<condensingDeviceList.length;i++){
//                	    	var day = parseInt(condensingDeviceList[i].runtime / 24);
//                			var hour = condensingDeviceList[i].runtime % 24;
                	    	var runtimeStr =  "--";
                			var runningState = '<span style="color:#C3C3C3">正常</span>';
                			if(condensingDeviceList[i].switchState==0){
                				runningState = '<span style="color:#C3C3C3">停机</span>';               				
                			}else{
                				if(condensingDeviceList[i].alarmState==0){
                    				runningState='<span style="color:#47B347">运行</span>';
                    			}else if(condensingDeviceList[i].alarmState==1){
                    				runningState='<span style="color:#F2CC0C">预警</span>';
                    			}else if(condensingDeviceList[i].alarmState==2){
                    				runningState='<span style="color:#F2495C">故障</span>';
                    			}           				
                			}
                			runtimeStr = condensingDeviceList[i].runtime + "小时";
                			
                	    	infoHtml +='<div class="layui-form-item">';
                       	    infoHtml +='<div class="layui-inline" style="width:230px">';
                       	    infoHtml +='<label class="layui-form-label">机组名称：</label>';
                       	    infoHtml +='<label class="layui-form-label" style="text-align:left;width:130px">'+condensingDeviceList[i].condensingDeviceName+'</label>';
                       	    infoHtml +='</div>';
                       	    infoHtml +='<div class="layui-inline">';
                       	    infoHtml +='<label class="layui-form-label" style="width:80px">运行时间：</label>';
                       	    infoHtml +='<label  class="layui-form-label" style="text-align:left;width:100px">'+runtimeStr+'</label>';
                       	    infoHtml +='</div>';
                       	    infoHtml +='<div class="layui-inline">';
                       	    infoHtml +='<label class="layui-form-label" style="width:50px">状态：</label>';
                       	    infoHtml +='<label  class="layui-form-label" style="text-align:left;width:50px">'+runningState+'</label>';
                       	    infoHtml +='</div>';
                       	    infoHtml +='</div>';
                	    }
                	 
                			
                			
                				
//                	    infoHtml +='<div class="layui-form-item layui-btn-item">';
//                		infoHtml +='<button class="layui-btn map-layui-btn map-data">企业主看板</button>';
//                		infoHtml +='</div>';
                	    
//                	    infoHtml +='</div>';
                	    infoHtml += "</div>";
                	    infoHtml += "<div class=\"map-bottom-lg\"></div>";
                	    info.html(infoHtml);
                       //绑定地图关闭事件
                       info.find("a.map-close").click(function () {
                             map.clearInfoWindow();
                       });
                       
//                       info.find("div.layui-btn-item button.map-data").click(function () {
                       info.find("h1.toEnterperiseMainPanel").click(function () {
                    	   toEnterpriseMainPanel(coordinates.deviceCode)
                       });
                       
                       var infoWindow = new AMap.InfoWindow({                
                           isCustom: true,  //使用自定义窗体
                           content: info[0],
                           offset: new AMap.Pixel(100, 0)
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
            SetLayers: function (map, layers,satelliteLayer,roadNetLayer) {
                if (layers == "satellite") {
                	map.add([satelliteLayer, roadNetLayer]);
//                    map.setDefaultLayer(new AMap.TileLayer.Satellite({ zIndex: 6 }), new AMap.TileLayer.RoadNet({ zIndex: 11 }));
                } else {
                	map.remove([satelliteLayer, roadNetLayer]);
//                    map.setDefaultLayer(new AMap.TileLayer({ tileUrl: "http://mt{1,2,3,0}.google.cn/vt/lyrs=m@142&hl=zh-CN&gl=cn&x=[x]&y=[y]&z=[z]&s=Galil" }));
                }
            },
            
        }
    
    });
    
})(jQuery);


function toEnterpriseMainPanel(deviceCode){
	sessionStorage.setItem("deviceCode", deviceCode);
    var clickDocument = $("#enterprise_main_panel");
    clickDocument.click();
}



