package com.ics.inspectionMaintenance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.InspectionMaintainTemplate;

import java.io.Serializable;
import java.util.Date;

public class TaskOverviewItem {
    private String typeCode;
    private String typeName;
    private int totalCount;
    private int completedCount;
    private int toBeComplete;
    private int toBeConfirm;

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