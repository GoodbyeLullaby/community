package com.ustc.community.service;

import com.ustc.community.dto.NotificationDTO;
import com.ustc.community.dto.Pagination;
import com.ustc.community.dto.QuestionDTO;
import com.ustc.community.enums.NotificationStatusEnunm;
import com.ustc.community.enums.NotificationTypeEnunm;
import com.ustc.community.exception.CustomizeErrorCode;
import com.ustc.community.exception.CustomizeException;
import com.ustc.community.mapper.NotificationMapper;
import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/8
 */
@Service
public class NotificationService {
	@Resource
	private NotificationMapper notificationMapper;
	@Resource
	private UserMapper userMapper;

	public Pagination listByUserId(Long userId, Integer page, Integer size) {

		Pagination<NotificationDTO> pagination=new Pagination<>();
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.setOrderByClause("gmt_modified desc");
		notificationExample.createCriteria()
				.andReceiverEqualTo(userId);

		Integer totalCount=(int)notificationMapper.countByExample(notificationExample);//计算数据总数
		Integer totalPage=(int)Math.ceil(totalCount*1.0/size);//页码

		//防止page参数越界
		if(page>totalPage){
			page=totalPage;
		}
		if(page<1){
			page=1;
		}

		pagination.setPagination(totalPage,page);

		Integer offset=size*(page-1);
		NotificationExample example = new NotificationExample();
		example.createCriteria()
				.andReceiverEqualTo(userId);
		example.setOrderByClause("gmt_create desc");
		List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
		if(notifications.size()==0){
			return pagination;
		}
		List<NotificationDTO>  notificationDTOS= new ArrayList<>();
		for (Notification notification : notifications) {
			NotificationDTO notificationDTO= new NotificationDTO();
			BeanUtils.copyProperties(notification, notificationDTO);
			notificationDTO.setTypeName(NotificationTypeEnunm.nameOfType(notification.getType()));
			notificationDTOS.add(notificationDTO);
		}

		pagination.setData(notificationDTOS);

		return pagination;

	}

	public Long unreadCount(Long id) {
		NotificationExample example = new NotificationExample();
		example.createCriteria()
				.andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnunm.UNREAD.getStatus());
		return notificationMapper.countByExample(example);
	}

	public NotificationDTO read(Long id, User user) {
		Notification notification = notificationMapper.selectByPrimaryKey(id);
		if(notification==null){
			throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
		}
		if(!user.getId().equals(notification.getReceiver())){
			throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FIAL);
		}
		notification.setStatus(NotificationStatusEnunm.READ.getStatus());
		notificationMapper.updateByPrimaryKey(notification);


		NotificationDTO notificationDTO=new NotificationDTO();
		BeanUtils.copyProperties(notification, notificationDTO);
		notificationDTO.setTypeName(NotificationTypeEnunm.nameOfType(notification.getType()));
		return notificationDTO;
	}
}
