<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="layui-form" action="" style="padding:20px;">
	<div style="width:660px;float:left">
		<div class="layui-form-item">
			<div class="layui-inline">
			    <label class="layui-form-label">所属机构</label>
			    <div class="layui-input-inline">
	  				<input type="text" value="${sysorg.orgName}" readonly="readonly" unselectable="on" class="layui-input">
			    </div>
		    </div>
		    
		    <div class="layui-inline">
			    <label class="layui-form-label">状态</label>
			    <div class="layui-input-inline">
			    	<c:if test="${model.status==1}"><input type="text" value="正常" readonly="readonly" unselectable="on" class="layui-input"></c:if>
			    	<c:if test="${model.status==2}"><input type="text" value="禁止登录平台" readonly="readonly" unselectable="on" class="layui-input"></c:if>
			    	<c:if test="${model.status==3}"><input type="text" value="禁止操作终端" readonly="readonly" unselectable="on" class="layui-input"></c:if>
			    </div>
	    	</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">用户编号</label>
				<div class="layui-input-inline">
					<input type="text" readonly="readonly" unselectable="on" value="${model.userCode }" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
			    <label class="layui-form-label">用户名称</label>
			    <div class="layui-input-inline">
	   				<input type="text" readonly="readonly" unselectable="on" value="${model.userName }" class="layui-input">
			    </div>
	    	</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">手机号</label>
				<div class="layui-input-inline">
					<input type="text" name="cellphone" value="${model.cellphone }" readonly="readonly" unselectable="on" class="layui-input">
				</div>
			</div>
			
			<div class="layui-inline">
			    <label class="layui-form-label">电话号码</label>
			    <div class="layui-input-inline">
					<input type="text" name="telephone" value="${model.telephone }" readonly="readonly" unselectable="on" class="layui-input">
			    </div>
	    	</div>
		</div>
	</div>
	
	<div style="width:300px;float:left;text-align: center;">
		<img id="imgPreview" alt="图片预览" title="图片预览" src="environmentalQuality/device/listfileLook.do?url=${upload_url}/${model.photoPath}" height="164" onerror="loadDefaultImg(this)" style="max-width: 300px;">
	</div>	

	<div class="layui-form-item">
	
		<div class="layui-inline">
		    <label class="layui-form-label">性别</label>
		    <div class="layui-input-inline">
		    	<c:if test="${model.sex==1 }">
					<input type="text" name="telephone" value="男" readonly="readonly" unselectable="on" class="layui-input">
				</c:if>
		    	<c:if test="${model.sex==2 }">
					<input type="text" name="telephone" value="女" readonly="readonly" unselectable="on" class="layui-input">
				</c:if>
		    </div>
    	</div>
    	<div class="layui-inline">
			<label class="layui-form-label">角色</label>
			<div class="layui-input-inline">
		      <input type="text" value="${model.sysrole.roleName}" readonly="readonly" unselectable="on" class="layui-input">
		    </div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">身份证号</label>
			<div class="layui-input-inline">
				<input type="text" readonly="readonly" unselectable="on" value="${model.idCard }" class="layui-input">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
		    <label class="layui-form-label">用户身份</label>
		    <div class="layui-input-inline">
				<input type="text" value="${model.identityName}" readonly="readonly" unselectable="on" class="layui-input">
			</div>
    	</div>
    	<div class="layui-inline">
			<label class="layui-form-label">用户职称</label>
			<div class="layui-input-inline">
		      <input type="text" value="${model.titleName}" readonly="readonly" unselectable="on" class="layui-input">
		    </div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">用户职级</label>
			<div class="layui-input-inline">
		      <input type="text" value="${model.rankName}" readonly="readonly" unselectable="on" class="layui-input">
		    </div>
		</div>
	</div>
	<div class="layui-form-item">
		
		<div class="layui-inline">
			<label class="layui-form-label">注册来源</label>
		    <div class="layui-input-inline">
		    	<c:choose>
		    		<c:when test="${model.regFrom==1}">
  						<input type="text" readonly="readonly" unselectable="on" value="终端" class="layui-input">
		    		</c:when>
		    		<c:otherwise>
  						<input type="text" readonly="readonly" unselectable="on" value="平台" class="layui-input">
		    		</c:otherwise>
		    	</c:choose>
		    </div>
	    </div>
    </div>
	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">备注</label>
	    <div class="layui-input-block" style=" margin-right: 35px;">
	      <textarea class="layui-textarea" readonly="readonly" unselectable="on">${model.remark}</textarea>
	    </div>
	  </div>
	
</div>

