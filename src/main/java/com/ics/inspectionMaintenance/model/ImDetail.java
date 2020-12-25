package com.ics.inspectionMaintenance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.InspectionMaintainSchedule;
import com.ics.utils.ConstantProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 任务
 */
public class ImDetail {

    private Task task;

    private InspectionMaintainSchedule schedule;

    private ControlMachine controlMachine;

    private static String updateUrl = ConstantProperty.condensingDevice_url;

    public InspectionMaintainSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(InspectionMaintainSchedule schedule) {
        this.schedule = schedule;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public ControlMachine getControlMachine() {
        return controlMachine;
    }

    public void setControlMachine(ControlMachine controlMachine) {
        this.controlMachine = controlMachine;
    }
}