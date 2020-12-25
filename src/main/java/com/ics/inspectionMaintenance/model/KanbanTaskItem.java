package com.ics.inspectionMaintenance.model;

public class KanbanTaskItem {
    private String typeCode;
    private String typeName;
    private int totalCount;
    private float unfinishedRate;
    private int unfinishedCount;
    private float completedRate;
    private int completedCount;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public float getUnfinishedRate() {
        return unfinishedRate;
    }

    public void setUnfinishedRate(float unfinishedRate) {
        this.unfinishedRate = unfinishedRate;
    }

    public int getUnfinishedCount() {
        return unfinishedCount;
    }

    public void setUnfinishedCount(int unfinishedCount) {
        this.unfinishedCount = unfinishedCount;
    }

    public float getCompletedRate() {
        return completedRate;
    }

    public void setCompletedRate(float completedRate) {
        this.completedRate = completedRate;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }
}