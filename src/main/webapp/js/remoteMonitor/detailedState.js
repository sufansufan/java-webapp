var index =0
$(function(){
	changeDetailedStateList();
});

/**
 * 信息查询
 */
function searchDetailedStateList() {
	var params = {};
	var enterpriseId = $("#detailedState-content [name='enterpriseId']").val();
	var machineId = $("#detailedState-content [name='machineId']").val();
	var monitorFactors = $("#detailedState-content [name='monitorFactors']").val();
	 var query_startTime = $("#detailedState-content [name='query_startTime']").val();
	 var query_endTime = $("#detailedState-content [name='query_endTime']").val();

	params['enterpriseId'] = enterpriseId;
	params['machineId'] = machineId;
	if(monitorFactors!=null){
		params['monitorFactors'] = monitorFactors.toString();
	}else{
		params['monitorFactors'] = "";
	}

	params['query_startTime'] = query_startTime;
	params['query_endTime'] = query_endTime;
	// 加载历史记录列表
	loadDetailedStateInfo(params, false);
	// 加载历史记录图表
	loadDetailedStateChart(params);
}

//改变机组列表
function changeCondensingDeviceList(storage_machineId){
	var params = {};
	var enterpriseId = $("#detailedState-content [name='enterpriseId']").val();
	sessionStorage.setItem("enterpriseId", enterpriseId);
	params['enterpriseId'] = enterpriseId;
	$.post("remoteMonitor/operationTrend/changeCondensingDevice.do",params,function(data){
		var machineList = JSON.parse(JSON.parse(data));
		$("#machineSelect").empty();

		var html = '<select id="machineId" name="machineId" onchange="changeDetailedStateList()" style="display: inline-block;width:140px;height: 18px">';
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
	    	 $("#detailedState-content [name='machineId']").val(storage_machineId);
	    }
		$("#detailedState-content select[name='machineId']").SumoSelect({
		    placeholder:"请选设备",
		    search: true,
		    searchText: '请选设备',
		    noMatch: '没有匹配 "{0}" 的项' ,
		    csvDispCount: 2,
		    captionFormat:'选中 {0} 项',
		    okCancelInMulti:true,
		    selectAll:false,
		    locale : ['确定', '取消']
		});
//		changeDetailedStateList()

	});
}
/**
 * 改变机组选择框值的方法
 */
function changeDetailedStateList() {
	var storage_enterpriseId = sessionStorage.getItem("enterpriseId");
	var storage_machineId = sessionStorage.getItem("machineId");
	//获取完先移除当前的缓存
//	sessionStorage.removeItem("enterpriseId")
	sessionStorage.removeItem("machineId")
	 if(storage_enterpriseId!=null){
		 if(index==0){
			 index++;
			 $("#detailedState-content [name='enterpriseId']").val(storage_enterpriseId);
			 changeCondensingDeviceList(storage_machineId);
		 }

	 }
	var params = {};
	var enterpriseId = $("#detailedState-content [name='enterpriseId']").val();
	var machineId = $("#detailedState-content [name='machineId']").val();
	 var query_startTime = $("#detailedState-content [name='query_startTime']").val();
	 var query_endTime = $("#detailedState-content [name='query_endTime']").val();
	 if(storage_enterpriseId!=null){
		 enterpriseId = storage_enterpriseId;
	 }
	 if(storage_machineId!=null){
		 machineId = storage_machineId;
	 }
	 params['enterpriseId'] = enterpriseId;
	params['machineId'] = machineId;
	params['query_startTime'] = query_startTime;
	params['query_endTime'] = query_endTime;
	// 改变到对应的检测因子模板
	changeMonitorFactor(params)
}

/**
 * 改变监测因子
 * @returns
 */
function changeMonitorFactor(params){
	$.post("remoteMonitor/detailedState/changeMonitorFactor.do",params,function(data){
		var result = JSON.parse(JSON.parse(data));
		var monitorFactorsCodeArr = result.monitorFactorsCodeArr;
		var monitorFactorsNameArr = result.monitorFactorsNameArr;
		var dataAccuracyArr = result.dataAccuracyArr;
		var decimalDigitsArr = result.decimalDigitsArr;
		var monitorFactorsUnitArr = result.monitorFactorsUnitArr;
		$("#monitorFactorSelect").empty();
		var html = '<select id="monitorFactors" name="monitorFactors" style="display: inline-block;width:140px;height: 50%" multiple="multiple">';
		for(i=0;i<monitorFactorsCodeArr.length;i++){
			var monitorFactorContent = monitorFactorsCodeArr[i]+";"+monitorFactorsNameArr[i]+";"+dataAccuracyArr[i]+";"+decimalDigitsArr[i]+";"+monitorFactorsUnitArr[i];
			if(i==0){
				html += '<option value="'+monitorFactorContent+'" selected="selected">'+monitorFactorsNameArr[i]+'</option>';
			}else{
				html += '<option value="'+monitorFactorContent+'" >'+monitorFactorsNameArr[i]+'</option>';
			}
		}
		html += '</select>';
		$("#monitorFactorSelect").append(html);

		$("#detailedState-content select[name='monitorFactors']").SumoSelect({
		    placeholder:"请选择监测因子",
		    search: true,
		    searchText: '请选择监测因子',
		    noMatch: '没有匹配 "{0}" 的项' ,
		    csvDispCount: 4,
		    captionFormat:'选中 {0} 项',
		    okCancelInMulti:true,
		    selectAll:true,
		    captionFormatAllSelected: "全选",
		    locale : ['确定', '取消', '全选']
		});

		// 先加载完对应机组的检测因子的数据模板，在加载图表
		var monitorFactors = $("#monitorFactors").val();
		if(monitorFactors!=null){
			params['monitorFactors'] = monitorFactors.toString();
		}else{
			params['monitorFactors'] = "";
			layer.alert("该机组下没有监测因子");
			return;
		}
		// 加载历史记录列表
		loadDetailedStateInfo(params, false);
		// 加载历史记录图表
		loadDetailedStateChart(params);
	});
}

