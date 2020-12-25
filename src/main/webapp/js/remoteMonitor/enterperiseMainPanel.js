(function(){
	closeLoading();
	
	var charts = [];
  var doms = {
    baseChart: $('#enterprisePanel #baseChart')[0],
    exceptionRing: $('#enterprisePanel #excpLeft')[0],
		exceptionBar: $('#enterprisePanel #excpRight')[0],
		dailyCheckRing: $('#enterprisePanel #dailyCheckLeft')[0],
		dailyCheckBar: $('#enterprisePanel #dailyCheckRight')[0],
		periodCheckRing: $('#enterprisePanel #periodCheckLeft')[0],
		periodCheckBar: $('#enterprisePanel #periodCheckRight')[0],
		periodMaintainRing: $('#enterprisePanel #periodMaintainLeft')[0],
		periodMaintainBar: $('#enterprisePanel #periodMaintainRight')[0],
    periodDeviceMaintain: $('#periodDeviceMaintain')[0],
    startTime: $('#enterprisePanel #query_startTime'),
    endTime: $('#enterprisePanel #query_endTime'),
  };
  var datas = {};
	console.log("${defaultTime}");
	var chartList = [
		{ func: renderMainChart, ele: doms.baseChart, title: ''},
		{ func: renderRingChart, ele: doms.exceptionRing, title: '异常工单'},
		{ func: renderBarChart, ele: doms.exceptionBar, title: '班组异常工单处置情况'},
		{ func: renderRingChart, ele: doms.dailyCheckRing, title: '日常点检'},
		{ func: renderBarChart, ele: doms.dailyCheckBar, title: '班组排班情况'},
		{ func: renderRingChart, ele: doms.periodCheckRing, title: '定期点检'},
		{ func: renderBarChart, ele: doms.periodCheckBar, title: '班组排班情况'},
		{ func: renderRingChart, ele: doms.periodMaintainRing, title: '定期保养'},
		{ func: renderBarChart, ele: doms.periodMaintainBar, title: '班组排班情况'},
		{ func: renderBarChart, ele: doms.periodDeviceMaintain, title: '设备点检情况'},
	];
  
	setTimeout(function () {
		charts = chartList.reduce(function(list, item) {
			var c = item.func(item.ele, item.title);
			if (c) {
				list.push(c);
			}
			console.log(c);
			return list;
		}, []);
		$(window).bind('resize', rerenderCharts);
  }, 0);

  doms.startTime.bind('focus', showDatePicker);
  doms.endTime.bind('focus', showDatePicker);

  function showDatePicker () {
    WdatePicker({ isShowClear:false, readOnly:true, dateFmt:'yyyy-MM-dd HH:mm:ss' });
  }
  
	var resizeTimer = null;
	function rerenderCharts () {
    if (resizeTimer) {
      clearTimeout(resizeTimer);
    }
    resizeTimer = setTimeout(function(){
      console.log("window resize");
      if (!charts || !charts.length) return;
      // 重新渲染图表
      charts.forEach(chart => {
        chart.resize();
      });
    }, 200);
	}
	
  function renderMainChart (ele) {
    var option = {
      color: ['#22A3ED'],
      title: {
        text: '每日用电量',
        x:'center',
        y:'top',
        textStyle: {
          fontSize: 14,
          lineHeight: 24,
          color: '#333',
        }
      },
      grid: { left: '15%' },
      xAxis: {
        type: 'category',
        data: ['10月1日', '10月2日', '10月3日', '10月4日', '10月5日', '10月6日', '10月7日'],
        axisLabel: {
          rotate: 45,
        }
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: [1200, 2000, 1500, 800, 500, 1000, 2000],
        type: 'bar',
        barWidth: 14,
      }],
    };

		return renderChart(ele, option);
  }
  
  function renderRingChart (ele, title) {
    var option = {
      color: ['#F5756C', '#3CD6A0'],
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
      },
      title: {
        text: title || '异常工单',
        x:'center',
        y:'top',
        textStyle: {
          fontSize: 14,
          lineHeight: 24,
          color: '#333',
        }
     },
      legend: {
        orient: 'vertical',
        bottom: 0,
        data: ['未完成', '完成']
      },
      graphic: [{　　
        type: 'text',
        left: 'center',　　　　　　　　　　
        top: '46%',
        style: {　　　　　　　　　　　　　　　　
          text: "总数",
          textAlign: 'center',
          fill: '#999',　
          width: 30,
          height: 30,
          fontSize: 14,
        }
    	}, {
        type: 'text',
        left: 'center',
        top: '50%',
        style: {
            text: '101',
            textAlign: 'center',
            fill: '#000',
            width: 30,
            height: 30,
            fontSize: 20,
        }
      }],
      series: [
        {
          name: '每日用电量',
          type: 'pie',
          radius: ['40%', '65%'],
          avoidLabelOverlap: false,
          label:{            //饼图图形上的文本标签
            normal:{
              show:true,
              position:'outer', //标签的位置
              textStyle : {
                  fontSize : 12   //文字的字体大小
              },
              formatter:' {b} \n {c} | {d}%',
            }
          },
          labelLine: {
            show: true,
          },
          data: [
            {value: 2, name: '未完成'},
            {value: 99, name: '完成'},
          ]
        }
    	]
		};

    return renderChart(ele, option);
  }
  
  function renderBarChart (ele, title) {
    var option = {
      color: ['#3CD6A0', '#FAE55E', '#F5756C', ],
      title: {
        text: title || '每日用电量',
        x:'center',
        y:'top',
        textStyle: {
          fontSize: 14,
          lineHeight: 24,
          color: '#333',
        }
      },
      legend: {
        orient: 'horizontal',
        data: ['已完成', '待确认', '未完成'],
        top: 30,
     	},
      grid: {top: 80},
        dataset: {
        	source: [
            ['状态', '已完成', '待确认', '未完成'],
            ['班组1', 43.3, 85.8, 93.7],
            ['班组2', 83.1, 73.4, 55.1],
            ['班组3', 86.4, 65.2, 82.5],
            ['班组4', 72.4, 53.9, 39.1]
        	]
    		},
			xAxis: {type: 'category'},
			yAxis: {},
			series: [
        {type: 'bar', barWidth: 14},
        {type: 'bar', barWidth: 14},
        {type: 'bar', barWidth: 14},
    	]
    };

		return renderChart(ele, option);
  }

	function renderChart (ele, option) {
		if (!ele || !option) return;
    
    var myChart = echarts.init(ele, { width: '86%' });
    myChart.setOption(option);
    
    return myChart;
  }
})();
// $(function(){
// //	searchHistoryRecordList();
	
