package com.ustc.community.controller;

import com.ustc.community.exception.CustomizeErrorCode;
import com.ustc.community.service.CommentService;
import com.ustc.community.dto.CommentDTO;
import com.ustc.community.dto.ResultDTO;
import com.ustc.community.mapper.CommentMapper;
import com.ustc.community.model.Comment;
import com.ustc.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/5
 */
@Controller
public class CommentController {

	@Resource
	private CommentService commentService;
	@ResponseBody
	@RequestMapping(value = "/comment",method = RequestMethod.POST)
	public Object post(@RequestBody CommentDTO commentDTO,
					   HttpServletRequest request){

		User user = (User)request.getSession().getAttribute("user");
//		if(user==null){
//			//System.out.println(CustomizeErrorCode.NO_LOGIN.getMessage());
//			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
//		}

		Comment comment = new Comment();
		comment.setParentId(commentDTO.getParentId());
		comment.setContent(commentDTO.getContent());
		comment.setType(commentDTO.getType());

		comment.setCommentator(25l);
		comment.setLikeCount(0L);
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(comment.getGmtCreate());
		commentService.insert(comment);

		return ResultDTO.okOf();
	}
}
