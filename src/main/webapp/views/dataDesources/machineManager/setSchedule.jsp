<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/dataDesources/monitorFactor.js"></script>
<style>
    .layui-form-label {
        width: 80px;
    }

    .layui-input-block {
        margin-left: 110px;
        margin-right: 30px;
    }

    .layui-form-item {
        margin-bottom: 10px;
        clear: both;
    }
</style>
<div class="layui-form" style="padding: 20px">
    <div class="layui-form-item">
        <input type="txt" name="id" class="layui-input layui-hide" value="${setSchedule.id}" />
        <label class="layui-form-label"><font class="required-dot">*</font>开始日期</label>
        <div class="layui-input-block">
            <input type="text" id="startDate" name="startDate" lay-verify="required"
                   value="<fmt:formatDate value="${setSchedule.startDate}" pattern="yyyy-MM-dd"/>"
                   onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"
                   style="cursor: default;width:100%;height:30px;" class="Wdate layui-input search-maxlenght"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><font class="required-dot">*</font>次数</label>
        <div class="layui-input-block">
            <input type="text" name="times" lay-verify="required|numFlag" maxlength="2" class="layui-input"
                   value="${setSchedule.times}"/>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label"><font class="required-dot">*</font>频率</label>
        <div class="layui-input-block">
            <select multiple="" name="frequency" xm-select="select7_3" xm-select-search=""
                    xm-select-show-count="3" xm-select-height="30px" xm-select-width="200px" lay-verify="required">>
                <%--<option selected="selected" value="">请选择频率</option>--%>
                <option value="DAY" ${setSchedule.frequency=='DAY'?'selected="selected"':''}>天</option>
                <option value="WEEK"${setSchedule.frequency=='WEEK'?'selected="selected"':''}>周</option>
                <option value="MONTH"${setSchedule.frequency=='MONTH'?'selected="selected"':''}>月</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item" style="text-align: right;">
        <button class="layui-btn layui-btn-normal set-schedule-btn" lay-submit="" lay-filter="setSchedule">提交</button>
        <a class="layui-btn layui-btn-primary" onclick="closeLayer()">取消</a>
    </div>
</div>

<script>

    form.render();
    //自定义验证规则
    form.verify({
        numFlag: function (value) {
            if (isNaN(value)) {
                return '请输入数字';
            }
            var x = Number(value);
            if (x > 0) {
            } else {
                return '请输入大于0的整数';
            }
        }
    });

    //监听提交
    form.on('submit(setSchedule)', function (data) {
        var url = "dataDesources/inspectionMaintainSchedule/saveSetSchedule.do";
        var param = data.field;
        param.machineId = '${machineId}';
        param.templateId = '${templateId}';
        if(!param.templateId){
            openErrAlert("模版为空，请先保存模版！");
            return false;
        }
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(param), // 传入已封装的参数
            dataType: "json",
            contentType: 'application/json',
            success: function (result) {
                var lay_tab_this_id = $("#machine_im_info").find("li.layui-this").attr("id");
                var templateIdType = lay_tab_this_id.substring(0, 1);
                if(templateIdType == "d"){
                    calendar_d.init();
                }else if(templateIdType == "b"){
                    calendar_b.init();
                }
                closeLoading();
                requestSuccess(result);
                closeLayer();
            }
        });
        return false;
    });

</script>