package com.ustc.community.controller;

import com.ustc.community.cache.TagCache;
import com.ustc.community.service.QuestionService;
import com.ustc.community.dto.QuestionDTO;
import com.ustc.community.mapper.QuestionMapper;
import com.ustc.community.model.Question;
import com.ustc.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/26
 */
@Controller
public class PublishController {

	@Resource
	private QuestionService questionService;
	@GetMapping("/publish/{id}")
	public String edit(@PathVariable(name = "id")Long id,
					   Model model){
		QuestionDTO question = questionService.getById(id);
		model.addAttribute("tittle", question.getTitle());
		model.addAttribute("description", question.getDescription());
		model.addAttribute("tag", question.getTag());
		model.addAttribute("id", question.getId());
		model.addAttribute("tags", TagCache.get());
		return "publish";
	}

	@GetMapping("/publish")
	public String publish(Model model){
		model.addAttribute("tags", TagCache.get());
		return "publish";
	}

	@PostMapping("/publish")
	public String doPublish(
			@RequestParam(value = "title",required = false) String title,
			@RequestParam(value = "description",required = false) String description,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "id",required = false) Long id,
			HttpServletRequest request,
			Model model
			){
		model.addAttribute("tittle", title);
		model.addAttribute("description", description);
		model.addAttribute("tag", tag);
		model.addAttribute("tags", TagCache.get());

		if(title==null||title.equals("")){
			model.addAttribute("error", "标题不能为空");
			return "publish";
		}
		if(description==null||description.equals("")){
			model.addAttribute("error", "内容不能为空");
			return "publish";
		}
		if(tag==null||tag.equals("")){
			model.addAttribute("error", "标签不能为空");
			return "publish";
		}

		String invalid = TagCache.filterInvalid(tag);
		if(StringUtils.isNotBlank(invalid)){
			model.addAttribute("error", "输入非法标签："+invalid);
			return "publish";
		}

		//检查用户是否登陆
		User user= (User) request.getSession().getAttribute("user");
		if(user==null){
			model.addAttribute("error","用户未登录");
			return "publish";
		}
//		if(id==null){
//			model.addAttribute("error","id为空");
//			return "publish";
//		}
		Question question=new Question();
		question.setId(id);
		question.setTitle(title);
		question.setDescription(description);
		question.setTag(tag);
		question.setCreator(user.getId());
		question.setGmtModified(System.currentTimeMillis());

		questionService.createOrUpdate(question);
		return "redirect:/profile/questions";
	}

}
