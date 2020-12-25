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
import com.ics.system.model.SysDictionary;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.service.SysDictionaryItemService;
import com.ics.system.service.SysDictionaryService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
@Controller
@RequestMapping("/system/sysDictionary")
public class SysDictionaryController {
	protected static final String indexJsp = "views/system/sysDictionary/index";
	protected static final String addJsp = "views/system/sysDictionary/add";
	protected static final String editJsp = "views/system/sysDictionary/edit";

	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SysDictionaryItemService sysDictionaryItemService;

	@RequiresPermissions(value = "sys_dict")
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
	@RequiresPermissions(value = "sys_dict")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public PagingBean list(HttpServletRequest request, int page, int limit, String keyword) {

		Page<SysDictionary> pager = new Page<>(page, limit);

		// 构造条件查询
		EntityWrapper<SysDictionary> ew = new EntityWrapper<>();
		ew.setEntity(new SysDictionary());

		if (StringUtils.isNotBlank(keyword)) {
			ew.orNew();
			ew.like("dict_code", keyword);
			ew.or();
			ew.like("dict_name", keyword);
			
		}
		ew.orderBy("create_time", false);
		
		pager = sysDictionaryService.selectPage(pager, ew);

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
	public ModelAndView add(HttpServletRequest request, String id) {
		
		ModelAndView mav = new ModelAndView(addJsp);
		if(StringUtils.isNotBlank(id)){
			SysDictionary model = this.sysDictionaryService.selectById(id);
			mav.addObject("model", model);
		}
		
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
	public JsonResult addSave(HttpServletRequest request, SysDictionary model) {
		
		JsonResult jsonResult = new JsonResult();
		
		boolean flag;
		
		if (StringUtils.isBlank(model.getId())) {
			//add
			model.setId(CommonUtil.getRandomUUID());
			model.setCreateTime(new Date());
			flag = this.sysDictionaryService.insert(model);
			
		}else {
			
			model.setModifyTime(new Date());
			flag = this.sysDictionaryService.updateById(model);
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
	public ModelAndView edit(HttpServletRequest request, String id) {
		
		ModelAndView mav = new ModelAndView(editJsp);
		if(StringUtils.isNotBlank(id)){
			SysDictionary model = this.sysDictionaryService.selectById(id);
			mav.addObject("model", model);
		}
		
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
	public JsonResult editSave(HttpServletRequest request, SysDictionary model) {
		
		JsonResult jsonResult = new JsonResult();
		
		boolean flag;
		
		if (StringUtils.isBlank(model.getId())) {
			//add
			model.setId(CommonUtil.getRandomUUID());
			model.setCreateTime(new Date());
			flag = this.sysDictionaryService.insert(model);
			
		}else {
			
			model.setModifyTime(new Date());
			flag = this.sysDictionaryService.updateById(model);
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
			
			EntityWrapper<SysDictionaryItem> ew = new EntityWrapper<>();
			ew.setEntity(new SysDictionaryItem());
			ew.in("dict_id", idList);
			
			int count = this.sysDictionaryItemService.selectCount(ew);
			if(count>0) {
				jsonResult.setFaildMsg("存在字典项，请先删除字典项");
			}else {
				flag = this.sysDictionaryService.deleteBatchIds(idList);
			}
			
			
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
	public JsonResult checkCodeExist(HttpServletRequest request, String id, String str) {
		
		JsonResult jsonResult = new JsonResult();
		
		if (StringUtils.isNotBlank(str)) {

			EntityWrapper<SysDictionary> ew = new EntityWrapper<>();
			ew.setEntity(new SysDictionary());
			ew.eq("dict_code", str);

			List<SysDictionary> list = this.sysDictionaryService.selectList(ew);

			if (list != null && list.size() > 0) {

				SysDictionary model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}
		
		return jsonResult;
	}
}
