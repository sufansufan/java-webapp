<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="kit-side-fold">
	<img id="side-menu" src="${ctx}/css/images/common/menu.png" style="">
</div>
<div class="more-menu-up" onclick="moreUpMenu()">
		<u><i class="layui-icon layui-icon-triangle-d"></i></u>
	</div>
<div class="left layui-side layui-bg-l-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree" lay-filter="nav-left" lay-shrink="all">
      
      	<c:forEach items="${authMap[authId]}" var="item" varStatus="vs">
      		<li class="layui-nav-item">
	      		<a class="first-menu" href="javascript:;" 
	      		tabid="menu-${vs.count}" taburl="${item.authorityUrl}${fn:contains(item.authorityUrl,'?')?'&':'?'}authId=${item.id}"
				tabname="${item.authorityName}"
				tabicon="${item.authorityImage}"
	      		<c:if test="${fn:length(authMap[item.id])==0}">onclick="addMainTab('${item.authorityUrl}${fn:contains(item.authorityUrl,'?')?'&':'?'}authId=${item.id}' ,'menu-${vs.count}','${item.authorityName}','${item.authorityImage}',{},true)"</c:if>>
		      		<img src="${ctx}/css/images/left/second/${item.authorityImage}.png" style="margin-bottom: 5px;">
		      		<span style=" padding-left: 0px;">${item.authorityName}</span>
	      		</a>
	      		<c:if test="${fn:length(authMap[item.id])>0}">
		      		<dl class="layui-nav-child">
		      			<c:forEach items="${authMap[item.id]}" var="childItem" varStatus="cvs">
		      				<dd>
		      					<a class="second-menu" href="javascript:;" 
		      					style="padding-left: 30px;padding-right: 10px;" 
		      					tabid="menu-${vs.count}-${cvs.count}"
		      					taburl="${childItem.authorityUrl}${fn:contains(item.authorityUrl,'?')?'&':'?'}authId=${childItem.id}"
		      					tabname="${childItem.authorityName}"
		      					tabicon="${childItem.authorityImage}"
		      					onclick="addMainTab('${childItem.authorityUrl}${fn:contains(item.authorityUrl,'?')?'&':'?'}authId=${childItem.id}' ,'menu-${vs.count}-${cvs.count}','${childItem.authorityName}','${childItem.authorityImage}',{},true)">
		      						<img src="${ctx}/css/images/left/second/${childItem.authorityImage}.png" style="margin-bottom: 5px;">
		      						<span style=" padding-left: 0px;">${childItem.authorityName}</span>
		      					</a>
		      				</dd>
		      			</c:forEach>
			       </dl>
	      		</c:if>
      		</li>
      	</c:forEach>
      </ul>
    </div>
  </div>
  <div class="more-menu" onclick="moreMenu()">
		<u><i class="layui-icon layui-icon-triangle-d"></i></u>
	</div>
  
  <div id="sys-content" class="second-main-content">
    <!-- 内容主体区域 -->
    
    <div id="main-tab" class="layui-tab layui-tab-brief" lay-filter="main-tab" lay-allowClose="true">
		<ul id="main-tab-title" class="layui-tab-title">
		</ul>
		<div id="main-tab-content" class="layui-tab-content"></div>
	</div>
    
  </div>
  
  <script>

$(function(){
	
	element.render();
	if ($(".left .layui-nav .layui-nav-item").length > 0) {
		var $p = $(".left .layui-nav .layui-nav-item a[onclick*='addMainTab']:first");
		
		//获取该元素的节点名称
		var nodeName = $p.parent().prop("nodeName");
		if(nodeName.toLowerCase()=="dd"){
			$p.parent().parent().parent("li.layui-nav-item").addClass("layui-nav-itemed");
		}else{
			$p.parent("li.layui-nav-item").addClass("layui-this");
		}
		$p.click();
	}
	
})
</script>