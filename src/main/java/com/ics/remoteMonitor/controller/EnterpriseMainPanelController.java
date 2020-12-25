package com.ics.remoteMonitor.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ics.system.model.SysDictionaryItem;
import com.ics.system.service.SysDictionaryItemService;
import com.ics.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.model.MonitorFactorTemplate;
import com.ics.dataDesources.service.EnterpriseInfoService;
import com.ics.dataDesources.service.MonitorFactorTemplateService;
import com.ics.remoteMonitor.model.RtdHistory;
import com.ics.remoteMonitor.model.RtdHistoryFromLatestDay;
import com.ics.remoteMonitor.model.RtdHistoryFromLatestMonth;
import com.ics.remoteMonitor.model.RtdHistoryFromLatestWeek;
import com.ics.remoteMonitor.service.RtdHistoryFromLatestDayService;
import com.ics.remoteMonitor.service.RtdHistoryFromLatestMonthService;
import com.ics.remoteMonitor.service.RtdHistoryFromLatestWeekService;
import com.ics.remoteMonitor.service.RtdHistoryService;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CSVUtil;
import com.ics.utils.CommonUtil;
import com.ics.utils.DateUtils;
import com.ics.utils.JsonResult;
import com.ics.remoteMonitor.model.AlarmInfo;
import com.ics.remoteMonitor.service.AlarmInfoService;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.remoteMonitor.model.PanelTemplate;
import com.ics.remoteMonitor.service.PanelTemplateService;
import com.alibaba.fastjson.JSONObject;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 企业主看板
 *
 * @author jjz
 *
 */
@Controller
@RequestMapping("/remoteMonitor/enterpriseMainPanel")
public class EnterpriseMainPanelController {
	protected static Logger logger = Logger.getLogger(EnterpriseMainPanelController.class);
	protected static final String enterpriseMainPanelJsp = "views/remoteMonitor/enterpriseMainPanel/enterpriseMainPanel/index";
	protected static final String departmentMainPanelJsp = "views/remoteMonitor/enterpriseMainPanel/departmentMainPanel/index";
	protected static final String deviceMainPanelJsp = "views/remoteMonitor/enterpriseMainPanel/switchInfo/index";
	protected static CacheManager cacheManager = CacheManager.create();

	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private SysDictionaryItemService sysDictionaryItemService;
	@Autowired
	private ControlMachineService controlMachineService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;
	@Autowired
	private RtdHistoryService rtdHistoryService;
	@Autowired
	private RtdHistoryFromLatestDayService rtdHistoryFromLastDayService;
	@Autowired
	private RtdHistoryFromLatestWeekService rtdHistoryFromLastWeekService;
	@Autowired
	private RtdHistoryFromLatestMonthService rtdHistoryFromLastMonthService;
	@Autowired
	private MonitorFactorTemplateService monitorFactorTemplateService;
	@Autowired
	private AlarmInfoService alarmInfoService;
	@Autowired
	private PanelTemplateService panelTemplateService;

