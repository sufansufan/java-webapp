package com.ics.dataDesources.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.CondensingDeviceMapper;
import com.ics.dataDesources.model.CondensingDevice;
import com.ics.dataDesources.service.CondensingDeviceService;

/**
 * 冷凝机设备信息表 服务实现类
 * @author jjz
 *
 * 2019年11月26日
 */
@Service("condensingDeviceService")
public class CondensingDeviceServiceImpl extends ServiceImpl<CondensingDeviceMapper, CondensingDevice> implements CondensingDeviceService {

	@Autowired
	private CondensingDeviceMapper condensingDeviceMapper;
	
	@Override
	public Page<CondensingDevice> selectRelationPageList(Page<CondensingDevice> page, Wrapper<CondensingDevice> wrapper) {
		page.setRecords(condensingDeviceMapper.selectRelationPageList(page, wrapper));
		return page;
	}

	@Override
	public List<CondensingDevice> selectRelationList(Wrapper<CondensingDevice> wrapper) {
		return condensingDeviceMapper.selectRelationList(wrapper);
	}
}
