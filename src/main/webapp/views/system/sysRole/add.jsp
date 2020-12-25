<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="layui-form" action="" style="">

	<input type="hidden" id="sysRoleid" name="id" value="${model.id}">
	
	<div class="layui-form-item" style="margin-bottom: 0px; background-color: #fff; position: absolute; z-index: 9; padding: 20px; padding-bottom: 0;">
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>角色名称</label>
			<div class="layui-input-inline">
				<input type="text" maxlength="25" name="roleName" lay-verify="required|title|sysRoleunique|specialChar" value="${model.roleName }" autocomplete="off" placeholder="请输入名称" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
		    <div class="layui-input-inline">
		    
		      <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysRolebutton">提交</button>
				<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
		    </div>
    	</div>
	</div>
	
	<div class="layui-form-item" style="margin-bottom: 0px; background-color: #fff; position: absolute; z-index: 10;  padding-left: 20px; padding-bottom: 0;  top: 60px;padding-top: 10px;">
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>角色编码</label>
			<div class="layui-input-inline">
				<input type="text" maxlength="25" name="roleCode" lay-verify="required|title|sysRoleCodeunique|specialChar" value="${model.roleCode }" autocomplete="off" placeholder="请输入编码" class="layui-input">
			</div>
		</div>
	</div>
	<div class="layui-form-item" style="margin-bottom: 0px; background-color: #fff; position: absolute; z-index: 10;  padding-left: 20px; padding-bottom: 0;  top: 108px;padding-top: 10px;">
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>所属组织</label>
			  <div class="layui-input-inline" style="">
	  				<input type="hidden" id="orgId" name="orgId">
					<input type="text" id="orgName" name="orgName" lay-verify="required" onclick="orgTreeShow()" readonly="readonly" style="cursor:default;background-color: #e5e5e5" class="layui-input">
			    </div>
		</div>
	</div>
		<div class="tree" id="orgTreeDiv" style="position: absolute;top: 150px;left: 130px;width:230px;background: #fff;display: none;z-index: 999;height: 260px;border: 0">
			    <ul id="orgTree" class="ztree"></ul>
			</div>
	
	<div class="layui-form-item" style="margin-bottom: 0px; background-color: #fff; position: absolute; z-index: 9; padding: 20px; top: 144px; left: 0; right: 30px;">
		<label class="layui-form-label">快捷选择</label>
		<input type="checkbox" name="like[all]" title="全选" lay-filter="sysRoleselAll">
	</div>
	 
	<div id="authTreeSet" style="padding-left: 45px; overflow-y: auto; padding-top: 200px;">
		<ul id="authInfoTree" class="ztree" style="height: 360px;display:none"></ul>
		
		<div id="tree-loading" style="height: 360px; line-height: 340px; text-align: center;">
			<img alt="" src="${ctx}/css/images/common/loading-2.gif">
		</div>
	</div>
	
</div>


<script>
  form.render();
  //自定义验证规则
  form.verify({
    title: function(value){
      if(value.length > 25){
        return '长度超过25个字符';
      }
    },
    specialChar: function(value){
   		var pattern = new RegExp(specialCharReg); 
    	if(isNotEmpty(value) && pattern.test(value)){
    		return '禁止输入特殊字符';
    	}
    },
    sysRoleunique: function(value){
    	var flag = true;
    	$.ajax({  
	         type : "get",  
	          url : "system/sysRole/checkNameExist.do",  
	          data : {str:value,id:$("input#sysRoleid").val()},  
	          async : false,  
	          dataType: "json",
	          success : function(data){  
	          	flag = data.success;
	          }  
	     }); 
    	if(!flag) return '名称已存在';
    	
    },
    sysRoleCodeunique: function(value){
    	var flag = true;
    	$.ajax({  
	         type : "get",  
	          url : "system/sysRole/checkCodeExist.do",  
	          data : {str:value,id:$("input#sysRoleid").val()},  
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
  form.on('submit(sysRolebutton)', function(data){
	  sysRoleAddEditSave(data.field,"add");
    return false;
  });
  
  form.on('checkbox(sysRoleselAll)', function(data){
	  selectAllAuth(data.elem.checked);
	});       
  

$(function(){
	$('[placeholder]').placeholder();
	authTreeInit($("#sysRoleid").val());
	
})

showZtreeFun($("#orgTree"),"system/sysOrg/getOrgTree.do",orgTreeClick,orgTreeShow);
    function orgTreeClick(treeNode) {
       $("#orgName").val(treeNode.name);
       $("#orgId").val(treeNode.id);
   }

    function orgTreeShow() {
       $("#orgTreeDiv").toggle();
   }

</script>