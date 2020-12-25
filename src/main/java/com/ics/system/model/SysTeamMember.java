package com.ics.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author suxiangrong
 * @date 2020/10/17
 */
@TableName("sys_team_member")
public class SysTeamMember extends Model<SysTeamMember> {
    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("team_id")
    private String teamId;
    @TableField("user_id")
    private String userId;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String cellphone;
    @TableField(exist = false)
    private String roleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "SysTeamMember{" +
                "id='" + id + '\'' +
                ", teamId='" + teamId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
