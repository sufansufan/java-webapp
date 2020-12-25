package com.ics.inspectionMaintenance.model;

public class ExceptionTaskCalculateModel {
    private String imTaskId;

    private String  exceptionTaskId;

    private String exceptionTaskStatus;

    public String getImTaskId() {
        return imTaskId;
    }

    public void setImTaskId(String imTaskId) {
        this.imTaskId = imTaskId;
    }

    public String getExceptionTaskId() {
        return exceptionTaskId;
    }

    public void setExceptionTaskId(String exceptionTaskId) {
        this.exceptionTaskId = exceptionTaskId;
    }

    public String getExceptionTaskStatus() {
        return exceptionTaskStatus;
    }

    public void setExceptionTaskStatus(String exceptionTaskStatus) {
        this.exceptionTaskStatus = exceptionTaskStatus;
    }
}