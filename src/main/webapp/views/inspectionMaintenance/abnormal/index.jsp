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
                    <%--<shiro:hasPermission name="abnormal_add">--%>
                    <button class="layui-btn layui-btn-light-blue" onclick="abnormalAddPage()" id="add_btn">
                        <i class="layui-icon layui-icon-add-1"></i>添加
                    </button>
                    <%--</shiro:hasPermission>
                    <shiro:hasPermission name="abnormal_del">--%>
                    <button class="layui-btn layui-btn-pink" onclick="abnormalDelPage()">
                        <i class="layui-icon layui-icon-delete"></i>删除
                    </button>
                    <%-- </shiro:hasPermission>--%>
                </div>
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

                <div class="layui-input-inline" style="width: 140px">
                    <select name="status" xm-select="select7_1" xm-select-radio="" xm-select-search=""
                            xm-select-show-count="3" xm-select-height="26px" xm-select-width="140px" id="query_status">
                        <option value="">处理状态</option>
                        <option value="0">待处理</option>
                        <option value="1">待确认</option>
                        <option value="2">已完成</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="abnormalSearchList()">
                    <i class="layui-icon" style="font-size: 18px; color: #FFFFFF;">&#xe615;</i> 查询
                </button>
            </div>
        </div>
    </div>
    <table id="abnormal" lay-filter="abnormalFilter"></table>

</div>

<script type="text/html" id="abnormalOptBar">
    <div class="layui-btn-group optBar">
        <button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="detail">
            <i class="fa fa-pencil-square-o" aria-hidden="true"></i> 详情
        </button>
    </div>
</script>

<script type="text/javascript">
    $(function () {
        $('[placeholder]').placeholder();
    });
    var form;
    layui.use('form', function () {
        form = layui.form;
    });
    abnormal.init();
    function abnormalAddPage(){
        openLoading();
        $.get("inspectionMaintenance/exception/add.do", function (data) {
            closeLoading();
            topIdx = layer.open({
                type: 1,
                title: "添加异常",
                area: ['800px', '80%'],
                closeBtn: 1,
                content: data,
                maxmin: true,
                resize: true
            });
        });
    }
    function abnormalSearchList(){
        table.reload('abnormal', {
            where : {
                startTime : $('#query_startTime').val(),
                endTime : $('#query_endTime').val(),
                status : $('#query_items').find('[name="status"]').val(),
            },
            page : pageParam
        });
    }
</script>