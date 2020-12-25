package com.ics.device.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 设备信息表
 * @author jjz
 *
 * 2019年7月22日
 */
@TableName("device_info")
public class DeviceInfo extends Model<DeviceInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id，guid
     */
	private String id;
	 /**
     * 实时数据ID
     */
    @TableField("dynamic_id")
	private String dynamicId;
    /**
     * 设备编码
     */
	@TableField("device_code")
	private String deviceCode;
    /**
     * 设备名称
     */
	@TableField("device_name")
	private String deviceName;
    /**
     * 所属区域Id
     */
	@TableField("org_id")
	private String orgId;
    /**
     * 所属企业
     */
	@TableField("enterprise_id")
	private String enterpriseId;
	 /**
     * 数据模板
     */
	@TableField("template_id")
	private String templateId;
//	 /**
//     * 绑定机组
//     */
//	@TableField("condensing_device_num_str")
//	private String condensingDeviceNumStr;
	 /**
     * 在线状态
     */
	@TableField("net_state")
	private Integer netState;
	 /**
     * 终端序列ID
     */
	private String sn;
	 /**
     * 当前连接IP
     */
	@TableField("ip_addr")
	private String ipAddr;
	 /**
     * 信号强度
     */
	@TableField("signal_strength")
	private String signalStrength;
	 /**
     * 在线时长
     */
	@TableField("online_time")
	private String onlineTime;
	 /**
     * 启动时长
     */
	@TableField("start_time")
	private String startTime;
	 /**
     * 运营商
     */
	private String operators;
	 /**
     * LBS定位信息
     */
	@TableField("lbs_locating")
	private String lbsLocating;
	 /**
     * 网络制式
     */
	@TableField("network_type")
	private String networkType;
	 /**
     * 固件版本
     */
	@TableField("firmware_version")
	private String firmwareVersion;
	 /**
     * CPU负荷
     */
	@TableField("cpu_load")
	private String cpuLoad;
	 /**
     * 内存剩余容量
     */
	@TableField("memory_surplus")
	private String memorySurplus;
	 /**
     * 内存使用百分比
     */
	@TableField("memory_percent")
	private String memoryPercent;
	 /**
     * Flash剩余容量
     */
	@TableField("flash_surplus")
	private String flashSurplus;
	 /**
     *  Flash使用百分比
     */
	@TableField("flash_percent")
	private String flashPercent;
	 /**
     * 设备型号
     */
	@TableField("device_model")
	private String deviceModel;
    /**
     * 设备类型
     */
	@TableField("device_type")
	private String deviceType;
	 /**
     * 设备版本
     */
	@TableField("device_version")
	private String deviceVersion;
	 /**
     * 监测类型，1:污染源，2:水质，3:噪声，4:大气，5:扬尘，6:地质灾害点
     */
	@TableField("monitor_type")
	private Integer monitorType;
	 /**
     * SIM卡号
     */
	@TableField("sim_card")
	private String simCard;
	 /**
     * 运营商
     */
	@TableField("sim_operator")
	private String simOperator;
	 /**
     * 安装地址
     */
	@TableField("address")
	private String address;
    /**
     * 安装时间
     */
	@TableField("install_time")
	private Date installTime;
	/**
	 * 经度
	 */
	private BigDecimal longitude;
	/**
	 * 纬度
	 */
	private BigDecimal latitude;
	/**
	 * 联系人
	 */
	@TableField("owner_name")
	private String ownerName;
	/**
	 * 联系人电话
	 */
	@TableField("owner_phone")
	private String ownerPhone;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 图片路径
	 */
	@TableField("photo_path")
	private String photoPath;
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
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getNetState() {
		return netState;
	}
	public void setNetState(Integer netState) {
		this.netState = netState;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	public String getLbsLocating() {
		return lbsLocating;
	}
	public void setLbsLocating(String lbsLocating) {
		this.lbsLocating = lbsLocating;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	public String getCpuLoad() {
		return cpuLoad;
	}
	public void setCpuLoad(String cpuLoad) {
		this.cpuLoad = cpuLoad;
	}
	public String getMemorySurplus() {
		return memorySurplus;
	}
	public void setMemorySurplus(String memorySurplus) {
		this.memorySurplus = memorySurplus;
	}
	public String getMemoryPercent() {
		return memoryPercent;
	}
	public void setMemoryPercent(String memoryPercent) {
		this.memoryPercent = memoryPercent;
	}
	public String getFlashSurplus() {
		return flashSurplus;
	}
	public void setFlashSurplus(String flashSurplus) {
		this.flashSurplus = flashSurplus;
	}
	public String getFlashPercent() {
		return flashPercent;
	}
	public void setFlashPercent(String flashPercent) {
		this.flashPercent = flashPercent;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceVersion() {
		return deviceVersion;
	}
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	public Integer getMonitorType() {
		return monitorType;
	}
	public void setMonitorType(Integer monitorType) {
		this.monitorType = monitorType;
	}
	public String getSimCard() {
		return simCard;
	}
	public void setSimCard(String simCard) {
		this.simCard = simCard;
	}
	public String getSimOperator() {
		return simOperator;
	}
	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getInstallTime() {
		return installTime;
	}
	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
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
//	public String getCondensingDeviceNumStr() {
//		return condensingDeviceNumStr;
//	}
//	public void setCondensingDeviceNumStr(String condensingDeviceNumStr) {
//		this.condensingDeviceNumStr = condensingDeviceNumStr;
//	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
