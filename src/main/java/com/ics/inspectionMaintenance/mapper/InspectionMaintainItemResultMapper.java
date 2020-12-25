package com.ics.inspectionMaintenance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.inspectionMaintenance.model.ImResultForCount;
import com.ics.inspectionMaintenance.model.InspectionMaintainItemResult;
import com.ics.inspectionMaintenance.model.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectionMaintainItemResultMapper extends BaseMapper<InspectionMaintainItemResult> {
    List<InspectionMaintainItemResult> getItemResultsByScheduleIds(@Param("ew") Wrapper<InspectionMaintainItemResult> wrapper);
    List<ImResultForCount> selectImResultByTaskInfo(@Param("ew") Wrapper<ImResultForCount> wrapper);
}
