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
<!--       左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree" lay-filter="nav-left" lay-shrink="all">
      
      	<c:forEach items="${authMap['0']}" var="item" varStatus="vs">
      	
      		<li class="layui-nav-item" class="${item.authorityName='机组信息'?'layui-nav-itemed':''}">
	      		<a class="first-menu" href="javascript:;" 
	      		tabid="menu-${vs.count}" taburl="${item.authorityUrl}${fn:contains(item.authorityUrl,'?')?'&':'?'}authId=${item.id}"
				tabname="${item.authorityName}"
				tabicon="${item.authorityImage}"
	      		<c:if test="${fn:length(authMap[item.id])==0}">onclick="addMainTab('${item.authorityUrl}${fn:contains(item.authorityUrl,'?')?'&':'?'}authId=${item.id}' ,'menu-${vs.count}','${item.authorityName}','${item.authorityImage}',{},true)"</c:if>>
		      		<img src="${ctx}/css/images/left/${item.authorityImage}.png" style="margin-bottom: 5px;">
		      		<span style=" padding-left: 0px;">${item.authorityName}</span>
	      		</a>
	      		<c:if test="${fn:length(authMap[item.id])>0}">
		      		<dl class="layui-nav-child">
		      			<c:forEach items="${authMap[item.id]}" var="childItem" varStatus="cvs">
		      				<dd">
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
  
  <script type="text/javascript">
  var _tabId;
  var _taburl;
  console.log(4545)
  layui.config({
      base: '${ctx}/js/plugins/layui_plugins/'
  }).use(['element','layer','table','laydate','laytpl','upload','util', 'treetable'], function(){
		//默认载入第一个菜单
		table = layui.table;
		laytpl = layui.laytpl;
		element = layui.element;
		laydate = layui.laydate;
		upload = layui.upload;
		treetable = layui.treetable;
		if ($(".left .layui-nav .layui-nav-item").length > 0) {
			var $p = $(".left .layui-nav .layui-nav-item a[onclick*='addMainTab']:first");
			
			var nodeName = $p.parent().prop("nodeName");
			if(nodeName.toLowerCase()=="dd"){
				$p.parent().parent().parent("li.layui-nav-item").addClass("layui-nav-itemed");
			}else{
				$p.parent("li.layui-nav-item").addClass("layui-this");
			}
			$p.click();
		}
		
		element.on('nav(nav-left)', function(elem){
			if($(elem).attr("class")=="first-menu"){
				$(".left.layui-side .layui-side-scroll").animate({  
					scrollTop:0
				},50) 
			}
		});
		
		element.on('tabDelete(main-tab)', function(data){
			  checkMainTabsClose()
		});
		
		element.on('tab(main-tab)', function(data){
			  var layid = $(this).attr("lay-id");
			  console.log(layid)
// 			  selectedLeftMenu($(this).find("span").text());
			  selectedLeftMenu(layid);
			  if(isNotEmpty(layid) && layid != 'overall-search' && !isDisabledRefresh){
				  //刷新页面
				   if(_tabId==undefined || _tabId != layid){
					  var url = $(".left a[tabid='"+layid +"']").attr('taburl');
					  if(_taburl!=undefined){
						  url=_taburl; 
					  }
					  if(isNotEmpty(url)){
						  refrestTabs(url,layid);
					  }
				   }
			  }
			  
			  _tabId = undefined;
			  _taburl = undefined;
		});
  });
  	$(function() {
	  	
  		$(".layui-nav.layui-nav-tree .layui-nav-item").click(function(){
  			resizeLeft();
  		});
  		
	})
  </script>
  	