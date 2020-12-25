package com.ics.inspectionMaintenance.model;

import java.util.List;

public class TaskOverview {
    private List<TaskOverviewItem> taskOverviewItems;
    private String orgName;
    private String teamName;
    private String orgCode;
    private String teamCode;

    public List<TaskOverviewItem> getTaskOverviewItems() {
        return taskOverviewItems;
    }

    public void setTaskOverviewItems(List<TaskOverviewItem> taskOverviewItems) {
        this.taskOverviewItems = taskOverviewItems;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamId(String teamCode) {
        this.teamCode = teamCode;
    }
}