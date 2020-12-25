<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div id="maintenanceRecordForm" class="layui-form" action="" style="padding: 20px">
	<div style="width: 660px; float: left">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>所属企业</label>
				<div class="layui-input-inline" style="">
				     	<select id="deviceCode" name="deviceCode" lay-verify="required" ${enterpriseInfoListSize==1?'disabled="disabled"':''}>
						<c:forEach var="item" items="${enterpriseInfoList}" varStatus="vs">
							<option value="${item.deviceCode}" ${vs.index==0?'selected="selected"':''} >${item.enterpriseName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>机组编号</label>
				<div class="layui-input-inline" style="">
				     <select name="condensingDeviceNum" lay-verify="required">
						<option value="">请选择</option>
						<option value="1">1</option>
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
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>维修时间</label>
				<div class="layui-input-inline">
					 <input type="text" id="maintenanceTimeStr" name="maintenanceTimeStr" class="Wdate" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: default;height: 28px;width: 183px" class="layui-input search-maxlenght">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>维修时长</label>
				<div class="layui-input-inline">
					 <input type="text" name="maintenanceDuration" lay-verify="required|textMaxlength" class="layui-input">
				</div>
			</div>
		</div>
     <div class="layui-form-item layui-form-text">
		<label class="layui-form-label"><font class="required-dot">*</font>维修内容</label>
		<div class="layui-input-block">
		    <textarea placeholder="" name="maintenanceContent" style="width:515px" lay-verify="required|textareaLen" maxlength="500" class="layui-textarea"></textarea> 
	     </div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
		    <textarea placeholder="" name="remark" style="width:515px" lay-verify="textareaLen" maxlength="500" class="layui-textarea"></textarea> 
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
			maintenanceRecordAddEditSave(data.field,1);
			return false;
		});

	})(jQuery)
	

       
</script>