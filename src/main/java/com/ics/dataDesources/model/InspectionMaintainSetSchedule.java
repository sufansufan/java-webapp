package com.ics.dataDesources.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 排班设定数据
 */
@TableName("inspection_maintain_set_schedule")
public class InspectionMaintainSetSchedule extends Model<InspectionMaintainSetSchedule> {

    /**
     * 点检分类id
     */
    private String id;

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
     * 排班设定的开始日期
     */
    @TableField("start_date")
    private Date startDate;

    /**
     * 次数
     */
    @TableField("times")
    private int times;

    /**
     * 频率 DAY天 WEEK周 MONTH月
     */
    @TableField("frequency")
    private String frequency;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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