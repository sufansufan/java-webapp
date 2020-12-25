<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<script type="text/javascript" charset="utf-8"
        src="${ctx}/js/inspectionMaintenance/inspectionMationtenance.js"></script>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css"/>
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<style>
    #polluteEnterprise-content a:hover {
        color: #039;
        text-decoration: underline
    }

    .label_custom {
        width: 200px;
        text-align: left;
    }
</style>

<div id="condensingDevice-content" class="contain-content"
     style="bottom: 0;padding-top:5px;">
    <div class="layui-form-item" id="im_machine_detail">
        <div style="padding: 9px 15px;">设备基础信息</div>
        <div class="layui-inline">
            <label class="layui-form-label label_custom im_machine_name">设备名称：${machine.machineName}</label>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label_custom im_machine_type">设备类型：${machine.machineTypeName}</label>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label label_custom im_machine_type">设备型号：${machine.machineModel}</label>
        </div>
    </div>
    <div style="background-color: white;">
        <div style="margin: 10px 20px;padding-top: 20px;padding-bottom: 10px;">

            <div class="layui-input-inline">
                <input type="text" id="query_startTime" name="query_startTime" class="Wdate"
                       value="<fmt:formatDate value="${lastHour}" pattern="yyyy-MM-dd" />" type="text"
                       onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"
                       style="cursor: default; height: 33px; width: 200px; padding-left: 10px;border-color: #E6E6E6; " class="layui-input search-maxlenght" placeholder="请选择开始时间">
            </div>
            －
            <div class="layui-input-inline">
                <input type="text" id="query_endTime" name="query_endTime" class="Wdate"
                       value="<fmt:formatDate value="${defaultTime}" pattern="yyyy-MM-dd" />" type="text"
                       onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"
                       style="cursor: default;height: 33px; width: 200px; padding-left: 10px; border-color: #E6E6E6;" class="layui-input search-maxlenght" placeholder="请选择结束时间">
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" style="width: 180px">
                    <select id="query_templateType" name="templateType" xm-select="select7_1"
                            xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px"
                            xm-select-width="180px">
                        <option value="">请选择检查类别</option>
                        <option value="R">日常</option>
                        <option value="D">定期</option>
                        <option value="B">保养</option>
                    </select>
                </div>
                <div class="layui-input-inline" style="width: 100px;">
                    <button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="imListSearchList()" style="display: flex">
                        <i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
                    </button>
                </div>
            </div>

        </div>
        <input type="hidden" id="machineId" name="machineId" value="${machineId}">
        <table id="imResultList" lay-filter="loadImResultList"></table>
    </div>
</div>
<script type="text/html" id="optionDateTimeTpl">
    {{ getFormatDate(d.operateDate) }}
</script>

<script type="text/html" id="resultTimeTpl">
    <span>成功：</span>{{ d.resultSummary.successCount}}
    <span>；失败：</span>{{ d.resultSummary.failureCount}}
    <span>；成功：</span>{{ d.resultSummary.successCount}}
</script>
<script type="text/html" id="imListBar">
    <div class="layui-btn-group optBar">
        <button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="im_detail" style="">详情</button>
        <button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="im_down" style="">下载</button>
    </div>
</script>
<script type="text/javascript">
    $(function () {
        $('[placeholder]').placeholder();
    })
    var _selObj;
    table.render({
        elem: '#imResultList',
        id: 'imResultList',
        cols: [[{type: 'checkbox', width: '3%'}
            , {
                align: 'center', field: 'operateDate', title: '执行时间', minWidth: 120, templet: function (d) {
                    return getFormatDate(d.operateDate);
                }
            }
            , {
                align: 'center', field: 'templateType', title: '检查类别', minWidth: 120, templet: function (d) {
                    var templateType = d.templateType;
                    if (templateType == "R") return "日常";
                    if (templateType == "D") return "定期";
                    if (templateType == "B") return "保养";
                    return ""
                }
            }
            , {align: 'center', field: '', title: '检查结果', minWidth: 120, templet: function (d) {
                    var resultSummary = d.resultSummary;
                    return "O  " + resultSummary.successCount + "  X  " + resultSummary.failureCount + "  /  " + resultSummary.noCheckCount;
                }}
            , {align: 'center', field: 'operatorUserName', title: '执行人', minWidth: 120}
            , {align: 'center', field: 'confirmUserName', title: '确认人', minWidth: 120}
            , {align: 'center', field: 'admitUserName', title: '承认人'}
            , {align: 'center', title: '操作', minWidth: 220, toolbar: '#imListBar', fixed: 'right'}
        ]],
        url: 'inspectionMaintenance/imResult/list.do',
        where: {
            id: $("#machineId").val(),
            startTime: $("#query_startTime").val(),
            endTime: $("#query_endTime").val(),
            templateType: $("#query_templateType option:selected").val()
        },
        page: pageParam,
        limit: 10,
        height: 'full-' + 300,
        done: function (res, curr, count) {
            closeLoading();
        }
    });

    //监听工具条
    table.on('tool(loadImResultList)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'im_detail') {
            openImDetail(data.id)
        } else if (layEvent === 'im_down') {
            downloadIMReport(data.id);
        }
    });

    //详情
    function openImDetail(taskId) {
        var tabid = 'menu-5-3';
        var url = 'inspectionMaintenance/imResult/detail.do?taskId=' + taskId;
        addHrefToLeftTree(url, tabid);
        addMainTab(url, tabid, '点检报告', 'system_icon_sysOrg', {}, true);
        // openLoading();
        // $.get("inspectionMaintenance/imResult/detail.do", {
        //     taskId: taskId
        // }, function (data) {
        //     closeLoading();
        //     layui.form.render();
        //     topIdx = layer.open({
        //         type: 1,
        //         title: "报告详情",
        //         // area: ['1030px','380px'],
        //         area: ['90%', '90%'],
        //         closeBtn: 1,
        //         content: data,
        //         maxmin: true,
        //         resize: true
        //     });
        // });
    }

    //下载
    function downloadIMReport(taskId) {
        openLoading();
        window.open("inspectionMaintenance/im/downloadIMReport.do?taskId=" + taskId);
        closeLoading();
    }

    function imListSearchList() {
        // openLoading();
        table.reload('imResultList', {
            where: {
                id: $("#machineId").val(),
                startDate: $("#query_startTime").val(),
                endDate: $("#query_endTime").val(),
                type: $("#query_templateType").parent().find("input[name='templateType']").val()
            },
            page: pageParam
        });
    }

</script>