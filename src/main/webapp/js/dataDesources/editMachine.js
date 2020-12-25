var htmlConst = {
    calendarBtn: '<button type="button" class="layui-btn layui-btn-warm">已排班</button>'
};

var r_check = {
    templateType: "R",
    //模版html中id的拼接类型
    templateIdType: "r",
    checkClassIndex: 0,
    checkItemIndexList: [],
    getClassHtml: getClassHtmlR,
    getItemHtml: getItemHtmlR,
    init: function () {
        this.reset();
        this.initEvent();
        this.loadData();
    },
    reset: function () {
        $("#check-class-list-" + this.templateIdType).html('');
        this.checkClassIndex = 0;
        this.checkItemIndexList = [];
    },
    initEvent: function () {
        var me = this;
        // layui.use(['element', 'layer', 'upload'], function () {
        //     var form = layui.form, element = layui.element;
        //
        //     element.render();
        //     form.render();

        me.form = form;
        me.element = element;

        //监听折叠
        /*element.on('collapse(test)', function(data){
            // layer.msg('展开状态：'+ data.show);
        });*/

        /*element.on('button(checkClassAdd)', function (data) {
            layer.msg('hello');
        });*/
        var tab = "#tab_template_" + me.templateIdType;
        $(tab).off('click', '#check-class-add-' + me.templateIdType).on('click', '#check-class-add-' + me.templateIdType, function (data) {
            // ++me.checkClassCount;
            ++me.checkClassIndex;
            me.checkItemIndexList[me.checkClassIndex] = [];
            var html = me.getClassHtml(me.checkClassIndex);
            $("#check-class-list-" + me.templateIdType).append(html);
            me.element.render();
            me.form.render();
        });
        //添加分类
        $(tab).off('click', 'button.check-item-add').on('click', 'button.check-item-add', function (data) {
            var strList = $(this).attr("id").split('-');
            var index = strList[4];
            var subIndex = me.checkItemIndexList[index];
            ++subIndex;
            me.checkItemIndexList[index] = subIndex;
            var html = me.getItemHtml(index, subIndex);
            var id = '#check-item-list-' + me.templateIdType + "-" + index;
            $(id).append(html);
            me.element.render();
            me.form.render();
            me.checkItemIndexList[index][subIndex] = 1;
            initUpload("#btn-img-add-" + me.templateIdType + "-" + index + "-" + subIndex)
            resetItemNo(me.templateIdType);
        });

        //删除项
        $(tab).off('click', 'button.check-class-del').on('click', 'button.check-class-del', function (data) {
            var strList = $(this).attr("id").split('-');
            var id = '#colla-item-' + me.templateIdType + "-" + strList[4] + '-' + strList[5];
            $(id).remove();
            me.checkItemIndexList[strList[4]][strList[5]] = 0;
            return false;
        });
        //上移
        $(tab).off('click', 'button.check-item-up').on('click', 'button.check-item-up', function (data) {
            var $this_item = $(this).closest(".layui-colla-item");
            $this_item.prev().before($this_item);
            resetItemNo(me.templateIdType);
            return false;
        });
        //下移
        // me.form.on('submit(check-item-down)', function (data) {
        $(tab).off('click', 'button.check-item-down').on('click', 'button.check-item-down', function (data) {
            var $this_item = $(this).closest(".layui-colla-item");
            $this_item.next().after($this_item);
            resetItemNo(me.templateIdType);
            return false;
        });
        //删除分类
        $(tab).off('click', 'button.check-class-del').on('click', 'button.check-class-del', function (data) {
            var strList = $(this).attr("id").split('-');
            var id = '#colla-item-' + me.templateIdType + "-" + strList[4];
            $(id).remove();
            me.checkItemIndexList[strList[4]] = [];
            // --me.checkClassCount;
            return false;
        });

        $(tab).off('click', 'button.check-class-save').on('click', 'button.check-class-save', function (data) {
            var forms = $("#check-class-list-" + me.templateIdType).find("form.classForm");
            var classes = [];
            $.each(forms, function (i, item) {
                var classData = {
                    "className": $(item).find("input[name='className']").val(),
                    "itemList": []
                };
                var itemsDivs = $(item).find(".layui-colla-content");
                $.each(itemsDivs, function (j, itemDiv) {
                    var formData = {
                        "indexNum": $(itemDiv).find("input[name='indexNum']").val(),
                        "name": $(itemDiv).find("input[name='name']").val(),
                        "method": $(itemDiv).find("textarea[name='method']").val(),
                        "standard": $(itemDiv).find("textarea[name='standard']").val(),
                        "noCheck": $(itemDiv).find("input.this_one:checkbox[checked]").val() === "on" ? 1 : 0,
                        "stepImgUrls": $(itemDiv).find("input.photoPath").val()
                    };
                    classData.itemList.push(formData);
                });
                classes.push(classData);
            });
            var validMsg = me.validClassItemData(classes);
            if (!validMsg.status) {
                openErrAlert(validMsg.msg);
                return false;
            }
            me.saveDataToDB(classes, me.templateType);
        });
        // });
    },
    loadData: function () {
        var me = this;
        var element = this.element;
        var form = this.form;
        var machineId = $("#id").val();
        if (!machineId) return false;
        $.get("dataDesources/inspectionMaintainTemplate/getTemplateClassItemByMachineIdAndType.do", {
            machineId: machineId,
            templateType: me.templateType
        }, function (data) {
            var template = $.parseJSON(data).data;
            $("#template_id_" + me.templateIdType).val(template.id);
            if (template.id) {
                for (; me.checkClassIndex < template.classList.length; ++me.checkClassIndex) {
                    var index = me.checkClassIndex + 1;
                    var classData = template.classList[me.checkClassIndex];
                    $("#check-class-list-" + me.templateIdType).append(me.getClassHtml(index));
                    $("#input-class-" + me.templateIdType + "-" + index).val(classData.className);
                    for (var j = 0; j < classData.itemList.length; j++) {
                        var subIndex = j + 1;
                        me.checkItemIndexList[index] = subIndex;
                        var itemData = classData.itemList[j];
                        var html = me.getItemHtml(index, subIndex);
                        var id = '#check-item-list-' + me.templateIdType + "-" + index;
                        $(id).append(html);

                        var item_id = "#colla-item-" + me.templateIdType + "-" + index + "-" + subIndex;
                        $(item_id).find("input[name='name']").val(itemData.name);
                        $(item_id).find("textarea[name='method']").val(itemData.method);
                        $(item_id).find("textarea[name='standard']").val(itemData.standard);
                        $(item_id).find("input.this_one").prop("checked", itemData.noCheck === 1);
                        var imgUrls = itemData.stepImgUrls;
                        $(item_id).find("input.photoPath").val(imgUrls);
                        if (imgUrls) {
                            var urlArr = imgUrls.split(";");
                            for (var i = 0; i < urlArr.length; i++) {
                                var imgUrl = 'device/deviceInfo/listfileLook.do?url=' + upload_url + '/' + urlArr[i];
                                $("#colla-item-" + me.templateIdType + "-" + index + "-" + subIndex).find(".photoPath").closest(".layui-form-item").append(getImagHtml(imgUrl));
                            }
                        }
                        initUpload("#btn-img-add-" + me.templateIdType + "-" + index + "-" + subIndex);
                    }
                }
                resetItemNo(me.templateIdType);
            }
            element.render();
            form.render();
        });
    },
    validClassItemData: function (arr) {
        var returnMsg = {"status": true, "msg": ""};
        if (!arr.length) return {
            "status": false,
            "msg": "请添加点检分类信息！"
        };
        for (var i = 0; i < arr.length; i++) {
            var classData = arr[i];
            var classMsg = "点检分类" + Number(i + 1);
            if (!classData.className) {
                return {
                    "status": false,
                    "msg": "点检分类" + classMsg + "【分类名称】不能为空！"
                };
            } else {
                if (!classData.itemList || classData.itemList.length === 0) {
                    return {
                        "status": false,
                        "msg": classMsg + "没有找到项，请添加后再保存！"
                    };
                } else {
                    for (var j = 0; j < classData.itemList.length; j++) {
                        var itemData = classData.itemList[j];
                        var itemMsg = "点检项" + Number(j + 1);
                        if (!itemData.name) return {
                            "status": false,
                            "msg": classMsg + " / " + itemMsg + "中【点检项目】不能为空！"
                        };
                        if (!itemData.method) return {
                            "status": false,
                            "msg": classMsg + " / " + itemMsg + "中【点检方法】不能为空！"
                        };
                        if (!itemData.standard) return {
                            "status": false,
                            "msg": classMsg + " / " + itemMsg + "中【点检标准】不能为空！"
                        };
                    }
                }
            }
        }
        return returnMsg;
    },

    //保存点检信息到数据库
    saveDataToDB: function (arr, templateType) {
        var me = this;
        var param = {
            machineId: $("#id").val(),
            id: $("#template_id_" + me.templateIdType).val(),
            templateType: templateType,
            classList: arr
        };
        var url = "dataDesources/inspectionMaintainTemplate/save.do";
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(param), // 传入已封装的参数
            dataType: "json",
            contentType: 'application/json',
            success: function (result) {
                closeLoading();
                requestSuccess(result);
                $("#template_id_" + me.templateIdType).val(result.data.id);
            }
        });
    }
};

