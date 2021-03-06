package com.ustc.community.service;

import com.ustc.community.dto.CommentDTO;
import com.ustc.community.enums.CommentTypeEnum;
import com.ustc.community.enums.NotificationStatusEnunm;
import com.ustc.community.enums.NotificationTypeEnunm;
import com.ustc.community.exception.CustomizeErrorCode;
import com.ustc.community.exception.CustomizeException;
import com.ustc.community.mapper.*;
import com.ustc.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	@Resource
	private UserMapper userMapper;
	@Resource
	private CommentExtMapper commentExtMapper;
	@Resource
	private NotificationMapper notificationMapper;

	@Transactional
	public void insert(Comment comment, User commentator) {
		if(comment.getParentId()==null||comment.getParentId()==0){
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}
		if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}
		if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
			//回复评论
			Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());/*被回复的评论Id*/
			if(parentComment==null){
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}
			parentComment.setCommentCount(1);
			commentExtMapper.incCommentCount(parentComment);/*被回复的评论数++*/

			//查询你回复所在的question
			Question parentQuestion = questionMapper.selectByPrimaryKey(parentComment.getParentId());
			if(parentQuestion==null){
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}


			//创建通知
			createNotify(comment, parentComment.getCommentator(), commentator.getName(), parentQuestion.getTitle(), NotificationTypeEnunm.REPLY_COMMENT,parentQuestion.getId());

		}else{
			//回复问题
			Question parentQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
			if(parentQuestion==null){
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			parentQuestion.setCommentCount(1);
			questionExtMapper.incComment(parentQuestion);
			//创建通知
			createNotify(comment, parentQuestion.getCreator(),commentator.getName(),parentQuestion.getTitle() ,NotificationTypeEnunm.REPLY_QUESTION,parentQuestion.getId());

		}
		commentMapper.insert(comment);

	}

	private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnunm replyComment,Long outerId) {
		//评论 通知别人
		Notification notification = new Notification();
		notification.setGmtCreate(System.currentTimeMillis());
		notification.setOuterId(outerId);
		notification.setType(replyComment.getType());
		notification.setNotifier(comment.getCommentator());
		notification.setReceiver(receiver);
		notification.setNotifierName(notifierName);
		notification.setOuterTitle(outerTitle);
		notification.setStatus(NotificationStatusEnunm.UNREAD.getStatus());
		notificationMapper.insert(notification);
	}

	public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {

		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria()
				.andParentIdEqualTo(id)
				.andTypeEqualTo(type.getType());
		//查询所有的该问题的评论（不是评论的评论）
		List<Comment> comments = commentMapper.selectByExample(commentExample);

		//通过Commentator获得去重的评论人的所有Id,存在userIds里
		if (comments.size() == 0) {
			return new ArrayList<>();
		}
		Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
		ArrayList<Long> userIds = new ArrayList<>(commentators);

		//将评论人转化为Map，降低时间复杂度
		UserExample userExample = new UserExample();
		userExample.createCriteria()
				.andIdIn(userIds);
		userExample.setOrderByClause("gmt_modified desc");
		List<User> users = userMapper.selectByExample(userExample);
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

		//转换comment为commentDTO
		List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setName(userMap.get(comment.getCommentator()).getName());
			commentDTO.setAvatarUrl(userMap.get(comment.getCommentator()).getAvatarUrl());
			commentDTO.setUser(userMap.get(comment.getCommentator()));
			return commentDTO;
		}).collect(Collectors.toList());
		return commentDTOS;
	}
}
