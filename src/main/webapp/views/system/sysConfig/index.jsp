<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysConfig.js"></script>

<div class="contain-content" style="bottom: 0;">
	<div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<shiro:hasPermission name="sys_config_add">
						<button class="layui-btn layui-btn-light-blue" onclick="sysConfigAddPage()">
							<i class="layui-icon layui-icon-add-1"></i>添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys_config_del">
						<button class="layui-btn layui-btn-pink" onclick="sysConfigDelPage()">
							<i class="layui-icon layui-icon-delete"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
			</div>

			<div class="layui-inline" style="float: right">
				<div class="layui-input-inline">
					<input type="text" name="sysConfigconfigKey" placeholder="请输入配置项" class="layui-input">
				</div>

				<div class="layui-input-inline" style="width: 100px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysConfigSearchList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
			</div>
		</div>
	</div>

	<table id="sysConfig" lay-filter="lSysConfig"></table>

</div>
<script type="text/html" id="sysConfigOptBar">
<div class="layui-btn-group optBar">
	<shiro:hasPermission name="sys_config_edit">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit"  style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys_config_del">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style="margin-right:0"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
	</shiro:hasPermission>
</div>
</script>

<script type="text/html" id="sysConfigFlagTpl"> 
  {{#  if(d.flag == 1){ }}
   	需同步
  {{#  } else { }}
   	无需同步
  {{#  } }}
</script>

<script type="text/html" id="sysConfigTimeTpl"> 
  {{ getFormatDate(d.createTime) }}
</script>

<script type="text/javascript">

$(function(){
	$('[placeholder]').placeholder();
})

	var _selObj;
	table.render({
		elem : '#sysConfig',
		id : 'sysConfig',
		cols : [ [ 
			{type : 'checkbox', width:'5%'}
			,{align : 'center',field : 'configKey',title : '配置项',	minWidth : 150, width:'10%'}
			,{align : 'center',field : 'configValue',title : '配置值',	minWidth : 210, width:'19%'}
			,{align : 'center',field : 'flag', title : '状态',minWidth : 210, width:'10%',templet:'#sysConfigFlagTpl'}
			,{align : 'center',field : 'remark', title : '备注',minWidth : 210, width:'20%'}
			,{align : 'center',field : 'createTime', title : '创建时间',minWidth : 210, width:'18%',templet:'#sysConfigTimeTpl'}
			,{title : '操作',minWidth : 240, width:'18%',align : 'center',toolbar : '#sysConfigOptBar'} 
		] ],
		url : 'system/sysConfig/list.do',
		page : pageParam,
		limit : 10,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			emptyOptBar("sysConfig");
			resizeTable('sysConfig');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("sysConfig");
		}
	});

	table.on('tool(lSysConfig)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			sysConfigDelPage(obj);
		} else if (layEvent === 'edit') {
			sysConfigEditPage(data.id);
		}
	});
</script>