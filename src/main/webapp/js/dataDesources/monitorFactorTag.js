function monitorFactorTagAddPage() {
	openLoading();
	$.get("dataDesources/monitorFactorTag/add.do",function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加监测因子标签",
			area: ['450px','200px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function monitorFactorTagEditPage(id) {
	openLoading();
	$.get("dataDesources/monitorFactorTag/edit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑监测因子标签",
			area: ['450px','200px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function monitorFactorTagAddEditSave(params,flag) {
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "dataDesources/monitorFactorTag/editSave.do";
	} else {
		url = "dataDesources/monitorFactorTag/addSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		monitorFactorTagSearchList();
	}, 'json');
}

function monitorFactorTagSearchList() {
	openLoading();
	table.reload('monitorFactorTag', {
		where : {
			tagName : $('#monitorFactorTag-content input[name="tagName"]').val()
		},
		page : pageParam
	});
}

function monitorFactorTagDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.tagName);
	} else {
		checkStatus = layui.table.checkStatus('monitorFactorTag');
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].tagName);
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
		$.post("dataDesources/monitorFactorTag/del.do", {
			ids : ids.toString(),
			names : names.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				monitorFactorTagSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});

}

