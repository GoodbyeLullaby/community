package com.ustc.community.controller;

import com.ustc.community.dto.CommentDTO;
import com.ustc.community.enums.CommentTypeEnum;
import com.ustc.community.model.Question;
import com.ustc.community.service.CommentService;
import com.ustc.community.service.QuestionService;
import com.ustc.community.dto.QuestionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/3
 */
@Controller
public class QuestionController {
	@Resource
	private QuestionService questionService;

	@Resource
	private CommentService commentService;

	@GetMapping("/question/{id}")
	public String question(@PathVariable(name = "id")Long id,
						   Model model){
		QuestionDTO questionDTO=questionService.getById(id);
		List<QuestionDTO> relatedQuestions=questionService.selectRelated(questionDTO);
		List<CommentDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

		//累加阅读数
		questionService.incView(id);
		model.addAttribute("question", questionDTO);
		model.addAttribute("comments", comments);
		model.addAttribute("relatedQuestions",relatedQuestions);
		return "question";
	}
}
