function maintenanceRecordAddPage() {
	openLoading();
	$.get("remoteMonitor/maintenanceRecord/maintenanceRecordAdd.do",function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加维修保养记录",
			area: ['730px','500px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function maintenanceRecordEditPage(id) {
	openLoading();
	$.get("remoteMonitor/maintenanceRecord/maintenanceRecordEdit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑维修保养记录",
			area: ['730px','500px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function maintenanceRecordAddEditSave(params,flag) {
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "remoteMonitor/maintenanceRecord/maintenanceRecordEditSave.do";
	} else {
		url = "remoteMonitor/maintenanceRecord/maintenanceRecordAddSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		maintenanceRecordSearchList();
	}, 'json');
}

function maintenanceRecordSearchList() {
	openLoading();
	table.reload('maintenanceRecord', {
		where : {
			deviceCode : $('#maintenanceRecord-content [name="deviceCode"]').val(),
			condensingDeviceNum : $('#maintenanceRecord-content [name="condensingDeviceNum"]').val(),
		},
		page : pageParam
	});
}

function maintenanceRecordDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	console.log(obj)
	if (obj) {
		ids.push(obj.data.id);
	} else {
		checkStatus = layui.table.checkStatus('maintenanceRecord');
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
		$.post("remoteMonitor/maintenanceRecord/maintenanceRecordDel.do", {
			ids : ids.toString(),
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				maintenanceRecordSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});

}

