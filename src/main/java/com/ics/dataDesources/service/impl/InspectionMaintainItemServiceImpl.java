package com.ics.dataDesources.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.InspectionMaintainItemMapper;
import com.ics.dataDesources.model.InspectionMaintainItem;
import com.ics.dataDesources.service.InspectionMaintainItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("inspectionMaintainItemService")
public class InspectionMaintainItemServiceImpl extends ServiceImpl<InspectionMaintainItemMapper, InspectionMaintainItem> implements InspectionMaintainItemService {

	@Autowired
	private InspectionMaintainItemMapper inspectionMaintainItemMapper;

	
}
