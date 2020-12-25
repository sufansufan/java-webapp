package com.ics.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: CookieUtil</p>
 * <p>Description: Cookie工具类</p>
 * @author yi
 * @data 2018年1月3日 下午8:43:43
 */
public class CookieUtil {

	/**
	 * 登录用户cookie名称
	 */
	public static String mCookieLoginUser ="loginInfo";
	
	/**
	 * 密码错误次数cookie
	 */
	public static String mCookieLoginErrCount ="ec";
	
	/**
	 * 获取登录注册用户的信息
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookieInfo(HttpServletRequest request, String key, String defaultValue)
	{
		String result = defaultValue;
		Cookie[] cookies = request.getCookies();
	    for(Cookie cookie : cookies){
	        if(cookie.getName().equals(key)){
	        	result = cookie.getValue();
	        	break;
	        }
	     }
	    return result;
	}
	
	/**
	 * 保存登录用户的信息
	 * @param response
	 * @param key
	 * @param info
	 */
	public static void setCookieInfo(HttpServletResponse response, String key,String info, Integer expiry)
	{
		Cookie userCookie=new Cookie(key,info); 
		userCookie.setMaxAge(expiry);//存活期为一个月 30*24*60*60
        userCookie.setPath("/");
        response.addCookie(userCookie); 
	}
	
	public static void addLoginErrCount(HttpServletRequest request, HttpServletResponse response) {
        setCookieInfo(response, CookieUtil.mCookieLoginErrCount, (Integer.parseInt(CookieUtil.getCookieInfo(request, CookieUtil.mCookieLoginErrCount,"0"))+1)+"", 4*60*60);
	}
	public static void delLoginErrCount(HttpServletResponse response) {
		setCookieInfo(response, CookieUtil.mCookieLoginErrCount, null, 0);
	}
}
