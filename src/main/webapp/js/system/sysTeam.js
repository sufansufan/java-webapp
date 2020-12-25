var _selObj;
$(function(){
    ztreeInit($("#sysTeamTree"),"system/sysTeam/getTeamTree.do",sysTeamMemberSearchList,sysTeamCompleteFun,
        null,beforeRemove);
})

 function ztreeInit(id,url,onclickFun,callbackFun, otherParam,beforeRemove){
    var setting = {
        view: {
            nameIsHTML: true,
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
        },
        //获取json数据
        async : {
            enable : true,
            url : url,
            autoParam : ["id","pId","name"],
            otherParam: otherParam==undefined?[]:otherParam
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
        data:{
            key: {
                title:"name"
            },
            simpleData : {
                enable : true,
                idKey : "id", // id
                pIdKey : "pId", // 父id
                rootId : 0
            }
        },
        // 回调函数
        callback : {
            beforeRemove: beforeRemove,
            beforeEditName: beforeEditName,
            onRemove: onRemove,
            beforeDrag:function(){return false;},
            onClick : function nodeClick(event, treeId, treeNode, clickFlag){
                if(onclickFun!=null){
                    //通过点击左侧获取右侧内容
                    onclickFun(treeNode);
                }
            },
            onAsyncSuccess: function asyncSuccess(event, treeId, treeNode, msg) {
                if(callbackFun!=null){
                    //通过点击左侧获取右侧内容
                    callbackFun(treeId, treeNode);
                }else{
                    onAsyncSuccess(event, treeId, treeNode, msg);
                }
            }

        }
    };

    return $.fn.zTree.init(id, setting);
}



//添加班组
function sysTeamAddPage(pId){
    openLoading()
    $.get("system/sysTeam/add.do",{pId:pId},function(data){
        closeLoading();
        topIdx = layer.open({
            type: 1,
            title: "添加班组",
            closeBtn: 1,
            area: ['418px','307px'],
            content: data,
            maxmin:true,
            resize:true
        });
    });
}
//添加组员
function sysTeamsUserAddPage(pId){
    var treeObj = $.fn.zTree.getZTreeObj("sysTeamTree");
    var sNodes = treeObj.getSelectedNodes();
    var rt = sNodes[0];
    openLoading()
    $.get("system/sysTeam/addUser.do",{teamId:rt.id},function(data){
        closeLoading();
        topIdx = layer.open({
            type: 1,
            title: "添加组员",
            closeBtn: 1,
            area: ['750px','500px'],
            content: data,
            maxmin:true,
            resize:true
        });
    });
}


function sysTeamAddEditSave(params){
    var url = "system/sysTeam/addSave.do";
    if(isNotEmpty(params.id)){
        url = "system/sysTeam/editSave.do"
    }

    var treeObj = $.fn.zTree.getZTreeObj("sysTeamTree");
    openLoading();
    $.post(url,params,function(data){
        closeLoading();
        requestSuccess(data,closeLayer);
        // sysTeamLoadTeamTree();
        ztreeInit($("#sysTeamTree"),"system/sysTeam/getTeamTree.do",sysTeamMemberSearchList,sysTeamCompleteFun,
            null,beforeRemove);
    },'json');
}



function sysTeamMemberSearchList(){
    openLoading();
    table.reload('sysTeamMember',{
        where : sysTeamMembergetParams()
        /*,page : pageParam*/
    });
}
var selectNodeId;
function sysTeamMembergetParams(){
    var idArr = [];

    var treeObj = $.fn.zTree.getZTreeObj("sysTeamTree");
    var selectdNode = treeObj.getSelectedNodes()[0];
    var ids = [];
    ids = getChildren(ids,selectdNode);
    selectNodeId=selectdNode.id;
    if(ids != undefined) idArr=ids;
    var params = {
        userName: $('input[name="sysUseruserName"]').val(),
        // orgIds:idArr.toString(),
        selectNodeId:selectdNode.id
    };
    return params;
}

function sysTeamCompleteFun(treeId, treeNode){
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
        sysTeamMemberInitTable();
    }else{
        $(".right-search").hide();
        closeLoading();
    }
}

