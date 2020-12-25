$(function(){
	sysLogInitTable();
})

function sysLogInitTable(){
	table.render({
		elem: '#sysLog', 
		id:'sysLog',
		cols:  [[ 
		   {align: 'center', field: 'userName', title: '操作用户', minWidth: 150,width: '10%'} 
		  ,{align: 'center', field: 'logType', title: '操作类型', minWidth: 150,width: '15%', templet:'#sysLogTypeTpl'} 
		  ,{align: 'center', field: 'userIp', title: 'IP地址', minWidth: 250,width: '15%'} 
		  ,{align: 'center', field: 'logDetail', title: '操作详情', minWidth: 250,width: '30%'} 
		  ,{align: 'center', field: 'createTime', title: '操作时间', minWidth: 180,width: '20%', templet:'#sysLogtimeTpl'}
		  ,{fixed: 'right', title: '操作', minWidth:150,width: '10%', align:'center', toolbar: '#sysLogoptBar'}

		]],
		url : 'system/sysLog/list.do',
		where : sysLoggetParams(), // 查询参数
		method : 'post',
		page : pageParam,
		limit : 10,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			emptyOptBar('sysLog');
			resizeTable('sysLog');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("sysLog");
		}
	});
		
	// 监听工具条
	table.on('tool(SysLog)', function(obj){ 
		var data = obj.data; 
		var layEvent = obj.event;
		var tr = obj.tr; 
		_selObj = obj;
		if(layEvent === 'view'){
			sysLogviewDetailPage(data.id);
		}
	});
}

function sysLogviewDetailPage(id){
	openLoading();
	
	$.get("system/sysLog/viewDetail.do",{id:id},function(data){
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

function sysLogsearchList(){
	openLoading();
	table.reload('sysLog',{
		where : sysLoggetParams(),
		page : pageParam
	});
}

function sysLoggetParams(){
	
	var params = {
			beginTimeStr: $('#sysLogbeginTime').val(),
			endTimeStr: $('#sysLogendTime').val(),
	};
	
	return params;
}

function sysLogexcelExport(obj){
	$(obj).attr("disabled","disabled");
	//$(obj).addClass("layui-btn-disabled");
	$(obj).html('<i class="layui-icon">&#xe601;</i> &nbsp;....&nbsp;');
	
	var params =sysLoggetParams();
	
	$.post("system/sysLog/excelExport.do", params, function(data){
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