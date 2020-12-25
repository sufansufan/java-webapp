package com.ics.dataDesources.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ics.inspectionMaintenance.model.InspectionMaintainItemResult;

import java.io.Serializable;
import java.util.Date;

/**
 * 点检保养项
 * 设备 -> 模版 -> 分类 -> 项
 */
@TableName("inspection_maintain_item")
public class InspectionMaintainItem extends Model<InspectionMaintainItem> {

    /**
     * 点检项id
     */
    private String id;

    /**
     * 点检保养分类id
     */
    @TableField("class_id")
    private String classId;

    /**
     * 点检保养项名称
     */
    @TableField("name")
    private String name;

    /**
     * 点检保养项状态
     */
    @TableField("is_available")
    private Short isAvailable;

    /**
     * 排序
     */
    @TableField("index_num")
    private Short indexNum;

    /**
     * 点检保养方法
     */
    @TableField("method")
    private String method;

    /**
     * 点检保养标准
     */
    @TableField("standard")
    private String standard;

    /**
     * 1检查
     */
    @TableField("no_check")
    private Integer noCheck;

    /**
     * 操作规程相关图片存放地址
     */
    @TableField("step_img_urls")
    private String stepImgUrls;

    /**
     * 模版id
     */
    @TableField("template_id")
    private String templateId;

    /**
     * 设备id
     */
    @TableField("machine_id")
    private String machineId;

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

    @TableField(exist = false)
    private InspectionMaintainItemResult itemResult;


    public void setIsAvailable(Short isAvailable) {
        this.isAvailable = isAvailable;
    }

    public InspectionMaintainItemResult getItemResult() {
        return itemResult;
    }

    public void setItemResult(InspectionMaintainItemResult itemResult) {
        this.itemResult = itemResult;
    }

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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getIsAvailable() {
        return isAvailable;
    }

    public Short getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Short indexNum) {
        this.indexNum = indexNum;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Integer getNoCheck() {
        return noCheck;
    }

    public void setNoCheck(Integer noCheck) {
        this.noCheck = noCheck;
    }

    public String getStepImgUrls() {
        return stepImgUrls;
    }

    public void setStepImgUrls(String stepImgUrls) {
        this.stepImgUrls = stepImgUrls;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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
}