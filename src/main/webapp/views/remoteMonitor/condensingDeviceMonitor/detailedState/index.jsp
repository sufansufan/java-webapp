<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/remoteMonitor/detailedState.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/UrlEncode.js"></script>

<style>
.SumoSelect > .optWrapper > .options {
    max-height: 140px;
}
#monitorFactorDiv .layui-form-select{
display: none
}
.SumoSelect > .CaptionCont > span {
    height: 18px;
    line-height: 18px

</style>

<style>
.device {
    position: absolute;
    left: 0px;
    top: 55px;
    right: 0px;
    bottom: 30px;
    overflow: hidden;
    overflow-y: auto;
}
table.list-table {
    float: left;
    border-collapse: collapse;
}
table {
    width: 100%;
}
table.list-table thead {
    background-color: #e1eaf0;
    border-top: 1px solid #d0d9de;
    border-left: 1px solid #d0d9de;
}
table.list-table {
    float: left;
    border-collapse: collapse;
}
table.list-table thead tr th, table.list-table tbody tr td {
    font-size: 12px;
    height: 35px;
    border-bottom: 1px solid #d0d9de;
    border-right: 1px solid #d0d9de;
}
table.list-table tbody {
    border-top: 1px solid #d0d9de;
    border-left: 1px solid #d0d9de;
}
</style>
<div class="detailedState-content" style="bottom: 0;">

	<div id="detailedState-content" class="layui-form right-search" style="box-shadow: none;;position:fixed;z-index:9999999;width: 100%;top:121px">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<select id="enterpriseId" name="enterpriseId" onchange="changeCondensingDeviceList()" ${enterpriseInfoListSize==1?'disabled="disabled"':''} style="display: inline-block;width:140px;height: 18px">
                       <c:forEach var="item" items="${enterpriseInfoList}">
                             <option value="${item.id}">${item.enterpriseName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline" id="machineSelect">
					<select id="machineId" name="machineId" onchange="changeDetailedStateList()" style="display: inline-block;width:140px;height: 18px">
                       <c:forEach var="item" items="${machineList}">
                             <option value="${item.id}">${item.machineName}</option>
			           </c:forEach>
		            </select>
				</div>

				<div class="layui-input-inline" id="monitorFactorSelect">
					<select id="monitorFactors" name="monitorFactors" style="display: inline-block;width:140px;height: 18px" multiple="multiple">
<%--                      <c:forEach var="item" items="${monitorFactorsNameArr}"> --%>
<%-- 			           <option value="${item}" ${item==monitorFactorsNameArr[0]?'selected="selected"':''}>${item}</option> --%>
<%-- 			         </c:forEach> --%>
		            </select>
				</div>
				<div class="layui-input-inline" style="margin-right: 0">
					<input type="text" id="query_startTime" name="query_startTime" class="Wdate" value="<fmt:formatDate value="${defaultTime}" pattern="yyyy-MM-dd 00:00:00" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px" class="layui-input search-maxlenght">
<%-- 					<input type="text" id="query_startTime" name="query_startTime" class="Wdate" value="<fmt:formatDate value="${lastHour}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px" class="layui-input search-maxlenght"> --%>
				</div>
				<label class="layui-form-label" style="padding: 5px">--</label>
				<div class="layui-input-inline">
					<input type="text" id="query_endTime" name="query_endTime" class="Wdate" value="<fmt:formatDate value="${defaultTime}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px" class="layui-input search-maxlenght">
				</div>


				<div class="layui-input-inline" style="width: 500px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="searchDetailedStateList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
					<button class="layui-btn layui-btn-light-blue" onclick="chooseDate(1)"> 当日</button>
					<button class="layui-btn layui-btn-light-blue" onclick="chooseDate(2)"> 1个月内</button>
					<button class="layui-btn layui-btn-light-blue" onclick="chooseDate(3)"> 3个月内</button>
					<button class="layui-btn layui-btn-danger" id="exportDetailedStateList">
						<i class="layui-icon layui-icon-export" style="font-size: 18px; color: #FFFFFF;"></i> 导出
					</button>
				</div>
			</div>
		</div>
	</div>
	<div style="height: 100%;margin-top: 55px">
	<div style="height: 450px; background-color: #fff; box-shadow: 5px 5px 5px #888888; border: 1px solid #c2c2c2; border-radius: 3px;margin: 0px 10px 15px 10px;">
	<div id="detailedStateChart" style="height: 450px;width: 100%;"> </div>
	</div>

    <div style="height: 520px; background-color: #fff; box-shadow: 5px 5px 5px #888888; border: 1px solid #c2c2c2; border-radius: 3px;margin: 10px">
	<div id="detailedStateContent" style="width: 100%;"></div>
	</div>
	</div>


</div>

<script type="text/javascript">

$(function(){
	$('[placeholder]').placeholder();
	$("#detailedState-content select[name='enterpriseId']").SumoSelect({
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

	$("#detailedState-content select[name='machineId']").SumoSelect({
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

	$("#detailedState-content select[name='monitorFactors']").SumoSelect({
	    placeholder:"请选择监测因子",
	    search: true,
	    searchText: '请选择监测因子',
	    noMatch: '没有匹配 "{0}" 的项' ,
	    csvDispCount: 3,
	    captionFormat:'选中 {0} 项',
	    okCancelInMulti:true,
	    selectAll:true,
	    captionFormatAllSelected: "全选",
	    locale : ['确定', '取消', '全选']
	});
})

$("#exportDetailedStateList").click(function(){
	openLoading();
	var enterpriseId = $("#detailedState-content [name='enterpriseId']").val();
	var machineId = $("#detailedState-content [name='machineId']").val();
	var monitorFactors = $("#detailedState-content [name='monitorFactors']").val();
	var query_startTime = $("#detailedState-content [name='query_startTime']").val();
	var query_endTime = $("#detailedState-content [name='query_endTime']").val();

    var content = urlencode(monitorFactors.toString())

    window.location.href="remoteMonitor/detailedState/exportDetailedStateList.do?enterpriseId=" + enterpriseId + "&query_startTime=" + query_startTime + "&query_endTime=" + query_endTime+"&machineId=" + machineId+"&monitorFactors=" + content;
    closeLoading();

// 	var params = {};
// 	params['enterpriseId'] = enterpriseId;
// 	params['machineId'] = machineId;
// 	if(monitorFactors!=null){
// 		params['monitorFactors'] = monitorFactors.toString();
// 	}else{
// 		params['monitorFactors'] = "";
// 	}
// 	params['query_startTime'] = query_startTime;
// 	params['query_endTime'] = query_endTime;
// 	$.post("remoteMonitor/detailedState/exportHistoryRecordList.do",params,function(data){
// 		closeLoading();
// 		console.log(data)
// 		var result = JSON.parse(data);

// // 		if(result.success){
// // 			layer.alert("导出成功")
// // 		}else{
// // 			layer.alert("导出失败")
// // 		}
// 	});
});


</script>