// 	if($("#refreshSwitch_enterprise").prop("checked")){
// 		timerRefresh();
// 	}else{
// 		searchHistoryRecordList();
// 	}
// });
// var timeDataTicket;
// function timerRefresh(){
// 	var selectedMenuName = $(".left .layui-nav .layui-this a[onclick*='addMainTab']").attr("tabname");
// 	if(selectedMenuName=="企业主看板"){
// 		clearInterval(timeDataTicket);
// 		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
// 		$("#enterpriseMainPanel-content [name='query_endTime']").val(endTime);
// 		searchHistoryRecordList();
// 		timeDataTicket = setTimeout(function() {
// 			timerRefresh();
// 		}, 180*1000);
// 	}else{
// 		clearTimeout(timeDataTicket);
// 	}
// }

// form.on('switch(switchTest)', function(data){
//        if(this.checked){
//        	clearInterval(timeDataTicket);
//        	timerRefresh();
//        }else{
//     	   clearTimeout(timeDataTicket);
//        }
// });


// /**
//  * 信息查询
//  */
// function searchHistoryRecordList() {
// 	var storage_deviceCode = sessionStorage.getItem("deviceCode");
// 	//获取完先移除当前的缓存
// 	sessionStorage.removeItem("deviceCode")
// 	 if(storage_deviceCode!=null){
// 		 $("#enterpriseMainPanel-content [name='deviceCode']").val(storage_deviceCode);
// 	 }
// 	openLoading();
// 	var params = {};
// 	var deviceCode = $("#enterpriseMainPanel-content [name='deviceCode']").val();
// 	//把企业对于的deviceCode存到缓存中，以便于从企业主看板，调整到其他二级目录的话，可以对应当前企业主看板所选的企业
// 	sessionStorage.setItem("deviceCode", deviceCode);
//     var query_startTime = $("#enterpriseMainPanel-content [name='query_startTime']").val();
// 	var query_endTime = $("#enterpriseMainPanel-content [name='query_endTime']").val();
// 	params['deviceCode'] = deviceCode;
// 	params['condensingDeviceNum'] = '0';
// 	params['query_startTime'] = query_startTime;
// 	params['query_endTime'] = query_endTime;
// 	// 加载历史记录列表
// 	loadHistoryRecordInfo(params);

