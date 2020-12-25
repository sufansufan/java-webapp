<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css"/>
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<div class="contain-content" style="bottom: 0; padding: 10px">

    <div class="layui-form right-search">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" id="deviceQryName" name="deviceQryName" class="layui-input"
                           placeholder="请输入设备名称">
                </div>
            </div>
            <div class="layui-inline">
                <button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="reloadDeviceTable()">
                    <i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i> 查询
                </button>
            </div>
        </div>
    </div>
    <table id="deviceTable" lay-filter="deviceFilter"></table>
    <div class="layui-form-item" style="text-align: right;padding-top: 15px;">
        <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="selectDevice">提交</button>
        <a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
    </div>
</div>

<script type="text/javascript">
    table.render({
        elem: '#deviceTable',
        id: 'deviceTable',
        cols: [[
            {type: 'checkbox', width: '5%', fixed: 'left'}
            , {align: 'center', field: 'machineNo', title: '设备编号'}
            , {align: 'center', field: 'machineName', title: '设备名称'}
            , {align: 'center', field: 'machineTypeName', title: '设备类型'}
            , {align: 'center', field: 'machineModel', title: '设备型号'}
            , {align: 'center', field: 'orgName', title: '管理部门'}
        ]],
        url: 'dataDesources/machineManager/machineList.do',
        method: 'GET',
        page: pageParam,
        limit: 100,
        height: '380px',
        where: {
            machineName: $('#deviceQryName').val()
        },
        done: function (res, curr, count) {
            closeLoading();
            var data = res.data;
        }
    });


    //监听提交
    form.on('submit(selectDevice)', function (data) {
        var dataArr = table.checkStatus('deviceTable').data;
        if (dataArr.length > 0) {
            $("#machineId").val(dataArr[0].id);
            $("#machineName").val(dataArr[0].machineName);
            closeLayer();
        }
        return false;
    });

    function reloadDeviceTable() {
        table.reload("deviceTable", {
            where: {
                machineName: $('#deviceQryName').val()
            }
        });
    }


</script>