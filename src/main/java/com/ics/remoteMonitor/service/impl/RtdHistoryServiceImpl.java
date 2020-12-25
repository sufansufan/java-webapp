package com.ics.remoteMonitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.remoteMonitor.mapper.RtdHistoryMapper;
import com.ics.remoteMonitor.model.RtdHistory;
import com.ics.remoteMonitor.service.RtdHistoryService;

/**
 * 设备实时数据历史信息表 服务实现类
 * @author jjz
 *
 * 2019年12月3日
 */
@Service("rtdHistoryService")
public class RtdHistoryServiceImpl extends ServiceImpl<RtdHistoryMapper, RtdHistory> implements RtdHistoryService {

	@Autowired
	private RtdHistoryMapper rtdHistoryMapper;
	
	@Override
	public Integer getCount(Map<String, Object> params) {
		int result = rtdHistoryMapper.getCount(params);
		return result;
	}

	@Override
	public List<RtdHistory> getList(Map<String, Object> params) {
		return rtdHistoryMapper.getList(params);
	}

	@Override
	public Integer getMaxCount(Map<String, Object> params) {
		int result = rtdHistoryMapper.getMaxCount(params);
		return result;
	}

	@Override
	public Integer addBatchRtdHistory(Map<String, Object> params) {
		Integer result = rtdHistoryMapper.addBatchRtdHistory(params);
		return result;
	}


	
}
