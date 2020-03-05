package com.ustc.community.dto;

import lombok.Data;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/23
 */
@Data
public class GithubUser {
	private String name;
	private Long id;
	private String bio;
	private String avatarUrl;
}