	/**
	 * 企业主看板
	 *
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_main_panel")
	@RequestMapping(value = "/enterpriseMainPanel", method = RequestMethod.GET)
	public ModelAndView enterpriseMainPanel(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(enterpriseMainPanelJsp);
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		if (StringUtils.isNotBlank(id)) {
			ew.andNew();
			ew.eq("id", id);
		}
		// 获取用户登录所属区域idlist
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
		if (getorgIdList.size() > 0) {
			ew.in("pe.org_id", getorgIdList);
		}
		ew.orderBy("di.device_code");
		List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
		mav.addObject("enterpriseInfoList", enterpriseInfoList);
		mav.addObject("enterpriseInfoListSize", enterpriseInfoList.size());
		if (enterpriseInfoList.size() > 0) {
			EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new ControlMachine());
			deviceEw.andNew();
			deviceEw.eq("enterprise_id", enterpriseInfoList.get(0).getId());
			List<ControlMachine> machineList = this.controlMachineService.selectList(deviceEw);
			mav.addObject("machineList", machineList);
		}
		Map map = sysDictionaryItemService.getIntValueNameMap("department");
		mav.addObject("department", map);
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		// calendar.add(Calendar.HOUR_OF_DAY, -1);
		calendar.add(Calendar.DAY_OF_WEEK, -1);
		// calendar.add(Calendar.MONTH,-1);
		mav.addObject("defaultTime", date);
		mav.addObject("lastHour", calendar.getTime());
		return mav;
	}


	/**
	 * 车间主看板
	 *
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_main_panel")
	@RequestMapping(value = "/departmentMainPanel", method = RequestMethod.GET)
	public ModelAndView departmentMainPanel(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(departmentMainPanelJsp);
		List<SysDictionaryItem> items = sysDictionaryItemService.selectByDict("department");
		mav.addObject("department", items);
		String departmentName = request.getParameter("departmentName");
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		if (StringUtils.isNotBlank(id)) {
			ew.andNew();
			ew.eq("id", id);
		}
		// 获取用户登录所属区域idlist
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
		if (getorgIdList.size() > 0) {
			ew.in("pe.org_id", getorgIdList);
		}
		ew.orderBy("di.device_code");
		List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
		mav.addObject("enterpriseInfoList", enterpriseInfoList);
		mav.addObject("enterpriseInfoListSize", enterpriseInfoList.size());

		if (StringUtils.isNotBlank(departmentName)) {
			EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new ControlMachine());
			deviceEw.andNew();
			deviceEw.eq("location", departmentName);
			List<ControlMachine> machineList = this.controlMachineService.selectList(deviceEw);
			mav.addObject("machineList", machineList);
		} else if (items.size() > 0) {
			EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new ControlMachine());
			deviceEw.andNew();
			deviceEw.eq("location", items.get(0).getItemName());
			List<ControlMachine> machineList = this.controlMachineService.selectList(deviceEw);
			mav.addObject("machineList", machineList);
		}

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		// calendar.add(Calendar.HOUR_OF_DAY, -1);
		calendar.add(Calendar.DAY_OF_WEEK, -1);
		// calendar.add(Calendar.MONTH,-1);
		mav.addObject("defaultTime", date);
		mav.addObject("lastHour", calendar.getTime());
		mav.addObject("temperature",25);
		mav.addObject("electric",2750);
		mav.addObject("damp",20);

		return mav;
	}

	/**
	 * 设备主看板
	 *
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_main_panel")
	@RequestMapping(value = "/deviceMainPanel", method = RequestMethod.GET)
	public ModelAndView deviceMainPanel(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView(deviceMainPanelJsp);
		List<SysDictionaryItem> items = sysDictionaryItemService.selectByDict("department");
		mav.addObject("department", items);
		String departmentName = request.getParameter("departmentName");
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		if (StringUtils.isNotBlank(id)) {
			ew.andNew();
			ew.eq("id", id);
		}
		// 获取用户登录所属区域idlist
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		List<String> getorgIdList = CommonUtil.getorgIdList(loginUser, sysOrgService);
		if (getorgIdList.size() > 0) {
			ew.in("pe.org_id", getorgIdList);
		}
		ew.orderBy("di.device_code");
		List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
		mav.addObject("enterpriseInfoList", enterpriseInfoList);
		mav.addObject("enterpriseInfoListSize", enterpriseInfoList.size());
		EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
		deviceEw.setEntity(new ControlMachine());
		deviceEw.andNew();
		deviceEw.eq("location", departmentName);
		List<ControlMachine> machineList = this.controlMachineService.selectList(deviceEw);
		mav.addObject("machineList", machineList);
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		// calendar.add(Calendar.HOUR_OF_DAY, -1);
		calendar.add(Calendar.DAY_OF_WEEK, -1);
		// calendar.add(Calendar.MONTH,-1);
		mav.addObject("defaultTime", date);
		mav.addObject("lastHour", calendar.getTime());
		mav.addObject("temperature",25);
		mav.addObject("electric",2750);
		mav.addObject("damp",20);
		return mav;
	}

	/**
	 * 获取车间信息
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_workshop_basic_info")
	@RequestMapping(value = "/workshopBasicInfo")
	@ResponseBody
	public JsonResult workshopBasicInfo(HttpServletRequest request){
		String locationItemValue = request.getParameter("locationItemValue");

		EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
		deviceEw.setEntity(new ControlMachine());
		deviceEw.andNew();
		deviceEw.eq("location", locationItemValue);
		List<ControlMachine> machineList = this.controlMachineService.selectRelationList(deviceEw);

		EntityWrapper<AlarmInfo> ew = new EntityWrapper<>();
		ew.setEntity(new AlarmInfo());
		ew.andNew();
		ew.isNull("recovery_time");
		ew.and();
		ew.eq("b.location", locationItemValue);
		ew.and();
		ew.eq("alarm_type", 1);
		Integer countAlarms = this.alarmInfoService.getCount(ew);

		EntityWrapper<AlarmInfo> ew1 = new EntityWrapper<>();
		ew1.setEntity(new AlarmInfo());
		ew1.andNew();
		ew1.isNull("recovery_time");
		ew1.and();
		ew1.eq("b.location", locationItemValue);
		ew1.and();
		ew1.in("alarm_type", new Integer[]{0, 2});
		Integer countEarlyAlarms = this.alarmInfoService.getCount(ew1);

		EntityWrapper<SysDictionaryItem> ew2 = new EntityWrapper<>();
		ew2.setEntity(new SysDictionaryItem());
		ew2.andNew();
		ew2.eq("bct.dict_code", "department");
		ew2.and();
		ew2.eq("bc.item_value", locationItemValue);
		List<SysDictionaryItem> itemList = this.sysDictionaryItemService.selectRelationList(ew2);


		Map map = new HashMap();
		map.put("powerConsumption", "--");
		map.put("temperature", "--");
		map.put("humidity", "--");
		map.put("countMachine", machineList.size());

		map.put("countEarlyAlarm", countEarlyAlarms);
		map.put("countAlarm", countAlarms);
		if (itemList.size() > 0) {
			map.put("title", itemList.get(0).getItemName());
		} else {
			map.put("title", "");
		}

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(map);
		return jsonResult;
	}

	/**
	 * 获取设备信息
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_machine_basic_info")
	@RequestMapping(value = "/machineBasicInfo")
	@ResponseBody
	public JsonResult machineBasicInfo(HttpServletRequest request){
		String machineId = request.getParameter("machineId");

		EntityWrapper<ControlMachine> ew = new EntityWrapper<>();
		ew.setEntity(new ControlMachine());
		ew.andNew();
		ew.eq("a.id", machineId);
		List<ControlMachine> machineList = this.controlMachineService.selectRelationList(ew);

		Map map = new HashMap();

		if (machineList.size() > 0) {

			EntityWrapper<SysDictionaryItem> ew2 = new EntityWrapper<>();
			ew2.setEntity(new SysDictionaryItem());
			ew2.andNew();
			ew2.eq("bct.dict_code", "department");
			ew2.and();
			ew2.eq("bc.item_value", machineList.get(0).getLocation());
			List<SysDictionaryItem> itemList = this.sysDictionaryItemService.selectRelationList(ew2);

			if (itemList.size() > 0) {
				map.put("title", itemList.get(0).getItemName());
			} else {
				map.put("title", "");
			}

			EntityWrapper<AlarmInfo> ew3 = new EntityWrapper<>();
			ew3.setEntity(new AlarmInfo());
			ew3.andNew();
			ew3.isNull("recovery_time");
			ew3.and();
			ew3.eq("b.id", machineList.get(0).getId());
			ew3.and();
			ew3.eq("alarm_type", 1);
			Integer countAlarms = this.alarmInfoService.getCount(ew3);

			EntityWrapper<AlarmInfo> ew4 = new EntityWrapper<>();
			ew4.setEntity(new AlarmInfo());
			ew4.andNew();
			ew4.isNull("recovery_time");
			ew4.and();
			ew4.eq("b.id", machineList.get(0).getId());
			ew4.and();
			ew4.in("alarm_type", new Integer[]{0, 2});
			Integer countEarlyAlarms = this.alarmInfoService.getCount(ew4);


			map.put("name", machineList.get(0).getMachineName());
			map.put("machineNo", machineList.get(0).getMachineNo());
			map.put("machineModel", machineList.get(0).getMachineModel());
			map.put("machineType", machineList.get(0).getMachineType());
			map.put("machineTypeName", machineList.get(0).getMachineTypeName());
			map.put("countEarlyAlarm", countEarlyAlarms);
			map.put("countAlarm", countAlarms);
			map.put("runtime", machineList.get(0).getRuntime());
			map.put("photoPath", machineList.get(0).getPhotoPath());
		}

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(map);
		return jsonResult;
	}


	/**
	 * 获取车间设备列表
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_workshop_machine_list")
	@RequestMapping(value = "/workshopMachineList")
	@ResponseBody
	public JsonResult workshopMachineList(HttpServletRequest request){
		String locationItemValue = request.getParameter("locationItemValue");

		EntityWrapper<ControlMachine> ew = new EntityWrapper<>();
		ew.setEntity(new ControlMachine());
		ew.andNew();
		ew.eq("location", locationItemValue);

		List<ControlMachine> machineList = this.controlMachineService.selectRelationList(ew);

		List<Object> list = new ArrayList<Object>();
		int countRunning = 0;
		int countStop = 0;
		int countOffline = 0;

		for (int i = 0; i < machineList.size(); i++) {
			System.out.println(machineList.get(i).getId());
			Map map = new HashMap();
			map.put("id", machineList.get(i).getId());
			System.out.println(machineList.get(i).getMachineName());
			System.out.println(machineList.get(i).getRunningStatus());

			map.put("name", machineList.get(i).getMachineName());
			map.put("runningStatus", machineList.get(i).getRunningStatus());
			if (machineList.get(i).getRunningStatus() == 1) {
				map.put("runningStatusText", "运行");
				countRunning += 1;
			} else {
				map.put("runningStatusText", "停机");
				countStop += 1;
			}
			list.add(map);
		}

		Map map = new HashMap();
		map.put("machineList", list);
		map.put("countRunning", countRunning);
		map.put("countStop", countStop);
		map.put("countOffline", countOffline);

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(map);
		return jsonResult;
	}

	/**
	 * 获取报警信息
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "enterprise_alarm_list")
	@RequestMapping(value = "/alarmList")
	@ResponseBody
	public JsonResult alarmList(HttpServletRequest request){
		String locationItemValue = request.getParameter("locationItemValue");
		String machineId = request.getParameter("machineId");
		String query_startTime = request.getParameter("query_startTime");
		String query_endTime = request.getParameter("query_endTime");

		Page<AlarmInfo> pager = new Page<>(1, 5);
		// 构造条件查询
		EntityWrapper<AlarmInfo> ew = new EntityWrapper<>();
		ew.setEntity(new AlarmInfo());
		if (StringUtils.isNotBlank(locationItemValue)) {
			ew.andNew();
			ew.eq("b.location", locationItemValue);
		}

		if (StringUtils.isNotBlank(machineId)) {
			ew.andNew();
			ew.eq("b.id", machineId);
		}
		if (StringUtils.isNotBlank(query_startTime)) {
			ew.andNew();
			ew.ge("a.create_time", query_startTime);
		}
		if (StringUtils.isNotBlank(query_endTime)) {
			ew.andNew();
			ew.le("a.create_time", query_endTime);
		}
		ew.orderBy("a.create_time", false);
		pager = alarmInfoService.selectRelationPageAlarmList(pager, ew);
		List<Map<String, Object>> alarmList = new ArrayList<Map<String, Object>>();

		Map map = new HashMap();
		List<AlarmInfo> list = pager.getRecords();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (AlarmInfo alarm : list) {
			Map<String, Object> alarmMap = new HashMap<String, Object>();
			alarmMap.put("alarmContent", alarm.getAlarmContent());
			alarmMap.put("alarmType", alarm.getAlarmType());

			if (alarm.getAlarmType().equals("0")) {
				alarmMap.put("alarmTypeStr", "预警");
			} else if (alarm.getAlarmType().equals("1")) {
				alarmMap.put("alarmTypeStr", "报警");
			} else if (alarm.getAlarmType().equals("2")) {
				alarmMap.put("alarmTypeStr", "阈值预警");
			}

			alarmMap.put("machineName", alarm.getMachineName());
			alarmMap.put("factorName", alarm.getFactorName());
			alarmMap.put("createTime", alarm.getCreateTime());
			if(alarm.getCreateTime() != null) {
				alarmMap.put("createTimeStr", sdf.format(alarm.getCreateTime()));
			} else {
				alarmMap.put("createTimeStr", "");
			}
			alarmMap.put("recoveryTime", alarm.getRecoveryTime());
			if(alarm.getRecoveryTime() != null) {
				alarmMap.put("recoveryTimeStr", sdf.format(alarm.getRecoveryTime()));
			} else {
				alarmMap.put("recoveryTimeStr", "");
			}
			alarmMap.put("id", alarm.getId());
			alarmList.add(alarmMap);
		}

		map.put("alarmList", alarmList);

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(map);
		return jsonResult;
	}

	/**
	 * 获取车间布局
	 * @param request
	 * @returnt
	 */
	@RequestMapping(value = "/layout")
	@ResponseBody
	public JsonResult getLayout(HttpServletRequest request) {
		String locationItemValue = request.getParameter("locationItemValue");
		String machineId = request.getParameter("machineId");
		EntityWrapper<PanelTemplate> ew = new EntityWrapper<>();
		ew.setEntity(new PanelTemplate());

		if (StringUtils.isNotBlank(locationItemValue)) {
			ew.andNew();
			ew.eq("template_code", "location_" + locationItemValue);
			ew.or();
			ew.eq("template_code", "location");
		}

		if (StringUtils.isNotBlank(machineId)) {
			EntityWrapper<ControlMachine> ew1 = new EntityWrapper<>();
			ew1.setEntity(new ControlMachine());
			ew1.andNew();
			ew1.eq("a.id", machineId);
			List<ControlMachine> machineList = this.controlMachineService.selectRelationList(ew1);

			ew.andNew();
			ew.eq("template_code", "machine_" + machineId);

			if (machineList.size() > 0) {
				ew.or();
				ew.eq("template_code", "machine_" + machineList.get(0).getMachineType());
			}

			ew.or();
			ew.eq("template_code", "machine");
		}

		// ew.eq("item_value", departmentName);
		// if(StringUtils.isNotBlank(machineNum)) {
		// 	ew.and();
		// 	ew.eq("machine_no", machineNum);
		// }
		//
		ew.orderBy("level", false);


		List<PanelTemplate> templateList = panelTemplateService.selectList(ew);
		JSONObject jsonObject = JSONObject.parseObject(templateList.get(0).getLayout());

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(jsonObject);
		return jsonResult;
	}

