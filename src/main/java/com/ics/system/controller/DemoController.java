package com.ics.system.controller;


import com.alibaba.fastjson.JSONObject;
import com.ics.utils.JsonFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author yi
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/appd")
public class DemoController {

	protected static final String indexJsp = "/views/system/sysAuthority/index";

	@Value(value="classpath:demo/app_task_toBeCompletedList.json")
	private Resource toBeCompletedList;

	@Value(value="classpath:demo/app_im_submit.json")
	private Resource submit;

	@Value(value="classpath:demo/app_im_getImTemp.json")
	private Resource getImTemp;

	@Value(value="classpath:demo/app_task_exceptionBeforeRecord.json")
	private Resource exceptionBeforeRecord;

	@Value(value="classpath:demo/app_task_rejectException.json")
	private Resource rejectException;

	@Value(value="classpath:demo/app_task_confirmException.json")
	private Resource confirmException;

	@Value(value="classpath:demo/app_task_handleException.json")
	private Resource handleException;

	@Value(value="classpath:demo/app_task_exceptionDetail.json")
	private Resource exceptionDetail;

	@Value(value="classpath:demo/app_task_allTask.json")
	private Resource allTask;

	@Value(value="classpath:demo/app_task_confirm.json")
	private Resource confirm;

	@Value(value="classpath:demo/app_index_kanbanTask.json")
	private Resource kanbanTask;

	@Value(value="classpath:demo/app_im_getBeforeImDetail.json")
	private Resource getBeforeImDetail;

	@ResponseBody
	@RequestMapping(value = "/im/getBeforeImDetail", method = RequestMethod.GET)
	public JSONObject getBeforeImDetail(HttpServletRequest request, String userCode, String roleCode) {
		return JsonFileUtils.jsonRead(getBeforeImDetail);
	}

	@ResponseBody
	@RequestMapping(value = "/index/kanbanTask", method = RequestMethod.GET)
	public JSONObject kanbanTask(HttpServletRequest request, String userCode, String roleCode) {
		return JsonFileUtils.jsonRead(kanbanTask);
	}


	@ResponseBody
	@RequestMapping(value = "/task/toBeCompletedList", method = RequestMethod.GET)
	public JSONObject toBeCompletedList(HttpServletRequest request, String userCode, String roleCode, String teamCode, String machineCode, String date) {
		return JsonFileUtils.jsonRead(toBeCompletedList);
	}


	@ResponseBody
	@RequestMapping(value = "/task/exceptionDetail", method = RequestMethod.GET)
	public JSONObject exceptionDetail(HttpServletRequest request, String userCode, String roleCode, String machineCode,String teamCode,String date, String taskId) {
		return JsonFileUtils.jsonRead(exceptionDetail);
	}


	@ResponseBody
	@RequestMapping(value = "/task/handleException", method = RequestMethod.POST)
	public JSONObject handleException(HttpServletRequest request, String userCode, String roleCode,String taskId,String handlerUserCode,String handlerDesc,String handlerImgs) {
		return JsonFileUtils.jsonRead(handleException);
	}


	@ResponseBody
	@RequestMapping(value = "/task/confirmException", method = RequestMethod.POST)
	public JSONObject confirmException(HttpServletRequest request, String userCode, String roleCode, String taskId) {
		return JsonFileUtils.jsonRead(confirmException);
	}


	@ResponseBody
	@RequestMapping(value = "/task/rejectException", method = RequestMethod.POST)
	public JSONObject rejectException(HttpServletRequest request, String userCode, String roleCode, String taskId, String rejectReason) {
		return JsonFileUtils.jsonRead(rejectException);
	}


	@ResponseBody
	@RequestMapping(value = "/task/exceptionBeforeRecord", method = RequestMethod.GET)
	public JSONObject exceptionBeforeRecord(HttpServletRequest request, String userCode, String roleCode, String taskId) {
		return JsonFileUtils.jsonRead(exceptionBeforeRecord);
	}

	@ResponseBody
	@RequestMapping(value = "/im/getImTemp", method = RequestMethod.GET)
	public JSONObject getImTemp(HttpServletRequest request, String userCode, String roleCode, String taskId) {
		return JsonFileUtils.jsonRead(getImTemp);
	}

	@ResponseBody
	@RequestMapping(value = "/im/submit", method = RequestMethod.POST)
	public JSONObject submit(HttpServletRequest request, String userCode, String roleCode,String taskId,String imResult) {
		return JsonFileUtils.jsonRead(submit);
	}


	@ResponseBody
	@RequestMapping(value = "/task/allTask", method = RequestMethod.GET)
	public JSONObject getImDetailList(HttpServletRequest request, String userCode, String roleCode,  String machineCode, String teamCode,
									  String date) {
		return JsonFileUtils.jsonRead(allTask);
	}

	@ResponseBody
	@RequestMapping(value = "/task/confirm", method = RequestMethod.POST)
	public JSONObject confirm(HttpServletRequest request, String userCode, String roleCode, String taskId) {
		return JsonFileUtils.jsonRead(confirm);
	}
}
