<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8"src="${ctx}/js/remoteMonitor/alarmInfo.js"></script>
<style>
#alarmInfo-content a:hover {
	color: #039;
	text-decoration: underline
}
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

<div id="alarmInfo-content" class="contain-content" style="bottom: 0;">

<div class="layui-form right-search" style="box-shadow: none;">
		<div class="layui-form-item">
			<div class="layui-inline">
					<div class="layui-input-inline">
					<select id="enterpriseId" name="enterpriseId" onchange="changeMachineList()" ${enterpriseInfoListSize==1?'disabled="disabled"':''} style="display: inline-block;width:140px;height: 18px">
                       <c:forEach var="item" items="${enterpriseInfoList}">
                             <option value="${item.id}">${item.enterpriseName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline" id="machineSelect">
					<select id="machineId" name="machineId" style="display: inline-block;width:140px;height: 18px">
                       <c:forEach var="item" items="${machineList}">
                             <option value="${item.id}">${item.machineName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline">
					<select id="alarmType" name="alarmType" style="display: inline-block;width:140px;height: 18px">
                            <option value=""></option>
                            <option value="0">预警信息</option>
                            <option value="1">故障信息</option>
                            <option value="2">阈值报警信息</option>
		            </select>
				</div>
				<div class="layui-input-inline" style="margin-right: 0">
					<input type="text" id="query_startTime" name="query_startTime" class="Wdate" value="<fmt:formatDate value="${lastHour}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px" class="layui-input search-maxlenght">
				</div>
				<label class="layui-form-label" style="padding: 5px">--</label>
				<div class="layui-input-inline">
					<input type="text" id="query_endTime" name="query_endTime" class="Wdate" value="<fmt:formatDate value="${defaultTime}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px" class="layui-input search-maxlenght">
				</div>


				<div class="layui-input-inline" style="width: 200px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="alarmInfoSearchList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
						<button class="layui-btn layui-btn-danger" id="exportAlarmInfoList">
						<i class="layui-icon layui-icon-export" style="font-size: 18px; color: #FFFFFF;"></i> 导出
					</button>
				</div>
			</div>
		</div>
	</div>
	</div>

	<table id="alarmInfo" lay-filter="lalarmInfo"></table>

</div>

<script type="text/html" id="alarmInfoTimeTpl">
  {{ getFormatDate(d.createTime) }}
</script>

<script type="text/html" id="recoveryTimeTpl">

{{#  if(d.recoveryTime == null){ }}
       --
 {{#  } else{ }}
   	  {{ getFormatDate(d.recoveryTime) }}
  {{#  } }}
</script>


<script type="text/html" id="alarmTypeTpl">
  {{#  if(d.alarmType == '0'){ }}
   	<span style="color:#F2CC0C">预警</span>
  {{#  } else if(d.alarmType == '1'){ }}
   	<span style="color:#F2495C">故障</span>
  {{#  } else if(d.alarmType == '2'){ }}
   	<span style="color:#F2495C">阈值报警</span>
  {{#  } }}


</script>

<script type="text/html" id="alarmContentTpl">
<lable style="float:left">{{d.alarmContent}}</lable>
</script>

<script type="text/javascript">
	$(function() {
		$('[placeholder]').placeholder();
	});

  var storage_enterpriseId = sessionStorage.getItem("enterpriseId");
	var storage_machineId = sessionStorage.getItem("machineId");
	//获取完先移除当前的缓存
// 	sessionStorage.removeItem("enterpriseId")
	sessionStorage.removeItem("machineId")
	 if(storage_enterpriseId!=null){
		 $("#alarmInfo-content [name='enterpriseId']").val(storage_enterpriseId);
		 changeMachineList(storage_machineId);
	 }

	var enterpriseId = $("#alarmInfo-content [name='enterpriseId']").val();
	var machineId = $("#alarmInfo-content [name='machineId']").val();
	 if(storage_enterpriseId!=null){
		 enterpriseId = storage_enterpriseId;
	 }
	 if(storage_machineId!=null){
		 machineId = storage_machineId;
	 }

	 var _selObj;

	 setTimeout(()=>{
		table.render({
			elem : '#alarmInfo',
			id : 'alarmInfo',
			cols : [ [
			{
				align : 'center',
				field : 'createTime',
				title : '报警时间',
				minWidth : 150,
				width : '20%',
				templet:'#alarmInfoTimeTpl'
			},{
				align : 'center',
				field : 'factorCode',
				title : '监测因子',
				minWidth : 150,
				width : '10%',
			},{
				align : 'center',
				field : 'factorName',
				title : '监测因子名称',
				minWidth : 150,
				width : '10%',
			}, {
				align : 'center',
				field : 'factorValue',
				title : '数值',
				minWidth : 150,
				width : '10%',
			}, {
				align : 'center',
				field : 'alarmContent',
				title : '报警内容',
				minWidth : 150,
				width : '20%',
				templet:'#alarmContentTpl'
			}, {
				align : 'center',
				field : 'alarmType',
				title : '报警类型',
				minWidth : 150,
				width : '10%',
				templet:'#alarmTypeTpl'
			}, {
				align : 'center',
				field : 'recoveryTime',
				title : '报警恢复时间',
				minWidth : 150,
				width : '20%',
				templet:'#recoveryTimeTpl'
			}
			] ],
			url : 'remoteMonitor/alarmInfo/alarmInfoList.do',
			page : pageParam,
			where : {
				enterpriseId : enterpriseId,
				machineId : machineId,
				query_startTime : $('#alarmInfo-content [name="query_startTime"]').val(),
				query_endTime : $('#alarmInfo-content [name="query_endTime"]').val(),
			},
			limit : 10,
			height : 'full-' + getTableFullHeight(),
			done : function(res, curr, count) {
				//无操作提示项
				emptyOptBar("alarmInfo");
				resizeTable('alarmInfo');
				closeLoading();
				var data = res.data;
				if (data.length == 0)
					emptyList("alarmInfo");
			}
		});
	},20);

	table.on('tool(lalarmInfo)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
	});

	$("#alarmInfo-content select[name='enterpriseId']").SumoSelect({
		placeholder : "请选择企业",
		search : true,
		searchText : '请选择企业',
		noMatch : '没有匹配 "{0}" 的项',
		csvDispCount : 2,
		captionFormat : '选中 {0} 项',
		okCancelInMulti : true,
		selectAll : false,
		locale : [ '确定', '取消' ]
	});

	$("#alarmInfo-content select[name='machineId']").SumoSelect({
		placeholder : "请选择设备",
		search : true,
		searchText : '请选择设备',
		noMatch : '没有匹配 "{0}" 的项',
		csvDispCount : 2,
		captionFormat : '选中 {0} 项',
		okCancelInMulti : true,
		selectAll : false,
		locale : [ '确定', '取消' ]
	});

	$("#alarmInfo-content select[name='alarmType']").SumoSelect({
		placeholder : "请选择报警类型",
		search : true,
		searchText : '请选择报警类型',
		noMatch : '没有匹配 "{0}" 的项',
		csvDispCount : 2,
		captionFormat : '选中 {0} 项',
		okCancelInMulti : true,
		selectAll : false,
		locale : [ '确定', '取消' ]
	});

	$("#exportAlarmInfoList").click(function(){
		openLoading();
		var enterpriseId = $("#alarmInfo-content [name='enterpriseId']").val();
		var machineId = $("#alarmInfo-content [name='machineId']").val();
		var alarmType = $("#alarmInfo-content [name='alarmType']").val();
		var query_startTime = $("#alarmInfo-content [name='query_startTime']").val();
		var query_endTime = $("#alarmInfo-content [name='query_endTime']").val();
		var params = {};
		params['enterpriseId'] = enterpriseId;
		params['machineId'] = machineId;
		params['alarmType'] = alarmType;
		params['query_startTime'] = query_startTime;
		params['query_endTime'] = query_endTime;
	    window.location.href="remoteMonitor/alarmInfo/exportAlarmInfoList.do?enterpriseId=" + enterpriseId + "&query_startTime=" + query_startTime + "&query_endTime=" + query_endTime+"&machineId=" + machineId+"&alarmType=" + alarmType;
	    closeLoading();

	});

</script>
