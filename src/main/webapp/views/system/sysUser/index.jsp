<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysUser.js"></script>
<style>
.layui-table-main{
overflow: auto;
}
</style>
<div class="contain-content" style="bottom: 0;">
	<div class="tree">
		<ul id="sysUserOrgTree" class="ztree"></ul>
	</div>
	<div class="tree-content ">
		<div class="layui-form right-search">
			<div class="layui-form-item">
				<div class="layui-inline">
					<div class="layui-input-inline">
						<shiro:hasPermission name="sys_user_add">
							<button class="layui-btn layui-btn-light-blue" onclick="sysUserAddPage()">
								<i class="layui-icon">&#xe654;</i>添加
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys_user_del">
							<button class="layui-btn layui-btn-pink" onclick="sysUserDelPage()">
								<i class="layui-icon">&#xe640;</i>删除
							</button>
						</shiro:hasPermission>
<%-- 						<shiro:hasPermission name="sys_user_import"> --%>
<!-- 							<button class="layui-btn layui-btn-slightgreen" onclick="sysUserImportPage()"> -->
<!-- 								<i class="layui-icon">&#xe62f;</i>导入 -->
<!-- 							</button> -->
<%-- 						</shiro:hasPermission> --%>
					</div>
				</div>

				<div class="layui-inline" style="float: right">
					<div class="layui-input-inline">
						<input type="text" name="sysUseruserName" placeholder="账号名/用户名称/手机号" class="layui-input search-maxlenght" style="width: 200px;">
					</div>

					<div class="layui-input-inline" style="">
						<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysUserSearchList()">
							<i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i> 查询
						</button>
					</div>
				</div>
			</div>
		</div>

		<table id="sysUser" lay-filter="lSysUser"></table>

	</div>

</div>
<script type="text/html" id="sysUseroptBar">

	<div class="layui-btn-group optBar">
		<shiro:hasPermission name="sys_user_edit">
			<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit"  style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
		</shiro:hasPermission>
       <shiro:hasPermission name="sys_user_del">
			<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del"  style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 删除</button>
		</shiro:hasPermission>

	</div>

  
</script>

<script type="text/html" id="sysUseruserNameTpl"> 
	  <a href="javascript:;" title="点击查看详情" onclick="sysUserViewPage('{{d.id}}')" style="color: #63a8eb;text-decoration: underline;">{{d.userName}}</a>  
</script>

<script type="text/html" id="sysUsertimeTpl"> 
  {{ getFormatDate(d.createTime) }}
</script>

<script type="text/html" id="deadlineTpl"> 
  {{ getFormatDate(d.deadline) }}
</script>

<script type="text/html" id="sysUseruserCodeTpl">
  {{ getFormatCodeHtm(d.userCode,d.orgId) }}
</script>

<script type="text/javascript">
var nodeId = '${nodeId}';
$(function(){
	$('[placeholder]').placeholder();
})
</script>
