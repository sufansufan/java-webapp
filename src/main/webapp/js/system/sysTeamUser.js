$(function(){
    sysTeamUserInitTable();
})

function sysTeamUserInitTable(){
    table.render({
        elem: '#sysTeamsUser'
        ,id: 'idTeamUser'
        ,cols: [[
            {type: 'checkbox', width: '5%'}
            // , { hide:'true', field: 'id',width: '0'}
            , {align: 'center', type:'numbers', title: '编号', minWidth: 150, width: '10%'}
            , {align: 'center', field: 'userName', title: '用户名', minWidth: 150, width: '20%'}
            , {align: 'center', field: 'orgName', title: '所属部门', minWidth: 130, width: '20%'}
            , {align: 'center', field: 'roleName', title: '角色', minWidth: 130, width: '20%'}
            , {align: 'center', field: 'cellphone', title: '手机号', minWidth: 150}
        ]],
        url: 'system/sysTeam/userList.do',
        method: 'post',
        page: pageParam,
        limit: 10,
        // height: 'full-' + getTableFullHeight(),
        height: '300px',
        done: function (res, curr, count) {
            closeLoading();
            var data = res.data;
            if (data.length == 0) {
                emptyList("sysTeamsUser");
                $(".layui-table-body.layui-table-main").html('<table cellspacing="0" cellpadding="0" border="0" class="layui-table"><tbody></tbody></table><div class="layui-none">无数据</div>');
            }
        }
    });
}

function sysTeamUserSearchList(){
    openLoading();
    table.reload('idTeamUser',{
        where :  {
            userName: $('input[name="userName"]').val(),
        },
        page : pageParam
    });
}
function sysTeamUserAddSave(obj) {
    var checkStatus = table.checkStatus('idTeamUser'); //idTest 即为基础参数 id 对应的值
    var userIds = [];
    if(checkStatus.data.length==0){
        layer.msg('请至少勾选一条记录', function(){
            //关闭后的操作
        });
        return false;
    }

    for (var i = 0; i < checkStatus.data.length; i++) {
        userIds.push(checkStatus.data[i].id);
    }

    openLoading();
    $.post("system/sysTeam/sysTeamUserSave.do",{
        userIds:userIds.toString(),
        teamId:obj.teamId
    },function(data){
        closeLoading();
        requestSuccess(data,closeLayer);
        sysTeamMemberSearchList();
    },'json');
}