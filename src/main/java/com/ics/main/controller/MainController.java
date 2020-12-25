package com.ics.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ics.system.model.SysRole;
import com.ics.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ics.dataDesources.model.EnterpriseInfo;
import com.ics.exception.CaptchaException;
import com.ics.exception.UserNoRoleException;
import com.ics.exception.UserStateException;
import com.ics.shiro.CaptchaUsernamePasswordToken;
import com.ics.system.model.SysAuthority;
import com.ics.system.model.SysLog;
import com.ics.system.model.SysUser;
import com.ics.utils.CommonUtil;
import com.ics.utils.CookieUtil;
import com.ics.utils.JsonResult;
import com.ics.utils.PagingBean;
import com.ics.utils.PropertiesUtils;

/**
 * <p>
 * Title: MainController
 * </p>
 * <p>
 * Description: 主页控制器
 * </p>
 * 
 * @author yi
 * @date 2017年10月25日 下午2:47:09
 */
@Controller
public class MainController {

	protected static Logger logger = Logger.getLogger(MainController.class);

	protected static final String indexJsp = "/index";
	protected static final String IESettingJsp = "/common/IESetting";
	protected static final String loginJsp = "/login";
	protected static final String finggerLoginJsp = "/finggerLogin";
	protected static final String faceLoginJsp = "/faceLogin";
	protected static final String IEActiveXSettingJsp = "/common/IEActiveXSetting";
	protected static final String printerSettingJsp="/common/printerInstallSetting";

	@Autowired
	private SysAuthorityService sysAuthorityService;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * @Title: index
	 * @Description: 主页
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

//		String company_name = this.sysConfigService.selectValueByKey("copyright_company_name");
//		mav.addObject("company_name", company_name);
//		mav.addObject("version", PropertiesUtils.getPara("version", true));
		Subject subject = SecurityUtils.getSubject();

		if (subject.isAuthenticated()) {
			mav.setViewName(indexJsp);
			SysUser loginUser = (SysUser) subject.getPrincipals().getPrimaryPrincipal();
			mav.addObject("loginUser", loginUser);
			
			//获取一级菜单
			List<SysAuthority> authList = this.sysAuthorityService.getListByPidAndUser("0", loginUser);
			mav.addObject("authList", authList);
//			Map<String, List<SysAuthority>> authMap = authList.stream()
//					.collect(Collectors.groupingBy(SysAuthority::getAuthorityParentId));
//			mav.addObject("authMap", authMap);
			
//			List<SysAuthority> authList = this.sysAuthorityService.getListByPidAndUser("0", loginUser);
//			Map<String, List<SysAuthority>> authMap = authList.stream()
//					.collect(Collectors.groupingBy(SysAuthority::getAuthorityParentId));
//			mav.addObject("authMap", authMap);
			
		} else {
			mav.setViewName(loginJsp);
		}

