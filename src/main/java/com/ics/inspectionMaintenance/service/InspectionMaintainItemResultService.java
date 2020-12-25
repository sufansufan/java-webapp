package com.ics.inspectionMaintenance.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.ics.inspectionMaintenance.model.InspectionMaintainItemResult;
import com.ics.inspectionMaintenance.model.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface InspectionMaintainItemResultService extends IService<InspectionMaintainItemResult> {
    List<InspectionMaintainItemResult> getItemResultsByScheduleIds(@Param("ew") Wrapper<InspectionMaintainItemResult> wrapper);
}
