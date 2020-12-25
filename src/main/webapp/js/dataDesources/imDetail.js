var ImDetail = {
    init: function (taskId) {
        var me = this;
        openLoading();
        me.initEvent();
        $.get("inspectionMaintenance/im/imDetail.do", {
            taskId: taskId
        }, function (data) {
            me.translateData(JSON.parse(data).data);
            me.initTable();
            me.initMachine();
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
            var tabid = 'menu-5-4';
            var url = 'inspectionMaintenance/imResult/detail.do?taskId=' + me.beforeTaskId;
            addHrefToLeftTree(url, tabid);
            addMainTab(url, tabid, '点检报告-前回履历', 'system_icon_sysOrg', {}, true);
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
                    },txt);
                }
                , btn2: function (index, layero) {
                    $(layero).find("textarea").val('');
                    return false;
                }
            });
            return false;
        });
    },
    initTable: function () {
        //点检详情
        var me = this;
        table.render({
            elem: '#im_detail_table',
            id: 'im_detail_table',
            cols: [[{align: 'center', field: 'className', title: '点检分类'}
                , {align: 'center', field: 'indexNum', title: '编号'}
                , {align: 'center', field: 'itemName', title: '点检项目'}
                , {align: 'center', field: 'method', title: '点检方法'}
                , {align: 'center', field: 'standard', title: '判断标准'}
                //？？？？？没有操作规程
                // , {align: 'center', field: 'admitUserName', title: '操作规程'}
                , {align: 'center', field: 'result', title: '检查结果'}
                , {align: 'center', field: 'desc', title: '检查结果描述'}
                //检查图片怎么显示
                // , {align: 'center', field: 'admitUserName', title: '检查结果描述'}
            ]],
            height: '200px',
            data: me.detailData
        });

        table.render({
            elem: '#im_exception_table',
            id: 'im_exception_table',
            cols: [[{align: 'center', field: 'e_date', title: '日期'}
                , {align: 'center', field: 'e_desc', title: '异常内容'}
            ]],
            height: '150px',
            data: [
                {
                    e_date: '2020-10-01',
                    e_desc: '这是第一条异常'
                },
                {
                    e_date: '2020-10-02',
                    e_desc: '这是第二条异常'
                }
            ]
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
            machineName: controlMachine.machineName,
            machineTypeName: controlMachine.machineTypeName,
            machineModel: controlMachine.machineModel,
            manufacotry: controlMachine.manufacotry,
            location: controlMachine.location,
            orgName: controlMachine.orgName,
            remoteMonitorFlag: me.getFlagName(controlMachine.remoteMonitorFlag),
            deviceName: controlMachine.deviceName,
            checkFlag: me.getFlagName(controlMachine.checkFlag),
            maintainFlag: me.getFlagName(controlMachine.maintainFlag),
            onlineStatus: controlMachine.onlineStatus == "1" ? "在线" : "离线"
        };
        var admitUserName = data.task.admitUserName;
        me.othersData = {
            isAdmit: admitUserName ? "是" : "否",
            admitUserName: admitUserName,
            operateDate: data.task.operateDate,
            operatorUserName: data.task.operatorUserName,
            confirmUserName: data.task.confirmUserName,
            rejectUserName: data.task.rejectUserName,
            rejectDesc: data.task.rejectDesc
        };
        //获取点检详情点数据
        me.detailData = [];
        var classArr = data.task.inspectionMaintainTemplate.classList;
        $.each(classArr, function (i, clazz) {
            var itemList = clazz.itemList;
            $.each(itemList, function (j, item) {
                var detailObj = {className: clazz.className};
                detailObj.indexNum = item.indexNum;
                detailObj.itemName = item.name;
                detailObj.method = item.method;
                detailObj.standard = item.standard;
                if(item.itemResult){
                    detailObj.result = item.itemResult.result;
                    detailObj.desc = item.itemResult.desc;
                }else{
                    detailObj.result = "";
                    detailObj.desc = "";
                }
                me.detailData.push(detailObj);
            });
        });
    },
    getFlagName: function (v) {
        if (v == "1") {
            return "是";
        }
        return "否";
    },
    initMachine: function () {
        var me = this;
        var $machineTable = $("#machine_table");
        $machineTable.find(".td-machineNo").html(me.machineData.machineNo);
        $machineTable.find(".td-machineName").html(me.machineData.machineName);
        $machineTable.find(".td-machineTypeName").html(me.machineData.machineTypeName);
        $machineTable.find(".td-machineModel").html(me.machineData.machineModel);
        $machineTable.find(".td-manufacotry").html(me.machineData.manufacotry);
        $machineTable.find(".td-location").html(me.machineData.location);
        $machineTable.find(".td-orgName").html(me.machineData.orgName);
        $machineTable.find(".td-remoteMonitorFlag").html(me.machineData.remoteMonitorFlag);
        $machineTable.find(".td-deviceName").html(me.machineData.deviceName);
        $machineTable.find(".td-checkFlag").html(me.machineData.checkFlag);
        $machineTable.find(".td-maintainFlag").html(me.machineData.maintainFlag);
        $machineTable.find(".td-onlineStatus").html(me.machineData.onlineStatus);
    },
    initOthers: function () {
        var me = this;
        var $admitTable = $("#admit_table");
        $admitTable.find(".td-isAdmit").html(me.othersData.isAdmit);
        $admitTable.find(".td-admitUserName").html(me.othersData.admitUserName);
        var $detailUser = $("#detail_user");
        $detailUser.find(".detail_operateDate").html("点检日期：" + getFormatDate(me.othersData.operateDate));
        $detailUser.find(".detail_operatorUserName").html("执行人：" + me.othersData.operatorUserName);
        $detailUser.find(".detail_confirmUserName").html("确认人：" + me.othersData.confirmUserName);

        //$(".admitBtn").removeClass("layui-hide");

        if(me.othersData.rejectUserName){
            $("#reject_div").find(".td-rejectUserName").html(me.othersData.rejectUserName);
            $("#reject_div").find(".td-rejectDesc").html(me.othersData.rejectDesc);
            $("#reject_div").removeClass("layui-hide");
        }else{
            $("#reject_div").addClass("layui-hide");
        }
        if(me.beforeTaskId){
            $("#before_info").removeClass("layui-hide");
        }
        if (me.oldTaskStatus == "4") {
            $(".admitBtn").removeClass("layui-hide");
        }

    },
    updateStatus: function (taskStatus, callback, txt) {
        var me = this;
        openLoading();
        var param = {
            taskIds: me.taskId,
            taskStatus: taskStatus
        };
        if(txt){
            param.rejectDesc = txt;
        }
        $.post("inspectionMaintenance/imResult/updateTaskStatus.do", param , function (data) {
            closeLoading();
            if (data.success) {
                if (callback) callback();
                layer.alert('操作成功', {icon: 1});
                $("#main-tab").find('[lay-id="menu-5-3"]').click();
            } else {
                openErrAlert(data.msg);
            }
        }, 'json');
    }
};