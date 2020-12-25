package com.ics.dataDesources.mapper;

import com.ics.dataDesources.model.MonitorFactorTemplate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
  * 监测因子模板表 Mapper 接口
 * </p>
 *
 * @author jjz
 * @since 2019-07-22
 */
public interface MonitorFactorTemplateMapper extends BaseMapper<MonitorFactorTemplate> {
	List<MonitorFactorTemplate> selectRelationPageList(Page<MonitorFactorTemplate> page, @Param("ew") Wrapper<MonitorFactorTemplate> wrapper);

	List<MonitorFactorTemplate> selectRelationList(@Param("ew") Wrapper<MonitorFactorTemplate> wrapper);

	List<MonitorFactorTemplate> selectList(@Param("ew") Wrapper<MonitorFactorTemplate> wrapper);
}