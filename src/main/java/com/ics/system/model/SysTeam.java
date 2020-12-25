package com.ics.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@TableName("sys_team")
public class SysTeam extends Model<SysTeam> {
    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("org_id")
    private String orgId;
    private String name;
    @TableField("parent_id")
    private String parentId;
    @TableField("team_id_path")
    private String teamIdPath;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;

    @TableField(exist = false)
    private String orgName;

    @TableField(exist = false)
    private String parentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTeamIdPath() {
        return teamIdPath;
    }

    public void setTeamIdPath(String teamIdPath) {
        this.teamIdPath = teamIdPath;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysTeam{" +
                "id='" + id + '\'' +
                ", orgId='" + orgId + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", teamIdPath='" + teamIdPath + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", orgName='" + orgName + '\'' +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}
