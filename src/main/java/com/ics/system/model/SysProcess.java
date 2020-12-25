package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kld
 * @since 2019-08-07
 */
@TableName("sys_process")
public class SysProcess extends Model<SysProcess> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 流程编码，唯一索引
     */
	@TableField("process_code")
	private String processCode;
    /**
     * 流程名称
     */
	@TableField("process_name")
	private String processName;
    /**
     * 是否可删除 0:不可删除 1:可删除
     */
	@TableField("is_delete_enable")
	private Integer isDeleteEnable;
	private String remark;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getIsDeleteEnable() {
		return isDeleteEnable;
	}

	public void setIsDeleteEnable(Integer isDeleteEnable) {
		this.isDeleteEnable = isDeleteEnable;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysProcess{" +
			", id=" + id +
			", processCode=" + processCode +
			", processName=" + processName +
			", isDeleteEnable=" + isDeleteEnable +
			", remark=" + remark +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