	/**
	 * 获取开关信息
	 * @param request
	 * @param query_startTime
	 * @param query_endTime
	 * @returnt
	 */
	@RequestMapping(value = "/switchInfo")
	@ResponseBody
	public JsonResult switchInfo(HttpServletRequest request, String query_startTime, String query_endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Cache cache = cacheManager.getCache("switchCache");

		List<Map<String, Object>> switchList = new ArrayList<Map<String, Object>>();

		String machineId = request.getParameter("machineId");

		EntityWrapper<ControlMachine> ew1 = new EntityWrapper<>();
		ew1.setEntity(new ControlMachine());
		ew1.andNew();
		ew1.eq("a.id", machineId);
		List<ControlMachine> machineList = this.controlMachineService.selectRelationList(ew1);

		EntityWrapper<MonitorFactorTemplate> factorEw = new EntityWrapper<>();
		factorEw.setEntity(new MonitorFactorTemplate());
		factorEw.andNew();
		factorEw.eq("machine_id", machineId);
		factorEw.and();
		factorEw.eq("type_id", "4");
		factorEw.orderBy("factor_code");
		List<MonitorFactorTemplate> factorList = monitorFactorTemplateService.selectList(factorEw);

		for (MonitorFactorTemplate factor : factorList) {
			Map<String, Object> factorMap = new HashMap<String, Object>();
			factorMap.put("name", factor.getFactorName());
			factorMap.put("status", 0);

			if (machineList.size() > 0) {
				String key = "switch:" + factor.getDeviceCode() + ":" + machineList.get(0).getMachineNo() + ":" + factor.getFactorCode();
				System.out.println("+++++++++++++3");
				System.out.println(key);
				// final Element ele = new Element(key, switchValueList.get(j));
				// cache.put(ele);
				if (cache != null) {
					Element b = cache.get(key);
					System.out.println(b);
					if (b != null) {
						System.out.println(b.getObjectValue());
						Integer v = Integer.parseInt((String)b.getValue());
						if (v > 0) {
							factorMap.put("status", 1);
						}
					}
				}
			}

			switchList.add(factorMap);
		}

		map.put("switchList", switchList);

		// List<String[]> valueList = new ArrayList<String[]>();

		// String[] factorCodeArr = new String[factorList_chart.size()];
		// String[] factorNameArr = new String[factorList_chart.size()];
		// BigDecimal[] dataAccuracyArr = new BigDecimal[factorList_chart.size()];
		// int[] decimalDigitsArr = new int[factorList_chart.size()];
		// String[] factorUnitArr = new String[factorList_chart.size()];
		// for (int i = 0; i < factorList_chart.size(); i++) {
		// 	factorCodeArr[i] = factorList_chart.get(i).getFactorCode();
		// 	factorNameArr[i] = factorList_chart.get(i).getFactorName();
		// 	dataAccuracyArr[i] = factorList_chart.get(i).getDataAccuracy();
		// 	decimalDigitsArr[i] = factorList_chart.get(i).getDecimalDigits();
		// 	factorUnitArr[i] = factorList_chart.get(i).getFactorUnit();
		// }
		//
		// // 设备编码
		// if (StringUtils.isNotBlank(enterpriseId)) {
		// 	params.put("enterpriseId", enterpriseId);
		// } else {
		// 	params.put("enterpriseId", null);
		// }
		//
		// if (StringUtils.isNotBlank(machineId)) {
		// 	params.put("machineId", machineId);
		// } else {
		// 	params.put("machineId", null);
		// }
		// if (query_startTime != null && query_endTime != null) {
		// 	params.put("startTime", query_startTime);
		// 	params.put("endTime", query_endTime);
		// } else {
		// 	params.put("startTime", null);
		// 	params.put("endTime", null);
		// }
		// params.put("sort", "desc");
		// try {
		// 	map = getSwitchFromLatestDay(params, map,factorCodeArr);
		//
		// }  catch (Exception e) {
		// 	logger.info(e.getMessage());
		// }

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(map);
		return jsonResult;
	}


	private Map<String, Object> getSwitchFromLatestDay(Map<String, Object> params, Map<String, Object> map,String[] factorCodeArr) {

		List<RtdHistoryFromLatestDay> rtdHistoryList = rtdHistoryFromLastDayService.getList(params);
		rtdHistoryList.stream().forEach(
				r->{
					if(ArrayUtils.contains(factorCodeArr, r.getFactorCode())){
						map.put(r.getFactorCode(), r.getFactorValue());
					}
				}
		);
		return map;
	}


