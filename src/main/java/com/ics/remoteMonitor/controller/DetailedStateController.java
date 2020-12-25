package com.ics.remoteMonitor.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.dataDesources.model.MonitorFactorTemplate;
import com.ics.dataDesources.service.EnterpriseInfoService;
import com.ics.dataDesources.service.MonitorFactorTemplateService;
import com.ics.remoteMonitor.model.RtdHistory;
import com.ics.remoteMonitor.service.RtdHistoryService;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.utils.CSVUtil;
import com.ics.utils.CommonUtil;
import com.ics.utils.PageBean;
import com.ics.utils.PageUtil;

/**
 * 详细状态
 *
 * @author jjz
 *
 */
@Controller
@RequestMapping("/remoteMonitor/detailedState")
public class DetailedStateController {
	protected static Logger logger = Logger.getLogger(DetailedStateController.class);
	protected static final String detailedStateJsp = "views/remoteMonitor/condensingDeviceMonitor/detailedState/index";
	protected static final String detailedStateListJsp = "views/remoteMonitor/condensingDeviceMonitor/detailedState/historyRecordList";

	@Autowired
	private RtdHistoryService rtdHistoryService;
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private ControlMachineService controlMachineService;
	@Autowired
	private EnterpriseInfoService enterpriseInfoService;
	@Autowired
	private MonitorFactorTemplateService monitorFactorTemplateService;

