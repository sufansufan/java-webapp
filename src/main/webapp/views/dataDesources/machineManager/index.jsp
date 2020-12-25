<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/dataDesources/machineManager.js"></script>
	<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css" />
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<style>
#polluteEnterprise-content a:hover {
	color: #039;
	text-decoration: underline
}
</style>
<style>
.SumoSelect>.optWrapper>.options {
	max-height: 140px !important;
}

#monitorFactorDiv .layui-form-select {
	display: none !important;
}

.SumoSelect>.CaptionCont>span {
	height: 18px !important;
	line-height: 18px !important;
}
.layui-table-main{
overflow: auto;
}
.layui-table-body {
	position: relative;
	overflow-x: auto!important;
	margin-right: -1px;
	margin-bottom: -1px;
}
</style>

<div id="condensingDevice-content" class="contain-content"
	style="bottom: 0;">

	<div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<shiro:hasPermission name="machine_add">
						<button class="layui-btn layui-btn-light-blue"
							onclick="machineAddPage()">
							<i class="layui-icon layui-icon-add-1"></i>添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="condensing_device_del">
						<button class="layui-btn layui-btn-pink"
							onclick="machineDelPage()">
							<i class="layui-icon layui-icon-delete"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
			</div>
			 <div class="layui-inline">
				 <div class="layui-input-inline">
					 <input type="text" name="machineName" placeholder="请输入设备名称" class="layui-input" style="height: 36px">
				 </div>
				 <div class="layui-input-inline" id="machineTypeSelect" style="width: 200px">
					 <select id="machineType" name="machineType" xm-select="select_1" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">
						 <option value="">请选择设备类型</option>
						 <c:forEach var="item" items="${machineTypeList}">
							 <option value="${item.itemValue}">${item.itemName}</option>
						 </c:forEach>
					 </select>
				 </div>
				 <div class="layui-input-inline" id="orgListSelect" style="width: 200px">
					 <select id="orgList" name="orgId" xm-select="select_3" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">
						 <option value="">请选择管理部门</option>
						 <c:forEach var="item" items="${orgList}">
							 <option value="${item.id}">${item.orgName}</option>
						 </c:forEach>
					 </select>
				 </div>
				 <div class="layui-input-inline" id="remoteMonitorFlagSelect" style="width: 200px">
					 <select id="remoteMonitorFlag" name="remoteMonitorFlag" xm-select="select_2" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">
						 <option value="">是否远程监视</option>
						 <option value="1">是</option>
						 <option value="0">否</option>
					 </select>
				 </div>
				<div class="layui-input-inline" style="width: 100px; margin-top: 3px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" style="display: flex"
						onclick="machineSearchList()">
						<i class="layui-icon layui-icon-search"
							style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
				</div>

			</div>
		</div>
	</div>

	<table id="machine" lay-filter="loadMachineInfo"></table>

</div>
<script type="text/html" id="machineOptBar">
<div class="layui-btn-group optBar">
	<%--<shiro:hasPermission name="machine_edit">--%>
        <button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="check_list" style="">点检履历</button>
	<%--</shiro:hasPermission>--%>
	<%--<shiro:hasPermission name="machine_edit">--%>
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
	<%--</shiro:hasPermission>
	<shiro:hasPermission name="machine_del">--%>
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style=""><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
	<%--</shiro:hasPermission>--%>
</div>
</script>

<script type="text/html" id="remoteMonitorFlagl">
	{{#  if(d.remoteMonitorFlag == 1){ }}
	是
	{{#  } else if(d.remoteMonitorFlag == 0){ }}
	否
	{{#  } }}
</script>
<script type="text/html" id="checkFlagl">
	{{#  if(d.checkFlag == 1){ }}
	是
	{{#  } else if(d.checkFlag == 0){ }}
	否
	{{#  } }}
</script>
<script type="text/html" id="maintainFlagl">
	{{#  if(d.maintainFlag == 1){ }}
	是
	{{#  } else if(d.maintainFlag == 0){ }}
	否
	{{#  } }}
</script>
<script type="text/html" id="onlineStatusl">
	{{#  if(d.onlineStatus == 1){ }}
	<span style="color: #00B83F; font-size: 28px;">●</span>
	{{#  } else if(d.onlineStatus == 0){ }}
	<span style="color: #cecece; font-size: 28px;">●</span>
	{{#  } }}
</script>

<script type="text/javascript">
	$(function() {
		$('[placeholder]').placeholder();
	});

	var _selObj;
	table.render({
		elem : '#machine',
		id : 'machine',
		cols : [ [ {type : 'checkbox', width : '3%'}
		// , { align : 'center', field : 'enterpriseName', title : '所属企业', minWidth : 120, width : '10%'}
		, { align : 'center', field : 'machineNo', title : '设备编号', minWidth : 80}
		, { align : 'center', field : 'machineName', title : '设备名称', minWidth : 120, width : '8%'}
		, {	align : 'center', field : 'machineTypeName',title : '设备类型',minWidth : 120,width: '6%'}
		, {	align : 'center', field : 'machineModel',title : '设备型号',minWidth : 120,width: '6%'}
		, {	align : 'center', field : 'manufacotry',title : '生产厂商',minWidth : 120,width: '8%'}
		, {	align : 'center', field : 'location',title : '所属位置',minWidth : 120,width: '8%'}
		, {	align : 'center', field : 'orgName',title : '管理部门',minWidth : 120,width: '8%'}
		, {	align : 'center', field : 'remoteMonitorFlag',title : '是否远程监控',minWidth : 140,width: '7%',templet:'#remoteMonitorFlagl'}
		, { align : 'center', field : 'deviceName', title: '监听设备', minWidth : 120, width: '10%'}
		, {	align : 'center', field : 'checkFlag',title : '是否点检',minWidth : 100,width: '6%',templet:'#checkFlagl'}
		, {	align : 'center', field : 'maintainFlag',title : '是否保养',minWidth : 120,width: '6%',templet:'#maintainFlagl'}
		, { align : 'center', field : 'onlineStatus', title : '通讯状态', minWidth : 120, width : '6%',templet:'#onlineStatusl'}
		,{
			align : 'center',
			title : '操作',
			minWidth : 220,
			// width : '14%',
			toolbar : '#machineOptBar',fixed: 'right'
		}] ],
		url : 'dataDesources/machineManager/machineList.do',
		page : pageParam,
		limit : 50,
		height : 'full-' + getTableFullHeight(),
		done : function(res, curr, count) {
			//无操作提示项
			emptyOptBar("machine");
			resizeTable('machine');
			closeLoading();
			var data = res.data;
			if (data.length == 0)
				emptyList("machine");

			console.log(res)
		}
	});

	//监听工具条
	table.on('tool(loadMachineInfo)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'check_list') {
			toCheckListPage(data.id)
		} else if (layEvent === 'del') {
			var checkStatus = table.checkStatus('machine');
			machineDelPage(obj);
		} else if (layEvent === 'edit') {
			machineEditPage(data.id);
		}
	});


</script>