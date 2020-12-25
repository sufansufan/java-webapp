package com.ics.dataDesources.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("enterprise_info")
public class EnterpriseInfo extends Model<EnterpriseInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id，guid
     */
	private String id;
    /**
     * 企业名称
     */
	@TableField("enterprise_name")
	private String enterpriseName;
    /**
     * 绑定终端编码
     */
	@TableField("device_code")
	private String deviceCode;
	 /**
     * 企业简称
     */
	private String abbreviation;
    /**
     * 所属区域Id
     */
	@TableField("org_id")
	private String orgId;
    /**
     * 所在流域
     */
	@TableField("basin_id")
	private String basinId;
	 /**
     * 关注程度
     */
	@TableField("concern_degree")
	private String concernDegree;
    /**
     * 控制级别
     */
	@TableField("control_level")
	private String controlLevel;
    /**
     * 排水类型
     */
	@TableField("drainage_type")
	private String drainageType;
	 /**
     * 排口位置
     */
	@TableField("outlet_position")
	private String outletPosition;
	 /**
     * 排向流域
     */
	@TableField("drainage_basin")
	private String drainageBasin;
	 /**
     * 排向河道
     */
	@TableField("drainage_river")
	private String drainageRiver;
	 /**
     * 企业类型
     */
	@TableField("enterprise_type")
	private String enterpriseType;
	 /**
     * 企业规模
     */
	@TableField("enterprise_scale")
	private String enterpriseScale;
	 /**
     * 所属园区
     */
	@TableField("affiliated_park")
	private String affiliatedPark;
    /**
     * 行业类别
     */
	@TableField("industry_category")
	private String industryCategory;
	/**
	 * 统一社会信用代码
	 */
	@TableField("enterprise_credit_code")
	private String enterpriseCreditCode;
	/**
	 * 法人代表
	 */
	@TableField("legal_representative")
	private String legalRepresentative;
	/**
	 * 企业生产的产品
	 */
	@TableField("enterprise_product")
	private String enterpriseProduct;
	/**
	 * 企业概况
	 */
	@TableField("enterprise_overview")
	private String enterpriseOverview;
	/**
	 * 图片路径
	 */
	@TableField("photo_path")
	private String photoPath;
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
	@TableField("contact_name")
	private String contactName;
	/**
	 * 联系人电话
	 */
	@TableField("contact_phone")
	private String contactPhone;
	/**
	 * 企业地址
	 */
	private String address;
	/**
	 * 邮箱推送状态，0：不推送，1：推送
	 */
	@TableField("mail_push_state")
	private Integer mailPushState;
	/**
	 * 邮箱推送地址
	 */
	@TableField("mail_address")
	private String mailAddress;
	/**
	 * 企业微信推送状态，0：不推送，1：推送
	 */
	@TableField("wechat_push_state")
	private Integer wechatPushState;
	/**
	 * 企业微信推送名称
	 */
	@TableField("wechat_address")
	private String wechatAddress;
	/**
	 * 企业微信推送ID
	 */
	@TableField("agent_id")
	private String agentId;
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
	
	
	//其他表数据
	@TableField(exist = false)
	private String orgName;
	@TableField(exist = false)
	private String basinName;
	@TableField(exist = false)
	private String concernDegreeName;
	@TableField(exist = false)
	private String enterpriseTypeName;
	@TableField(exist = false)
	private String affiliatedParkName;
	@TableField(exist = false)
	private String industryCategoryName;
	@TableField(exist = false)
	private String controlLevelName;
	@TableField(exist = false)
	private String drainageTypeName;
	@TableField(exist = false)
	private String image;
	
	@TableField(exist = false)
	private Integer netState;
	@TableField(exist = false)
	private Integer alarmState;
	@TableField(exist = false)
	private String sn;
	@TableField(exist = false)
	private String ipAddr;
	@TableField(exist = false)
	private String signalStrength;
	@TableField(exist = false)
	private String onlineTime;
	@TableField(exist = false)
	private String startTime;
	@TableField(exist = false)
	private String operators;
	@TableField(exist = false)
	private String lbsLocating;
	@TableField(exist = false)
	private String networkType;
	@TableField(exist = false)
	private String firmwareVersion;
	@TableField(exist = false)
	private String cpuLoad;
	@TableField(exist = false)
	private String memorySurplus;
	@TableField(exist = false)
	private String memoryPercent;
	@TableField(exist = false)
	private String flashSurplus;
	@TableField(exist = false)
	private String flashPercent;
	@TableField(exist = false)
	private Date deviceModifyTime;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getBasinId() {
		return basinId;
	}

	public void setBasinId(String basinId) {
		this.basinId = basinId;
	}

	public String getConcernDegree() {
		return concernDegree;
	}

	public void setConcernDegree(String concernDegree) {
		this.concernDegree = concernDegree;
	}

	public String getControlLevel() {
		return controlLevel;
	}

	public void setControlLevel(String controlLevel) {
		this.controlLevel = controlLevel;
	}

	public String getDrainageType() {
		return drainageType;
	}

	public void setDrainageType(String drainageType) {
		this.drainageType = drainageType;
	}

	public String getOutletPosition() {
		return outletPosition;
	}

	public void setOutletPosition(String outletPosition) {
		this.outletPosition = outletPosition;
	}

	public String getDrainageBasin() {
		return drainageBasin;
	}

	public void setDrainageBasin(String drainageBasin) {
		this.drainageBasin = drainageBasin;
	}

	public String getDrainageRiver() {
		return drainageRiver;
	}

	public void setDrainageRiver(String drainageRiver) {
		this.drainageRiver = drainageRiver;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getEnterpriseScale() {
		return enterpriseScale;
	}

	public void setEnterpriseScale(String enterpriseScale) {
		this.enterpriseScale = enterpriseScale;
	}

	public String getAffiliatedPark() {
		return affiliatedPark;
	}

	public void setAffiliatedPark(String affiliatedPark) {
		this.affiliatedPark = affiliatedPark;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getEnterpriseCreditCode() {
		return enterpriseCreditCode;
	}

	public void setEnterpriseCreditCode(String enterpriseCreditCode) {
		this.enterpriseCreditCode = enterpriseCreditCode;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getEnterpriseProduct() {
		return enterpriseProduct;
	}

	public void setEnterpriseProduct(String enterpriseProduct) {
		this.enterpriseProduct = enterpriseProduct;
	}

	public String getEnterpriseOverview() {
		return enterpriseOverview;
	}

	public void setEnterpriseOverview(String enterpriseOverview) {
		this.enterpriseOverview = enterpriseOverview;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBasinName() {
		return basinName;
	}

	public void setBasinName(String basinName) {
		this.basinName = basinName;
	}

	

	public String getConcernDegreeName() {
		return concernDegreeName;
	}

	public void setConcernDegreeName(String concernDegreeName) {
		this.concernDegreeName = concernDegreeName;
	}

	public String getEnterpriseTypeName() {
		return enterpriseTypeName;
	}

	public void setEnterpriseTypeName(String enterpriseTypeName) {
		this.enterpriseTypeName = enterpriseTypeName;
	}

	public String getAffiliatedParkName() {
		return affiliatedParkName;
	}

	public void setAffiliatedParkName(String affiliatedParkName) {
		this.affiliatedParkName = affiliatedParkName;
	}

	public String getIndustryCategoryName() {
		return industryCategoryName;
	}

	public void setIndustryCategoryName(String industryCategoryName) {
		this.industryCategoryName = industryCategoryName;
	}

	public String getControlLevelName() {
		return controlLevelName;
	}

	public void setControlLevelName(String controlLevelName) {
		this.controlLevelName = controlLevelName;
	}

	public String getDrainageTypeName() {
		return drainageTypeName;
	}

	public void setDrainageTypeName(String drainageTypeName) {
		this.drainageTypeName = drainageTypeName;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getNetState() {
		return netState;
	}

	public void setNetState(Integer netState) {
		this.netState = netState;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public Integer getMailPushState() {
		return mailPushState;
	}

	public void setMailPushState(Integer mailPushState) {
		this.mailPushState = mailPushState;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public Integer getWechatPushState() {
		return wechatPushState;
	}

	public void setWechatPushState(Integer wechatPushState) {
		this.wechatPushState = wechatPushState;
	}

	public String getWechatAddress() {
		return wechatAddress;
	}

	public void setWechatAddress(String wechatAddress) {
		this.wechatAddress = wechatAddress;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Date getDeviceModifyTime() {
		return deviceModifyTime;
	}

	public void setDeviceModifyTime(Date deviceModifyTime) {
		this.deviceModifyTime = deviceModifyTime;
	}

	public Integer getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(Integer alarmState) {
		this.alarmState = alarmState;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

	

}
