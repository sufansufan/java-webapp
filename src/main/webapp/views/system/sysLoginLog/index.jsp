<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysLoginLog.js"></script>

<div class="contain-content" style="bottom: 0;">

<div class="layui-form search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">操作时间</label>
				<div class="layui-input-inline">
					<input type="text" class="layui-input datetime" id="sysLoginLogbeginTime" readonly="readonly" onfocus="this.blur()">
				</div>
				
				<div class="layui-input-inline line-separate">
					<input type="text" class="layui-input" disabled value="-">
				</div>
				<div class="layui-input-inline">
					<input type="text" class="layui-input datetime" id="sysLoginLogendTime" readonly="readonly" onfocus="this.blur()">
				</div>
			</div>
			<div class="layui-inline">
				<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysLoginLogsearchList()">
					<i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i> 查询
				</button>
			</div>
			<shiro:hasPermission name="sys_log_export">
			<div class="layui-inline">
				<button class="layui-btn layui-btn-lavender" onclick="sysLoginLogexcelExport(this)">
					<i class="layui-icon">&#xe601;</i>导出
				</button>
			</div>
			</shiro:hasPermission>
		</div>
	</div>
	<table id="sysLoginLog" lay-filter="SysLog"></table>

</div>

<script type="text/html" id="sysLoginLogTypeTpl"> 
	{{  getValueFromMap(sysLoginLogTypeMap,d.logType) }}
</script>

<script type="text/html" id="sysLoginLogoptBar">

<div class="layui-btn-group optBar">
<shiro:hasPermission name="sys_log_view">
	<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="view"  style=""><i class="fa fa-eye" aria-hidden="true"></i> 查看</button>
</shiro:hasPermission>
</div>
</script>

<script type="text/html" id="sysLoginLogtimeTpl"> 
  {{ getFormatDate(d.createTime) }}
</script>

<script type="text/javascript">
	var _u = '${upload_url}';
	var sysLoginLogTypeMap = stringToJson('${sysLoginLogTypeJson}');
	//执行渲染
	var laydate = layui.laydate;

	var sysLoginLogbegin = laydate.render({
		elem : '#sysLoginLogbeginTime',
		btns: ['clear', 'confirm'],
		done : function(value, date, endDate) {
			var param = sysLoginLogbegin.config.min;
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
			sysLoginLogend.config.min = param;
		},
		theme : '#63A8EB'
	});

	var sysLoginLogend = laydate.render({
		elem : '#sysLoginLogendTime',
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
			sysLoginLogbegin.config.max = param;
		},
		theme : '#63A8EB'
	});
</script>