package com.ics.inspectionMaintenance.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.ics.dataDesources.model.ControlMachine;
import com.ics.dataDesources.model.ImCalendarData;
import com.ics.dataDesources.model.InspectionMaintainTemplate;
import com.ics.dataDesources.service.ControlMachineService;
import com.ics.dataDesources.service.InspectionMaintainScheduleService;
import com.ics.dataDesources.service.InspectionMaintainTemplateService;
import com.ics.inspectionMaintenance.model.ImDetail;
import com.ics.inspectionMaintenance.model.ImResultForSchedule;
import com.ics.inspectionMaintenance.model.ScheduleExcel;
import com.ics.inspectionMaintenance.service.IMService;
import com.ics.utils.JsonResult;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/inspectionMaintenance/im")
public class IMController {

    @Autowired
    private IMService imService;

    @Autowired
    private InspectionMaintainScheduleService scheduleService;
    @Autowired
    private InspectionMaintainTemplateService templateService;

    @Autowired
    private ControlMachineService controlMachineService;

    @ResponseBody
    @RequestMapping(value = "/imDetail", method = RequestMethod.GET)
    public JsonResult getIMResultTempList(HttpServletRequest request, String taskId) {
        JsonResult result = new JsonResult();
        try {
            ImDetail imDetail = imService.buildImDetail(taskId);
            result.setData(imDetail);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFaild();
        }
        return result;
    }


    /**********************************download  doc start *******************************************************/


    @ResponseBody
    @RequestMapping(value = "/downloadIMReport", method = RequestMethod.GET)
    public void downloadIMReport(HttpServletResponse response, String taskId) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Content-Type", "application/msword");
        response.setHeader("Content-Disposition", "attachment;filename=exportWord.docx");

        InputStream fin = null;
        File file;
        ServletOutputStream out = null;
        try {
            Map<String, Object> dataMap = getWordData(imService.buildImDetail(taskId));
            String tempFtlPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("checkTemplate.ftl")).getPath();
            file = this.createDocFile(tempFtlPath, dataMap, 1);

