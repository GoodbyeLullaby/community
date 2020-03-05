package com.ustc.community.dto;

import com.ustc.community.model.User;
import lombok.Data;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/4
 */

@Data
public class QuestionDTO {
	private Long id;
	private String title;
	private String description;
	private String tag;
	private Long gmtCreate;
	private Long gmtModified;
	private Long creator;
	private Integer viewCount;
	private Integer commentCount;
	private Integer likeCount;
	private User user;
}
