package com.ics.inspectionMaintenance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ics.dataDesources.model.ControlMachine;

import java.io.Serializable;
import java.util.Date;

/**
 * 点检项异常处理
 */
@TableName("exception")
public class Exception extends Model<Exception> {

    /**
     * 异常id
     */
    private String id;

    /**
     * 异常源id（点检保养产生的异常这里是itemid）
     */
    @TableField("source_id")
    private String sourceId;


    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createdTime;

    /**
     * 异常描述
     */
    @TableField("describe")
    private String describe;

    /**
     * 异常类型（IM(点检保养)/BO(break out突发异常)）
     */
    @TableField("exception_type")
    private String exceptionType;

    /**
     * 检出人Id
     */
    @TableField("checkout_user_id")
    private String checkoutUserId;


    /**
     * 设备ID
     */
    @TableField("machine_id")
    private String machineId;

    @TableField(exist = false)
    private ControlMachine controlMachine;
    /**
     * 异常图片链接
     */
    @TableField("exception_img_urls")
    private String exceptionImgUrls;

    /**
     * 检出人姓名
     */
    @TableField(exist = false)
    private String checkoutUserName;

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


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionImgUrls() {
        return exceptionImgUrls;
    }

    public void setExceptionImgUrls(String exceptionImgUrls) {
        this.exceptionImgUrls = exceptionImgUrls;
    }

    public String getMachineId() {
        return machineId;
    }

    public ControlMachine getControlMachine() {
        return controlMachine;
    }

    public void setControlMachine(ControlMachine controlMachine) {
        this.controlMachine = controlMachine;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getCheckoutUserId() {
        return checkoutUserId;
    }

    public void setCheckoutUserId(String checkoutUserId) {
        this.checkoutUserId = checkoutUserId;
    }

    public String getCheckoutUserName() {
        return checkoutUserName;
    }

    public void setCheckoutUserName(String checkoutUserName) {
        this.checkoutUserName = checkoutUserName;
    }

    @Override
    public String toString() {
        return "Exception{" +
                "id='" + id + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", createdTime=" + createdTime +
                ", describe='" + describe + '\'' +
                ", exceptionType='" + exceptionType + '\'' +
                ", checkoutUserId='" + checkoutUserId + '\'' +
                ", machineId='" + machineId + '\'' +
                ", controlMachine=" + controlMachine +
                ", exceptionImgUrls='" + exceptionImgUrls + '\'' +
                '}';
    }
}