function enterpriseInfoAddPage() {
	openLoading();
	$.get("dataDesources/enterpriseInfo/enterpriseInfoAdd.do", function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加企业信息",
			area: ['1030px','450px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function enterpriseInfoEditPage(id) {
	openLoading();
	$.get("dataDesources/enterpriseInfo/enterpriseInfoEdit.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑企业信息",
			area: ['1030px','450px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});

}

function enterpriseInfoAddEditSave(formData,flag) {
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "dataDesources/enterpriseInfo/enterpriseInfoEditSave.do";
	} else {
		url = "dataDesources/enterpriseInfo/enterpriseInfoAddSave.do";
	}
	$.ajax({
        type: 'POST',
        url: url,
        data: formData,
        dataType: "json",
        // 告诉jQuery不要去处理发送的数据
        processData : false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType : false,
        //contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function (data) {
        	var datas = JSON.parse(data);
            if (datas.success==true){
        			requestSuccess(datas,closeLayer);
        			layer.alert(datas.msg);
        			enterpriseInfoSearchList();
        			
            } else {
                layer.alert(datas.msg);
            }
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            layer.alert("系统错误，导入失败");
        }
    });
	
}
function enterpriseInfoEdit(params,flag) {
	openLoading();
	var url;
	//flag等于1为添加。等于2为编辑
	if (flag==2) {
		url = "dataDesources/enterpriseInfo/enterpriseInfoEditSave.do";
	} else {
	}
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		var id = $("input#id").val();
		if (flag==2) {
//			_selObj.update({
//				enterpriseName : params.enterpriseName,
//			});
		} else {
			enterpriseInfoSearchList();
		}
	}, 'json');
}
function enterpriseInfoSearchList() {
	openLoading();
	table.reload('enterpriseInfo', {
		where : {
			deviceCode : $('#enterpriseInfo-content [name="deviceCode"]').val(),
		},
		page : pageParam
	});
}

function enterpriseInfoDelPage(obj) {
	var ids = [];
	var names = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.enterpriseName);
	} else {
		checkStatus = layui.table.checkStatus('enterpriseInfo');
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].enterpriseName);
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
		$.post("dataDesources/enterpriseInfo/enterpriseInfoDel.do", {
			ids : ids.toString(),
			names : names.toString()
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				// if(obj){
				// obj.del();
				// }else{
				enterpriseInfoSearchList();
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
			title : "选择企业位置",
			area: ['500px','400px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});
}
