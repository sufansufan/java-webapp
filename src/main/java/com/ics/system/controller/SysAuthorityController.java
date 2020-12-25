package com.ics.system.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.ics.system.model.SysAuthority;
import com.ics.system.service.SysAuthorityService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/system/sysAuthority")
public class SysAuthorityController {
	
	protected static final String indexJsp = "/views/system/sysAuthority/index";
	protected static final String addJsp = "/views/system/sysAuthority/add";
	protected static final String editJsp = "/views/system/sysAuthority/edit";
	
	
	@Autowired
	private SysAuthorityService sysAuthorityService;
	
	
	
	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresPermissions(value = "sys_authority")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, String nodeId) {
		ModelAndView mav = new ModelAndView(indexJsp);
		return mav;
	}
	
	/**
	 * 请求列表数据
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONObject list(HttpServletRequest request) {

		
		EntityWrapper<SysAuthority> ew = new EntityWrapper<>();
		ew.setEntity(new SysAuthority());
		ew.orderBy("authorityOrder", true);
		
		List<SysAuthority> list = sysAuthorityService.selectList(ew);
        
        JSONArray jsonArray = new JSONArray();       
		for (SysAuthority sysAuthority : list) {
			JSONObject json = new JSONObject();
			json.put("id", sysAuthority.getId());
			json.put("authorityCode", sysAuthority.getAuthorityCode());
			json.put("authorityImage", sysAuthority.getAuthorityImage());
			json.put("authorityName", sysAuthority.getAuthorityName());
			json.put("authorityOrder", sysAuthority.getAuthorityOrder());
			json.put("authorityParentId", sysAuthority.getAuthorityParentId());
			json.put("authorityType", sysAuthority.getAuthorityType());
			json.put("authorityUrl", sysAuthority.getAuthorityUrl());
			json.put("createTime", sysAuthority.getCreateTime());
			json.put("modifyTime", sysAuthority.getModifyTime());

			jsonArray.add(json);
		}
		
		
		JSONObject  result=new JSONObject() ;
		result.put("data", jsonArray);
		result.put("count", list.size());
   	    
		return result;
	}
	
	
	/**
	 * 新增页面
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = "sys_authority_add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(addJsp);
		mav.addObject("authorityTypeMap", getAuthorityTypeMap());
		if(StringUtils.isNotBlank(id)) {
			SysAuthority pModel = this.sysAuthorityService.selectById(id);
			mav.addObject("pModel", pModel);
		}
		
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
	@RequiresPermissions(value = "sys_authority_add")
	@RequestMapping(value = "/addSave", method = RequestMethod.POST)
	public JsonResult addSave(HttpServletRequest request, SysAuthority model) {

		JsonResult jsonResult = new JsonResult();

		model.setId(CommonUtil.getRandomUUID());
		if(!StringUtils.isNotBlank(model.getAuthorityParentId())) {
			model.setAuthorityParentId("0");
		}
		boolean	flag = this.sysAuthorityService.insert(model);

		if (!flag) {
			jsonResult.setFaild();
		}
		

		return jsonResult;
	}

	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = "sys_authority_edit")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView(editJsp);
		SysAuthority model = this.sysAuthorityService.selectById(id);
		mav.addObject("model", model);
		mav.addObject("authorityTypeMap", getAuthorityTypeMap());
		return mav;
	}
	
	/**
	 * 编辑保存
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_authority_edit")
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public JsonResult editSave(HttpServletRequest request, SysAuthority model) {

		JsonResult jsonResult = new JsonResult();

		boolean	flag = this.sysAuthorityService.updateById(model);

		if (!flag) {
			jsonResult.setFaild();
		}

		return jsonResult;
	}
	
	/**
	 * 权限类型
	 * @return
	 */
	public Map<Integer, String> getAuthorityTypeMap(){
		Map<Integer, String> map=new LinkedHashMap<>();
		map.put(1, "菜单");
		map.put(2, "功能");
		return map;		
	}
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_authority_del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResult del(HttpServletRequest request, String ids,String names,String ids2,String names2) {

		JsonResult jsonResult = new JsonResult();
		boolean flag=false;
		String msg="";
		
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));			
			if(idList.size()==1) {
				EntityWrapper<SysAuthority> ew = new EntityWrapper<>();
				ew.setEntity(new SysAuthority());
				ew.eq("authority_parent_id", idList.get(0));
				int count = this.sysAuthorityService.selectCount(ew);
				if(count>0) {
					msg+=("【"+names+"】");
				}else {
					flag=this.sysAuthorityService.deleteBatchIds(idList);
				}
			}
			if(idList.size()>1) {				
				flag=this.sysAuthorityService.deleteBatchIds(idList);
			}
		}
		
		if (StringUtils.isNotBlank(names2)) {	
			List<String> namesList2 = new ArrayList<>(Arrays.asList(names2.split(",")));
			if(namesList2.size()>0) {
				for(int i=0;i<namesList2.size();i++) {				
				    if("".equals(msg)) {
				        msg+=("【"+namesList2.get(i)+"】");
				    }else {
				        msg+=("、【"+namesList2.get(i)+"】");
				    }	
				}
			}		    
		}	

		if(!("").equals(msg)) {
			Map<String, String> map=new HashMap<>();
			map.put("ids", ids2);
			map.put("names", names2);
			map.put("msg", msg);
			jsonResult.setData(map);
			if(flag) {
				jsonResult.isSuccess();
			}else {
				jsonResult.setFaild();
			}
				
		}else if (flag && ("").equals(msg)) {
			jsonResult.isSuccess();
		}	
		return jsonResult;
	}
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys_authority_del")
	@RequestMapping(value = "/directDel", method = RequestMethod.POST)
	public JsonResult directDel(String ids,String names){
		
		JsonResult jsonResult = new JsonResult();
		boolean flag=false;
		
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			flag=this.sysAuthorityService.deleteBatchIds(idList);
			
			for(int i=0;i<idList.size();i++) {
				EntityWrapper<SysAuthority> ew = new EntityWrapper<>();
				ew.setEntity(new SysAuthority());
				ew.eq("authority_parent_id", idList.get(i));
				flag=this.sysAuthorityService.delete(ew);
			}		
		}

		if (flag) {
			jsonResult.isSuccess();
		}else {
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
	@RequestMapping(value = "/checkCodeExist", method = RequestMethod.GET)
	public JsonResult checkCodeExist(HttpServletRequest request, String id, String authorityCode) {
		
		JsonResult jsonResult = new JsonResult();
		
		if (StringUtils.isNotBlank(authorityCode)) {
			
			// 构造条件查询
			EntityWrapper<SysAuthority> ew = new EntityWrapper<>();
			ew.setEntity(new SysAuthority());
			ew.eq("authority_code", authorityCode);
			
			List<SysAuthority> list = this.sysAuthorityService.selectList(ew);
			
			if (list != null && list.size() > 0) {
				
				SysAuthority model = list.get(0);
				if (model != null && !(model.getId().equals(id))) {
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
	public JsonResult checkNameExist(HttpServletRequest request, String id, String authorityName) {
		
		JsonResult jsonResult = new JsonResult();
		
		if (StringUtils.isNotBlank(authorityName)) {
			
			// 构造条件查询
			EntityWrapper<SysAuthority> ew = new EntityWrapper<>();
			ew.setEntity(new SysAuthority());
			ew.eq("authority_name", authorityName);
			
			List<SysAuthority> list = this.sysAuthorityService.selectList(ew);
			
			if (list != null && list.size() > 0) {
				
				SysAuthority model = list.get(0);
				if (model != null && !(model.getId().equals(id))) {
					jsonResult.setSuccess(false);
				}
			}
			
		}
		
		return jsonResult;
	}
	
}
