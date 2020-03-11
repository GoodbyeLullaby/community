package com.ustc.community.controller;

import com.ustc.community.dto.CommentDTO;
import com.ustc.community.enums.CommentTypeEnum;
import com.ustc.community.exception.CustomizeErrorCode;
import com.ustc.community.service.CommentService;
import com.ustc.community.dto.CommentCreateDTO;
import com.ustc.community.dto.ResultDTO;
import com.ustc.community.model.Comment;
import com.ustc.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
	public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
					   HttpServletRequest request){

		User user = (User)request.getSession().getAttribute("user");
		if(user==null){
			System.out.println(CustomizeErrorCode.NO_LOGIN.getMessage());
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}
		if(commentCreateDTO==null||StringUtils.isBlank(commentCreateDTO.getContent())){
			return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
		}

		Comment comment = new Comment();
		comment.setParentId(commentCreateDTO.getParentId());
		comment.setContent(commentCreateDTO.getContent());
		comment.setType(commentCreateDTO.getType());

		comment.setCommentator(25l);
		comment.setLikeCount(0L);
		comment.setCommentCount(0);
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(comment.getGmtCreate());
		commentService.insert(comment,user);

		return ResultDTO.okOf();
	}
	@ResponseBody
	@RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
	public ResultDTO<List> comments(@PathVariable(name = "id") Long id){
		List<CommentDTO> commentDTOS=commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
		return ResultDTO.okOf(commentDTOS);
	}
}
