<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysRole.js"></script>
<style>
.layui-table-main{
overflow: auto;
}
</style>
<div class="contain-content" style="bottom: 0;">

	<div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<shiro:hasPermission name="sys_role_add">
						<button class="layui-btn layui-btn-light-blue" onclick="sysRoleAddPage()">
							<i class="layui-icon layui-icon-add-1"></i>添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys_role_del">
						<button class="layui-btn layui-btn-pink" onclick="sysRoleDelPage()">
							<i class="layui-icon layui-icon-delete"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
			</div>

			<div class="layui-inline" style="float: right">
				<div class="layui-input-inline">
					<input type="text" name="sysRoleroleName" placeholder="请输入角色名称" class="layui-input search-maxlenght">
				</div>

				<div class="layui-input-inline" style="width: 100px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysRoleSearchList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
			</div>
		</div>
	</div>

	<table id="sysRole" lay-filter="lSysRole"></table>

</div>
<script type="text/html" id="sysRoleOptBar">
<div class="layui-btn-group optBar">
	<shiro:hasPermission name="sys_role_edit">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit"  style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys_role_del">
		 <button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style="margin-right:0"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
	</shiro:hasPermission>
</div>
</script>

<script type="text/html" id="sysRoleTimeTpl"> 
  {{ getFormatDate(d.createTime) }}
</script>

<script type="text/javascript">

$(function(){
	$('[placeholder]').placeholder();
})

	var _selObj;
	table.render({
		elem : '#sysRole',
		id : 'sysRole',
		cols : [ [ 
			{type : 'checkbox',width: '5%'}
			, {	align : 'center',field : 'roleName',title : '角色名称',minWidth : 150,width: '20%'}
			, {	align : 'center',field : 'authorityDesc',title : '权限',minWidth : 350,width: '35%'}
			, {	align : 'center',field : 'createTime',title : '创建时间',minWidth : 180,width: '20%',	templet : '#sysRoleTimeTpl'}
			, { align : 'center',title : '操作',minWidth : 200,width: '20%',toolbar : '#sysRoleOptBar'}
			] ],
		url : 'system/sysRole/list.do',
		page : pageParam,
		limit : 10,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			emptyOptBar("sysRole");
			resizeTable('sysRole');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("sysRole");
		}
	});

	//监听工具条
	table.on('tool(lSysRole)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			if(data.id=="00000000000000000000000000000001"){
				openErrAlert("默认角色,无法删除!");
			}else{
				sysRoleDelPage(obj);
			}
		} else if (layEvent === 'edit') {
			if(data.id=="00000000000000000000000000000001"){
				openErrAlert("默认角色,无法编辑!");
			}else{
				sysRoleEditPage(data.id);
			}
		}
	});
</script>