// 异步加载成功回调函数
function onAsyncSuccess(event, treeId, treeNode, msg) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    // 获取节点
    var nodes = treeObj.getNodes();
    if (nodes.length > 0) {
        treeObj.selectNode(nodes[0]);
        // 默认点击第一个节点
        treeObj.setting.callback.onClick(null, treeObj.setting.treeId, nodes[0]);

        // 展开第一父节点
        var cNodes = treeObj.getSelectedNodes();
        if (cNodes!=undefined && cNodes.length > 0) {
//			treeObj.expandNode(treeObj.getNodes()[0],true);
            treeObj.expandNode(cNodes[0], true, true, false);
        }

//		treeObj.expandAll(true);
    }else{
        closeLoading();
    }

    // 展开所有节点
    //treeObj.expandAll(true);
}

function sysTeamMemberInitTable() {
    var table = layui.table;
//	var idArr = [];
//	var ids = getNodeIdsByTreeId("sysUserOrgTree");
//	if(ids != undefined) idArr=ids;
    table.render({
        elem: '#sysTeamMember',
        id: 'sysTeamMember',
        cols: [[
            {type: 'checkbox', width: '5%'}
            // ,{hide:true,field:'id'}
            , {align: 'center', type:'numbers', title: '编号', minWidth: 100, width: '15%'}
            , {align: 'center', field: 'userName', title: '用户名', minWidth: 130, width: '20%'}
            , {align: 'center', field: 'cellphone', title: '手机号', minWidth: 130, width: '25%'}
            , {align: 'center', field: 'roleName', title: '用户角色', minWidth: 130, width: '20%'}
            , {align: 'center', title: '操作', minWidth: 130, width: '14.8%', toolbar: '#sysTeamsoptBar'}
        ]],
        url: 'system/sysTeam/list.do',
        where: sysTeamMembergetParams(),
        method: 'post',
        page: false,
        height: 'full-' + getTableFullHeight(),
        done: function (res, curr, count) {
            emptyOptBar('sysTeamMember');
            closeLoading();
            var data = res.data;
            if (data.length == 0) {
                emptyList("sysTeamMember");
                $(".layui-table-body.layui-table-main").html('<table cellspacing="0" cellpadding="0" border="0"' +
                    ' class="layui-table"><tbody></tbody></table><div class="layui-none">无数据</div>');
            }
        }
    });
}


function addHoverDom(treeId, treeNode) {
    var path = treeNode.teamIdPath;
    var l = 0;
    if(path!= undefined) l = path.split(":").length;
    var hasAdd = $("#teamHasAdd").val();
    if(treeNode.level<4 && l<5){
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0 || treeNode.tag == 0) return;
        // var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加班组' onfocus='this.blur();'></span>";
        // sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) {
            btn.bind("click", function () {
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                treeObj.selectNode(treeNode);
                sysTeamAddPage(treeNode.id);
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
        $.post("system/sysTeam/del.do", {
            ids : treeNode.id, names:treeNode.name
        }, function(data) {
            closeLoading();
            requestSuccess(data);
            if (data.success) {
                treeObj.removeNode(treeNode, null);
                ztreeInit($("#sysTeamTree"),"system/sysTeam/getTeamTree.do",sysTeamMemberSearchList,sysTeamCompleteFun,
                    null,beforeRemove);
            }else{
                openErrAlert(data.msg)
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
    sysTeamEditPage(treeNode.id);
    return false;
}

function sysTeamEditPage(id){
    openLoading()
    $.get("system/sysTeam/edit.do",{id:id},function(data){
        closeLoading();
        topIdx = layer.open({
            type: 1,
            title: "编辑班组",
            closeBtn: 1,
            area: ['418px','307px'],
            content: data,
            maxmin:true,
            resize:true
        });
    });

}

table.on('tool(lSysTeamMember)', function(obj){
    var data = obj.data;
    var layEvent = obj.event;
    var tr = obj.tr;
    _selObj = obj;
    if(layEvent === 'del'){
        sysTeamsUserDelPage(obj);
    }
});

function sysTeamsUserDelPage(obj){
    var ids = [];
    var checkStatus;
    if(obj){
        ids.push(obj.data.id);
    }else{
        checkStatus = layui.table.checkStatus('sysTeamMember');
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

    layer.confirm('确认删除?', function(index){
        openLoading();
        $.post("system/sysTeam/member_del.do",{ids:ids.toString()},function(data){
            closeLoading();
            requestSuccess(data);
            console.log(data);
            if(data.success||data.data){
                sysTeamMemberSearchList();
            }
            layer.close(index);
        },'json');

    });

}
