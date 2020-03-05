package com.ustc.community.service;

import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.User;
import com.ustc.community.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/3
 */
@Service
public class UserService {
	@Resource
	private UserMapper userMapper;

	public void createOrUpdate(User user) {
		UserExample userExample = new UserExample();
		userExample.createCriteria()
				.andAccountIdEqualTo(user.getAccountId());
		List<User> users = userMapper.selectByExample(userExample);
		if(users.size()==0){
			//插入
			user.setGmtCreate(System.currentTimeMillis());
			userMapper.insert(user);
		}else {

			//更新
			UserExample example = new UserExample();
			example.createCriteria()
					.andIdEqualTo(users.get(0).getId());
			userMapper.updateByExampleSelective(user, example);
		}
	}
}
