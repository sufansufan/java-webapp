package com.ics.inspectionMaintenance.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.system.model.SysLog;
import com.ics.utils.PagingBean;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author suxiangrong
 * @date 2020/10/20
 */
@Controller
@RequestMapping("/inspectionMaintenance/abnormal")
public class abnormalController {
    protected static final String indexJsp="views/inspectionMaintenance/abnormal/index";

    @RequiresPermissions(value = "abnormal")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(indexJsp);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public PagingBean list(HttpServletRequest request, int page, int limit,
                           String orgIds, String beginTimeStr, String endTimeStr ) {

        Page<SysLog> pager = new Page<>(page, limit);

        // 构造条件查询
       /* EntityWrapper<SysLog> ew = getParams(orgIds, beginTimeStr, endTimeStr);
        pager=this.sysLogService.selectPage(pager, ew);*/
        PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
        pageBean.setData(pager.getRecords());
        return pageBean;
    }
}
