package com.ics.inspectionMaintenance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 点检项异常处理
 */
@TableName("exception_fix")
public class ExceptionFix extends Model<ExceptionFix> {

    /**
     * 点检保养异常处理id
     */
    private String id;

    /**
     * 异常处理项id
     */
    @TableField("exception_id")
    private String exceptionId;


    /**
     * 异常处理描述
     */
    @TableField("describe")
    private String describe;

    /**
     * 维修图片链接
     */
    @TableField("fix_img_urls")
    private String fixImgUrls;

    @TableField(exist = false)
    private Date operateDate;

    @TableField(exist = false)
    private String itrmResultId;


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

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFixImgUrls() {
        return fixImgUrls;
    }

    public void setFixImgUrls(String fixImgUrls) {
        this.fixImgUrls = fixImgUrls;
    }

    public String getItrmResultId() {
        return itrmResultId;
    }

    public void setItrmResultId(String itrmResultId) {
        this.itrmResultId = itrmResultId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }
}