	/**
	 * 获取历史图表
	 * @param request
	 * @param query_startTime
	 * @param query_endTime
	 * @return
	 */
	@RequestMapping(value = "/historyRecordChart")
	@ResponseBody
	public JsonResult historyRecordChart(HttpServletRequest request, String query_startTime, String query_endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<String[]> valueList = new ArrayList<String[]>();
		// String enterpriseId = request.getParameter("enterpriseId");
		String factorTag = request.getParameter("factorTag");
		String machineId = request.getParameter("machineId");
		String locationItemValue = request.getParameter("locationItemValue");

		EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
		ew.setEntity(new MonitorFactorTemplate());
		ew.andNew();
		String sqlFilter = " FIND_IN_SET('"+factorTag+"', factor_tag)";
		ew.addFilter(sqlFilter);

		if(StringUtils.isNotBlank(locationItemValue)) {
			ew.and();
			ew.eq("location", locationItemValue);
		}
		if(StringUtils.isNotBlank(machineId)) {
			ew.and();
			ew.eq("a.machine_id", machineId);
		}
		ew.orderBy("factor_code");
		List<MonitorFactorTemplate> factorList_chart = monitorFactorTemplateService.selectList(ew);

		String[] factorCodeArr = new String[factorList_chart.size()];
		String[] factorNameArr = new String[factorList_chart.size()];
		BigDecimal[] dataAccuracyArr = new BigDecimal[factorList_chart.size()];
		int[] decimalDigitsArr = new int[factorList_chart.size()];
		String[] factorUnitArr = new String[factorList_chart.size()];

		String[] factorDeviceArr = new String[factorList_chart.size()];
		String[] factorMachineArr = new String[factorList_chart.size()];
		String[] factorMachineNameArr = new String[factorList_chart.size()];

		for (int i = 0; i < factorList_chart.size(); i++) {
			factorCodeArr[i] = factorList_chart.get(i).getFactorCode();

			if(StringUtils.isNotBlank(locationItemValue)) {
				factorNameArr[i] = factorList_chart.get(i).getMachineName() + " - "+ factorList_chart.get(i).getFactorName();
			}
			if(StringUtils.isNotBlank(machineId)) {
				factorNameArr[i] = factorList_chart.get(i).getFactorName();
			}

			// factorNameArr[i] = factorList_chart.get(i).getFactorName();
			dataAccuracyArr[i] = factorList_chart.get(i).getDataAccuracy();
			decimalDigitsArr[i] = factorList_chart.get(i).getDecimalDigits();
			factorUnitArr[i] = factorList_chart.get(i).getFactorUnit();
			factorDeviceArr[i] = factorList_chart.get(i).getDeviceCode();
			factorMachineArr[i] = factorList_chart.get(i).getMachineNo();
			factorMachineNameArr[i] = factorList_chart.get(i).getMachineName();
		}

		if (StringUtils.isNotBlank(machineId)) {
			params.put("machineId", machineId);
		} else {
			params.put("machineId", null);
		}

		if (StringUtils.isNotBlank(factorTag)) {
			params.put("factorTag", factorTag);
		} else {
			params.put("factorTag", null);
		}

		if (StringUtils.isNotBlank(locationItemValue)) {
			params.put("location", locationItemValue);
		} else {
			params.put("location", null);
		}

		if (query_startTime != null && query_endTime != null) {
			params.put("startTime", query_startTime);
			params.put("endTime", query_endTime);
		} else {
			params.put("startTime", null);
			params.put("endTime", null);
		}
		params.put("sort", "desc");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime;

		try {
			startTime = df.parse(query_startTime);
			if (DateUtils.inThisDay(startTime)) {
				map = getListFromAll(params, map,factorCodeArr, factorDeviceArr, factorMachineArr, factorMachineNameArr, valueList,
						factorNameArr, dataAccuracyArr, decimalDigitsArr, factorUnitArr);
			} else if (DateUtils.isAfterLatestWeek(startTime)) {
				map = getListFromLatestWeek(params, map,factorCodeArr, factorDeviceArr, factorMachineArr, factorMachineNameArr, valueList,
						factorNameArr, dataAccuracyArr, decimalDigitsArr, factorUnitArr);
			} else if (DateUtils.isAfterLatestMonth(startTime)) {
				map = getListFromLatestMonth(params, map,factorCodeArr, factorDeviceArr, factorMachineArr, factorMachineNameArr, valueList,
						factorNameArr, dataAccuracyArr, decimalDigitsArr, factorUnitArr);
			} else {
				map = getListFromAll(params, map,factorCodeArr, factorDeviceArr, factorMachineArr, factorMachineNameArr, valueList,
						factorNameArr, dataAccuracyArr, decimalDigitsArr, factorUnitArr);
			}
		}  catch (Exception e) {
			logger.info(e.getMessage());
		}

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(map);
		return jsonResult;
	}

