<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="layui-form import-content">
	<input type="hidden" id="importOrgId" value="${orgId}">
	<input type="hidden" id="importOrgIdPath" value="${orgIdPath}">
	<div class="layui-upload-drag" id="upload-drag">
		<i class="layui-icon">&#xe67c;</i>
		<p></p>
	</div>

	<div class="drag-loading">
		<div class="drag-loading-div">
			<div class="loading-div"></div>
			<div class="content-div"><p>正在导入,请稍候...</p></div>
		</div>
	</div>

	<div class="layui-form-item" style="padding: 20px; padding-bottom: 0;">
		<div class="layui-inline">
			<label class="layui-form-label" style="width: auto;">模板示例：</label>
			<div class="layui-input-inline">
				<button type="button" class="layui-btn layui-btn-light-blue" onclick="download('${down_url}')">
					<i class="layui-icon">&#xe61e;</i>点击下载
				</button>
			</div>
		</div>
	</div>

	<div class="layui-form-item" style="padding: 20px; ">
		<div id="upload-status" class="layui-inline" style="width: 300px;padding-left: 15px;">
			
			<i id="bulletTypeImgLoad" class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop" style="display:none;float: left;font-size: 20px;"></i>
			<i id="bulletTypeImgYes" class="layui-icon layui-icon-ok-circle" style="display:none;float: left;color:#5FB878;font-size: 20px;" title="上传图片成功"></i>
			<i id="bulletTypeImgNo" class="layui-icon layui-icon-close-fill" style="display:none;float: left;color:#F96768;font-size: 20px;" title="上传图片失败"></i>
			
			<span></span>
				
		</div>
		
		<div class="layui-inline" style="text-align: right;width: 100px;">
			<a class="layui-btn layui-btn-primary" onclick="closeLayer()">关闭</a>
		</div>
	</div>
</div>

<script type="text/javascript">

$(function(){
	
	if(isIE()<10 && isIE()>=6){
		$("#upload-drag p").text("点击上传");
	}else{
		$("#upload-drag p").text("点击上传 ，或将文件拖拽到此处");
	}
	
})
	var upload = layui.upload;
	
	upload.render({
		elem : '#upload-drag',
		url : 'upload.do?type=${upload_url}&size=20480',
		accept : 'file',
		exts: 'xls|xlsx',
		xhr:xhrOnProgress,
		progress:function(value){},
		size : 20480, //KB
		data :{
			//type:'${upload_url}'
		},
		before : function(obj) {
			$("#bulletTypeImgLoad").show();
			$("#bulletTypeImgYes").hide();
			$("#bulletTypeImgNo").hide();
			setInfoText("正在上传...");
		},
		done : function(data) {
			$("#bulletTypeImgLoad").hide();

			if (data.success) {
				setInfoText("上传成功,开始导入...");
				$("#bulletTypeImgYes").show();
				$("#bulletTypeImgNo").hide();
				
				$(".layui-upload-drag").hide();
				$(".drag-loading").show();
				
				$.post("system/sysUser/analyImport.do",{
					orgId:$("#importOrgId").val(),
					orgIdPath:$("#importOrgIdPath").val(),
					path:data.data.fullFileName
				},function(data){
					$(".layui-upload-drag").show();
					$(".drag-loading").hide();
					
					if(data.success){
						setInfoText("导入成功");
						layer.alert('导入成功', {icon: 1});
						sysUserSearchList();
					}else{
						
						$("#bulletTypeImgYes").hide();
						$("#bulletTypeImgNo").show();
						setInfoText(data.msg);
					}
					
					
				},'json')
				
			} else {
				$("#bulletTypeImgYes").hide();
				$("#bulletTypeImgNo").show();
				openErrAlert(data.msg);
			}
		},
		error : function(index, upload) {
			$("#bulletTypeImgLoad").hide();
			$("#bulletTypeImgYes").hide();
			$("#bulletTypeImgNo").show();
			setInfoText("上传失败,请稍候再试!");
		}
	});
	
	$(".layui-upload-file").attr("unselectable","on");
	
	function download(p){
		var f = encodeURI(encodeURI("用户导入模板.xls"));
		window.location.href="download.do?realName=" + f + "&fileName=" +  encodeURI(encodeURI(p));

	}
	
	function setInfoText(str){
		$("#upload-status span").text(str);
	}
</script>
