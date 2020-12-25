package com.ics.dataDesources.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ics.dataDesources.model.InspectionMaintainClass;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 点检报告分类表 Mapper 接口
 * @Date 2020/10/26 22:32
 * @Author by yuankeyan
 */
public interface InspectionMaintainClassMapper extends BaseMapper<InspectionMaintainClass> {

    /**
     * @Description 根据模版id删除分类数据
     * @Date 2020/10/29 13:01
     * @Author by yuankeyan
     */
    void deleteByTemplateId(@Param("templateId") String templateId);

    /**
     * @Description 软删除分类信息
     * @Date 2020/11/13 15:38
     * @Author by yuankeyan
     */
    void updateStatusByTemplateId(String templateId);
}