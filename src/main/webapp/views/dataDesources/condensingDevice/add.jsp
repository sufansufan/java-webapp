<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div id="condensingDeviceForm" class="layui-form" action="" style="padding: 20px">
	<div style="width: 660px; float: left">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>所属企业</label>
				<div class="layui-input-inline" style="">
				     	<select id="enterpriseId" name="enterpriseId" lay-verify="required">
						<option value="">请选择</option>
						<c:forEach var="item" items="${enterpriseInfoList}" varStatus="vs">
							<option value="${item.id}">${item.enterpriseName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">设备图片</label>
				<div class="layui-input-block">
					<input type="hidden" id="photoPath" name="photoPath" lay-verify=""
						value="${model.photoPath}" autocomplete="off" class="layui-input">
					<!-- <button type="button" class="layui-btn layui-btn-light-blue" id="btn-upload"><i class="layui-icon">&#xe67c;</i>上传图片</button> -->
					<button type="button" class="layui-btn layui-btn-light-blue"
						id="condensingDeviceImgBtn" style="width: 190px;">
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
				<label class="layui-form-label"><font class="required-dot">*</font>设备编号</label>
				<div class="layui-input-inline" style="">
				     <select name="machineNo" lay-verify="required|uniqueCondensingDeviceNum">
						<option value="">请选择</option>
						<option value=``"1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>设备名称</label>
				<div class="layui-input-inline">
					 <input type="text" name="machineName" lay-verify="required|textMaxlength" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><span style="color:red">*</span>设备类型</label>
				<div class="layui-input-inline">
					<select name="machineType" lay-verify="required">
						<option value="">请选择</option>
						<c:forEach var="item" items="${machineTypeList}" varStatus="vs">
							<option value="${item.itemValue}" ${model.machineType==item.itemName?'selected="selected"':''}>${item.itemName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>设备型号</label>
				<div class="layui-input-inline">
					<input type="text" name="machineModel" lay-verify="required|textMaxlength" class="layui-input">
				</div>
			</div>

		</div>

	</div>
	<div style="width: 300px; float: left; text-align: center;">
		<img id="imgPreview" alt="图片预览" title="图片预览"
			src="" height="164"
			onerror="loadDefaultImg(this)" style="max-width: 300px;">
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>监控设备</label>
			<div class="layui-input-inline" style="">
				<select name="deviceCode" lay-verify="required">
					<option value="">请选择</option>
					<c:forEach var="item" items="${deviceInfo}" varStatus="vs">
						<option value="${item.deviceCode}" ${model.deviceName==item.deviceName?'selected="selected"':''}>${item.deviceName}</option>
					</c:forEach>
				</select>
			</div>
		</div>

	</div>
	<div class="layui-form-item">
		  	<div class="layui-inline">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-inline">
					<input type="text" name="remark" class="layui-input">
				</div>
			</div>

	</div>


	<div class="layui-form-item"
		style="text-align: right; padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit=""
			lay-filter="condensingDeviceButton">提交</button>
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
		    uniqueCondensingDeviceNum: function(value){
		    	var flag = true;
		    	$.ajax({
			         type : "get",
			          url : "dataDesources/condensingDevice/checkCondensingDeviceNumExist.do",
			          data : {str:value,id:$("input#id").val(),enterpriseId:$("#enterpriseId").val()},
			          async : false,
			          dataType: "json",
			          success : function(data){
			          	flag = data.success;
			          }
			     });
		    	if(!flag) return '该企业下的机组编号已存在';
		    }
		});

		//监听提交
		form.on('submit(condensingDeviceButton)', function(data) {
			condensingDeviceAddEditSave(data.field,1);
			return false;
		});

		upload.render({
			elem : '#condensingDeviceImgBtn',
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
				$("#condensingDeviceForm #bulletTypeImgLoad").show();
				$("#condensingDeviceForm #bulletTypeImgYes").hide();
				$("#condensingDeviceForm #bulletTypeImgNo").hide();
			},
			done : function(data) {
				$("#condensingDeviceForm #bulletTypeImgLoad").hide();
				if (data.success) {
					$("#condensingDeviceForm #photoPath").val(data.data.fileName);
					var _i = data.data.fullFileName + "?t=" + Math.random();
					$("#condensingDeviceForm #bulletTypeImgYes").show();
					$("#condensingDeviceForm #bulletTypeImgNo").hide();
				} else {
					$("#condensingDeviceForm #bulletTypeImgYes").hide();
					$("#condensingDeviceForm #bulletTypeImgNo").show();
					openErrAlert(data.msg);
				}
			},
			error : function(index, upload) {
				$("#condensingDeviceForm #bulletTypeImgLoad").hide();
				$("#condensingDeviceForm #bulletTypeImgYes").hide();
				$("#condensingDeviceForm #bulletTypeImgNo").show();
			}
		});

	})(jQuery)



</script>
