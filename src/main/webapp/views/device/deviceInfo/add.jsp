<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css" />
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>

<div id="deviceInfoForm" class="layui-form" action="" style="padding: 20px">
	<div style="width: 660px; float: left">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>终端编码</label>
				<div class="layui-input-inline" style="">
				     <input type="text" name="deviceCode" lay-verify="required|textMaxlength|uniqueDeviceCode" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>终端名称</label>
				<div class="layui-input-inline">
					 <input type="text" name="deviceName" lay-verify="required|textMaxlength|uniqueDeviceName" class="layui-input">
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
<!-- 					<input type="text" name="deviceModel" lay-verify="required|textMaxlength" class="layui-input"> -->
<!-- 				</div> -->
<!-- 			</div> -->

		</div>

	</div>
	<div style="width: 300px; float: left; text-align: center;">
		<img id="imgPreview" alt="图片预览" title="图片预览"
			src="" height="164"
			onerror="loadDefaultImg(this)" style="max-width: 300px;">
	</div>

	<div class="layui-form-item">
		<%--		<div class="layui-inline">--%>
		<%--			<label class="layui-form-label"><font class="required-dot">*</font>绑定机组</label>--%>
		<%--			<div class="layui-input-inline">--%>
		<%--				<select name="condensingDeviceNumStr" lay-verify="required" xm-select="select7_3" xm-select-search="" xm-select-show-count="2" xm-select-height="36px" xm-select-width="250px">--%>
		<%--					  <option value="">请选择机组</option>--%>
		<%--					  <option value="00" ${model.condensingDeviceNumStr.indexOf("00") != -1 ?'selected="selected"':''}>0</option>--%>
		<%--					  <option value="01" ${model.condensingDeviceNumStr.indexOf("01") != -1 ?'selected="selected"':''}>1</option>--%>
		<%--					  <option value="02" ${model.condensingDeviceNumStr.indexOf("02") != -1 ?'selected="selected"':''}>2</option>--%>
		<%--					  <option value="03" ${model.condensingDeviceNumStr.indexOf("03") != -1 ?'selected="selected"':''}>3</option>--%>
		<%--					  <option value="04" ${model.condensingDeviceNumStr.indexOf("04") != -1 ?'selected="selected"':''}>4</option>--%>
		<%--					  <option value="05" ${model.condensingDeviceNumStr.indexOf("05") != -1 ?'selected="selected"':''}>5</option>--%>
		<%--					  <option value="06" ${model.condensingDeviceNumStr.indexOf("06") != -1 ?'selected="selected"':''}>6</option>--%>
		<%--					  <option value="07" ${model.condensingDeviceNumStr.indexOf("07") != -1 ?'selected="selected"':''}>7</option>--%>
		<%--					  <option value="08" ${model.condensingDeviceNumStr.indexOf("08") != -1 ?'selected="selected"':''}>8</option>--%>
		<%--					  <option value="09" ${model.condensingDeviceNumStr.indexOf("09") != -1 ?'selected="selected"':''}>9</option>--%>
		<%--					  <option value="10" ${model.condensingDeviceNumStr.indexOf("10") != -1 ?'selected="selected"':''}>10</option>--%>
		<%--					  <option value="11" ${model.condensingDeviceNumStr.indexOf("11") != -1 ?'selected="selected"':''}>11</option>--%>
		<%--					  <option value="12" ${model.condensingDeviceNumStr.indexOf("12") != -1 ?'selected="selected"':''}>12</option>--%>
		<%--					  <option value="13" ${model.condensingDeviceNumStr.indexOf("13") != -1 ?'selected="selected"':''}>13</option>--%>
		<%--					  <option value="14" ${model.condensingDeviceNumStr.indexOf("14") != -1 ?'selected="selected"':''}>14</option>--%>
		<%--					  <option value="15" ${model.condensingDeviceNumStr.indexOf("15") != -1 ?'selected="selected"':''}>15</option>--%>
		<%--					  <option value="16" ${model.condensingDeviceNumStr.indexOf("16") != -1 ?'selected="selected"':''}>16</option>--%>
		<%--					  <option value="17" ${model.condensingDeviceNumStr.indexOf("17") != -1 ?'selected="selected"':''}>17</option>--%>
		<%--					  <option value="18" ${model.condensingDeviceNumStr.indexOf("18") != -1 ?'selected="selected"':''}>18</option>--%>
		<%--					  <option value="19" ${model.condensingDeviceNumStr.indexOf("19") != -1 ?'selected="selected"':''}>19</option>--%>
		<%--					  <option value="20" ${model.condensingDeviceNumStr.indexOf("20") != -1 ?'selected="selected"':''}>20</option>--%>
		<%--					  <option value="21" ${model.condensingDeviceNumStr.indexOf("21") != -1 ?'selected="selected"':''}>21</option>--%>
		<%--					  <option value="22" ${model.condensingDeviceNumStr.indexOf("22") != -1 ?'selected="selected"':''}>22</option>--%>
		<%--				</select>--%>
		<%--			</div>--%>
		<%--		  </div>--%>
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
					<option value="" ${model.simOperator==''?'selected="selected"':''}>请选择运营商</option>
					<option value="移动" ${model.simOperator=='移动'?'selected="selected"':''}>移动</option>
					<option value="电信" ${model.simOperator=='电信'?'selected="selected"':''}>电信</option>
					<option value="联通" ${model.simOperator=='联通'?'selected="selected"':''}>联通</option>
				</select>
			</div>
		</div>

	</div>

	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label"><img onclick="showMapPage()" style="position:absolute;top:0;left: 28px" src="/css/images/map/position.png"></font>经度</label>
			<div class="layui-input-inline">
				<input type="text" id="longitude" name="longitude" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">纬度</label>
			<div class="layui-input-inline">
				<input type="text" id="latitude" name="latitude" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">终端地址</label>
			<div class="layui-input-inline">
				<input type="text" id="address" name="address" class="layui-input">
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

$("#deviceInfoForm select[name='monitorFactors']").SumoSelect({
    placeholder:"请选择监测因子",
    search: true, 
    searchText: '请选择监测因子', 
    noMatch: '没有匹配 "{0}" 的项' ,
    csvDispCount: 3,
    captionFormat:'选中 {0} 项',
    okCancelInMulti:true,
    selectAll:true,
    captionFormatAllSelected: "全选", 
    locale : ['确定', '取消', '全选'] 
});

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
			deviceInfoAddEditSave(data.field,1);
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