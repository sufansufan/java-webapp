
function sysDictionaryItemAddPage(){
	openLoading();
	$.get("system/sysDictionaryItem/add.do",{dictId:$("#query_dictId").val()},function(data){
		closeLoading();
		topIdx = layer.open({
			  type: 1,
			  area: '540px',
			  title: "添加字典项",
			  closeBtn: 1,
			  content: data,
			});
	});
	
}

function sysDictionaryItemEditPage(id){
	openLoading();
	$.get("system/sysDictionaryItem/edit.do",{id:id,dictId:$("#query_dictId").val()},function(data){
		closeLoading();
		topIdx = layer.open({
			type: 1,
			area: '540px',
			title: "编辑字典项",
			closeBtn: 1,
			content: data,
		});
	});
	
}

function sysDictionaryItemAddEditSave(params){
	sysDictionaryItemSubmitForm(params);
}

function sysDictionaryItemSubmitForm(params){
	openLoading();
	var url = "system/sysDictionaryItem/addSave.do";
	if(isNotEmpty(params.id)){
		url = "system/sysDictionaryItem/editSave.do"
	}
	 $.post(url,params,function(data){
		 	closeLoading();
	    	requestSuccess(data);
	    	var id = $("input#sysDictionaryItemid").val();
	    	if(isNotEmpty(id)){
	    		_selObj.update({
	    			itemName: params.itemName,
	    			itemValue: params.itemValue,
	    			status: params.status,
	    		});
	    	}else{
	    		sysDictionaryItemSearchList();
	    	}
	    	layer.closeAll('page');
	    },'json');
}

function sysDictionaryItemSearchList(){
	openLoading();
	table.reload('sysDictionaryItem',{
		where : {
			dictId : $("#query_dictId").val(),
			keyword : $('input[name="dictionaryItem-keyword"]').val()
		},
		page : pageParam
	});
}

function sysDictionaryItemDelPage(obj){
	var ids = [];
	var names = [];
	var checkStatus;
	if(obj){
		ids.push(obj.data.id);
		names.push(obj.data.itemName);
	}else{
		checkStatus = layui.table.checkStatus('sysDictionaryItem'); 
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].itemName);
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
		$.post("system/sysDictionaryItem/del.do",{ids:ids.toString(),names:names.toString()},function(data){
			closeLoading();
			requestSuccess(data);
			
			if(data.success){
				
//				if(obj){
//					obj.del();
//				}else{
				sysDictionaryItemSearchList();
//				}
			}
			layer.close(index);
		},'json');
		
	  });
	
}