// 	// 加载报警信息
// 	loadAlarmInfo(params);
// }



// function loadHistoryRecordInfo(params){
// 	$.post("remoteMonitor/enterpriseMainPanel/historyRecordList.do",params,function(data){
// 		closeLoading();
// 		var historyRecordList = JSON.parse(JSON.parse(data));
// 		var pressureValueList = historyRecordList["pressure"];
// 		var temperatureValueList = historyRecordList["temperature"];
// 		var electricValueList = historyRecordList["electric"];
// 		var otherValuesValueList = historyRecordList["otherValues"];
		
// 		var pressureFactorNameArr = historyRecordList["pressureFactorNameArr"];
// 		var temperatureFactorNameArr = historyRecordList["temperatureFactorNameArr"];
// 		var electricFactorNameArr = historyRecordList["electricFactorNameArr"];
// 		var otherValuesFactorNameArr = historyRecordList["otherValuesFactorNameArr"];
		
// 		var pressureFactorUnitArr = historyRecordList["pressureFactorUnitArr"];
// 		var temperatureFactorUnitArr = historyRecordList["temperatureFactorUnitArr"];
// 		var electricFactorUnitArr = historyRecordList["electricFactorUnitArr"];
// 		var otherValuesFactorUnitArr = historyRecordList["otherValuesFactorUnitArr"];
		
// 		$("#pressureTable").empty();
// 		$("#temperatureTable").empty();
// 		$("#electricTable").empty();
// 		$("#otherValuesTable").empty();
		
// 		$("#pressureThead").empty();
// 		$("#temperatureThead").empty();
// 		$("#electricThead").empty();
// 		$("#otherValuesThead").empty();
		
// 		var pressureTheadHtml ="<tr><th style='width:70px'>采集时间</th>";
// 		for(i=0;i<pressureFactorNameArr.length;i++){
// 			pressureTheadHtml +="<th>"+pressureFactorNameArr[i]+"<br>"+pressureFactorUnitArr[i]+"</th>"
// 		}
// 		pressureTheadHtml +="</tr>";
// 		$("#pressureThead").append(pressureTheadHtml);
		
// 		var temperatureTheadHtml ="<tr><th style='width:70px'>采集时间</th>";
// 		for(i=0;i<temperatureFactorNameArr.length;i++){
// 			temperatureTheadHtml +="<th>"+temperatureFactorNameArr[i]+"<br>"+temperatureFactorUnitArr[i]+"</th>"
// 		}
// 		temperatureTheadHtml +="</tr>";
// 		$("#temperatureThead").append(temperatureTheadHtml);		
		
// 		var electricTheadHtml ="<tr><th>采集时间</th>";
// 		for(i=0;i<electricFactorNameArr.length;i++){
// 			electricTheadHtml +="<th>"+electricFactorNameArr[i]+"<br>"+electricFactorUnitArr[i]+"</th>"
// 		}
// 		electricTheadHtml +="</tr>";
// 		$("#electricThead").append(electricTheadHtml);
		
// 		var otherValuesHtml ="<tr><th>采集时间</th>";
// 		for(i=0;i<otherValuesFactorNameArr.length;i++){
// 			otherValuesHtml +="<th>"+otherValuesFactorNameArr[i]+"<br>"+otherValuesFactorUnitArr[i]+"</th>"
// 		}
// 		otherValuesHtml +="</tr>";
// 		$("#otherValuesThead").append(otherValuesHtml);
		
// 		$("#pressureTable").height(350-$("#pressureThead").height());
// 		$("#temperatureTable").height(350-$("#temperatureThead").height());
// 		$("#electricTable").height(250-$("#electricThead").height());
		
