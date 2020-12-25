package com.ics.dataDesources.model;

import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("control_machine")
public class CondensingDevice{

	  /**
     * 权限id，guid
     */
	private String id;
	  /**
     * 所属终端
     */
	@TableField("device_code")
	private String deviceCode;
    /**
     * 企业Id
     */
	@TableField("enterprise_id")
	private String enterpriseId;
    /**
     * 模板Id
     */
	@TableField("template_id")
	private String templateId;
	 /**
     * 冷凝机设备编号
     */
	@TableField("machine_no")
	private String condensingDeviceNum;
    /**
     * 冷凝机设备名称
     */
	@TableField("machine_name")
	private String condensingDeviceName;
	/**
     * 报警状态，0:正常，1：预警，2：报警
     */
	@TableField("alarm_status")
	private Integer alarmState;
	/**
	 * 运行状态，0：停机，1：运行
	 */
	@TableField("power_status")
	private Integer switchState;
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
	/**
	 * 设备开关状态，0：停机，1：启动
	 */
	@TableField("running_status")
	private Integer runningStatus;
	/**
     * 运行时间
     */
	private Integer runtime;
    /**
     * 设备类型
     */
	@TableField("machine_type")
	private String machineType;
	/**
	 * 设备名
	 */
	@TableField("machine_name")
	private String machineName;
	 /**
     * 设备型号
     */
	@TableField("machine_model")
	private String deviceModel;
	  /**
     * 图片路径
     */
	@TableField("photo_path")
	private String photoPath;
    /**
     * 所属区域Id
     */
	private String remark;
	/**
     *  创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("modify_time")
	private Date modifyTime;
	
	//非本表数据
	 /**
     * 企业名称
     */
	@TableField(exist = false)
	private String enterpriseName;
	 /**
     * 模板名称
     */
	@TableField(exist = false)
	private String templateName;
	 /**
     * 区域ID
     */
	@TableField(exist = false)
	private String orgId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getCondensingDeviceNum() {
		return condensingDeviceNum;
	}
	public void setCondensingDeviceNum(String condensingDeviceNum) {
		this.condensingDeviceNum = condensingDeviceNum;
	}
	public String getCondensingDeviceName() {
		return condensingDeviceName;
	}
	public void setCondensingDeviceName(String condensingDeviceName) {
		this.condensingDeviceName = condensingDeviceName;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) { this.machineName = machineName; }
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
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
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Integer getAlarmState() {
		return alarmState;
	}
	public void setAlarmState(Integer alarmState) {
		this.alarmState = alarmState;
	}
	public Integer getRuntime() {
		return runtime;
	}
	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}
	public Integer getSwitchState() {
		return switchState;
	}
	public void setSwitchState(Integer switchState) {
		this.switchState = switchState;
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
}
