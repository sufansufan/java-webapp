package com.ics.dataDesources.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.InspectionMaintainScheduleMapper;
import com.ics.dataDesources.mapper.InspectionMaintainSetScheduleMapper;
import com.ics.dataDesources.mapper.InspectionMaintainTemplateMapper;
import com.ics.dataDesources.model.ImCalendarData;
import com.ics.dataDesources.model.InspectionMaintainSchedule;
import com.ics.dataDesources.model.InspectionMaintainSetSchedule;
import com.ics.dataDesources.model.InspectionMaintainTemplate;
import com.ics.dataDesources.service.InspectionMaintainScheduleService;
import com.ics.inspectionMaintenance.mapper.InspectionMaintainItemResultMapper;
import com.ics.inspectionMaintenance.mapper.TaskMapper;
import com.ics.inspectionMaintenance.model.ImResultForCount;
import com.ics.inspectionMaintenance.model.ImResultForSchedule;
import com.ics.inspectionMaintenance.model.Task;
import com.ics.inspectionMaintenance.service.InspectionMaintainItemResultService;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("inspectionMaintainScheduleService")
public class InspectionMaintainScheduleServiceImpl extends ServiceImpl<InspectionMaintainScheduleMapper, InspectionMaintainSchedule> implements InspectionMaintainScheduleService {

    @Autowired
    private InspectionMaintainScheduleMapper inspectionMaintainScheduleMapper;

    @Autowired
    private InspectionMaintainSetScheduleMapper setScheduleMapper;

    @Autowired
    private InspectionMaintainItemResultMapper inspectionMaintainItemResultMapper;

    @Autowired
    private InspectionMaintainTemplateMapper inspectionMaintainTemplateMapper;

    @Autowired
    private TaskMapper taskMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSchedules(ImCalendarData imCalendarData) {
        //删除本月从今天（不含）至日历页中最大日期的数据
        Date now = new Date();
        String templateId = imCalendarData.getTemplateId();
        String machineId = imCalendarData.getMachineId();
        //修改task状态
        Map<String, Object> param = new HashMap<>();
        param.put("templateId", templateId);
        param.put("machineId", machineId);
        param.put("startDate", now);
        param.put("endDate", imCalendarData.getMaxDate());
        taskMapper.updateTaskStatusBySchedule(param);

        EntityWrapper<InspectionMaintainSchedule> scheduleEw = new EntityWrapper<>();
        scheduleEw.eq("template_id", templateId);
        scheduleEw.eq("machine_id", machineId);
        scheduleEw.gt("date", now);
        scheduleEw.le("date", imCalendarData.getMaxDate());
        inspectionMaintainScheduleMapper.delete(scheduleEw);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        imCalendarData.getScheduleMap().forEach((key, value) -> {
            try {
                Date thisDay = format.parse(key);
                //如果有排班并且排班日期大于当前日期
                if (value && thisDay.compareTo(now) > 0) {
                    InspectionMaintainSchedule schedule = new InspectionMaintainSchedule();
                    String scheduleId = CommonUtil.getRandomUUID();
                    schedule.setId(scheduleId);
                    schedule.setMachineId(machineId);
                    schedule.setDate(thisDay);
                    schedule.setTemplateId(templateId);
                    schedule.setCreateTime(new Date());
                    inspectionMaintainScheduleMapper.insert(schedule);
                    Task task = new Task();
                    task.setId(CommonUtil.getRandomUUID());
                    task.setStartTime(thisDay);
                    task.setType(getTaskType(imCalendarData.getTemplateIdType()));
                    task.setSourceId(scheduleId);
                    task.setMachineId(machineId);
                    task.setIsAvailable(1);
                    task.setStatus(ConstantProperty.TASK_STATUS_1);
                    taskMapper.insert(task);
                }
            } catch (ParseException ignored) {
                throw new RuntimeException("日期格式不正确，请联系管理员！");
            }
        });
    }

    private String getTaskType(String templateIdType) {
        switch (templateIdType) {
            case "r":
                return "D";
            case "b":
                return "M";
            case "d":
                return "R";
        }
        return null;
    }

