package com.ics.system.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 数据字典项表
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
@TableName("sys_dictionary_item")
public class SysDictionaryItem extends Model<SysDictionaryItem> {

    private static final long serialVersionUID = 1L;

    /**
     * id，guid
     */
	private String id;
    /**
     * 所属字典表id
     */
	@TableField("dict_id")
	private String dictId;
    /**
     * 数据项名称
     */
	@TableField("item_name")
	private String itemName;
    /**
     * 数据项值，同一字典表值不可重复
     */
	@TableField("item_value")
	private String itemValue;
    /**
     * 状态，0：禁用；1：启用；
     */
	private Integer status;
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

	/**
	 * 布局
	 */
	@TableField("layout")
	private String layout;

	public String getLayout() {
		return layout;
	}

	public void setLayout(String id) {
		this.layout = layout;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return "SysDictionaryItem{" +
			", id=" + id +
			", dictId=" + dictId +
			", itemName=" + itemName +
			", itemValue=" + itemValue +
			", status=" + status +
			", sortIdx=" + sortIdx +
			", remark=" + remark +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			"}";
	}
}