/* 公用数据 */
var editMachineState = {
    renderData: {},
};

/**
 * @Description 定期点检
 * @Date 2020/10/31 21:50
 * @Author by yuankeyan
 */
var d_check = $.extend(false, r_check, {
    templateType: "D",
    //模版html中id的拼接类型
    templateIdType: "d",
    checkClassIndex: 0,
    checkItemIndexList: [],
    getClassHtml: getClassHtmlD,
    getItemHtml: getItemHtmlD
});

var b_check = $.extend(false, r_check, {
    templateType: "B",
    //模版html中id的拼接类型
    templateIdType: "b",
    checkClassIndex: 0,
    checkItemIndexList: [],
    getClassHtml: getClassHtmlB,
    getItemHtml: getItemHtmlB
});

var r_calendar = {
    calendarId: "#container_r",
    templateIdType: "r",
    init: function () {
        var me = this;

    }
}

/**
 * @Description 排班日历定义
 * @Date 2020/10/30 19:32
 * @Author by yuankeyan
 */
var calendar_r = {
    calendarId: "#container_r",
    templateIdType: "r",
    init: function () {
        var me = this;
        // $(this.calendarId).html("");
        this.myCalendar = new FullCalendar.Calendar($(this.calendarId)[0], this.calendarOpt());
        this.myCalendar.render();
        me.myCalendar.updateSize();
        this.initEvent();
        this.getDataByDb();

    },
    initEvent: function () {
        var me = this;
        $("#" + me.templateIdType + "_cal_save").off("click").click(function () {
            var templateId = $("#template_id_" + me.templateIdType).val();
            var machineId = $("#id").val();
            openLoading();
            if (!machineId) {
                openErrAlert("请先保存设备信息！");
                return false;
            }
            if (!templateId) {
                openErrAlert("模版为空，请先保存模版！");
                return false;
            }
            var calendarData = me.getCalendarData();
            var param = {
                scheduleMap: calendarData.scheduleMap,
                minDate: calendarData.minDate,
                maxDate: calendarData.maxDate,
                machineId: machineId,
                templateIdType: me.templateIdType,
                templateId: templateId
            };
            $.ajax({
                type: "POST",
                url: "dataDesources/inspectionMaintainSchedule/save.do",
                data: JSON.stringify(param), // 传入已封装的参数
                dataType: "json",
                contentType: 'application/json',
                success: function (result) {
                    closeLoading();
                    me.init();
                    requestSuccess(result);

                }
            });
        });

        $("#" + me.templateIdType + "_cal_down").off("click").click(function () {
            var templateId = $("#template_id_" + me.templateIdType).val();
            var machineId = $("#id").val();
            openLoading();
            if (!machineId) {
                openErrAlert("请先保存设备信息！");
                return false;
            }
            if (!templateId) {
                openErrAlert("模版为空，请先保存模版！");
                return false;
            }
            me.downloadExcel();
        });
    },
    /**
     * 获取日历的最大最小值
     * @returns {{minDate: *, maxDate: *}}
     */
    getMaxMinDate: function () {
        var me = this;
        var all_ele = $(me.calendarId).find("td.fc-day");
        return {
            maxDate: $(all_ele[all_ele.length - 1]).attr("data-date"),
            minDate: $(all_ele[0]).attr("data-date")
        };
    },
    /**
     * 获取日期的数据
     * @returns {{minDate: 页面的最小日期, scheduleMap: 每一天是否排班, maxDate: 最大日期}}
     */
    getCalendarData: function () {
        var me = this;
        var all_ele = $(me.calendarId).find("td.fc-day");
        var map = {}, maxDate, minDate;
        for (var i = 0; i < all_ele.length; i++) {
            var v = me.getValueByEle(all_ele[i]);
            $.extend(true, map, v)
            if (i === 0) {
                for (var name in v) {
                    minDate = name;
                }
            }
            if (i === all_ele.length - 1) {
                for (var name in v) {
                    maxDate = name;
                }
            }
        }
        return {
            scheduleMap: map,
            minDate: minDate,
            maxDate: maxDate
        };
    },
    /**
     * 获取当前元素的{日期：是否排班}
     * @param ele
     */
    getValueByEle: function (ele) {
        var me = this;
        var res = {};
        var thisDate = $(ele).attr("data-date");
        if ($(ele).find(".fc-daygrid-day-events .fc-daygrid-event-harness").length > 0) {
            res[thisDate] = true;
        } else {
            res[thisDate] = false;
        }
        return res;
    },
    /**
     * 从后台获取数据
     * 为了防止点的快加载无效数据，这里设置延迟毫秒数来解决
     */
    getDataByDb: function () {
        var me = this;
        var templateId = $("#template_id_" + me.templateIdType).val();
        var machineId = $("#id").val();
        if (!machineId) return false;
        if (!templateId) return false;
        openLoading();
        var param = me.getMaxMinDate();
        param.templateId = templateId;
        param.machineId = machineId;
        setTimeout(function () {
            $.ajax({
                type: "POST",
                url: "dataDesources/inspectionMaintainSchedule/getSchedules.do",
                data: JSON.stringify(param), // 传入已封装的参数
                dataType: "json",
                contentType: 'application/json',
                success: function (result) {
                    editMachineState.renderData = result.data;
                    me.renderCalendar(result.data);
                }
            });
            closeLoading();
        }, 100);
    },
    /**
     * 根据数据渲染日期的排班数据
     */
    renderCalendar: function (data) {
        var me = this;
        var dataKeyArr = Object.keys(data);
        //TODO
        //这个htm的数字之后会改为动态
        var htm = '<span class="im_result">'
                +   '<span class="im_result_o">O <span class="im_result_num">10</span> </span>'
                +   '<span class="im_result_x">x <span class="im_result_num">0</span> </span>'
                +   '<span class="im_result_slash">/ <span class="im_result_num">0</span></span>'
                + '</span>'
        for (var i = 0; i < dataKeyArr.length; i++) {
            var thisDayStr = dataKeyArr[i];
            var thisDayDate = new Date(thisDayStr);
            var flag = data[thisDayStr];
            if (flag) {
                me.myCalendar.addEvent({
                    title: '已排班',
                    start: thisDayDate,
                    // end: me.addDate(thisDayDate, 1),
                    allDay: true
                });
                $(me.calendarId).find("td[data-date='" + thisDayStr + "']").find(".fc-daygrid-day-events").find(".im_result").remove();
                $(me.calendarId).find("td[data-date='" + thisDayStr + "']").find(".fc-daygrid-day-events").append(htm);
            }
        }
    },
    downloadExcel: function () {
        var me = this;
        var param = me.getMaxMinDate();
        var templateId = $("#template_id_" + me.templateIdType).val();
        var machineId = $("#id").val();
        openLoading();
        window.open("inspectionMaintenance/im/downloadIMSchedule.do?templateId=" + templateId + "&machineId="
            + machineId + "&maxDate=" + param.maxDate + "&minDate=" + param.minDate);
        closeLoading();
    },
    addDate: function (date, days) {
        var date = new Date(date);
        date.setDate(date.getDate() + days);
        return date;
    },
    getEventsByDay: function (arr, day) {
        if (arr.length === 0) return null;
        for (var i = 0; i < arr.length; i++) {
            var event = arr[i];
            var start = event._instance.range.start;
            if (day.getMonth() === start.getMonth() && day.getYear() === start.getYear() && day.getDate() === start.getDate()) {
                return event;
            }
        }
        return null;
    },
    customClickFun: function (execFun) {
        var me = this;
        var arr = me.myCalendar.getEvents();
        if (execFun) execFun();
        for (var i = 0; i < arr.length; i++) {
            arr[i].remove();
        }
        me.getDataByDb();
    },
    calendarOpt: function () {
        var me = this;
        return {
            headerToolbar: {
                left: 'today,prev,title,next',
                center: false,
                right: false
            },
            navLinks: false, // can click day/week names to navigate views
            selectable: true,
            selectMirror: true,
            locale: 'zh-cn',
            fixedWeekCount: false,
            select: function (arg) {
                var start = arg.start;
                var end = arg.end;
                var calendarDOM = $(me.calendarId);
                for (var d = new Date(start); d < end; d = me.addDate(d, 1)) {
                    var thisEvent = me.getEventsByDay(this.getEvents(), d);
                    if (thisEvent) {
                        thisEvent.remove();
                    } else {
                        var thisDayStr = d.format('yyyy-MM-dd');
                        var htm = calendarDOM.find("td[data-date='" + thisDayStr + "']").find(".fc-daygrid-day-events").find(".im_result");
                        htm.remove();
                        // var dp = me.addDate(d, 1);
                        me.myCalendar.addEvent({
                            title: '已排班',
                            start: d,
                            // end: dp,
                            allDay: arg.allDay
                        });
                        calendarDOM.find("td[data-date='" + thisDayStr + "']").find(".fc-daygrid-day-events").append(htm);
                    }
                }
                me.myCalendar.unselect();
            },
            eventClick: function (arg) {
                arg.event.remove()
            },
            customButtons: {
                today: {
                    text: '今日',
                    click: function () {
                        me.customClickFun(function () {
                            me.myCalendar.today();
                        });
                    }
                },
                prev: {
                    text: '上月',
                    click: function () {
                        me.customClickFun(function () {
                            me.myCalendar.prev();
                        });
                    }
                },
                next: {
                    text: '下月',
                    click: function () {
                        me.customClickFun(function () {
                            me.myCalendar.next();
                        });
                    }
                }
            },
            editable: true,
            dayMaxEvents: true // allow "more" link when too many events
        }
    }
};

