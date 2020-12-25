package com.ics.dataDesources.service;

import com.baomidou.mybatisplus.service.IService;
import com.ics.dataDesources.model.ImCalendarData;
import com.ics.dataDesources.model.InspectionMaintainSchedule;
import com.ics.dataDesources.model.InspectionMaintainSetSchedule;
import com.ics.inspectionMaintenance.model.ImResultForSchedule;

import java.text.ParseException;
import java.util.Map;

/**
 * @Description 点检保养排班
 * @Date 2020/10/26 22:26
 * @Author by yuankeyan
 */
public interface InspectionMaintainScheduleService extends IService<InspectionMaintainSchedule> {


    /**
     * @Description 保存排班数据
     * @Date 2020/10/30 20:32
     * @Author by yuankeyan
     */
    void saveSchedules(ImCalendarData imCalendarData);

    /**
     * @Description 根据开始结束时间、模版id、设备id获取排班数据
     * @Date 2020/10/30 21:01
     * @Author by yuankeyan
     */
    Map<String, ImResultForSchedule> getSchedules(ImCalendarData imCalendarData);

    /**
     * @Description 保存排班设定
     * @Date 2020/11/1 09:29
     * @Author by yuankeyan
     */
    void saveSetSchedule(InspectionMaintainSetSchedule setSchedule) throws ParseException;

    /**
     * 根据设备和模版id获取排班设定数据
     *
     * @return
     */
    InspectionMaintainSetSchedule getSetSchedule(String templateId, String machineId);
}
