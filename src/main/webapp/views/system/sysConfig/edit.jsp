<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div id="sysConfigForm" class="layui-form" action="" style="padding: 20px">
	<input type="hidden" id="sysConfigid" name="id" value="${model.id}">
	
	<div class="layui-form-item">
		<label class="layui-form-label">状态</label>
		<div class="layui-input-block">
			<input type="checkbox" name="flag" ${model.flag==1?'checked':'' } lay-skin="primary" title="需同步" value="1">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>配置项</label>
		<div class="layui-input-block">
			<input type="text" name="configKey" lay-verify="required|title" value="${model.configKey }" autocomplete="off"
				placeholder="请输入配置项" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>配置值</label>
		<div class="layui-input-block">
			<textarea placeholder="" name="configValue" lay-verify="required|valueLength" maxlength="256" class="layui-textarea">${model.configValue}</textarea>
		</div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block" style="">
			<textarea placeholder="" name="remark" lay-verify="remark" maxlength="250" class="layui-textarea">${model.remark}</textarea>
		</div>
	</div>
	<div class="layui-form-item" style="text-align: right; padding-top: 15px;">
		<button class="layui-btn" onclick="sysConfigNetTest()" style="display:${model.configKey=='server_api_url'?'':'none'}" >网络测试</button>
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysConfigButton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
</div>

<script>

$(function(){
	$('[placeholder]').placeholder();
})

	form.render();
	//自定义验证规则
	form.verify({
		title : function(value) {
			if (value.length > 32) {
				return '长度超过32个字符';
			}
		},
		valueLength : function(value) {
			if (value.length > 128) {
				return '长度超过128个字符';
			}
		},
		specialChar: function(value){
	   		var pattern = new RegExp(specialCharReg); 
	    	if(isNotEmpty(value) && pattern.test(value)){
	    		return '禁止输入特殊字符';
	    	}
	    }
	});

	//监听提交
	form.on('submit(sysConfigButton)', function(data) {
		sysConfigAddEditSave(data.field);
		return false;
	});
	
</script>