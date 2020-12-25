package com.ics.dataDesources.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.InspectionMaintainClassMapper;
import com.ics.dataDesources.model.InspectionMaintainClass;
import com.ics.dataDesources.service.InspectionMaintainClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("inspectionMaintainClassService")
public class InspectionMaintainClassServiceImpl extends ServiceImpl<InspectionMaintainClassMapper, InspectionMaintainClass> implements InspectionMaintainClassService {

	@Autowired
	private InspectionMaintainClassMapper inspectionMaintainClassMapper;

	
}
