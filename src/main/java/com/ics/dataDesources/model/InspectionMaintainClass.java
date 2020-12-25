package com.ics.dataDesources.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 点检保养分类
 */
@TableName("inspection_maintain_class")
public class InspectionMaintainClass extends Model<InspectionMaintainClass> {
    /**
     * 点检分类id
     */
    private String id;
    /**
     * 模版id
     */
    @TableField("template_id")
    private String templateId;
    /**
     * 分类名称
     */
    @TableField("class_name")
    private String className;
    /**
     * 1(可用)/0(删除)
     */
    @TableField("is_available")
    private Short isAvailable;
    /**
     * 设备id
     */
    @TableField("machine_id")
    private String machineId;
    /**
     * 分类顺序
     */
    @TableField("index_num")
    private int indexNum;

    /**
     * 项明细
     */
    @TableField(exist = false)
    private List<InspectionMaintainItem> itemList = new ArrayList<>();

    /**
     * 创建日期
     */
    @TableField("create_time")
    private Date createTime;

    /**
     *更新日期
     */
    @TableField("modify_time")
    private Date modifyTime;

    @Override
    protected Serializable pkVal() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Short getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Short isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
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

    public List<InspectionMaintainItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<InspectionMaintainItem> itemList) {
        this.itemList = itemList;
    }

    public int getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(int indexNum) {
        this.indexNum = indexNum;
    }
}