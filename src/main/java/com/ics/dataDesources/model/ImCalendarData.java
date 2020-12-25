package com.ics.dataDesources.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;

/**
 * @Classname 点检排班、日常排班、保养排班，存，查对象数据模版
 * @Description TODO
 * @Date 2020/10/30 20:26
 * @Created by yuankeyan
 */
public class ImCalendarData {
    /**
     * 日历值
     * map 中key为日期字符串，value为是否排班 true/false
     */
    private Map<String,Boolean> scheduleMap;
    /**
     * 模版id
     */
    private String templateId;
    /**
     * 设备id
     */
    private String machineId;

    /**
     * 日历中的最大日期
     * 因为每月日期的开始和结束未必是本月的日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",locale = "zh" , timezone="GMT+8")
    private Date maxDate;
    /**
     * 日历中的最小日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",locale = "zh" , timezone="GMT+8")
    private Date minDate;

    /**
     * 模版类型
     * r 日常 d 定期 b 保养
     */
    private String templateIdType;

    public Map<String, Boolean> getScheduleMap() {
        return scheduleMap;
    }

    public void setScheduleMap(Map<String, Boolean> scheduleMap) {
        this.scheduleMap = scheduleMap;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public String getTemplateIdType() {
        return templateIdType;
    }

    public void setTemplateIdType(String templateIdType) {
        this.templateIdType = templateIdType;
    }
}
