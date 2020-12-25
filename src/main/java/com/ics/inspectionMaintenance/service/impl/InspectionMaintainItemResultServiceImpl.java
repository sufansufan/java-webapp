package com.ics.inspectionMaintenance.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.inspectionMaintenance.mapper.InspectionMaintainItemResultMapper;
import com.ics.inspectionMaintenance.model.InspectionMaintainItemResult;
import com.ics.inspectionMaintenance.service.InspectionMaintainItemResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("inspectionMaintainItemResultService")
public class InspectionMaintainItemResultServiceImpl extends ServiceImpl<InspectionMaintainItemResultMapper, InspectionMaintainItemResult> implements InspectionMaintainItemResultService {

    @Autowired
    private InspectionMaintainItemResultMapper mapper;

    @Override
    public List<InspectionMaintainItemResult> getItemResultsByScheduleIds(Wrapper<InspectionMaintainItemResult> wrapper) {
        return mapper.getItemResultsByScheduleIds(wrapper);
    }
}
