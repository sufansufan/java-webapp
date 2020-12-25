package com.ics.dataDesources.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 监测因子标签表
 *
 * @author jjz
 *
 *         2019年7月22日
 */
@TableName("monitor_factor_tag")
public class MonitorFactorTag extends Model<MonitorFactorTag> {

	private static final long serialVersionUID = 1L;

	/**
	 * 权限id，guid
	 */
	private String id;
	/**
	 * 设备类型
	 */
	@TableField("tag_name")
	private String tagName;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTagName() { return tagName; }

	public void setTagName(String tagName) {
		this.tagName = tagName;
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

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
