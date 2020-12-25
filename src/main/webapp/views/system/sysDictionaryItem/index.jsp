<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysDictionaryItem.js"></script>
<style>
	.layui-border-box.layui-table-view {
    	height: 580px;
	}
</style>
<div class="contain-content" style="bottom: 0;">
	<input type="hidden" id="query_dictId" value="${dictId }">
	<div class="layui-form right-search" style="box-shadow: none;">
		
			<div class="layui-inline">
				<div class="layui-input-inline">
					<shiro:hasPermission name="sys_dict_add">
						<button class="layui-btn layui-btn-light-blue" onclick="sysDictionaryItemAddPage()">
							<i class="fa fa-plus" aria-hidden="true"></i> 添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys_dict_del">
						<button class="layui-btn layui-btn-pink" onclick="sysDictionaryItemDelPage()">
							<i class="fa fa-trash-o" aria-hidden="true"></i> 删除
						</button>
					</shiro:hasPermission>
				</div>
			</div>
			
			<div class="layui-inline" style="float: right">
				<div class="layui-input-inline" style="margin-right: -5px;">
					<input type="text" name="dictionaryItem-keyword" placeholder="请输入数据项名称" class="layui-input search-maxlenght">
				</div>

				<div class="layui-input-inline" style="width: 100px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysDictionaryItemSearchList()">
						<i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i> 查询
					</button>
				</div>
			</div>
	</div>
</div>
<table id="sysDictionaryItem"  lay-filter="lSysDictionaryItem"></table>

<script type="text/html" id="sysDictionaryItemOptBar">

<div class="layui-btn-group optBar">
	<shiro:hasPermission name="sys_dict_edit">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" ><i class="fa fa-pencil-square-o" aria-hidden="true"></i> </button>
	</shiro:hasPermission>

	<shiro:hasPermission name="sys_dict_del">
  		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style="margin-right:0"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
	</shiro:hasPermission>
</div>

</script>

<script type="text/html" id="sysDictionaryItemStatusTpl">
  {{#  if(d.status == 1){ }}
   	 <font color="green">启用</font>
  {{#  } else { }}
   	 <font color="red">禁用</font> 
  {{#  } }}
</script>


<script type="text/javascript">

$(function(){
	
})

	var _selObj;
	table.render({
		elem : '#sysDictionaryItem',
		id : 'sysDictionaryItem',
		cols : [ [ 
			{type:'checkbox',width: '5%'},
			{	align : 'center',field : 'itemName',title : '字典项名称',width : '20%'}
			,{	align : 'center',field : 'itemValue',title : '字典项值',width : '20%'}
			,{	align : 'center',field : 'status',title : '状态',width : '20%', templet:'#sysDictionaryItemStatusTpl'}
			, { align : 'center',title : '操作',width : '35%',toolbar : '#sysDictionaryItemOptBar'}
			] ],
		url : 'system/sysDictionaryItem/list.do',
		page : {
			curr : 1,
			//layout : ['prev', 'page', 'next','count']
		},
		where : {
			dictId : $("#query_dictId").val(),
		},
		limit : 10,
		/* height:'full-'+getTableFullHeight(), */
		done : function(res, curr, count) {
			//无操作项提示
			emptyOptBar("sysDictionaryItem");
			resizeTable('sysDictionaryItem');
			closeLoading();
		}
	});

	//监听工具条
	table.on('tool(lSysDictionaryItem)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'list') {
			//layer.msg('查看列表:'+ data.id);
		} else if (layEvent === 'del') {
			sysDictionaryItemDelPage(obj);
		} else if (layEvent === 'edit') {
			sysDictionaryItemEditPage(data.id);
		}
	});
</script>