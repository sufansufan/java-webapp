package com.ics.dataDesources.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.dataDesources.mapper.InspectionMaintainClassMapper;
import com.ics.dataDesources.mapper.InspectionMaintainItemMapper;
import com.ics.dataDesources.mapper.InspectionMaintainTemplateMapper;
import com.ics.dataDesources.model.InspectionMaintainClass;
import com.ics.dataDesources.model.InspectionMaintainItem;
import com.ics.dataDesources.model.InspectionMaintainTemplate;
import com.ics.dataDesources.service.InspectionMaintainTemplateService;
import com.ics.utils.CommonUtil;
import com.ics.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("inspectionMaintainTemplateService")
public class InspectionMaintainTemplateServiceImpl extends ServiceImpl<InspectionMaintainTemplateMapper, InspectionMaintainTemplate> implements InspectionMaintainTemplateService {

    @Autowired
    private InspectionMaintainTemplateMapper templateMapper;

    @Autowired
    private InspectionMaintainClassMapper classMapper;

    @Autowired
    private InspectionMaintainItemMapper itemMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTemplate(InspectionMaintainTemplate template) {
        String oldTemplateId = template.getId();
        String templateId;
        String machineId = template.getMachineId();
        if (StringUtils.isEmpty(template.getId())) {
            templateId = CommonUtil.getRandomUUID();
            template.setId(templateId);
            template.setCreateTime(new Date());
            insert(template);
        } else {
            templateId = template.getId();
            template.setModifyTime(new Date());
            updateById(template);
        }
        //先删除老的 class item数据
        if (StringUtils.isNotEmpty(oldTemplateId)) deleteClassAndItemByTemplateId(oldTemplateId);

        //插入class item数据
        int classIndex = 0;
        for (InspectionMaintainClass inspectionMaintainClass : template.getClassList()) {
            String classId = CommonUtil.getRandomUUID();
            inspectionMaintainClass.setTemplateId(templateId);
            inspectionMaintainClass.setMachineId(machineId);
            inspectionMaintainClass.setIndexNum(++classIndex);
            inspectionMaintainClass.setId(classId);
            inspectionMaintainClass.setCreateTime(new Date());
            classMapper.insert(inspectionMaintainClass);
            for (InspectionMaintainItem inspectionMaintainItem : inspectionMaintainClass.getItemList()) {
                inspectionMaintainItem.setTemplateId(templateId);
                inspectionMaintainItem.setMachineId(machineId);
                inspectionMaintainItem.setClassId(classId);
                inspectionMaintainItem.setId(CommonUtil.getRandomUUID());
                inspectionMaintainItem.setCreateTime(new Date());
                short available = 1;
                inspectionMaintainItem.setIsAvailable(available);
                itemMapper.insert(inspectionMaintainItem);
            }
        }
    }

    @Override
    public InspectionMaintainTemplate getTemplateClassItemByMachineIdAndType(String machineId, String templateType) {
        EntityWrapper<InspectionMaintainTemplate> templateEw = new EntityWrapper<>();
        templateEw.eq("machine_id", machineId);
        templateEw.eq("template_type", templateType);
        templateEw.eq("is_available", 1);
        int templateCount = templateMapper.selectCount(templateEw);
        if(templateCount == 0) return new InspectionMaintainTemplate();
        List<InspectionMaintainTemplate> templateList = templateMapper.selectList(templateEw);
        InspectionMaintainTemplate template = templateList.get(0);

        String templateId = template.getId();

        EntityWrapper<InspectionMaintainClass> classEw = new EntityWrapper<>();
        classEw.eq("template_id", templateId);
        classEw.eq("is_available", 1);
        classEw.orderBy("index_num");
        List<InspectionMaintainClass> classList = classMapper.selectList(classEw);

        EntityWrapper<InspectionMaintainItem> itemsEw = new EntityWrapper<>();
        itemsEw.eq("template_id", templateId);
        itemsEw.eq("is_available", 1);
        itemsEw.orderBy("index_num");
        List<InspectionMaintainItem> itemList = itemMapper.selectList(itemsEw);
        Map<String, List<InspectionMaintainItem>> classMap = MapUtils.listToMapListByKey(itemList, InspectionMaintainItem.class, "classId");

        for (InspectionMaintainClass inspectionMaintainClass : classList) {
            inspectionMaintainClass.setItemList(classMap.get(inspectionMaintainClass.getId()));
        }
        template.setClassList(classList);

        return template;
    }

    /**
     * 软删除
     * @param templateId
     */
    private void deleteClassAndItemByTemplateId(String templateId) {
        classMapper.updateStatusByTemplateId(templateId);
        itemMapper.updateStatusByTemplateId(templateId);
    }
}
