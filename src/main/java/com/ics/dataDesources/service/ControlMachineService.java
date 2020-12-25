package com.ics.dataDesources.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ics.dataDesources.model.ControlMachine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ControlMachineService extends IService<ControlMachine> {
    Page<ControlMachine> selectRelationPageList(Page<ControlMachine> page, @Param("ew") Wrapper<ControlMachine> wrapper);

    List<ControlMachine> selectRelationList(@Param("ew") Wrapper<ControlMachine> wrapper);

    Integer getCount(@Param("ew") Wrapper<ControlMachine> wrapper);

    void fillMachineEmptyField(List<ControlMachine> machines);

    ControlMachine getFullFieldControleMachine(String machineId);

    /**
     * @Description 获取单个设备信息
     * @Date 2020/11/12 22:07
     * @Author by yuankeyan
     */
    ControlMachine selectMachineById(String id);
}
