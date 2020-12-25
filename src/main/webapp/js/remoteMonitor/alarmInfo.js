function alarmInfoSearchList() {
	openLoading();
	table.reload('alarmInfo', {
//		cols : cols,
		where : {
			enterpriseId : $("#alarmInfo-content [name='enterpriseId']").val(),
			machineId : $("#alarmInfo-content [name='machineId']").val(),
			alarmType : $("#alarmInfo-content [name='alarmType']").val(),
			query_startTime : $('#alarmInfo-content [name="query_startTime"]').val(),
			query_endTime : $('#alarmInfo-content [name="query_endTime"]').val(),
		},
		page : pageParam
	});

}
//改变机组列表
function changeMachineList(storage_machineId){
	var params = {};
	var enterpriseId = $("#alarmInfo-content [name='enterpriseId']").val();
	sessionStorage.setItem("enterpriseId", enterpriseId);
	params['enterpriseId'] = enterpriseId;
	$.post("remoteMonitor/operationTrend/changeCondensingDevice.do",params,function(data){
		var machineList = JSON.parse(JSON.parse(data));
		$("#machineSelect").empty();

		var html = '<select id="machineId" name="machineId" onchange="alarmInfoSearchList()" style="display: inline-block;width:140px;height: 18px">';
		for(i=0;i<machineList.length;i++){
			if(i==0){
				html += '<option value="'+machineList[i].id+'">'+machineList[i].machineName+'</option>';
			}else{
				html += '<option value="'+machineList[i].id+'" >'+machineList[i].machineName+'</option>';
			}
		}
		html += '</select>';
		$("#machineSelect").append(html);
		   if(storage_machineId!=null){
		    	 $("#alarmInfo-content [name='machineId']").val(storage_machineId);
		    }
		$("#alarmInfo-content select[name='machineId']").SumoSelect({
		    placeholder:"请选择设备",
		    search: true,
		    searchText: '请选择设备',
		    noMatch: '没有匹配 "{0}" 的项' ,
		    csvDispCount: 2,
		    captionFormat:'选中 {0} 项',
		    okCancelInMulti:true,
		    selectAll:false,
		    locale : ['确定', '取消']
		});

	});
}
