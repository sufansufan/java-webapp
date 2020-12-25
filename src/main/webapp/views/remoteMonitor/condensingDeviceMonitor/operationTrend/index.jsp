<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/remoteMonitor/operationTrend.js"></script>
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
}
table tbody {
    display:block;
    height:303px;
    overflow-y:scroll;
    overflow-x:hidden;
}


table thead, tbody tr {
    display:table;
    width:100%;
    table-layout:fixed;
}

table thead {
    width: calc( 100% - 1.2em )
}
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

table.list-table tr th, table.list-table tr td {
	margin: 0;
	text-align: center;
}

.list-page {
	width: 100%;
	float: left;
	padding-top: 15px;
	padding-bottom: 20px;
	text-align: center;
	color: #adadad;
}
</style>

<div id="operationTrend-content" class="layui-form right-search" style="box-shadow: none;position:fixed;z-index:9999999;width: 100%">
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
					<select id="condensingDeviceNum" name="condensingDeviceNum" onchange="searchHistoryRecordList()" style="display: inline-block;width:140px;height: 18px">
                       <c:forEach var="item" items="${condensingDeviceList}">
                             <option value="${item.condensingDeviceNum}">${item.condensingDeviceName}</option>
			           </c:forEach>
		            </select>
				</div>
				<div class="layui-input-inline" style="margin-right: 0">
					<input type="text" id="query_startTime" name="query_startTime" class="Wdate" value="<fmt:formatDate value="${defaultTime}" pattern="yyyy-MM-dd 00:00:00" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px" class="layui-input search-maxlenght">
<%-- 					<input type="text" id="query_startTime" name="query_startTime" class="Wdate" value="<fmt:formatDate value="${lastHour}" pattern="yyyy-MM-dd 00:00:00" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px" class="layui-input search-maxlenght"> --%>
				</div>
				<label class="layui-form-label" style="padding: 5px">--</label>
				<div class="layui-input-inline">
					<input type="text" id="query_endTime" name="query_endTime" class="Wdate" value="<fmt:formatDate value="${defaultTime}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px" class="layui-input search-maxlenght">
				</div>


				<div class="layui-input-inline" style="width: 400px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="searchHistoryRecordList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
					<button class="layui-btn layui-btn-light-blue" onclick="chooseDate(1)"> 当日</button>
					<button class="layui-btn layui-btn-light-blue" onclick="chooseDate(4)"> 1周内</button>
					<button class="layui-btn layui-btn-light-blue" onclick="chooseDate(2)"> 1个月内</button>
<!-- 					<button class="layui-btn layui-btn-light-blue" onclick="chooseDate(3)"> 3个月内</button> -->
				</div>
			</div>
		</div>
	</div>

<div style="height: 100%;">
	<!--     预警数 start -->
	<div id="earlyAlarmDiv" style="position: absolute; height: 110px; width: 230px; top: 60px; left: 10px; background-color: #F2CC0C; text-align: center;">
		<div style="height: 30px; line-height: 30px;font-weight: bold;color: #333">预警数</div>
		<div style="height: 80px; line-height: 80px; font-size: 25px; font-weight: bold;" id="earlyAlarmCount"></div>
	</div>
	<div id="alarmDiv" style="position: absolute; height: 110px; width: 230px; top: 60px; left: 250px; background-color: #F2495C; text-align: center;">
		<div style="height: 30px; line-height: 30px;font-weight: bold;color: #333">故障数</div>
		<div
			style="height: 80px; line-height: 80px; font-size: 25px; font-weight: bold;" id="alarmCount"></div>
	</div>
	<!--     预警数 end -->
	<!--   预警信息 start -->
