package com.ustc.community.exception;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/4
 */

public class CustomizeException extends RuntimeException{
	private String message;
	private Integer code;

	public CustomizeException(ICustomizeErrorCode errorCode) {
		this.code=errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	@Override
	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}
}