//定期点检排班
var calendar_d = $.extend(false, calendar_r, {
    calendarId: "#container_d",
    templateIdType: "d"
});

//保养点检排班
var calendar_b = $.extend(false, calendar_r, {
    calendarId: "#container_b",
    templateIdType: "b"
});

/**
 * @Description 初始化模版中的上传附件按钮
 * @Date 2020/10/30 19:33
 * @Author by yuankeyan
 */
function initUpload(id, templateIdType) {
    //附件
    var suffix = id.substring(12);
    layui.use(['form', 'upload'], function () {
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            elem: id,
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
                    $(id).closest(".layui-form-item").append(getImagHtml(result));
                    // $('#imgPreview').attr('src', result); //图片链接（base64）
                });
            },
            before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                $("#bulletTypeImgLoad" + suffix).show();
                $("#bulletTypeImgYes" + suffix).hide();
                $("#bulletTypeImgNo" + suffix).hide();
            },
            done: function (data) {
                $("#bulletTypeImgLoad" + suffix).hide();
                if (data.success) {
                    //待处理
                    var oldPath = $("#photoPath" + suffix).val();
                    var newPath = "";
                    var diffPath = data.data.fullFileName.substring(upload_url.length + 1);
                    if (oldPath) newPath += oldPath + ";" + diffPath; else newPath = diffPath;
                    $("#photoPath" + suffix).val(newPath);
                    $("#bulletTypeImgYes" + suffix).show();
                    $("#bulletTypeImgNo" + suffix).hide();
                } else {
                    $("#bulletTypeImgYes" + suffix).hide();
                    $("#bulletTypeImgNo" + suffix).show();
                    openErrAlert(data.msg);
                }
            },
            error: function (index, upload) {
                $("#bulletTypeImgLoad" + suffix).hide();
                $("#bulletTypeImgYes" + suffix).hide();
                $("#bulletTypeImgNo" + suffix).show();
            }
        });
    });
}


