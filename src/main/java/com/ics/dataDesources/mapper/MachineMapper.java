package com.ics.dataDesources.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.Team;
import com.ics.system.model.SysOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MachineMapper extends BaseMapper<ControlMachine> {

    List<ControlMachine> selectMachinePageList(Page<ControlMachine> page, @Param("ew") Wrapper<ControlMachine> wrapper);

    List<ControlMachine> selectMachineList(@Param("ew") Wrapper<ControlMachine> wrapper);

    List<Team> selectTeamList(@Param("ew") Wrapper<Team> wrapper);

    List<SysOrg> selectMachineOrgList();
}
