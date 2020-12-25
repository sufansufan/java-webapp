package com.ics.main.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysUserService;
import com.ics.utils.CommonUtil;
import com.ics.utils.DateUtils;
import com.ics.utils.EChartBean;
import com.ics.utils.JsonResult;


/**
 * <p>Title: WorkController</p>
 * <p>Description: 工作台控制器</p>
 * @author yi
 * @date 2017年10月25日 下午1:04:24
 */
@Controller
@RequestMapping(value ="/workbench")
public class WorkbenchController {
	
	protected static final String indexJsp="views/workbench/index";
	protected static final String infoJsp="views/workbench/info";
	
	protected static final String printQRParamJsp="views/workbench/printQRParam";
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresPermissions(value = "workbench")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(indexJsp);
		return mav;
	}
	
	/**
	 * 查询工作台统计参数
	 * @param request
	 * @param orgIds
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value = "/searchInfo", method = RequestMethod.GET)
	public ModelAndView searchInfo(HttpServletRequest request, String orgIds, String nodeId, String nodeName) {
		ModelAndView mav = new ModelAndView(infoJsp);
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		if(!loginUser.getIssupermanager()) {}
		mav.addObject("loginUser", loginUser);
		mav.addObject("nodeName", nodeName);
		return mav;
	}
	
	/**
	 * 获取柜数量
	 * @param request
	 * @param orgIds
	 * @return
	 */
	@RequestMapping(value = "/getCabientData", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getCabientData(HttpServletRequest request, String orgIds){
		JsonResult jsonResult = new JsonResult();
		
		Map<String, Object> dataMap = new HashMap<>();
		//查询分组下数量

		//演示数据
		dataMap.put("cabinetNum", new Random().nextInt(125));
		
		jsonResult.setData(dataMap);
		return jsonResult;
	}

	/**
	 * 获取用户数量
	 * @param request
	 * @param orgIds
	 * @return
	 */
	@RequestMapping(value = "/getUserData", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getUserData(HttpServletRequest request, String orgIds){
		JsonResult jsonResult = new JsonResult();
		int userNum=0;
		String[] orgIdArray = orgIds.split(",");
		//查询分组下用户数量
		EntityWrapper<SysUser> userEw = new EntityWrapper<>();
		userEw.setEntity(new SysUser());
		userEw.in("org_id", orgIdArray);
		userNum = this.sysUserService.selectCount(userEw);
		
		jsonResult.setData(userNum);
		return jsonResult;
	}
	
	/**
	 * 获取报警数量
	 * @param request
	 * @param orgIds
	 * @return
	 */
	@RequestMapping(value = "/getAlarmData", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getAlarmData(HttpServletRequest request, String orgIds){
		JsonResult jsonResult = new JsonResult();
		Map<String, Object> dataMap = new HashMap<>();
		//查询分组下报警数量
		dataMap.put("alarmNum", new Random().nextInt(68));
		jsonResult.setData(dataMap);
		return jsonResult;
	}
	
	/**
	 * 存管柜类型图
	 * @param request
	 * @param orgIds
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value = "/getCabinetModelChart", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getCabinetModelChart(HttpServletRequest request,String orgIds, String nodeId){
		JsonResult jsonResult = new JsonResult();
		
		EChartBean echart = new EChartBean();
		
		String[] orgIdArray = orgIds.split(",");
		
		//横坐标
		List<String> xAxiDataList = new ArrayList<>();
		
		Date now = new Date();
		Date fDate = DateUtils.add(now, Calendar.DATE, -4);
		
		while (now.after(fDate)) {
			fDate = DateUtils.add(fDate, Calendar.DATE, 1);
			xAxiDataList.add(DateUtils.DateToString(fDate, "MM-dd"));
		}
		
		//具体数值
		List<Integer> countist = new ArrayList<>();
		
		//生成动态演示数据
		if(xAxiDataList != null && xAxiDataList.size()>0) {
			Random ran = new Random();
			for (int i = 0; i < xAxiDataList.size(); i++) {
				countist.add(ran.nextInt(25));
			}
		}
		
		echart.setSeries(countist);
		echart.setxAxiDataList(xAxiDataList);
		
		jsonResult.setData(echart);
		return jsonResult;
	}
	
	/**
	 * 报警类型图
	 * @param request
	 * @param orgIds
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value = "/getAlarmTypeChart", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getAlarmTypeChart(HttpServletRequest request,String orgIds, String nodeId){
		JsonResult jsonResult = new JsonResult();
		
		EChartBean echart = new EChartBean();
		
		//横坐标
		List<String> xAxiDataList = new ArrayList<>();
		xAxiDataList.add("网络异常");
		xAxiDataList.add("温度异常");
		xAxiDataList.add("湿度异常");
		xAxiDataList.add("非法入侵");
		xAxiDataList.add("设备震动");
		xAxiDataList.add("电源异常");
		
		//具体数值
//		List<Integer> countist = new ArrayList<>();
		
		List<Map<String,Object>> countist = new ArrayList<>();
		
		if(xAxiDataList != null && xAxiDataList.size()>0) {
			Random ran = new Random();
			for (int i = 0; i < xAxiDataList.size(); i++) {
				String name = xAxiDataList.get(i);
				Map<String,Object> map = new HashMap<>();
				map.put("name", name);
				map.put("value", ran.nextInt(142));
				countist.add(map);
			}
		}
		
		echart.setSeries(countist);
		echart.setxAxiDataList(xAxiDataList);
		
		jsonResult.setData(echart);
		return jsonResult;
	}
	
}
