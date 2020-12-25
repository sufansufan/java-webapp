var ExceptionDetail = {
    init: function (taskId) {
        var me = this;
        openLoading();
        me.initEvent();
        $.get("inspectionMaintenance/exception/getExceptionDetail.do", {
            taskId: taskId,
            taskStatus: 5
        }, function (data) {
            me.translateData(JSON.parse(data).data);
            me.initData();
            me.initOthers();
            closeLoading();
        });
    },
    initEvent: function () {
        var me = this;
        //承认
        form.on('submit(admitBtn)', function (data) {
            me.updateStatus(5);
            return false;
        });
        //前回履历
        form.on('submit(before-admit)', function (data) {
            var tabid = 'menu-6-2';
            var url = 'inspectionMaintenance/exception/detail.do?taskId=' + me.beforeTaskId;
            me.addHrefToLeftTree(url, tabid);
            addMainTab(url, tabid, '异常详情-前回履历', 'system_icon_sysOrg', {}, true);
            return false;
        });
        //驳回
        form.on('submit(turnDownBtn)', function (data) {
            layer.prompt({
                formType: 2,
                title: '请输入驳回理由',
                offset: 'rb',
                // offset:['10px', '20px'],
                btn: ['确定', '重置'],
                area: ['350px', '200px'] //自定义文本域宽高
                , yes: function (index, layero) {
                    var txt = $(layero).find("textarea").val();
                    me.updateStatus(2, function () {
                        layer.close(index);
                    }, txt);
                }
                , btn2: function (index, layero) {
                    //按钮【按钮二】的回调
                    $(layero).find("textarea").val('');
                    return false;
                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
            return false;
        });
    },
    //根据后台数据转换对象
    translateData: function (data) {
        var me = this;
        me.taskId = data.task.id;
        me.beforeTaskId = data.task.beforeTaskId;
        me.oldTaskStatus = data.task.status;
        var controlMachine = data.controlMachine;
        me.machineData = {
            machineId: controlMachine.id,
            machineNo: controlMachine.machineNo,
            machineName: controlMachine.machineName
        };
        me.exItem = {
            describe: data.exception.describe,
            exceptionImgUrls: data.exception.exceptionImgUrls
        };
        if (data.inspectionMaintainItem) me.exItem.itemName = data.inspectionMaintainItem.name;
        me.exceptionData = {
            exceptionTypeName: me.getExceptionTypeName(data.exception.exceptionType),
            checkoutUserName: data.exception.checkoutUserName,
            createdTime: getFormatDate(data.exception.createdTime)
        };
        me.exceptionFixData = {
            statusName: me.getStatusName(data.task.status),
            operatorUserName: data.task.operatorUserName,
            operateDate: data.task.operateDate
        };
        if (data.exceptionFix) {
            me.exceptionFixData.describe = data.exceptionFix.describe;
            me.exceptionFixData.fixImgUrls = data.exceptionFix.fixImgUrls;
        }

        me.othersData = {
            admitUserName: data.task.admitUserName,
            isAdmit: data.task.admitUserName ? "是" : "否",
            confirmUserName: data.task.confirmUserName,
            rejectUserName: data.task.rejectUserName,
            rejectDesc: data.task.rejectDesc
        };
    },
    initData: function () {
        var me = this;
        var $machineTable = $("#machine_table");
        $machineTable.find(".td-machineNo").html(me.machineData.machineNo);
        $machineTable.find(".td-machineName").html(me.machineData.machineName);
        $machineTable.find(".td-exceptionTypeName").html(me.exceptionData.exceptionTypeName);
        $machineTable.find(".td-checkoutUserName").html(me.exceptionData.checkoutUserName);
        $machineTable.find(".td-createdTime").html(me.exceptionData.createdTime);
        $machineTable.find(".td-itemName").html(me.exItem.itemName);
        $machineTable.find(".td-ex-describe").html(me.exItem.describe);
        $machineTable.find(".td-exceptionImgUrls").html(me.exItem.exceptionImgUrls);

        var $admitTable = $("#admit_table");
        $admitTable.find(".td-isAdmit").html(me.othersData.isAdmit);
        $admitTable.find(".td-admitUserName").html(me.othersData.admitUserName);

        var $fixTable = $("#exception_fix_table");
        $fixTable.find(".td-statusName").html(me.exceptionFixData.statusName);
        $fixTable.find(".td-operatorUserName").html(me.exceptionFixData.operatorUserName);
        $fixTable.find(".td-describe").html(me.exceptionFixData.describe);
        $fixTable.find(".td-fixImgUrls").html(me.exceptionFixData.fixImgUrls);
    },
    initOthers: function () {
        var me = this;
        if (me.othersData.rejectUserName) {
            $("#reject_div").find(".td-rejectUserName").html(me.othersData.rejectUserName);
            $("#reject_div").find(".td-rejectDesc").html(me.othersData.rejectDesc);
            $("#reject_div").removeClass("layui-hide");
        } else {
            $("#reject_div").addClass("layui-hide");
        }
        if (me.beforeTaskId) {
            $("#before_info").removeClass("layui-hide");
        }
        if (me.oldTaskStatus == "4") {
            $(".admitBtn").removeClass("layui-hide");
        }

    },
    getExceptionTypeName: function (type) {
        if (type == "D") return "日常点检";
        if (type == "R") return "定期点检";
        if (type == "M") return "保养";
        if (type == "DE") return "日常点检异常";
        if (type == "RE") return "定期点检异常";
        if (type == "ME") return "保养异常";
        if (type == "ME") return "保养异常";
        if (type == "BE") return "突发异常";
        return "";
    },
    getStatusName: function (status) {
        if (status == "1") return "待处理";
        if (status == "2") return "被驳回";
        if (status == "3") return "待确认";
        if (status == "4") return "待承认";
        if (status == "5") return "已完成";
        return "";
    },
    updateStatus: function (taskStatus, callback, txt) {
        var me = this;
        openLoading();
        var param = {
            taskIds: me.taskId,
            taskStatus: taskStatus
        };
        if (txt) {
            param.rejectDesc = txt;
        }
        $.post("inspectionMaintenance/imResult/updateTaskStatus.do", param, function (data) {
            closeLoading();
            if (data.success) {
                if (callback) callback();
                layer.alert('操作成功', {icon: 1});
                $("#main-tab").find('[lay-id="menu-6-1"]').click();
            } else {
                openErrAlert(data.msg);
            }
        }, 'json');
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