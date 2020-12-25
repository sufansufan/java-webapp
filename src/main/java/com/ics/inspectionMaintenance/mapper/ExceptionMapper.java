package com.ics.inspectionMaintenance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ics.inspectionMaintenance.model.Exception;
import org.apache.ibatis.annotations.Param;

public interface ExceptionMapper extends BaseMapper<Exception>  {
    Exception selectExceptionById(@Param("id") String id);
}
