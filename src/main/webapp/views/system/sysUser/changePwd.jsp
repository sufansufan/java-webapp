<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<form class="layui-form" action="" style="padding:20px">

	<div class="layui-form-item">
		<label class="layui-form-label">旧密码</label>
		<div class="layui-input-block">
			<input type="password" name="oldPwd" lay-verify="required" autocomplete="off" placeholder="请输入旧密码" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">新密码</label>
		<div class="layui-input-block">
			<input type="password" name="newPwd" lay-verify="required|password" autocomplete="new-password" placeholder="请输入6~15位新密码" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">确认新密码</label>
		<div class="layui-input-block">
			<input type="password" name="newPwd2" lay-verify="equal" autocomplete="off" placeholder="请再次输入新密码" class="layui-input">
		</div>
	</div>
	
	<div class="layui-form-item" style="text-align: right;padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="changePwdbutton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
	
</form>


<script>

$(function(){
	$('[placeholder]').placeholder();
})

	var form = layui.form;
	form.render();
	//自定义验证规则
	form.verify({
		title : function(value) {
			if (value.length > 20) {
				return '长度不能超过20个字符';
			}
		},
    	password: function(value){
	        if(value.length>15 || value.length <6){
	            return '密码长度6~15位';
	          }
	        },
		equal : function(value) {

			var v = $("input[name='newPwd']").val();

			if (v != value) {
				return '两次密码输入不一致';
			}
		}
	});

	//监听提交
	form.on('submit(changePwdbutton)', function(data) {
		changePwdSave(data.field);
		return false;
	});

	function changePwdSave(params){
		$.post("system/sysUser/changePwdSave.do",params,function(data){
			closeLoading();
			requestSuccess(data);
	    	
			if(data.success){
				layer.closeAll('page');
			}
	    	
	    },'json');
	}
</script>