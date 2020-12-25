package com.ics.remoteMonitor.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.remoteMonitor.model.MaintenanceRecord;

/**
 * <p>
  * 维修保养表 Mapper 接口
 * </p>
 *
 * @author jjz
 * @since 2019-12-2
 */
public interface MaintenanceRecordMapper extends BaseMapper<MaintenanceRecord> {
	
	List<MaintenanceRecord> selectRelationPageList(Page<MaintenanceRecord> page, @Param("ew") Wrapper<MaintenanceRecord> wrapper);

	List<MaintenanceRecord> selectRelationList(@Param("ew") Wrapper<MaintenanceRecord> wrapper);
	
	MaintenanceRecord selectRelationById(String id);
	

}