<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/remoteMonitor/condensingDeviceMonitor.js"></script>

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
					<select id="enterpriseId" name="enterpriseId" onchange="changeCondensingDeviceList()" ${enterpriseInfoListSize==1?'disabled="disabled"':''} style="display: inline-block;width:140px;height: 18px">
                       <c:forEach var="item" items="${enterpriseInfoList}">
                             <option value="${item.id}">${item.enterpriseName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline" id="condensingDeviceNumSelect">
					<select id="condensingDeviceNum" name="condensingDeviceNum" style="display: inline-block;width:140px;height: 18px">
                       <option value=""></option>
                       <c:forEach var="item" items="${condensingDeviceList}">
                             <option value="${item.condensingDeviceNum}">${item.condensingDeviceName}</option>
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

<script type="text/html" id="runtimeTpl">

{{ getRuntimeStr(d.runtime) }}

</script>
<!-- {{ getFormatDate(d.createTime) }} -->
<script type="text/html" id="runningStateTpl">
  {{#  if(d.switchState == '0'){ }}
      <span style="color:#C3C3C3">停机</span>
  {{#  } else { }}
      {{#  if(d.alarmState == '0'){ }}
   	     <span style="color:#47B347">运行</span>
      {{#  } else if(d.alarmState == '1'){ }}
   	     <span style="color:#F2CC0C">预警</span>
      {{#  } else if(d.alarmState == '2'){ }}
   	    <span style="color:#F2495C">故障</span>
  {{#  } }}
  {{#  } }}



</script>


<script type="text/html" id="condensingDeviceDateBar">
<button class="layui-btn layui-btn-sm layui-btn-slightgreen" onclick="toOperationTrend('{{ d.enterpriseId }}','{{ d.condensingDeviceNum }}')">运行趋势</button>
<button class="layui-btn layui-btn-sm layui-btn-danger" onclick="toAlarmInfo('{{ d.enterpriseId }}','{{ d.condensingDeviceNum }}')">报警查询</button>
<button class="layui-btn layui-btn-sm layui-btn-light-blue" onclick="toDetailedState('{{ d.enterpriseId }}','{{ d.condensingDeviceNum }}')">详细查询</button>
<button class="layui-btn layui-btn-sm layui-btn-lavender" onclick="toDayOperationTrend('{{ d.enterpriseId }}','{{ d.condensingDeviceNum }}')">按天趋势</button>
</script>

<script type="text/javascript">
	$(function() {
		$('[placeholder]').placeholder();
	})
   var storage_enterpriseId = sessionStorage.getItem("enterpriseId");
	//获取完先移除当前的缓存
// 	sessionStorage.removeItem("enterpriseId")
	 if(storage_enterpriseId!=null){
		 $("#condensingDevice-content [name='enterpriseId']").val(storage_enterpriseId);
		 changeCondensingDeviceList()
	 }
	var _selObj;
	table.render({
		elem : '#condensingDevice',
		id : 'condensingDevice',
		cols : [ [ {
			type : 'checkbox',
			width : '5%'
		}, {
			align : 'center',
			field : 'enterpriseName',
			title : '所属企业',
			minWidth : 150,
			width : '20%'
		},{
			align : 'center',
			field : 'condensingDeviceNum',
			title : '机组编号',
			minWidth : 150,
			width : '10%'
		}, {
			align : 'center',
			field : 'condensingDeviceName',
			title : '机组名称',
			minWidth : 150,
			width : '15%'
		}, {
			align : 'center',
			field : 'runtime',
			title : '运行时间',
			minWidth : 150,
			width : '10%',
			templet:'#runtimeTpl'
		}, {
			align : 'center',
			field : 'runningState',
			title : '状态',
			minWidth : 150,
			width : '10%',
			templet:'#runningStateTpl'
		},{
			align : 'center',
			title : '数据查看',
			minWidth : 240,
			width : '30%',
			toolbar : '#condensingDeviceDateBar'
		} ] ],
		url : 'remoteMonitor/condensingDeviceMonitor/condensingDeviceList.do',
		page : pageParam,
		where : {
			enterpriseId : $('#condensingDevice-content [name="enterpriseId"]').val(),
			condensingDeviceNum : $('#condensingDevice-content [name="condensingDeviceNum"]').val(),
		},
		limit : 10,
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

	$("#condensingDevice-content select[name='enterpriseId']").SumoSelect({
	    placeholder:"请选择企业",
	    search: true,
	    searchText: '请选择企业',
	    noMatch: '没有匹配 "{0}" 的项' ,
	    csvDispCount: 2,
	    captionFormat:'选中 {0} 项',
	    okCancelInMulti:true,
	    selectAll:false,
	    locale : ['确定', '取消']
	});

	$("#condensingDevice-content select[name='condensingDeviceNum']").SumoSelect({
	    placeholder:"请选择机组名称",
	    search: true,
	    searchText: '请选择机组名称',
	    noMatch: '没有匹配 "{0}" 的项' ,
	    csvDispCount: 2,
	    captionFormat:'选中 {0} 项',
	    okCancelInMulti:true,
	    selectAll:false,
	    locale : ['确定', '取消']
	});



	function toOperationTrend(enterpriseId,condensingDeviceNum){
		sessionStorage.setItem("enterpriseId", enterpriseId);
		sessionStorage.setItem("condensingDeviceNum", condensingDeviceNum);
// 		var $p = $(".left .layui-nav .layui-nav-item a[onclick*='addMainTab']").eq(4);
// 		$p.click();
        var clickDocument = $("#operation_trend");
        clickDocument.click();
	}
	function toAlarmInfo(enterpriseId,condensingDeviceNum){
		sessionStorage.setItem("enterpriseId", enterpriseId);
		sessionStorage.setItem("condensingDeviceNum", condensingDeviceNum);
// 		var $p = $(".left .layui-nav .layui-nav-item a[onclick*='addMainTab']").eq(4);
// 		$p.click();
		var clickDocument = $("#alarm_info");
        clickDocument.click();
	}
	function toDetailedState(enterpriseId,condensingDeviceNum){
		sessionStorage.setItem("enterpriseId", enterpriseId);
		sessionStorage.setItem("condensingDeviceNum", condensingDeviceNum);
// 		var $p = $(".left .layui-nav .layui-nav-item a[onclick*='addMainTab']").eq(5);
// 		$p.click();
		var clickDocument = $("#detailed_state");
        clickDocument.click();
	}
	function toDayOperationTrend(enterpriseId,condensingDeviceNum){
		sessionStorage.setItem("enterpriseId", enterpriseId);
		sessionStorage.setItem("condensingDeviceNum", condensingDeviceNum);
// 		var $p = $(".left .layui-nav .layui-nav-item a[onclick*='addMainTab']").eq(6);
// 		$p.click();
		var clickDocument = $("#day_operation_trend");
        clickDocument.click();
	}

	function getRuntimeStr(runtime){
// 		var day = parseInt(runtime / 24);
// 		var hour = runtime % 24;
// 		var runtimeStr = day + "天" + hour + "小时";
		var runtimeStr = runtime + "小时";
		return runtimeStr;
	}
</script>