// //		$("#pressureThead").append("<tr><th>采集时间</th><th>"+pressureFactorNameArr[0]+"<br>"+pressureFactorUnitArr[0]+"</th><th>"+pressureFactorNameArr[1]+"<br>"+pressureFactorUnitArr[1]+"</th><th>"+pressureFactorNameArr[2]+"<br>"+pressureFactorUnitArr[2]+"</th><th>"+pressureFactorNameArr[3]+"<br>"+pressureFactorUnitArr[3]+"</th></tr>");
// //		$("#temperatureThead").append("<tr><th style='width:70px'>采集时间</th><th>"+temperatureFactorNameArr[0]+"<br>"+temperatureFactorUnitArr[0]+"</th><th>"+temperatureFactorNameArr[1]+"<br>"+temperatureFactorUnitArr[1]+"</th><th>"+temperatureFactorNameArr[2]+"<br>"+temperatureFactorUnitArr[2]+"</th><th>"+temperatureFactorNameArr[3]+"<br>"+temperatureFactorUnitArr[3]+"</th><th>"+temperatureFactorNameArr[4]+"<br>"+temperatureFactorUnitArr[4]+"</th><th>"+temperatureFactorNameArr[5]+"<br>"+temperatureFactorUnitArr[5]+"</th><th>"+temperatureFactorNameArr[6]+"<br>"+temperatureFactorUnitArr[6]+"</th><th>"+temperatureFactorNameArr[7]+"<br>"+temperatureFactorUnitArr[7]+"</th></tr>");
// //		$("#electricThead").append("<tr><th>采集时间</th><th>"+electricFactorNameArr[0]+"<br>"+electricFactorUnitArr[0]+"</th><th>"+electricFactorNameArr[1]+"<br>"+electricFactorUnitArr[1]+"</th><th>"+electricFactorNameArr[2]+"<br>"+electricFactorUnitArr[2]+"</th></tr>");
// //		$("#otherValuesThead").append("<tr><th>采集时间</th><th>"+otherValuesFactorNameArr[0]+"<br>"+otherValuesFactorUnitArr[0]+"</th><th>"+otherValuesFactorNameArr[1]+"<br>"+otherValuesFactorUnitArr[1]+"</th><th>"+otherValuesFactorNameArr[2]+"<br>"+otherValuesFactorUnitArr[2]+"</th><th>"+otherValuesFactorNameArr[3]+"<br>"+otherValuesFactorUnitArr[3]+"</th><th>"+otherValuesFactorNameArr[4]+"<br>"+otherValuesFactorUnitArr[4]+"</th><th>"+otherValuesFactorNameArr[5]+"<br>"+otherValuesFactorUnitArr[5]+"</th><th>"+otherValuesFactorNameArr[6]+"<br>"+otherValuesFactorUnitArr[6]+"</th><th>"+otherValuesFactorNameArr[7]+"<br>"+otherValuesFactorUnitArr[7]+"</th></tr>");
		
// 		if(pressureValueList.length==0){
// 			$("#pressureTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
// 		}
// 		if(temperatureValueList.length==0){
// 			$("#temperatureTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
// 		}
// 		if(electricValueList.length==0){
// 			$("#electricTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
// 		}
// 		if(otherValuesValueList.length==0){
// 			$("#otherValuesTable").append("<tr><td colspan='20'>没有相关数据</td></tr>");
// 		}
		
	
		
// 		//遍历压力列表
// 		for(i=0;i<pressureValueList.length;i++){
// 			var pressureValue = pressureValueList[i];
// 			var html ="<tr>";	
// 			if(pressureValue[0]!=null){
// 				html +="<td style='width:70px'>"+pressureValue[0]+"</td>";
// 			}else{
// 				html +="<td style='width:70px'>--</td>";
// 			}
// 			for(j=0;j<pressureFactorNameArr.length;j++){
// 				if(pressureValue[j+1]!=null){
// 					html +="<td>"+pressureValue[j+1]+"</td>";
// 				}else{
// 					html +="<td>--</td>";
// 				}
// 			}
// 			html +="</tr>";
// 			$("#pressureTable").append(html);
// 		}
// 		//遍历温度列表
// 		for(i=0;i<temperatureValueList.length;i++){
// 			var temperatureValue = temperatureValueList[i];
// 			var html ="<tr>";	
// 			if(temperatureValue[0]!=null){
// 				html +="<td style='width:70px'>"+temperatureValue[0]+"</td>";
// 			}else{
// 				html +="<td style='width:70px'>--</td>";
// 			}
// 			for(j=0;j<temperatureFactorNameArr.length;j++){
// 				if(temperatureValue[j+1]!=null){
// 					html +="<td>"+temperatureValue[j+1]+"</td>";
// 				}else{
// 					html +="<td>--</td>";
// 				}
// 			}
// 			html +="</tr>";
// 			$("#temperatureTable").append(html);
// 		}
// 		//遍历马达电流列表
// 		for(i=0;i<electricValueList.length;i++){
// 			var electricValue = electricValueList[i];
// 			var html ="<tr>";	
// 			if(electricValue[0]!=null){
// 				html +="<td>"+electricValue[0]+"</td>";
// 			}else{
// 				html +="<td>--</td>";
// 			}
// 			for(j=0;j<electricFactorNameArr.length;j++){
// 				if(electricValue[j+1]!=null){
// 					html +="<td>"+electricValue[j+1]+"</td>";
// 				}else{
// 					html +="<td>--</td>";
// 				}
// 			}
// 			html +="</tr>";
// 			$("#electricTable").append(html);
// 		}
// 		//其他数据列表
// 		for(i=0;i<otherValuesValueList.length;i++){
// 			var otherValuesValue = otherValuesValueList[i];
// 			var html ="<tr>";	
// 			if(otherValuesValue[0]!=null){
// 				html +="<td>"+otherValuesValue[0]+"</td>";
// 			}else{
// 				html +="<td>--</td>";
// 			}
// 			for(j=0;j<otherValuesFactorNameArr.length;j++){
// 				if(otherValuesValue[j+1]!=null){
// 					html +="<td>"+otherValuesValue[j+1]+"</td>";
// 				}else{
// 					html +="<td>--</td>";
// 				}
// 			}
// 			html +="</tr>";
// 			$("#otherValuesTable").append(html);
// 		}
				
