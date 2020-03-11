package com.ustc.community.dto;

import com.ustc.community.model.User;
import lombok.Data;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/8
 */
@Data
public class NotificationDTO {
	private Long id;
	private Long notifier;
	private String notifierName;
//	private Long receiver;
	private Long outerId;
	private Integer type;
	private Long gmtCreate;
	private Integer status;
	private String outerTitle;
	private String typeName;
}
