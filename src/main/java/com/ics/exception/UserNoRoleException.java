package com.ics.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义异常-用户未配置角色异常
 * @author admin
 *
 */
public class UserNoRoleException extends AuthenticationException {

	private static final long serialVersionUID = 1689161884262506140L;

	public UserNoRoleException() {
        super();
    }
    public UserNoRoleException(String message) {
        super(message);
    }

    public UserNoRoleException(Throwable cause) {
        super(cause);
    }

    public UserNoRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
