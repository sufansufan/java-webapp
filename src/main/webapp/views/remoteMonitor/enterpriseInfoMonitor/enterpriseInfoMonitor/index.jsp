<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/remoteMonitor/enterpriseInfoMonitor.js"></script>
<style>
#enterpriseInfo-content a:hover{
color: #039;
text-decoration: underline
}
.layui-table-body {
    position: relative;
    overflow: auto!important;
    margin-right: -1px;
    margin-bottom: -1px;
}
.layui-table-main{
overflow: auto;
}
</style>

<div id="enterpriseInfo-content" class="contain-content" style="bottom: 0;">

	<div class="layui-form right-search">
		<div class="layui-form-item">

			<div class="layui-inline">
				<div class="layui-input-inline">
					<input type="text" name="keyWord" placeholder="请输入企业名称" class="layui-input search-maxlenght">
				</div>
				<div class="layui-input-inline" style="width: 100px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="enterpriseInfoSearchList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
			</div>
		</div>
	</div>

	<table id="enterpriseInfo" lay-filter="lenterpriseInfo"></table>

</div>

<script type="text/html" id="enterpriseInfoTimeTpl"> 
  {{ getFormatDate(d.deviceModifyTime) }}
</script>

<script type="text/html" id="enterpriseDateBar">
<button class="layui-btn layui-btn-sm layui-btn-light-blue" onclick="toEnterpriseMainPanel('{{d.deviceCode}}')">企业主看板</button>
</script>

<script type="text/html" id="enterpriseInfoNameTpl"> 
	  <a href="javascript:;" title="点击查看详情" onclick="toDeviceList('{{d.deviceCode}}')" style="color: #06C;cursor:pointer">{{d.enterpriseName}}</a>  
</script>
<script type="text/html" id="netStateTpl">
  {{#  if(d.netState == '1'){ }}
   	 在线
  {{#  } else { }}
   	  离线
  {{#  } }}
</script>
<script type="text/javascript">
$(function(){
	$('[placeholder]').placeholder();
})

	var _selObj;
	table.render({
		elem : '#enterpriseInfo',
		id : 'enterpriseInfo',
		cols : [ [ 
			{ type : 'checkbox',width: '5%',fixed: 'left'}
			, {	align : 'center',field : 'enterpriseName',title : '企业名称',minWidth : 150,width: '12.5%',templet:'#enterpriseInfoNameTpl',fixed: 'left'}
			, {	align : 'center',field : 'deviceCode',title : '绑定终端',minWidth : 210,width: '12.5%',fixed: 'left'}
			, {	align : 'center',field : 'netState',title : '在线状态',minWidth : 210,width: '10%',templet:'#netStateTpl'}
// 			, {	align : 'center',field : 'sn',title : '序列号',minWidth : 210,width: '10%'}
			, {	align : 'center',field : 'firmwareVersion',title : '固件版本',minWidth : 210,width: '10%'}
			, {	align : 'center',field : 'ipAddr',title : 'ip',minWidth : 210,width: '10%'}
			, {	align : 'center',field : 'networkType',title : '网络',minWidth : 210,width: '10%'}
			, {	align : 'center',field : 'operators',title : '运营商',minWidth : 210,width: '10%'}
			, {	align : 'center',field : 'signalStrength',title : '信号值',minWidth : 210,width: '10%'}
			, {	align : 'center',field : 'onlineTime',title : '在线时长',minWidth : 210,width: '10%',sort: true}
			, {	align : 'center',field : 'startTime',title : '启动时长',minWidth : 210,width: '10%',sort: true}
			, {	align : 'center',field : 'lbsLocating',title : '地理位置信息',minWidth : 210,width: '10%'}
			, {	align : 'center',field : 'cpuLoad',title : 'CPU负荷',minWidth : 210,width: '10%',sort: true}
			, {	align : 'center',field : 'memorySurplus',title : '内存剩余容量',minWidth : 210,width: '12.5%',sort: true}
			, {	align : 'center',field : 'memoryPercent',title : '内存使用百分比',minWidth : 210,width: '12.5%',sort: true}
			, {	align : 'center',field : 'flashSurplus',title : 'Flash剩余容量',minWidth : 210,width: '12.5%',sort: true}
			, {	align : 'center',field : 'flashPercent',title : 'Flash使用百分比',minWidth : 210,width: '12.5%',sort: true}
			, {	align : 'center',field : 'address',title : '企业地址',minWidth : 210,width: '12.5%',fixed: 'right'}
			, {	align : 'center',field : 'deviceModifyTime',title : '更新时间',minWidth : 210,width: '12.5%',templet:'#enterpriseInfoTimeTpl',fixed: 'right'}
			, { align : 'center', title : '操作',minWidth : 240,width: '10%',toolbar : '#enterpriseDateBar',fixed: 'right'}
			] ],
		url : 'remoteMonitor/enterpriseInfoMonitor/enterpriseInfoList.do',
		page : pageParam,
		limit : 10,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			//无操作提示项
			emptyOptBar("enterpriseInfo");
			resizeTable('enterpriseInfo');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("enterpriseInfo");
		}
	});

	//监听工具条
	table.on('tool(lenterpriseInfo)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			var checkStatus = table.checkStatus('enterpriseInfo');
			enterpriseInfoDelPage(obj);
		} else if (layEvent === 'edit') {
			enterpriseInfoEditPage(data.id);
		}
	});
	
	function toDeviceList(deviceCode){
		sessionStorage.setItem("deviceCode", deviceCode);
        var clickDocument = $("#condensing_device_monitor");
        clickDocument.click();
	}
	
	function toEnterpriseMainPanel(deviceCode){
		sessionStorage.setItem("deviceCode", deviceCode);
		var clickDocument = $("#enterprise_main_panel");
	    clickDocument.click();
	}
		
	
</script>