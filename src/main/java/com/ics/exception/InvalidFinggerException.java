package com.ics.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义异常-无效指纹异常
 * @author admin
 *
 */
public class InvalidFinggerException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public InvalidFinggerException() {
        super();
    }
    public InvalidFinggerException(String message) {
        super(message);
    }

    public InvalidFinggerException(Throwable cause) {
        super(cause);
    }

    public InvalidFinggerException(String message, Throwable cause) {
        super(message, cause);
    }
}
