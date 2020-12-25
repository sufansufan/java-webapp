var _selObj;
$(function(){
	ztreeFun($("#sysUserOrgTree"),"system/sysOrg/getOrgTree.do",sysUserSearchList,sysUserCompleteFun);
})

function sysUserCompleteFun(treeId, treeNode){
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getNodes();
	if (nodes.length > 0) {
		if(nodeId != undefined && nodeId != ""){
			var node = treeObj.getNodeByParam("id", nodeId, null);
			treeObj.selectNode(node);
		}else{
			treeObj.selectNode(nodes[0]);
		}
		//treeObj.expandAll(true);
		var cNodes = treeObj.getSelectedNodes();
		if (cNodes!=undefined && cNodes.length > 0) {
			treeObj.expandNode(cNodes[0], true, true, false);
		}
		sysUserInitTable();
	}else{
		$(".right-search").hide();
		closeLoading();
	}
}

function sysUserInitTable(){
	var table = layui.table;
//	var idArr = [];
//	var ids = getNodeIdsByTreeId("sysUserOrgTree");
//	if(ids != undefined) idArr=ids;
	table.render({
		elem: '#sysUser', 
		id:'sysUser',
		cols:  [[ 
		  {type:'checkbox',width: '5%'}
		  ,{align: 'center', field: 'userCode', title: '账号名', minWidth: 150,width: '12.5%',sort:true, templet:'#sysUseruserCodeTpl'} 
		  ,{align: 'center', field: 'roleName', title: '角色名称', minWidth: 150,width: '12.5%'} 
//		  ,{align: 'center', field: 'userName', title: '用户名称', minWidth: 150,width: '15%' , templet:'#sysUseruserNameTpl'} 
		  ,{align: 'center', field: 'userName', title: '用户名称', minWidth: 150,width: '12.5%'} 
		  ,{align: 'center', field: 'cellphone', title: '手机号', minWidth: 130,width: '12.5%'} 
		  ,{align: 'center', field: 'deadline', title: '使用期限', minWidth: 180,width: '15%', templet:'#deadlineTpl'}
		  ,{align: 'center', field: 'modifyTime', title: '更新时间', minWidth: 180,width: '15%', templet:'#sysUsertimeTpl'}
		  ,{align: 'center',  title: '操作', minWidth:260,width: '15%',  toolbar: '#sysUseroptBar'}
		]],
		url: 'system/sysUser/list.do',
		where: sysUsergetParams(), 
		method: 'post',
		//initSort: {field:'userCode',type:'asc'},
		page : pageParam,
		limit: 10,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			emptyOptBar('sysUser');
			closeLoading();
			var data = res.data;
			if(data.length==0){
				emptyList("sysUser");
				$(".layui-table-body.layui-table-main").html('<table cellspacing="0" cellpadding="0" border="0" class="layui-table"><tbody></tbody></table><div class="layui-none">无数据</div>');
			}
		}
	});
		
	table.on('tool(lSysUser)', function(obj){ 
		var data = obj.data; 
		var layEvent = obj.event; 
		var tr = obj.tr; 
		_selObj = obj;
		if(layEvent === 'view'){
			sysUserViewPage(data.id);
		} else if(layEvent === 'del'){
			sysUserDelPage(obj);
		} else if(layEvent === 'edit'){
			sysUserEditPage(data.id);
		} else if(layEvent === 'report'){
			sysUserCardIndexPage(data.id,data.orgId,data.userName);
		} else if(layEvent === 'box'){
			isShowUserBoxPage(data.id)
			//cabinetBoxTreePage(data.id,data.orgId,data.userName)
		}
	});
	
	table.on('sort(lSysUser)', function(obj){
		  openLoading();
		  var par = sysUsergetParams();
		  par['field'] =obj.field;
		  par['order'] =obj.type;
		  
		  table.reload('sysUser',{
		    initSort: undefined,
		    where: par,
		    page : pageParam
		  });
		});
}

