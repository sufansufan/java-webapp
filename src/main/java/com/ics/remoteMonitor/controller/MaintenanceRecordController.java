package com.ics.remoteMonitor.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.CondensingDevice;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.service.CondensingDeviceService;
import com.ics.dataDesources.service.EnterpriseInfoService;
import com.ics.remoteMonitor.model.MaintenanceRecord;
import com.ics.remoteMonitor.service.MaintenanceRecordService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

/**
 * 维修保养记录
 * 
 * @author jjz
 *
 */
@Controller
@RequestMapping("/remoteMonitor/maintenanceRecord")
public class MaintenanceRecordController {

	protected static final String maintenanceRecordJsp = "views/remoteMonitor/condensingDeviceMonitor/maintenanceRecord/index";
	protected static final String maintenanceRecordAddJsp = "views/remoteMonitor/condensingDeviceMonitor/maintenanceRecord/add";
	protected static final String maintenanceRecordEditJsp = "views/remoteMonitor/condensingDeviceMonitor/maintenanceRecord/edit";
	
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;
	@Autowired
	private MaintenanceRecordService maintenanceRecordService;
	@Autowired
	private CondensingDeviceService condensingDeviceService;
	
	@RequiresPermissions(value = "maintenance_record")
	@RequestMapping(value = "/maintenanceRecord", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(maintenanceRecordJsp);
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());

		// 获取用户登录所属区域idlist
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
		if (getorgIdList.size() > 0) {
			ew.in("pe.org_id", getorgIdList);
		}
		
		ew.orderBy("di.device_code");
		List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
		mav.addObject("enterpriseInfoList", enterpriseInfoList);
		mav.addObject("enterpriseInfoListJson", JSONObject.toJSONString(enterpriseInfoList));
		mav.addObject("enterpriseInfoListSize", enterpriseInfoList.size());
		if(enterpriseInfoList.size()>0) {
			EntityWrapper<CondensingDevice> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new CondensingDevice());
			deviceEw.andNew();
			deviceEw.eq("enterprise_id", enterpriseInfoList.get(0).getId());
			deviceEw.orderBy("machine_no+0");
			List<CondensingDevice> condensingDeviceList = this.condensingDeviceService.selectList(deviceEw);
			mav.addObject("condensingDeviceList", condensingDeviceList);
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/maintenanceRecordList", method = RequestMethod.GET)
	public PagingBean maintenanceRecordList(HttpServletRequest request, int page, int limit, String deviceCode,String condensingDeviceNum,
			String id, String enterpriseId) {
		Page<MaintenanceRecord> pager = new Page<>(page, limit);
		// 构造条件查询
		EntityWrapper<MaintenanceRecord> ew = new EntityWrapper<>();
		ew.setEntity(new MaintenanceRecord());
		// 获取用户登录所属区域idlist
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
		if (getorgIdList.size() > 0) {
			ew.in("b.org_id", getorgIdList);
		}
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("a.device_code", deviceCode);
		}
		if (StringUtils.isNotBlank(condensingDeviceNum)) {
			ew.andNew();
			ew.eq("a.machine_no", condensingDeviceNum);
		}
		ew.orderBy("a.device_code");
		ew.orderBy("maintenance_time");

		pager = maintenanceRecordService.selectRelationPageList(pager, ew);

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
		@RequiresPermissions(value = "maintenance_record_add")
		@RequestMapping(value = "/maintenanceRecordAdd", method = RequestMethod.GET)
		public ModelAndView maintenanceRecordAdd(HttpServletRequest request) {
			ModelAndView mav = new ModelAndView(maintenanceRecordAddJsp);
			Map<String, Object> map = new HashMap<>();
			// 获取用户登录所属区域idlist
			SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
			// 构造条件查询
			EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
			ew.setEntity(new EnterpriseInfo());
			List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
			if (getorgIdList.size() > 0) {
				ew.in("pe.org_id", getorgIdList);
			}
			ew.orderBy("di.device_code");
			List<EnterpriseInfo> enterpriseInfoList = enterpriseInfoService.selectRelationList(ew);
			
			mav.addObject("enterpriseInfoList", enterpriseInfoList);
			mav.addObject("enterpriseInfoListSize", enterpriseInfoList.size());
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
		@RequestMapping(value = "/maintenanceRecordAddSave", method = RequestMethod.POST)
		public JsonResult maintenanceRecordAddSave(HttpServletRequest request, MaintenanceRecord model,String maintenanceTimeStr) {

			JsonResult jsonResult = new JsonResult();
			
			boolean flag;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				model.setMaintenanceTime(sdf.parse(maintenanceTimeStr));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.setCreateTime(new Date());
			model.setModifyTime(new Date());
			model.setId(CommonUtil.getRandomUUID());
			flag = this.maintenanceRecordService.insert(model);

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
		@RequiresPermissions(value = "maintenance_record_edit")
		@RequestMapping(value = "/maintenanceRecordEdit", method = RequestMethod.GET)
		public ModelAndView maintenanceRecordEdit(HttpServletRequest request, String id) {

			ModelAndView mav = new ModelAndView(maintenanceRecordEditJsp);
			Map<String, Object> map = new HashMap<>();
			// 获取用户登录所属区域idlist
			SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
			// 构造条件查询
			EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
			ew.setEntity(new EnterpriseInfo());
			List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
			if (getorgIdList.size() > 0) {
				ew.in("pe.org_id", getorgIdList);
			}
			ew.orderBy("di.device_code");
			List<EnterpriseInfo> enterpriseInfoList = enterpriseInfoService.selectRelationList(ew);
			MaintenanceRecord model = this.maintenanceRecordService.selectById(id);
			mav.addObject("model", model);
			
			mav.addObject("enterpriseInfoList", enterpriseInfoList);
			mav.addObject("enterpriseInfoListSize", enterpriseInfoList.size());
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
		@RequestMapping(value = "/maintenanceRecordEditSave", method = RequestMethod.POST)
		public JsonResult maintenanceRecordEditSave(HttpServletRequest request, MaintenanceRecord model,String maintenanceTimeStr) {

			JsonResult jsonResult = new JsonResult();

			boolean flag;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				model.setMaintenanceTime(sdf.parse(maintenanceTimeStr));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.setModifyTime(new Date());
			flag = this.maintenanceRecordService.updateById(model);
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
		@RequiresPermissions(value = "maintenance_record_del")
		@RequestMapping(value = "/maintenanceRecordDel", method = RequestMethod.POST)
		public JsonResult MaintenanceRecordDel(HttpServletRequest request, String ids) {

			JsonResult jsonResult = new JsonResult();

			boolean flag = false;

			if (StringUtils.isNotBlank(ids)) {
				List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
				flag = this.maintenanceRecordService.deleteBatchIds(idList);

			}

			if (!flag) {
				jsonResult.setFaild();
			}
			return jsonResult;
		}
			
	
}
