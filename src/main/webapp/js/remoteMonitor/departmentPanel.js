(function() {
  $(function () {
    var doms = {
      searcher: {
        start: $('#department_panel #query_startTime'),
        end: $('#department_panel #query_endTime'),
        department: $('#department_panel #department'),
        search: $('#department_panel #devSearch'),
        today: $('#department_panel #devToday'),
        week: $('#department_panel #devWeek'),
        month: $('#department_panel #devMonth'),
        year: $('#department_panel #devYear'),
      },
      content: {
				depElec: $('#department_panel #dep_electric'),
				depName: $('#department_panel #dep_departmentName'),
				depCount: $('#department_panel #dep_devicesCount'),
				depTemp: $('#department_panel #dep_temperature'),
				depDamp: $('#department_panel #dep_damp'),
				depWarn: $('#department_panel #dep_warn'),
				depAlarm: $('#department_panel #dep_alarm'),
				depList: $('#department_panel #dep_list'),
				devicesBox: $('#department_panel #devicesBox'),
				depTip: $('#department_panel #dep_tip'),
				table: $('#department_panel #departmentAlarmInfo'),
				tableBody: $($('#department_panel #departmentAlarmInfo tbody')[0]),
			},
			chart: {
				main: $('#department_panel #mainChart'),
				chartsContainer: $('#department_panel #depChartsBox')
			}
    };
    var state = {
			currentDepartment: {},
			statusMapper: {
				'停机': 'stop',
				'离线': 'offline',
				'运行': 'run',
			},
			chartTypeMapper: {
				'chart_line': 'line',
				'chart_bar': 'bar',
				'chart_dot': 'dot',
			},
			charts: [],
			alarmList: [],
			query: {},
    }
  
    var dateFilters = [
      { ele: doms.searcher.today, param: 1 },
      { ele: doms.searcher.week, param: 4 },
      { ele: doms.searcher.month, param: 2 },
      { ele: doms.searcher.year, param: 3 },
    ];
  
    dateFilters.forEach(function (item) {
      item.ele.on('click', item, handleClickFilter);
    });
  
    var today = (new Date().format('yyyy-MM-dd hh:mm:ss'));
    doms.searcher.start.val(new Date().format('yyyy-MM-dd') + ' 00:00:00');
		doms.searcher.end.val(today);
          
    doms.searcher.start.on('focus', showDatePicker);
    doms.searcher.end.on('focus', showDatePicker);
    doms.searcher.search.on('click', handleSearch);
			
    var totalModules = 3;  // $(window).bind('resize', rerenderCharts);

		var resizeTimer = null; 

		var interval;
		autoRefresh();
		function autoRefresh () {
			if (interval) {
				clearInterval(interval);
				interval = null;
			}
			interval = setInterval(function () {
				var element = $('#department_panel #mainChart');
				if (!element || !element.length) {
					clearInterval(interval);
					interval = null;
					return;
				}
				console.log('车间看板-自动刷新.');
				handleSearch(true);
			}, 60000);
		}

		layui.use('form', function(){
			var form = layui.form;
			form.render();
		});
		
		form.on('switch(depSwitch)', function(data){
			if (this.checked) {
				autoRefresh();
			} else {
				clearInterval(interval);
				interval = null;
			}
		});
		form.on('checkbox(depViewAll)', function(data){
			if (this.checked) {
				var newAlarmList = [];
				for (var a = 0; a < state.alarmList.length; a++) {
					if (!state.alarmList[a].recoveryTime) newAlarmList.push(state.alarmList[a]);
				}
				console.log('仅看未恢复', newAlarmList);
				renderTable(newAlarmList);
			} else {
				renderTable(state.alarmList);
			}
		});
	
		doms.content.depList.on('click', function (e) {
			if (e.target.dataset.type === 'machine') {
				var machineId = e.target.dataset.id;
				sessionStorage.setItem("machineId", machineId);
				sessionStorage.setItem("departmentId", state.currentDepartment.id);
				console.log(sessionStorage);
				$('#switch_info').click();
			}
		});

		$(window).bind('resize', rerenderCharts);
		window.onbeforeunload=function(e){     
			$(window).unbind('resize', rerenderCharts);
			clearInterval(interval);
			interval = null;
		} 
		closeLoading();
    init();
  
    function init () {
			initSelect();
      // openLoading();
			// handleGetDevices(handleSearch);
			handleSearch();
		}
		
		function initSelect () {
			doms.searcher.department.SumoSelect({ 
				placeholder:"请选择车间",
				// search: true, 
				searchText: '请选择车间', 
				noMatch: '没有匹配 "{0}" 的项' ,
				csvDispCount: 1,
				captionFormat:'选中 {0} 项',
				okCancelInMulti:true,
				selectAll:false,
				locale : ['确定', '取消'] 
			});
			doms.searcher.department.val('1');
			doms.searcher.department[0].sumo.reload();
		}
  
    // 日历面板
    function showDatePicker () {
      WdatePicker({ isShowClear:false, readOnly:true, dateFmt:'yyyy-MM-dd HH:mm:ss' });
    }
          
    // 切换日期筛选条件
    function handleClickFilter (e) {
      var data = e.data;
      var ele = data.ele;
      var param = data.param;
      dateFilters.forEach(function (item) {
        if (ele === item.ele) {
          item.ele.addClass('layui-btn-light-blue');
          item.ele.removeClass('layui-btn-primary ');
        } else {
          item.ele.removeClass('layui-btn-light-blue');
          item.ele.addClass('layui-btn-primary ');
        }
      });
      chooseDate(param);
    }
    function chooseDate (flag) {
      var myDate = new Date();
      var startTimeDOM = doms.searcher.start;
      var endTimeDOM = doms.searcher.end;
      var startTime;
      var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
  
      if (flag == 1) {
          startTime = new Date().format("yyyy-MM-dd 00:00:00");
      } else if (flag == 2) {
          myDate.setMonth(myDate.getMonth() - 1);
          startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
      } else if (flag == 3) {
          var currentYear = new Date().getFullYear();
          startTime = currentYear + '-01-01 00:00:00';
      } else if (flag == 4) {
          myDate.setDate(myDate.getDate() - 7);
          startTime = myDate.format("yyyy-MM-dd hh:mm:ss");
      }
               
      startTimeDOM.val(startTime);
      endTimeDOM.val(endTime);
    }
  
    // 查询
    function handleSearch (notLoading) {
			autoRefresh();
      totalModules = 3;
      if (!notLoading) openLoading();
      var start = doms.searcher.start.val();
      var end = doms.searcher.end.val();
      var department = doms.searcher.department.val();
  
      var query = {
        locationItemValue: department,
        query_startTime: start,
        query_endTime: end,
			};
			state.query = query;
      handleGetBasicInfo(query);
			renderAlarmList(query);
			handleGetLayout(query);
    }
    // 车间基础信息
    function handleGetBasicInfo (query) {
			// addMainTab('remoteMonitor/enterpriseMainPanel/deviceMainPanel.do', 'switch_panel', '设备看板', 'base_icon_department');
      $.get("remoteMonitor/enterpriseMainPanel/workshopBasicInfo.do", query, function(data) {
        addModule();
        if (data && data.data) {
          var d = data.data;
					state.currentDepartment = d;
					state.currentDepartment.id = query.locationItemValue;
					doms.content.depElec.text((Number(d.powerConsumption) || 0).toLocaleString() + ' 千瓦时');
					doms.content.depName.text(d.title);
					doms.content.depTemp.text(d.temperature + '℃');
					doms.content.depDamp.text(d.humidity + '°');
					doms.content.depWarn.text(d.countEarlyAlarm);
					doms.content.depAlarm.text(d.countAlarm);
					doms.content.depCount.text(d.countMachine);
        }
			}, 'json');
			
			// 设备列表
      $.get("remoteMonitor/enterpriseMainPanel/workshopMachineList.do", query, function(data) {
        addModule();
        if (data && data.data) {
					var d = data.data;
  
					renderPie(
						doms.chart.main[0],
						'设备状态',
						[
							{ name: '运行', value: d.countRunning },
							{ name: '离线', value: d.countOffline },
							{ name: '停机', value: d.countStop },
						],
						['运行', '停机', '离线']
					);

					var str = '';
					if (!d.machineList.length) {
						doms.content.depTip.removeClass('hide');
					} else {
						doms.content.depTip.addClass('hide');
					}
					for (var i = 0; i < d.machineList.length; i++) {
						var device = d.machineList[i];
						str += 	'<div class="device">'
								+			'<span class="name" data-id=' + device.id + ' data-type="machine">' + device.name + '</span>'
								+			'<span class="status ' + state.statusMapper[device.runningStatusText] + '">' + device.runningStatusText + '</span>'
								+		'</div>';
					}
					doms.content.depList.html(str);
        }
			}, 'json');
			closeLoading();
    }
  
    // 报警信息
    function renderAlarmList (query) {
      $.get("remoteMonitor/enterpriseMainPanel/alarmList.do", query, function(data) {
        addModule();
				try {
					if (!data.success) throw 1;

					var alarmList = data.data.alarmList;
					if (!alarmList || !alarmList.length) throw 1;
					state.alarmList = alarmList;
					renderTable(alarmList);
				} catch (e) {
					renderTable([]);
				}
      }, 'json');
		}
		function renderTable (data) {
			if (!data || !data.length) {
				doms.content.tableBody.html('<tr><td colspan="6">暂无报警信息</td></tr>');
				return;
			}

			var tableStr = '';
			for (var i = 0; i < data.length; i++) {
				var item = data[i];
				if (item.alarmTypeStr === '报警') {
					item.typeClass = 'alarm';
				} else if (item.alarmTypeStr === '预警') {
					item.typeClass = 'warn';
				}

				item.extra = '';
				if (item.recoveryTime) {
					item.extra = '（已恢复）';
					item.typeClass = 'recovery';
				}

				tableStr += '<tr>'
									+		'<td>' + item.createTimeStr + '</td>'
									+ 	'<td>' + item.machineName + '</td>'
									+ 	'<td>' + (item.factorName || '') + '</td>'
									+		'<td><span class="' + item.typeClass + '">' + item.alarmTypeStr + '<span class="recovery">' + item.extra + '</span></span></td>'
									+		'<td>' + item.alarmContent + '</td>'
									+		'<td>' + item.recoveryTimeStr + '</td>'
									+ '</tr>'
			}
			doms.content.tableBody.html(tableStr);
		}
		
		// 图表
		function handleGetLayout (query) {
			$.get("remoteMonitor/enterpriseMainPanel/layout.do", query, function(data) {
        if (data && data.data) {
					var rows = data.data.rows;
					if (!rows || !rows.length) {
						return;
					}
					// 构建图表配置
					var chartList = [];
					for (var r = 0; r < rows.length; r++) {
						var cols = rows[r].cols;
						for (var c = 0; c < cols.length; c++) {
							chartList.push(cols[c]);
						}
					}
					handleGetCharts(chartList, query);
        }
    	}, 'json');
		}
		function handleGetCharts (charts, query) {
			var count = charts.length;
			// openLoading();

			for (var c = 0; c < charts.length; c++) {
				(function (index) {
					var q = {
						locationItemValue: query.locationItemValue,
						query_startTime: query.query_startTime,
						query_endTime: query.query_endTime,
						factorTag: charts[index].factor_tag,
					};

					$.get("remoteMonitor/enterpriseMainPanel/historyRecordChart.do", q, function(data) {
						count--;
	
						if (data && data.data) {
							charts[index].chartData = data.data;
							charts[index].query = q;
						}
						if (!count) {
							closeLoading();
							handleRenderCharts(charts);
						}
					}, 'json');
				}(c));
			}
		}
		function handleRenderCharts (charts) {
			var str = '';
			for (var i = 0; i < charts.length; i++) {
				(function (idx) {
					str += 	'<div class="card">'
							+			'<div class="chart" id=depChart-' + idx + '></div>'
							+		'</div>'
				})(i);
			}
			doms.chart.chartsContainer.html(str);

			state.charts = [];
			for (var i = 0; i < charts.length; i++) {
				var currentChart = charts[i];
				var chartObj;
				var title = currentChart.title;
				var unit = currentChart.unit;
				var units = currentChart.chartData.factorUnitArr || [];
				var values = currentChart.chartData.chart.value;
				var xAxis = currentChart.chartData.chart.timeChart;
				var legend = currentChart.chartData.factorNameArr;
				var ele = $('#department_panel #depChartsBox #depChart-' + i)[0];
				var query = currentChart.query;

				var config = {
					ele,
					title,
					valueData: values,
					legendData: legend,
					xAxis: xAxis,
					unit,
					units,
					originXAxis: xAxis,
				};
				switch (state.chartTypeMapper[currentChart.type]) {
					case 'line':
						config.xAxis = xAxis[0] || [];
						chartObj = renderLine(config, query);
						break;
					case 'bar':
						chartObj = renderBar(config, query);
						break;
					case 'dot':
						chartObj = renderDot(config, query);
						break;
				}

				state.charts.push(chartObj);
			}
		}
  
    function addModule () {
      totalModules --;
      // console.log(totalModules, 'total');
      if (!totalModules) closeLoading();
		}
		
		function renderPie (ele, title, valueData, legendData) {
			var option = {
				color: ['#AFDDCB', '#FB9747', '#55A7F3'],
				tooltip: {
					trigger: 'item',
					formatter: '{a} <br/>{b} : {c} ({d}%)'
				},
				legend: {
					orient: 'horizontal',
					bottom: 'bottom',
					data: legendData
				},
				series: [
					{
						labelLine: { show: false },
						label: { show: false, },
						name: title,
						type: 'pie',
						radius: '50%',
						center: ['50%', '50%'],
						data: valueData || [],
						emphasis: {
							itemStyle: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}
				]
			};
			return renderChart(ele, option);
		}

		function rerenderCharts () {
			if (resizeTimer) {
				clearTimeout(resizeTimer);
			}
			resizeTimer = setTimeout(function(){
				console.log("window resize....");
				if (!state.charts || !state.charts.length) return;
				// 重新渲染图表
				state.charts.forEach(chart => {
					if (!chart) return;
					chart.resize();
				});
			}, 200);
		}
  });
})();