// 		//加载图表
// 		var pressureValueChart = historyRecordList["pressureChart"];
// 		var temperatureValueChart = historyRecordList["temperatureChart"];
// 		var electricValueChart = historyRecordList["electricChart"];
		
// 		var pressureFactorArr =[];
// 		var temperatureFactorArr =[];
// 		var electricFactorArr =[];
// 		var pressureTimeArr = pressureValueChart.timeChart;
// 		var temperatureTimeArr = temperatureValueChart.timeChart;
// 		var electricTimeArr = electricValueChart.timeChart;
// 		for(i=0;i<pressureTimeArr.length;i++){
// 			pressureTimeArr[i]=pressureTimeArr[i].replace(" ","\n")
// 		}
// 		for(i=0;i<temperatureTimeArr.length;i++){
// 			temperatureTimeArr[i]=temperatureTimeArr[i].replace(" ","\n")
// 		}
// 		for(i=0;i<electricTimeArr.length;i++){
// 			electricTimeArr[i]=electricTimeArr[i].replace(" ","\n")
// 		}
// 		var pressureChart = echarts.init(document.getElementById('pressureChart'));
// 		var temperatureChart = echarts.init(document.getElementById('temperatureChart'));
// 		var electricChart = echarts.init(document.getElementById('electricChart'));
// 		for(i=0;i<pressureFactorNameArr.length;i++){
// 			var monitorFactorParam ={};
// 				monitorFactorParam['name']=pressureFactorNameArr[i];
// 			    monitorFactorParam['smooth']=true;
// 			    monitorFactorParam['type']='line';
// 			    monitorFactorParam['data']=pressureValueChart.value[i];
// 			    pressureFactorArr[i]=monitorFactorParam;
// 		}
// 		for(i=0;i<temperatureFactorNameArr.length;i++){
// 			var monitorFactorParam ={};
// 			    monitorFactorParam['name']=temperatureFactorNameArr[i];
// 			    monitorFactorParam['smooth']=true;
// 			    monitorFactorParam['type']='line';
// 			    monitorFactorParam['data']=temperatureValueChart.value[i];
// 			    temperatureFactorArr[i]=monitorFactorParam;
// 		}
// 		for(i=0;i<electricFactorNameArr.length;i++){
// 			var monitorFactorParam ={};
// 			    monitorFactorParam['name']=electricFactorNameArr[i];
// 			    monitorFactorParam['smooth']=true;
// 			    monitorFactorParam['type']='line';
// 			    monitorFactorParam['data']=electricValueChart.value[i];
// 			    electricFactorArr[i]=monitorFactorParam;
// 		}
// 		var pressureOption = {
// 			    tooltip: {
// 			        trigger: 'axis'
// 			    },
// 				title : {
// 					text : '压力趋势图\n(MPa)',
// 					top:10,
// 					textStyle:{
// 						fontWeight:'bold',
// 					    fontSize:15
// 					}
// 				},
// 			    legend: {
// 			        data:pressureFactorNameArr,
// 			        top:10,
// 			        left:100
// 			    },
//  				grid : {
//  					left : '3%',
// 					right : '5%',
// 					bottom : '3%',
// 					top:'20%',
// 					containLabel : true
// 				},
// 			    xAxis:  {
// 			        type: 'category',
// 			        boundaryGap: false,
// 			        data: pressureTimeArr
// 			    },
// 			    yAxis: {
// 			        type: 'value',
// 			        axisLabel: {
// 			            formatter: '{value}'
// 			        }
// 			    },
// 			    series: pressureFactorArr
// 		};

