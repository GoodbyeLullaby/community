package com.ustc.community.enums;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/8
 */

public enum NotificationStatusEnunm {
	UNREAD(0)
	,READ(1);
	private int status;

	NotificationStatusEnunm(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
