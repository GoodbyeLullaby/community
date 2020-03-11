package com.ustc.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/8
 */
@Data
public class TagDTO {
	private String categoryName;
	private List<String> tags;
}
