<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div id="sysOrgForm" class="layui-form" action="" style="padding: 20px">

	<input type="hidden" name="id" value="${model.id}">
	<input type="hidden" name="orgIdPath" value="${orgIdPath}">

	<div class="layui-form-item">
		<label class="layui-form-label">上级组织</label>
		<div class="layui-input-block">
			<input type="text" name="" value="${pModel==null?'无':pModel.orgName }" readonly="readonly" class="layui-input" unselectable="on">
			<input type="hidden" id="parentId" name="parentId" value="${pId}">
		</div>

	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>组织编码</label>
		<div class="layui-input-block">
			<input type="text" name="orgCode" lay-verify="required|sysOrgtitle|sysOrgunique|specialChar" maxlength="12" value="${model.orgCode }" autocomplete="off"
				placeholder="请输入编码" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"><font class="required-dot">*</font>组织名称</label>
		<div class="layui-input-block">
			<input type="text" name="orgName" lay-verify="required|sysOrgtitle|specialChar|sysOrguniqueName" maxlength="12" value="${model.orgName }" autocomplete="off"
				placeholder="请输入名称" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">排序号</label>
		<div class="layui-input-block">
			<input type="text" name="sortIdx" maxlength="2" lay-verify="myNumber|specialChar|gt0" value="${model.sortIdx==100?'':model.sortIdx}" autocomplete="off"
				placeholder="从小到大排序" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block" style="">
			<textarea placeholder="请输入备注内容" name="remark" lay-verify="sysOrgremark" maxlength="250" class="layui-textarea">${model.remark}</textarea>
		</div>
	</div>
	<!-- parentId -->
	<div class="layui-form-item" style="text-align: right; padding-top: 15px;">
		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysOrgButton">提交</button>
		<a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
	</div>

</div>

<script>
	(function($) {
		$('[placeholder]').placeholder();
		form.render();
		//自定义验证规则
		form.verify({
			title : function(value) {
				if (value.length > 12) {
					return '长度超过12个字符';
				}
			},
			gt0 : function(value) {
				if (isNotEmpty(value)) {
					var x = Number(value);
					if (x == 0)
						return '请输入大于0的整数';
				}
			},
			sysOrgremark : function(value) {
				if (value.length > 250) {
					return '长度超过250个字符';
				}
			},
			specialChar : function(value) {
				var pattern = new RegExp(specialCharReg);
				if (isNotEmpty(value) && pattern.test(value)) {
					return '禁止输入特殊字符';
				}
			},
			myNumber : function(value) {
				if (isNotEmpty(value) && isNaN(value)) {
					return '只能填写数字';
				}
			},

			sysOrgunique : function(value) {

				var flag = true;
				$.ajax({
					type : "get",
					url : "system/sysOrg/checkExist.do",
					data : {
						code : value,
						id : $("input[name='id']").val()
					},
					async : false,
					dataType : "json",
					success : function(data) {
						flag = data.success;

					}
				});
				if (!flag)
					return '组织编码已存在';

			},
			sysOrguniqueName : function(value) {

				var flag = true;
				$.ajax({
					type : "get",
					url : "system/sysOrg/checkNameExist.do",
					data : {
						str : value,
						id : $("input[name='id']").val(),
						parentId : $("input#parentId").val()
					},
					async : false,
					dataType : "json",
					success : function(data) {
						flag = data.success;

					}
				});
				if (!flag)
					return '上级组织已存在同名组织';

			}
		});

		//监听提交
		form.on('submit(sysOrgButton)', function(data) {
			sysOrgAddEditSave(data.field);
			return false;
		});
	})(jQuery)
</script>