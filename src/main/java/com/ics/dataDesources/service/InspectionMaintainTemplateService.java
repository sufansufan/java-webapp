package com.ics.dataDesources.service;

import com.baomidou.mybatisplus.service.IService;
import com.ics.dataDesources.model.InspectionMaintainTemplate;

/**
 * @Description 点检保养模版
 * @Date 2020/10/26 22:26
 * @Author by yuankeyan
 */
public interface InspectionMaintainTemplateService extends IService<InspectionMaintainTemplate> {


    void saveTemplate(InspectionMaintainTemplate template);

    InspectionMaintainTemplate getTemplateClassItemByMachineIdAndType(String machineId, String templateType);
}
