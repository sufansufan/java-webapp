<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<style>
.device {
    position: absolute;
    left: 0px;
    top: 55px;
    right: 0px;
    bottom: 30px;
    overflow: hidden;
    overflow-y: auto;
}
table.list-table {
    float: left;
    border-collapse: collapse;
}
table {
    width: 100%;
}
table.list-table thead {
    background-color: #e1eaf0;
    border-top: 1px solid #d0d9de;
    border-left: 1px solid #d0d9de;
}
table.list-table {
    float: left;
    border-collapse: collapse;
}
table.list-table thead tr th, table.list-table tbody tr td {
    font-size: 12px;
    height: 35px;
    border-bottom: 1px solid #d0d9de;
    border-right: 1px solid #d0d9de;
}
table.list-table tbody {
    border-top: 1px solid #d0d9de;
    border-left: 1px solid #d0d9de;
}
table.list-table tr th, table.list-table tr td {
    margin: 0;
    text-align: center;
}
.list-page {
    width: 100%;
    float: left;
    padding-top: 15px;
    padding-bottom: 20px;
    text-align: center;
    color: #adadad;
}
</style>
<div class="device" id="historyRecordList"  style="right:0;top: 507px;height: 520px;padding: 10px;">
<table cellspacing='0' cellpadding='3' class='list-table'>
				<thead>
					<tr>
						<th>采集时间</th>
						<c:forEach var="item" items="${factorNameArr}" varStatus="vs">
						<th>
						${item}
						</th>
						</c:forEach>	                    
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty returnValueList}">
						<tr>
							<td colspan="50">没有相关数据</td>
						</tr>
					</c:if>
					<c:if test="${!empty returnValueList}">
						<c:forEach var="item" items="${returnValueList}" varStatus="vs">
							<tr>
							<c:forEach var="item2" items="${item}" varStatus="vs2">
							<td>${item2==null?"--":item2}</td>
							</c:forEach>							
							</tr>	
						</c:forEach>
						
					</c:if>
				</tbody>
			</table>
			<div class="list-page">
				<div id="pagination"></div>
			</div>
		</div>
	<script type="text/javascript">
$(function(){
	var totalPage = ${pagingBean.pageNum};
	var totalRecords = ${pagingBean.totalItems};
	var pageNo = ${pagingBean.pageNo};
	if(!pageNo){
		pageNo = 1;
	}
	//生成分页
	pagination.generPageHtml({
		//填充的id
		pagerid : "pagination",
		//当前页
		pno : pageNo,
		//总页码
		total : totalPage,
		//总数据条数
		totalRecords : totalRecords,
		mode : 'click',
		click : function(n){
			//分页执行方法
			changeDetailedStatePage(n);
			return false;
		}
	});
});
</script>
