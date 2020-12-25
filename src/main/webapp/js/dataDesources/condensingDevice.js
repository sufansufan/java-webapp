function condensingDeviceAddPage() {
	openLoading();
	$.get("dataDesources/condensingDevice/condensingDeviceAdd.do",function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加机组",
			area: ['1030px','380px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function condensingDeviceEditPage(id) {
	openLoading();
	$.get("dataDesources/condensingDevice/condensingDeviceEdit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑设备",
			area: ['1030px','380px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function condensingDeviceAddEditSave(params,flag) {
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "dataDesources/condensingDevice/condensingDeviceEditSave.do";
	} else {

		url = "dataDesources/condensingDevice/condensingDeviceAddSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		condensingDeviceSearchList();
	}, 'json');
}

function condensingDeviceSearchList() {
	openLoading();
	table.reload('condensingDevice', {
		where : {
			deviceCode : $('#condensingDevice-content [name="deviceCode"]').val(),
			condensingDeviceNum : $('#condensingDevice-content [name="condensingDeviceNum"]').val(),
		},
		page : pageParam
	});
}

function condensingDeviceDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	console.log(obj)
	if (obj) {
		ids.push(obj.data.id);
	} else {
		checkStatus = layui.table.checkStatus('condensingDevice');
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
		$.post("dataDesources/condensingDevice/condensingDeviceDel.do", {
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


function changeCondensingDeviceList(){
	var params = {};
	// var deviceCode = $("#condensingDevice-content [name='deviceCode']").val();
	// params['deviceCode'] = deviceCode;
	// console.log(deviceCode)
	$.post("remoteMonitor/operationTrend/changeCondensingDevice.do",params,function(data){
		var condensingDeviceList = JSON.parse(JSON.parse(data));
		console.log(condensingDeviceList)
		$("#condensingDeviceNumSelect").empty();

//		var html = '<select id="condensingDeviceNum" name="condensingDeviceNum" style="display: inline-block;width:140px;height: 18px">';
		var html = '<select id="condensingDeviceNum" name="condensingDeviceNum"  xm-select="select7_2" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">';
		html +='<option value=""></option>'
		for(i=0;i<condensingDeviceList.length;i++){
			html += '<option value="'+condensingDeviceList[i].condensingDeviceNum+'" >'+condensingDeviceList[i].condensingDeviceName+'</option>';
		}
		html += '</select>';
		$("#condensingDeviceNumSelect").append(html);


	});
}
