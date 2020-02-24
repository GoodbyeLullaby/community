package com.ustc.community.controller;

import com.ustc.community.dto.AccessToken;
import com.ustc.community.dto.GithubUser;
import com.ustc.community.provider.GithubProvicder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/23
 */
@Controller

public class AuthorizeController {
	@Autowired
	private GithubProvicder githubProvicder;

	@Value("${github.client.id}")
	private String clientId;
	@Value("{github.client.secret}")
	private String clientSecret;
	@Value("${github.redirect.uri}")
	private String uri;

	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,@RequestParam String state){
		System.out.println(code);
		AccessToken accessToken = new AccessToken();
		accessToken.setCode(code);
		accessToken.setRedirect_uri(uri);
		accessToken.setState(state);
		accessToken.setClient_id(clientId);
		accessToken.setClient_secret(clientSecret);

	//	System.out.println(accessToken);

		String token = githubProvicder.getAccessToken(accessToken);
		GithubUser user = githubProvicder.getUser(token);
//		System.out.println(user.getId());
		System.out.println(user.getName());
		return "index";
	}
}
