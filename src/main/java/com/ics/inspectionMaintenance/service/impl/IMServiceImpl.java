package com.ics.inspectionMaintenance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.InspectionMaintainSchedule;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.dataDesources.service.InspectionMaintainScheduleService;
import com.ics.inspectionMaintenance.model.ImDetail;
import com.ics.inspectionMaintenance.model.Task;
import com.ics.inspectionMaintenance.service.IMService;
import com.ics.inspectionMaintenance.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("imService")
public class IMServiceImpl implements IMService {

    @Autowired
    private InspectionMaintainScheduleService inspectionMaintainScheduleService;

    @Autowired
    private ControlMachineService controlMachineService;

    @Autowired
    private TaskService taskService;

    @Override
    public ImDetail buildImDetail(String taskId) {
        ImDetail imDetail = new ImDetail();
        Task task = taskService.buildIMTaskDetail(taskId);

        InspectionMaintainSchedule inspectionMaintainSchedule = inspectionMaintainScheduleService.selectById(task.getSourceId());
        String machineId = inspectionMaintainSchedule.getMachineId();
        List<String> ids = new ArrayList<>();
        ids.add(machineId);
        List<ControlMachine> machines = getMachine(ids);
        ControlMachine machine = machines.get(0);


        imDetail.setSchedule(inspectionMaintainSchedule);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        taskService.fillTaskEmptyFields(tasks);
        imDetail.setTask(task);
        imDetail.setControlMachine(machine);
        return imDetail;
    }

    @Override
    public List<ControlMachine> getMachine(List<String> machineIds){
        List<ControlMachine> machines = controlMachineService.selectBatchIds(machineIds);


        controlMachineService.fillMachineEmptyField(machines);

        return machines;
    }

}