	/**
	 * 详细状态
	 * @param request
	 * @return
	 */
	 @RequiresPermissions(value = "detailed_state")
	@RequestMapping(value = "/detailedState")
	public ModelAndView detailedState(HttpServletRequest request,String id) {
		ModelAndView mav = new ModelAndView(detailedStateJsp);

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
		String[] monitorFactorsArr =null;
		String[] monitorFactorsNameArr =null;
		if(enterpriseInfoList.size()>0) {
			EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new ControlMachine());
			deviceEw.orderBy("machine_no+0");
			List<ControlMachine> machineList = this.controlMachineService.selectRelationList(deviceEw);
			mav.addObject("machineList", machineList);
			if(machineList.size()>0) {
				EntityWrapper<MonitorFactorTemplate> templateEw = new EntityWrapper<>();
				templateEw.setEntity(new MonitorFactorTemplate());
				templateEw.andNew();
				templateEw.eq("a.device_code", machineList.get(0).getDeviceCode());
				templateEw.eq("machine_no", machineList.get(0).getMachineNo());
				templateEw.andNew();
				templateEw.in("type_id","1,4");
				templateEw.orderBy("type_id");
				templateEw.orderBy("factor_code");
				List<MonitorFactorTemplate> monitorFactorTemplateList = monitorFactorTemplateService.selectList(templateEw);
				monitorFactorsArr =new String[monitorFactorTemplateList.size()];
				monitorFactorsNameArr =new String[monitorFactorTemplateList.size()];
				for(int i=0;i<monitorFactorTemplateList.size();i++) {
					monitorFactorsArr[i] = monitorFactorTemplateList.get(i).getFactorCode();
					monitorFactorsNameArr[i] = monitorFactorTemplateList.get(i).getFactorName();
				}
			}
		}



		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
//		calendar.add(Calendar.HOUR_OF_DAY, -1);
		 calendar.add(Calendar.DAY_OF_WEEK, -1);
//		 calendar.add(Calendar.MONTH,-1);
		mav.addObject("defaultTime", date);
		mav.addObject("lastHour", calendar.getTime());
		mav.addObject("monitorFactorsArr", monitorFactorsArr);
		mav.addObject("monitorFactorsNameArr", monitorFactorsNameArr);
		return mav;
	}


	@RequestMapping(value = "/changeMonitorFactor", method = RequestMethod.POST)
	@ResponseBody
	public String changeMonitorFactor(HttpServletRequest request) throws Exception {
		String enterpriseId = request.getParameter("enterpriseId");
		String machineId = request.getParameter("machineId");
		Map<String,Object> hm = new HashMap<String,Object>();
		EntityWrapper<EnterpriseInfo> ew = new EntityWrapper<>();
		ew.setEntity(new EnterpriseInfo());
		if (StringUtils.isNotBlank(enterpriseId)) {
			ew.andNew();
			ew.eq("pe.id", enterpriseId);
		}

		List<EnterpriseInfo> enterpriseInfoList = this.enterpriseInfoService.selectRelationList(ew);

//		String monitorFactors ="";
		String[] monitorFactorsCodeArr =null;
		String[] monitorFactorsNameArr =null;
		BigDecimal[] dataAccuracyArr =null;
		int[] decimalDigitsArr =null;
		String[] monitorFactorsUnitArr =null;
		if(enterpriseInfoList.size()>0) {
			EntityWrapper<ControlMachine> deviceEw = new EntityWrapper<>();
			deviceEw.setEntity(new ControlMachine());
			deviceEw.andNew();
			deviceEw.eq("a.id", machineId);

				List<ControlMachine> machineList = this.controlMachineService.selectRelationList(deviceEw);

				System.out.println("---------");
				System.out.println(machineList.size());

				if(machineList.size()>0) {
					EntityWrapper<MonitorFactorTemplate> templateEw = new EntityWrapper<>();
					templateEw.setEntity(new MonitorFactorTemplate());
					templateEw.andNew();
					// templateEw.eq("a.device_code", condensingDeviceList.get(0).getDeviceCode());
					templateEw.eq("machine_id", machineList.get(0).getId());
					templateEw.andNew();
					templateEw.eq("type_id",1);
					templateEw.or();
					templateEw.eq("type_id",4);
					templateEw.orderBy("type_id");
					templateEw.orderBy("factor_code");
					List<MonitorFactorTemplate> monitorFactorTemplateList = monitorFactorTemplateService.selectList(templateEw);

					System.out.println(monitorFactorTemplateList.size());

					monitorFactorsCodeArr =new String[monitorFactorTemplateList.size()];
					monitorFactorsNameArr =new String[monitorFactorTemplateList.size()];
					dataAccuracyArr =new BigDecimal[monitorFactorTemplateList.size()];
					decimalDigitsArr =new int[monitorFactorTemplateList.size()];
					monitorFactorsUnitArr =new String[monitorFactorTemplateList.size()];
					for(int i=0;i<monitorFactorTemplateList.size();i++) {
						monitorFactorsCodeArr[i] = monitorFactorTemplateList.get(i).getFactorCode();
						monitorFactorsNameArr[i] = monitorFactorTemplateList.get(i).getFactorName();
						dataAccuracyArr[i] = monitorFactorTemplateList.get(i).getDataAccuracy();
						decimalDigitsArr[i] = monitorFactorTemplateList.get(i).getDecimalDigits();
						monitorFactorsUnitArr[i] = monitorFactorTemplateList.get(i).getFactorUnit();
					}

			}

		}
		hm.put("monitorFactorsCodeArr", monitorFactorsCodeArr);
		hm.put("monitorFactorsNameArr", monitorFactorsNameArr);
		hm.put("dataAccuracyArr", dataAccuracyArr);
		hm.put("decimalDigitsArr", decimalDigitsArr);
		hm.put("monitorFactorsUnitArr", monitorFactorsUnitArr);
		return JSON.toJSONString(hm);
	}

	@RequestMapping(value = "detailedStateChart")
	@ResponseBody
	public String historyRecordChart(HttpServletRequest request, String query_startTime, String query_endTime) {
		Map<String, Object> hm = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		String enterpriseId = request.getParameter("enterpriseId");
		String machineId = request.getParameter("machineId");
		String monitorFactors = request.getParameter("monitorFactors");
		String[] monitorFactorsArr = monitorFactors.split(",");
		String[] factorCodeArr = new String[monitorFactorsArr.length];
		String[] factorNameArr = new String[monitorFactorsArr.length];
		BigDecimal[] dataAccuracyArr = new BigDecimal[monitorFactorsArr.length];
		int[] decimalDigitsArr = new int[monitorFactorsArr.length];
		for(int i=0;i<monitorFactorsArr.length;i++) {
			String[] monitorFactorContent = monitorFactorsArr[i].split(";");
			if (monitorFactorContent.length < 2) {
				continue;
			}
			factorCodeArr[i]=monitorFactorContent[0];
			factorNameArr[i]=monitorFactorContent[1];
			dataAccuracyArr[i]=new BigDecimal(monitorFactorContent[2]);
			decimalDigitsArr[i]=Integer.valueOf(monitorFactorContent[3]);
		}
		hm.put("factorNameArr", factorNameArr);


		Map<String, Object> params = new HashMap<String, Object>();
		// 设备编码
		if (null != enterpriseId && !"".equals(enterpriseId)) {
			params.put("enterpriseId", enterpriseId);
		} else {
			params.put("enterpriseId", null);
		}

		// 设备编号
		if (null != machineId && !"".equals(machineId)) {
			params.put("machineId", machineId);
		} else {
			params.put("machineId", null);
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    long oneDayMsec = 86400000;

		List<String> rtdTableDateList = new ArrayList<String>();
		if (query_startTime != null && query_endTime != null) {
			params.put("startTime", query_startTime);
			params.put("endTime", query_endTime);
		} else {
			params.put("startTime", null);
			params.put("endTime", null);
		}

		params.put("sort", "asc");
		for (int j = 0; j < factorCodeArr.length; j++) {
			params.put("factorCode", factorCodeArr[j]);
			// 获取历史记录列表
			List<RtdHistory> rtdHistoryList = rtdHistoryService.getList(params);
			String[] monitorFactorValueArr = new String[rtdHistoryList.size()];
			String[] monitorFactorTimeArr = new String[rtdHistoryList.size()];
			for (int m = 0; m < rtdHistoryList.size(); m++) {
				String factorValue = rtdHistoryList.get(m).getFactorValue();
				monitorFactorValueArr[m] = String.valueOf(dataAccuracyArr[j].multiply(new BigDecimal(factorValue)).setScale(decimalDigitsArr[j],BigDecimal.ROUND_UP));
				monitorFactorTimeArr[m] = format.format(rtdHistoryList.get(m).getCollectTime());
			}
			if(rtdHistoryList.size()>0) {
				hm.put("time", monitorFactorTimeArr);
			}
			list.add(monitorFactorValueArr);
		}
		hm.put("value", list);

		return JSON.toJSONString(hm);
	}


	@RequestMapping(value = "/detailedStateList")
	public ModelAndView detailedStateList(HttpServletRequest request, String query_startTime, String query_endTime) {
		ModelAndView mav = new ModelAndView(detailedStateListJsp);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<String[]> returnValueList = new ArrayList<String[]>();
			String enterpriseId = request.getParameter("enterpriseId");
			String machineId = request.getParameter("machineId");
			if(enterpriseId.equals("")) {
				String pageNo = request.getParameter("pageNo");
				PageBean pageBean = null;
				pageBean = PageUtil.page(pageNo, PageBean.DEFAULT_PAGE_SIZE + "", 0, PageBean.DEFAULT_PAGE_SIZE);
				mav.addObject("pagingBean", pageBean);
				mav.addObject("returnValueList", returnValueList);
				return mav;
			}
			String monitorFactors = request.getParameter("monitorFactors");
			String[] monitorFactorsArr = monitorFactors.split(",");
			String[] factorCodeArr = new String[monitorFactorsArr.length];
			String[] factorNameArr = new String[monitorFactorsArr.length];
			BigDecimal[] dataAccuracyArr = new BigDecimal[monitorFactorsArr.length];
			int[] decimalDigitsArr = new int[monitorFactorsArr.length];
			for(int i=0;i<monitorFactorsArr.length;i++) {
				String[] monitorFactorContent = monitorFactorsArr[i].split(";");
				if (monitorFactorContent.length < 4) {
					continue;
				}
				factorCodeArr[i]=monitorFactorContent[0];
				//如果长度为4，则表示单位为空，为5则不为空
				if(monitorFactorContent.length==4) {
					factorNameArr[i]=monitorFactorContent[1];
				}
	            if(monitorFactorContent.length==5) {
	            	if(monitorFactorContent[4].equals("-")) {
	            		factorNameArr[i]=monitorFactorContent[1];
	            	}else {
	            		factorNameArr[i]=monitorFactorContent[1]+"<br>"+monitorFactorContent[4];
	            	}

				}
				dataAccuracyArr[i]=new BigDecimal(monitorFactorContent[2]);
				decimalDigitsArr[i]=Integer.valueOf(monitorFactorContent[3]);
			}

			mav.addObject("factorNameArr", factorNameArr);

//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// 设备编码
			if (null != enterpriseId && !"".equals(enterpriseId)) {
				params.put("enterpriseId", enterpriseId);
			} else {
				params.put("enterpriseId", null);
			}

			// 设备编号
			if (null != machineId && !"".equals(machineId)) {
				params.put("machineId", machineId);
			} else {
				params.put("machineId", null);
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			if (query_startTime != null && query_endTime != null) {
				params.put("startTime", query_startTime);
				params.put("endTime", query_endTime);
			} else {
				params.put("startTime", null);
				params.put("endTime", null);
			}

			params.put("factorCode",factorCodeArr[0]);
//			params.put("factorCodeList",factorCodeList);

			params.put("sort", "desc");


			// 分页操作,因layui的表格分页不适用于这部分，所以采取这种分页
			String pageNo = request.getParameter("pageNo");
			PageBean pageBean = null;
			// 获取分页查询总记录数
			List<RtdHistory> countList = rtdHistoryService.getList(params);
			int count = countList.size();
			pageBean = PageUtil.page(pageNo, PageBean.DEFAULT_PAGE_SIZE + "", count, PageBean.DEFAULT_PAGE_SIZE);
			params.put("pageStart", pageBean.getStart());
			params.put("pageEnd", pageBean.getLimit());


			if(count>10) {
				count=10;
			}
			for(int i=0;i<count;i++) {
				String[] monitorFactorValueArr = new String[factorCodeArr.length+1];
				returnValueList.add(monitorFactorValueArr);
			}

			for (int j = 0; j < factorCodeArr.length; j++) {
				params.put("factorCode",factorCodeArr[j]);
				List<RtdHistory> rtdHistoryList = rtdHistoryService.getList(params);
				for (int m = 0; m < rtdHistoryList.size(); m++) {
					String factorValue = rtdHistoryList.get(m).getFactorValue();
					returnValueList.get(m)[j+1] = String.valueOf(dataAccuracyArr[j].multiply(new BigDecimal(factorValue)).setScale(decimalDigitsArr[j],BigDecimal.ROUND_UP));
					returnValueList.get(m)[0]= format.format(rtdHistoryList.get(m).getCollectTime());
				}


			}

			mav.addObject("pagingBean", pageBean);
			mav.addObject("returnValueList", returnValueList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("详细状态"+ e.getMessage());
		}

		return mav;
	}


	@RequestMapping(value = "/exportDetailedStateList")
	@ResponseBody
	public String exportDetailedStateList(HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		 Map<String, Object> hm = new HashMap<String, Object>();
		List<Map<String, String>> returnValueList = new ArrayList<Map<String, String>>();
		String deviceCode = request.getParameter("deviceCode");
 		String machineId = request.getParameter("machineId");
		String monitorFactors = request.getParameter("monitorFactors");
		String query_startTime = request.getParameter("query_startTime");
		String query_endTime = request.getParameter("query_endTime");

	    LinkedHashMap<String,String> headerMap = new LinkedHashMap<String,String>();
		String[] monitorFactorsArr = monitorFactors.split(",");
		String[] factorCodeArr = new String[monitorFactorsArr.length];
		String[] factorNameArr = new String[monitorFactorsArr.length];
		BigDecimal[] dataAccuracyArr = new BigDecimal[monitorFactorsArr.length];
		int[] decimalDigitsArr = new int[monitorFactorsArr.length];
		for(int i=0;i<monitorFactorsArr.length;i++) {
			if(i==0) {
				headerMap.put("collectTime", "采集时间");
			}
			String[] monitorFactorContent = monitorFactorsArr[i].split(";");
			factorCodeArr[i]=monitorFactorContent[0];
			factorNameArr[i]=monitorFactorContent[1];
			dataAccuracyArr[i]=new BigDecimal(monitorFactorContent[2]);
			decimalDigitsArr[i]=Integer.valueOf(monitorFactorContent[3]);
			headerMap.put(monitorFactorContent[0], monitorFactorContent[1]);
		}

		// 设备编码
		if (null != deviceCode && !"".equals(deviceCode)) {
			params.put("deviceCode", deviceCode);
		} else {
			params.put("deviceCode", null);
		}

		// 设备编号
		if (null != machineId && !"".equals(machineId)) {
			params.put("machineId", machineId);
		} else {
			params.put("machineId", null);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (query_startTime != null && query_endTime != null) {
			params.put("startTime", query_startTime);
			params.put("endTime", query_endTime);
		} else {
			params.put("startTime", null);
			params.put("endTime", null);
		}

	    params.put("sort", "desc");

		params.put("factorCode",factorCodeArr[0]);
		// 获取总记录数
		int count = rtdHistoryService.getCount(params);

		for(int i=0;i<count;i++) {
//			 Map<String, String> map = new HashMap<String, String>();
			 Map map = new LinkedHashMap<String, String>();
			returnValueList.add(map);
		}

		for (int j = 0; j < factorCodeArr.length; j++) {
			params.put("factorCode",factorCodeArr[j]);
			List<RtdHistory> rtdHistoryList = rtdHistoryService.getList(params);
			for (int m = 0; m < rtdHistoryList.size(); m++) {
				returnValueList.get(m).put("collectTime", sdf.format(rtdHistoryList.get(m).getCollectTime()));
				String factorValue = rtdHistoryList.get(m).getFactorValue();
				returnValueList.get(m).put(factorCodeArr[j], String.valueOf(dataAccuracyArr[j].multiply(new BigDecimal(factorValue)).setScale(decimalDigitsArr[j],BigDecimal.ROUND_UP)));
			}

		}

		//实体类导出时才有用
	     String fileds[] = new String[] { "id", "deviceCode","machineId","factorCode","factorValue","collectTime" };
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
