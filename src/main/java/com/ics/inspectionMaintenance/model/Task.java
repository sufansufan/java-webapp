package com.ics.inspectionMaintenance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.InspectionMaintainTemplate;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务
 */
@TableName("task")
public class Task extends Model<Task> {
    private static final long serialVersionUID = 1L;
    /**
     * 点检保养异常处理id
     */
    private String id;

    @TableField("start_time")
    private Date startTime;

    @TableField("status")
    private String status;

    @TableField("type")
    private String type;

    @TableField("last_modified_time")
    private Date lastModifiedTime;

    //如果是点检，则为scheduleId，如果是异常，则为exceptionid
    @TableField("source_id")
    private String sourceId;

    @TableField("is_available")
    private Integer isAvailable;

    @TableField("operator_user_id")
    private String operatorUserId;

    @TableField("operate_date")
    private Date operateDate;

    @TableField("confirm_user_id")
    private String confirmUserId;

    @TableField("confirm_date")
    private Date confirmDate;

    @TableField("admit_user_id")
    private String admitUserId;

    @TableField("admit_date")
    private Date admitDate;

    @TableField("reject_user_id")
    private String rejectUserId;

    @TableField("reject_date")
    private Date rejectDate;

    @TableField("before_task_id")
    private String beforeTaskId;

    @TableField("machine_id")
    private String machineId;

    @TableField("reject_desc")
    private String rejectDesc;

    @TableField(exist = false)
    private String operatorUserName;

    @TableField(exist = false)
    private String confirmUserName;

    @TableField(exist = false)
    private String admitUserName;

    @TableField(exist = false)
    private String rejectUserName;

    /**
     * 检出人
     */
    @TableField(exist = false)
    private String checkUserName;

    /**
     * 检出时间
     */
    @TableField(exist = false)
    private Date createTime;

    @TableField(exist = false)
    private String templateType;

    @TableField(exist = false)
    private String teamId;

    @TableField(exist = false)
    private String machineTypeName;

    @TableField(exist = false)
    private InspectionMaintainTemplate inspectionMaintainTemplate;

    @TableField(exist = false)
    private ControlMachine controlMachine;

    @TableField(exist = false)
    private Exception exception;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
    public InspectionMaintainTemplate getInspectionMaintainTemplate() {
        return inspectionMaintainTemplate;
    }

    public void setInspectionMaintainTemplate(InspectionMaintainTemplate inspectionMaintainTemplate) {
        this.inspectionMaintainTemplate = inspectionMaintainTemplate;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getOperatorUserName() {
        return operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }

    public String getConfirmUserName() {
        return confirmUserName;
    }

    public void setConfirmUserName(String confirmUserName) {
        this.confirmUserName = confirmUserName;
    }

    public String getAdmitUserName() {
        return admitUserName;
    }

    public void setAdmitUserName(String admitUserName) {
        this.admitUserName = admitUserName;
    }

    @TableField(exist = false)
    private ResultSummary resultSummary;

    @Override
    protected Serializable pkVal() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public ResultSummary getResultSummary() {
        return resultSummary;
    }

    public void setResultSummary(ResultSummary resultSummary) {
        this.resultSummary = resultSummary;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(String confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getAdmitUserId() {
        return admitUserId;
    }

    public void setAdmitUserId(String admitUserId) {
        this.admitUserId = admitUserId;
    }

    public Date getAdmitDate() {
        return admitDate;
    }

    public void setAdmitDate(Date admitDate) {
        this.admitDate = admitDate;
    }

    public String getRejectUserId() {
        return rejectUserId;
    }

    public void setRejectUserId(String rejectUserId) {
        this.rejectUserId = rejectUserId;
    }

    public Date getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
    }

    public String getBeforeTaskId() {
        return beforeTaskId;
    }

    public void setBeforeTaskId(String beforeTaskId) {
        this.beforeTaskId = beforeTaskId;
    }

    public String getRejectUserName() {
        return rejectUserName;
    }

    public void setRejectUserName(String rejectUserName) {
        this.rejectUserName = rejectUserName;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public ControlMachine getControlMachine() {
        return controlMachine;
    }

    public void setControlMachine(ControlMachine controlMachine) {
        this.controlMachine = controlMachine;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    public String getMachineNo() {
        if (controlMachine != null) return controlMachine.getMachineNo();
        return null;
    }
    public String getMachineName() {
        if (controlMachine != null) return controlMachine.getMachineName();
        return null;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTemplateTypeName() {
        if(templateType == null) return null;
        switch (templateType) {
            case "R":
                return "日常";
            case "D":
                return "定期";
            case "B":
                return "保养";
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", startTime=" + startTime +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", lastModifiedTime=" + lastModifiedTime +
                ", sourceId='" + sourceId + '\'' +
                ", isAvailable=" + isAvailable +
                ", operatorUserId='" + operatorUserId + '\'' +
                ", operateDate=" + operateDate +
                ", confirmUserId='" + confirmUserId + '\'' +
                ", confirmDate=" + confirmDate +
                ", admitUserId='" + admitUserId + '\'' +
                ", admitDate=" + admitDate +
                ", rejectUserId='" + rejectUserId + '\'' +
                ", rejectDate=" + rejectDate +
                ", beforeTaskId='" + beforeTaskId + '\'' +
                ", operatorUserName='" + operatorUserName + '\'' +
                ", confirmUserName='" + confirmUserName + '\'' +
                ", admitUserName='" + admitUserName + '\'' +
                ", rejectUserName='" + rejectUserName + '\'' +
                ", templateType='" + templateType + '\'' +
                ", inspectionMaintainTemplate=" + inspectionMaintainTemplate +
                ", resultSummary=" + resultSummary +
                '}';
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task task = null;
        try{
            task = (Task)super.clone();   //浅复制
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return task;
    }

    public String getRejectDesc() {
        return rejectDesc;
    }

    public void setRejectDesc(String rejectDesc) {
        this.rejectDesc = rejectDesc;
    }
}