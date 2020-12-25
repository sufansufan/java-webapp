package com.ics.remoteMonitor.controller;

import java.util.List;

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
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.service.EnterpriseInfoService;
import com.ics.dataDesources.model.CondensingDevice;
import com.ics.dataDesources.service.CondensingDeviceService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CommonUtil;
import com.ics.utils.PagingBean;

/**
 * 设备监测
 * 
 * @author jjz
 *
 */
@Controller
@RequestMapping("/remoteMonitor/condensingDeviceMonitor")
public class CondensingDeviceMonitorController {

	protected static final String condensingDeviceMonitorJsp = "views/remoteMonitor/condensingDeviceMonitor/condensingDevice/index";

	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private CondensingDeviceService condensingDeviceService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;

	@RequiresPermissions(value = "condensing_device_monitor")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(condensingDeviceMonitorJsp);
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
	@RequestMapping(value = "/condensingDeviceList", method = RequestMethod.GET)
	public PagingBean condensingDeviceList(HttpServletRequest request, int page, int limit, String deviceCode,String condensingDeviceNum,
			String id, String enterpriseId) {
		Page<CondensingDevice> pager = new Page<>(page, limit);
		// 构造条件查询
		EntityWrapper<CondensingDevice> ew = new EntityWrapper<>();
		ew.setEntity(new CondensingDevice());
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("a.device_code", deviceCode);
		}
		if (StringUtils.isNotBlank(condensingDeviceNum)) {
			ew.andNew();
			ew.eq("machine_no", condensingDeviceNum);
		}
		if (StringUtils.isNotBlank(id)) {
			ew.andNew();
			ew.eq("id", id);
		}
		if (StringUtils.isNotBlank(enterpriseId)) {
			ew.andNew();
			ew.eq("enterprise_id", enterpriseId);
		}
		ew.orderBy("machine_no+0");

		pager = condensingDeviceService.selectRelationPageList(pager, ew);

		PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
		pageBean.setData(pager.getRecords());
		return pageBean;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/mapCondensingDeviceList")
	public String mapCondensingDeviceList(HttpServletRequest request, String deviceCode,String condensingDeviceNum,
			String id, String enterpriseId) {
		// 构造条件查询
		EntityWrapper<CondensingDevice> ew = new EntityWrapper<>();
		ew.setEntity(new CondensingDevice());
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("a.device_code", deviceCode);
		}
		if (StringUtils.isNotBlank(condensingDeviceNum)) {
			ew.andNew();
			ew.eq("machine_no", condensingDeviceNum);
		}
		if (StringUtils.isNotBlank(id)) {
			ew.andNew();
			ew.eq("id", id);
		}
		if (StringUtils.isNotBlank(enterpriseId)) {
			ew.andNew();
			ew.eq("enterprise_id", enterpriseId);
		}
		ew.orderBy("machine_no+0");

		List<CondensingDevice> list = condensingDeviceService.selectRelationList(ew);

		return JSONObject.toJSONString(list);
	}
	
	
}
