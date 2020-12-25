package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author yi
 * @since 2017-12-19
 */
@TableName("sys_org")
public class SysOrg extends Model<SysOrg> {

    private static final long serialVersionUID = 1L;

    /**
     * 机构id，guid
     */
	private String id;
    /**
     * 机构代码，唯一索引
     */
	@TableField("org_code")
	private String orgCode;
    /**
     * 机构名称
     */
	@TableField("org_name")
	private String orgName;
    /**
     * 父机构id，如果为根节点，则为‘’
     */
	@TableField("parent_id")
	private String parentId;
    /**
     * 所属机构路径，从一级到自身
     */
	@TableField("org_id_path")
	private String orgIdPath;
    /**
     * 排序，越小排在前面
     */
	@TableField("sort_idx")
	private Integer sortIdx;
    /**
     * 备注
     */
	private String remark;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrgIdPath() {
		return orgIdPath;
	}

	public void setOrgIdPath(String orgIdPath) {
		this.orgIdPath = orgIdPath;
	}

	public Integer getSortIdx() {
		return sortIdx;
	}

	public void setSortIdx(Integer sortIdx) {
		this.sortIdx = sortIdx;
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
		return "SysOrg{" +
			", id=" + id +
			", orgCode=" + orgCode +
			", orgName=" + orgName +
			", parentId=" + parentId +
			", orgIdPath=" + orgIdPath +
			", sortIdx=" + sortIdx +
			", remark=" + remark +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
