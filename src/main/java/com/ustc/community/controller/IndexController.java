package com.ustc.community.controller;

import com.ustc.community.service.QuestionService;
import com.ustc.community.dto.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/22
 */
@Controller
public class IndexController {
	@Resource
	private QuestionService questionService;
	@GetMapping("/")
	public String index(Model model,
						@RequestParam(value = "page", defaultValue = "1") Integer page,
						@RequestParam(value = "size", defaultValue = "5") Integer size){

		Pagination pagination = questionService.list(page, size);
		System.out.println(pagination.getQuestions());
		System.out.println(pagination);
		model.addAttribute("pagination",pagination);
		return "index";
	}
}
