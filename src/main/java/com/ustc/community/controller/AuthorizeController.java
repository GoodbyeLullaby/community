package com.ustc.community.controller;

import com.ustc.community.service.UserService;
import com.ustc.community.dto.AccessToken;
import com.ustc.community.dto.GithubUser;
import com.ustc.community.model.User;
import com.ustc.community.provider.GithubProvicder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/23
 */
@Controller

public class AuthorizeController {
	@Resource
	private GithubProvicder githubProvicder;
	@Resource
	private UserService userService;


	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Value("${github.redirect.uri}")
	private String uri;


	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,
						   @RequestParam("state") String state,
						   HttpServletResponse response){
		System.out.println(code);
		AccessToken accessToken = new AccessToken();
		accessToken.setCode(code);
		accessToken.setRedirect_uri(uri);
		accessToken.setState(state);
		accessToken.setClient_id(clientId);
		accessToken.setClient_secret(clientSecret);

		System.out.println(accessToken);

		GithubUser githubUser = githubProvicder.getUser(githubProvicder.getAccessToken(accessToken));

		System.out.println(githubUser);
		if(githubUser!=null){
			//登录成功
			System.out.println("登录成功");
			User user = new User();
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(githubUser.getName());
			user.setBio(githubUser.getBio());
			user.setAvatarUrl(githubUser.getAvatarUrl());
			user.setGmtModified(System.currentTimeMillis());
			user.setAccountId(String.valueOf(githubUser.getId()));
			userService.createOrUpdate(user);
			//把生成的token加入到cookie中
			response.addCookie(new Cookie("token",token));
			return "redirect:/";
		}else {
			//登录失败
			System.out.println("登录失败");
			return "redirect:/";
		}
	}
	@GetMapping("logout")
	public String logout(HttpServletRequest request,
						 HttpServletResponse response){
		request.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("token", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}
}