/**
 * 重置item的编号 从1开始
 * @param templateIdType
 */
function resetItemNo(templateIdType) {
    var forms = $("#check-class-list-" + templateIdType).find("form.classForm");
    $.each(forms, function (i, item) {
        var index = 0;
        var itemsDivs = $(item).find("input[name='indexNum']");
        $.each(itemsDivs, function (j, _input) {
            $(_input).val(++index);
        });
    });
}


function getImagHtml(src) {
    return `<div style="position: relative; max-width: 300px; margin-right: 5px">
        <img alt="图片预览" title="图片预览" src=${src} height="164" onerror="loadDefaultImg(this)" style="max-width: 300px;">
        <div style="position: absolute; top: -4px; right: -4px; width: 15px; height: 15px; line-height: 15px; border: 1px solid red; border-radius: 50%;color: red;">
            <i class="layui-icon layui-icon-close"></i>
        </div>
    </div>`
    // return '<img alt="图片预览" title="图片预览" src="' + src + '" height="164" onerror="loadDefaultImg(this)" style="max-width: 300px;">';
}

function getClassHtmlR(checkClassIndex) {
    var html = '';
    html += '<div id="colla-item-r-' + checkClassIndex + '" class="layui-colla-item">';
    html += '  <h2 class="layui-colla-title">点检分类-' + checkClassIndex + '</h2>';
    html += '  <div class="layui-colla-content">';
    html += '    <form class="layui-form classForm" action="">';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>点检分类</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input id="input-class-r-' + checkClassIndex + '" style="width: 650px;" required type="text" autocomplete="off" name="className" placeholder="请输入点检分类" class="layui-input">';
    html += '          </div>';
    html += '          <div style="margin-left: 810px;">';
    html += '            <button type="button" id="btn-item-add-r-' + checkClassIndex + '" class="layui-btn layui-btn-normal check-item-add" ><i class="fa fa-plus-square" aria-hidden="true"></i> 添加点检项</button>';
    html += '            <button type="button" id="btn-class-del-r-' + checkClassIndex + '" class="layui-btn layui-btn-normal check-class-del"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-collapse" lay-accordion="" id="check-item-list-r-' + checkClassIndex + '">';
    html += '      </div>';
    html += '    </form>';
    html += '  </div>';
    html += '</div>';
    return html;
}

