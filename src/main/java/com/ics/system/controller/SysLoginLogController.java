package com.ics.system.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ics.system.model.SysLog;
import com.ics.system.service.SysLogService;
import com.ics.utils.CommonUtil;
import com.ics.utils.DateUtils;
import com.ics.utils.ExcelExportBean;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;
import com.ics.utils.Enum.ModuleType;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author kld
 * @since 2019-08-09
 */
@Controller
@RequestMapping("/system/sysLoginLog")
public class SysLoginLogController {
	
	protected static final String indexJsp="views/system/sysLoginLog/index";
	protected static final String viewDetailJsp="views/system/sysLoginLog/viewDetail";
	
	@Autowired
	private SysLogService sysLoginLogService;
	
	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(indexJsp);
		mav.addObject("sysLoginLogTypeJson", JSONObject.toJSONString(ModuleType.getMap()));
		mav.addObject("loginUser", CommonUtil.getLoginUserInfo(request));
		return mav;
	}
	
	/**
	 * 请求列表数据
	 * @param request
	 * @param page
	 * @param limit
	 * @param orgIds
	 * @param beginTimeStr
	 * @param endTimeStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PagingBean list(HttpServletRequest request, int page, int limit, String orgIds, String beginTimeStr, String endTimeStr ) {
		
		Page<SysLog> pager = new Page<>(page, limit);

		// 构造条件查询
		EntityWrapper<SysLog> ew = getParams(orgIds, beginTimeStr, endTimeStr);
		pager=this.sysLoginLogService.selectPage(pager, ew);
		PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
		pageBean.setData(pager.getRecords());
		return pageBean;
	}
	
	public EntityWrapper<SysLog> getParams(String orgIds, String beginTimeStr, String endTimeStr){
		EntityWrapper<SysLog> ew = new EntityWrapper<>();
		ew.setEntity(new SysLog());
		
		ew.eq("log_type", 1);
		
		if (StringUtils.isNotBlank(beginTimeStr)) {
			Date beginTime = DateUtils.setMinTimeForDate(DateUtils.StringToDate(beginTimeStr, "yyyy-MM-dd"));
			ew.ge("create_time", beginTime);
		}

		if (StringUtils.isNotBlank(endTimeStr)) {
			Date endTime = DateUtils.setMaxTimeForDate(DateUtils.StringToDate(endTimeStr, "yyyy-MM-dd"));
			ew.le("create_time", endTime);
		}

		ew.orderBy("create_time", false);
		return ew;
	}
	/**
	 * 数据导出
	 * @param request
	 * @param response
	 * @param orgIds
	 * @param beginTimeStr
	 * @param endTimeStr
	 * @return
	 */
	@RequestMapping(value = "/excelExport", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult excelExport(HttpServletRequest request, HttpServletResponse response, String orgIds, String beginTimeStr, String endTimeStr) {
		JsonResult result = new JsonResult();
		
		EntityWrapper<SysLog> ew = getParams(orgIds, beginTimeStr, endTimeStr);

		List<SysLog> list = this.sysLoginLogService.selectList(ew);
		
		String fileName = "系统登录管理";
		String title = "系统登录管理";
		
		String[] colNames = {"操作用户","IP地址","操作类型","操作详情","操作时间"};
		
		List<Object[]> dataList = new ArrayList<>();
		if(list != null && list.size()>0) {
			for (SysLog obj : list) {
				Object[] model = {	
						obj.getUserName(),
						obj.getUserIp(),
						getOperateTypeResult(obj.getLogType()),
						obj.getLogDetail(),
						obj.getCreateTime()
				};
				
				dataList.add(model);
			}
		}
		
		ExcelExportBean bean = new ExcelExportBean(fileName, title, colNames, dataList, request);
		
		try {
			String downUrl = bean.export();
			result.setData(downUrl);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFaildMsg("导出失败");
		}
		return result;
		
	}
	
	public String getOperateTypeResult(Integer type) {
		String result = "";
		
		Map<Integer, String> mapType = ModuleType.getMap();
		if(mapType.containsKey(type)) {
			result = mapType.get(type);
		}
		
		return result;
	}
	
	/**
	 * 查看明细
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value="sys_log_view")
	@RequestMapping(value = "/viewDetail", method = RequestMethod.GET)
	public ModelAndView viewDetail(HttpServletRequest request, String id) {
		
		ModelAndView mav = new ModelAndView(viewDetailJsp);
		SysLog model=this.sysLoginLogService.selectById(id);
		mav.addObject("model", model);
		mav.addObject("sysLoginLogTypeMap", ModuleType.getMap());

		return mav;
	}
}