// 		var temperatureOption = {
// 			    tooltip: {
// 			        trigger: 'axis'
// 			    },
//  				title : {
// 					text : '温度趋势图\n(℃)',
// 					top:10,
// 					textStyle:{
// 						fontWeight:'bold',
// 					    fontSize:15,
// 					    left:100
// 					}
// 				},
// 			    legend: {
// 			        data:temperatureFactorNameArr,
// 			        top:10,
// 			        left:100,
// 			    },
//  				grid : {
//  					left : '3%',
// 					right : '5%',
// 					bottom : '3%',
// 					top:'20%',
// 					containLabel : true
// 				},
// 			    xAxis:  {
// 			        type: 'category',
// 			        boundaryGap: false,
// 			        data: temperatureTimeArr
// 			    },
// 			    yAxis: {
// 			        type: 'value',
// 			        axisLabel: {
// 			            formatter: '{value}'
// 			        }
// 			    },		  
// 			    series: temperatureFactorArr
// 		};
// 		var electricOption = {
// 			    tooltip: {
// 			        trigger: 'axis'
// 			    },
// 				title : {
// 					text : '马达电流\n(A)',
// 					top:10,
// 					textStyle:{
// 						fontWeight:'bold',
// 					    fontSize:15,
// 					    left:100
// 					}
// 				},
// 			    legend: {
// 			        data:electricFactorNameArr,
// 			        top:10,
// 			        left:100,
// 			    },
//  				grid : {
//  					top: 45,
//  					left : '3%',
// 					right : '5%',
// 					bottom : '3%',
// 					top:'20%',
// 					containLabel : true
// 				},
// 			    xAxis:  {
// 			        type: 'category',
// 			        boundaryGap: false,
// 			        data: electricTimeArr
// 			    },
// 			    yAxis: {
// 			        type: 'value',
// 			        axisLabel: {
// 			            formatter: '{value}'
// 			        }
// 			    },		  
// 			    series: electricFactorArr
// 		};
// 		pressureChart.setOption(pressureOption, true);   //是否不跟之前设置的 option 进行合并，默认为 false，即合并。
// 		temperatureChart.setOption(temperatureOption, true);   //是否不跟之前设置的 option 进行合并，默认为 false，即合并。
// 		electricChart.setOption(electricOption, true);   //是否不跟之前设置的 option 进行合并，默认为 false，即合并。
// 	});
// }


// function loadAlarmInfo(params){
// 	params['flag']='enterprise';
// 	//企业报警数跟信息
// 	$.post("remoteMonitor/alarmInfo/alarmTrendList.do",params,function(data){
// 		var result = JSON.parse(JSON.parse(data));
// 		var alarmInfoList = result.alarmInfoList;
// 		var mainAlarmCount = result.alarmInfoListCount;
// 		var alarmDeviceNameList = result.alarmDeviceNameList;
// 		$("#mainAlarmTable").empty();
// 		var mainAlarmHtml ="";
		
// 		for(i=0;i<alarmInfoList.length;i++){
// 				mainAlarmHtml += '<tr style="height:22px">';
// 				mainAlarmHtml += '<td style="width: 20%;">'+getFormatDate(alarmInfoList[i].createTime)+'</td>';
// 				var condensingDeviceName ="";
// 				if(alarmInfoList[i].condensingDeviceNum=="0"){
// 					condensingDeviceName = "共通";
// 				}else{
// 					condensingDeviceName = alarmDeviceNameList[i];
// 				}
// 				mainAlarmHtml += '<td style="width: 10%;">'+condensingDeviceName+'</td>';
// 				var alarmType ="";
// 				if(alarmInfoList[i].alarmType==0){
// 					alarmType ='<span style="color:#F2CC0C">预警</span>';					
// 				}else if(alarmInfoList[i].alarmType==1){
// 					alarmType ='<span style="color:#F2495C">故障</span>';
// 				}else if(alarmInfoList[i].alarmType==2){
// 					alarmType ='<span style="color:#F2495C;font-size:-1px">阈值报警</span>';
// 				}
// 				if(alarmInfoList[i].recoveryTime){
// 					alarmType = alarmType+'<span style="color:#47B347">(已恢复)</span>';
// 				}
// 				mainAlarmHtml += '<td style="width: 15%;">'+alarmType+'</td>';
// 				mainAlarmHtml += '<td style="width: 25%;">'+alarmInfoList[i].alarmContent+'</td>';
// 				mainAlarmHtml += '<td style="width: 20%;">'+getFormatDate(alarmInfoList[i].recoveryTime)+'</td>';
// 				mainAlarmHtml += '</tr>';
// 		}
// 		if(alarmInfoList.length==0){
// 			mainAlarmHtml += '<tr style="height:22px"><td>无报警</td></tr>';
// 		}

