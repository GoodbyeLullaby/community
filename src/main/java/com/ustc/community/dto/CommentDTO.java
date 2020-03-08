package com.ustc.community.dto;

import com.ustc.community.model.User;
import lombok.Data;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/6
 */
@Data
public class CommentDTO {
	Long id;
	private Long parentId;
	private Integer type;
	private Long commentator;
	private Long gmtCreate;
	private Long gmtModified;
	private Long likeCount;
	private Integer commentCount;
	private String content;
	private User user;
}
