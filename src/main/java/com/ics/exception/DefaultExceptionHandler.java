package com.ics.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.HostUnauthorizedException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

public class DefaultExceptionHandler implements HandlerExceptionResolver {

	protected static Logger log = Logger.getLogger(DefaultExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = new ModelAndView();
		/* 使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常 */
		FastJsonJsonView view = new FastJsonJsonView();
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("success", false);
		attributes.put("code", "1000001");
		attributes.put("status", 401);
		attributes.put("msg", "发生未知错误");
		if (ex instanceof AuthorizationException || ex instanceof HostUnauthorizedException
				|| ex instanceof UnauthorizedException || ex instanceof UnauthenticatedException) {
			// 权限访问异常
			attributes.put("code", "1000002");
			attributes.put("msg", "无操作权限");
		}
		view.setAttributesMap(attributes);
		mv.setView(view);
		log.error("异常:" + ex.getMessage(), ex);
		return mv;
	}
}