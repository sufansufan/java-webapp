package com.ics.system.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.service.SysDictionaryItemService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 数据字典项表 前端控制器
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
@Controller
@RequestMapping("/system/sysDictionaryItem")
public class SysDictionaryItemController {
	protected static final String indexJsp = "views/system/sysDictionaryItem/index";
	protected static final String addJsp = "views/system/sysDictionaryItem/add";
	protected static final String editJsp = "views/system/sysDictionaryItem/edit";

	@Autowired
	private SysDictionaryItemService sysDictionaryItemService;

	@RequiresPermissions(value = "sys_dict")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, String dictId) {
		ModelAndView mav = new ModelAndView(indexJsp);
		mav.addObject("dictId", dictId);
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
	@RequiresPermissions(value = "sys_dict")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public PagingBean list(HttpServletRequest request, int page, int limit, String keyword, String dictId) {

		Page<SysDictionaryItem> pager = new Page<>(page, limit);

		// 构造条件查询
		EntityWrapper<SysDictionaryItem> ew = new EntityWrapper<>();
		ew.setEntity(new SysDictionaryItem());

		if (StringUtils.isNotBlank(dictId)) {
			ew.eq("dict_id", dictId);
		}
		if (StringUtils.isNotBlank(keyword)) {
			ew.like("item_name", keyword);
		}
		ew.orderBy("sort_idx", true);
		
		pager = sysDictionaryItemService.selectPage(pager, ew);

		PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
		pageBean.setData(pager.getRecords());
		return pageBean;
	}

	/**
	 * 新增/编辑页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = "sys_dict_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, String id, String dictId) {
		
		ModelAndView mav = new ModelAndView(addJsp);
		if(StringUtils.isNotBlank(id)){
			SysDictionaryItem model = this.sysDictionaryItemService.selectById(id);
			mav.addObject("model", model);
		}
		mav.addObject("dictId", dictId);
		return mav;
	}
	
	/**
	 * 新增/编辑保存
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_dict_add")
	@RequestMapping(value = "/addSave", method = RequestMethod.POST)
	public JsonResult addSave(HttpServletRequest request, SysDictionaryItem model) {
		
		JsonResult jsonResult = new JsonResult();
		
		boolean flag;
		if(model.getStatus()==null) model.setStatus(0);
		if (StringUtils.isBlank(model.getId())) {
			//add
			model.setId(CommonUtil.getRandomUUID());
			model.setCreateTime(new Date());
			flag = this.sysDictionaryItemService.insert(model);
			
		}else {
			
			model.setModifyTime(new Date());
			flag = this.sysDictionaryItemService.updateById(model);
		}
		
		if(!flag && jsonResult.getMsg().equals("操作成功")) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	
	/**
	 * 新增/编辑页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = "sys_dict_edit")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, String id, String dictId) {
		
		ModelAndView mav = new ModelAndView(editJsp);
		if(StringUtils.isNotBlank(id)){
			SysDictionaryItem model = this.sysDictionaryItemService.selectById(id);
			mav.addObject("model", model);
		}
		mav.addObject("dictId", dictId);
		return mav;
	}
	
	/**
	 * 新增/编辑保存
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_dict_edit")
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public JsonResult editSave(HttpServletRequest request, SysDictionaryItem model) {
		
		JsonResult jsonResult = new JsonResult();
		
		boolean flag;
		if(model.getStatus()==null) model.setStatus(0);
		if (StringUtils.isBlank(model.getId())) {
			//add
			model.setId(CommonUtil.getRandomUUID());
			model.setCreateTime(new Date());
			flag = this.sysDictionaryItemService.insert(model);
			
		}else {
			
			model.setModifyTime(new Date());
			flag = this.sysDictionaryItemService.updateById(model);
		}
		
		if(!flag && jsonResult.getMsg().equals("操作成功")) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	
	/**
	 * 删除，支持批量删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_dict_del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResult del(HttpServletRequest request, String ids, String names) {
		
		JsonResult jsonResult = new JsonResult();
		
		boolean flag = false;
		
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			flag = this.sysDictionaryItemService.deleteBatchIds(idList);
		}
		
		if(!flag && jsonResult.getMsg().equals("操作成功")) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	
	/**
	 * 名称重复验证是否通过 ：false不通过； true通过
	 * @param request
	 * @param id
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCodeExist", method = RequestMethod.GET)
	public JsonResult checkCodeExist(HttpServletRequest request, String id, String str, String dictId) {
		
		JsonResult jsonResult = new JsonResult();
		
		if (StringUtils.isNotBlank(str)) {

			EntityWrapper<SysDictionaryItem> ew = new EntityWrapper<>();
			ew.setEntity(new SysDictionaryItem());
			ew.eq("item_value", str);
			ew.eq("dict_id", dictId);

			List<SysDictionaryItem> list = this.sysDictionaryItemService.selectList(ew);

			if (list != null && list.size() > 0) {

				SysDictionaryItem model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}
		
		return jsonResult;
	}
}
