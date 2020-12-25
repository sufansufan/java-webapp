<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<style>
<!--

-->
/* .layui-table-body { */
/*     overflow: hidden; */
/* } */
</style>
<div class="header">
	<div class="logo">
<%-- 		<img src="${ctx}/css/images/logo.png"> --%>
<%-- 		<img src="${ctx}/css/images/header/arrow_left.png"> --%>
	</div>
<%-- 	<div class="logo-desc" style="">${project}</div> --%>
	<div class="logo-desc" style="">智慧工厂IoT远程监控系统</div>

	<div class="ics-header-left">
		<img class="ics-header-left-img" src="${ctx}/css/images/header/arrow_left.png">
	</div>
	<ul class="ics-header">
		<c:forEach items="${authList}" var="item">
		    <c:choose>
		       <c:when test="${item.authorityName=='监控大屏'}">
		       	<li class="ics-header-item" onclick="showMonitorLargeScreen()">
				<div class="ics-header-item-img" style="width:100%;height:50px;">
					<img src="${ctx}/css/images/header/${item.authorityImage}.png">
				</div>
				<div class="ics-header-item-text" style="width:100%;height:30px;">
					<span>${item.authorityName}</span>
				</div>
			   </li>
		       </c:when>
		       <c:otherwise>
		       <li class="ics-header-item" onclick="showContent('${item.authorityUrl}?authId=${item.id}' ,'contain')">
				<div class="ics-header-item-img" style="width:100%;height:50px;">
					<img src="${ctx}/css/images/header/${item.authorityImage}.png">
				</div>
				<div class="ics-header-item-text" style="width:100%;height:30px;">
					<span>${item.authorityName}</span>
				</div>
			  </li>
		       </c:otherwise>
		    </c:choose>
		</c:forEach>
	</ul>

	<div class="ics-header-right">
		<img class="ics-header-right-img" src="${ctx}/css/images/header/arrow_right.png">
	</div>

	<ul class="layui-nav layui-layout-right">
		<li class="layui-nav-item">
			<a href="javascript:;">
				<c:choose>
					<c:when test="${!loginUser.issupermanager and loginUser.photoPath!=''}">
						 <img src="shareUpload/sysUser/${loginUser.photoPath }" onerror="loadDefaultUserImg(this)" class="layui-nav-img"> 
						<%--<img src="${sysuser_photo_upload_url}/${loginUser.photoPath }" onerror="loadDefaultUserImg(this)" class="layui-nav-img">--%>
					</c:when>
					<c:otherwise>
						<img src="${ctx}/css/images/header/user_admin.png" class="layui-nav-img">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${fn:length(loginUser.userName)>7 }">
						<span title="${loginUser.userName}">${fn:substring(loginUser.userName,0,6)}...</span>
					</c:when>
					<c:otherwise>
						<span title="${loginUser.userName}">${loginUser.userName}</span>
					</c:otherwise>
				</c:choose>
			</a>
				<c:if test="${loginUser.issupermanager}">
					<dl class="layui-nav-child">
			
					<dd>
					<a href="javascript:void(0)" onclick="changePwd()"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 修改密码</a>
				   </dd>
				
			
<!-- 				<dd> -->
<!-- 					<a href="javascript:void(0)"><i class="fa fa-info-circle" aria-hidden="true"></i> 系统信息</a> -->
<!-- 				</dd> -->
			</dl>
				</c:if>
	
		</li>
		
		<li class="layui-nav-item admin-side-full" style="display: none \9">
			<a href="javascript:;" title="点击全屏" style="padding: 0 5px 0 15px;line-height: 80px;">
				<i class="layui-icon" style="font-size: 24px;">&#xe622;</i>
			</a>
		</li>
		
		<li class="layui-nav-item">
			<a href="javascript:void(0)" title="退出" onclick="logout()" style="padding: 0 10px;margin-bottom: 3px">
				<img src="${ctx}/css/images/header/exit.png">
			</a>
		</li>
	</ul>

	<input type="hidden" id="logUId" value="${loginUser.id}">
	<input type="hidden" id="isFull" value="false">
</div>

<script type="text/javascript">
	
