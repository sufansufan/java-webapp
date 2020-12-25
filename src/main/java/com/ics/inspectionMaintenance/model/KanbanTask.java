package com.ics.inspectionMaintenance.model;

import java.util.List;

public class KanbanTask {
    private String orgName;
    private String teamName;
    private String teamId;
    private List<KanbanTaskItem> kanbanTaskItems;

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

    public List<KanbanTaskItem> getKanbanTaskItems() {
        return kanbanTaskItems;
    }

    public void setKanbanTaskItems(List<KanbanTaskItem> kanbanTaskItems) {
        this.kanbanTaskItems = kanbanTaskItems;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}