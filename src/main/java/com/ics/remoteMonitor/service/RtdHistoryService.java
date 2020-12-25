package com.ics.remoteMonitor.service;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.ics.remoteMonitor.model.RtdHistory;

/**
 * 实时数据历史信息 服务类
 * @author jjz
 *
 * 2019年12月3日
 */
public interface RtdHistoryService extends IService<RtdHistory> {
	
	Integer getCount(Map<String, Object> params);
	
	List<RtdHistory> getList(Map<String, Object> params);
	
	Integer getMaxCount(Map<String, Object> params);
	
	Integer addBatchRtdHistory(Map<String, Object> params);
	
}
