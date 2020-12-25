(function() {
  $(function () {
		var doms = {
			searcher: {
				start: $('#switch_panel #query_startTime'),
				end: $('#switch_panel #query_endTime'),
				department: $('#switch_panel #department'),
				device: $('#switch_panel #deviceCode'),
				search: $('#switch_panel #devSearch'),
				today: $('#switch_panel #devToday'),
				week: $('#switch_panel #devWeek'),
				month: $('#switch_panel #devMonth'),
				year: $('#switch_panel #devYear'),
			},
			content: {
				main: $('#switch_panel #switch_main_panel'),
  			devDepartmentName: $('#switch_panel #dev_departmentName'),
  			devDeviceName: $('#switch_panel #dev_deviceName'),
  			devDeviceCode: $('#switch_panel #dev_deviceCode'),
  			devDeviceType: $('#switch_panel #dev_deviceType'),
				devDeviceModel: $('#switch_panel #dev_deviceModel'),
				deviceEarlyAlarm: $('#switch_panel #deviceEarlyAlarm'),
				deviceAlarm: $('#switch_panel #deviceAlarm'),
  			devDeviceRuntime: $('#switch_panel #deviceRuntime'),
				devTip: $('#switch_panel #dev_tip'),
				lights: $('#switch_panel #lights'),
				image: $('#switch_panel #dev_infoImg'),
				table: $('#switch_panel #deviceAlarmInfo'),
				tableBody: $($('#switch_panel #deviceAlarmInfo tbody')[0]),
			},
			chart: {
				chartsContainer: $('#switch_panel #devChartsBox'),
			}
		};
		var state = {
			currentDepartment: {},
			currentDevice: {},
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
		var interval;
		autoRefresh();
		function autoRefresh () {
			if (interval) {
				clearInterval(interval);
				interval = null;
			}
			interval = setInterval(function () {
				var element = $('#switch_panel #switch_main_panel');
				if (!element || !element.length) {
					clearInterval(interval);
					interval = null;
					return;
				}
				console.log('设备看板-自动刷新.');
				handleSearch(true);
			}, 60000);
		}

		dateFilters.forEach(function (item) {
			item.ele.on('click', item, handleClickFilter);
		});

    var today = (new Date().format('yyyy-MM-dd hh:mm:ss'));
    doms.searcher.start.val(new Date().format('yyyy-MM-dd') + ' 00:00:00');
		doms.searcher.end.val(today);

		doms.searcher.start.on('focus', showDatePicker);
		doms.searcher.end.on('focus', showDatePicker);
		doms.searcher.search.on('click', handleSearch);
		doms.searcher.department.on('change', handleGetDevices);

		$(window).bind('resize', rerenderCharts);
		window.onbeforeunload=function(e){
			$(window).unbind('resize', rerenderCharts);
			clearInterval(interval);
			interval = null;
		}

		layui.use('form', function(){
			var form = layui.form;
			form.render();
		});

		form.on('switch(devSwitch)', function(data){
			if (this.checked) {
				autoRefresh();
			} else {
				clearInterval(interval);
				interval = null;
			}
		});
		form.on('checkbox(devViewAll)', function(data){
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

		var totalModules = 3;

		initSelect();
		init();

		function init () {
			openLoading();
			handleGetDevices(handleSearch);
		}

		function initSelect () {
			doms.searcher.department.SumoSelect({
				placeholder:"请选择车间",
				searchText: '请选择车间',
				noMatch: '没有匹配 "{0}" 的项' ,
				csvDispCount: 2,
				captionFormat:'选中 {0} 项',
				okCancelInMulti:true,
				selectAll:false,
				locale : ['确定', '取消']
			});
			doms.searcher.device.SumoSelect({
				placeholder:"请选择设备",
				searchText: '请选择设备',
				noMatch: '没有匹配 "{0}" 的项' ,
				csvDispCount: 2,
				captionFormat:'选中 {0} 项',
				okCancelInMulti:true,
				selectAll:false,
				locale : ['确定', '取消']
			});

			var sd = sessionStorage.getItem("departmentId");
			var departmentId = sd || '1';

			doms.searcher.department.val(departmentId);
			doms.searcher.department[0].sumo.reload();

			sessionStorage.removeItem("departmentId");
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

		// 获取设备列表
		function handleGetDevices (callback) {
			var depSelector = doms.searcher.department;
			var value = depSelector.val();

			$.get("remoteMonitor/enterpriseMainPanel/workshopMachineList.do", {
        locationItemValue: value,
    	}, function(data) {
        if (data && data.data) {
					var machines = data.data.machineList || [];
          var htmlStr = machines.reduce((str, device) => {
            str += '<option value="' + device.id + '">' + device.name + '</option>';
            return str;
          }, '');
					doms.searcher.device.html(htmlStr);
					if (machines.length) {
						var sm = sessionStorage.getItem("machineId");
						doms.searcher.device.val(sm || machines[0].id);
						sessionStorage.removeItem("machineId");
					} else {
						closeLoading();
					}
					doms.searcher.device[0].sumo.reload();

					if (callback && typeof callback === 'function') callback();
        }
    	}, 'json');
		}

		// 查询
		function handleSearch (notLoading) {
			autoRefresh();
			totalModules = 3;
			var start = doms.searcher.start.val();
			var end = doms.searcher.end.val();
			var machine = doms.searcher.device.val();
			var locationItemValue = doms.searcher.department.val();
			if (!machine) {
				doms.content.main.addClass('hide');
				return
			}
			doms.content.main.removeClass('hide');
			if (!notLoading) openLoading();

			var query = {
				machineId: machine,
				query_startTime: start,
				query_endTime: end,
				locationItemValue,
			};
			state.query = query;
			try {
				handleGetDeviceBasicInfo(query);
				handleGetDeviceSwitchList(query);
				renderAlarmList(query);
				handleGetLayout(query);
			} catch {
				doms.content.main.addClass('hide');
			}
		}
		// 设备基础信息
		function handleGetDeviceBasicInfo (query) {
			$.get("remoteMonitor/enterpriseMainPanel/machineBasicInfo.do", query, function(data) {
				addModule();
        if (data && data.data) {
					var d = data.data;
					state.currentDevice = d;

					doms.content.devDepartmentName.text(d.title);
					doms.content.devDeviceName.text(d.name);
					doms.content.devDeviceCode.text(d.machineNo);
					doms.content.devDeviceType.text(d.machineTypeName);
					doms.content.devDeviceModel.text(d.machineModel);
					doms.content.deviceEarlyAlarm.text(d.countEarlyAlarm);
					doms.content.deviceAlarm.text(d.countAlarm);
					doms.content.devDeviceRuntime.text(d.runtime);

					if (d.photoPath) {
						var path = 'device/deviceInfo/listfileLook.do?url=C:/EnvironmentalProtection/condensingDevice/' + d.photoPath;
						doms.content.image.html('<img src="' + path + '" style="max-height: 120px; max-width: 100%" />');
					}
        } else {
					doms.content.main.addClass('hide');
				}
			}, 'json');
		}
		// 设备开关信息
		function handleGetDeviceSwitchList (query) {
			$.get("remoteMonitor/enterpriseMainPanel/switchInfo.do", query, function(data) {
				addModule();
				try {
					if (data && data.data) {
						var switchList = data.data.switchList;
						if (!switchList.length) throw 1;

						doms.content.devTip.addClass('hide');
						var htmlStr = switchList.reduce(function (str, item) {
							var icon = 'css/images/common/light' + (item.status ? 'on' : 'off') + '.png';
							str += 	'<span class="switch-light">'
									+ 		'<img src="' + icon + '" />'
									+ 		'<span>' + item.name + '</span>'
									+ 	'</span>';
							return str;
						}, '');
						doms.content.lights.html(htmlStr);
					} else throw 1;
				} catch (e) {
					doms.content.devTip.removeClass('hide');
				}
    	}, 'json');
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
			$.get("remoteMonitor/enterpriseMainPanel/layout.do", {
				machineId: query.machine,
				query_startTime: query.start,
				query_endTime: query.end,
			}, function(data) {
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
						machineId: query.machineId,
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
				var values = currentChart.chartData.chart.value;
				var xAxis = currentChart.chartData.chart.timeChart;
				var legend = currentChart.chartData.factorNameArr;
				var units = currentChart.chartData.factorUnitArr || [];
				var ele = $('#switch_panel #devChartsBox #depChart-' + i)[0];
				var query = currentChart.query;
				console.log('current chart: ', currentChart);

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

		var resizeTimer = null;
		function rerenderCharts () {
			if (resizeTimer) {
				clearTimeout(resizeTimer);
			}
			resizeTimer = setTimeout(function(){
				if (!state.charts || !state.charts.length) return;
				// 重新渲染图表
				state.charts.forEach(chart => {
					if (!chart) return;
					chart.resize();
				});
			}, 200);
		}

		function addModule () {
			totalModules --;
			// console.log(totalModules, 'total');
			if (!totalModules) closeLoading();
		}
	});
})();
