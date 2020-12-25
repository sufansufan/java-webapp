package com.ics.remoteMonitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.remoteMonitor.mapper.MaintenanceRecordMapper;
import com.ics.remoteMonitor.model.MaintenanceRecord;
import com.ics.remoteMonitor.service.MaintenanceRecordService;

/**
 * 维修保养信息 服务实现类
 * @author jjz
 *
 * 2019年12月2日
 */
@Service("maintenanceRecordService")
public class MaintenanceRecordServiceImpl extends ServiceImpl<MaintenanceRecordMapper, MaintenanceRecord> implements MaintenanceRecordService {

	@Autowired
	private MaintenanceRecordMapper maintenanceRecordMapper;
	@Override
	public Page<MaintenanceRecord> selectRelationPageList(Page<MaintenanceRecord> page,
			Wrapper<MaintenanceRecord> wrapper) {
		page.setRecords(maintenanceRecordMapper.selectRelationPageList(page, wrapper));
		return page;
	}
	@Override
	public List<MaintenanceRecord> selectRelationList(Wrapper<MaintenanceRecord> wrapper) {
		return maintenanceRecordMapper.selectRelationList(wrapper);
	}
	@Override
	public MaintenanceRecord selectRelationById(String id) {
		return maintenanceRecordMapper.selectRelationById(id);
	}

	
}