// //		if(mainAlarmCount>0){
// //			$("#mainAlarmDiv").css("background-color","#F2495C");
// //		}else{
// //			$("#mainAlarmDiv").css("background-color","#47B347");
// //
// //		}
// 		$("#mainAlarmTable").append(mainAlarmHtml);
// 		if(mainAlarmCount>0){
// 			$("#mainAlarmCount").css("color","#c00000")
// 		}else{
// 			$("#mainAlarmCount").css("color","#000")
// 		}
// 		$("#mainAlarmCount").text(mainAlarmCount);


// 	});
	
	
// 	$.post("remoteMonitor/enterpriseMainPanel/enterpriseInfo.do",params,function(data){
// 		var result = JSON.parse(JSON.parse(data));
// 		var netState = result.netState;
// 		$("#alarmEnterpriseName").text(result.enterpriseName)
// 		$("#netstate").text(netState=='0'?'离线':'在线');
// 		$("#modifyTime").text(getFormatDate(result.modifyTime));
// 	})
	
// 	//1-22号机的是否报警
// 	$.post("remoteMonitor/alarmInfo/enterpriseAlarmList.do",params,function(data){
// 		var enterpriseAlarmList = JSON.parse(JSON.parse(data));
// 		$("#childDeviceDiv").empty();
// 		var childAlarmHtml ="";
// 		var width = $("#main-tab-content").width();
// 		var alarmLen = enterpriseAlarmList.length;
// 		var num1 = parseInt(width/135) ;
// 		var num2 = parseInt(alarmLen/num1);
// 		var num3 = alarmLen%num1;
// 		if(num3>0){
// 			num2 = num2+1;
// 		}
// 		$("#condensingDeviceCount").text(alarmLen);
// 		for(i=0;i<alarmLen;i++){
// 			var condensingDeviceNum = enterpriseAlarmList[i].condensingDeviceNum;
// 			var condensingDeviceName = enterpriseAlarmList[i].condensingDeviceName;
// 			var flag = enterpriseAlarmList[i].flag;
// 			var deviceModel = enterpriseAlarmList[i].deviceModel;
// 			var runtime = enterpriseAlarmList[i].runtime;
// 			var runningState = "停机";
// 			var backgroundColor ="#C3C3C3"
// 			if(flag==0){
// 				runningState = "停机";
// 				backgroundColor ="#C3C3C3"
// 			}else if(flag==1){
// 				runningState = "运行";
// 				backgroundColor ="#47B347"
// 			}else if(flag==2){
// 				runningState = "预警";
// 				backgroundColor ="#F2CC0C"
// 			}else if(flag==3){
// 				runningState = "故障";
// 				backgroundColor ="#F2495C"
// 			}

// 			var runtimeStr = "总运行"+runtime + "小时";
			 
// 			childAlarmHtml += '<div style="height: 110px; width: 120px;margin: 10px 5px 10px 10px;background-color: '+backgroundColor+'; text-align: center;float: left;cursor: pointer;" onclick="toOperationTrend('+condensingDeviceNum+')">';
// 			childAlarmHtml +='<div style="height: 30px; line-height: 30px;font-weight: bold;font-size: 16px">'+condensingDeviceName+'</div>';
// 			childAlarmHtml +='<div style="height: 15px; line-height: 15px;font-size: 12px;transform: scale(0.8)">('+deviceModel+')</div>';
// 			childAlarmHtml +='<div style="height: 40px; line-height: 40px; font-size: 25px; font-weight: bold;padding-top:5px">'+runningState+'</div>';
// 			childAlarmHtml +='<div style="height: 20px; line-height: 20px; font-size: 12px;">'+runtimeStr+'</div>';
// 			childAlarmHtml +='</div>';
// 		}
// 		$("#childDeviceDiv").append(childAlarmHtml);
		
