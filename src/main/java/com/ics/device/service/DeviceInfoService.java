package com.ics.device.service;

import com.ics.device.model.DeviceInfo;

import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * 设备信息 服务类
 * @author jjz
 *
 * 2019年7月22日
 */
public interface DeviceInfoService extends IService<DeviceInfo> {
	
	Page<DeviceInfo> selectRelationList(Page<DeviceInfo> page, Wrapper<DeviceInfo> wrapper);
	List<DeviceInfo> selectDeviceInfoList(Wrapper<DeviceInfo> wrapper);
}
