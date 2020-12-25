package com.ics.remoteMonitor.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * @author jjz
 *
 * 2019年12月2日
 */
@TableName("alarm_info")
public class AlarmInfo extends Model<AlarmInfo> {

	private static final long serialVersionUID = 1L;
	/**
     * ID
     */
	private String id;
	 /**
     * 实时数据ID
     */
  @TableField("dynamic_id")
	private String dynamicId;
	 /**
     * 所属终端
     */
  @TableField("device_code")
	private String deviceCode;
    /**
     * 设备编号
     */
  @TableField("condensing_device_num")
	private String condensingDeviceNum;
    /**
     * 监测因子编码
     */
  @TableField("factor_code")
	private String factorCode;
    /**
     * 监测因子数值
     */
  @TableField("factor_value")
	private String factorValue;
    /**
     * 报警类型，0：预警，1：报警
     */
  @TableField("alarm_type")
	private String alarmType;
    /**
     * 预警/报警内容
     */
  @TableField("alarm_content")
	private String alarmContent;
    /**
     * 创建时间
     */
  @TableField("create_time")
	private Date createTime;
    /**
     * 报警恢复时间
     */
  @TableField("recovery_time")
	private Date recoveryTime;

  @TableField(exist = false)
  private String factorName;
  @TableField(exist = false)
  private String createTimeStr;
  @TableField(exist = false)
  private String recoveryTimeStr;
  @TableField(exist = false)
  private String alarmTypeStr;
  @TableField(exist = false)
  private String enterpriseName;

	@TableField(exist = false)
  private String machineId;
	@TableField(exist = false)
  private String machineName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getCondensingDeviceNum() {
		return condensingDeviceNum;
	}

	public void setCondensingDeviceNum(String condensingDeviceNum) {
		this.condensingDeviceNum = condensingDeviceNum;
	}

	public String getFactorCode() {
		return factorCode;
	}

	public void setFactorCode(String factorCode) {
		this.factorCode = factorCode;
	}

	public String getFactorValue() {
		return factorValue;
	}

	public void setFactorValue(String factorValue) {
		this.factorValue = factorValue;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRecoveryTime() {
		return recoveryTime;
	}

	public void setRecoveryTime(Date recoveryTime) {
		this.recoveryTime = recoveryTime;
	}

	public String getFactorName() {
		return factorName;
	}

	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getRecoveryTimeStr() {
		return recoveryTimeStr;
	}

	public void setRecoveryTimeStr(String recoveryTimeStr) {
		this.recoveryTimeStr = recoveryTimeStr;
	}

	public String getAlarmTypeStr() {
		return alarmTypeStr;
	}

	public void setAlarmTypeStr(String alarmTypeStr) {
		this.alarmTypeStr = alarmTypeStr;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}



}
