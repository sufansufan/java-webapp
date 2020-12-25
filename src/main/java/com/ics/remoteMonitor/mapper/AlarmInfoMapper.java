package com.ics.remoteMonitor.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.remoteMonitor.model.AlarmInfo;

/**
 * <p>
  * 报警信息表 Mapper 接口
 * </p>
 *
 * @author jjz
 * @since 2019-12-2
 */
public interface AlarmInfoMapper extends BaseMapper<AlarmInfo> {

	List<AlarmInfo> selectRelationPageList(Page<AlarmInfo> page, @Param("ew") Wrapper<AlarmInfo> wrapper);

	List<AlarmInfo> selectRelationList(@Param("ew") Wrapper<AlarmInfo> wrapper);

	AlarmInfo selectRelationById(String id);

	List<AlarmInfo> selectRelationPageAlarmList(Page<AlarmInfo> page, @Param("ew") Wrapper<AlarmInfo> wrapper);

	List<AlarmInfo> selectRelationAlarmList(@Param("ew") Wrapper<AlarmInfo> wrapper);

	Integer getCount(@Param("ew") Wrapper<AlarmInfo> wrapper);
}
