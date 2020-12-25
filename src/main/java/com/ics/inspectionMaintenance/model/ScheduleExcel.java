package com.ics.inspectionMaintenance.model;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

/**
 * @Classname ScheduleExcel
 * @Description TODO
 * @String 2020/11/7 15:03
 * @Created by yuankeyan
 */
@ContentRowHeight(13)
@HeadRowHeight(20)
public class ScheduleExcel {

    @ColumnWidth(30)
    private String date;

    @ColumnWidth(30)
    private String scheduleFlag;

    //备注
    @ColumnWidth(50)
    private String desc;

    public ScheduleExcel() {
    }

    public ScheduleExcel(String date, String scheduleFlag, String desc) {
        this.date = date;
        this.scheduleFlag = scheduleFlag;
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScheduleFlag() {
        return scheduleFlag;
    }

    public void setScheduleFlag(String scheduleFlag) {
        this.scheduleFlag = scheduleFlag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
