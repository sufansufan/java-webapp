package com.ics.inspectionMaintenance.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.inspectionMaintenance.mapper.TaskMapper;
import com.ics.inspectionMaintenance.model.Exception;
import com.ics.inspectionMaintenance.model.ExceptionDetail;
import com.ics.inspectionMaintenance.model.Task;
import com.ics.inspectionMaintenance.service.ExceptionService;
import com.ics.inspectionMaintenance.service.TaskService;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysUserService;
import com.ics.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 点检结果
 *
 * @author jjz
 */
@Controller
@RequestMapping("/inspectionMaintenance/exception")
public class ExceptionController {

    protected static final String detailJsp = "views/inspectionMaintenance/abnormal/detail";
    protected static final String addJsp = "views/inspectionMaintenance/abnormal/add";
    protected static final String addDeviceJsp = "views/inspectionMaintenance/abnormal/addDevice";

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ControlMachineService controlMachineService;

    @ResponseBody
    @RequestMapping(value = "/getExceptionDetail", method = RequestMethod.GET)
    public PagingBean getExceptionDetail(HttpServletRequest request, String taskId, String taskStatus) {
        PagingBean result = new PagingBean();
        ExceptionDetail exceptionTaskList = exceptionService.getExceptionDetail(taskId);
        result.setData(exceptionTaskList);

        return result;
    }

    /**
     * @Description 跳转异常详情页面
     * @Date 2020/11/8 17:11
     * @Author by yuankeyan
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(HttpServletRequest request, String taskId) {
        ModelAndView mav = new ModelAndView(detailJsp);
        mav.addObject("taskId", taskId);
        return mav;
    }

    /**
     * @Description 跳转选择设备
     * @Date 2020/11/8 23:23
     * @Author by yuankeyan
     */
    @RequestMapping(value = "/addDevice", method = RequestMethod.GET)
    public ModelAndView addDeviceJsp(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(addDeviceJsp);
        return mav;
    }

    /**
     * @Description 跳转异常添加页面
     * @Date 2020/11/8 17:11
     * @Author by yuankeyan
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(addJsp);
        mav.addObject("upload_url", ConstantProperty.exception_url);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteException", method = RequestMethod.POST)
    public JsonResult getExceptionDetail(HttpServletRequest request, String taskId) {

        Task task = taskService.selectById(taskId);
        task.setIsAvailable(0);
        taskService.updateById(task);

        return new JsonResult();
    }

    @ResponseBody
    @RequestMapping(value = "/addException", method = RequestMethod.POST)
    public JsonResult addException(HttpServletRequest request, HttpSession session, String machineId, String createdTime, String desc, String exceptionImgUrls) {

        Date createTime = DateUtils.StringToDate(createdTime, "yyyy-MM-dd HH:mm:ss");
        Exception exception = new Exception();
        exception.setId(CommonUtil.getRandomUUID());
        exception.setMachineId(machineId);
        exception.setDescribe(desc);
        exception.setExceptionImgUrls(exceptionImgUrls);
        exception.setExceptionType(ConstantProperty.TASK_TYPE_BREAK_OUT_EXCEPTION);
        exception.setCreatedTime(createTime);
        String userCode = session.getAttribute("userCode").toString();
        if (!"admin".equals(userCode)) {
            SysUser byUserCode = sysUserService.getByUserCode(userCode);
            exception.setCheckoutUserId(byUserCode.getId());
        } else {
            exception.setCheckoutUserId("admin");
        }

        exceptionService.insert(exception);

        Task task = new Task();
        task.setId(CommonUtil.getRandomUUID());
        task.setSourceId(exception.getId());
        task.setMachineId(machineId);
        task.setType(ConstantProperty.TASK_TYPE_BREAK_OUT_EXCEPTION);
        task.setStartTime(new Date());
        task.setStatus("1");
        task.setIsAvailable(ConstantProperty.IS_AVAILABLE_1);
        taskService.insert(task);

        JsonResult result = new JsonResult();
        result.setData(exception);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getExceptionList", method = RequestMethod.GET)
    public PagingBean getExceptionList(HttpServletRequest request, int limit, int page) {
        Page<Task> pager = new Page<>(page, limit);
        EntityWrapper<Task> ew = new EntityWrapper<>();
        ew.eq("t.is_available", 1);
        List<Task> tasks = taskMapper.selectExceptionTaskByMachineId(pager, ew);

        if (tasks.isEmpty()) {
            return new PagingBean();
        }
        taskService.fillTaskEmptyFields(tasks);
        List<String> machineIds = tasks.stream().map(Task::getMachineId).collect(Collectors.toList());
        List<ControlMachine> controlMachines = controlMachineService.selectBatchIds(machineIds);
        controlMachineService.fillMachineEmptyField(controlMachines);

        Map<String, ControlMachine> machineMap = controlMachines.stream().collect(Collectors.toMap(ControlMachine::getId, a -> a, (k1, k2) -> k1));

        tasks.stream().forEach(task -> {
            ControlMachine machine = machineMap.get(task.getMachineId());
            task.setControlMachine(machine);
        });
        pager.setRecords(tasks);
        PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
        pageBean.setData(pager.getRecords());
        return pageBean;
    }
}
