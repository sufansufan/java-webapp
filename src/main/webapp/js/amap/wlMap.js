var _selObj,mapObj;
$(function(){
	ztreeFun($("#wlMapAreaTree"),"system/sysArea/getAreaTree.do",wlMapAreaTreeClick,wlMapCompleteFun);
})

function wlMapCompleteFun(treeId, treeNode){
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getNodes();
	if (nodes.length > 0) {
		if(nodeId != undefined && nodeId != ""){
			var node = treeObj.getNodeByParam("id", nodeId, null);
			treeObj.selectNode(node);
		}else{
			treeObj.selectNode(nodes[0]);
		}
		//treeObj.expandAll(true);
		var cNodes = treeObj.getSelectedNodes();
		if (cNodes!=undefined && cNodes.length > 0) {
			treeObj.expandNode(cNodes[0], true, false, false);
		}
		wlMapInitMap();
	}else{
		$(".right-search").hide();
		closeLoading();
	}
}

function wlMapAreaTreeClick(){
	wlMapSearchMapList();
}

var selectNodeId;
function wlMapgetParams(){
	var idArr = [];
	
	var treeObj = $.fn.zTree.getZTreeObj("wlMapAreaTree");
	var selectdNode = treeObj.getSelectedNodes()[0];
	var ids = [];
	ids = getChildren(ids,selectdNode);
	selectNodeId=selectdNode.id;
	if(ids != undefined) idArr=ids;
	var params = {
			keyword: $('input[name="wlMapQueryKeyword"]').val(),
			areaIds:idArr.toString(),
			selectNodeId:selectdNode.id
	};
	return params;
}

function wlMapInitMap(){
	
	mapObj = $.Map.Init('map-contain', {
		zoom : 13,
		mapStyle : 'amap://styles/macaron',
	});

	mapObj.on("complete", function() {
		wlMapSearchMapList();
	});
}

function wlMapSearchMapList(){
	mapObj.clearMap();
	$.getJSON("wl/wlMap/allList.do",wlMapgetParams(),function(data){
		closeLoading();
		if(data.success){
			wlMapMarkersInit(data.data)
		}else{
			layer.msg(data.msg);
		}
	})
}

function wlMapMarkersInit(coordinates){
	var cluster, markers = [];
	for (var i = 0; i < coordinates.length; i++) {
		if(coordinates[i].longitude != undefined && coordinates[i].latitude != undefined){
			
			var marker = new AMap.Marker({
				//自定义图标
//				icon: new AMap.Icon({
//					//图标大小
//					size: new AMap.Size(30, 38),
//					//图标地址
//					image: "content/skin/css/images/map/map-" + coordinates[i].code + "-" + coordinates[i].status + ".png"
//				}),
//				icon: coordinates[i].state==1?"http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png":"http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png",
				extData:coordinates[i].id,
				icon: 'css/images/map/map-icon-cj.png',
			 	position: new AMap.LngLat(coordinates[i].longitude, coordinates[i].latitude)
			});
			markers.push(marker);
		}
	}
	
	$.Map.SetMarker(mapObj,markers,coordinates,wlMapClickMarker);
}

/**
 * 地图坐标点击
 * @param map
 * @param marker
 * @param coordinate
 * @returns
 */
