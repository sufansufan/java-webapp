package com.ics.inspectionMaintenance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 点检保养项结果
 */
@TableName("inspection_maintain_item_result")
public class InspectionMaintainItemResult extends Model<InspectionMaintainItemResult> {

    /**
     * 点检保养结果id
     */
    private String id;

    /**
     * 班长确认时间
     */
    @TableField("execute_date")
    private Date executeDate;

    /**
     * 对应排班id
     */
    @TableField("schedule_id")
    private String scheduleId;

    /**
     * 对应排班id
     */
    @TableField("task_id")
    private String taskId;

    /**
     * 1(可用)/0(删除)
     */
    @TableField("result")
    private String result;

    /**
     * 描述
     */
    @TableField("desc")
    private String desc;

    /**
     * 检查图片相关图片链接
     */
    @TableField("result_img_urls")
    private String resultImgUrls;

    /**
     * 设备id
     */
    @TableField("machine_id")
    private String machineId;

    /**
     * 类型（日常点检-D，定期点检-R，保养-M）
     */
    @TableField("type")
    private String type;

    /**
     * 点检保养项id
     */
    @TableField("machine_item_id")
    private String machineItemId;

    /**
     * 创建日期
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新日期
     */
    @TableField("modify_time")
    private Date modifyTime;

    /**
     * 更新日期
     */
    @TableField("meter_data")
    private String meterData;

    @TableField(exist = false)
    private ExceptionFix exceptionFix;

    public ExceptionFix getExceptionFix() {
        return exceptionFix;
    }

    public void setExceptionFix(ExceptionFix exceptionFix) {
        this.exceptionFix = exceptionFix;
    }

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

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResultImgUrls() {
        return resultImgUrls;
    }

    public void setResultImgUrls(String resultImgUrls) {
        this.resultImgUrls = resultImgUrls;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineItemId() {
        return machineItemId;
    }

    public void setMachineItemId(String machineItemId) {
        this.machineItemId = machineItemId;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeterData() {
        return meterData;
    }

    public void setMeterData(String meterData) {
        this.meterData = meterData;
    }

    @Override
    public String toString() {
        return "InspectionMaintainItemResult{" +
                "id='" + id + '\'' +
                ", executeDate=" + executeDate +
                ", scheduleId='" + scheduleId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", result='" + result + '\'' +
                ", desc='" + desc + '\'' +
                ", resultImgUrls='" + resultImgUrls + '\'' +
                ", machineId='" + machineId + '\'' +
                ", type='" + type + '\'' +
                ", machineItemId='" + machineItemId + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", meterData=" + meterData +
                ", exceptionFix=" + exceptionFix +
                '}';
    }
}