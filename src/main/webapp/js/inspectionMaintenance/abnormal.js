var abnormal = {
    init: function () {
        this.initTable();
        this.initEvent();
        form.render();
    },
    initTable: function () {
        table.render({
            elem: '#abnormal',
            id: 'abnormal',
            cols: [[
                {type: 'checkbox', width: '5%', fixed: 'left'}
                , {align: 'center', field: 'machineNo', title: '设备编号', fixed: 'left'}
                , {align: 'center', field: 'machineName', title: '设备名称', fixed: 'left'}
                , {align: 'center', field: 'machineTypeName', title: '设备类型', fixed: 'left'}
                , {
                    align: 'center', field: 'type', title: '检查类别', fixed: 'left', templet: function (d) {
                        if (d.type == "DE") return "日常点检异常";
                        if (d.type == "RE") return "定期点检异常";
                        if (d.type == "ME") return "保养异常";
                        if (d.type == "BE") return "突发异常";
                        return "";
                    }
                }
                , {
                    align: 'center',
                    field: 'createTime',
                    title: '检查时间',
                    minWidth: 210,
                    width: '12%',
                    fixed: 'left',
                    templet: function (d) {
                        return getFormatDate(d.createTime);
                    }
                }
                , {align: 'center', field: 'checkUserName', title: '检出人', fixed: 'left'}
                , {
                    align: 'center', field: '', title: '班组', fixed: 'left', templet: function (d) {
                        if (d.controlMachine) return d.controlMachine.teamName;
                        return "";
                    }
                }
                , {align: 'center', field: 'operatorUserName', title: '作业执行人', fixed: 'left'}
                , {
                    align: 'center',
                    field: 'status',
                    title: '处理状态',
                    minWidth: 210,
                    width: '9.5%',
                    templet: function (d) {
                        if (d.status == "1") return "待处理";
                        if (d.status == "2") return "被驳回";
                        if (d.status == "3") return "待确认";
                        if (d.status == "4") return "待承认";
                        if (d.status == "5") return "已完成";
                        return "";
                    }
                }
                , {
                    title: '操作',
                    minWidth: 240,
                    width: '9.5%',
                    align: 'center',
                    toolbar: '#abnormalOptBar',
                    fixed: 'right'
                }
            ]],
            url: 'inspectionMaintenance/exception/getExceptionList.do',
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
    },
    initEvent: function () {
        var me = this;
        //监听工具条
        table.on('tool(abnormalFilter)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'detail') {
                me.openDetail(data.id)
            }
        });
    },
    openDetail: function (taskId) {
        const tabid = 'menu-6-1';
        var url = 'inspectionMaintenance/exception/detail.do?taskId=' + taskId;
        this.addHrefToLeftTree(url, tabid);
        addMainTab(url, tabid, '异常详情', 'system_icon_sysOrg', {}, true);
        // $.get("inspectionMaintenance/exception/detail.do", {
        //     taskId: taskId
        // }, function (data) {
        //     closeLoading();
        //     topIdx = layer.open({
        //         type: 1,
        //         title: "异常详情",
        //         area: ['100%', '100%'],
        //         closeBtn: 1,
        //         content: data,
        //         maxmin: true,
        //         resize: true
        //     });
        // });
    },
    //添加链接到左边的树菜单
    addHrefToLeftTree: function (url, tabid) {
        if ($(".left a[tabid='" + tabid + "']").length > 0) {
            $(".left a[tabid='" + tabid + "']").remove();
        }
        var htm = '<a class="first-menu" tabid="' + tabid + '" taburl="' + url + '"tabicon="system_icon_sysOrg" ></a>'
        $(".left ul").append(htm);
    }
};

$(function () {

});