package com.ics.dataDesources.model;

import com.baomidou.mybatisplus.annotations.TableField;

public class Team {

    /**
     * 权限id，guid
     */
    private String id;
    /**
     * 组织id
     */
    @TableField("org_id")
    private String orgId;
    /**
     * 组织名称
     */
    @TableField(exist = false)
    private String orgName;
    /**
     * 班组名称
     */
    @TableField("name")
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }
}
