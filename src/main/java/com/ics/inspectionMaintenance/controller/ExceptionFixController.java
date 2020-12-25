package com.ics.inspectionMaintenance.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.inspectionMaintenance.model.InspectionMaintainItemResult;
import com.ics.inspectionMaintenance.model.ResultSummary;
import com.ics.inspectionMaintenance.model.Task;
import com.ics.inspectionMaintenance.service.ExceptionService;
import com.ics.inspectionMaintenance.service.InspectionMaintainItemResultService;
import com.ics.inspectionMaintenance.service.TaskService;
import com.ics.utils.CommonUtil;
import com.ics.utils.ConstantProperty;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 点检结果
 *
 * @author jjz
 *
 */
@Controller
@RequestMapping("/inspectionMaintenance/exceptionFix")
public class ExceptionFixController {

}
