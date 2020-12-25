package com.ics.dataDesources.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 点检/保养模版
 */
@TableName("inspection_maintain_template")
public class InspectionMaintainTemplate extends Model<InspectionMaintainTemplate> {

    /**
     * 点检分类id
     */
    private String id;

    /**
     *
     */
    @TableField("machine_id")
    private String machineId;

    /**
     * 点检模版类型
     */
    @TableField("template_type")
    private String templateType;

    /**
     * 1(可用)/0(删除)
     */
    @TableField("is_available")
    private Integer isAvailable;

    @TableField(exist = false)
    private List<InspectionMaintainClass> classList = new ArrayList<>();

    /**
     * 创建日期
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新日期
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

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
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

    public List<InspectionMaintainClass> getClassList() {
        return classList;
    }

    public void setClassList(List<InspectionMaintainClass> classList) {
        this.classList = classList;
    }

    public String getTemplateTypeName() {
        if(templateType == null) return null;
        switch (templateType) {
            case "R":
                return "日常";
            case "D":
                return "定期";
            case "B":
                return "保养";
            default:
                return "";
        }
    }

}