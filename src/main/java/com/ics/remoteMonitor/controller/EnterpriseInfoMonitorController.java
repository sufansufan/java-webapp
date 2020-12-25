package com.ics.remoteMonitor.controller;

import java.util.ArrayList;
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
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

/**
 * 企业信息监测
 * 
 * @author jjz
 *
 */
@Controller
@RequestMapping("/remoteMonitor/enterpriseInfoMonitor")
public class EnterpriseInfoMonitorController {

	protected static final String enterpriseInfoMonitorJsp = "views/remoteMonitor/enterpriseInfoMonitor/enterpriseInfoMonitor/index";

	protected static final String enterpriseMapJsp = "views/remoteMonitor/enterpriseInfoMonitor/enterpriseInfoMonitor/map";

	protected static final String getMapPositonJsp = "views/remoteMonitor/enterpriseInfo/enterpriseInfo/getMapPositon";

	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;

	/**
	 * 污染源企业管理
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_info_monitor")
	@RequestMapping(value = "/enterpriseInfoMonitor", method = RequestMethod.GET)
	public ModelAndView enterpriseInfoMonitor(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(enterpriseInfoMonitorJsp);
		return mav;
	}

	/**
	 * 企业地图
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_map")
	@RequestMapping(value = "/enterpriseMap", method = RequestMethod.GET)
	public ModelAndView enterpriseMap(HttpServletRequest request, String enterpriseName) {
		ModelAndView mav = new ModelAndView(enterpriseMapJsp);
		return mav;
	}

	/**
	 * 地图列表信息
	 * 
	 * @param request
	 * @param idsArr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dataListMap", method = RequestMethod.POST)
	public JsonResult dataListMap(HttpServletRequest request) {

		JsonResult jsonResult = new JsonResult();
//		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		String orgIds = request.getParameter("idArr");
		String enterpriseName = request.getParameter("enterpriseName");
		// 构造条件查询
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		if (StringUtils.isNotBlank(enterpriseName)) {
			ew.orNew();
			ew.like("enterprise_name", enterpriseName);
		}
		if (StringUtils.isNotBlank(orgIds)) {
			ew.andNew();
			ew.in("pe.org_id", orgIds.split(","));
		}
		ew.orderBy("modify_time", false);

		List<EnterpriseInfo> list = enterpriseInfoService.selectRelationList(ew);
		jsonResult.setData(list);
		return jsonResult;
	}

	@RequestMapping(value = "/getMapPositon")
	public ModelAndView getMapPositon(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(getMapPositonJsp);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/enterpriseInfoList", method = RequestMethod.GET)
	public PagingBean enterpriseInfoList(HttpServletRequest request, int page, int limit, String keyWord) {
		Page<EnterpriseInfo> pager = new Page<>(page, limit);
		// 获取用户登录所属区域idlist
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		// 构造条件查询
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
		if (getorgIdList.size() > 0) {
			ew.in("pe.org_id", getorgIdList);
		}
		if (StringUtils.isNotBlank(keyWord)) {
			ew.andNew();
			ew.like("enterprise_name", keyWord);
		}
		ew.orderBy("di.device_code");

		pager = enterpriseInfoService.selectRelationPageList(pager, ew);

		PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
		pageBean.setData(pager.getRecords());
		return pageBean;
	}
	@ResponseBody
	@RequestMapping(value = "/mapRealData")
	public String mapRealData(HttpServletRequest request, String enterpriseName) {

		List<EnterpriseInfo> list = new ArrayList<EnterpriseInfo>();
		// 构造条件查询
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		if (StringUtils.isNotBlank(enterpriseName)) {
			ew.orNew();
			ew.like("enterprise_name", enterpriseName);
		}
		ew.orderBy("modify_time", false);

		list = enterpriseInfoService.selectRelationList(ew);

		return JSONObject.toJSONString(list.get(0));
	}

	
}
