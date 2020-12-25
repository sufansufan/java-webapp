package com.ics.dataDesources.mapper;

import com.ics.dataDesources.model.CondensingDevice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
  * 冷凝机设备表 Mapper 接口
 * </p>
 *
 * @author jjz
 * @since 2019-11-26
 */
public interface CondensingDeviceMapper extends BaseMapper<CondensingDevice> {	
	List<CondensingDevice> selectRelationPageList(Page<CondensingDevice> page, @Param("ew") Wrapper<CondensingDevice> wrapper);

	List<CondensingDevice> selectRelationList(@Param("ew") Wrapper<CondensingDevice> wrapper);
}