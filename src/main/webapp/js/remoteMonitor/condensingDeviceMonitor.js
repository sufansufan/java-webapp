function condensingDeviceAddPage() {
	openLoading();
	$.get("remoteMonitor/condensingDeviceAdd.do",function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加机组",
			area: ['1030px','450px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function condensingDeviceEditPage(id) {
	openLoading();
	$.get("remoteMonitor/condensingDeviceEdit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑机组",
			area: ['1030px','450px'],
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
		url = "remoteMonitor/condensingDeviceEditSave.do";
	} else {
		url = "remoteMonitor/condensingDeviceAddSave.do";
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
			enterpriseId : $('#condensingDevice-content [name="enterpriseId"]').val(),
			condensingDeviceNum : $('#condensingDevice-content [name="condensingDeviceNum"]').val(),
		},
		page : pageParam
	});
}

function condensingDeviceDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
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
		$.post("remoteMonitor/condensingDeviceDel.do", {
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
	var enterpriseId = $("#condensingDevice-content [name='enterpriseId']").val();
	sessionStorage.setItem("enterpriseId", enterpriseId);
	params['enterpriseId'] = enterpriseId;
	$.post("remoteMonitor/operationTrend/changeCondensingDevice.do",params,function(data){
		var condensingDeviceList = JSON.parse(JSON.parse(data));
		$("#condensingDeviceNumSelect").empty();

		var html = '<select id="condensingDeviceNum" name="condensingDeviceNum" style="display: inline-block;width:140px;height: 18px">';
		html +='<option value=""></option>'
		for(i=0;i<condensingDeviceList.length;i++){
			html += '<option value="'+condensingDeviceList[i].condensingDeviceNum+'" >'+condensingDeviceList[i].condensingDeviceName+'</option>';
		}
		html += '</select>';
		$("#condensingDeviceNumSelect").append(html);

		$("#condensingDevice-content select[name='condensingDeviceNum']").SumoSelect({
		    placeholder:"请选择机组名称",
		    search: true,
		    searchText: '请选择机组名称',
		    noMatch: '没有匹配 "{0}" 的项' ,
		    csvDispCount: 2,
		    captionFormat:'选中 {0} 项',
		    okCancelInMulti:true,
		    selectAll:false,
		    locale : ['确定', '取消']
		});

	});
}
