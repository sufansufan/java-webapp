package com.ics.remoteMonitor.service;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.ics.remoteMonitor.model.RtdHistoryFromLatestMonth;
/**
 * 实时数据历史信息 服务类
 * @author jjz
 *
 * 2019年12月3日
 */
public interface RtdHistoryFromLatestMonthService extends IService<RtdHistoryFromLatestMonth> {
	
	Integer getCount(Map<String, Object> params);
	
	List<RtdHistoryFromLatestMonth> getList(Map<String, Object> params);
	
	Integer getMaxCount(Map<String, Object> params);
	
	Integer addBatchRtdHistory(Map<String, Object> params);
	
}
