package com.ics.dataDesources.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.Team;
import com.ics.system.model.SysOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MachineService extends IService<ControlMachine> {
    /**
     * 根据条件查询设备列表（分页）
     * @author suxiangrong
     * @date 2020/10/16
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.plugins.Page<com.ics.dataDesources.model.ControlMachine>
     */
    Page<ControlMachine> selectRelationPageList(Page<ControlMachine> page, @Param("ew") Wrapper<ControlMachine> wrapper);

    /**
     * 根据条件查询设备列表
     * @author suxiangrong
     * @date 2020/10/16
     * @param wrapper
     * @return com.baomidou.mybatisplus.plugins.Page<com.ics.dataDesources.model.ControlMachine>
     */
    List<ControlMachine> selectRelationList(@Param("ew") Wrapper<ControlMachine> wrapper);

    /**
     * 根据条件查询班组信息列表
     * @author suxiangrong
     * @date 2020/10/16
     * @param wrapper
     * @return java.util.List<com.ics.dataDesources.model.Team>
     */
    List<Team> selectTeamList(@Param("ew") Wrapper<Team> wrapper);

    /**
     * @Description 根据设备获取部门信息
     * @Date 2020/11/11 15:53
     * @Author by yuankeyan
     */
    List<SysOrg> selectMachineOrgList();
}