function sysUserAddPage(){
	
	var treeObj = $.fn.zTree.getZTreeObj("sysUserOrgTree");
	var sNodes = treeObj.getSelectedNodes();
	var rt = sNodes[0];
//	if(!rt.isParent){
		openLoading();
		//var orgIdPath = getNodeIdPathByTreeId("orgTree");
		$.get("system/sysUser/add.do",{orgId:rt.id},function(data){
			closeLoading();
			topIdx = layer.open({
				  type: 1,
				  title: "添加用户",
				  area: ['720px','630px'],
				  closeBtn: 1,
				  content: data,
				  maxmin:true,
				  resize:true
				});
		});
		
//	}else{
//		
//		openErrAlert('只能在最子集机构添加数据');
//		return false;
//	}
	
}


function sysUserEditPage(id){
	openLoading();
	$.get("system/sysUser/edit.do",{id:id},function(data){
		closeLoading();
		topIdx = layer.open({
			type: 1,
			title: "编辑用户",
			area: ['720px','560px'],
			closeBtn: 1,
			content: data,
			maxmin:true,
			resize:true
		});
	});
	
}

function isShowUserBoxPage(id){
	openLoading();
	$.post("biz/bizUserBox/isBindBox.do",{id:id},function(data){
		closeLoading();
		if (data.success) {		
			showUserBoxPage(id);
	    }else{
	    	layer.alert('当前用户未占用箱门', {icon:2});
	    }		
	},'json');	
}

function showUserBoxPage(id){
	openLoading();
	$.get("biz/bizUserBox/view.do",{id:id},function(data2){
		closeLoading();
		topIdx = layer.open({
			type: 1,
			title: "用户占用箱门查看",
			area: ['720px','330px'],
			closeBtn: 1,
			content: data2,
			maxmin:true,
			resize:true
		});
	});
}

function sysUserViewPage(id){
	openLoading();
	$.get("system/sysUser/view.do",{id:id},function(data){
		closeLoading();
		topIdx = layer.open({
			type: 1,
			title: "查看用户详细信息",
			area: ['1030px','660px'],
			closeBtn: 1,
			content: data,
			maxmin:true,
			resize:true
		});
	});
	
}

function sysUserAddEditSave(params){
	openLoading();
	var url = "system/sysUser/addSave.do";
	if(isNotEmpty(params.id)){
		url = "system/sysUser/editSave.do"
	}
	 $.post(url,params,function(data){
	    	
	    	closeLoading();
	    	requestSuccess(data);
	    	var id = $("input[name='sysUserid']").val();
	    	if(isNotEmpty(id)){
	    		_selObj.update({
	    			userCode: params.userCode,
	    			userName: params.userName,
	    			sex: params.sex,
	    			cellphone: params.cellphone,
	    			identityIdx:params.identityIdx,
	    			identityName:params.identityName,
	    			identityImage:params.identityImage
	    		});
	    	}else{
	    		sysUserSearchList();
	    	}
	    	layer.closeAll('page');
	    },'json');
}

function sysUserSearchList(){
	openLoading();
	table.reload('sysUser',{
		//initSort: {field:'userCode',type:'asc'},
		where : sysUsergetParams(),
		page : pageParam
	});
}

var selectNodeId;
function sysUsergetParams(){
	var idArr = [];
	
	var treeObj = $.fn.zTree.getZTreeObj("sysUserOrgTree");
	var selectdNode = treeObj.getSelectedNodes()[0];
	var ids = [];
	ids = getChildren(ids,selectdNode);
	selectNodeId=selectdNode.id;
	if(ids != undefined) idArr=ids;
	var params = {
			userName: $('input[name="sysUseruserName"]').val(),
			orgIds:idArr.toString(),
			selectNodeId:selectdNode.id
	};
	return params;
}

function sysUserDelPage(obj){
	var ids = [];
	var names = [];
	var checkStatus;
	if(obj){
		ids.push(obj.data.id);
		names.push(obj.data.userName);
	}else{
		checkStatus = layui.table.checkStatus('sysUser'); 
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].userName);
		}
	}
	if(ids.length==0){
		layer.msg('请至少勾选一条记录', function(){
			//关闭后的操作
		});
		return false;
	}
	
	layer.confirm('确认删除?', function(index){
		openLoading();
		$.post("system/sysUser/del.do",{ids:ids.toString(),names:names.toString()},function(data){
			closeLoading();
			requestSuccess(data);
			console.log(data);
			if(data.success||data.data){
				sysUserSearchList();
			}
			layer.close(index);
		},'json');
		
	  });
	
}

