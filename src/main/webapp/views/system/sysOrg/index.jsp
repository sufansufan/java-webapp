<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysOrg.js"></script>

<div style=" width: 100%; background-color: #fff;height: 100%;">
	<div class="contain-content" style="padding: 15px; bottom: 0; background-color: #FFF;overflow-y:auto">
		
		<c:if test="${loginUser.issupermanager }">
			<button id="addFirst" class="layui-btn layui-btn-light-blue" onclick="sysOrgAddPage()" style="margin-left: 20px; margin-top: 10px;">
				<i class="layui-icon layui-icon-add-1"></i>添加一级组织
			</button>
		</c:if>
		
	
		<div style="padding-left: 10px; margin-top: 10px; width: auto;">
			<shiro:hasPermission name="sys_org_add">
				<input type="hidden" id="orgHasAdd" value="1">
			</shiro:hasPermission>
			<shiro:hasPermission name="sys_org_edit">
				<input type="hidden" id="orgHasEdit" value="1">
			</shiro:hasPermission>
			<shiro:hasPermission name="sys_org_del">
				<input type="hidden" id="orgHasDel" value="1">
			</shiro:hasPermission>
			<ul id="sysOrgorgTree" class="ztree"></ul>
		</div>
	
	</div>
</div>
