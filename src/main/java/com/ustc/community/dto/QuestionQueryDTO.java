package com.ustc.community.dto;

import lombok.Data;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/10
 */
@Data
public class QuestionQueryDTO {
	private String search;
	private Integer page;
	private Integer size;
}
