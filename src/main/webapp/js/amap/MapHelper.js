﻿/**
 * 
 * 描述：MapHelper地图加载插件，调用方式：var mapObj = $.Map.Init('id',{});
 */
(function ($, window, document) {
    $.extend({
        Map: {
            /* 
				用途：初始化地图 
				输入：id图层Id  
				返回：mapObj实例化后地图对象 
			*/
            Init: function (mapDiv, opts) {
                //初始化地图
                var mapObj = new AMap.Map(mapDiv, opts);
                return mapObj;
            },
            /**
             * 设置地图显示坐标集合 
			 * 返回：无 
			 */
            SetMarker: function (map, markers, coordinates, clickCallback) {
                fn = {
                    _click: function (map, marker, coordinate,clickCallback) {
                        AMap.event.addListener(marker, 'click', function () {
                        	clickCallback(map, marker, coordinate);
                    	});
                    	
                    },
                    _mouseover: function (map, marker, coordinates) {//鼠标移近点标记时触发事件
                    	
                        AMap.event.addListener(marker, 'mouseover', function () {
                        	
                        });
                    },
                    _mouseout : function(map, marker){//鼠标移出点标记时触发事件
                    	AMap.event.addListener(marker, 'mouseout', function () {
                    		//map.clearInfoWindow();
                        });
                    }
                    
                }
                if(markers != undefined && markers.length>0){
                	var cluster = [];
                	for (var i = 0; i < markers.length; i++) {
                		var marker = markers[i];
                		marker.setMap(map);
                		fn._click(map, marker, coordinates[i],clickCallback);
                	}
                	
//            	    map.plugin(["AMap.MarkerClusterer"], function() {
//                       var cluster = new AMap.MarkerClusterer(map, markers);
//                    });
                	
                	map.setFitView();//地图自适应
                	if(map.getZoom()>16) map.setZoom(16);	
                }
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
})(jQuery)