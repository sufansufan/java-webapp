package com.ics.dataDesources.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.MachineMapper;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.Team;
import com.ics.dataDesources.service.MachineService;
import com.ics.system.model.SysOrg;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("machineService")
public class MachineServiceImpl extends ServiceImpl<MachineMapper, ControlMachine> implements MachineService {

    @Autowired
    private MachineMapper machineMapper;


    /**
     * 根据条件查询设备列表（分页）
     * @author suxiangrong
     * @date 2020/10/16
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.plugins.Page<com.ics.dataDesources.model.ControlMachine>
     */
    @Override
    public Page<ControlMachine> selectRelationPageList(Page<ControlMachine> page, Wrapper<ControlMachine> wrapper) {
        page.setRecords(machineMapper.selectMachinePageList(page, wrapper));
        return page;
    }

    /**
     * 根据条件查询设备列表
     * @author suxiangrong
     * @date 2020/10/16
     * @param wrapper
     * @return java.util.List<com.ics.dataDesources.model.ControlMachine>
     */
    @Override
    public List<ControlMachine> selectRelationList(Wrapper<ControlMachine> wrapper) {
        return machineMapper.selectMachineList(wrapper);
    }

    /**
     * 根据条件查询班组信息列表
     * @author suxiangrong
     * @date 2020/10/16
     * @param wrapper
     * @return java.util.List<com.ics.dataDesources.model.Team>
     */
    @Override
    public List<Team> selectTeamList(@Param("ew") Wrapper<Team> wrapper) {
        return machineMapper.selectTeamList(wrapper);
    }

    @Override
    public List<SysOrg> selectMachineOrgList() {
        return machineMapper.selectMachineOrgList();
    }
}
