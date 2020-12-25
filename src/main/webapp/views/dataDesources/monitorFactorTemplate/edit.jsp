<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/dataDesources/monitorFactor.js"></script>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css" />
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
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
		<label class="layui-form-label"><font class="required-dot">*</font>监测因子名称</label>
		<div class="layui-input-block">
			<input type="text" name="factorName" value="${model.factorName}" lay-verify="required|textMaxlength" class="layui-input", onchange="changeWarningText(this.value)">
		</div>
		<div style="display: none">
			<input type="text" value="[${model.factorName}]" name="oldFactorName">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>监测因子编码</label>
		<div class="layui-input-block">
			<input type="text" name="factorCode" value="${model.factorCode}" lay-verify="required|textMaxlength" readonly="readonly" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><span style="color:red">*</span>监测因子标签</label>
		<div class="layui-input-block">
			<select multiple="" id="factorTag" name="factorTag" xm-select="select7_4" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="200px">
				<option value="">请选择标签名称</option>
				<c:forEach var="value" items="${model.factorTag.split(',')}" varStatus="vs">
					<c:forEach var="item" items="${factorTagList}" varStatus="vs">
						<option value="${item.tagName}" <c:if test="${value==item.tagName}"> selected </c:if> > ${item.tagName}</option>
					</c:forEach>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">监测因子单位</label>
		<div class="layui-input-block">
			<input type="text" name="factorUnit" value="${model.factorUnit}" lay-verify="textMaxlength" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><span style="color:#ff0000">*</span>设备名</label>
		<div class="layui-input-block">
			<select name="machineType" lay-verify="required" disabled="disabled">
				<option value="">请选择</option>
				<c:forEach var="item" items="${machineNameList}" varStatus="vs">
					<option value="${item.machineType}" ${model.machineType==item.machineType?'selected="selected"':''}>${item.machineName}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><span style="color:red">*</span>设备开启开关</label>
		<div class="layui-input-block">
			<input type="checkbox" name="isStartSwitch" value="是否是设备开启开关" lay-verify="textMaxlength" class="layui-input" disabled="disabled">
		</div>
		<div style="display: none">
			<input type="text" value="${model.startSwitch}" name="startSwitch">
		</div>
	</div>
  <div class="layui-form-item">
		<label class="layui-form-label"><span style="color:red">*</span>运转时间</label>
		<div class="layui-input-block">
      <input type="checkbox" name="isRuntime" value="1" ${model.isRuntime==1?'checked':''} lay-verify="textMaxlength" class="layui-input" lay-filter="isRuntime">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><span style="color:red">*</span>数据类型</label>
			<div class="layui-input-block">
				<select name="typeId" lay-verify="required" disabled="disabled">
					<option value="">请选择</option>
					<option value="1" ${model.typeId==1?'selected="selected"':''}>数值</option>
					<option value="2" ${model.typeId==2?'selected="selected"':''}>预警</option>
					<option value="3" ${model.typeId==3?'selected="selected"':''}>故障</option>
					<option value="4" ${model.typeId==4?'selected="selected"':''}>开关</option>
				</select>
			</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>数据精度</label>
		<div class="layui-input-block">
			<input type="text" name="dataAccuracy" value="${model.dataAccuracy}" lay-verify="required|numFlag2" maxlength="32" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>保留小数位</label>
		<div class="layui-input-block">
			<input type="text" name="decimalDigits" value="${model.decimalDigits}" lay-verify="required|numFlag" maxlength="10" class="layui-input">
		</div>
	</div>
		<div class="layui-form-item">
		<label class="layui-form-label"><span style="color:red">*</span>是否报警</label>
			<div class="layui-input-block">
				<select name="alarmState" lay-verify="required">
					<option value="">请选择</option>
					<option value="1" ${model.alarmState==1?'selected="selected"':''}>报警</option>
					<option value="0" ${model.alarmState==0?'selected="selected"':''}>不报警</option>
				</select>
			</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>下限</label>
		<div class="layui-input-block">
			<input type="text" name="lowerLimit" value="${model.lowerLimit}" lay-verify="required|numFlag2" maxlength="16" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>上限</label>
		<div class="layui-input-block">
			<input type="text" name="upperLimit" value="${model.upperLimit}" lay-verify="required|numFlag2" maxlength="16" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">低值报警信息</label>
		<div class="layui-input-block">
			<input type="text" name="lowerLimitText" value="${model.lowerLimitText}" lay-verify="textMaxlength" class="layui-input" disabled>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">高值报警信息</label>
		<div class="layui-input-block">
			<input type="text" name="upperLimitText" value="${model.upperLimitText}" lay-verify="textMaxlength" class="layui-input" disabled>
		</div>
	</div>
	<div class="layui-form-item" style="text-align: right;">
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="monitorFactorTemplateButton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
</div>

<script>

$(function(){
	$('[placeholder]').placeholder();
	initWarningText();
	initCheckBox();
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
	    uniqueFactorName: function(value){
	    	var flag = true;
	    	$.ajax({
		         type : "get",
		          url : "dataDesources/monitorFactorTemplate/checkFactorNameExist.do",
		          data : {str:value,id:$("input#id").val()},
		          async : false,
		          dataType: "json",
		          success : function(data){
		          	flag = data.success;
		          }
		     });
	    	if(!flag) return '监测因子名称已存在';
	    },
	    uniqueFactorCode: function(value){
	    	var flag = true;
	    	$.ajax({
		         type : "get",
		          url : "dataDesources/monitorFactorTemplate/checkFactorCodeExist.do",
		          data : {str:value,id:$("input#id").val()},
		          async : false,
		          dataType: "json",
		          success : function(data){
		          	flag = data.success;
		          }
		     });
	    	if(!flag) return '监测因子编码已存在';
	    }
	});

	//监听提交
	form.on('submit(monitorFactorTemplateButton)', function(data) {
		monitorFactorTemplateAddEditSave(data.field,2);
		return false;
	});
</script>
