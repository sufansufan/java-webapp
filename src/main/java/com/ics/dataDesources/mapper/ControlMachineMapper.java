package com.ics.dataDesources.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.ControlMachine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ControlMachineMapper extends BaseMapper<ControlMachine> {

    List<ControlMachine> selectRelationPageList(Page<ControlMachine> page, @Param("ew") Wrapper<ControlMachine> wrapper);

    List<ControlMachine> selectRelationList(@Param("ew") Wrapper<ControlMachine> wrapper);

    Integer getCount(@Param("ew") Wrapper<ControlMachine> wrapper);

    ControlMachine selectMachineById(@Param("id") String id);
}
