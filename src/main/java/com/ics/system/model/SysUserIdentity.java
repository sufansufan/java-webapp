package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户身份表，描述用户身份信息。
 * </p>
 *
 * @author yi
 * @since 2017-11-17
 */
@TableName("sys_user_identity")
public class SysUserIdentity extends Model<SysUserIdentity> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 身份序号，唯一索引，数值越小身份越高
     */
	@TableField("identity_idx")
	private Integer identityIdx;
    /**
     * 身份名称，如领导、管理员、普通人员
     */
	@TableField("identity_name")
	private String identityName;
    /**
     * 身份意义图片
     */
	@TableField("identity_image")
	private String identityImage;
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

	public Integer getIdentityIdx() {
		return identityIdx;
	}

	public void setIdentityIdx(Integer identityIdx) {
		this.identityIdx = identityIdx;
	}

	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public String getIdentityImage() {
		return identityImage;
	}

	public void setIdentityImage(String identityImage) {
		this.identityImage = identityImage;
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
		return "SysUserIdentity{" +
			", id=" + id +
			", identityIdx=" + identityIdx +
			", identityName=" + identityName +
			", identityImage=" + identityImage +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