<!-- 	<div -->
<!-- 		style="position: absolute; height: 190px; width: 550px; top: 185px; left: 10px; text-align: center; border: 1px solid #c2c2c2; border-radius: 15px"> -->
<!-- 		<div style="height: 30px; line-height: 30px;font-weight: bold;color: #333">预警信息</div> -->
<!-- 		<div style="height: 160px;padding-left: 10px"> -->
<!-- 		 <table  cellspacing='0' cellpadding='3' class='list-table'> -->
<!-- 		 <thead style="height: 20px;"> -->
<!-- 		   <tr> -->
<!-- 		      <th style="width: 30%;text-align: center;">报警时间</th> -->
<!-- 		      <th style="width: 40%;text-align: center;">报警内容</th> -->
<!-- 		      <th style="width: 30%;text-align: center;">报警恢复时间</th> -->
<!-- 		   </tr> -->
<!-- 		   </thead> -->
<!-- 		   <tbody id="earlyAlarmTable" style="height: 123px;"> -->

<!-- 		   </tbody> -->
<!-- 		 </table> -->

<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div -->
<!-- 		style="position: absolute; height: 190px; width: 550px; top: 185px; left: 570px; text-align: center; border: 1px solid #c2c2c2; border-radius: 15px"> -->
<!-- 		<div style="height: 30px; line-height: 30px;font-weight: bold;color: #333">故障信息</div> -->
<!-- 		<div style="height: 160px;padding-left: 10px"> -->
<!-- 		 <table  cellspacing='0' cellpadding='3' class='list-table'> -->
<!-- 		  <thead style="height: 20px;"> -->
<!-- 		   <tr> -->
<!-- 		      <th style="width: 30%;text-align: center;">报警时间</th> -->
<!-- 		      <th style="width: 40%;text-align: center;">报警内容</th> -->
<!-- 		      <th style="width: 30%;text-align: center;">报警恢复时间</th> -->
<!-- 		   </tr> -->
<!-- 		   </thead> -->
<!-- 		   <tbody id="alarmTable" style="height: 123px;"> -->
<!-- 		   </tbody> -->
<!-- 		 </table> -->

<!-- 		</div> -->
<!-- 	</div> -->

<!-- <div -->
<!-- 		style="position: absolute; height: 190px; width: 50%; top: 185px; text-align: center; border: 1px solid #c2c2c2; border-radius: 15px;padding: 0 10px"> -->
<!-- 		<div style="height: 30px; line-height: 30px;font-weight: bold;color: #333">预警信息</div> -->
<!-- 		<div style="height: 160px;padding-left: 10px"> -->
<!-- 		 <table  cellspacing='0' cellpadding='3' class='list-table'> -->
<!-- 		 <thead style="height: 20px;"> -->
<!-- 		   <tr> -->
<!-- 		      <th style="width: 30%;text-align: center;">报警时间</th> -->
<!-- 		      <th style="width: 40%;text-align: center;">报警内容</th> -->
<!-- 		      <th style="width: 30%;text-align: center;">报警恢复时间</th> -->
<!-- 		   </tr> -->
<!-- 		   </thead> -->
<!-- 		   <tbody id="earlyAlarmTable" style="height: 123px;"> -->

<!-- 		   </tbody> -->
<!-- 		 </table> -->

<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div -->
<!-- 		style="position: absolute; height: 190px; width: 50%; top: 185px; left: 50%; text-align: center; border: 1px solid #c2c2c2; border-radius: 15px;padding: 0 10px"> -->
<!-- 		<div style="height: 30px; line-height: 30px;font-weight: bold;color: #333">故障信息</div> -->
<!-- 		<div style="height: 160px;padding-left: 10px"> -->
<!-- 		 <table  cellspacing='0' cellpadding='3' class='list-table'> -->
<!-- 		  <thead style="height: 20px;"> -->
<!-- 		   <tr> -->
<!-- 		      <th style="width: 30%;text-align: center;">报警时间</th> -->
<!-- 		      <th style="width: 40%;text-align: center;">报警内容</th> -->
<!-- 		      <th style="width: 30%;text-align: center;">报警恢复时间</th> -->
<!-- 		   </tr> -->
<!-- 		   </thead> -->
<!-- 		   <tbody id="alarmTable" style="height: 123px;"> -->
<!-- 		   </tbody> -->
<!-- 		 </table> -->