var scrollTop = 0;
var maxTop = 100;
layui.config({
	base : '${ctx}/js/plugins/layui_plugins/'
}).use([ 'element', 'form', 'layer', 'table', 'laydate', 'laytpl','upload', 'util', 'treetable' ],function() {
	form = layui.form;
	table = layui.table;
	laytpl = layui.laytpl;
	element = layui.element;
	laydate = layui.laydate;
	upload = layui.upload;
	treetable = layui.treetable;

	
	$("ul.ics-header li.ics-header-item").click(function(data){
		$(this).addClass("sell");
		$(this).siblings().removeClass("sell");
	})
	
	//默认载入第一个菜单
	if ($("ul.ics-header li.ics-header-item").length > 0) {
		$("ul.ics-header li.ics-header-item:first").click();
	}

	form = layui.form;
	//执行渲染
	form.render();

	if ($(".left .layui-nav .layui-nav-item").length > 0) {
		var $p = $(".left .layui-nav .layui-nav-item a[onclick*='addMainTab']:first");

		var nodeName = $p.parent().prop("nodeName");
		if (nodeName.toLowerCase() == "dd") {
			$p.parent().parent()
					.parent("li.layui-nav-item").addClass(
							"layui-nav-itemed");
		} else {
			$p.parent("li.layui-nav-item").addClass(
					"layui-this");
		}
		$p.click();
	}

	$(".ics-header-left").click(function(){
		if($(".ics-header-left-img").is(":visible")){
			if(scrollTop >= 100){
				scrollTop = scrollTop-100;
			}else{
				scrollTop = 0;
			}
			$(".ics-header").scrollTop(scrollTop);
		}
	})

	$(".ics-header-right").click(function(){
		if($(".ics-header-right-img").is(":visible")){
			if(scrollTop < maxTop){
				scrollTop = scrollTop+100;
			}else{
				scrollTop = maxTop;
			}
			$(".ics-header").scrollTop(scrollTop);
		}
	})
	
	element.on('nav(nav-left)', function(elem) {
		if ($(elem).attr("class") == "first-menu") {
			$(".left.layui-side .layui-side-scroll")
					.animate({
						scrollTop : 0
					}, 50)
		}
	});

	element.on('tabDelete(main-tab)', function(data) {
		checkMainTabsClose()
	});

	element.on('tab(main-tab)', function(data) {
		selectedLeftMenu($(this).find("span").text());
		var layid = $(this).attr("lay-id");
		if (isNotEmpty(layid) && layid != 'overall-search'
				&& !isDisabledRefresh) {
			//刷新页面
			if (_tabId == undefined || _tabId != layid) {
				var url = $(
						".left a[tabid='" + layid + "']")
						.attr('taburl');
				if (_taburl != undefined) {
					url = _taburl;
				}
				if (isNotEmpty(url)) {
					refrestTabs(url, layid);
				}
			}
		}

		_tabId = undefined;
		_taburl = undefined;
	});
});

	$(function() {
		
		evalUlWidth();

		if (isIE() > 0) {
			$("li.admin-side-full").hide();
			$("li.admin-side-full").prev("li").css("padding-right", "0px")
		}

		$('[placeholder]').placeholder();

		var w = $(".welcome-user").width() + 12;
		$(".pulldown").css("width", w + "px");

		$(".welcome-user").mouseenter(function() {
			if ($(".pulldown").is(":hidden")) {
				$(".pulldown").show();
			}
		});
		$(".welcome").mouseleave(function() {
			if (!$(".pulldown").is(":hidden")) {
				$(".pulldown").hide();
			}
		});

	})
	
	function evalUlWidth(){
		var wWidth = $(window).width();
		var Ulw = wWidth-410-130-252+12;
		$(".ics-header").css("width",Ulw+"px");
		
		var l = $(".ics-header .ics-header-item").length;
		
		var line_l = parseInt(Ulw/116);
		var hl = parseInt(l/line_l);
		maxTop = hl*100;
		if(line_l < l){
			//显示右
			$(".ics-header-left-img").show();
			$(".ics-header-right-img").show();
		}else{
			$(".ics-header-left-img").hide();
			$(".ics-header-right-img").hide();
		}
		
	}
	
	function logout() {
		$.get("logout.do", function(data) {
			top.location.reload();
		}, "json");
	}

	function editUserPage(id) {

		var flag = '${loginUser.issupermanager}';

		if (flag == true || flag == 'true') {
			openErrAlert('超级管理员无法修改信息');
		} else {
			openLoading();
			$.get("system/sysUser/edit.do", {
				id : id,
				type : 2
			}, function(data) {
				closeLoading();
				topIdx = layer.open({
					type : 1,
					id:'editUserInfoLayer',
					title : "用户信息",
					area : [ '1030px', '600px' ],
					closeBtn : 1,
					content : data,
					maxmin : true,
					resize : true
				});
			});
		}

	}

	function editUserInfoSave(params) {
		openLoading();
		params['type'] = 2;
		$.post("system/sysUser/editSave.do", params, function(data) {
			closeLoading();
			requestSuccess(data, closeLayer);
		}, 'json');
	}

	function changePwd() {
		var flag = '${loginUser.issupermanager}';
		openLoading();
		$.get("system/sysUser/changePwd.do", function(data) {
			closeLoading();
			topIdx = layer.open({
				type : 1,
				area : '400px',
				id:'editUserPwdLayer',
				title : "密码修改",
				closeBtn : 1,
				content : data,
				maxmin : true,
				resize : true
			});
		});
	}
	
	function showMonitorLargeScreen(){
		window.open("${ctx}/views/monitorLargeScreen/index.jsp");
	}
	function loadDefaultUserImg(_this) {
		$(_this).attr("src","/css/images/header/user_admin.png");
	}
</script>