function getItemHtmlR(index, subIndex) {
    var html = '';
    html += '<div id="colla-item-r-' + index + '-' + subIndex + '" class="layui-colla-item">';
    html += '  <h2 class="layui-colla-title">点检项-' + subIndex + '</h2>';
    html += '  <div class="layui-colla-content">';
    html += '    <form class="layui-form" action="">';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label">点检编号：</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input style="width: 300px;" type="text" value="1" readonly name="indexNum" autocomplete="off" class="layui-input">';
    html += '          </div>';
    html += '          <div style="margin-left: 680px;">';
    html += '            <button type="button" id="btn-item-up-r-' + index + '-' + subIndex + '" style="margin-left: 100px;" class="layui-btn layui-btn-normal check-item-up"><i class="fa fa-arrow-up" aria-hidden="true"></i> 上移</button>';
    html += '            <button type="button" id="btn-item-down-r-' + index + '-' + subIndex + '" class="layui-btn layui-btn-normal check-item-up"><i class="fa fa-arrow-down" aria-hidden="true"></i> 下移</button>';
    html += '            <button type="button" id="btn-item-del-r-' + index + '-' + subIndex + '" class="layui-btn layui-btn-normal check-item-del"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>点检项目</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input style="width: 850px;" type="text" name="name"  autocomplete="off" placeholder="请输入点检项目" class="layui-input">';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>点检方法</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <textarea style="width: 850px;" class="layui-textarea" placeholder="请输入点检方法" name="method" ></textarea>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>判断标准</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <textarea style="width: 850px;" class="layui-textarea" placeholder="请输入判断标准" name="standard" ></textarea>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>检查结果</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input type="checkbox" name="result" lay-skin="primary" title="O" checked="checked" readonly>';
    html += '            <input type="checkbox" name="result" lay-skin="primary" title="X" checked="checked" readonly>';
    html += '            <input type="checkbox" class="this_one" name="result" lay-skin="primary" title="/">';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item" style="display: flex">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label">操作规程</label>';
    html += '          <div id="div-img-add-r-' + index + '-' + subIndex + '" class="layui-input-inline" style="width: auto;">';
    html += '          <input id="photoPath-r-' + index + '-' + subIndex + '" class="photoPath" type="hidden">';
    // html += '            <img id="' + index + '-' + subIndex + '" id="btn-img-add-' + index + '-' + subIndex + '" style="margin-top: 5px; width: 80px; height: 80px;" src=' + ctx +'/css/images/img_add.jpg>';
    html += '            <button type="button" class="layui-btn layui-btn-light-blue" id="btn-img-add-r-' + index + '-' + subIndex + '" style="margin-top: 5px; width: 80px; height: 80px;"><i class="layui-icon layui-icon-add-1"></i></button>';
    html += '              <i id="bulletTypeImgLoad-r-' + index + '-' + subIndex + '" class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"' +
        'style="display: none; font-size: 20px;"></i> <i id="bulletTypeImgYes-r-' + index + '-' + subIndex + '" class="layui-icon layui-icon-ok-circle"' +
        'style="display: none; color: #5FB878; font-size: 20px;" title="上传图片成功"></i> <i id="bulletTypeImgNo-r-' + index + '-' + subIndex + '" class="layui-icon layui-icon-close-fill"' +
        'style="display: none; color: #F96768; font-size: 20px;"title="上传图片失败"></i>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '    </form>';
    html += '  </div>';
    html += '</div>';
    return html;
}


