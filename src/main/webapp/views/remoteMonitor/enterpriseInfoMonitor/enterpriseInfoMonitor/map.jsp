<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ctx}/css/demo-center.css"/>
<link rel="stylesheet" href="${ctx}/css/my.lib.css"/>
<link rel="stylesheet" href="${ctx}/css/demo.css"/>
<link rel="stylesheet" href="${ctx}/css/amap/mapLegend.css"/>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/amap/enterpriseInfoMapHelper.js"></script>

<style>
.layui-form-label {
    padding: 9px 5px;
}

.show-type {
    right: 1000px;
}
.tree {
    width: 230px;
    /* opacity: 0.8; */
    z-index:100;
    background-color:#FFF;
}
.leftnav {
    width: 11px;
    height: 61px;
    float: left;
    position: absolute;
    top: 277px;
    z-index: 9;
    background-color: blue;
}
.looknav {
    width: 11px;
    height: 64px;
    float: left;
    position: absolute;
    top: 20px;
    left: 92%;
    z-index: 9;
}
.content-card {
    top: 20px;
    right: 0rem;
    height: calc(96% - 30px);
    word-wrap: break-word;
    background-color: #fff;
    border-radius: .25rem;
    width: 30rem;
    border-width: 0;
    border-radius: 0.4rem;
    box-shadow: 0 2px 6px 0 rgba(114, 124, 245, .5);
    padding: 0.75rem 1.25rem;
}
.map-contain div.map-content-lg {
    width: 460px;
}
</style>

<div id="enterpiserMap" class="map-contain" style="width: 100%; background-color: #fff; height: 100%;overflow: auto;">
	<ul class="show-type">
		<li class="sell" data="2d">地图</li>
		<li data="satellite">卫星</li>
	</ul>
	<!-- 左侧区域显示 -->
	<div class="tree" style="position:absolute;top:0px;right:0px;z-index: 999;width: 0">
		<ul id="orgTree" class="ztree" ></ul>
	</div>
	<div class="leftnav" onclick="treeshow(this)" style="left: 0">
		<a href="javascript:void(0)"><img src="${ctx}/css/images/botton-closeRight.gif"></a>
	</div>
	
</div>

<div class="map-legend">
    <span class="legend-title">图例介绍</span>
    <input type="hidden" id="markerDeviceCode" value="">
    <ul class="legend-icon">
        <li class="map-legend-online">
            <span>正常</span>
            <span style="margin-left: 5px" id="onlineCount"></span>
        </li>
        <li class="map-legend-onlineAlarm" style="margin-left: 6px">
            <span>报警</span>
            <span style="margin-left: 5px" id="alarmCount"></span>
        </li>
        <li class="map-legend-offline" style="margin-left: 6px">
            <span>离线</span>
            <span style="margin-left: 5px" id="outlineCount"></span>
        </li>
    </ul>
</div>

<script>
	
	var devList;
	var index=1;
	var _selObj;
	var nodeId;
	var table = layui.table;
	ztreeFun($("#orgTree"),"system/sysOrg/getOrgTree.do",orgSearchList,orgTreeCompleteFun);
	// 构造官方卫星、路网图层
	var satelliteLayer = new AMap.TileLayer.Satellite();
	var roadNetLayer = new AMap.TileLayer.RoadNet();
	var mapObj = $.Map.Init();
	function orgTreeCompleteFun(treeId, treeNode){
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
				treeObj.expandNode(cNodes[0], true, true, false);
			}
			orgSearchList();
		}else{
			$(".right-search").hide();
			closeLoading();
		}
	}
	function orgSearchList() {

		openLoading();
		$("#lay-loading").css("height","64px");
		var idArr = [];
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var selectdNode = treeObj.getSelectedNodes()[0];
		var ids = [];
		ids = getChildren(ids,selectdNode);
		selectNodeId=selectdNode.id;
		if(ids != undefined) idArr=ids;
		$("input[name='enterpriseName']").val("");
		var enterpriseName=$("input[name='enterpriseName']").val();
		$.post("remoteMonitor/enterpriseInfoMonitor/dataListMap.do",{idArr:idArr.toString(),enterpriseName:enterpriseName},function(data){
			closeLoading();
			if(data.success||data.data){
				devList=data.data;
				mapObj.clearMap();
				$.Map.SetMarker(mapObj, devList);
			}
		},'json');
	}

	
	//左侧区域显示隐藏
	function treeshow(_this) {
		//$(".tree").toggle();
		if(index==0){
			$(".tree").animate( { width: 0}, 300 );
			$(".leftnav").animate( { left: 0}, 300 );
			$(".show-type").animate( { right: 1200}, 300 );
			$(".leftnav img").attr("src","${ctx}/css/images/botton-closeRight.gif");
			index++;
		}else{
			$(".tree").animate( { width: 230}, 300 );
			$(".leftnav").animate( { left: 230}, 300 );
			$(".show-type").animate( { right: 1000}, 300 );
			$(".leftnav img").attr("src","${ctx}/css/images/botton-closeLeft.gif");
			index--;
		}
	}
	//右侧列表信息显示隐藏
	function infoLook() {
		$(".abs").toggle();
	}
	$(".show-type li").click(function() {
		var m = $(this), s = m.siblings();
		m.addClass("sell");
		s.removeClass("sell");
		var type = m.attr("data");
		switch (type) {
		case "2d":
			$.Map.SetLayers(mapObj, type, satelliteLayer, roadNetLayer);
			break;
		case "satellite":
			$.Map.SetLayers(mapObj, type, satelliteLayer, roadNetLayer);
			break;
		}
	});
	
// 	function polluteParams(page){
// 		var idArr = [];
// 		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
// 		var selectdNode = treeObj.getSelectedNodes()[0];
// 		var ids = [];
// 		ids = getChildren(ids,selectdNode);
// 		selectNodeId=selectdNode.id;
// 		if(ids != undefined) idArr=ids;
// 		var polluteName=$("input[name='polluteName']").val();
// 		var params = {
// 				polluteName: polluteName,
// 				orgIds:idArr.toString(),
// 				pageNo:page
// 		};
// 		return params;
// 	}
</script>
