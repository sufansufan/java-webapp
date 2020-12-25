var index =0;
$(function(){
//	searchHistoryRecordList();
	timerRefresh();
});

var timeDataTicket;
function timerRefresh(){
	var selectedMenuName = $(".left .layui-nav .layui-this a[onclick*='addMainTab']").attr("tabname");
	if(selectedMenuName=="运行趋势"){
		clearInterval(timeDataTicket);
		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
		$("#operationTrend-content [name='query_endTime']").val(endTime);
		searchHistoryRecordList();
		timeDataTicket = setTimeout(function() {
			timerRefresh();
		}, 180*1000);
	}else{
		clearTimeout(timeDataTicket);
	}
}

/**
 * 信息查询
 */
function searchHistoryRecordList() {
	var storage_enterpriseId = sessionStorage.getItem("enterpriseId");
	var storage_condensingDeviceNum = sessionStorage.getItem("condensingDeviceNum");
	//获取完先移除当前的缓存
//	sessionStorage.removeItem("enterpriseId")
	sessionStorage.removeItem("condensingDeviceNum")
//	 if(storage_enterpriseId!=null && storage_condensingDeviceNum !=null){
	 if(storage_enterpriseId!=null){
		 if(index==0){
			 index++;
			 $("#operationTrend-content [name='enterpriseId']").val(storage_enterpriseId);
			 changeCondensingDeviceList(storage_condensingDeviceNum);
		 }

	 }
	openLoading();
	var params = {};
	var enterpriseId = $("#operationTrend-content [name='enterpriseId']").val();
	var condensingDeviceNum = $("#operationTrend-content [name='condensingDeviceNum']").val();
	 var query_startTime = $("#operationTrend-content [name='query_startTime']").val();
	 var query_endTime = $("#operationTrend-content [name='query_endTime']").val();
	 if(storage_enterpriseId!=null){
		 enterpriseId = storage_enterpriseId;
	 }
	 if(storage_condensingDeviceNum!=null){
		 condensingDeviceNum = storage_condensingDeviceNum;
	 }
	params['enterpriseId'] = enterpriseId;
	params['condensingDeviceNum'] = condensingDeviceNum;
	params['query_startTime'] = query_startTime;
	params['query_endTime'] = query_endTime;
	// 加载历史记录列表
	loadHistoryRecordInfo(params);
	// 加载报警信息
	loadAlarmInfo(params);
}

//改变机组列表
function changeCondensingDeviceList(storage_condensingDeviceNum){
	var params = {};
	var enterpriseId = $("#operationTrend-content [name='enterpriseId']").val();
	sessionStorage.setItem("enterpriseId", enterpriseId);
	params['enterpriseId'] = enterpriseId;
	$.post("remoteMonitor/operationTrend/changeCondensingDevice.do",params,function(data){
		var condensingDeviceList = JSON.parse(JSON.parse(data));
		$("#condensingDeviceNumSelect").empty();

		var html = '<select id="condensingDeviceNum" name="condensingDeviceNum" onchange="searchHistoryRecordList()" style="display: inline-block;width:140px;height: 18px">';
		for(i=0;i<condensingDeviceList.length;i++){
			if(i==0){
				html += '<option value="'+condensingDeviceList[i].condensingDeviceNum+'"  selected="selected">'+condensingDeviceList[i].condensingDeviceName+'</option>';
			}else{
				html += '<option value="'+condensingDeviceList[i].condensingDeviceNum+'" >'+condensingDeviceList[i].condensingDeviceName+'</option>';
			}
		}
		html += '</select>';
		$("#condensingDeviceNumSelect").append(html);
	    if(storage_condensingDeviceNum!=null){
	    	 $("#operationTrend-content [name='condensingDeviceNum']").val(storage_condensingDeviceNum);
	    }

		$("#operationTrend-content select[name='condensingDeviceNum']").SumoSelect({
		    placeholder:"请选择机组名称",
		    search: true,
		    searchText: '请选择机组名称',
		    noMatch: '没有匹配 "{0}" 的项' ,
		    csvDispCount: 2,
		    captionFormat:'选中 {0} 项',
		    okCancelInMulti:true,
		    selectAll:false,
		    locale : ['确定', '取消']
		});

	});
}


