package com.ics.dataDesources.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.MonitorFactorTag;
import com.ics.dataDesources.service.MonitorFactorTagService;
import com.ics.utils.CommonUtil;
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

/**
 * 监测因子标签管理
 * @author jjz
 *
 */
@Controller
@RequestMapping("/dataDesources/monitorFactorTag")
public class MonitorFactorTagController {

	protected static final String tagindexJsp = "views/dataDesources/monitorFactorTag/index";
	protected static final String tagaddJsp = "views/dataDesources/monitorFactorTag/add";
	protected static final String tageditJsp = "views/dataDesources/monitorFactorTag/edit";

	@Autowired
	private MonitorFactorTagService monitorFactorTagService;

	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresPermissions(value = "monitor_factor_tag")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(tagindexJsp);
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
	public PagingBean list(HttpServletRequest request, int page, int limit, String tagName) {

		Page<MonitorFactorTag> pager = new Page<>(page, limit);

		// 构造条件查询
		EntityWrapper<MonitorFactorTag> ew = new EntityWrapper<>();
		ew.setEntity(new MonitorFactorTag());

		if (StringUtils.isNotBlank(tagName)) {
			ew.andNew();
			ew.like("tag_name", tagName);
		}

		pager = monitorFactorTagService.selectPage(pager, ew);

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
	@RequiresPermissions(value = "monitor_factor_tag_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(tagaddJsp);
		return mav;
	}

	/**
	 * 编辑页面
	 *
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = "monitor_factor_tag_edit")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(tageditJsp);
		MonitorFactorTag model = this.monitorFactorTagService.selectById(id);
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
	@RequiresPermissions(value = "monitor_factor_tag_add")
	@RequestMapping(value = "/addSave", method = RequestMethod.POST)
	public JsonResult addSave(HttpServletRequest request, MonitorFactorTag model) {

		JsonResult jsonResult = new JsonResult();

		boolean flag;
		model.setCreateTime(new Date());
		model.setModifyTime(new Date());
		model.setId(CommonUtil.getRandomUUID());
		flag = this.monitorFactorTagService.insert(model);
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
	@RequiresPermissions(value = "monitor_factor_tag_edit")
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public JsonResult editSave(HttpServletRequest request, MonitorFactorTag model) {
		
		JsonResult jsonResult = new JsonResult();

		boolean flag;
		model.setModifyTime(new Date());
		flag = this.monitorFactorTagService.updateById(model);
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
	@RequiresPermissions(value = "monitor_factor_tag_del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResult del(HttpServletRequest request, String ids, String names) {
		JsonResult jsonResult = new JsonResult();
		boolean flag = false;
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			flag = this.monitorFactorTagService.deleteBatchIds(idList);
		}
		if (!flag) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkTagNameExist", method = RequestMethod.GET)
	public JsonResult checkTagNameExist(HttpServletRequest request, String id, String str) {
		
		JsonResult jsonResult = new JsonResult();
		
		if (StringUtils.isNotBlank(str)) {

			// 构造条件查询
			EntityWrapper<MonitorFactorTag> ew = new EntityWrapper<>();
			ew.setEntity(new MonitorFactorTag());
			ew.eq("tag_name", str);

			List<MonitorFactorTag> list = this.monitorFactorTagService.selectList(ew);

			if (list != null && list.size() > 0) {

				MonitorFactorTag model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}
		
		return jsonResult;
	}
	
}

