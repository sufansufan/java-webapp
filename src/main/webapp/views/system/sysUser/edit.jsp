<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div id="sysUserForm" class="layui-form" action="" style="padding:20px">
	<input type="hidden" id="sysUserid" name="id" value="${model.id}">
	<input type="hidden" id="source_type" name="type" value="${type}">
	<div style="width:660px;float:left">
		<div class="layui-form-item">
			<div class="layui-inline">
			    <label class="layui-form-label">所属组织</label>
			    <div class="layui-input-inline" style="">
	  				<input type="hidden" name="orgId" value="${sysorg.id}">
	  				<input type="text" name="orgName" value="${sysorg.orgName}" disabled class="layui-input">
			    </div>
		    </div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>账号名</label>
				<div class="layui-input-inline">
					<input type="text" name="userCode" lay-verify="required|sysUsermaxlength|sysUserunique|specialChar" ${model.id!=null?'disabled':'' } value="${model.userCode }" autocomplete="off" placeholder="请输入用户编号" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
			    <label class="layui-form-label"><font class="required-dot">*</font>用户名称</label>
			    <div class="layui-input-inline">
	   				<input type="text" name="userName" lay-verify="required|sysUsermaxlength|specialChar" value="${model.userName }" autocomplete="off" placeholder="请输入用户名称" class="layui-input">
			    </div>
	    	</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>平台密码</label>
				<div class="layui-input-inline">
					<input type="password" id="userPassword" name="userPassword" lay-verify="required|password" value="${fn:substring(model.userPassword,0,15)}" autocomplete="off" placeholder="请输入6~15位密码" class="layui-input">
					<input type="hidden" id="oldPassword" value="${fn:substring(model.userPassword,0,15)}" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font class="required-dot">*</font>确认密码</label>
				<div class="layui-input-inline">
					<input type="password" id="userPassword2" name="userPassword2" lay-verify="required|sysUserequal" value="${fn:substring(model.userPassword,0,15)}" autocomplete="off" placeholder="请再次输入密码" class="layui-input">
				</div>
			</div>
			<input type="hidden" name="isChangePwd" value="false">
		</div>
	</div>
	
		<div class="layui-form-item">
		<div class="layui-inline">
					<label class="layui-form-label"><font class="required-dot">*</font>角色</label>
					<div class="layui-input-inline">
				      <select name="roleId"  lay-verify="required"> 
				      	<option value="">请选择</option>
				      	<c:forEach items="${roleList}" var="item"><!-- <c:if test="${item.id==pId}">selected="selected"</c:if>  -->
							<option value="${item.id }" ${item.id==model.roleId?'selected="selected"':'' } >${item.roleName }</option>
				      	</c:forEach>
				      </select>
				    </div>
				</div>
			<div class="layui-inline">
				<label class="layui-form-label">手机号</label>
				<div class="layui-input-inline">
					<input type="text" name="cellphone" value="${model.cellphone }" lay-verify="myPhone" autocomplete="off" class="layui-input">
				</div>
			</div>
			
	    	
		</div>

	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">邮箱</label>
			<div class="layui-input-inline">
				<input type="text" name="email" value="${model.email}" lay-verify="" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">企业微信号</label>
			<div class="layui-input-inline">
				<input type="text" name="enterpriseWechat" value="${model.enterpriseWechat}" lay-verify="" autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>
	
	<div class="layui-form-item">
		<div class="layui-inline">
		    <label class="layui-form-label">状态</label>
		    <div class="layui-input-inline">
		    	<input type="radio" name="status" value="1" title="正常" checked="checked" <c:if test="${type==2}">disabled</c:if> >
			    <input type="radio" name="status" value="2" title="禁止登录" ${model.status==2?'checked="checked"':'' } <c:if test="${type==2}">disabled</c:if>>
<%-- 			    <input type="radio" name="status" value="3" title="禁止操作终端" ${model.status==3?'checked="checked"':'' } <c:if test="${type==2}">disabled</c:if>> --%>
		    </div>
    	</div>
	</div>
	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">备注</label>
	    <div class="layui-input-block" style=" margin-right: 55px;">
	      <textarea placeholder="" name="remark" lay-verify="sysUserremark" maxlength="250" class="layui-textarea">${model.remark}</textarea>
	    </div>
	  </div>
	<div class="layui-form-item" style="text-align: right;padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysUserbutton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>
</div>

<script type="text/javascript">