<!-- 		</div> -->
<!-- 	</div> -->
	<div style="position: absolute; height: 190px; width: 50%; top: 185px;">
		<div id="" style="height: 180px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px">
				<div style="height: 30px; line-height: 30px;font-weight: bold;color: #333;text-align: center;">预警信息</div>
		<div style="height: 140px;padding-left: 10px">
		 <table  cellspacing='0' cellpadding='3' class='list-table'>
		 <thead style="height: 20px;">
		   <tr>
		      <th style="width: 30%;text-align: center;">报警时间</th>
		      <th style="width: 40%;text-align: center;">报警内容</th>
		      <th style="width: 30%;text-align: center;">报警恢复时间</th>
		   </tr>
		   </thead>
		   <tbody id="earlyAlarmTable" style="height: 108px;">

		   </tbody>
		 </table>

		</div>
		</div>
	</div>
	<div
		style="position: absolute; height: 190px; width: 50%; top: 185px; left: 50%;">
		<div id="" style="height: 180px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px">
				<div style="height: 30px; line-height: 30px;font-weight: bold;color: #333;text-align: center">故障信息</div>
		<div style="height: 140px;padding-left: 10px">
		 <table  cellspacing='0' cellpadding='3' class='list-table'>
		  <thead style="height: 20px;">
		   <tr>
		      <th style="width: 30%;text-align: center;">报警时间</th>
		      <th style="width: 40%;text-align: center;">报警内容</th>
		      <th style="width: 30%;text-align: center;">报警恢复时间</th>
		   </tr>
		   </thead>
		   <tbody id="alarmTable" style="height: 108px;">
		   </tbody>
		 </table>

		</div>


		</div>
	</div>
	<!--   预警信息 end -->