// 		if(num2==1){
// 			$("#mainAlarmDiv").css("top",185);
// 			$("#mainAlarmListDiv").css("top",185);
// 			$("#pressureChartDiv").css("top",460);
// 			$("#pressureListDiv").css("top",460);
// 			$("#temperatureChartDiv").css("top",865);
// 			$("#temperatureListDiv").css("top",865);
// 			$("#electricChartDiv").css("top",1270);
// 			$("#electricListDiv").css("top",1270);
// 			$("#otherValuesListDiv").css("top",1575);
// 		}else if(num2==2){
// 			$("#mainAlarmDiv").css("top",185+1*130);
// 			$("#mainAlarmListDiv").css("top",185+1*130);
// 			$("#pressureChartDiv").css("top",460+1*130);
// 			$("#pressureListDiv").css("top",460+1*130);
// 			$("#temperatureChartDiv").css("top",865+1*130);
// 			$("#temperatureListDiv").css("top",865+1*130);
// 			$("#electricChartDiv").css("top",1270+1*130);
// 			$("#electricListDiv").css("top",1270+1*130);
// 			$("#otherValuesListDiv").css("top",1575+1*130);
// 		}else if(num2==3){
// 			$("#mainAlarmDiv").css("top",185+2*130);
// 			$("#mainAlarmListDiv").css("top",185+2*130);
// 			$("#pressureChartDiv").css("top",460+2*130);
// 			$("#pressureListDiv").css("top",460+2*130);
// 			$("#temperatureChartDiv").css("top",865+2*130);
// 			$("#temperatureListDiv").css("top",865+2*130);
// 			$("#electricChartDiv").css("top",1270+2*130);
// 			$("#electricListDiv").css("top",1270+2*130);
// 			$("#otherValuesListDiv").css("top",1575+2*130);
// 		}
// 	});
// }

// function toOperationTrend(condensingDeviceNum){
// 	var deviceCode = $("#enterpriseMainPanel-content [name='deviceCode']").val();
// 	sessionStorage.setItem("deviceCode", deviceCode);
// 	sessionStorage.setItem("condensingDeviceNum", condensingDeviceNum);
// 	var clickDocument = $("#operation_trend");
//     clickDocument.click();
// }


// function chooseDate(flag){
//     var myDate=new Date();
// 	if(flag==1){
// 		var startTime = new Date().format("yyyy-MM-dd 00:00:00");
// 		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
// 	    $("#enterpriseMainPanel-content [name='query_startTime']").val(startTime);
// 		$("#enterpriseMainPanel-content [name='query_endTime']").val(endTime);
// 	}else if(flag==2){
// 		myDate.setMonth(myDate.getMonth()-1);
// 		var startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
// 		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
// 	    $("#enterpriseMainPanel-content [name='query_startTime']").val(startTime);
// 		$("#enterpriseMainPanel-content [name='query_endTime']").val(endTime);
// 	}else if(flag==3){
// 		myDate.setMonth(myDate.getMonth()-3);
// 		var startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
// 		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
// 	    $("#enterpriseMainPanel-content [name='query_startTime']").val(startTime);
// 		$("#enterpriseMainPanel-content [name='query_endTime']").val(endTime);
// 	}else if(flag==4){
// 		myDate.setDate(myDate.getDate()-7);
// 		var startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
// 		var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
// 	    $("#enterpriseMainPanel-content [name='query_startTime']").val(startTime);
// 		$("#enterpriseMainPanel-content [name='query_endTime']").val(endTime);
// 	}
// 	searchHistoryRecordList();
// }


// function exportHistoryRecord(flag){
// 	openLoading();
// 	var deviceCode = $("#enterpriseMainPanel-content [name='deviceCode']").val();
// 	var query_startTime = $("#enterpriseMainPanel-content [name='query_startTime']").val();
// 	var query_endTime = $("#enterpriseMainPanel-content [name='query_endTime']").val();
//     window.location.href="remoteMonitor/enterpriseMainPanel/exportHistoryRecordList.do?deviceCode=" + deviceCode + "&query_startTime=" + query_startTime + "&query_endTime=" + query_endTime+"&flag="+flag;
//     closeLoading();
// }


// function changeEnterpriseInfo(){
// 	var deviceCode = $("#enterpriseMainPanel-content [name='deviceCode']").val();
// 	//把企业对于的deviceCode存到缓存中，以便于从企业主看板，调整到其他二级目录的话，可以对应当前企业主看板所选的企业
// 	sessionStorage.setItem("deviceCode", deviceCode);
// }

