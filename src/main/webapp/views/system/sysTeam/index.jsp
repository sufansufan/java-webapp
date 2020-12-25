<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysTeam.js"></script>
<style>
    .layui-table-main{
        overflow: auto;
    }
    .tree1{
        width: 250px;
    }
    .tree-content1 {
        left: 250px;
    }
</style>
<div class="contain-content" style="bottom: 0;">
    <div class="tree tree1">
        <div>
            <button  class="layui-btn layui-btn-light-blue"  onclick="sysTeamAddPage()" style="margin-left: 20px; margin-top: 10px;">
                <i class="layui-icon layui-icon-add-1"></i>添加班组
            </button>
        </div>

    <%--    <shiro:hasPermission name="sys_team_add">
            <input type="hidden" id="orgHasAdd" value="1">
        </shiro:hasPermission>--%>
        <shiro:hasPermission name="sys_team_edit">
            <input type="hidden" id="orgHasEdit" value="1">
        </shiro:hasPermission>
        <shiro:hasPermission name="sys_team_del">
            <input type="hidden" id="orgHasDel" value="1">
        </shiro:hasPermission>
        <ul id="sysTeamTree" class="ztree"></ul>
    </div>
    <div class="tree-content tree-content1">
        <div class="layui-form right-search">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-input-inline">
                            <button class="layui-btn layui-btn-light-blue" onclick="sysTeamsUserAddPage()">
                                <i class="layui-icon">&#xe654;</i>添加组员
                            </button>
                            <button class="layui-btn layui-btn-pink" onclick="sysTeamsUserDelPage()">
                                <i class="layui-icon">&#xe640;</i>删除
                            </button>
                    </div>
                </div>

                <div class="layui-inline" style="float: right">
                    <div class="layui-input-inline">
                        <input type="text" name="sysUseruserName" placeholder="用户名称/手机号" class="layui-input search-maxlenght" style="width: 200px;">
                    </div>

                    <div class="layui-input-inline" style="">
                        <button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="sysTeamMemberSearchList()">
                            <i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i> 查询
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <table id="sysTeamMember" class="layui-table" lay-filter="lSysTeamMember"></table>
    </div>

</div>

<script type="text/html" id="sysTeamsoptBar">
    <div class="layui-btn-group optBar">
            <button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del"  style="">删除</button>
    </div>
</script>
