package com.ics.remoteMonitor.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ics.remoteMonitor.model.AlarmInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 报警信息 服务类
 * @author jjz
 *
 * 2019年12月2日
 */
public interface AlarmInfoService extends IService<AlarmInfo> {

	Page<AlarmInfo> selectRelationPageList(Page<AlarmInfo> page, Wrapper<AlarmInfo> wrapper);

	List<AlarmInfo> selectRelationList(Wrapper<AlarmInfo> wrapper);

	AlarmInfo selectRelationById(String id);

	Page<AlarmInfo> selectRelationPageAlarmList(Page<AlarmInfo> page, Wrapper<AlarmInfo> wrapper);

	List<AlarmInfo> selectRelationAlarmList(Wrapper<AlarmInfo> wrapper);

	Integer getCount(@Param("ew") Wrapper<AlarmInfo> wrapper);
}