(function($) {
		$('[placeholder]').placeholder();
		
		//$('#userPassword,#userPassword2').bind('input propertychange', function() {  
			//alert(1)
			//$("input[name='isChangePwd']").val(true);
		//});  
		$('#terminaUslerPassword,#terminalUserPassword2').bind('change', function() {
			var layVerify = $("#terminalUserPassword").attr("lay-verify");
			if(layVerify.indexOf("number")<0){
				//$("#terminalUserPassword").attr("lay-verify",layVerify+"|number");
			}
		});  
		
		var d = $("#sysUserForm #sysUserid").val();
		if(!isNotEmpty(d)){
			$("#sysUserForm #identityIdx").val("3");
			form.render();
			setIdxName();
		}
		
		form.render();
		  //自定义验证规则
		  form.verify({
			  sysUsermaxlength: function(value){
		      if(value.length > 32){
		        return '长度不能超过32位';
		      }
		    },
		    sysUserremark: function(value){
		        if(value.length > 250){
		            return '长度超过250个字符';
		          }
		        },
		    nameMaxlength: function(value){
		      if(value.length > 6){
		        return '长度不能超过6位';
		      }
		    },
		    title: function(value){
		      if(value.length > 20){
		        return '长度超过20个字符';
		      }
		    },
		    password: function(value){
		        if(value.length>15 || value.length <6){
		            return '密码长度6~15位';
		          }
		        },
		    onlyNumAlpha: function(value){
		    	if(isNotEmpty(value) && !/^(\d|[a-zA-Z])+$/.test(value)){
		    		return '只能输入数字和字母组合';
		    	}
		    },
		    sysUserequal: function(value){
		    	var v = $("input[name='userPassword']").val();
		    	if(v != value){
		    		return '两次密码输入不一致';
		    	}
		    },
		    terequal: function(value){
		    	var v = $("input[name='terminalUserPassword']").val();
		    	if(v != value){
		    		return '两次密码输入不一致';
		    	}
		    },
		    sysUserunique: function(value){
		    	var flag = true;
		    	$.ajax({  
			         type : "get",  
			          url : "system/sysUser/checkCodeExist.do",  
			          data : {code:value,id:$("#sysUserid").val()},  
			          async : false,  
			          dataType: "json",
			          success : function(data){  
			          	flag = data.success;
			          }  
			     }); 
		    	if(!flag) return '账号名已存在';
		    	
		    },
		   	specialChar: function(value){
		   		var pattern = new RegExp(specialCharReg); 
		    	if(isNotEmpty(value) && pattern.test(value)){
		    		return '禁止输入特殊字符';
		    	}
		    },
		    myIdentity: function(value){
		    	if(isNotEmpty(value) && !/(^\d{15}$)|(^\d{17}(x|X|\d)$)/.test(value)){
		    		return '请输入正确的身份证号';
		    	}
		    },
		    myPhone: function(value){
		    	if(isNotEmpty(value) && !/^1\d{10}$/.test(value)){
		    		return '请输入正确的手机号';
		    	}
		    },
		    myNumber: function(value){
		    	if(isNotEmpty(value) && isNaN(value)){
		    		return '只能填写数字';
		    	}
		    }
		  });
		  
			form.on('select(identitySl)', function(data){
			  var t = $(this).text();
			  if(t=="请选择") t="";
			  var code = "";
			  if(t!=""){
				  code = data.elem.options[data.elem.selectedIndex].getAttribute("code");
			  }
			  $("#sysUserForm #identityName").val(t);
			  $("#sysUserForm #identityImage").val(code);
			});  
			
		  //监听提交
		  form.on('submit(sysUserbutton)', function(data){
			checkPwd();
			data.field['isChangePwd'] = $("#sysUserForm input[name='isChangePwd']").val();
			data.field['isChangeTerminalPwd'] = $("#sysUserForm input[name='isChangeTerminalPwd']").val();
			data.field['isChangePhoto'] = $("#sysUserForm input[name='isChangePhoto']").val();
			if($("#source_type").val()=="2"){
				editUserInfoSave(data.field);
			}else{
				sysUserAddEditSave(data.field);
			}
		    return false;
		  });
		
		  
		  
})(jQuery)
function checkPwd(){
	  if($("#userPassword").val()!=$("#oldPassword").val()){
		  $("#sysUserForm input[name='isChangePwd']").val(true);
	  }
	  if($("#terminalUserPassword").val()!=$("#oldTerminalUserPassword").val()){
		  $("#sysUserForm input[name='isChangeTerminalPwd']").val(true);
	  }
	  if($("#photoPath").val()!=$("#oldPhoto").val()){
		  $("#sysUserForm input[name='isChangePhoto']").val(true);
	  }
  }
  
  function setIdxName(){
	  var $x = $("#sysUserForm #identityIdx").find("option:selected");
	  $("#sysUserForm #identityName").val($x.text());
	  $("#sysUserForm #identityImage").val($x.attr("code"));
  }
</script>