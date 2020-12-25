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
 * 2020年4月1日
 */
@TableName("maintenance_record")
public class MaintenanceRecord extends Model<MaintenanceRecord> {
	
	private static final long serialVersionUID = 1L;
	/**
     * ID
     */
	private String id;
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
     * 维修时间
     */
    @TableField("maintenance_time")
	private Date maintenanceTime;
    /**
     * 维修保养内容
     */
    @TableField("maintenance_content")
	private String maintenanceContent;
    /**
     * 维修时长
     */
    @TableField("maintenance_duration")
	private String maintenanceDuration;
    /**
     * 备注
     */
	private String remark;
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

    
    @TableField(exist = false)
    private String enterpriseName;
    
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
	public String getCondensingDeviceNum() {
		return condensingDeviceNum;
	}
	public void setCondensingDeviceNum(String condensingDeviceNum) {
		this.condensingDeviceNum = condensingDeviceNum;
	}
	public String getMaintenanceContent() {
		return maintenanceContent;
	}
	public void setMaintenanceContent(String maintenanceContent) {
		this.maintenanceContent = maintenanceContent;
	}
	public String getMaintenanceDuration() {
		return maintenanceDuration;
	}
	public void setMaintenanceDuration(String maintenanceDuration) {
		this.maintenanceDuration = maintenanceDuration;
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
	public Date getMaintenanceTime() {
		return maintenanceTime;
	}
	public void setMaintenanceTime(Date maintenanceTime) {
		this.maintenanceTime = maintenanceTime;
	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

	

}
