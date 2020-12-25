package com.ics.system.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.system.mapper.SysDictionaryItemMapper;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.service.SysDictionaryItemService;
import com.ics.system.service.SysDictionaryService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典项表 服务实现类
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
@Service("sysDictionaryItemService")
public class SysDictionaryItemServiceImpl extends ServiceImpl<SysDictionaryItemMapper, SysDictionaryItem> implements SysDictionaryItemService {

	@Autowired
	private SysDictionaryService sysDictionaryService;

	@Autowired
	private SysDictionaryItemMapper sysDictionaryItemMapper;

	@Override
	public Map<String, String> getValueNameMap(String dictCode) {
		EntityWrapper<SysDictionaryItem> ew = new EntityWrapper<>();
		ew.setEntity(new SysDictionaryItem());
		ew.eq("dict_code", dictCode);
		ew.eq("status", 1);
		ew.orderBy("sort_idx", true);
		List<SysDictionaryItem> list = sysDictionaryItemMapper.selectListByDict(ew);
		Map<String, String> map = new LinkedHashMap<>();
		if(list != null && list.size()>0) {
			for (SysDictionaryItem model : list) {
				map.put(model.getItemValue(), model.getItemName());
			}
		}
		return map;
	}

	@Override
	public Map<Integer, String> getIntValueNameMap(String dictCode) {

		EntityWrapper<SysDictionaryItem> ew = new EntityWrapper<>();
		ew.setEntity(new SysDictionaryItem());
		ew.eq("dict_code", dictCode);
		ew.eq("status", 1);
		ew.orderBy("sort_idx", true);
		List<SysDictionaryItem> list = sysDictionaryItemMapper.selectListByDict(ew);
		Map<Integer, String> map = new LinkedHashMap<>();
		if(list != null && list.size()>0) {
			for (SysDictionaryItem model : list) {
				map.put(Integer.parseInt(model.getItemValue()), model.getItemName());
			}
		}
		return map;
	}

	@Override
	public List<SysDictionaryItem> selectByDict(String dictCode) {

		EntityWrapper<SysDictionaryItem> ew = new EntityWrapper<>();
		ew.setEntity(new SysDictionaryItem());
		ew.eq("dict_code", dictCode);
		ew.eq("status", 1);
		ew.orderBy("sort_idx", true);
		List<SysDictionaryItem> list = sysDictionaryItemMapper.selectListByDict(ew);
		return list;
	}

	@Override
	public Map<String, Integer> getNameIntValueMap(String dictCode) {
		EntityWrapper<SysDictionaryItem> ew = new EntityWrapper<>();
		ew.setEntity(new SysDictionaryItem());
		ew.eq("dict_code", dictCode);
		ew.eq("status", 1);
		ew.orderBy("sort_idx", true);
		List<SysDictionaryItem> list = sysDictionaryItemMapper.selectListByDict(ew);
		Map<String, Integer> map = new LinkedHashMap<>();
		if(list != null && list.size()>0) {
			for (SysDictionaryItem model : list) {
				map.put(model.getItemName(), Integer.parseInt(model.getItemValue()));
			}
		}
		return map;
	}

	@Override
	public List<SysDictionaryItem> selectRelationList(Wrapper<SysDictionaryItem> wrapper) {
		return sysDictionaryItemMapper.selectRelationList(wrapper);
	}

}