function getFormatCodeHtm(code,orgId){
	var color = "";
	if(orgId==selectNodeId){
		rHtml = '<span style="color: #4182b8;">' + code +'</span>';
	}else{
		rHtml = code;
	}
	return rHtml;
}

function sysUserImportPage(){
	openLoading();
	
	var treeObj = $.fn.zTree.getZTreeObj("sysUserOrgTree");
	var selectdNode = treeObj.getSelectedNodes()[0];
	
	var orgIdPath = getNodeIdPathByTreeId("sysUserOrgTree");
	
	$.get("system/sysUser/import.do",{orgId:selectdNode.id,orgIdPath:orgIdPath.toString()},function(data){
		closeLoading();
		topIdx = layer.open({
			type: 1,
			title: "导入用户",
			area: ['500px'],
			closeBtn: 1,
			content: data,
//			maxmin:true,
			resize:false
		});
	});
}

function sysUserCardIndexPage(userId,orgId, userName){
	openLoading();
	$.get("system/sysUser/cardListPage.do",{
			userId:userId,
			orgId:orgId
		},function(data){
		closeLoading();
		topIdx = layer.open({
			  type: 1,
			  area: ['900px','620px'],
			  title: "用户卡管理【" + userName + "】",
			  closeBtn: 1,
			  content: data,
			  maxmin:true,
			  resize:true
			});
	});
	
}


var bindCardIdx;
function sysUserBindCardPage(userId,cardSerial){
	openLoading();
	var treeObj = $.fn.zTree.getZTreeObj("sysUserOrgTree");
	var selectdNode = treeObj.getSelectedNodes()[0];
	
	var params = {
			orgId:selectdNode.id
	}
	if(userId != undefined){
		params['userId'] = userId
	}
	if(cardSerial != undefined){
		params['cardSerial'] = cardSerial
	}
	$.get("system/sysUser/bindCardPage.do",params,function(data){
		closeLoading();
		bindCardIdx = layer.open({
			type: 1,
			title: "绑卡",
			area: ['800px','500px'],
			closeBtn: 1,
			content: data,
			maxmin:false,
			resize:false,
			cancel : function(index, layero){
					try {
						CardAccessor.stopIdentifedCard();
						CardAccessor.closeComm();
					} catch (e) {
						
					}
					  
				  $("#CardAccessor").remove();
				  setTimeout(function () {
					  return true; 
				  }, 50);
			  }
			
		});
	});
}

var unBindCardIdx;
function sysUserUnBindCardPage(){
	openLoading();
	$.get("system/sysUser/unBindCardPage.do",function(data){
		closeLoading();
		unBindCardIdx = layer.open({
			type: 1,
			title: "销卡",
			area: ['800px','500px'],
			closeBtn: 1,
			content: data,
			maxmin:false,
			resize:false,
			cancel : function(index, layero){
				try {
					CardAccessor.stopIdentifedCard();
					CardAccessor.closeComm();
				} catch (e) {
					
				}
				  
			  $("#CardAccessor").remove();
			  setTimeout(function () {
				  return true; 
			  }, 50);
		  }
		});
	});
}

var cabinetBoxTreeIdx;
function cabinetBoxTreePage(userId,orgId, userName){
	openLoading();
	
	var param={
			userId:userId,
			orgId:orgId,
			userName:userName
	}
	
	$.get("system/sysUser/cabinetBoxTree.do",param,function(data){
		closeLoading();
		cabinetBoxTreeIdx = layer.open({
			type: 1,
			area: '560px',
			title: "选择箱门",
			closeBtn: 1,
			content: data,
			resize:false
		});
	});
}

var cabinetBoxTreeIdx2;
function cabinetBoxTreePage2(orgId, idInput,nameInput){
	openLoading();
	
	var param={
			orgId:orgId,
			idInput:idInput,
			nameInput:nameInput,
			ids:$("#" + idInput).val(),
			names:$("#" + nameInput).val()
	}
	
	$.get("system/sysUser/cabinetBoxTree.do",param,function(data){
		closeLoading();
		cabinetBoxTreeIdx2 = layer.open({
			type: 1,
			area: '560px',
			title: "选择箱门",
			closeBtn: 1,
			content: data,
			resize:false
		});
	});
}

