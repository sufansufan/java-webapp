package com.ics.dataDesources.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.mapper.ControlMachineMapper;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.model.Team;
import com.ics.dataDesources.service.ControlMachineService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.service.EnterpriseInfoService;
import com.ics.dataDesources.service.MachineService;
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.model.SysOrg;
import com.ics.system.service.SysDictionaryItemService;
import com.ics.system.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("controlMachineService")
public class ControlMachineServiceImpl extends ServiceImpl<ControlMachineMapper, ControlMachine> implements ControlMachineService {

    @Autowired
    private ControlMachineMapper controlMachineMapper;


    @Override
    public Page<ControlMachine> selectRelationPageList(Page<ControlMachine> page, Wrapper<ControlMachine> wrapper) {
        page.setRecords(controlMachineMapper.selectRelationPageList(page, wrapper));
        return page;
    }

    @Override
    public List<ControlMachine> selectRelationList(Wrapper<ControlMachine> wrapper) {
        return controlMachineMapper.selectRelationList(wrapper);
    }

    @Override
    public Integer getCount(Wrapper<ControlMachine> wrapper) {
        return controlMachineMapper.getCount(wrapper);
    }

    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    @Autowired
    private SysDictionaryItemService sysDictionaryItemService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private MachineService machineService;

    @Autowired
    private SysOrgService sysOrgService;

    @Override
    public ControlMachine getFullFieldControleMachine(String machineId) {
        ControlMachine controlMachine = controlMachineMapper.selectById(machineId);
        List<ControlMachine> controlMachines = new ArrayList<>();
        controlMachines.add(controlMachine);
        fillMachineEmptyField(controlMachines);
        return controlMachine;
    }

    @Override
    public ControlMachine selectMachineById(String id) {
        return controlMachineMapper.selectMachineById(id);
    }

    @Override
    public void fillMachineEmptyField(List<ControlMachine> machines) {
        //所属企业列表
        EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
        List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
        Map<String, EnterpriseInfo> enterpriseInfoMap = new HashMap<>();
        enterpriseInfoList.stream().forEach(enterpriseInfo -> {
            enterpriseInfoMap.put(enterpriseInfo.getId(), enterpriseInfo);
        });

        // 设备类型列表
        EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
        List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew1);
        Map<String, SysDictionaryItem> machineTypeMap = new HashMap<>();
        machineTypeList.stream().forEach(machineType -> {
            machineTypeMap.put(machineType.getItemValue(), machineType);
        });

        //监控设备列表
        EntityWrapper<DeviceInfo> ew2 = new EntityWrapper<>();
        List<DeviceInfo> deviceInfoList = deviceInfoService.selectDeviceInfoList(ew2);
        Map<String, DeviceInfo> deviceInfoMap = new HashMap<>();
        deviceInfoList.stream().forEach(deviceInfo -> {
            deviceInfoMap.put(deviceInfo.getDeviceCode(), deviceInfo);
        });

        //班组列表
        EntityWrapper<Team> ew3 = new EntityWrapper<>();
        List<Team> teamList = machineService.selectTeamList(ew3);
        Map<String, Team> teamMap = new HashMap<>();
        teamList.stream().forEach(team -> {
            teamMap.put(team.getId(), team);
        });

        EntityWrapper<SysOrg> ew4 = new EntityWrapper<>();
        List<SysOrg> orgList = sysOrgService.selectList(ew4);
        Map<String, SysOrg> orgMap = new HashMap<>();
        orgList.stream().forEach(org -> {
            orgMap.put(org.getId(), org);
        });
        machines.stream().forEach(machine -> {
            if (deviceInfoMap.containsKey(machine.getDeviceCode()))
                machine.setDeviceName(deviceInfoMap.get(machine.getDeviceCode()).getDeviceName());
            if (enterpriseInfoMap.containsKey(machine.getEnterpriseId()))
                machine.setEnterpriseName(enterpriseInfoMap.get(machine.getEnterpriseId()).getEnterpriseName());
            if (machineTypeMap.containsKey(machine.getMachineType()))
                machine.setMachineTypeName(machineTypeMap.get(machine.getMachineType()).getItemName());
            if (teamMap.containsKey(machine.getTeamId()))
                machine.setTeamName(teamMap.get(machine.getTeamId()).getName());
            if (orgMap.containsKey(machine.getOrgId()))
                machine.setOrgName(orgMap.get(machine.getOrgId()).getOrgName());
        });
    }
}
