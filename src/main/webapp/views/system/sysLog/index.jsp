<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysLog.js"></script>
<style>
.layui-table-main{
overflow: auto;
}
</style>
<div class="contain-content" style="bottom: 0;">

<div class="layui-form search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">操作时间</label>
				<div class="layui-input-inline">
					<input type="text" class="layui-input datetime" id="sysLogbeginTime" readonly="readonly" onfocus="this.blur()">
				</div>
				
				<div class="layui-input-inline line-separate">
					<input type="text" class="layui-input" disabled value="-">
				</div>
				<div class="layui-input-inline">
					<input type="text" class="layui-input datetime" id="sysLogendTime" readonly="readonly" onfocus="this.blur()">
				</div>
			</div>
			<div class="layui-inline">
				<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysLogsearchList()">
					<i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i> 查询
				</button>
			</div>
			<shiro:hasPermission name="sys_log_export">
			<div class="layui-inline">
				<button class="layui-btn layui-btn-lavender" onclick="sysLogexcelExport(this)">
					<i class="layui-icon">&#xe601;</i>导出
				</button>
			</div>
			</shiro:hasPermission>
		</div>
	</div>
	<table id="sysLog" lay-filter="SysLog"></table>

</div>

<script type="text/html" id="sysLogTypeTpl"> 
	{{  getValueFromMap(sysLogTypeMap,d.logType) }}
</script>

<script type="text/html" id="sysLogoptBar">

<div class="layui-btn-group optBar">
<shiro:hasPermission name="sys_log_view">
	<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="view"  style=""><i class="fa fa-eye" aria-hidden="true"></i> 查看</button>
</shiro:hasPermission>
</div>
</script>

<script type="text/html" id="sysLogtimeTpl"> 
  {{ getFormatDate(d.createTime) }}
</script>

<script type="text/javascript">
	var _u = '${upload_url}';
	var sysLogTypeMap = stringToJson('${sysLogTypeJson}');
	//执行渲染
	var laydate = layui.laydate;

	var sysLogbegin = laydate.render({
		elem : '#sysLogbeginTime',
		btns: ['clear', 'confirm'],
		done : function(value, date, endDate) {
			var param = sysLogbegin.config.min;
			if(value != ""){
				param = {
						year : date.year,
						month : date.month - 1,
						date : date.date,
						hours : date.hours,
						minutes : date.minutes,
						seconds : date.seconds
					}
			}
			sysLogend.config.min = param;
		},
		theme : '#63A8EB'
	});

	var sysLogend = laydate.render({
		elem : '#sysLogendTime',
		btns: ['clear', 'confirm'],
		done : function(value, date, endDate) {
			var param = {
					date : 31,
					hours : 0,
					minutes : 0,
					month : 11,
					seconds : 0,
					year : 2099
			};
			if(value != ""){
				param = {
						year : date.year,
						month : date.month - 1,
						date : date.date,
						hours : date.hours,
						minutes : date.minutes,
						seconds : date.seconds
					}
			}
			sysLogbegin.config.max = param;
		},
		theme : '#63A8EB'
	});
</script>