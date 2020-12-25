package com.ics.dataDesources.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ics.dataDesources.model.InspectionMaintainItem;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 点检报告项表 Mapper 接口
 * @Date 2020/10/26 22:32
 * @Author by yuankeyan
 */
public interface InspectionMaintainItemMapper extends BaseMapper<InspectionMaintainItem> {

    /**
     * @Description 根据模版id删除项
     * @Date 2020/10/29 13:01
     * @Author by yuankeyan
     */
    void deleteByTemplateId(@Param("templateId") String templateId);

    /**
     * @Description 软删除item信息
     * @Date 2020/11/13 15:39
     * @Author by yuankeyan
     */
    void updateStatusByTemplateId(String templateId);
}