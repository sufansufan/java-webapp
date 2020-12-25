
function sysRoleAddPage(){
	openLoading();
	$.get("system/sysRole/add.do",function(data){
		closeLoading();
		topIdx = layer.open({
			  type: 1,
			  area: '600px',
			  title: "添加角色",
			  closeBtn: 1,
			  content: data,
			  resize:false
			});
	});
	
}

function sysRoleEditPage(id){
	openLoading();
	$.get("system/sysRole/edit.do",{id:id},function(data){
		closeLoading();
		topIdx = layer.open({
			type: 1,
			area: '600px',
			title: "编辑角色",
			closeBtn: 1,
			content: data,
			resize:false
		});
	});
	
}

/**
 * 菜单配置全选
 */
function selectAllAuth(flag){
	var treeObj = $.fn.zTree.getZTreeObj("authInfoTree");
	treeObj.checkAllNodes(flag);
}

function sysRoleAddEditSave(params,type){
	var spNode = authTreeObj.getNodeByParam("name", "系统管理", null);
	if(spNode.checked || spNode.halfCheck){
		
		layer.confirm('系统管理模块的数据平台共用，确定要授权?',{
			title:"警告",
			icon: 7,
			area: ['380px'],
			skin:"warn-class",
		}, function(index){
			sysRoleSubmitForm(params,type);
		});
	}else{
		sysRoleSubmitForm(params,type);
	}
	
}

function sysRoleSubmitForm(params,type){
	openLoading();
	/** 菜单树处理  */
	var authIdArray = [];
	var authDescArray = [];
	var authCodeArray = [];
	//获取选中的顶级菜单节点
	var topSelectAuthNodes = authTreeObj.getNodesByFilter(getTopNodes);
	//循环选中顶级菜单节点，获取其下选中的菜单
	for(var i=0;i<topSelectAuthNodes.length;i++){
		
		var parentNode = topSelectAuthNodes[i];
		authIdArray.push(parentNode.id);
		authDescArray.push(parentNode.name);
		authCodeArray.push(parentNode.code);
		var selectChildNodes = authTreeObj.getNodesByParam("checked", true, parentNode);
		
		for (var j=0; j < selectChildNodes.length; j++) {
			var childNode = selectChildNodes[j];
			authIdArray.push(childNode.id);
			authCodeArray.push(childNode.code);
			
			if(childNode.type==1){
				authDescArray.push(childNode.name);
			}
		}
	}
	
	params['authorityValue'] = authIdArray.toString();
	params['authorityDesc'] = authDescArray.toString();
	params['authorityCode'] = authCodeArray.toString();
	
	var url = "system/sysRole/addSave.do";
	if(type == "edit"){
		url = "system/sysRole/editSave.do"
	}
	
	 $.post(url,params,function(data){
		 	closeLoading();
	    	requestSuccess(data,closeLayer);
	    	var id = $("input#sysRoleid").val();
	    	if(isNotEmpty(id)){
	    		_selObj.update({
	    			roleName: params.roleName,
	    			authorityDesc: params.authorityDesc
	    		});
	    	}else{
	    		sysRoleSearchList();
	    	}
	    },'json');
}

function sysRoleSearchList(){
	openLoading();
	table.reload('sysRole',{
		where : {
			roleName : $('input[name="sysRoleroleName"]').val()
		},
		page : pageParam
	});
}

function sysRoleDelPage(obj){
	var ids = [];
	var names = [];
	var checkStatus;
	if(obj){
		ids.push(obj.data.id);
		names.push(obj.data.roleName);
	}else{
		checkStatus = layui.table.checkStatus('sysRole'); 
		for (var i = 0; i < checkStatus.data.length; i++) {
			ids.push(checkStatus.data[i].id);
			names.push(checkStatus.data[i].roleName);
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
		$.post("system/sysRole/del.do",{ids:ids.toString(),names:names.toString()},function(data){
			closeLoading();
			requestSuccess(data);
			
			if(data.success){
				
//				if(obj){
//					obj.del();
//				}else{
				sysRoleSearchList();
//				}
			}
			layer.close(index);
		},'json');
		
	  });
	
}

/**
 * 菜单Tree配置
 */
var authTreeObj;
function authTreeInit(roleId){
	var params = {
			roleId: roleId
	};
	$.getJSON(
			"system/sysRole/getAuthTree.do",params,
			function(data){
				var setting = {
					 	data:{ 
					        simpleData : {  
					            enable : true 
					        }  
					    },  
						check: {
							enable: true
						},
						callback : {
							onClick : authTreeOnClick
						}
				}; 
				var zNodes = data;
				authTreeObj = $.fn.zTree.init($("#authInfoTree"), setting, zNodes);
				$("#tree-loading").hide();
				$("#authInfoTree").show();
				
				//展开选中节点
				var parentNodes = authTreeObj.getNodesByParam("isParent",true);
				for(var i=0;i<parentNodes.length;i++){
			    	var node = parentNodes[i];
			    	if(node.check_Child_State == 1 || node.check_Child_State==2){
			    		authTreeObj.expandNode(node, true);
			    	}
			    }
			});
	
}

/**
 * ztree节点点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function authTreeOnClick(event, treeId, treeNode) {
	//console.log(treeNode);
	authTreeObj.checkNode(treeNode,null,true);
}


/**
 * 自定义过滤方法——获取所有选中的最顶级父类
 * @param node
 * @returns {Boolean}
 */
function getTopNodes(node) {
    return ((node.checked == true || node.check_Child_State == 1 || node.check_Child_State==2)&&(node.level==0));
}
