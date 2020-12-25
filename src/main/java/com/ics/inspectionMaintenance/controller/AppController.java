package com.ics.inspectionMaintenance.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.inspectionMaintenance.model.*;
import com.ics.inspectionMaintenance.service.*;
import com.ics.system.model.SysTeamMember;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysTeamMemberService;
import com.ics.system.service.SysUserService;
import com.ics.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 点检结果
 *
 * @author jjz
 *
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SysTeamMemberService sysTeamMemberService;

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    private InspectionMaintainItemResultService inspectionMaintainItemResultService;

    @Autowired
    private IMService imService;

    @Autowired
    private ExceptionFixService exceptionFixService;

    @ResponseBody
    @RequestMapping(value = "/index/toBeCompleted", method = RequestMethod.GET)
    public JsonResult getIMResultTempList(HttpServletRequest request, HttpSession session) {
        JsonResult result = new JsonResult();
        if(Objects.isNull(session.getAttribute("userCode")) || Objects.isNull(session.getAttribute("roleCode"))){
            result.setFaildMsg("用户信息错误");
            return result;
        }
        String roleCode = session.getAttribute("roleCode").toString();

        EntityWrapper<Task> ew = new EntityWrapper<>();
        Date current =  new Date();

        Date endTime1 = DateUtils.add(current, Calendar.DAY_OF_MONTH, 3);
        Date startTime1 = DateUtils.add(current, Calendar.DAY_OF_MONTH, -3);
        Date endTime = DateUtils.setMaxTimeForDate(endTime1);
        Date startTime = DateUtils.setMinTimeForDate(startTime1);
        ew.ge("start_time",DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time",DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        boolean isLeader = true;
        String userCode = session.getAttribute("userCode").toString();
        SysUser user = sysUserService.getByUserCode(userCode);
        if(!ConstantProperty.DEPARTMENT_LEADER_ROLECODE.equals(roleCode)){
            ew.eq("sm.user_id",user.getId());
            isLeader = false;
        }
        List<TaskOverview> taskOverviews = taskService.selectToBeCompleted(ew, isLeader, user);
        result.setData(taskOverviews);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/index/kanbanTask", method = RequestMethod.GET)
    public JsonResult kanbanTask(HttpServletRequest request, HttpSession session, String queryType) {
        JsonResult result = new JsonResult();
        if(Objects.isNull(session.getAttribute("userCode")) || Objects.isNull(session.getAttribute("roleCode"))){
            result.setFaildMsg("用户信息错误");
            return result;
        }
        String roleCode = session.getAttribute("roleCode").toString();

        EntityWrapper<Task> ew = new EntityWrapper<>();
        Date current =  new Date();
        Date startTime;
        Date endTime;
        if(ConstantProperty.CURRENT_YEAR.equals(queryType)){
            endTime = DateUtils.setMaxTimeForYearDate(current);
            startTime = DateUtils.setMinTimeForYearDate(current);
        }else if(ConstantProperty.CURRENT_MONTH.equals(queryType)){
            endTime = DateUtils.setMaxTimeForMonthDate(current);
            startTime = DateUtils.setMinTimeForMonthDate(current);
        }else if(ConstantProperty.CURRENT_SEASON.equals(queryType)){
            startTime = DateUtils.getCurrentQuarterStartTime();
            endTime = DateUtils.getCurrentQuarterEndTime();
        }else{
            endTime = DateUtils.setMaxTimeForDate(current);
            startTime = DateUtils.setMinTimeForDate(current);
        }
        ew.ge("start_time",DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time",DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
        ew.eq("is_available", 1);

        String userCode = session.getAttribute("userCode").toString();
        SysUser user = sysUserService.getByUserCode(userCode);
        boolean isLeader = true;
        if(!ConstantProperty.DEPARTMENT_LEADER_ROLECODE.equals(roleCode)){
            isLeader = false;
            ew.eq("sm.user_id",user.getId());
        }
        List<KanbanTask> kanbanTaskList = taskService.getKanbanTaskList(ew, isLeader, user);
        result.setData(kanbanTaskList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/task/toBeCompletedList", method = RequestMethod.GET)
    public JsonResult toBeCompletedList(HttpServletRequest request, HttpSession session, String teamId) {
        JsonResult result = new JsonResult();
        if(Objects.isNull(session.getAttribute("userCode")) || Objects.isNull(session.getAttribute("roleCode"))){
            result.setFaildMsg("用户信息错误");
            return result;
        }
        String roleCode = session.getAttribute("roleCode").toString();

        EntityWrapper<TaskForApp> ew = new EntityWrapper<>();
        ew.eq("is_available", 1);
        //如果是组长，则返回待处理，被驳回，待确认任务
        //如果是组员，则返回待处理，待驳回任务
        if(ConstantProperty.TEAM_MAMBER_ROLECODE.equals(roleCode)){
            String userCode = session.getAttribute("userCode").toString();
            SysUser byUserCode = sysUserService.getByUserCode(userCode);
            EntityWrapper<SysTeamMember> ewTM = new EntityWrapper<>();
            ewTM.eq("user_id", byUserCode.getId());
            SysTeamMember teamMember = sysTeamMemberService.selectOne(ewTM);

            ew.eq("st.id",teamMember.getTeamId());
            ew.in("t.status", new String[]{ConstantProperty.TASK_STATUS_1,ConstantProperty.TASK_STATUS_2});
        }else{
            ew.eq("st.id",teamId);
            ew.in("t.status", new String[]{ConstantProperty.TASK_STATUS_1,ConstantProperty.TASK_STATUS_2,ConstantProperty.TASK_STATUS_3});
        }

        List<TaskForApp> taskListForApp = taskService.getTaskListForApp(ew);
        result.setData(taskListForApp);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/task/allTask", method = RequestMethod.GET)
    public JsonResult allTask(HttpServletRequest request, HttpSession session, String teamId, String machineId, String month) {
        JsonResult result = new JsonResult();
        if(Objects.isNull(session.getAttribute("userCode")) || Objects.isNull(session.getAttribute("roleCode"))){
            result.setFaildMsg("用户信息错误");
            return result;
        }
        String roleCode = session.getAttribute("roleCode").toString();

        EntityWrapper<TaskForApp> ew = new EntityWrapper<>();
        ew.eq("is_available", 1);

        if(!StringUtils.isEmpty(machineId)){
            ew.eq("t.machine_id", machineId);
        }
        //如果是组长，则返回待处理，被驳回，待确认任务
        //如果是组员，则返回待处理，待驳回任务
        if(ConstantProperty.TEAM_MAMBER_ROLECODE.equals(roleCode)){
            String userCode = session.getAttribute("userCode").toString();
            SysUser byUserCode = sysUserService.getByUserCode(userCode);
            EntityWrapper<SysTeamMember> ewTM = new EntityWrapper<>();
            ewTM.eq("user_id", byUserCode.getId());
            SysTeamMember teamMember = sysTeamMemberService.selectOne(ewTM);

            ew.eq("st.id",teamMember.getTeamId());
        }else{
            ew.eq("st.id",teamId);
        }
        Date current = new Date();
        Date endTime;
        Date startTime;
        if(!StringUtils.isEmpty(month)){
            Date monthDate = DateUtils.StringToDate(month,"yyyy-MM");
            endTime = DateUtils.setMaxTimeForMonthDate(monthDate);
            startTime = DateUtils.setMinTimeForMonthDate(monthDate);
        }else{
            endTime = DateUtils.setMaxTimeForMonthDate(current);
            startTime = DateUtils.setMinTimeForMonthDate(current);
        }

        ew.ge("start_time",DateUtils.DateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
        ew.lt("start_time",DateUtils.DateToString(endTime,"yyyy-MM-dd HH:mm:ss"));

        List<TaskForApp> taskListForApp = taskService.getTaskListForApp(ew);
        result.setData(taskListForApp);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/task/exceptionDetail", method = RequestMethod.GET)
    public JsonResult getExceptionDetail(HttpServletRequest request, String taskId) {
        JsonResult result = new JsonResult();
        ExceptionDetail exceptionTaskList = exceptionService.getExceptionDetail(taskId);
        result.setData(exceptionTaskList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/task/uploadIMResult", method = RequestMethod.POST)
    public JsonResult uploadIMResult(HttpServletRequest request,  HttpSession session, String taskId, String imResult) {
        JsonResult result = new JsonResult();
        if(Objects.isNull(session.getAttribute("userCode")) || Objects.isNull(session.getAttribute("roleCode"))){
            result.setFaildMsg("用户信息错误");
            return result;
        }

        JSONArray imResultJson = JSONArray.parseArray(imResult);
        if(imResultJson.size() == 0){
            return result;
        }
        Task task = taskService.selectById(taskId);
        List<HashMap> list = JSON.parseArray(imResult, HashMap.class);
        List<InspectionMaintainItemResult> itemResultList = new ArrayList<>();
        for (HashMap map : list) {
            InspectionMaintainItemResult inspectionMaintainItemResult = new InspectionMaintainItemResult();
            inspectionMaintainItemResult.setCreateTime(new Date());
            inspectionMaintainItemResult.setModifyTime(new Date());
            inspectionMaintainItemResult.setScheduleId(task.getSourceId());
            inspectionMaintainItemResult.setId(CommonUtil.getRandomUUID());
            inspectionMaintainItemResult.setType(task.getType());
            inspectionMaintainItemResult.setMachineId(task.getMachineId());
            if(map.containsKey("itemId")){
                inspectionMaintainItemResult.setMachineItemId(map.get("itemId").toString());
            }
            if(map.containsKey("meterData")){
                inspectionMaintainItemResult.setMeterData(map.get("meterData").toString());
            }
            if(map.containsKey("result")){
                inspectionMaintainItemResult.setResult(map.get("result").toString());
            }
            if(map.containsKey("desc")){
                inspectionMaintainItemResult.setDesc(map.get("desc").toString());
            }
            if(map.containsKey("imgUrls")){
                inspectionMaintainItemResult.setResultImgUrls(map.get("imgUrls").toString());
            }
            inspectionMaintainItemResult.setTaskId(taskId);
            itemResultList.add(inspectionMaintainItemResult);
        }
        boolean b = inspectionMaintainItemResultService.insertBatch(itemResultList);
        if(!b){
            result.setFaildMsg("保存点检结果失败");
        }

        SysUser userCode = sysUserService.getByUserCode(session.getAttribute("userCode").toString());
        String userId = userCode.getId();
        task.setStatus(ConstantProperty.TASK_STATUS_3);
        task.setOperatorUserId(userId);
        task.setOperateDate(new Date());
        taskService.updateById(task);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/im/getImDetail", method = RequestMethod.GET)
    public JsonResult getImDetail(HttpServletRequest request, String taskId) {
        JsonResult result = new JsonResult();
        ImDetail imDetail = imService.buildImDetail(taskId);
        result.setData(imDetail);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/task/handleException", method = RequestMethod.POST)
    public JsonResult handleException(HttpServletRequest request, String taskId,
                                      String handlerUserCode, String  handlerDesc,String  handlerImgs) {
        JsonResult result = new JsonResult();
        Task task = taskService.selectById(taskId);
        String sourceId = task.getSourceId();
        SysUser user = sysUserService.getByUserCode(handlerUserCode);

        ExceptionFix exceptionFix = new ExceptionFix();
        exceptionFix.setId(CommonUtil.getRandomUUID());
        exceptionFix.setDescribe(handlerDesc);
        exceptionFix.setFixImgUrls(handlerImgs);
        exceptionFix.setExceptionId(sourceId);
        boolean insertFlag = exceptionFixService.insert(exceptionFix);
        if(!insertFlag){
            result.setFaildMsg("保存失败");
        }
        task.setStatus(ConstantProperty.TASK_STATUS_3);
        task.setOperatorUserId(user.getId());
        task.setLastModifiedTime(new Date());
        taskService.updateById(task);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/task/confirm", method = RequestMethod.POST)
    public JsonResult taskConfirm(HttpServletRequest request, String taskIds,  HttpSession session) {

        JsonResult result = new JsonResult();
        SysUser user = sysUserService.getByUserCode(session.getAttribute("userCode").toString());
        if(Objects.isNull(session.getAttribute("userCode")) || Objects.isNull(session.getAttribute("roleCode"))){
            result.setFaildMsg("用户信息错误");
            return result;
        }
        boolean successFlag = taskService.updateTaskStatus(taskIds, ConstantProperty.TASK_STATUS_4, user, null);
        if(!successFlag){
            result.setFaildMsg("确认任务失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/task/rejectException", method = RequestMethod.POST)
    public JsonResult rejectException(HttpServletRequest request, String taskIds,  HttpSession session, String rejectDesc) {

        JsonResult result = new JsonResult();
        SysUser user = sysUserService.getByUserCode(session.getAttribute("userCode").toString());
        if(Objects.isNull(session.getAttribute("userCode")) || Objects.isNull(session.getAttribute("roleCode"))){
            result.setFaildMsg("用户信息错误");
            return result;
        }
        boolean successFlag = taskService.updateTaskStatus(taskIds, ConstantProperty.TASK_STATUS_2, user, rejectDesc);
        if(!successFlag){
            result.setFaildMsg("确认任务失败");
        }
        return result;
    }
}
