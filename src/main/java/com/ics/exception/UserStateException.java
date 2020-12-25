package com.ics.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义异常-用户状态异常
 * @author admin
 *
 */
public class UserStateException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public UserStateException() {
        super();
    }
    public UserStateException(String message) {
        super(message);
    }

    public UserStateException(Throwable cause) {
        super(cause);
    }

    public UserStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
