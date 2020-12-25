<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<style>
.layui-form-label{
    width: 110px;
}
.layui-input-block {
    margin-left: 140px;
}
.layui-form-item {
    margin-bottom: 10px;
    clear: both;
}
</style>
<div class="layui-form" action="" style="padding: 20px">
	<input type="hidden" id="id" name="id" value="${model.id}">
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>标签名称</label>
		<div class="layui-input-block">
			<input type="text" name="tagName" value="${model.tagName}" lay-verify="required|textMaxlength" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item" style="text-align: right;">
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="monitorFactorTagButton">提交</button>
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
		textMaxlength : function(value) {
			if (value.length > 32) {
				return '长度超过32个字符';
			}
		},
		valueLength : function(value) {
			if (value.length > 256) {
				return '长度超过256个字符';
			}
		},
		numFlag : function(value) {
			var x = Number(value);
			if (x >=0){				
			}else{
				return '请输入大于等于0的整数';
			}
				
		},
		numFlag2 : function(value){
			if(isNaN(value)){
				 return '请输入数字';
		    }
		},
		specialChar: function(value){
	   		var pattern = new RegExp(specialCharReg); 
	    	if(isNotEmpty(value) && pattern.test(value)){
	    		return '禁止输入特殊字符';
	    	}
	    },
	    uniqueTagName: function(value){
	    	var flag = true;
	    	$.ajax({  
		         type : "get",  
		          url : "dataDesources/monitorFactorTag/checkTagNameExist.do",
		          data : {str:value,id:$("input#id").val()},  
		          async : false,  
		          dataType: "json",
		          success : function(data){  
		          	flag = data.success;
		          }  
		     }); 
	    	if(!flag) return '监测因子标签名称已存在';
	    }
	});

	//监听提交
	form.on('submit(monitorFactorTagButton)', function(data) {
		monitorFactorTagAddEditSave(data.field,2);
		return false;
	});
</script>