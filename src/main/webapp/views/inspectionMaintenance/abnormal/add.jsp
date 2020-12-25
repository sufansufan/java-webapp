<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<style>
    .layui-input-inline {
        width: 300px !important;
    }

    .layui-form-label {
        width: 100px;
    }
</style>
<div id="sysUserForm" class="layui-form" action="" style="padding:20px">
    <input type="hidden" id="exceptionIype" name="id" value="${model.id}">
    <div style="width:660px;float:left">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label"><font class="required-dot">*</font>异常来源</label>
                <div class="layui-input-inline">
                    <input type="text" id="exceptionTypeName" name="exceptionSourceName" value="突发异常" disabled
                           class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label"><font class="required-dot">*</font>选择设备</label>
                <div class="layui-input-inline">
                    <input type="text" name="machineName" lay-verify="required" value="${model.machineName }"
                           id="machineName"
                           autocomplete="off" placeholder="请选择设备" class="layui-input" readonly style="cursor: default;">
                    <input type="text" name="machineId" autocomplete="off" placeholder="请选择设备"
                           value="${model.machineId }" class="layui-input layui-hide" id="machineId" readonly>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label"><font class="required-dot">*</font>发生时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="createdTime" class="Wdate"
                           value="<fmt:formatDate value="${model.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
                           type="text"
                           onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                           style="cursor: default;height: 33px;width: 100%;" class="layui-input search-maxlenght"
                           placeholder="请选择时间">
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">异常情况描述</label>
        <div class="layui-input-inline" style="width: 500px;">
            <textarea placeholder="" name="desc" class="layui-textarea" placeholder="请输入情况描述"
                      style="width: 100%;">${model.describe}</textarea>
        </div>
    </div>
    <div class="layui-form-item layui-form-text" id="img_dev">
        <label class="layui-form-label">异常图片</label>
        <div class="layui-input-block">
            <input type="hidden" id="photoPath" name="exceptionImgUrls" lay-verify=""
                   value="${model.exceptionImgUrls}" autocomplete="off" class="layui-input">
            <button type="button" class="layui-btn layui-btn-light-blue" id="addImgBtn" style="width: 190px;">
                <i class="layui-icon">&#xe67c;</i>上传图片
            </button>
            <i id="bulletTypeImgLoad" class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"
               style="display: none; font-size: 20px;"></i>
            <i id="bulletTypeImgYes" class="layui-icon layui-icon-ok-circle"
               style="display: none; color: #5FB878; font-size: 20px;" title="上传图片成功"></i>
            <i id="bulletTypeImgNo" class="layui-icon layui-icon-close-fill"
               style="display: none; color: #F96768; font-size: 20px;" title="上传图片失败"></i>
            <input type="hidden" id="oldPhoto" value="${model.exceptionImgUrls}" class="layui-input">

        </div>
        <div style="width: 200px; float: left; text-align: center;">
            <img id="imgPreview" alt="图片预览" title="图片预览"
                 src="device/deviceInfo/listfileLook.do?url=${upload_url}/${model.photoPath}"
                 height="164" onerror="loadDefaultImg(this)" style="max-width: 200px;">
        </div>
    </div>
    <div class="layui-form-item" style="text-align: right;padding-top: 15px;">
        <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="saveData">提交</button>
        <a class="layui-btn layui-btn-primary" onclick="closeThis()">取消</a>
    </div>
</div>
<script type="text/javascript">
	function closeThis() {
		layer.closeAll();
	}
    (function ($) {
        $('[placeholder]').placeholder();
        //监听提交
        form.on('submit(saveData)', function (data) {
            $.post("inspectionMaintenance/exception/addException.do", data.field, function (data) {
                if (data.success) {
                    debugger
                    closeLoading();
                    requestSuccess(data, closeThis);
                    abnormalSearchList();
                }
            }, 'json');
            return false;
        });
        upload.render({
            elem: '#addImgBtn',
            url: 'upload.do?type=${upload_url}&size=2048',
            accept: 'images',
            size: 2048, //限制文件大小，单位 KB
            xhr: xhrOnProgress,
            progress: function (value) {
            },
            choose: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                obj.preview(function (index, file, result) {
                    $('#imgPreview').attr('src', result); //图片链接（base64）
                });
            },
            before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                $("#bulletTypeImgLoad").show();
                $("#bulletTypeImgYes").hide();
                $("#bulletTypeImgNo").hide();
            },
            done: function (data) {
                $("#bulletTypeImgLoad").hide();
                if (data.success) {
                    $("#photoPath").val(data.data.fileName);
                    $("#bulletTypeImgYes").show();
                    $("#bulletTypeImgNo").hide();
                } else {
                    $("#bulletTypeImgYes").hide();
                    $("#bulletTypeImgNo").show();
                    openErrAlert(data.msg);
                }
            },
            error: function (index, upload) {
                $("#bulletTypeImgLoad").hide();
                $("#bulletTypeImgYes").hide();
                $("#bulletTypeImgNo").show();
            }
        });
        $("#machineName").click(function () {
            openLoading();
            $.get("inspectionMaintenance/exception/addDevice.do", function (data) {
                closeLoading();
                topIdx = layer.open({
                    type: 1,
                    title: "选择设备",
                    area: ['800px', '560px'],
                    closeBtn: 1,
                    content: data,
                    maxmin: true,
                    resize: true
                });
            });
        });

    })(jQuery);
</script>