package com.ustc.community.mapper;

import com.ustc.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/24
 */
@Mapper
public interface UserMapper {
	@Insert("insert into user(name,account_id,token,gmt_create,gmt_modified)values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
	public void insert(User user);
}
