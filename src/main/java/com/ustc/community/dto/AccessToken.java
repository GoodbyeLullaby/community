package com.ustc.community.dto;

import lombok.Data;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/23
 */
@Data
public class AccessToken {
	private String client_id;
	private String client_secret;
	private String code;
	private String redirect_uri;
	private String state;
}
