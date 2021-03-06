package com.ics.dataDesources.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 监测因子表
 * @author jjz
 *
 * 2019年7月22日
 */
@TableName("monitor_factor_template")
public class MonitorFactorTemplate extends Model<MonitorFactorTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id，guid
     */
    @TableField("id")
	private String id;
    /**
     * 终端编码
     */
	@TableField("device_code")
	private String deviceCode;
    /**
     * 设备类型
     */
	@TableField("machine_type")
	private String machineType;
    /**
     * 监测因子名称
     */
	@TableField("factor_name")
	private String factorName;
    /**
     * 监测因子编码
     */
	@TableField("factor_code")
	private String factorCode;
	  /**
     * 监测因子标签
     */
	@TableField("factor_tag")
	private String factorTag;
    /**
     * 监测因子单位
     */
	@TableField("factor_unit")
	private String factorUnit;
	 /**
     * 数据精度，当前值乘以数据精度
     */
	@TableField("data_accuracy")
	private BigDecimal dataAccuracy;
    /**
     * 保留的小数位
     */
	@TableField("decimal_digits")
	private Integer decimalDigits;
	  /**
     * 协议类型
     */
	private String protocol;
	  /**
     * 数据类型，1：数值，2：预警，3：故障，4：开关
     */
	@TableField("type_id")
	private String typeId;

	@TableField("start_switch")
	private String startSwitch;
	/**
	 * 是否报警，0：不报警：1：报警
	 */
	@TableField("alarm_state")
	private Integer alarmState;
	/**
	 * 阈值上限报警信息
	 */
	@TableField("upper_limit_text")
	private String upperLimitText;

	/**
	 * 阈值下限报警信息
	 */
	@TableField("lower_limit_text")
	private String lowerLimitText;
	/**
	 * 阈值上限
	 */
	@TableField("upper_limit")
	private String upperLimit;
	/**
	 * 阈值下限
	 */
	@TableField("lower_limit")
	private String lowerLimit;

  @TableField("is_runtime")
  private Integer isRuntime;

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
	/**
	 * 企业名称
	 */
	@TableField(exist = false)
	private String enterpriseName;
	/**
	 * 设备ID
	 */
	@TableField("machine_id")
	private String machineId;

	/**
	 * 设备名称
	 */
	@TableField(exist = false)
	private String machineName;
	/**
	 * 设备类型名称
	 */
	@TableField(exist = false)
	private String machineTypeName;

  @TableField(exist = false)
	private String machineNo;

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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public String getFactorName() {
		return factorName;
	}

	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}

	public String getFactorCode() {
		return factorCode;
	}

	public void setFactorCode(String factorCode) {
		this.factorCode = factorCode;
	}

	public String getFactorTag() {
		return factorTag;
	}

	public void setFactorTag(String factorTag) {
		this.factorTag = factorTag;
	}

	public String getFactorUnit() {
		return factorUnit;
	}

	public void setFactorUnit(String factorUnit) {
		this.factorUnit = factorUnit;
	}

	public BigDecimal getDataAccuracy() {
		return dataAccuracy;
	}

	public void setDataAccuracy(BigDecimal dataAccuracy) {
		this.dataAccuracy = dataAccuracy;
	}

	public Integer getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(Integer alarmState) {
		this.alarmState = alarmState;
	}

	public String getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}

	public String getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
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

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

  public String getMachineNo() {
    return machineNo;
  }

  public void setMachineNo(String machineNo) {
    this.machineNo = machineNo;
  }

	public void setUpperLimitText(String upperLimitText) {
		this.upperLimitText = upperLimitText;
	}

	public void setLowerLimitText(String lowerLimitText) {
		this.lowerLimitText = lowerLimitText;
	}
	public String getUpperLimitText() {
		return upperLimitText;
	}

	public String getLowerLimitText() {
		return lowerLimitText;
	}
	public String getStartSwitch() {
		return startSwitch;
	}

	public void setStartSwitch(String startSwitch) {
		this.startSwitch = startSwitch;
	}

  public Integer getIsRuntime() {
    return isRuntime;
  }

  public void setIsRuntime(Integer isRuntime) {
    this.isRuntime = isRuntime;
  }

	public String getMachineTypeName() {
		return machineTypeName;
	}

	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
	}

	@Override
	public String toString() {
		return "MonitorFactorTemplate [id=" + id + ", deviceCode=" + deviceCode + ", machineName="
				+ machineType + ", factorName=" + factorName + ", factorCode=" + factorCode + ", factorUnit="
				+ factorUnit + ", dataAccuracy=" + dataAccuracy + ", decimalDigits=" + decimalDigits + ", protocol="
				+ protocol + ", typeId=" + typeId + ", alarmState=" + alarmState + ", upperLimit=" + upperLimit
				+ ", lowerLimit=" + lowerLimit + ", createTime=" + createTime + ", modifyTime=" + modifyTime
				+ ", enterpriseName=" + enterpriseName + "]";
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}


}
