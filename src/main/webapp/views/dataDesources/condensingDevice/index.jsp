<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/dataDesources/condensingDevice.js"></script>
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
</style>

<div id="condensingDevice-content" class="contain-content"
	style="bottom: 0;">

	<div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<shiro:hasPermission name="condensing_device_add">
						<button class="layui-btn layui-btn-light-blue"
							onclick="condensingDeviceAddPage()">
							<i class="layui-icon layui-icon-add-1"></i>添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="condensing_device_del">
						<button class="layui-btn layui-btn-pink"
							onclick="condensingDeviceDelPage()">
							<i class="layui-icon layui-icon-delete"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
			</div>
			 <div class="layui-inline">
				<div class="layui-input-inline"  style="width: 200px">
					<select id="enterpriseId" name="deviceCode"  xm-select="select7_1" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="200px">
                       <option value="">请选择企业名称</option>
                       <c:forEach var="item" items="${enterpriseInfoList}">
                             <option value="${item.deviceCode}">${item.enterpriseName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline" id="condensingDeviceNumSelect" style="width: 200px">
					<select id="machineType" name="condensingDeviceNum" xm-select="select7_2" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">
                       <option value="">请选择设备类型</option>
                       <c:forEach var="item" items="${condensingDeviceList}">
                             <option value="${item.machineType}">${item.machineName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline" style="width: 100px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue"
						onclick="condensingDeviceSearchList()">
						<i class="layui-icon layui-icon-search"
							style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
				</div>

			</div>
		</div>
	</div>

	<table id="condensingDevice" lay-filter="lcondensingDevice"></table>

</div>
<script type="text/html" id="condensingDeviceOptBar">
<div class="layui-btn-group optBar">
	<shiro:hasPermission name="condensing_device_edit">
<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="condensing_device_del">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style=""><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
	</shiro:hasPermission>
</div>
</script>

<script type="text/html" id="condensingDeviceTimeTpl"> 
  {{ getFormatDate(d.modifyTime) }}
</script>

<script type="text/javascript">
	$(function() {
		$('[placeholder]').placeholder();
	})

	var _selObj;
	table.render({
		elem : '#condensingDevice',
		id : 'condensingDevice',
		cols : [ [ {type : 'checkbox', width : '5%'}
		, { align : 'center', field : 'enterpriseName', title : '所属企业', minWidth : 150, width : '10%'}
		, { align : 'center', field : 'machineNo', title : '设备编号', minWidth : 150, width : '10%'}
		, { align : 'center', field : 'machineName', title : '设备名称', minWidth : 150, width : '10%'}
		, {	align : 'center', field : 'itemName',title : '设备类型',minWidth : 150,width: '10%'}
		, {	align : 'center', field : 'machineModel',title : '设备型号',minWidth : 150,width: '10%'}
		, { align : 'center', field : 'deviceCode', title: '监听设备', minWidth : 150, width: '10%'}
		,  {
			align : 'center',
			field : 'remark',
			title : '备注',
			minWidth : 150,
			width : '20%'
		}, {
			align : 'center',
			title : '操作',
			minWidth : 240,
			width : '15%',
			toolbar : '#condensingDeviceOptBar'
		}] ],
		url : 'dataDesources/condensingDevice/condensingDeviceList.do',
		page : pageParam,
		limit : 50,
		height : 'full-' + getTableFullHeight(),
		done : function(res, curr, count) {
			//无操作提示项
			emptyOptBar("condensingDevice");
			resizeTable('condensingDevice');
			closeLoading();
			var data = res.data;
			if (data.length == 0)
				emptyList("condensingDevice");
		}
	});

	//监听工具条
	table.on('tool(lcondensingDevice)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			var checkStatus = table.checkStatus('condensingDevice');
			condensingDeviceDelPage(obj);
		} else if (layEvent === 'edit') {
			condensingDeviceEditPage(data.id);
		}
	});
	
	var formSelects = layui.formSelects;
	formSelects.closed('select7_1', function(id){
		  var deviceCodes=formSelects.value('select7_1','val');
		  var deviceCode=deviceCodes.toString();
			var params = {};
			params['deviceCode'] = deviceCode;
			$.post("remoteMonitor/operationTrend/changeCondensingDevice.do",params,function(data){
				var condensingDeviceList = JSON.parse(JSON.parse(data));
				var html = '<select id="machineNo" name="machineNo"  xm-select="select7_2" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">';
				html +='<option value=""></option>'
				for(i=0;i<condensingDeviceList.length;i++){
					html += '<option value="'+condensingDeviceList[i].machineNo+'" >'+condensingDeviceList[i].machineName+'</option>';
				}
				html += '</select>';
				$("#condensingDeviceNumSelect").html(html);
				formSelects.render('select7_2');
				
			});
			
	})

</script>