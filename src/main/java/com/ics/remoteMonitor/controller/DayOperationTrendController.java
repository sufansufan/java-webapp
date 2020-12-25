package com.ics.remoteMonitor.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.ics.remoteMonitor.service.RtdHistoryService;
import com.ics.dataDesources.model.CondensingDevice;
import com.ics.dataDesources.service.CondensingDeviceService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CSVUtil;
import com.ics.utils.CommonUtil;

/**
 * 天运行趋势
 * 
 * @author jjz
 *
 */
@Controller
@RequestMapping("/remoteMonitor/dayOperationTrend")
public class DayOperationTrendController {

	
	protected static final String dayOperationTrendJsp = "views/remoteMonitor/condensingDeviceMonitor/dayOperationTrend/index";
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private CondensingDeviceService condensingDeviceService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;
	@Autowired
	private RtdHistoryService rtdHistoryService;
	@Autowired
	private MonitorFactorTemplateService monitorFactorTemplateService;

	/**
	 * 天运行趋势
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dayOperationTrend", method = RequestMethod.GET)
	public ModelAndView dayOperationTrend(HttpServletRequest request,String id) {
		ModelAndView mav = new ModelAndView(dayOperationTrendJsp);
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
		if(enterpriseInfoList.size()>0) {
			EntityWrapper<CondensingDevice> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new CondensingDevice());
			deviceEw.andNew();
			deviceEw.eq("enterprise_id", enterpriseInfoList.get(0).getId());
			deviceEw.orderBy("machine_no+0");
			List<CondensingDevice> condensingDeviceList = this.condensingDeviceService.selectList(deviceEw);
			mav.addObject("condensingDeviceList", condensingDeviceList);
		}
		
	
		
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
	

	@RequestMapping(value = "/historyRecordList")
	@ResponseBody
	public String historyRecordList(HttpServletRequest request, String query_startTime, String query_endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String[]> pressureValueList = new ArrayList<String[]>();
		List<String[]> temperatureValueList = new ArrayList<String[]>();
		List<String[]> vibrationValueList = new ArrayList<String[]>();
		List<String[]> electricValueList = new ArrayList<String[]>();
		List<String[]> positionValueList = new ArrayList<String[]>();
		String deviceCode = request.getParameter("deviceCode");
		String condensingDeviceNum = request.getParameter("condensingDeviceNum");
		
//		查询压力的监测因子
		EntityWrapper<MonitorFactorTemplate> factorEw5 = new EntityWrapper<>();
		factorEw5.setEntity(new MonitorFactorTemplate());
		factorEw5.andNew();
		factorEw5.eq("machine_no", condensingDeviceNum);
		factorEw5.and();
		factorEw5.eq("a.device_code", deviceCode);
		factorEw5.and();
		factorEw5.eq("factor_tag", "chart5");
		factorEw5.orderBy("factor_code");		
		List<MonitorFactorTemplate> factorList_chart5 = monitorFactorTemplateService.selectList(factorEw5);
		
		String[] pressureFactorCodeArr = new String[factorList_chart5.size()];
		String[] pressureFactorNameArr = new String[factorList_chart5.size()];
		BigDecimal[] pressure_dataAccuracyArr = new BigDecimal[factorList_chart5.size()];
		int[] pressure_decimalDigitsArr = new int[factorList_chart5.size()];
		String[] pressureFactorUnitArr = new String[factorList_chart5.size()];
		for(int i=0;i<factorList_chart5.size();i++) {
			pressureFactorCodeArr[i] = factorList_chart5.get(i).getFactorCode();
			pressureFactorNameArr[i] = factorList_chart5.get(i).getFactorName();
			pressure_dataAccuracyArr[i] = factorList_chart5.get(i).getDataAccuracy();
			pressure_decimalDigitsArr[i] = factorList_chart5.get(i).getDecimalDigits();
			pressureFactorUnitArr[i] = factorList_chart5.get(i).getFactorUnit();
		}
		
//		查询温度的监测因子
		EntityWrapper<MonitorFactorTemplate> factorEw6 = new EntityWrapper<>();
		factorEw6.setEntity(new MonitorFactorTemplate());
		factorEw6.andNew();
		factorEw6.eq("machine_no", condensingDeviceNum);
		factorEw6.and();
		factorEw6.eq("a.device_code", deviceCode);
		factorEw6.and();
		factorEw6.eq("factor_tag", "chart6");
		factorEw6.orderBy("factor_code");
		List<MonitorFactorTemplate> factorList_chart6 = monitorFactorTemplateService.selectList(factorEw6);
		
		String[] temperatureFactorCodeArr = new String[factorList_chart6.size()];
		String[] temperatureFactorNameArr = new String[factorList_chart6.size()];
		BigDecimal[] temperature_dataAccuracyArr = new BigDecimal[factorList_chart6.size()];
		int[] temperature_decimalDigitsArr = new int[factorList_chart6.size()];
		String[] temperatureFactorUnitArr = new String[factorList_chart6.size()];
		for(int i=0;i<factorList_chart6.size();i++) {
			temperatureFactorCodeArr[i] = factorList_chart6.get(i).getFactorCode();
			temperatureFactorNameArr[i] = factorList_chart6.get(i).getFactorName();
			temperature_dataAccuracyArr[i] = factorList_chart6.get(i).getDataAccuracy();
			temperature_decimalDigitsArr[i] = factorList_chart6.get(i).getDecimalDigits();
			temperatureFactorUnitArr[i] = factorList_chart6.get(i).getFactorUnit();
		}
		
//		查询各类振动的监测因子
		EntityWrapper<MonitorFactorTemplate> factorEw7 = new EntityWrapper<>();
		factorEw7.setEntity(new MonitorFactorTemplate());
		factorEw7.andNew();
		factorEw7.eq("machine_no", condensingDeviceNum);
		factorEw7.and();
		factorEw7.eq("a.device_code", deviceCode);
		factorEw7.and();
		factorEw7.eq("factor_tag", "chart7");
		factorEw7.orderBy("factor_code");
		List<MonitorFactorTemplate> factorList_chart7 = monitorFactorTemplateService.selectList(factorEw7);
		
		String[] vibrationFactorCodeArr = new String[factorList_chart7.size()];
		String[] vibrationFactorNameArr = new String[factorList_chart7.size()];
		BigDecimal[] vibration_dataAccuracyArr = new BigDecimal[factorList_chart7.size()];
		int[] vibration_decimalDigitsArr = new int[factorList_chart7.size()];
		String[] vibrationFactorUnitArr = new String[factorList_chart7.size()];
		for(int i=0;i<factorList_chart7.size();i++) {
			vibrationFactorCodeArr[i] = factorList_chart7.get(i).getFactorCode();
			vibrationFactorNameArr[i] = factorList_chart7.get(i).getFactorName();
			vibration_dataAccuracyArr[i] = factorList_chart7.get(i).getDataAccuracy();
			vibration_decimalDigitsArr[i] = factorList_chart7.get(i).getDecimalDigits();
			vibrationFactorUnitArr[i] = factorList_chart7.get(i).getFactorUnit();
		}
		
//		查询电流的监测因子
		EntityWrapper<MonitorFactorTemplate> factorEw8 = new EntityWrapper<>();
		factorEw8.setEntity(new MonitorFactorTemplate());
		factorEw8.andNew();
		factorEw8.eq("machine_no", condensingDeviceNum);
		factorEw8.and();
		factorEw8.eq("a.device_code", deviceCode);
		factorEw8.and();
		factorEw8.eq("factor_tag", "chart8");
		factorEw8.orderBy("factor_code");
		List<MonitorFactorTemplate> factorList_chart8 = monitorFactorTemplateService.selectList(factorEw8);
		
		String[] electricFactorCodeArr = new String[factorList_chart8.size()];
		String[] electricFactorNameArr = new String[factorList_chart8.size()];
		BigDecimal[] electric_dataAccuracyArr = new BigDecimal[factorList_chart8.size()];
		int[] electric_decimalDigitsArr = new int[factorList_chart8.size()];
		String[] electricFactorUnitArr = new String[factorList_chart8.size()];
		for(int i=0;i<factorList_chart8.size();i++) {
			electricFactorCodeArr[i] = factorList_chart8.get(i).getFactorCode();
			electricFactorNameArr[i] = factorList_chart8.get(i).getFactorName();
			electric_dataAccuracyArr[i] = factorList_chart8.get(i).getDataAccuracy();
			electric_decimalDigitsArr[i] = factorList_chart8.get(i).getDecimalDigits();
			electricFactorUnitArr[i] = factorList_chart8.get(i).getFactorUnit();
		}
		
//		查询位置的监测因子
		EntityWrapper<MonitorFactorTemplate> factorEw9 = new EntityWrapper<>();
		factorEw9.setEntity(new MonitorFactorTemplate());
		factorEw9.andNew();
		factorEw9.eq("machine_no", condensingDeviceNum);
		factorEw9.and();
		factorEw9.eq("a.device_code", deviceCode);
		factorEw9.and();
		factorEw9.eq("factor_tag", "chart9");
		factorEw9.orderBy("factor_code");
		List<MonitorFactorTemplate> factorList_chart9 = monitorFactorTemplateService.selectList(factorEw9);
		
		String[] positionFactorCodeArr = new String[factorList_chart9.size()];
		String[] positionFactorNameArr = new String[positionFactorCodeArr.length];
		BigDecimal[] position_dataAccuracyArr = new BigDecimal[positionFactorCodeArr.length];
		int[] position_decimalDigitsArr = new int[positionFactorCodeArr.length];
		String[] positionFactorUnitArr = new String[positionFactorCodeArr.length];
		for(int i=0;i<factorList_chart9.size();i++) {
			positionFactorCodeArr[i] = factorList_chart9.get(i).getFactorCode();
			positionFactorNameArr[i] = factorList_chart9.get(i).getFactorName();
			position_dataAccuracyArr[i] = factorList_chart9.get(i).getDataAccuracy();
			position_decimalDigitsArr[i] = factorList_chart9.get(i).getDecimalDigits();
			positionFactorUnitArr[i] = factorList_chart9.get(i).getFactorUnit();
		}
		
		// 设备编码
		if (null != deviceCode && !"".equals(deviceCode)) {
			params.put("deviceCode", deviceCode);
		} else {
			params.put("deviceCode", null);
		}
		
		// 设备编号
		if (null != condensingDeviceNum && !"".equals(condensingDeviceNum)) {
			params.put("condensingDeviceNum", condensingDeviceNum);
		} else {
			params.put("condensingDeviceNum", null);
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    long oneDayMsec = 86400000;
	    query_startTime = query_startTime + " 08:00:00";
	    query_endTime = query_endTime + " 08:00:00";
		List<String> timeList = new ArrayList<String>();

		if (query_startTime != null && query_endTime != null) {
			String query_startTimeStr = query_startTime.split(" ")[0].replace("-", "");
			String query_endTimeStr = query_endTime.split(" ")[0].replace("-", "");
			if(query_startTimeStr.equals(query_endTimeStr)) {
				timeList.add(query_startTime);
			}else {
				try {
					long startTimeMsec = format.parse(query_startTime).getTime();
					long endTimeMsec = format.parse(query_endTime).getTime();				
					for(int i=0;i<100;i++) {
						long tempTimeMsec = startTimeMsec + (i*oneDayMsec);
						if(tempTimeMsec>endTimeMsec) {
							break;
						}else {
							String tempTimeStr = format.format(tempTimeMsec);
							timeList.add(tempTimeStr);
						}
					}
															
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		params.put("timeList", timeList);
		
		params.put("sort", "desc");
		
//		params.put("factorCodeList",Arrays.asList(factorCodeArr));
		String[] factorTagArr = new String[] {"chart5","chart6","chart7","chart8","chart9"};
		params.put("factorTagList",Arrays.asList(factorTagArr));
		List<RtdHistory> rtdHistoryList = rtdHistoryService.getList(params);
		
		for (int i = 0; i < pressureFactorCodeArr.length; i++) {
			int index = 0;
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if(rtdHistoryList.get(j).getFactorCode().equals(pressureFactorCodeArr[i])) {
					if(i==0) {
						pressureValueList.add(new String[pressureFactorCodeArr.length+1]);
					}
					if(rtdHistoryList.get(j).getFactorValue().equals("")) {
						pressureValueList.get(index)[i+1] = "";
					}else {
						pressureValueList.get(index)[i+1] = String.valueOf(pressure_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(pressure_decimalDigitsArr[i],BigDecimal.ROUND_UP));
					}
					if(pressureValueList.get(index)[0]==null) {
						pressureValueList.get(index)[0]= format.format(rtdHistoryList.get(j).getCollectTime());
					}
					index++;
				}
			}
		}
		
		for (int i = 0; i < temperatureFactorCodeArr.length; i++) {
			int index = 0;
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if(rtdHistoryList.get(j).getFactorCode().equals(temperatureFactorCodeArr[i])) {	
					if(i==0) {
						temperatureValueList.add(new String[temperatureFactorCodeArr.length+1]);
					}
					if(rtdHistoryList.get(j).getFactorValue().equals("")) {
						temperatureValueList.get(index)[i+1] = "";
					}else {
						temperatureValueList.get(index)[i+1] = String.valueOf(temperature_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(temperature_decimalDigitsArr[i],BigDecimal.ROUND_UP));
					}
					if(temperatureValueList.get(index)[0]==null) {
						temperatureValueList.get(index)[0]= format.format(rtdHistoryList.get(j).getCollectTime());
					}
					index++;
				}
			}
		}
		
		for (int i = 0; i < vibrationFactorCodeArr.length; i++) {
			int index = 0;
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if(rtdHistoryList.get(j).getFactorCode().equals(vibrationFactorCodeArr[i])) {					
					if(i==0) {
						vibrationValueList.add(new String[vibrationFactorCodeArr.length+1]);
					}
					if(rtdHistoryList.get(j).getFactorValue().equals("")) {
						vibrationValueList.get(index)[i+1] = "";
					}else {
						vibrationValueList.get(index)[i+1] = String.valueOf(vibration_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(vibration_decimalDigitsArr[i],BigDecimal.ROUND_UP));
					}
					if(vibrationValueList.get(index)[0]==null) {
						vibrationValueList.get(index)[0]= format.format(rtdHistoryList.get(j).getCollectTime());
					}
					index++;
				}
			}
		}
		
		
		for (int i = 0; i < electricFactorCodeArr.length; i++) {
			int index = 0;
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if(rtdHistoryList.get(j).getFactorCode().equals(electricFactorCodeArr[i])) {					
					if(i==0) {
						electricValueList.add(new String[electricFactorCodeArr.length+1]);
					}
					if(rtdHistoryList.get(j).getFactorValue().equals("")) {
						electricValueList.get(index)[i+1] = "";
					}else {
						electricValueList.get(index)[i+1] = String.valueOf(electric_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(electric_decimalDigitsArr[i],BigDecimal.ROUND_UP));
					}
					if(electricValueList.get(index)[0]==null) {
						electricValueList.get(index)[0]= format.format(rtdHistoryList.get(j).getCollectTime());
					}
					index++;
				}
			}
		}
			
		
		for (int i = 0; i < positionFactorCodeArr.length; i++) {
			int index = 0;
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if(rtdHistoryList.get(j).getFactorCode().equals(positionFactorCodeArr[i])) {					
					if(i==0) {
						positionValueList.add(new String[positionFactorCodeArr.length+1]);
					}
					if(rtdHistoryList.get(j).getFactorValue().equals("")) {
						positionValueList.get(index)[i+1] = "";
					}else {
						positionValueList.get(index)[i+1] = String.valueOf(position_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(position_decimalDigitsArr[i],BigDecimal.ROUND_UP));
					}
					if(positionValueList.get(index)[0]==null) {
						positionValueList.get(index)[0]= format.format(rtdHistoryList.get(j).getCollectTime());
					}
					index++;
				}
			}
		}
		
		//获取图表的数据start
		if(true) {
			Map<String, Object> hm = new HashMap<String, Object>();
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < pressureFactorCodeArr.length; i++) {
				List<String> monitorFactorValueList = new ArrayList<String>();
				List<String> monitorFactorTimeList = new ArrayList<String>();
				for(int j=rtdHistoryList.size()-1;j>=0;j--) {
					if(rtdHistoryList.get(j).getFactorCode().equals(pressureFactorCodeArr[i])) {
						monitorFactorValueList.add(String.valueOf(pressure_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(pressure_decimalDigitsArr[i],BigDecimal.ROUND_UP)));
						monitorFactorTimeList.add(format.format(rtdHistoryList.get(j).getCollectTime()));

					}
				}
				String[] monitorFactorValueArr = new String[monitorFactorValueList.size()];
				String[] monitorFactorTimeArr = new String[monitorFactorValueList.size()];

				monitorFactorValueList.toArray(monitorFactorValueArr);
				monitorFactorTimeList.toArray(monitorFactorTimeArr);
				list.add(monitorFactorValueList);
				if(i==0) {
					hm.put("timeChart", monitorFactorTimeArr);
				}
				
			}
			hm.put("value", list);
			map.put("pressureChart", hm);
			
		}
		
		if(true) {
			Map<String, Object> hm = new HashMap<String, Object>();
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < temperatureFactorCodeArr.length; i++) {
				List<String> monitorFactorValueList = new ArrayList<String>();
				List<String> monitorFactorTimeList = new ArrayList<String>();
				for(int j=rtdHistoryList.size()-1;j>=0;j--) {
					if(rtdHistoryList.get(j).getFactorCode().equals(temperatureFactorCodeArr[i])) {
						monitorFactorValueList.add(String.valueOf(temperature_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(temperature_decimalDigitsArr[i],BigDecimal.ROUND_UP)));
						monitorFactorTimeList.add(format.format(rtdHistoryList.get(j).getCollectTime()));

					}
				}
				String[] monitorFactorValueArr = new String[monitorFactorValueList.size()];
				String[] monitorFactorTimeArr = new String[monitorFactorValueList.size()];

				monitorFactorValueList.toArray(monitorFactorValueArr);
				monitorFactorTimeList.toArray(monitorFactorTimeArr);
				list.add(monitorFactorValueList);
				if(i==0) {
					hm.put("timeChart", monitorFactorTimeArr);
				}
				
			}
			hm.put("value", list);
			map.put("temperatureChart", hm);
			
		}
		
		
		if(true) {
			Map<String, Object> hm = new HashMap<String, Object>();
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < vibrationFactorCodeArr.length; i++) {
				List<String> monitorFactorValueList = new ArrayList<String>();
				List<String> monitorFactorTimeList = new ArrayList<String>();
				for(int j=rtdHistoryList.size()-1;j>=0;j--) {
					if(rtdHistoryList.get(j).getFactorCode().equals(vibrationFactorCodeArr[i])) {
						monitorFactorValueList.add(String.valueOf(vibration_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(vibration_decimalDigitsArr[i],BigDecimal.ROUND_UP)));
						monitorFactorTimeList.add(format.format(rtdHistoryList.get(j).getCollectTime()));

					}
				}
				String[] monitorFactorValueArr = new String[monitorFactorValueList.size()];
				String[] monitorFactorTimeArr = new String[monitorFactorValueList.size()];

				monitorFactorValueList.toArray(monitorFactorValueArr);
				monitorFactorTimeList.toArray(monitorFactorTimeArr);
				list.add(monitorFactorValueList);
				if(i==0) {
					hm.put("timeChart", monitorFactorTimeArr);
				}
				
			}
			hm.put("value", list);
			map.put("vibrationChart", hm);
			
		}
		
		
		if(true) {
			Map<String, Object> hm = new HashMap<String, Object>();
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < electricFactorCodeArr.length; i++) {
				List<String> monitorFactorValueList = new ArrayList<String>();
				List<String> monitorFactorTimeList = new ArrayList<String>();
				for(int j=rtdHistoryList.size()-1;j>=0;j--) {
					if(rtdHistoryList.get(j).getFactorCode().equals(electricFactorCodeArr[i])) {
						monitorFactorValueList.add(String.valueOf(electric_dataAccuracyArr[i].multiply(new BigDecimal(rtdHistoryList.get(j).getFactorValue())).setScale(electric_decimalDigitsArr[i],BigDecimal.ROUND_UP)));
						monitorFactorTimeList.add(format.format(rtdHistoryList.get(j).getCollectTime()));

					}
				}
				String[] monitorFactorValueArr = new String[monitorFactorValueList.size()];
				String[] monitorFactorTimeArr = new String[monitorFactorValueList.size()];

				monitorFactorValueList.toArray(monitorFactorValueArr);
				monitorFactorTimeList.toArray(monitorFactorTimeArr);
				list.add(monitorFactorValueList);
				if(i==0) {
					hm.put("timeChart", monitorFactorTimeArr);
				}
				
			}
			hm.put("value", list);
			map.put("electricChart", hm);
			
		}
		//获取图表的数据end
		map.put("pressure", pressureValueList);
		map.put("temperature", temperatureValueList);
		map.put("vibration", vibrationValueList);
		map.put("electric", electricValueList);
		map.put("position", positionValueList);
		//返回监测因子名称
		map.put("pressureFactorNameArr", pressureFactorNameArr);
		map.put("temperatureFactorNameArr", temperatureFactorNameArr);
		map.put("vibrationFactorNameArr", vibrationFactorNameArr);
		map.put("electricFactorNameArr", electricFactorNameArr);
		map.put("positionFactorNameArr", positionFactorNameArr);
		//返回监测因子单位
		map.put("pressureFactorUnitArr", pressureFactorUnitArr);
		map.put("temperatureFactorUnitArr", temperatureFactorUnitArr);
		map.put("vibrationFactorUnitArr", vibrationFactorUnitArr);
		map.put("electricFactorUnitArr", electricFactorUnitArr);
		map.put("positionFactorUnitArr", positionFactorUnitArr);
		return  JSON.toJSONString(map);
	}

	@RequestMapping(value = "/changeCondensingDevice", method = RequestMethod.POST)
	@ResponseBody
	public String changeCondensingDevice(HttpServletRequest request) throws Exception {
		String deviceCode = request.getParameter("deviceCode");
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		if (StringUtils.isNotBlank(deviceCode)) {
			ew.andNew();
			ew.eq("device_code", deviceCode);
		}

		List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);
		
		List<CondensingDevice> condensingDeviceList =null;
		if(enterpriseInfoList.size()>0) {
			EntityWrapper<CondensingDevice> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new CondensingDevice());
			deviceEw.andNew();
			deviceEw.eq("enterprise_id", enterpriseInfoList.get(0).getId());
			condensingDeviceList = this.condensingDeviceService.selectList(deviceEw);
		}
		return JSONObject.toJSONString(condensingDeviceList);
	}
	
	
	@RequestMapping(value = "/exportHistoryRecordList")
	@ResponseBody
	public String exportHistoryRecordList(HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		 Map<String, Object> hm = new HashMap<String, Object>();
		List<Map<String, String>> returnValueList = new ArrayList<Map<String, String>>();
		String deviceCode = request.getParameter("deviceCode");
 		String condensingDeviceNum = request.getParameter("condensingDeviceNum");
		String query_startTime = request.getParameter("query_startTime");
		String query_endTime = request.getParameter("query_endTime");
		String flag = request.getParameter("flag");

	    LinkedHashMap<String,String> headerMap = new LinkedHashMap<String,String>(); 
		
		String factor_tag = "";
		if(flag.equals("pressure")) {
			factor_tag = "chart5";
	}else if(flag.equals("temperature")) {
		factor_tag = "chart6";
	}else if(flag.equals("vibration")) {
		factor_tag = "chart7";
	}else if(flag.equals("electric")) {
		factor_tag = "chart8";
	}else if(flag.equals("position")) {
		factor_tag = "chart9";
	}

		
		EntityWrapper<MonitorFactorTemplate> factorEw = new EntityWrapper<>();
		factorEw.setEntity(new MonitorFactorTemplate());
		factorEw.andNew();
		factorEw.eq("a.device_code", deviceCode);
		factorEw.andNew();
		factorEw.eq("machine_no", condensingDeviceNum);
		factorEw.and();
		factorEw.eq("factor_tag", factor_tag);
		factorEw.orderBy("factor_code");	

		List<MonitorFactorTemplate> factorList = monitorFactorTemplateService.selectList(factorEw);
		
		String[] factorCodeArr = new String[factorList.size()];
		BigDecimal[] dataAccuracyArr = new BigDecimal[factorList.size()];
		int[] decimalDigitsArr = new int[factorList.size()];
		for(int i=0;i<factorList.size();i++) {
			if(i==0) {
				   headerMap.put("collectTime", "采集时间");
			    }
			factorCodeArr[i] = factorList.get(i).getFactorCode();
			dataAccuracyArr[i] = factorList.get(i).getDataAccuracy();
			decimalDigitsArr[i] = factorList.get(i).getDecimalDigits();
			headerMap.put(factorList.get(i).getFactorCode(), factorList.get(i).getFactorName());
		}
		
		// 设备编码
		if (null != deviceCode && !"".equals(deviceCode)) {
			params.put("deviceCode", deviceCode);
		} else {
			params.put("deviceCode", null);
		}
		
		// 设备编号
		if (null != condensingDeviceNum && !"".equals(condensingDeviceNum)) {
			params.put("condensingDeviceNum", condensingDeviceNum);
		} else {
			params.put("condensingDeviceNum", null);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    long oneDayMsec = 86400000;
	    query_startTime = query_startTime + " 08:00:00";
	    query_endTime = query_endTime + " 08:00:00";
		List<String> timeList = new ArrayList<String>();
	    
		if (query_startTime != null && query_endTime != null) {
			String query_startTimeStr = query_startTime.split(" ")[0].replace("-", "");
			String query_endTimeStr = query_endTime.split(" ")[0].replace("-", "");
			if(query_startTimeStr.equals(query_endTimeStr)) {
				timeList.add(query_startTime);
			}else {
				try {
					long startTimeMsec = format.parse(query_startTime).getTime();
					long endTimeMsec = format.parse(query_endTime).getTime();						
					for(int i=0;i<100;i++) {
						long tempTimeMsec = startTimeMsec + (i*oneDayMsec);
						if(tempTimeMsec>endTimeMsec) {
							break;
						}else {
							String tempTimeStr = format.format(tempTimeMsec);
							//获取时间区间的每个8点整的时间
							timeList.add(tempTimeStr);
						}
						
					}
					

															
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}			
			
			params.put("timeList", timeList);
		} 
		
	    params.put("sort", "desc");
		

		params.put("factorTag",factor_tag);
		List<RtdHistory> rtdHistoryList = rtdHistoryService.getList(params);
		
		for(int i=0;i<rtdHistoryList.size();i++) {
			 Map<String, String> map = new LinkedHashMap<String, String>(); 
			returnValueList.add(map);
		}
		
		for (int i = 0; i < factorCodeArr.length; i++) {
			int index = 0;
			for (int j = 0; j < rtdHistoryList.size(); j++) {
				if(rtdHistoryList.get(j).getFactorCode().equals(factorCodeArr[i])) {
					returnValueList.get(index).put("collectTime", format.format(rtdHistoryList.get(j).getCollectTime()));
					String factorValue = rtdHistoryList.get(j).getFactorValue();
					returnValueList.get(index).put(factorCodeArr[i], String.valueOf(dataAccuracyArr[i].multiply(new BigDecimal(factorValue)).setScale(decimalDigitsArr[i],BigDecimal.ROUND_UP)));
					index++;
				}
			}
		}
		
		
		//实体类导出时才有用
	     String fileds[] = new String[] { "id", "deviceCode","condensingDeviceNum","factorCode","factorValue","collectTime" };
	        try {
				CSVUtil.exportFile(response, headerMap, returnValueList, fileds);
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
