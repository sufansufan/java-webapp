package com.ics.dataDesources.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ics.dataDesources.model.*;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.dataDesources.service.MonitorFactorTagService;
import com.ics.dataDesources.service.MonitorFactorTemplateService;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.service.SysDictionaryItemService;
import com.ics.system.service.SysDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.service.MonitorFactorService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

/**
 * 监测因子管理
 *
 * @author jjz
 */
@Controller
@RequestMapping("/dataDesources/monitorFactor")
public class MonitorFactorController {

    protected static final String indexJsp = "views/dataDesources/monitorFactor/index";
    protected static final String addJsp = "views/dataDesources/monitorFactor/add";
    protected static final String editJsp = "views/dataDesources/monitorFactor/edit";

    @Autowired
    private MonitorFactorService monitorFactorService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysDictionaryItemService sysDictionaryItemService;
    @Autowired
    private ControlMachineService controlMachineService;
    @Autowired
    private MonitorFactorTemplateService monitorFactorTemplateService;
    @Autowired
    private MonitorFactorTagService monitorFactorTagService;

    /**
     * @param request
     * @return ModelAndView
     * @Title: index
     * @Description: 主页
     */
    @RequiresPermissions(value = "monitor_factor")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(indexJsp);

        EntityWrapper<MonitorFactorTag> ew = new EntityWrapper<>();
        ew.setEntity(new MonitorFactorTag());
        List<MonitorFactorTag> factorTagList = this.monitorFactorTagService.selectList(ew);
        mav.addObject("factorTagList", factorTagList);
        return mav;
    }

    /**
     * 请求列表数据
     *
     * @param request
     * @param page
     * @param limit
     * @param roleName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PagingBean list(HttpServletRequest request, int page, int limit, String factorName, String factorCode, String typeId, String machineType, String factorTag) {

        Page<MonitorFactor> pager = new Page<>(page, limit);
        // 构造条件查询
        EntityWrapper<MonitorFactor> ew = new EntityWrapper<>();
        ew.setEntity(new MonitorFactor());

        if (StringUtils.isNotBlank(factorTag)) {
            List<String> tagList = new ArrayList<>(Arrays.asList(factorTag.split(",")));
            String sqlFilter = "";
            ew.andNew();
            for (int i=0;i<tagList.size();i++){
                if (i == tagList.size() -1){
                    sqlFilter = sqlFilter + " FIND_IN_SET('"+tagList.get(i)+"', a.factor_tag)";
                }else{
                    sqlFilter = sqlFilter + " FIND_IN_SET('"+tagList.get(i)+"', a.factor_tag) OR ";
                }
            }
            ew.addFilter(sqlFilter);
        }
        if (StringUtils.isNotBlank(factorName)) {
            ew.andNew();
            ew.like("a.factor_name", factorName);
        }
        if (StringUtils.isNotBlank(factorCode)) {
            ew.andNew();
            ew.like("a.factor_code", factorCode);
        }
        if (StringUtils.isNotBlank(typeId)) {
            ew.andNew();
            ew.eq("a.type_id", typeId);
        }
        if (StringUtils.isNotBlank(machineType)) {
            ew.andNew();
            ew.eq("a.machine_type", machineType);
        }

        ew.orderBy("a.factor_code", true);
        pager = monitorFactorService.selectRelationPageList(pager, ew);

        PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
        pageBean.setData(pager.getRecords());
        return pageBean;
    }

    /**
     * 新增页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequiresPermissions(value = "monitor_factor_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request, String id) {
        ModelAndView mav = new ModelAndView(addJsp);

        // 设备类型列表
        List<String> idList = sysDictionaryService.getIdListByDictCode("machineType");
        EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
        ew1.setEntity(new SysDictionaryItem());
        ew1.in("dict_id", idList);
        ew1.orderBy("sort_idx", true);
        List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew1);
        mav.addObject("machineTypeList",machineTypeList);

        // 标签列表
        EntityWrapper<MonitorFactorTag> ew2 = new EntityWrapper<>();
        ew2.setEntity(new MonitorFactorTag());
        List<MonitorFactorTag> factorTagList = monitorFactorTagService.selectList(ew2);
        mav.addObject("factorTagList", factorTagList);
        return mav;
    }

    /**
     * 新增保存
     *
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = "monitor_factor_add")
    @RequestMapping(value = "/addSave", method = RequestMethod.POST)
    public JsonResult addSave(HttpServletRequest request, MonitorFactor model) {
        JsonResult jsonResult = new JsonResult();
        boolean flag;

        flag = insertRelationData(model);
        if (!flag) {
            jsonResult.setFaild();
            return jsonResult;
        }
        model.setCreateTime(new Date());
        model.setModifyTime(new Date());
        model.setId(CommonUtil.getRandomUUID());
        flag = this.monitorFactorService.insert(model);
        if (!flag) {
            jsonResult.setFaild();
        }
        return jsonResult;
    }

    /**
     * 编辑页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequiresPermissions(value = "monitor_factor_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request, String id) {

        ModelAndView mav = new ModelAndView(editJsp);
        MonitorFactor model = this.monitorFactorService.selectById(id);
        mav.addObject("model", model);

        // 设备类型
        List<String> idList = sysDictionaryService.getIdListByDictCode("machineType");
        EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
        ew1.setEntity(new SysDictionaryItem());
        ew1.in("dict_id", idList);
        ew1.orderBy("sort_idx", true);
        List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew1);
        mav.addObject("machineTypeList",machineTypeList);

        // 标签列表
        EntityWrapper<MonitorFactorTag> ew2 = new EntityWrapper<>();
        ew2.setEntity(new MonitorFactorTag());
        List<MonitorFactorTag> factorTagList = monitorFactorTagService.selectList(ew2);
        mav.addObject("factorTagList", factorTagList);
        return mav;
    }

    /**
     * 编辑保存
     *
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = "monitor_factor_edit")
    @RequestMapping(value = "/editSave", method = RequestMethod.POST)
    public JsonResult editSave(HttpServletRequest request, MonitorFactor model) {
        JsonResult jsonResult = new JsonResult();
        boolean flag;

        model.setModifyTime(new Date());
        flag = this.monitorFactorService.updateById(model);
        if (!flag) {
            jsonResult.setFaild();
            return jsonResult;
        }

        flag = deleteRelationData(model.getId());
        if (!flag){
            jsonResult.setFaild();
            return jsonResult;
        }

        flag =  insertRelationData(model);
        if (!flag){
            jsonResult.setFaild();
            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * 删除，支持批量删除
     *
     * @param request
     * @param ids
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = "monitor_factor_del")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public JsonResult del(HttpServletRequest request, String ids, String names) {
        JsonResult jsonResult = new JsonResult();
        boolean flag = false;
        flag = deleteRelationData(ids);
        if (!flag){
            jsonResult.setFaild();
            return jsonResult;
        }
        if (StringUtils.isNotBlank(ids)) {
            List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
            flag = this.monitorFactorService.deleteBatchIds(idList);
        }
        if (!flag) {
            jsonResult.setFaild();
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/checkFactorNameExist", method = RequestMethod.GET)
    public JsonResult checkFactorNameExist(HttpServletRequest request, String id, String str) {

        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isNotBlank(str)) {

            // 构造条件查询
            EntityWrapper<MonitorFactor> ew = new EntityWrapper<>();
            ew.setEntity(new MonitorFactor());
            ew.eq("factor_name", str);

            List<MonitorFactor> list = this.monitorFactorService.selectList(ew);

            if (list != null && list.size() > 0) {

                MonitorFactor model = list.get(0);
                if (model != null && !model.getId().equals(id)) {
                    jsonResult.setSuccess(false);
                }
            }

        }

        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/checkFactorCodeExist", method = RequestMethod.GET)
    public JsonResult checkFactorCodeExist(HttpServletRequest request, String id, String str) {

        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isNotBlank(str)) {

            // 构造条件查询
            EntityWrapper<MonitorFactor> ew = new EntityWrapper<>();
            ew.setEntity(new MonitorFactor());
            ew.eq("factor_code", str);

            List<MonitorFactor> list = this.monitorFactorService.selectList(ew);

            if (list != null && list.size() > 0) {

                MonitorFactor model = list.get(0);
                if (model != null && !model.getId().equals(id)) {
                    jsonResult.setSuccess(false);
                }
            }

        }

        return jsonResult;
    }


    /**
     * 添加监控因子模板数据
     *
     * @return
     */
    public Boolean insertRelationData(MonitorFactor model) {

        // 构造条件查询
        EntityWrapper<ControlMachine> ew = new EntityWrapper<>();
        ew.setEntity(new ControlMachine());
        ew.andNew();
        ew.eq("machine_type", model.getMachineType());
        List<ControlMachine> controlMachineList = controlMachineService.selectRelationList(ew);
        // List<ControlMachine> controlMachineList = controlMachineService.selectList(ew);

        List<MonitorFactorTemplate> monitorFactorTemplateList = new ArrayList<>();
        for (ControlMachine controlMachine : controlMachineList) {
            MonitorFactorTemplate monitorFactorTemplate = new MonitorFactorTemplate();
            //是否报警
            monitorFactorTemplate.setAlarmState(model.getAlarmState());
            monitorFactorTemplate.setCreateTime(new Date());
            monitorFactorTemplate.setDataAccuracy(model.getDataAccuracy());
            monitorFactorTemplate.setDecimalDigits(model.getDecimalDigits());
            monitorFactorTemplate.setFactorCode(model.getFactorCode());
            monitorFactorTemplate.setFactorName(model.getFactorName());
            monitorFactorTemplate.setFactorTag(model.getFactorTag());
            monitorFactorTemplate.setFactorUnit(model.getFactorUnit());
            monitorFactorTemplate.setId(CommonUtil.getRandomUUID());
            monitorFactorTemplate.setUpperLimit(model.getUpperLimit());
            monitorFactorTemplate.setLowerLimit(model.getLowerLimit());
            monitorFactorTemplate.setUpperLimitText(model.getUpperLimitText());
            monitorFactorTemplate.setLowerLimitText(model.getLowerLimitText());
            monitorFactorTemplate.setStartSwitch(model.getStartSwitch());
            monitorFactorTemplate.setIsRuntime(model.getIsRuntime());
            monitorFactorTemplate.setModifyTime(new Date());
            monitorFactorTemplate.setTypeId(model.getTypeId());
            monitorFactorTemplate.setProtocol(model.getProtocol());
            monitorFactorTemplate.setMachineType(controlMachine.getMachineType());
            monitorFactorTemplate.setDeviceCode(controlMachine.getDeviceCode());
            monitorFactorTemplate.setMachineId(controlMachine.getId());
            monitorFactorTemplateList.add(monitorFactorTemplate);
        }
        boolean flag = true;
        if (monitorFactorTemplateList.size() > 0) {
            flag = monitorFactorTemplateService.insertBatch(monitorFactorTemplateList);
        }
        return flag;
    }

    /**
     * 删除监控因子模板
     *
     * @return
     */
    public Boolean deleteRelationData(String ids) {
        //
        //设备类型集合
        List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
        // 构造条件查询
        for (String id : idList) {
            EntityWrapper<MonitorFactor> ew = new EntityWrapper<>();
            ew.setEntity(new MonitorFactor());
            ew.andNew();
            ew.eq("a.id", id);
            List<MonitorFactor> monitorFactorList = monitorFactorService.selectRelationList(ew);
            //删除
            if(monitorFactorList.size() > 0){
                for (MonitorFactor monitorFactor : monitorFactorList) {
                    EntityWrapper<MonitorFactorTemplate> ew1 = new EntityWrapper<>();
                    ew1.setEntity(new MonitorFactorTemplate());
                    ew1.andNew();
                    ew1.eq("factor_code", monitorFactor.getFactorCode());
                    ew1.andNew();
                    ew1.eq("machine_id", monitorFactor.getDeviceId());
                    monitorFactorTemplateService.delete(ew1);
                }
            }
        }
        return true;
    }

}
