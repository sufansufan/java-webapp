<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/inspectionMaintenance/detail.js"></script>
<style>
    .layui-table td, .layui-table th, .layui-table-fixed-r, .layui-table-header, .layui-table-page, .layui-table-tips-main, .layui-table-tool, .layui-table-view, .layui-table[lay-skin=line], .layui-table[lay-skin=row] {
        border-color: #e6e6e6;
    }
    .layui-form-label {
        width: 80px;
    }

    .detail_header {
        font-size: 16px;
        font-weight: bold;
        margin: 20px 0px;
    }

    .layui-form-item {
        margin-bottom: 10px;
        clear: both;
    }
    .table-td-label{
        background-color: #fafafa;
        color: #000;
        font-weight: bold;
    }
    .table-td-content{
        /*background-color: #4A5064;*/
    }
    .label_custom {
        width: 200px;
        text-align: left;
    }

    .layui-form .layui-btn:hover {
        color: #333;
    }
    .layui-form .layui-btn-normal {
        background-color: #fff;
        color: #333;
        font-weight: bold;
    }
</style>
<div class="layui-form" style="padding: 20px">
    <div class="detail_header" style="margin-top: 0px">异常详情</div>
    <div style="display: flex; justify-content: space-between; margin-bottom: 10px">
        <div  class="title-color">设备基本信息</div>
        <button id="before_info" class="layui-btn layui-btn-normal layui-hide" lay-submit="" lay-filter="before-admit">
            <i class="layui-icon layui-icon-align-left"></i>
        </button>
    </div>
    <table class="layui-table" id="machine_table">
        <colgroup>
            <col width="13%">
            <col width="20%">
            <col width="13%">
            <col width="20%">
            <col width="13%">
            <col>
        </colgroup>
        <tbody class="layui-text">
        <tr>
            <td class="table-td-label">设备名称</td>
            <td class="table-td-content td-machineName" colspan="3">螺旋空气压缩机</td>
            <td class="table-td-label">设备编号</td>
            <td class="table-td-content td-machineNo">001</td>
        </tr>
        <tr>
            <td class="table-td-label">检查类别</td>
            <td class="table-td-content td-exceptionTypeName">日常</td>
            <td class="table-td-label">检出人</td>
            <td class="table-td-content td-checkoutUserName">xx</td>
            <td class="table-td-label">检查时间</td>
            <td class="table-td-content td-createdTime">xxx</td>
        </tr>
        <tr>
            <td class="table-td-label">检查项</td>
            <td class="table-td-content td-itemName" colspan="5"></td>
        </tr>
        <tr style="height: 60px;">
            <td class="table-td-label">检查内容描述</td>
            <td class="table-td-content td-ex-describe" colspan="5"></td>
        </tr>
        <tr>
            <td class="table-td-label">检查图片</td>
            <td class="table-td-content td-exceptionImgUrls" colspan="5"></td>
        </tr>
        </tbody>
    </table>
    <div class="detail_header">异常处理</div>
    <table class="layui-table" id="exception_fix_table">
        <colgroup>
            <col width="200">
            <col>
        </colgroup>
        <tbody class="layui-text">
        <tr>
            <td class="table-td-label">执行状态</td>
            <td class="table-td-content td-statusName"></td>
        </tr>
        <tr>
            <td class="table-td-label">作业执行人</td>
            <td class="table-td-content td-operatorUserName">
                sss
            </td>
        </tr>
        <tr>
            <td class="table-td-label">处理内容描述</td>
            <td class="table-td-content td-describe">
            </td>
        </tr>
        <tr style="height: 120px;">
            <td class="table-td-label">作业图片</td>
            <td class="table-td-content td-fixImgUrls"></td>
        </tr>
        </tbody>
    </table>
    <div class="layui-form-item">
        <div class="detail_header">是否承认</div>
    </div>
    <table class="layui-table" id="admit_table">
        <colgroup>
            <col width="200">
            <col>
        </colgroup>
        <tbody class="layui-text">
        <tr>
            <td class="table-td-label">是否承认</td>
            <td class="table-td-content td-isAdmit"></td>
        </tr>
        <tr>
            <td class="table-td-label">承认人</td>
            <td class="table-td-content td-admitUserName"></td>
        </tr>
        </tbody>
    </table>
    <div class="layui-hide label-box" id="reject_div">
        <div class="layui-form-item">
            <div class="title-color">驳回反馈</div>
        </div>
        <table class="layui-table" id="reject_table">
            <colgroup>
                <col width="200">
                <col>
            </colgroup>
            <tbody class="layui-text">
            <tr>
                <td class="table-td-label">驳回人</td>
                <td class="table-td-content td-rejectUserName"></td>
            </tr>
            <tr>
                <td class="table-td-label">驳回理由</td>
                <td class="table-td-content td-rejectDesc"></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="layui-form-item" style="text-align: right; margin-top: 5px;">
        <button class="layui-btn layui-btn-normal admitBtn layui-hide" lay-submit="" lay-filter="admitBtn">承认</button>
        <button class="layui-btn layui-btn-normal admitBtn layui-hide" lay-submit="" lay-filter="turnDownBtn">驳回</button>
    </div>
</div>

<script>
    ExceptionDetail.init('${taskId}');
    form.render();

</script>