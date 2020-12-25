package com.ics.inspectionMaintenance.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.inspectionMaintenance.model.Task;
import com.ics.inspectionMaintenance.model.TaskOverviewAboutMachineItem;
import com.ics.inspectionMaintenance.model.TaskOverviewForWebKanban;
import com.ics.inspectionMaintenance.service.TaskService;
import com.ics.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 点检结果
 *
 * @author jjz
 *
 */
@Controller
@RequestMapping("/inspectionMaintenance/enterpriseKanban")
public class EnterpriseKanbanController {


    @Autowired
    private TaskService taskService;


    @ResponseBody
    @RequestMapping(value = "/getExceptionOverview", method = RequestMethod.GET)
    public JsonResult getToBeAdmitList(HttpServletRequest request, String startDate, String endDate) {

        Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
        Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));

        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.gt("start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        ew.eq("is_available",1);
        ew.in("type", new String[]{ConstantProperty.TASK_TYPE_MAINTAIN_EXCEPTION,ConstantProperty.TASK_TYPE_REGULAR_INSPECTION_EXCEPTION,ConstantProperty.TASK_TYPE_DAILY_INSPECTION_EXCEPTION,ConstantProperty.TASK_TYPE_BREAK_OUT_EXCEPTION});
        TaskOverviewForWebKanban exceptionKanbanOverview = taskService.getKanbanOverview(ew);
        JsonResult result = new JsonResult();
        result.setData(exceptionKanbanOverview);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getRegularOverview", method = RequestMethod.GET)
    public JsonResult getRegularOverview(HttpServletRequest request, String startDate, String endDate) {

        Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
        Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));

        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.gt("start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        ew.eq("is_available",1);
        ew.eq("type", ConstantProperty.TASK_TYPE_REGULARLY);
        TaskOverviewForWebKanban taskServiceKanbanOverview = taskService.getKanbanOverview(ew);
        taskServiceKanbanOverview.setDisplayType(ConstantProperty.TASK_TYPE_REGULARLY);
        JsonResult result = new JsonResult();
        result.setData(taskServiceKanbanOverview);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getDillyOverview", method = RequestMethod.GET)
    public JsonResult getDillyOverview(HttpServletRequest request, String startDate, String endDate) {

        Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
        Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));

        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.gt("start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        ew.eq("is_available",1);
        ew.eq("type", ConstantProperty.TASK_TYPE_DAILY);
        TaskOverviewForWebKanban taskServiceKanbanOverview = taskService.getKanbanOverview(ew);
        taskServiceKanbanOverview.setDisplayType(ConstantProperty.TASK_TYPE_DAILY);
        JsonResult result = new JsonResult();
        result.setData(taskServiceKanbanOverview);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getMaintainOverview", method = RequestMethod.GET)
    public JsonResult getMaintainOverview(HttpServletRequest request, String startDate, String endDate) {

        Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
        Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));

        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.gt("start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        ew.eq("is_available",1);
        ew.eq("type", ConstantProperty.TASK_TYPE_MAINTAIN);
        TaskOverviewForWebKanban taskServiceKanbanOverview = taskService.getKanbanOverview(ew);
        taskServiceKanbanOverview.setDisplayType(ConstantProperty.TASK_TYPE_MAINTAIN);
        JsonResult result = new JsonResult();
        result.setData(taskServiceKanbanOverview);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getDillyOverviewByMachine", method = RequestMethod.GET)
    public JsonResult getDillyOverviewByMachine(HttpServletRequest request, String startDate, String endDate) {

        Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
        Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));

        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.gt("start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        ew.eq("is_available",1);
        ew.eq("type", ConstantProperty.TASK_TYPE_DAILY);
        List<TaskOverviewAboutMachineItem> kanbanOverviewAboutMachine = taskService.getKanbanOverviewAboutMachine(ew);
        JsonResult result = new JsonResult();
        result.setData(kanbanOverviewAboutMachine);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getRegularOverviewByMachine", method = RequestMethod.GET)
    public JsonResult getRegularOverviewByMachine(HttpServletRequest request, String startDate, String endDate) {

        Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
        Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));

        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.gt("start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        ew.eq("is_available",1);
        ew.eq("type", ConstantProperty.TASK_TYPE_REGULARLY);
        List<TaskOverviewAboutMachineItem> kanbanOverviewAboutMachine = taskService.getKanbanOverviewAboutMachine(ew);
        JsonResult result = new JsonResult();
        result.setData(kanbanOverviewAboutMachine);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getMaintainOverviewByMachine", method = RequestMethod.GET)
    public JsonResult getMaintainOverviewByMachine(HttpServletRequest request, String startDate, String endDate) {

        Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
        Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));

        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.gt("start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        ew.eq("is_available",1);
        ew.eq("type", ConstantProperty.TASK_TYPE_MAINTAIN);
        List<TaskOverviewAboutMachineItem> kanbanOverviewAboutMachine = taskService.getKanbanOverviewAboutMachine(ew);
        JsonResult result = new JsonResult();
        result.setData(kanbanOverviewAboutMachine);

        return result;
    }



}
