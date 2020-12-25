<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/inspectionMaintenance/abnormal.js"></script>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css"/>
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<div class="contain-content" style="bottom: 0;">

    <div class="layui-form right-search">
        <div class="layui-form-item" id="query_items">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" id="query_startTime" name="query_startTime" class="Wdate" value="<fmt:formatDate
                    value="${lastHour}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text"
                           onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                           style="cursor: default;height: 33px" class="layui-input search-maxlenght" placeholder="开始时间">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="query_endTime" name="query_endTime" class="Wdate"
                           value="<fmt:formatDate value="${defaultTime}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text"
                           onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                           style="cursor: default;height: 33px" class="layui-input search-maxlenght" placeholder="结束时间">
                </div>

                <div class="layui-input-inline">
                    <select name="status" xm-select="select7_1" xm-select-radio="" xm-select-search=""
                            xm-select-show-count="3" xm-select-height="26px" xm-select-width="140px" id="query_status">
                        <option value="">处理状态</option>
                        <option value="0">待处理</option>
                        <option value="1">待确认</option>
                        <option value="2">已完成</option>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <select name="status" xm-select="select7_1" xm-select-radio="" xm-select-search=""
                            xm-select-show-count="3" xm-select-height="26px" xm-select-width="140px" id="query_teams">
                        <option value="">选择班组</option>
                        <option value="0">待处理</option>
                        <option value="1">待确认</option>
                        <option value="2">已完成</option>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="deviceQryName" class="layui-input" placeholder="设备名称">
                </div>
            </div>
            <div class="layui-inline">
                <button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="completionSearchList()">
                    <i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i> 查询
                </button>
                <button class="layui-btn layui-btn-light-blue" onclick="xxx()" >
                    批量排班
                </button>
                <button class="layui-btn layui-btn-light-blue" onclick="xxx()">
                    批量休假
                </button>
            </div>
        </div>
    </div>
    <table id="admit" lay-filter="completionFilter"></table>

</div>

<script type="text/javascript">
    $(function () {
        $('[placeholder]').placeholder();
    });
    var form;
    layui.use('form', function () {
        form = layui.form;
    });
    table.render({
        elem: '#admit',
        id: 'admit',
        cols: [[
            {type: 'checkbox', width: '5%', fixed: 'left'}
            , {align: 'center', field: 'machineNo', title: '班组'}
            , {align: 'center', field: 'machineName', title: '检查类别'}
            , {align: 'center', field: 'machineName', title: '设备名称'}
            , {align: 'center', field: 'machineNo', title: '设备编号'}
            , {align: 'center', field: '实施日期', title: '实施日期', templet: function (d) {
                    return getFormatDate(d.createTime);
                }}
            , {align: 'center', field: 'status', title: '状态', templet: function (d) {
                    if(d.status == "1") return "待处理";
                    if(d.status == "2") return "被驳回";
                    if(d.status == "3") return "待确认";
                    if(d.status == "4") return "待承认";
                    if(d.status == "5") return "已完成";
                    return"";
                }}
            , {align: 'center', field: 'operateDate', title: '异常数'}
            , {align: 'center', field: 'operatorUserName', title: '维修状态'}
        ]],
        url: 'inspectionMaintenance/imResult/getToBeAdmitList.do',
        method: 'GET',
        page: pageParam,
        limit: 100,
        height: 'full-' + getTableFullHeight(),
        done: function (res, curr, count) {
            emptyOptBar("abnormal");
            resizeTable('abnormal');
            closeLoading();

        }
    });
    function completionSearchList(){
        table.reload('admit', {
            where : {
                startTime : $('#query_startTime').val(),
                endTime : $('#query_endTime').val(),
                status : $('#query_items').find('[name="status"]').val(),
            },
            page : pageParam
        });
    }
    function allAdmit() {
        alert("一键承认！");
    }
</script>