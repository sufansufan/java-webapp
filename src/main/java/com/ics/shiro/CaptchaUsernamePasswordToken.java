package com.ics.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;  

/**
 * 自定义token类，增加验证码属性
 * @author yi
 *
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {  
	private static final long serialVersionUID = 6824265428283579222L;
	
	//是否开启验证码验证
    private boolean open;  
    //验证码 
    private String captcha;  
    
    public CaptchaUsernamePasswordToken(String username, String password,  
            boolean rememberMe, String host, String captcha, boolean open) {  
    	
        super(username, password != null ? password.toCharArray() : null, rememberMe, host);  
        this.captcha = captcha;  
        this.open = open;
    }
  
    public String getCaptcha() {  
        return captcha;  
    }  
  
    public void setCaptcha(String captcha) {  
        this.captcha = captcha;  
    }

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}  