package com.ics.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class UserFormAuthenticationFilter extends FormAuthenticationFilter {

	protected static Logger logger = Logger.getLogger(UserFormAuthenticationFilter.class);

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				return executeLogin(request, response);
			} else {
				return true;
			}
		} else {
			HttpServletRequest httpRequest = WebUtils.toHttp(request);

			if (isAjax(httpRequest)) {

				HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
				httpServletResponse.sendError(401);

				return false;

			} else {
				saveRequestAndRedirectToLogin(request, response);
			}

			return false;
		}
	}

	/**
	 * 判断ajax请求
	 * 
	 * @param request
	 * @return
	 */
	boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	}
}