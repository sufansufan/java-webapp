package com.ics.inspectionMaintenance.model;

import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.InspectionMaintainItem;
import com.ics.dataDesources.model.InspectionMaintainSchedule;
import com.ics.utils.ConstantProperty;

/**
 * 任务
 */
public class ExceptionDetail {

    private Task task;

    private Exception exception;

    private ControlMachine controlMachine;

    private ExceptionFix exceptionFix;

    private InspectionMaintainItem inspectionMaintainItem;

    private static String updateUrl = ConstantProperty.condensingDevice_url;

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

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public ExceptionFix getExceptionFix() {
        return exceptionFix;
    }

    public void setExceptionFix(ExceptionFix exceptionFix) {
        this.exceptionFix = exceptionFix;
    }

    public InspectionMaintainItem getInspectionMaintainItem() {
        return inspectionMaintainItem;
    }

    public void setInspectionMaintainItem(InspectionMaintainItem inspectionMaintainItem) {
        this.inspectionMaintainItem = inspectionMaintainItem;
    }

    @Override
    public String toString() {
        return "ExceptionDetail{" +
                "task=" + task +
                ", exception=" + exception +
                ", controlMachine=" + controlMachine +
                ", exceptionFix=" + exceptionFix +
                '}';
    }
}