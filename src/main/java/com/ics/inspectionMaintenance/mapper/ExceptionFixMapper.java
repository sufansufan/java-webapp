package com.ics.inspectionMaintenance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ics.inspectionMaintenance.model.Exception;
import com.ics.inspectionMaintenance.model.ExceptionFix;
import com.ics.inspectionMaintenance.model.TaskForApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExceptionFixMapper extends BaseMapper<ExceptionFix>  {
    List<ExceptionFix> selectExceptionFixWithItemResultIds(@Param("ew") Wrapper<ExceptionFix> wrapper);
}
