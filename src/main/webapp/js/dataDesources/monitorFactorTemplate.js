function monitorFactorTemplateAddPage() {
	openLoading();
	$.get("dataDesources/monitorFactorTemplate/add.do",function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加监测因子模板",
			area: ['450px','600px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function monitorFactorTemplateEditPage(id) {
	openLoading();
	$.get("dataDesources/monitorFactorTemplate/edit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑监测因子模板",
			area: ['450px','600px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function monitorFactorTemplateAddEditSave(params,flag) {
	console.log(flag)
	
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "dataDesources/monitorFactorTemplate/editSave.do";
	} else {
		url = "dataDesources/monitorFactorTemplate/addSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		monitorFactorTemplateSearchList();
	}, 'json');
}

function monitorFactorTemplateSearchList() {
	openLoading();
	table.reload('monitorFactorTemplate', {
		where : {
			factorTag : $('#monitorFactorTemplate-content input[name="factorTag"]').val(),
			factorName : $('#monitorFactorTemplate-content input[name="factorName"]').val(),
			factorCode : $('#monitorFactorTemplate-content input[name="factorCode"]').val(),
			typeId : $('#monitorFactorTemplate-content input[name="typeId"]').val(),
			condensingDeviceNum : $('#monitorFactorTemplate-content input[name="condensingDeviceNum"]').val(),
			deviceCode : $('#monitorFactorTemplate-content input[name="deviceCode"]').val(),
			enterpriseName : $('#monitorFactorTemplate-content input[name="enterpriseName"]').val()
		},
		page : pageParam
	});
}

function monitorFactorTemplateDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.factorName);
	} else {
		checkStatus = layui.table.checkStatus('monitorFactorTemplate');
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
		$.post("dataDesources/monitorFactorTemplate/del.do", {
			ids : ids.toString(),
			names : names.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				monitorFactorTemplateSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});

}

