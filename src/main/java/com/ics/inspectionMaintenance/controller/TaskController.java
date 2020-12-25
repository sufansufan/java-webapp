package com.ics.inspectionMaintenance.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.inspectionMaintenance.model.InspectionMaintainItemResult;
import com.ics.inspectionMaintenance.model.ResultSummary;
import com.ics.inspectionMaintenance.model.Task;
import com.ics.inspectionMaintenance.model.WorkOrderOverview;
import com.ics.inspectionMaintenance.service.InspectionMaintainItemResultService;
import com.ics.inspectionMaintenance.service.TaskService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysUserService;
import com.ics.utils.ConstantProperty;
import com.ics.utils.DateUtils;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 点检结果
 *
 * @author jjz
 *
 */
@Controller
@RequestMapping("/inspectionMaintenance/imResult")
public class TaskController {

    protected static final String listJSP = "views/dataDesources/machineManager/imList";
    protected static final String detailJSP = "views/dataDesources/machineManager/imDetail";
    protected static final String toBeAdmitJSP = "views/inspectionMaintenance/toBeAdmit/index";
    protected static final String completionJSP = "views/inspectionMaintenance/completion/index";

    @Autowired
    private TaskService taskService;

    @Autowired
    private ControlMachineService controlMachineService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private InspectionMaintainItemResultService inspectionMaintainItemResultService;

