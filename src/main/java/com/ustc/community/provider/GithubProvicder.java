package com.ustc.community.provider;

import com.alibaba.fastjson.JSON;
import com.ustc.community.dto.AccessToken;
import com.ustc.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/23
 */
@Component
public class GithubProvicder {


	public String getAccessToken(AccessToken accessToken){
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
		Request request = new Request.Builder()
				.url("https://github.com/login/oauth/access_token")
				.post(body)
				.build();
		try (Response response = client.newCall(request).execute()) {
			String string = response.body().string();
			String token=string.split("&")[0].split("=")[1];
//			System.out.println(token);
			return string;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public GithubUser getUser(String accessToken){
		OkHttpClient client=new OkHttpClient();
		String url="https://api.github.com/user?"+accessToken;
		Request request=new Request.Builder().url(url).build();

		try {
			Response response = client.newCall(request).execute();
			String str=response.body().string();
			GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
			return githubUser;
		} catch (IOException e) {
			e.printStackTrace();
		}
			return null;
	}

}
