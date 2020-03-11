package com.ustc.community.controller;

import com.ustc.community.dto.NotificationDTO;
import com.ustc.community.enums.NotificationTypeEnunm;
import com.ustc.community.mapper.NotificationMapper;
import com.ustc.community.model.Notification;
import com.ustc.community.model.User;
import com.ustc.community.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/9
 */
@Controller
public class NotificationController {
	@Resource
	private NotificationService notificationService;

	@GetMapping("/notification/{id}")
	public String getNotification(HttpServletRequest request,
								  @PathVariable("id") Long id){
		User user = (User)request.getSession().getAttribute("user");
		if(user==null){
			return "redirect:/";
		}
		NotificationDTO notificationDTO=notificationService.read(id,user);
		if(NotificationTypeEnunm.REPLY_COMMENT.getType()==notificationDTO.getType()
				||NotificationTypeEnunm.REPLY_QUESTION.getType()==notificationDTO.getType()){
			return "redirect:/question/"+notificationDTO.getOuterId();
		}else {
			return "redirect:/";
		}
	}

}
