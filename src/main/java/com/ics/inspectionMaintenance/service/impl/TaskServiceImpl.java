package com.ics.inspectionMaintenance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.model.*;
import com.ics.dataDesources.service.*;
import com.ics.inspectionMaintenance.mapper.ExceptionFixMapper;
import com.ics.inspectionMaintenance.mapper.ExceptionMapper;
import com.ics.inspectionMaintenance.mapper.TaskMapper;
import com.ics.inspectionMaintenance.model.*;
import com.ics.inspectionMaintenance.model.Exception;
import com.ics.inspectionMaintenance.service.ExceptionFixService;
import com.ics.inspectionMaintenance.service.ExceptionService;
import com.ics.inspectionMaintenance.service.InspectionMaintainItemResultService;
import com.ics.inspectionMaintenance.service.TaskService;
import com.ics.system.model.SysTeam;
import com.ics.system.model.SysTeamMember;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysTeamMemberService;
import com.ics.system.service.SysTeamService;
import com.ics.system.service.SysUserService;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private InspectionMaintainItemResultService itemResultService;

    @Autowired
    private InspectionMaintainScheduleService inspectionMaintainScheduleService;

    @Autowired
    private InspectionMaintainTemplateService inspectionMaintainTemplateService;

    @Autowired
    private InspectionMaintainClassService inspectionMaintainClassService;

    @Autowired
    private InspectionMaintainItemService inspectionMaintainItemService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ControlMachineService controlMachineService;

    @Autowired
    private SysTeamMemberService teamMemberService;

    @Autowired
    private ExceptionMapper exceptionMapper;

    @Autowired
    private ExceptionFixMapper exceptionFixMapper;

    @Autowired
    private SysTeamService sysTeamService;


    @Autowired
    private InspectionMaintainItemResultService  maintainItemResultService;

    @Override
    public Page<Task> selectIMTaskByMachineId(Page<Task> page, @Param("ew") Wrapper<Task> wrapper ) {

        return page.setRecords(taskMapper.selectIMTaskByMachineId(page, wrapper));
    }


    @Override
    public Task buildIMTaskDetail(String taskId) {

        Task task = taskMapper.selectById(taskId);
        List<InspectionMaintainItemResult> itemResults = getInspectionMaintainItemResults(task);
        List<String> itemResultItemIds = itemResults.stream().map(InspectionMaintainItemResult::getId).collect(Collectors.toList());

        EntityWrapper<ExceptionFix> exceptionFixEntityWrapper = new EntityWrapper<>();
        exceptionFixEntityWrapper.in("ir.id",itemResultItemIds);
        List<ExceptionFix> exceptionFixes = exceptionFixMapper.selectExceptionFixWithItemResultIds(exceptionFixEntityWrapper);
        Map<String, ExceptionFix> itrmResultMapByResultId = MapUtils.listToMapByKey(exceptionFixes, ExceptionFix.class, "itrmResultId");


        //得到itemId和itemResult映射
        Map<String, List<InspectionMaintainItemResult>> itemResultsGroupByItemId =
                itemResults.stream().collect(Collectors.groupingBy(InspectionMaintainItemResult::getMachineItemId));

        InspectionMaintainTemplate template = getTemp(task.getSourceId());
        String tempLateId = template.getId();
        EntityWrapper<InspectionMaintainClass> ew = new EntityWrapper<>();
        ew.eq("template_id", tempLateId);
        List<InspectionMaintainClass> templateClass = inspectionMaintainClassService.selectList(ew);
        List<String> tempLateClassIds =  templateClass.stream().map(InspectionMaintainClass::getId).collect(Collectors.toList());


        EntityWrapper<InspectionMaintainItem> itemEntityWrapper = new EntityWrapper<>();
        itemEntityWrapper.in("class_id",tempLateClassIds);
        List<InspectionMaintainItem> itemList = inspectionMaintainItemService.selectList(itemEntityWrapper);
        Map<String, List<InspectionMaintainItem>> itemGroupByClassId =
                itemList.stream().collect(Collectors.groupingBy(InspectionMaintainItem::getClassId));



        itemList.stream().forEach(item ->{
            String itemId = item.getId();
            List<InspectionMaintainItemResult> tempItemsResults =  itemResultsGroupByItemId.get(itemId);
            if(tempItemsResults != null && !tempItemsResults.isEmpty()){
                InspectionMaintainItemResult inspectionMaintainItemResult = tempItemsResults.get(0);
                item.setItemResult(inspectionMaintainItemResult);
                String itemResultId = inspectionMaintainItemResult.getId();
                if(!CollectionUtils.isEmpty(itrmResultMapByResultId) && Objects.nonNull(itrmResultMapByResultId.get(itemResultId))){
                    inspectionMaintainItemResult.setExceptionFix(itrmResultMapByResultId.get(itemResultId));
                }
            }
        });

        templateClass.stream().forEach(tempClass -> {
            String classId = tempClass.getId();
            if(itemGroupByClassId.containsKey(classId)){
                tempClass.setItemList(itemGroupByClassId.get(classId));
            };
        });

        template.setClassList(templateClass);

        task.setInspectionMaintainTemplate(template);

        return  task;
    }

    @Override
    public Task getFullFieldTask(String taskId){
        Task task = taskMapper.selectById(taskId);
        if(Objects.isNull(task)){
            return null;
        }
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        fillTaskEmptyFields(tasks);
        return task;
    }

    @Override
    public void fillTaskEmptyFields(List<Task> availableTasks) {
        EntityWrapper<SysUser> sysUserEntityWrapper = new EntityWrapper<>();
        List<SysUser> userList = sysUserService.selectList(sysUserEntityWrapper);
        Map<String, SysUser> userMap = new HashMap<>();
        userList.stream().forEach(user -> {
            String userId = user.getId();
            userMap.put(userId,user);
        });
        availableTasks.stream().forEach(availableTask ->{

            String admitUserId = availableTask.getAdmitUserId();
            String optionUserId = availableTask.getOperatorUserId();
            String confirmUserId = availableTask.getConfirmUserId();
            String rejectUserId = availableTask.getRejectUserId();

            if(!StringUtils.isEmpty(admitUserId)){
                String userName = userMap.get(admitUserId).getUserName();
                availableTask.setAdmitUserName(userName);
            }

            if(!StringUtils.isEmpty(optionUserId)){
                String userName = userMap.get(optionUserId).getUserName();
                availableTask.setOperatorUserName(userName);
            }

            if(!StringUtils.isEmpty(confirmUserId)){
                String userName = userMap.get(confirmUserId).getUserName();
                availableTask.setConfirmUserName(userName);
            }

            if(!StringUtils.isEmpty(rejectUserId)){
                String userName = userMap.get(rejectUserId).getUserName();
                availableTask.setRejectUserName(userName);
            }
        } );
    }

    private InspectionMaintainTemplate getTemp(String scheduleId) {
        InspectionMaintainSchedule schedule = inspectionMaintainScheduleService.selectById(scheduleId);
        String templateId = schedule.getTemplateId();
        InspectionMaintainTemplate template = inspectionMaintainTemplateService.selectById(templateId);
        return template;
    }

    private List<InspectionMaintainItemResult> getInspectionMaintainItemResults(Task task) {
        EntityWrapper<InspectionMaintainItemResult> itemResultEntityWrapper = new EntityWrapper<>();
        itemResultEntityWrapper.in("task_id", task.getId());
        List<InspectionMaintainItemResult> itemResults = itemResultService.selectList(itemResultEntityWrapper);
        return itemResults;
    }

    @Override
    public Page<Task> selectPage(Page<Task> pager, EntityWrapper<Task> ew){
        List<Task> tasks = taskMapper.selectTaskAllByPage(pager, ew);
        if(tasks.isEmpty()){
            return pager;
        }
        List<String> machineIds = tasks.stream().map(Task::getMachineId).collect(Collectors.toList());

        Wrapper<ControlMachine> controlMachineEntityWrapper = new EntityWrapper<>();
        controlMachineEntityWrapper.in("a.id", machineIds);
        List<ControlMachine> controlMachines = controlMachineService.selectRelationList(controlMachineEntityWrapper);

        Map<String, ControlMachine> machineMap = MapUtils.listToMapByKey(controlMachines, ControlMachine.class, "id");

        tasks.stream().forEach( task -> {
                String machineId = task.getMachineId();
                task.setControlMachine(machineMap.get(machineId));
            }
        );
        pager.setRecords(tasks);
        return pager;
    }

    @Override
    public List<TaskOverview> selectToBeCompleted(EntityWrapper<Task> ew, boolean isLeader, SysUser user) {
        List<TaskOverview> result = new ArrayList<>();
        List<Task> tasks = taskMapper.selectTeamTaskByUserId(ew);
        for (Task task : tasks) {
            task.setType(rebuildTaskType(task.getType()));
        }
        List<SysTeam> sysTeams = sysTeamService.selectList(new EntityWrapper<>());
        Map<String, SysTeam> teamMap = MapUtils.listToMapByKey(sysTeams, SysTeam.class, "id");

        Map<String, List<Task>> taskMapByTeamId = MapUtils.listToMapListByKey(tasks, Task.class, "teamId");
        if(isLeader){
            fillAllTeamsTaskMap(taskMapByTeamId, sysTeams);
        }else{
            EntityWrapper<SysTeamMember> umew = new EntityWrapper<SysTeamMember>();
            umew.eq("user_id",user.getId());
            SysTeamMember sysTeamMember = teamMemberService.selectOne(umew);
            SysTeam sysTeam = teamMap.get(sysTeamMember.getTeamId());
            List<SysTeam> list = new ArrayList<>();
            list.add(sysTeam);
            fillAllTeamsTaskMap(taskMapByTeamId, list);
        }

        for (String teamId:taskMapByTeamId.keySet()) {
            TaskOverview taskOverview = new TaskOverview();
            List<Task> tasksTeam = taskMapByTeamId.get(teamId);
            SysTeam sysTeam = teamMap.get(teamId);
            taskOverview.setTeamName(sysTeam.getName());
            taskOverview.setOrgName(sysTeam.getOrgName());
            taskOverview.setTeamId(teamId);
            taskOverview.setOrgCode(sysTeam.getOrgId());
            Map<String, List<Task>> typeMapType = MapUtils.listToMapListByKey(tasksTeam, Task.class, "type");

            List<TaskOverviewItem> taskOverviewItems =  new ArrayList<>();
            for (String key: typeMapType.keySet()) {
                TaskOverviewItem taskOverviewItem = new TaskOverviewItem();
                taskOverviewItem.setTypeCode(key);
                taskOverviewItem.setTypeName(getTypeName(key));

                List<Task> list = typeMapType.get(key);
                Map<String, List<Task>> taskGroupByStatusMap = getEmptyTaskMapGroupByStatus();
                list.stream().forEach(task -> {
                    String status = task.getStatus();
                    if(ConstantProperty.TASK_STATUS_1.equals(status)||ConstantProperty.TASK_STATUS_2.equals(status)){
                        taskGroupByStatusMap.get("tb_complete").add(task);
                    }else if(ConstantProperty.TASK_STATUS_5.equals(status)){
                        taskGroupByStatusMap.get("completed").add(task);
                    }else if(ConstantProperty.TASK_STATUS_3.equals(status)||ConstantProperty.TASK_STATUS_4.equals(status)){
                        taskGroupByStatusMap.get("tb_confirm").add(task);
                    }
                });
                taskOverviewItem.setCompletedCount(taskGroupByStatusMap.get("completed").size());
                taskOverviewItem.setToBeComplete(taskGroupByStatusMap.get("tb_complete").size());
                taskOverviewItem.setToBeConfirm(taskGroupByStatusMap.get("tb_confirm").size());
                taskOverviewItem.setTotalCount(list.size());
                taskOverviewItems.add(taskOverviewItem);
                fileEmptyTypeTaskOverViews(taskOverviewItems);
                taskOverview.setTaskOverviewItems(taskOverviewItems);
            }
            if(CollectionUtils.isEmpty(tasksTeam)){
                fileEmptyTypeTaskOverViews(taskOverviewItems);
                taskOverview.setTaskOverviewItems(taskOverviewItems);
            }
            result.add(taskOverview);
        }

        return result;
    }

    private void fillAllTeamsTaskMap(Map<String, List<Task>> taskMapByTeamId, List<SysTeam> sysTeams) {
        for (SysTeam sysTeam : sysTeams) {
            String teamId = sysTeam.getId();
            if(!taskMapByTeamId.containsKey(teamId)){
                taskMapByTeamId.put(teamId, new ArrayList<>());
            }
        }
    }

    private void fileEmptyTypeTaskOverViews(List<TaskOverviewItem> taskOverviewItems) {
        // D , E ,R ,M
        Set<String> typeSet = new HashSet<>();
        typeSet.add("D");
        typeSet.add("E");
        typeSet.add("R");
        typeSet.add("M");

        taskOverviewItems.stream().forEach(taskOverviewItem -> {
            typeSet.remove(taskOverviewItem.getTypeCode());
        });

        typeSet.stream().forEach(s -> {
            TaskOverviewItem taskOverviewItem = new TaskOverviewItem();
            taskOverviewItem.setTypeCode(s);
            taskOverviewItem.setTypeName(getTypeName(s));
            taskOverviewItem.setCompletedCount(0);
            taskOverviewItem.setToBeComplete(0);
            taskOverviewItem.setToBeConfirm(0);
            taskOverviewItem.setTotalCount(0);
            taskOverviewItems.add(taskOverviewItem);
        });
    }

    @Override
    public TaskOverviewForWebKanban getKanbanOverview(EntityWrapper<Task> ew){
        TaskOverviewForWebKanban taskOverview = new TaskOverviewForWebKanban();
        List<Task> tasks = taskMapper.selectTaskIncludeTeamId(ew);
        EntityWrapper<SysTeam> teamEw = new EntityWrapper<>();
        List<SysTeam> sysTeams = sysTeamService.selectList(teamEw);
//        Map<String, SysTeam> teamMap = MapUtils.listToMapByKey(sysTeams, SysTeam.class, "id");
        Map<String, List<Task>> taskGroupByTeamId = MapUtils.listToMapListByKey(tasks, Task.class, "teamId");
         int completedCount = 0;
         int tbCompleteCount = 0;
         int tbConfirmCount = 0;
         if(CollectionUtils.isEmpty(taskGroupByTeamId)){
             List<TaskOverviewItem> list = new ArrayList<>();
             TaskOverviewItem taskOverviewItem = getEmptyTaskOverviewItem();
             list.add(taskOverviewItem);
             taskOverview.setTaskOverviewItems(list);
         }else{
             for (String key: taskGroupByTeamId.keySet()) {
//                 SysTeam sysTeam = teamMap.get(key);
                 List<Task> tempTasks = taskGroupByTeamId.get(key);
                 List<TaskOverviewItem> taskOverviews = getTaskOverviews(tempTasks);
                 TaskOverviewItem currentTypeTask = taskOverviews.get(0);
//                 currentTypeTask.setTeamName(sysTeam.getName());
//                 currentTypeTask.setTeamId(sysTeam.getId());
                 completedCount = currentTypeTask.getCompletedCount() + completedCount;
                 tbCompleteCount = currentTypeTask.getToBeComplete()+ tbCompleteCount;
                 tbConfirmCount = currentTypeTask.getToBeConfirm() + tbConfirmCount;
                 taskOverview.setTaskOverviewItems(taskOverviews);
             }
         }
        taskOverview.setCompletedCount(completedCount);
        taskOverview.setTbCompleteCount(tbCompleteCount);
        taskOverview.setTbConfirmCount(tbConfirmCount);
        DecimalFormat df=new DecimalFormat("0.00");
        int allTaskCount = tasks.size();
        float completedRate = allTaskCount != 0 ? (float)completedCount/allTaskCount : 0f;
        float tbCompletedRate = allTaskCount != 0 ? (float)tbCompleteCount/allTaskCount : 0f;
        float tbConfirmRate = allTaskCount != 0 ? (float)tbConfirmCount/allTaskCount : 0f;
        taskOverview.setTbConfirmRate(tbCompletedRate);
        taskOverview.setTbConfirmRate(tbConfirmRate);
        taskOverview.setCompletedRate(completedRate);

        return taskOverview;
    }

    @Override
    public List<TaskOverviewAboutMachineItem> getKanbanOverviewAboutMachine(EntityWrapper<Task> ew){
        List<Task> tasks = taskMapper.selectTaskIncludeTeamId(ew);
        List<TaskOverviewAboutMachineItem> taskOverviewAboutMachineItems = new ArrayList<>();
        Map<String, List<Task>> taskGroupByMachineId = MapUtils.listToMapListByKey(tasks, Task.class, "machineId");
        List<ControlMachine> controlMachines = controlMachineService.selectRelationList(new EntityWrapper<>());
        Map<String, ControlMachine> machineMap = new HashMap<>();
        for (ControlMachine controlMachine:controlMachines) {
            machineMap.put(controlMachine.getId(),controlMachine);
        }
        for (String key: taskGroupByMachineId.keySet()) {
            List<Task> tempTasks = taskGroupByMachineId.get(key);
            TaskOverviewAboutMachineItem taskOverviews = getTaskOverviewsGroupByMachine(tempTasks, machineMap, key);
            taskOverviewAboutMachineItems.add(taskOverviews);
        }

        fillAllMachineDate(machineMap, taskOverviewAboutMachineItems);

        return taskOverviewAboutMachineItems;
    }

    @Override
    public List<TaskForApp> getTaskListForApp(EntityWrapper<TaskForApp> ew){

        List<TaskForApp> taskForApps = taskMapper.selectTaskForApp(ew);

        return taskForApps;
    }

    @Override
    public List<KanbanTask> getKanbanTaskList(EntityWrapper<Task> ew, boolean isLeader, SysUser user) {
        List<Task> allTasks = taskMapper.selectTeamTaskByUserId(ew);
        List<SysTeam> allTeams = sysTeamService.selectTeamList(new EntityWrapper<>());
        Map<String, SysTeam> teamMap = MapUtils.listToMapByKey(allTeams, SysTeam.class, "id");
        Map<String, List<Task>> taskGroupByTeamId = MapUtils.listToMapListByKey(allTasks, Task.class, "teamId");
        if(isLeader){
            fillEmpTaskGroupByTeamId(allTeams,taskGroupByTeamId);
        }else{
            EntityWrapper<SysTeamMember> umew = new EntityWrapper<SysTeamMember>();
            umew.eq("user_id",user.getId());
            SysTeamMember sysTeamMember = teamMemberService.selectOne(umew);
            SysTeam sysTeam = teamMap.get(sysTeamMember.getTeamId());
            List<SysTeam> list = new ArrayList<>();
            list.add(sysTeam);
            fillEmpTaskGroupByTeamId(list,taskGroupByTeamId);
        }
        List<KanbanTask> result = new ArrayList<>();
        for (String teamId: taskGroupByTeamId.keySet()) {
            KanbanTask kanbanTask = new KanbanTask();
            kanbanTask.setOrgName(teamMap.get(teamId).getOrgName());
            kanbanTask.setTeamName(teamMap.get(teamId).getName());
            kanbanTask.setTeamId(teamId);
            List<Task> tasks = taskGroupByTeamId.get(teamId);
            tasks.stream().forEach(
                    task -> {
                        task.setType(rebuildTaskType(task.getType()));
                    }
            );
            Map<String, List<Task>> taskGroupByType= MapUtils.listToMapListByKey(tasks, Task.class, "type");
            fillAllTypeOfTask(taskGroupByType);
            List<KanbanTaskItem> kanbanTaskItems = new ArrayList<>();
            for (String type: taskGroupByType.keySet()) {
                KanbanTaskItem kanbanTaskItem = new KanbanTaskItem();
                String typeName = getTypeName(type);
                kanbanTaskItem.setTypeName(typeName);
                kanbanTaskItem.setTypeCode(type);
                int finishCount = 0;
                int unFinishCount = 0;
                List<Task> tempTasks = taskGroupByType.get(type);
                for (Task task:tempTasks) {
                    if(ConstantProperty.TASK_STATUS_5.equals(task.getStatus())){
                        finishCount = finishCount + 1;
                    }else{
                        unFinishCount = unFinishCount + 1;
                    }
                }
                kanbanTaskItem.setCompletedCount(finishCount);
                kanbanTaskItem.setUnfinishedCount(unFinishCount);
                float finishRate = 0.00f;
                float unFinishRate = 0.00f;
                int total = tempTasks.size();
                if(total > 0){
                    DecimalFormat df = new DecimalFormat("0.00");
                    String finishCountStr = df.format((float) finishCount / total);
                    String unFinishCountCountStr = df.format((float) unFinishCount / total);
                    finishRate = Float.parseFloat(finishCountStr);
                    unFinishRate = Float.parseFloat(unFinishCountCountStr);
                }
                kanbanTaskItem.setCompletedRate(finishRate);
                kanbanTaskItem.setUnfinishedRate(unFinishRate);
                kanbanTaskItem.setTotalCount(total);
                kanbanTaskItems.add(kanbanTaskItem);
                kanbanTask.setKanbanTaskItems(kanbanTaskItems);
            }
            result.add(kanbanTask);
        }
        return result;
    }

    private void fillEmpTaskGroupByTeamId(List<SysTeam> allTeams, Map<String, List<Task>> taskGroupByTeamId) {
        allTeams.stream().forEach(t ->{
            String id = t.getId();
            if(!taskGroupByTeamId.containsKey(id)){
                taskGroupByTeamId.put(id, new ArrayList<>());
            }
        } );
    }

    private void fillAllTypeOfTask(Map<String, List<Task>> taskGroupByType) {

        if(!taskGroupByType.containsKey(ConstantProperty.TASK_TYPE_DAILY)) {
            taskGroupByType.put(ConstantProperty.TASK_TYPE_DAILY, new ArrayList<>());
        }

        if(!taskGroupByType.containsKey(ConstantProperty.TASK_TYPE_MAINTAIN)) {
            taskGroupByType.put(ConstantProperty.TASK_TYPE_MAINTAIN, new ArrayList<>());
        }

        if(!taskGroupByType.containsKey(ConstantProperty.TASK_TYPE_REGULARLY)) {
            taskGroupByType.put(ConstantProperty.TASK_TYPE_REGULARLY, new ArrayList<>());
        }

        if(!taskGroupByType.containsKey(ConstantProperty.TASK_TYPE_ALL_EXCEPTION)) {
            taskGroupByType.put(ConstantProperty.TASK_TYPE_ALL_EXCEPTION, new ArrayList<>());
        }
    }

    private void fillAllMachineDate(Map<String, ControlMachine> machineMap, List<TaskOverviewAboutMachineItem> taskOverviewAboutMachineItems) {
        Map<String, TaskOverviewAboutMachineItem> map = MapUtils.listToMapByKey(taskOverviewAboutMachineItems, TaskOverviewAboutMachineItem.class,"id");
        List<TaskOverviewAboutMachineItem> list = new ArrayList<>();
        for (String key:machineMap.keySet()) {
            if(!map.containsKey(key)){
                TaskOverviewAboutMachineItem taskOverviewAboutMachineItem = getEmptyTaskOverviewAboutMachjineItem();
                taskOverviewAboutMachineItem.setControlMachine(machineMap.get(key));
                list.add(taskOverviewAboutMachineItem);
            }

        }
        taskOverviewAboutMachineItems.addAll(list);
    }

    private TaskOverviewAboutMachineItem getEmptyTaskOverviewAboutMachjineItem() {
        TaskOverviewAboutMachineItem taskOverview = new TaskOverviewAboutMachineItem();
        taskOverview.setCompletedCount(0);
        taskOverview.setToBeComplete(0);
        taskOverview.setToBeConfirm(0);
        return taskOverview;
    }


    private TaskOverviewItem getEmptyTaskOverviewItem() {
        TaskOverviewItem taskOverviewItem = new TaskOverviewItem();
        taskOverviewItem.setTotalCount(0);
        taskOverviewItem.setToBeConfirm(0);
        taskOverviewItem.setToBeComplete(0);
        taskOverviewItem.setCompletedCount(0);
        return taskOverviewItem;
    }

    private List<TaskOverviewItem> getTaskOverviews(List<Task> tasks) {
        Map<String, List<Task>> taskGroupByTybeMap = new HashMap<>();
        resetTaskType(tasks, taskGroupByTybeMap);


        List<TaskOverviewItem> taskOverviews = new ArrayList<>();
        for (String key: taskGroupByTybeMap.keySet()) {
            TaskOverviewItem taskOverview = new TaskOverviewItem();
            taskOverview.setTypeCode(key);
            taskOverview.setTypeName(getTypeName(key));

            List<Task> list = taskGroupByTybeMap.get(key);
            Map<String, List<Task>> taskGroupByStatusMap = getEmptyTaskMapGroupByStatus();
            list.stream().forEach(task -> {
                String status = task.getStatus();
                if(ConstantProperty.TASK_STATUS_1.equals(status)||ConstantProperty.TASK_STATUS_2.equals(status)){
                    taskGroupByStatusMap.get("tb_complete").add(task);
                }else if(ConstantProperty.TASK_STATUS_5.equals(status)){
                    taskGroupByStatusMap.get("completed").add(task);
                }else if(ConstantProperty.TASK_STATUS_3.equals(status)||ConstantProperty.TASK_STATUS_4.equals(status)){
                    taskGroupByStatusMap.get("tb_confirm").add(task);
                }
            });
            taskOverview.setCompletedCount(taskGroupByStatusMap.get("completed").size());
            taskOverview.setToBeComplete(taskGroupByStatusMap.get("tb_complete").size());
            taskOverview.setToBeConfirm(taskGroupByStatusMap.get("tb_confirm").size());
            taskOverview.setTotalCount(list.size());
            taskOverviews.add(taskOverview);
        }
        EntityWrapper<SysTeam> teamEw = new EntityWrapper<>();
        List<SysTeam> sysTeams = sysTeamService.selectList(teamEw);
        fillTaskOverViews(sysTeams, taskOverviews);
        return taskOverviews;
    }

    private void resetTaskType(List<Task> tasks, Map<String, List<Task>> taskGroupByTybeMap) {
        tasks.stream().forEach(task -> {
            String type = rebuildTaskType(task.getType());
            if(taskGroupByTybeMap.containsKey(type)){
                taskGroupByTybeMap.get(type).add(task);
            }else{
                List<Task> list = new ArrayList<>();
                list.add(task);
                taskGroupByTybeMap.put(type,list);
            }
        });
    }

    private void fillTaskOverViews(List<SysTeam>  sysTeams, List<TaskOverviewItem> taskOverviews) {
        Map<String, TaskOverviewItem> taskOverviewItemMap = MapUtils.listToMapByKey(taskOverviews, TaskOverviewItem.class, "teamId");
        List<TaskOverviewItem> list = new ArrayList<>();
        sysTeams.stream().forEach(team ->{
            String teamId = team.getId();
            if(!taskOverviewItemMap.containsKey(teamId)){
                TaskOverviewItem item = getEmptyTaskOverviewItem();
//                item.setTeamId(team.getId());
//                item.setTeamName(team.getName());
                list.add(item);
            }
        });
        taskOverviews.addAll(list);
    }

    private TaskOverviewAboutMachineItem getTaskOverviewsGroupByMachine(List<Task> tasks, Map<String, ControlMachine> machineMap, String machineId) {
        TaskOverviewAboutMachineItem taskOverviewAboutMachineItem = new TaskOverviewAboutMachineItem();
        taskOverviewAboutMachineItem.setControlMachine(machineMap.get(machineId));
        Map<String, List<Task>> taskGroupByStatusMap = getEmptyTaskMapGroupByStatus();
        tasks.stream().forEach(task -> {
            String status = task.getStatus();
            if(ConstantProperty.TASK_STATUS_1.equals(status)||ConstantProperty.TASK_STATUS_2.equals(status)){
                taskGroupByStatusMap.get("tb_complete").add(task);
            }else if(ConstantProperty.TASK_STATUS_5.equals(status)){
                taskGroupByStatusMap.get("completed").add(task);
            }else if(ConstantProperty.TASK_STATUS_3.equals(status)||ConstantProperty.TASK_STATUS_4.equals(status)){
                taskGroupByStatusMap.get("tb_confirm").add(task);
            }
        });

        taskOverviewAboutMachineItem.setCompletedCount(taskGroupByStatusMap.get("completed").size());
        taskOverviewAboutMachineItem.setToBeComplete(taskGroupByStatusMap.get("tb_complete").size());
        taskOverviewAboutMachineItem.setToBeConfirm(taskGroupByStatusMap.get("tb_confirm").size());
        return taskOverviewAboutMachineItem;
    }

    private Map<String, List<Task>> getEmptyTaskMapGroupByStatus() {
        Map<String,List<Task>> result = new HashMap<>();
        result.put("completed",new ArrayList<>());
        result.put("tb_complete",new ArrayList<>());
        result.put("tb_confirm",new ArrayList<>());
        return result;
    }

    private String rebuildTaskType(String oldTask){
        if(oldTask.equals(ConstantProperty.TASK_TYPE_BREAK_OUT_EXCEPTION)||oldTask.equals(ConstantProperty.TASK_TYPE_DAILY_INSPECTION_EXCEPTION)
                ||oldTask.equals(ConstantProperty.TASK_TYPE_MAINTAIN_EXCEPTION)||oldTask.equals(ConstantProperty.TASK_TYPE_REGULAR_INSPECTION_EXCEPTION)){
            return ConstantProperty.TASK_TYPE_ALL_EXCEPTION;
        }else{
            return oldTask;
        }
    }


    private String getTypeName(String taskType){
        if(ConstantProperty.TASK_TYPE_ALL_EXCEPTION.equals(taskType)){
            return ConstantProperty.TASK_TYPE_ALL_EXCEPTION_NAME;
        }else if(ConstantProperty.TASK_TYPE_MAINTAIN.equals(taskType)){
            return ConstantProperty.TASK_TYPE_MAINTAIN_NAME;
        }else if(ConstantProperty.TASK_TYPE_REGULARLY.equals(taskType)){
            return ConstantProperty.TASK_TYPE_REGULARLY_NAME;
        }else if(ConstantProperty.TASK_TYPE_DAILY.equals(taskType)){
            return ConstantProperty.TASK_TYPE_DAILY_name;
        }else{
            return "";
        }
    }


    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public boolean updateTaskStatus(String taskIds, String taskStatus, SysUser user, String rejectDesc) {
        String[] taskIdArray = StringUtils.split(taskIds,",");
        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.in("id",taskIdArray);
        List<Task> oldTasks = taskMapper.selectList(ew);
        if(!verifyUpdateStatus(oldTasks, taskStatus)){
            return false;
        }
        oldTasks.stream().forEach(task -> {
            task.setLastModifiedTime(new Date());
            if(ConstantProperty.TASK_STATUS_5.equals(taskStatus)){//5.  待确认（4） -> 待确认（5）
                task.setStatus(taskStatus);
                task.setAdmitUserId(user.getId());
                if(isIMTask(task)){ //如果是点检保养，在承认的同时会产生新的exception Task和exception
                    addIMExceptionToExceptionAndTask(task);
                }
            }else if(ConstantProperty.TASK_STATUS_2.equals(taskStatus)){//2.  待确认（3） -> 被驳回（2） 或 待承认（4） -> 被驳回（2）
                task.setStatus(taskStatus);
                task.setRejectUserId(user.getId());
                task.setRejectDate(new Date());
                task.setRejectDesc(rejectDesc);
                task.setIsAvailable(ConstantProperty.IS_AVAILABLE_0);

                Task newTask = new Task();
                newTask.setId(CommonUtil.getRandomUUID());
                newTask.setIsAvailable(ConstantProperty.IS_AVAILABLE_1);
                newTask.setBeforeTaskId(task.getId());
                newTask.setSourceId(task.getSourceId());
                newTask.setType(task.getType());
                newTask.setStatus(ConstantProperty.TASK_STATUS_2);
                newTask.setStartTime(task.getStartTime());
//                newTask.setRejectDate(new Date());
//                newTask.setRejectDesc(rejectDesc);
//                newTask.setRejectUserId(user.getId());
                if(!StringUtils.isEmpty(rejectDesc)){
                    newTask.setRejectDesc(rejectDesc);
                }
                newTask.setMachineId(task.getMachineId());

                taskMapper.insert(newTask);
            }else if(ConstantProperty.TASK_STATUS_4.equals(taskStatus)){//4.  待确认（3） -> 待承认（4）
                task.setConfirmUserId(user.getId());
                task.setConfirmDate(new Date());
                task.setStatus(taskStatus);
            }
            taskMapper.updateById(task);
        });
        return true;
    }

    private boolean isIMTask(Task task) {
        String taskType = task.getType();
        if(ConstantProperty.TASK_TYPE_DAILY.equals(taskType) || ConstantProperty.TASK_TYPE_MAINTAIN.equals(taskType) ||ConstantProperty.TASK_TYPE_REGULARLY.equals(taskType)){
            return true;
        }
        return false;
    }

    private boolean verifyUpdateStatus(List<Task> tasks, String taskStatus) {
        boolean flag = true;
        for (Task task: tasks) {
            String currentStatus = task.getStatus();

            if("2".equals(currentStatus) && ("3".equals(taskStatus))){
                continue;
            }else if("3".equals(currentStatus) && ("2".equals(taskStatus) || "4".equals(taskStatus))){//待确认状态只能到被驳回或者待承认状态
                continue;
            }else if("4".equals(currentStatus) && ("2".equals(taskStatus) || "5".equals(taskStatus))){
                continue;
            }else{
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void addIMExceptionToExceptionAndTask(Task task) {
        EntityWrapper<InspectionMaintainItemResult> ew = new EntityWrapper<InspectionMaintainItemResult>();
        ew.eq("task_id",task.getId() );
        ew.eq("result","failure" );//只查询检查不通过的
        List<InspectionMaintainItemResult> itemResultsByScheduleIds = maintainItemResultService.getItemResultsByScheduleIds(ew);


        itemResultsByScheduleIds.stream().forEach(inspectionMaintainItemResult -> {
            Exception newException = addAndReturnNewException(inspectionMaintainItemResult,task);
            addExceptionTask(newException, task);
        });
    }

    private void addExceptionTask(Exception newException, Task oldTask) {
        Task task = new Task();
        task.setMachineId(newException.getMachineId());
        task.setId(CommonUtil.getRandomUUID());
        task.setStatus(ConstantProperty.TASK_STATUS_1);
        task.setType(newException.getExceptionType());
        task.setStartTime(new Date());
        task.setSourceId(newException.getId());
        task.setIsAvailable(ConstantProperty.IS_AVAILABLE_1);
        task.setMachineId(newException.getMachineId());
        task.setBeforeTaskId(oldTask.getId());
        taskMapper.insert(task);
    }

    private Exception addAndReturnNewException(InspectionMaintainItemResult inspectionMaintainItemResult, Task task) {
        Exception exception = new Exception();
        String desc = inspectionMaintainItemResult.getDesc();
        String sourceId = inspectionMaintainItemResult.getId();
        String urls = inspectionMaintainItemResult.getResultImgUrls();
        String machineId = inspectionMaintainItemResult.getMachineId();

        exception.setId(CommonUtil.getRandomUUID());
        exception.setDescribe(desc);
        exception.setCheckoutUserId(task.getOperatorUserId());
        exception.setSourceId(sourceId);
        exception.setExceptionType(getType(inspectionMaintainItemResult.getType()));
        exception.setExceptionImgUrls(urls);
        exception.setCreatedTime(new Date());
        exception.setMachineId(machineId);

        exceptionMapper.insert(exception);

        return exception;
    }

    private String getType(String iMType){
        if(ConstantProperty.TASK_TYPE_DAILY.equals(iMType)){
            return ConstantProperty.TASK_TYPE_DAILY_INSPECTION_EXCEPTION;
        }else if(ConstantProperty.TASK_TYPE_REGULARLY.equals(iMType)){
            return ConstantProperty.TASK_TYPE_REGULAR_INSPECTION_EXCEPTION;
        }else if(ConstantProperty.TASK_TYPE_MAINTAIN.equals(iMType)){
            return ConstantProperty.TASK_TYPE_MAINTAIN_EXCEPTION;
        }
        return null;
    }

    @Override
    public Page<WorkOrderOverview> taskOverviewList(Page<WorkOrderOverview> pager, EntityWrapper<WorkOrderOverview> ew){
        List<WorkOrderOverview> exceptionTaskByImTaskIds = taskMapper.taskOverviewList(pager, ew);
        List<String> itemResultItemIds = exceptionTaskByImTaskIds.stream().map(WorkOrderOverview::getTaskId).collect(Collectors.toList());
        EntityWrapper<ExceptionTaskCalculateModel> exceptionTaskCalculateModelEntityWrapper = new EntityWrapper<>();
        exceptionTaskCalculateModelEntityWrapper.in("imTask.id", itemResultItemIds);
        List<ExceptionTaskCalculateModel> exceptionTaskCalculateModels = taskMapper.getExceptionTaskByImTaskIds(exceptionTaskCalculateModelEntityWrapper);
        Map<String, List<ExceptionTaskCalculateModel>> exceptionTaskCalculateModeMapByLimTaskId = MapUtils.listToMapListByKey(exceptionTaskCalculateModels, ExceptionTaskCalculateModel.class, "imTaskId");
        for (WorkOrderOverview workOrderOverview : exceptionTaskByImTaskIds ) {
            String taskId = workOrderOverview.getTaskId();
            workOrderOverview.setStatusName(getTaskStatusName(workOrderOverview.getStatus()));
            workOrderOverview.setTypeName(getTypeName(workOrderOverview.getType()));
            if(exceptionTaskCalculateModeMapByLimTaskId.containsKey(taskId)){
                List<ExceptionTaskCalculateModel> modelList = exceptionTaskCalculateModeMapByLimTaskId.get(taskId);
                int fixCount = 0;
                for (ExceptionTaskCalculateModel exceptionTaskCalculateModel : modelList) {
                    String status = exceptionTaskCalculateModel.getExceptionTaskStatus();
                    if(ConstantProperty.TASK_STATUS_5.equals(status)){
                        fixCount = fixCount + 1;
                    }
                }
                workOrderOverview.setExceptionFixCount(fixCount);
                workOrderOverview.setExceptionCount(modelList.size());
            }else{
                workOrderOverview.setExceptionCount(0);
                workOrderOverview.setExceptionFixCount(0);
            }
        }
        pager.setRecords(exceptionTaskByImTaskIds);
        return pager;
    }

    private String getTaskStatusName(String statusCode){
        String statusName = "";
        if(ConstantProperty.TASK_STATUS_1.equals(statusCode)){
            statusName = ConstantProperty.TASK_STATUS_NAME_1;
        }else if(ConstantProperty.TASK_STATUS_2.equals(statusCode)){
            statusName = ConstantProperty.TASK_STATUS_NAME_2;
        }else if(ConstantProperty.TASK_STATUS_3.equals(statusCode)){
            statusName = ConstantProperty.TASK_STATUS_NAME_3;
        }else if(ConstantProperty.TASK_STATUS_4.equals(statusCode)){
            statusName = ConstantProperty.TASK_STATUS_NAME_4;
        }else if(ConstantProperty.TASK_STATUS_5.equals(statusCode)){
            statusName = ConstantProperty.TASK_STATUS_NAME_5;
        }
return statusName;
    }

}