function loadDetailedStateChart(params){
	var monitorFactors = $("#monitorFactors").val();
	var myChart = echarts.init(document.getElementById('detailedStateChart'));
	$.post("remoteMonitor/detailedState/detailedStateChart.do",params,function(data){
		var detailedStateList = JSON.parse(JSON.parse(data));
		var monitorFactorArr =[];
		var factorNameArr = detailedStateList.factorNameArr;
		for(i=0;i<monitorFactors.length;i++){
			var monitorFactorParam ={};
//			monitorFactorParam['name']=monitorFactors[i];
			monitorFactorParam['name']=factorNameArr[i];
		    monitorFactorParam['smooth']=true;
		    monitorFactorParam['type']='line';
		    monitorFactorParam['data']=detailedStateList.value[i];
		    monitorFactorArr[i]=monitorFactorParam
		}

		var option = {
			    tooltip: {
			        trigger: 'axis'
			    },
			    legend: {
			        data:factorNameArr,
			        top:10,
			        bottom:10,
			    },
			    toolbox: {
			        show: true,
			        feature: {
			            magicType: {type: ['line', 'bar']},
			            restore: {},
			            saveAsImage: {}
			        },
			        top:10,
			        right:35
			    },
			  grid: {
			        borderWidth: 0,
			        top: 50,
			        bottom: 50,
			        left:80,
			        right:80,
			        textStyle: {
			            color: "#fff"
			        }
			    },
			    xAxis:  {
			        type: 'category',
			        boundaryGap: false,
			        data: detailedStateList.time
			    },
			    yAxis: {
			        type: 'value',
			        axisLabel: {
			            formatter: '{value}'
			        }
			    },

			    series: monitorFactorArr
			};
		myChart.setOption(option, true);   //是否不跟之前设置的 option 进行合并，默认为 false，即合并。

	});

}

function changeDetailedStatePage(page) {

  	var id = null;
	var enterpriseId = $("#detailedState-content [name='enterpriseId']").val();
	var machineId = $("#detailedState-content [name='machineId']").val();
	var monitorFactors = $("#detailedState-content [name='monitorFactors']").val();
	var query_startTime = $("#detailedState-content [name='query_startTime']").val();
	var query_endTime = $("#detailedState-content [name='query_endTime']").val();
	// 获取权限树
	var params = {
		id : id,
		pageNo : page,
		enterpriseId : enterpriseId,
		machineId : machineId,
		monitorFactors : monitorFactors.toString(),
		query_startTime : query_startTime,
		query_endTime : query_endTime
	};
	loadDetailedStateInfo(params);
}

/**
 * 加载历史记录
 * @param params
 * @param flag
 */
function loadDetailedStateInfo(params, flag) {
	var url = "remoteMonitor/detailedState/detailedStateList.do";
	// 显示加载圈圈
	openLoading();
	$.post(url, params, function(data){
		$("#detailedStateContent").html(data);
		closeLoading();
	});
}


function chooseDate(flag){
    var myDate=new Date();
	if(flag==1){
		var startTime = new Date().format("yyyy-MM-dd 00:00:00");
		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    $("#detailedState-content [name='query_startTime']").val(startTime);
		$("#detailedState-content [name='query_endTime']").val(endTime);
	}else if(flag==2){
		myDate.setMonth(myDate.getMonth()-1);
		var startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    $("#detailedState-content [name='query_startTime']").val(startTime);
		$("#detailedState-content [name='query_endTime']").val(endTime);
	}else if(flag==3){
		myDate.setMonth(myDate.getMonth()-3);
		var startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    $("#detailedState-content [name='query_startTime']").val(startTime);
		$("#detailedState-content [name='query_endTime']").val(endTime);
	}
	searchDetailedStateList()
}
