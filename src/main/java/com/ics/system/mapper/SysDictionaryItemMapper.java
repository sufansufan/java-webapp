package com.ics.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ics.system.model.SysDictionaryItem;

/**
 * <p>
  * 数据字典项表 Mapper 接口
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
public interface SysDictionaryItemMapper extends BaseMapper<SysDictionaryItem> {
	List<SysDictionaryItem> selectListByDict(@Param("ew") Wrapper<SysDictionaryItem> wrapper);
	List<SysDictionaryItem> selectRelationList(@Param("ew") Wrapper<SysDictionaryItem> wrapper);
}
