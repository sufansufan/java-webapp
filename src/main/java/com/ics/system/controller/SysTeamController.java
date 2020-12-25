package com.ics.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.system.model.SysTeam;
import com.ics.system.model.SysTeamMember;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysTeamMemberService;
import com.ics.system.service.SysTeamService;
import com.ics.system.service.SysUserService;
import com.ics.utils.CommonUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

// import netscape.javascript.JSObject;

/**
 * 班组管理
 *
 * @author suxiangrong
 * @date 2020/10/19
 */
@Controller
@RequestMapping("/system/sysTeam")
public class SysTeamController {
    protected static final String indexJsp = "views/system/sysTeam/index";
    protected static final String addJsp = "views/system/sysTeam/add";
    protected static final String addUserJsp = "views/system/sysTeam/addUser";
    @Autowired
    private SysTeamService sysTeamService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysTeamMemberService sysTeamMemberService;

    @RequiresPermissions(value = "sys_team")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(indexJsp);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResult list(HttpServletRequest request, String userName, String selectNodeId) {
        JsonResult jsonResult = new JsonResult();
        EntityWrapper<SysTeamMember> ew = new EntityWrapper<>();
        ew.setEntity(new SysTeamMember());
        if (StringUtils.isNotBlank(userName)) {
            ew.orNew();
            ew.like("t2.user_name", userName);
            ew.or();
            ew.like("t2.cellphone", userName);
        }
        if (StringUtils.isNotBlank(selectNodeId)) {
            ew.andNew();
            ew.eq("t1.team_id", selectNodeId);
        }
        List<SysTeamMember> list = sysTeamMemberService.selectTeamMemberList(ew);
        jsonResult.setData(list);
        return jsonResult;
    }

    /**
     * 初始化组员table,查询所有角色为保养组长或保养组员的用户
     *
     * @param request
     * @return com.ics.utils.PagingBean
     * @author suxiangrong
     * @date 2020/10/18
     */
    @ResponseBody
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public PagingBean userList(HttpServletRequest request, int page, int limit, String userName) {
        Page<SysUser> pager = new Page<>(page, limit);
        EntityWrapper<SysUser> ew = new EntityWrapper<>();
        ew.setEntity(new SysUser());
        if (StringUtils.isNotBlank(userName)) {
            ew.like("t2.user_name", userName);
        }
        pager = sysUserService.selectTeamUserByRoleName(pager, ew);
        PagingBean pageBean = PagingBean.page(page, limit, pager.getTotal());
        pageBean.setData(pager.getRecords());
        return pageBean;
    }

