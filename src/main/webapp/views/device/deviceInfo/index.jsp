<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/device/deviceInfo.js"></script>
<style>
#deviceInfo-content a:hover{
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

<div id="deviceInfo-content" class="contain-content" style="bottom: 0;">

	<div class="layui-form right-search" style="box-shadow: none;">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
				<shiro:hasPermission name="device_info_add">
					<button class="layui-btn layui-btn-light-blue" onclick="deviceInfoAddPage()">
						<i class="layui-icon layui-icon-add-1"></i>添加
					</button>
				</shiro:hasPermission>	
				<shiro:hasPermission name="device_info_del">
					<button class="layui-btn layui-btn-pink" onclick="deviceInfoDelPage()">
						<i class="layui-icon layui-icon-delete"></i>删除
					</button>
				</shiro:hasPermission>
				</div>
			</div>

			<div class="layui-inline" style="float: right">
				<label class="layui-form-label"></label>
				<div class="layui-input-inline">
					<input type="text" name="deviceKeyWord" placeholder="请输入终端编码" class="layui-input search-maxlenght">
				</div>
				
				<div class="layui-input-inline" style="width: 100px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="deviceInfoSearchList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
			</div>
		</div>
	</div>

	<table id="deviceInfo" lay-filter="ldeviceInfo"></table>

</div>
<script type="text/html" id="deviceInfoOptBar">
<div class="layui-btn-group optBar">
<shiro:hasPermission name="device_info_edit">
<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
</shiro:hasPermission>
<shiro:hasPermission name="device_info_del"> 
<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style=""><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
</shiro:hasPermission>
</div>
</script>
<script type="text/html" id="deviceInfoTimeTpl"> 
  {{ getFormatDate(d.modifyTime) }}
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

 var id = '${id}'
 
	var _selObj;

	table.render({
		elem : '#deviceInfo',
		id : 'deviceInfo',
		cols : [ [ 
			{ type : 'checkbox',width: '5%',fixed: 'left'}
			, {	align : 'center',field : 'deviceCode',title : '终端编码',minWidth : 150,width: '12.5%',fixed: 'left'}
			, {	align : 'center',field : 'deviceName',title : '终端名称',minWidth : 150,width: '12.5%',fixed: 'left'}
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
			, {	align : 'center',field : 'modifyTime',title : '更新时间',minWidth : 210,width: '12.5%',sort: true,templet:'#deviceInfoTimeTpl',fixed: 'right'}
			, { align : 'center', title : '操作',minWidth : 240,width: '12.5%',toolbar : '#deviceInfoOptBar',fixed: 'right'}
			] ],
		url : 'device/deviceInfo/deviceInfoList.do',
		page : pageParam,
		where:{
			enterpriseId:id
		},
		limit : 10,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			//无操作提示项
			emptyOptBar("deviceInfo");
			resizeTable('deviceInfo');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("deviceInfo");
		}
	});

	//监听工具条
	table.on('tool(ldeviceInfo)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			var checkStatus = table.checkStatus('deviceInfo');
			deviceInfoDelPage(obj);
		} else if (layEvent === 'edit') {
			deviceInfoEditPage(data.id);
		}
	});
	
</script>