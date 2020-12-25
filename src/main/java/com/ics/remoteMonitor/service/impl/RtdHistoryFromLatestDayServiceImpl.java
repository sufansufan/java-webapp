package com.ics.remoteMonitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.remoteMonitor.mapper.RtdHistoryFromLatestDayMapper;
import com.ics.remoteMonitor.model.RtdHistoryFromLatestDay;
import com.ics.remoteMonitor.service.RtdHistoryFromLatestDayService;

/**
 * 设备实时数据历史信息表 服务实现类
 * @author jjz
 *
 * 2019年12月3日
 */
@Service("rtdHistoryFromLatestDayService")
public class RtdHistoryFromLatestDayServiceImpl extends ServiceImpl<RtdHistoryFromLatestDayMapper, RtdHistoryFromLatestDay> implements RtdHistoryFromLatestDayService {

	@Autowired
	private RtdHistoryFromLatestDayMapper mapper;
	
	@Override
	public Integer getCount(Map<String, Object> params) {
		int result = mapper.getCount(params);
		return result;
	}

	@Override
	public List<RtdHistoryFromLatestDay> getList(Map<String, Object> params) {
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
