package com.ics.inspectionMaintenance.model;

import com.ics.dataDesources.model.ControlMachine;

public class TaskOverviewAboutMachineItem {
    private ControlMachine controlMachine;
    private int totalCount;
    private int completedCount;
    private int toBeComplete;
    private int toBeConfirm;


    public ControlMachine getControlMachine() {
        return controlMachine;
    }

    public void setControlMachine(ControlMachine controlMachine) {
        this.controlMachine = controlMachine;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getToBeComplete() {
        return toBeComplete;
    }

    public void setToBeComplete(int toBeComplete) {
        this.toBeComplete = toBeComplete;
    }

    public int getToBeConfirm() {
        return toBeConfirm;
    }

    public void setToBeConfirm(int toBeConfirm) {
        this.toBeConfirm = toBeConfirm;
    }

}