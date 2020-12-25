<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysTeamUser.js"></script>
<div class="contain-content" style="bottom: 0;padding:20px">
    <div id="sysTeamUserForm" class="layui-form" action="">
        <div class="layui-inline" style="margin-bottom: 15px;">
            <div class="layui-input-inline">
                <input type="text" name="userName" placeholder="请输入用户名" class="layui-input search-maxlenght" style="width: 200px;">
            </div>
            <div class="layui-input-inline" style="margin-left: 15px;">
                <button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysTeamUserSearchList()">
                    查询
                </button>
            </div>
        </div>

        <input type="hidden" name="teamId" value="${teamId}">
        <table id="sysTeamsUser" lay-filter="lSysTeamsUser"></table>
        <!-- parentId -->
        <div class="layui-form-item" style="text-align: right;padding-top: 15px;">
            <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="sysTeamsUserButton">提交</button>
            <a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
        </div>
    </div>

</div>

<script>
    form.render();
    //监听提交
    form.on('submit(sysTeamsUserButton)', function(data){
        sysTeamUserAddSave(data.field);
        return false;
    });
    $(function(){
        $('[placeholder]').placeholder();
    })



</script>
