package com.ics.shiro;

import java.util.*;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.ics.exception.CaptchaException;
import com.ics.exception.UserNoRoleException;
import com.ics.exception.UserStateException;
import com.ics.system.model.SysOrg;
import com.ics.system.model.SysRole;
import com.ics.system.model.SysUser;
import com.ics.system.service.SysOrgService;
import com.ics.system.service.SysRoleService;
import com.ics.system.service.SysUserService;
import com.ics.utils.ApplicationUtil;
import com.ics.utils.PropertiesUtils;

public class MyRealm extends AuthorizingRealm {

	/**
	 * 登录成功授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户名

		SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		if(sysUser.getIssupermanager()) {
			// 超级管理员，添加所有角色、添加所有权限
			authorizationInfo.addRole("*");
			authorizationInfo.addRole("admin");
			authorizationInfo.addStringPermission("*");
		}else {
			
			// 进行授权角色
			SysRole role = sysUser.getSysrole();
			//添加角色
			authorizationInfo.addRole(role.getRoleCode());
			//添加权限
			Set<String> permissions = new HashSet<String>();
			List<String> _plist = new ArrayList<>(Arrays.asList(role.getAuthorityCode().split(":")));
			permissions.addAll(_plist);
			authorizationInfo.setStringPermissions(permissions);
		}
		
		return authorizationInfo;
	}

	/**
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		CaptchaUsernamePasswordToken _uToken = (CaptchaUsernamePasswordToken) token;
		
		SysUser validUser = null;
		SysUserService sysUserService =  ApplicationUtil.getBean("sysUserService");
		//密码登录
		String userCode = (String) _uToken.getPrincipal();
		
		//验证码
		Session session = getSession();  
		boolean open = _uToken.isOpen();
		String code = (String) session.getAttribute("validCode");
		if (open && (_uToken.getCaptcha() == null || !_uToken.getCaptcha().equalsIgnoreCase(code))){  
            throw new CaptchaException();  
        }  
		String source = Objects.isNull(session.getAttribute("source"))?"":session.getAttribute("source").toString();
		// 超管验证
		if ("admin".equals(userCode)) {
			SysUser user = new SysUser();
			user.setUserName(userCode);
			user.setUserCode(userCode);
			user.setIssupermanager(true);
			
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user,
					PropertiesUtils.getPara("super_pwd", true), getName());
			return authcInfo;
		} else {
			// 根据用户名查找用户信息
			validUser = sysUserService.getByUserCode(userCode);
		}
			
		if (validUser != null) {
			
			//判断用户状态是否被禁用
			if(validUser.getStatus()!=1){//1：正常；2：禁止登录平台；3：禁止操作终端
				throw new UserStateException();  
			}
			
			if (StringUtils.isNotBlank(validUser.getRoleId())) {

				SysRoleService sysRoleService =  ApplicationUtil.getBean("sysRoleService");
				SysRole role = sysRoleService.selectById(validUser.getRoleId());

				if("APP".equals(source)){
					AuthenticationInfo authcInfo = getAuthenticationInfo(validUser, role);
					return authcInfo;
				}

				if (role == null || StringUtils.isBlank(role.getAuthorityCode())) {
					throw new UserNoRoleException();
				} else {
					AuthenticationInfo authcInfo = getAuthenticationInfo(validUser, role);
					return authcInfo;
				}
			} else {
				throw new UserNoRoleException();
			}
			
		} else {
			return null;
		}
		

	}

	private AuthenticationInfo getAuthenticationInfo(SysUser validUser, SysRole role) {
		validUser.setIssupermanager(false);
		validUser.setSysrole(role);
		SysOrgService sysOrgService = ApplicationUtil.getBean("sysOrgService");
		SysOrg org = sysOrgService.selectById(validUser.getOrgId());
		if (org != null) {
			validUser.setOrgName(org.getOrgName());
		}

		return new SimpleAuthenticationInfo(validUser, validUser.getUserPassword(), getName());
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		// 重写shiro的密码验证，使用自定义验证
		setCredentialsMatcher(new CredentialsMatcher());

	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	private Session getSession(){  
        try{  
            Subject subject = SecurityUtils.getSubject();  
            Session session = subject.getSession(false);  
            if (session == null){  
                session = subject.getSession();  
            }  
            if (session != null){  
                return session;  
            }  
        }catch (InvalidSessionException e){  
              
        }  
        return null;  
    }  
}
