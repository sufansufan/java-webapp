function sysAuthorityAddPage(id) {
	openLoading();
	$.get("system/sysAuthority/add.do", {
		id : id
	}, function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "添加菜单",
			area:['750px','350px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});
}

function sysAuthorityEditPage(id) {
	openLoading();
	$.get("system/sysAuthority/edit.do", {
		id : id
	},function(data) {
		closeLoading();
		topIdx = layer.open({
			type : 1,
			title : "编辑菜单",
			area:['750px','350px'],
			closeBtn : 1,
			content : data,
			maxmin : true,
			resize : true
		});
	});
}


function sysAuthorityAddSave(params) {
	openLoading();
	var url= "system/sysAuthority/addSave.do";
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		loadTable();
	}, 'json');
}


function sysAuthorityEditSave(params) {
	openLoading();
	var url = "system/sysAuthority/editSave.do";
	
	$.post(url, params, function(data) {
		closeLoading();
		requestSuccess(data,closeLayer);
		loadTable();
		
	}, 'json');
}


function sysAuthorityDelPage(obj) {
	var ids = [];
	var ids2 = [];
	var names = [];
	var names2 = [];
	var checkStatus;
	if (obj) {
		ids.push(obj.data.id);
		names.push(obj.data.authorityName);
	} else {
		checkStatus = layui.table.checkStatus('sysAuthority');
		
		for (var i = 0; i < checkStatus.data.length; i++) {
			if(checkStatus.data[i].isParent!=null){
				names2.push(checkStatus.data[i].authorityName);
				ids2.push(checkStatus.data[i].id);
			}
			if(checkStatus.data[i].isParent==null){
				ids.push(checkStatus.data[i].id);
				names.push(checkStatus.data[i].authorityName);
			}
		}
	}
	if (ids.length == 0 && ids2.length == 0) {
		layer.msg('请至少勾选一条记录', function() {
			// 关闭后的操作
		});
		return false;
	}
	layer.confirm('确认删除记录?', function(index) {
		 openLoading();
	    	$.post("system/sysAuthority/del.do",{ 
			    ids:ids.toString(),
			    names:names.toString(),
			    ids2:ids2.toString(),
			    names2:names2.toString()
		    }, 
		    function(data) {
			    closeLoading();	    
			    if (data.success) {
			    	requestSuccess(data); 
			    	loadTable();
				}
			    if(data.data!=null){
					del2(data.data.ids,data.data.names,data.data.msg);
				}
				layer.close(index);
		     },'json');  	
	})	
}

function del2(ids,names,msg){
	layer.confirm(msg+"存在子项，删除则子项一并删除，确定删除？", function(index2) {
		 openLoading();
	    	$.post("system/sysAuthority/directDel.do",{ 
			    ids:ids.toString(),
			    names:names.toString()
		    }, 
		    function(data) {
			    closeLoading();
			    requestSuccess(data); 
			    if (data.success) {			    	
			    	loadTable();
				}
				layer.close(index2);
		     },'json');  	
	})
}