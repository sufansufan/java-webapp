package com.ics.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义异常-验证码错误异常
 * @author admin
 *
 */
public class CaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public CaptchaException() {
        super();
    }
    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
