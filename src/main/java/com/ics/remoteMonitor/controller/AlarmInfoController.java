package com.ics.remoteMonitor.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.dataDesources.service.EnterpriseInfoService;
import com.ics.dataDesources.service.MonitorFactorTemplateService;
import com.ics.device.service.DeviceInfoService;
import com.ics.remoteMonitor.model.AlarmInfo;
import com.ics.remoteMonitor.service.AlarmInfoService;
import com.ics.remoteMonitor.service.RtdHistoryService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CSVUtil;
import com.ics.utils.CommonUtil;
import com.ics.utils.PagingBean;

/**
 * 报警信息
 *
 * @author jjz
 *
 */
@Controller
@RequestMapping("/remoteMonitor/alarmInfo")
public class AlarmInfoController {
	protected static Logger logger = Logger.getLogger(AlarmInfoController.class);
	protected static final String alarmInfoJsp = "views/remoteMonitor/condensingDeviceMonitor/alarmInfo/index";

	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private AlarmInfoService alarmInfoService;
	@Autowired
	private ControlMachineService controlMachineService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;

	/**
	 * 报警信息
	 *
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = "alarm_info")
	@RequestMapping(value = "/alarmInfo", method = RequestMethod.GET)
	public ModelAndView alarmInfo(HttpServletRequest request,String id) {
		ModelAndView mav = new ModelAndView(alarmInfoJsp);
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
		// if(enterpriseInfoList.size()>0) {
		EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
		deviceEw.setEntity(new ControlMachine());
		deviceEw.andNew();
		deviceEw.orderBy("machine_no+0");
		List<ControlMachine> machineList = this.controlMachineService.selectRelationList(deviceEw);
		mav.addObject("machineList", machineList);
		// }

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
//		calendar.add(Calendar.HOUR_OF_DAY, -1);
		 calendar.add(Calendar.DAY_OF_WEEK, -7);
//		 calendar.add(Calendar.MONTH,-1);
		mav.addObject("defaultTime", date);
		mav.addObject("lastHour", calendar.getTime());

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/alarmInfoList", method = RequestMethod.GET)
	public PagingBean alarmInfoList(HttpServletRequest request, int page, int limit,String deviceCode,String enterpriseId, String machineId,String alarmType,String query_startTime,String query_endTime) {
		Page<AlarmInfo> pager = new Page<>(page, limit);
		// 构造条件查询
		EntityWrapper<AlarmInfo> ew = new EntityWrapper<>();
		ew.setEntity(new AlarmInfo());
		if (StringUtils.isNotBlank(enterpriseId)) {
			ew.andNew();
			ew.eq("b.enterprise_id", enterpriseId);
		}

		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("a.device_code", deviceCode);
		}
		if (StringUtils.isNotBlank(machineId)) {
			ew.andNew();
			ew.eq("b.id", machineId);
		}
		if (StringUtils.isNotBlank(alarmType)) {
			ew.andNew();
			ew.eq("a.alarm_type", alarmType);
		}
		if(StringUtils.isNotBlank(query_startTime) && StringUtils.isNotBlank(query_endTime)) {
			ew.andNew();
			ew.gt("a.create_time", query_startTime);
			ew.and();
			ew.lt("a.create_time", query_endTime);
		}
		// ew.groupBy("a.id");
		ew.orderBy("a.create_time", false);

//		pager = alarmInfoService.selectPage(pager, ew);
		pager = alarmInfoService.selectRelationPageAlarmList(pager, ew);

		PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
		pageBean.setData(pager.getRecords());
		return pageBean;
	}


	@ResponseBody
	@RequestMapping(value = "/alarmTrendList")
	public String alarmTrendList(HttpServletRequest request,String deviceCode,String condensingDeviceNum,String alarmType,String query_startTime,String query_endTime,String flag) {
		String departmentName = request.getParameter("departmentName");
		// 构造条件查询
		EntityWrapper<AlarmInfo> ew = new EntityWrapper<>();
		ew.setEntity(new AlarmInfo());
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("a.device_code", deviceCode);
		}
		//企业主看板报警信息需要显示该企业下所有机组的报警
		if(flag==null) {
			if (StringUtils.isNotBlank(condensingDeviceNum)) {
				ew.andNew();
				ew.eq("a.condensing_device_num", condensingDeviceNum);
			}
		}

		if (StringUtils.isNotBlank(alarmType)) {
			ew.andNew();
			ew.eq("a.alarm_type", alarmType);
		}
		if(StringUtils.isNotBlank(departmentName)){
			ew.andNew();
			ew.eq("location", departmentName);
		}
		ew.and();
		ew.gt("a.create_time", query_startTime);
		ew.and();
		ew.lt("a.create_time", query_endTime);
		ew.orderBy("a.create_time", false);

		List<AlarmInfo> alarmInfoList = alarmInfoService.selectRelationAlarmList(ew);
		List<String> alarmDeviceNameList = new ArrayList<>();
		for (AlarmInfo ai : alarmInfoList) {
			if(ai.getCondensingDeviceNum().equals("0")){
				alarmDeviceNameList.add("共通");
			}else{
				EntityWrapper<ControlMachine> cdew = new EntityWrapper<>();
				if (StringUtils.isNotBlank(deviceCode)) {
					cdew.andNew();
					cdew.eq("device_code", deviceCode);
				}
				cdew.andNew();
				cdew.eq("machine_no", ai.getCondensingDeviceNum());
				alarmDeviceNameList.add(controlMachineService.selectOne(cdew).getMachineName());
			}
		}
		EntityWrapper<AlarmInfo> ew1 = new EntityWrapper<>();
		ew1.setEntity(new AlarmInfo());
		if (StringUtils.isNotBlank(deviceCode)) {
			ew1.andNew();
			ew1.eq("a.device_code", deviceCode);
		}
		if (StringUtils.isNotBlank(condensingDeviceNum)) {
			ew1.andNew();
			ew1.eq("a.condensing_device_num", condensingDeviceNum);
		}
		if (StringUtils.isNotBlank(alarmType)) {
			ew1.andNew();
			ew1.eq("a.alarm_type", alarmType);
		}
		ew1.and();
		ew1.gt("a.create_time", query_startTime);
		ew1.and();
		ew1.lt("a.create_time", query_endTime);
		List<AlarmInfo> alarmInfoList1 = alarmInfoService.selectRelationList(ew1);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("alarmDeviceNameList", alarmDeviceNameList);
		map.put("alarmInfoList", alarmInfoList);
		if(condensingDeviceNum.endsWith("0")) {
			map.put("alarmInfoListCount", alarmInfoList1.size());
		}else {
			map.put("deviceAlarmInfoList", alarmInfoList1);
		}


		return JSONObject.toJSONString(map);
	}


	@ResponseBody
	@RequestMapping(value = "/enterpriseAlarmList")
	public String enterpriseAlarmList(HttpServletRequest request,String deviceCode,String query_startTime,String query_endTime) {


		EntityWrapper<ControlMachine> ew2 = new EntityWrapper<>();
		ew2.setEntity(new ControlMachine());
		if (StringUtils.isNotBlank(deviceCode)) {
			ew2.andNew();
			ew2.eq("b.device_code", deviceCode);
		}
		ew2.groupBy("a.machine_no");
		//带数字的字符类型可以加0来排序，这样就可以按照正常的数值排序了
		ew2.orderBy("a.machine_no+0");
		List<JSONObject> list = new ArrayList<JSONObject>();
		List<ControlMachine> condensingDeviceList = controlMachineService.selectRelationList(ew2);
		for(int i=0;i<condensingDeviceList.size();i++) {
			JSONObject jsonObject = new JSONObject();
			String condensingDeviceNum = condensingDeviceList.get(i).getMachineNo();
			// 构造条件查询
			EntityWrapper<AlarmInfo> ew = new EntityWrapper<>();
			ew.setEntity(new AlarmInfo());
			if (StringUtils.isNotBlank(deviceCode)) {
				ew.andNew();
				ew.eq("a.device_code", deviceCode);
			}
			ew.and();
			ew.gt("a.create_time", query_startTime);
			ew.and();
			ew.lt("a.create_time", query_endTime);
			ew.andNew();
			ew.eq("a.machine_no", condensingDeviceNum);
			int flag = 0 ;//0:停机；2：运行；3：预警；4：故障
			//判断开关状态，0：停机，1：运行
			if(condensingDeviceList.get(i).getPowerStatus()==0) {
				flag = 0;
			}else if(condensingDeviceList.get(i).getPowerStatus()==1) {
				if(condensingDeviceList.get(i).getAlarmStatus()==0) {
					flag = 1;
				}else if(condensingDeviceList.get(i).getAlarmStatus()==1) {
					flag = 2;
				}else if(condensingDeviceList.get(i).getAlarmStatus()==2) {
					flag = 3;
				}
			}
//			List<AlarmInfo> alarmInfoList = alarmInfoService.selectRelationList(ew);
//			int flag = 0 ;//0:正常；1：预警；2：报警
//			for(int j=0;j<alarmInfoList.size();j++) {
//				if(alarmInfoList.get(j).getAlarmType().equals("0")) {
//					flag =1;
//				}else if(alarmInfoList.get(j).getAlarmType().equals("1")) {
//					flag =2;
//					break;
//				}
//			}
			jsonObject.put("condensingDeviceNum", condensingDeviceNum);
			jsonObject.put("condensingDeviceName", condensingDeviceList.get(i).getMachineNo());
			jsonObject.put("deviceModel", condensingDeviceList.get(i).getMachineModel());
			jsonObject.put("runtime", condensingDeviceList.get(i).getRuntime());
			jsonObject.put("flag", flag);
			list.add(jsonObject);

		}

		return JSONObject.toJSONString(list);
	}


	@RequestMapping(value = "/exportAlarmInfoList")
	@ResponseBody
	public String exportAlarmInfoList(HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> hm = new HashMap<String, Object>();
		String deviceCode = request.getParameter("deviceCode");
 		String machineId = request.getParameter("machineId");
		String alarmType = request.getParameter("alarmType");
		String query_startTime = request.getParameter("query_startTime");
		String query_endTime = request.getParameter("query_endTime");



		EntityWrapper<AlarmInfo> ew = new EntityWrapper<>();
		ew.setEntity(new AlarmInfo());
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("a.device_code", deviceCode);
		}
		if (StringUtils.isNotBlank(machineId)) {
			ew.andNew();
			ew.eq("b.id", machineId);
		}
		if (StringUtils.isNotBlank(alarmType)) {
			ew.andNew();
			ew.eq("a.alarm_type", alarmType);
		}
		if(StringUtils.isNotBlank(query_startTime) && StringUtils.isNotBlank(query_endTime)) {
			ew.andNew();
			ew.gt("a.create_time", query_startTime);
			ew.and();
			ew.lt("a.create_time", query_endTime);
		}
		ew.orderBy("a.create_time", false);

		List<AlarmInfo> alarmInfolist = alarmInfoService.selectRelationAlarmList(ew);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<alarmInfolist.size();i++) {
			if(alarmInfolist.get(i).getCreateTime()!=null) {
				alarmInfolist.get(i).setCreateTimeStr(sdf.format(alarmInfolist.get(i).getCreateTime()));
			}else {
				alarmInfolist.get(i).setCreateTimeStr("");
			}

			if(alarmInfolist.get(i).getRecoveryTime()!=null) {
				alarmInfolist.get(i).setRecoveryTimeStr(sdf.format(alarmInfolist.get(i).getRecoveryTime()));
			}else {
				alarmInfolist.get(i).setRecoveryTimeStr("");
			}

			if(alarmInfolist.get(i).getAlarmType().equals("0")) {
				alarmInfolist.get(i).setAlarmTypeStr("预警");
			}else if(alarmInfolist.get(i).getAlarmType().equals("1")) {
				alarmInfolist.get(i).setAlarmTypeStr("故障");
			}else if(alarmInfolist.get(i).getAlarmType().equals("2")) {
				alarmInfolist.get(i).setAlarmTypeStr("阈值预警");
			}

		}


		LinkedHashMap<String,String> headerMap = new LinkedHashMap<String,String>();
		 headerMap.put("enterpriseName", "企业名称");
		 headerMap.put("condensingDeviceNum", "机组号");
		 headerMap.put("createTimeStr", "采集时间");
		 headerMap.put("factorCode", "监测因子");
		 headerMap.put("factorName", "监测因子名称");
		 headerMap.put("factorValue", "数值");
		 headerMap.put("alarmContent", "报警内容");
		 headerMap.put("alarmTypeStr", "报警类型");
		 headerMap.put("recoveryTimeStr", "报警恢复时间");


		//实体类导出时才有用
	     String fileds[] = new String[] { "enterpriseName","condensingDeviceNum","createTimeStr", "factorCode","factorName","factorValue","alarmContent","alarmTypeStr" ,"recoveryTimeStr"};
	        try {
				CSVUtil.exportFileByModel(response, headerMap, alarmInfolist, fileds);
				hm.put("success", true);
				/*
				 * response：直接传入response
				 * map：对应文件的第一行
				 * list：对应 List<CVSBean>  list对象形式
				 * fileds：对应每一列的数据
				 * */
			} catch (IOException e) {
				// TODO Auto-generated catch block
				hm.put("success", false);
				e.printStackTrace();
			}//直接调用

			return JSON.toJSONString(hm);
		}


}
