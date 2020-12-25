package com.ics.dataDesources.controller;

import com.ics.dataDesources.model.InspectionMaintainTemplate;
import com.ics.dataDesources.service.InspectionMaintainTemplateService;
import com.ics.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 点检保养模版
 * @Date 2020/10/26 22:26
 * @Author by yuankeyan
 */
@Controller
@RequestMapping("/dataDesources/inspectionMaintainTemplate")
public class InspectionMaintainTemplateController {

    protected static final String indexJsp = "views/dataDesources/monitorFactorTemplate/index";
    protected static final String addJsp = "views/dataDesources/monitorFactorTemplate/add";
    protected static final String editJsp = "views/dataDesources/monitorFactorTemplate/edit";

    @Autowired
    private InspectionMaintainTemplateService inspectionMaintainTemplateService;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonResult save(@RequestBody InspectionMaintainTemplate template) {

        JsonResult jsonResult = new JsonResult();
        try {
            inspectionMaintainTemplateService.saveTemplate(template);
            jsonResult.setData(template);
            jsonResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setFaild();
        }

        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/getTemplateClassItemByMachineIdAndType", method = RequestMethod.GET)
    public JsonResult getTemplateClassItemByMachineIdAndType(String machineId, String templateType) {

        JsonResult jsonResult = new JsonResult();
        try {
            InspectionMaintainTemplate template = inspectionMaintainTemplateService.getTemplateClassItemByMachineIdAndType(machineId, templateType);
            jsonResult.setSuccess(true);
            jsonResult.setData(template);
        } catch (Exception e) {
            jsonResult.setFaild();
        }

        return jsonResult;
    }

}
