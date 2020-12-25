function deviceInfoAddPage() {
	openLoading();
	$.get("device/deviceInfo/deviceInfoAdd.do", {
		id : id
	},function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加终端信息",
			area: ['1030px','450px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function deviceInfoEditPage(id) {
	openLoading();
	$.get("device/deviceInfo/deviceInfoEdit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑终端信息",
			area: ['1030px','450px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function deviceInfoAddEditSave(params,flag) {
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "device/deviceInfo/deviceInfoEditSave.do";
	} else {
		url = "device/deviceInfo/deviceInfoAddSave.do";
	}

		$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
//		if(flag==2){
//			//编辑成功后，只刷新下列数值
//			_selObj.update({
//				deviceCode : params.deviceCode,
//				deviceName : params.deviceName,
//			});
//		} else {
//			deviceInfoSearchList();
//		}
		deviceInfoSearchList();
	}, 'json');
}

function deviceInfoSearchList() {
	openLoading();
	table.reload('deviceInfo', {
		where : {
			deviceKeyWord : $('input[name="deviceKeyWord"]').val(),
		},
		page : pageParam
	});
}

function deviceInfoDelPage(obj) {
	var ids = [];
	var deviceCodes = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		deviceCodes.push(obj.data.deviceCode);
	} else {
		checkStatus = layui.table.checkStatus('deviceInfo');
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			deviceCodes.push(checkStatus.data[i].deviceCode);
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
		$.post("device/deviceInfo/deviceInfoDel.do", {
			ids : ids.toString(),
			deviceCodes : deviceCodes.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				deviceInfoSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});

}


function showMapPage(){
	openLoading();
	$.get("device/deviceInfo/getMapPositon.do", function(data) {
		closeLoading();
		layer.open({
			type : 1,
			title : "选择终端位置",
			area: ['500px','400px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});
}