    @Override
    public Map<String, ImResultForSchedule> getSchedules(ImCalendarData imCalendarData) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        EntityWrapper<InspectionMaintainSchedule> scheduleEw = new EntityWrapper<>();
        Date minDate = imCalendarData.getMinDate();
        scheduleEw.ge("date", minDate);
        Date maxDate = imCalendarData.getMaxDate();
        scheduleEw.le("date", maxDate);
        scheduleEw.eq("template_id", imCalendarData.getTemplateId());
        scheduleEw.eq("machine_id", imCalendarData.getMachineId());
        scheduleEw.orderBy("date");
        List<InspectionMaintainSchedule> scheduleList = inspectionMaintainScheduleMapper.selectList(scheduleEw);
        Set<String> scheduleSet = new HashSet<>();
        scheduleList.forEach(sc -> scheduleSet.add(format.format(sc.getDate())));

        Map<String, List<ImResultForCount>> mapImResultForCounts = getImResutForScheduleMap(imCalendarData, minDate, maxDate);

        Map<String, ImResultForSchedule> imResultForScheduleMap = new HashMap<>();
        for (String dateStr : mapImResultForCounts.keySet()) {
            List<ImResultForCount> imResultForCountList = mapImResultForCounts.get(dateStr);
            int noChackCount = 0;
            int successCount = 0;
            int failureCount = 0;
            for (ImResultForCount imResultForCount : imResultForCountList) {
                String result = imResultForCount.getResult();
                if("success".equals(result)){
                    successCount = successCount + 1;
                }else if("failure".equals(result)){
                    failureCount = failureCount + 1;
                }else{
                    noChackCount = noChackCount +1;
                }
            }
            ImResultForSchedule imResultForSchedule = new ImResultForSchedule();
            imResultForSchedule.setFailureCount(failureCount);
            imResultForSchedule.setSuccessCount(successCount);
            imResultForSchedule.setNoChackCount(noChackCount);
            imResultForScheduleMap.put(dateStr,imResultForSchedule);
        }

