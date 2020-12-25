<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>

</style>

<form id="operateForm" class="layui-form" action="" style="padding: 20px">

	<div class="layui-form-item">
		<div class="layui-inline"> 
			<label class="layui-form-label">操作用户</label>
			<div class="layui-input-inline">
				<input type="text" value="${model.userName }"
					class="layui-input" disabled>
			</div>
		 </div>
		<div class="layui-inline"> 
			<label class="layui-form-label">操作类型</label>
			<div class="layui-input-inline">
				<input type="text" value="${sysLogTypeMap[model.logType]}" class="layui-input" disabled>
			</div>
		 </div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline"> 
			<label class="layui-form-label">IP地址</label>
			<div class="layui-input-inline">
				<input type="text" value="${model.userIp}" class="layui-input" disabled>
			</div>
		 </div>
		 <div class="layui-inline"> 
			<label class="layui-form-label">操作时间</label>
			<div class="layui-input-inline">
				<input type="text"
				value="<fmt:formatDate value="${model.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />" class="layui-input" disabled>
			</div>
		 </div>
	</div>

	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">操作详情</label>
	    <div class="layui-input-block" style="margin-right: 55px;">
	    	<textarea readonly="readonly" unselectable="on" class="layui-textarea">${model.logDetail}</textarea>
	    </div>
	  </div>
	
	<div class="layui-form-item" style="text-align: right; padding-top: 15px;">
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">关闭</a>
	</div>
</form>


<script>
	var form = layui.form;
	form.render();
</script>