//定期模版
function getClassHtmlD(checkClassIndex) {
    var html = '';
    html += '<div id="colla-item-d-' + checkClassIndex + '" class="layui-colla-item">';
    html += '  <h2 class="layui-colla-title">点检分类-' + checkClassIndex + '</h2>';
    html += '  <div class="layui-colla-content">';
    html += '    <form class="layui-form classForm">';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>点检分类</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input id="input-class-d-' + checkClassIndex + '" style="width: 650px;" required type="text" autocomplete="off" name="className" placeholder="请输入点检分类" class="layui-input">';
    html += '          </div>';
    html += '          <div style="margin-left: 810px;">';
    html += '            <button type="button" id="btn-item-add-d-' + checkClassIndex + '" class="layui-btn layui-btn-normal check-item-add"><i class="fa fa-plus-square" aria-hidden="true"></i> 添加点检项</button>';
    html += '            <button type="button" id="btn-class-del-d-' + checkClassIndex + '" class="layui-btn layui-btn-normal check-class-del"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-collapse" lay-accordion="" id="check-item-list-d-' + checkClassIndex + '">';
    html += '      </div>';
    html += '    </form>';
    html += '  </div>';
    html += '</div>';
    return html;
}

//定期项模版
function getItemHtmlD(index, subIndex) {
    var html = '';
    html += '<div id="colla-item-d-' + index + '-' + subIndex + '" class="layui-colla-item">';
    html += '  <h2 class="layui-colla-title">点检项-' + subIndex + '</h2>';
    html += '  <div class="layui-colla-content">';
    html += '    <form class="layui-form" action="">';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label">点检编号：</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input style="width: 300px;" type="text" value="1" readonly name="indexNum" autocomplete="off" class="layui-input">';
    html += '          </div>';
    html += '          <div style="margin-left: 680px;">';
    html += '            <button type="button" id="btn-item-up-d-' + index + '-' + subIndex + '" style="margin-left: 100px;" class="layui-btn layui-btn-normal check-item-up" ><i class="fa fa-arrow-up" aria-hidden="true"></i> 上移</button>';
    html += '            <button type="button" id="btn-item-down-d-' + index + '-' + subIndex + '" class="layui-btn layui-btn-normal check-item-down"><i class="fa fa-arrow-down" aria-hidden="true"></i> 下移</button>';
    html += '            <button type="button" id="btn-item-del-d-' + index + '-' + subIndex + '" class="layui-btn layui-btn-normal check-item-del"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>点检项目</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input style="width: 850px;" type="text" name="name"  autocomplete="off" placeholder="请输入点检项目" class="layui-input">';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>点检方法</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <textarea style="width: 850px;" class="layui-textarea" placeholder="请输入点检方法" name="method" ></textarea>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>判断标准</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <textarea style="width: 850px;" class="layui-textarea" placeholder="请输入判断标准" name="standard" ></textarea>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>检查结果</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input type="checkbox" name="result" lay-skin="primary" title="O" checked="checked" readonly>';
    html += '            <input type="checkbox" name="result" lay-skin="primary" title="X" checked="checked" readonly>';
    html += '            <input type="checkbox" class="this_one" name="result" lay-skin="primary" title="/">';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label">操作规程</label>';
    html += '          <div id="div-img-add-d-' + index + '-' + subIndex + '" class="layui-input-inline" style="width: auto;">';
    html += '          <input id="photoPath-d-' + index + '-' + subIndex + '" class="photoPath" type="hidden">';
    html += '            <button type="button" class="layui-btn layui-btn-light-blue" id="btn-img-add-d-' + index + '-' + subIndex + '" style="margin-top: 5px; width: 80px; height: 80px;"><i class="layui-icon layui-icon-add-1"></i></button>';
    html += '              <i id="bulletTypeImgLoad-d-' + index + '-' + subIndex + '" class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"' +
        'style="display: none; font-size: 20px;"></i> <i id="bulletTypeImgYes-d-' + index + '-' + subIndex + '" class="layui-icon layui-icon-ok-circle"' +
        'style="display: none; color: #5FB878; font-size: 20px;" title="上传图片成功"></i> <i id="bulletTypeImgNo-d-' + index + '-' + subIndex + '" class="layui-icon layui-icon-close-fill"' +
        'style="display: none; color: #F96768; font-size: 20px;"title="上传图片失败"></i> ';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '    </form>';
    html += '  </div>';
    html += '</div>';
    return html;
}