function loadHistoryRecordInfo(params){
	$.post("remoteMonitor/operationTrend/historyRecordList.do",params,function(data){
		closeLoading();
		var historyRecordList = JSON.parse(JSON.parse(data));
		//数值
		var pressureValueList = historyRecordList["pressure"];
		var temperatureValueList = historyRecordList["temperature"];
		var vibrationValueList = historyRecordList["vibration"];
		var electricValueList = historyRecordList["electric"];
		var positionValueList = historyRecordList["position"];
		//因子名称
		var pressureFactorNameArr = historyRecordList["pressureFactorNameArr"];
		var temperatureFactorNameArr = historyRecordList["temperatureFactorNameArr"];
		var vibrationFactorNameArr = historyRecordList["vibrationFactorNameArr"];
		var electricFactorNameArr = historyRecordList["electricFactorNameArr"];
		var positionFactorNameArr = historyRecordList["positionFactorNameArr"];
		//因子单位
		var pressureFactorUnitArr = historyRecordList["pressureFactorUnitArr"];
		var temperatureFactorUnitArr = historyRecordList["temperatureFactorUnitArr"];
		var vibrationFactorUnitArr = historyRecordList["vibrationFactorUnitArr"];
		var electricFactorUnitArr = historyRecordList["electricFactorUnitArr"];
		var positionFactorUnitArr = historyRecordList["positionFactorUnitArr"];

		$("#pressureTable").empty();
		$("#temperatureTable").empty();
		$("#vibrationTable").empty();
		$("#electricTable").empty();
		$("#positionTable").empty();

		$("#pressureThead").empty();
		$("#temperatureThead").empty();
		$("#vibrationThead").empty();
		$("#electricThead").empty();
		$("#positionThead").empty();

		var pressureTheadHtml ="<tr><th style='width:70px'>采集时间</th>";
		for(i=0;i<pressureFactorNameArr.length;i++){
			pressureTheadHtml +="<th>"+pressureFactorNameArr[i]+"<br>"+pressureFactorUnitArr[i]+"</th>"
		}
		pressureTheadHtml +="</tr>";
		$("#pressureThead").append(pressureTheadHtml);

		var temperatureTheadHtml ="<tr><th style='width:70px'>采集时间</th>";
		for(i=0;i<temperatureFactorNameArr.length;i++){
			temperatureTheadHtml +="<th>"+temperatureFactorNameArr[i]+"<br>"+temperatureFactorUnitArr[i]+"</th>"
		}
		temperatureTheadHtml +="</tr>";
		$("#temperatureThead").append(temperatureTheadHtml);

		var vibrationTheadHtml ="<tr><th>采集时间</th>";
		for(i=0;i<vibrationFactorNameArr.length;i++){
			vibrationTheadHtml +="<th>"+vibrationFactorNameArr[i]+"<br>"+vibrationFactorUnitArr[i]+"</th>"
		}
		vibrationTheadHtml +="</tr>";
		$("#vibrationThead").append(vibrationTheadHtml);

		var electricTheadHtml ="<tr><th>采集时间</th>";
		for(i=0;i<electricFactorNameArr.length;i++){
			electricTheadHtml +="<th>"+electricFactorNameArr[i]+"<br>"+electricFactorUnitArr[i]+"</th>"
		}
		electricTheadHtml +="</tr>";
		$("#electricThead").append(electricTheadHtml);

		var positionTheadHtml ="<tr><th>采集时间</th>";
		for(i=0;i<positionFactorNameArr.length;i++){
			positionTheadHtml +="<th>"+positionFactorNameArr[i]+"<br>"+positionFactorUnitArr[i]+"</th>"
		}
		electricTheadHtml +="</tr>";
		$("#positionThead").append(positionTheadHtml);

		$("#pressureTable").height(350-$("#pressureThead").height());
		$("#temperatureTable").height(350-$("#temperatureThead").height());
		$("#vibrationTable").height(250-$("#vibrationThead").height());
		$("#electricTable").height(250-$("#electricThead").height());

//		$("#pressureThead").append("<tr><th style='width:70px'>采集时间</th><th>"+pressureFactorNameArr[0]+"<br>"+pressureFactorUnitArr[0]+"</th><th>"+pressureFactorNameArr[1]+"<br>"+pressureFactorUnitArr[1]+"</th><th>"+pressureFactorNameArr[2]+"<br>"+pressureFactorUnitArr[2]+"</th><th>"+pressureFactorNameArr[3]+"<br>"+pressureFactorUnitArr[3]+"</th><th>"+pressureFactorNameArr[4]+"<br>"+pressureFactorUnitArr[4]+"</th><th>"+pressureFactorNameArr[5]+"<br>"+pressureFactorUnitArr[5]+"</th><th>"+pressureFactorNameArr[6]+"<br>"+pressureFactorUnitArr[6]+"</th><th>"+pressureFactorNameArr[7]+"<br>"+pressureFactorUnitArr[7]+"</th></tr>");
//		$("#temperatureThead").append("<tr><th style='width:70px'>采集时间</th><th>"+temperatureFactorNameArr[0]+"<br>"+temperatureFactorUnitArr[0]+"</th><th>"+temperatureFactorNameArr[1]+"<br>"+temperatureFactorUnitArr[1]+"</th><th>"+temperatureFactorNameArr[2]+"<br>"+temperatureFactorUnitArr[2]+"</th><th>"+temperatureFactorNameArr[3]+"<br>"+temperatureFactorUnitArr[3]+"</th><th>"+temperatureFactorNameArr[4]+"<br>"+temperatureFactorUnitArr[4]+"</th><th style='width:90px'>"+temperatureFactorNameArr[5]+"<br>"+temperatureFactorUnitArr[5]+"</th><th>"+temperatureFactorNameArr[6]+"<br>"+temperatureFactorUnitArr[6]+"</th></tr>");
//		$("#vibrationThead").append("<tr><th>采集时间</th><th>"+vibrationFactorNameArr[0]+"<br>"+vibrationFactorUnitArr[0]+"</th></tr>");
//		$("#electricThead").append("<tr><th>采集时间</th><th>"+electricFactorNameArr[0]+"<br>"+electricFactorUnitArr[0]+"</th></tr>");
//		$("#positionThead").append("<tr><th>采集时间</th><th>"+positionFactorNameArr[0]+"<br>"+positionFactorUnitArr[0]+"</th><th>"+positionFactorNameArr[1]+"<br>"+positionFactorUnitArr[1]+"</th><th>"+positionFactorNameArr[2]+"<br>"+positionFactorUnitArr[2]+"</th><th>"+positionFactorNameArr[3]+"<br>"+positionFactorUnitArr[3]+"</th><th>"+positionFactorNameArr[4]+"<br>"+positionFactorUnitArr[4]+"</th><th>"+positionFactorNameArr[5]+"<br>"+positionFactorUnitArr[5]+"</th><th>"+positionFactorNameArr[6]+"<br>"+positionFactorUnitArr[6]+"</th><th>"+positionFactorNameArr[7]+"<br>"+positionFactorUnitArr[7]+"</th></tr>");

		if(pressureValueList.length==0){
			$("#pressureTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
		}
		if(temperatureValueList.length==0){
			$("#temperatureTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
		}
		if(vibrationValueList.length==0){
			$("#vibrationTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
		}
		if(electricValueList.length==0){
			$("#electricTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
		}
		if(positionValueList.length==0){
			$("#positionTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
		}
		//遍历压力列表
		for(i=0;i<pressureValueList.length;i++){
			var pressureValue = pressureValueList[i];
			var html ="<tr>";
			if(pressureValue[0]!=null){
				html +="<td style='width:70px'>"+pressureValue[0]+"</td>";
			}else{
				html +="<td style='width:70px'>--</td>";
			}
			for(j=0;j<pressureFactorNameArr.length;j++){
				if(pressureValue[j+1]!=null){
					html +="<td>"+pressureValue[j+1]+"</td>";
				}else{
					html +="<td>--</td>";
				}
			}
			html +="</tr>";
			$("#pressureTable").append(html);
		}
		//遍历温度列表
		for(i=0;i<temperatureValueList.length;i++){
			var temperatureValue = temperatureValueList[i];
			var html ="<tr>";
			if(temperatureValue[0]!=null){
				html +="<td style='width:70px'>"+temperatureValue[0]+"</td>";
			}else{
				html +="<td style='width:70px'>--</td>";
			}
			for(j=0;j<temperatureFactorNameArr.length;j++){
				if(temperatureValue[j+1]!=null){
					html +="<td>"+temperatureValue[j+1]+"</td>";
				}else{
					html +="<td>--</td>";
				}
			}
			html +="</tr>";
			$("#temperatureTable").append(html);
		}
		//遍历振动列表
		for(i=0;i<vibrationValueList.length;i++){
			var vibrationValue = vibrationValueList[i];
			var html ="<tr>";
			if(vibrationValue[0]!=null){
				html +="<td>"+vibrationValue[0]+"</td>";
			}else{
				html +="<td>--</td>";
			}
			for(j=0;j<vibrationFactorNameArr.length;j++){
				if(vibrationValue[j+1]!=null){
					html +="<td>"+vibrationValue[j+1]+"</td>";
				}else{
					html +="<td>--</td>";
				}
			}
			html +="</tr>";
			$("#vibrationTable").append(html);
		}
		//遍历马达电流列表
		for(i=0;i<electricValueList.length;i++){
			var electricValue = electricValueList[i];
			var html ="<tr>";
			if(electricValue[0]!=null){
				html +="<td>"+electricValue[0]+"</td>";
			}else{
				html +="<td>--</td>";
			}
			for(j=0;j<electricFactorNameArr.length;j++){
				if(electricValue[j+1]!=null){
					html +="<td>"+electricValue[j+1]+"</td>";
				}else{
					html +="<td>--</td>";
				}
			}
			html +="</tr>";
			$("#electricTable").append(html);
		}
		//遍历位置列表
		for(i=0;i<positionValueList.length;i++){
			var positionValue = positionValueList[i];
			var html ="<tr>";
			if(positionValue[0]!=null){
				html +="<td>"+positionValue[0]+"</td>";
			}else{
				html +="<td>--</td>";
			}
			for(j=0;j<positionFactorNameArr.length;j++){
				if(positionValue[j+1]!=null){
					html +="<td>"+positionValue[j+1]+"</td>";
				}else{
					html +="<td>--</td>";
				}
			}
			html +="</tr>";
			$("#positionTable").append(html);
		}

		//加载图表
		var pressureValueChart = historyRecordList["pressureChart"];
		var temperatureValueChart = historyRecordList["temperatureChart"];
		var vibrationValueChart = historyRecordList["vibrationChart"];
		var electricValueChart = historyRecordList["electricChart"];

		var pressureFactorArr =[];
		var temperatureFactorArr =[];
		var vibrationFactorArr =[];
		var electricFactorArr =[];
		var pressureTimeArr = pressureValueChart.timeChart;
		var temperatureTimeArr = temperatureValueChart.timeChart;
		var vibrationTimeArr = vibrationValueChart.timeChart;
		var electricTimeArr = electricValueChart.timeChart;
		for(i=0;i<pressureTimeArr.length;i++){
			pressureTimeArr[i]=pressureTimeArr[i].replace(" ","\n")
		}
		for(i=0;i<temperatureTimeArr.length;i++){
			temperatureTimeArr[i]=temperatureTimeArr[i].replace(" ","\n")
		}
		for(i=0;i<vibrationTimeArr.length;i++){
			vibrationTimeArr[i]=vibrationTimeArr[i].replace(" ","\n")
		}
		for(i=0;i<electricTimeArr.length;i++){
			electricTimeArr[i]=electricTimeArr[i].replace(" ","\n")
		}

		var pressureChart = echarts.init(document.getElementById('pressureChart'));
		var temperatureChart = echarts.init(document.getElementById('temperatureChart'));
		var vibrationChart = echarts.init(document.getElementById('vibrationChart'));
		var electricChart = echarts.init(document.getElementById('electricChart'));
		for(i=0;i<pressureFactorNameArr.length;i++){
			var monitorFactorParam ={};
				monitorFactorParam['name']=pressureFactorNameArr[i];
			    monitorFactorParam['smooth']=true;
			    monitorFactorParam['type']='line';
			    monitorFactorParam['data']=pressureValueChart.value[i];
			    pressureFactorArr[i]=monitorFactorParam;
		}
		for(i=0;i<temperatureFactorNameArr.length;i++){
			var monitorFactorParam ={};
			    monitorFactorParam['name']=temperatureFactorNameArr[i];
			    monitorFactorParam['smooth']=true;
			    monitorFactorParam['type']='line';
			    monitorFactorParam['data']=temperatureValueChart.value[i];
			    temperatureFactorArr[i]=monitorFactorParam;
		}
		for(i=0;i<vibrationFactorNameArr.length;i++){
			var monitorFactorParam ={};
			    monitorFactorParam['name']=vibrationFactorNameArr[i];
			    monitorFactorParam['smooth']=true;
			    monitorFactorParam['type']='line';
			    monitorFactorParam['data']=vibrationValueChart.value[i];
			    vibrationFactorArr[i]=monitorFactorParam;
		}
		for(i=0;i<electricFactorNameArr.length;i++){
			var monitorFactorParam ={};
			    monitorFactorParam['name']=electricFactorNameArr[i];
			    monitorFactorParam['smooth']=true;
			    monitorFactorParam['type']='line';
			    monitorFactorParam['data']=electricValueChart.value[i];
			    electricFactorArr[i]=monitorFactorParam;
		}
		var pressureOption = {
			    tooltip: {
			        trigger: 'axis'
			    },
				title : {
					text : '压力趋势图\n(MPa)',
					top:10,
					textStyle:{
						fontWeight:'bold',
					    fontSize:15,
					    left:100
					}
				},
			    legend: {
			        data:pressureFactorNameArr,
			        top:10,
			        left:100
			    },
//			    toolbox: {
//			        show: true,
//			        feature: {
//			            magicType: {type: ['line', 'bar']},
//			            restore: {},
//			            saveAsImage: {}
//			        },
//			        top:10,
//			        right:35
//			    },
 				grid : {
 					left : '3%',
					right : '5%',
					bottom : '3%',
					top:'20%',
					containLabel : true
				},
			    xAxis:  {
			        type: 'category',
			        boundaryGap: false,
			        data: pressureTimeArr
			    },
			    yAxis: {
			        type: 'value',
			        axisLabel: {
			            formatter: '{value}'
			        }
			    },
			    series: pressureFactorArr
		};

		var temperatureOption = {
			    tooltip: {
			        trigger: 'axis'
			    },
 				title : {
					text : '温度趋势图\n(℃)',
					top:10,
					textStyle:{
						fontWeight:'bold',
					    fontSize:15,
					    left:100
					}
				},
			    legend: {
			        data:temperatureFactorNameArr,
			        top:10,
			        left:100
			    },
 				grid : {
 					left : '3%',
					right : '5%',
					bottom : '3%',
					top:'20%',
					containLabel : true
				},
			    xAxis:  {
			        type: 'category',
			        boundaryGap: false,
			        data: temperatureTimeArr
			    },
			    yAxis: {
			        type: 'value',
			        axisLabel: {
			            formatter: '{value}'
			        }
			    },
			    series: temperatureFactorArr
		};
		var vibrationOption = {
			    tooltip: {
			        trigger: 'axis'
			    },
			    title : {
					text : '振动趋势图\n(mm2/s)',
					top:10,
					textStyle:{
						fontWeight:'bold',
					    fontSize:15,
					    left:100
					}
				},
			    legend: {
			        data:vibrationFactorNameArr,
			        top:10
			    },
 				grid : {
 					top: 45,
 					left : '3%',
					right : '5%',
					bottom : '3%',
					top:'20%',
					containLabel : true
				},
			    xAxis:  {
			        type: 'category',
			        boundaryGap: false,
			        data: vibrationTimeArr
			    },
			    yAxis: {
			        type: 'value',
			        axisLabel: {
			            formatter: '{value}'
			        }
			    },
			    series: vibrationFactorArr
		};
		var electricOption = {
			    tooltip: {
			        trigger: 'axis'
			    },
				title : {
					text : '马达电流\n(A)',
					top:10,
					textStyle:{
						fontWeight:'bold',
					    fontSize:15,
					    left:100
					}
				},
			    legend: {
			        data:electricFactorNameArr,
			        top:10
			    },
 				grid : {
 					top: 45,
 					left : '3%',
					right : '5%',
					bottom : '3%',
					top:'20%',
					containLabel : true
				},
			    xAxis:  {
			        type: 'category',
			        boundaryGap: false,
			        data: electricTimeArr
			    },
			    yAxis: {
			        type: 'value',
			        axisLabel: {
			            formatter: '{value}'
			        }
			    },
			    series: electricFactorArr
		};
		pressureChart.setOption(pressureOption, true);   //是否不跟之前设置的 option 进行合并，默认为 false，即合并。
		temperatureChart.setOption(temperatureOption, true);   //是否不跟之前设置的 option 进行合并，默认为 false，即合并。
		vibrationChart.setOption(vibrationOption, true);   //是否不跟之前设置的 option 进行合并，默认为 false，即合并。
		electricChart.setOption(electricOption, true);   //是否不跟之前设置的 option 进行合并，默认为 false，即合并。


	});
}


function loadAlarmInfo(params){
	$.post("remoteMonitor/alarmInfo/alarmTrendList.do",params,function(data){
		var result = JSON.parse(JSON.parse(data));
		var alarmInfoList = result.alarmInfoList;
		var deviceAlarmInfoList = result.deviceAlarmInfoList;
		$("#earlyAlarmTable").empty();
		$("#alarmTable").empty();
		var earlyAlarmHtml ="";
		var alarmHtml ="";
		var earlyAlarmCount = 0;
		var alarmCount = 0;
		for(i=0;i<alarmInfoList.length;i++){
			if(alarmInfoList[i].alarmType==0){
				earlyAlarmHtml += '<tr style="height:22px"><td style="width: 30%;">'+getFormatDate(alarmInfoList[i].createTime)+'</td><td style="width: 40%;">'+alarmInfoList[i].alarmContent+'</td><td style="width: 30%;">'+getFormatDate(alarmInfoList[i].recoveryTime)+'</td></tr>';
			}else if(alarmInfoList[i].alarmType==1){
				alarmHtml += '<tr style="height:22px"><td style="width: 30%;">'+getFormatDate(alarmInfoList[i].createTime)+'</td><td style="width: 40%;">'+alarmInfoList[i].alarmContent+'</td><td style="width: 30%;">'+getFormatDate(alarmInfoList[i].recoveryTime)+'</td></tr>';
			}
		}
		for(i=0;i<deviceAlarmInfoList.length;i++){
			if(deviceAlarmInfoList[i].alarmType==0){
				earlyAlarmCount++;
			}else if(deviceAlarmInfoList[i].alarmType==1){
				alarmCount++;
			}
		}
		if(earlyAlarmHtml==""){
			earlyAlarmHtml += '<tr style="height:22px"><td>无报警</td></tr>';
		}
		if(alarmHtml==""){
			alarmHtml += '<tr style="height:22px"><td>无报警</td></tr>';
		}

		if(earlyAlarmCount>0){
			$("#earlyAlarmDiv").css("background-color","#F2CC0C");
		}else{
			$("#earlyAlarmDiv").css("background-color","#47B347");

		}
		if(alarmCount>0){
			$("#alarmDiv").css("background-color","#F2495C");
		}else{
			$("#alarmDiv").css("background-color","#47B347");
		}
		$("#earlyAlarmTable").append(earlyAlarmHtml);
		$("#alarmTable").append(alarmHtml);
		$("#earlyAlarmCount").text(earlyAlarmCount);
		$("#alarmCount").text(alarmCount);


	});
}

function chooseDate(flag){
    var myDate=new Date();
	if(flag==1){
		var startTime = new Date().format("yyyy-MM-dd 00:00:00");
		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    $("#operationTrend-content [name='query_startTime']").val(startTime);
		$("#operationTrend-content [name='query_endTime']").val(endTime);
	}else if(flag==2){
		myDate.setMonth(myDate.getMonth()-1);
		var startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    $("#operationTrend-content [name='query_startTime']").val(startTime);
		$("#operationTrend-content [name='query_endTime']").val(endTime);
	}else if(flag==3){
		myDate.setMonth(myDate.getMonth()-3);
		var startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    $("#operationTrend-content [name='query_startTime']").val(startTime);
		$("#operationTrend-content [name='query_endTime']").val(endTime);
	}else if(flag==4){
		myDate.setDate(myDate.getDate()-7);
		var startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    $("#operationTrend-content [name='query_startTime']").val(startTime);
		$("#operationTrend-content [name='query_endTime']").val(endTime);
	}
	searchHistoryRecordList()
}


function exportHistoryRecord(flag){
	openLoading();
	var enterpriseId = $("#operationTrend-content [name='enterpriseId']").val();
	var condensingDeviceNum = $("#operationTrend-content [name='condensingDeviceNum']").val();
	var query_startTime = $("#operationTrend-content [name='query_startTime']").val();
	var query_endTime = $("#operationTrend-content [name='query_endTime']").val();
    window.location.href="remoteMonitor/operationTrend/exportHistoryRecordList.do?enterpriseId=" + enterpriseId + "&query_startTime=" + query_startTime + "&query_endTime=" + query_endTime+"&condensingDeviceNum=" + condensingDeviceNum+"&flag="+flag;
    closeLoading();
}
