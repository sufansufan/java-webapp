<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
    .layui-form-select dl { max-height:110px; }
    .layui-form-item-width {width: 79.4%;}
</style>
<div id="sysTeamsForm" class="layui-form" action="" style="padding:20px">
    <input type="hidden" name="id" value="${model.id}">
    <div class="layui-form-item layui-form-item-width">
        <label class="layui-form-label"><font class="required-dot">*</font>班组名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" value="${model.name}" lay-verify="required" maxlength="12" value="" autocomplete="off" placeholder="请输入班组名称" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-item-width">
        <label class="layui-form-label"><font class="required-dot">*</font>管理部门</label>
        <div class="layui-input-block">
            <input type="hidden" id="orgId" name="orgId" value="${model.orgId}">
            <input type="text" id="orgName" name="orgName" value="${model.orgName}" lay-verify="required"
                   onclick="orgTreeShow()" readonly="readonly" style="cursor:default;background-color: #e5e5e5" class="layui-input">
        </div>
    </div>
    <div class="tree" id="orgTreeDiv" style="position: absolute;top: 110px;left: 100px;width:230px;background: #fff;display: none;z-index: 999;height: 260px;border: 0">
        <ul id="orgTree" class="ztree"></ul>
    </div>
    <input type="hidden" id="teamId" name="teamId" value="${parentId}">

    <!-- parentId -->
    <div class="layui-form-item" style="text-align: right;padding-top: 15px;">
        <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysTeamsButton">提交</button>
        <a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
    </div>

</div>

<script>

    (function($) {
        $('[placeholder]').placeholder();
        form.render();
        //自定义验证规则
        form.verify({

        });

        //监听提交
        form.on('submit(sysTeamsButton)', function(data){
            sysTeamAddEditSave(data.field);
            return false;
        });
    })(jQuery)

    showZtreeFun($("#orgTree"),"system/sysOrg/getOrgTree.do",orgTreeClick,orgTreeShow);
    function orgTreeClick(treeNode) {
        $("#orgName").val(treeNode.name);
        $("#orgId").val(treeNode.id);
    }

    function orgTreeShow() {
        $("#orgTreeDiv").toggle();
    }


    showZtreeFun($("#teamTree"),"system/sysTeam/getTeamTree.do",teamTreeClick,teamTreeShow);
    function teamTreeClick(treeNode) {
        $("#teamName").val(treeNode.name);
        $("#teamId").val(treeNode.id);
    }

    function teamTreeShow() {
        $("#teamTreeDiv").toggle();
    }

</script>