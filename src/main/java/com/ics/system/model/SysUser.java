package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户表，用于登录管理平台和终端操作
 * </p>
 *
 * @author yi
 * @since 2017-11-17
 */
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id，guid
     */
	private String id;
    /**
     * 用户编号，唯一索引
     */
	@TableField("user_code")
	private String userCode;
    /**
     * 用户名称
     */
	@TableField("user_name")
	private String userName;
    /**
     * 平台登录密码SM3
     */
	@TableField("user_password")
	private String userPassword;
    /**
     * 终端操作密码SM3
     */
	@TableField("terminal_user_password")
	private String terminalUserPassword;
    /**
     * 身份证号
     */
	@TableField("id_card")
	private String idCard;
    /**
     * 性别，1：男；2：女
     */
	private Integer sex;
    /**
     * 电话
     */
	private String telephone;
    /**
     * 手机
     */
	private String cellphone;
    /**
     * 照片路径
     */
	@TableField("photo_path")
	private String photoPath;
    /**
     * 状态，1：正常；2：禁止登录平台；3：禁止操作终端
     */
	private Integer status;
    /**
     *  备注
     */
	private String remark;
    /**
     * 所属机构id，最基层的机构
     */
	@TableField("org_id")
	private String orgId;
    /**
     * 所属机构路径，从一级到最基层，最多10级，各个机构id之间用:（英文冒号）分隔
     */
	@TableField("org_id_path")
	private String orgIdPath;
    /**
     * 角色id
     */
	@TableField("role_id")
	private String roleId;
    /**
     * 用户身份序号，数值越小级别越高
     */
	@TableField("identity_idx")
	private Integer identityIdx;
    /**
     * 用户身份名称
     */
	@TableField("identity_name")
	private String identityName;
    /**
     * 用户职称
     */
	@TableField("title_name")
	private String titleName;
    /**
     * 用户职级
     */
	@TableField("rank_name")
	private String rankName;
	/**
     * 注册来源，0平台；1终端
     */
	@TableField("reg_from")
	private Integer regFrom;

	/**
	 * 账号使用期限
	 */
	private Date deadline;
	/*
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("modify_time")
	private Date modifyTime;

	/**是否超级管理员*/
	@TableField(exist = false)
	private boolean issupermanager;
	
	@TableField(exist = false)
	private SysRole sysrole;
	
	@TableField(exist = false)
	private String orgName;
	
	@TableField(exist = false)
	private String identityImage;
	//管辖河流名称
	@TableField(exist = false)
	private String riverNameArr;
	//管辖河流id
	@TableField(exist = false)
	private String riverIdArr;
	@TableField(exist = false)
	private String roleName;

	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 企业微信号
	 */
	@TableField("enterprise_wechat")
	private String enterpriseWechat;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getTerminalUserPassword() {
		return terminalUserPassword;
	}

	public void setTerminalUserPassword(String terminalUserPassword) {
		this.terminalUserPassword = terminalUserPassword;
	}
	
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgIdPath() {
		return orgIdPath;
	}

	public void setOrgIdPath(String orgIdPath) {
		this.orgIdPath = orgIdPath;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
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

	public boolean getIssupermanager() {
		return issupermanager;
	}

	public void setIssupermanager(boolean issupermanager) {
		this.issupermanager = issupermanager;
	}
	
	public SysRole getSysrole() {
		return sysrole;
	}

	public void setSysrole(SysRole sysrole) {
		this.sysrole = sysrole;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getIdentityImage() {
		return identityImage;
	}

	public void setIdentityImage(String identityImage) {
		this.identityImage = identityImage;
	}
	
	public String getRiverNameArr() {
		return riverNameArr;
	}

	public void setRiverNameArr(String riverNameArr) {
		this.riverNameArr = riverNameArr;
	}

	public String getRiverIdArr() {
		return riverIdArr;
	}

	public void setRiverIdArr(String riverIdArr) {
		this.riverIdArr = riverIdArr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnterpriseWechat() {
		return enterpriseWechat;
	}

	public void setEnterpriseWechat(String enterpriseWechat) {
		this.enterpriseWechat = enterpriseWechat;
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", userCode=" + userCode + ", userName=" + userName + ", userPassword="
				+ userPassword + ", terminalUserPassword=" + terminalUserPassword + ", idCard=" + idCard + ", sex="
				+ sex + ", telephone=" + telephone + ", cellphone=" + cellphone + ", photoPath=" + photoPath
				+ ", status=" + status + ", remark=" + remark + ", orgId=" + orgId + ", orgIdPath=" + orgIdPath
				+ ", roleId=" + roleId + ", identityIdx=" + identityIdx + ", identityName=" + identityName
				+ ", titleName=" + titleName + ", rankName=" + rankName + ", regFrom=" + regFrom + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + ", issupermanager=" + issupermanager + ", sysrole="
				+ sysrole + ", orgName=" + orgName + ", identityImage=" + identityImage + ", riverNameArr="
				+ riverNameArr + ", riverIdArr=" + riverIdArr + "]";
	}

	public void setDefaultIdx() {
		this.identityIdx=3;
		this.identityName="普通";
	}
	
	public Integer getRegFrom() {
		return regFrom;
	}

	public void setRegFrom(Integer regFrom) {
		this.regFrom = regFrom;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
