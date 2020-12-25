package com.ics.system.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
@TableName("sys_dictionary")
public class SysDictionary extends Model<SysDictionary> {

    private static final long serialVersionUID = 1L;

    /**
     * id，guid
     */
	private String id;
    /**
     * 字典编码，唯一索引
     */
	@TableField("dict_code")
	private String dictCode;
    /**
     * 字典名称
     */
	@TableField("dict_name")
	private String dictName;
    /**
     * 是否可删除 0:不可删除 1:可删除
     */
	@TableField("is_delete_enable")
	private Integer isDeleteEnable;
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

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
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
		return "SysDictionary{" +
			", id=" + id +
			", dictCode=" + dictCode +
			", dictName=" + dictName +
			", isDeleteEnable=" + isDeleteEnable +
			", remark=" + remark +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}
