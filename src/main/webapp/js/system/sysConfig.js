function sysConfigAddPage() {
	openLoading();
	$.get("system/sysConfig/add.do", function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加参数",
			area:['680px','500px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function sysConfigEditPage(id) {
	openLoading();
	$.get("system/sysConfig/edit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑参数",
			area:['680px','500px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});
}

function sysConfigAddEditSave(params) {
	openLoading();

	var url;
	if (isNotEmpty(params.id)) {
		url = "system/sysConfig/editSave.do";
	} else {
		url = "system/sysConfig/addSave.do";
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		if (isNotEmpty(params.id)) {
			_selObj.update({
				configValue : params.configValue,
				configKey : params.configKey,
				remark : params.remark,
				flag : params.flag
			});
		} else {
			sysConfigSearchList();
		}
		
		if(params.configKey=="talk_video_url"){
			closeRbTalkBackPrompt();
		}else if(params.configKey=="server_api_url"){
			getNetTestTask();
		}
		
	}, 'json');
}

function sysConfigNetTest() {
	openLoading();
	
	$.getJSON("system/sysConfig/netTest.do", {
		configValue:$("#sysConfigForm #configValue").val()
	}, function(data) {
		closeLoading();
		if(data.success){
			var telnetFlag = data.data.telnetFlag;
			if(telnetFlag){
				layer.alert("通信测试成功", {icon: 1});
			}else{
				layer.alert("通信测试失败", {icon: 2});
			}
		}else{
			layer.alert(data.msg, {icon: 2});
		}
		
	});
}

function sysConfigSearchList() {
	openLoading();
	table.reload('sysConfig', {
		where : {
			configKey : $('input[name="sysConfigconfigKey"]').val()
		},
		page : pageParam
	});
}

function sysConfigDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.configKey);
	} else {
		checkStatus = layui.table.checkStatus('sysConfig');
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].configKey);
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
		$.post("system/sysConfig/del.do", {
			ids : ids.toString(),
			names : names.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				sysConfigSearchList();
				// }
			}
			layer.close(index);
		}, 'json');

	});
}
