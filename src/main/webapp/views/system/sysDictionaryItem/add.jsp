<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="layui-form" action="" style="padding: 20px;">

	<input type="hidden" id="sysDictionaryItemid" name="id" value="${model.id}">
	<input type="hidden" id="sysDictionaryItemdictId" name="dictId" value="${dictId}">
	
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>字典项名称</label>
		<div class="layui-input-block">
			<input type="text" maxlength="32" name="itemName" lay-verType="tips" lay-verify="required" value="${model.itemName }" autocomplete="off" placeholder="请输入名称" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>字典项值</label>
		<div class="layui-input-block">
			<input type="text" name="itemValue" lay-verType="tips" lay-verify="required|title|sysDictionaryItemCodeunique" value="${model.itemValue }" autocomplete="off" placeholder="请输入字典项值,不可重复" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">排序号</label>
		<div class="layui-input-block">
			<input type="text" name="sortIdx" lay-verType="tips" lay-verify="myNumber|specialChar|gt0" value="${model.sortIdx }" autocomplete="off" placeholder="从小到大排序" class="layui-input">
		</div>
	</div>
	
	<div class="layui-form-item">
		<label class="layui-form-label">是否启用</label>
		<div class="layui-input-block">
			<input type="checkbox" name="status" ${model.status==1||model.status==null?'checked':'' } value="1" lay-skin="switch" lay-text="">
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
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysDictionaryItemButton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
	
</div>


<script>
  form.render();
  //自定义验证规则
  form.verify({
    title: function(value){
      if(value.length > 10){
        return '长度超过10个字符';
      }
    },
    specialChar: function(value){
   		var pattern = new RegExp(specialCharReg); 
    	if(isNotEmpty(value) && pattern.test(value)){
    		return '禁止输入特殊字符';
    	}
    },
    gt0: function(value) {
    	if(isNotEmpty(value)){
			var x = Number(value);
			if(x<0) return '请输入大等于0的整数';
    	}
	},
	 myNumber: function(value){
	    	if(isNotEmpty(value) && isNaN(value)){
	    		return '只能填写数字';
	    	}
	    },
	sysDictionaryItemCodeunique: function(value){
    	var flag = true;
    	$.ajax({  
	         type : "get",  
	          url : "system/sysDictionaryItem/checkCodeExist.do",  
	          data : {str:value,id:$("input#sysDictionaryItemid").val(), dictId:$("#sysDictionaryItemdictId").val()},  
	          async : false,  
	          dataType: "json",
	          success : function(data){  
	          	flag = data.success;
	          }  
	     }); 
    	if(!flag) return '值已存在';
    	
    }
  });
  
  //监听提交
  form.on('submit(sysDictionaryItemButton)', function(data){
	  sysDictionaryItemAddEditSave(data.field);
    return false;
  });
  
$(function(){
	$('[placeholder]').placeholder();
	
})

</script>