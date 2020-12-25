<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div id="polluteEnterpriseForm" class="layui-form" action="" style="padding: 20px">
    <input type="hidden" id="id" name="id" value="${model.id}">
	<div style="width: 660px; float: left">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>所属区域</label>
				<div class="layui-input-inline" style="">
					<input type="hidden" id="orgId" name="orgId" value="${model.orgId}">
					<input type="text" id="orgName" name="orgName" value="${model.orgName}" lay-verify="required" onclick="orgTreeShow()" readonly="readonly" style="cursor:default;background-color: #e5e5e5" class="layui-input">
				</div>
			</div>
			<div class="tree" id="orgTreeDiv" style="position: absolute;top: 51px;left: 130px;width:230px;background: #fff;display: none;z-index: 999;height: 260px;border: 0">
			    <ul id="orgTree" class="ztree"></ul>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>所属流域</label>
				<div class="layui-input-inline" style="">
					<input type="hidden" id="basinId" name="basinId" value="${model.basinId}">
					<input type="text" id="basinName" name="basinName" value="${model.basinName}" lay-verify="required" onclick="basinTreeShow()" readonly="readonly" style="cursor:default;background-color: #e5e5e5" class="layui-input">
				</div>
			</div>
			<div class="tree" id="basinTreeDiv" style="position: absolute;top: 51px;left: 454px;width:230px;background: #fff;display: none;z-index: 999;height: 260px;border: 0">
			    <ul id="basinTree" class="ztree"></ul>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>企业名称</label>
				<div class="layui-input-inline" style="">
				     <input type="text" name="enterpriseName" value="${model.enterpriseName}" lay-verify="required|textMaxlength|polluteEnterpriseuniqueName|specialChar" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">企业简称</label>
				<div class="layui-input-inline">
					 <input type="text" name="abbreviation" value="${model.abbreviation}" lay-verify="textMaxlength|specialChar" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><span style="color:red">*</span>关注程度</label>
				<div class="layui-input-inline">
					<select name="concernDegree" lay-verify="required">
						<option value="">请选择</option>
						<option value="一般污染企业" ${model.concernDegree=='一般污染企业'?'selected="selected"':''}>一般污染企业</option>
						<option value="重点污染企业" ${model.concernDegree=='重点污染企业'?'selected="selected"':''}>重点污染企业</option>
						<option value="非重点污染企业" ${model.concernDegree=='非重点污染企业'?'selected="selected"':''}>非重点污染企业</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">企业照片</label>
				<div class="layui-input-block">
					<input type="hidden" id="photoPath" name="photoPath" lay-verify=""
						value="${model.photoPath}" autocomplete="off" class="layui-input">
					<!-- <button type="button" class="layui-btn layui-btn-light-blue" id="btn-upload"><i class="layui-icon">&#xe67c;</i>上传图片</button> -->
					<button type="button" class="layui-btn layui-btn-light-blue"
						id="polluteEnterpriseImgBtn" style="width: 190px;">
						<i class="layui-icon">&#xe67c;</i>上传照片
					</button>
					<i id="bulletTypeImgLoad"
						class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"
						style="display: none; font-size: 20px;"></i> <i
						id="bulletTypeImgYes" class="layui-icon layui-icon-ok-circle"
						style="display: none; color: #5FB878; font-size: 20px;"
						title="上传照片成功"></i> <i id="bulletTypeImgNo"
						class="layui-icon layui-icon-close-fill"
						style="display: none; color: #F96768; font-size: 20px;"
						title="上传照片失败"></i>
						<input type="hidden" id="oldPhoto" value="${model.photoPath}" class="layui-input">
						<input type="hidden" name="isChangePhoto" value="false">
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>法人代表</label>
				<div class="layui-input-inline">
					<input type="text" name="legalRepresentative" value="${model.legalRepresentative}" lay-verify="required|textMaxlength|specialChar" class="layui-input">
				</div>
			</div>

			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>信用代码</label>
				<div class="layui-input-inline">
					<input type="text" name="enterpriseCreditCode" value="${model.enterpriseCreditCode}" lay-verify="required|textMaxlength|specialChar" class="layui-input">
				</div>
			</div>

		</div>
	</div>
	<div style="width: 300px; float: left; text-align: center;">
		<img id="imgPreview" alt="图片预览" title="图片预览"
			src="${upload_url}/${model.photoPath}" height="164"
			onerror="loadDefaultImg(this)" style="max-width: 300px;">
	</div>
	<div class="layui-form-item">

		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>联系人</label>
			<div class="layui-input-inline">
				<input type="text" name="contactName" value="${model.contactName}" lay-verify="required|textMaxlength|specialChar" class="layui-input">
			</div>
		</div>


		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>联系人电话</label>
			<div class="layui-input-inline">
				<input type="text" name="contactPhone" value="${model.contactPhone}" lay-verify="required|textMaxlength|specialChar|phoneLength" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">企业类型</label>
			<div class="layui-input-inline">
				<select name="enterpriseType">
					<option value="">请选择</option>
					<option value="有限责任公司" ${model.enterpriseType=='有限责任公司'?'selected="selected"':''}>有限责任公司</option>
					<option value="个人独资企业" ${model.enterpriseType=='个人独资企业'?'selected="selected"':''}>个人独资企业</option>
					<option value="合伙企业" ${model.enterpriseType=='合伙企业'?'selected="selected"':''}>合伙企业</option>
					<option value="全民所有制企业" ${model.enterpriseType=='全民所有制企业'?'selected="selected"':''}>全民所有制企业</option>
					<option value="上市企业" ${model.enterpriseType=='上市企业'?'selected="selected"':''}>上市企业</option>
				</select>
			</div>
		</div>

	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">企业规模</label>
			<div class="layui-input-inline">
				<select name="enterpriseScale">
					<option value="">请选择</option>
					<option value="大型" ${model.enterpriseScale=='大型'?'selected="selected"':''}>大型</option>
					<option value="中型" ${model.enterpriseScale=='中型'?'selected="selected"':''}>中型</option>
					<option value="小型" ${model.enterpriseScale=='小型'?'selected="selected"':''}>小型</option>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">所属园区</label>
			<div class="layui-input-inline">
				<select name="affiliatedPark">
					<option value="">请选择</option>
					<option value="园区1" ${model.affiliatedPark=='园区1'?'selected="selected"':''}>园区1</option>
					<option value="园区2" ${model.affiliatedPark=='园区2'?'selected="selected"':''}>园区2</option>
					<option value="园区3" ${model.affiliatedPark=='园区3'?'selected="selected"':''}>园区3</option>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">行业类别</label>
			<div class="layui-input-inline">
				<select name="industryCategory">
					<option value="">请选择</option>
					<option value="医药制造行业" ${model.industryCategory=='医药制造行业'?'selected="selected"':''}>医药制造行业</option>
					<option value="家具制造行业" ${model.industryCategory=='家具制造行业'?'selected="selected"':''}>家具制造行业</option>
					<option value="印刷行业" ${model.industryCategory=='印刷行业'?'selected="selected"':''}>印刷行业</option>
					<option value="有机化学行业" ${model.industryCategory=='有机化学行业'?'selected="selected"':''}>有机化学行业</option>
					<option value="其他行业" ${model.industryCategory=='其他行业'?'selected="selected"':''}>其他行业</option>
				</select>
			</div>
		</div>

	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">控制级别</label>
			<div class="layui-input-inline">
				<select name="controlLevel">
					<option value="">请选择</option>
					<option value="国控" ${model.controlLevel=='国控'?'selected="selected"':''}>国控</option>
					<option value="省控" ${model.controlLevel=='省控'?'selected="selected"':''}>省控</option>
					<option value="市控" ${model.controlLevel=='市控'?'selected="selected"':''}>市控</option>
					<option value="县控" ${model.controlLevel=='县控'?'selected="selected"':''}>县控</option>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">排水类型</label>
			<div class="layui-input-inline">
				<select name="drainageType">
					<option value="">请选择</option>
					<option value="处理后排放" ${model.drainageType=='处理后排放'?'selected="selected"':''}>处理后排放</option>
					<option value="直接排放" ${model.drainageType=='直接排放'?'selected="selected"':''}>直接排放</option>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">排口位置</label>
			<div class="layui-input-inline">
				<input type="text" name="outletPosition" value="${model.outletPosition}" class="layui-input">
			</div>
		</div>

	</div>


	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">排向流域</label>
			<div class="layui-input-inline">
				<input type="text" name="drainageBasin" value="${model.drainageBasin}" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">河段</label>
			<div class="layui-input-inline">
				<input type="text" name="drainageRiver" value="${model.drainageRiver}" class="layui-input">
			</div>
		</div>
	</div>

	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label"><img onclick="showMapPage()" style="position:absolute;top:0;left: 28px" src="/css/images/map/position.png"><font class="required-dot">*</font>经度</label>
			<div class="layui-input-inline">
				<input type="text" id="longitude" name="longitude" value="${model.longitude}" lay-verify="required" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>纬度</label>
			<div class="layui-input-inline">
				<input type="text" id="latitude" name="latitude" value="${model.latitude}" lay-verify="required" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label"><font class="required-dot">*</font>企业地址</label>
			<div class="layui-input-inline">
				<input type="text" id="address" name="address" value="${model.address}" lay-verify="required" class="layui-input">
			</div>
		</div>

	</div>


	<div class="layui-form-item"
		style="text-align: right; padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit=""
			lay-filter="polluteEnterpriseButton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		showZtreeFun($("#orgTree"),"system/sysOrg/getOrgTree.do",orgTreeClick,orgTreeShow);
		showZtreeFun($("#basinTree"),"system/sysBasin/getsysBasinTree.do",basinTreeClick,basinTreeShow);
		
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
		    polluteEnterpriseuniqueName: function(value){
		    	var flag = true;
		    	$.ajax({  
			         type : "get",  
			          url : "environmentalQuality/pollute/checkEnterpriseNameExist.do",  
			          data : {str:value,id:$("input#polluteEnterpriseId").val()},  
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
		form.on('submit(polluteEnterpriseButton)', function(data) {
			polluteEnterpriseAddEditSave(data.field,2);
			return false;
		});

		upload.render({
			elem : '#polluteEnterpriseImgBtn',
			url : 'upload.do?type=${upload_url}&size=2048',
			accept : 'images',
			size : 2048, //限制文件大小，单位 KB
			xhr : xhrOnProgress,
			progress : function(value) {
			},
			data : {
			//type:'${upload_url}'
			},
			before : function(obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
				//this.data['specifiedName'] = $("#bulletTypeName").val();
				$("#polluteEnterpriseForm #bulletTypeImgLoad").show();
				$("#polluteEnterpriseForm #bulletTypeImgYes").hide();
				$("#polluteEnterpriseForm #bulletTypeImgNo").hide();
			},
			done : function(data) {
				$("#polluteEnterpriseForm #bulletTypeImgLoad").hide();
				if (data.success) {
					$("#polluteEnterpriseForm #photoPath").val(data.data.fileName);
					var _i = data.data.fullFileName + "?t=" + Math.random();
					$("#polluteEnterpriseForm #imgPreview").attr("src", "" + _i);
					$("#polluteEnterpriseForm #bulletTypeImgYes").show();
					$("#polluteEnterpriseForm #bulletTypeImgNo").hide();
				} else {
					$("#polluteEnterpriseForm #bulletTypeImgYes").hide();
					$("#polluteEnterpriseForm #bulletTypeImgNo").show();
					openErrAlert(data.msg);
				}
			},
			error : function(index, upload) {
				$("#polluteEnterpriseForm #bulletTypeImgLoad").hide();
				$("#polluteEnterpriseForm #bulletTypeImgYes").hide();
				$("#polluteEnterpriseForm #bulletTypeImgNo").show();
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
    
    function basinTreeClick(treeNode) {
        $("#basinName").val(treeNode.name);
        $("#basinId").val(treeNode.id);
    }

     function basinTreeShow() {
        $("#basinTreeDiv").toggle();
    }
       
</script>