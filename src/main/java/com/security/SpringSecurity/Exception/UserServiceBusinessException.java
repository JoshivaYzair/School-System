
package com.security.SpringSecurity.Exception;

public class UserServiceBusinessException extends RuntimeException{
	public UserServiceBusinessException(String message) {
		super(message);
	}
}
