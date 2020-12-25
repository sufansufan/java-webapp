<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/system/sysAuthority.js"></script>
<style>
<!--

-->
.layui-table-body {
    overflow: auto;
}
</style>
<div class="contain-content" style="bottom: 0;">
    <div class="layui-form right-search">
		<div class="layui-form-item">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<shiro:hasPermission name="sys_authority_add">
					    <button class="layui-btn layui-btn-light-blue" onclick="sysAuthorityAddPage()">
							<i class="layui-icon layui-icon-add-1"></i>添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys_authority_del">
					    <button class="layui-btn layui-btn-light-blue" onclick="sysAuthorityDelPage()">
							<i class="layui-icon layui-icon-add-1"></i>删除
						</button>
					</shiro:hasPermission>
				    <button class="layui-btn" id="btn-expand">全部展开</button>
				    <button class="layui-btn" id="btn-fold">全部折叠</button>
				</div>
			</div>
		</div>
	</div>
	
    <table id="sysAuthority" name="sysAuthority" class="layui-table" lay-filter="lSysAuthority" ></table>
</div>


<!-- 操作列 -->
<script type="text/html" id="oper-col">

<div class="layui-btn-group optBar">
	<shiro:hasPermission name="sys_authority_add">
		<button class="layui-btn layui-btn-sm" lay-event="add" title="添加">
    		<i class="fa fa-plus" aria-hidden="true"></i> 
  		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys_authority_edit">
		<button class="layui-btn layui-btn-sm" lay-event="edit" title="修改">
    		<i class="fa fa-pencil-square-o" aria-hidden="true"></i> 
  		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys_authority_del">
		<button class="layui-btn layui-btn-sm" lay-event="del" title="删除">
    		<i class="fa fa-trash-o" aria-hidden="true"></i> 
  		</button>
	</shiro:hasPermission>
</div>
</script>

<!--<script type="text/html" id="sysAuthorityImageTpl">
	{{#  if(d.identityImage != "" && d.authorityParentId==0){ }}
		<img src="${ctx}/css/images/left/${d.authorityImage}.png"/>
 	{{#  } else if(d.identityImage != "" && d.authorityParentId!=0){ }}
		<img src="${ctx}/css/images/left/second/${d.authorityImage}.png"/>
 	{{#  } else { }}
		
 	{{#  } }}
	
</script>-->

<script type="text/html" id="sysAuthorityTypeTpl">
	{{#  if(d.identityType != "" && d.authorityType==1){ }}
		菜单
 	{{#  } else if(d.identityType != "" && d.authorityType==2){ }}
		功能
 	{{#  } else { }}
		
 	{{#  } }}
	
</script>


<script type="text/javascript">

$(function(){
	
	loadTable();

	$('#btn-expand').click(function() {
		treetable.expandAll('#sysAuthority');
	});

	$('#btn-fold').click(function() {
		treetable.foldAll('#sysAuthority');
	});
	
	table.on('tool(lSysAuthority)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		_selObj = obj;
		if (layEvent === 'detail') {
			sysAuthorityViewPage(data.id);
		} else if (layEvent === 'add') {
			sysAuthorityAddPage(data.id);
		} else if (layEvent === 'del') {
			sysAuthorityDelPage(obj);
		} else if (layEvent === 'edit') {
			sysAuthorityEditPage(data.id);
		}
	});

})

function loadTable(){
	treetable.render({
        treeColIndex:1,
        treeSpid: 0,
        treeId: 'id',
        treePId: 'authorityParentId',
        treeDefaultClose: true,
        treeLinkage: false,
        elem: '#sysAuthority',
        id : 'sysAuthority',
        cols: [[
          //  {type: 'numbers',width: '5%'},  
            {type : 'checkbox', width:'5%'}
          , {align : 'left',field : 'authorityName',title : '名称',minWidth : 300,width: '25%'} 
		  , {align : 'left',field : 'authorityCode',title : '编码',minWidth : 210,width: '15%'}
		  , {align : 'left',field : 'authorityType',title : '类型',minWidth : 210,width: '10%',templet:'#sysAuthorityTypeTpl'}
		  , {align : 'left',field : 'authorityOrder',title : '顺序',minWidth : 210,width: '10%'}
		  , {align : 'left',field : 'authorityUrl',title : '路径',minWidth : 210,width: '15%'}
//		    , {align : 'center',field : 'authorityImage',title : '图标',minWidth : 210,width: '10%',templet:'#sysAuthorityImageTpl'}
          , {align : 'center', title : '操作',minWidth : 240,width: '20%',templet: '#oper-col'}
        ]],
        url : 'system/sysAuthority/list.do',
        page: false,
		height:'full-'+getTableFullHeight(),
		done : function() {
			//emptyOptBar("sysAuthority");
			resizeTable('sysAuthority');
			closeLoading();
		}
    }); 
	

}

</script>
</body>
</html>