<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysDictionary.js"></script>

<style>
<!--
#sysDictionary + .layui-table-view .layui-table-main tr td{cursor: pointer;}
-->
   
</style>

<div id="sysDictionary-left" class="tree-side" style="width:350px;">
	<div class="layui-form right-search" style="box-shadow: none;">
		
			<div class="layui-inline">
				<shiro:hasPermission name="sys_dict_add">
					<div class="layui-input-inline">
						<button class="layui-btn layui-btn-light-blue" onclick="sysDictionaryAddPage()">
							<i class="fa fa-plus" aria-hidden="true"></i> 添加
						</button>
					</div>
				</shiro:hasPermission>
			</div>
			
			<div class="layui-inline" style="float: right">
				<div class="layui-input-inline" style="margin-right: -5px;width: 130px;">
					<input type="text" name="dictionary-keyword" placeholder="请输入关键字" class="layui-input search-maxlenght">
				</div>

				<div class="layui-input-inline" style="">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysDictionarySearchList()">
						<i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i>
					</button>
				</div>
			</div>
	</div>
	<div class="" style="height:100%; overflow-x: auto;">
		<table id="sysDictionary" lay-filter="lSysDictionary"></table>
	</div>
</div>

<div id="sysDictionary-content" class="layui-anim layui-anim-fadein" >

</div>


<script type="text/html" id="sysDictionaryOptBar">
<div class="layui-btn-group optBar">
	<shiro:hasPermission name="sys_dict_edit">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" ><i class="fa fa-pencil-square-o" aria-hidden="true"></i> </button>
	</shiro:hasPermission>

	<shiro:hasPermission name="sys_dict_del">
  		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style="margin-right:0"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
	</shiro:hasPermission>

	<shiro:hasPermission name="sys_dict">
  		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="list" style="margin-right:0;display:none"><i class="layui-icon">&#xe623;</i></button>
	</shiro:hasPermission>
</div>
</script>


<script type="text/javascript">

$(function(){
	$('[placeholder]').placeholder();
	
	$(document).off('click', '#sysDictionary + .layui-table-view .layui-table-main tr td[data-field!=1]');
	$(document).on('click', '#sysDictionary + .layui-table-view .layui-table-main tr td[data-field!=1]', function () {
        $(this).next("td").find('.layui-table-cell button[lay-event="list"]').click();
    })
    
})

	var _selObj;
	table.render({
		elem : '#sysDictionary',
		id : 'sysDictionary',
		cols : [ [ 
			{	align : 'center',field : 'dictName',title : '字典名称',minWidth: 200,width: '65%'}
			, { align : 'center',title : '操作', minWidth: 140,width: '35%',toolbar : '#sysDictionaryOptBar'}
			] ],
		url : 'system/sysDictionary/list.do',
		page : {
			curr : 1,
			layout : ['prev', 'page', 'next','count']
		},
		limit : 10,
		/* height:'full-'+getTableFullHeight(), */
		done : function(res, curr, count) {
			//无操作项提示
			emptyOptBar("sysDictionary");
			resizeTable('sysDictionary');
			closeLoading();
			var data = res.data;
			if(data.length!=0) {
				//默认选中第一行
			   defaultClickDictionary();
			}
		}
	});

	//监听工具条
	table.on('tool(lSysDictionary)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'list') {
			$.get("system/sysDictionaryItem/index.do",{dictId:data.id},function(data){
				$("#sysDictionary-content").html(data)
			})
		} else if (layEvent === 'del') {
			if(obj.data.isDeleteEnable==0){
				openErrAlert("数据不可删除!");
			}else{
				sysDictionaryDelPage(obj);
			}
		} else if (layEvent === 'edit') {
			sysDictionaryEditPage(data.id);
		}
	});
	
	function defaultClickDictionary(){
		 $("#sysDictionary + .layui-table-view .layui-table-main tr td:first").click()
	}
</script>