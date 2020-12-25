function imResultAddPage() {
    // var width = document.getElementById('machine').clientWidth;
    var height = document.getElementById('main-tab-content').clientHeight;
    openLoading();
    $.get("dataDesources/machineManager/machineAdd.do",function(data) {
        closeLoading();
        layui.form.render();
        topIdx = layer.open({
            type : 1,
            title : "添加设备",
            // area: ['1030px','380px'],
            area: ['80%','800px'],
            closeBtn : 1,
            content : data,
            maxmin : true,
            resize : true
        });
        // layer.full(topIdx);
    });
}


function imResultEditPage(id) {
    var height = document.getElementById('main-tab-content').clientHeight;
    openLoading();
    $.get("dataDesources/machineManager/machineEdit.do", {
        id : id
    }, function(data) {
        closeLoading();
        layui.form.render();
        topIdx = layer.open({
            type : 1,
            title : "编辑设备",
            // area: ['1030px','380px'],
            area: ['80%','800px'],
            closeBtn : 1,
            content : data,
            maxmin : true,
            resize : true
        });
    });
}

function imResultAddEditSave(params,flag) {
    openLoading();
    var url;
    //flag等于1为添加。等于2为编辑
    if (flag==2) {
        url = "dataDesources/machineManager/machineEditSave.do";
    } else {

        url = "dataDesources/machineManager/machineAddSave.do";
    }
    $.post(url, params, function(data) {
        closeLoading();
        requestSuccess(data,closeLayer);
        machineSearchList();
    }, 'json');
}

function imResultSearchList() {
    openLoading();
    table.reload('machine', {
        where : {
            machineName : $('#condensingDevice-content [name="machineName"]').val(),
            machineType : $('#condensingDevice-content [name="machineType"]').val(),
            remoteMonitorFlag : $('#condensingDevice-content [name="remoteMonitorFlag"]').val(),
        },
        page : pageParam
    });
}

function imResultDelPage(obj) {
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
        layer.msg('请至少勾选一条记录', function() {
            // 关闭后的操作
        });
        return false;
    }

    layer.confirm('确认删除记录?', function(index) {
        openLoading();
        $.post("dataDesources/machineManager/machineDel.do", {
            ids : ids.toString(),
        }, function(data) {
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