<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="layui-form" action="" style="padding: 20px">
	<input type="hidden" id="id" name="id" value="${model.id}">
	<input type="hidden" id="authorityParentId" name="authorityParentId" value="${pModel.id}">
	
	<div class="layui-form-item">
		<div class="layui-inline"> 
			<label class="layui-form-label"><font class="required-dot">*</font>名称</label>
			<div class="layui-input-inline">
				<input type="text" id="authorityName" name="authorityName"  value="${model.authorityName }" class="layui-input" lay-verify="required">
			</div>
		 </div>
		 <div class="layui-inline"> 
			<label class="layui-form-label"><font class="required-dot">*</font>编码</label>
			<div class="layui-input-inline">
				<input type="text" id="authorityCode" name="authorityCode"  value="${model.authorityCode }"class="layui-input" lay-verify="required|typeCodeUnique">
			</div>
		 </div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline"> 
			<label class="layui-form-label"><font class="required-dot">*</font>类型</label>
			<div class="layui-input-inline">
				<select id="authorityType" name="authorityType" lay-filter="authorityTypeSl" lay-verify="required"> 
				    <option value="">请选择</option>	
				    <c:forEach items="${authorityTypeMap}" var="item">
					    <option value="${item.key }" ${item.key==model.authorityType?'selected="selected"':'' }>${item.value }</option>
			        </c:forEach>
			    </select>
			</div>
		 </div>
		 <div class="layui-inline"> 
			<label class="layui-form-label"><font class="required-dot">*</font>序号</label>
			<div class="layui-input-inline">
				<input type="text" id="authorityOrder" name="authorityOrder" value="${model.authorityOrder }" class="layui-input" lay-verify="required">
			</div>
		 </div>
	</div>
	
	<div class="layui-form-item" id="authorityTypeDiv">
		<div class="layui-inline"> 
			<label class="layui-form-label"><font class="required-dot">*</font>图标</label>
			<div class="layui-input-inline">
				<input type="text"  id="authorityImage" name="authorityImage"  value="${model.authorityImage}" class="layui-input" lay-verify="">
			</div>
		 </div>
		 <div class="layui-inline"> 
			<label class="layui-form-label"><font class="required-dot">*</font>路径</label>
			<div class="layui-input-inline">
				<input type="text" id="authorityUrl" name="authorityUrl" value="${model.authorityUrl}" class="layui-input" lay-verify="">
			</div>
		 </div>
	</div>
	
	<div class="layui-form-item" style="text-align: right; padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysAuthorityaddButton">提交</button>
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
			if (value.length > 20) {
				return '长度超过20个字符';
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
	    },
	    typeCodeUnique : function(value) {
			var flag = true;
			$.ajax({
				type : "get",
				url : "system/sysAuthority/checkCodeExist.do",
				data : {
					authorityCode : value,
					id : $("#id").val()
				},
				async : false,
				dataType : "json",
				success : function(data) {
					flag = data.success;
				}
			});
			if (!flag)
				return '编码已存在';

		},
		typeNameUnique : function(value) {
			var flag = true;
			$.ajax({
				type : "get",
				url : "system/sysAuthority/checkNameExist.do",
				data : {
					authorityName : value,
					id : $("#id").val()
				},
				async : false,
				dataType : "json",
				success : function(data) {
					flag = data.success;
				}
			});
			if (!flag)
				return '名称已存在';

		}
	});
	
	 form.on('select(authorityTypeSl)', function(data) {
			var t = $(this).text();
			if (t == "请选择")
				t = "";
			if ($("#authorityType").val() == 1){
				document.getElementById("authorityTypeDiv").style.display="block";				
			}
	        if ($("#authorityType").val() == 2){
	        	document.getElementById("authorityTypeDiv").style.display="none";
			}
				
		});

	//监听提交
	form.on('submit(sysAuthorityaddButton)', function(data) {
		sysAuthorityAddSave(data.field);
		return false;
	});
</script>