package com.ics.dataDesources.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.*;
import com.ics.dataDesources.service.*;
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.model.SysOrg;
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
@RequestMapping("/dataDesources/machineManager")
public class MachineController {

    protected static final String machineJsp = "views/dataDesources/machineManager/index";
    protected static final String machineAddJsp = "views/dataDesources/machineManager/add";
    protected static final String machineEditJsp = "views/dataDesources/machineManager/edit";
    protected static final String editMachineJsp = "views/dataDesources/machineManager/editMachine";

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
    private MachineService machineService;
    @Autowired
    private MonitorFactorTemplateService monitorFactorTemplateService;
    @Autowired
    private ControlMachineService controlMachineService;

    /**
     * 设备管理
     *
     * @param request
     * @return org.springframework.web.servlet.ModelAndView
     * @author suxiangrong
     * @date 2020/10/16
     */
    @RequiresPermissions(value = "machine_manager")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(machineJsp);
        // 设备类型
        List<String> idList = sysDictionaryService.getIdListByDictCode("machineType");
        if (idList.size() > 0) {
            EntityWrapper<SysDictionaryItem> ew = new EntityWrapper<>();
            ew.setEntity(new SysDictionaryItem());
            ew.in("dict_id", idList);
            ew.orderBy("sort_idx", true);
            List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew);
            mav.addObject("machineTypeList", machineTypeList);
        }
        List<SysOrg> orgList = machineService.selectMachineOrgList();
        mav.addObject("orgList", orgList);
        return mav;
    }

    /**
     * 编辑设备
     *
     * @param request
     * @return org.springframework.web.servlet.ModelAndView
     * @author suxiangrong
     * @date 2020/10/16
     */
    @RequestMapping(value = "/editMachine", method = RequestMethod.GET)
    public ModelAndView editMachine(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(editMachineJsp);

        EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
        ew.setEntity(new EnterpriseInfo());

        // 获取用户登录所属区域idlist
        SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
        List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
        if (getorgIdList.size() > 0) {
            ew.in("pe.org_id", getorgIdList);
        }

        //所属企业列表
        List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
        mav.addObject("enterpriseInfoList", enterpriseInfoList);

        // 设备类型列表
        List<String> idList = sysDictionaryService.getIdListByDictCode("machineType");
        if (idList.size() > 0) {
            EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
            ew1.setEntity(new SysDictionaryItem());
            ew1.in("dict_id", idList);
            ew1.orderBy("sort_idx", true);
            List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew1);
            mav.addObject("machineTypeList", machineTypeList);
        }

        //监控设备列表
        EntityWrapper<DeviceInfo> ew2 = new EntityWrapper<>();
        ew2.setEntity(new DeviceInfo());
        List<DeviceInfo> deviceInfo = deviceInfoService.selectDeviceInfoList(ew2);
        mav.addObject("deviceInfo", deviceInfo);


        //班组列表
        EntityWrapper<Team> ew3 = new EntityWrapper<>();
        ew3.setEntity(new Team());
        List<Team> teamList = machineService.selectTeamList(ew3);
        mav.addObject("teamList", teamList);

        return mav;
    }

    /**
     * 设备列表
     *
     * @param request
     * @param page
     * @param limit
     * @param machineType
     * @param machineName
     * @param remoteMonitorFlag
     * @param enterpriseId
     * @return com.ics.utils.PagingBean
     * @author suxiangrong
     * @date 2020/10/16
     */
    @ResponseBody
    @RequestMapping(value = "/machineList", method = RequestMethod.GET)
    public PagingBean machineList(HttpServletRequest request, int page, int limit, String machineType, String machineName,
                                  String remoteMonitorFlag, String orgId, String enterpriseId) {
        Page<ControlMachine> pager = new Page<>(page, limit);
        // 构造条件查询
        EntityWrapper<ControlMachine> ew = new EntityWrapper<>();
        ew.setEntity(new ControlMachine());
        if (StringUtils.isNotBlank(machineType)) {
            ew.andNew();
            ew.eq("cm.machine_type", machineType);
        }
        if (StringUtils.isNotBlank(machineName)) {
            ew.andNew();
            ew.like("cm.machine_name", machineName);
        }
        if (StringUtils.isNotBlank(remoteMonitorFlag)) {
            ew.andNew();
            ew.eq("cm.remote_monitor_flag", remoteMonitorFlag);
        }
        if (StringUtils.isNotBlank(enterpriseId)) {
            ew.andNew();
            ew.eq("cm.enterprise_id", enterpriseId);
        }
        if (StringUtils.isNotBlank(orgId)) {
            ew.andNew();
            ew.eq("cm.org_id", orgId);
        }
        ew.orderBy("cm.machine_no");

        pager = machineService.selectRelationPageList(pager, ew);

        PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
        pageBean.setData(pager.getRecords());
        return pageBean;
    }


    /**
     * 添加设备
     *
     * @param request
     * @return org.springframework.web.servlet.ModelAndView
     * @author suxiangrong
     * @date 2020/10/16
     */
    @RequiresPermissions(value = "machine_add")
    @RequestMapping(value = "/machineAdd", method = RequestMethod.GET)
    public ModelAndView machineAdd(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(editMachineJsp);

        EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
        ew.setEntity(new EnterpriseInfo());

        // 获取用户登录所属区域idlist
        SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
        List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
        if (getorgIdList.size() > 0) {
            ew.in("pe.org_id", getorgIdList);
        }

        //所属企业列表
        List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
        mav.addObject("enterpriseInfoList", enterpriseInfoList);

        // 设备类型列表
        List<String> idList = sysDictionaryService.getIdListByDictCode("machineType");
        if (idList.size() > 0) {
            EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
            ew1.setEntity(new SysDictionaryItem());
            ew1.in("dict_id", idList);
            ew1.orderBy("sort_idx", true);
            List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew1);
            mav.addObject("machineTypeList", machineTypeList);
        }

        //监控设备列表
        EntityWrapper<DeviceInfo> ew2 = new EntityWrapper<>();
        ew2.setEntity(new DeviceInfo());
        List<DeviceInfo> deviceInfo = deviceInfoService.selectDeviceInfoList(ew2);
        mav.addObject("deviceInfo", deviceInfo);


        //班组列表
        EntityWrapper<Team> ew3 = new EntityWrapper<>();
        ew3.setEntity(new Team());
        List<Team> teamList = machineService.selectTeamList(ew3);
        mav.addObject("teamList", teamList);

        //上传图片路径
        mav.addObject("upload_url", ConstantProperty.condensingDevice_url);

        return mav;
    }

    /**
     * 保存新增设备表单
     *
     * @param request
     * @param model
     * @return com.ics.utils.JsonResult
     * @author suxiangrong
     * @date 2020/10/16
     */
    @ResponseBody
    @RequestMapping(value = "/machineAddSave", method = RequestMethod.POST)
    public JsonResult machineAddSave(HttpServletRequest request, ControlMachine model) {

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

        flag = this.machineService.insert(model);
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
     * 编辑设备
     *
     * @param request
     * @param id
     * @return org.springframework.web.servlet.ModelAndView
     * @author suxiangrong
     * @date 2020/10/16
     */
    @RequiresPermissions(value = "machine_edit")
    @RequestMapping(value = "/machineEdit", method = RequestMethod.GET)
    public ModelAndView condensingDeviceEdit(HttpServletRequest request, String id) {
        ModelAndView mav = new ModelAndView(editMachineJsp);

        EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
        ew.setEntity(new EnterpriseInfo());

        // 获取用户登录所属区域idlist
        SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
        List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
        if (getorgIdList.size() > 0) {
            ew.in("pe.org_id", getorgIdList);
        }

        //所属企业列表
        List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
        mav.addObject("enterpriseInfoList", enterpriseInfoList);

        // 设备类型列表
        List<String> idList = sysDictionaryService.getIdListByDictCode("machineType");
        if (idList.size() > 0) {
            EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
            ew1.setEntity(new SysDictionaryItem());
            ew1.in("dict_id", idList);
            ew1.orderBy("sort_idx", true);
            List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew1);
            mav.addObject("machineTypeList", machineTypeList);
        }

        //监控设备列表
        EntityWrapper<DeviceInfo> ew2 = new EntityWrapper<>();
        ew2.setEntity(new DeviceInfo());
        List<DeviceInfo> deviceInfo = deviceInfoService.selectDeviceInfoList(ew2);
        mav.addObject("deviceInfo", deviceInfo);


        //班组列表
        EntityWrapper<Team> ew3 = new EntityWrapper<>();
        ew3.setEntity(new Team());
        List<Team> teamList = machineService.selectTeamList(ew3);
        mav.addObject("teamList", teamList);

        //设备信息
        ControlMachine machineInfo = this.machineService.selectById(id);
        mav.addObject("model", machineInfo);

        //上传图片路径
        mav.addObject("upload_url", ConstantProperty.condensingDevice_url);

        return mav;
    }

    /**
     * 保存编辑设备表单
     *
     * @param request
     * @param model
     * @return com.ics.utils.JsonResult
     * @author suxiangrong
     * @date 2020/10/16
     */
    @ResponseBody
    @RequestMapping(value = "/machineEditSave", method = RequestMethod.POST)
    public JsonResult condensingDeviceEditSave(HttpServletRequest request, ControlMachine model) {

        JsonResult jsonResult = new JsonResult();
        boolean flag;

        model.setModifyTime(new Date());
        flag = this.machineService.updateById(model);
        if (!flag) {
            jsonResult.setFaild();
        }
        ControlMachine controlMachine = machineService.selectById(model.getId());
        //删除监控因子相关数据
        flag = deleteRelationData(controlMachine.getId());
        if (!flag) {
            jsonResult.setFaild();
            return jsonResult;
        }
        //添加监控因子相关数据
        flag = insertRelationData(controlMachine.getId());
        if (!flag) {
            jsonResult.setFaild();
            return jsonResult;
        }


        return jsonResult;
    }

    /**
     * 删除设备
     *
     * @param request
     * @param ids
     * @return com.ics.utils.JsonResult
     * @author suxiangrong
     * @date 2020/10/16
     */
    @ResponseBody
    @RequiresPermissions(value = "machine_del")
    @RequestMapping(value = "/machineDel", method = RequestMethod.POST)
    public JsonResult machineDel(HttpServletRequest request, String ids) {

        JsonResult jsonResult = new JsonResult();
        boolean flag = false;

        if (StringUtils.isNotBlank(ids)) {
            flag = deleteRelationData(ids);
        }
        if (!flag) {
            jsonResult.setFaild();
            return jsonResult;
        }
        if (StringUtils.isNotBlank(ids)) {
            List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
            flag = this.machineService.deleteBatchIds(idList);
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
    public JsonResult checkCondensingDeviceNumExist(HttpServletRequest request, String id, String str, String enterpriseId) {
        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isNotBlank(str)) {

            // 构造条件查询
            EntityWrapper<ControlMachine> ew = new EntityWrapper<>();
            ew.setEntity(new ControlMachine());
            ew.eq("machine_no", str);
            ew.andNew();
            ew.eq("enterprise_id", enterpriseId);

            List<ControlMachine> list = this.machineService.selectList(ew);

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
    /**
     * 添加监控因子模板数据
     *
     * @param request
     * @param id
     * @param code
     * @return
     */
    public Boolean insertRelationData(String ids) {

        List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));

        for (String id : idList) {
            ControlMachine controlMachine = controlMachineService.selectById(id);
            // 构造条件查询
            EntityWrapper<MonitorFactor> ew = new EntityWrapper<>();
            ew.setEntity(new MonitorFactor());
            ew.andNew();
            ew.eq("machine_type", controlMachine.getMachineType());
            List<MonitorFactor> monitorFactorList = monitorFactorService.selectList(ew);
            List<MonitorFactorTemplate> monitorFactorTemplateList = new ArrayList<MonitorFactorTemplate>();
            for (MonitorFactor monitorFactor : monitorFactorList) {
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
                monitorFactorTemplate.setIsRuntime(monitorFactor.getIsRuntime());
                monitorFactorTemplate.setTypeId(monitorFactor.getTypeId());
                monitorFactorTemplate.setProtocol(monitorFactor.getProtocol());
                monitorFactorTemplate.setMachineType(monitorFactor.getMachineType());
                monitorFactorTemplate.setDeviceCode(controlMachine.getDeviceCode());
                monitorFactorTemplate.setMachineId(id);

                monitorFactorTemplateList.add(monitorFactorTemplate);
            }
            boolean flag = true;
            if (monitorFactorTemplateList.size() > 0) {
                flag = monitorFactorTemplateService.insertBatch(monitorFactorTemplateList);
            }
            return flag;
        }

        return true;
    }

    /**
     * 删除监控因子模板
     *
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
            ControlMachine controlMachine = machineService.selectById(id);
            controlMachineList.add(controlMachine);
        }

        // 构造删除语句
        if (controlMachineList.size() > 0) {
            for (ControlMachine controlMachine : controlMachineList) {
                EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
                ew.setEntity(new MonitorFactorTemplate());
                ew.andNew();
                ew.eq("machine_id", controlMachine.getId());
                monitorFactorTemplateService.delete(ew);
            }
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/monitor_factor_list", method = RequestMethod.GET)
    public PagingBean monitorFactorList(HttpServletRequest request, int page, int limit, String id) {
        Page<MonitorFactorTemplate> pager = new Page<>(page, limit);
        ControlMachine controlMachine = machineService.selectById(id);
        if (controlMachine != null) {
            EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
            ew.setEntity(new MonitorFactorTemplate());
            ew.eq("a.device_code", controlMachine.getDeviceCode());
            pager = monitorFactorTemplateService.selectRelationPageList(pager, ew);
        }

        PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
        pageBean.setData(pager.getRecords());
        return pageBean;
    }

}
