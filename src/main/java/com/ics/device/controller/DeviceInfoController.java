package com.ics.device.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.model.MonitorFactor;
import com.ics.dataDesources.model.MonitorFactorTemplate;
import com.ics.dataDesources.service.MonitorFactorService;
import com.ics.dataDesources.service.MonitorFactorTemplateService;
import com.ics.device.model.DeviceInfo;
import com.ics.device.service.DeviceInfoService;
import com.ics.system.model.SysDictionaryItem;
import com.ics.system.service.SysDictionaryItemService;
import com.ics.system.service.SysDictionaryService;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;
import com.ics.utils.mqtt.MqttDataUtil;

/**
 * 设备信息
 *
 * @author jjz
 *
 */
@Controller
@RequestMapping("/device/deviceInfo")
public class DeviceInfoController {

	protected static final String deviceInfoJsp = "views/device/deviceInfo/index";
	protected static final String deviceInfoAddJsp = "views/device/deviceInfo/add";
	protected static final String deviceInfoEditJsp = "views/device/deviceInfo/edit";

	protected static final String getMapPositonJsp = "views/device/deviceInfo/getMapPositon";


	protected static final String remoteConfigureJsp = "views/device/remoteConfigure/index";

	@Autowired
	private DeviceInfoService deviceInfoService;
	@Autowired
	private SysDictionaryItemService sysDictionaryItemService;
	@Autowired
	private SysDictionaryService sysDictionaryService;

//	@Autowired
//	private MonitorFactorService monitorFactorService;
	@Autowired
	private MonitorFactorTemplateService monitorFactorTemplateService;

	@RequestMapping(value = "/getMapPositon")
	public ModelAndView getMapPositon(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(getMapPositonJsp);
		return mav;
	}

	@RequestMapping(value = "/deviceInfo")
	public ModelAndView deviceInfo(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(deviceInfoJsp);
		mav.addObject("id", id);
		return mav;
	}

