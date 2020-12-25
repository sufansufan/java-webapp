<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/dataDesources/enterpriseInfo.js"></script>
	<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css" />
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<style>
#enterpriseInfo-content a:hover{
color: #039;
text-decoration: underline
}
.layui-table-main{
overflow: auto;
}
</style>

<div id="enterpriseInfo-content" class="contain-content" style="bottom: 0;">

	<div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
				<shiro:hasPermission name="enterprise_info_add">
					<button class="layui-btn layui-btn-light-blue" onclick="enterpriseInfoAddPage()">
						<i class="layui-icon layui-icon-add-1"></i>添加
					</button>
				</shiro:hasPermission>	
				<shiro:hasPermission name="enterprise_info_del">
					<button class="layui-btn layui-btn-pink" onclick="enterpriseInfoDelPage()">
						<i class="layui-icon layui-icon-delete"></i>删除
					</button>
				</shiro:hasPermission>
				</div>
			</div>

			<div class="layui-inline">
				<div class="layui-input-inline"  style="width: 180px">
					<select id="deviceCode" name="deviceCode" onchange="changeCondensingDeviceList()"  xm-select="select7_1" xm-select-radio="" xm-select-search="" xm-select-show-count="3" xm-select-height="30px" xm-select-width="180px">
                       <option value="">请选择企业名称</option>
<%--                       <c:forEach var="item" items="${enterpriseInfoList}">--%>
<%--                             <option value="${item.deviceCode}">${item.enterpriseName}</option>--%>
<%--			           </c:forEach>--%>
		            </select>
				</div>
				<div class="layui-input-inline" style="width: 100px;">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue" onclick="enterpriseInfoSearchList()">
						<i class="layui-icon layui-icon-search" style="font-size: 18px; color: #FFFFFF;"></i> 查询
					</button>
				</div>
			</div>
		</div>
	</div>

	<table id="enterpriseInfo" lay-filter="lenterpriseInfo"></table>

</div>
<script type="text/html" id="enterpriseInfoOptBar">
<div class="layui-btn-group optBar">
	<shiro:hasPermission name="enterprise_info_edit">
<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="edit" style=""><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="enterprise_info_del">
		<button class="layui-btn layui-btn-sm layui-btn-light-blue" lay-event="del" style=""><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
	</shiro:hasPermission>
</div>
</script>

<script type="text/html" id="enterpriseInfoTimeTpl"> 
  {{ getFormatDate(d.modifyTime) }}
</script>

<script type="text/html" id="wechatPushStateTpl"> 
  {{#  if(d.wechatPushState == '1'){ }}
   	推送
  {{#  } else { }}
   	不推送
  {{#  } }}
</script>
<script type="text/html" id="mailPushStateTpl"> 
  {{#  if(d.mailPushState == '1'){ }}
   	推送
  {{#  } else { }}
   	不推送
  {{#  } }}
</script>

<script type="text/javascript">
closeLoading();
$(function(){
	$('[placeholder]').placeholder();
})

	var _selObj;
	table.render({
		elem : '#enterpriseInfo',
		id : 'enterpriseInfo',
		cols : [ [ 
			{ type : 'checkbox',width: '5%'	}
			, {	align : 'center',field : 'enterpriseName',title : '企业名称',minWidth : 150,width: '10%'}
// 			, {	align : 'center',field : 'orgName',title : '组织名称',minWidth : 150,width: '10%'}
			, {	align : 'center',field : 'deviceCode',title : '绑定终端',minWidth : 210,width: '8%'}
			, {	align : 'center',field : 'agentId',title : 'AgentID',minWidth : 210,width: '8%'}
			, {	align : 'center',field : 'wechatPushState',title : '微信推送',minWidth : 150,width: '7.5%',templet:'#wechatPushStateTpl'}
			, {	align : 'center',field : 'wechatAddress',title : '微信ID',minWidth : 210,width: '11.5%'}
			, {	align : 'center',field : 'mailPushState',title : '邮箱推送',minWidth : 150,width: '7.5%',templet:'#mailPushStateTpl'}
			, {	align : 'center',field : 'mailAddress',title : '邮箱地址',minWidth : 210,width: '11.5%'}
			, {	align : 'center',field : 'address',title : '企业地址',minWidth : 210,width: '10%'}
			, {	align : 'center',field : 'modifyTime',title : '更新时间',minWidth : 210,width: '10%',templet:'#enterpriseInfoTimeTpl'}
			, { align : 'center', title : '操作',minWidth : 240,width: '11%',toolbar : '#enterpriseInfoOptBar'}
			] ],
		url : 'dataDesources/enterpriseInfo/enterpriseInfoList.do',
		page : pageParam,
		limit : 10,
		height:'full-'+getTableFullHeight(),
		done : function(res, curr, count) {
			//无操作提示项
			emptyOptBar("enterpriseInfo");
			resizeTable('enterpriseInfo');
			closeLoading();
			var data = res.data;
			if(data.length==0) emptyList("enterpriseInfo");
		}
	});

	//监听工具条
	table.on('tool(lenterpriseInfo)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			layer.msg('查看');
		} else if (layEvent === 'del') {
			var checkStatus = table.checkStatus('enterpriseInfo');
			enterpriseInfoDelPage(obj);
		} else if (layEvent === 'edit') {
			enterpriseInfoEditPage(data.id);
		}
	});
	


	
	
</script>