package com.ustc.community.enums;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/8
 */

public enum NotificationTypeEnunm {
	REPLY_QUESTION(1,"回复了问题"),
	REPLY_COMMENT(2,"回复了评论")
	;
	private int type;
	private String name;

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	NotificationTypeEnunm(int status, String name) {
		this.type = status;
		this.name = name;
	}

	public static String nameOfType(int type){
		for (NotificationTypeEnunm notificationTypeEnunm : NotificationTypeEnunm.values()) {
			if(notificationTypeEnunm.getType()==type){
				return notificationTypeEnunm.getName();
			}
		}
		return "";
	}
}