    /**
     * 增加班组下的组员
     *
     * @param request
     * @param userIds
     * @param teamId
     * @return com.ics.utils.JsonResult
     * @author suxiangrong
     * @date 2020/10/18
     */
    @ResponseBody
    @RequiresPermissions(value = "sys_team_add")
    @RequestMapping(value = "/sysTeamUserSave", method = RequestMethod.POST)
    public JsonResult sysTeamUserSave(HttpServletRequest request, String userIds, String teamId) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isNotBlank(userIds) && StringUtils.isNotBlank(teamId)) {
            boolean flag = sysTeamMemberService.addSysTeamMember(userIds, teamId);
            if (!flag) {
                jsonResult.setFaild();
            }
        }
        return jsonResult;
    }

    /**
     * 删除班组下的组员，支持批量删除
     *
     * @param request
     * @param ids
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = "sys_team_del")
    @RequestMapping(value = "/member_del", method = RequestMethod.POST)
    public JsonResult member_del(HttpServletRequest request, String ids) {
        JsonResult jsonResult = new JsonResult();
        boolean flag = false;
        if (StringUtils.isNotBlank(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            flag = sysTeamMemberService.deleteBatchIds(idList);
        }

        if (!flag && jsonResult.getMsg().equals("操作成功")) {
            jsonResult.setFaild();
        }
        return jsonResult;
    }


    @RequiresPermissions(value = "sys_team_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request, String id) {
        ModelAndView mav = new ModelAndView(addJsp);
        EntityWrapper<SysTeam> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new SysTeam());
        wrapper.eq("t1.id", id);
        List<SysTeam> list = this.sysTeamService.selectTeamList(wrapper);
        SysTeam model = new SysTeam();
        if (!list.isEmpty()) {
            model = list.get(0);
        }
        mav.addObject("model", model);
        return mav;
    }

    @RequiresPermissions(value = "sys_team_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request, String pId) {
        ModelAndView mav = new ModelAndView(addJsp);
        mav.addObject("parentId", pId);
        return mav;
    }

    /**
     * 新增班组保存
     *
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = "sys_team_add")
    @RequestMapping(value = "/addSave", method = RequestMethod.POST)
    public JsonResult addSave(HttpServletRequest request, SysTeam model, String teamId) {

        JsonResult jsonResult = new JsonResult();

        model.setId(CommonUtil.getRandomUUID());
        model.setCreateTime(new Date());

        EntityWrapper<SysTeam> wrapper = new EntityWrapper<>();
        SysTeam st = new SysTeam();
        wrapper.setEntity(st);
        wrapper.eq("id", teamId);
        st = sysTeamService.selectOne(wrapper);

        String oIdPath;
        if (Objects.isNull(st)) {

            //orgIdPath增加自身id
            oIdPath = model.getId();
        /*String oIdPath = model.getTeamIdPath();
        if(StringUtils.isNotBlank(oIdPath)) {
            oIdPath = oIdPath.replaceAll(",", ":");
            oIdPath += ":";
        }
        oIdPath += model.getId();*/

        } else {
            oIdPath = st.getTeamIdPath() + ":" + model.getId();
            model.setParentId(st.getId());
        }
        model.setTeamIdPath(oIdPath);


        boolean flag = this.sysTeamService.insert(model);

        jsonResult.setData(model);
        if (!flag) {
            jsonResult.setFaild();
        }
        return jsonResult;
    }

    /**
     * 编辑班组保持
     *
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = "sys_team_edit")
    @RequestMapping(value = "/editSave", method = RequestMethod.POST)
    public JsonResult editSave(HttpServletRequest request, SysTeam model) {
        JsonResult jsonResult = new JsonResult();
        model.setModifyTime(new Date());
        boolean flag = this.sysTeamService.updateById(model);
        jsonResult.setData(model);
        if (!flag) {
            jsonResult.setFaild();
        }
        return jsonResult;
    }

    /**
     * 查询所有team
     *
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllTeams", method = RequestMethod.GET)
    public JsonResult getAllTeams(HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        EntityWrapper<SysTeam> wrapper = new EntityWrapper<>();
        List<SysTeam> sysTeams = sysTeamService.selectList(wrapper);
        jsonResult.setData(sysTeams);
        return jsonResult;
    }

    /**
     * 删除班组及班组下的所有组员，支持批量删除
     *
     * @param request
     * @param ids
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = "sys_team_del")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public JsonResult del(HttpServletRequest request, String ids, String names) {
        JsonResult jsonResult = new JsonResult();
        try {
            sysTeamService.deleteTeamAndMember(ids);
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            jsonResult.setFaild();
            jsonResult.setFaildMsg(e.getMessage());
        }
        return jsonResult;
    }

    @RequiresPermissions(value = "sys_team")
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public ModelAndView addUser(HttpServletRequest request, String teamId) {
        ModelAndView mav = new ModelAndView(addUserJsp);
        mav.addObject("teamId", teamId);
        return mav;
    }

    /**
     * 获取组织树
     *
     * @param request
     * @param showAll       是否显示所有节点
     * @param showUser      是否显示机构下用户
     * @param showUserIden  用户身份，用,分隔
     * @param addCustomNode 自定义节点，json串
     * @param showCabinet   是否显示机构下存管柜
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamTree", method = RequestMethod.POST)
    public JSONArray getOrgTree(HttpServletRequest request,
                                boolean showAll, boolean showUser, String showUserIden, String addCustomNode,
                                boolean showCabinet, boolean showCase) {
        SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);

        JSONArray jsonArray = new JSONArray();
        List<SysTeam> list = new ArrayList<>();
        if (showAll || loginUser.getIssupermanager()) {
            EntityWrapper<SysTeam> wrapper = new EntityWrapper<>();
            wrapper.setEntity(new SysTeam());
            wrapper.orderBy("t1.create_time", true);
            list = this.sysTeamService.selectTeamList(wrapper);
        }

        for (int i = 0; i < list.size(); i++) {
            SysTeam team = list.get(i);
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", team.getId());
            if (StringUtils.isBlank(team.getOrgName())) {
                jsonObject.put("name", team.getName());
            } else {
                jsonObject.put("name", team.getName() + "-" + team.getOrgName());
            }
            jsonObject.put("teamIdPath", team.getTeamIdPath());
            jsonObject.put("pId", StringUtils.isBlank(team.getParentId()) ? "0" : team.getParentId());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
