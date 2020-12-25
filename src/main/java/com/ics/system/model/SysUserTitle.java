package com.ics.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户职称表，描述用户职称信息
 * </p>
 *
 * @author yi
 * @since 2017-11-17
 */
@TableName("sys_user_title")
public class SysUserTitle extends Model<SysUserTitle> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 职称名称，如警监、警督、警司、警员
     */
	@TableField("title_name")
	private String titleName;
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

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
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
		return "SysUserTitle{" +
			", id=" + id +
			", titleName=" + titleName +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
