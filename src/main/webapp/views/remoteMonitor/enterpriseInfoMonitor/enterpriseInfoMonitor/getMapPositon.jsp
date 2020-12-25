<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/amap/dialogMap.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/amap/ZMap.js"></script>

<style>
#searchArea {
    background-color: #fff;
    border: 1px solid #ccc;
    padding-left: 10px;
    padding-right: 2px;
    position: absolute;
    min-height: 65px;
    top: 38px;
    font-size: 12px;
    right: 0px;
    border-radius: 3px;
    overflow: hidden;
    line-height: 20px;
    min-width: 435px;
}
</style>

<div id="mapContainer" style="float:right; height:353px; width:500px; border: 1px solid #dcdddd; position: relative">
	<input type="hidden" id="localLng"> <input type="hidden" id="localLat"> <input type="hidden" id="localAddress">
    	<!-- 关键字查询 -->
	<div id="searchArea"  style="z-index:901;top:0px;min-height:55px;min-width:385px;">
		<b>请输入关键字：</b> 
		<input type="text" id="keyword" name="keyword" value="" onkeydown='keydown(event)' style="width: 60%;" /> 
		<input type="button" value="查 询" onclick="keywordSearch()">
		<div id="result1" name="result1"></div>
	</div>
</div>
