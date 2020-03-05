package com.ustc.community.exception;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/4
 */

public enum CustomizeErrorCode implements ICustomizeErrorCode{
	QUESTION_NOT_FOUND(2001,"你找的问题不在了"),
	TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或者评论进行回复"),
	NO_LOGIN(2003,"当前操作需要登录，请登陆后重试"),
	SYS_ERROR(2004,"服务器冒烟了，请稍候重试"),
	TYPE_PARAM_WRONG(2005,"评论类型错误，或不存在"),
	COMMENT_NOT_FOUND(2006,"你找的评论不在了"),
	;

	private Integer code;
	private String message;



	@Override
	public String getMessage(){
		return message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	CustomizeErrorCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