function wlMapClickMarker(map, marker, coordinate){
	
	var info = $(".mapInfo");
    info.attr("class", "mapInfo");
    var infoHtml = "<div class=\"map-title-lg map-skin\">";
    infoHtml += "<h1>" + coordinate.siteName + "</h1>";
    infoHtml += "<a class=\"map-close\"></a>";
    infoHtml += "</div>";
//    infoHtml += "<div class=\"map-content\">";
    infoHtml += "<div class=\"map-content-lg layui-form\">";
    
    infoHtml +='<div class="layui-form-left">';
    
    
    var siteCode = coordinate.siteCode;
    if(isNotEmpty(siteCode) && siteCode.length>15){
    	siteCode = siteCode.substring(0,14)+"..."
    }
    infoHtml +='<div class="layui-form-item"><label class="layui-form-label">';
    infoHtml +='站点编码：';
    infoHtml +='</label>';
    infoHtml +='<div class="layui-input-block">';
    infoHtml +='<div class="layui-form-left-text" title="' + coordinate.siteCode + '">' + siteCode;
    infoHtml +='</div></div></div>';
    
    var siteName = coordinate.siteName;
    if(isNotEmpty(siteName) && siteName.length>10){
    	siteName = siteName.substring(0,9)+"..."
    }
    infoHtml +='<div class="layui-form-item"><label class="layui-form-label">';
    infoHtml +='站点名称：';
    infoHtml +='</label>';
    infoHtml +='<div class="layui-input-block">';
    infoHtml +='<div class="layui-form-left-text" title="' + coordinate.siteName + '">' + siteName;
    infoHtml +='</div></div></div>';
    
    
    var chargeUser = coordinate.chargeUser;
    if(isNotEmpty(chargeUser) && chargeUser.length>10){
    	chargeUser = chargeUser.substring(0,9)+"..."
    }
    infoHtml +='<div class="layui-form-item"><label class="layui-form-label">';
    infoHtml +='负责人：';
    infoHtml +='</label>';
    infoHtml +='<div class="layui-input-block">';
    infoHtml +='<div class="layui-form-left-text" title="'+ coordinate.chargeUser +'">' + chargeUser;
    infoHtml +='</div></div></div>';
    
    var chargeUserPhone = coordinate.chargeUserPhone;
    if(isNotEmpty(chargeUserPhone) && chargeUserPhone.length>15){
    	chargeUserPhone = chargeUserPhone.substring(0,14)+"..."
    }
    infoHtml +='<div class="layui-form-item"><label class="layui-form-label">';
    infoHtml +='负责人手机：';
    infoHtml +='</label>';
    infoHtml +='<div class="layui-input-block">';
    infoHtml +='<div class="layui-form-left-text" title="' + coordinate.chargeUserPhone +'">' + chargeUserPhone;
    infoHtml +='</div></div></div>';
    
    infoHtml +='</div>';
    infoHtml +='<div class="layui-form-right">';
    infoHtml +='<img id="imgPreview" alt="站点图片" title="站点图片预览" src="' + upload_url + "/"  + coordinate.image + '" height="144" onerror="loadDefaultImg(this)" style="max-width: 180px !important;">';
    infoHtml +='</div>';
    
    infoHtml +='<div class="layui-form-item layui-btn-item">';
	infoHtml +='<button class="layui-btn map-layui-btn map-detail">详细信息</button>';
	infoHtml +='<button class="layui-btn map-layui-btn map-data">监测数据</button>';
	infoHtml +='<button class="layui-btn map-layui-btn map-video">视频监控</button>';
	infoHtml +='</div>';
    	
    infoHtml += "</div>";
    infoHtml += "<div class=\"map-bottom-lg\"></div>";
    info.html(infoHtml);
    
    //绑定地图关闭事件
    info.find("a.map-close").click(function () {
        map.clearInfoWindow();
    });
    
    info.find("div.layui-btn-item button.map-data").click(function () {
    	layer.msg("监测数据【" + coordinate.siteCode + '】')
    });
    info.find("div.layui-btn-item button.map-detail").click(function () {
    	layer.msg("详细信息【" + coordinate.siteCode + '】')
    });
    info.find("div.layui-btn-item button.map-video").click(function () {
    	layer.msg("视频监控【" + coordinate.siteCode + '】')
    });
	
    var infoWindow = new AMap.InfoWindow({
        isCustom: true,  //使用自定义窗体
        content: info[0],
        offset: new AMap.Pixel(18, -20)
    });
    infoWindow.open(map, marker.getPosition());
	
}

