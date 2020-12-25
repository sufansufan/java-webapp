<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/dataDesources/imDetail.js"></script>
<style>
    .layui-table td, .layui-table th, .layui-table-fixed-r, .layui-table-header, .layui-table-page, .layui-table-tips-main, .layui-table-tool, .layui-table-view, .layui-table[lay-skin=line], .layui-table[lay-skin=row] {
        border-color: #f0f0f0;
    }
    .layui-form-label {
        width: 80px;
    }

    .layui-input-block {
        margin-left: 110px;
        margin-right: 30px;
    }

    .layui-form-item {
        margin-bottom: 10px;
        clear: both;
    }

    .table-td-label {
        background-color: #fafafa;
    }

    .table-td-content {
        /*background-color: #4A5064;*/
    }

    .label_custom {
        width: 200px;
        text-align: left;
    }
    .label-box {
        background: #fff;
        padding: 20px;
        margin-top: 20px;
    }
    .title-color {
        font-size: 16px;
        font-weight: bold;
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
    <div style="background: #fff; padding: 20px">
        <div style="display: flex; justify-content: space-between; margin-bottom: 10px">
            <div  class="title-color">设备基本信息</div>
            <button id="before_info" class="layui-btn layui-btn-normal layui-hide" lay-submit="" lay-filter="before-admit">
                <i class="layui-icon layui-icon-align-left" ></i>
            </button>
        </div>
        <table class="layui-table" id="machine_table">
            <colgroup>
                <col width="120">
                <col width="200">
                <col width="120">
                <col width="200">
                <col width="120">
                <col>
            </colgroup>
            <tbody class="layui-text">
            <tr>
                <td class="table-td-label">设备编号</td>
                <td class="table-td-content td-machineNo">001</td>
                <td class="table-td-label">设备名称</td>
                <td class="table-td-content td-machineName">螺旋空气压缩机</td>
                <td class="table-td-label">设备类型</td>
                <td class="table-td-content td-machineTypeName">螺旋空气压缩机xxxxx</td>
            </tr>
            <tr>
                <td class="table-td-label">设备型号</td>
                <td class="table-td-content td-machineModel">xxx</td>
                <td class="table-td-label">生产厂商</td>
                <td class="table-td-content td-manufacotry">厂商</td>
                <td class="table-td-label">所在位置</td>
                <td class="table-td-content td-location">车间a</td>
            </tr>
            <tr>
                <td class="table-td-label">管理部门</td>
                <td class="table-td-content td-orgName">财务部</td>
                <td class="table-td-label">是否远程监视</td>
                <td class="table-td-content td-remoteMonitorFlag">是</td>
                <td class="table-td-label">监听设备</td>
                <td class="table-td-content td-deviceName">AAA</td>
            </tr>
            <tr>
                <td class="table-td-label">是否点检</td>
                <td class="table-td-content td-checkFlag">是</td>
                <td class="table-td-label">是否保养</td>
                <td class="table-td-content td-maintainFlag">是</td>
                <td class="table-td-label">通讯状态</td>
                <td class="table-td-content td-onlineStatus">在线</td>
            </tr>
            </tbody>
        </table>

    </div>
    <div class="layui-form-item label-box" id="detail_user">
        <div class="title-color">点检详情</div>
        <div class="layui-inline">
            <label class="layui-form-label label_custom detail_operateDate"
                   style="width: 300px; padding-left: 0px; ">点检日期：2020-10-11</label>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label_custom detail_operatorUserName">执行人：张三</label>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label_custom detail_confirmUserName">确认人：王班长</label>
        </div>
        <table id="im_detail_table" lay-filter="im_detail_table"></table>
    </div>
    <div class="label-box">
        <div class="layui-form-item">
            <div class="title-color">异常处理记录</div>
        </div>
        <table id="im_exception_table" lay-filter="im_exception_table"></table>
    </div>
    <div class="label-box">
        <div class="layui-form-item">
            <div class="title-color">是否承认</div>
        </div>
        <table class="layui-table" id="admit_table">
            <colgroup>
                <col width="200">
                <col>
            </colgroup>
            <tbody class="layui-text">
            <tr>
                <td class="table-td-label">是否承认</td>
                <td class="table-td-content td-isAdmit">否</td>
            </tr>
            <tr>
                <td class="table-td-label">承认人</td>
                <td class="table-td-content td-admitUserName">隔壁老王</td>
            </tr>
            </tbody>
        </table>
    </div>

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
        <button class="layui-btn layui-btn-normal admitBtn layui-hide" lay-submit="" lay-filter="turnDownBtn">驳回
        </button>
    </div>
</div>

<script>
    ImDetail.init('${taskId}');

</script>