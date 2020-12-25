<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script type="text/javascript" charset="utf-8"
        src="${ctx}/js/dataDesources/machineManager.js"></script>
<link rel="stylesheet" href="${ctx}/css/layui-formSelects/formSelects-v4.css"/>
<script src="${ctx}/js/plugins/layui-formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/dataDesources/editMachine.js" type="text/javascript" charset="utf-8"></script>
<%--<link rel="stylesheet" href="${ctx}/js/plugins/layui/css/layui.css"  media="all">--%>
<style>
    .layui-tab-brief > .layui-tab-title {
        font-size: large;
    }
    .layui-form-label {
        width: 110px;
    }

    .layui-input-block {
        margin-left: 140px;
    }

    .layui-form-item {
        margin-bottom: 10px;
        clear: both;
    }
    .layui-tab-content.padding-20 {
        padding: 20px;
    }
    /*$("#r_template_tab").addClass("r-hidee-button");*/
    #tab_template_r.r-hidee-button button{
        display: none;
    }
</style>

<style>
    .cd-topbar {
        background-color: #ffffff;
    }
    .cd-topbar .layui-tab-title {
        display: flex;
        justify-content: space-between;
        padding: 0 30px;
        height: 60px;
        font-weight: 500;
    }
    .cd-topbar .layui-tab-title .cd-li {
        height: 60px;
        line-height: 60px;
        padding: 0 20px;
    }
    .cd-topbar .layui-tab-title .cd-li.layui-this::after {
        top: auto;
        bottom: 0;
    }

    .cd-toolbar {
        box-sizing: border-box;
        display: flex;
        justify-content: space-between;
        height: 64px;
        padding: 0 20px 0 50px;
        margin: -20px -20px 20px -20px;
        line-height: 64px;
        background-color: rgba(140, 213, 255, 0.1);
    }
    .cd-toolbar .cd-toolbar-left {
        font-size: 0;
    }
    .cd-toolbar .cd-toolbar-left .cd-toolbar-tip {
        font-size: 16px;
        font-weight: 500;
        color: #000;
    }
    .cd-toolbar .cd-toolbar-left .cd-toolbar-color-chunk {
        display: inline-block;
        width: 30px;
        height: 14px;
        margin-left: 12px;
        border-radius: 2px;
        background: #56E9FD;
        vertical-align: -1px;
    }
    .cd-toolbar .cd-btn.layui-btn {
        height: 26px;
        line-height: 26px;
    }
    .cd-toolbar .cd-btn.layui-btn.layui-btn-normal {
        background: none;
        border: 1px solid #DEE2EC;
        color: #666666;
    }
    .cd-toolbar .cd-btn.layui-btn.layui-btn-light-blue {
        background: #009DFF;
        color: #ffffff;
        border: 1px solid rgba(0, 0, 0, 0);
    }
    .im_result {
        display: flex;
        justify-content: space-between;
        height: 24px;
        margin-top: 6%;
        line-height: 24px;
        color: #333333;
    }
    .im_result .im_result_o { color: #0783C9; }
    .im_result .im_result_x { color: #DE2D06; font-size: 18px; margin-top: -1px; }
    .im_result .im_result_slash { color: #ED8000; }
    .im_result .im_result_num {
        margin-left: 2px;
        color: #333333;
        font-size: 14px;
    }
</style>
<div class="layui-tab layui-tab-brief" style="width: 100%; justify-content: center;" lay-filter="changeTab1">
    <ul class="layui-tab-title" style="justify-content: center;">
        <li class="layui-this">设备基础信息</li>
        <li id="device_guard_tab">设备监视信息</li>
        <li>设备点检信息</li>
    </ul>
    <div class="layui-tab-content" style="justify-content: center;">
        <div class="layui-tab-item layui-show">
            <div id="machineForm" class="layui-form" action=""
                 style="padding: 20px; width: 97%;background-color: white;">
                <input type="hidden" id="id" name="id" value="${model.id}">
                <div style="flex-direction: row;">
                    <div style="float: left;">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label"><font class="required-dot">*</font>所属企业</label>
                                <div class="layui-input-inline" style="">
                                    <select id="enterpriseId" name="enterpriseId" lay-verify="required">
                                        <option value="">请选择</option>
                                        <c:forEach var="item" items="${enterpriseInfoList}" varStatus="vs">
                                            <option value="${item.id}" ${model.enterpriseId==item.id?'selected="selected"':''}>${item.enterpriseName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">设备图片</label>
                                <div class="layui-input-block">
                                    <input type="hidden" id="photoPath" name="photoPath" lay-verify=""
                                           value="${model.photoPath}" autocomplete="off" class="layui-input">
                                    <!-- <button type="button" class="layui-btn layui-btn-light-blue" id="btn-upload"><i class="layui-icon">&#xe67c;</i>上传图片</button> -->
                                    <button type="button" class="layui-btn layui-btn-light-blue"
                                            id="machineImgBtn" style="width: 190px;">
                                        <i class="layui-icon">&#xe67c;</i>上传图片
                                    </button>
                                    <p style="font-size: 5px; color: #999999">推荐图片格式jpg，大小限制在200kb以内，分辨率***</p>
                                    <i id="bulletTypeImgLoad"
                                       class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"
                                       style="display: none; font-size: 20px;"></i> <i
                                        id="bulletTypeImgYes" class="layui-icon layui-icon-ok-circle"
                                        style="display: none; color: #5FB878; font-size: 20px;"
                                        title="上传图片成功"></i> <i id="bulletTypeImgNo"
                                                               class="layui-icon layui-icon-close-fill"
                                                               style="display: none; color: #F96768; font-size: 20px;"
                                                               title="上传图片失败"></i>
                                    <input type="hidden" id="oldPhoto" value="${model.photoPath}" class="layui-input">
                                    <input type="hidden" name="isChangePhoto" value="false">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label"><font class="required-dot">*</font>设备编号</label>
                                <div class="layui-input-inline" style="">
                                    <select name="machineNo" lay-verify="required|uniqueCondensingDeviceNum">
                                        <option value="">请选择</option>
                                        <option value="1" ${model.machineNo==1?'selected="selected"':''}>1</option>
                                        <option value="2" ${model.machineNo==2?'selected="selected"':''}>2</option>
                                        <option value="3" ${model.machineNo==3?'selected="selected"':''}>3</option>
                                        <option value="4" ${model.machineNo==4?'selected="selected"':''}>4</option>
                                        <option value="5" ${model.machineNo==5?'selected="selected"':''}>5</option>
                                        <option value="6" ${model.machineNo==6?'selected="selected"':''}>6</option>
                                        <option value="7" ${model.machineNo==7?'selected="selected"':''}>7</option>
                                        <option value="8" ${model.machineNo==8?'selected="selected"':''}>8</option>
                                        <option value="9" ${model.machineNo==9?'selected="selected"':''}>9</option>
                                        <option value="10" ${model.machineNo==10?'selected="selected"':''}>10</option>
                                        <option value="11" ${model.machineNo==11?'selected="selected"':''}>11</option>
                                        <option value="12" ${model.machineNo==12?'selected="selected"':''}>12</option>
                                        <option value="13" ${model.machineNo==13?'selected="selected"':''}>13</option>
                                        <option value="14" ${model.machineNo==14?'selected="selected"':''}>14</option>
                                        <option value="15" ${model.machineNo==15?'selected="selected"':''}>15</option>
                                        <option value="16" ${model.machineNo==16?'selected="selected"':''}>16</option>
                                        <option value="17" ${model.machineNo==17?'selected="selected"':''}>17</option>
                                        <option value="18" ${model.machineNo==18?'selected="selected"':''}>18</option>
                                        <option value="19" ${model.machineNo==19?'selected="selected"':''}>19</option>
                                        <option value="20" ${model.machineNo==20?'selected="selected"':''}>20</option>
                                        <option value="21" ${model.machineNo==21?'selected="selected"':''}>21</option>
                                        <option value="22" ${model.machineNo==22?'selected="selected"':''}>22</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label"><font class="required-dot">*</font>设备名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="machineName" value="${model.machineName}"
                                           lay-verify="required|textMaxlength" class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label"><font class="required-dot">*</font>设备类型</label>
                                <div class="layui-input-inline">
                                    <select name="machineType" lay-verify="required">
                                        <option value="">请选择</option>
                                        <c:forEach var="item" items="${machineTypeList}" varStatus="vs">
                                            <option value="${item.itemValue}" ${model.machineType==item.itemValue?'selected="selected"':''}>${item.itemName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label"><font class="required-dot">*</font>设备型号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="machineModel" value="${model.machineModel}"
                                           lay-verify="required|textMaxlength" class="layui-input">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="width: 300px; float: left; text-align: center;">
                        <img id="imgPreview" alt="图片预览" title="图片预览"
                             src="device/deviceInfo/listfileLook.do?url=${upload_url}/${model.photoPath}" height="164"
                             onerror="loadDefaultImg(this)" style="max-width: 300px;">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label"><font class="required-dot">*</font>是否远程监视</label>
                        <div class="layui-input-inline" style="">
                            <select name="remoteMonitorFlag" lay-verify="required">
                                <option value="">请选择</option>
                                <option value="1" ${model.remoteMonitorFlag==1?'selected="selected"':''}>是</option>
                                <option value="0" ${model.remoteMonitorFlag==0?'selected="selected"':''}>否</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label"><font class="required-dot">*</font>监控设备</label>
                        <div class="layui-input-inline" style="">
                            <select name="deviceCode" lay-verify="required">
                                <option value="">请选择</option>
                                <c:forEach var="item" items="${deviceInfo}" varStatus="vs">
                                    <option value="${item.deviceCode}" ${model.deviceCode==item.deviceCode?'selected="selected"':''}>${item.deviceName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label"><font class="required-dot">*</font>是否点检</label>
                        <div class="layui-input-inline" style="">
                            <select name="checkFlag" lay-verify="required" id="checkFlag">
                                <option value="">请选择</option>
                                <option value="1" ${model.checkFlag==1?'selected="selected"':''}>是</option>
                                <option value="0" ${model.checkFlag==0?'selected="selected"':''}>否</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label"><font class="required-dot">*</font>是否保养</label>
                        <div class="layui-input-inline" style="">
                            <select name="maintainFlag" lay-verify="required" id="maintainFlag">
                                <option value="">请选择</option>
                                <option value="1" ${model.maintainFlag==1?'selected="selected"':''}>是</option>
                                <option value="0" ${model.maintainFlag==0?'selected="selected"':''}>否</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label"><font class="required-dot">*</font>选择班组</label>
                        <div class="layui-input-inline" style="">
                            <select id="team" name="teamId" lay-verify="required" lay-filter="changeTeam">
                                <option value="">请选择</option>
                                <c:forEach var="item" items="${teamList}" varStatus="vs">
                                    <option value="${item.id}-${item.orgId}-${item.orgName}" ${model.teamId==item.id?'selected="selected"':''}>${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">管理部门</label>
                        <div class="layui-input-inline">
                            <input id="orgId" type="text" name="orgId"
                                   class="layui-input layui-hide">
                            <input id="orgName" type="text" disabled="disabled"
                                   class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">生产厂商</label>
                        <div class="layui-input-inline">
                            <input type="text" name="manufacotry" value="${model.manufacotry}" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">所属位置</label>
                        <div class="layui-input-inline">
                            <input type="text" name="location" value="${model.location}" class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">备注</label>
                        <div class="layui-input-inline">
                            <input type="text" name="remark" value="${model.remark}" class="layui-input">
                        </div>
                    </div>
                </div>


                <div class="layui-form-item"
                     style="text-align: right; padding-top: 20px;">
                    <button class="layui-btn layui-btn-normal" lay-submit=""
                            lay-filter="machineInfoSummit">提交
                    </button>
                    <a class="layui-btn layui-btn-primary" onclick="resetModel()">取消</a>
                </div>
            </div>
        </div>
        <div class="layui-tab-item">
            <table id="remote-monitor-list" lay-filter="loadMachineInfo"></table>
        </div>
        <div class="layui-tab-item">
            <div class="cd-topbar">
                <div class="layui-tab layui-tab-brief" lay-filter="changeTab2" id="machine_im_info">
                    <ul class="layui-tab-title">
                        <li id="r_template_tab" class="layui-this cd-li">日常点检模板</li>
                        <li id="r_schedule_tab" class="cd-li">日常点检排班</li>
                        <li id="d_template_tab" class="cd-li">定期点检模板</li>
                        <li id="d_schedule_tab" class="cd-li">定期点检排班</li>
                        <li id="b_template_tab" class="cd-li">保养模板</li>
                        <li id="b_schedule_tab" class="cd-li">保养排班</li>
                    </ul>
                    <div class="layui-tab-content padding-20">
                        <div class="layui-tab-item layui-show" id="tab_template_r">
                            <div style="width: 100%;">
                                <input id="template_id_r" type="text" class="layui-input layui-hide">
                                <div style="margin: 0px 10px 10px 10px;">
                                    <button class="layui-btn layui-btn-normal layui-btn-light-blue"
                                            id="check-class-add-r">
                                        <i class="fa fa-plus-square" aria-hidden="true"></i> 添加点检分类
                                    </button>
                                </div>
                                <div id="check-class-list-r" class="layui-collapse" lay-accordion="">

                                </div>
                                <div class="layui-form-item" style="text-align: right;padding-top: 15px;">
                                    <button class="layui-btn layui-btn-normal check-class-save" type="button">提交</button>
                                    <a class="layui-btn layui-btn-primary" onclick="resetModel()">取消</a>
                                </div>
                            </div>
                        </div>
                        <div class="layui-tab-item">
                            <div class="cd-toolbar">
                                <div class="cd-toolbar-left">
                                    <span class="cd-toolbar-tip">已排班</span>
                                    <span class="cd-toolbar-color-chunk"></span>
                                </div>
                                <div class="cd-toolbar-right">
                                    <button class="layui-btn layui-btn-normal cd-btn" type="button"
                                            id="r_cal_down"> 下载排班表
                                    </button>
                                    <button class="layui-btn layui-btn-normal layui-btn-light-blue cd-btn" type="button"
                                            id="r_cal_save"> 提交
                                    </button>
                                </div>
                            </div>
                            <div id='container_r'></div>
                        </div>
                        <div class="layui-tab-item" id="tab_template_d">
                            <div style="width: 100%;">
                                <input id="template_id_d" type="text" class="layui-input layui-hide">
                                <div style="margin: 0px 10px 10px 10px;">
                                    <button class="layui-btn layui-btn-normal layui-btn-light-blue"
                                            id="check-class-add-d">
                                        <i class="fa fa-plus-square" aria-hidden="true"></i> 添加点检分类
                                    </button>
                                </div>
                                <div id="check-class-list-d" class="layui-collapse" lay-accordion="">

                                </div>
                                <div class="layui-form-item" style="text-align: right;padding-top: 15px;">
                                    <button class="layui-btn layui-btn-normal check-class-save" type="button">提交</button>
                                    <a class="layui-btn layui-btn-primary" onclick="resetModel()">取消</a>
                                </div>
                            </div>
                        </div>
                        <div class="layui-tab-item">

                            <div class="cd-toolbar">
                                <div class="cd-toolbar-left">
                                    <span class="cd-toolbar-tip">已排班</span>
                                    <span class="cd-toolbar-color-chunk"></span>
                                </div>
                                <div class="cd-toolbar-right">
                                    <button class="layui-btn layui-btn-normal cd-btn" type="button"
                                            id="d_cal_set_schedule" onclick="openSetSchedule('D')"> 排班设定
                                    </button>
                                    <button class="layui-btn layui-btn-normal cd-btn" type="button"
                                            id="d_cal_down"> 下载排班表
                                    </button>
                                    <button class="layui-btn layui-btn-normal layui-btn-light-blue cd-btn" type="button"
                                            id="d_cal_save"> 提交
                                    </button>
                                </div>
                            </div>
                            <div id='container_d'></div>
                        </div>
                        <div class="layui-tab-item" id="tab_template_b">
                            <div style="width: 100%;">
                                <input id="template_id_b" type="text" class="layui-input layui-hide">
                                <div style="margin: 0px 10px 10px 10px;">
                                    <button class="layui-btn layui-btn-normal layui-btn-light-blue"
                                            id="check-class-add-b">
                                        <i class="fa fa-plus-square" aria-hidden="true"></i> 添加点检分类
                                    </button>
                                </div>
                                <div id="check-class-list-b" class="layui-collapse" lay-accordion="">

                                </div>
                                <div class="layui-form-item" style="text-align: right;padding-top: 15px;">
                                    <button class="layui-btn layui-btn-normal check-class-save" type="button">提交</button>
                                    <a class="layui-btn layui-btn-primary" onclick="resetModel()">取消</a>
                                </div>
                            </div>
                        </div>
                        <div class="layui-tab-item">
                            <div class="cd-toolbar">
                                <div class="cd-toolbar-left">
                                    <span class="cd-toolbar-tip">已排班</span>
                                    <span class="cd-toolbar-color-chunk"></span>
                                </div>
                                <div class="cd-toolbar-right">
                                    <button class="layui-btn layui-btn-normal cd-btn" type="button"
                                            id="b_cal_set_schedule" onclick="openSetSchedule('B')"> 排班设定
                                    </button>
                                    <button class="layui-btn layui-btn-normal cd-btn" type="button"
                                            id="b_cal_down"> 下载排班表
                                    </button>
                                    <button class="layui-btn layui-btn-normal layui-btn-light-blue cd-btn" type="button"
                                            id="b_cal_save"> 提交
                                    </button>
                                </div>
                            </div>
                            <div id='container_b'></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script>closeLoading();

var ctx = '${ctx}';
var upload_url = '${upload_url}';
closeLoading();


function openSetSchedule(templateType) {
    openLoading();
    var templateId = templateType == "D" ? $("#template_id_d").val() : $("#template_id_b").val();
    var machineId = $("#id").val();
    if (!templateId) {
        closeLoading();
        openErrAlert("模版为空，请先保存模版！");
        return false;
    }
    $.get("dataDesources/inspectionMaintainSchedule/setSchedule.do", {
            templateId: templateId,
            machineId: machineId
        }, function (data) {
            closeLoading();
            topIdx = layer.open({
                type: 1,
                title: "设定模版",
                area: ['450px', '420px'],
                closeBtn: 1,
                content: data,
                maxmin: true,
                resize: true
            });
        }
    );
}

function resetModel(){
    $("#main-tab").find('[lay-id="menu-5-1"]').click();
}


$(function ($) {
    var form, element, upload;
    layui.use(['element', 'layer', 'upload'], function () {
        form = layui.form, element = layui.element, upload = layui.upload;
    });
});

</script>
<script>
    r_check.init();
    $('[placeholder]').placeholder();
    var strList = document.getElementById('team').value.split('-');
    document.getElementById("orgId").value = strList[1];
    document.getElementById("orgName").value = strList[2];

    upload.render({
        elem: '#machineImgBtn',
        url: 'upload.do?type=' + upload_url + '&size=2048',
        accept: 'images',
        size: 2048, //限制文件大小，单位 KB
        xhr: xhrOnProgress,
        progress: function (value) {
        },
        data: {
            //type:'${upload_url}'
        },
        choose: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            obj.preview(function (index, file, result) {
                $('#imgPreview').attr('src', result); //图片链接（base64）
            });
        },
        before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            //this.data['specifiedName'] = $("#bulletTypeName").val();
            $("#machineForm #bulletTypeImgLoad").show();
            $("#machineForm #bulletTypeImgYes").hide();
            $("#machineForm #bulletTypeImgNo").hide();
        },
        done: function (data) {
            $("#machineForm #bulletTypeImgLoad").hide();
            if (data.success) {
                $("#machineForm #photoPath").val(data.data.fileName);
                var _i = data.data.fullFileName + "?t=" + Math.random();
                $("#machineForm #bulletTypeImgYes").show();
                $("#machineForm #bulletTypeImgNo").hide();
            } else {
                $("#machineForm #bulletTypeImgYes").hide();
                $("#machineForm #bulletTypeImgNo").show();
                openErrAlert(data.msg);
            }
        },
        error: function (index, upload) {
            $("#machineForm #bulletTypeImgLoad").hide();
            $("#machineForm #bulletTypeImgYes").hide();
            $("#machineForm #bulletTypeImgNo").show();
        }
    });

    //自定义验证规则
    form.verify({
        textMaxlength: function (value) {
            if (value.length > 32) {
                return '长度超过32个字符';
            }
        },
        valueLength: function (value) {
            if (value.length > 256) {
                return '长度超过256个字符';
            }
        },
        numFlag: function (value) {
            var x = Number(value);
            if (x >= 0) {
            } else {
                return '请输入大于等于0的整数';
            }
        },
        numFlag2: function (value) {
            if (isNaN(value)) {
                return '请输入数字';
            }
        },
        specialChar: function (value) {
            var pattern = new RegExp(specialCharReg);
            if (isNotEmpty(value) && pattern.test(value)) {
                return '禁止输入特殊字符';
            }
        },
        uniqueTagName: function (value) {
            var flag = true;
            $.ajax({
                type: "get",
                url: "dataDesources/monitorFactorTag/checkTagNameExist.do",
                data: {str: value, id: $("input#id").val()},
                async: false,
                dataType: "json",
                success: function (data) {
                    flag = data.success;
                }
            });
            if (!flag) return '监测因子标签名称已存在';
        }
    });

    form.on('select(changeTeam)', function (data) {
        var strList = data.value.split('-');
        document.getElementById("orgId").value = strList[1];
        document.getElementById("orgName").value = strList[2];
    })

    //监听提交
    form.on('submit(machineInfoSummit)', function (data) {
        var strList = data.field.teamId.split('-');
        data.field.teamId = strList[0];
        if (document.getElementById('id').value == '') {
            machineAddEditSave(data.field, 1);
        } else {
            machineAddEditSave(data.field, 2);
        }
        return false;
    });

    $("#device_guard_tab").click(function () {
        table.render({
            elem: '#remote-monitor-list',
            id: 'remote-monitor-list',
            cols: [[{align: 'center', field: 'factorName', title: '检测因子名称', minWidth: 180, width: '15%'}
                , {align: 'center', field: 'factorCode', title: '检测因子编码', minWidth: 120, width: '8%'}
                , {align: 'center', field: 'factorTag', title: '检测因子标签', minWidth: 120, width: '8%'}
                , {align: 'center', field: 'factorUnit', title: '检测因子单位', minWidth: 120, width: '8%'}
                , {align: 'center', field: 'machineTypeName', title: '设备类型', minWidth: 150, width: '10%'}
                , {align: 'center', field: 'typeId', title: '数据类型', minWidth: 120, width: '10%'}
                , {align: 'center', field: 'dataAccuracy', title: '数据精度', minWidth: 120, width: '8%'}
                , {align: 'center', field: 'decimalDigits', title: '保留小数位', minWidth: 120, width: '8%'}
                , {
                    align: 'center',
                    field: 'alarmState',
                    title: '是否报警',
                    minWidth: 120,
                    width: '8%',
                    templet: function(d){
                        if(d.alarmState == "0")return "不报警";
                        if(d.alarmState == "1")return "报警";
                        return "";
                    }
                }
                , {align: 'center', field: 'upperLimit', title: '上限', minWidth: 120}
                , {align: 'center', field: 'lowerLimit', title: '下限', minWidth: 120}
            ]],
            url: 'dataDesources/machineManager/monitor_factor_list.do',
            where: {
                id: document.getElementById('id').value
            },
            page: pageParam,
            limit: 10,
            height: 'full-' + getTableFullHeight(),
            done: function (res, curr, count) {
                //无操作提示项
                emptyOptBar("remote-monitor-list");
                resizeTable('remote-monitor-list');
                closeLoading();
                var data = res.data;
                if (data.length == 0)
                    emptyList("remote-monitor-list");
            }
        });
    });

    //是否点检
    var checkFlag = $("#checkFlag option:selected").val();
    //是否保养
    var maintainFlag = $("#maintainFlag option:selected").val();

    //因为默认打开的是日常点检，因此如果不允许点检，去掉按钮
    if(checkFlag != "1"){
        $("#tab_template_r").addClass("r-hidee-button");
    }

    //默认打开的是日常模版tab
    var oldLi = "#r_template_tab";
    //点检排班tab
    $("#r_schedule_tab").off("click").click(function () {
        if (checkFlag === "1"){
            setTimeout(function () {
                calendar_r.init();
            },200)
        }
    });

    //定期排班tab
    $("#d_schedule_tab").off("click").click(function () {
        if (checkFlag === "1"){
            setTimeout(function () {
                calendar_d.init();
            },200)
        }
    });
    //保养排班tab
    $("#b_schedule_tab").off("click").click(function () {
        if (maintainFlag === "1"){
            setTimeout(function () {
                calendar_b.init();
            },200)
        }
    });

    if (checkFlag === "1") {
        r_check.init();
        d_check.init();
        $("#d_cal_set_schedule").show();
    } else {
        $("#d_cal_set_schedule").hide();
    }
    if (maintainFlag === "1") {
        b_check.init();
        $("#b_cal_set_schedule").show();
    } else {
        $("#b_cal_set_schedule").hide();
    }


    element.on('tab(changeTab2)', function(data){
        var id = $(this).attr("id");
        var templateType = id.substring(0,1);
        if(checkFlag != "1" && maintainFlag != "1"){
            $("#machine_im_info").find(".layui-this").removeClass("layui-this");
            $("#machine_im_info").find(".layui-show").removeClass("layui-show");
            openErrAlert("该设备不允许点检和保养");
            return;
        }
        if(templateType === "r" || templateType === "d"){
            if(checkFlag != "1"){
                openErrAlert("该设备不允许点检");
                $(oldLi).click();
                return;
            }
        }
        if(templateType === "b"){
            if(maintainFlag != "1"){
                $(oldLi).click();
                openErrAlert("该设备不允许保养");
                return;
            }
        }
        oldLi = "#" + id;
    });

    element.render();
    form.render();

</script>