/**
 * ztree节点点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function bindBoxTreeOnClick(event, treeId, treeNode) {
	//console.log(treeNode);
	bindCabinetBoxTreeObj.checkNode(treeNode,null,true);
}

function submitBind(params, userId){

	openLoading();
	 $.post("system/sysUser/bindCardSave.do",params,function(data){
	    	
	    	closeLoading();
	    	requestSuccess(data)
	    	if(data.success){
	    		if(isNotEmpty(userId)){
	    			searchSysUserCardList()
	    		}else{
	    			
	    		}
	    		layer.close(bindCardIdx);
	    	}else{
	    		
	    	}
	    },'json');

}
function submitUnBind(params){
	
	openLoading();
	$.post("system/sysUser/unBindCardSave.do",params,function(data){
		closeLoading();
		requestSuccess(data);
		layer.close(unBindCardIdx);
	},'json');
	
}


function searchSysUserCardList(){
	openLoading();
	
	var params = {
		userId: $('#card_userId').val(),
		orgId: $('#card_orgId').val()
	};
	
	susUserCardTableIns.reload({where: params,page: pageParam});
}

var sysUserCardEditTopIdx;
function sysUserCardeditPage(id){
	openLoading();
	var params = {
			id:id
			};
	$.get("system/sysUserCard/addEdit.do",params,function(data){
		closeLoading();
		sysUserCardEditTopIdx = layer.open({
			type: 1,
			title: "编辑卡状态",
			area:['400px','280px'],
			closeBtn: 1,
			content: data,
			maxmin:true,
			resize:true
		});
	});
	
}

function sysUserCarddelPage(obj){
	var ids = [];
	var names = [];
	var checkStatus;
	if(obj){
		ids.push(obj.data.id);
		names.push(obj.data.cardSerial);
	}else{
		checkStatus = layui.table.checkStatus('sysUserCard'); 
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].cardSerial);
		}
	}
	if(ids.length==0){
		layer.msg('请至少勾选一条记录', function(){
			//关闭后的操作
		});
		return false;
	}
	
	layer.confirm('确定注销卡?', function(index){
		openLoading();
		$.post("system/sysUserCard/del.do",{
				userId:$("#card_userId").val(),
				ids:ids.toString(),
				names:names.toString()
			},function(data){
			closeLoading();
			requestSuccess(data);
			
			if(data.success){
				searchSysUserCardList();
			}
			layer.close(index);
		},'json');
		
	  });
}
function sysUserCardreportPage(userId,cardSerial){
	
	layer.confirm('挂失将会注销本卡，确定继续操作?', function(index){
		sysUserBindCardPage(userId,cardSerial);
	});
}

var sysUserBoxEditTopIdx;
function sysUserBoxeditPage(id){
	openLoading();
	var params = {
			id:id
			};
	$.get("system/sysUserBox/addEdit.do",params,function(data){
		closeLoading();
		sysUserBoxEditTopIdx = layer.open({
			type: 1,
			title: "编辑绑定状态",
			closeBtn: 1,
			content: data,
			maxmin:true,
			resize:true
		});
	});
	
}

function sysUserBoxdelPage(obj){
	var ids = [];
	var checkStatus;
	if(obj){
		ids.push(obj.data.id);
	}else{
		checkStatus = layui.table.checkStatus('baseCabinetBoxUser'); 
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
		}
		
	}
	
	if(ids.length==0){
		layer.msg('请至少勾选一条记录', function(){
			//关闭后的操作
		});
		return false;
	}
	
	layer.confirm('确认解绑箱门?', function(index){
		openLoading();
		$.post("system/sysUserBox/del.do",{
				ids:ids.toString(),
				userId:$("#user_id").val(),
				orgId:$("#user_orgId").val(),
				userName:$("#user_name").val()
			},function(data){
			closeLoading();
			requestSuccess(data);
			
			if(data.success){
				searchSysUserBoxList();
			}
			layer.close(index);
		},'json');
		
	  });
}

function searchSysUserBoxList(){
	openLoading();
	
	var params = {
			userId: $('#user_id').val(),
	}
	
	boxUserTableIns.reload({where: params,page: pageParam});
}