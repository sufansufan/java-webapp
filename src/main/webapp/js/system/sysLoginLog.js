$(function(){
	sysLoginLogInitTable();
})

function sysLoginLogInitTable(){
	table.render({
		elem: '#sysLoginLog', 
		id:'sysLoginLog',
		cols:  [[ 
		   {align: 'center', field: 'userName', title: '操作用户', minWidth: 150,width: '10%'} 
		  ,{align: 'center', field: 'logType', title: '操作类型', minWidth: 150,width: '15%', templet:'#sysLoginLogTypeTpl'} 
		  ,{align: 'center', field: 'userIp', title: 'IP地址', minWidth: 250,width: '15%'} 
		  ,{align: 'center', field: 'logDetail', title: '操作详情', minWidth: 250,width: '30%'} 
		  ,{align: 'center', field: 'createTime', title: '操作时间', minWidth: 180,width: '20%', templet:'#sysLoginLogtimeTpl'}
		  ,{fixed: 'right', title: '操作', minWidth:150,width: '10%', align:'center', toolbar: '#sysLoginLogoptBar'}

		]],
		url : 'system/sysLoginLog/list.do',
		where : sysLoginLoggetParams(), // 查询参数
		method : 'post',
		page : pageParam,
		limit : 10,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			emptyOptBar('sysLoginLog');
			resizeTable('sysLoginLog');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("sysLoginLog");
		}
	});
		
	// 监听工具条
	table.on('tool(SysLog)', function(obj){ 
		var data = obj.data; 
		var layEvent = obj.event;
		var tr = obj.tr; 
		_selObj = obj;
		if(layEvent === 'view'){
			sysLoginLogviewDetailPage(data.id);
		}
	});
}

function sysLoginLogviewDetailPage(id){
	openLoading();
	
	$.get("system/sysLoginLog/viewDetail.do",{id:id},function(data){
		closeLoading();
		topIdx = layer.open({
			  type: 1,
			  area: '720px',
			  title: "日志明细",
			  closeBtn: 1,
			  content: data,
			  maxmin:true,
			  resize:true
			});
	});
	
}

function sysLoginLogsearchList(){
	openLoading();
	table.reload('sysLoginLog',{
		where : sysLoginLoggetParams(),
		page : pageParam
	});
}

function sysLoginLoggetParams(){
	
	var params = {
			beginTimeStr: $('#sysLoginLogbeginTime').val(),
			endTimeStr: $('#sysLoginLogendTime').val(),
	};
	
	return params;
}

function sysLoginLogexcelExport(obj){
	$(obj).attr("disabled","disabled");
	//$(obj).addClass("layui-btn-disabled");
	$(obj).html('<i class="layui-icon">&#xe601;</i> &nbsp;....&nbsp;');
	
	var params =sysLoginLoggetParams();
	
	$.post("system/sysLoginLog/excelExport.do", params, function(data){
		if(data.success){
			window.location.href="download.do?fileName=" +  encodeURI(encodeURI(data.data));
		}else{
			openErrAlert("数据导出失败!");
		}
		$(obj).removeAttr("disabled");
		//$(obj).removeClass("layui-btn-disabled");
		$(obj).html('<i class="layui-icon">&#xe601;</i>导出')
	},"json")
}