	private Map<String, Object> getListFromAll(Map<String, Object> params, Map<String, Object> map,
											   String[] factorCodeArr,
												 String[] factorDeviceArr,
												 String[] factorMachineArr,
												 String[] factorMachineNameArr,
												 List<String[]> valueList,
											   String[] factorNameArr, BigDecimal[] dataAccuracyArr, int[] decimalDigitsArr,
											   String[] factorUnitArr) {

		List<RtdHistory> rtdHistoryList = rtdHistoryService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			for (int i = 0; i < factorCodeArr.length; i++) {
				int index = 0;
				for (int j = 0; j < rtdHistoryList.size(); j++) {
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];
					if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
							&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
							&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
						) {
						if (index == valueList.size()) {
							valueList.add(new String[factorCodeArr.length + 1]);
						}
						if (rtdHistoryList.get(j).getFactorValue().equals("")) {
							valueList.get(index)[i + 1] = "";
						} else {
							valueList.get(index)[i + 1] = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

						}
						if (valueList.get(index)[0] == null) {
							valueList.get(index)[0] = format.format(rtdHistoryList.get(j).getCollectTime());
						}
						index++;
					}
				}
			}
		} catch (Exception e) {
			logger.info("表" + e.getMessage());
		}

		// 获取图表的数据start
		try {
			if (true) {
				Map<String, Object> hm = new HashMap<String, Object>();
				List<Object> list = new ArrayList<Object>();
				List<Object> timeList = new ArrayList<>();
				for (int i = 0; i < factorCodeArr.length; i++) {
					List<String> monitorFactorValueList = new ArrayList<String>();
					List<String> monitorFactorTimeList = new ArrayList<String>();

					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];
					for (int j = rtdHistoryList.size() - 1; j >= 0; j--) {
						if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
							) {
							monitorFactorValueList.add(String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP)));
							monitorFactorTimeList.add(format.format(rtdHistoryList.get(j).getCollectTime()));

						}
					}
					String[] monitorFactorValueArr = new String[monitorFactorValueList.size()];
					String[] monitorFactorTimeArr = new String[monitorFactorValueList.size()];

					monitorFactorValueList.toArray(monitorFactorValueArr);
					monitorFactorTimeList.toArray(monitorFactorTimeArr);
					timeList.add(monitorFactorTimeList);
					list.add(monitorFactorValueList);
				}
				hm.put("timeChart", timeList);
				hm.put("value", list);
				map.put("chart", hm);

			}
		} catch (Exception e) {
			logger.info("图" + e.getMessage());
		}


		// 获取图表的数据end
		map.put("factorNameArr", factorNameArr);
		map.put("factorUnitArr", factorUnitArr);
		return map;
	}

	private Map<String, Object> getListFromLatestDay(Map<String, Object> params, Map<String, Object> map,
														String[] factorCodeArr,
														String[] factorDeviceArr,
														String[] factorMachineArr,
														String[] factorMachineNameArr,
														List<String[]> valueList,
													 String[] factorNameArr, BigDecimal[] dataAccuracyArr, int[] decimalDigitsArr,
													 String[] factorUnitArr) {

		List<RtdHistoryFromLatestDay> rtdHistoryList = rtdHistoryFromLastDayService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			for (int i = 0; i < factorCodeArr.length; i++) {
				int index = 0;
				String deviceCode = factorDeviceArr[i];
				String machineNo = factorMachineArr[i];
				String machineName = factorMachineNameArr[i];

				for (int j = 0; j < rtdHistoryList.size(); j++) {
					if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
								) {
						if (index == valueList.size()) {
							valueList.add(new String[factorCodeArr.length + 1]);
						}
						if (rtdHistoryList.get(j).getFactorValue().equals("")) {
							valueList.get(index)[i + 1] = "";
						} else {
							valueList.get(index)[i + 1] = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

						}
						if (valueList.get(index)[0] == null) {
							valueList.get(index)[0] = format.format(rtdHistoryList.get(j).getCollectTime());
						}
						index++;
					}
				}
			}
		} catch (Exception e) {
			logger.info("表" + e.getMessage());
		}

		// 获取图表的数据start
		try {
			if (true) {
				Map<String, Object> hm = new HashMap<String, Object>();
				List<Object> list = new ArrayList<Object>();
				List<Object> timeList = new ArrayList<>();
				for (int i = 0; i < factorCodeArr.length; i++) {
					List<String> monitorFactorValueList = new ArrayList<String>();
					List<String> monitorFactorTimeList = new ArrayList<String>();

					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];

					for (int j = rtdHistoryList.size() - 1; j >= 0; j--) {
						if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
							) {
							monitorFactorValueList.add(String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP)));
							monitorFactorTimeList.add(format.format(rtdHistoryList.get(j).getCollectTime()));

						}
					}
					String[] monitorFactorValueArr = new String[monitorFactorValueList.size()];
					String[] monitorFactorTimeArr = new String[monitorFactorValueList.size()];

					monitorFactorValueList.toArray(monitorFactorValueArr);
					monitorFactorTimeList.toArray(monitorFactorTimeArr);
					list.add(monitorFactorValueList);
					timeList.add(monitorFactorTimeList);
				}
				hm.put("timeChart", timeList);
				hm.put("value", list);
				map.put("chart", hm);

			}
		} catch (Exception e) {
			logger.info("图" + e.getMessage());
		}

		// 获取图表的数据end
		map.put("factorNameArr", factorNameArr);
		map.put("factorUnitArr", factorUnitArr);
		return map;
	}

	private Map<String, Object> getListFromLatestWeek(Map<String, Object> params, Map<String, Object> map,
														String[] factorCodeArr,
														String[] factorDeviceArr,
														String[] factorMachineArr,
														String[] factorMachineNameArr,
														List<String[]> valueList,
													  String[] factorNameArr, BigDecimal[] dataAccuracyArr, int[] decimalDigitsArr,
													  String[] factorUnitArr) {

		List<RtdHistoryFromLatestWeek> rtdHistoryList = rtdHistoryFromLastWeekService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			for (int i = 0; i < factorCodeArr.length; i++) {
				int index = 0;
				String deviceCode = factorDeviceArr[i];
				String machineNo = factorMachineArr[i];
				String machineName = factorMachineNameArr[i];
				for (int j = 0; j < rtdHistoryList.size(); j++) {
					if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
							&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
							&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
						) {
						if (index == valueList.size()) {
							valueList.add(new String[factorCodeArr.length + 1]);
						}
						if (rtdHistoryList.get(j).getFactorValue().equals("")) {
							valueList.get(index)[i + 1] = "";
						} else {
							valueList.get(index)[i + 1] = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

						}
						if (valueList.get(index)[0] == null) {
							valueList.get(index)[0] = format.format(rtdHistoryList.get(j).getCollectTime());
						}
						index++;
					}
				}
			}
		} catch (Exception e) {
			logger.info("表" + e.getMessage());
		}

		// 获取图表的数据start
		try {
			if (true) {
				Map<String, Object> hm = new HashMap<String, Object>();
				List<Object> list = new ArrayList<Object>();
				List<Object> timeList = new ArrayList<>();
				for (int i = 0; i < factorCodeArr.length; i++) {
					List<String> monitorFactorValueList = new ArrayList<String>();
					List<String> monitorFactorTimeList = new ArrayList<String>();
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];
					for (int j = rtdHistoryList.size() - 1; j >= 0; j--) {
						if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
							) {
							monitorFactorValueList.add(String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP)));
							monitorFactorTimeList.add(format.format(rtdHistoryList.get(j).getCollectTime()));

						}
					}
					String[] monitorFactorValueArr = new String[monitorFactorValueList.size()];
					String[] monitorFactorTimeArr = new String[monitorFactorValueList.size()];

					monitorFactorValueList.toArray(monitorFactorValueArr);
					monitorFactorTimeList.toArray(monitorFactorTimeArr);
					list.add(monitorFactorValueList);
					timeList.add(monitorFactorTimeList);
				}
				hm.put("timeChart", timeList);
				hm.put("value", list);
				map.put("chart", hm);

			}
		} catch (Exception e) {
			logger.info("图" + e.getMessage());
		}

		// 获取图表的数据end
		map.put("factorNameArr", factorNameArr);
		map.put("factorUnitArr", factorUnitArr);
		return map;
	}

	private Map<String, Object> getListFromLatestMonth(Map<String, Object> params, Map<String, Object> map,
															String[] factorCodeArr,
															String[] factorDeviceArr,
															String[] factorMachineArr,
															String[] factorMachineNameArr,
															List<String[]> valueList,
													   String[] factorNameArr, BigDecimal[] dataAccuracyArr, int[] decimalDigitsArr,
													   String[] factorUnitArr) {

		List<RtdHistoryFromLatestMonth> rtdHistoryList = rtdHistoryFromLastMonthService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			for (int i = 0; i < factorCodeArr.length; i++) {
				int index = 0;
				String deviceCode = factorDeviceArr[i];
				String machineNo = factorMachineArr[i];
				String machineName = factorMachineNameArr[i];
				for (int j = 0; j < rtdHistoryList.size(); j++) {
					if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
							&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
							&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
						) {
						if (index == valueList.size()) {
							valueList.add(new String[factorCodeArr.length + 1]);
						}
						if (rtdHistoryList.get(j).getFactorValue().equals("")) {
							valueList.get(index)[i + 1] = "";
						} else {
							valueList.get(index)[i + 1] = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

						}
						if (valueList.get(index)[0] == null) {
							valueList.get(index)[0] = format.format(rtdHistoryList.get(j).getCollectTime());
						}
						index++;
					}
				}
			}
		} catch (Exception e) {
			logger.info("表" + e.getMessage());
		}


		// 获取图表的数据start
		try {
			if (true) {
				Map<String, Object> hm = new HashMap<String, Object>();
				List<Object> list = new ArrayList<Object>();
				List<Object> timeList = new ArrayList<>();
				for (int i = 0; i < factorCodeArr.length; i++) {
					List<String> monitorFactorValueList = new ArrayList<String>();
					List<String> monitorFactorTimeList = new ArrayList<String>();
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];
					for (int j = rtdHistoryList.size() - 1; j >= 0; j--) {
						if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
							) {
							monitorFactorValueList.add(String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP)));
							monitorFactorTimeList.add(format.format(rtdHistoryList.get(j).getCollectTime()));

						}
					}
					String[] monitorFactorValueArr = new String[monitorFactorValueList.size()];
					String[] monitorFactorTimeArr = new String[monitorFactorValueList.size()];

					monitorFactorValueList.toArray(monitorFactorValueArr);
					monitorFactorTimeList.toArray(monitorFactorTimeArr);
					list.add(monitorFactorValueList);
					timeList.add(monitorFactorTimeList);
				}
				hm.put("timeChart", timeList);
				hm.put("value", list);
				map.put("chart", hm);

			}
		} catch (Exception e) {
			logger.info("图" + e.getMessage());
		}


		// 获取图表的数据end
		map.put("factorNameArr", factorNameArr);
		map.put("factorUnitArr", factorUnitArr);
		return map;
	}


	@RequestMapping(value = "/historyRecordList")
	@ResponseBody
	public JsonResult historyRecordList(HttpServletRequest request, String query_startTime, String query_endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<String[]> valueList = new ArrayList<String[]>();
		// String enterpriseId = request.getParameter("enterpriseId");
		String factorTag = request.getParameter("factorTag");
		String machineId = request.getParameter("machineId");
		String locationItemValue = request.getParameter("locationItemValue");

		EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
		ew.setEntity(new MonitorFactorTemplate());
		ew.andNew();
		String sqlFilter = " FIND_IN_SET('"+factorTag+"', factor_tag)";
		ew.addFilter(sqlFilter);

		if(StringUtils.isNotBlank(locationItemValue)) {
			ew.and();
			ew.eq("location", locationItemValue);
		}
		if(StringUtils.isNotBlank(machineId)) {
			ew.and();
			ew.eq("a.machine_id", machineId);
		}
		ew.orderBy("factor_code");
		List<MonitorFactorTemplate> factorList_chart = monitorFactorTemplateService.selectList(ew);

		String[] factorCodeArr = new String[factorList_chart.size()];
		String[] factorNameArr = new String[factorList_chart.size()];
		BigDecimal[] dataAccuracyArr = new BigDecimal[factorList_chart.size()];
		int[] decimalDigitsArr = new int[factorList_chart.size()];
		String[] factorUnitArr = new String[factorList_chart.size()];

		String[] factorDeviceArr = new String[factorList_chart.size()];
		String[] factorMachineArr = new String[factorList_chart.size()];
		String[] factorMachineNameArr = new String[factorList_chart.size()];

		for (int i = 0; i < factorList_chart.size(); i++) {
			factorCodeArr[i] = factorList_chart.get(i).getFactorCode();

			if(StringUtils.isNotBlank(locationItemValue)) {
				factorNameArr[i] = factorList_chart.get(i).getMachineName() + " - "+ factorList_chart.get(i).getFactorName();
			}
			if(StringUtils.isNotBlank(machineId)) {
				factorNameArr[i] = factorList_chart.get(i).getFactorName();
			}

			// factorNameArr[i] = factorList_chart.get(i).getFactorName();
			dataAccuracyArr[i] = factorList_chart.get(i).getDataAccuracy();
			decimalDigitsArr[i] = factorList_chart.get(i).getDecimalDigits();
			factorUnitArr[i] = factorList_chart.get(i).getFactorUnit();
			factorDeviceArr[i] = factorList_chart.get(i).getDeviceCode();
			factorMachineArr[i] = factorList_chart.get(i).getMachineNo();
			factorMachineNameArr[i] = factorList_chart.get(i).getMachineName();
		}

		if (StringUtils.isNotBlank(machineId)) {
			params.put("machineId", machineId);
		} else {
			params.put("machineId", null);
		}

		if (StringUtils.isNotBlank(factorTag)) {
			params.put("factorTag", factorTag);
		} else {
			params.put("factorTag", null);
		}

		if (StringUtils.isNotBlank(locationItemValue)) {
			params.put("location", locationItemValue);
		} else {
			params.put("location", null);
		}

		if (query_startTime != null && query_endTime != null) {
			params.put("startTime", query_startTime);
			params.put("endTime", query_endTime);
		} else {
			params.put("startTime", null);
			params.put("endTime", null);
		}
		params.put("sort", "desc");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime;

		try {
			startTime = df.parse(query_startTime);
			if (DateUtils.inThisDay(startTime)) {
				map = getTableListFromAll(params, map,factorCodeArr, factorDeviceArr, factorMachineArr, factorMachineNameArr, valueList,
						factorNameArr, dataAccuracyArr, decimalDigitsArr, factorUnitArr);
			} else if (DateUtils.isAfterLatestWeek(startTime)) {
				map = getTableListFromLatestWeek(params, map,factorCodeArr, factorDeviceArr, factorMachineArr, factorMachineNameArr, valueList,
						factorNameArr, dataAccuracyArr, decimalDigitsArr, factorUnitArr);
			} else if (DateUtils.isAfterLatestMonth(startTime)) {
				map = getTableListFromLatestMonth(params, map,factorCodeArr, factorDeviceArr, factorMachineArr, factorMachineNameArr, valueList,
						factorNameArr, dataAccuracyArr, decimalDigitsArr, factorUnitArr);
			} else {
				map = getTableListFromAll(params, map,factorCodeArr, factorDeviceArr, factorMachineArr, factorMachineNameArr, valueList,
						factorNameArr, dataAccuracyArr, decimalDigitsArr, factorUnitArr);
			}
		}  catch (Exception e) {
			logger.info(e.getMessage());
		}

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(map);
		return jsonResult;
	}

	private Map<String, Object> getTableListFromAll(Map<String, Object> params, Map<String, Object> map,
											   String[] factorCodeArr,
												 String[] factorDeviceArr,
												 String[] factorMachineArr,
												 String[] factorMachineNameArr,
												 List<String[]> valueList,
											   String[] factorNameArr, BigDecimal[] dataAccuracyArr, int[] decimalDigitsArr,
											   String[] factorUnitArr) {

		List<RtdHistory> rtdHistoryList = rtdHistoryService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			for (int i = 0; i < factorCodeArr.length; i++) {
				int index = 0;
				for (int j = 0; j < rtdHistoryList.size(); j++) {
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];
					if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
							&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
							&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
						) {
						if (index == valueList.size()) {
							valueList.add(new String[factorCodeArr.length + 1]);
						}
						if (rtdHistoryList.get(j).getFactorValue().equals("")) {
							valueList.get(index)[i + 1] = "";
						} else {
							valueList.get(index)[i + 1] = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

						}
						if (valueList.get(index)[0] == null) {
							valueList.get(index)[0] = format.format(rtdHistoryList.get(j).getCollectTime());
						}
						index++;
					}
				}
			}
		} catch (Exception e) {
			logger.info("表" + e.getMessage());
		}

		// 获取图表的数据start
		try {
			if (true) {
				List<String[]> list = new ArrayList<String[]>();
				for (int i = 0; i < factorCodeArr.length; i++) {
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];

					for (int j = rtdHistoryList.size() - 1; j >= 0; j--) {
						if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
							) {

							String timeStr = format.format(rtdHistoryList.get(j).getCollectTime());
							String value = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

							Integer pos = -1;
							for (int k = 0; k < list.size(); k++) {
								if (timeStr.equals(list.get(k)[0])) {
									pos = k;
								}
							}

							if (pos >= 0) {
								String[] row = list.get(pos);
								row[i+1] = value;
								list.set(pos, row);
							} else {
								String[] row = new String[factorCodeArr.length+1];
								row[i+1] = value;
								row[0] = timeStr;
								list.add(row);
							}
						}
					}
				}
				map.put("rows", list);
			}
		} catch (Exception e) {
			logger.info("图" + e.getMessage());
		}


		// 获取图表的数据end
		List<Map<String, String>> headersList = new ArrayList<>();

		for (int i = 0; i < factorNameArr.length; i++) {
			String name = factorNameArr[i];
			String unit = factorUnitArr[i];

			Map<String, String> mh = new HashMap<String, String>();
			mh.put("name", name);
			mh.put("unit", unit);

			headersList.add(mh);
		}
		map.put("headers", headersList);
		return map;
	}

	private Map<String, Object> getTableListFromLatestDay(Map<String, Object> params, Map<String, Object> map,
														String[] factorCodeArr,
														String[] factorDeviceArr,
														String[] factorMachineArr,
														String[] factorMachineNameArr,
														List<String[]> valueList,
													 String[] factorNameArr, BigDecimal[] dataAccuracyArr, int[] decimalDigitsArr,
													 String[] factorUnitArr) {

	 	List<RtdHistoryFromLatestDay> rtdHistoryList = rtdHistoryFromLastDayService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			for (int i = 0; i < factorCodeArr.length; i++) {
				int index = 0;
				for (int j = 0; j < rtdHistoryList.size(); j++) {
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];
					if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
							&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
							&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
						) {
						if (index == valueList.size()) {
							valueList.add(new String[factorCodeArr.length + 1]);
						}
						if (rtdHistoryList.get(j).getFactorValue().equals("")) {
							valueList.get(index)[i + 1] = "";
						} else {
							valueList.get(index)[i + 1] = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

						}
						if (valueList.get(index)[0] == null) {
							valueList.get(index)[0] = format.format(rtdHistoryList.get(j).getCollectTime());
						}
						index++;
					}
				}
			}
		} catch (Exception e) {
			logger.info("表" + e.getMessage());
		}

		// 获取图表的数据start
		try {
			if (true) {
				List<String[]> list = new ArrayList<String[]>();
				for (int i = 0; i < factorCodeArr.length; i++) {
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];

					for (int j = rtdHistoryList.size() - 1; j >= 0; j--) {
						if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
							) {

							String timeStr = format.format(rtdHistoryList.get(j).getCollectTime());
							String value = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

							Integer pos = -1;
							for (int k = 0; k < list.size(); k++) {
								if (timeStr.equals(list.get(k)[0])) {
									pos = k;
								}
							}

							if (pos >= 0) {
								String[] row = list.get(pos);
								row[i+1] = value;
								list.set(pos, row);
							} else {
								String[] row = new String[factorCodeArr.length+1];
								row[i+1] = value;
								row[0] = timeStr;
								list.add(row);
							}
						}
					}
				}
				map.put("rows", list);
			}
		} catch (Exception e) {
			logger.info("图" + e.getMessage());
		}


		// 获取图表的数据end
		List<Map<String, String>> headersList = new ArrayList<>();

		for (int i = 0; i < factorNameArr.length; i++) {
			String name = factorNameArr[i];
			String unit = factorUnitArr[i];

			Map<String, String> mh = new HashMap<String, String>();
			mh.put("name", name);
			mh.put("unit", unit);

			headersList.add(mh);
		}
		map.put("headers", headersList);
		return map;
	}

	private Map<String, Object> getTableListFromLatestWeek(Map<String, Object> params, Map<String, Object> map,
														String[] factorCodeArr,
														String[] factorDeviceArr,
														String[] factorMachineArr,
														String[] factorMachineNameArr,
														List<String[]> valueList,
													  String[] factorNameArr, BigDecimal[] dataAccuracyArr, int[] decimalDigitsArr,
													  String[] factorUnitArr) {

		List<RtdHistoryFromLatestWeek> rtdHistoryList = rtdHistoryFromLastWeekService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			for (int i = 0; i < factorCodeArr.length; i++) {
				int index = 0;
				for (int j = 0; j < rtdHistoryList.size(); j++) {
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];
					if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
							&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
							&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
						) {
						if (index == valueList.size()) {
							valueList.add(new String[factorCodeArr.length + 1]);
						}
						if (rtdHistoryList.get(j).getFactorValue().equals("")) {
							valueList.get(index)[i + 1] = "";
						} else {
							valueList.get(index)[i + 1] = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

						}
						if (valueList.get(index)[0] == null) {
							valueList.get(index)[0] = format.format(rtdHistoryList.get(j).getCollectTime());
						}
						index++;
					}
				}
			}
		} catch (Exception e) {
			logger.info("表" + e.getMessage());
		}

		// 获取图表的数据start
		try {
			if (true) {
				List<String[]> list = new ArrayList<String[]>();
				for (int i = 0; i < factorCodeArr.length; i++) {
					String deviceCode = factorDeviceArr[i];
					String machineNo = factorMachineArr[i];
					String machineName = factorMachineNameArr[i];

					for (int j = rtdHistoryList.size() - 1; j >= 0; j--) {
						if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
							) {

							String timeStr = format.format(rtdHistoryList.get(j).getCollectTime());
							String value = String.valueOf(dataAccuracyArr[i]
									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

							Integer pos = -1;
							for (int k = 0; k < list.size(); k++) {
								if (timeStr.equals(list.get(k)[0])) {
									pos = k;
								}
							}

							if (pos >= 0) {
								String[] row = list.get(pos);
								row[i+1] = value;
								list.set(pos, row);
							} else {
								String[] row = new String[factorCodeArr.length+1];
								row[i+1] = value;
								row[0] = timeStr;
								list.add(row);
							}
						}
					}
				}
				map.put("rows", list);
			}
		} catch (Exception e) {
			logger.info("图" + e.getMessage());
		}


		// 获取图表的数据end
		List<Map<String, String>> headersList = new ArrayList<>();

		for (int i = 0; i < factorNameArr.length; i++) {
			String name = factorNameArr[i];
			String unit = factorUnitArr[i];

			Map<String, String> mh = new HashMap<String, String>();
			mh.put("name", name);
			mh.put("unit", unit);

			headersList.add(mh);
		}
		map.put("headers", headersList);
		return map;
	}

	private Map<String, Object> getTableListFromLatestMonth(Map<String, Object> params, Map<String, Object> map,
															String[] factorCodeArr,
															String[] factorDeviceArr,
															String[] factorMachineArr,
															String[] factorMachineNameArr,
															List<String[]> valueList,
													   String[] factorNameArr, BigDecimal[] dataAccuracyArr, int[] decimalDigitsArr,
													   String[] factorUnitArr) {

		List<RtdHistoryFromLatestMonth> rtdHistoryList = rtdHistoryFromLastMonthService.getList(params);
 		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

 		try {
 			for (int i = 0; i < factorCodeArr.length; i++) {
 				int index = 0;
 				for (int j = 0; j < rtdHistoryList.size(); j++) {
 					String deviceCode = factorDeviceArr[i];
 					String machineNo = factorMachineArr[i];
 					String machineName = factorMachineNameArr[i];
 					if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
 							&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
 							&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
 						) {
 						if (index == valueList.size()) {
 							valueList.add(new String[factorCodeArr.length + 1]);
 						}
 						if (rtdHistoryList.get(j).getFactorValue().equals("")) {
 							valueList.get(index)[i + 1] = "";
 						} else {
 							valueList.get(index)[i + 1] = String.valueOf(dataAccuracyArr[i]
 									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
 									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

 						}
 						if (valueList.get(index)[0] == null) {
 							valueList.get(index)[0] = format.format(rtdHistoryList.get(j).getCollectTime());
 						}
 						index++;
 					}
 				}
 			}
 		} catch (Exception e) {
 			logger.info("表" + e.getMessage());
 		}

 		// 获取图表的数据start
 		try {
 			if (true) {
 				List<String[]> list = new ArrayList<String[]>();
 				for (int i = 0; i < factorCodeArr.length; i++) {
 					String deviceCode = factorDeviceArr[i];
 					String machineNo = factorMachineArr[i];
 					String machineName = factorMachineNameArr[i];

 					for (int j = rtdHistoryList.size() - 1; j >= 0; j--) {
 						if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
 								&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
 								&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
 							) {

 							String timeStr = format.format(rtdHistoryList.get(j).getCollectTime());
 							String value = String.valueOf(dataAccuracyArr[i]
 									.multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue()))
 									.setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP));

 							Integer pos = -1;
 							for (int k = 0; k < list.size(); k++) {
 								if (timeStr.equals(list.get(k)[0])) {
 									pos = k;
 								}
 							}

 							if (pos >= 0) {
 								String[] row = list.get(pos);
 								row[i+1] = value;
 								list.set(pos, row);
 							} else {
 								String[] row = new String[factorCodeArr.length+1];
 								row[i+1] = value;
 								row[0] = timeStr;
 								list.add(row);
 							}
 						}
 					}
 				}
 				map.put("rows", list);
 			}
 		} catch (Exception e) {
 			logger.info("图" + e.getMessage());
 		}


 		// 获取图表的数据end
 		List<Map<String, String>> headersList = new ArrayList<>();

 		for (int i = 0; i < factorNameArr.length; i++) {
 			String name = factorNameArr[i];
 			String unit = factorUnitArr[i];

 			Map<String, String> mh = new HashMap<String, String>();
 			mh.put("name", name);
 			mh.put("unit", unit);

 			headersList.add(mh);
 		}
 		map.put("headers", headersList);
 		return map;
	}

	@RequestMapping(value = "/changeControlMachine", method = RequestMethod.POST)
	@ResponseBody
	public String changeControlMachine(HttpServletRequest request) throws Exception {
		String deviceCode = request.getParameter("deviceCode");
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("device_code", deviceCode);
		}

		List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);

		List<ControlMachine> machineList = null;
		if (enterpriseInfoList.size() > 0) {
			EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new ControlMachine());
			deviceEw.andNew();
			deviceEw.eq("enterprise_id", enterpriseInfoList.get(0).getId());
			machineList = this.controlMachineService.selectList(deviceEw);
		}
		return JSONObject.toJSONString(machineList);
	}

	@ResponseBody
	@RequestMapping(value = "/enterpriseInfo")
	public String enterpriseInfoList(HttpServletRequest request, String deviceCode) {
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

		List<EnterpriseInfo> list = enterpriseInfoService.selectRelationList(ew);

		return JSONObject.toJSONString(list.get(0));
	}

	@RequestMapping(value = "/exportHistoryRecordList")
	@ResponseBody
	public String exportHistoryRecordList(HttpServletRequest request, HttpServletResponse response, String query_startTime, String query_endTime)
			throws ParseException {

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<String[]> valueList = new ArrayList<String[]>();
		// String enterpriseId = request.getParameter("enterpriseId");
		String factorTag = request.getParameter("factorTag");
		String machineId = request.getParameter("machineId");
		String locationItemValue = request.getParameter("locationItemValue");

		EntityWrapper<MonitorFactorTemplate> ew = new EntityWrapper<>();
		ew.setEntity(new MonitorFactorTemplate());
		ew.andNew();
		String sqlFilter = " FIND_IN_SET('"+factorTag+"', factor_tag)";
		ew.addFilter(sqlFilter);

		if(StringUtils.isNotBlank(locationItemValue)) {
			ew.and();
			ew.eq("location", locationItemValue);
		}
		if(StringUtils.isNotBlank(machineId)) {
			ew.and();
			ew.eq("a.machine_id", machineId);
		}
		ew.orderBy("factor_code");
		List<MonitorFactorTemplate> factorList = monitorFactorTemplateService.selectList(ew);

		String[] factorCodeArr = new String[factorList.size()];
		String[] factorNameArr = new String[factorList.size()];
		BigDecimal[] dataAccuracyArr = new BigDecimal[factorList.size()];
		int[] decimalDigitsArr = new int[factorList.size()];
		String[] factorUnitArr = new String[factorList.size()];

		String[] factorDeviceArr = new String[factorList.size()];
		String[] factorMachineArr = new String[factorList.size()];
		String[] factorMachineNameArr = new String[factorList.size()];

		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();

		for (int i = 0; i < factorList.size(); i++) {
			if (i == 0) {
				headerMap.put("collectTime", "采集时间");
			}

			factorCodeArr[i] = factorList.get(i).getFactorCode();

			if(StringUtils.isNotBlank(locationItemValue)) {
				factorNameArr[i] = factorList.get(i).getMachineName() + " - "+ factorList.get(i).getFactorName();
			}
			if(StringUtils.isNotBlank(machineId)) {
				factorNameArr[i] = factorList.get(i).getFactorName();
			}

			// factorNameArr[i] = factorList_chart.get(i).getFactorName();
			dataAccuracyArr[i] = factorList.get(i).getDataAccuracy();
			decimalDigitsArr[i] = factorList.get(i).getDecimalDigits();
			factorUnitArr[i] = factorList.get(i).getFactorUnit();
			factorDeviceArr[i] = factorList.get(i).getDeviceCode();
			factorMachineArr[i] = factorList.get(i).getMachineNo();
			factorMachineNameArr[i] = factorList.get(i).getMachineName();

			String key = factorList.get(i).getDeviceCode() + "_" + factorList.get(i).getMachineNo() + "_" + factorList.get(i).getFactorCode();
			headerMap.put(key, factorNameArr[i]);
		}

		if (StringUtils.isNotBlank(machineId)) {
			params.put("machineId", machineId);
		} else {
			params.put("machineId", null);
		}

		if (StringUtils.isNotBlank(factorTag)) {
			params.put("factorTag", factorTag);
		} else {
			params.put("factorTag", null);
		}

		if (StringUtils.isNotBlank(locationItemValue)) {
			params.put("location", locationItemValue);
		} else {
			params.put("location", null);
		}

		if (query_startTime != null && query_endTime != null) {
			params.put("startTime", query_startTime);
			params.put("endTime", query_endTime);
		} else {
			params.put("startTime", null);
			params.put("endTime", null);
		}
		params.put("sort", "desc");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime;

		List<Map<String, String>> returnValueList = new ArrayList<Map<String, String>>();

		try {
			startTime = df.parse(query_startTime);

			if (DateUtils.inThisDay(startTime)) {
				returnValueList = exportListFromAll(params,
						returnValueList,
						factorCodeArr,
						factorDeviceArr,
						factorMachineArr,
						factorMachineNameArr,
						dataAccuracyArr,
						decimalDigitsArr);
			} else if (DateUtils.isAfterLatestWeek(startTime)) {
				returnValueList = exportListFromLatestWeek(params,
						returnValueList,
						factorCodeArr,
						factorDeviceArr,
						factorMachineArr,
						factorMachineNameArr,
						dataAccuracyArr,
						decimalDigitsArr);
			} else if (DateUtils.isAfterLatestMonth(startTime)) {
				returnValueList = exportListFromLatestMonth(params,
						returnValueList,
						factorCodeArr,
						factorDeviceArr,
						factorMachineArr,
						factorMachineNameArr,
						dataAccuracyArr,
						decimalDigitsArr);
			} else {
				returnValueList = exportListFromAll(
						params,
						returnValueList,
						factorCodeArr,
						factorDeviceArr,
						factorMachineArr,
						factorMachineNameArr,
						dataAccuracyArr,
						decimalDigitsArr);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> hm = new HashMap<String, Object>();
		// 实体类导出时才有用
		String fileds[] = new String[] { "id", "deviceCode", "machineName", "factorCode", "factorValue",
				"collectTime" };
		try {
			CSVUtil.exportFile(response, headerMap, returnValueList, fileds);
			hm.put("success", true);
			/*
			 * response：直接传入response map：对应文件的第一行 list：对应 List<CVSBean> list对象形式
			 * fileds：对应每一列的数据
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			hm.put("success", false);
			e.printStackTrace();
		} // 直接调用

		return JSON.toJSONString(hm);
	}

	private List<Map<String, String>> exportListFromAll(
			Map<String, Object> params,
			List<Map<String, String>> returnValueList,
			String[] factorCodeArr,
			String[] factorDeviceArr,
			String[] factorMachineArr,
			String[] factorMachineNameArr,
			BigDecimal[] dataAccuracyArr,
			int[] decimalDigitsArr
			){
		List<RtdHistory> rtdHistoryList = rtdHistoryService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		for (int i = 0; i < rtdHistoryList.size(); i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			returnValueList.add(map);
		}

		for (int i = 0; i < factorCodeArr.length; i++) {
			int index = 0;
			String deviceCode = factorDeviceArr[i];
			String machineNo = factorMachineArr[i];
			String machineName = factorMachineNameArr[i];
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
						&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
						&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
				) {
					returnValueList.get(index).put("collectTime",
							format.format(rtdHistoryList.get(j).getCollectTime()));
					String factorValue = rtdHistoryList.get(j).getFactorValue();

					String key = deviceCode + "_" + machineNo + "_" + factorCodeArr[i];

					returnValueList.get(index).put(key, String.valueOf(dataAccuracyArr[i]
							.multiply(new BigDecimal(factorValue)).setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP)));
					index++;
				}
			}
		}
		return returnValueList;
	}

	private List<Map<String, String>> exportListFromLatestDay(
			Map<String, Object> params,
			List<Map<String, String>> returnValueList,
			String[] factorCodeArr,
			String[] factorDeviceArr,
			String[] factorMachineArr,
			String[] factorMachineNameArr,
			BigDecimal[] dataAccuracyArr,
			int[] decimalDigitsArr
			){
		List<RtdHistoryFromLatestDay> rtdHistoryList = rtdHistoryFromLastDayService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		for (int i = 0; i < rtdHistoryList.size(); i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			returnValueList.add(map);
		}

		for (int i = 0; i < factorCodeArr.length; i++) {
			int index = 0;
			String deviceCode = factorDeviceArr[i];
			String machineNo = factorMachineArr[i];
			String machineName = factorMachineNameArr[i];
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
						&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
						&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
				) {
					returnValueList.get(index).put("collectTime",
							format.format(rtdHistoryList.get(j).getCollectTime()));
					String factorValue = rtdHistoryList.get(j).getFactorValue();

					String key = deviceCode + "_" + machineNo + "_" + factorCodeArr[i];

					returnValueList.get(index).put(key, String.valueOf(dataAccuracyArr[i]
							.multiply(new BigDecimal(factorValue)).setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP)));
					index++;
				}
			}
		}
		return returnValueList;
	}

	private List<Map<String, String>> exportListFromLatestWeek(
			Map<String, Object> params,
			List<Map<String, String>> returnValueList,
			String[] factorCodeArr,
			String[] factorDeviceArr,
			String[] factorMachineArr,
			String[] factorMachineNameArr,
			BigDecimal[] dataAccuracyArr,
			int[] decimalDigitsArr
			){
		List<RtdHistoryFromLatestWeek> rtdHistoryList = rtdHistoryFromLastWeekService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		for (int i = 0; i < rtdHistoryList.size(); i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			returnValueList.add(map);
		}

		for (int i = 0; i < factorCodeArr.length; i++) {
			int index = 0;
			String deviceCode = factorDeviceArr[i];
			String machineNo = factorMachineArr[i];
			String machineName = factorMachineNameArr[i];
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
						&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
						&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
				) {
					returnValueList.get(index).put("collectTime",
							format.format(rtdHistoryList.get(j).getCollectTime()));
					String factorValue = rtdHistoryList.get(j).getFactorValue();

					String key = deviceCode + "_" + machineNo + "_" + factorCodeArr[i];

					returnValueList.get(index).put(key, String.valueOf(dataAccuracyArr[i]
							.multiply(new BigDecimal(factorValue)).setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP)));
					index++;
				}
			}
		}
		return returnValueList;
	}

	private List<Map<String, String>> exportListFromLatestMonth(
			Map<String, Object> params,
			List<Map<String, String>> returnValueList,
			String[] factorCodeArr,
			String[] factorDeviceArr,
			String[] factorMachineArr,
			String[] factorMachineNameArr,
			BigDecimal[] dataAccuracyArr,
			int[] decimalDigitsArr
			){
		List<RtdHistoryFromLatestMonth> rtdHistoryList = rtdHistoryFromLastMonthService.getList(params);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		for (int i = 0; i < rtdHistoryList.size(); i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			returnValueList.add(map);
		}

		for (int i = 0; i < factorCodeArr.length; i++) {
			int index = 0;
			String deviceCode = factorDeviceArr[i];
			String machineNo = factorMachineArr[i];
			String machineName = factorMachineNameArr[i];
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if (rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])
						&& rtdHistoryList.get(j).getDeviceCode().equals(deviceCode)
						&& rtdHistoryList.get(j).getCondensingDeviceNum().equals(machineNo)
				) {
					returnValueList.get(index).put("collectTime",
							format.format(rtdHistoryList.get(j).getCollectTime()));
					String factorValue = rtdHistoryList.get(j).getFactorValue();

					String key = deviceCode + "_" + machineNo + "_" + factorCodeArr[i];

					returnValueList.get(index).put(key, String.valueOf(dataAccuracyArr[i]
							.multiply(new BigDecimal(factorValue)).setScale(decimalDigitsArr[i], BigDecimal.ROUND_UP)));
					index++;
				}
			}
		}
		return returnValueList;
	}
}
