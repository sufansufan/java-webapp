package com.ics.device.mapper;

import com.ics.device.model.DeviceInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
  * 设备信息表 Mapper 接口
 * </p>
 *
 * @author jjz
 * @since 2019-07-22
 */
public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {
	
	List<DeviceInfo> selectRelationList(Page<DeviceInfo> page, @Param("ew") Wrapper<DeviceInfo> wrapper);
	List<DeviceInfo> selectDeviceInfoList( @Param("ew") Wrapper<DeviceInfo> wrapper);
}