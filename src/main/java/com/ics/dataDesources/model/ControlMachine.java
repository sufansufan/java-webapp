package com.ics.dataDesources.model;

import com.baomidou.mybatisplus.annotations.TableField;

import java.math.BigInteger;
import java.util.Date;

public class ControlMachine {

    /**
     * 权限id，guid
     */
    private String id;
    /**
     * 序列号
     */
    @TableField("serial_no")
    private String serialNo;
    /**
     * 所属终端
     */
    @TableField("device_code")
    private String deviceCode;
    /**
     * 企业ID
     */
    @TableField("enterprise_id")
    private String enterpriseId;
    /**
     * 组织ID
     */
    @TableField("org_id")
    private String orgId;
    /**
     * 班组ID
     */
    @TableField("team_id")
    private String teamId;
    /**
     * 数据模板ID
     */
    @TableField("template_id")
    private String templateId;
    /**
     * 设备编号
     */
    @TableField("machine_no")
    private String machineNo;
    /**
     * 设备名称
     */
    @TableField("machine_name")
    private String machineName;
    /**
     * 运行时间
     */
    @TableField("runtime")
    private Integer runtime;
    /**
     * 设备类型
     */
    @TableField("machine_type")
    private String machineType;
    /**
     * 设备类型名称
     */
    @TableField(exist = false)
    private String machineTypeName;
    /**
     * 设备型号
     */
    @TableField("machine_model")
    private String machineModel;
    /**
     * 图片路径
     */
    @TableField("photo_path")
    private String photoPath;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     *  采购时间
     */
    @TableField("purchasing_time")
    private Date purchasingTime;
    /**
     * 厂家
     */
    @TableField("manufacotry")
    private String manufacotry;

    //非本表数据
    /**
     * 用途
     */
    @TableField("machine_usage")
    private String machineUsage;
    /**
     * 功率
     */
    @TableField("rated_power")
    private Float ratedPower;
    /**
     * 是否在线，0：offline，1：online
     */
    @TableField("online_status")
    private int onlineStatus;
    /**
     * 运行状态，0：停机，1：运行
     */
    @TableField("power_status")
    private int powerStatus;
    /**
     * 报警状态，0:停机，1：运行，2：预警，3：报警
     */
    @TableField("alarm_status")
    private int alarmStatus;
    /**
     * 监控状态，2：监控，1：不监控，0：失效
     */
    @TableField("status")
    private int status;
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
    /**
     * 设备开关状态，0：停机，1：启动
     */
    @TableField("running_status")
    private Integer runningStatus;

    /**
     * 故障状态，0：无故障，1：有故障
     */
    @TableField("fault_status")
    private Integer faultStatus;
    /**
     * 预警的数量
     */
    @TableField("early_alarm_status")
    private Integer earlyAlarmStatus;


    //非本表数据
    /**
     * 企业名称
     */
    @TableField(exist = false)
    private String enterpriseName;

    /**
     * 是否远程监视
     * @return
     */
    @TableField("remote_monitor_flag")
    private Integer remoteMonitorFlag;
    /**
     * 是否点检
     * @return
     */
    @TableField("check_flag")
    private Integer checkFlag;
    /**
     * 是否保养
     * @return
     */
    @TableField("maintain_flag")
    private Integer maintainFlag;
    /**
     * 所属位置
     * @return
     */
    @TableField("location")
    private String location;
    /**
     * 组织机构名称
     * @return
     */
    @TableField(exist = false)
    private String orgName;
    /**
     * 监听设备
     * @return
     */
    @TableField(exist = false)
    private String deviceName;

    /**
     * teamName
     * @return
     */
    @TableField(exist = false)
    private String teamName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getMaintainFlag() {
        return maintainFlag;
    }

    public void setMaintainFlag(Integer maintainFlag) {
        this.maintainFlag = maintainFlag;
    }

    public Integer getRemoteMonitorFlag() {
        return remoteMonitorFlag;
    }

    public void setRemoteMonitorFlag(Integer remoteMonitorFlag) {
        this.remoteMonitorFlag = remoteMonitorFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(String machineModel) {
        this.machineModel = machineModel;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getPurchasingTime() {
        return purchasingTime;
    }

    public void setPurchasingTime(Date purchasingTime) {
        this.purchasingTime = purchasingTime;
    }

    public String getManufacotry() {
        return manufacotry;
    }

    public void setManufacotry(String manufacotry) {
        this.manufacotry = manufacotry;
    }

    public String getMachineUsage() {
        return machineUsage;
    }

    public void setMachineUsage(String machineUsage) {
        this.machineUsage = machineUsage;
    }

    public Float getRatedPower() {
        return ratedPower;
    }

    public void setRatedPower(Float ratedPower) {
        this.ratedPower = ratedPower;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public int getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(int powerStatus) {
        this.powerStatus = powerStatus;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setItemName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

    public Integer getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(Integer runningStatus) {
        this.runningStatus = runningStatus;
    }

    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }

    public Integer getEarlyAlarmStatus() {
        return earlyAlarmStatus;
    }

    public void setEarlyAlarmStatus(Integer earlyAlarmStatus) {
        this.earlyAlarmStatus = earlyAlarmStatus;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
