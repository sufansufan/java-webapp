<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/remoteMonitor/maintenanceRecord.js"></script>
	<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css" />
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
	
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

<div id="maintenanceRecord-content" class="contain-content"
	style="bottom: 0;">

	<div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<shiro:hasPermission name="maintenance_record_add">
						<button class="layui-btn layui-btn-light-blue"
							onclick="maintenanceRecordAddPage()">
							<i class="layui-icon layui-icon-add-1"></i>添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="maintenance_record_del">
						<button class="layui-btn layui-btn-pink"
							onclick="maintenanceRecordDelPage()">
							<i class="layui-icon layui-icon-delete"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
			</div>
             <div class="layui-inline"> 
             <div class="layui-input-inline"  style="width: 200px" id="enterpriseInfoSelect">
					<select id="deviceCode" name="deviceCode" ${enterpriseInfoListSize==1?'disabled="disabled"':''}  xm-select="select7_1"  xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="200px">
                       <option value="">请选择企业名称</option>
                       <c:forEach var="item" varStatus="vs" items="${enterpriseInfoList}">
                             <option value="${item.deviceCode}" ${vs.index==0?'selected="selected"':''}>${item.enterpriseName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline" id="condensingDeviceNumSelect" style="width: 140px">
					<select id="condensingDeviceNum" name="condensingDeviceNum" xm-select="select7_2" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">
                       <option value="">请选择机组名称</option>
                       <c:forEach var="item" items="${condensingDeviceList}">
                             <option value="${item.condensingDeviceNum}">${item.condensingDeviceName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline" style="width: 100px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue"
						onclick="maintenanceRecordSearchList()">
						<i class="layui-icon layui-icon-search"
							style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
				</div>

			</div>
		</div>
	</div>

	<table id="maintenanceRecord" lay-filter="lmaintenanceRecord"></table>

</div>
<script type="text/html" id="maintenanceRecordOptBar">
<div class="layui-btn-group optBar">
	<shiro:hasPermission name="maintenance_record_edit">
<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="maintenance_record_del">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style=""><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
	</shiro:hasPermission>
</div>
</script>

<script type="text/html" id="maintenanceTimeTpl"> 
  {{ getFormatDate(d.maintenanceTime) }}
</script>


<script type="text/javascript">
	$(function() {
		$('[placeholder]').placeholder();
	})
	

	var enterpriseInfoList = eval("("+'${enterpriseInfoListJson}'+")");
	var enterpriseInfoListSize = '${enterpriseInfoListSize}';
	console.log(enterpriseInfoListSize)

	 var storage_deviceCode = sessionStorage.getItem("deviceCode");
	 if(storage_deviceCode!=null){
		 $("#maintenanceRecord-content [name='deviceCode']").val(storage_deviceCode);
		 changeEnterpriseInfoList(storage_deviceCode);
	 }
	
	var deviceCode = $("#maintenanceRecord-content [name='deviceCode']").val();
	 if(storage_deviceCode!=null){
		 deviceCode = storage_deviceCode;
	 }
	

	var _selObj;
	table.render({
		elem : '#maintenanceRecord',
		id : 'maintenanceRecord',
		cols : [ [ {
			type : 'checkbox',
			width : '5%'
		}, {
			align : 'center',
			field : 'enterpriseName',
			title : '所属企业',
			minWidth : 150,
			width : '12.5%'
		}, {
			align : 'center',
			field : 'condensingDeviceNum',
			title : '机组编号',
			minWidth : 150,
			width : '7.5%'
		}, {
			align : 'center',
			field : 'maintenanceTime',
			title : '维修时间',
			minWidth : 150,
			width : '12.5%',
			templet : '#maintenanceTimeTpl'
		}, {
			align : 'center',
			field : 'maintenanceDuration',
			title : '维修时长',
			minWidth : 150,
			width : '10%'
		}, {
			align : 'center',
			field : 'maintenanceContent',
			title : '维修内容',
			minWidth : 150,
			width : '22.5%'
		},  {
			align : 'center',
			field : 'remark',
			title : '备注',
			minWidth : 150,
			width : '15%'
		}, {
			align : 'center',
			title : '操作',
			minWidth : 240,
			width : '15%',
			toolbar : '#maintenanceRecordOptBar'
		}] ],
		url : 'remoteMonitor/maintenanceRecord/maintenanceRecordList.do',
		page : pageParam,
		where : {
			deviceCode : deviceCode,
		},
		limit : 50,
		height : 'full-' + getTableFullHeight(),
		done : function(res, curr, count) {
			//无操作提示项
			emptyOptBar("maintenanceRecord");
			resizeTable('maintenanceRecord');
			closeLoading();
			var data = res.data;
			if (data.length == 0)
				emptyList("maintenanceRecord");
		}
	});

	//监听工具条
	table.on('tool(lmaintenanceRecord)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			var checkStatus = table.checkStatus('maintenanceRecord');
			maintenanceRecordDelPage(obj);
		} else if (layEvent === 'edit') {
			maintenanceRecordEditPage(data.id);
		}
	});
	

	var formSelects = layui.formSelects;
	formSelects.closed('select7_1', function(id){
		  var deviceCodes=formSelects.value('select7_1','val');
		  var deviceCode=deviceCodes.toString();
		  sessionStorage.setItem("deviceCode", deviceCode);
			var params = {};
			params['deviceCode'] = deviceCode;
			$.post("remoteMonitor/operationTrend/changeCondensingDevice.do",params,function(data){
				var condensingDeviceList = JSON.parse(JSON.parse(data));
				var html = '<select id="condensingDeviceNum" name="condensingDeviceNum"  xm-select="select7_2" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">';
				html +='<option value=""></option>'
				for(i=0;i<condensingDeviceList.length;i++){
					html += '<option value="'+condensingDeviceList[i].condensingDeviceNum+'" >'+condensingDeviceList[i].condensingDeviceName+'</option>';
				}
				html += '</select>';
				$("#condensingDeviceNumSelect").html(html);
				formSelects.render('select7_2');
				
			});
			
	})
	
	
	function changeEnterpriseInfoList(storage_deviceCode){
		var enterpriseHtml ="";
		if(enterpriseInfoListSize==1){
			enterpriseHtml = '<select id="deviceCode" name="deviceCode" disabled="disabled" xm-select="select7_1" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">';
		}else{
			enterpriseHtml = '<select id="deviceCode" name="deviceCode" xm-select="select7_1" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">';
		}
		
		enterpriseHtml +='<option value=""></option>'
		for(i=0;i<enterpriseInfoList.length;i++){
			if(enterpriseInfoList[i].deviceCode==storage_deviceCode){
				enterpriseHtml += '<option value="'+enterpriseInfoList[i].deviceCode+'" selected="selected">'+enterpriseInfoList[i].enterpriseName+'</option>';
			}else{
				enterpriseHtml += '<option value="'+enterpriseInfoList[i].deviceCode+'">'+enterpriseInfoList[i].enterpriseName+'</option>';
			}
			
		}
		$("#enterpriseInfoSelect").html(enterpriseHtml);
		$("#maintenanceRecord-content [name='deviceCode']").val(storage_deviceCode);
		layui.formSelects.value('select7_1','all')
		layui.formSelects.render('select7_1');
		
		var params = {};
		params['deviceCode'] = storage_deviceCode;
		$.post("remoteMonitor/operationTrend/changeCondensingDevice.do",params,function(data){
			var condensingDeviceList = JSON.parse(JSON.parse(data));
			var html = '<select id="condensingDeviceNum" name="condensingDeviceNum"  xm-select="select7_2" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">';
			html +='<option value=""></option>'
			for(i=0;i<condensingDeviceList.length;i++){
				html += '<option value="'+condensingDeviceList[i].condensingDeviceNum+'" >'+condensingDeviceList[i].condensingDeviceName+'</option>';
			}
			html += '</select>';
			$("#condensingDeviceNumSelect").html(html);
			formSelects.render('select7_2');
			
		});
	}
	

</script>