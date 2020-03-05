package com.ustc.community.dto;

import lombok.Data;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/5
 */
@Data
public class CommentDTO {
	private Long parentId;
	private String content;
	private Integer type;
}
