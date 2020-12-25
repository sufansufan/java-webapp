package com.ics.dataDesources.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
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
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;

/**
 * 企业信息
 * 
 * @author jjz
 *
 */
@Controller
@RequestMapping("/dataDesources/enterpriseInfo")
public class EnterpriseInfoController {

	protected static final String enterpriseInfoJsp = "views/dataDesources/enterpriseInfo/index";
	protected static final String enterpriseInfoAddJsp = "views/dataDesources/enterpriseInfo/add";
	protected static final String enterpriseInfoEditJsp = "views/dataDesources/enterpriseInfo/edit";

	
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;
	@Autowired
	private SysOrgService sysOrgService;
//	@Autowired
//	private DeviceInfoService deviceInfoService;
	
	/**
	 * 企业管理
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_info")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(enterpriseInfoJsp);
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());

		// 获取用户登录所属区域idlist
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
		if (getorgIdList.size() > 0) {
			ew.in("pe.org_id", getorgIdList);
		}
		
		List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
		mav.addObject("enterpriseInfoList", enterpriseInfoList);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/enterpriseInfoList", method = RequestMethod.GET)
	public PagingBean enterpriseInfoList(HttpServletRequest request, int page, int limit, String deviceCode) {
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
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("pe.device_code", deviceCode);
		}
		ew.orderBy("modify_time", false);

		pager = enterpriseInfoService.selectRelationPageList(pager, ew);

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
	@RequiresPermissions(value = "enterprise_info_add")
	@RequestMapping(value = "/enterpriseInfoAdd", method = RequestMethod.GET)
	public ModelAndView enterpriseInfoAdd(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView(enterpriseInfoAddJsp);
		mav.addObject("upload_url", ConstantProperty.device_url);
//		mav.addObject("upload_url", ConstantProperty.enterpriseInfo_url);
		
//		//终端列表
//		EntityWrapper<DeviceInfo> ew6 = new EntityWrapper<>();
//		ew6.setEntity(new DeviceInfo());
//		ew6.orderBy("modifyTime", false);
//		List<DeviceInfo> deviceInfoList = deviceInfoService.selectList(ew6);
		
		//企业列表
		EntityWrapper<EnterpriseInfo> ew7 = new EntityWrapper<>();
		ew7.setEntity(new EnterpriseInfo());
		List<EnterpriseInfo> enterpriseInfoList = enterpriseInfoService.selectList(ew7);
		
//		Iterator<DeviceInfo> it = deviceInfoList.iterator();
//		while(it.hasNext()){
//			DeviceInfo deviceInfo = it.next();
//			String deviceCode = deviceInfo.getDeviceCode();
//			for(int j=0;j<enterpriseInfoList.size();j++) {
//				String deviceCode2 = enterpriseInfoList.get(j).getDeviceCode();
//				if(deviceCode.equals(deviceCode2)){
//			        it.remove();
//			    }
//			}
//
//		}
//
//
//		mav.addObject("deviceInfoList", deviceInfoList);
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
	@RequiresPermissions(value = "enterprise_info_add")
	@RequestMapping(value = "/enterpriseInfoAddSave", method = RequestMethod.POST)
	public String enterpriseInfoAddSave(HttpServletRequest request, EnterpriseInfo model) {

		JsonResult jsonResult = new JsonResult();
		
		boolean flag;
		model.setCreateTime(new Date());
		model.setModifyTime(new Date());
		model.setId(CommonUtil.getRandomUUID());
		flag = this.enterpriseInfoService.insert(model);

		if (!flag) {
			jsonResult.setFaild();
		}
		
//		JsonResult jsonResult = new JsonResult();
//		// 获取用户登录信息
//		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
//		// 上传文件路径
//		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//		String pathDate = sf.format(new Date());
//		String path = "D:/FreezerSystem/enterpriseInfo/" + pathDate;
//
//		// 添加JobBriefing数据
//		String id = CommonUtil.getRandomUUID();
//		model.setId(id);
//		model.setCreateTime(new Date());
//		model.setModifyTime(new Date());
//		if (file != null) {
//			String fileName = file.getOriginalFilename();
//			fileName = fileName.replace(".", "&");
//			String[] fileformat = fileName.split("&");
//			String patrolPicture = pathDate + "/" + id + "." + fileformat[1];
//			model.setPhotoPath(patrolPicture);
//			// 上传文件名
//			String upfilename = "/" + id + "." + fileformat[1];
//			File filepath = new File(path, upfilename);
//			// 判断路径是否存在，没有就创建一个
//			if (!filepath.getParentFile().exists()) {
//				filepath.getParentFile().mkdirs();
//			}
//			// 将上传文件保存到一个目标文档中
//			File file1 = new File(path + File.separator + upfilename);
//			try {
//				file.transferTo(file1);
//
//				boolean b = this.enterpriseInfoService.insert(model);
//				if (b) {
//					jsonResult.setSuccess(true);
//					jsonResult.setMsg("上传成功！");
//				} else {
//					jsonResult.setSuccess(false);
//					jsonResult.setMsg("上传失败！");
//				}
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			boolean b = this.enterpriseInfoService.insert(model);
//			if (b) {
//				jsonResult.setSuccess(true);
//				jsonResult.setMsg("上传成功！");
//			} else {
//				jsonResult.setSuccess(false);
//				jsonResult.setMsg("上传失败！");
//			}
//		}
		return JSONObject.toJSONString(jsonResult);
	}

	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_info_edit")
	@RequestMapping(value = "/enterpriseInfoEdit", method = RequestMethod.GET)
	public ModelAndView enterpriseInfoEdit(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(enterpriseInfoEditJsp);
		EnterpriseInfo model = enterpriseInfoService.selectRelationById(id);
		mav.addObject("model", model);
		mav.addObject("upload_url", ConstantProperty.enterpriseInfo_url);
		
//		//终端列表
//		EntityWrapper<DeviceInfo> ew6 = new EntityWrapper<>();
//		ew6.setEntity(new DeviceInfo());
//		ew6.orderBy("modifyTime", false);
//		List<DeviceInfo> deviceInfoList = deviceInfoService.selectList(ew6);
		
		//企业列表
		EntityWrapper<EnterpriseInfo> ew7 = new EntityWrapper<>();
		ew7.setEntity(new EnterpriseInfo());
		List<EnterpriseInfo> enterpriseInfoList = enterpriseInfoService.selectList(ew7);
		
//		Iterator<DeviceInfo> it = deviceInfoList.iterator();
//		while(it.hasNext()){
//			DeviceInfo deviceInfo = it.next();
//			String deviceCode = deviceInfo.getDeviceCode();
//			for(int j=0;j<enterpriseInfoList.size();j++) {
//				String deviceCode2 = enterpriseInfoList.get(j).getDeviceCode();
//				if(deviceCode.equals(deviceCode2)){
//					if(!deviceCode2.equals(model.getDeviceCode())) {
//						 it.remove();
//					}
//
//			    }
//			}
//
//		}
//
//		mav.addObject("deviceInfoList", deviceInfoList);
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
	@RequiresPermissions(value = "enterprise_info_edit")
	@RequestMapping(value = "/enterpriseInfoEditSave", method = RequestMethod.POST)
	public String enterpriseInfoEditSave(HttpServletRequest request, EnterpriseInfo model) {

		JsonResult jsonResult = new JsonResult();

		boolean flag;
		model.setModifyTime(new Date());
		flag = this.enterpriseInfoService.updateById(model);
		if (!flag) {
			jsonResult.setFaild();
		}
		return  JSONObject.toJSONString(jsonResult);
	}

	/**
	 * 删除，支持批量删除
	 * 
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "pollute_enterprise_del")
	@RequestMapping(value = "/enterpriseInfoDel", method = RequestMethod.POST)
	public JsonResult enterpriseInfoDel(HttpServletRequest request, String ids, String names) {

		JsonResult jsonResult = new JsonResult();

		boolean flag = false;

		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			flag = this.enterpriseInfoService.deleteBatchIds(idList);

		}

		if (!flag) {
			jsonResult.setFaild();
		}
		return jsonResult;
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
	@RequestMapping(value = "/checkEnterpriseNameExist", method = RequestMethod.GET)
	public JsonResult checkEnterpriseNameExist(HttpServletRequest request, String id, String str) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isNotBlank(str)) {

			// 构造条件查询
			EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
			ew.setEntity(new EnterpriseInfo());
			ew.eq("enterprise_name", str);

			List<EnterpriseInfo> list = this.enterpriseInfoService.selectList(ew);

			if (list != null && list.size() > 0) {

				EnterpriseInfo model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}

		return jsonResult;
	}
}
