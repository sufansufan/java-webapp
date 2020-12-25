package com.ics.dataDesources.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.model.MonitorFactor;
import com.ics.dataDesources.model.MonitorFactorTemplate;
import com.ics.dataDesources.service.*;
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysDictionaryItemService;
import com.ics.system.service.SysDictionaryService;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/dataDesources/condensingDevice")
public class ControlMachineController {

    protected static final String condensingDeviceJsp = "views/dataDesources/condensingDevice/index";
    protected static final String condensingDeviceAddJsp = "views/dataDesources/condensingDevice/add";
    protected static final String condensingDeviceEditJsp = "views/dataDesources/condensingDevice/edit";

    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysDictionaryItemService sysDictionaryItemService;
    @Autowired
    private MonitorFactorService monitorFactorService;
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private ControlMachineService controlMachineService;
    @Autowired
    private MonitorFactorTemplateService monitorFactorTemplateService;

    @RequiresPermissions(value = "control_machine")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(condensingDeviceJsp);
        EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
        ew.setEntity(new EnterpriseInfo());

        // 获取用户登录所属区域idlist
        SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
        List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
        if (getorgIdList.size() > 0) {
            ew.in("pe.org_id", getorgIdList);
        }

        List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
        mav.addObject("enterpriseInfoList", enterpriseInfoList);
        if(enterpriseInfoList.size()>0) {
            EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
            deviceEw.setEntity(new ControlMachine());
            deviceEw.andNew();
            deviceEw.eq("enterprise_id", enterpriseInfoList.get(0).getId());
            deviceEw.orderBy("machine_no+0");
            List<ControlMachine> condensingDeviceList = this.controlMachineService.selectList(deviceEw);
            mav.addObject("condensingDeviceList", condensingDeviceList);
        }
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/condensingDeviceList", method = RequestMethod.GET)
    public PagingBean condensingDeviceList(HttpServletRequest request, int page, int limit, String machineType, String machineNo,
                                           String id, String enterpriseId) {
        Page<ControlMachine> pager = new Page<>(page, limit);
        // 构造条件查询
        EntityWrapper<ControlMachine> ew = new EntityWrapper<>();
        ew.setEntity(new ControlMachine());
        if (StringUtils.isNotBlank(machineType)) {
            ew.andNew();
            ew.eq("a.machineType", machineType);
        }
        if (StringUtils.isNotBlank(machineNo)) {
            ew.andNew();
            ew.eq("a.machineNo", machineNo);
        }
        if (StringUtils.isNotBlank(id)) {
            ew.andNew();
            ew.eq("id", id);
        }
        if (StringUtils.isNotBlank(enterpriseId)) {
            ew.andNew();
            ew.eq("a.enterpriseId", enterpriseId);
        }
        ew.orderBy("a.machineNo+0");

        pager = controlMachineService.selectRelationPageList(pager, ew);

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
    @RequiresPermissions(value = "condensing_device_add")
    @RequestMapping(value = "/condensingDeviceAdd", method = RequestMethod.GET)
    public ModelAndView condensingDeviceAdd(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(condensingDeviceAddJsp);
        // 获取用户登录所属区域idlist
        SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
        // 构造条件查询
        EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
        ew.setEntity(new EnterpriseInfo());
        List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
        if (getorgIdList.size() > 0) {
            ew.in("pe.org_id", getorgIdList);
        }
        List<EnterpriseInfo> enterpriseInfoList = enterpriseInfoService.selectRelationList(ew);

        mav.addObject("upload_url", ConstantProperty.condensingDevice_url);

        // 设备类型
        List<String> idList = sysDictionaryService.getIdListByDictCode("machineType");
        EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
        ew1.setEntity(new SysDictionaryItem());
        ew1.in("dict_id", idList);
        ew1.orderBy("sort_idx", true);
        List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew1);
        mav.addObject("machineTypeList",machineTypeList);
        //get device infos detail
        // device 构造条件查询
        EntityWrapper<DeviceInfo> ew2 = new EntityWrapper<>();
        ew2.setEntity(new DeviceInfo());
        List<DeviceInfo> deviceInfo = deviceInfoService.selectDeviceInfoList(ew2);

        mav.addObject("deviceInfo", deviceInfo);
        mav.addObject("enterpriseInfoList", enterpriseInfoList);
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
    @RequestMapping(value = "/condensingDeviceAddSave", method = RequestMethod.POST)
    public JsonResult condensingDeviceAddSave(HttpServletRequest request, ControlMachine model) {

        JsonResult jsonResult = new JsonResult();

        boolean flag;
//        EntityWrapper<DeviceInfo> ew = new EntityWrapper<>();
//        ew.setEntity(new DeviceInfo());
//        ew.andNew();
//        ew.eq("device_code", model.getDeviceCode());
//        List<DeviceInfo> deviceInfo = deviceInfoService.selectDeviceInfoList(ew);
//
////        List<EnterpriseInfo> list =  enterpriseInfoService.selectList(ew);
//        model.setDeviceCode(deviceInfo.get(0).getDeviceCode());
        model.setCreateTime(new Date());
        model.setModifyTime(new Date());
        model.setId(CommonUtil.getRandomUUID());

        flag = this.controlMachineService.insert(model);
        if (!flag) {
            jsonResult.setFaild();
        }

        flag = insertRelationData(model.getId());
        if (!flag) {
            jsonResult.setFaild();
            return jsonResult;
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
    @RequiresPermissions(value = "condensing_device_edit")
    @RequestMapping(value = "/condensingDeviceEdit", method = RequestMethod.GET)
    public ModelAndView condensingDeviceEdit(HttpServletRequest request, String id) {

        ModelAndView mav = new ModelAndView(condensingDeviceEditJsp);
        // 获取用户登录所属区域idlist
        SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
        // 构造条件查询
        EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
        ew.setEntity(new EnterpriseInfo());
        List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
        if (getorgIdList.size() > 0) {
            ew.in("pe.org_id", getorgIdList);
        }
        List<EnterpriseInfo> enterpriseInfoList = enterpriseInfoService.selectRelationList(ew);
        ControlMachine model = this.controlMachineService.selectById(id);
        mav.addObject("model", model);

        mav.addObject("upload_url", ConstantProperty.condensingDevice_url);

        // 设备类型
        List<String> idList = sysDictionaryService.getIdListByDictCode("machineType");
        EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
        ew1.setEntity(new SysDictionaryItem());
        ew1.in("dict_id", idList);
        ew1.orderBy("sort_idx", true);
        List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew1);
        mav.addObject("machineTypeList",machineTypeList);

        EntityWrapper<DeviceInfo> deviceEw = new EntityWrapper<>();
        deviceEw.setEntity(new DeviceInfo());
        List<DeviceInfo> deviceInfo = deviceInfoService.selectDeviceInfoList(deviceEw);

        mav.addObject("deviceInfo", deviceInfo);
        mav.addObject("machineTypeList", machineTypeList);
        mav.addObject("enterpriseInfoList", enterpriseInfoList);
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
    @RequestMapping(value = "/condensingDeviceEditSave", method = RequestMethod.POST)
    public JsonResult condensingDeviceEditSave(HttpServletRequest request, ControlMachine model) {

        JsonResult jsonResult = new JsonResult();
        boolean flag;

        model.setModifyTime(new Date());
        flag = this.controlMachineService.updateById(model);
        if (!flag) {
            jsonResult.setFaild();
        }
        ControlMachine controlMachine = controlMachineService.selectById(model.getId());
        //删除监控因子相关数据
        flag = deleteRelationData(controlMachine.getId());
        if (!flag){
            jsonResult.setFaild();
            return jsonResult;
        }
        //添加监控因子相关数据
        flag = insertRelationData(controlMachine.getId());
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
    @RequiresPermissions(value = "condensing_device_del")
    @RequestMapping(value = "/condensingDeviceDel", method = RequestMethod.POST)
    public JsonResult condensingDeviceDel(HttpServletRequest request, String ids) {

        JsonResult jsonResult = new JsonResult();
        boolean flag = false;

        if (StringUtils.isNotBlank(ids)){
            flag = deleteRelationData(ids);
        }
        if (!flag){
            jsonResult.setFaild();
            return jsonResult;
        }
        if (StringUtils.isNotBlank(ids)) {
            List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
            flag = this.controlMachineService.deleteBatchIds(idList);
        }

        if (!flag) {
            jsonResult.setFaild();
        }
        return jsonResult;
    }


    /**
     * 名称重复验证是否通过 ：false不通过； true通过
     *
     * @param request
     * @param id
     * @param code
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkCondensingDeviceNumExist", method = RequestMethod.GET)
    public JsonResult checkCondensingDeviceNumExist(HttpServletRequest request, String id, String str,String enterpriseId) {
        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isNotBlank(str)) {

            // 构造条件查询
            EntityWrapper<ControlMachine> ew = new EntityWrapper<>();
            ew.setEntity(new ControlMachine());
            ew.eq("machine_no", str);
            ew.andNew();
            ew.eq("enterprise_id", enterpriseId);

            List<ControlMachine> list = this.controlMachineService.selectList(ew);

            if (list != null && list.size() > 0) {

                ControlMachine model = list.get(0);
                if (model != null && !model.getId().equals(id)) {
                    jsonResult.setSuccess(false);
                }
            }

        }

        return jsonResult;
    }


    /**
     * 添加监控因子模板数据
     * @param request
     * @param id
     * @param code
     * @return
     */
    public Boolean insertRelationData(String ids) {

        List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));

        for (String id: idList){
            ControlMachine controlMachine = controlMachineService.selectById(id);
            // 构造条件查询
            EntityWrapper<MonitorFactor> ew = new EntityWrapper<>();
            ew.setEntity(new MonitorFactor());
            ew.andNew();
            ew.eq("machine_type", controlMachine.getMachineType());
            List <MonitorFactor> monitorFactorList = monitorFactorService.selectList(ew);
            List <MonitorFactorTemplate> monitorFactorTemplateList = new ArrayList<MonitorFactorTemplate>();
            for (MonitorFactor monitorFactor: monitorFactorList){
                MonitorFactorTemplate monitorFactorTemplate = new MonitorFactorTemplate();
                //是否报警
                monitorFactorTemplate.setAlarmState(monitorFactor.getAlarmState());
                monitorFactorTemplate.setCreateTime(monitorFactor.getCreateTime());
                monitorFactorTemplate.setDataAccuracy(monitorFactor.getDataAccuracy());
                monitorFactorTemplate.setDecimalDigits(monitorFactor.getDecimalDigits());
                monitorFactorTemplate.setFactorCode(monitorFactor.getFactorCode());
                monitorFactorTemplate.setFactorName(monitorFactor.getFactorName());
                monitorFactorTemplate.setFactorTag(monitorFactor.getFactorTag());
                monitorFactorTemplate.setFactorUnit(monitorFactor.getFactorUnit());
                monitorFactorTemplate.setId(CommonUtil.getRandomUUID());
                monitorFactorTemplate.setUpperLimit(monitorFactor.getUpperLimit());
                monitorFactorTemplate.setModifyTime(monitorFactor.getModifyTime());
                monitorFactorTemplate.setUpperLimitText(monitorFactor.getUpperLimitText());
                monitorFactorTemplate.setLowerLimitText(monitorFactor.getLowerLimitText());
                monitorFactorTemplate.setStartSwitch(monitorFactor.getStartSwitch());
                monitorFactorTemplate.setTypeId(monitorFactor.getTypeId());
                monitorFactorTemplate.setProtocol(monitorFactor.getProtocol());
                monitorFactorTemplate.setMachineType(monitorFactor.getMachineType());
                monitorFactorTemplate.setDeviceCode(controlMachine.getDeviceCode());
                monitorFactorTemplate.setMachineId(id);

                monitorFactorTemplateList.add(monitorFactorTemplate);
            }
            boolean flag = true;
            if (monitorFactorTemplateList.size()>0){
                flag = monitorFactorTemplateService.insertBatch(monitorFactorTemplateList);
            }
            return flag;
        }

       return true;
    }

    /**
     * 删除监控因子模板
     * @param request
     * @param id
     * @param code
     * @return
     */
    public Boolean deleteRelationData(String ids) {
        //设备类型集合
        List<ControlMachine> controlMachineList = new ArrayList<>();
        List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
        // 构造条件查询
        for (String id : idList) {
            ControlMachine controlMachine = controlMachineService.selectById(id);
            controlMachineList.add(controlMachine);
        }

        // 构造删除语句
        for (ControlMachine controlMachine :controlMachineList) {
            EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
            ew.setEntity(new MonitorFactorTemplate());
            ew.andNew();
            ew.eq("machine_id", controlMachine.getId());
            monitorFactorTemplateService.delete(ew);
        }
        return true;
    }
}
