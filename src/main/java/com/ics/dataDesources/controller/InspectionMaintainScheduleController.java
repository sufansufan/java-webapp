package com.ics.dataDesources.controller;

import com.ics.dataDesources.model.ImCalendarData;
import com.ics.dataDesources.model.InspectionMaintainSetSchedule;
import com.ics.dataDesources.service.InspectionMaintainScheduleService;
import com.ics.inspectionMaintenance.model.ImResultForSchedule;
import com.ics.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Description 点检保养排班
 * @Date 2020/10/30 21:37
 * @Author by yuankeyan
 */
@Controller
@RequestMapping("/dataDesources/inspectionMaintainSchedule")
public class InspectionMaintainScheduleController {

    protected static final String setScheduleJsp = "views/dataDesources/machineManager/setSchedule";

    @Autowired
    private InspectionMaintainScheduleService scheduleService;


    @RequiresPermissions(value = "set_schedule")
    @RequestMapping(value = "/setSchedule", method = RequestMethod.GET)
    public ModelAndView add(String templateId, String machineId) {
        ModelAndView mav = new ModelAndView(setScheduleJsp);
        InspectionMaintainSetSchedule setSchedule = scheduleService.getSetSchedule(templateId, machineId);
        mav.addObject("templateId", templateId);
        mav.addObject("machineId", machineId);
        mav.addObject("setSchedule", setSchedule);
        return mav;
    }


    /**
     * @Description 保存点检、保养日历的排班数据
     * @Date 2020/10/30 21:10
     * @Author by yuankeyan
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonResult save(@RequestBody ImCalendarData imCalendarData) {
        JsonResult jsonResult = new JsonResult();
        try {
            scheduleService.saveSchedules(imCalendarData);
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setFaild();
        }
        return jsonResult;
    }

    /**
     * @Description 获取点检、保养的排班日历数据
     * @Date 2020/10/30 21:11
     * @Author by yuankeyan
     */
    @ResponseBody
    @RequestMapping(value = "/getSchedules", method = RequestMethod.POST)
    public JsonResult getSchedules(@RequestBody ImCalendarData imCalendarData) {
        JsonResult jsonResult = new JsonResult();
        try {
            Map<String, ImResultForSchedule> scheduleMap = scheduleService.getSchedules(imCalendarData);
            jsonResult.setData(scheduleMap);
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            jsonResult.setFaild();
        }
        return jsonResult;
    }


    /**
     * 保存排班设定
     * @param setSchedule
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveSetSchedule", method = RequestMethod.POST)
    public JsonResult saveSetSchedule(@RequestBody InspectionMaintainSetSchedule setSchedule) {
        JsonResult jsonResult = new JsonResult();
        try {
            scheduleService.saveSetSchedule(setSchedule);
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            jsonResult.setFaild();
        }
        return jsonResult;
    }



}
