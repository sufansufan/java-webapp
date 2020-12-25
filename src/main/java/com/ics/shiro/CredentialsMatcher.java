package com.ics.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.ics.utils.ConstantProperty;
import com.ics.utils.security.SM3;

/**
 * 加密匹配器
 * @author admin
 *
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {

		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;  
		
		//密码登录
		String salt = ConstantProperty.userSalt;
        Object tokenCredentials = SM3.hash(String.valueOf(token.getPassword())+salt);  
        Object accountCredentials = getCredentials(info);  
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false  
        return equals(tokenCredentials, accountCredentials);  
		
	}
}