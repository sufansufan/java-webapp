package com.ics.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.ics.system.model.SysAuthority;
import com.ics.system.model.SysRole;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysRoleService;
import com.ics.system.service.SysUserService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

/**
 * <p>
 * 角色表，描述用户具有的角色类型，如领导、管理员，每个角色赋予相应的权限 前端控制器
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/system/sysRole")
public class SysRoleController {

	protected static final String indexJsp = "views/system/sysRole/index";
	protected static final String addJsp = "views/system/sysRole/add";
	protected static final String editJsp = "views/system/sysRole/edit";
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;

	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresPermissions(value = "sys_role")
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
	public PagingBean list(HttpServletRequest request, int page, int limit, String roleName) {

		Page<SysRole> pager = new Page<>(page, limit);

		// 构造条件查询
		EntityWrapper<SysRole> ew = new EntityWrapper<>();
		ew.setEntity(new SysRole());

		if (StringUtils.isNotBlank(roleName)) {
			ew.like("role_name", roleName);
		}
		ew.orderBy("create_time", false);
		pager = sysRoleService.selectPage(pager, ew);

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
	@RequiresPermissions(value = "sys_role_add")
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
	@RequiresPermissions(value = "sys_role_edit")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(editJsp);
//		SysRole model = this.sysRoleService.selectById(id);
		SysRole model = this.sysRoleService.selectRelationById(id);
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
	@RequiresPermissions(value = "sys_role_add")
	@RequestMapping(value = "/addSave", method = RequestMethod.POST)
	public JsonResult addSave(HttpServletRequest request, SysRole model) {

		JsonResult jsonResult = new JsonResult();
		model.setId(CommonUtil.getRandomUUID());
		model.setCreateTime(new Date());
		boolean flag = this.sysRoleService.insert(model);
		if (!flag && jsonResult.getMsg().equals("操作成功")) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}

	/**
	 * 编辑保存
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_role_edit")
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public JsonResult editSave(HttpServletRequest request, SysRole model) {
		JsonResult jsonResult = new JsonResult();
		model.setModifyTime(new Date());
		boolean flag = this.sysRoleService.updateById(model);
		if (!flag && jsonResult.getMsg().equals("操作成功")) {
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
	@RequiresPermissions(value = "sys_role_del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResult del(HttpServletRequest request, String ids, String names) {

		JsonResult jsonResult = new JsonResult();

		boolean flag = false;
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			// 构造条件查询
			EntityWrapper<SysUser> ew = new EntityWrapper<>();
			ew.setEntity(new SysUser());
			ew.in("role_id", idList);
			int count = this.sysUserService.selectCount(ew);
			if (count > 0) {
				jsonResult.setFaildMsg("角色已分配给用户，无法删除!");
			} else {
				flag = this.sysRoleService.deleteBatchIds(idList);
			}
		}

		if (!flag && jsonResult.getMsg().equals("操作成功")) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}

	/**
	 * 获取菜单树
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getAuthTree", method = RequestMethod.GET)
	public List<HashMap<String, Object>> getAuthTree(HttpServletRequest request, String roleId) throws Exception {
		SysRole role = this.sysRoleService.selectById(roleId);

		List<HashMap<String, Object>> list = new ArrayList<>();
		// 获取所有菜单
		EntityWrapper<SysAuthority> ew = new EntityWrapper<>();
		ew.setEntity(new SysAuthority());
		ew.orderBy("authority_order", true);
		List<SysAuthority> authList = new SysAuthority().selectList(ew);

		for (int i = 0; i < authList.size(); i++) {
			SysAuthority model = authList.get(i);
			HashMap<String, Object> authHm = new HashMap<String, Object>();
			if (model != null) {
				authHm.put("name", model.getAuthorityName());
				authHm.put("id", model.getId());
				authHm.put("type", model.getAuthorityType());
				authHm.put("code", model.getAuthorityCode());
				authHm.put("pId",
						StringUtils.isBlank(model.getAuthorityParentId()) ? "0" : model.getAuthorityParentId());

				authHm.put("open", "0".equals(String.valueOf(authHm.get("pId"))) ? true : false);

				// 工作台权限默认拥有
				// if("b0d8851e79e24057baddfb753ae7f91d".equals(model.getId())) {
				// authHm.put("checked", true);
				// authHm.put("chkDisabled", true);
				// }
				// 判断是否有应用权限
//				if (role != null && role.getAuthorityCode() != null&& role.getAuthorityCode().contains(model.getAuthorityCode())) {
				if (role != null && role.getAuthorityCode() != null) {
					String[] codeArr  = role.getAuthorityCode().split(",");
					Set<String> set = new HashSet<String>(Arrays.asList(codeArr));
					if(set.contains(model.getAuthorityCode())) {
						authHm.put("checked", true);
					}
					
				}
				list.add(authHm);

			}

		}

		return list;

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
	@RequestMapping(value = "/checkNameExist", method = RequestMethod.GET)
	public JsonResult checkNameExist(HttpServletRequest request, String id, String str) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isNotBlank(str)) {

			// 构造条件查询
			EntityWrapper<SysRole> ew = new EntityWrapper<>();
			ew.setEntity(new SysRole());
			ew.eq("role_name", str);

			List<SysRole> list = this.sysRoleService.selectList(ew);

			if (list != null && list.size() > 0) {

				SysRole model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}

		return jsonResult;
	}

	/**
	 * 编码重复验证是否通过 ：false不通过； true通过
	 * 
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
			if (str.equalsIgnoreCase("admin")) {
				jsonResult.setSuccess(false);
			} else {
				// 构造条件查询
				EntityWrapper<SysRole> ew = new EntityWrapper<>();
				ew.setEntity(new SysRole());
				ew.eq("role_code", str);
				List<SysRole> list = this.sysRoleService.selectList(ew);
				if (list != null && list.size() > 0) {
					SysRole model = list.get(0);
					if (model != null && !model.getId().equals(id)) {
						jsonResult.setSuccess(false);
					}
				}
			}
		}
		return jsonResult;
	}
	
	

}
