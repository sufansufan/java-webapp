function monitorFactorAddPage() {
	openLoading();
	$.get("dataDesources/monitorFactor/add.do",function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加监测因子",
			area: ['450px','600px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function monitorFactorEditPage(id) {
	openLoading();
	$.get("dataDesources/monitorFactor/edit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑监测因子",
			area: ['450px','600px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function monitorFactorAddEditSave(params,flag) {
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "dataDesources/monitorFactor/editSave.do";
	} else {
		url = "dataDesources/monitorFactor/addSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		monitorFactorSearchList();
	}, 'json');
}

function monitorFactorSearchList() {
	openLoading();
	table.reload('monitorFactor', {
		where : {
			factorTag : $('#monitorFactor-content input[name="factorTag"]').val(),
			factorName : $('#monitorFactor-content input[name="factorName"]').val(),
			factorCode : $('#monitorFactor-content input[name="factorCode"]').val(),
			typeId : $('#monitorFactor-content input[name="typeId"]').val(),
			machineType : $('#monitorFactor-content input[name="machineType"]').val()
		},
		page : pageParam
	});
}

function monitorFactorDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.factorName);
	} else {
		checkStatus = layui.table.checkStatus('monitorFactor');
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].factorName);
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
		$.post("dataDesources/monitorFactor/del.do", {
			ids : ids.toString(),
			names : names.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				monitorFactorSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});

}

