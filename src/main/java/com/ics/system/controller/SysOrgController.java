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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.system.model.SysOrg;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.system.service.SysUserService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;

/**
 * <p>
 * 组织机构表 前端控制器
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/system/sysOrg")
public class SysOrgController {
	
	protected static final String indexJsp="views/system/sysOrg/index";
	protected static final String addJsp="views/system/sysOrg/add";
	protected static final String editJsp="views/system/sysOrg/edit";
	@Autowired
	private SysOrgService sysOrgService;
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresPermissions(value = "sys_org")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(indexJsp);
		
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		
		mav.addObject("loginUser", loginUser);
		return mav;
	}
	
	
	/**
	 * 获取机构树
	 * @param request
	 * @param showAll 是否显示所有节点
	 * @param showUser 是否显示机构下用户
	 * @param showUserIden 用户身份，用,分隔
	 * @param addCustomNode 自定义节点，json串
	 * @param showCabinet 是否显示机构下存管柜
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrgTree", method = RequestMethod.POST)
	public JSONArray getOrgTree(HttpServletRequest request, 
			boolean showAll, boolean showUser, String showUserIden, String addCustomNode,
			boolean showCabinet,boolean showCase) {
		
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		
		JSONArray jsonArray = new JSONArray();
		List<SysOrg> list = new ArrayList<>();
		
		if(showAll || loginUser.getIssupermanager()) {
			EntityWrapper<SysOrg> ew = new EntityWrapper<>();
			ew.setEntity(new SysOrg());
			ew.orderBy("sort_idx", true);
			list = this.sysOrgService.selectList(ew);
		}else {
			//根据用户所属机构，查询其可见机构
			String orgId = loginUser.getOrgId();
			
			EntityWrapper<SysOrg> ew = new EntityWrapper<>();
			ew.setEntity(new SysOrg());
			ew.like("org_id_path", orgId);
			ew.orderBy("sort_idx", true);
			
			list = this.sysOrgService.selectList(ew);
		}
		
		List<String> orgIdList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			
			SysOrg org = list.get(i);
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("id", org.getId());
			jsonObject.put("name", org.getOrgName());
			jsonObject.put("code", org.getOrgCode());
			//jsonObject.put("open", true);
			jsonObject.put("orgIdPath", org.getOrgIdPath());
			jsonObject.put("pId", StringUtils.isBlank(org.getParentId())?"0":org.getParentId());
			
			jsonArray.add(jsonObject);
			
			orgIdList.add(org.getId());
		}
		
		//orgIdList
		if(StringUtils.isNotBlank(addCustomNode)) {
			
			try {
				JSONObject other = JSONObject.parseObject(addCustomNode);
				Integer order = other.getInteger("order");
				if(order != null) {
					jsonArray.add(order, other);
				}else {
					jsonArray.add(other);
				}
				
			} catch (Exception e) {
				
			}
		}
		if(showUser) {
			//获取用户节点
			EntityWrapper<SysUser> ew = new EntityWrapper<>();
			ew.setEntity(new SysUser());
			ew.orderBy("user_code", true);
			
			if(StringUtils.isNotBlank(showUserIden)) {
				String[] idenAry = showUserIden.split(",");
				ew.in("bc.identity_idx", idenAry);
			}
			
			List<SysUser> userList = this.sysUserService.selectIdentityList(ew);
			if(userList != null) {
				for (SysUser user : userList) {
					if(orgIdList.contains(user.getOrgId())) {
						
						JSONObject jsonObject = new JSONObject();
						
						jsonObject.put("id", user.getId());
						jsonObject.put("name", user.getUserName()+"("+user.getUserCode()+")");
						jsonObject.put("code", user.getUserCode());
						jsonObject.put("pId", user.getOrgId());
						jsonObject.put("isUser", true);
						
						String iconSkin = "user";
						if(StringUtils.isNotBlank(user.getIdentityImage())) {
							iconSkin += user.getIdentityImage();
						}else {
							iconSkin += "3";
						}
						jsonObject.put("iconSkin", iconSkin);
						jsonArray.add(jsonObject);
						
					}
				}
			}
		}
		
		return jsonArray;
	}
	
	/**
	 * 新增页面
	 * @param request
	 * @param id
	 * @param pId
	 * @return
	 */
	@RequiresPermissions(value = "sys_org_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, String id,String pId) {
		ModelAndView mav = new ModelAndView(addJsp);
		String orgIdPath = null;
		SysOrg pModel = null;
		if(StringUtils.isNotBlank(pId)){
			pModel = this.sysOrgService.selectById(pId);
			mav.addObject("pModel", pModel);
		}
			//add
		if(pModel != null)
			orgIdPath = pModel.getOrgIdPath();
			mav.addObject("pId", pId);
			mav.addObject("orgIdPath", orgIdPath);
			
		return mav;
	}
	
	/**
	 * 编辑页面
	 * @param request
	 * @param id
	 * @param pId
	 * @return
	 */
	@RequiresPermissions(value = "sys_org_edit")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, String id,String pId) {
		ModelAndView mav = new ModelAndView(editJsp);
		String orgIdPath = null;
		SysOrg pModel = null;
		if(StringUtils.isNotBlank(pId)){
			pModel = this.sysOrgService.selectById(pId);
			mav.addObject("pModel", pModel);
		}
			SysOrg model = this.sysOrgService.selectById(id);
			mav.addObject("model", model);
			mav.addObject("pId", model.getParentId());
			
			orgIdPath = model.getOrgIdPath();
			
			if(StringUtils.isNotBlank(model.getParentId())) {
				pModel = this.sysOrgService.selectById(model.getParentId());
				mav.addObject("pModel", pModel);
			}
			mav.addObject("orgIdPath", orgIdPath);
		return mav;
	}
	/**
	 * 新增保存
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_org_add")
	@RequestMapping(value = "/addSave", method = RequestMethod.POST)
	public JsonResult addSave(HttpServletRequest request, SysOrg model) {
		
		JsonResult jsonResult = new JsonResult();
		
		model.setId(CommonUtil.getRandomUUID());
		model.setCreateTime(new Date());
		
		//orgIdPath增加自身id
		String oIdPath = model.getOrgIdPath();
		if(StringUtils.isNotBlank(oIdPath)) {
			oIdPath = oIdPath.replaceAll(",", ":");
			oIdPath += ":";
		}
		oIdPath += model.getId();
		
		model.setOrgIdPath(oIdPath);
		
		if(model.getSortIdx()==null) {
			model.setSortIdx(100);
		}
		
		boolean flag = this.sysOrgService.insert(model);
		
		jsonResult.setData(model);
		if(!flag) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	
	/**
	 * 编辑保存
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_org_edit")
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public JsonResult editSave(HttpServletRequest request, SysOrg model) {
		
		JsonResult jsonResult = new JsonResult();
		model.setModifyTime(new Date());
		if(model.getSortIdx()==null) {
			model.setSortIdx(100);
		}
		boolean flag = this.sysOrgService.updateById(model);	
		jsonResult.setData(model);
		if(!flag) {
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
	@RequiresPermissions(value = "sys_org_del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResult del(HttpServletRequest request, String ids, String names) {
		
		JsonResult jsonResult = new JsonResult();
		boolean flag = false;
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			flag = this.sysOrgService.deleteBatchIds(idList);
		}
		
		if(!flag && jsonResult.getMsg().equals("操作成功")) {
			jsonResult.setFaild();
		}
		return jsonResult;
	}
	
	/**
	 * 编码重复验证是否通过 ：false不通过； true通过
	 * @param request
	 * @param id
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkExist", method = RequestMethod.GET)
	public JsonResult checkExist(HttpServletRequest request, String id, String code) {
		
		JsonResult jsonResult = new JsonResult();
		
		if (StringUtils.isNotBlank(code)) {
			
			//构造条件查询
			 EntityWrapper<SysOrg> ew=new EntityWrapper<>();
			 ew.setEntity(new SysOrg());
			 ew.eq("org_code", code);
			 
			List<SysOrg> list = this.sysOrgService.selectList(ew);
			
			if(list != null && list.size()>0) {
				
				SysOrg model = list.get(0);
				if(model!=null && !model.getId().equals(id)){
					jsonResult.setSuccess(false);
				}
			}
			
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
	@RequestMapping(value = "/checkNameExist", method = RequestMethod.GET)
	public JsonResult checkNameExist(HttpServletRequest request, String id, String str, String parentId) {
		
		JsonResult jsonResult = new JsonResult();
		
		if (StringUtils.isNotBlank(str)) {

			// 构造条件查询
			EntityWrapper<SysOrg> ew = new EntityWrapper<>();
			ew.setEntity(new SysOrg());
			ew.eq("parent_id", parentId==null?"":parentId);
			ew.eq("org_name", str);

			List<SysOrg> list = this.sysOrgService.selectList(ew);

			if (list != null && list.size() > 0) {

				SysOrg model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}
		
		return jsonResult;
	}
}
