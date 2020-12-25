package com.ics.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.ics.system.model.SysDictionaryItem;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典项表 服务类
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
public interface SysDictionaryItemService extends IService<SysDictionaryItem> {

	Map<String, String> getValueNameMap(String dictCode);

	Map<Integer, String> getIntValueNameMap(String dictCode);

	Map<String, Integer> getNameIntValueMap(String dictCode);

	List<SysDictionaryItem> selectByDict(String dictCode);

	List<SysDictionaryItem> selectRelationList(@Param("ew") Wrapper<SysDictionaryItem> wrapper);
}
