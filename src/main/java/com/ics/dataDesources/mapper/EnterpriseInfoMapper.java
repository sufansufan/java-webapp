package com.ics.dataDesources.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.EnterpriseInfo;

/**
 * <p>
  * 企业表 Mapper 接口
 * </p>
 *
 * @author jjz
 * @since 2019-12-2
 */
public interface EnterpriseInfoMapper extends BaseMapper<EnterpriseInfo> {
	
	List<EnterpriseInfo> selectRelationPageList(Page<EnterpriseInfo> page, @Param("ew") Wrapper<EnterpriseInfo> wrapper);

	List<EnterpriseInfo> selectRelationList(@Param("ew") Wrapper<EnterpriseInfo> wrapper);
	
	EnterpriseInfo selectRelationById(String id);

}