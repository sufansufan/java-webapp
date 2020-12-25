<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="layui-form" action="" style="padding: 20px;">

	<input type="hidden" id="sysDictionaryid" name="id" value="${model.id}">
	
	
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>字典编码</label>
		<div class="layui-input-block">
			<input type="text" maxlength="32" name="dictCode" lay-verType="tips" lay-verify="required|title|sysDictionaryCodeunique" value="${model.dictCode }" autocomplete="off" placeholder="请输入编码,不可重复" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>字典名称</label>
		<div class="layui-input-block">
			<input type="text" maxlength="32"  name="dictName" lay-verType="tips" lay-verify="required|title" value="${model.dictName }" autocomplete="off" placeholder="请输入名称" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">是否可删除</label>
		<div class="layui-input-block">
			<input type="checkbox" name="isDeleteEnable" ${model.isDeleteEnable==1?'checked':'' } value="1" lay-skin="switch" lay-text="">
		</div>
	</div>
	
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block" style="">
			<textarea placeholder="" name="remark" lay-verType="tips" lay-verify="remark" maxlength="250" class="layui-textarea">${model.remark}</textarea>
		</div>
	</div>
	
	<div class="layui-form-item"
		style="text-align: right; margin-bottom: 15px;padding-right: 59px;">
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysDictionaryButton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
	
</div>


<script>
  form.render();
  //自定义验证规则
  form.verify({
    title: function(value){
      if(value.length > 32){
        return '长度超过32个字符';
      }
    },
    specialChar: function(value){
   		var pattern = new RegExp(specialCharReg); 
    	if(isNotEmpty(value) && pattern.test(value)){
    		return '禁止输入特殊字符';
    	}
    },
    sysDictionaryCodeunique: function(value){
    	var flag = true;
    	$.ajax({  
	         type : "get",  
	          url : "system/sysDictionary/checkCodeExist.do",  
	          data : {str:value,id:$("input#sysDictionaryid").val()},  
	          async : false,  
	          dataType: "json",
	          success : function(data){  
	          	flag = data.success;
	          }  
	     }); 
    	if(!flag) return '编码已存在';
    	
    }
  });
  
  //监听提交
  form.on('submit(sysDictionaryButton)', function(data){
	  sysDictionaryAddEditSave(data.field);
    return false;
  });
  
$(function(){
	$('[placeholder]').placeholder();
	
})

</script>