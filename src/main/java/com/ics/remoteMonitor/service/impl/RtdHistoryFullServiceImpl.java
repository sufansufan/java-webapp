package com.ics.remoteMonitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.remoteMonitor.mapper.RtdHistoryFullMapper;
import com.ics.remoteMonitor.model.RtdHistoryFull;
import com.ics.remoteMonitor.service.RtdHistoryFullService;

/**
 * 设备实时数据历史信息表 服务实现类
 * @author jjz
 *
 * 2019年12月3日
 */
@Service("rtdHistoryFullService")
public class RtdHistoryFullServiceImpl extends ServiceImpl<RtdHistoryFullMapper, RtdHistoryFull> implements RtdHistoryFullService {

	@Autowired
	private RtdHistoryFullMapper rtdHistoryFullMapper;
	
	@Override
	public Integer getCount(Map<String, Object> params) {
		int result = rtdHistoryFullMapper.getCount(params);
		return result;
	}

	@Override
	public List<RtdHistoryFull> getList(Map<String, Object> params) {
		return rtdHistoryFullMapper.getList(params);
	}

	@Override
	public Integer getMaxCount(Map<String, Object> params) {
		int result = rtdHistoryFullMapper.getMaxCount(params);
		return result;
	}

	@Override
	public Integer addBatchRtdHistory(Map<String, Object> params) {
		Integer result = rtdHistoryFullMapper.addBatchRtdHistory(params);
		return result;
	}


	
}
