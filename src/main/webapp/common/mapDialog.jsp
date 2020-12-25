<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<style type="text/css">
#mapDialogContainer {
	height: 500px;
	width: 100%;
}
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/css/amap/mapDialog.css">

<input type="hidden" id="mapDialog_longitude" value="${longitude}">
<input type="hidden" id="mapDialog_latitude" value="${latitude}">
<input type="hidden" id="mapDialog_callback" value="${callback}">
<input type="hidden" id="mapDialog_localAddress" value="${address}">
<div id="mapDialogContainer"></div>
<c:if test="${!isNotShowSelect}">
<!-- 关键字查询 -->
<div id="searchArea">
<input type="hidden" id="mapDialog_local" value="${isNotShowSelect}">
	<b>关键字查询：</b> <br>
	<input type="text" id="mapDialog_keyword" name="mapDialog_keyword" onkeydown='dialogKeydown(event)'/>
	<input type="button" class="layui-btn" value="查询" onclick="keywordSearch()">
	<input type="button" class="layui-btn" value="确定" id="returnBtn" onclick="returnValue(this)">
	<div id="result1" name="result1"></div>
</div>
</c:if>
<script type="text/javascript" src="${ctx}/js/amap/ZMap.js"></script>
<script type="text/javascript" src="${ctx}/js/amap/dialogMap.js"></script>