		return mav;
	}


	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param userName
	 * @param passWord
	 * @param isValid
	 * @param validCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JsonResult login(HttpServletRequest request, HttpServletResponse response, HttpSession session, String userName, String passWord,
			boolean isValid, String validCode, boolean rememberMe, String captcha) {
//		SecurityUtils.getSubject().getSession().setTimeout(-1000L);

		// JsonResult
		JsonResult result = new JsonResult();
		//登入操作记录
		SysLog sysLog=new SysLog();
		sysLog.setId(CommonUtil.getRandomUUID());
		sysLog.setCreateTime(new Date());
		sysLog.setLogType(1);
		try {
			InetAddress addr = InetAddress.getLocalHost();
			sysLog.setUserIp(addr.getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		sysLog.setUserIp(CommonUtil.getLocalIpAddr());
		sysLog.setUserId(userName);
		sysLog.setUserName(userName);
		String logDetail="";
		Subject subject = SecurityUtils.getSubject();
		
		CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(userName, passWord, false, null, captcha,
				isValid);
		try {
			subject.login(token);
			CookieUtil.delLoginErrCount(response);
			
			if(!userName.equals("admin")) {
				EntityWrapper<SysUser> ew = new EntityWrapper<>();
				ew.setEntity(new SysUser());
				ew.andNew();
				ew.eq("userCode", userName);
				List<SysUser> userList = sysUserService.selectList(ew);
				Date deadLine = userList.get(0).getDeadline();

				Date date = new Date();
				//判断使用期限是否小于当前时间
				if(deadLine.getTime()<date.getTime()) {
					result.setFaildMsg("该账号使用期限已过，请联系管理员");
					return result;
				}
			}
		
			if (rememberMe) { // 1表示用户勾选了记住密码
				String loginInfo = userName + ":" + passWord + ":" + rememberMe;
				CookieUtil.setCookieInfo(response, CookieUtil.mCookieLoginUser, loginInfo, 30 * 24 * 60 * 60);
			} else {
				CookieUtil.setCookieInfo(response, CookieUtil.mCookieLoginUser, null, 0);
			}
			logDetail="【"+userName+"】登录管理平台；操作结果：操作成功";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
			session.setAttribute("userCode", userName);

		} catch (IncorrectCredentialsException e) {
			result.setFaildMsg("用户名或密码错误");
			logDetail="【"+userName+"】登录管理平台；操作结果：用户名或密码错误";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
			CookieUtil.addLoginErrCount(request, response);
		} catch (UnknownAccountException e) {
			result.setFaildMsg("用户名或密码错误");
			logDetail="【"+userName+"】登录管理平台；操作结果：用户名或密码错误";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
			CookieUtil.addLoginErrCount(request, response);
		} catch (ExcessiveAttemptsException e) {
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误,登录失败次数过多";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
			result.setFaildMsg("登录失败次数过多");
		} catch (CaptchaException e) {
			result.setFaildMsg("验证码错误");
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误,验证码错误";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
		} catch (UserStateException e) {
			result.setFaildMsg("用户被禁止登录平台,请联系管理员");
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误,用户被禁止登录平台";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
		} catch (UserNoRoleException e) {
			result.setFaildMsg("用户未配置角色权限,请联系管理员");
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误,用户未配置角色权限";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFaildMsg("登录错误");
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
		}

		return result;
	}


	/**
	 * 登录
	 *
	 * @param request
	 * @param response
	 * @param userName
	 * @param passWord
	 * @param isValid
	 * @param validCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/login", method = RequestMethod.POST)
	public JsonResult appLogin(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, String userName, String passWord,
							   boolean isValid, String validCode, boolean rememberMe, String captcha) {
//		SecurityUtils.getSubject().getSession().setTimeout(-1000L);

		// JsonResult
		JsonResult result = new JsonResult();
		//登入操作记录
		SysLog sysLog=new SysLog();
		sysLog.setId(CommonUtil.getRandomUUID());
		sysLog.setCreateTime(new Date());
		sysLog.setLogType(1);
		try {
			InetAddress addr = InetAddress.getLocalHost();
			sysLog.setUserIp(addr.getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		sysLog.setUserIp(CommonUtil.getLocalIpAddr());
		sysLog.setUserId(userName);
		sysLog.setUserName(userName);
		String logDetail="";
		Subject subject = SecurityUtils.getSubject();

		CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(userName, passWord, false, null, captcha,
				isValid);
		httpSession.setAttribute("source","APP");
		try {
			subject.login(token);
			CookieUtil.delLoginErrCount(response);

			if(!userName.equals("admin")) {
				EntityWrapper<SysUser> ew = new EntityWrapper<>();
				ew.setEntity(new SysUser());
				ew.andNew();
				ew.eq("user_code", userName);
				List<SysUser> userList = sysUserService.selectList(ew);
				SysUser user = userList.get(0);
				Date deadLine =user.getDeadline();
				Date date = new Date();
				//判断使用期限是否小于当前时间
				if(deadLine != null && (deadLine.getTime()<date.getTime())) {
					result.setFaildMsg("该账号使用期限已过，请联系管理员");
					return result;
				}

				EntityWrapper<SysRole> ew1 = new EntityWrapper<>();
				ew1.setEntity(new SysRole());
				ew1.eq("t.user_code", user.getUserCode());
				SysRole role = sysRoleService.selectUserRole(ew1);
				String roleCode = role.getRoleCode();

				if(!("999".equals(roleCode) || "998".equals(roleCode) || "997".equals(roleCode))){
					result.setFaildMsg("该账号无使用权限，请联系管理员");
					return result;
				}
				Map<String, String> userInfo = new HashMap<>();
				userInfo.put("userCode",user.getUserCode());
				userInfo.put("roleCode",roleCode);
				result.setData(userInfo);
				httpSession.setAttribute("userCode",user.getUserCode());
				httpSession.setAttribute("roleCode",roleCode);
			}

			if (rememberMe) { // 1表示用户勾选了记住密码
				String loginInfo = userName + ":" + passWord + ":" + rememberMe;
				CookieUtil.setCookieInfo(response, CookieUtil.mCookieLoginUser, loginInfo, 30 * 24 * 60 * 60);
			} else {
				CookieUtil.setCookieInfo(response, CookieUtil.mCookieLoginUser, null, 0);
			}
			logDetail="【"+userName+"】登录管理平台；操作结果：操作成功";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);

		} catch (IncorrectCredentialsException e) {
			result.setFaildMsg("用户名或密码错误");
			logDetail="【"+userName+"】登录管理平台；操作结果：用户名或密码错误";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
			CookieUtil.addLoginErrCount(request, response);
		} catch (UnknownAccountException e) {
			result.setFaildMsg("用户名或密码错误");
			logDetail="【"+userName+"】登录管理平台；操作结果：用户名或密码错误";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
			CookieUtil.addLoginErrCount(request, response);
		} catch (ExcessiveAttemptsException e) {
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误,登录失败次数过多";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
			result.setFaildMsg("登录失败次数过多");
		} catch (CaptchaException e) {
			result.setFaildMsg("验证码错误");
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误,验证码错误";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
		} catch (UserStateException e) {
			result.setFaildMsg("用户被禁止登录平台,请联系管理员");
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误,用户被禁止登录平台";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
		} catch (UserNoRoleException e) {
			result.setFaildMsg("用户未配置角色权限,请联系管理员");
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误,用户未配置角色权限";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFaildMsg("登录错误");
			logDetail="【"+userName+"】登录管理平台；操作结果：登录错误";
			sysLog.setLogDetail(logDetail);
			sysLogService.insert(sysLog);
		}

		return result;
	}

	/**
	 * 用户退出
	 * 
	 * @param request
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public JsonResult logout(HttpServletRequest request) {
		JsonResult result = new JsonResult();
		//登出操作记录
		SysUser sysUser = (SysUser) CommonUtil.getLoginUserInfo(request);
		SysLog sysLog=new SysLog();
		sysLog.setId(CommonUtil.getRandomUUID());
		sysLog.setCreateTime(new Date());
		sysLog.setLogType(1);
		try {
			InetAddress addr = InetAddress.getLocalHost();
			sysLog.setUserIp(addr.getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		sysLog.setUserIp(CommonUtil.getLocalIpAddr());
		sysLog.setUserId(sysUser.getUserCode());
		sysLog.setUserName(sysUser.getUserCode());
		String logDetail="";
		
		logDetail="【"+sysUser.getUserCode()+"】退出管理平台";
		sysLog.setLogDetail(logDetail);
		sysLogService.insert(sysLog);
		
		// 注销
		CommonUtil.setLoginUserInfo(request, null);
		request.getSession().invalidate();
		
		return result;
	}

	
	/**
	 * 通用下载
	 * @param request
	 * @param response
	 * @param realName
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/download")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response, String realName)
			throws UnsupportedEncodingException {
		String fileNameStr = request.getParameter("fileName");
		String fileNameUtf = null;
		String fileName = null;

		String agent = (String) request.getHeader("USER-AGENT").toLowerCase();
		
		String brower = getBrowserName(agent);
		
		if (StringUtils.isNotBlank(realName) && agent != null && "firefox".equals(brower)) {
			realName = java.net.URLDecoder.decode(realName, "UTF-8");
			realName = new String(realName.getBytes("UTF-8"), "iso-8859-1");
		}

		try {
			fileNameUtf = java.net.URLDecoder.decode(fileNameStr, "UTF-8");
			fileName = fileNameUtf.substring(fileNameUtf.lastIndexOf("/") + 1);
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			
			if("firefox".equals(brower)) {
				fileName = new String(fileNameUtf.getBytes("GB2312"), "ISO_8859_1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String savePath;
		savePath = request.getSession().getServletContext().getRealPath("/") + "/";
		response.setContentType("multipart/form-data charset=UTF-8");

		response.setHeader("Content-Disposition", "attachment;fileName="
				+ (StringUtils.isBlank(realName) ? fileName.split("/")[fileName.split("/").length - 1] : realName));
		ServletOutputStream out;

		File file = new File(savePath + fileNameUtf);

		try {
			FileInputStream inputStream = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			inputStream.close();
			out.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getBrowserName(String agent) {
		if (agent.indexOf("msie 7") > 0) {
			return "ie7";
		} else if (agent.indexOf("msie 8") > 0) {
			return "ie8";
		} else if (agent.indexOf("msie 9") > 0) {
			return "ie9";
		} else if (agent.indexOf("msie 10") > 0) {
			return "ie10";
		} else if (agent.indexOf("msie") > 0) {
			return "ie";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("firefox") > 0) {
			return "firefox";
		} else if (agent.indexOf("webkit") > 0) {
			return "webkit";
		} else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
			return "ie11";
		} else {
			return "Others";
		}
	}
	
	/**
	 * 通用上传方法
	 * 
	 * @param request
	 * @param type
	 *            指定保存路径
	 * @param retain
	 *            是否保留原文件名
	 * @param specifiedName
	 *            指定文件名
	 * @param size
	 *            文件大小限制 KB
	 * @param noSameName
	 *            是否允许不同名文件 默认false
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public JsonResult upload(HttpServletRequest request, String type, boolean retain, String specifiedName,
			Integer size, boolean noSameName) throws IllegalStateException, IOException {
		// JsonResult
		JsonResult result = new JsonResult();

		Map<String, Object> data = new HashMap<>();

		// 文件保存目录相对路径
		String basePath = type + "/";

		String webPath = request.getSession().getServletContext().getRealPath("/");
		String savePath = null;

		savePath =  basePath;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String sdate = simpleDateFormat.format(new Date());
		int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		String str = sdate + rannum;

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String fName = "";
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				long length = file.getSize();
				File saveDirFile = new File(savePath);
				if (!saveDirFile.exists()) {
					saveDirFile.mkdirs();
				}
				if (file != null) {
					if (size == null || (size > 0 && length <= (size * 1024))) {
						String originalFileName = file.getOriginalFilename();

						if (originalFileName.trim() != "") {
							String nString[] = originalFileName.split("\\.");
							String ext = nString[nString.length - 1];

							String fileName = "";
							if (retain) {
								// 保留原文件名
								fileName = originalFileName;
							} else if (StringUtils.isNotBlank(specifiedName)) {
								// 指定文件名
								fileName = specifiedName + "." + ext;
							} else {
								// 随机文件名
								fileName = str + "." + ext;
							}

							File localFile = new File(savePath, fileName);

							if (noSameName) {
								if (!localFile.exists()) {
									file.transferTo(localFile);
									fName = fileName;
									data.put("fileSize", length);
								} else {
									result.setFaildMsg("已存在同名文件!");
								}
							} else {
								file.transferTo(localFile);
								fName = fileName;
								data.put("fileSize", length);
							}

						}
					} else {
						result.setFaildMsg("文件不能超过" + size / 1024 + "MB");
					}

				}
			}

		}

		data.put("fileName", fName);
		data.put("fullFileName", basePath + fName);

		result.setData(data);

		return result;
	}
	
}
