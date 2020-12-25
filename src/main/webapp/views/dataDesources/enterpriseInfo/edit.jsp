<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<form id="enterpriseInfoForm" class="layui-form" action="" style="padding: 20px">
  <input type="hidden" id="id" name="id" value="${model.id}">

<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>所属组织</label>
				<div class="layui-input-inline" style="">
					<input type="hidden" id="orgId" name="orgId" value="${model.orgId}">
					<input type="text" id="orgName" name="orgName" value="${model.orgName}" lay-verify="required" onclick="orgTreeShow()" readonly="readonly" style="cursor:default;background-color: #e5e5e5" class="layui-input">
				</div>
			</div>
			<div class="tree" id="orgTreeDiv" style="position: absolute;top: 51px;left: 130px;width:230px;background: #fff;display: none;z-index: 999;height: 260px;border: 0">
			    <ul id="orgTree" class="ztree"></ul>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>企业名称</label>
				<div class="layui-input-inline" style="">
				     <input type="text" name="enterpriseName" value="${model.enterpriseName}" lay-verify="required|textMaxlength|enterpriseInfouniqueName|specialChar" class="layui-input">
				</div>
			</div>
<%--			<div class="layui-inline">--%>
<%--				<label class="layui-form-label"><span style="color:red">*</span>绑定终端</label>--%>
<%--				<div class="layui-input-inline">--%>
<%--					<select name="deviceCode" lay-verify="required">--%>
<%--						<option value="">请选择</option>--%>
<%--						<c:forEach var="item" items="${deviceInfoList}" varStatus="vs">--%>
<%--							<option value="${item.deviceCode}" ${model.deviceCode==item.deviceCode?'selected="selected"':''}>${item.deviceName}</option>--%>
<%--						</c:forEach>--%>
<%--					</select>--%>
<%--				</div>--%>
<%--			</div>--%>
		</div>
	
	
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>邮件推送</label>
			<div class="layui-input-inline">
				<select name="mailPushState" lay-verify="required">
					<option value="">请选择</option>
					<option value="1" ${model.mailPushState==1?'selected="selected"':''}>推送</option>
					<option value="0" ${model.mailPushState==0?'selected="selected"':''}>不推送</option>
				</select>
			</div>
		</div>

	</div>
	
		<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">邮件地址</label>
		<div class="layui-input-block">
		    <textarea placeholder="" name="mailAddress" style="width: 838px" lay-verify="textareaLen" maxlength="300" class="layui-textarea">${model.mailAddress}</textarea> 
	     </div>
	</div>
	
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>微信推送</label>
			<div class="layui-input-inline">
				<select name="wechatPushState" lay-verify="required">
					<option value="">请选择</option>
					<option value="1" ${model.wechatPushState==1?'selected="selected"':''}>推送</option>
					<option value="0" ${model.wechatPushState==0?'selected="selected"':''}>不推送</option>
				</select>
			</div>
		</div>
	   <div class="layui-inline">
			<label class="layui-form-label">AgentID</label>
			<div class="layui-input-inline">
				<input type="text" name="agentId" value="${model.agentId}" class="layui-input">
			</div>
		</div>

	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">微信ID</label>
		<div class="layui-input-block">
		    <textarea placeholder="" name="wechatAddress" style="width: 838px" lay-verify="textareaLen" maxlength="300" class="layui-textarea">${model.wechatAddress}</textarea> 
	     </div>
	</div>
	


	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label"><img onclick="showMapPage()" style="position:absolute;top:0;left: 28px" src="/css/images/map/position.png"><font class="required-dot">*</font>经度</label>
			<div class="layui-input-inline">
				<input type="text" id="longitude" name="longitude" value="${model.longitude}"  lay-verify="required" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>纬度</label>
			<div class="layui-input-inline">
				<input type="text" id="latitude" name="latitude" value="${model.latitude}"  lay-verify="required" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>企业地址</label>
			<div class="layui-input-inline">
				<input type="text" id="address" name="address" value="${model.address}"  lay-verify="required" class="layui-input">
			</div>
		</div>

	</div>


	<div class="layui-form-item"
		style="text-align: right; padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit=""
			lay-filter="enterpriseInfoButton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
</from>

<script type="text/javascript">
	(function($) {
		showZtreeFun($("#orgTree"),"system/sysOrg/getOrgTree.do",orgTreeClick,orgTreeShow);
		
		$('[placeholder]').placeholder();

		form.render();
		//自定义验证规则
		form.verify({
		    textMaxlength: function(value){
			      if(value.length > 32){
			        return '长度不能超过32位';
			      }
			},
			phoneLength:function(value){
				console.log(value)
				 if(value.length != 11){
				        return '电话号码必须为11位';
				  }
			},
			specialChar: function(value){
		   		var pattern = new RegExp(specialCharReg); 
		    	if(isNotEmpty(value) && pattern.test(value)){
		    		return '禁止输入特殊字符';
		    	}
		    },
		    enterpriseInfouniqueName: function(value){
		    	var flag = true;
		    	$.ajax({  
			         type : "get",  
			          url : "dataDesources/enterpriseInfo/checkEnterpriseNameExist.do",  
			          data : {str:value,id:$("input#id").val()},  
			          async : false,  
			          dataType: "json",
			          success : function(data){  
			          	flag = data.success;
			          }  
			     }); 
		    	if(!flag) return '企业名称已存在';
		    }
		});

		//监听提交
		form.on('submit(enterpriseInfoButton)', function(data) {
// 			var file =$("input[name='file']")[0].files[0];
			var form = document.getElementById("enterpriseInfoForm");
			var formData = new FormData(form); 
// 			formData.append("file", file);
			enterpriseInfoAddEditSave(formData,2);
			
			return false;
		});

		upload.render({
			elem : '#enterpriseInfoImgBtn',
			accept : 'images',
			size : 2048, //限制文件大小，单位 KB
			xhr : xhrOnProgress,
			auto: false, //选完文件后不要自动上传
			progress : function(value) {
			},
			data : {
			//type:'${upload_url}'
			},
			choose : function(obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
				obj.preview(function(index, file, result){
			        $('#imgPreview').attr('src', result); //图片链接（base64）
			      });
			},
			done : function(data) {
			},
			error : function() {
				
			}
		});

	})(jQuery)
	
 
    function orgTreeClick(treeNode) {
       $("#orgName").val(treeNode.name);
       $("#orgId").val(treeNode.id);
   }

    function orgTreeShow() {
       $("#orgTreeDiv").toggle();
   }
    
       
</script>