//保养模版
function getClassHtmlB(checkClassIndex) {
    var html = '';
    html += '<div id="colla-item-b-' + checkClassIndex + '" class="layui-colla-item">';
    html += '  <h2 class="layui-colla-title">保养分类-' + checkClassIndex + '</h2>';
    html += '  <div class="layui-colla-content">';
    html += '    <form class="layui-form classForm">';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>保养分类</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input id="input-class-b-' + checkClassIndex + '" style="width: 650px;" required type="text" autocomplete="off" name="className" placeholder="请输入点检分类" class="layui-input">';
    html += '          </div>';
    html += '          <div style="margin-left: 810px;">';
    html += '            <button type="button" id="btn-item-add-b-' + checkClassIndex + '" class="layui-btn layui-btn-normal check-item-add"><i class="fa fa-plus-square" aria-hidden="true"></i> 添加点检项</button>';
    html += '            <button type="button" id="btn-class-del-b-' + checkClassIndex + '" class="layui-btn layui-btn-normal check-class-del"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-collapse" lay-accordion="" id="check-item-list-b-' + checkClassIndex + '">';
    html += '      </div>';
    html += '    </form>';
    html += '  </div>';
    html += '</div>';
    return html;
}

//保养项模版
function getItemHtmlB(index, subIndex) {
    var html = '';
    html += '<div id="colla-item-b-' + index + '-' + subIndex + '" class="layui-colla-item">';
    html += '  <h2 class="layui-colla-title">保养项-' + subIndex + '</h2>';
    html += '  <div class="layui-colla-content">';
    html += '    <form class="layui-form" action="">';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label">保养编号：</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input style="width: 300px;" type="text" value="1" readonly name="indexNum" autocomplete="off" class="layui-input">';
    html += '          </div>';
    html += '          <div style="margin-left: 680px;">';
    html += '            <button type="button" id="btn-item-up-b-' + index + '-' + subIndex + '" style="margin-left: 100px;" class="layui-btn layui-btn-normal check-item-up" ><i class="fa fa-arrow-up" aria-hidden="true"></i> 上移</button>';
    html += '            <button type="button" id="btn-item-down-b-' + index + '-' + subIndex + '" class="layui-btn layui-btn-normal check-item-down"><i class="fa fa-arrow-down" aria-hidden="true"></i> 下移</button>';
    html += '            <button type="button" id="btn-item-del-b-' + index + '-' + subIndex + '" class="layui-btn layui-btn-normal check-item-del"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>保养项目</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input style="width: 850px;" type="text" name="name"  autocomplete="off" placeholder="请输入点检项目" class="layui-input">';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>保养方法</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <textarea style="width: 850px;" class="layui-textarea" placeholder="请输入保养方法" name="method" ></textarea>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>判断标准</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <textarea style="width: 850px;" class="layui-textarea" placeholder="请输入判断标准" name="standard" ></textarea>';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label"><font class="required-dot">*</font>检查结果</label>';
    html += '          <div class="layui-input-inline">';
    html += '            <input type="checkbox" name="result" lay-skin="primary" title="O" checked="checked" readonly>';
    html += '            <input type="checkbox" name="result" lay-skin="primary" title="X" checked="checked" readonly>';
    html += '            <input type="checkbox" class="this_one" name="result" lay-skin="primary" title="/">';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '      <div class="layui-form-item">';
    html += '        <div class="layui-inline">';
    html += '          <label class="layui-form-label">操作规程</label>';
    html += '          <div id="div-img-add-b-' + index + '-' + subIndex + '" class="layui-input-inline" style="width: auto;">';
    html += '          <input id="photoPath-b-' + index + '-' + subIndex + '" class="photoPath" type="hidden">';
    html += '            <button type="button" class="layui-btn layui-btn-light-blue" id="btn-img-add-b-' + index + '-' + subIndex + '" style="margin-top: 5px; width: 80px; height: 80px;"><i class="layui-icon layui-icon-add-1"></i></button>';
    html += '              <i id="bulletTypeImgLoad-b-' + index + '-' + subIndex + '" class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"' +
        'style="display: none; font-size: 20px;"></i> <i id="bulletTypeImgYes-b-' + index + '-' + subIndex + '" class="layui-icon layui-icon-ok-circle"' +
        'style="display: none; color: #5FB878; font-size: 20px;" title="上传图片成功"></i> <i id="bulletTypeImgNo-b-' + index + '-' + subIndex + '" class="layui-icon layui-icon-close-fill"' +
        'style="display: none; color: #F96768; font-size: 20px;"title="上传图片失败"></i> ';
    html += '          </div>';
    html += '        </div>';
    html += '      </div>';
    html += '    </form>';
    html += '  </div>';
    html += '</div>';
    return html;
}

(function () {
    var resizeTimer = null;
    // 重新渲染日历
    function rerenderCalendar () {
        if (resizeTimer) {
            clearTimeout(resizeTimer);
        }
        resizeTimer = setTimeout(function() {
            console.log('重新渲染日历。');
            if (!editMachineState.renderData || !r_check || !r_check.renderCalendar) return;
            r_check.renderCalendar(editMachineState.renderData);
        }, 200);
    }

    $(window).bind('resize', rerenderCalendar);
    $(window).bind('beforeunload', function (e) {
        $(window).unbind('resize', rerenderCalendar);
        interval = null;
    });
})();
