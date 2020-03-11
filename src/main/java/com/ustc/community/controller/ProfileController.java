package com.ustc.community.controller;

import com.ustc.community.dto.NotificationDTO;
import com.ustc.community.model.Notification;
import com.ustc.community.service.NotificationService;
import com.ustc.community.service.QuestionService;
import com.ustc.community.dto.Pagination;
import com.ustc.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/2
 */
@Controller
public class ProfileController {

	@Resource
	private QuestionService questionService;
	@Resource
	private NotificationService notificationService;
	@GetMapping("/profile/{action}")
	public String profile(@PathVariable(name = "action") String action,
						  Model model,
						  HttpServletRequest request,
						  @RequestParam(value = "page", defaultValue = "1") Integer page,
						  @RequestParam(value = "size", defaultValue = "10") Integer size){

		User user= (User) request.getSession().getAttribute("user");
		if(user==null){
			return "redirect:/";
		}
		if("questions".equals(action)){

			Pagination pagination = questionService.listByUserId(user.getId(),page,size);
//			System.out.println(pagination.getQuestions());
//			System.out.println(pagination);
			model.addAttribute("pagination",pagination);

			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "我的问题");

		}else if("replies".equals(action)){
			Pagination pagination = notificationService.listByUserId(user.getId(),page,size);
			model.addAttribute("pagination",pagination);
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "最新回复");
		}

		return "profile";
	}
}
