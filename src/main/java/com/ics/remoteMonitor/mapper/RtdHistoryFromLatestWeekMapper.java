package com.ics.remoteMonitor.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ics.remoteMonitor.model.RtdHistoryFromLatestWeek;
import com.ics.remoteMonitor.model.RtdHistoryFull;

/**
 * <p>
  * 实时数据历史表 Mapper 接口
 * </p>
 *
 * @author jjz
 * @since 2019-12-3
 */
public interface RtdHistoryFromLatestWeekMapper extends BaseMapper<RtdHistoryFromLatestWeek> {
	
	Integer getCount(Map<String, Object> params);
	
	List<RtdHistoryFromLatestWeek> getList(Map<String, Object> params);
	
	Integer getMaxCount(Map<String, Object> params);
	
	Integer addBatchRtdHistory(Map<String, Object> params);
	
}