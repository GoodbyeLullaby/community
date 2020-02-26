package com.ustc.community.model;


import lombok.Data;
import org.junit.Test;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/24
 */
@Data
public class User {
	private int id;
	private String account_id;
	private String name;
	private String token;
	private long gmt_create;
	private long gmt_modified;
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", account_id='" + account_id + '\'' +
				", name='" + name + '\'' +
				", token='" + token + '\'' +
				", gmt_create=" + gmt_create +
				", gmt_modified=" + gmt_modified +
				'}';
	}
}
