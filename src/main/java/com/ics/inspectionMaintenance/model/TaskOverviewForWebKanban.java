package com.ics.inspectionMaintenance.model;

import java.util.ArrayList;
import java.util.List;

public class TaskOverviewForWebKanban {
    private List<TaskOverviewItem> taskOverviewItems;
    private int completedCount;
    private int tbCompleteCount;
    private int tbConfirmCount;
    private String displayType;

    private float completedRate;
    private float tbCompleteRate;
    private float tbConfirmRate;

    public List<TaskOverviewItem> getTaskOverviewItems() {
        return taskOverviewItems;
    }

    public void setTaskOverviewItems(List<TaskOverviewItem> taskOverviewItems) {
        this.taskOverviewItems = taskOverviewItems;
    }


    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getTbCompleteCount() {
        return tbCompleteCount;
    }

    public void setTbCompleteCount(int tbCompleteCount) {
        this.tbCompleteCount = tbCompleteCount;
    }

    public int getTbConfirmCount() {
        return tbConfirmCount;
    }

    public void setTbConfirmCount(int tbConfirmCount) {
        this.tbConfirmCount = tbConfirmCount;
    }

    public float getCompletedRate() {
        return completedRate;
    }

    public void setCompletedRate(float completedRate) {
        this.completedRate = completedRate;
    }

    public float getTbCompleteRate() {
        return tbCompleteRate;
    }

    public void setTbCompleteRate(float tbCompleteRate) {
        this.tbCompleteRate = tbCompleteRate;
    }

    public float getTbConfirmRate() {
        return tbConfirmRate;
    }

    public void setTbConfirmRate(float tbConfirmRate) {
        this.tbConfirmRate = tbConfirmRate;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}