            fin = new FileInputStream(file);
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
            //删除临时文件
            file.delete();
        } catch (Exception e) {
            throw e;
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
        }

    }

    public Map<String, Object> getWordData(ImDetail imDetail) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> dataMap = new HashMap<>();
        //设备名称
        dataMap.put("checkName", imDetail.getControlMachine().getMachineName());
        dataMap.put("checkDate", sdf.format(imDetail.getTask().getOperateDate()));
        dataMap.put("checkClass", imDetail.getTask().getInspectionMaintainTemplate().getTemplateTypeName());
        dataMap.put("execer", imDetail.getTask().getOperatorUserName());
        dataMap.put("qrr", imDetail.getTask().getConfirmUserName());

        List<Map<String, Object>> list = new ArrayList<>();
        imDetail.getTask().getInspectionMaintainTemplate().getClassList().forEach(classObj -> {
            classObj.getItemList().forEach(itemObj -> {
                Map<String, Object> item = new HashMap<>();
                item.put("className", classObj.getClassName());
                item.put("itemNo", itemObj.getIndexNum());
                item.put("checkItem", itemObj.getName());
                item.put("checkMethod", itemObj.getMethod());
                item.put("checkResult", "success".equals(itemObj.getItemResult().getResult()) ? "通过" : "不通过");
                item.put("picList", new ArrayList<Map<String, Object>>());
                list.add(item);
            });
        });
        dataMap.put("itemList", list);
        return dataMap;
    }

    public File createDocFile(String templateFilePath, Map<String, Object> dataMap, int loadType) throws Exception {
        Template t = null;
        File outFile;
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        try {
            templateFilePath = pathReplace(templateFilePath);
            String ftlPath = templateFilePath.substring(0, templateFilePath.lastIndexOf("/"));
            if (loadType == 1) {
                configuration.setDirectoryForTemplateLoading(new File(ftlPath)); // FTL文件所存在的位置
            } else {
                configuration.setClassForTemplateLoading(this.getClass(), ftlPath);//以类加载的方式查找模版文件路径
            }

            String ftlFile = templateFilePath.substring(templateFilePath.lastIndexOf("/") + 1);
            t = configuration.getTemplate(ftlFile); // 模板文件名

            String name = "temp" + (int) (Math.random() * 100000) + ".docx";
            outFile = new File(name);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));

            t.process(dataMap, out);
        } catch (Exception e) {
            throw e;
        }

        return outFile;
    }

    private String pathReplace(String path) {
        while (path != null && path.contains("\\")) {
            path = path.replace("\\", "/");
        }
        return path;
    }

    /**********************************download   doc  end *******************************************************/

    /**********************************download excel start*******************************************************/
    @ResponseBody
    @RequestMapping(value = "/downloadIMSchedule", method = RequestMethod.GET)
    public void downloadIMSchedule(HttpServletResponse response, String templateId, String machineId, String maxDate, String minDate) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=schedule.xlsx");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ImCalendarData imCalendarData = new ImCalendarData();
        imCalendarData.setMachineId(machineId);
        imCalendarData.setTemplateId(templateId);
        Map<String, Object> dateMap = resetDate(minDate);
        imCalendarData.setMaxDate((Date) dateMap.get("maxDate"));
        imCalendarData.setMinDate((Date) dateMap.get("minDate"));
        Map<String, ImResultForSchedule> scheduleMap = scheduleService.getSchedules(imCalendarData);
        ControlMachine controlMachine = controlMachineService.selectMachineById(machineId);
        InspectionMaintainTemplate template = templateService.selectById(templateId);
        List<ScheduleExcel> scheduleExcelList = new ArrayList<>();
        scheduleMap.forEach((key, value) -> {
            ScheduleExcel scheduleExcel = new ScheduleExcel(key, value.isHasSchedule() ? "已排班" : "休", "");
            scheduleExcelList.add(scheduleExcel);
        });
        this.createExcelFile(response, scheduleExcelList, controlMachine, template, dateMap.get("month").toString());
    }

    /**
     * @Description 根据最小的日期获取下一个月的月初与月末
     * 如果传的是2020-09-29 返回2020-10-01，2020-10-31
     * 如果传的是2020-11-01 返回2020-11-01，2020-11-30
     * @Date 2020/11/12 21:56
     * @Author by yuankeyan
     */
    private static Map<String, Object> resetDate(String minDateStr) throws ParseException {
        Map<String, Object> result = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy年MM月");
        Date minDate = format.parse(minDateStr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(minDate);
        while (cal.get(Calendar.DATE) != 1) {
            cal.add(Calendar.DATE, 1);
        }
        result.put("minDate", cal.getTime());
        result.put("month", formatMonth.format(cal.getTime()));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        result.put("maxDate", cal.getTime());
        return result;
    }

    private void createExcelFile(HttpServletResponse response, List<ScheduleExcel> list,
                                 ControlMachine controlMachine, InspectionMaintainTemplate template, String month) throws IOException {
        String fileName = "temp" + (int) (Math.random() * 100000) + ".xlsx";
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(response.getOutputStream(), ScheduleExcel.class)
                // 这里放入动态头
                .head(head(controlMachine, template, month)).registerWriteHandler(horizontalCellStyleStrategy).sheet("排班表")
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(list);
    }

    private List<List<String>> head(ControlMachine controlMachine, InspectionMaintainTemplate template, String month) {
        String headerOne = controlMachine.getMachineName() + "  设备" + template.getTemplateTypeName() + "排班表";
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add(headerOne);
        head0.add("管理部门：" + controlMachine.getOrgName());
        head0.add("日期");
        List<String> head1 = new ArrayList<String>();
        head1.add(headerOne);
        head1.add("班组：" + controlMachine.getTeamName());
        head1.add("排班情况");
        List<String> head2 = new ArrayList<String>();
        head2.add(headerOne);
        head2.add("月度：" + month);
        head2.add("备注");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }


    /**********************************download excel  end *******************************************************/
}
