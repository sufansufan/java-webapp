

$(function(){
	sysOrgLoadOrgTree();
});

function sysOrgAddPage(pId){
	openLoading()
	var orgIdPath=[];
//	if(isNotEmpty(pId)){
//		orgIdPath = getNodeIdPathByTreeId("sysOrgorgTree");
//	}
	
	$.get("system/sysOrg/add.do",{pId:pId},function(data){
		closeLoading();
		topIdx = layer.open({
			  type: 1,
			  title: "添加组织",
			  closeBtn: 1,
			  area: ['500px'],
			  content: data,
			  maxmin:true,
			  resize:true
			});
	});
	
}

function sysOrgEditPage(id){
	openLoading()
	$.get("system/sysOrg/edit.do",{id:id},function(data){
		closeLoading();
		topIdx = layer.open({
			type: 1,
			title: "编辑组织",
			closeBtn: 1,
			 area: ['500px'],
			content: data,
			maxmin:true,
			resize:true
		});
	});
	
}

function sysOrgAddEditSave(params){
	
	var url = "system/sysOrg/addSave.do";
	if(isNotEmpty(params.id)){
		url = "system/sysOrg/editSave.do"
	}
	
	var treeObj = $.fn.zTree.getZTreeObj("sysOrgorgTree");
	openLoading();
	 $.post(url,params,function(data){
		 	closeLoading();
		 	requestSuccess(data,closeLayer);
	    	sysOrgLoadOrgTree();
	    },'json');
}

function sysOrgLoadOrgTree(){
	
	openLoading();
	$.post(
			"system/sysOrg/getOrgTree.do",
			function(data){
				var setting = {
						data:{ 
							simpleData : {  
								enable : true 
							}  
						},  
						view: {
			                addHoverDom: addHoverDom,
			                removeHoverDom: removeHoverDom,
			            },
			            edit: {
			                enable: true,
			                editNameSelectAll: false,
			                showRenameBtn: showRenameBtn,
			                showRemoveBtn: showRemoveBtn,
			                renameTitle: '编辑',
			                removeTitle: '删除'
			            },
						
						check: {
							enable: false
						},
						callback : {
							beforeRemove: beforeRemove,
			                beforeEditName: beforeEditName,
			                onRemove: onRemove,
							beforeDrag:function(){return false;} 
						}
				}; 
				var zNodes = data;
				
				orgTreeObj = $.fn.zTree.init($("#sysOrgorgTree"), setting, zNodes);
				//展开选中节点
				orgTreeObj.expandAll(true);
//				var nodes = orgTreeObj.getNodes();
//				if (nodes.length > 0) {
//					orgTreeObj.selectNode(nodes[0]);
//					var parentNodes = orgTreeObj.getNodesByParam("isParent",true);
//					for(var i=0;i<parentNodes.length;i++){
//						var node = parentNodes[i];
//						if(node.check_Child_State == 1 || node.check_Child_State==2){
//							orgTreeObj.expandNode(node, true);
//						}
//					}
//				}
				
				closeLoading();
			},'json');
}

function addHoverDom(treeId, treeNode) {
	var path = treeNode.orgIdPath;
	var l = 0;
	if(path!= undefined) l = path.split(":").length;
	var hasAdd = $("#orgHasAdd").val();
	if(treeNode.level<4 && l<5 && hasAdd==1){
	    var sObj = $("#" + treeNode.tId + "_span");
	    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0 || treeNode.tag == 0) return;
	    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加组织' onfocus='this.blur();'></span>";
	/*    if(treeNode.level != 0 && (treeNode.groupFlag == 1 || treeNode.nocheck==true)){
	    	sObj.after(addStr);
	    }*/
	    sObj.after(addStr);
	    var btn = $("#addBtn_" + treeNode.tId);
	    if (btn) {
	        btn.bind("click", function () {
	        	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	        	treeObj.selectNode(treeNode);
	        	sysOrgAddPage(treeNode.id);
	            return false;
	        });
	    }
	}
}        

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
}

function showRenameBtn(treeId, treeNode) {
	var hasEdit = $("#orgHasEdit").val();
	if(hasEdit==1){
		return true
	}else{
		return false
	}
	//return treeNode.level != 0;
}
function showRemoveBtn(treeId, treeNode) {
//    return !treeNode.isParent && treeNode.level != 0;
	var hasDel = $("#orgHasDel").val();
	var flag = !treeNode.isParent;
	if(hasDel==1){
		return flag
	}else{
		return false
	}
}

function beforeRemove(treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	treeObj.selectNode(treeNode);
	var flag = false;
	layer.confirm('确认删除记录?', function(index) {
		openLoading();
		$.post("system/sysOrg/del.do", {
			ids : treeNode.id, names:treeNode.name
		}, function(data) {
			closeLoading();
			requestSuccess(data);
			if (data.success) {
				treeObj.removeNode(treeNode, null);
			}
			layer.close(index);
		},'json');

	});

	return false;
} 
function onRemove(event, treeId, treeNode) {
	
}
function beforeEditName(treeId, treeNode, newName) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	treeObj.selectNode(treeNode);
	sysOrgEditPage(treeNode.id);
    return false;
}