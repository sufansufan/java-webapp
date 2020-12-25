package com.ics.dataDesources.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.MonitorFactor;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ics.dataDesources.model.MonitorFactorTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 监测因子表 Mapper 接口
 * </p>
 *
 * @author jjz
 * @since 2019-07-22
 */
public interface MonitorFactorMapper extends BaseMapper<MonitorFactor> {
    List<MonitorFactor> selectRelationPageList(Page<MonitorFactor> page, @Param("ew") Wrapper<MonitorFactor> wrapper);

    List<MonitorFactor> selectRelationList(@Param("ew") Wrapper<MonitorFactor> wrapper);
}