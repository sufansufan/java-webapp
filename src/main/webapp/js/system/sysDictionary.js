
function sysDictionaryAddPage(){
	openLoading();
	$.get("system/sysDictionary/add.do",function(data){
		closeLoading();
		topIdx = layer.open({
			  type: 1,
			  area: '540px',
			  title: "添加字典",
			  closeBtn: 1,
			  content: data,
			});
	});
	
}

function sysDictionaryEditPage(id){
	openLoading();
	$.get("system/sysDictionary/edit.do",{id:id},function(data){
		closeLoading();
		topIdx = layer.open({
			type: 1,
			area: '540px',
			title: "编辑字典",
			closeBtn: 1,
			content: data,
		});
	});
	
}

function sysDictionaryAddEditSave(params){
	sysDictionarySubmitForm(params);
}

function sysDictionarySubmitForm(params){
	openLoading();
	var url = "system/sysDictionary/addSave.do";
	if(isNotEmpty(params.id)){
		url = "system/sysDictionary/editSave.do"
	}
	 $.post(url,params,function(data){
		 	closeLoading();
	    	requestSuccess(data);
	    	var id = $("input#sysDictionaryid").val();
	    	if(isNotEmpty(id)){
	    		_selObj.update({
	    			dictName: params.dictName,
	    			isDeleteEnable: params.isDeleteEnable
	    		});
	    	}else{
	    		sysDictionarySearchList();
	    	}
	    	layer.closeAll('page');
	    },'json');
}

function sysDictionarySearchList(){
	openLoading();
	table.reload('sysDictionary',{
		where : {
			keyword : $('input[name="dictionary-keyword"]').val()
		},
		page : pageParam
	});
}

function sysDictionaryDelPage(obj){
	var ids = [];
	var names = [];
	var checkStatus;
	if(obj){
		ids.push(obj.data.id);
		names.push(obj.data.dictName);
	}else{
		checkStatus = layui.table.checkStatus('sysDictionary'); 
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].dictName);
		}
	}
	
	if(ids.length==0){
		layer.msg('请至少勾选一条记录', function(){
			//关闭后的操作
		});
		return false;
	}
	
	layer.confirm('确认删除记录?', function(index){
		openLoading();
		$.post("system/sysDictionary/del.do",{ids:ids.toString(),names:names.toString()},function(data){
			closeLoading();
			requestSuccess(data);
			
			if(data.success){
				
//				if(obj){
//					obj.del();
//				}else{
				sysDictionarySearchList();
//				}
			}
			layer.close(index);
		},'json');
		
	  });
	
}