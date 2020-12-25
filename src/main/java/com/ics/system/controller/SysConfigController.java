package com.ics.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.system.model.SysConfig;
import com.ics.system.service.SysConfigService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

/**
 * <p>
 * 配置表 前端控制器
 * </p>
 *
 * @author yi
 * @since 2017-11-28
 */
@Controller
@RequestMapping("/system/sysConfig")
public class SysConfigController {

	protected static final String indexJsp = "views/system/sysConfig/index";
	protected static final String addJsp = "views/system/sysConfig/add";
	protected static final String editJsp = "views/system/sysConfig/edit";
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresPermissions(value = "sys_config")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(indexJsp);
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
	public PagingBean list(HttpServletRequest request, int page, int limit, String configKey) {

		Page<SysConfig> pager = new Page<>(page, limit);

		// 构造条件查询
		EntityWrapper<SysConfig> ew = new EntityWrapper<>();
		ew.setEntity(new SysConfig());

		if (StringUtils.isNotBlank(configKey)) {
			ew.like("config_key", configKey);
		}
		
		ew.orderBy("config_key", true);
		pager = sysConfigService.selectPage(pager, ew);

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
	@RequiresPermissions(value = "sys_config_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(addJsp);
		return mav;
	}

	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = "sys_config_edit")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView(editJsp);
		SysConfig model = this.sysConfigService.selectById(id);
		mav.addObject("model", model);

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
	@RequiresPermissions(value = "sys_config_add")
	@RequestMapping(value = "/addSave", method = RequestMethod.POST)
	public JsonResult addSave(HttpServletRequest request, SysConfig model) {

		JsonResult jsonResult = new JsonResult();

		model.setId(CommonUtil.getRandomUUID());
		if(model.getFlag()==null) {
			model.setFlag(0);
		}
		model.setCreateTime(new Date());
		boolean	flag = this.sysConfigService.insert(model);

		if (!flag) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	/**
	 *	编辑保存
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_config_edit")
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public JsonResult editSave(HttpServletRequest request, SysConfig model) {
		
		JsonResult jsonResult = new JsonResult();
		if(model.getFlag()==null) {
			model.setFlag(0);
		}
		model.setModifyTime(new Date());
		boolean flag = this.sysConfigService.updateById(model);
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
	@RequiresPermissions(value = "sys_config_del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResult del(HttpServletRequest request, String ids, String names) {
		JsonResult jsonResult = new JsonResult();
		boolean flag = false;
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			flag = this.sysConfigService.deleteBatchIds(idList);
		}
		if (!flag) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	
}
