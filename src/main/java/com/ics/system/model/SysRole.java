package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 角色表，描述用户具有的角色类型，如领导、管理员，每个角色赋予相应的权限
 * </p>
 *
 * @author yi
 * @since 2017-12-03
 */
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id，guid
     */
	private String id;
	/**
     * 组织id
     */
	@TableField("org_id")
	private String orgId;
    /**
     * 角色名称
     */
	@TableField("role_name")
	private String roleName;
	/**
	 * 角色编码
	 */
	@TableField("role_code")
	private String roleCode;
	/**
	 * 权限编码值，一串权限编码的组合，由权限编码组成，以:分隔，最多支持50个权限
	 */
	@TableField("authority_code")
	private String authorityCode;
	@TableField("authority_desc")
	private String authorityDesc;
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
	private String orgName;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	public String getAuthorityDesc() {
		return authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysRole{" +
			", id=" + id +
			", roleName=" + roleName +
			", authorityDesc=" + authorityDesc +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
