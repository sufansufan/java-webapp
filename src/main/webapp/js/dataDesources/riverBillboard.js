function riverBillboardAddPage() {
	openLoading();
	$.get("dataDesources/riverBillboard/add.do", function(data) {
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

function riverBillboardEditPage(id) {
	openLoading();
	$.get("dataDesources/riverBillboard/edit.do", {
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

function riverBillboardAddEditSave(params) {
	openLoading();
	var url;
	if (isNotEmpty(params.id)) {
		url = "dataDesources/riverBillboard/editSave.do";
	} else {
		url = "dataDesources/riverBillboard/addSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		var id = $("input#riverBillboard").val();
		if (isNotEmpty(id)) {
			_selObj.update({
				publicityTitle : params.publicityTitle,
				publicityNumber : params.publicityNumber,
				riverName : params.riverName,
				riverLength : params.riverLength,
				riverStart : params.riverStart,
				riverEnd : params.riverEnd,
				riverChief : params.riverChief,
				riverChiefPhone : params.riverChiefPhone,
				riverDistrict : params.riverDistrict,
				riverDistrictPhone : params.riverDistrictPhone,
				riverSheriff : params.riverSheriff,
				riverSheriffPhone : params.riverSheriffPhone,
				riverVillage : params.riverVillage,
				riverVillagePhone : params.riverVillagePhone,
				riverVillageKeeper : params.riverVillageKeeper,
				riverVillageKeeperPhone : params.riverVillageKeeperPhone,
				longitude : params.longitude,
				latitude : params.latitude,
				riverChiefDuty : params.riverChiefDuty,
				governGoal : params.governGoal,
				supervisoryPhone : params.supervisoryPhone,
				supervisorTime : params.supervisorTime,
				supervisorUnit : params.supervisorUnit,
				updateTime : params.updateTime
			});
		} else {
			publicityTitleSearchList();
		}
	}, 'json');
}

function publicityTitleSearchList() {
	openLoading();
	table.reload('riverBillboard', {
		where : {
			publicityTitle : $('input[name="publicityTitleKey"]').val()
		},
		page : pageParam
	});
}

function riverBillboardDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.publicityTitle);
	} else {
		checkStatus = layui.table.checkStatus('riverBillboard');
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].publicityTitle);
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
		$.post("dataDesources/riverBillboard/del.do", {
			ids : ids.toString(),
			names : names.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				publicityTitleSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});

}



