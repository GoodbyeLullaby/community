package com.ustc.community.controller;

import com.ustc.community.dto.AccessToken;
import com.ustc.community.dto.GithubUser;
import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.User;
import com.ustc.community.provider.GithubProvicder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/23
 */
@Controller

public class AuthorizeController {
	@Autowired
	private GithubProvicder githubProvicder;
	@Autowired
	private UserMapper userMapper;


	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Value("${github.redirect.uri}")
	private String uri;


	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,
						   @RequestParam String state,
						   HttpServletRequest request){
		System.out.println(code);
		AccessToken accessToken = new AccessToken();
		accessToken.setCode(code);
		accessToken.setRedirect_uri(uri);
		accessToken.setState(state);
		accessToken.setClient_id(clientId);
		accessToken.setClient_secret(clientSecret);

		System.out.println(accessToken);

		String token = githubProvicder.getAccessToken(accessToken);
		GithubUser githubUser = githubProvicder.getUser(token);

		System.out.println(githubUser);
		if(githubUser!=null){
			//登录成功
			System.out.println("登录成功");
			User user = new User();
			user.setToken(UUID.randomUUID().toString());
			user.setName(githubUser.getName());
			user.setAccount_id(String.valueOf(githubUser.getId()));
			user.setGmt_create(System.currentTimeMillis());
			user.setGmt_modified(user.getGmt_create());
		//	userMapper.insert(user);
			request.getSession().setAttribute("githubUser", githubUser);
			return "redirect:/";
		}else {
			//登录失败
			System.out.println("登录失败");
			return "redirect:/";
		}
	}
}
