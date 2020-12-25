package com.ics.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义异常-无效人脸异常
 * @author admin
 *
 */
public class InvalidFaceException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public InvalidFaceException() {
        super();
    }
    public InvalidFaceException(String message) {
        super(message);
    }

    public InvalidFaceException(Throwable cause) {
        super(cause);
    }

    public InvalidFaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