	@RequestMapping(value = "/remoteConfigure")
	public ModelAndView remoteConfigure(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(remoteConfigureJsp);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceInfoList", method = RequestMethod.GET)
	public PagingBean deviceInfoList(HttpServletRequest request, int page, int limit, String deviceKeyWord,
			String id, String enterpriseId) {
		Page<DeviceInfo> pager = new Page<>(page, limit);
		// 构造条件查询
		EntityWrapper<DeviceInfo> ew = new EntityWrapper<>();
		ew.setEntity(new DeviceInfo());
		if (StringUtils.isNotBlank(deviceKeyWord)) {
			ew.orNew();
			ew.like("device_name", deviceKeyWord);
			ew.or();
			ew.like("device_code", deviceKeyWord);
		}
		if (StringUtils.isNotBlank(id)) {
			ew.andNew();
			ew.eq("id", id);
		}
		if (StringUtils.isNotBlank(enterpriseId)) {
			ew.andNew();
			ew.eq("enterprise_id", enterpriseId);
		}
		ew.orderBy("modifyTime", false);

		pager = deviceInfoService.selectPage(pager, ew);

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
	@RequiresPermissions(value = "device_info_add")
	@RequestMapping(value = "/deviceInfoAdd", method = RequestMethod.GET)
	public ModelAndView deviceInfoAdd(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(deviceInfoAddJsp);
		if (StringUtils.isNotBlank(id)) {
			EnterpriseInfo enterpriseInfo = new EnterpriseInfo().selectById(id);
			mav.addObject("enterpriseInfo", enterpriseInfo);
		}

		mav.addObject("upload_url", ConstantProperty.device_url);

		// 设备类型
		List<String> idList = sysDictionaryService.getIdListByDictCode("deviceType");
		EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
		ew1.setEntity(new SysDictionaryItem());
		ew1.in("dict_id", idList);
		ew1.orderBy("sort_idx", true);
		List<SysDictionaryItem> deviceTypeList = sysDictionaryItemService.selectList(ew1);
		mav.addObject("deviceTypeList",deviceTypeList);

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
	@RequiresPermissions(value = "device_info_add")
	@RequestMapping(value = "/deviceInfoAddSave", method = RequestMethod.POST)
	public JsonResult deviceInfoAddSave(HttpServletRequest request, DeviceInfo model) {

		JsonResult jsonResult = new JsonResult();

		boolean flag;
		model.setCreateTime(new Date());
		model.setModifyTime(new Date());
		model.setId(CommonUtil.getRandomUUID());
		flag = this.deviceInfoService.insert(model);

//		String condensingDeviceNumStr = model.getCondensingDeviceNumStr();
//		String deviceCode = model.getDeviceCode();
//		if(!condensingDeviceNumStr.equals("")) {
//			List<MonitorFactorTemplate> monitorFactorTemplateList = new ArrayList<MonitorFactorTemplate>();
//			String[] condensingDeviceNumArr = condensingDeviceNumStr.split(",");
//			for(int i=0;i<condensingDeviceNumArr.length;i++) {
//				EntityWrapper<MonitorFactor> ew = new EntityWrapper<>();
//				ew.setEntity(new MonitorFactor());
//				ew.eq("condensing_device_num", condensingDeviceNumArr[i]);
//				List<MonitorFactor> monitorFactorList = monitorFactorService.selectList(ew);
//				for(int j=0;j<monitorFactorList.size();j++) {
//					MonitorFactorTemplate monitorFactorTemplate = new MonitorFactorTemplate();
//					monitorFactorTemplate.setId(CommonUtil.getRandomUUID());
//					monitorFactorTemplate.setDeviceCode(deviceCode);
//					monitorFactorTemplate.setCondensingDeviceNum(condensingDeviceNumArr[i]);
//					monitorFactorTemplate.setFactorCode(monitorFactorList.get(j).getFactorCode());
//					monitorFactorTemplate.setFactorName(monitorFactorList.get(j).getFactorName());
//					monitorFactorTemplate.setFactorUnit(monitorFactorList.get(j).getFactorUnit());
//					monitorFactorTemplate.setFactorTag(monitorFactorList.get(j).getFactorTag());
//					monitorFactorTemplate.setDataAccuracy(monitorFactorList.get(j).getDataAccuracy());
//					monitorFactorTemplate.setDecimalDigits(monitorFactorList.get(j).getDecimalDigits());
//					monitorFactorTemplate.setTypeId(monitorFactorList.get(j).getTypeId());
//					monitorFactorTemplate.setAlarmState(monitorFactorList.get(j).getAlarmState());
//					monitorFactorTemplate.setLowerLimit(monitorFactorList.get(j).getLowerLimit());
//					monitorFactorTemplate.setUpperLimit(monitorFactorList.get(j).getUpperLimit());
//					monitorFactorTemplate.setCreateTime(new Date());
//					monitorFactorTemplate.setModifyTime(new Date());
//					monitorFactorTemplateList.add(monitorFactorTemplate);
//				}
//			}
//			monitorFactorTemplateService.insertBatch(monitorFactorTemplateList);
//		}

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
	@RequiresPermissions(value = "device_info_edit")
	@RequestMapping(value = "/deviceInfoEdit", method = RequestMethod.GET)
	public ModelAndView deviceInfoEdit(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(deviceInfoEditJsp);
		DeviceInfo model = this.deviceInfoService.selectById(id);
		mav.addObject("model", model);
		EnterpriseInfo enterpriseInfo = new EnterpriseInfo().selectById(model.getEnterpriseId());
		mav.addObject("enterpriseInfo", enterpriseInfo);

		mav.addObject("upload_url", ConstantProperty.device_url);

		// 设备类型
		// 设备类型
		List<String> idList = sysDictionaryService.getIdListByDictCode("deviceType");
		EntityWrapper<SysDictionaryItem> ew1 = new EntityWrapper<>();
		ew1.setEntity(new SysDictionaryItem());
		ew1.in("dict_id", idList);
		ew1.orderBy("sort_idx", true);
		List<SysDictionaryItem> deviceTypeList = sysDictionaryItemService.selectList(ew1);
		mav.addObject("deviceTypeList",deviceTypeList);
		
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
	@RequiresPermissions(value = "device_info_edit")
	@RequestMapping(value = "/deviceInfoEditSave", method = RequestMethod.POST)
	public JsonResult deviceInfoEditSave(HttpServletRequest request, DeviceInfo model,String oldCondensingDeviceNumStr) {

		JsonResult jsonResult = new JsonResult();

		boolean flag;
		model.setModifyTime(new Date());
		flag = this.deviceInfoService.updateById(model);
		if (!flag) {
			jsonResult.setFaild();
		}else {
//			String condensingDeviceNumStr = model.getCondensingDeviceNumStr();
			String deviceCode = model.getDeviceCode();

//			if(!condensingDeviceNumStr.equals("")) {
//				List<MonitorFactorTemplate> monitorFactorTemplateList = new ArrayList<MonitorFactorTemplate>();
//				String[] condensingDeviceNumArr = condensingDeviceNumStr.split(",");
//				String[] oldCondensingDeviceNumArr = oldCondensingDeviceNumStr.split(",");
//				List<String> oldCondensingDeviceNumList =  Arrays.asList(oldCondensingDeviceNumArr);
//				List<String> condensingDeviceNumList =  Arrays.asList(condensingDeviceNumArr);
////
////				try {
////					//去掉condensingDeviceNumList中跟oldCondensingDeviceNumList相同的元素,删除的报警内容
////					List<String> addList = MqttDataUtil.removeRepeatFactor(oldCondensingDeviceNumList, condensingDeviceNumList);
////					//去掉oldCondensingDeviceNumList中跟condensingDeviceNumList相同的元素,新增的报警内容
////					List<String> delList = MqttDataUtil.removeRepeatFactor(condensingDeviceNumList, oldCondensingDeviceNumList);
////
////					for(int i=0;i<delList.size();i++) {
////						EntityWrapper<MonitorFactorTemplate> delEw = new EntityWrapper<>();
////						delEw.setEntity(new MonitorFactorTemplate());
////						delEw.eq("device_code", deviceCode);
////						delEw.eq("condensing_device_num", delList.get(i));
////						//先移除该终端下对应机组的monitorFactorTemplate记录
////						monitorFactorTemplateService.delete(delEw);
////					}
////
////					for(int i=0;i<addList.size();i++) {
////						EntityWrapper<MonitorFactor> ew = new EntityWrapper<>();
////						ew.setEntity(new MonitorFactor());
////						ew.eq("condensing_device_num", addList.get(i));
////						List<MonitorFactor> monitorFactorList = monitorFactorService.selectList(ew);
////						for(int j=0;j<monitorFactorList.size();j++) {
////							MonitorFactorTemplate monitorFactorTemplate = new MonitorFactorTemplate();
////							monitorFactorTemplate.setId(CommonUtil.getRandomUUID());
////							monitorFactorTemplate.setDeviceCode(deviceCode);
////							monitorFactorTemplate.setCondensingDeviceNum(addList.get(i));
////							monitorFactorTemplate.setFactorCode(monitorFactorList.get(j).getFactorCode());
////							monitorFactorTemplate.setFactorName(monitorFactorList.get(j).getFactorName());
////							monitorFactorTemplate.setFactorUnit(monitorFactorList.get(j).getFactorUnit());
////							monitorFactorTemplate.setFactorTag(monitorFactorList.get(j).getFactorTag());
////							monitorFactorTemplate.setDataAccuracy(monitorFactorList.get(j).getDataAccuracy());
////							monitorFactorTemplate.setDecimalDigits(monitorFactorList.get(j).getDecimalDigits());
////							monitorFactorTemplate.setTypeId(monitorFactorList.get(j).getTypeId());
////							monitorFactorTemplate.setAlarmState(monitorFactorList.get(j).getAlarmState());
////							monitorFactorTemplate.setLowerLimit(monitorFactorList.get(j).getLowerLimit());
////							monitorFactorTemplate.setUpperLimit(monitorFactorList.get(j).getUpperLimit());
////							monitorFactorTemplate.setCreateTime(new Date());
////							monitorFactorTemplate.setModifyTime(new Date());
////							monitorFactorTemplateList.add(monitorFactorTemplate);
////					}
////
////					}
////					if(monitorFactorTemplateList.size()>0) {
////						monitorFactorTemplateService.insertBatch(monitorFactorTemplateList);
////					}
////				} catch (Exception e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//
//
//			}
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
	@RequiresPermissions(value = "device_info_del")
	@RequestMapping(value = "/deviceInfoDel", method = RequestMethod.POST)
	public JsonResult deviceInfoDel(HttpServletRequest request, String ids, String deviceCodes) {

		JsonResult jsonResult = new JsonResult();

		boolean flag = false;

		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			flag = this.deviceInfoService.deleteBatchIds(idList);
			String[] deviceCodeArr = deviceCodes.split(",");
			for(int i=0;i<deviceCodeArr.length;i++) {
				EntityWrapper<MonitorFactorTemplate> delEw = new EntityWrapper<>();
				delEw.setEntity(new MonitorFactorTemplate());
				delEw.eq("device_code", deviceCodeArr[i]);
				//先移除该终端下全部的monitorFactorTemplate记录
				monitorFactorTemplateService.delete(delEw);
			}
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
	@RequestMapping(value = "/checkDeviceNameExist", method = RequestMethod.GET)
	public JsonResult checkEnterpriseNameExist(HttpServletRequest request, String id, String str) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isNotBlank(str)) {

			// 构造条件查询
			EntityWrapper<DeviceInfo> ew = new EntityWrapper<>();
			ew.setEntity(new DeviceInfo());
			ew.eq("device_name", str);

			List<DeviceInfo> list = this.deviceInfoService.selectList(ew);

			if (list != null && list.size() > 0) {

				DeviceInfo model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}

		return jsonResult;
	}

	@ResponseBody
	@RequestMapping(value = "/checkDeviceCodeExist", method = RequestMethod.GET)
	public JsonResult checkDeviceCodeExist(HttpServletRequest request, String id, String str) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isNotBlank(str)) {

			// 构造条件查询
			EntityWrapper<DeviceInfo> ew = new EntityWrapper<>();
			ew.setEntity(new DeviceInfo());
			ew.eq("device_code", str);

			List<DeviceInfo> list = this.deviceInfoService.selectList(ew);

			if (list != null && list.size() > 0) {

				DeviceInfo model = list.get(0);
				if (model != null && !model.getId().equals(id)) {
					jsonResult.setSuccess(false);
				}
			}

		}

		return jsonResult;
	}

	@ResponseBody
	@RequestMapping(value = "/listfile")
	public void listfile(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
		System.out.println(url);
		String path = "D:/FreezerSystem/";
		File file = new File(path + url);
		FileInputStream in = new FileInputStream(file);
		byte[] img = CommonUtil.toByteArray(in);
		OutputStream out = response.getOutputStream();
		out.write(img);
		out.close();
	}
	@ResponseBody
	@RequestMapping(value = "/listfileLook")
	public void listfileLook(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
		System.out.println(url);
		String path = "D:/FreezerSystem/";
		File file = new File(url);
		FileInputStream in = new FileInputStream(file);
		byte[] img = CommonUtil.toByteArray(in);
		OutputStream out = response.getOutputStream();
		out.write(img);
		out.close();
	}
}
