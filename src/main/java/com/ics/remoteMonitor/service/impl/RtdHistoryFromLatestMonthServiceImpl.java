package com.ics.remoteMonitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.remoteMonitor.mapper.RtdHistoryFromLatestMonthMapper;
import com.ics.remoteMonitor.model.RtdHistoryFromLatestMonth;
import com.ics.remoteMonitor.service.RtdHistoryFromLatestMonthService;

/**
 * 设备实时数据历史信息表 服务实现类
 * @author jjz
 *
 * 2019年12月3日
 */
@Service("rtdHistoryFromLastMonthService")
public class RtdHistoryFromLatestMonthServiceImpl extends ServiceImpl<RtdHistoryFromLatestMonthMapper, RtdHistoryFromLatestMonth> implements RtdHistoryFromLatestMonthService {

	@Autowired
	private RtdHistoryFromLatestMonthMapper mapper;
	
	@Override
	public Integer getCount(Map<String, Object> params) {
		int result = mapper.getCount(params);
		return result;
	}

	@Override
	public List<RtdHistoryFromLatestMonth> getList(Map<String, Object> params) {
		return mapper.getList(params);
	}

	@Override
	public Integer getMaxCount(Map<String, Object> params) {
		int result = mapper.getMaxCount(params);
		return result;
	}

	@Override
	public Integer addBatchRtdHistory(Map<String, Object> params) {
		Integer result = mapper.addBatchRtdHistory(params);
		return result;
	}


	
}
