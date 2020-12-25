package com.ics.dataDesources.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 点检保养排班
 */
@TableName("inspection_maintain_schedule")
public class InspectionMaintainSchedule extends Model<InspectionMaintainSchedule> {

    /**
     * 点检保养排班id
     */
    private String id;

    /**
     * 排班日期
     */
    @TableField("date")
    private Date date;

    /**
     * 设备id
     */
    @TableField("machine_id")
    private String machineId;

    /**
     * 模版id
     */
    @TableField("template_id")
    private String templateId;

    /**
     * 创建日期
     */
    @TableField("create_time")
    private Date createTime;

    /**
     *更新日期
     */
    @TableField("modify_time")
    private Date modifyTime;

    @Override
    protected Serializable pkVal() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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
}