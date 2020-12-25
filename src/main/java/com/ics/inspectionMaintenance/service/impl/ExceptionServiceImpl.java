package com.ics.inspectionMaintenance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.InspectionMaintainItem;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.dataDesources.service.InspectionMaintainItemService;
import com.ics.inspectionMaintenance.mapper.ExceptionMapper;
import com.ics.inspectionMaintenance.model.*;
import com.ics.inspectionMaintenance.model.Exception;
import com.ics.inspectionMaintenance.service.*;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("exceptionService")
public class ExceptionServiceImpl extends ServiceImpl<ExceptionMapper, Exception> implements ExceptionService {

    @Autowired
    private InspectionMaintainItemResultService maintainItemResultService;

    @Autowired
    private ExceptionMapper exceptionMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ControlMachineService controlMachineService;

    @Autowired
    private ExceptionFixService exceptionFixService;

    @Autowired
    private InspectionMaintainItemResultService inspectionMaintainItemResultService;

    @Autowired
    private InspectionMaintainItemService inspectionMaintainItemService;

    @Autowired
    private IMService imService;

    @Override
    public List<Exception> getExceptionList() {
        EntityWrapper<Exception> ew = new EntityWrapper<>();
        List<Exception> exceptions = exceptionMapper.selectList(ew);

        List<String> machineIds = exceptions.stream().map(Exception::getMachineId).collect(Collectors.toList());
        List<ControlMachine> machines = imService.getMachine(machineIds);


        Map<String, ControlMachine> stringControlMachineMap = MapUtils.listToMapByKey(machines, ControlMachine.class, "id");

        exceptions.stream().forEach(e -> {
            String machineId = e.getMachineId();
            ControlMachine machine = stringControlMachineMap.get(machineId);
            e.setControlMachine(machine);
        });

        return exceptions;
    }

    @Override
    public ExceptionDetail getExceptionDetail(String taskId){
        ExceptionDetail exceptionDetail = new ExceptionDetail();
        Task task = taskService.getFullFieldTask(taskId);
        String machineId = task.getMachineId();
        ControlMachine fullFieldControleMachine = controlMachineService.getFullFieldControleMachine(machineId);
        String exceptionId = task.getSourceId();

        EntityWrapper<ExceptionFix> ew = new EntityWrapper<ExceptionFix>();
        ew.eq("exception_id", exceptionId);
        ExceptionFix exceptionFix = exceptionFixService.selectOne(ew);
        if(!Objects.isNull(exceptionFix)){
            exceptionDetail.setExceptionFix(exceptionFix);
        }

        String taskType = task.getType();

        if(ConstantProperty.TASK_TYPE_DAILY_INSPECTION_EXCEPTION.equals(taskType) || ConstantProperty.TASK_TYPE_REGULAR_INSPECTION_EXCEPTION.equals(taskType) || ConstantProperty.TASK_TYPE_MAINTAIN_EXCEPTION.equals(taskType)){
            String exceptionResultId = task.getSourceId();
            Exception exception = exceptionMapper.selectById(exceptionResultId);
            String itemResutId = exception.getSourceId();
            InspectionMaintainItemResult inspectionMaintainItemResult = inspectionMaintainItemResultService.selectById(itemResutId);
            String machineItemId = inspectionMaintainItemResult.getMachineItemId();
            InspectionMaintainItem inspectionMaintainItem = inspectionMaintainItemService.selectById(machineItemId);
            exceptionDetail.setInspectionMaintainItem(inspectionMaintainItem);
        }
        Exception exception = exceptionMapper.selectExceptionById(exceptionId);
        exceptionDetail.setControlMachine(fullFieldControleMachine);
        exceptionDetail.setTask(task);
        exceptionDetail.setException(exception);
        return exceptionDetail;
    }
}
