package com.ics.inspectionMaintenance.service;

import com.ics.dataDesources.model.ControlMachine;
import com.ics.inspectionMaintenance.model.ImDetail;

import java.util.List;

public interface IMService{
    ImDetail buildImDetail(String scheduleId);
    List<ControlMachine> getMachine(List<String> machineIds);
}
