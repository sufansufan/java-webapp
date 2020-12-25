package com.ics.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.device.mapper.DeviceInfoMapper;
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;

/**
 * 设备信息表 服务实现类
 * @author jjz
 *
 * 2019年7月22日
 */
@Service("deviceInfoService")
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements DeviceInfoService {

	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	
	@Override
	public Page<DeviceInfo> selectRelationList(Page<DeviceInfo> page, Wrapper<DeviceInfo> wrapper) {
		page.setRecords(deviceInfoMapper.selectRelationList(page, wrapper));
		return page;
	}

	@Override
	public List<DeviceInfo> selectDeviceInfoList(Wrapper<DeviceInfo> wrapper) {
		return deviceInfoMapper.selectDeviceInfoList(wrapper);
	}
}
