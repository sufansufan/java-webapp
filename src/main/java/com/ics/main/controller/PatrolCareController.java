package com.ics.main.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ics.system.model.SysAuthority;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysAuthorityService;
import com.ics.utils.CommonUtil;


/**
 * 巡查管护
 * @author jjz
 *
 */
@Controller
@RequestMapping(value ="/patrolCare")
public class PatrolCareController {
	
	protected static final String indexJsp="views/patrolCare/index";
	
	@Autowired
	private SysAuthorityService sysAuthorityService;
	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, String authId, String point, String nodeId) {
		ModelAndView mav = new ModelAndView(indexJsp);
		SysUser loginUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		
		List<SysAuthority> authList = this.sysAuthorityService.getListByPidAndUser(null, loginUser);
		mav.addObject("authList", authList);
		Map<String, List<SysAuthority>> authMap = authList.stream()
				.collect(Collectors.groupingBy(SysAuthority::getAuthorityParentId));
		mav.addObject("authMap", authMap);
		mav.addObject("authId", authId);
		mav.addObject("point", point);
		mav.addObject("nodeId", nodeId);
		return mav;
	}
}
