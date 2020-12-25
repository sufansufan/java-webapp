package com.ics.dataDesources.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ics.dataDesources.model.*;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.dataDesources.service.MonitorFactorTagService;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.service.SysDictionaryItemService;
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
import com.ics.dataDesources.service.EnterpriseInfoService;
import com.ics.dataDesources.service.MonitorFactorTemplateService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

/**
 * 监测因子模板管理
 * @author jjz
 *
 */
@Controller
@RequestMapping("/dataDesources/monitorFactorTemplate")
public class MonitorFactorTemplateController {

	protected static final String indexJsp = "views/dataDesources/monitorFactorTemplate/index";
	protected static final String addJsp = "views/dataDesources/monitorFactorTemplate/add";
	protected static final String editJsp = "views/dataDesources/monitorFactorTemplate/edit";
	@Autowired
	private MonitorFactorTemplateService monitorFactorTemplateService;
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;
	@Autowired
	private ControlMachineService controlMachineService;
	@Autowired
	private MonitorFactorTagService monitorFactorTagService;
	@Autowired
	private SysDictionaryItemService sysDictionaryItemService;

	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresPermissions(value = "monitor_factor_template")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(indexJsp);
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

		EntityWrapper<MonitorFactorTag> ew2 = new EntityWrapper<>();
		ew2.setEntity(new MonitorFactorTag());
		List<MonitorFactorTag> factorTagList = this.monitorFactorTagService.selectList(ew2);
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
	public PagingBean list(HttpServletRequest request, int page, int limit, String factorName,String factorCode,String typeId,String machineType,String deviceCode,String enterpriseName,String factorTag) {

		Page<MonitorFactorTemplate> pager = new Page<>(page, limit);
		// 构造条件查询
		EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
		ew.setEntity(new MonitorFactorTemplate());
		if (StringUtils.isNotBlank(factorTag)) {
			List<String> tagList = new ArrayList<>(Arrays.asList(factorTag.split(",")));
			ew.andNew();
			String sqlFilter = "";
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
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.like("a.device_code", deviceCode);
		}
		if (StringUtils.isNotBlank(enterpriseName)) {
			ew.andNew();
			ew.eq("c.enterprise_name", enterpriseName);
		}
		pager = monitorFactorTemplateService.selectRelationPageList(pager, ew);

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
	@RequiresPermissions(value = "monitor_factor_template_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(addJsp);

		// 设备名
		EntityWrapper<ControlMachine> ew3 = new EntityWrapper<>();
		ew3.setEntity(new ControlMachine());
		List<ControlMachine> machineNameList = controlMachineService.selectRelationList(ew3);
		mav.addObject("machineNameList", machineNameList);

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
	@RequiresPermissions(value = "monitor_factor_template_add")
	@RequestMapping(value = "/addSave", method = RequestMethod.POST)
	public JsonResult addSave(HttpServletRequest request, MonitorFactorTemplate model) {
		JsonResult jsonResult = new JsonResult();

		boolean flag;
		model.setCreateTime(new Date());
		model.setModifyTime(new Date());

		EntityWrapper<ControlMachine> ew3 = new EntityWrapper<>();
		ew3.setEntity(new ControlMachine());
		if (StringUtils.isNotBlank(model.getMachineId())) {
			ew3.andNew();
			ew3.eq("a.id", model.getMachineId());
		}
		List<ControlMachine> machineNameList = controlMachineService.selectRelationList(ew3);
		model.setDeviceCode(machineNameList.get(0).getDeviceCode());
		model.setMachineType(machineNameList.get(0).getMachineType());
		model.setId(CommonUtil.getRandomUUID());

		flag = this.monitorFactorTemplateService.insert(model);
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
	@RequiresPermissions(value = "monitor_factor_template_edit")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView(editJsp);
		MonitorFactorTemplate model = this.monitorFactorTemplateService.selectById(id);
		mav.addObject("model", model);


		// 设备名
		EntityWrapper<ControlMachine> ew3 = new EntityWrapper<>();
		ew3.setEntity(new ControlMachine());
		ew3.andNew();
		ew3.eq("a.id",model.getMachineId());
		List<ControlMachine> machineNameList = controlMachineService.selectRelationList(ew3);
		mav.addObject("machineNameList", machineNameList);
//		EntityWrapper<SysDictionaryItem> ew3 = new EntityWrapper<>();
//		ew3.setEntity(new SysDictionaryItem());
//		List<SysDictionaryItem> machineTypeList = sysDictionaryItemService.selectList(ew3);
//		mav.addObject("machineTypeList", machineTypeList);

		// 标签列表
		EntityWrapper<MonitorFactorTag> ew2 = new EntityWrapper<>();
		ew2.setEntity(new MonitorFactorTag());
		List<MonitorFactorTag> factorTagList = monitorFactorTagService.selectList(ew2);
		mav.addObject("factorTagList", factorTagList);
		return mav;
	}

	/**
	 *	编辑保存
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "monitor_factor_template_edit")
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public JsonResult editSave(HttpServletRequest request, MonitorFactorTemplate model) {
		JsonResult jsonResult = new JsonResult();

		boolean flag;
		model.setModifyTime(new Date());
		flag = this.monitorFactorTemplateService.updateById(model);
		if (!flag) {
			jsonResult.setFaild();
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
	@RequiresPermissions(value = "monitor_factor_template_del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResult del(HttpServletRequest request, String ids, String names) {
		JsonResult jsonResult = new JsonResult();
		boolean flag = false;
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			flag = this.monitorFactorTemplateService.deleteBatchIds(idList);
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
			EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
			ew.setEntity(new MonitorFactorTemplate());
			ew.eq("factor_name", str);

			List<MonitorFactorTemplate> list = this.monitorFactorTemplateService.selectList(ew);

			if (list != null && list.size() > 0) {

				MonitorFactorTemplate model = list.get(0);
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
			EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
			ew.setEntity(new MonitorFactorTemplate());
			ew.eq("factor_code", str);

			List<MonitorFactorTemplate> list = this.monitorFactorTemplateService.selectList(ew);

			if (list != null && list.size() > 0) {

				MonitorFactorTemplate model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}

		return jsonResult;
	}

}
