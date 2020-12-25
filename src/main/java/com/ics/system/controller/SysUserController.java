package com.ics.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.system.model.SysOrg;
import com.ics.system.model.SysRole;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.system.service.SysRoleService;
import com.ics.system.service.SysUserService;
import com.ics.utils.*;
import com.ics.utils.security.SM3;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表，用于登录管理平台和终端操作 前端控制器
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/system/sysUser")
public class SysUserController {

	protected static final String indexJsp = "/views/system/sysUser/index";
	protected static final String addJsp = "views/system/sysUser/add";
	protected static final String editJsp = "views/system/sysUser/edit";
	protected static final String viewJsp = "views/system/sysUser/view";
	protected static final String changePwdJsp = "views/system/sysUser/changePwd";
	protected static final String userTreeJsp = "views/system/sysUser/userTree";
	protected static final String importJsp = "views/system/sysUser/import";

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysOrgService sysOrgService;
	
	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresPermissions(value = "sys_user")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, String nodeId) {
		ModelAndView mav = new ModelAndView(indexJsp);
		mav.addObject("nodeId", nodeId);
		return mav;
	}

	/**
	 * 请求列表数据
	 * 
	 * @param request
	 * @param page
	 * @param limit
	 * @param userName
	 * @param orgIds
	 * @param field
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PagingBean list(HttpServletRequest request, int page, int limit, String userName, String orgIds,
			String field, String order, String selectNodeId) {
		Page<SysUser> pager = new Page<>(page, limit);
		// 构造条件查询
		EntityWrapper<SysUser> ew = new EntityWrapper<>();
		ew.setEntity(new SysUser());
		if (StringUtils.isNotBlank(userName)) {
			ew.orNew();
			ew.like("user_code", userName);
			ew.or();
			ew.like("user_name", userName);
			ew.or();
			ew.like("cellphone", userName);
		}
//		if (StringUtils.isNotBlank(orgIds)) {
//			ew.andNew();
//			ew.in("bc.org_id", orgIds.split(","));
//		}
		if (StringUtils.isNotBlank(selectNodeId)) {
			ew.andNew();
			ew.in("bc.org_id", selectNodeId);
		}
		if(StringUtils.isNotBlank(selectNodeId)) {
			ew.orderBy("CASE bc.org_id WHEN '" + selectNodeId + "' THEN 1 ELSE 2 END", true);
		}
		if(StringUtils.isNotBlank(order) && "userCode".equals(field)) {
			ew.orderBy("user_code " + order);
		}else {
			ew.orderBy("user_code", true);
		}
//		ew.orderBy("createTime", false);
		pager = sysUserService.selectRelationList(pager, ew);

		PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
		pageBean.setData(pager.getRecords());
		return pageBean;
	}

	/**
	 * 新增页面
	 * 
	 * @param request
	 * @param id
	 * @param orgId
	 * @param type 请求类型 1正常 2个人信息(屏蔽部分输入)
	 * @return
	 */
	@RequiresPermissions(value = "sys_user_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, String id, String orgId, @RequestParam(defaultValue="1") int type) {
		
		ModelAndView mav = new ModelAndView(addJsp);
//		List<String> getorgIdList  = null;
		if (StringUtils.isNotBlank(orgId)) {
			SysOrg sysorg = new SysOrg().selectById(orgId);
//			String orgIdPath  = sysorg.getOrgIdPath();
//			String[] orgIdPathArr  = orgIdPath.split(":");
//			getorgIdList = Arrays.asList(orgIdPathArr);
			mav.addObject("sysorg", sysorg);
		}
		
		// 获取用户登录所属区域idlist
		EntityWrapper<SysRole> ew = new EntityWrapper<>();
//		ew.setEntity(new SysRole());
//		SysUser loginUser = new SysUser();
//		loginUser.setOrgId(orgId);
//		List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
//		if (getorgIdList.size() > 0) {
//			ew.in("org_id", getorgIdList);
//		}
		// 获取角色列表
		List<SysRole> roleList = this.sysRoleService.selectList(ew);
		
		
		mav.addObject("roleList", roleList);
		mav.addObject("type", type);
		mav.addObject("upload_url", ConstantProperty.sysUser_url);
		return mav;
	}
	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param id
	 * @param orgId
	 * @param type 请求类型 1正常 2个人信息(屏蔽部分输入)
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, String id, String orgId, @RequestParam(defaultValue="1") int type) {

		ModelAndView mav = new ModelAndView(editJsp);
		if (StringUtils.isNotBlank(id)) {
			SysUser model = this.sysUserService.selectRelationById(id);
			mav.addObject("model", model);
			SysOrg sysorg = new SysOrg().selectById(model.getOrgId());
			mav.addObject("sysorg", sysorg);
		}
		// 获取角色列表
		List<SysRole> roleList = this.sysRoleService.selectList(null);
		

		mav.addObject("roleList", roleList);
		mav.addObject("type", type);
		return mav;
	}
	/**
	 * 新增保存
	 * 
	 * @param request
	 * @param model
	 * @param isChangePwd
	 * @param type 1正常 2个人信息
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_user_add")
	@RequestMapping(value = "/addSave", method = RequestMethod.POST)
	public JsonResult addSave(HttpServletRequest request, SysUser model, boolean isChangePwd, boolean isChangeTerminalPwd, @RequestParam(defaultValue="1") int type, boolean isChangePhoto,String deadlineStr) throws ParseException {

		JsonResult jsonResult = new JsonResult();
		if (isChangePwd) {
			model.setUserPassword(
					SM3.hash(model.getUserPassword() + ConstantProperty.userSalt));
		} else {
			model.setUserPassword(null);
		}
		if (isChangeTerminalPwd) {
			model.setTerminalUserPassword(
					SM3.hash(model.getTerminalUserPassword() + ConstantProperty.userSalt));
		} else {
			model.setTerminalUserPassword(null);
		}

		model.setId(CommonUtil.getRandomUUID());
		model.setCreateTime(new Date());

		model.setOrgIdPath(null);
		//获取机构树的orgIdPath
		SysOrg sysOrg = this.sysOrgService.selectById(model.getOrgId());
		if(sysOrg!=null) {
			model.setOrgIdPath(sysOrg.getOrgIdPath());
		}
		
		if(model.getIdentityIdx()==null) model.setDefaultIdx();
		
		boolean flag = this.sysUserService.insert(model);

		if (!flag) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	/**
	 * 编辑保存
	 * 
	 * @param request
	 * @param model
	 * @param isChangePwd
	 * @param type 1正常 2个人信息
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public JsonResult editSave(HttpServletRequest request, SysUser model, boolean isChangePwd, boolean isChangeTerminalPwd, @RequestParam(defaultValue="1") int type, boolean isChangePhoto,String deadlineStr) throws ParseException {
		
		JsonResult jsonResult = new JsonResult();
		if (isChangePwd) {
			model.setUserPassword(
					SM3.hash(model.getUserPassword() + ConstantProperty.userSalt));
		} else {
			model.setUserPassword(null);
		}
		if (isChangeTerminalPwd) {
			model.setTerminalUserPassword(
					SM3.hash(model.getTerminalUserPassword() + ConstantProperty.userSalt));
		} else {
			model.setTerminalUserPassword(null);
		}
		model.setModifyTime(new Date());
		if(model.getIdentityIdx()==null && type!=2) model.setDefaultIdx();
		boolean flag = this.sysUserService.updateById(model);
		
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
	@RequiresPermissions(value = "sys_user_del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResult del(HttpServletRequest request, String ids, String names) {

		JsonResult jsonResult = new JsonResult();
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		boolean flag = false;
		if (StringUtils.isNotBlank(ids)) {
			if(!loginUser.getIssupermanager() && ids.contains(loginUser.getId())) {
				jsonResult.setFaildMsg("无法删除自身用户!");
			}else {
				try{
					List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
					flag = this.sysUserService.deleteAllDataByBatchIds(idList);
				}catch(Exception ex){
					String msg = ex.getMessage();
					jsonResult.setFaildMsg(msg);
				}
			}
		}

		if(!flag && jsonResult.getMsg().equals("操作成功")) {
			jsonResult.setFaild();
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
	public JsonResult checkCodeExist(HttpServletRequest request, String id, String code) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setSuccess(isCodeNotExist(id, code));
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
	public static boolean isCodeNotExist(String id, String code) {

		boolean result = true;
		if (StringUtils.isNotBlank(code)) {
			if ("admin".equalsIgnoreCase(code)) {
				result = false;
			} else {
				// 构造条件查询
				EntityWrapper<SysUser> ew = new EntityWrapper<>();
				ew.setEntity(new SysUser());
				ew.eq("user_code", code);

				List<SysUser> list = new SysUser().selectList(ew);
				if (list != null && list.size() > 0) {
					SysUser model = list.get(0);
					if (model != null && !model.getId().equals(id)) {
						result = false;
					}
				}

			}

		}

		return result;
	}

	/**
	 * 用户密码修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changePwd")
	public ModelAndView changePwd(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(changePwdJsp);
		return mav;
	}

	/**
	 * 保存密码修改
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/changePwdSave", method = RequestMethod.POST)
	public JsonResult changePwdSave(HttpServletRequest request, String oldPwd, String newPwd) throws Exception {

		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		JsonResult jsonResult = new JsonResult();
		String userId = loginUser.getId();

		if (loginUser.getIssupermanager()) {
			//获取配置文件密码
			String oldPd = PropertiesUtils.getPara("super_pwd",true);
			if (SM3.hash(oldPwd + ConstantProperty.userSalt).equals(oldPd)) {
				try {
					PropertiesUtils.setPara("super_pwd",SM3.hash(newPwd + ConstantProperty.userSalt));
					jsonResult.setMsg("密码修改成功");
				} catch (Exception e) {
					jsonResult.setFaildMsg("密码更新失败");
				}
				
			} else {
				jsonResult.setFaildMsg("旧密码错误");
			}
		} else {
			// 密码校验
			SysUser user = this.sysUserService.selectById(userId);

			if (SM3.hash(oldPwd + ConstantProperty.userSalt).equals(user.getUserPassword())) {
				user.setUserPassword(SM3.hash(newPwd + ConstantProperty.userSalt));
				boolean flag = this.sysUserService.updateById(user);
				if (!flag) {
					jsonResult.setFaildMsg("密码更新失败");
				} else {
					jsonResult.setMsg("密码修改成功");
				}
			} else {
				jsonResult.setFaildMsg("旧密码错误");
			}
		}
		return jsonResult;
	}

	/**
	 * 用户树弹窗页面
	 * 
	 * @param request
	 * @param userId
	 * @param userName
	 * @param idInput
	 * @param nameInput
	 * @return
	 */
	@RequestMapping(value = "/userTree", method = RequestMethod.GET)
	public ModelAndView userTree(HttpServletRequest request, String userId, String userName, String idInput,
			String nameInput) {
		ModelAndView mav = new ModelAndView(userTreeJsp);

		mav.addObject("userId", userId);
		mav.addObject("userName", userName);
		mav.addObject("idInput", idInput);
		mav.addObject("nameInput", nameInput);

		return mav;

	}

	/**
	 * 导入页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "import")
	@RequiresPermissions(value = "sys_user_import")
	public ModelAndView importPage(HttpServletRequest request, String orgId, String orgIdPath) {
		ModelAndView mav = new ModelAndView(importJsp);
		mav.addObject("orgId", orgId);
		mav.addObject("orgIdPath", orgIdPath);
		mav.addObject("upload_url", ConstantProperty.import_sysUser_url);
		mav.addObject("down_url", ConstantProperty.tpl_sysUser_url);
		return mav;
	}

	@ResponseBody
	@RequiresPermissions(value = "sys_user_import")
	@RequestMapping(value = "/analyImport", method = RequestMethod.POST)
	public JsonResult analyImport(HttpServletRequest request, String orgId, String path, String orgIdPath) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isNotBlank(orgId)) {

			String realPath = request.getSession().getServletContext().getRealPath("/") + path;

			try {
				jsonResult = this.sysUserService.importList(orgId, realPath, orgIdPath);
			} catch (Exception ex) {
				String msg = ex.getMessage();
				String[] smsg = msg.split(",");

				jsonResult.setFaildMsg("第" + smsg[0] + "行数据导入失败:" + smsg[1]);
			}
		}

		return jsonResult;
	}
	
	/**
	 * 查看详情
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = "sys_user_view")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView(viewJsp);
		
		SysUser model = new SysUser();
		if (StringUtils.isNotBlank(id)) {
			model = this.sysUserService.selectById(id);
			if(StringUtils.isNotBlank(model.getRoleId())) {
				SysRole role = this.sysRoleService.selectById(model.getRoleId());
				model.setSysrole(role);
			}
			SysOrg sysorg = new SysOrg().selectById(model.getOrgId());
			mav.addObject("sysorg", sysorg);
		}
		mav.addObject("model", model);
		mav.addObject("upload_url", ConstantProperty.sysUser_url);
		return mav;
	}
	
	/**
	 * 根据id判断用户是否存在
	 * @param id
	 * @return boolean
	 */
	public static boolean isExistById(String id) {
		boolean flag = false;
		
		int count = new SysUser().selectCount("id={0}", id);
		
		if(count>0) {
			flag = true;
		}
		return flag;
	}
	
}
