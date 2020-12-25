package com.ics.system.controller;

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
 * <p>Title: SystemController</p>
 * <p>Description: 系统管理控制器</p>
 * @author yi
 * @date 2017年10月25日 下午1:02:54
 */
@Controller
@RequestMapping(value ="/system/threeMenu1")
public class ThreeMenu1Controller {
	
	protected static final String indexJsp="views/system/threeMenu1/index";
	
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
		mav.addObject("point", point);
		mav.addObject("nodeId", nodeId);
		return mav;
	}
}
