package com.ustc.community.service;

import com.ustc.community.enums.CommentTypeEnum;
import com.ustc.community.exception.CustomizeErrorCode;
import com.ustc.community.exception.CustomizeException;
import com.ustc.community.mapper.CommentMapper;
import com.ustc.community.mapper.QuestionExtMapper;
import com.ustc.community.mapper.QuestionMapper;
import com.ustc.community.model.Comment;
import com.ustc.community.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/5
 */
@Service
public class CommentService {
	@Resource
	private CommentMapper commentMapper;
	@Resource
	private QuestionMapper questionMapper;
	@Resource
	private QuestionExtMapper questionExtMapper;
	@Transactional
	public void insert(Comment comment) {
		if(comment.getParentId()==null||comment.getParentId()==0){
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}
		if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}
		if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
			//回复评论
			Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
			if(parentComment==null){
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}

		}else{
			//回复问题
			Question parentQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
			if(parentQuestion==null){
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			parentQuestion.setCommentCount(1);
			questionExtMapper.incComment(parentQuestion);
		}
		commentMapper.insert(comment);

	}
}
