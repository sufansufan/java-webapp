package com.ics.remoteMonitor.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 实时数据历史表
 * @author jjz
 *
 * 2019年7月23日
 */
@TableName("rtd_history_latestday")
public class RtdHistoryFromLatestDay extends Model<RtdHistoryFromLatestDay> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	private String id;
	 /**
     * 实时数据ID，关联实时数据表
     */
    @TableField("dynamic_id")
	private String dynamicId;
    /**
     * 设备编码
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
     * 采集时间
     */
	@TableField("collect_time")
	private Date collectTime;
	
	@TableField(exist = false)
	private String collectTimeStr;
	
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

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public String getCollectTimeStr() {
		return collectTimeStr;
	}

	public void setCollectTimeStr(String collectTimeStr) {
		this.collectTimeStr = collectTimeStr;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
