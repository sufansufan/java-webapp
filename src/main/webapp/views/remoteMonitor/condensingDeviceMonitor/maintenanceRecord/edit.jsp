<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div id="maintenanceRecordForm" class="layui-form" action="" style="padding: 20px">
  <input type="hidden" id="id" name="id" value="${model.id}">
	<div style="width: 660px; float: left">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>所属企业</label>
				<div class="layui-input-inline" style="">
				     	<select id="deviceCode" name="deviceCode" lay-verify="required" ${enterpriseInfoListSize==1?'disabled="disabled"':''}>
						<c:forEach var="item" items="${enterpriseInfoList}" varStatus="vs">
							<option value="${item.deviceCode}" ${item.deviceCode==model.deviceCode?'selected="selected"':''}>${item.enterpriseName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>机组编号</label>
				<div class="layui-input-inline" style="">
				     <select name="condensingDeviceNum" lay-verify="required">
						<option value="">请选择</option>
						<option value="1" ${model.condensingDeviceNum=='1'?'selected="selected"':''}>1</option>
						<option value="2" ${model.condensingDeviceNum=='2'?'selected="selected"':''}>2</option>
						<option value="3" ${model.condensingDeviceNum=='3'?'selected="selected"':''}>3</option>
						<option value="4" ${model.condensingDeviceNum=='4'?'selected="selected"':''}>4</option>
						<option value="5" ${model.condensingDeviceNum=='5'?'selected="selected"':''}>5</option>
						<option value="6" ${model.condensingDeviceNum=='6'?'selected="selected"':''}>6</option>
						<option value="7" ${model.condensingDeviceNum=='7'?'selected="selected"':''}>7</option>
						<option value="8" ${model.condensingDeviceNum=='8'?'selected="selected"':''}>8</option>
						<option value="9" ${model.condensingDeviceNum=='9'?'selected="selected"':''}>9</option>
						<option value="10" ${model.condensingDeviceNum=='10'?'selected="selected"':''}>10</option>
						<option value="11" ${model.condensingDeviceNum=='11'?'selected="selected"':''}>11</option>
						<option value="12" ${model.condensingDeviceNum=='12'?'selected="selected"':''}>12</option>
						<option value="13" ${model.condensingDeviceNum=='13'?'selected="selected"':''}>13</option>
						<option value="14" ${model.condensingDeviceNum=='14'?'selected="selected"':''}>14</option>
						<option value="15" ${model.condensingDeviceNum=='15'?'selected="selected"':''}>15</option>
						<option value="16" ${model.condensingDeviceNum=='16'?'selected="selected"':''}>16</option>
						<option value="17" ${model.condensingDeviceNum=='17'?'selected="selected"':''}>17</option>
						<option value="18" ${model.condensingDeviceNum=='18'?'selected="selected"':''}>18</option>
						<option value="19" ${model.condensingDeviceNum=='19'?'selected="selected"':''}>19</option>
						<option value="20" ${model.condensingDeviceNum=='20'?'selected="selected"':''}>20</option>
						<option value="21" ${model.condensingDeviceNum=='21'?'selected="selected"':''}>21</option>
						<option value="22" ${model.condensingDeviceNum=='22'?'selected="selected"':''}>22</option>
					</select>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>维修时间</label>
				<div class="layui-input-inline">
					 <input type="text" id="maintenanceTimeStr" name="maintenanceTimeStr" class="Wdate" value="<fmt:formatDate value="${model.maintenanceTime}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px;width: 183px" class="layui-input search-maxlenght">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>维修时长</label>
				<div class="layui-input-inline">
					 <input type="text" name="maintenanceDuration" value="${model.maintenanceDuration}" lay-verify="required|textMaxlength" class="layui-input">
				</div>
			</div>
		</div>
     <div class="layui-form-item layui-form-text">
		<label class="layui-form-label"><font class="required-dot">*</font>维修内容</label>
		<div class="layui-input-block">
		    <textarea placeholder="" name="maintenanceContent" style="width:515px" lay-verify="required|textareaLen" maxlength="500" class="layui-textarea">${model.maintenanceContent}</textarea> 
	     </div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
		    <textarea placeholder="" name="remark" style="width:515px" lay-verify="textareaLen" maxlength="500" class="layui-textarea">${model.remark}</textarea> 
	     </div>
	</div>

	</div>
	



	<div class="layui-form-item"
		style="text-align: right; padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit=""
			lay-filter="maintenanceRecordButton">提交</button>
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
			textareaLen: function(value){
		        if(value.length > 500){
		            return '长度超过500个字符';
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
		});

		//监听提交
		form.on('submit(maintenanceRecordButton)', function(data) {
			maintenanceRecordAddEditSave(data.field,2);
			return false;
		});

	})(jQuery)
	

       
</script>