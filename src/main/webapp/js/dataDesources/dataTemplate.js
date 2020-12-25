function dataTemplateAddPage() {
	openLoading();
	$.get("dataDesources/dataTemplate/add.do",function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加数据模板",
			area: ['450px','420px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function dataTemplateEditPage(id) {
	openLoading();
	$.get("dataDesources/dataTemplate/edit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑数据模板",
			area: ['450px','420px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function dataTemplateAddEditSave(params,flag) {
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "dataDesources/dataTemplate/editSave.do";
	} else {
		url = "dataDesources/dataTemplate/addSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		dataTemplateSearchList();
	}, 'json');
}

function dataTemplateSearchList() {
	openLoading();
	table.reload('dataTemplate', {
		where : {
			templateName : $('input[name="templateName"]').val()
		},
		page : pageParam
	});
}

function dataTemplateDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.factorName);
	} else {
		checkStatus = layui.table.checkStatus('dataTemplate');
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
		$.post("dataDesources/dataTemplate/del.do", {
			ids : ids.toString(),
			names : names.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				dataTemplateSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});

}

