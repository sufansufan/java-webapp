package com.ics.remoteMonitor.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ics.remoteMonitor.model.MaintenanceRecord;

/**
 * 维修保养信息 服务类
 * @author jjz
 *
 * 2019年12月2日
 */
public interface MaintenanceRecordService extends IService<MaintenanceRecord> {
	
	Page<MaintenanceRecord> selectRelationPageList(Page<MaintenanceRecord> page, Wrapper<MaintenanceRecord> wrapper);
	
	List<MaintenanceRecord> selectRelationList(Wrapper<MaintenanceRecord> wrapper);
	
	MaintenanceRecord selectRelationById(String id);
	
}
