<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/dataDesources/monitorFactorTag.js"></script>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css" />
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<style>
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
<div id="monitorFactorTag-content" class="contain-content" style="bottom: 0;">
	<div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<shiro:hasPermission name="monitor_factor_tag_add">
						<button class="layui-btn layui-btn-light-blue" onclick="monitorFactorTagAddPage()">
							<i class="layui-icon layui-icon-add-1"></i>添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="monitor_factor_tag_del">
						<button class="layui-btn layui-btn-pink" onclick="monitorFactorTagDelPage()">
							<i class="layui-icon layui-icon-delete"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
			</div>
			
			<div class="layui-inline">
				<div class="layui-input-inline">
					<input type="text" name="tagName" placeholder="请输入监测因子标签" class="layui-input" style="height: 36px">
				</div>
				<div class="layui-input-inline">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="monitorFactorTagSearchList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
			</div>

		</div>
	</div>

	<table id="monitorFactorTag" lay-filter="lmonitorFactorTag"></table>

</div>
<script type="text/html" id="monitorFactorTagOptBar">
<div class="layui-btn-group optBar">
	<shiro:hasPermission name="monitor_factor_tag_edit">
<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i>编辑</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="monitor_factor_tag_del">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style=""><i class="fa fa-trash-o" aria-hidden="true"></i>删除</button>
	</shiro:hasPermission>
</div>
</script>


<script type="text/html" id="monitorFactorTagTimeTpl">
  {{ getFormatDate(d.createTime) }}
</script>

<script type="text/javascript">

$(function(){
	$('[placeholder]').placeholder();
})

	var _selObj;
	table.render({
		elem : '#monitorFactorTag',
		id : 'monitorFactorTag',
		cols : [ [ 
			{type : 'checkbox', width:'5%',fixed: 'left'}
			,{align : 'center',field : 'tagName',title : '监测因子标签名',minWidth : 150, width:'7.5%',fixed: 'left'}
			,{title : '操作',minWidth : 240, width:'11.5%',align : 'center',toolbar : '#monitorFactorTagOptBar',fixed: 'right'}
		] ],
		url : 'dataDesources/monitorFactorTag/list.do',
		page : pageParam,
		limit : 100,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			emptyOptBar("monitorFactorTag");
			resizeTable('monitorFactorTag');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("monitorFactorTag");
		}
	});

	table.on('tool(lmonitorFactorTag)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			monitorFactorTagDelPage(obj);
		} else if (layEvent === 'edit') {
			monitorFactorTagEditPage(data.id);
		}
	});
</script>