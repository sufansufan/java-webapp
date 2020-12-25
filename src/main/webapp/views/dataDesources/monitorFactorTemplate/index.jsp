<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/dataDesources/monitorFactorTemplate.js"></script>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css" />
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<style>
.layui-table-main{
overflow: auto;
}
.layui-table-body {
    position: relative;
    overflow-x: auto!important;
    margin-right: -1px;
    margin-bottom: -1px;
}
</style>
<div id="monitorFactorTemplate-content" class="contain-content" style="bottom: 0;">
	<div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline" style="margin-right: 15px">
 					<shiro:hasPermission name="monitor_factor_add">
 						<button class="layui-btn layui-btn-light-blue" onclick="monitorFactorTemplateAddPage()">
 							<i class="layui-icon layui-icon-add-1"></i>添加
 						</button>
 					</shiro:hasPermission>
					<shiro:hasPermission name="monitor_factor_template_del">
						<button class="layui-btn layui-btn-pink" onclick="monitorFactorTemplateDelPage()">
							<i class="layui-icon layui-icon-delete"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
				   <div class="layui-input-inline" style="width: 180px">
					<select name="enterpriseName" xm-select="select7_1" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="180px">
					    <option value="">请选择企业</option>
					     <c:forEach var="item" items="${enterpriseInfoList}">
                             <option value="${item.enterpriseName}">${item.enterpriseName}</option>
			           </c:forEach>
					</select>
				</div>
				<div class="layui-input-inline">
					<input type="text" name="deviceCode" placeholder="请输入终端编码" class="layui-input" style="height: 36px">
				</div>
				<div class="layui-input-inline" style="width: 140px">
					<select name="typeId" xm-select="select7_2" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="140px">
					    <option value="">请选择数据类型</option>
					    	<option value="1">数值</option>
					    	<option value="2">预警</option>
					        <option value="3">故障</option>
					        <option value="4">开关</option>
					</select>
				</div>
				<div class="layui-input-inline"  style="width: 200px">
					<select multiple="" id="factorTag" name="factorTag"  xm-select="select7_3" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="200px">
						<option value="">请选择标签名称</option>
						<c:forEach var="item" items="${factorTagList}" varStatus="vs">
							<option value="${item.tagName}" ${model.factorTag==item.tagName?'selected="selected"':''}>${item.tagName}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="layui-input-inline">
					<input type="text" name="factorName" placeholder="请输入监测因子名称" class="layui-input" style="height: 36px">
				</div>
				
				<div class="layui-input-inline">
					<input type="text" name="factorCode" placeholder="请输入监测因子编码" class="layui-input" style="height: 36px">
				</div>

				<div class="layui-input-inline">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="monitorFactorTemplateSearchList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
			</div>

		</div>
	</div>

	<table id="monitorFactorTemplate" lay-filter="lmonitorFactorTemplate"></table>

</div>
<script type="text/html" id="monitorFactorTemplateOptBar">
<div class="layui-btn-group optBar">
	<shiro:hasPermission name="monitor_factor_edit">
<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="monitor_factor_del">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style=""><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
	</shiro:hasPermission>
</div>
</script>


<script type="text/html" id="monitorFactorTemplateTimeTpl"> 
  {{ getFormatDate(d.createTime) }}
</script>

<script type="text/html" id="typeIdTpl">
  {{#  if(d.typeId == 1){ }}
   	 数值
  {{#  } else if(d.typeId == 2){ }}
   	 预警
  {{#  } else if(d.typeId == 3){ }}
   	 故障
  {{#  } else if(d.typeId == 4){ }}
   	 开关
  {{#  } }}
</script>

<script type="text/html" id="alarmStateTpl">
  {{#  if(d.alarmState == 0){ }}
   	 不报警
  {{#  } else if(d.alarmState == 1){ }}
   	 报警
  {{#  } }}
</script>

<script type="text/javascript">

$(function(){
	$('[placeholder]').placeholder();
})

	var _selObj;
	table.render({
		elem : '#monitorFactorTemplate',
		id : 'monitorFactorTemplate',
		cols : [ [ 
			{type : 'checkbox', width:'5%',fixed: 'left'}
			,{align : 'center',field : 'enterpriseName',title : '企业名称',	minWidth : 150, width:'12%',fixed: 'left'}
			,{align : 'center',field : 'deviceCode',title : '终端编码',	minWidth : 150, width:'10%',fixed: 'left'}
			,{align : 'center',field : 'machineName',title : '设备名',	minWidth : 150, width:'8%',fixed: 'left'}
			,{align : 'center',field : 'factorName',title : '名称',	minWidth : 210, width:'13%',fixed: 'left'}
			,{align : 'center',field : 'factorCode', title : '编码',minWidth : 210, width:'10%',fixed: 'left'}
			,{align : 'center',field : 'factorTag', title : '标签',minWidth : 210, width:'7.5%',fixed: 'left'}
			,{align : 'center',field : 'factorUnit', title : '单位',minWidth : 210, width:'7.5%',fixed: 'left'}
			,{align : 'center',field : 'dataAccuracy', title : '数据精度',minWidth : 210, width:'7.5%'}
			,{align : 'center',field : 'decimalDigits', title : '小数位',minWidth : 210, width:'6%'}
			,{align : 'center',field : 'typeId', title : '数据类型',minWidth : 210, width:'7.5%',templet:'#typeIdTpl'}
			,{align : 'center',field : 'alarmState', title : '是否报警',minWidth : 210, width:'7.5%',templet:'#alarmStateTpl'}
			,{align : 'center',field : 'lowerLimit', title : '下限',minWidth : 210, width:'5%'}
			,{align : 'center',field : 'upperLimit', title : '上限',minWidth : 210, width:'5%'}
			
			,{title : '操作',minWidth : 240, width:'11.5%',align : 'center',toolbar : '#monitorFactorTemplateOptBar',fixed: 'right'} 
		] ],
		url : 'dataDesources/monitorFactorTemplate/list.do',
		page : pageParam,
		limit : 100,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			emptyOptBar("monitorFactorTemplate");
			resizeTable('monitorFactorTemplate');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("monitorFactorTemplate");
		}
	});

	table.on('tool(lmonitorFactorTemplate)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			monitorFactorTemplateDelPage(obj);
		} else if (layEvent === 'edit') {
			monitorFactorTemplateEditPage(data.id);
		}
	});
</script>