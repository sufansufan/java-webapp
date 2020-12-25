package com.ics.inspectionMaintenance.model;

public class ResultSummary {
    private int successCount;
    private int failureCount;
    private int noCheckCount;

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public int getNoCheckCount() {
        return noCheckCount;
    }

    public void setNoCheckCount(int noCheckCount) {
        this.noCheckCount = noCheckCount;
    }

    public ResultSummary(int successCount, int failureCount, int noCheckCount) {
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.noCheckCount = noCheckCount;
    }

    public ResultSummary() {
    }
}
