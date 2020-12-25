package com.ics.remoteMonitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.remoteMonitor.mapper.AlarmInfoMapper;
import com.ics.remoteMonitor.model.AlarmInfo;
import com.ics.remoteMonitor.service.AlarmInfoService;

/**
 * 报警信息 服务实现类
 * @author jjz
 *
 * 2019年12月2日
 */
@Service("alarmInfoService")
public class AlarmInfoServiceImpl extends ServiceImpl<AlarmInfoMapper, AlarmInfo> implements AlarmInfoService {

	@Autowired
	private AlarmInfoMapper alarmInfoMapper;
	@Override
	public Page<AlarmInfo> selectRelationPageList(Page<AlarmInfo> page,
			Wrapper<AlarmInfo> wrapper) {
		page.setRecords(alarmInfoMapper.selectRelationPageList(page, wrapper));
		return page;
	}
	@Override
	public List<AlarmInfo> selectRelationList(Wrapper<AlarmInfo> wrapper) {
		return alarmInfoMapper.selectRelationList(wrapper);
	}
	@Override
	public AlarmInfo selectRelationById(String id) {
		return alarmInfoMapper.selectRelationById(id);
	}
	@Override
	public Page<AlarmInfo> selectRelationPageAlarmList(Page<AlarmInfo> page, Wrapper<AlarmInfo> wrapper) {
		page.setRecords(alarmInfoMapper.selectRelationPageAlarmList(page, wrapper));
		return page;
	}
	@Override
	public List<AlarmInfo> selectRelationAlarmList(Wrapper<AlarmInfo> wrapper) {
		return alarmInfoMapper.selectRelationAlarmList(wrapper);
	}

	@Override
	public Integer getCount(Wrapper<AlarmInfo> wrapper) {
			return alarmInfoMapper.getCount(wrapper);
	}



}
