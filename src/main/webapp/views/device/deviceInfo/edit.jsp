<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css" />
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>

<div id="deviceInfoForm" class="layui-form" action="" style="padding: 20px">
	<input type="hidden" id="id" name="id" value="${model.id}">
	<div style="width: 660px; float: left">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>终端编码</label>
				<div class="layui-input-inline" style="">
				     <input type="text" name="deviceCode" value="${model.deviceCode}" lay-verify="required|textMaxlength|uniqueDeviceCode" readonly="readonly" style="cursor:default;background-color: #e5e5e5" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>终端名称</label>
				<div class="layui-input-inline">
					 <input type="text" name="deviceName" value="${model.deviceName}" lay-verify="required|textMaxlength|uniqueDeviceName" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
<!-- 			<div class="layui-inline"> -->
<!-- 				<label class="layui-form-label"><font class="required-dot">*</font>所属企业</label> -->
<!-- 				<div class="layui-input-inline" style=""> -->
<%-- 			         <input type="hidden" id="enterpriseId" name="enterpriseId" value="${polluteEnterprise.id}"> --%>
<%-- 				     <input type="text" name="enterpriseName" value="${polluteEnterprise.enterpriseName}" readonly="readonly" style="cursor:default;background-color: #e5e5e5" class="layui-input"> --%>
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="layui-inline">
				<label class="layui-form-label">终端图片</label>
				<div class="layui-input-block">
					<input type="hidden" id="photoPath" name="photoPath" lay-verify=""
						value="${model.photoPath}" autocomplete="off" class="layui-input">
					<!-- <button type="button" class="layui-btn layui-btn-light-blue" id="btn-upload"><i class="layui-icon">&#xe67c;</i>上传图片</button> -->
					<button type="button" class="layui-btn layui-btn-light-blue"
						id="deviceInfoImgBtn" style="width: 190px;">
						<i class="layui-icon">&#xe67c;</i>上传图片
					</button>
					<i id="bulletTypeImgLoad"
						class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"
						style="display: none; font-size: 20px;"></i> <i
						id="bulletTypeImgYes" class="layui-icon layui-icon-ok-circle"
						style="display: none; color: #5FB878; font-size: 20px;"
						title="上传图片成功"></i> <i id="bulletTypeImgNo"
						class="layui-icon layui-icon-close-fill"
						style="display: none; color: #F96768; font-size: 20px;"
						title="上传图片失败"></i>
						<input type="hidden" id="oldPhoto" value="${model.photoPath}" class="layui-input">
						<input type="hidden" name="isChangePhoto" value="false">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><span style="color:red">*</span>设备类型</label>
				<div class="layui-input-inline">
					<select name="deviceType" lay-verify="required">
						<option value="">请选择</option>
						<c:forEach var="item" items="${deviceTypeList}" varStatus="vs">
							<option value="${item.itemValue}" ${model.deviceType==item.itemValue?'selected="selected"':''}>${item.itemName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
<!-- 			<div class="layui-inline"> -->
<!-- 				<label class="layui-form-label"><font class="required-dot">*</font>设备型号</label> -->
<!-- 				<div class="layui-input-inline"> -->
<%-- 					<input type="text" name="deviceModel" value="${model.deviceModel}" lay-verify="required|textMaxlength" class="layui-input"> --%>
<!-- 				</div> -->
<!-- 			</div> -->

		</div>

	</div>
	<div style="width: 300px; float: left; text-align: center;">
		<img id="imgPreview" alt="图片预览" title="图片预览"
			src="device/deviceInfo/listfileLook.do?url=${upload_url}/${model.photoPath}" height="164" onerror="loadDefaultImg(this)" style="max-width: 300px;">
	</div>

	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">SIM卡号</label>
			<div class="layui-input-inline">
				<input type="text" name="simCard" value="${model.simCard}" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">运营商</label>
			<div class="layui-input-inline">
				<select name="simOperator" value="${model.simOperator}">
					<option value="移动" ${model.simOperator=='移动'?'selected="selected"':''}>移动</option>
					<option value="电信" ${model.simOperator=='电信'?'selected="selected"':''}>电信</option>
					<option value="联通" ${model.simOperator=='联通'?'selected="selected"':''}>联通</option>
				</select>
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label"><img onclick="showMapPage()" style="position:absolute;top:0;left: 28px" src="/css/images/map/position.png">经度</label>
			<div class="layui-input-inline">
				<input type="text" id="longitude" name="longitude" value="${model.longitude}" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">纬度</label>
			<div class="layui-input-inline">
				<input type="text" id="latitude" name="latitude" value="${model.latitude}" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">终端地址</label>
			<div class="layui-input-inline">
				<input type="text" id="address" name="address" value="${model.address}" class="layui-input">
			</div>
		</div>

	</div>


	<div class="layui-form-item"
		style="text-align: right; padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit=""
			lay-filter="deviceInfoButton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
</div>

<script type="text/javascript">
	(function($) {		
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
		    uniqueDeviceName: function(value){
		    	var flag = true;
		    	$.ajax({  
			         type : "get",  
			          url : "device/deviceInfo/checkDeviceNameExist.do",  
			          data : {str:value,id:$("input#id").val()},  
			          async : false,  
			          dataType: "json",
			          success : function(data){  
			          	flag = data.success;
			          }  
			     }); 
		    	if(!flag) return '终端名称已存在';
		    },
		    uniqueDeviceCode: function(value){
		    	var flag = true;
		    	$.ajax({  
			         type : "get",  
			          url : "device/deviceInfo/checkDeviceCodeExist.do",  
			          data : {str:value,id:$("input#id").val()},  
			          async : false,  
			          dataType: "json",
			          success : function(data){  
			          	flag = data.success;
			          }  
			     }); 
		    	if(!flag) return '终端编码已存在';
		    }
		});

		//监听提交
		form.on('submit(deviceInfoButton)', function(data) {
			deviceInfoAddEditSave(data.field,2);
			return false;
		});

		upload.render({
			elem : '#deviceInfoImgBtn',
			url : 'upload.do?type=${upload_url}&size=2048',
			accept : 'images',
			size : 2048, //限制文件大小，单位 KB
			xhr : xhrOnProgress,
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
			before : function(obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
				//this.data['specifiedName'] = $("#bulletTypeName").val();
				$("#deviceInfoForm #bulletTypeImgLoad").show();
				$("#deviceInfoForm #bulletTypeImgYes").hide();
				$("#deviceInfoForm #bulletTypeImgNo").hide();
			},
			done : function(data) {
				$("#deviceInfoForm #bulletTypeImgLoad").hide();
				if (data.success) {
					$("#deviceInfoForm #photoPath").val(data.data.fileName);
					var _i = data.data.fullFileName + "?t=" + Math.random();
					$("#deviceInfoForm #bulletTypeImgYes").show();
					$("#deviceInfoForm #bulletTypeImgNo").hide();
				} else {
					$("#deviceInfoForm #bulletTypeImgYes").hide();
					$("#deviceInfoForm #bulletTypeImgNo").show();
					openErrAlert(data.msg);
				}
			},
			error : function(index, upload) {
				$("#deviceInfoForm #bulletTypeImgLoad").hide();
				$("#deviceInfoForm #bulletTypeImgYes").hide();
				$("#deviceInfoForm #bulletTypeImgNo").show();
			}
		});

	})(jQuery)
	

       
</script>