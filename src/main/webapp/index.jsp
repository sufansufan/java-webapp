<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<title>智慧工厂IoT远程监控系统</title>
<link rel="shortcut icon" href="${ctx}/css/images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="${ctx}/js/plugins/layui/css/layui.css?v=2.3.0">
<link rel="stylesheet" href="${ctx}/css/layui-extend.css?v=2020100101">
<link rel="stylesheet" href="${ctx}/css/base.css">
<link rel="stylesheet" href="${ctx}/js/plugins/select2/css/select2.min.css">
<link rel="stylesheet" href="${ctx}/css/header.css?v=20180409">
<link rel="stylesheet" href="${ctx}/css/left.css?v=20180410">
<link rel="stylesheet" href="${ctx}/css/core.css?v=20180519">
<link rel="stylesheet" href="${ctx}/css/fontawesome/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${ctx}/css/sumoselect.css">
<link rel="stylesheet" href="${ctx}/js/pagination/pagination_blue.css">
<link href="${ctx}/js/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=b6c49003c13af335bfa5f923966cbde1"></script>
<!-- <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b6c49003c13af335bfa5f923966cbde1&plugin=AMap.MarkerClusterer,AMap.CitySearch"></script> -->
<%-- <script type="text/javascript" charset="utf-8" src="${ctx}/js/amap/jquery.SLWeb.MapHelper.js"></script> --%>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/amap/jquery.SLWeb.PopupHelper.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/amap/tinybox.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ajaxFontFun.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.rotate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.placeholder.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/utils.js?v=20180415"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/common.js?v=20200924"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/dateUtils.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ztree.js?v=20180115"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/jquery.sumoselect.min.js"></script>
<script src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/js/pagination/pagination.js"></script>

<script src="${ctx}/js/plugins/ztree/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="${ctx}/js/plugins/ztree/jquery.ztree.excheck.min.js" type="text/javascript"></script>
<script src="${ctx}/js/plugins/ztree/jquery.ztree.exedit.min.js" type="text/javascript"></script>
<script src="${ctx}/js/plugins/ztree/jquery.ztree.exhide.min.js" type="text/javascript"></script>

<!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/plugins/layui/html5.min.js"></script>controlLevel
  <script src="${ctx}/js/plugins/layui/respond.min.js"></script>
<![endif]-->
<script src="${ctx}/js/plugins/layui/layui.js?v=2.3.0"></script>
<%-- <script src="${ctx}/js/plugins/echarts/echarts.min.js"></script> --%>
<script src="${ctx}/js/plugins/echarts/echarts.common.min.js"></script>

<link rel="stylesheet" href="${ctx}/js/plugins/simple-calendar/css/simple-calendar.css" />
<script src="${ctx}/js/plugins/simple-calendar/simple-calendar.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/js/plugins/fullcalendar/main.css" />
<script src="${ctx}/js/plugins/fullcalendar/main.js" type="text/javascript"></script>
<!--这是KEY-->
<!-- <script>jwplayer.key="iP+vLYU9H5KyhZeGt5eVuJJIoULUjltoaMeHXg==";</script> -->
<link rel="stylesheet" href="${ctx}/css/treetable.css">
<%-- <link rel="stylesheet" href="${ctx}/css/workbench.css"> --%>
<%--     <link rel="stylesheet" href="${ctx}/css/my.lib.css"> --%>
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin ">
		<jsp:include page="/header.jsp"></jsp:include>
		<div id="contain"></div>
<%-- 		<jsp:include page="/footer.jsp"></jsp:include> --%>
	</div>
	<script src="${ctx}/js/download.min.js" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/select2/js/select2.js"></script>
</body>

<!-- <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=174deb37050c3ec5b58f3b78f74d50d7"></script>  -->
<%-- <script src="${ctx}/js/amap/MapHelper.js"></script> --%>
<%-- <script src="${ctx}/js/amap/dialogMapManage.js"></script> --%>

</html>
