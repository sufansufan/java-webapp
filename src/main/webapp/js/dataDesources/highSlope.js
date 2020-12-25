function highSlopeAddPage() {
	openLoading();
	$.get("dataDesources/highSlope/add.do", function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加信息",
			area: ['750px','600px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function highSlopeEditPage(id) {
	openLoading();
	$.get("dataDesources/highSlope/edit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑信息",
			area: ['750px','600px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function highSlopeAddEditSave(params) {
	openLoading();
	var url;
	if (isNotEmpty(params.id)) {
		url = "dataDesources/highSlope/editSave.do";
	} else {
		url = "dataDesources/highSlope/addSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		var id = $("input#highSlope").val();
		if (isNotEmpty(id)) {
			_selObj.update({
				hiddenTroubleCode : params.hiddenTroubleCode,
				unifiedNumber : params.unifiedNumber,
				dataSources : params.dataSources,
				disastersName : params.disastersName,
				disastersType : params.disastersType,
				townshipCode : params.townshipCode,
				townshipAdministrative : params.townshipAdministrative,
				villageCode : params.villageCode,
				villageAdministrative : params.villageAdministrative,
				naturalVillageName : params.naturalVillageName,
				longitude : params.longitude,
				latitude : params.latitude,
				artificialSlopeDifference : params.artificialSlopeDifference,
				artificialSlopeLength : params.artificialSlopeLength,
				artificialSlopeWidth : params.artificialSlopeWidth,
				artificialSlopeDirection : params.artificialSlopeDirection
			});
		} else {
			disastersNameSearchList();
		}
	}, 'json');
}

function disastersNameSearchList() {
	openLoading();
	table.reload('highSlope', {
		where : {
			disastersName : $('input[name="disastersNameKey"]').val()
		},
		page : pageParam
	});
}

function highSlopeDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.disastersName);
	} else {
		checkStatus = layui.table.checkStatus('highSlope');
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].disastersName);
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
		$.post("dataDesources/highSlope/del.do", {
			ids : ids.toString(),
			names : names.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				disastersNameSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});

}