    @ResponseBody
    @RequestMapping(value = "/updateTaskStatus", method = RequestMethod.POST)
    public JsonResult updateTaskStatus(HttpServletRequest request, HttpSession session, String taskIds, String taskStatus, String rejectDesc ) {
        JsonResult result = new JsonResult();
        SysUser user = sysUserService.getByUserCode(session.getAttribute("userCode").toString());

        boolean success = taskService.updateTaskStatus(taskIds, taskStatus, user, rejectDesc);
        if (!success){
            result.setFaildMsg("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, String id) {
        ModelAndView mav = new ModelAndView(listJSP);
        ControlMachine machine = controlMachineService.selectById(id);
        List<ControlMachine> controlMachines = new ArrayList<>();
        controlMachines.add(machine);
        controlMachineService.fillMachineEmptyField(controlMachines);
        mav.addObject("machineId", id);
        mav.addObject("machine", controlMachines.get(0));
        return mav;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(HttpServletRequest request, String taskId) {
        ModelAndView mav = new ModelAndView(detailJSP);
        mav.addObject("taskId", taskId);
        return mav;
    }

    /**
     * @Description 待承认列表页面
     * @Date 2020/11/12 18:32
     * @Author by yuankeyan
     */
    @RequestMapping(value = "/toBeAdmitIndex", method = RequestMethod.GET)
    public ModelAndView toBeAdmitIndex(HttpServletRequest request, String taskId) {
        ModelAndView mav = new ModelAndView(toBeAdmitJSP);
        return mav;
    }

    /**
     * @Description 待承认列表页面
     * @Date 2020/11/12 18:32
     * @Author by yuankeyan
     */
    @RequestMapping(value = "/completionIndex", method = RequestMethod.GET)
    public ModelAndView completionIndex(HttpServletRequest request, String taskId) {
        ModelAndView mav = new ModelAndView(completionJSP);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/getToBeAdmitList", method = RequestMethod.GET)
    public PagingBean getToBeAdmitList(HttpServletRequest request, String id, int page, int limit) {
        Page<Task> pager = new Page<>(page, limit);
        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.eq("t.status",ConstantProperty.TASK_STATUS_4);
        ew.eq("t.is_available",ConstantProperty.IS_AVAILABLE_1);
        pager = taskService.selectPage(pager, ew);
        PagingBean pageBean = PagingBean.page(page ,limit , pager.getTotal());
        pageBean.setData(pager.getRecords());
        return pageBean;
    }


    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PagingBean getIMResultTempList(HttpServletRequest request, String startDate, String endDate, String type, String id, int page, int limit) {
        Page<Task> pager = new Page<>(page, limit);
        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.eq("sc.machine_id",id);
        if(!StringUtils.isEmpty(startDate)){
            Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
            ew.gt("start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        }

        if(!StringUtils.isEmpty(endDate)){
            Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));
            ew.lt("start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        }

        if(!StringUtils.isEmpty(type)){
            ew.eq("t.type", type);
        }else{
            ew.in("t.type", new String[]{"D","R","M"});
        }
        ew.eq("t.is_available",ConstantProperty.IS_AVAILABLE_1);
        pager = taskService.selectIMTaskByMachineId(pager, ew);


        //获得所有排班Id
        List<String> scheduleIds=  pager.getRecords().stream().map(Task::getSourceId).collect(Collectors.toList());
        //获得所有scheduleIds有关的itemResult.

        EntityWrapper<InspectionMaintainItemResult> ew1 = new EntityWrapper<InspectionMaintainItemResult>();
        ew1.in("schedule_id",scheduleIds );
        List<InspectionMaintainItemResult> itemResultItems = inspectionMaintainItemResultService.getItemResultsByScheduleIds(ew1);

        //按照scheduleId分组
        Map<String, List<InspectionMaintainItemResult>> itemResultsGroupByScheduleId =
                itemResultItems.stream().collect(Collectors.groupingBy(InspectionMaintainItemResult::getScheduleId));



        Map<String, ResultSummary> schedule2ResultSummaryMap = new HashMap<>();
        itemResultsGroupByScheduleId.keySet().forEach(key -> {
            List<InspectionMaintainItemResult> list = itemResultsGroupByScheduleId.get(key);
            Map<String, Integer> summaryMap = new HashMap<>();
            list.stream().forEach(e -> {
                String result = e.getResult();
                if(summaryMap.containsKey(result)){
                    summaryMap.put(result, summaryMap.get(e.getResult()) +1) ;
                }else{
                    summaryMap.put(e.getResult(), 1);
                }
            });
            ResultSummary summary = new ResultSummary();
            summary.setSuccessCount(summaryMap.get("success")==null?0:summaryMap.get("success"));
            summary.setFailureCount(summaryMap.get("failure")==null?0:summaryMap.get("failure"));
            summary.setNoCheckCount(summaryMap.get("noCheck")==null?0:summaryMap.get("noCheck"));

            schedule2ResultSummaryMap.put(key, summary);

        });
//
        pager.getRecords().stream().forEach(e ->{
            String scheduleId = e.getSourceId();
            if (schedule2ResultSummaryMap.containsKey(scheduleId)) {
                e.setResultSummary(schedule2ResultSummaryMap.get(scheduleId));
            } else {
                e.setResultSummary(new ResultSummary(0, 0, 0));
            }
        });

        taskService.fillTaskEmptyFields(pager.getRecords());

        PagingBean pageBean = PagingBean.page(page ,limit , pager.getTotal());
        pageBean.setData(pager.getRecords());
         return pageBean;
    }

    @ResponseBody
    @RequestMapping(value = "/taskOverviewList", method = RequestMethod.GET)
    public PagingBean taskOverviewList(HttpServletRequest request, int page, int limit, String startDate, String endDate, String status, String teamId, String machineName) {
        Page<WorkOrderOverview> pager = new Page<>(page, limit);

        EntityWrapper<WorkOrderOverview> ew = new EntityWrapper<>();
        if(!StringUtils.isEmpty(startDate)){
            Date startTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(startDate,"yyyy-MM-dd"));
            ew.gt("t.start_time", DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        }

        if(!StringUtils.isEmpty(endDate)){
            Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(endDate,"yyyy-MM-dd"));
            ew.lt("t.start_time", DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        }

        if(StringUtils.isNotEmpty(status)){
            ew.eq("t.status",status);
        }

        if(StringUtils.isNotEmpty(teamId)){
            ew.eq("cm.team_id",teamId);
        }

        if(StringUtils.isNotEmpty(machineName)){
            ew.like("cm.machine_name", machineName);
        }
        ew.eq("t.is_available",1);
        pager = taskService.taskOverviewList(pager, ew);
        PagingBean pageBean = PagingBean.page(page ,limit , pager.getTotal());
        pageBean.setData(pager.getRecords());
        return pageBean;
    }
}
