function machineAddPage() {
    // var width = document.getElementById('machine').clientWidth;
    var height = document.getElementById('main-tab-content').clientHeight;
    openLoading();
    $.get("dataDesources/machineManager/machineAdd.do", function (data) {
        closeLoading();
        layui.form.render();
        topIdx = layer.open({
            type: 1,
            title: "添加设备",
            // area: ['1030px','380px'],
            area: ['80%', '800px'],
            closeBtn: 1,
            content: data,
            maxmin: true,
            resize: true
        });
        // layer.full(topIdx);
    });
}

//添加链接到左边的树菜单
function addHrefToLeftTree(url, tabid) {
    if ($(".left a[tabid='" + tabid + "']").length > 0) {
        $(".left a[tabid='" + tabid + "']").remove();
    }
    var htm = '<a class="first-menu" tabid="' + tabid + '" taburl="' + url + '"tabicon="system_icon_sysOrg" ></a>'
    $(".left ul").append(htm);
}

function toCheckListPage(id) {
    var tabid = 'menu-5-2';
    var url = 'inspectionMaintenance/imResult/index.do?id=' + id;
    addHrefToLeftTree(url, tabid);
    addMainTab(url, tabid, '点检履历', 'system_icon_sysOrg', {}, true);
}

function machineEditPage(id) {
    var tabid = 'menu-5-1';
    var url = 'dataDesources/machineManager/machineEdit.do?id=' + id;
    addHrefToLeftTree(url, tabid);
    addMainTab(url, tabid, '编辑设备', 'system_icon_sysOrg', {}, true);
}

function machineAddEditSave(params, flag) {
    openLoading();
    var url;
    //flag等于1为添加。等于2为编辑
    if (flag == 2) {
        url = "dataDesources/machineManager/machineEditSave.do";
    } else {

        url = "dataDesources/machineManager/machineAddSave.do";
    }
    $.post(url, params, function (data) {
        closeLoading();
        requestSuccess(data, closeLayer);
        machineSearchList();
    }, 'json');
}

function machineSearchList() {
    openLoading();
    table.reload('machine', {
        where: {
            machineType: $('#condensingDevice-content [name="machineType"]').val(),
            machineName: $('#condensingDevice-content [name="machineName"]').val(),
            remoteMonitorFlag: $('#condensingDevice-content [name="remoteMonitorFlag"]').val(),
            orgId: $('#condensingDevice-content [name="orgId"]').val(),
        },
        page: pageParam
    });
}

function machineDelPage(obj) {
    var ids = [];
    var names = [];
    var checkStatus;
    console.log(obj)
    if (obj) {
        ids.push(obj.data.id);
    } else {
        checkStatus = layui.table.checkStatus('machine');
        for (var i = 0; i < checkStatus.data.length; i++) {
            ids.push(checkStatus.data[i].id);
        }
    }

    if (ids.length == 0) {
        layer.msg('请至少勾选一条记录', function () {
            // 关闭后的操作
        });
        return false;
    }

    layer.confirm('确认删除记录?', function (index) {
        openLoading();
        $.post("dataDesources/machineManager/machineDel.do", {
            ids: ids.toString(),
        }, function (data) {
            closeLoading();
            requestSuccess(data);
            if (data.success) {
                // if(obj){
                // obj.del();
                // }else{
                condensingDeviceSearchList();
                // }
            }
            layer.close(index);
        }, 'json');

    });

}