package com.ustc.community.controller;

import com.ustc.community.service.QuestionService;
import com.ustc.community.dto.QuestionDTO;
import com.ustc.community.mapper.QuestionMapper;
import com.ustc.community.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/3
 */
@Controller
public class QuestionController {
	@Resource
	private QuestionService questionService;

	@GetMapping("/question/{id}")
	public String question(@PathVariable(name = "id")Long id,
						   Model model){
		QuestionDTO question=questionService.getById(id);
		//累加阅读数
		questionService.incView(id);
		model.addAttribute("question", question);
		return "question";
	}
}