<!--     压力趋势图 start -->
	<div style="position: absolute; height: 400px; width: 50%; top: 390px;">
		<div id="pressureChart" style="height: 380px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px"></div>
	</div>
	<div
		style="position: absolute; height: 400px; width: 50%; top: 390px; left: 50%;">
		<div id="" style="height: 380px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px">
			<div style="height: 30px;line-height: 30px;text-align: center;">压力数据列表<img alt="导出" title="导出" style="position:absolute;right:40px;top: 4px;cursor: pointer;" onclick="exportHistoryRecord('pressure')" src="../../../../css/images/export.png"></div>
				<div style="height: 350px;overflow: hidden;">
				<table cellspacing='0' cellpadding='3' class='list-table'>
				<thead id="pressureThead">
					<tr>
						<th style='width:70px'>采集时间</th>
						<th>吸气压力</th>
						<th>排气压力</th>
						<th>供油压力</th>
						<th>滤网后压力</th>
						<th>中間压力</th>
						<th>供油差压</th>
						<th>过滤器差压</th>
						<th>差压</th>
					</tr>
				</thead>
				<tbody id="pressureTable">

				</tbody>
			</table>

				</div>


		</div>
	</div>
	<!--     压力趋势图 end -->

	<!--     温度趋势图 start -->
	<div style="position: absolute; height: 400px; width: 50%; top: 795px;">
		<div id="temperatureChart" style="height: 380px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px"></div>
	</div>
	<div
		style="position: absolute; height: 400px; width: 50%; top: 795px; left: 50%;">
		<div id="" style="height: 380px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px">
			<div style="height: 30px;line-height: 30px;text-align: center;">温度数据列表<img alt="导出" title="导出" style="position:absolute;right:40px;top: 4px;cursor: pointer;" onclick="exportHistoryRecord('temperature')" src="../../../../css/images/export.png"></div>
			<div style="height: 350px;">
			<table cellspacing='0' cellpadding='3' class='list-table'>
				<thead id="temperatureThead">
					<tr>
						<th style='width:70px'>采集时间</th>
						<th>吸入温度</th>
						<th>排气温度</th>
						<th>给油温度</th>
						<th>油分温度</th>
						<th>中间温度</th>
						<th>不冻液入口温度</th>
						<th>冷水出口温度</th>
					</tr>
				</thead>
				<tbody id="temperatureTable">

				</tbody>
			</table>
			</div>
		</div>
	</div>
	<!--     温度趋势图 end -->

	<!--     振动趋势图 start -->
	<div style="position: absolute; height: 300px; width: 50%; top: 1200px;">
		<div id="vibrationChart" style="height: 280px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px"></div>
	</div>
	<div
		style="position: absolute; height: 300px; width: 50%; top: 1200px; left: 50%;">
		<div id="" style="height: 280px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px">
			<div style="height: 30px;line-height: 30px;text-align: center;">振动数据列表<img alt="导出" title="导出" style="position:absolute;right:40px;top: 4px;cursor: pointer;" onclick="exportHistoryRecord('vibration')" src="../../../../css/images/export.png"></div>
			<div style="height: 250px;">
			<table cellspacing='0' cellpadding='3' class='list-table'>
				<thead id="vibrationThead">
					<tr>
						<th>采集时间</th>
						<th>冷却器液位/压缩机振动值</th>
					</tr>
				</thead>
				<tbody id="vibrationTable" style="height: 203px">

				</tbody>
			</table>
			</div>

		</div>
	</div>
	<!--     振动趋势图 end -->

	<!--     马达电流趋势图 start -->
	<div style="position: absolute; height: 300px; width: 50%; top: 1505px;">
		<div id="electricChart" style="height: 280px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px"></div>
	</div>
	<div style="position: absolute; height: 300px; width: 50%; top: 1505px; left: 50%;">
		<div id="" style="height: 280px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px">
			<div style="height: 30px;line-height: 30px;text-align: center;">马达电流数据列表<img alt="导出" title="导出" style="position:absolute;right:40px;top: 4px;cursor: pointer;" onclick="exportHistoryRecord('electric')" src="../../../../css/images/export.png"></div>
			<div style="height: 250px;">
			<table cellspacing='0' cellpadding='3' class='list-table'>
				<thead id="electricThead">
					<tr>
						<th>采集时间</th>
						<th>压缩机马达电流</th>
					</tr>
				</thead>
				<tbody id="electricTable" style="height: 203px">

				</tbody>
			</table>
			</div>
		</div>
	</div>
	<!--    马达电流趋势图 end -->

	<!--     位置趋势图 start -->
	<div style="position: absolute; height: 300px; width: 100%; top: 1810px;">
		<div id="" style="height: 280px; padding: 0 10px 10px 10px; border-radius: 15px; border: 1px solid #c2c2c2; margin: 0 10px">
			<div style="height: 30px;line-height: 30px;text-align: center;">位置数据列表<img alt="导出" title="导出" style="position:absolute;right:40px;top: 4px;cursor: pointer;" onclick="exportHistoryRecord('position')" src="../../../../css/images/export.png"></div>
			<div style="height: 250px;">
			<table cellspacing='0' cellpadding='3' class='list-table'>
				<thead id="positionThead">
					<tr>
						<th>采集时间</th>
						<th>低段滑阀容量位置</th>
						<th>高段滑阀/Vi位置</th>
						<th>排气/吸气压力比</th>
						<th>计算Vi</th>
						<th>当前Vi</th>
						<th>吸气过热度</th>
						<th>中间过热度</th>
						<th>排气过热度</th>
					</tr>
				</thead>
				<tbody id="positionTable" style="height: 203px">

				</tbody>
			</table>
			</div>
		</div>
	</div>
	<!--    位置趋势图 end -->


</div>

	<script>

	$("#operationTrend-content select[name='enterpriseId']").SumoSelect({
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

	$("#operationTrend-content select[name='condensingDeviceNum']").SumoSelect({
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

// 		closeLoading();


	</script>