        Calendar cal = Calendar.getInstance();
        Map<String, ImResultForSchedule> results = new TreeMap<>();
        for (cal.setTime(minDate); minDate.compareTo(maxDate) < 0; cal.add(Calendar.DATE, 1)) {
            minDate = cal.getTime();
            String thisDay = format.format(minDate);
            //scheduleSet有数据就说明有排班
            if (scheduleSet.contains(thisDay)) {
                if(imResultForScheduleMap.containsKey(thisDay)) {
                    ImResultForSchedule  imResultForSchedule = imResultForScheduleMap.get(thisDay);
                    imResultForSchedule.setHasSchedule(true);
                    results.put(thisDay,imResultForSchedule);
                }else{
                    ImResultForSchedule empImResultForSchedule = getEmpImResultForSchedule(true);
                    results.put(thisDay,empImResultForSchedule);
                }
            } else {
                results.put(thisDay, getEmpImResultForSchedule(false));
            }
        }
        return results;
    }

    private Map<String, List<ImResultForCount>> getImResutForScheduleMap(ImCalendarData imCalendarData, Date minDate, Date maxDate) {
        InspectionMaintainTemplate inspectionMaintainTemplate = inspectionMaintainTemplateMapper.selectById(imCalendarData.getTemplateId());
        EntityWrapper<ImResultForCount> imResultForCountEntityWrapper = new EntityWrapper<>();
        imResultForCountEntityWrapper.eq("t.machine_id", imCalendarData.getMachineId());
        if("D".equals(inspectionMaintainTemplate.getTemplateType())){
            imResultForCountEntityWrapper.eq("t.type", ConstantProperty.TASK_TYPE_REGULARLY);
        }else if("R".equals(inspectionMaintainTemplate.getTemplateType())){
            imResultForCountEntityWrapper.eq("t.type", ConstantProperty.TASK_TYPE_DAILY);
        }else{
            imResultForCountEntityWrapper.eq("t.type", ConstantProperty.TASK_TYPE_MAINTAIN);
        }
        imResultForCountEntityWrapper.ge("t.start_time", DateUtils.DateToString(minDate,"yyyy-MM-dd HH:mm:ss"));
        if(maxDate.after(new Date())){
            imResultForCountEntityWrapper.lt("t.start_time", DateUtils.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }else{
            imResultForCountEntityWrapper.lt("t.start_time", DateUtils.DateToString(maxDate,"yyyy-MM-dd HH:mm:ss"));
        }
        List<ImResultForCount> imResultForCounts = inspectionMaintainItemResultMapper.selectImResultByTaskInfo(imResultForCountEntityWrapper);
        Map<String, List<ImResultForCount>> mapImResultForCounts = new HashMap<>();
        for (ImResultForCount imResultForCount : imResultForCounts) {
            Date date = imResultForCount.getScheduleDate();
            String dateStr = DateUtils.DateToString(date, "yyyy-MM-dd");
            if(mapImResultForCounts.containsKey(dateStr)){
                mapImResultForCounts.get(dateStr).add(imResultForCount);
            }else{
                List<ImResultForCount> list = new ArrayList<>();
                list.add(imResultForCount);
                mapImResultForCounts.put(dateStr, list);
            }
        }
        return mapImResultForCounts;
    }

    private ImResultForSchedule getEmpImResultForSchedule(boolean hasSchedule){
        ImResultForSchedule imResultForSchedule = new ImResultForSchedule();
        imResultForSchedule.setHasSchedule(hasSchedule);
        imResultForSchedule.setFailureCount(0);
        imResultForSchedule.setNoChackCount(0);
        imResultForSchedule.setSuccessCount(0);
        return imResultForSchedule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSetSchedule(InspectionMaintainSetSchedule setSchedule) throws ParseException {
        /**
         * 1. 更新之前的task数据
         * 2. 删除之前排班设定的排班数据但不能删除已点检的数据
         * 3. 保存设定数据
         * 4. 添加排班数据
         */
        Date startDate = setSchedule.getStartDate();
        Date now = new Date();
        if (startDate.compareTo(now) < 0) {
            startDate = now;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String templateId = setSchedule.getTemplateId();
        String machineId = setSchedule.getMachineId();
        //修改task状态
        Map<String, Object> param = new HashMap<>();
        param.put("templateId", templateId);
        param.put("machineId", machineId);
        param.put("startDate", startDate);
        param.put("endDate", format.parse("2099-01-01"));
        taskMapper.updateTaskStatusBySchedule(param);

        EntityWrapper<InspectionMaintainSchedule> scheduleEw = new EntityWrapper<>();
        //删除今天之后的排班
        scheduleEw.gt("date", new Date());
        inspectionMaintainScheduleMapper.delete(scheduleEw);
        if (StringUtils.isNotEmpty(setSchedule.getId())) {
            setSchedule.setModifyTime(new Date());
            setScheduleMapper.updateById(setSchedule);
        } else {
            setSchedule.setId(CommonUtil.getRandomUUID());
            setSchedule.setCreateTime(new Date());
            setScheduleMapper.insert(setSchedule);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(setSchedule.getStartDate());
        for (int i = 0; i < setSchedule.getTimes(); i++) {
            if(calendar.getTime().compareTo(now) > 0){
                InspectionMaintainSchedule schedule = new InspectionMaintainSchedule();
                schedule.setId(CommonUtil.getRandomUUID());
                schedule.setTemplateId(templateId);
                schedule.setMachineId(machineId);
                schedule.setDate(calendar.getTime());
                schedule.setCreateTime(new Date());
                inspectionMaintainScheduleMapper.insert(schedule);
            }
            switch (setSchedule.getFrequency()) {
                case "DAY":
                    calendar.add(Calendar.DATE, 1);
                    break;
                case "WEEK":
                    calendar.add(Calendar.DATE, 7);
                    break;
                case "MONTH":
                    calendar.add(Calendar.MONTH, 1);
                    break;
            }
        }
    }

    @Override
    public InspectionMaintainSetSchedule getSetSchedule(String templateId, String machineId) {
        EntityWrapper<InspectionMaintainSetSchedule> setScheduleEw = new EntityWrapper<>();
        setScheduleEw.eq("template_id", templateId);
        setScheduleEw.eq("machine_id", machineId);
        int count = setScheduleMapper.selectCount(setScheduleEw);
        if (count == 0) return new InspectionMaintainSetSchedule();
        List<InspectionMaintainSetSchedule> list = setScheduleMapper.selectList(setScheduleEw);
        return list.get(0);
    }

}
