package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author yi
 * @since 2017-10-26
 */
@TableName("sys_authority")
public class SysAuthority extends Model<SysAuthority> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id，guid
     */
	private String id;
    /**
     * 权限名称
     */
	@TableField("authority_name")
	private String authorityName;
    /**
     * 权限编码，唯一
     */
	@TableField("authority_code")
	private String authorityCode;
    /**
     * 资源类型：1菜单；2功能
     */
	@TableField("authority_type")
	private Integer authorityType;
    /**
     * 权限顺序，数值越小排在前面
     */
	@TableField("authority_order")
	private Integer authorityOrder;
    /**
     * 该权限所属的父权限 0为顶级权限
     */
	@TableField("authority_parent_id")
	private String authorityParentId;
    /**
     * 该权限对应的url
     */
	@TableField("authority_url")
	private String authorityUrl;
	/**
	 * 该权限对应的图片
	 */
	@TableField("authority_image")
	private String authorityImage;
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

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	
	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	public Integer getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(Integer authorityType) {
		this.authorityType = authorityType;
	}

	public Integer getAuthorityOrder() {
		return authorityOrder;
	}

	public void setAuthorityOrder(Integer authorityOrder) {
		this.authorityOrder = authorityOrder;
	}

	public String getAuthorityParentId() {
		return authorityParentId;
	}

	public void setAuthorityParentId(String authorityParentId) {
		this.authorityParentId = authorityParentId;
	}

	public String getAuthorityUrl() {
		return authorityUrl;
	}

	public void setAuthorityUrl(String authorityUrl) {
		this.authorityUrl = authorityUrl;
	}

    public String getAuthorityImage() {
		return authorityImage;
	}

	public void setAuthorityImage(String authorityImage) {
		this.authorityImage = authorityImage;
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
		return "SysAuthority{" +
			", id=" + id +
			", authorityName=" + authorityName +
			", authorityOrder=" + authorityOrder +
			", authorityParentId=" + authorityParentId +
			", authorityUrl=" + authorityUrl +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
